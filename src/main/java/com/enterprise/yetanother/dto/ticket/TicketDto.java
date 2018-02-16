package com.enterprise.yetanother.dto.ticket;

import com.enterprise.yetanother.entities.Category;
import com.enterprise.yetanother.entities.Feedback;
import com.enterprise.yetanother.entities.User;
import com.enterprise.yetanother.enums.State;
import com.enterprise.yetanother.enums.Urgency;
import com.enterprise.yetanother.entities.Category;
import com.enterprise.yetanother.entities.Feedback;
import com.enterprise.yetanother.entities.User;
import com.enterprise.yetanother.enums.State;
import com.enterprise.yetanother.enums.Urgency;

import java.util.Date;
import java.util.Objects;

/**
 *@author andrey
 */
public class TicketDto {

    private Long id;
    private Category category;
    private String name;
    private String description;
    private Urgency urgency;
    private Date createdOn;
    private Date desiredResolutionDate;
    private State state;
    private User assignee;
    private User owner;
    private User approver;
    private Feedback feedback;

    public TicketDto() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Urgency getUrgency() {
        return urgency;
    }

    public void setUrgency(Urgency urgency) {
        this.urgency = urgency;
    }

    public Date getDesiredResolutionDate() {
        return desiredResolutionDate;
    }

    public void setDesiredResolutionDate(Date desiredResolutionDate) {
        this.desiredResolutionDate = desiredResolutionDate;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public User getAssignee() {
        return assignee;
    }

    public void setAssignee(User assignee) {
        this.assignee = assignee;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public User getApprover() {
        return approver;
    }

    public void setApprover(User approver) {
        this.approver = approver;
    }

    public Feedback getFeedback() {
        return feedback;
    }

    public void setFeedback(Feedback feedback) {
        this.feedback = feedback;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TicketDto that = (TicketDto) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(category, that.category) &&
                Objects.equals(name, that.name) &&
                Objects.equals(description, that.description) &&
                urgency == that.urgency &&
                Objects.equals(createdOn, that.createdOn) &&
                Objects.equals(desiredResolutionDate, that.desiredResolutionDate) &&
                state == that.state &&
                Objects.equals(assignee, that.assignee) &&
                Objects.equals(owner, that.owner) &&
                Objects.equals(approver, that.approver) &&
                Objects.equals(feedback, that.feedback);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, category, name, description, urgency, createdOn, desiredResolutionDate, state, assignee, owner, approver, feedback);
    }

    @Override
    public String toString() {
        return "TicketDto{" +
                "id=" + id +
                ", category=" + category +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", urgency=" + urgency +
                ", createdOn=" + createdOn +
                ", desiredResolutionDate='" + desiredResolutionDate + '\'' +
                ", state=" + state +
                ", assignee=" + assignee +
                ", owner=" + owner +
                ", approver=" + approver +
                ", feedback=" + feedback +
                '}';
    }
}
