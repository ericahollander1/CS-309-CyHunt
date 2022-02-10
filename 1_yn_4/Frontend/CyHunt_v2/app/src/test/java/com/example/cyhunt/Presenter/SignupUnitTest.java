package com.example.cyhunt.Presenter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import android.content.Context;

import com.example.cyhunt.Model.SignupCredentialCheckerInterface;
import com.example.cyhunt.Presenter.SignupCredentialChecker;

@RunWith(MockitoJUnitRunner.class)
public class SignupUnitTest {
    String noPassword =("Password is not at least 8 characters long\nPassword must contain at least one number\nPassword must contain at least one special character\nPassword and repeat password fields must match\n");
    String passwordIsTooShort = ("Password is not at least 8 characters long\n");
    String passwordHasNoNumber = ("Password must contain at least one number\n");
    String passwordHasNoSpecialCharacter = ("Password must contain at least one special character\n");
    String valid = ("valid");

    String firstNameTooShort = ("First name cannot be empty!\n");
    String lastNameTooShort = ("Last name cannot be empty!\n");
    String userNameTooShort = ("NetID cannot be empty!\n");
    String allNamesTooShort = ("First name cannot be empty!\nLast name cannot be empty!\nNetID cannot be empty!\n");

    SignupCredentialCheckerInterface s = new SignupCredentialChecker();

    //TEST PASSWORD CHECKER
    @Test
    public void PasswordHasNoEntry() {assertEquals(s.checkPasswordRequirements("",""), noPassword);};
    @Test
    public void PasswordIsNotLongEnough() {assertEquals(s.checkPasswordRequirements("7_","7_"), passwordIsTooShort);};
    @Test
    public void PasswordDoesNotContainANumber() {assertEquals(s.checkPasswordRequirements("aaaaaaaaa_","aaaaaaaaa_"), passwordHasNoNumber);};
    @Test
    public void PasswordDoesNotContainASpecialCharacter() {assertEquals(s.checkPasswordRequirements("aaaaaaaaaa2","aaaaaaaaaa2"), passwordHasNoSpecialCharacter);};
    @Test
    public void PasswordIsProper() {assertEquals(s.checkPasswordRequirements("p@ssw0rd","p@ssw0rd"), valid);};

    //TEST USERNAME CHECKER
    @Test
    public void firstNameIsTooShort(){assertEquals(s.checkNameRequirements("", "aa", "aa"), firstNameTooShort);};
    @Test
    public void lastNameIsTooShort(){assertEquals(s.checkNameRequirements("aa","","aa"), lastNameTooShort);}
    @Test
    public void userNameIsTooShort(){assertEquals(s.checkNameRequirements("aa", "aa", ""), userNameTooShort);}
    @Test
    public void allNamesAreTooShort(){assertEquals(s.checkNameRequirements("","",""), allNamesTooShort);}
    @Test
    public void namesAreValid(){assertEquals(s.checkNameRequirements("aa", "aa", "aa"), valid);}
}
