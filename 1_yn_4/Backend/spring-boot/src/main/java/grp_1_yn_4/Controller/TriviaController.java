package grp_1_yn_4.Controller;


import grp_1_yn_4.Chat.MessageRepo;
import grp_1_yn_4.Chat.chat;
import grp_1_yn_4.Model.Trivia;
import grp_1_yn_4.Model.User;
import grp_1_yn_4.Service.TriviaRepository;
import grp_1_yn_4.Service.UserRepository;
import grp_1_yn_4.Chat.chat.*;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Trivia Controller
 */
@Api(value = "TrivaController", description = "REST APIs related to Trivia Questions")
@RestController
public class TriviaController {


    @Autowired
    TriviaRepository triviaRepository;
    @Autowired
    UserRepository userRepository;

    private static chat chat;

    @Autowired
    public void setMessageRepository(chat Chat) {
        chat = Chat;  // we are setting the static variable
    }

    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

    /**
     *  Get all Trivia Questions
     * @return
     */

    @GetMapping(path = "/trivia")
    List<Trivia> getAllTrivia(){
        return triviaRepository.findAll();
    }

    /**
     * Find specific trivia question by its id
     * @param id
     * @return Trivia object
     */
    @GetMapping(path = "/trivia/{id}")
    Trivia findById( @PathVariable int id){
        return triviaRepository.findById(id);
    }

    /**
     * Get all trivia questions by a certain author id
     * @param authorId
     * @return List of Trivia by Specific Author
     */
    @GetMapping(path = "/trivia/author/{authorId}")
    List<Trivia> findByAuthorId( @PathVariable int authorId){

        List<Trivia> listOfTrivia = triviaRepository.findAll();
        List<Trivia> triviaByAuthor = listOfTrivia.stream()
                .filter(trivia -> trivia.getAuthor().getId() == authorId).collect(Collectors.toList());

        return triviaByAuthor;
    }

    /**
     * Create new trivia question
     * @param trivia
     * @return string success or failure message
     */
    @PostMapping(path = "/trivia")
    String createTrivia(@RequestBody Trivia trivia){
        if (trivia == null)
            return failure;
        triviaRepository.save(trivia);
        return trivia.getId() + "";
    }

    /**
     * Update a trivia question
     * @param id
     * @param request
     * @return updated trivia question
     */
    @PutMapping("/trivia/{id}")
    Trivia updateTrivia(@PathVariable int id, @RequestBody Trivia request){
        Trivia trivia = triviaRepository.findById(id);
        if(trivia == null)
            return null;
        triviaRepository.save(request);
        return triviaRepository.findById(id);
    }

    /**
     * Delete Trivia Question
     * @param id
     * @return success message
     */
    @DeleteMapping(path = "/trivia/{id}")
    String deleteTrivia(@PathVariable int id){
        triviaRepository.deleteById(id);
        return success;
    }

    /**
     * Get the author of a trivia question
     * @param id
     * @return author
     */
    @GetMapping(path = "/trivia/{id}/author")
    User findAuthorById( @PathVariable int id){
        return triviaRepository.findById(id).getAuthor();
    }

    /**
     * Assign an author to a trivia question
     * @param triviaId
     * @param userId
     * @return success or failure message
     */
    @PutMapping("/trivia/{triviaId}/user/{userId}")
    String assignAuthorToQuestion(@PathVariable int triviaId,@PathVariable int userId){
        User user = userRepository.findById(userId);
        Trivia trivia = triviaRepository.findById(triviaId);
        if(user == null || trivia == null)
            return failure;
        trivia.setAuthor(user);
        triviaRepository.save(trivia);
        return success;
    }

    /**
     *  Get pending Trivia Questions
     * @return list of pending trivia questions
     */
    @GetMapping(path = "/pending/trivia")
    List<Trivia> getPendingTrivia(){
        List<Trivia> listAllTrivia = triviaRepository.findAll();
        List<Trivia> pendingTrivia = new ArrayList();;

        for(Trivia trivia: listAllTrivia) {
            if(trivia.getApproved() == false) {
                pendingTrivia.add(trivia);
            }
        }

        return pendingTrivia;
    }

    /**
     * Approve a trivia question
     * @param id
     * @return updated trivia question
     */
    @PutMapping("/approve/trivia/{id}")
    Trivia approveTrivia(@PathVariable int id){
        Trivia trivia = triviaRepository.findById(id);
        if(trivia == null)
            return null;
        trivia.setApproved(true);
        triviaRepository.save(trivia);
        String approvalMessage;

        if (trivia.getAuthor() != null) {
            approvalMessage = "Congratulations "+ trivia.getAuthor() + "your trivia question: "+ trivia.getQuestion() + " has been approved. Play the game to see your answer!";

        }
        else {
            approvalMessage = "Congratulations the trivia question: "+ trivia.getQuestion() + " has been approved. Play the game to see your answer!";
        }

        chat.onApproval("CyBot", approvalMessage);

        return triviaRepository.findById(id);
    }
}
