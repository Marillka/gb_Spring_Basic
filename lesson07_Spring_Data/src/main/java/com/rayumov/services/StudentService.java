package com.rayumov.services;

import com.rayumov.entities.Student;
import com.rayumov.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import com.rayumov.repositories.StudentRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


@Service
public class StudentService {

    private StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    /*
    Почему из репозитория возвращается Optional и мы сами возвращаем Optional из сервиса?
    Потому что не факт что в репозитории есть сущность с таким Id. Не очень хорошо, чтобы репозиторий в чистом виде вернул null.
     */
    public Optional<Student> findById(Long id) {
        return studentRepository.findById(id);
    }


    public void deleteById(Long id) {
        studentRepository.deleteById(id);
    }

    // @Transactional - при запуске метода открывает транзакцию. Когда метод заканчивается успешно, то транзакция комитится. Если вылетит Exception - то сделается откат (Rollback). Благодаря ей убираем послдеюнюю строчку save(student).
    @Transactional
    public void changeScore(Long studentId, Integer delta) {
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new ResourceNotFoundException("Unable to change student's score. Student not found, id: " + studentId));
        student.setScore(student.getScore() + delta);
//        studentRepository.save(student);
    }


    public List<Student> findStudentsByScoreBetween(Integer min, Integer max) {
        return studentRepository.findAllByScoreBetween(min, max);
    }
}
