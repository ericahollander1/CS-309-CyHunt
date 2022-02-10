package com.example.cyhunt.View;

import java.util.List;
import java.util.Set;

/**
 * This is an interface for the Trivia Fragment. It contains the methods that are called
 * from the Trivia presenter
 * @author Lexi
 */
public interface ITriviaView {
    /**
     * Fills the appropriate TextView on the UI with the trivia question
     * @param s the trivia question
     */
    void fillQuestion(String s);

    /**
     * Fills the RadioButtons on the UI with the answer choices
     * @param list a list of four possible randomized answers
     */
    void fillAnswers(List<String> list);

    /**
     * Saves the current correct answer from the server to be compared against later
     * @param s the correct answer to the current trivia question
     */
    void assignCorrectAnswer(String s);

    /**
     * Saves the current question ID to be used later
     * @param qID the current question ID
     */
    void assignTriviaId(int qID);

    /**
     * Creates a Snackbar with a given message
     * @param s a message to display
     */
    void createSnackbar(String s);

    /**
     * Returns the user to the map page after answering the question
     */
    void returnToMap();

    /**
     * Updates the user's score inside shared preferences
     * @param s the user's new score
     */
    void setScore(String s);

    /**
     * Updates the user's achievements inside shared preferences
     */
    void unlockAchievement();
}
