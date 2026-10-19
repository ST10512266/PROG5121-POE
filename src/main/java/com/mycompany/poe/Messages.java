/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.poe;


import java.util.Random;


import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;


/**
 *
 * @author Thandiswa
 */
public class Messages {
    
    private final String MessageID;
    private final int numMessageSent;
    private final String recipient;
    private final String messageText;
    private final String messageHash;
    private String sentStatus;
    
    private static final ArrayList<Messages> sentMessages = new ArrayList <>();
    private static int totalMessagesSent = 0;
    private static int messageCounter = 0;
    
    //Constructor
    public Messages(String recipient ,String messageText){
       this.MessageID = generateMessageID();
       Messages.messageCounter ++;
       this.recipient = recipient;
       this.messageText = messageText;
       this.numMessageSent = Messages.messageCounter;
       this.messageHash = createMessageHash();
    }
    
    //Getters
    public String getMessageID (){return MessageID;}
    public String getRecipient (){return recipient;}
    public String getMessageText (){return messageText;}
    public int getnumMessagesSent(){return numMessageSent;}
    public String getMessageHash (){return messageHash;}
    public String getMessageStatus (){return sentStatus;}
   
       // Generating Random ten-digit number 
    private String generateMessageID() {
    Random rand = new Random();
    long id = (long)(rand.nextDouble() * 9_000_000_000L) + 1_000_000_000L;
    return String.valueOf(id);
}
 
    // ensuring message ID is not more that 10 characters
    public boolean checkMessageID() {
        return MessageID.length() <= 10;
    }
    
   
    
    //Recipient
    public String checkRecipientCell() {
        if (recipient.length() <= 12 && recipient.startsWith("+27")){
            return"Cellphone number successfully captured.";
        }
        else{
           return"Cell phone number is incorrectly formatted or does not contain " +
              "an international code. Please correct the number and try again.";
        }
    }
     //  Message hash
 
   private String createMessageHash() {
    String firstTwo = MessageID.substring(0, 2);
    String[] words = messageText.trim().split("\\s+");

    String firstWord = words[0]
        .replaceAll("[^A-Za-z0-9]", "").toUpperCase();

    String lastWord = words[words.length - 1]
        .replaceAll("[^A-Za-z0-9]", "").toUpperCase();

    return (firstTwo + ":" + numMessageSent + ":" + firstWord + lastWord).toUpperCase();
}
 
   

    
    public String sentMessage(int choice) {
        switch (choice) {
            case 1 -> {
                sentStatus = "status";
                sentMessages.add(this);
                totalMessagesSent++;
                return"Message successfully sent.";
            }
                
            case 2 -> {
                sentStatus = "disregarded";
                return"Press 0 to delete message.";
            }
            case 3 -> {
                sentStatus = "stored";
                storeMessage();
                return"Message successfully stored.";
            }
               default -> {
                   return "Invalid option.";
            }
        }
    }
    
    //Print messages
    public  String printMessages(){
       if(sentMessages.isEmpty()){
          return"No message has been sent yet.";
       }
        StringBuilder sb= new StringBuilder();
           for (Messages m : sentMessages){
               sb.append("Message ID:  ").append(m.getMessageID()).append("\n");
               sb.append("Message Hash:   ").append(m.getMessageHash()).append("\n");
               sb.append("Recipient:   ").append(m.getRecipient()).append("\n");
               sb.append("Message:   ").append(m.getMessageText()).append("\n");
              
               sb.append("-----------------------------\n");
           
           }
           return sb.toString();
    }
     public static int returnTotalMessages() {
        return totalMessagesSent;
    }
     public void storeMessage() {
    JsonObject obj = new JsonObject();

    obj.addProperty("MessageID", MessageID);
    obj.addProperty("MessageHash", messageHash);
    obj.addProperty("Recipient", recipient);
    obj.addProperty("Message", messageText);
    obj.addProperty("NumMessagesSent", numMessageSent); 

    JsonArray msgArray = new JsonArray();
    msgArray.add(obj);

     try (FileWriter file = new FileWriter("storedMessages.json", true)) {
        file.write(new Gson().toJson(msgArray));
        file.write("\n");
    } 
      catch (IOException e) {
        System.out.println("Error storing message: " + e.getMessage());
      }
    }
     //message length

   
     public static String checkMessageLength(String Messages){
       if(Messages.length ()<=250){
         return "Message ready to send.";
       }
       else {
          int excess = Messages.length()-250;
          return"Message exceeds 250 characters by"+ excess + "reduce the size of your message";
       }
     
     }
     
     
     
    }  

    
    
    

