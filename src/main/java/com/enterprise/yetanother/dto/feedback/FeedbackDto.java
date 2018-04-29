package com.enterprise.yetanother.dto.feedback;

import com.enterprise.yetanother.entities.Ticket;
import com.enterprise.yetanother.entities.User;
/*import com.enterprise.yetanother.entities.Ticket;
import com.enterprise.yetanother.entities.User;*/

import java.util.Date;
import java.util.Objects;

/**
 *@author andrey
 */
public class FeedbackDto {

    private Long id;
    private User user;
    private Integer rate;
    private Date date;
    private String text;
    private Ticket ticket;

    public FeedbackDto() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FeedbackDto that = (FeedbackDto) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(user, that.user) &&
                Objects.equals(rate, that.rate) &&
                Objects.equals(date, that.date) &&
                Objects.equals(text, that.text) &&
                Objects.equals(ticket, that.ticket);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, user, rate, date, text, ticket);
    }

    @Override
    public String toString() {
        return "FeedbackDto{" +
                "id=" + id +
                ", user=" + user +
                ", rate=" + rate +
                ", date=" + date +
                ", text='" + text + '\'' +
                ", ticket=" + ticket +
                '}';
    }
}