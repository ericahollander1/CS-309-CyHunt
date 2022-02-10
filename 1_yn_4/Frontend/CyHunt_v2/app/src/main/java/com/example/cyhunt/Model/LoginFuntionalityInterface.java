package com.example.cyhunt.Model;

/**
 *Interface for the LoginFunctionality class
 */
public interface LoginFuntionalityInterface {
    /**
     * This function makes a call to the database to learn if the user's password and username
     * corresponds to a real user. The thread created by this function calls a class in CVL when
     * it has an answer from the server, either an ID or a fialure message.
     * @param CVL
     * @param emailId username provided by user
     * @param password password provided by user
     */
    public void checkIfLoginValid(CustomVolleyListenerLogin CVL, String emailId, String password);

    /**
     * Once the user's ID has been retrieved, this one takes the ID and retrieves the user's info
     * from the database
     * @param CVL
     * @param userId
     */
    public void getUserDataById(CustomVolleyListenerLogin CVL, String userId);

    /**
     * Before calling the database, check the strings given by the user for proper length. This
     * will avoid unneccecary accidental calls to the database by users that haven't finished
     * logging in.
     * @param p password provided by user
     * @param u username provided by user
     * @return if the user has put in a valid username and password
     */
    public boolean checkIfUserCredentialsExist(String p, String u);

    /**
     * create a new guest user
     * @param CVL CVL, the class that called this one
     */
    public void signInAsGuest(CustomVolleyListenerLogin CVL);
}
