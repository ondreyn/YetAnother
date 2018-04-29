package com.enterprise.yetanother.dao.interfaces;

import com.enterprise.yetanother.entities.Comment;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *@author andrey
 */
@Repository
public interface CommentDao {

    void create(Comment comment);
    List<Comment> findByTicket(Long ticketId, Integer quantity);
    void persist(Comment comment);
    void update(Comment comment);
    void saveOrUpdate(Comment comment);
}
