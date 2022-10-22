package com.rayumov.dto;


import com.rayumov.entities.Student;

// dto - data transfer object - объект для передачи данных
// в dto есть только те поля, которые мы хотим передать клиенту на фронт.
public class StudentDto {

    private Long id;
    private String name;
    private Integer score;


    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public StudentDto(Student student) {
        this.name = student.getName();
        this.id = student.getId();
        this.score = student.getScore();
    }

    public StudentDto() {
    }

}


