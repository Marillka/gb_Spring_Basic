package com.rayumov.repositories;


import com.rayumov.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


/*
Наследуюемся от JpaRepository, который является обобщенным интерфейсом.
И указываем с какой сущностью хотим работать, и какого типа у сущности PrimaryKey.

 */
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    // @Query("select s from Student s where s.score between ?1 and ?2)
    List<Student> findAllByScoreBetween(Integer min, Integer max);

    // @Query ("select s from Student s where s.name = :name)
    Optional<Student> findByName(String name);

    @Query("select s from Student s where s.score < 20")
    List<Student> findAllLowRatingStudents();

    @Query("select s.score from Student s where s.name = ?1")
    Integer hqlGetScoreByName(String name);

    @Query(value = "select score from students where name = :name", nativeQuery = true)
    Integer nativeSqlGetScoreByName(String name);

    // Если бы у студентов был List<Book>, то не ленивая загрузка книг:
    // fetch - значит, что я хочу достать студента и вместе с ним сразу загрузить его books
    //  @Query("select s.score from Student s join fetch s.books where s.id = :id")
    // Optional<Student> findByIdWithBooks(Integer id);

}

