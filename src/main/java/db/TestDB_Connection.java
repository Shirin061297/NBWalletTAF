package db;

import db.beans.AspNetUsers_Bean;
import db.beans.Account;
import db.db_utils.DBConnection;
import ui.utils.RandomUtils;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class TestDB_Connection {

    public static void main(String[] args) {
        try {

            DBConnection.open("NBWallet"); // Открываем соединение с вашей базой данных

            // Создаем новый объект AspNetUsers_Bean с использованием RandomUtils
            AspNetUsers_Bean user = RandomUtils.generateRandomUser();

            // Вставляем объект AspNetUsers_Bean в базу данных
           DBConnection.createBean(user);

            System.out.println("Запись пользователя успешно добавлена! AspNetUsers_Bean");
            System.out.println("Сгенерированный ID пользователя  : " + user.getId());

            // Создаем новый объект Account с использованием RandomUtils

            // Создаем новый объект Account вручную
            Account account = Account.builder()
                    .Number("1234567890")
                    .Balance(1000.50)
                    .Currency(1)
                    .Status(1)
                    .UserId(1)
                    .AccountPlanId(1)
                    .CreatedDate(new Timestamp(System.currentTimeMillis()))
                    .build();

            // Вставляем объект Account в базу данных
            DBConnection.createBean(account);

            System.out.println("Record inserted successfully!");


  //           Удаление нескольких пользователей по их ID
            try {
                DBConnection.deleteRecordsByIds(Account.class, 285);
                System.out.println("Пользователи с ID удалены.");
            } catch (SQLException e) {
                e.printStackTrace();
            }

            user.setFirstName("UpdatedFirstName");
            user.setLastName("UpdatedLastName");
            DBConnection.updateRecord(user);
            System.out.println("Запись пользователя успешно обновлена!");

            // Получаем все записи из таблицы AspNetUsers и выводим их на консоль
            List<AspNetUsers_Bean> users = DBConnection.getAllRecords(AspNetUsers_Bean.class);
            System.out.println("Все пользователи AspNetUsers:");
            users.forEach(System.out::println);

            // Получаем все записи из таблицы Account и выводим их на консоль
            List<Account> accounts = DBConnection.getAllRecords(Account.class);
            System.out.println("Все счета Account:");
            accounts.forEach(System.out::println);

//            Map<String, Object> criteria = new HashMap<>();
//            criteria.put("Id", 285);
//            criteria.put("TwoFactorEnabled", false);
//
//            // Вызываем метод для удаления записей по критериям
//            DBConnection.deleteByMultipleColumnValues(Account.class, criteria);




            // Закрываем соединение с базой данных
            DBConnection.close();
            System.out.println("Соединение с базой данных закрыто.");


        } catch (SQLException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}





