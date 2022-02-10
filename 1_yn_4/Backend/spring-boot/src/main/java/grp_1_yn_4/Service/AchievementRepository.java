package grp_1_yn_4.Service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import grp_1_yn_4.Model.Achievement;

public interface AchievementRepository extends JpaRepository<grp_1_yn_4.Model.Achievement, Long> {

    /**
     * Find a specific achievement in the database by it's id
     * @param id
     * @return Place object
     */
    Achievement findById(int id);

    /**
     * Delete a Achievement in the database by the id of the Place
     * @param id
     */
    @Transactional
    void deleteById(int id);

}