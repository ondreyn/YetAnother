package com.enterprise.yetanother.dto.history;

import com.enterprise.yetanother.entities.Ticket;
import com.enterprise.yetanother.entities.User;
import com.enterprise.yetanother.entities.Ticket;
import com.enterprise.yetanother.entities.User;

import java.util.Date;
import java.util.Objects;

/**
 *@author andrey
 */
public class HistoryDto {

    private Long id;
    private Ticket ticket;
    private Date date;
    private String action;
    private String description;
    private User user;

    public HistoryDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HistoryDto that = (HistoryDto) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(ticket, that.ticket) &&
                Objects.equals(date, that.date) &&
                Objects.equals(action, that.action) &&
                Objects.equals(description, that.description) &&
                Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, ticket, date, action, description, user);
    }

    @Override
    public String toString() {
        return "HistoryDto{" +
                "id=" + id +
                ", ticket=" + ticket +
                ", date=" + date +
                ", action='" + action + '\'' +
                ", description='" + description + '\'' +
                ", user=" + user +
                '}';
    }
}
