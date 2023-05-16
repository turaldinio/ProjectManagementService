create table employee
(
    id         bigint primary key auto_increment,
    first_name varchar(20) not null,
    last_name  varchar(20) not null,
    patronymic varchar(20),
    post       varchar(20),
    `account`    varchar(30) unique,
    email      varchar(30),
    `status`     varchar(15) not null
);
create table project
(
    id     bigint primary key auto_increment,
    `name`   varchar(20) not null,
    `text`   text,
    `status` varchar(20) not null
);
create table role
(
    id int primary key auto_increment,
    `name` varchar(20) not null
);
create table team
(
    employee_id bigint not null,
    project_id  bigint not null,
    role_id     int not null,
    foreign key (employee_id) references employee(id),
    foreign key (project_id) references project(id),
    foreign key (role_id) references role(id)
);
create table task(
    id bigint primary key auto_increment,
    `name` varchar(30)not null ,
    `description` text,
    employee_id bigint,
    labor_costs int not null ,
    deadline date not null ,
    `status` varchar (30),
    author varchar(30),
    date_of_creation date not null ,
    `update` date
);
