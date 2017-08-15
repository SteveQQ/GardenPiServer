package com.steveq.communication.models;

import java.util.List;

/**
 * Created by Adam on 2017-08-07.
 */
public class Section {
    private Integer number;
    private List<String> times;
    private List<String> days;
    private Boolean active;

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public List<String> getTimes() {
        return times;
    }

    public void setTimes(List<String> times) {
        this.times = times;
    }

    public List<String> getDays() {
        return days;
    }

    public void setDays(List<String> days) {
        this.days = days;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "Section{" +
                "number=" + number +
                ", times=" + times +
                ", days=" + days +
                ", active=" + active +
                '}';
    }
}
