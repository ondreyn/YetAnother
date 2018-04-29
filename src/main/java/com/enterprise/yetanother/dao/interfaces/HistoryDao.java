package com.enterprise.yetanother.dao.interfaces;

import com.enterprise.yetanother.entities.History;
//import com.enterprise.yetanother.entities.History;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *@author andrey
 */
@Repository
public interface HistoryDao {

    void create(History history);
    List<History> findByTicket(Long ticketId, Integer quantity);
    void persist(History history);
    void update(History history);
    void saveOrUpdate(History history);
}
