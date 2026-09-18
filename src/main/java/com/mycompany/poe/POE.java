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
        
             
         System.out.println("========== Registration ========");
         
         
        String firstName;
        String lastName;
        String username;
        String password;
        String cellPhoneNumber;
        
        
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
         
          Login login = new Login(username, password, cellPhoneNumber,firstName, lastName );
          
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
                                 + "contain international code."
                 );
             } else {
                 System.out.println("Cell phone number successfully added.");
             }
        
           
            
                  System.out.println();
        System.out.println(login.registerUser(username, password, cellPhoneNumber));

        if (Login.checkUsername(username)
                && Login.checkPasswordComplexity(password)
                && Login.checkCellphoneNumber(cellPhoneNumber)) {

            System.out.println();
            System.out.println("=== LOGIN ===");

            System.out.println("Enter username: ");
            String loginUsername = scanner.nextLine();

            System.out.println("Enter password: ");
            String loginPassword = scanner.nextLine();

            System.out.println();
            System.out.println(login.returnLoginStatus(loginUsername,loginPassword));
        }
            
                     
    }
}  
         
                   
    
         
         

         
         
         
         
         
    
    
    
   
     
        
   
    
    
    
    

  
   
   
    

