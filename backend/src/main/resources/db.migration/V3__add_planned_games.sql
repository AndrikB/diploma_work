CREATE TABLE IF NOT EXISTS planned_games
(
    planned_game_id   uuid    not null
        constraint planned_games_pk
            primary key,
    game_id           int8    not null,
    author_id         uuid    not null
        constraint planned_games_users_null_fk
            references users (user_id),
    max_count_players int8    not null,
    date              DATE    not null,
    address           varchar not null
);

CREATE TABLE IF NOT EXISTS game_participants
(
    user_id         uuid not null
        constraint game_participants_users_null_fk
            references users (user_id),
    planned_game_id uuid not null
        constraint game_participants_planned_games_null_fk
            references planned_games (planned_game_id)
)