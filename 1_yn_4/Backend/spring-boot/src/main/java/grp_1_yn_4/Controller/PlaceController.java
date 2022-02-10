package grp_1_yn_4.Controller;


import grp_1_yn_4.Model.Place;
import grp_1_yn_4.Model.User;
import grp_1_yn_4.Service.PlaceRepository;
import grp_1_yn_4.Service.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;


/**
 * Place Controller class
 */
@RestController
public class PlaceController {

    @Autowired
    PlaceRepository placeRepository;

    @Autowired
    UserRepository userRepository;

    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

    /**
     * Get Request to get all places stored in the database
     * @return List of all places
     */
    @GetMapping(path = "/places")
    List<Place> getAllPlaces(){
        return placeRepository.findAll();
    }

    /**
     * Get Request to get specific location by the Place's id
     * @param id
     * @return Place object
     */
    @GetMapping(path = "/places/{id}")
    Place getPlaceById( @PathVariable int id){
        return placeRepository.findById(id);
    }

    /**
     * Get Request to get specific location by the Place's name
     * @param name
     * @return Place object
     */
    @GetMapping(path = "/places/name/{name}")
    Place getPlaceByName( @PathVariable String name){
        return placeRepository.findByName(name);
    }

    /**
     * Post Request to create a new Place
     * @param place
     * @return string success or failure message
     */
    @PostMapping(path = "/places")
    String createPlace(@RequestBody Place place){
        if (place == null)
            return failure;
        placeRepository.save(place);
        return success;
    }

    /**
     * Put mapping to update information about a place
     *
     * @param id
     * @param request
     * @return Place Object
     */
    @PutMapping("/places/{id}")
    Place updatePlace(@PathVariable int id, @RequestBody Place request){
        Place place = placeRepository.findById(id);
        if(place == null)
            return null;
        placeRepository.save(request);
        return placeRepository.findById(id);
    }

    /**
     * Delete mapping to delete a place from the database
     * @param id
     * @return string success message
     */
    @DeleteMapping(path = "/places/{id}")
    String deletePlace(@PathVariable int id){
        placeRepository.deleteById(id);
        return success;
    }

    /**
     * Generate new cy location
     *
     * @param id
     * @return Place Object
     */
    @GetMapping("/places/generateNewLocation/user/{id}")
    Place generateNewLocation(@PathVariable int id){
        User user = userRepository.findById(id);
        if(user == null) {
            return null;
        }


        Place lastFoundLocation = user.getCurrentPlace();
        Place newCyLocation = null;
        List<Place> listOfPlaces = placeRepository.findAll();
        if(lastFoundLocation == null) {
            user.setCurrentPlace(listOfPlaces.get(0));
            userRepository.save(user);
            return listOfPlaces.get(0);
        }

        Random rand = new Random();
        boolean flag = true;

        while (flag) {
            int index = rand.nextInt(listOfPlaces.size());
            newCyLocation = listOfPlaces.get(index);

            if (newCyLocation.getDescription().equals(lastFoundLocation.getDescription())) {
                // flag is true
                //generate new random location that hasn't already been hit
            }
            else {
                flag = false;
            }
        }
        if(newCyLocation != null) {
            user.setCurrentPlace(newCyLocation);
            userRepository.save(user);

            return newCyLocation;
        }
        userRepository.save(user);

        return null;
    }
}
