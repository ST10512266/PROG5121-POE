/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.poe;


import java.util.Random;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.io.FileReader;
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
    
    private static int totalMessagesSent = 0;
    private static int messageCounter = 0;
    
    private static final ArrayList<Messages> sentMessages = new ArrayList<>();
    private static final ArrayList<Messages> storedMessages = new ArrayList<>();
    private static final ArrayList<Messages> disregardedMessages = new ArrayList<>();
    private static final ArrayList<String> messageHashes = new ArrayList<>();
    private static final ArrayList<String> messageIDs = new ArrayList<>();
    
    
    //Constructor

    
    public Messages(String recipient, String messageText){
       this.MessageID = generateMessageID();
       Messages.messageCounter ++;
       this.recipient = recipient;
       this.messageText = messageText;
       this.numMessageSent = Messages.messageCounter;
       this.messageHash = createMessageHash();
    }
    
// New constructor for loading messages from JSON
     public Messages(String MessageID, int numMessageSent, String recipient, String messageText, String messageHash){

    this.MessageID = MessageID;
    this.numMessageSent = numMessageSent;
    this.recipient = recipient;
    this.messageText = messageText;
    this.messageHash = messageHash;

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
            sentStatus = "Sent";

            sentMessages.add(this);
            messageHashes.add(messageHash);
            messageIDs.add(MessageID);

            totalMessagesSent++;
            return "Message successfully sent.";
            }

        case 2 -> {
            sentStatus = "Disregarded";

            disregardedMessages.add(this);

            return "Press 0 to delete message.";
            }

        case 3 -> {
            sentStatus = "Stored";

            storedMessages.add(this);
            messageHashes.add(messageHash);
            messageIDs.add(MessageID);

            storeMessage();

            return "Message successfully stored.";
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

    JsonArray msgArray = new JsonArray();
    Gson gson = new Gson();

    // Add all stored messages to the JSON array
    for (Messages m : storedMessages) {

        JsonObject obj = new JsonObject();

        obj.addProperty("MessageID", m.MessageID);
        obj.addProperty("MessageHash", m.messageHash);
        obj.addProperty("Recipient", m.recipient);
        obj.addProperty("Message", m.messageText);
        obj.addProperty("NumMessagesSent", m.numMessageSent);

        msgArray.add(obj);
    }

    // Save the complete array to the file
    try (FileWriter file =
            new FileWriter("storedMessages.json")) {

        file.write(gson.toJson(msgArray));

        System.out.println("Messages saved successfully.");

    } catch (IOException e) {

        System.out.println("Error storing messages: "
                + e.getMessage());
    }
}

    public static void loadMessages() {

    storedMessages.clear();
    messageIDs.clear();
    messageHashes.clear();

    try (FileReader reader = new FileReader("storedMessages.json")) {

        Gson gson = new Gson();

        JsonArray msgArray =
                gson.fromJson(reader, JsonArray.class);

        if (msgArray == null) {
            return;
        }

        for (int i = 0; i < msgArray.size(); i++) {

            JsonObject obj = msgArray.get(i).getAsJsonObject();

            String id = obj.get("MessageID").getAsString();
            String hash = obj.get("MessageHash").getAsString();
            String recipient =obj.get("Recipient").getAsString();
            String text =obj.get("Message").getAsString();

            int number = obj.get("NumMessagesSent").getAsInt();

            Messages message = new Messages(id, number, recipient, text, hash
            );

            storedMessages.add(message);
            messageIDs.add(id);
            messageHashes.add(hash);

            messageCounter = Math.max(messageCounter, number);
        }

        System.out.println("Stored messages loaded.");

    } catch (IOException e) {

        System.out.println("Unable to load stored messages: "
                + e.getMessage());

    } catch (com.google.gson.JsonParseException
            | IllegalStateException e) {

        System.out.println("Invalid stored messages JSON: "
                + e.getMessage());
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
     
     //Display sender/recipient
     public static String displayStoredMessages() {

       if (storedMessages.isEmpty()) {
           return"No stored messages.";
          
       }
        StringBuilder sb = new StringBuilder();
        sb.append("----Stored Messages: Sender/ Recipient----\n");
        
        for (int i=0; i< storedMessages.size();i++) {
            Messages message = storedMessages.get(i);
              sb.append("Message ID:  ").append(i+1).append(":\n");
               sb.append("Sender:  You\n ");
               sb.append("Recipient:   ").append(message.getRecipient()).append("\n");
               sb.append("Message:   ").append(message.getMessageText()).append("\n");
        }
         return sb.toString();
    }
        //Longest Message
   
    public static String getLongestMessage() {

    if (storedMessages.isEmpty()) {
        return "";
    }

    Messages longest = storedMessages.get(0);

    for (Messages message : storedMessages) {

        if (message.getMessageText().length()
                > longest.getMessageText().length()) {

            longest = message;
        }
    }

    return longest.getMessageText();
}
  
     //Search Message ID
    public static String getMessageByID(String id) {

    for (Messages message : sentMessages) {

        if (message.getMessageID().equals(id)) {
            return message.getMessageText();
        }
    }

    for (Messages m : storedMessages) {

        if (m.getMessageID().equals(id)) {
            return m.getMessageText();
        }
    }

    return "Message not found.";
}
    
     //Search Recipient
   
     public static String searchRecipient(String recipient) {
    ArrayList<Messages> all = new ArrayList<>();
    all.addAll(sentMessages);
    all.addAll(storedMessages);

    StringBuilder sb = new StringBuilder();

    for (Messages message : all) {
        if (message.getRecipient().equals(recipient)) {
            sb.append(message.getMessageText());
        }
    }

    if (sb.length() == 0) {
        return "No messages found.";
    }

    return sb.toString();
}

      
      //Delete using Hash
      public static String deleteMessageByHash(String hash) {

    for (int i = 0; i < storedMessages.size(); i++) {
         Messages m = storedMessages.get(i);
            if (m.getMessageHash().equals(hash)) {

            String deletedText = m.getMessageText();

            storedMessages.remove(i);
            messageHashes.remove(hash);
            messageIDs.remove(m.getMessageID());

            return "Message: \"" + deletedText+ "\" successfully deleted.";
        }
    }

    return "Message not found.";
}

      //Full Report
      public static String displayReport() {
          
          StringBuilder sb= new StringBuilder();
          
           sb.append("========= REPORT =========");
           
      ArrayList<Messages> all = new ArrayList<>();
      
      all.addAll(sentMessages);
      all.addAll(storedMessages);
      
      
       if (all.isEmpty()) {

       return"No stored messages.";

      }
    
   

       for (Messages messages : all){
               sb.append("Message ID:  ").append(messages.getMessageID()).append("\n");
               sb.append("Message Hash:   ").append(messages.getMessageHash()).append("\n");
               sb.append("Recipient:   ").append(messages.getRecipient()).append("\n");
               sb.append("Message:   ").append(messages.getMessageText()).append("\n");
              
               sb.append("-----------------------------\n");
           
           }
     return sb.toString();
   }
      

     public static ArrayList<Messages> getSentMessages() {
       return sentMessages;
     }

     public static ArrayList<Messages> getStoredMessages() {
       return storedMessages;
    }

    public static ArrayList<Messages> getDisregardedMessages() {
     return disregardedMessages;
    }
   public static void storeMessages(Messages message){
    storedMessages.add(message);
   }
}
  
  

    
    
    

