package com.example.cyhunt.Model;

/**
 * An interface for the SignupCredentialChecker class
 */
public interface SignupCredentialCheckerInterface {
    /**
     * Check if the password meets some set requirements of security
     * @param pass the user's inputted password
     * @param repeatPass the user's inputted repeatPassword
     * @return a message that says either 'valid' or the list of things the user has to fix
     */
    String checkPasswordRequirements(String pass, String repeatPass);

    /**
     * Check if the names the user put in exist
     * @param fn first name
     * @param ln last name
     * @param en email username
     * @return a message that says either 'valid' or lists the problems the user needs to fix
     */
    String checkNameRequirements(String fn, String ln, String en);

    /**
     * If the user's names and password meets requirements, run the info by the database to either add
     * them or reject them. Calls a method in CVL with either a failure (user already exists) or success
     * message
     * @param fn user's first name
     * @param ln user's last name
     * @param en user's email
     * @param pass user's password
     * @param CVL CVL
     */
    void addNewUserIfPossible(String fn, String ln, String en, String pass, CustomVolleyListenerSignup CVL);

    /**
     * Once the user has been successfully created, call this to re-retireve their new info from the
     * database
     * @param CVL CVL
     * @param userId the user's ID
     */
    void getUserDataById(CustomVolleyListenerSignup CVL, String userId);
}
