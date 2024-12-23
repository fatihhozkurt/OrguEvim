package com.fatih.KnitShop.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Table(name = "comments")
public class CommentEntity extends BaseEntity {

    @Column(name = "content", length = 2200, nullable = false, columnDefinition = "TEXT")
    private String content;

    //TODO: Relations

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", referencedColumnName = "id", nullable = false)
    private PostEntity post;

    @OneToMany(mappedBy = "comment", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LikeEntity> likes;

    //Subcomment'lerin Comment'i
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supcomment_id", referencedColumnName = "id")
    @JsonManagedReference
    private CommentEntity supComment;

    //Comment'in subcomment'leri
    @OneToMany(mappedBy = "supComment", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private List<CommentEntity> subComments;
}