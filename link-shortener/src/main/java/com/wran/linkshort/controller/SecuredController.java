package com.wran.linkshort.controller;

import com.wran.linkshort.model.Link;
import com.wran.linkshort.service.LinkService;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.Principal;
import java.util.Map;

@RestController
public class SecuredController {

    @Autowired
    private LinkService linkService;

    @PreAuthorize("#oauth2.hasScope('READ')")
    @GetMapping("/secured/{shortLink}")
    public String shortLinkDetails(@PathVariable("shortLink") String shortLink, Principal principal){
        System.out.println(principal.getName());
        Link link = linkService.findByShortLink(shortLink);
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(link);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Short link not found";
    }

    @PreAuthorize("#oauth2.hasScope('READ')")
    @GetMapping("/secured/extras")
    public Map<String, Object> getExtraInfo(Authentication auth) {
        OAuth2AuthenticationDetails oauthDetails
                = (OAuth2AuthenticationDetails) auth.getDetails();
        return (Map<String, Object>) oauthDetails
                .getDecodedDetails();
    }
}
