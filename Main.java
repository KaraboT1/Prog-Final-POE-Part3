/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.mycompany.loginapp;

import java.util.Scanner;

/**
 *
 * @Karabo_tema
 */
public class Main {

    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {

        System.out.println("=== WELCOME TO THE LOGIN SYSTEM ===");

        // Step 1: Collect name
        System.out.print("Enter your first name: ");
        String firstName = input.nextLine();

        System.out.print("Enter your last name: ");
        String lastName = input.nextLine();

        Registration reg = new Registration(firstName, lastName);

        // step 2: Registration loop 
        System.out.println("\n--- REGISTRATION ---");

        while (true) {
            System.out.print("Enter username: ");
            String username = input.nextLine();

            System.out.print("Enter password: ");
            String password = input.nextLine();

            System.out.print("Enter cell phone number (e.g. +27831234567): ");
            String cellNumber = input.nextLine();

            String registrationResult = reg.registerUser(username, password, cellNumber);
            System.out.println(registrationResult);

            if (registrationResult.contains("successfully")) {
                //  Record this user's cell number as the "sender" for
                // all messages they create — needed for the Stored Messages
                // "sender and recipient" display (requirement 2a)
                Message.setSenderCellNumber(cellNumber);
                break;
            }
            System.out.println("Please try again.\n");
        }

        // step 3: Login loop
        System.out.println("\n--- LOGIN ---");
        Login login = new Login(reg);

        while (true) {
            System.out.print("Enter username: ");
            String enteredUsername = input.nextLine();

            System.out.print("Enter password: ");
            String enteredPassword = input.nextLine();

            String loginResult = login.returnLoginStatus(enteredUsername, enteredPassword);
            System.out.println(loginResult);

            if (loginResult.contains("Welcome")) {
                break;
            }
            System.out.println("Please try again.\n");
        }

        // Step 4: QuickChat main menu 
        System.out.println("\nWelcome to QuickChat.");

        System.out.print("How many messages would you like to send? ");
        int maxMessages  = Integer.parseInt(input.nextLine().trim());
        int messageCount = 0;

        while (true) {
            System.out.println("\n1. Send Messages");
            System.out.println("2. Show recently sent messages");
            System.out.println("3. Stored Messages");
            System.out.println("4. Quit");
            System.out.print("Choose option: ");
            int choice = Integer.parseInt(input.nextLine().trim());

            // Option 1: Send a message 
            if (choice == 1) {

                if (messageCount >= maxMessages) {
                    System.out.println("You have reached your message limit of " + maxMessages + ".");
                    continue;
                }

                System.out.print("Enter recipient cell number (e.g. +27831234567): ");
                String recipient = input.nextLine();

                System.out.print("Enter your message: ");
                String messageText = input.nextLine();

                String lengthCheck = Message.checkMessageLength(messageText);
                System.out.println(lengthCheck);

                if (!lengthCheck.equals("Message ready to send.")) {
                    System.out.println("Please shorten your message and try again.");
                    continue;
                }

                messageCount++;
                Message msg = new Message(recipient, messageText, messageCount);

                System.out.println(msg.checkRecipientCell());

                if (!msg.checkRecipientCell().equals("Cell phone number successfully captured.")) {
                    messageCount--;
                    continue;
                }

                System.out.println("Message ID generated: " + msg.getMessageID());
                System.out.println("Message Hash: " + msg.getMessageHash());

                System.out.println("\n1. Send Message");
                System.out.println("2. Disregard Message");
                System.out.println("3. Store Message");
                System.out.print("Choose option: ");
                int sendChoice = Integer.parseInt(input.nextLine().trim());

                System.out.println(msg.sentMessage(sendChoice));

            // Option 2: Show recently sent messages
            } else if (choice == 2) {
                System.out.println("Coming Soon.");

            //  Option 3: Stored Messages menu (Part 3)
            } else if (choice == 3) {
                storedMessagesMenu();

            // Option 4: Quit
            } else if (choice == 4) {
                System.out.println("\n--- Messages Sent ---");
                System.out.println(Message.printMessages());
                System.out.println("Total messages sent: " + Message.returnTotalMessages());
                System.out.println("Goodbye!");
                break;

            } else {
                System.out.println("Invalid option.");
            }
        }

        input.close();
    }

    // This is Part 3: Stored Messages sub-menu

    /**
     *
     */
    public static void storedMessagesMenu() {

        while (true) {
            System.out.println("\n--- STORED MESSAGES ---");
            System.out.println("a. Display sender and recipient of all stored messages");
            System.out.println("b. Display the longest message");
            System.out.println("c. Search by Message ID");
            System.out.println("d. Search by recipient");
            System.out.println("e. Delete a message using message hash");
            System.out.println("f. Display full report");
            System.out.println("g. Back to main menu");
            System.out.print("Choose option: ");
            String option = input.nextLine().trim().toLowerCase();

            switch (option) {
                case "a":
                    System.out.println(Message.displayStoredMessages());
                    break;

                case "b":
                    System.out.println("Longest message: " + Message.longestMessage());
                    break;

                case "c":
                    System.out.print("Enter Message ID to search: ");
                    String searchID = input.nextLine().trim();
                    System.out.println(Message.searchByMessageID(searchID));
                    break;

                case "d":
                    System.out.print("Enter recipient number to search: ");
                    String searchRecipient = input.nextLine().trim();
                    System.out.println(Message.searchByRecipient(searchRecipient));
                    break;

                case "e":
                    System.out.print("Enter message hash to delete: ");
                    String deleteHash = input.nextLine().trim();
                    System.out.println(Message.deleteMessageByHash(deleteHash));
                    break;

                case "f":
                    System.out.println(Message.displayReport());
                    break;

                case "g":
                    return; // back to main menu

                default:
                    System.out.println("Invalid option.");
            }
        }
    }
}