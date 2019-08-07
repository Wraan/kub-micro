package com.wran.linkshort.job.cleaner;

import com.wran.linkshort.service.LinkService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class ExpiredLinkCleaner implements Runnable{

    private Logger LOGGER = LogManager.getLogger(getClass());
    private LinkService linkService;

    public ExpiredLinkCleaner(LinkService linkService) {
        this.linkService = linkService;
    }

    public void run() {
        LOGGER.info("Expired links cleaner has started.");
        List<Future<?>> futures = new ArrayList<>();
        ExecutorService executor = Executors.newFixedThreadPool(4);

        long count = linkService.countAll();
        int pages = (int)Math.ceil((double) count / LinkService.PAGE_SIZE);
        LOGGER.info("Found {} links, {} tasks will be created.", count, pages);

        long startTime = System.currentTimeMillis();

        for(int i=0; i < pages; i++){
            futures.add(executor.submit(new ExpiredLinkPageCleaner(i, linkService)));
            LOGGER.info("New page cleaner has been created [{}/{}]", i+1, pages);
        }

        executor.shutdown();
        try {
            executor.awaitTermination(1, TimeUnit.HOURS);
        } catch (InterruptedException e) {
            LOGGER.info("The expired links cleaning job has been terminated after {}.",
                    (System.currentTimeMillis() - startTime) / 1000.0);
            e.printStackTrace();
        }
        LOGGER.info("The expired links cleaning job has been finished successfully after {} seconds",
                String.format("%.3f", (System.currentTimeMillis() - startTime) / 1000.0));

    }
}
