package com.enterprise.yetanother.services.implementations;

import com.enterprise.yetanother.dao.interfaces.CommentDao;
import com.enterprise.yetanother.entities.Comment;
import com.enterprise.yetanother.init.Properties;
import com.enterprise.yetanother.services.interfaces.CommentsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *@author andrey
 */
@Service
public class CommentsServiceImpl implements CommentsService {

    final static Logger LOGGER = LoggerFactory
                                 .getLogger(CommentsServiceImpl.class);

    @Autowired
    private CommentDao commentDao;

    @Override
    public List<Comment> getCompleteComments(Long ticket_id) {
        if (ticket_id != null) {
            return commentDao.findByTicket(ticket_id, 0);
        } else {
            return null;
        }
    }

    @Override
    public List<Comment> getLatestComments(Long ticket_id) {
        if (ticket_id != null) {
            return commentDao.findByTicket(ticket_id, Properties.COMMENTS_LENGTH);
        } else {
            return null;
        }
    }

    @Override
    public void addComment(Comment comment) {
        if (comment != null) {
            commentDao.create(comment);
        }
    }

    @Override
    public void create(Comment comment) {
        if (comment != null) {
            commentDao.create(comment);
        } else {
            LOGGER.warn("[create: comment is null!]");
        }
    }
}