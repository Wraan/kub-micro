package com.wran.linkshort.service;

import com.wran.linkshort.model.Link;
import com.wran.linkshort.model.LongLinkDto;
import com.wran.linkshort.repository.LinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service("linkService")
public class LinkService {

    @Autowired
    LinkRepository linkRepository;

    public Link convert(LongLinkDto longLink){
        //String shortLink = generateShortLink();

        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();
        calendar.add(Calendar.MINUTE, longLink.getLiveness());
        Date expires = calendar.getTime();

        return new Link(longLink.getLink(), longLink.getLink(), now, expires, longLink.isPublic(), true);

    }

    public Link save(Link link){
        return linkRepository.save(link);
    }

    public void delete(Link link){
        linkRepository.delete(link);
    }
    public void deleteByShortenedLink(String shortenedLink){
        linkRepository.deleteByShortenedLink(shortenedLink);
    }

    public Link findByShortenedLink(String shortenedLink){
        return linkRepository.findByShortenedLink(shortenedLink);

    }
    public Iterable<Link> findAll(){
        return linkRepository.findAll();

    }

}
