package ui.pages;

import org.openqa.selenium.support.PageFactory;
import ui.drivers.DriverManager;
import ui.helper.DropDownHelper;
import ui.helper.WebElementActions;


public abstract class BasePage {

        public BasePage(){
            PageFactory.initElements(DriverManager.getDriver(),this);
        }

        public WebElementActions webElementActions = new WebElementActions();

        public DropDownHelper dropDownHelper = new DropDownHelper(DriverManager.getDriver());
    }

