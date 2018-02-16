package com.enterprise.yetanother.dto.editticket;

import com.enterprise.yetanother.enums.State;
import com.enterprise.yetanother.enums.State;

import java.util.Objects;

/**
 *@author andrey
 */
public class EditTicketDto {

    private String action;
    private State state;

    public EditTicketDto() {}

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EditTicketDto that = (EditTicketDto) o;
        return Objects.equals(action, that.action);
    }

    @Override
    public int hashCode() {

        return Objects.hash(action);
    }

    @Override
    public String toString() {
        return "EditTicketDto{" +
                " action='" + action + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
