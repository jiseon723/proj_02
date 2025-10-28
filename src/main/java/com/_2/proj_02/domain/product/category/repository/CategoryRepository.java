package com._2.proj_02.domain.product.category.repository;

import com._2.proj_02.domain.product.entity.Category;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("""
    select c
    from Category c
    where c.active = true
    order by c.displayOrder asc, c.id desc
    """)
    List<Category> findActiveCategories(Pageable pageable);


    Optional<Category> findByCode(String code);

    List<Category> findByActiveTrueOrderByDisplayOrderAsc(PageRequest of);

    boolean existsByCode(String code);


}
