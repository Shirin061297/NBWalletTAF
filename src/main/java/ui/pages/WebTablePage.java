package ui.pages;

import com.github.javafaker.Faker;
import db.db_utils.DBSHome;
import ui.entites.UserAccountDetails;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import java.sql.SQLException;
import java.util.List;

public class WebTablePage extends BasePage {

    @FindBy(id = "addNewRecordButton")
    public WebElement addNewBtn;

    @FindBy(id = "firstName")
    public WebElement firstnameInput;

    @FindBy(id = "lastName")
    public WebElement lastnameInput;

    @FindBy(id = "age")
    public WebElement ageInput;

    @FindBy(id = "userEmail")
    public WebElement emailInput;

    @FindBy(id = "salary")
    public WebElement salaryInput;

    @FindBy(id = "department")
    public WebElement departmentInput;

    @FindBy(id = "submit")
    public WebElement submitBtn;

    private static final Faker faker = new Faker();
    public WebTablePage add(UserAccountDetails userAccountDetails) {
        int randomAge = faker.number().numberBetween(18, 40);
        String randomDepartment = faker.job().position();
        long roundedSalary = Math.round(userAccountDetails.getBalance());

        webElementActions.click(addNewBtn)
                .sendKeys(firstnameInput, userAccountDetails.getFirstName())
                .sendKeys(lastnameInput, userAccountDetails.getLastName())
                .sendKeys(ageInput, String.valueOf(randomAge))
                .sendKeys(emailInput, userAccountDetails.getEmail())
                .sendKeys(salaryInput, String.valueOf(roundedSalary))
                .sendKeys(departmentInput, randomDepartment)
                .click(submitBtn);

        return this;
    }


    // Метод для добавления всех пользователей из базы данных
    public void addAllUsersFromDB() throws SQLException {
        List<UserAccountDetails> userAccountDetailsList = DBSHome.details(UserAccountDetails.class);

        for (UserAccountDetails user : userAccountDetailsList) {
            System.out.println("Adding user: " + user);
            add(user);
        }
    }
}

