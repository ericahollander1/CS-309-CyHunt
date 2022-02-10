package grp_1_yn_4.Service;

import grp_1_yn_4.Model.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Place Repository is an interface that extends JpaRepository
 * This communicated with out database
 */
public interface PlaceRepository extends JpaRepository<Place, Long> {

    /**
     * Find a specific place in the database by it's id
     * @param id
     * @return Place object
     */
    Place findById(int id);

    /**
     * Find a specific place in the database by it's name
     * @param name
     * @return Place object
     */
    Place findByName(String name);

    /**
     * Delete a place in the database by the id of the Place
     * @param id
     */
    @Transactional
    void deleteById(int id);

}