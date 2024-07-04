package db.db_utils;

import db.annotations.Id;
import db.annotations.TableI;
import org.postgresql.ds.PGSimpleDataSource;

import java.lang.reflect.Field;
import java.sql.*;

import static ua.utils.ConfigReader.getValue;

public class DBConnection {

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

    public static int updateAndReturnGeneratedKey(String query, Object... params) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]);
            }
            preparedStatement.executeUpdate();
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating record failed, no ID obtained.");
                }
            }
        }
    }

    private static int update(String query, Object... params) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]);
            }
            return preparedStatement.executeUpdate();
        }
    }

    public  static <T> void deleteRecordsByIds(Class<T> clazz, Object... ids) throws SQLException {
        // Проверка наличия аннотации @Table у класса
        TableI tableAnnotation = clazz.getAnnotation(TableI.class);
        if (tableAnnotation == null) {
            throw new IllegalArgumentException("The class must be annotated with @Table");
        }

        // Получение имени таблицы из аннотации @Table
        String tableName = tableAnnotation.name();

        // Поиск поля с аннотацией @Id
        Field idField = null;
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(Id.class)) {
                idField = field;
                break;
            }
        }

        if (idField == null) {
            throw new IllegalArgumentException("The class must have a field annotated with @Id");
        }

        // Получение имени столбца идентификатора из аннотации @Id
        Id idAnnotation = idField.getAnnotation(Id.class);
        String idColumnName = idAnnotation.name();

        // Удаление связанных записей из таблицы "Account"
        StringBuilder deleteAccountsQueryBuilder = new StringBuilder("DELETE FROM \"Account\" WHERE \"UserId\" IN (");
        for (int i = 0; i < ids.length; i++) {
            deleteAccountsQueryBuilder.append("?");
            if (i < ids.length - 1) {
                deleteAccountsQueryBuilder.append(", ");
            }
        }
        deleteAccountsQueryBuilder.append(")");

        String deleteAccountsQuery = deleteAccountsQueryBuilder.toString();

        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteAccountsQuery)) {
            for (int i = 0; i < ids.length; i++) {
                preparedStatement.setObject(i + 1, ids[i]);
            }
            preparedStatement.executeUpdate();
        }

        // Удаление записей из основной таблицы
        StringBuilder deleteRecordsQueryBuilder = new StringBuilder("DELETE FROM \"" + tableName + "\" WHERE \"" + idColumnName + "\" IN (");
        for (int i = 0; i < ids.length; i++) {
            deleteRecordsQueryBuilder.append("?");
            if (i < ids.length - 1) {
                deleteRecordsQueryBuilder.append(", ");
            }
        }
        deleteRecordsQueryBuilder.append(")");

        String deleteRecordsQuery = deleteRecordsQueryBuilder.toString();

        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteRecordsQuery)) {
            for (int i = 0; i < ids.length; i++) {
                preparedStatement.setObject(i + 1, ids[i]);
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
