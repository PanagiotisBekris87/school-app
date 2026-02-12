package gr.aueb.cf.schoolapp.service;

import gr.aueb.cf.schoolapp.core.exceptions.EntityAlreadyExistsException;
import gr.aueb.cf.schoolapp.core.exceptions.EntityInvalidArgumentException;
import gr.aueb.cf.schoolapp.dto.TeacherInsertDTO;
import gr.aueb.cf.schoolapp.dto.TeacherReadOnlyDTO;
import gr.aueb.cf.schoolapp.mapper.Mapper;
import gr.aueb.cf.schoolapp.model.Teacher;
import gr.aueb.cf.schoolapp.model.static_data.Region;
import gr.aueb.cf.schoolapp.repository.RegionRepository;
import gr.aueb.cf.schoolapp.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service                        // IoC Container
@RequiredArgsConstructor        // Dependency-Injection , δες σχόλια παρακάτω
@Slf4j                          // Logger
public class TeacherService implements ITeacherService {

    private final TeacherRepository teacherRepository;
    private final RegionRepository regionRepository;

    private final Mapper mapper;

    // Αν τα από πάνω είναι final τότε δίνοντας με Lombok @RequiredArgsConstructor Πάνω από την κλάση
    // Τότε δεν χρειάζεται να δώσουμε τον παρακάτω constructor για dependency injection

//    @Autowired
//    public TeacherService(TeacherRepository teacherRepository, RegionRepository regionRepository) {
//        this.teacherRepository = teacherRepository;
//        this.regionRepository = regionRepository;
//    }
//    To mapper το χρειαστήκαμε και το προσθέσαμε στην πορεία

    @Override
    @Transactional(rollbackFor = {EntityAlreadyExistsException.class, EntityInvalidArgumentException.class} )           // Όλες οι πράξεις πρέπει να πραγματοποιούνται μέσα σε transactions
    public TeacherReadOnlyDTO saveTeacher(TeacherInsertDTO dto)
            throws EntityAlreadyExistsException, EntityInvalidArgumentException {

        try {

            if (dto.vat() != null && teacherRepository.findByVat(dto.vat()).isPresent()) {
                throw new EntityAlreadyExistsException("Teacher with vat=" + dto.vat() + " already exists");
            }

            Region region = regionRepository.findById(dto.regionId()).
                    orElseThrow(() -> new EntityInvalidArgumentException("Region id=" + dto.regionId() + " invalid" ));

            // map σε entity
            Teacher teacher = mapper.mapToTeacherEntity(dto);

            region.addTeacher(teacher);             // η convenient method (θυμήσου ότι αντιστοιχεί και τις 2 πλευρές)

            teacherRepository.save(teacher);        // το teacher αλλάζει και γίνεται updated με αυτόν που έρχεται από την DB
                                                    // Οπότε έχει πάρει και id
            log.info("Teacher with vat={} saved successfully", dto.vat());          // success logging

            return mapper.mapToTeacherReadOnlyDTO(teacher);

        } catch (EntityAlreadyExistsException e) {
            log.error("Save failed for teacher with vat={}. Teacher already exists", dto.vat());       // Structured Logging
            throw e;
        } catch (EntityInvalidArgumentException e) {
            log.error("Save failed for teacher with vat={}. Region id={} invalid", dto.vat(), dto.regionId());
            throw e;
        }
        // ότι rollback χρειάζεται να γίνει, θα γίνει μέσα στο catch επειδή έχουμε ορίσει transaction και rollback policy
    }

}
