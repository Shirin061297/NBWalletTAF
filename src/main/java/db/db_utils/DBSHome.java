package db.db_utils;

import ui.entites.UserAccountDetails;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBSHome {

    public static <T> List<UserAccountDetails> details(Class<T> bean) throws SQLException {
        String query = "SELECT * " +
                "FROM public.\"AspNetUsers\" u " +
                "JOIN public.\"Account\" a ON u.\"Id\" = a.\"UserId\" " +
                "ORDER BY u.\"Id\" DESC " +
                "LIMIT 10";
        List<UserAccountDetails> userDetailsList = new ArrayList<>();
        try (ResultSet resultSet = DBConnection.query(query)) {
            while (resultSet.next()) {
                String lastName = resultSet.getObject("LastName") != null ? resultSet.getString("LastName") : null;
                String firstName = resultSet.getObject("FirstName") != null ? resultSet.getString("FirstName") : null;
                String email = resultSet.getObject("Email") != null ? resultSet.getString("Email") : null;
                double balance = resultSet.getObject("Balance") != null ? resultSet.getDouble("Balance") : 0.0;
                userDetailsList.add(new UserAccountDetails(lastName, firstName, email, balance));
            }
        } catch (SQLException e) {
          //  System.out.println("SQL Exception: " + e.getMessage());
            e.printStackTrace();
        }
        return userDetailsList;
    }

//    String query = "SELECT u.\"LastName\", u.\"FirstName\", u.\"Email\", a.\"Balance\" " +
//            "FROM public.\"AspNetUsers\" u " +
//            "JOIN public.\"Account\" a ON u.\"Id\" = a.\"UserId\" " +
//            "ORDER BY u.\"Id\" DESC " +
//            "LIMIT 10";
//    List<UserAccountDetails> userDetailsList = new ArrayList<>();
//        try (ResultSet resultSet = DBConnection.query(query)) {
//            while (resultSet.next()) {
//                String lastName = resultSet.getString("LastName");
//                String firstName = resultSet.getString("FirstName");
//                String email = resultSet.getString("Email");
//                double balance = resultSet.getDouble("Balance");
//
//                UserAccountDetails userDetails = new UserAccountDetails(lastName, firstName, email, balance);
//                userDetailsList.add(userDetails);
//            }
//        } catch (SQLException e) {
//            System.out.println("SQL Exception: " + e.getMessage());
//            e.printStackTrace();
//        }
//
//       // System.out.println("Final list: " + userDetailsList);
//        return userDetailsList;
    }
