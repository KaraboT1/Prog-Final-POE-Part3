/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.loginapp;

/**
 *
 * @Karabo_tema
 */
public class Registration {

    private String username;
    private String password;
    private String cellPhoneNumber;
    private String firstName;
    private String lastName;

    // Constructor
    public Registration(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    // checks if username contains an underscore and is no more than 5 characters
    public boolean checkUserName(String username) {
        if (username == null) {
            return false;
        }
        return username.contains("_") && username.length() <= 5;
    }

    // Checks if password meets all complexity rules
    public boolean checkPasswordComplexity(String password) {
        if (password == null || password.length() < 8) {
            return false;
        }
        boolean hasUpperCase = password.chars().anyMatch(Character::isUpperCase);
        boolean hasDigit     = password.chars().anyMatch(Character::isDigit);
        boolean hasSpecial   = password.chars().anyMatch(c -> !Character.isLetterOrDigit(c));
        return hasUpperCase && hasDigit && hasSpecial;
    }

    // see's if cell number contains international code and is no more than 10 characters
    // Reference: regex pattern adapted from regexlib.com for South African numbers (+27)
    public boolean checkCellPhoneNumber(String cellPhoneNumber) {
        if (cellPhoneNumber == null) {
            return false;
        }
        return cellPhoneNumber.matches("^\\+27[0-9]{9}$");
    }

    // Register a new user - validates all fields and stores details if everything passes
    public String registerUser(String username, String password, String cellPhoneNumber) {

        if (!checkUserName(username)) {
            return "Username is not correctly formatted; please ensure that your "
                 + "username contains an underscore and is no more than five "
                 + "characters in length.";
        }

        if (!checkPasswordComplexity(password)) {
            return "Password is not correctly formatted; please ensure that the "
                 + "password contains at least eight characters, a capital letter, "
                 + "a number, and a special character.";
        }

        if (!checkCellPhoneNumber(cellPhoneNumber)) {
            return "Cell phone number incorrectly formatted or does not contain "
                 + "international code.";
        }

        // all checks passed - save the user's details
        this.username        = username;
        this.password        = password;
        this.cellPhoneNumber = cellPhoneNumber;

        // KEPT AS ORIGINAL STRING FORMAT (not a text block):
        // A text block would add a trailing \n at the end of the last line,
        // which would cause assertEquals() in the unit tests to fail
        // because the expected string would not match exactly.
        return "Username successfully captured.\n"
             + "Password successfully captured.\n"
             + "Cell phone number successfully added.";
    }

    // Getters - used by the Login class to verify credentials
    public String getUsername()        { return username; }
    public String getPassword()        { return password; }
    public String getCellPhoneNumber() { return cellPhoneNumber; }
    public String getFirstName()       { return firstName; }
    public String getLastName()        { return lastName; }
}