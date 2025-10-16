package com._2.proj_02.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Entity
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userAddressId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Long userId;

    @Column(nullable = false, length = 50, name = "recipient_name")
    private String recipientName;

    @Column(nullable = false, length = 10)
    private String baseAddress;

    @Column(nullable = false)
    private String detailAddress;

    @Column(nullable = false)
    private String zipcode;

    @Builder.Default
    private Boolean isDefault = false;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column
    private LocalDateTime updatedAt;
}
