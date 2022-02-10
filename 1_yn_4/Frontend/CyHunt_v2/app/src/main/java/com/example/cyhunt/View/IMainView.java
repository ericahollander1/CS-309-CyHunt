package com.example.cyhunt.View;

/**
 * An interface for the Main Activity. It contains methods that are called from other
 * java files
 * @author Lexi
 */
public interface IMainView {
    /**
     * Takes the user to the map page after pressing a button on the landing page
     */
    void onFindCyButtonClicked();

    /**
     * Updates the user's score inside shared preferences
     * @param s the user's new score
     */
    void setScore(String s);

    /**
     * Adds a completed achievement to the user's shared preferences
     * @param i the achievement ID
     */
    void checkAchievements(int i);

    /**
     * Locks the navigation drawer
     */
    void lockDrawer();

    /**
     * Unlocks the navigation drawer
     */
    void unlockDrawer();
}
