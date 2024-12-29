package com.fatih.KnitShop.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.List;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(name = "categories")
@SQLRestriction("record_status <> '1'")
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

