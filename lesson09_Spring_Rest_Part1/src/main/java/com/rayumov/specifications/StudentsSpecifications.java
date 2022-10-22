package com.rayumov.specifications;


import com.rayumov.entities.Student;
import org.springframework.data.jpa.domain.Specification;


// Спецификация - это некий критерий поиска
public class StudentsSpecifications {

    // метод, которые подготовит некое правило.
    /*
    Есть объект criteriaBuilder - он позволяет строить различные критерии. Что это такое?
    Вы можете выполнять те же операции, что и в SQL (что то должно быть больше чего то, что то должно быть меньше чего то, что то должно быть между чем то и т.д.).
    Говорим: что то должно быть больше или равно чему то.
    У нас есть root - это корневой объект (объект того типа, для которого мы строим спецификацию (Student)). Знаем, что у студента есть поле score.
    greaterThanOrEqualTo(root.get("score"), score) - хотим, чтобы "score" студента был больше или равен score, который мы передаем.
     */
    public static Specification<Student> scoreGreaterOrEqualsThan(Integer score) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("score"), score);
    }

    // меньше или равно
    public static Specification<Student> scoreLessOrEqualsThan(Integer score) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("score"), score);
    }

    /*
    допустим передаем Bob
    "%%%s%%" = %Bob%
    select s from Student s where s.name like %Bob%
    %% - экранированный процент
    %s - какой то элемент (строка)
     */
    public static Specification<Student> nameLike(String namePart) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("name"), String.format("%%%s%%", namePart));
    }

}
