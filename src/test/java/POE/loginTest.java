/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package POE;


import com.mycompany.poe.Login;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Thandiswa
 */
public class loginTest {
    
  @Test
  public void testUsernameCorrectlyFormatted(){
    Login login = new Login(  "kyl_1", "Ch&&sec@ke99!", "+27838968976", "Kyle", "Smith");
        assertTrue(Login.checkUsername("Ky1_1"));
  }
    
  @Test
  public void testUsernameIncorrectlyFormatted(){
       Login login = new Login(  "kyl_1", "Ch&&sec@ke99!", "+27838968976", "Kyle", "Smith");
          assertFalse(Login.checkUsername("Kyle!!!!"));
  }
  @Test
  public void testPasswordMeetsComplexity(){
       Login login = new Login(  "kyl_1", "Ch&&sec@ke99!", "+27838968976", "Kyle", "Smith");
          assertTrue(Login.checkPasswordComplexity("Ch&&sec@ke99"));
  }
  @Test
  public void testPasswordDoesNotMeetComplexity(){
       Login login = new Login(  "kyl_1", "Ch&&sec@ke99!", "+27838968976", "Kyle", "Smith");
          assertFalse(Login.checkPasswordComplexity("password"));
  }     
  @Test
  public void testCellPhoneCorrectlyFormatted(){
       Login login = new Login(  "kyl_1", "Ch&&sec@ke99!", "+27838968976", "Kyle", "Smith");
          assertTrue(Login.checkCellphoneNumber("+27838968976"));
  }
  @Test
  public void testCellPhoneInCorrectlyFormatted(){
       Login login = new Login(  "kyl_1", "Ch&&sec@ke99!", "+27838968976", "Kyle", "Smith");
         assertFalse(Login.checkCellphoneNumber("0838968976"));
  }
  @Test
  public void testLoginSuccessful(){
       Login login = new Login(  "kyl_1", "Ch&&sec@ke99!", "+27838968976", "Kyle", "Smith");
         assertTrue(login.loginUser("ky1_1", "Ch&&secke99!"));
  }
  @Test
  public void testLoginFailed(){
       Login login = new Login(  "kyl_1", "Ch&&sec@ke99!", "+27838968976", "Kyle", "Smith");
          assertFalse(login.loginUser("ky1_1", "password"));    
  }
}

    
