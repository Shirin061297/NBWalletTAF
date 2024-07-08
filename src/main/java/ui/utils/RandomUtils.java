package ui.utils;

import com.github.javafaker.Faker;
import db.beans.AspNetUsers_Bean;
import ui.entites.Employee;
import ui.entites.UserAccountDetails;

import java.sql.Timestamp;
import java.util.Random;
import java.util.concurrent.TimeUnit;


public class RandomUtils {
    private static final Faker faker = new Faker();
    private static final Random random = new Random();

    public static AspNetUsers_Bean generateRandomUser() {
        return AspNetUsers_Bean.builder()
                .FirebaseUid(faker.idNumber().valid())
                .FirstName(faker.name().firstName())
                .LastName(faker.name().lastName())
                .ProfileImage(faker.avatar().image())
                .Deleted(faker.bool().bool())
                .UserName(faker.name().username())
                .NormalizedUserName(faker.name().username().toUpperCase())
                .Email(faker.internet().emailAddress())
                .NormalizedEmail(faker.internet().emailAddress().toUpperCase())
                .EmailConfirmed(faker.bool().bool())
                .PasswordHash(faker.internet().password())
                .SecurityStamp(faker.idNumber().valid())
                .ConcurrencyStamp(faker.idNumber().valid())
                .PhoneNumber(faker.phoneNumber().phoneNumber())
                .PhoneNumberConfirmed(faker.bool().bool())
                .TwoFactorEnabled(faker.bool().bool())
                .LockoutEnd(null)
                .LockoutEnabled(faker.bool().bool())
                .AccessFailedCount(faker.number().numberBetween(0, 10))
                .RefreshToken(faker.internet().uuid())
                .RefreshTokenExpiryTime(new Timestamp(faker.date().future(10, TimeUnit.DAYS).getTime()))
                .build();
    }



    public UserAccountDetails creatMocEmployee(){
        UserAccountDetails userAccountDetails = new UserAccountDetails();
        userAccountDetails.setAge(faker.number().numberBetween(18,40));
        userAccountDetails.setDepartment(faker.job().position());

        return userAccountDetails;
    }

}



