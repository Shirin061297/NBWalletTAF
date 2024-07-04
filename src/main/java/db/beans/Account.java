package db.beans;

import db.db_utils.DBConnection;
import lombok.*;
import org.apache.commons.dbutils.BeanProcessor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

    @Getter
    @Setter
    @EqualsAndHashCode
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @ToString
    public class Account {
        private int AccountId;
        private String Number;
        private double Balance;
        private int Currency;
        private int Status;
        private int UserId;
        private int AccountPlanId;
        private Timestamp CreatedDate;

        public static List<Account> getAllAccounts() throws SQLException {
            String query = "select * from \"Account\"";
            try(ResultSet resultSet = DBConnection.query(query)){
                return new BeanProcessor().toBeanList(resultSet, Account.class);
            }
        }

        public static int createAccount(Account account) throws SQLException {
            String query = "INSERT INTO \"Account\" (\"Number\", \"Balance\", \"Currency\", \"Status\", \"UserId\", \"AccountPlanId\", \"CreatedDate\") " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";
            return DBConnection.updateAndReturnGeneratedKey(query, account.getNumber(), account.getBalance(), account.getCurrency(), account.getStatus(), account.getUserId(), account.getAccountPlanId(), account.getCreatedDate());
        }

        public static int deleteAccount(int userId) throws SQLException {
           String query = "DELETE FROM \"Account\" WHERE \"UserId\" = ?";
           return DBConnection.updateAndReturnGeneratedKey(query, userId);
        }
    }