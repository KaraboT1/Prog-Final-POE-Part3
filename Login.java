/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.loginapp;

/**
 *
 * @Karabo_tema
 */
public class Login {

    // login references a registration object to access stored credentials
    private Registration registeredUser;

    // Constructor - receives the registered user's details
    public Login(Registration registeredUser) {
        this.registeredUser = registeredUser;
    }

    // Checking if the entered username and password match the stored credentials
    public boolean loginUser(String username, String password) {
        if (registeredUser.getUsername() == null || registeredUser.getPassword() == null) {
            return false;
        }
        return registeredUser.getUsername().equals(username)
            && registeredUser.getPassword().equals(password);
    }

    // Returning a welcome or failure message based on the login attempt
    public String returnLoginStatus(String username, String password) {
        if (loginUser(username, password)) {
            return "Welcome " + registeredUser.getFirstName() + ", "
                 + registeredUser.getLastName()
                 + " it is great to see you again.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }
}