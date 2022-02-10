package com.example.cyhunt.Presenter;

import com.example.cyhunt.Model.LoginFuntionalityInterface;

import org.junit.Test;
import static org.junit.Assert.*;

public class LoginUnitTest {

    LoginFuntionalityInterface l = new LoginFunctionality();
    @Test
    public void noEntry(){assertEquals(l.checkIfUserCredentialsExist("",""), false);}
    @Test
    public void noUsername(){assertEquals(l.checkIfUserCredentialsExist("password", ""), false);}
    @Test
    public void noPassword(){assertEquals(l.checkIfUserCredentialsExist("", "username"), false);}
    @Test
    public void entriesValid(){assertEquals(l.checkIfUserCredentialsExist("password", "username"), true);}
}
