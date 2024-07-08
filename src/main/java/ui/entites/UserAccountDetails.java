package ui.entites;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class UserAccountDetails {
    private String lastName;
    private String firstName;
    private int age;
    private String email;
    private double balance;
    private String department;

    public UserAccountDetails(String lastName, String firstName, String email, double balance) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.balance = balance;
    }
}