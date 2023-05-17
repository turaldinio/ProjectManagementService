insert into employee_status (status)
values ('ACTIVE'),
       ('REMOTE');
insert into role (role)
values ('MANAGER'),
       ('ANALYST'),
       ('DEVELOPER'),
       ('TESTER');

insert into project_status (status)
values ('DRAFT'),
       ('TESTING'),
       ('DEVELOPING'),
       ('COMPLETED');

insert into task_status (status)
values ('NEW'),
       ('AT_WORK'),
       ('COMPLETED'),
       ('CLOSED');
insert into employee(first_name, last_name, patronymic, post, account, email, status_id)
VALUES ('Дмитрий', 'Соколов', 'Иванович', 'junior developer', 'dimsoc', 'dima@mail.ru', 1),
       ('Роман', 'Шумайлов', '', 'senior developer', 'romshum98', 'roma@bk.ru', 1),
       ('Илья', 'Салихов', 'Дмитриевич', 'junior developer', 'sali_ilya', 'ilusha@mail.ru', 1),
       ('Вова', 'Норвегов', 'Александрович', 'middle developer', 'vovanNorver-93@mail.ru', 'vovnorv@mail.ru', 2),
       ('Никита', 'Беликов', 'Юрьевич', 'junior developer', 'niKa_9271', 'belikovNikia@bk.ru', 2);
insert into project(name, text, status_id)
VALUES ('Smart house', 'Проект должен позволять управлять домом через мобильно приложение', 1),
       ('Fazan', 'Приложение, для поиска видео по снятым отрывкам', 2),
       ('Amazing picture', 'приложение , позволяющее перевести любую фотографию в символьный вариант (.,/$|)', 3),
       ('RusDo', 'приложение для поиска вторых половинок', 4);
insert into team (employee_id, project_id, role_id)
VALUES (1,1,1),
       (2,1,2),
       (3,1,4);

insert into task (name, description, executor_id, labor_costs, deadline, status_id, author_id, date_of_creation, update_time)
VALUES ('Провести дебагинг','Метод foo выбрасывает exception. Разобраться в основных причинах данной оишкби и устранить ее',
        1,43,CURDATE(),1,2,CURDATE(),DATE_ADD(CURDATE(),INTERVAL 20 DAY ));
