package com.enterprise.yetanother.dao.interfaces;

import com.enterprise.yetanother.entities.Feedback;
//import com.enterprise.yetanother.entities.Feedback;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *@author andrey
 */
@Repository
public interface FeedbackDao {

    void create(Feedback feedback);
    Feedback findByTicket(Long ticketId);
    void persist(Feedback feedback);
    void update(Feedback feedback);
    void saveOrUpdate(Feedback feedback);
    List<Feedback> findAllTickets();
}
