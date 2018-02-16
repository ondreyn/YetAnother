package com.enterprise.yetanother.services.interfaces;

import com.enterprise.yetanother.entities.Comment;

import java.util.List;

/**
 *@author andrey
 */
public interface CommentsService {

    List<Comment> getCompleteComments(Long ticket_id);
    List<Comment> getLatestComments(Long ticket_id);
    void addComment(Comment comment);
    void create(Comment comment);
}
