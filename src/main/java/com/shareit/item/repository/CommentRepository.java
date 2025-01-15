package com.shareit.item.repository;

import com.shareit.item.model.Comment;
import com.shareit.item.model.Item;
import com.shareit.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    Comment findFirstCommentByAuthorAndItem(User user, Item item);

}
