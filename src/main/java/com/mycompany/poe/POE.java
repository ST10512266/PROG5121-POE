/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.poe;

/**
 *
 * @author Thandiswa
 */

import java.util.Scanner;



public class POE {

    public static void main(String[] args) {
         
        Scanner scanner= new Scanner(System.in);
        
         Messages.loadMessages();
        
         boolean running = true;
        boolean registered = false;
        
        String firstName;
        String lastName;
        String username;
        String password;
        String cellPhoneNumber;
        Login login = null;
        
        
        while (running) {
               System.out.println("\n===== MAIN MENU =====");
               System.out.println("1. Register");
               System.out.println("2. Login");
               System.out.println("0. Exit");
               System.out.println("Choose an option: ");
            
              int choice;

                try {
                   choice = Integer.parseInt(scanner.nextLine());
                } 
                   catch (NumberFormatException e) {
                         System.out.println("Invalid input. Please enter a number.");
                     continue;
                   }
            
                    
                if (choice == 1) {
                    
                    System.out.println("========== Registration ========");
                   
                    
                    System.out.println("Enter your first name: ");
                    firstName = scanner.nextLine();
                    
                    System.out.println("Enter your last name: ");
                    lastName = scanner.nextLine();
                    
                    System.out.println("Enter username: ");
                    username = scanner.nextLine();
                    
                    System.out.println("Enter password: ");
                    password = scanner.nextLine();
                    
                    System.out.println("Enter cellphone number (with international code, e.g., +27...)");
                    cellPhoneNumber= scanner.nextLine();
                    
                    login = new Login(username, password, cellPhoneNumber,firstName, lastName );
                    if (Login.checkUsername(username)) {
                        System.out.println("Username successfully captured.");
                    }
                    
                      else{
                        System.out.println("Username is not correctly formatted; please ensure "
                                + "that your username contains an underscore and is "
                                + "no more than five characters in length." );
                    }
                    
                    if (!Login.checkPasswordComplexity(password)) {
                        System.out.println(
                                "Password is not correctly formatted; please ensure "
                                        + "that the password contains at least eight characters, "
                                        + "a capital letter, a number, and a special character.");
                    }
                    
                      else{
                        System.out.println("Password successfully captured.");
                      }
                    if (!Login.checkCellphoneNumber(cellPhoneNumber)) {
                        System.out.println(
                                "Cell phone number incorrectly formatted or does not "
                                        + "contain international code.");
                    }
                    
                       else {
                        System.out.println("Cell phone number successfully added.");
                       }
                    System.out.println();
                      System.out.println(login.registerUser(username, password, cellPhoneNumber)
                      );
                    if (Login.checkUsername(username)
                            && Login.checkPasswordComplexity(password)
                            && Login.checkCellphoneNumber(cellPhoneNumber)) {
                        
                        registered = true;
                    }
                    else {
                        registered = false;
                        login = null;
                    }
                }
                    else if(choice ==2) {
                      
                   
                        if (!registered || login == null){
                          System.out.println("You must register first");
                          continue;
                        }

                        System.out.println("=== LOGIN ===");
                        
                        System.out.println("Enter username: ");
                        String loginUsername = scanner.nextLine();
                        
                        System.out.println("Enter password: ");
                        String loginPassword = scanner.nextLine();
                        
                        System.out.println();
                        System.out.println(login.returnLoginStatus(loginUsername,loginPassword));
                      
                       
                        if (!login.loginUser(loginUsername, loginPassword)){
                        System.out.println("you must be logged in to send messages");
                        }
                
                        
                           else {
                           startQuickChat(scanner);
                          }
                    }  
        }
                
    }
            
   public static String startQuickChat(Scanner scanner) {
       
       
        int messagesSent = 0;
        boolean running = true;
        

    System.out.println("Welcome to QuickChat.");
    
    
    System.out.println("How many messages would you like to send? ");
     int numberOfMessages = Integer.parseInt(scanner.nextLine().trim());
     
      while (numberOfMessages < 0) {
              System.out.println("Please enter zero or a positive number: ");
              numberOfMessages = scanner.nextInt();
              scanner.nextLine();
        }
     
     
   

    while (running) {

        System.out.println();
        System.out.println("===== QUICKCHAT MENU =====");
        System.out.println("1. Send Messages");
        System.out.println("2. Show Recently Sent Messages");
        System.out.println("3. Quit");
        System.out.println("4. Stored Messages"); 
        System.out.println("Choose an option: ");

        int option = Integer.parseInt(scanner.nextLine().trim());

        if (option == 1) {
           

            if (messagesSent >= numberOfMessages) {
                System.out.println("You have reach your message limit." );
            } 
              else {

                System.out.println("Enter recipient cell number: ");
                String recipient = scanner.nextLine().trim();

                
               
                
                System.out.println("Enter your message: ");
                String messageText = scanner.nextLine().trim();
                
                //Check message length
                String lengthCheck = Messages.checkMessageLength(messageText);
                  System.out.println(lengthCheck);
                    if (messageText.length() > 250) continue;

                 Messages message = new Messages(recipient, messageText);
                
                 messagesSent++;
                  //check Recipient
                  System.out.println(message.checkRecipientCell());
                  
                  
                    System.out.println("Choose:");
                    System.out.println("1) Send");    
                    System.out.println("2) Disregard");
                    System.out.println("3) Store");      
                   
                    
               int choice = Integer.parseInt(scanner.nextLine());

              String result = message.sentMessage(choice);

               System.out.println(result);

        
                   
               
                   
            }

         } 
          else if (option == 2) {

            System.out.println("Coming Soon.");

        } 
          else if (option == 3) {

            System.out.println("Thank you for using QuickChat.");
            
            System.exit(0);
          }  
          else if (option ==4){
             storedMessagesMenu();
          }
            else {
            System.out.println(
                "Invalid option. Please choose 1, 2 , 3 or 4.");
        }

        }
        return null;
       
                 }
    public static void storedMessagesMenu() {

    Scanner scanner = new Scanner(System.in);

    int choice;

    do {

        System.out.println("\n===== STORED MESSAGES =====");
        System.out.println("1. Display sender and recipient");
        System.out.println("2. Display longest message");
        System.out.println("3. Search by Message ID");
        System.out.println("4. Search by Recipient");
        System.out.println("5. Delete using Message Hash");
        System.out.println("6. Display Full Report");
        System.out.println("7. Back");

        System.out.print("Choose an option: ");
        choice = Integer.parseInt(scanner.nextLine());

        switch (choice) {

            case 1 -> System.out.println(Messages.displayStoredMessages());

            case 2 -> System.out.println(Messages.getLongestMessage());

            case 3 -> {
                System.out.print("Enter Message ID: ");
                String id = scanner.nextLine();
                System.out.println(Messages.getMessageByID(id));
            }

            case 4 -> {
                System.out.print("Enter Recipient: ");
                String recipient = scanner.nextLine();
                System.out.println(Messages.searchRecipient(recipient));
            }

            case 5 -> {
                System.out.print("Enter Message Hash: ");
                String hash = scanner.nextLine();
                System.out.println(Messages.deleteMessageByHash(hash));
            }

            case 6 -> System.out.println(Messages.displayReport());

            case 7 -> System.out.println("Returning to Main Menu...");

            default -> System.out.println("Invalid option.");

        }

    } while (choice != 7);

  }

    private void storeMessage(Messages message) {
       Messages.storeMessages(message);
    }
}

             


                 

                

  
         
                   
    
         
         

         
         
         
         
         
    
    
    
   
     
        
   
    
    
    
    

  
   
   
    

