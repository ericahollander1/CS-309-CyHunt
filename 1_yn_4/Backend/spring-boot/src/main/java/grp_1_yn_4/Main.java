package grp_1_yn_4;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import grp_1_yn_4.Service.*;
import grp_1_yn_4.Model.*;
import grp_1_yn_4.Controller.*;
import grp_1_yn_4.SpringFoxConfig.*;



@SpringBootApplication
//@EnableJpaRepositories
class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    CommandLineRunner initUser(UserRepository userRepository, TriviaRepository triviaRepository) {
        return args -> {
//            User user1 = new User("Sean", "Reilly", "sean@iastate.edu", "password1");
//            User user2 = new User("Patrick", "Ryan", "patrick@iastate.edu", "password2");
//            User user3 = new User("Michael", "Quinn", "justin@iastate.edu", "password3");
//            userRepository.save(user1);
//            userRepository.save(user2);
//            userRepository.save(user3);
//
//            String[] decoyAnswers = {"Bomont", "Chicago", "Des Moines"};
//
//            Trivia trivia1 = new Trivia(1, "Where is Iowa State located?", "Ames", decoyAnswers);
//
//            triviaRepository.save(trivia1);

//            User guest1 = new User();
//            userRepository.save(guest1);
        };
    }

}