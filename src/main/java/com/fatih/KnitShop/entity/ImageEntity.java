package com.fatih.KnitShop.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(name = "images")
public class ImageEntity extends BaseEntity {

    @Column(name = "image_path", nullable = false, length = 500)
    private String imagePath;

    @Column(name = "image_count", nullable = false)
    private int imageCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", referencedColumnName = "id", nullable = false)
    @JsonManagedReference
    private PostEntity post;

    @OneToOne(mappedBy = "avatarImage")
    @JsonBackReference
    private UserEntity user;

}
