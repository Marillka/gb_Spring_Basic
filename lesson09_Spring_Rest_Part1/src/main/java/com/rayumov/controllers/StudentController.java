package com.rayumov.controllers;


import com.rayumov.dto.StudentDto;
import com.rayumov.entities.Student;
import com.rayumov.exceptions.ResourceNotFoundException;
import com.rayumov.services.StudentService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
Что такое Rest сервисы?
Это Web сервисы, которые обрабатывают какие то данные и работают по определенному набору правил.
Если вы пишите Rest сервис, то вы работаете с какими то ресурмами. Ресурсами может называться все что угодно. В случае с нашим проектом - это студенты.
И мы со стороны клиента как то манипулируем этими ресурсами. Мы используем некоторый набор запросов. Rest API работает поверх протокола Http и мы используем 4 возможных http метода (GET, POST, PUT, DELETE).
Мы посылаем запросы на некоторые END поинты, то есть на некоторые адреса в рамках нашего приложения.
Мы можем послать GET запрос по адресу /students. В Rest API приянято имя ресурса писать во множественном числе.
Если посылаем GET запрос - то мы должны получить список этих объектов (студентов).
Если послать запрос на students/{id} и указать какой нибудь id, то должна прийти информация об одном объекте.
Если посылаем POST запрос по адресу /students, то ожидаю что я создам новый ресурс, то есть создан на бекенде нового студента и положу его в БД. POST - это создание чего либо.
Если на бекенде есть какой то ресурс, и я хочу модифицировать или изменить что то внем (имя, балл и т.д) - то нужно посылать PUT запрос на /students.
DELETE на /students - то я хочу удалить всех студентов. Если на /students/{id} - то я хочу удалить кокретного студента.
В Rest API в end поинтах не принято писать глаголы (/getStudentsInfo, /createStudent)
В PUT запросе в теле запроса будет объект и его id.

Еще иногда используют PATCH запрос. Дело в том, что в PUT вы должны послать объект целиком.
А бывает что нужно изменить только одно поле объекта. PATCH на /students/score.

Поскольку основным данными для передачи является JSON, поэтому REST API написанный на Java может спокойно общаться с REST на пайтони C# и т.д.

При разработке Web сервисов стараютс я закладывать версии REST API. Перед адресом (/students) добавляют припискую
/api/v1/students
/api/v2/students

v1 - никакой программной нагрузки не несет.Просто endpoint.
 */
@RestController
@RequestMapping("/api/v1/students")
public class StudentController {
    private StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public Page<StudentDto> getAllStudents(
            @RequestParam(name = "p", defaultValue = "1") Integer page,
            @RequestParam(name = "min_score", required = false) Integer minScore,
            @RequestParam(name = "max_score", required = false) Integer maxScore,
            @RequestParam(name = "name_part", required = false) String namePart
    ) {
        if (page < 1) {
            page = 1;
        }
        return studentService.find(minScore, maxScore, namePart, page).map(
                s -> new StudentDto(s)
        );
    }

    @GetMapping("/{id}")
    public Student getStudentById(@PathVariable Long id) {
        return studentService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Student not found, id: " + id));
    }

    @PostMapping
    public Student saveNewStudent(@RequestBody Student student) {
        // если в теле запроса придет студент с id, то мы в БД перезапишем существующего студента.
        // Что бы этого измежать можно у входящего студента занулить id
        student.setId(null);
        return studentService.save(student);
    }

    @PutMapping
    public Student updateStudent(@RequestBody Student student) {
        // В PUT присылают студента с id и мы его просто перезапишем.
        return studentService.save(student);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        studentService.deleteById(id);
    }


}
