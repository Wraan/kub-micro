package com.wran.linkshort.repository;

import com.wran.linkshort.model.Link;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LinkRepository extends CrudRepository<Link, String> {
    Optional<Link> findByShortLink(String shortLink);
    void deleteByShortLink(String shortLink);
    boolean existsByShortLink(String shortLink);

}
