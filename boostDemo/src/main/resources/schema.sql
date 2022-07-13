create table if not exists comment (id varchar(255) not null, message varchar(255), owner_id varchar(255), post varchar(255), publish_date datetime, primary key (id));
create table if not exists user  (id varchar(255) not null, first_name varchar(255), last_name varchar(255), picture varchar(255), title varchar(255), primary key (id));

ALTER DATABASE
    boost
    CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_unicode_ci;


ALTER TABLE
    comment
    CONVERT TO CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;