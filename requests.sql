create table subscription (
    id bigserial primary key,
    msisdn int not null,
    timestamp timestamp not null
);

create table purchase (
    id bigserial primary key,
    msisdn int not null,
    timestamp timestamp not null
);
