package gr.aueb.cf.schoolapp.dto;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record TeacherInsertDTO(

        // Προσοχή είμαστε ακόμη μέσα στις παρενθέσεις

        @NotNull(message = "Το όνομα δεν μπορεί να είναι κενό")
        @Size(min = 2, message = "Το όνομα πρέπει να περιέχει τουλάχιστον 2 χαρακτήρες")
        String firstname,

        @NotNull(message = "Το επώνυμο δεν μπορεί να είναι κενό")
        @Size(min = 2, message = "Το επώνυμο πρέπει να περιέχει τουλάχιστον 2 χαρακτήρες")
        String lastname,

        @Pattern(regexp = "\\d{9,}", message = "Το ΑΦΜ δεν μπορεί να είναι μικρότερο από 9 ψηφία")   // κανονικά θα πρέπει να το αφήνουμε ακριβώς 9. εδώ το κάνουμε {9,} για ευελιξία
        String vat,

        @NotNull(message = "Η περιοχή δεν μπορεί να είναι κενή.")
        Long regionId
) {

    //static factory για να δίνουμε ένα κενό instance στην αρχή
    public static TeacherInsertDTO empty() {
        return new TeacherInsertDTO("","","", 0L);
    }
}
