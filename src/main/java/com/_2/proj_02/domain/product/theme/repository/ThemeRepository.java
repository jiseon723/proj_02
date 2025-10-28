package com._2.proj_02.domain.product.theme.repository;

import com._2.proj_02.domain.product.entity.Theme;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ThemeRepository extends JpaRepository<Theme, Long> {
    @Query("""
        select t
        from Theme t
        where t.active = true
        order by t.displayOrder asc, t.id desc
    """)
    List<Theme> findActiveThemes(Pageable pageable);

    boolean existsByCode(String code);

    Optional<Theme> findByCode(String code);
}
