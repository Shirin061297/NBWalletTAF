package ua.helper;

import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ua.drivers.DriverManager;

import java.time.Duration;

public class AlertHelper {

    private WebDriver driver = DriverManager.getDriver();

    public AlertHelper(WebDriver driver){
        this.driver=driver;
    }

    //ДРАЙВЕР ПЕРЕКЛЮЧИСЬ НА АЛЕРТ С HTML КОДА
    public Alert getAlert(){
        return  driver.switchTo().alert();
    }

    //ЧТОБЫ ПРИНЯТЬ ИХ УСЛОВИЕ МЫ ВЫЗЫВАЕМ МЕТОД ACCEPT(ПРИНЕМАЙ АЛЕРТ)
    public void  acceptAlert(){
        getAlert().accept();
    }

    //ЕСЛИ НЕ ХОТИМ СОГЛ С ИХ УСЛОВИЯМИ ТО ОТКЛОНИТЬ.. ЯВНОЕ ОЖИДАНИЯ ЧТОБ ЖДАЛ 10 СЕК
    public void dismissAlert(){
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.alertIsPresent());
        getAlert().dismiss();
    }

    public String getAlertText(){
        return  getAlert().getText();
    }

    //ЕСЛИ ЕСТЬ АЛЕРТ ТО ЛОВИ ЕСЛИ НЕТ ТО ПОКАЖИ ОШИБКУ И FALSE
    public boolean isAlertPresent(){
        try {
            driver.switchTo().alert().accept();
            return true;
        }catch (NoAlertPresentException e){
            return  false;
        }
    }


    //ЕСЛИ АЛЕРТ ЕСТЬ ТО ПРИНИМАЙ А ЕСЛИ НЕТ ОСТАНОВИ
    public  void  acceptAlertIfPresent(){
        if (!isAlertPresent())
            return;
        acceptAlert();
    }

    //ЕСЛИ ОН ЕСТЬ ОТКЛОНИ
    public  void dismissAlertIfPresent(){
        if (!isAlertPresent())
            return;
        dismissAlert();
    }


    //КОГДА ВЫЗЫВАЕМ МЕТОД ПЕРЕДАЕМ ПАРАМЕТР ЕСЛИ ЕСТЬ ВВОДИ ИМЯ НАПРИМЕР
    public void acceptProm(String txt){
        if (!isAlertPresent())
            return;
        Alert alert = getAlert();
        alert.sendKeys(txt);
        alert.accept();
    }

    //ЕСЛИ ЕСТЬ ВВОДИ ТЕКСТ И ОТКЛОНИ
    public void dismissProm(String txt){
        if (!isAlertPresent())
            return;
        Alert alert = getAlert();
        alert.sendKeys(txt);
        alert.dismiss();
    }




}

