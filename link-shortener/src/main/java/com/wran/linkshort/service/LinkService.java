package com.wran.linkshort.service;

import com.wran.linkshort.model.Link;
import com.wran.linkshort.model.LongLinkDto;
import com.wran.linkshort.repository.LinkRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

@Service("linkService")
public class LinkService {

    Logger LOGGER = LogManager.getLogger(getClass());

    @Autowired
    private LinkRepository linkRepository;

    private static final String ALPHA_NUMERIC_STRING = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    @Value("${link.generator.length}")
    private int linkGeneratorLength;

    public Link convert(LongLinkDto longLink){
        String shortLink = generateShortLink(linkGeneratorLength);
        while(shortLinkExists(shortLink)){
            LOGGER.info("Short link already exists: {}, generating new one.", shortLink);
            shortLink = generateShortLink(linkGeneratorLength);
        }

        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();
        calendar.add(Calendar.MINUTE, longLink.getLiveness());
        Date expires = calendar.getTime();

        return new Link(longLink.getLink(), shortLink, now, expires, 0, true, 0);

    }

    private String generateShortLink(int length){
        StringBuilder sb = new StringBuilder();

        while(length-- != 0){
            int index = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
            sb.append(ALPHA_NUMERIC_STRING.charAt(index));
        }
        return sb.toString();
    }

    private boolean shortLinkExists(String shortLink){
        return linkRepository.existsByShortLink(shortLink);
    }


    public Link save(Link link){
        return linkRepository.save(link);
    }

    public void delete(Link link){
        linkRepository.delete(link);
    }
    public void deleteByShortLink(String shortLink){
        linkRepository.deleteByShortLink(shortLink);
    }

    public Link findByShortLink(String shortLink){
        return linkRepository.findByShortLink(shortLink).orElse(null);
    }
    public Iterable<Link> findAll(){
        return linkRepository.findAll();

    }

    public Link increaseTimesUsed(Link link) {
        link.setTimesUsed(link.getTimesUsed() + 1);
        return linkRepository.save(link);
    }
}
