package com.wran.linkshort.controller;

import com.wran.linkshort.model.Link;
import com.wran.linkshort.model.LongLinkDto;
import com.wran.linkshort.service.LinkService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class LinkController {

    Logger LOGGER = LogManager.getLogger(getClass());

    @Autowired
    public LinkService linkService;

    @PostMapping("/")
    public Link add(@RequestBody LongLinkDto longLinkDto) {
        Link link = linkService.convert(longLinkDto);
        LOGGER.info("New short link generated: {}", link.getShortLink());
        return linkService.save(link);
    }

    @GetMapping("/")
    public Iterable<Link> findAll() {
        LOGGER.info("All Short links found");
        return linkService.findAll();
    }

    @GetMapping("/{shortLink}")
    public Link findById(@PathVariable("shortLink") String shortLink) {
        LOGGER.info("Short link found: {}", shortLink);
        return linkService.findByShortLink(shortLink);
    }

    @DeleteMapping("/{shortLink}")
    public ResponseEntity delete(@PathVariable("shortLink") String shortLink){
        LOGGER.info("Short link deleted: {}", shortLink);
        linkService.deleteByShortLink(shortLink);
        return new ResponseEntity(HttpStatus.OK);
    }

}
