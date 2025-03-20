package com.atypon.data.repository;

import com.atypon.data.entity.Course;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

//    @PersistenceContext
//    private EntityManager entityManager;
//
//    public List<Course> findAll() {
//        return entityManager.createQuery("SELECT c FROM courses c", Course.class).getResultList();
//    }
//
//    public Course findById(Long id) {
//        return entityManager.find(Course.class, id);
//    }
//
//    public void save(Course course) {
//        entityManager.persist(course);
//    }
}
