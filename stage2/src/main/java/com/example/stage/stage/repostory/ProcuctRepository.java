package com.example.stage.stage.repostory;

import com.example.stage.stage.entity.Medicaments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProcuctRepository extends JpaRepository<Medicaments,Long> {

    List<Medicaments> findAllByNameContaining(String name);

//    @Query("SELECT p FROM Medicaments p WHERE p.category.id = :categoryId " +
//            "OR p.category.id IN (SELECT c.id FROM Category c WHERE c.parentCategory.id = :categoryId) " +
//            "OR p.category.id IN (SELECT sc.id FROM Category sc WHERE sc.parentCategory.id IN " +
//            "(SELECT c.id FROM Category c WHERE c.parentCategory.id = :categoryId))")
//    List<Medicaments> findByCategoryAndSubcategories(@Param("categoryId") Long categoryId);
}
