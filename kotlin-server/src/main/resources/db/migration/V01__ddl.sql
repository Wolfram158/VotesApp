create table if not exists users (
    username varchar(32) not null unique,
    email varchar(256) not null unique,
    password varchar(2048) not null,
    constraint pk_users primary key (username, email)
);

create table if not exists refresh_tokens (
    id bigserial primary key,
    username varchar(32) references users (username),
    refresh_token varchar(2048) not null
);

create table if not exists votes (
    id bigserial primary key,
    title varchar(512) not null,
    variant varchar(128) not null,
    votes_count bigint not null
);