package grp_1_yn_4.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import grp_1_yn_4.Model.Trivia;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.lang.Integer;
import grp_1_yn_4.Model.Achievement;

/**
 *
 * this class represents a user
 */
@Entity
public class User implements Comparable<User>{
    /**
     * Variables for the dataobject user
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String first_name;
    private String last_name;
    private String username;
    @OneToOne
    @JsonIgnore
    private Place currentPlace;

    @Column(unique=true)
    private String emailId;

    private String password;
    private int dist_traveled;
    private int cyScore;
    /**
     * represents the trivia questions answered by the user
     */
    @OneToMany
    @JsonIgnore
    private List<Trivia> answeredTrivia;
    /**
     * represents the trivia questions that were created by the user (Must have the role of contributer or admin)
     */
    @OneToMany //this is just for the author of the trivia question
    @JsonIgnore
    private List<Trivia> authoredTrivia;

    @ElementCollection
    @JsonIgnore
    private List<Integer> listAchievementIds;
    /**
     * role can be "USER", "CONTRIBUTER", or "ADMIN"
     */
    private String role;
    private LocalDateTime created_ts;
    private LocalDateTime updated_ts;

    /**
     * Constructor for User that passes in the following parameters
     * @param first_name
     * @param last_name
     * @param emailId
     * @param password
     * @param role
     */
    public User(String first_name, String last_name, String emailId, String password, String role) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.emailId = emailId;
        this.password = password;
        this.username = generateUsername(emailId);
        this.role = role;
        cyScore = 0;
        created_ts = LocalDateTime.now();
        authoredTrivia = new ArrayList<>();
        answeredTrivia = new ArrayList<>();
        listAchievementIds = new ArrayList<>();
        currentPlace = null;
    }

    /**
     * constructor for user that passes in only the role
     * @param role
     */
    public User(String role) {
        this.role = role;
        cyScore = 0;
        created_ts = LocalDateTime.now();
        authoredTrivia = new ArrayList<>();
        answeredTrivia = new ArrayList<>();
        listAchievementIds = new ArrayList<>();
        currentPlace = null;
    }

    /**
     * constructor that passes in no parameters
     */
    public User() {
        cyScore = 0;
        authoredTrivia = new ArrayList<>();
        answeredTrivia = new ArrayList<>();
        listAchievementIds = new ArrayList<>();
        currentPlace = null;
    }


    // =============================== Getters and Setters for each field ================================== //

    /**
     * getter for id
     * @return id
     */
    public int getId(){
        return id;
    }

    /**
     * setter for id
     * @param id
     */
    public void setId(int id){
        this.id = id;
    }

    /**
     * getter for the user's first name
     * @return first name
     */
    public String getFirst_name(){
        return first_name;
    }

    /**
     * setter for the user's first name
     * @param name
     */
    public void setFirst_name(String name){
        this.first_name = name;
    }

    /**
     * getter for the user's last name
     * @return last name
     */
    public String getLast_name(){
        return last_name;
    }

    /**
     * setter for the user's last name
     * @param name
     */
    public void setLast_name(String name){
        this.last_name = name;
    }

    /**
     * gets the email id of the user
     * @return emailId
     */
    public String getEmailId(){
        return emailId;
    }

    /**
     * sets the email id of the user
     * @param emailId
     */
    public void setEmailId(String emailId){
        this.emailId = emailId;
    }

    /**
     * gets the users password
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * sets the users password
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * gets the total distance traveled by the user
     * @return distance traveled
     */
    public int getDist_traveled() {
        return dist_traveled;
    }

    /**
     * sets the total distance traveled by the user
     * @param dist_traveled
     */
    public void setDist_traveled(int dist_traveled) {
        this.dist_traveled = dist_traveled;
    }

    /**
     * gets the list of answered trivia by the user
     * @return (list)answeredTrivia
     */
    public List<Trivia> getAnsweredTrivia() {
        return answeredTrivia;
    }

    /**
     * sets the list of answered trivia by the user
     * @param answeredTrivia
     */
    public void setAnsweredTrivia(List<Trivia> answeredTrivia) {
        this.answeredTrivia = answeredTrivia;
    }

    /**
     * adds a trivia object into the list of answered trivia question
     * will commonly be called when a user answers a question correctly
     * @param answeredTrivia
     */
    public void addAnsweredTrivia(Trivia answeredTrivia){
        this.answeredTrivia.add(answeredTrivia);
    }

    /**
     * gets the trivia the user has authored
     * @return (list)authoredTrivia
     */
    public List<Trivia> getAuthoredTrivia() {
        return authoredTrivia;
    }

    /**
     * sets the trivia the user has authored
     * @param authoredTrivia
     */
    public void setAuthoredTrivia(List<Trivia> authoredTrivia) {
        this.authoredTrivia = authoredTrivia;
    }

    /**
     * adds a trivia object to the list of authored trivia by a user
     * will be called when an admin approves a suggested trivia question or when they submit one themselves
     * @param authoredTrivia
     */
    public void addAuthoredTrivia(Trivia authoredTrivia){
        this.authoredTrivia.add(authoredTrivia);
    }

    /**
     * gets the users score (CyScore)
     * @return cyScore
     */
    public int getCyScore() {
        return cyScore;
    }

    /**
     * sets the users score (cyScore
     * @param cyScore
     */
    public void setCyScore(int cyScore) {
        this.cyScore = cyScore;
    }

    /**
     * Get user's achievements
     * @return list of achievements
     */
    public List<Integer> getAchievements() {
        return listAchievementIds;
    }


    public void addAchievementIds(Integer achievementId){
        this.listAchievementIds.add(achievementId);
    }

    /**
     * gets the users role
     * @return role
     */
    public String getRole() {
        return role;
    }

    /**
     * sets the users role
     * @param role
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * gets the created time
     * @return created_ts
     */
    public LocalDateTime getCreated_ts() {
        return created_ts;
    }

    /**
     * sets the created time
     * @param created_ts
     */
    public void setCreated_ts(LocalDateTime created_ts) {
        this.created_ts = created_ts;
    }

    /**
     * gets the updated time
     * @return updated_ts
     */
    public LocalDateTime getUpdated_ts() {
        return updated_ts;
    }

    /**
     * sets the updated time
     * @param updated_ts
     */
    public void setUpdated_ts(LocalDateTime updated_ts) {
        this.updated_ts = updated_ts;
    }

    /**
     * makes a compare to method to check when two users
     * will be used in leaderboard sort
     * @param u
     * @return u's cyScore- this users cyScore
     */
    @Override
    public int compareTo(User u) {
        return (u.getCyScore())-this.getCyScore();
    }

    /**
     * generates the username for the user based off of their email (commonly will be the university id)
     * @param email
     * @return
     */
    public String generateUsername(String email) {
        int index = 0;
        for(int i =0; i < email.length(); i++) {
            if(email.charAt(i) == '@') {
                index = i;
            }
        }

        if(index != 0) {
            return email.substring(0, index);
        }

        return email;
    }

    /**
     * gets the user's username
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * sets the user's username
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * gets the user's current location
     * @return username
     */
    public Place getCurrentPlace() {
        return currentPlace;
    }

    /**
     * sets the user's current location
     * @param currentPlace
     */
    public void setCurrentPlace(Place currentPlace) {
        this.currentPlace = currentPlace;
    }
}
