package gr.aueb.cf.schoolapp.mapper;

import gr.aueb.cf.schoolapp.dto.TeacherInsertDTO;
import gr.aueb.cf.schoolapp.dto.TeacherReadOnlyDTO;
import gr.aueb.cf.schoolapp.model.Teacher;
import org.springframework.stereotype.Component;

@Component
public class Mapper {

    public Teacher mapToTeacherEntity(TeacherInsertDTO teacherInsertDTO) {
        return new Teacher(null, null, teacherInsertDTO.vat(), teacherInsertDTO.firstname(), teacherInsertDTO.lastname(), null);

        // η σειρά των πεδίων είναι id, uuid, vat, firstname, lastname, region (ctrl+p)
        // δίνουμε null id γιατί θα το πάρει από την DB
        // δίνουμε null uuid γιατί το παίρνει στο pre-persist
        // δίνουμε null region γιατί θα το πάρουμε από το id που έχει έρθει από το dto
    }

    public TeacherReadOnlyDTO mapToTeacherReadOnlyDTO(Teacher teacher) {
        return new TeacherReadOnlyDTO(teacher.getUuid().toString(), teacher.getFirstname(), teacher.getLastname());
    }
}
