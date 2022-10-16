package com.rayumov.controllers;


import com.rayumov.entities.Student;
import com.rayumov.exceptions.ResourceNotFoundException;
import com.rayumov.repositories.StudentRepository;
import com.rayumov.services.StudentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class StudentController {

    private StudentService studentService;

    private StudentRepository studentRepository; // TODO Убрать это отсюда! Так делать нельзя

    public StudentController(StudentService studentService, StudentRepository studentRepository) {
        this.studentService = studentService;
        this.studentRepository = studentRepository;
    }

//    public StudentController(StudentService studentService) {
//        this.studentService = studentService;
//    }

    @GetMapping("/students")
    public List<Student> getAllStudents() {
        return studentService.findAll();
    }

    @GetMapping("/demo")
    public Object demoMethod() {
        return studentRepository.nativeSqlGetScoreByName("Bob");
    }

    /*
     ResponseEntity<?> - это с одной стороны - управление ответом, а с другой - управление телом ответа.
     То есть в ответ можно запихнуть какие то данные (в тело ответа), так и сказать что статус ответа должен быть такой то, хедеры или футеры должны быть такие то.
     */
//    @GetMapping("/students/{id}")
//    public ResponseEntity<?> getStudentById(@PathVariable Long id) {
//        Optional<Student> student = studentService.findById(id);
//        if (student.isPresent()) {
//            return new ResponseEntity<>(student.get(), HttpStatus.OK);//Студент найден. 200.
//        }
//        //404. Не найден. Сервер не может найти запрашиваемый ресурс.
////        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        return new ResponseEntity<>(new AppError(HttpStatus.NOT_FOUND.value(), "Student not found, id: " + id), HttpStatus.NOT_FOUND);
//    }

    @GetMapping("/students/{id}")
    public Student getStudentById(@PathVariable Long id) {
        return studentService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Student not found, id: " + id));
    }

    @GetMapping("/students/delete/{id}")
    public void deleteById(@PathVariable Long id) {
        studentService.deleteById(id);
    }

    @GetMapping("/students/score/change_score")
    public void changeScore(@RequestParam Long studentId, @RequestParam Integer delta) {
        studentService.changeScore(studentId, delta);
    }

    @GetMapping("/students/score_between")
    public List<Student> findStudentsByScoreBetween(@RequestParam(defaultValue = "0") Integer min, @RequestParam(defaultValue = "100") Integer max) {
        return studentService.findStudentsByScoreBetween(min, max);
    }


}
