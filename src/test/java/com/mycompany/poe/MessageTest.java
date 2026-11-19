/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.poe;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;


/**
 *
 * @author Thandiswa
 */
public class MessageTest {
    
    
    public MessageTest(){
    }
     
    
    @BeforeAll
    public static void setUpClass(){
    }
    
    @AfterAll
    public static void tearDownClass(){
    }
    
    
    
      private Messages message1;
    private Messages message2;
    private Messages message3;
    private Messages message4;
    private Messages message5;
    
    @BeforeEach
    public void setUp(){
      
        // Clear previous test data
        Messages.getSentMessages().clear();
        Messages.getStoredMessages().clear();
        Messages.getDisregardedMessages().clear();

        // Create the five test messages
        message1 = new Messages(
                "+27834557896",
                "Did you get the cake?"
        );

        message2 = new Messages(
                "+27838884567",
                "Where are you? You are late! I have asked you to be on time."
        );

        message3 = new Messages(
                "+27834484567",
                "Yohoooo, I am at your gate."
        );

        message4 = new Messages(
                "0838884567",
                "It is dinner time !"
        );

        message5 = new Messages(
                "+27838884567",
                "Ok, I am leaving without you."
        );

        // Populate arrays for testing
        Messages.getSentMessages().add(message1);
        Messages.getStoredMessages().add(message2);
        Messages.getDisregardedMessages().add(message3);
        Messages.getSentMessages().add(message4);
        Messages.getStoredMessages().add(message5);
    }


     
    
    @AfterEach
    public void tearDown(){
    }
    
      // Test data from the brief
    private static final String RECIPIENT_1 = "+27718693002";
    private static final String TEXT_1 = "Hi Mike, can you join us for dinner tonight";
 
    private static final String RECIPIENT_2 = "08575975889";   
    private static final String TEXT_2 = "Hi Keegan, did you receive the payment?";
 
    //  Message length 
 
    @Test
    public void testMessageLengthSuccess() {
        assertEquals("Message ready to send.",
                Messages.checkMessageLength(TEXT_1));
    }
 
    @Test
    public void testMessageLengthFailure() {
        String tooLong = "a".repeat(253);   
        int excess = tooLong.length()-250;
          assertEquals("Message exceeds 250 characters by"+ excess + "reduce the size of your message",
                Messages.checkMessageLength(tooLong));
    }
 
    // Recipient number 
 
    @Test
    public void testRecipientNumberSuccess() {
        Messages message = new Messages(RECIPIENT_1, TEXT_1);
        assertEquals("Cellphone number successfully captured.",
                message.checkRecipientCell());
    }
 
    @Test
    public void testRecipientNumberFailure() {
        Messages message = new Messages(RECIPIENT_2, TEXT_2);
        assertEquals("Cell phone number is incorrectly formatted or does not contain "
                + "an international code. Please correct the number and try again.",
                message.checkRecipientCell());
    }
 
    // Message hash (loop over both test messages) 
 
    @Test
    public void testMessageHash() {
        String[] recipients = {RECIPIENT_1, RECIPIENT_2};
        String[] texts = {TEXT_1, TEXT_2};
        String[] expectedWords = {"HITONIGHT" ,"HIPAYMENT"};
 
        for (int i = 0; i < texts.length; i++) {
            Messages message = new Messages(recipients[i], texts[i]);
 
            // hash = first two digits of ID : message number : FIRSTWORDLASTWORD
            String expected = message.getMessageID().substring(0, 2)
                    + ":" + message.getnumMessagesSent()
                    + ":" + expectedWords[i];
 
            assertEquals(expected, message.getMessageHash());
        }
    }
 
    // ---------- Message ID ----------
 
    @Test
    public void testMessageIDCreated() {
        Messages message = new Messages(RECIPIENT_1, TEXT_1);
        assertNotNull(message.getMessageID());
        assertTrue(message.checkMessageID());   // not more than 10 characters
    }
 
    // ---------- Send / Disregard / Store ----------
 
    @Test
    public void testSendMessage() {
        Messages message = new Messages(RECIPIENT_1, TEXT_1);
        assertEquals("Message successfully sent.", message.sentMessage(1));
    }
 
    @Test
    public void testDisregardMessage() {
        Messages message = new Messages(RECIPIENT_2, TEXT_2);
        assertEquals("Press 0 to delete message.", message.sentMessage(2));
    }
 
    @Test
    public void testStoreMessage() {
        Messages message = new Messages(RECIPIENT_1, TEXT_1);
        assertEquals("Message successfully stored.", message.sentMessage(3));
    }
 
    // ---------- Total number sent ----------
 
    @Test
    public void testReturnTotalMessages() {
        int before = Messages.returnTotalMessages();
 
        new Messages(RECIPIENT_1, TEXT_1).sentMessage(1);   // sent: total goes up by 1
        assertEquals(before + 1, Messages.returnTotalMessages());
 
        new Messages(RECIPIENT_2, TEXT_2).sentMessage(2);   // discarded: total unchanged
        assertEquals(before + 1, Messages.returnTotalMessages());
    }
    
      // TEST 1: Sent Messages Array
    @Test
    public void testSentMessagesArray() {

        assertEquals(2,
                Messages.getSentMessages().size());

        assertEquals("Did you get the cake?",
                Messages.getSentMessages()
                        .get(0).getMessageText());

        assertEquals("It is dinner time !",
                Messages.getSentMessages()
                        .get(1).getMessageText());
    }

    // TEST 2: Longest Message
    @Test
    public void testLongestMessage() {

        String expected =
                "Where are you? You are late! I have asked you to be on time.";

        String actual = Messages.getLongestMessage();

        assertEquals(expected, actual);
    }

    // TEST 3: Search by Message ID
    @Test
    public void testSearchMessageID() {

        String id = message4.getMessageID();

        String expected = "It is dinner time !";

        String actual = Messages.getMessageByID(id);

        assertEquals(expected, actual);
    }

    // TEST 4: Search by Recipient
    @Test
    public void testSearchRecipient() {

        String expected =
                "Where are you? You are late! I have asked you to be on time."
                + "Ok, I am leaving without you.";

        String actual = Messages.searchRecipient(
                "+27838884567");

        assertEquals(expected, actual);
    }

    // TEST 5: Delete by Message Hash
    @Test
    public void testDeleteMessageByHash() {

        String hash = message2.getMessageHash();

        String expected =
                "Message: \"Where are you? You are late! I have asked you to be on time.\" successfully deleted.";

        String actual = Messages.deleteMessageByHash(hash);

        assertEquals(expected, actual);

        // Check that only one stored message remains
        assertEquals(1,
                Messages.getStoredMessages().size());
    }

    // TEST 6: Display Report
    @Test
    public void testDisplayReport() {

        String report = Messages.displayReport();

        assertEquals(true,
                report.contains("Message Hash:"));

        assertEquals(true,
                report.contains("Recipient:"));

        assertEquals(true,
                report.contains("Message:"));

        assertEquals(true,
                report.contains("Did you get the cake?"));

        assertEquals(true,
                report.contains("It is dinner time !"));
    }
}   
    

