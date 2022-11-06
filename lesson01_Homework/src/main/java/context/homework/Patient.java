package context.homework;

import context.homework.card.Card;
import context.homework.doctor.Doctor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class Patient {

    private String name;
    private Card card;
    private Doctor doctor;
    private String doctorSpecialization;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public String getDoctorSpecialization() {
        return doctorSpecialization;
    }

    public void setDoctorSpecialization(String doctorSpecialization) {
        this.doctorSpecialization = doctorSpecialization;
    }

    public Patient() {

    }

    @Override
    public String toString() {
        return "Patient{" +
                "name='" + name + '\'' +
                ", cardId=" + card.getId() +
                ", doctor=" + doctor.getName() +
                ", doctorOffice=" + doctor.getOffice() +
                ", doctorSpecialization='" + doctorSpecialization + '\'' +
                '}';
    }
}
