package com.wran.linkshort.job.cleaner;

import com.wran.linkshort.model.Link;
import com.wran.linkshort.service.LinkService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ExpiredLinkPageCleaner implements Runnable{

    private int page;
    private LinkService linkService;

    private Logger LOGGER = LogManager.getLogger(getClass());


    public ExpiredLinkPageCleaner(int page, LinkService linkService){
        this.page = page;
        this.linkService = linkService;
    }

    public void run() {
        LOGGER.info("Cleaner [{}] started the job.", page+1);
        List<Link> links = linkService.findAllByPage(page);
        List<Link> deletionList = new ArrayList<>();

        Date now = new Date();
        links.forEach(link -> {
            Calendar deletionDate = Calendar.getInstance();
            deletionDate.setTime(link.getExpires());
            deletionDate.add(Calendar.MINUTE, 30);

            if(now.after(deletionDate.getTime())) {
                deletionList.add(link);
                return;
            }

            if(now.after(link.getExpires()))
                link.setEnabled(false);
        });
        deletionList.forEach(links::remove);
        linkService.saveAll(links);
        linkService.deleteAll(deletionList);
        LOGGER.info("Cleaner [{}] has deleted {} links.", page+1, deletionList.size());

    }
}
