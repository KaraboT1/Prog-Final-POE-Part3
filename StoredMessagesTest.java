/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.loginapp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Part 3 - Store Data and Display Task Report.
 *
 * Uses the five test messages provided in the assignment:
 *   Message 1: +27834557896  "Did you get the cake?"                              | Sent
 *   Message 2: +27838884567  "Where are you? You are late! I have asked you to be on time." | Stored
 *   Message 3: +27834484567  "Yohoooo, I am at your gate."                        | Disregard
 *   Message 4: 0838884567    "It is dinner time !"                               | Sent
 *   Message 5: +27838884567  "Ok, I am leaving without you."                      | Stored
 *
 * @Karabo_tema
 */
public class StoredMessagesTest {

    // Reset all static arrays before every test so each test starts clean
    @BeforeEach
    public void setUp() {
        Message.resetAll();
    }

    // Helper method - populates all five test messages and returns
    // references to message 2 and message 4 so their auto-generated
    // hash and ID can be used in later searches
    private Message[] populateTestMessages() {

        // Message 1 — Sent
        Message msg1 = new Message("+27834557896", "Did you get the cake?", 1);
        msg1.sentMessage(1);

        // Message 2 — Stored
        Message msg2 = new Message("+27838884567",
                "Where are you? You are late! I have asked you to be on time.", 2);
        msg2.sentMessage(3);

        // Message 3 — Disregard
        Message msg3 = new Message("+27834484567", "Yohoooo, I am at your gate.", 3);
        msg3.sentMessage(2);

        // Message 4 — Sent
        Message msg4 = new Message("0838884567", "It is dinner time !", 4);
        msg4.sentMessage(1);

        // Message 5 — Stored
        Message msg5 = new Message("+27838884567", "Ok, I am leaving without you.", 5);
        msg5.sentMessage(3);

        return new Message[] { msg1, msg2, msg3, msg4, msg5 };
    }

    //  Test: Sent Messages array correctly populated 
    @Test
    public void testSentMessagesArrayPopulated() {
        populateTestMessages();

        // The Sent Messages array should contain messages 1 and 4
        assertTrue(Message.getSentMessages().contains("Did you get the cake?"),
            "Sent messages should contain message 1");
        assertTrue(Message.getSentMessages().contains("It is dinner time !"),
            "Sent messages should contain message 4");
        assertEquals(2, Message.getSentMessages().size(),
            "Only 2 messages should have been sent");
    }

    //  Test: Display the longest message 
    @Test
    public void testDisplayLongestMessage() {
        populateTestMessages();

        String result = Message.longestMessage();

        assertEquals("Where are you? You are late! I have asked you to be on time.", result);
    }

    //  Test: Search for messageID 
    @Test
    public void testSearchByMessageID() {
        Message[] messages = populateTestMessages();
        Message message4 = messages[3]; // "It is dinner time !"

        // Search using the auto-generated ID of message 4
        String result = Message.searchByMessageID(message4.getMessageID());

        assertTrue(result.contains("It is dinner time !"),
            "Search by ID should return message 4's text");
        assertTrue(result.contains("0838884567"),
            "Search by ID should return message 4's recipient");
    }

    // Test: Search all messages stored/sent for a particular recipient 
    @Test
    public void testSearchByRecipient() {
        populateTestMessages();

        String result = Message.searchByRecipient("+27838884567");

        assertTrue(result.contains("Where are you? You are late! I have asked you to be on time."),
            "Should contain message 2");
        assertTrue(result.contains("Ok, I am leaving without you."),
            "Should contain message 5");
    }

    //  Test: Delete a message using a message hash 
    @Test
    public void testDeleteMessageByHash() {
        Message[] messages = populateTestMessages();
        Message message2 = messages[1]; // "Where are you? You are late!..."

        String result = Message.deleteMessageByHash(message2.getMessageHash());

        assertEquals(
            "Message: \"Where are you? You are late! I have asked you to be on time.\" successfully deleted.",
            result
        );

        // Confirm it no longer appears in stored messages or allMessages
        assertFalse(Message.getStoredMessages().contains(
            "Where are you? You are late! I have asked you to be on time."),
            "Deleted message should be removed from storedMessages");
    }

    //  Test: Display report 
    @Test
    public void testDisplayReport() {
        populateTestMessages();

        String result = Message.displayReport();

        // Report should include the Message Hash, Recipient, and Message
        // for every sent/stored message
        assertTrue(result.contains("Message Hash:"));
        assertTrue(result.contains("Recipient:"));
        assertTrue(result.contains("Message:"));
        assertTrue(result.contains("Did you get the cake?"));
        assertTrue(result.contains("Ok, I am leaving without you."));
    }

    //  Test: Display sender and recipient of all stored messages 
    @Test
    public void testDisplaySenderAndRecipientOfStoredMessages() {
        // Set the sender's cell number as it would be after registration
        Message.setSenderCellNumber("+27831234567");

        populateTestMessages();

        String result = Message.displayStoredMessages();

        // Only messages 2 and 5 are Stored
        assertTrue(result.contains("Sender: +27831234567"));
        assertTrue(result.contains("Recipient: +27838884567"));
        assertTrue(result.contains("Where are you? You are late! I have asked you to be on time."));
        assertTrue(result.contains("Ok, I am leaving without you."));

        // Disregarded and sent-only messages should NOT appear
        assertFalse(result.contains("Did you get the cake?"));
        assertFalse(result.contains("Yohoooo, I am at your gate."));
    }

    // ─ Test: Disregarded Messages array correctly populated ─
    @Test
    public void testDisregardedMessagesArrayPopulated() {
        populateTestMessages();

        assertTrue(Message.getDisregardedMessages().contains("Yohoooo, I am at your gate."),
            "Disregarded messages should contain message 3");
        assertEquals(1, Message.getDisregardedMessages().size());
    }

    // Test: Stored Messages array correctly populated 
    @Test
    public void testStoredMessagesArrayPopulated() {
        populateTestMessages();

        assertTrue(Message.getStoredMessages().contains(
            "Where are you? You are late! I have asked you to be on time."));
        assertTrue(Message.getStoredMessages().contains("Ok, I am leaving without you."));
        assertEquals(2, Message.getStoredMessages().size());
    }

    // Test: Message Hash and Message ID arrays populated 
    @Test
    public void testHashAndIDArraysPopulated() {
        populateTestMessages();

        // All 5 messages (Sent, Stored, and Disregarded) contribute a hash and ID
        assertEquals(5, Message.getMessageHashes().size());
        assertEquals(5, Message.getMessageIDs().size());
    }
}