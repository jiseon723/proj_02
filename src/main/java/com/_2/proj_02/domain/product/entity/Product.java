package com._2.proj_02.domain.product.entity;

import com.gobang.gobang.domain.product.common.ProductStatus;
import com.gobang.gobang.global.jpa.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
@Table(name = "product",
        uniqueConstraints = @UniqueConstraint(name = "uk_product_slug", columnNames = {"slug"}),
        indexes = {
//                @Index(name = "idx_product_subcategory", columnList = "subcategory_id"),
//                @Index(name = "idx_product_status", columnList = "status"),
                @Index(name = "idx_product_active_stock", columnList = "stock_quantity")
        })
public class Product extends BaseEntity {
//    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;


    @Column(length = 200, nullable = false)
    private String name;


    @Column(length = 200, nullable = false)
    private String slug;


    @Column(name = "base_price", nullable = false)
    private Integer basePrice = 0;


    @Column(name = "stock_quantity", nullable = false)
    private Integer stockQuantity = 0;


    @Column(name = "backorderable", nullable = false)
    private Boolean backorderable = false;


//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "subcategory_id")
//    private Subcategory subcategory;
//
//
    @Enumerated(EnumType.STRING)
    @Column(length = 16, nullable = false)
    private ProductStatus status = ProductStatus.DRAFT;


    @Column(name = "seo_title", length = 150)
    private String seoTitle;
    @Column(name = "seo_description", length = 255)
    private String seoDescription;


//    @CreationTimestamp
//    @Column(name = "created_at", updatable = false)
//    private LocalDateTime createdAt;
//
//
//    @UpdateTimestamp
//    @Column(name = "updated_at")
//    private LocalDateTime updatedAt;


    // 소유측(owning side): 여기에 @JoinTable
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "product_theme_map",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "theme_id"),
            uniqueConstraints = @UniqueConstraint(
                    name = "uk_product_theme", columnNames = {"product_id", "theme_id"}
            )
    )
    private Set<Theme> themes = new LinkedHashSet<>();
}
