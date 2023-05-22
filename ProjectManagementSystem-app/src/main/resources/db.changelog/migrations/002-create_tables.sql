--liquibase formatted sql
--changeset stevedonie:create
--multiple-tables splitStatements:true endDelimiter:;

create table employee
(
    id         bigint primary key auto_increment,
    first_name text not null,
    last_name  text not null,
    patronymic text,
    post       text,
    account    varchar(30) unique,
    email      text,
    status     text not null

);
create table project
(
    id     bigint primary key auto_increment,
    name   text not null,
    text   text,
    status text not null

);

create table team
(
    id            bigint primary key auto_increment,
    employee_id   bigint not null,
    project_id    bigint not null,
    employee_role text   not null,
    foreign key (employee_id) references employee (id),
    foreign key (project_id) references project (id)
);
create table task
(
    id               bigint primary key auto_increment,
    name             text      not null,
    description      text,
    executor_id      bigint,
    labor_costs      int       not null,
    deadline         timestamp not null,
    status           text      not null,
    author_id        bigint,
    date_of_creation timestamp not null,
    update_time      timestamp,
    foreign key (executor_id) references employee (id),
    foreign key (author_id) references employee (id)

);


