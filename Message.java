/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.loginapp;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @Karabo_tema
 */
public class Message {


    // Five arrays required by Part 3
    private static ArrayList<String> sentMessages        = new ArrayList<>();
    private static ArrayList<String> disregardedMessages = new ArrayList<>();
    private static ArrayList<String> storedMessages      = new ArrayList<>();
    private static ArrayList<String> messageHashes       = new ArrayList<>();
    private static ArrayList<String> messageIDs          = new ArrayList<>();

    // full Message objects kept separately so we can search/display them
    private static ArrayList<Message> allMessages = new ArrayList<>();

    // Holds the cell number of the logged-in user so it can be shown
    // as the "sender" for stored messages (requirement 2a)
    private static String senderCellNumber = "Unknown";

    // Per-message fields
    private String messageID;
    private String recipient;
    private String messageText;
    private String messageHash;
    private int    messageNumber;
    private String flag; // "Sent", "Stored", or "Disregarded"
    private String sender;

    // Running count of sent messages
    private static int totalMessagesSent = 0;

    // Constructor
    public Message(String recipient, String messageText, int messageNumber) {
        this.recipient     = recipient;
        this.messageText   = messageText;
        this.messageNumber = messageNumber;
        this.messageID     = generateMessageID();
        this.messageHash   = (messageText != null && !messageText.trim().isEmpty())
                             ? createMessageHash()
                             : "";
        // Record who the sender is at the time this message is created
        this.sender         = senderCellNumber;
    }

    // Allows Main.java to record the logged-in user's cell number
    // so it can later be shown as the "sender" of stored messages
    public static void setSenderCellNumber(String cellNumber) {
        senderCellNumber = cellNumber;
    }

    // Auto-generates a random 10-digit message ID
    private String generateMessageID() {
        Random random = new Random();
        long id = (long) (random.nextDouble() * 9_000_000_000L) + 1_000_000_000L;
        return String.valueOf(id);
    }

    // checking that the message ID is no more than 10 characters
    public boolean checkMessageID() {
        return messageID.length() <= 10;
    }

    // Check recipient cell number contains international code
    // Reference: regex adapted from regexlib.com for international numbers
    public String checkRecipientCell() {
        if (recipient == null) {
            return "Cell phone number incorrectly formatted or does not contain "
                 + "an international code. Please correct the number and try again.";
        }
        if (recipient.matches("^(\\+27|0)[0-9]{9}$")) {
            return "Cell phone number successfully captured.";
        } else {
            return "Cell phone number incorrectly formatted or does not contain "
                 + "an international code. Please correct the number and try again.";
        }
    }

    // Creates message hash: first 2 digits of ID : message number : FIRSTWORDLASTWORD
    public String createMessageHash() {
        String[] words    = messageText.trim().split("\\s+");
        String firstWord  = words[0].toUpperCase().replaceAll("[^A-Z0-9]", "");
        String lastWord   = words[words.length - 1].toUpperCase().replaceAll("[^A-Z0-9]", "");
        String idPrefix   = messageID.substring(0, 2);
        return idPrefix + ":" + messageNumber + ":" + firstWord + lastWord;
    }

    // Check message does not exceed 250 characters
    public static String checkMessageLength(String message) {
        if (message.length() <= 250) {
            return "Message ready to send.";
        } else {
            int excess = message.length() - 250;
            return "Message exceeds 250 characters by " + excess
                 + " [enter number here]; please reduce the size.";
        }
    }

    // User chooses to send, disregard, or store the message
    // Populates the correct arrays based on the choice
    public String sentMessage(int choice) {
        switch (choice) {
            case 1:
                // Add to sent arrays
                sentMessages.add(messageText);
                messageHashes.add(messageHash);
                messageIDs.add(messageID);
                allMessages.add(this);
                this.flag = "Sent";
                totalMessagesSent++;
                return "Message successfully sent.";
            case 2:
                // Disregarded messages now also contribute their hash and ID
                // to those arrays, since the requirement says these arrays must
                // "contain all the message hashes / IDs", not just sent/stored ones.
                disregardedMessages.add(messageText);
                messageHashes.add(messageHash);
                messageIDs.add(messageID);
                this.flag = "Disregarded";
                return "Message disregarded successfully.";
            case 3:
                // Add to stored arrays
                storedMessages.add(messageText);
                messageHashes.add(messageHash);
                messageIDs.add(messageID);
                allMessages.add(this);
                this.flag = "Stored";
                return "Message successfully stored.";
            default:
                return "Invalid option selected.";
        }
    }

    // Part 3 array feature methods 

    // a. Display the sender and recipient of all stored messages
    public static String displayStoredMessages() {
        if (allMessages.isEmpty()) {
            return "No messages stored.";
        }
        StringBuilder sb = new StringBuilder();
        for (Message m : allMessages) {
            if ("Stored".equals(m.flag)) {
                // Now includes the sender's cell number as well as the recipient
                sb.append("Sender: ").append(m.sender)
                  .append(" | Recipient: ").append(m.recipient)
                  .append(" | Message: ").append(m.messageText).append("\n");
            }
        }
        return sb.length() == 0 ? "No stored messages found." : sb.toString();
    }

    // b. Display the longest message
    public static String longestMessage() {
        if (allMessages.isEmpty()) {
            return "No messages available.";
        }
        Message longest = allMessages.get(0);
        for (Message m : allMessages) {
            if (m.messageText.length() > longest.messageText.length()) {
                longest = m;
            }
        }
        return longest.messageText;
    }

    // c. Search for a message by ID and return the recipient and message
    public static String searchByMessageID(String id) {
        for (Message m : allMessages) {
            if (m.messageID.equals(id)) {
                return "Recipient: " + m.recipient + "\nMessage: " + m.messageText;
            }
        }
        return "Message ID not found.";
    }

    // d. Search for all messages sent or stored for a particular recipient
    public static String searchByRecipient(String recipient) {
        StringBuilder sb = new StringBuilder();
        for (Message m : allMessages) {
            if (m.recipient.equals(recipient)) {
                sb.append(m.messageText).append("\n");
            }
        }
        return sb.length() == 0 ? "No messages found for this recipient." : sb.toString().trim();
    }

    // e. Delete a message using its message hash
    public static String deleteMessageByHash(String hash) {
        for (int i = 0; i < allMessages.size(); i++) {
            if (allMessages.get(i).messageHash.equals(hash)) {
                String deletedText = allMessages.get(i).messageText;
                // Remove from all relevant arrays
                sentMessages.remove(deletedText);
                storedMessages.remove(deletedText);
                messageHashes.remove(hash);
                messageIDs.remove(allMessages.get(i).messageID);
                allMessages.remove(i);
                return "Message: \"" + deletedText + "\" successfully deleted.";
            }
        }
        return "Message hash not found.";
    }

    // f. Display a full report of all stored messages
    public static String displayReport() {
        if (allMessages.isEmpty()) {
            return "No messages to display.";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("=== MESSAGE REPORT ===\n");
        for (Message m : allMessages) {
            sb.append("Message ID: ").append(m.messageID).append("\n");
            sb.append("Message Hash: ").append(m.messageHash).append("\n");
            sb.append("Recipient: ").append(m.recipient).append("\n");
            sb.append("Message: ").append(m.messageText).append("\n");
            sb.append("Status: ").append(m.flag).append("\n");
            sb.append("---\n");
        }
        return sb.toString();
    }

    // Print all sent messages (used on quit)
    public static String printMessages() {
        if (sentMessages.isEmpty()) {
            return "No messages sent yet.";
        }
        StringBuilder sb = new StringBuilder();
        for (Message m : allMessages) {
            if ("Sent".equals(m.flag)) {
                sb.append("Message ID: ").append(m.messageID).append("\n");
                sb.append("Message Hash: ").append(m.messageHash).append("\n");
                sb.append("Recipient: ").append(m.recipient).append("\n");
                sb.append("Message: ").append(m.messageText).append("\n");
                sb.append("---\n");
            }
        }
        return sb.toString();
    }

    // Returns the total number of messages sent
    public static int returnTotalMessages() {
        return totalMessagesSent;
    }

    // Stores the message details as a JSON-style string
    // Research reference: JSON formatting manually constructed as a String
    public String storeMessage() {
        return "{"
             + "\"messageID\": \""   + messageID   + "\", "
             + "\"messageHash\": \"" + messageHash + "\", "
             + "\"recipient\": \""   + recipient   + "\", "
             + "\"message\": \""     + messageText + "\""
             + "}";
    }

    // Resets all static arrays and counters — used between unit tests
    // so each test starts with a clean, predictable state
    public static void resetAll() {
        sentMessages.clear();
        disregardedMessages.clear();
        storedMessages.clear();
        messageHashes.clear();
        messageIDs.clear();
        allMessages.clear();
        totalMessagesSent  = 0;
        senderCellNumber   = "Unknown";
    }

    // Getters
    public String getMessageID()    { return messageID;    }
    public String getMessageHash()  { return messageHash;  }
    public String getRecipient()    { return recipient;    }
    public String getMessageText()  { return messageText;  }
    public int    getMessageNumber(){ return messageNumber;}
    public String getFlag()         { return flag;         }
    public String getSender()       { return sender;       }

    // Static array getters - used by tests to verify array contents.
    public static ArrayList<String> getSentMessages()        { return sentMessages;        }
    public static ArrayList<String> getDisregardedMessages() { return disregardedMessages; }
    public static ArrayList<String> getStoredMessages()      { return storedMessages;      }
    public static ArrayList<String> getMessageHashes()       { return messageHashes;       }
    public static ArrayList<String> getMessageIDs()          { return messageIDs;          }
    public static ArrayList<Message> getAllMessages()        { return allMessages;         }
}