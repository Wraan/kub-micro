package com.wran.authorizationserver.repository;

import com.wran.authorizationserver.model.oauth.OAuthClientDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OAuthClientDetailsRepository  extends JpaRepository<OAuthClientDetails, String> {
}
