package db;

import db.beans.AspNetUsers_Bean;
import db.db_utils.DBConnection;
import db.beans.Account;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class TestDB_Connection {

    public static void main(String[] args) {
        try {
            DBConnection.open("NBWallet");

            // Создание нового пользователя
            AspNetUsers_Bean newUser1 = AspNetUsers_Bean.builder()
                    .firebaseUID("firebase_uid1")
                    .firstName("Shirin1")
                    .lastName("Shirin1")
                    .profileImage(null)
                    .deleted(false)
                    .userName("Bekmuratova1")
                    .normalizedUserName("SHIRIN1" + System.currentTimeMillis())  // Добавляем уникальность
                    .email("shirin1.test" + System.currentTimeMillis() + "@test.com")  // Добавляем уникальность
                    .normalizedEmail("Shirin1" + System.currentTimeMillis() + "@TEST.COM")  // Добавляем уникальность
                    .emailConfirmed(true)  // Передаем булевое значение
                    .passwordHash("shirin1231")
                    .securityStamp("security_stamp1")
                    .concurrencyStamp("concurrency_stamp1")
                    .phoneNumber("12345678061")
                    .phoneNumberConfirmed(false)  // Передаем булевое значение
                    .twoFactorEnabled(false)  // Передаем булевое значение
                    .lockoutEnd(null)
                    .lockoutEnabled(true)  // Передаем булевое значение
                    .accessFailedCount(0)
                    .refreshToken("refresh_token1")
                    .refreshTokenExpiryTime(new Timestamp(System.currentTimeMillis()))  // Передаем Timestamp
                    .build();

            int userId1 = AspNetUsers_Bean.createUser(newUser1);
            newUser1.setId(userId1);

            AspNetUsers_Bean newUser2 = AspNetUsers_Bean.builder()
                    .firebaseUID("firebase_uid2")
                    .firstName("Shirin2")
                    .lastName("Shirin2")
                    .profileImage(null)
                    .deleted(false)
                    .userName("Bekmuratova2")
                    .normalizedUserName("SHIRIN2" + System.currentTimeMillis())  // Добавляем уникальность
                    .email("shirin2.test" + System.currentTimeMillis() + "@test.com")  // Добавляем уникальность
                    .normalizedEmail("Shirin2" + System.currentTimeMillis() + "@TEST.COM")  // Добавляем уникальность
                    .emailConfirmed(true)  // Передаем булевое значение
                    .passwordHash("shirin1232")
                    .securityStamp("security_stamp2")
                    .concurrencyStamp("concurrency_stamp2")
                    .phoneNumber("12345678062")
                    .phoneNumberConfirmed(false)  // Передаем булевое значение
                    .twoFactorEnabled(false)  // Передаем булевое значение
                    .lockoutEnd(null)
                    .lockoutEnabled(true)  // Передаем булевое значение
                    .accessFailedCount(0)
                    .refreshToken("refresh_token2")
                    .refreshTokenExpiryTime(new Timestamp(System.currentTimeMillis()))  // Передаем Timestamp
                    .build();

            int userId2 = AspNetUsers_Bean.createUser(newUser2);
            newUser2.setId(userId2);

            AspNetUsers_Bean newUser3 = AspNetUsers_Bean.builder()
                    .firebaseUID("firebase_uid3")
                    .firstName("Shirin3")
                    .lastName("Shirin3")
                    .profileImage(null)
                    .deleted(false)
                    .userName("Bekmuratova3")
                    .normalizedUserName("SHIRIN3" + System.currentTimeMillis())  // Добавляем уникальность
                    .email("shirin3.test" + System.currentTimeMillis() + "@test.com")  // Добавляем уникальность
                    .normalizedEmail("Shirin3" + System.currentTimeMillis() + "@TEST.COM")  // Добавляем уникальность
                    .emailConfirmed(true)  // Передаем булевое значение
                    .passwordHash("shirin1233")
                    .securityStamp("security_stamp3")
                    .concurrencyStamp("concurrency_stamp3")
                    .phoneNumber("12345678063")
                    .phoneNumberConfirmed(false)  // Передаем булевое значение
                    .twoFactorEnabled(false)  // Передаем булевое значение
                    .lockoutEnd(null)
                    .lockoutEnabled(true)  // Передаем булевое значение
                    .accessFailedCount(0)
                    .refreshToken("refresh_token3")
                    .refreshTokenExpiryTime(new Timestamp(System.currentTimeMillis()))  // Передаем Timestamp
                    .build();

            int userId3 = AspNetUsers_Bean.createUser(newUser3);
            newUser3.setId(userId3);

            // Проверяем, что пользователи были успешно созданы
            System.out.println("User1 created with ID: " + userId1);
            System.out.println("User2 created with ID: " + userId2);
            System.out.println("User3 created with ID: " + userId3);

            // Создание счетов для новых пользователей
            Account newAccount1 = Account.builder()
                    .Number("00000000001")
                    .Balance(100000.0)
                    .Currency(1)
                    .Status(1)
                    .UserId(newUser1.getId())  // Используем корректный ID пользователя
                    .AccountPlanId(1)
                    .CreatedDate(new Timestamp(System.currentTimeMillis()))
                    .build();
            Account.createAccount(newAccount1);

            Account newAccount2 = Account.builder()
                    .Number("00000000002")
                    .Balance(100000.0)
                    .Currency(1)
                    .Status(1)
                    .UserId(newUser1.getId())  // Используем корректный ID пользователя
                    .AccountPlanId(1)
                    .CreatedDate(new Timestamp(System.currentTimeMillis()))
                    .build();
            Account.createAccount(newAccount2);

            Account newAccount3 = Account.builder()
                    .Number("00000000003")
                    .Balance(100000.0)
                    .Currency(1)
                    .Status(1)
                    .UserId(newUser1.getId())  // Используем корректный ID пользователя
                    .AccountPlanId(1)
                    .CreatedDate(new Timestamp(System.currentTimeMillis()))
                    .build();
            Account.createAccount(newAccount3);

            System.out.println("Accounts created for users");

            // Получение всех пользователей и проверка
            List<AspNetUsers_Bean> users = AspNetUsers_Bean.getAllUsers();
            System.out.println("All users before deletion:");
            users.forEach(System.out::println);

            // Получение всех счетов и проверка
            List<Account> accounts = Account.getAllAccounts();
            System.out.println("All accounts before deletion:");
            accounts.forEach(System.out::println);

            // Удаление нескольких пользователей по их ID
           DBConnection.deleteRecordsByIds(AspNetUsers_Bean.class, userId1, userId2, userId3);

            System.out.println("Users with IDs " + userId1 + ", " + userId2 + ", " + userId3 + " deleted.");

            // Проверка удаления пользователей
            users = AspNetUsers_Bean.getAllUsers();
            System.out.println("All users after deletion:");
            users.forEach(System.out::println);

            // Закрываем соединение с базой данных
            DBConnection.close();
            System.out.println("Database connection closed.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


