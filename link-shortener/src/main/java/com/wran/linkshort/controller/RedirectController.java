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

import java.util.Calendar;
import java.util.Date;

@Controller
public class RedirectController {

    Logger LOGGER = LogManager.getLogger(getClass());

    @Autowired
    LinkService linkService;

    @GetMapping("/")
    @ResponseBody
    public String mainPage(){
        LOGGER.info("Showing Home page");
        return "Hello :)";
    }

    @GetMapping("/test")
    @ResponseBody
    public String mainPage2(){
        LOGGER.info("Showing Home page2");
        return "Hello2 :)";
    }

    @GetMapping("/expired")
    @ResponseBody
    public String expiredPage(){
        return "Expired";
    }

    @GetMapping("/not-found")
    @ResponseBody
    public String notFound(){
        return "Not found";
    }

    @GetMapping("/{link}")
    public String redirect(@PathVariable("link") String shortLink){
        Link link = linkService.findByShortLink(shortLink);
        if(link == null){
            LOGGER.info("Short link not found: {}", shortLink);
            return "redirect:/not-found";
        }
        if(new Date().after(link.getExpires())){
            LOGGER.info("Short link expired: {}", shortLink);
            return "redirect:/expired";
        }
        LOGGER.info("Short link found: {} - redirecting to long link", link.getShortLink());
        link = linkService.increaseTimesUsed(link);
        return "redirect:" + link.getLongLink();
    }
}
