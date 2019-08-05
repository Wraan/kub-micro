package com.wran.linkshort.service;

import com.wran.linkshort.job.cleaner.ExpiredLinkCleaner;
import com.wran.linkshort.model.Link;
import com.wran.linkshort.model.LongLinkDto;
import com.wran.linkshort.repository.LinkRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("linkService")
public class LinkService {

    private Logger LOGGER = LogManager.getLogger(getClass());
    public final static int PAGE_SIZE = 1000;

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
    public List<Link> findAll(){
        return linkRepository.findAll();
    }

    public Link increaseTimesUsed(Link link) {
        link.setTimesUsed(link.getTimesUsed() + 1);
        return linkRepository.save(link);
    }

    public long countAll(){
        return linkRepository.count();
    }

    public List<Link> findAllByPage(int page){
        return linkRepository.findAll(PageRequest.of(page, PAGE_SIZE)).getContent();
    }

    public Iterable<Link> saveAll(List<Link> links){
        return linkRepository.saveAll(links);
    }
    public void deleteAll(List<Link> links){
        linkRepository.deleteAll(links);
    }

    public void addTestData(int amount){
        List<Link> links = new ArrayList<>();

        Date now = new Date();

        for(int i = 1; i <= amount; i++){
            if(i % 2500 == 0 && i > 0)
                LOGGER.info("Test data added {}/{}", i, amount);

            Random random = new Random();
            String shortLink = generateShortLink(linkGeneratorLength);
            while(shortLinkExists(shortLink)){
                shortLink = generateShortLink(linkGeneratorLength);
            }

            Calendar expiresDate = Calendar.getInstance();
            expiresDate.setTime(now);
            expiresDate.add(Calendar.MINUTE, random.nextInt(30));

            Link link = new Link("https://www.google.com", shortLink, now, expiresDate.getTime(), random.nextInt(100), true, 0);
            links.add(link);
        }
        saveAll(links);
    }

    public void cleanExpiredLinks(){
        Thread cleaner = new Thread(new ExpiredLinkCleaner(this));
        cleaner.start();
    }
}
