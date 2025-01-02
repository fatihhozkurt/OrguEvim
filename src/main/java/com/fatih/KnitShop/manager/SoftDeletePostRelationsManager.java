package com.fatih.KnitShop.manager;

import com.fatih.KnitShop.entity.PostEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static com.fatih.KnitShop.url.RecordStatus.PASSIVE;

@Service
@RequiredArgsConstructor
public class SoftDeletePostRelationsManager {

    //Checked
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public void softDeletePostRelations(PostEntity post) {
        post.getLikes().forEach(like -> like.setRecordStatus(PASSIVE));
        post.getComments().forEach(comment -> comment.setRecordStatus(PASSIVE));
        post.getImages().forEach(image -> image.setRecordStatus(PASSIVE));
        post.getCoverImage().setRecordStatus(PASSIVE);
        post.setRecordStatus(PASSIVE);
    }
}
