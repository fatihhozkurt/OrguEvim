    package com.fatih.KnitShop.entity;

    import com.fasterxml.jackson.annotation.JsonBackReference;
    import com.fasterxml.jackson.annotation.JsonManagedReference;
    import com.fatih.KnitShop.entity.listeners.CommentListener;
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
    @AllArgsConstructor
    @NoArgsConstructor
    @SuperBuilder
    @Table(name = "comments")
    @SQLRestriction("record_status <> 'true'")
    @EntityListeners(CommentListener.class)
    public class CommentEntity extends BaseEntity {

        @Column(name = "content", length = 2200, nullable = false, columnDefinition = "TEXT")
        private String content;

        @PositiveOrZero
        @Column(name = "reply_count", nullable = false)
        private Long replyCount;

        @PositiveOrZero
        @Column(name = "like_count", nullable = false)
        private Long likeCount;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
        @JsonManagedReference
        private UserEntity user;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "post_id", referencedColumnName = "id")
        @JsonManagedReference
        private PostEntity post;

        @OneToMany(mappedBy = "comment", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
        @JsonBackReference
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