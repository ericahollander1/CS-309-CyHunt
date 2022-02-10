package grp_1_yn_4.Controller;


import java.util.List;

import grp_1_yn_4.Model.Achievement;
import grp_1_yn_4.Service.AchievementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import grp_1_yn_4.Service.AchievementRepository;


@RestController
public class AchievementController {

    @Autowired
    AchievementRepository achievementRepository;

    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

    @GetMapping(path = "/achievement")
    List<Achievement> getAllAchievement(){
        return achievementRepository.findAll();
    }

    @GetMapping(path = "/achievement/{id}")
    Achievement findById( @PathVariable int id){
        return achievementRepository.findById(id);
    }

    @PostMapping(path = "/achievement")
    String createAchievement(@RequestBody Achievement achievement){
        if (achievement == null)
            return failure;
        achievementRepository.save(achievement);
        return success;
    }

    @PutMapping("/achievement/{id}")
    Achievement updateAchievement(@PathVariable int id, @RequestBody Achievement request){
        Achievement achievement = achievementRepository.findById(id);
        if(achievement == null)
            return null;
        achievementRepository.save(request);
        return achievementRepository.findById(id);
    }

    @DeleteMapping(path = "/achievement/{id}")
    String deleteAchievement(@PathVariable int id){
        achievementRepository.deleteById(id);
        return success;
    }
}
