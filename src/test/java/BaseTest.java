import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import ui.drivers.DriverManager;
import ui.helper.AlertHelper;
import ui.helper.BrowserHelper;
import ui.helper.WebElementActions;
import ui.pages.WebTablePage;
import ui.utils.RandomUtils;

public class BaseTest {



    protected WebDriver driver;
    protected RandomUtils randomUtils;
    protected WebElementActions webElementActions;
    protected AlertHelper alertHelper;
    // protected DemoQAPages demoQAPages;
    protected BrowserHelper browserHelper;
    protected WebTablePage webTablePage;


    @BeforeClass(alwaysRun = true)
    public  void setUp(){
        driver = DriverManager.getDriver();
        randomUtils = new RandomUtils();
        webElementActions = new WebElementActions();
        alertHelper = new AlertHelper(driver);
        browserHelper = new BrowserHelper(driver);
        webTablePage = new WebTablePage();
    }

    @AfterClass(alwaysRun = true)
    public void tearDown(){
        DriverManager.closeDriver();
    }

}
