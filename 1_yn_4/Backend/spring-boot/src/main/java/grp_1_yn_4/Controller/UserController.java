package grp_1_yn_4.Controller;

import grp_1_yn_4.Model.LoginObject;
import grp_1_yn_4.Model.Place;
import grp_1_yn_4.Model.Trivia;
import grp_1_yn_4.Model.User;
import grp_1_yn_4.Model.Achievement;
import grp_1_yn_4.Service.TriviaRepository;
import grp_1_yn_4.Service.UserRepository;
import grp_1_yn_4.Service.AchievementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.lang.Integer;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * controller where all the mapping calls exist
 */
@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AchievementRepository achievementRepository;

    @Autowired
    TriviaRepository triviaRepository;

    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

    /**
     * gets all users in the repository
     * @return the list of users
     */
    @GetMapping(path = "/users")
    List<User> getAllUsers(){
        return userRepository.findAll();
    }

    /**
     * gets user by a specific id
     * @param id
     * @return user who has that specific id
     */
    @GetMapping(path = "/users/{id}")
    User getUserById( @PathVariable int id){
        return userRepository.findById(id);
    }

    /**
     * gets a user by the specific emailId
     * @param emailId
     * @return user with that emailid
     */
    @GetMapping(path = "/user/email/{emailId}")
    User getUsersByEmailId( @PathVariable String emailId){
        if(emailId == null) {
            return null;
        }
        emailId.toLowerCase();

        return userRepository.findByEmailId(emailId);
    }

    /**
     * tries to log in a user and checks if the password matches and if the user exists
     * @param info
     * @return string that says what the response is (if successful the users id)
     */
    @PostMapping(path = "/login")
    String login(@RequestBody LoginObject info) {
        try {
            User user = userRepository.findByEmailId(info.getEmailId());

            String userPassword = user.getPassword();

            if(userPassword.equals(info.getPassword())) {
                return "" + user.getId();
            }
            else {
                return "Wrong Password";
            }
        } catch (Exception error) {
            return "User does not exist";
        }

    }

    /**
     * creates a user given the parameters
     * @param user
     * @return string to tell success
     */
    @PostMapping(path = "/users")
    String createUser(@RequestBody User user){
        if (user == null)
            return failure;
        if(user.getEmailId() != null) {
            user.setEmailId(user.getEmailId().toLowerCase());
        }

        userRepository.save(user);

        if(user.getRole().equals("GUEST")) {
            user.setEmailId(user.getRole().toLowerCase() + user.getId());
            user.setFirst_name("Guest");
            user.setLast_name("");
            userRepository.save(user);

            return user.getId()+"";
        }

        return success;
    }

    /**
     * updates the user with the given parameters by finding the id and adjusting from there
     * @param id
     * @param request
     * @return new user object
     */
    @PutMapping("/users/{id}")
    User updateUser(@PathVariable int id, @RequestBody User request){
        User user = userRepository.findById(id);
        if(user == null)
            return null;
        userRepository.save(request);
        return userRepository.findById(id);
    }

    /**
     * deletes user by the id given
     * @param id
     * @return success message
     */
    @DeleteMapping(path = "/users/{id}")
    String deleteUser(@PathVariable int id){
        userRepository.deleteById(id);
        return success;
    }

    /**
     * gets the sorted user score
     * @return a list of users
     */
    @GetMapping(path = "/users/score")
    List<User> getScoreSortedUsers(){
        List<User> listOfUsers = userRepository.findAll();

        Collections.sort(listOfUsers);

        return listOfUsers;
    }

    /**
     * gets the user score by the id
     * @param id
     * @return the users score
     */
    @GetMapping(path = "/user/score/{id}")
    int getUserScore(@PathVariable int id){
        User user = userRepository.findById(id);
        if(user == null)
            return 0;

        return user.getCyScore();
    }

    /**
     * updates the cyscore of the user by 5
     * @param userId
     */
    @PutMapping(path = "/users/{userId}/setCyscore")
    void incrementCyscore(@PathVariable int userId){
        User user = userRepository.findById(userId);
        int score = user.getCyScore();

        System.out.println(score);
        int newScore = score +5;
        System.out.println(newScore);

        user.setCyScore(newScore);
        userRepository.save(user);
    }

    /**
     * finds the questions authored by the user by their id
     * @param id
     * @return a list of trivia
     */

    @GetMapping(path = "/users/{id}/authored/trivia")
    List<Trivia> findQuestionsById( @PathVariable int id){

        return triviaRepository.findByAuthorId(id);
    }

    /**
     * adds authored trivia to a user by their id and the trivia id
     * @param userId
     * @param triviaId
     */
    @PutMapping(path = "/trivia/authored/{userId}/{triviaId}")
    void findTriviaAuthoredById( @PathVariable int userId, @PathVariable int triviaId){
        User user = getUserById(userId);
        Trivia trivia = triviaRepository.findById(triviaId);
        user.addAuthoredTrivia(trivia);
        userRepository.save(user);
    }

    /**
     * finds the users answered trivia by their id
     * @param userId
     * @return
     */
    @GetMapping(path = "/trivia/answered/{userId}")
    List<Trivia> findTriviaAnsweredById( @PathVariable int userId){

        User user = getUserById(userId);
        List<Trivia> listOfAnsweredTrivia = user.getAnsweredTrivia();

//        if(listOfAnsweredTrivia.size() == 0) {
//            return triviaRepository.findAll();
//        }

        return listOfAnsweredTrivia;
    }

    /**
     * adds the trivia answered by the user
     * @param userId
     * @param triviaId
     */
    @PutMapping(path = "/trivia/answered/{userId}/{triviaId}")
    void addTriviaAnsweredById( @PathVariable int userId, @PathVariable int triviaId){

        User user = getUserById(userId);
        Trivia trivia = triviaRepository.findById(triviaId);
        user.addAnsweredTrivia(trivia);
        userRepository.save(user);
    }

    /**
     * gets all the trivia that has not been answered by the user
     * @param userId
     * @return
     */
    @GetMapping(path = "/trivia/not/answered/{userId}")
    List<Trivia> getListOfTriviaNotAnswered(@PathVariable int userId) {
        User user = getUserById(userId);
        List<Trivia> triviaAnsweredList = findTriviaAnsweredById(userId);
        List<Trivia> listOfTrivia = triviaRepository.findAll();
        List<Trivia> listOfUnAnsweredTrivia = new ArrayList();

        for(Trivia trivia: listOfTrivia) {
            if(triviaAnsweredList.contains(trivia)) {
                // do nothing
            }
            else {
                listOfUnAnsweredTrivia.add(trivia);
            }
        }

        userRepository.save(user);

        return listOfUnAnsweredTrivia;
    }

    /**
     * updates the user's role to collaborator
     * @param id
     * @return new user object
     */
    @PutMapping("/user/role/{id}")
    User userRoleToCollaborator(@PathVariable int id){
        User user = userRepository.findById(id);
        if(user == null)
            return null;

        user.setRole("COLLABORATOR");
        userRepository.save(user);
        return userRepository.findById(id);
    }

    /**
     * get cy's current location
     * @param id
     * @return
     */
    @GetMapping(path = "/users/{id}/CyLocation")
    Place getCyCurrentLocation(@PathVariable int id){
        User user = userRepository.findById(id);
        Place nullPlace = new Place(null, null, null, 0,0);
        if(user == null)
            return null;

        if(user.getCurrentPlace() == null) {
            return nullPlace;
        }
        return user.getCurrentPlace();
    }

    /**
     * set cy's current location
     * @param id
     * @return
     */
    @PutMapping(path = "/users/{id}/CyLocation")
    Place setCyCurrentLocation(@PathVariable int id){
        User user = userRepository.findById(id);
        if(user == null)
            return null;

        return user.getCurrentPlace();
    }

    @GetMapping(path = "/achievement/user/{userId}")
    List<Achievement> getAchievements(@PathVariable int userId) {
        User user = getUserById(userId);
        if(user == null)
            return null;

        List<Integer> userAchievementsIds = user.getAchievements();
        List<Achievement> listOfUserAchievements = new ArrayList<>();

        for (Integer achievementId: userAchievementsIds) {
            Achievement achievement = achievementRepository.findById(achievementId);
            if (achievement != null) {
                listOfUserAchievements.add(achievement);
            }
        }

        return listOfUserAchievements;
    }

    @PutMapping(path = "/achievements/{userId}/{achievementId}")
    void addAchievementById( @PathVariable int userId, @PathVariable int achievementId){

        User user = userRepository.findById(userId);
        Achievement achievement = achievementRepository.findById(achievementId);

        if(achievement != null && user != null) {
            user.addAchievementIds(achievementId);
        }

        userRepository.save(user);
    }

    @GetMapping(path = "/achievements/notAchieved/{userId}")
    List<Achievement> getNotAchievedAchievements(@PathVariable int userId) {
        User user = getUserById(userId);

        List<Achievement> listOfAchievements = achievementRepository.findAll();
        List<Achievement> listOfNotAchievedAchievements = new ArrayList<>();
        List<Achievement> userAchievements = getAchievements(userId);

        if(userAchievements.size() == 0) {
            return listOfAchievements;
        }

        for(Achievement achievement: listOfAchievements) {
            if(userAchievements.contains(achievement)) {
                // do nothing
            }
            else {
                listOfNotAchievedAchievements.add(achievement);
            }
        }

        return listOfNotAchievedAchievements;
    }
}
