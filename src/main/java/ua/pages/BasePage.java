package ua.pages;

import org.openqa.selenium.support.PageFactory;
import ua.drivers.DriverManager;
import ua.helper.DropDownHelper;
import ua.helper.WebElementActions;


public abstract class BasePage {

        public BasePage(){
            PageFactory.initElements(DriverManager.getDriver(),this);
        }

        public WebElementActions webElementActions = new WebElementActions();

        public DropDownHelper dropDownHelper = new DropDownHelper(DriverManager.getDriver());
    }

