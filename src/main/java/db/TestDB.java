package db;

import db.beans.AspNetUsers_Bean;
import db.db_utils.DBConnection;

import java.sql.SQLException;

import static db.db_utils.DBSHome.details;

public class TestDB {
    public static void main(String[] args) throws SQLException {
        try {
            DBConnection.open("NBWallet");
            // Вызов метода details
            details(AspNetUsers_Bean.class);
         DBConnection.close();
        System.out.println("Соединение с базой данных закрыто.");


    } catch (SQLException e) {
        e.printStackTrace();
    }

    }

}
