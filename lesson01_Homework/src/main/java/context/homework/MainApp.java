package context.homework;

import context.homework.config.AppConfig;
import context.homework.registry.RegistryService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


/*
Создать модель Пациент - поликлиника (используя Spring). (выбрать любой способ конфигурации)
 */

public class MainApp {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        RegistryService registryService = context.getBean(RegistryService.class);

        Patient patient1 = context.getBean(Patient.class);
        Patient patient2 = context.getBean(Patient.class);
        Patient patient3 = context.getBean(Patient.class);

        // создаем пациентов, выбираем какой доктор им нужен.
        patient1.setName("Alex");
        patient1.setDoctorSpecialization("allergist");

        patient2.setName("Bob");
        patient2.setDoctorSpecialization("dermatologist");

        patient3.setName("John");
        patient3.setDoctorSpecialization("cardiologist");

        /*
        Отдаем пациентов в регистратуру, она выдает им личную карту и говорит в каком кабинете находиться доктор.
         */
        registryService.referPatientToDoctor(patient1);
        registryService.referPatientToDoctor(patient2);
        registryService.referPatientToDoctor(patient3);

        System.out.println(patient1);
        System.out.println(patient2);
        System.out.println(patient3);


        context.close();

    }
}
