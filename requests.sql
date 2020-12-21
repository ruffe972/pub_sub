create table subscription (
    id bigserial primary key,
    message_id bigint not null,
    msisdn int not null,
    timestamp timestamp not null
);

create table purchase (
    id bigserial primary key,
    message_id bigint not null,
    msisdn int not null,
    timestamp timestamp not null
);
