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
@TableI(name = "AspNetUsers")
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class AspNetUsers_Bean {
    @Id(name = "Id")
    private int Id;
    private String FirebaseUid;
    private String FirstName;
    private String LastName;
    private String ProfileImage;
    private boolean Deleted;
    private String UserName;
    private String NormalizedUserName;
    private String Email;
    private String NormalizedEmail;
    private boolean EmailConfirmed;
    private String PasswordHash;
    private String SecurityStamp;
    private String ConcurrencyStamp;
    private String PhoneNumber;
    private boolean PhoneNumberConfirmed;
    private boolean TwoFactorEnabled;
    private String LockoutEnd;
    private boolean LockoutEnabled;
    private int AccessFailedCount;
    private String RefreshToken;
    private Timestamp RefreshTokenExpiryTime;

//    public static List<AspNetUsers_Bean> getAllUsers() throws SQLException {
//        String query = "select * from \"AspNetUsers\"";
//        try(ResultSet resultSet = DBConnection.query(query)){
//            return new BeanProcessor().toBeanList(resultSet, AspNetUsers_Bean.class);
//        }
//    }

//    public static AspNetUsers_Bean getBy(String column, String value) throws SQLException {
//        String query = "select * from \"AspNetUsers\" where \"" + column + "\" = ?;";
//        ResultSet resultSet = DBConnection.query(query, value);
//
//        if (!resultSet.next()){
//            return null;
//        } else {
//            return new BeanProcessor().toBean(resultSet, AspNetUsers_Bean.class);
//        }
//    }

//    public static int createUser(AspNetUsers_Bean user) throws SQLException {
//        // Проверка, существует ли пользователь с таким же NormalizedUserName или Email
//        if (getBy("NormalizedUserName", user.getNormalizedUserName()) != null || getBy("Email", user.getEmail()) != null) {
//            throw new SQLException("User with the same NormalizedUserName or Email already exists.");
//        }
//
//        String query = "INSERT INTO \"AspNetUsers\" (\"FirebaseUid\", \"FirstName\", \"LastName\", \"ProfileImage\", \"Deleted\", " +
//                "\"UserName\", \"NormalizedUserName\", \"Email\", \"NormalizedEmail\", \"EmailConfirmed\", \"PasswordHash\", " +
//                "\"SecurityStamp\", \"ConcurrencyStamp\", \"PhoneNumber\", \"PhoneNumberConfirmed\", \"TwoFactorEnabled\", " +
//                "\"LockoutEnd\", \"LockoutEnabled\", \"AccessFailedCount\", \"RefreshToken\", \"RefreshTokenExpiryTime\") " +
//                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING \"Id\"";
//        return DBConnection.updateAndReturnGeneratedKey(query, user.getFirebaseUID(), user.getFirstName(), user.getLastName(), user.getProfileImage(), user.isDeleted(),
//                user.getUserName(), user.getNormalizedUserName(), user.getEmail(), user.getNormalizedEmail(), user.isEmailConfirmed(),
//                user.getPasswordHash(), user.getSecurityStamp(), user.getConcurrencyStamp(), user.getPhoneNumber(), user.isPhoneNumberConfirmed(),
//                user.isTwoFactorEnabled(), user.getLockoutEnd(), user.isLockoutEnabled(), user.getAccessFailedCount(), user.getRefreshToken(),
//                user.getRefreshTokenExpiryTime());
//    }
//public static void createAccount(Account account) throws SQLException, IllegalAccessException {
//    DBConnection.createBean(account);
//}
//
//    public static void updateAccount(Account account) throws SQLException, IllegalAccessException {
//        DBConnection.updateBean(account);
//    }
//    public static void deleteAccount(int accountId) throws SQLException {
//        DBConnection.deleteRecordsByIds(Account.class, accountId);
//    }
}
