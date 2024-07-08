import db.db_utils.DBConnection;
import org.testng.annotations.Test;
import java.sql.SQLException;

public class WebTableTest extends BaseTest{

        @Test
        public void testAddAllUsersFromDB() throws SQLException, SQLException {
            driver.get("https://demoqa.com/webtables");

            // Убедитесь, что соединение с базой данных открыто
            DBConnection.open("NBWallet");

            // Вызов метода для добавления всех пользователей из базы данных
            webTablePage.addAllUsersFromDB();




        }


}
