package gr.aueb.cf.schoolapp.repository;

import gr.aueb.cf.schoolapp.model.static_data.Region;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RegionRepository extends JpaRepository<Region, Long> {

    // Θα χρειαστούμε ενα query να φέρνει τα regions απο την db

    List<Region> findAllByOrderByNameAsc();

    // ακόμα κι αν δεν βάλουμε where αφήνουμε το By
    // findAllBy     , name είναι το πεδίο μέσα στο Region
}
