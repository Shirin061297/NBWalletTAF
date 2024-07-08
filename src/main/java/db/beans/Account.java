package db.beans;

import db.annotations.Id;
import db.annotations.TableI;
import db.db_utils.DBConnection;
import lombok.*;
import org.apache.commons.dbutils.BeanProcessor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import java.sql.Timestamp;

@TableI(name = "Account") // Имя вашей таблицы
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Account {
    @Id(name = "Id") // Убедитесь, что это соответствует имени столбца в вашей таблице
  //  private Integer Id; // Изменено с AccountId на Id
    private String Number;
    private double Balance;
    private int Currency;
    private int Status;
    private int UserId;
    private int AccountPlanId;
    private Timestamp CreatedDate;


//        public static List<Account> getAllAccounts() throws SQLException {
//            String query = "select * from \"Account\"";
//            try(ResultSet resultSet = DBConnection.query(query)){
//                return new BeanProcessor().toBeanList(resultSet, Account.class);
//            }
//        }

//        public static int createAccount(Account account) throws SQLException {
//            String query = "INSERT INTO \"Account\" (\"Number\", \"Balance\", \"Currency\", \"Status\", \"UserId\", \"AccountPlanId\", \"CreatedDate\") " +
//                    "VALUES (?, ?, ?, ?, ?, ?, ?)";
//            return DBConnection.updateAndReturnGeneratedKey(query, account.getNumber(), account.getBalance(), account.getCurrency(), account.getStatus(), account.getUserId(), account.getAccountPlanId(), account.getCreatedDate());
//        }

//        public static int deleteAccount(int userId) throws SQLException {
//           String query = "DELETE FROM \"Account\" WHERE \"UserId\" = ?";
//           return DBConnection.updateAndReturnGeneratedKey(query, userId);
//        }

//        public static void createAccount(Account account) throws SQLException, IllegalAccessException {
//            DBConnection.createBean(account);
//        }
//
//        public static void updateAccount(Account account) throws SQLException, IllegalAccessException {
//            DBConnection.updateBean(account);
//        }
//        public static void deleteAccount(int accountId) throws SQLException {
//            DBConnection.deleteRecordsByIds(Account.class, accountId);
//        }
    }