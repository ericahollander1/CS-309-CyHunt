package grp_1_yn_4.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import grp_1_yn_4.Model.User;

import javax.persistence.*;


/**
 * Trivia Object
 */
@Entity
public class Trivia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String question;
    private String answer;
    private String[] answers;
    private boolean approved;

    @ManyToOne//@JoinColumn(name = "contributer")
    @JoinColumn(name = "authored_id")
    @JsonIgnore
    private User author;

    @ManyToOne//@JoinColumn(name = "contributer")
    @JoinColumn(name = "answered_id")
    @JsonIgnore
    private User answerer;


    /**
     * Trivia Question Constructor
     * @param question
     * @param answer
     * @param answers
     * @param author
     */
    public Trivia(String question, String answer, String[] answers, User author) {
        this.question = question;
        this.answer = answer;
        this.answers = new String[answers.length];
        for(int i=0; i < answers.length; i++){
            this.answers[i] = answers[i];
        }
        this.author = author;
    }

    /**
     * Trivia Constructor without author
     * @param id
     * @param question
     * @param answer
     * @param answers
     */
    public Trivia(int id, String question, String answer, String[] answers) {
        //let's create an author of Cy
        author = null;
        this.id = id;
        this.question = question;
        this.answer = answer;
        this.answers = new String[answers.length];
        for(int i=0; i < answers.length; i++){
            this.answers[i] = answers[i];
        }
    }

    /**
     * default trivia constructor
     */
    public Trivia() {
    }

    // =============================== Getters and Setters for each field ================================== //

    /**
     * Get Trivia Id
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Set Trivia id
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Get Trivia's decoy Answers
     * @return wrong answers
     */
    public String[] getAnswers() {
        return answers;
    }

    /**
     * Set decoy answers
     * @param answers
     */
    public void setAnswers(String[] answers) {
        this.answers = new String[answers.length];
        for(int i=0; i < answers.length; i++){
            this.answers[i] = answers[i];
        }
    }

    /**
     * Get approval status of Trivia Question
     * @return boolean
     */
    public boolean getApproved() {
        return approved;
    }

    /**
     * Set approved status of trivia Question
     * @param approved
     */
    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    /**
     * Get Author of Trivia Question
     * @return Author
     */
    public User getAuthor() {
        return author;
    }

    /**
     * Set Author of Trivia Question
     * @param author
     */
    public void setAuthor(User author) { this.author = author; }

    /**
     * Get Question
     * @return question
     */
    public String getQuestion() {
        return question;
    }

    /**
     * Set Question
     * @param question
     */
    public void setQuestion(String question) {
        this.question = question;
    }

    /**
     * Get Answer
     * @return answer
     */
    public String getAnswer() {
        return answer;
    }

    /**
     * Set Answer
     * @param answer
     */
    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
