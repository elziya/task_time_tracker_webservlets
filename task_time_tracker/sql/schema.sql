create table account(
    id serial primary key,
    first_name varchar(40),
    last_name varchar(40),
    email varchar(254) not null,
    password_hash varchar not null,
    photo_name uuid
);

create table project(
    id serial primary key,
    name varchar(50),
    start_date timestamp,
    end_date timestamp,
    duration integer default 0,
    is_done boolean default false,
    account_id integer not null,
    foreign key (account_id) references account(id)
);

create table task(
    id serial primary key,
    name varchar(50),
    duration integer default 0
);

create table tag(
    id serial primary key,
    account_id integer not null,
    foreign key (account_id) references account(id),
    tag_name varchar(40) not null
);

create table project_tag(
    project_id integer not null,
    foreign key (project_id) references project(id),
    tag_id integer not null,
    foreign key (tag_id) references tag(id),
    primary key(project_id, tag_id)
);

create table account_project(
    account_id integer not null,
    foreign key (account_id) references account(id),
    project_id integer not null,
    foreign key (project_id) references project(id),
    primary key(account_id, project_id)
);

create table project_task(
    project_id integer not null,
    foreign key (project_id) references project(id),
    task_id integer not null,
    foreign key (task_id) references task(id),
    primary key(project_id, task_id)
);
