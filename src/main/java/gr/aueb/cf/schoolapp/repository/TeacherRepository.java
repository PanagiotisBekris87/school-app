package gr.aueb.cf.schoolapp.repository;

import gr.aueb.cf.schoolapp.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


//@Repository       , not needed if we extend JpaRepository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    // παίρνουμε αυτόματα την save (κάνει insert και update)
    // την deleteByID
    // και την delete (παίρνει entity)
    // και την findById
    // Όλα αυτά έρχονται από το CRUD repository που γίνεται extends από το JpaRepository


    // και μπορούμε να φτιάξουμε και άλλα queries
    // π.χ. findByVat

    Optional<Teacher> findByVat(String vat);
    // Convention για τα custom queries στο Spring
}
