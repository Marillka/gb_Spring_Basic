package com.rayumov.services;

import com.rayumov.entities.Student;
import com.rayumov.exceptions.ResourceNotFoundException;
import com.rayumov.repositories.StudentRepository;
import com.rayumov.specifications.StudentsSpecifications;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


@Service
public class StudentService {

    private StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    /*
    List - реализация обычного списка.
    Page - список объектов, только еще с доп. информацией. (чуть чуть продвинутый List со смежной информацией).
    В объекте типа Page хранится список объектов, номер страницы, сколько всего страниц есть, есть ли следующая страница, если предыдущая страница.
     */
    public Page<Student> find(Integer minScore, Integer maxScore, String partName, Integer page) {
        // нужно собрать спецификацию. Есть много маленьких правил, мы хотим собрать итоговое правило.
        // Specification.where(null) - означает, что спецификация ничего не проверяет.
        Specification<Student> spec = Specification.where(null);
        // пустой spec выдаст запрос: select s from Student s where true

        // если задали minScore, то добавляем это условие к спецификации.
        if (minScore != null) {
            spec = spec.and(StudentsSpecifications.scoreGreaterOrEqualsThan(minScore));
            // select s from Student s where true AND s.score > minScore
        }
        if (maxScore != null) {
            spec = spec.and(StudentsSpecifications.scoreLessOrEqualsThan(maxScore));
            // select s from Student s where true AND s.score > minScore AND s.score < maxScore
        }
        if (partName != null) {
            spec = spec.and(StudentsSpecifications.nameLike(partName));
            // select s from Student s where true AND s.score > minScore AND s.score < maxScore AND s.name LIKE %partName%
        }

        // теперь у studentRepository появился метод с новой сигнатурой.
        // на вход принимает спецификацию и Pageable - пагинацию
        // устанавливаем страницу (с фронта номер страницы прилетает = 1, размер страницы 5).
        return studentRepository.findAll(spec, PageRequest.of(page - 1, 5));
    }

    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    public Optional<Student> findById(Long id) {
        return studentRepository.findById(id);
    }

    public void deleteById(Long id) {
        studentRepository.deleteById(id);
    }


    @Transactional
    public void changeScore(Long studentId, Integer delta) {
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new ResourceNotFoundException("Unable to change student's score. Student not found, id: " + studentId));
        student.setScore(student.getScore() + delta);
    }

    public List<Student> findStudentsByScoreBetween(Integer min, Integer max) {
        return studentRepository.findAllByScoreBetween(min, max);
    }

    public Student save(Student student) {
        return studentRepository.save(student);
    }
}
