package com.fatih.KnitShop.manager;

import com.fatih.KnitShop.entity.CommentEntity;
import com.fatih.KnitShop.entity.PostEntity;
import com.fatih.KnitShop.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.fatih.KnitShop.url.RecordStatus.PASSIVE;

@Service
@RequiredArgsConstructor
public class SoftDeletePost {

    private final PostRepository postRepository;

    //Checked
    @Transactional
    public void softDeletePost(PostEntity post) {
        if (post.getLikes() != null) {
            post.getLikes().forEach(like -> like.setRecordStatus(PASSIVE));
        }
        if (post.getComments() != null) {
            post.getComments().forEach(this::softDeleteComment); // Yorumları pasifleştir
        }
        if (post.getImages() != null) {
            post.getImages().forEach(image -> image.setRecordStatus(PASSIVE));
        }
        if (post.getCoverImage() != null) {
            post.getCoverImage().setRecordStatus(PASSIVE);
        }
        post.getUser().setPostCount(post.getUser().getPostCount() - 1);
        post.getCategory().setPostCount(post.getCategory().getPostCount() - 1);
        post.setRecordStatus(PASSIVE);
        postRepository.save(post);
    }

    private void softDeleteComment(CommentEntity comment) {
        if (comment.getLikes() != null) {
            comment.getLikes().forEach(like -> like.setRecordStatus(PASSIVE));
        }
        if (comment.getSubComments() != null) {
            comment.getSubComments().forEach(this::softDeleteComment);
        }
        comment.setRecordStatus(PASSIVE);
    }

}
