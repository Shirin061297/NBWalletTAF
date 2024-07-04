import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import ua.drivers.DriverManager;
import ua.helper.AlertHelper;
import ua.helper.BrowserHelper;
import ua.helper.WebElementActions;
import ua.utils.RandomUtils;

public class BaseTest {



    protected WebDriver driver;
    protected RandomUtils randomUtils;
    protected WebElementActions webElementActions;
    protected AlertHelper alertHelper;
    // protected DemoQAPages demoQAPages;
    protected BrowserHelper browserHelper;


    @BeforeClass(alwaysRun = true)
    public  void setUp(){
        driver = DriverManager.getDriver();
        randomUtils = new RandomUtils();
        webElementActions = new WebElementActions();
        alertHelper = new AlertHelper(driver);
        browserHelper = new BrowserHelper(driver);
    }

    @AfterClass(alwaysRun = true)
    public void tearDown(){
        DriverManager.closeDriver();
    }

}
