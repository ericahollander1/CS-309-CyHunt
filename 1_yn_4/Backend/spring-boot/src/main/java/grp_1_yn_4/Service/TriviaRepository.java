package grp_1_yn_4.Service;

import grp_1_yn_4.Model.Trivia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Trivia Repository is an interface that extends JpaRepository
 * This communicated with out database
 */
public interface TriviaRepository extends JpaRepository<Trivia, Long> {

    /**
     * Find a specific trivia question in the database by it's id
     * @param id
     * @return trivia object
     */
    Trivia findById(int id);

    /**
     * Delete a trivia question in the database by the id of the question
     * @param id
     */
    @Transactional
    void deleteById(int id);

    /**
     * Find all trivia questions authored by a specific user
     * @param id
     * @return List of trivia questions written by a specific author
     */
    List<Trivia> findByAuthorId(int id);

}
