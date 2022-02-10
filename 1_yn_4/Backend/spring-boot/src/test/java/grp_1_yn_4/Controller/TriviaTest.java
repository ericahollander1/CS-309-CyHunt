package grp_1_yn_4.Controller;

// Import Local classes
import grp_1_yn_4.Controller.TriviaController;
import grp_1_yn_4.Model.Trivia;
import grp_1_yn_4.Service.TriviaRepository;
import grp_1_yn_4.Model.Trivia;
import grp_1_yn_4.Model.User;
import grp_1_yn_4.Service.UserRepository;

// Import Java libraries
import java.util.ArrayList;
import java.util.List;


// import junit/spring tests
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

// import mockito related
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.*;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TriviaTest {

    @InjectMocks
    TriviaController triviaController;

    @Mock
    TriviaRepository triviaRepository;

    @Before
    public void init() {MockitoAnnotations.initMocks(this);}

    @Test
    public void findTriviaByIdTest() {
        String[] wrongAnswers = {"Quadanium Steel", "Phrik", "Vibranium"};
        when(triviaRepository.findById(1)).thenReturn(new Trivia(0, "What is mandalorian Armor made out of?", "Beskar Steel", wrongAnswers));

        Trivia trivia = triviaController.findById(1);
        assertEquals("What is mandalorian Armor made out of?", trivia.getQuestion());
        assertEquals("Beskar Steel", trivia.getAnswer());
        assertEquals(wrongAnswers, trivia.getAnswers());
    }
    @Test
    public void getAllTriviasTest() {
        String[] wrongAnswers1 = {"Quadanium Steel", "Phrik", "Vibranium"};
        String[] wrongAnswers2 = {"5 years", "500 years", "5 months"};
        String[] wrongAnswers3 = {"Ahsoka Tano", "Asaaj Ventress", "Rey"};

        List<Trivia> trivias = new ArrayList<>();
        Trivia trivia1 = new Trivia(0, "What is mandalorian Armor made out of?", "Beskar Steel", wrongAnswers1);
        Trivia trivia2 = new Trivia(1, "How old is Grogu", "50 years", wrongAnswers2);
        Trivia trivia3 = new Trivia(2, "Who is Luke and Leia's mother", "Padme Amidala", wrongAnswers3);

        trivias.add(trivia1);
        trivias.add(trivia2);
        trivias.add(trivia3);

        when(triviaRepository.findAll()).thenReturn(trivias);

        List<Trivia> moreTrivias  = triviaController.getAllTrivia();

        assertEquals(3, moreTrivias.size());
        verify(triviaRepository, times(1)).findAll();
    }

    @Test
    public void deleteByTriviaIdTest() {
        String[] wrongAnswers1 = {"Quadanium Steel", "Phrik", "Vibranium"};
        String[] wrongAnswers2 = {"5 years", "500 years", "5 months"};
        String[] wrongAnswers3 = {"Ahsoka Tano", "Asaaj Ventress", "Rey"};

        List<Trivia> trivias = new ArrayList<>();
        Trivia trivia1 = new Trivia(0, "What is mandalorian Armor made out of?", "Beskar Steel", wrongAnswers1);
        Trivia trivia2 = new Trivia(1, "How old is Grogu", "50 years", wrongAnswers2);
        Trivia trivia3 = new Trivia(2, "Who is Luke and Leia's mother", "Padme Amidala", wrongAnswers3);
        trivias.add(trivia1);
        trivias.add(trivia2);
        trivias.add(trivia3);

        when(triviaRepository.findAll()).thenReturn(trivias);
        List<Trivia> moreTrivias  = triviaController.getAllTrivia();
        assertEquals(3, moreTrivias.size());

        triviaController.deleteTrivia(3);
        trivias.remove(trivia3);
        moreTrivias = triviaController.getAllTrivia();
        assertEquals(2, moreTrivias.size());

        verify(triviaRepository, times(2)).findAll();

    }

}
