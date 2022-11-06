package context.homework.doctor;

import context.homework.Patient;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class DoctorRepository {

    private List<Doctor> doctors;

    @PostConstruct
    public void init() {
        doctors = new ArrayList<>(Arrays.asList(
                new Doctor("Christopher Miller", "allergist", 111),
                new Doctor("Max Thompson", "cardiologist", 222),
                new Doctor("Carl Johnson", "dermatologist", 333)
        ));
    }

    public Doctor findDoctorBySpecialization(Patient patient) {
        return doctors.stream().filter(p -> p.getSpecialization().equals(patient.getDoctorSpecialization())).findFirst().orElseThrow(RuntimeException::new);
    }

}
