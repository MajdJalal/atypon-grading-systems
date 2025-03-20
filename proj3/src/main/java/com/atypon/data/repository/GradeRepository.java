package com.atypon.data.repository;

import com.atypon.data.entity.Grade;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Long> {

//    @PersistenceContext
//    private EntityManager entityManager;
//
//    public List<Grade> findAll() {
//        return entityManager.createQuery("SELECT sc FROM Grade sc", Grade.class).getResultList();
//    }
//
//    public void save(Grade grade) {
//        entityManager.persist(grade);
//    }
}