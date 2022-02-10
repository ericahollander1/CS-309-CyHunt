package com.example.cyhunt.Presenter;

import org.junit.Test;

import static org.junit.Assert.*;

public class ProposalUnitTest {
    ProCreaTriviaFunctionality p = new ProCreaTriviaFunctionality();
    String invalid = "No field can be blank! \nPlease fill in all fields to submit.";
    @Test
    public void noQuestion(){assertEquals(p.simpleTriviaCheck("", "a", "a", "a", "a"), invalid);}
    @Test
    public void noAnswer(){assertEquals(p.simpleTriviaCheck("a", "", "a", "a", "a"), invalid);}
    @Test
    public void noDecoys(){assertEquals(p.simpleTriviaCheck("a", "a", "", "", ""), invalid);}
    @Test
    public void valid(){assertEquals(p.simpleTriviaCheck("a", "a", "a", "a", "a"), "Valid");}
}
