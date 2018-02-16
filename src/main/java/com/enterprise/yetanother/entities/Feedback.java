package com.enterprise.yetanother.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 *@author andrey
 */
@Entity
@Table(name = "FEEDBACK")
public class Feedback implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(
                                        name = "FEEDBACK_USER_ID_FK"))
    private User user;

    private Integer rate;
    private Date date;
    private String text;

    @OneToOne
    @JsonManagedReference
    @JoinColumn(name = "ticket_id", foreignKey = @ForeignKey(
                                          name = "FEEDBACK_TICKET_ID_FK"))
    private Ticket ticket;

    public Feedback() {}

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

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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

        Feedback feedback = (Feedback) o;

        if (!id.equals(feedback.id)) return false;
        if (!user.equals(feedback.user)) return false;
        if (!rate.equals(feedback.rate)) return false;
        if (!date.equals(feedback.date)) return false;
        if (!text.equals(feedback.text)) return false;
        return ticket.equals(feedback.ticket);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + user.hashCode();
        result = 31 * result + rate.hashCode();
        result = 31 * result + date.hashCode();
        result = 31 * result + text.hashCode();
        result = 31 * result + ticket.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "id=" + id +
                ", user=" + user +
                ", rate=" + rate +
                ", date=" + date +
                ", text='" + text + '\'' +
                ", ticket=" + ticket +
                '}';
    }
}
