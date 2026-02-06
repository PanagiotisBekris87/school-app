package gr.aueb.cf.schoolapp.controller;

import gr.aueb.cf.schoolapp.dto.RegionReadOnlyDTO;
import gr.aueb.cf.schoolapp.dto.TeacherInsertDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/teachers")
public class TeacherController {


    @GetMapping("/insert")
    public String getTeacherForm(Model model) {
        // στέλνει ένα άδειο teacherInsertDTO για να γίνει η εισαγωγή
        model.addAttribute("teacherInsertDTO", TeacherInsertDTO.empty());
        // model.addAttribute("regionsReadOnlyDTO", regions());   Υπονοείται από το ModelAttribute παρακάτω ***
        return "teacher-insert";
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
