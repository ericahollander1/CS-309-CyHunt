package grp_1_yn_4.Service;

import grp_1_yn_4.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * repository of all the users
 */

public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * finds the user by their id
     * @param id
     * @return
     */
    User findById(int id);

    /**
     * finds a user by their emailid
     * @param emailId
     * @return
     */
    User findByEmailId(String emailId);

    /**
     * deletes a user from the repository by their id
     * @param id
     */
    @Transactional
    void deleteById(int id);


}
