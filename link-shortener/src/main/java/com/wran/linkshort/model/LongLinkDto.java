package com.wran.linkshort.model;

public class LongLinkDto {

    private String link;
    private int liveness;

    public LongLinkDto() {
    }

    public LongLinkDto(String link, int liveness) {
        this.link = link;
        this.liveness = liveness;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getLiveness() {
        return liveness;
    }

    public void setLiveness(int liveness) {
        this.liveness = liveness;
    }
}
