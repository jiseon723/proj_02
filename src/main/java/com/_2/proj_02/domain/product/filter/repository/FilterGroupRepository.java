package com._2.proj_02.domain.product.filter.repository;

import com._2.proj_02.domain.product.entity.FilterGroup;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FilterGroupRepository extends JpaRepository<FilterGroup, Long> {

    Optional<FilterGroup> findByCategory_CodeAndCode(String categoryCode, String groupCode);

    List<FilterGroup> findAllByCategory_IdAndActiveTrueOrderByDisplayOrderAscIdAsc(Long categoryId, PageRequest of);
}
