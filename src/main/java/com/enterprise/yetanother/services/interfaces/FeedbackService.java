package com.enterprise.yetanother.services.interfaces;

import com.enterprise.yetanother.entities.Feedback;

import java.util.List;

/**
 *@author andrey
 */
public interface FeedbackService {

    void create(Feedback feedback);
    Feedback getFeedback(Long ticket_id);
    boolean doesExistFeedback(Long ticket_id);
    List<Feedback> getAllFeedback();
}
