package com.fatih.KnitShop.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.List;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(name = "categories")
public class CategoryEntity extends BaseEntity {

    @Column(name = "category_name", updatable = false, unique = true, nullable = false, length = 20)
    private String categoryName;

    @PositiveOrZero
    @Column(name = "post_count", nullable = false)
    private Long postCount;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private List<PostEntity> posts;
}

