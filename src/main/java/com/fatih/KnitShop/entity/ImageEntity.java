package com.fatih.KnitShop.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fatih.KnitShop.entity.listeners.ImageListener;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(name = "images")
@SQLRestriction("record_status <> 'true'")
@EntityListeners(ImageListener.class)
public class ImageEntity extends BaseEntity {

    @Column(name = "image_path", nullable = false, length = 500)
    private String imagePath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", referencedColumnName = "id")
    @JsonManagedReference
    private PostEntity post;

    @OneToOne(mappedBy = "avatarImage")
    @JsonBackReference
    private UserEntity user;

    @OneToOne(mappedBy = "coverImage")
    @JsonBackReference
    private PostEntity postCover;
}
