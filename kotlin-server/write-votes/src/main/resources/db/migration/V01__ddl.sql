create table if not exists votes (
    id bigserial primary key,
    title varchar(512) not null,
    variant varchar(128) not null
);
