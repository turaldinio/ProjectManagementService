--liquibase formatted sql
--changeset stevedonie:create
--multiple-tables splitStatements:true endDelimiter:;

insert into employee(first_name, last_name, patronymic, post, account, email, status)
VALUES ('Дмитрий', 'Соколов', 'Иванович', 'junior developer', 'dimsoc', 'dima@mail.ru', 'ACTIVE'),
       ('Роман', 'Шумайлов', '', 'senior developer', 'romshum98', 'roma@bk.ru', 'ACTIVE'),
       ('Илья', 'Салихов', 'Дмитриевич', 'junior developer', 'sali_ilya', 'ilusha@mail.ru', 'ACTIVE'),
       ('Вова', 'Норвегов', 'Александрович', 'middle developer', 'vovanNorver-93@mail.ru', 'vovnorv@mail.ru', 'REMOTE'),
       ('Никита', 'Беликов', 'Юрьевич', 'junior developer', 'niKa_9271', 'belikovNikia@bk.ru', 'REMOTE');
insert into project(name, text, status)
VALUES ('Smart house', 'Проект должен позволять управлять домом через мобильно приложение', 'DRAFT'),
       ('Fazan', 'Приложение, для поиска видео по снятым отрывкам', 'TESTING'),
       ('Amazing picture', 'приложение , позволяющее перевести любую фотографию в символьный вариант (.,/$|)',
        'DEVELOPING'),
       ('RusDo', 'приложение для поиска вторых половинок', 'COMPLETED');
insert into team (employee_id, project_id, employee_role)
VALUES (1, 1, 'MANAGER'),
       (2, 1, 'DEVELOPER'),
       (3, 1, 'DEVELOPER');

insert into task (name, description, executor_id, labor_costs, deadline, status, author_id, date_of_creation,
                  update_time)
VALUES ('Провести дебагинг',
        'Метод foo выбрасывает exception. Разобраться в основных причинах данной оишкби и устранить ее',
        1, 43, CURDATE(), 'NEW', 2, CURDATE(), DATE_ADD(CURDATE(), INTERVAL 20 DAY));
