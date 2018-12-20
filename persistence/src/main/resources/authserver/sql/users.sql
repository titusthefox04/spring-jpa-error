-- Inserts a default admin user with its permissions

-- Username: admin
-- Password: maps1234
INSERT INTO users (ID, CREATED_ON, DELETED, DELETED_ON, MODIFIED_ON, AUTH_TYPE,
                   CREATOR, EMAIL, PHONE, TIMEZONE,
                   USERNAME, FIRST_NAME, LAST_NAME, MODIFIER,
                   HUMAN,
                   ENABLED,
                   PASSWORD_EXPIRES,
                   FIRST_LOGIN,
                   HASH,
                   SALT
                   )
VALUES (1, CURRENT_TIMESTAMP, FALSE, NULL, NULL, 'DB',
        NULL, NULL, NULL, 'Europe/Rome',
        'admin', 'Admin', 'Istrator', NULL,
         /*admin is human*/ TRUE,
         /*enabled*/ TRUE,
         /*password expires just like others*/ TRUE,
         /*must confirm login*/ FALSE,
        '9ea5fd9c6d68fffe09c1faf5b0115abf697b3a510bfaff2b9c7f7a799eb7749f',
        '754147f0d6e4d6943a7950a93e0b15a8cb11e47508aa29dd6a1fcd535960c5120585241eddfe3d6d7d8cacc583b14dbb25505b8999aa26dbd95541830a199fab'
        );

-- permission to do anything with users
--INSERT INTO user_secured_objects (ID, PERMISSIONS, USER_ID)
--VALUES ('USER_ALL', '*', 1);

INSERT into user_password_history (USER_ID, CREATED_ON, SALT, HASH)
VALUES (1, now(), 'salt', 'hash');