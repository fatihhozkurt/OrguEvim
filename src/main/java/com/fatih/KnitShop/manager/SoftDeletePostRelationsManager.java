package com.fatih.KnitShop.manager;

import com.fatih.KnitShop.entity.PostEntity;
import com.fatih.KnitShop.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static com.fatih.KnitShop.url.RecordStatus.PASSIVE;

@Service
@RequiredArgsConstructor
public class SoftDeletePostRelationsManager {

    private final PostRepository postRepository;

    //Checked
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public void softDeletePostRelations(PostEntity post) {
        post.getLikes().forEach(like -> like.setRecordStatus(PASSIVE));
        post.getComments().forEach(comment -> comment.setRecordStatus(PASSIVE));
        post.getImages().forEach(image -> image.setRecordStatus(PASSIVE));
        post.getCoverImage().setRecordStatus(PASSIVE);
        post.getUser().setPostCount(post.getUser().getPostCount() - 1);
        post.getCategory().setPostCount(post.getCategory().getPostCount() - 1);
        post.setRecordStatus(PASSIVE);
        postRepository.save(post);
    }
}
