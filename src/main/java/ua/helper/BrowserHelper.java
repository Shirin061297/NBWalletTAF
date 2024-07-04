package ua.helper;


import org.openqa.selenium.WebDriver;

import java.util.LinkedList;
import java.util.Set;

public class BrowserHelper {

    private WebDriver driver;

    public BrowserHelper(WebDriver driver) {

        this.driver = driver;
    }

    //NAVIGET&&GET методы ОНИ ПОХОЖИ ОБА ОТКЫВАЮТ (GET= драйвер НЕ ЗАПОМИНАЕТ ИСТОРИЮ)
    //(NAVIGATE= ОН ЗАПОМИНАЕТ ВСИ ИСТОРИЮ ПОСЕШЕНИИ И МОЖЕТ ВЕРНУТСЯ НАЗАД ОБНОВИТЬ СТР
    public  void open(String url){
        driver.navigate().to(url);

    }

    //
    public void goBack(){
        driver.navigate().back();
    }
    // ВПЕРЕД
    public void goForward(){
        driver.navigate().forward();
    }

    //ОБНОВИТЬ СТРАНИЦУ
    public void refreshThePage(){
        driver.navigate().refresh();
    }
    //  ЭТОТ МЕТОД СОХРАНЯЕТ ВКЛАДКИ БЕЗ ДУБЛИКАТОВ (УПРАВЛЕНИЯ ВКЛАДКАМИ)
    public Set<String> getWindowHandles(){
        return  driver.getWindowHandles(); // метод забирает вкладки
    }

    //БУДУТ ЛЕЖАТЬ ВСЕ ОТКРЫТЫЕ ВКЛАДКИ (ЕСЛИ ЗАХОТИМ ПЕРЕКЛЮЧАТЬСЯ ЧЕРЕЗ ИНДЕКС) ЕСЛИ МЕНЬШЕ ИЛИ БОЛЬШЕ ТО ОШИБКА THROW
    public void switchToWindow(int index){
        LinkedList<String> windowsId = new LinkedList<>(getWindowHandles());
        for (String window:windowsId){
            System.out.println(window);
        }

        if (index < 0 || index > windowsId.size())
            throw new IllegalArgumentException("Invalid index : " + index );
        driver.switchTo().window(windowsId.get(index));
    }
    //ЕСЛИ ЗАХОТИМ ОСТАВИТЬ РОДИТЕЛЬЯ ПОД 0 ИНДЕКСОМ
    public void switchToParentWindow(){
        LinkedList<String> windowsId = new LinkedList<>(getWindowHandles());
        driver.switchTo().window(windowsId.get(0));
    }
    //ВСЕ ВКЛАДКИ ЗАКРЫВАЕТ И ПЕРЕХОДТ НА  РОДИТЕЛЯ
    public void switchToParentWithChildClose(){
        switchToParentWindow();
        LinkedList<String> windowsId = new LinkedList<>(getWindowHandles());
        for (int i = 1; i > windowsId.size(); i++){

        }
    }




}
