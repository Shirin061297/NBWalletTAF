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
    private int id;
    private String firebaseUID;
    private String firstName;
    private String lastName;
    private String profileImage;
    private boolean deleted;
    private String userName;
    private String normalizedUserName;
    private String email;
    private String normalizedEmail;
    private boolean emailConfirmed;
    private String passwordHash;
    private String securityStamp;
    private String concurrencyStamp;
    private String phoneNumber;
    private boolean phoneNumberConfirmed;
    private boolean twoFactorEnabled;
    private String lockoutEnd;
    private boolean lockoutEnabled;
    private int accessFailedCount;
    private String refreshToken;
    private Timestamp refreshTokenExpiryTime;

    public static List<AspNetUsers_Bean> getAllUsers() throws SQLException {
        String query = "select * from \"AspNetUsers\"";
        try(ResultSet resultSet = DBConnection.query(query)){
            return new BeanProcessor().toBeanList(resultSet, AspNetUsers_Bean.class);
        }
    }

    public static AspNetUsers_Bean getBy(String column, String value) throws SQLException {
        String query = "select * from \"AspNetUsers\" where \"" + column + "\" = ?;";
        ResultSet resultSet = DBConnection.query(query, value);

        if (!resultSet.next()){
            return null;
        } else {
            return new BeanProcessor().toBean(resultSet, AspNetUsers_Bean.class);
        }
    }

    public static int createUser(AspNetUsers_Bean user) throws SQLException {
        // Проверка, существует ли пользователь с таким же NormalizedUserName или Email
        if (getBy("NormalizedUserName", user.getNormalizedUserName()) != null || getBy("Email", user.getEmail()) != null) {
            throw new SQLException("User with the same NormalizedUserName or Email already exists.");
        }

        String query = "INSERT INTO \"AspNetUsers\" (\"FirebaseUid\", \"FirstName\", \"LastName\", \"ProfileImage\", \"Deleted\", " +
                "\"UserName\", \"NormalizedUserName\", \"Email\", \"NormalizedEmail\", \"EmailConfirmed\", \"PasswordHash\", " +
                "\"SecurityStamp\", \"ConcurrencyStamp\", \"PhoneNumber\", \"PhoneNumberConfirmed\", \"TwoFactorEnabled\", " +
                "\"LockoutEnd\", \"LockoutEnabled\", \"AccessFailedCount\", \"RefreshToken\", \"RefreshTokenExpiryTime\") " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING \"Id\"";
        return DBConnection.updateAndReturnGeneratedKey(query, user.getFirebaseUID(), user.getFirstName(), user.getLastName(), user.getProfileImage(), user.isDeleted(),
                user.getUserName(), user.getNormalizedUserName(), user.getEmail(), user.getNormalizedEmail(), user.isEmailConfirmed(),
                user.getPasswordHash(), user.getSecurityStamp(), user.getConcurrencyStamp(), user.getPhoneNumber(), user.isPhoneNumberConfirmed(),
                user.isTwoFactorEnabled(), user.getLockoutEnd(), user.isLockoutEnabled(), user.getAccessFailedCount(), user.getRefreshToken(),
                user.getRefreshTokenExpiryTime());
    }
}
