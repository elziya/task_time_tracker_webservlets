insert into account(first_name, last_name, email, password_hash) values ('Полина', 'Бригадиренко', 'mail@mail.ru',
'$2a$10$PJBglEIBPcMykM5Vlnc2NuvZk5bd8rm2qvib3Wjzg8OUojWMn0diq');

insert into tag(account_id, tag_name) values (1, 'лекции');
insert into tag(account_id, tag_name) values (1, 'дз');

insert into project(name, start_date, end_date, duration, is_done, account_id)
values ('Подготовка к кр по ВССиТ', '2021-11-03 19:00:00.232233', '2021-11-05 11:00:00.232233', 105, true, 1);
insert into task(name, duration) values ('сделать лабу №8_01', 40);
insert into task(name, duration) values ('сделать лабу №8_02', 50);
insert into task(name, duration) values ('выписать команды eNSP', 15);

insert into project_tag(project_id, tag_id) values (1, 2);

insert into project_task(project_id, task_id) values (1, 1);
insert into project_task(project_id, task_id) values (1, 2);
insert into project_task(project_id, task_id) values (1, 3);

insert into account_project(account_id, project_id) values (2, 1);
