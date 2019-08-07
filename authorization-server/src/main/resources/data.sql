 INSERT INTO oauth_client_details (client_id, client_secret, web_server_redirect_uri, scope, access_token_validity, refresh_token_validity, resource_ids, authorized_grant_types, additional_information)
    VALUES ('mobile', '{bcrypt}$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG', 'http://localhost:8080/code', 'READ,WRITE', '3600', '10000', 'inventory,payment', 'authorization_code,password,refresh_token,implicit', '{}'),
           ('link-shortener', '{bcrypt}$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG', 'http://localhost:8080/code', 'READ,WRITE', '3600', '10000', 'links', 'authorization_code,password,refresh_token,implicit', '{}')
    ON CONFLICT ON CONSTRAINT oauth_client_details_pkey DO NOTHING;

 INSERT INTO PERMISSION (NAME) VALUES
    ('create_profile'),
    ('read_profile'),
    ('update_profile'),
    ('delete_profile')
    ON CONFLICT ON CONSTRAINT permission_name_key DO NOTHING;

 INSERT INTO role (NAME) VALUES
		('ROLE_admin'),
		('ROLE_operator')
		ON CONFLICT ON CONSTRAINT role_name_key DO NOTHING;;

 INSERT INTO PERMISSION_ROLE (PERMISSION_ID, ROLE_ID) VALUES
    (1,1), /* create admin */
    (2,1), /* read admin */
    (3,1), /* update admin */
    (4,1), /* delete admin */
    (2,2), /* read operator */
    (3,2); /* update operator */

 INSERT INTO users (id, username,password, email, enabled, account_non_expired, credentials_non_expired, account_non_locked) VALUES
    ('1', 'admin','{bcrypt}$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG', 'admin@wran.com', '1', '1', '1', '1'),
    ('2', 'tester', '{bcrypt}$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG','tester@wran.com', '1', '1', '1', '1')
    ON CONFLICT ON CONSTRAINT users_pkey DO NOTHING;

 INSERT INTO ROLE_USER (ROLE_ID, USER_ID)
    VALUES
    (1, 1), /* admin -> admin */
    (2, 2); /* tester -> operatorr */