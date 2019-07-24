package com.wran.linkshort.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

@Document(collection = "link")
public class Link implements Serializable {

    private String longLink;
    @Id
    private String shortLink;
    private Date created;
    private Date expires;
    private int timesUsed;
    private boolean enabled;
    private int creatorId;

    public Link() {
    }

    public Link(String longLink, String shortLink, Date created, Date expires,
                int timesUsed, boolean enabled, int creatorId) {
        this.longLink = longLink;
        this.shortLink = shortLink;
        this.created = created;
        this.expires = expires;
        this.timesUsed = timesUsed;
        this.enabled = enabled;
        this.creatorId = creatorId;
    }


    public String getLongLink() {
        return longLink;
    }

    public void setLongLink(String longLink) {
        this.longLink = longLink;
    }

    public String getShortLink() {
        return shortLink;
    }

    public void setShortLink(String shortLink) {
        this.shortLink = shortLink;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getExpires() {
        return expires;
    }

    public void setExpires(Date expires) {
        this.expires = expires;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int getTimesUsed() {
        return timesUsed;
    }

    public void setTimesUsed(int timesUsed) {
        this.timesUsed = timesUsed;
    }

    public int getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(int creatorId) {
        this.creatorId = creatorId;
    }
}
