package ua.helper;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class IframeHelper {

    private static WebDriver driver;

    public IframeHelper(WebDriver driver) {
        this.driver = driver;
    }
//parentFrame() = переключится к родителю

    public  void  switchToParentFrame(){
        driver.switchTo().parentFrame();
    }

    public  void switchToFrame(String nameOrID){
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(nameOrID));//переключись по айди
        //driver.switchTo().frame(nameOrID);

    }

}
