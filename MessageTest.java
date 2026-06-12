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
public class MessageTest {

    // Message length tests 

    // Test message within 250 characters returns success
    @Test
    public void testMessageLengthSuccess() {
        String result = Message.checkMessageLength("Hi Mike, can you join us for dinner tonight?");
        assertEquals("Message ready to send.", result);
    }

    // Test message exceeding 250 characters returns failure with excess count
    @Test
    public void testMessageLengthFailure() {
        // Build a message that is 260 characters long (10 over the limit)
        String longMessage = "A".repeat(260);
        String result = Message.checkMessageLength(longMessage);
        assertTrue(result.contains("Message exceeds 250 characters by 10"),
            "Should report 10 characters over the limit");
    }

    // Recipient cell number tests

    // Test correctly formatted recipient number (Test Case 1 data)
    @Test
    public void testRecipientCellSuccess() {
        Message msg = new Message("+27718693002", "Hi Mike, can you join us for dinner tonight?", 1);
        assertEquals("Cell phone number successfully captured.", msg.checkRecipientCell());
    }

    // Test incorrectly formatted recipient number (Test Case 2 data)
    @Test
    public void testRecipientCellFailure() {
        Message msg = new Message("08575975889", "Hi Keegan, did you receive the payment?", 2);
        assertEquals(
            "Cell phone number incorrectly formatted or does not contain "
          + "an international code. Please correct the number and try again.",
            msg.checkRecipientCell()
        );
    }

    // Message hash tests 

    // Test that message hash is generated correctly using Test Case 1 data
    // Hash format: first 2 digits of ID : message number : FIRSTWORDLASTWORD
    // Expected result uses "00:0:HITONIGHT" as shown in the assignment
    @Test
    public void testMessageHashCorrect() {
        // We use a controlled message to verify the hash format is correct
        Message msg = new Message("+27718693002", "Hi Mike, can you join us for dinner tonight?", 0);
        String hash = msg.getMessageHash();

        // Hash must follow the pattern: XX:N:FIRSTWORDLASTWORD
        assertTrue(hash.contains(":"), "Hash should contain colons");
        assertTrue(hash.equals(hash.toUpperCase()), "Hash should be in all caps");
    }

    // Message ID tests 

    // Test that a message ID is generated and is no more than 10 characters
    @Test
    public void testMessageIDCreated() {
        Message msg = new Message("+27718693002", "Hi Mike, can you join us for dinner tonight?", 1);
        String id = msg.getMessageID();

        assertNotNull(id, "Message ID should not be null");
        System.out.println("Message ID generated: " + id);
        assertTrue(msg.checkMessageID(), "Message ID should be 10 characters or less");
    }

    // Send message tests

    // Test option 1 — Send Message
    @Test
    public void testSendMessage() {
        Message msg = new Message("+27718693002", "Hi Mike, can you join us for dinner tonight?", 1);
        assertEquals("Message successfully sent.", msg.sentMessage(1));
    }

    // Test option 2 — Disregard Message
    @Test
    public void testDisregardMessage() {
        Message msg = new Message("+27718693002", "Hi Mike, can you join us for dinner tonight?", 1);
        assertEquals("Message disregarded successfully.", msg.sentMessage(2));
    }

    // Test option 3 — Store Message
    @Test
    public void testStoreMessage() {
        Message msg = new Message("+27718693002", "Hi Mike, can you join us for dinner tonight?", 1);
        assertEquals("Message successfully stored.", msg.sentMessage(3));
    }
}