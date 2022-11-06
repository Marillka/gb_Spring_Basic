package context.homework.registry;

import context.homework.Patient;
import context.homework.card.CardsRepository;
import context.homework.doctor.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RegistryService {
    private DoctorRepository doctorRepository;

    private CardsRepository cardsRepository;

    @Autowired
    private void setDoctorRepository(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @Autowired
    private void setCardsRepository(CardsRepository cardsRepository) {
        this.cardsRepository = cardsRepository;
    }

    public void referPatientToDoctor(Patient patient) {
        patient.setCard(cardsRepository.findCardByPatientName(patient));
        patient.setDoctor(doctorRepository.findDoctorBySpecialization(patient));
    }

}
