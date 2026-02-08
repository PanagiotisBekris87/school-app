package gr.aueb.cf.schoolapp.controller;

import gr.aueb.cf.schoolapp.dto.RegionReadOnlyDTO;
import gr.aueb.cf.schoolapp.dto.TeacherInsertDTO;
import gr.aueb.cf.schoolapp.dto.TeacherReadOnlyDTO;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/teachers")
public class TeacherController {


    @GetMapping("/insert")
    public String getTeacherForm(Model model) {
        // στέλνει ένα άδειο teacherInsertDTO για να γίνει η εισαγωγή
        model.addAttribute("teacherInsertDTO", TeacherInsertDTO.empty());
        // model.addAttribute("regionsReadOnlyDTO", regions());   Υπονοείται από το ModelAttribute παρακάτω στα regions ***
        return "teacher-insert";
    }


    @PostMapping("/insert")
    public String teacherInsert(@Valid @ModelAttribute("teacherInsertDTO") TeacherInsertDTO teacherInsertDTO,
                                BindingResult bindingResult, Model model,
                                RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            // model.addAttribute("regionsReadOnlyDTO", regions());   Υπονοείται από το ModelAttribute παρακάτω στα regions ***
            return "teacher-insert";
        }

        // save teacher to DB , και επιστρέφουμε μία σελίδα που λέει success για την αποθήκευση
        TeacherReadOnlyDTO teacherReadOnlyDTO = new TeacherReadOnlyDTO("asdasd-1234", "Αθανάσιος", "Ανδρούτσος");       //dummy data
        // Είναι σαν κάτι που θα μας επέστρεφε η βάση

//        model.addAttribute("teacherReadOnlyDTO", teacherReadOnlyDTO);         Αυτό είναι λάθος γιατί θα υπήρχαν προβλήματα
//        return "teacher-success";                                             ασφαλείας με back και refresh(επανάληψη της πράξης π.χ insert ή πληρωμή)

        // οπότε μετά το post κάνουμε το redirect και ανοίγει νέο session , νέα σύνδεση χωρίς να κρατάει ιστορικότητα

        // PRG - Post-Redirect-Get
        redirectAttributes.addFlashAttribute("teacherReadOnlyDTO", teacherReadOnlyDTO);
        return "redirect:/teachers/success";
    }

    @GetMapping("/success")
    public String teacherSuccess(Model model) {
        return "teacher-success";
    }

    @ModelAttribute("regionsReadOnlyDTO")           //*** Εκτελείται πριν από κάθε request handler
    public List<RegionReadOnlyDTO> regions() {
        return List.of(
                new RegionReadOnlyDTO(1L, "Αθήνα"),                 // Dummy Data
                new RegionReadOnlyDTO(2L, "Βόλος"),                 // Προς το παρόν
                new RegionReadOnlyDTO(3L, "Θεσσαλονίκη")
                );
    }

}
