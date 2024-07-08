package db.db_utils;

import db.annotations.Id;
import db.annotations.TableI;
import lombok.var;
import org.apache.commons.dbutils.BeanProcessor;
import org.postgresql.ds.PGSimpleDataSource;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ui.utils.ConfigReader.getValue;

public class DBConnection {

    public static Connection c;
    private static Connection connection;
    private static Statement statement;

    private DBConnection() {
    }

    private static PGSimpleDataSource getBaseDataSource(String database) {
        PGSimpleDataSource dataSource = new PGSimpleDataSource() {{
            setServerName(getValue("server"));
            setPortNumber(Integer.parseInt(getValue("port")));
            setUser(getValue("user"));
            setPassword(getValue("sql_password"));
            setDatabaseName(database);
        }};
        return dataSource;
    }

    public static void open(String database) throws SQLException {
        if (connection == null) {
            connection = getBaseDataSource(database).getConnection();
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        }
    }

    public static ResultSet query(String query, Object... params) throws SQLException {
        if (connection == null || connection.isClosed()) {
            throw new SQLException("Connection is closed");
        }

        if (params.length == 0) {
            return statement.executeQuery(query);
        } else {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]);
            }
            return preparedStatement.executeQuery();
        }
    }


    /**
     * Вставляет новый объект Bean в таблицу базы данных.
     *
     * @param bean объект, который нужно вставить в базу данных
     * @param <T>  тип объекта
     * @throws SQLException           если происходит ошибка SQL
     * @throws IllegalAccessException если происходит ошибка доступа к полям объекта
     */
    public static <T> void createBean(T bean) throws SQLException, IllegalAccessException {
        // Проверка, что соединение с базой данных установлено и не закрыто
        if (connection == null || connection.isClosed()) {
            throw new SQLException("Connection is closed");
        }

        // Получение класса объекта и аннотации @TableI
        Class<?> clazz = bean.getClass();
        TableI tableAnnotation = clazz.getAnnotation(TableI.class);
        if (tableAnnotation == null) {
            throw new IllegalArgumentException("The class must be annotated with @TableI");
        }

        // Извлечение имени таблицы из аннотации @TableI
        String tableName = tableAnnotation.name();

        // Собираем значения всех полей Bean в карту
        Map<String, Object> fieldValues = new HashMap<>();
        Field idField = null;
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(Id.class)) {
                idField = field;
            } else {
                fieldValues.put(field.getName(), field.get(bean));
            }
        }

        // Формирование SQL-запроса для вставки новой записи
        StringBuilder insertQuery = new StringBuilder("INSERT INTO \"" + tableName + "\" (");
        StringBuilder valuesPart = new StringBuilder("VALUES (");
        int i = 0;
        for (Map.Entry<String, Object> entry : fieldValues.entrySet()) {
            if (i > 0) {
                insertQuery.append(", ");
                valuesPart.append(", ");
            }
            insertQuery.append("\"").append(entry.getKey()).append("\"");
            valuesPart.append("?");
            i++;
        }
        insertQuery.append(") ").append(valuesPart).append(")");

        String sql = insertQuery.toString();

        // Вывод отладочной информации
        System.out.println("Generated SQL: " + sql);
        System.out.println("Field values: " + fieldValues);

        // Выполнение запроса на вставку
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            i = 1;
            for (Map.Entry<String, Object> entry : fieldValues.entrySet()) {
                preparedStatement.setObject(i++, entry.getValue());
            }
            preparedStatement.executeUpdate();

            // Получение сгенерированного значения Id, если оно было автоинкрементным
            if (idField != null && idField.get(bean) == null) {
                try (var rs = preparedStatement.getGeneratedKeys()) {
                    if (rs.next()) {
                        idField.set(bean, rs.getInt(1));
                    }
                }
            }
        }
    }

    /**
     * Получает все записи из таблицы, соответствующей указанному классу.
     *
     * @param clazz класс, соответствующий таблице в базе данных
     * @param <T>   тип объекта
     * @return список объектов, соответствующих записям в таблице
     * @throws SQLException если происходит ошибка SQL
     */
    public static <T> List<T> getAllRecords(Class<T> clazz) throws SQLException {
        // Получение аннотации @TableI из класса
        TableI tableAnnotation = clazz.getAnnotation(TableI.class);
        if (tableAnnotation == null) {
            throw new IllegalArgumentException("The class must be annotated with @TableI");
        }

        // Извлечение имени таблицы из аннотации @TableI
        String tableName = tableAnnotation.name();

        // Формирование SQL-запроса для получения всех записей из таблицы
        String query = "SELECT * FROM \"" + tableName + "\"";

        // Выполнение запроса и преобразование результатов в список объектов
        try (ResultSet resultSet = DBConnection.query(query)) {
            return new BeanProcessor().toBeanList(resultSet, clazz);
        }
    }


    /**
     * Обновляет запись в базе данных на основе значений полей объекта.
     *
     * @param record объект, содержащий данные для обновления
     * @param <T>    тип объекта
     * @throws SQLException             если соединение с базой данных закрыто или происходит ошибка SQL
     * @throws IllegalAccessException   если происходит ошибка доступа к полям объекта
     */
    public static <T> void updateRecord(T record) throws SQLException, IllegalAccessException {
        // Проверка, что соединение с базой данных открыто
        if (connection == null || connection.isClosed()) {
            throw new SQLException("Connection is closed");
        }

        // Получение класса объекта
        Class<?> clazz = record.getClass();

        // Получение аннотации @TableI из класса
        TableI tableAnnotation = clazz.getAnnotation(TableI.class);
        if (tableAnnotation == null) {
            throw new IllegalArgumentException("The class must be annotated with @TableI");
        }

        // Извлечение имени таблицы из аннотации @TableI
        String tableName = tableAnnotation.name();

        // Поиск поля, аннотированного @Id (Primary Key)
        Field idField = null;
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(Id.class)) {
                idField = field;
                break;
            }
        }

        // Если поле с аннотацией @Id не найдено, выбрасываем исключение
        if (idField == null) {
            throw new IllegalArgumentException("The class must have a field annotated with @Id");
        }

        // Получение имени колонки идентификатора из аннотации @Id
        Id idAnnotation = idField.getAnnotation(Id.class);
        String idColumnName = idAnnotation.name();

        // Сбор значений полей объекта в карту
        Map<String, Object> fieldValues = new HashMap<>();
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            fieldValues.put(field.getName(), field.get(record));
        }

        // Формирование SQL-запроса для обновления записи
        StringBuilder updateQuery = new StringBuilder("UPDATE \"" + tableName + "\" SET ");
        int i = 0;
        for (Map.Entry<String, Object> entry : fieldValues.entrySet()) {
            // Исключаем поле идентификатора из списка обновляемых полей
            if (!entry.getKey().equals(idField.getName())) {
                if (i > 0) {
                    updateQuery.append(", ");
                }
                updateQuery.append("\"").append(entry.getKey()).append("\" = ?");
                i++;
            }
        }
        updateQuery.append(" WHERE \"").append(idColumnName).append("\" = ?");

        String sql = updateQuery.toString();

        // Выполнение подготовленного запроса на обновление
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            i = 1;
            for (Map.Entry<String, Object> entry : fieldValues.entrySet()) {
                if (!entry.getKey().equals(idField.getName())) {
                    preparedStatement.setObject(i++, entry.getValue());
                }
            }
            preparedStatement.setObject(i, fieldValues.get(idField.getName()));
            preparedStatement.executeUpdate();
        }
    }

    /**
     * Удаляет записи из таблицы на основе предоставленных идентификаторов.
     *
     * @param clazz класс, аннотированный @TableI, представляющий таблицу базы данных
     * @param ids   массив идентификаторов записей, которые нужно удалить
     * @param <T>   тип объекта
     * @throws SQLException если происходит ошибка SQL
     */
    public static <T> void deleteRecordsByIds(Class<T> clazz, Object... ids) throws SQLException {
        // Проверка, открыто ли соединение с базой данных
        if (connection == null || connection.isClosed()) {
            throw new SQLException("Connection is closed");
        }

        // Получение аннотации @TableI из класса
        TableI tableAnnotation = clazz.getAnnotation(TableI.class);
        if (tableAnnotation == null) {
            throw new IllegalArgumentException("The class must be annotated with @TableI");
        }

        // Извлечение имени таблицы из аннотации @TableI
        String tableName = tableAnnotation.name();

        // Поиск поля, аннотированного @Id, для получения имени колонки идентификатора
        Field idField = null;
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(Id.class)) {
                idField = field;
                break;
            }
        }

        // Если поле с аннотацией @Id не найдено, выбрасываем исключение
        if (idField == null) {
            throw new IllegalArgumentException("The class must have a field annotated with @Id");
        }

        // Получение имени колонки идентификатора из аннотации @Id
        Id idAnnotation = idField.getAnnotation(Id.class);
        String idColumnName = idAnnotation.name();

        // Построение SQL-запроса на удаление записей
        StringBuilder deleteRecordsQueryBuilder = new StringBuilder("DELETE FROM \"" + tableName + "\" WHERE \"" + idColumnName + "\" IN (");
        for (int i = 0; i < ids.length; i++) {
            // Добавляем плейсхолдер `?` для каждого идентификатора
            deleteRecordsQueryBuilder.append("?");
            // Если это не последний идентификатор, добавляем запятую
            if (i < ids.length - 1) {
                deleteRecordsQueryBuilder.append(", ");
            }
        }
        // Закрываем скобку после перечисления плейсхолдеров
        deleteRecordsQueryBuilder.append(")");

        // Преобразуем StringBuilder в строку, содержащую полный SQL-запрос
        String deleteRecordsQuery = deleteRecordsQueryBuilder.toString();

        // Подготавливаем и выполняем SQL-запрос на удаление записей
        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteRecordsQuery)) {
            // Заполняем плейсхолдеры `?` фактическими значениями идентификаторов из массива `ids`
            for (int i = 0; i < ids.length; i++) {
                preparedStatement.setObject(i + 1, ids[i]);
            }
            // Выполняем SQL-запрос на удаление записей
            preparedStatement.executeUpdate();
        }
    }

    /**
     * Удаляет записи из таблицы на основе предоставленных критериев.
     *
     * @param clazz    класс, аннотированный @TableI, представляющий таблицу базы данных
     * @param criteria карта, содержащая названия колонок и значения, по которым нужно удалить записи
     * @param <T>      тип объекта
     * @throws SQLException если происходит ошибка SQL
     */
    public static <T> void deleteByMultipleColumnValues(Class<T> clazz, Map<String, Object> criteria) throws SQLException {
        // Проверка, открыто ли соединение с базой данных
        if (connection == null || connection.isClosed()) {
            throw new SQLException("Connection is closed");
        }

        // Получение аннотации @TableI из класса
        TableI tableAnnotation = clazz.getAnnotation(TableI.class);
        if (tableAnnotation == null) {
            throw new IllegalArgumentException("The class must be annotated with @TableI");
        }

        // Извлечение имени таблицы из аннотации @TableI
        String tableName = tableAnnotation.name();

        // Построение SQL-запроса на удаление записей с учетом критериев
        StringBuilder deleteQueryBuilder = new StringBuilder("DELETE FROM \"" + tableName + "\" WHERE ");
        int i = 0;
        for (Map.Entry<String, Object> entry : criteria.entrySet()) {
            if (i > 0) {
                deleteQueryBuilder.append(" OR ");
            }
            deleteQueryBuilder.append("\"").append(entry.getKey()).append("\" = ?");
            i++;
        }

        // Преобразуем StringBuilder в строку, содержащую полный SQL-запрос
        String deleteQuery = deleteQueryBuilder.toString();

        // Подготавливаем и выполняем SQL-запрос на удаление записей
        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
            i = 1;
            for (Map.Entry<String, Object> entry : criteria.entrySet()) {
                preparedStatement.setObject(i++, entry.getValue());
            }
            preparedStatement.executeUpdate();
        }
    }


    public static void close() {
        try {
            if (statement != null) {
                statement.close();
                statement = null;
            }
            if (connection != null) {
                connection.close();
                connection = null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

