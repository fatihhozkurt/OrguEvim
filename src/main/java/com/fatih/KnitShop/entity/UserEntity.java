package com.fatih.KnitShop.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fatih.KnitShop.entity.listeners.UserListener;
import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLRestriction;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(name = "users")
@SQLRestriction("record_status <> 'true'")
@EntityListeners(UserListener.class)
public class UserEntity extends BaseEntity {

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "surname", nullable = false, length = 50)
    private String surname;

    @Column(name = "username", nullable = false, length = 33)
    private String username;

    @Column(name = "password", nullable = false, length = 16)
    private String password;

    @Column(name = "email", nullable = false, length = 345)
    private String email;

    @Column(name = "biography", columnDefinition = "TEXT", length = 500)
    private String biography;

    @PositiveOrZero
    @Column(name = "followers_count", nullable = false)
    private Long followersCount;

    @PositiveOrZero
    @Column(name = "following_count", nullable = false)
    private Long followingCount;

    @PositiveOrZero
    @Column(name = "post_count", nullable = false)
    private Long postCount;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_follow",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "follower_id", referencedColumnName = "id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "follower_id"}))
    @JsonManagedReference
    private List<UserEntity> followers;

    @ManyToMany(mappedBy = "followers", fetch = FetchType.LAZY)
    @JsonBackReference
    private List<UserEntity> following;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonBackReference
    private List<CommentEntity> comments;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonBackReference
    private List<LikeEntity> likes;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonBackReference
    private List<PostEntity> posts;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "avatar_id", referencedColumnName = "id")
    @JsonManagedReference
    private ImageEntity avatarImage;
}