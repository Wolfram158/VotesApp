create table if not exists votes (
    id bigserial primary key,
    title varchar(512) not null,
    variant varchar(128) not null,
    votes_count bigint not null
);

create table if not exists users_and_titles (
    id bigserial primary key,
    username varchar(32) not null,
    title varchar(512) not null,
    constraint unique_username_title unique (username, title)
);