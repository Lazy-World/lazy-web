create table chat (
    id int8 not null,
    primary key (id)
);;

create table chat_users (
    chat_id int8 not null,
    user_id int8 not null
);;

create table message (
    id int8 not null,
    message_date_time timestamp,
    text varchar(1024),
    user_id int8,
    chat_id int8,
    primary key (id)
);;

create table update_log (
    id int8 not null,
    filename varchar(255),
    text varchar(2048),
    title varchar(255),
    user_id int8,
    primary key (id)
);;

create table user_info (
    id int8 not null,
    activation_code varchar(255),
    active boolean not null,
    email varchar(255),
    password varchar(255),
    username varchar(255),
    primary key (id)
);;

create table user_role (
    user_id int8 not null,
    roles varchar(255)
);;

alter table if exists chat_users
    add constraint ci_chat_users_chat_user_id
        foreign key (user_id)
            references user_info;;

alter table if exists chat_users
    add constraint ci_chat_users_chat_id
        foreign key (chat_id)
            references chat;;

alter table if exists message
    add constraint ci_message_user_id
        foreign key (user_id)
            references user_info;;

alter table if exists message
    add constraint ci_message_chat_id
        foreign key (chat_id)
            references chat;;

alter table if exists update_log
    add constraint ci_update_log_user_id
        foreign key (user_id)
            references user_info;;

alter table if exists user_role
    add constraint ci_user_role_user_id
        foreign key (user_id)
            references user_info;;

create sequence hibernate_sequence;;
alter sequence hibernate_sequence owner to postgres;;