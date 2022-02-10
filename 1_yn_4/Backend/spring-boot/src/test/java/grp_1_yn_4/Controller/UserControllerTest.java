package grp_1_yn_4.Controller;

// Import Local classes
import grp_1_yn_4.Controller.UserController;
import grp_1_yn_4.Service.UserRepository;
import grp_1_yn_4.Model.User;
import grp_1_yn_4.Model.Trivia;
import grp_1_yn_4.Controller.TriviaController;

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
//import Model.User;
//import Service.UserRepository;
//import Controller.UserController;

//@RunWith(JUnit.class)
@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

    @InjectMocks
    UserController userController;

    @Mock
    UserRepository userRepository;

    @Before
    public void init() {MockitoAnnotations.initMocks(this);}

    @Test
    public void findUserByIdTest() {
        when(userRepository.findById(1)).thenReturn(new User("Han", "Solo", "Falcon327", "Chewy327!", "USER"));

        User user = userController.getUserById(1);
        assertEquals("Han", user.getFirst_name());
        assertEquals("Solo", user.getLast_name());
        assertEquals("Falcon327", user.getEmailId());
        assertEquals("Chewy327!", user.getPassword());
        assertEquals(0, user.getCyScore());
        assertEquals("USER", user.getRole());
    }
    @Test
    public void getAllUsersTest() {
        List<User> users = new ArrayList<>();
        User user1 = new User("Han", "Solo", "Falcon327", "Chewy327!", "USER");
        User user2 = new User("Anakin", "Skywalker", "DarthVader", "@LukeIAmYourFather327", "USER");
        User user3 = new User("Princess", "Leia", "PLeia", "SpaceBunsRock327!", "USER");

        users.add(user1);
        users.add(user2);
        users.add(user3);

        when(userRepository.findAll()).thenReturn(users);

        List<User> moreUsers  = userController.getAllUsers();

        assertEquals(3, moreUsers.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    public void deleteByUserIdTest() {
        List<User> users = new ArrayList<>();
        User user1 = new User("Han", "Solo", "Falcon327", "Chewy327!", "USER");
        User user2 = new User("Anakin", "Skywalker", "DarthVader", "@LukeIAmYourFather327", "USER");
        User user3 = new User("Princess", "Leia", "PLeia", "SpaceBunsRock327!", "USER");
        users.add(user1);
        users.add(user2);
        users.add(user3);

        when(userRepository.findAll()).thenReturn(users);
        List<User> moreUsers  = userController.getAllUsers();
        assertEquals(3, moreUsers.size());

        userController.deleteUser(3);
        users.remove(user3);
        moreUsers = userController.getAllUsers();
        assertEquals(2, moreUsers.size());

        verify(userRepository, times(2)).findAll();

    }

}
/*
import org.springframework.http.MediaType;

// import mockito related
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.is;

@RunWith(SpringRunner.class)
public class TestingControllerUnit {

    @Autowired
    private MockMvc controller;

    @MockBean // note that this repo is also needed in controller
    private StringRepo repo;


    */
/*
     * There are three steps here:
     *   1 - set up mock database methods
     *   2 - set up mock service methods
     *   3 - call and test the results of the controller
     *//*

    @Test
    public void testReverseEndpoint() throws Exception {

        // Set up MOCK methods for the REPO
        List<StringEntity> l = new ArrayList<StringEntity>();

        // mock the findAll method
        when(repo.findAll()).thenReturn(l);

        // mock the save() method to save argument to the list
        when(repo.save((StringEntity)any(StringEntity.class)))
                .thenAnswer(x -> {
                    StringEntity r = x.getArgument(0);
                    l.add(r);
                    return null;
                });


        // Set up MOCK methods for the SERVICE

        // mock the reverse method
        */
/*when(rService.reverse("hello")).thenReturn("olleh");*//*



        // CALL THE CONTROLLER DIRECTLY (instead of using postman) AND TEST THE RESULTS
        // we sent hello. we expect back a list with first object having "olleh" in it
        controller.perform(post("/users").contentType(MediaType.APPLICATION_JSON).content("User"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].data", is("olleh")));
    }

    @Test
    public void testCapitalizeEndpoint() throws Exception {

        // Set up MOCK methods for the SERVICE
        // mock the reverse method
        List<StringEntity> l = new ArrayList<StringEntity>();
        StringEntity rs = new StringEntity();
        rs.setData("HELLO");
        l.add(rs);
        when(cService.capitalize("hello")).thenReturn(l);


        // CALL THE CONTROLLER DIRECTLY (instead of using postman) AND TEST THE RESULTS
        // we sent hello. we expect back a list with first object having "olleh" in it
        controller.perform(post("/capitalize").contentType(MediaType.APPLICATION_JSON).content("hello"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].data", is("HELLO")));
    }

}

*/
