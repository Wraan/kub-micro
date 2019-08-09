package com.wran.linkshort.controller;

import com.wran.linkshort.model.Link;
import com.wran.linkshort.model.LongLinkDto;
import com.wran.linkshort.service.LinkService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api")
public class LinkController {

    private Logger LOGGER = LogManager.getLogger(getClass());

    @Autowired
    public LinkService linkService;

    @PostMapping("/")
    public Link add(@RequestBody LongLinkDto longLinkDto, Principal principal) {
        Link link = linkService.convert(longLinkDto);
        if(principal != null)
            link.setCreator(principal.getName());
        LOGGER.info("New short link generated: {}", link.getShortLink());
        return linkService.save(link);
    }

    @PreAuthorize("#oauth2.hasScope('READ')")
    @GetMapping("/")
    public Iterable<Link> findAll() {
        LOGGER.info("All Short links found");
        return linkService.findAll();
    }

    @PreAuthorize("#oauth2.hasScope('READ')")
    @GetMapping("/{shortLink}")
    public ResponseEntity<Link> findByShortLink(@PathVariable("shortLink") String shortLink,
                                Principal principal) {

        Link link = linkService.findByShortLink(shortLink);
        if(link == null || !link.getCreator().equals(principal.getName()))
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

        LOGGER.info("Short link found: {}", shortLink);
        return ResponseEntity.ok().body(link);
    }

    @PreAuthorize("#oauth2.hasScope('EXECUTE')")
    @DeleteMapping("/{shortLink}")
    public ResponseEntity delete(@PathVariable("shortLink") String shortLink,
                                 Principal principal){
        Link link = linkService.findByShortLink(shortLink);
        if(link == null || !link.getCreator().equals(principal.getName()))
            return new ResponseEntity(HttpStatus.NOT_FOUND);

        LOGGER.info("Short link deleted: {}", shortLink);
        linkService.delete(link);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PreAuthorize("#oauth2.hasScope('WRITE')")
    @GetMapping("/testData")
    public ResponseEntity addTestData(){
        LOGGER.info("Adding 10000 test links to database");
        linkService.addTestData(10000);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PreAuthorize("#oauth2.hasScope('WRITE')")
    @GetMapping("/cleanExpired")
    public ResponseEntity cleanExpired(){
        linkService.cleanExpiredLinks();
        return new ResponseEntity(HttpStatus.OK);
    }




}
