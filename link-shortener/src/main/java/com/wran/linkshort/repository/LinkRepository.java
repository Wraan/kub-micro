package com.wran.linkshort.repository;

import com.wran.linkshort.model.Link;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LinkRepository extends CrudRepository<Link, Long> {
    Link findByShortenedLink(String shortenedLink);
    void deleteByShortenedLink(String shortenedLink);

}
