package com.wran.linkshort.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "link")
public class Link {

    @Id
    private Long id;
    private String longLink;
    @Indexed(unique = true)
    private String shortenedLink;
    private Date created;
    private Date expires;
    private boolean isPublic;
    private boolean enabled;

    public Link() {
    }

    public Link(String longLink, String shortenedLink, Date created, Date expires, boolean isPublic, boolean enabled) {
        this.longLink = longLink;
        this.shortenedLink = shortenedLink;
        this.created = created;
        this.expires = expires;
        this.isPublic = isPublic;
        this.enabled = enabled;
    }

    public Long getId() { return id; }

    public String getLongLink() {
        return longLink;
    }

    public void setLongLink(String longLink) {
        this.longLink = longLink;
    }

    public String getShortenedLink() {
        return shortenedLink;
    }

    public void setShortenedLink(String shortenedLink) {
        this.shortenedLink = shortenedLink;
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

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
