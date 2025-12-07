create table if not exists users_and_titles (
    username varchar(32) not null,
    title varchar(512) not null,
    constraint pk_users_and_titles primary key (username, title)
);