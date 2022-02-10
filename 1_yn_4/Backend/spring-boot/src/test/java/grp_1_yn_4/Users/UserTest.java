
package grp_1_yn_4.Users;

import java.util.Random;
import org.junit.jupiter.api.Test;

import org.junit.*;
import junit.framework.TestCase;
import grp_1_yn_4.Model.User;

import static org.junit.Assert.assertEquals;

public class UserTest{

    Random rand = new Random();

    @Test
    public void testID() {
        //given
        User user = new User();
        int randomID = rand.nextInt();

        //when
        user.setId(randomID);
        int response = user.getId();

        //then
        assertEquals(randomID, response);
    }

    @Test
    public void testUsername(){
        //given
        User user = new User();
        String newName = "Falcon327";

        //when
        user.setUsername(newName);
        String response = user.getUsername();

        //then
        assertEquals(newName, response);
    }

    @Test
    public void testFirstName(){
        //given
        User user = new User();
        String newFirstName = "Luke";

        //when
        user.setFirst_name(newFirstName);
        String response = user.getFirst_name();

        //then
        assertEquals(newFirstName, response);
    }

    @Test
    public void testLastName(){
        //given
        User user = new User();
        String newLastName = "Skywalker";

        //when
        user.setLast_name(newLastName);
        String response = user.getLast_name();

        //then
        assertEquals(newLastName, response);
    }

    @Test
    public void testEmptyConstructor(){
        //given
        User user = new User();

        //then
        assertEquals(user.getCyScore(), 0);
        assertEquals(user.getRole(), null);
    }
}

