package com.vettica.gestioncursos.repository;

import com.vettica.gestioncursos.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {



}
