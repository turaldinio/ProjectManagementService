--liquibase formatted sql
--changeset stevedonie:create
--multiple-tables splitStatements:true endDelimiter:;
create table employee_status
(
    id     int primary key auto_increment,
    status varchar(20) not null
);
create table project_status
(
    id     int primary key auto_increment,
    status varchar(20) not null
);
create table task_status
(
    id     int primary key auto_increment,
    status varchar(20) not null
);
create table role
(
    id   int primary key auto_increment,
    role varchar(20) not null
);


create table employee
(
    id         bigint primary key auto_increment,
    first_name varchar(20) not null,
    last_name  varchar(20) not null,
    patronymic varchar(20),
    post       varchar(20),
    account    varchar(30) unique,
    email      varchar(30),
    status_id  int         not null,
    foreign key (status_id) references employee_status (id)
);
create table project
(
    id        bigint primary key auto_increment,
    name      varchar(20) not null,
    text      text,
    status_id int         not null,

    foreign key (status_id) references project_status (id)
);

create table team
(
    id bigint primary key auto_increment,
    employee_id bigint not null,
    project_id  bigint not null,
    role_id     int    not null,
    foreign key (employee_id) references employee (id),
    foreign key (project_id) references project (id),
    foreign key (role_id) references role (id)
);
create table task
(
    id               bigint primary key auto_increment,
    name             varchar(30) not null,
    description      text,
    executor_id      bigint,
    labor_costs      int         not null,
    deadline         date        not null,
    status_id        int         not null,
    author_id        bigint,
    date_of_creation date        not null,
    update_time      date,
    foreign key (executor_id) references employee (id),
    foreign key (status_id) references task_status (id),
    foreign key (author_id) references employee (id)

);


