package grp_1_yn_4.Controller;

// Import Local classes
import grp_1_yn_4.Controller.PlaceController;
import grp_1_yn_4.Service.PlaceRepository;
import grp_1_yn_4.Model.Place;
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
//import Model.Place;
//import Service.PlaceRepository;
//import Controller.PlaceController;

//@RunWith(JUnit.class)
@RunWith(MockitoJUnitRunner.class)
public class PlaceControllerTest {

    @InjectMocks
    PlaceController placeController;

    @Mock
    PlaceRepository placeRepository;

    @Before
    public void init() {MockitoAnnotations.initMocks(this);}

    @Test
    public void getPlaceById() {
        float longitude = 68.1234f;
        float latitude = 27.8234f;

        when(placeRepository.findById(1)).thenReturn(new Place("Coruscant", "The vibrant heart and capital of the galaxy", "327 Core Worlds", longitude, latitude));

        Place place = placeController.getPlaceById(1);
        assertEquals("Coruscant", place.getName());
        assertEquals("The vibrant heart and capital of the galaxy", place.getDescription());
        assertEquals("327 Core Worlds", place.getAddress());
        assertEquals(longitude, place.getLongitude());
        assertEquals(latitude, place.getLatitude());
    }
    @Test
    public void getAllPlacesTest() {
        float longitude = 68.1234f;
        float latitude = 27.8234f;
        List<Place> places = new ArrayList<>();
        Place place1 = new Place("Coruscant", "The vibrant heart and capital of the galaxy", "327 Core Worlds", longitude, latitude);
        Place place2 = new Place("Tatooine", "A lawless place ruled by Hutt gangsters", "327 Outer Rim", latitude, longitude);
       // Place place3 = new Place("Naboo", "It is inhabited by peaceful humans known as the Naboo", "723 Outer Rim", 70.11920, 20.89002);

        places.add(place1);
        places.add(place2);
        //places.add(place3);

        when(placeRepository.findAll()).thenReturn(places);

        List<Place> morePlaces  = placeController.getAllPlaces();

        assertEquals(3, morePlaces.size());
        verify(placeRepository, times(1)).findAll();
    }

    @Test
    public void deleteByPlaceIdTest() {
        List<Place> places = new ArrayList<>();
        float longitude = 68.1234f;
        float latitude = 27.8234f;
        Place place1 = new Place("Coruscant", "The vibrant heart and capital of the galaxy", "327 Core Worlds", longitude, latitude);
        Place place2 = new Place("Tatooine", "A lawless place ruled by Hutt gangsters", "327 Outer Rim", latitude, longitude);
       // Place place3 = new Place("Naboo", "It is inhabited by peaceful humans known as the Naboo", "723 Outer Rim", 70.11920, 20.89002);

        places.add(place1);
        places.add(place2);
       // places.add(place3);

        when(placeRepository.findAll()).thenReturn(places);
        List<Place> morePlaces  = placeController.getAllPlaces();
        assertEquals(2, morePlaces.size());

        placeController.deletePlace(2);
        places.remove(place2);
        morePlaces = placeController.getAllPlaces();
        assertEquals(1, morePlaces.size());

        verify(placeRepository, times(2)).findAll();

    }

}
//
//import org.springframework.http.MediaType;
//
//// import mockito related
//import static org.mockito.Mockito.when;
//import static org.mockito.Mockito.any;
//import static org.hamcrest.CoreMatchers.*;
//import static org.hamcrest.Matchers.*;
//import static org.hamcrest.Matchers.is;
//
//@RunWith(SpringRunner.class)
//public class TestingControllerUnit {
//
//    @Autowired
//    private MockMvc controller;
//
//    @MockBean // note that this repo is also needed in controller
//    private StringRepo repo;
//
//
///*
//     * There are three steps here:
//     *   1 - set up mock database methods
//     *   2 - set up mock service methods
//     *   3 - call and test the results of the controller
//     */
//
//    @Test
//    public void testReverseEndpoint() throws Exception {
//
//        // Set up MOCK methods for the REPO
//        List<StringEntity> l = new ArrayList<StringEntity>();
//
//        // mock the findAll method
//        when(repo.findAll()).thenReturn(l);
//
//        // mock the save() method to save argument to the list
//        when(repo.save((StringEntity)any(StringEntity.class)))
//                .thenAnswer(x -> {
//                    StringEntity r = x.getArgument(0);
//                    l.add(r);
//                    return null;
//                });
//
//
//        // Set up MOCK methods for the SERVICE
//
//        // mock the reverse method
//        */
///*when(rService.reverse("hello")).thenReturn("olleh");*//*
//
//
//
//        // CALL THE CONTROLLER DIRECTLY (instead of using postman) AND TEST THE RESULTS
//        // we sent hello. we expect back a list with first object having "olleh" in it
//        controller.perform(post("/places").contentType(MediaType.APPLICATION_JSON).content("Place"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$[0].data", is("olleh")));
//    }
//
//    @Test
//    public void testCapitalizeEndpoint() throws Exception {
//
//        // Set up MOCK methods for the SERVICE
//        // mock the reverse method
//        List<StringEntity> l = new ArrayList<StringEntity>();
//        StringEntity rs = new StringEntity();
//        rs.setData("HELLO");
//        l.add(rs);
//        when(cService.capitalize("hello")).thenReturn(l);
//
//
//        // CALL THE CONTROLLER DIRECTLY (instead of using postman) AND TEST THE RESULTS
//        // we sent hello. we expect back a list with first object having "olleh" in it
//        controller.perform(post("/capitalize").contentType(MediaType.APPLICATION_JSON).content("hello"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$[0].data", is("HELLO")));
//    }
//
//}
//
//
