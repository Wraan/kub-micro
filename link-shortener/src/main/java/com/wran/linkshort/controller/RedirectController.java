package com.wran.linkshort.controller;

import com.wran.linkshort.model.Link;
import com.wran.linkshort.service.LinkService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;
import java.util.Date;

@Controller
public class RedirectController {

    Logger LOGGER = LogManager.getLogger(getClass());

    @Autowired
    LinkService linkService;

    @Value("${ingress.path}")
    private String ingressPath;

    @GetMapping("/")
    @ResponseBody
    public String mainPage(){
        LOGGER.info("Showing Home page");
        return "Hello :)";
    }

    @GetMapping("/test")
    public String mainPage2(){
        LOGGER.info("Showing Home page2");
        return "redirect:http://www.google.com";
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
    public void redirect(@PathVariable("link") String shortLink, HttpServletResponse response){
        Link link = linkService.findByShortLink(shortLink);
        response.setStatus(302);
        if(link == null){
            LOGGER.info("Short link not found: {}", shortLink);
            response.setHeader("Location", ingressPath + "/not-found");
            return;
        }
        if(new Date().after(link.getExpires())){
            LOGGER.info("Short link expired: {}", shortLink);
            response.setHeader("Location", ingressPath + "/expired");
            return;
        }
        LOGGER.info("Short link found: {} - redirecting to long link", link.getShortLink());
        link = linkService.increaseTimesUsed(link);
        response.setHeader("Location", link.getLongLink());
    }
}
