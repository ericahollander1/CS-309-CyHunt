public class cyHuntFunctions {
    public int[] randomizeCoordinates(int upperBoundY, int upperBoundX) {
        // we pass in or create some bounds for isu

        Random rand = new Random();
        int cyXpos = nextInt(upperboundY);
        int cyYpos = nextInt(upperboundX);

        return [cyXpos, cyYpos];
    }

    /*
    Not sure if this will be a function, but I think setting it up as a hashmap would be good 
    with the key/value pairs. 
     */
    public void storeTriviaQuestions() {
        // we will store the trivia questions in a hashMap so we can have value of string question
        // and key of string answer
        HashMap<String, String> triviaQuestions = new HashMap<String, String>();

        // Add keys and values (Trivia Question, Trivia Answer)
        // this is an example. we will put more random questions, but this is to demonstrate how to
        triviaQuestions.put("Who is the president of ISU?", "Wendy Wintersten");
    }
    
}
