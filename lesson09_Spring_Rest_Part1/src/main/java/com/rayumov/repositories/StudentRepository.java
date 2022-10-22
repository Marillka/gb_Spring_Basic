package com.rayumov.repositories;


import com.rayumov.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


// JpaSpecificationExecutor<Student> - добавили поддержку спецификаций
@Repository
public interface StudentRepository extends JpaRepository<Student, Long>, JpaSpecificationExecutor<Student> {

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

}

