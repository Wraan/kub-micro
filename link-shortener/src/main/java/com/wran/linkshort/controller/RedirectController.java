package com.wran.linkshort.controller;

import com.wran.linkshort.model.Link;
import com.wran.linkshort.service.LinkService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RedirectController {

    Logger LOGGER = LogManager.getLogger(getClass());

    @Autowired
    LinkService linkService;

    @GetMapping("/")
    @ResponseBody
    public String mainPage(){
        return "Hello :)";
    }

    @GetMapping("/{link}")
    public String redirect(@PathVariable("link") String shortLink){
        Link link = linkService.findByShortenedLink(shortLink);
        if(link == null){
            LOGGER.info("Not found short link: {}", shortLink);
            return "forward:/";
        }
        LOGGER.info("Redirected to: {}", link.getLongLink());
        return "redirect:" + link.getLongLink();
    }
}
