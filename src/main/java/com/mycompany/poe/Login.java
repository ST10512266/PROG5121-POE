/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.poe;
import java.util.regex.Pattern;
/**
 *
 * @author Thandiswa
 */
public class Login {
    

    private final String username;
    private final String password;
    private final String cellPhoneNumber;
    private final String firstName; 
    private final String lastName;
  
    /**
     *
     * @param username
     * @param password
     * @param cellPhoneNumber
     * @param firstName
     * @param lastName
     */
    public Login(String username, String password,String cellPhoneNumber,String firstName, String lastName) {

        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.cellPhoneNumber = cellPhoneNumber;
} 
    
    
     public static boolean checkUsername(String username){
     return  username != null &&
             username.contains("_")&& 
             username.length()<=5;
     }
      
       private static final String PASSWORD_REGEX =
            "^(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z0-9]).{1,8}$";

        public static boolean checkPasswordComplexity(String password) {
            if (password == null) {
            return false; // Null check
            }
              return Pattern.matches(PASSWORD_REGEX, password);
              }
        
         public static boolean checkCellphoneNumber(String cellPhoneNumber) {
            if (cellPhoneNumber == null) {
            return false;
            }

        // Remove spaces and dashes for easier checking
              String cleaned = cellPhoneNumber.replaceAll("[\\s-]", "");

        // Regex: +27 followed by exactly 9 digits
              return cleaned.matches("^\\+27\\d{9}$");
         }
                     
         public String registerUser(String username, String password, String cellPhoneNumber){
               if(!checkUsername(username)){
                    return "Username is not correctly formatted, please ensure that your username contains an underscore and is no more than five characters";
               }
                    if(!checkPasswordComplexity(password)){
                       return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a chsracter";
                    }
                        if(!checkCellphoneNumber(cellPhoneNumber)){
                           return "Cellphone number incorrectly formatted or does not contain international code.";
                        }
                            
                               return "Registration successful.";
         }
         
         
         public boolean loginUser(String enteredUsername, String enteredPassword){
              return  enteredUsername!=null &&
                      this.username.equals(enteredUsername) &&
                      enteredPassword !=null &&
                     this.password.equals(enteredPassword);
         }
          
         
        public String returnLoginStatus(String enteredUsername, String enteredPassword) {
                if (loginUser(enteredUsername, enteredPassword)) {
                    return "Welcome " + firstName + "," + lastName + ""
                            + " it is great to see you again.";
                } 
                   else {
                         return "Username or password incorrect, please try again.";
                        }
        }
}
