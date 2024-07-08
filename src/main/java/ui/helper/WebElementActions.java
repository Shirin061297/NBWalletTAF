package ui.helper;
import io.qameta.allure.Step;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ui.drivers.DriverManager;

import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class WebElementActions {//вcпомогательные классы добовляем в этот пакет КАСТОМНЫЕ МЕТОДЫ

    Actions actions = new Actions(DriverManager.getDriver());//ДАЕТ БОЛЬШЕ ВОЗМОЖНОСТЬЕЙ РАБОТАТЬ МЫШКОЙ




    //мы делаем метод чейнинг, в эти готовые методы будем использовать
    //это кастомные методы , в методы можем добавить доп условии
    @Step("Performing click {1}")
    public WebElementActions click(WebElement element) {
        waitElementTobeClickAble(element);
        // scrollToElement(element);
        highlightElement(element);
        //jsClick(element);
        element.click();
        return this;
    }

    public WebElementActions sendKeys(WebElement element,String txt){
        waitElementToBeVisible(element);
        scrollToElement(element);
        highlightElement(element);
        element.sendKeys(txt);
        return this;

    }

    public WebElementActions waitElementTobeClickAble(WebElement element){
        new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(15))
                .until(ExpectedConditions.elementToBeClickable(element));
        return this;
    }

    public WebElementActions waitElementToBeVisible(WebElement element){
        new WebDriverWait(DriverManager.getDriver(),Duration.ofSeconds(15))
                .until(ExpectedConditions.visibilityOf(element));
        return this;
    }


    public WebElementActions scrollToElement(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
        js.executeScript("arguments[0].scrollIntoView(true);", element);
        return this;

    }

    public WebElementActions randomClickElement( List <WebElement> element){
        int randomIndex = new Random().nextInt(element.size());
        WebElement randomLabel = element.get(randomIndex);
        click(randomLabel);
        return this;
    }

    public WebElementActions jsClick (WebElement element){
        JavascriptExecutor js = (JavascriptExecutor)DriverManager.getDriver();
        return this;
    }

    public WebElementActions highlightElement(WebElement element){
        JavascriptExecutor js = (JavascriptExecutor)DriverManager.getDriver();
        js.executeScript("arguments[0].style.border='3px solid red'",element);
        return  this;
    }

    public WebElementActions  jsSendKeys(WebElement element , String txt){
        JavascriptExecutor js = (JavascriptExecutor)DriverManager.getDriver();
        js.executeScript("arguments[0].value=arguments[1];", element , txt);
        return this;

    }

    public WebElementActions  doubleClick(WebElement element){
        waitElementToBeVisible(element);
        waitElementTobeClickAble(element);
        actions.doubleClick(element).perform();
        return  this;
    }

    public WebElementActions rightClick(WebElement element){
        waitElementToBeVisible(element) ;
        waitElementTobeClickAble(element);
        actions.contextClick(element).perform();
        return  this;
    }
    //Наводит на элемент
    public WebElementActions moveToElement(WebElement element){
        waitElementToBeVisible(element);
        actions.moveToElement(element).perform();
        return  this;
    }

    public void pause(Integer milliseconds){
        try {
            TimeUnit.MICROSECONDS.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }





}
