/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.poe;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Thandiswa
 */
public class MessageTest {
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
   
}
