package com.fatih.KnitShop.entity;

import jakarta.persistence.*;

import java.util.List;

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

    @Column(name = "category_name", unique = true, nullable = false, length = 20)
    private String categoryName;

    //TODO: Relations

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostEntity> posts;
}

