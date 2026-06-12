/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.loginapp;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @Karabo_tema
 */
public class LoginTest {

    // assertEquals tests 

    // test correctly formatted username returns full success message
    @Test
    public void testUsernameCorrectlyFormatted() {
        Registration reg = new Registration("Kylie", "Smith");
        reg.registerUser("kyl_1", "Ch&&sec@ke99!", "+27838968976");

        Login login = new Login(reg);
        String result = login.returnLoginStatus("kyl_1", "Ch&&sec@ke99!");

        assertEquals("Welcome Kylie, Smith it is great to see you again.", result);
    }

    // Tests incorrectly formatted username returns correct error message
    @Test
    public void testUsernameIncorrectlyFormatted() {
        Registration reg = new Registration("Kylie", "Smith");
        String result = reg.registerUser("kyle!!!!!!!", "Ch&&sec@ke99!", "+27838968976");

        assertEquals(
            "Username is not correctly formatted; please ensure that your "
          + "username contains an underscore and is no more than five "
          + "characters in length.",
            result
        );
    }

    // Tests password meets complexity returns success message
    @Test
    public void testPasswordMeetsComplexity() {
        Registration reg = new Registration("Kylie", "Smith");
        String result = reg.registerUser("kyl_1", "Ch&&sec@ke99!", "+27838968976");

        assertTrue(result.contains("Password successfully captured."));
    }

    // test passcode does not meet complexity returns correct error message
    @Test
    public void testPasswordDoesNotMeetComplexity() {
        Registration reg = new Registration("Kylie", "Smith");
        String result = reg.registerUser("kyl_1", "password", "+27838968976");

        assertEquals(
            "Password is not correctly formatted; please ensure that the "
          + "password contains at least eight characters, a capital letter, "
          + "a number, and a special character.",
            result
        );
    }

    // Tests cell number correctly formatted returns success message
    @Test
    public void testCellNumberCorrectlyFormatted() {
        Registration reg = new Registration("Kylie", "Smith");
        String result = reg.registerUser("kyl_1", "Ch&&sec@ke99!", "+27838968976");

        assertTrue(result.contains("Cell phone number successfully added."));
    }

    // Tests if cell number is in the incorrectly formatted returns correct error message
    @Test
    public void testCellNumberIncorrectlyFormatted() {
        Registration reg = new Registration("Kylie", "Smith");
        String result = reg.registerUser("kyl_1", "Ch&&sec@ke99!", "08966553");

        assertEquals(
            "Cell phone number incorrectly formatted or does not contain "
          + "international code.",
            result
        );
    }

    // - assertTrue / assertFalse tests -

    // Test login successful returns true
    @Test
    public void testLoginSuccessful() {
        Registration reg = new Registration("Kylie", "Smith");
        reg.registerUser("kyl_1", "Ch&&sec@ke99!", "+27838968976");

        Login login = new Login(reg);
        assertTrue(login.loginUser("kyl_1", "Ch&&sec@ke99!"), "Login should succeed");
    }

    // Test login failed returns false
    @Test
    public void testLoginFailed() {
        Registration reg = new Registration("Kylie", "Smith");
        reg.registerUser("kyl_1", "Ch&&sec@ke99!", "+27838968976");

        Login login = new Login(reg);
        assertFalse(login.loginUser("kyl_1", "wrongPassword1!"), "Login should fail");
    }

    // Test username correctly formatted returns true
    @Test
    public void testCheckUserNameValid() {
        Registration reg = new Registration("Kylie", "Smith");
        assertTrue(reg.checkUserName("kyl_1"), "Username should be valid");
    }

    // Tests username incorrectly formatted returns false
    @Test
    public void testCheckUserNameInvalid() {
        Registration reg = new Registration("Kylie", "Smith");
        assertFalse(reg.checkUserName("kyle!!!!!!!"), "Username should be invalid");
    }

    // Test password meets complexity returns true
    @Test
    public void testCheckPasswordComplexityValid() {
        Registration reg = new Registration("Kylie", "Smith");
        assertTrue(reg.checkPasswordComplexity("Ch&&sec@ke99!"), "Password should be valid");
    }

    // Test password does not meet complexity returns false
    @Test
    public void testCheckPasswordComplexityInvalid() {
        Registration reg = new Registration("Kylie", "Smith");
        assertFalse(reg.checkPasswordComplexity("password"), "Password should be invalid");
    }

    // Tests cell number correctly formatted returns true
    @Test
    public void testCheckCellPhoneNumberValid() {
        Registration reg = new Registration("Kylie", "Smith");
        assertTrue(reg.checkCellPhoneNumber("+27838968976"), "Cell number should be valid");
    }

    // Test cell number incorrectly formatted returns false
    @Test
    public void testCheckCellPhoneNumberInvalid() {
        Registration reg = new Registration("Kylie", "Smith");
        assertFalse(reg.checkCellPhoneNumber("08966553"), "Cell number should be invalid");
    }
}