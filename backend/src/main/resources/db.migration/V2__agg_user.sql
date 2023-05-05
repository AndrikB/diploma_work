create table if not exists users
(
    user_id   uuid not null
        constraint users_pk
            primary key,
    login varchar not null,
    mail varchar not null,
    status varchar not null,
    password varchar not null,
    firstName varchar,
    lastName varchar,
    profile varchar
);

create table if not exists tokens(
    user_id uuid not null
        constraint tokens_users_null_fk
            references users (user_id),
    token varchar not null,
    created_at timestamptz default now()
);