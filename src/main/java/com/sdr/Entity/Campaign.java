package com.sdr.Entity;

import java.util.Calendar;

public class Campaign {
    private String partnerId;
    private String content;

    private Integer duration;

    private Calendar creationDate;

    public Campaign(String partnerId, String content, Integer duration) {
        this.partnerId = partnerId;
        this.content = content;
        this.duration = duration;
        this.creationDate = Calendar.getInstance();
    }

    public Campaign(String partnerId, String content, Integer duration, Calendar creationDate) {
        this.partnerId = partnerId;
        this.content = content;
        this.duration = duration;
        this.creationDate = creationDate;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Calendar getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Calendar creationDate) {
        this.creationDate = creationDate;
    }

    public String toString() {
        return this.partnerId + ": " + this.content;
    }
}
