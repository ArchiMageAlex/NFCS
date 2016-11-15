
    create table BaseEntity (
        id int8 not null,
        createdOn timestamp,
        validFrom timestamp,
        validTo timestamp,
        createdBy_id int8,
        primary key (id)
    );

    create table Colleague (
        birthDate date,
        id int8 not null,
        department_id int8,
        organization_id int8,
        primary key (id)
    );

    create table Department (
        id int8 not null,
        chief_id int8,
        organization_id int8,
        parentDepartment_id int8,
        primary key (id)
    );

    create table EmailTransport (
        EMail varchar(255) not null,
        id int8 not null,
        primary key (id)
    );

    create table Menu (
        entityName varchar(255),
        name varchar(255) not null,
        id int8 not null,
        parent_id int8,
        primary key (id)
    );

    create table Organization (
        id int8 not null,
        chief_id int8,
        primary key (id)
    );

    create table Report (
        code varchar(255) not null,
        name varchar(255) not null,
        priority int4,
        id int8 not null,
        controller_id int8 not null,
        executor_id int8 not null,
        prev_id int8,
        primary key (id)
    );

    create table Report_Transport (
        Report_id int8 not null,
        destinations_id int8 not null
    );

    create table Schedule (
        begins timestamp not null,
        calendar timestamp not null,
        ends timestamp not null,
        every int4,
        periodType int4 not null,
        repeat int4 not null,
        startFrom int4 not null,
        id int8 not null,
        report_id int8,
        primary key (id)
    );

    create table Subject (
        name varchar(255),
        id int8 not null,
        schedules int8,
        primary key (id)
    );

    create table Transport (
        id int8 not null,
        subject_id int8,
        primary key (id)
    );

    create table roles (
        name varchar(255),
        id int8 not null,
        primary key (id)
    );

    create table roles_users (
        roles_id int8 not null,
        users_id int8 not null
    );

    create table users (
        name varchar(255),
        password varchar(255),
        id int8 not null,
        primary key (id)
    );

    alter table BaseEntity 
        add constraint FK2DDC9234BD2C215F 
        foreign key (createdBy_id) 
        references Colleague;

    alter table Colleague 
        add constraint FKE7B4D38F4171A185 
        foreign key (department_id) 
        references Department;

    alter table Colleague 
        add constraint FKE7B4D38F5C9D3325 
        foreign key (organization_id) 
        references Organization;

    alter table Colleague 
        add constraint FKE7B4D38F62892D1C 
        foreign key (id) 
        references Subject;

    alter table Department 
        add constraint FKA9601F725C9D3325 
        foreign key (organization_id) 
        references Organization;

    alter table Department 
        add constraint FKA9601F72963B7D1B 
        foreign key (parentDepartment_id) 
        references Department;

    alter table Department 
        add constraint FKA9601F7262892D1C 
        foreign key (id) 
        references Subject;

    alter table Department 
        add constraint FKA9601F72A8227899 
        foreign key (chief_id) 
        references Colleague;

    alter table EmailTransport 
        add constraint FK2FDEE70D141A6DD9 
        foreign key (id) 
        references Transport;

    alter table Menu 
        add constraint FK24897F77F1F8F3 
        foreign key (parent_id) 
        references Menu;

    alter table Menu 
        add constraint FK24897F893AD2F3 
        foreign key (id) 
        references BaseEntity;

    alter table Organization 
        add constraint FK5010415362892D1C 
        foreign key (id) 
        references Subject;

    alter table Organization 
        add constraint FK50104153A8227899 
        foreign key (chief_id) 
        references Colleague;

    alter table Report 
        add constraint FK91B14154A42535E2 
        foreign key (controller_id) 
        references Colleague;

    alter table Report 
        add constraint FK91B14154893AD2F3 
        foreign key (id) 
        references BaseEntity;

    alter table Report 
        add constraint FK91B141542AD2814B 
        foreign key (executor_id) 
        references Colleague;

    alter table Report 
        add constraint FK91B141542FB71A66 
        foreign key (prev_id) 
        references Report;

    alter table Report_Transport 
        add constraint FK3254B9DE2D9EFBC5 
        foreign key (Report_id) 
        references Report;

    alter table Report_Transport 
        add constraint FK3254B9DEAB19A633 
        foreign key (destinations_id) 
        references Transport;

    alter table Schedule 
        add constraint FKDA40F6B72D9EFBC5 
        foreign key (report_id) 
        references Report;

    alter table Schedule 
        add constraint FKDA40F6B7893AD2F3 
        foreign key (id) 
        references BaseEntity;

    alter table Subject 
        add constraint FKF3E2ED0C391A1C7B 
        foreign key (schedules) 
        references Report;

    alter table Subject 
        add constraint FKF3E2ED0C893AD2F3 
        foreign key (id) 
        references BaseEntity;

    alter table Transport 
        add constraint FKB635170940581D0F 
        foreign key (subject_id) 
        references Subject;

    alter table Transport 
        add constraint FKB6351709893AD2F3 
        foreign key (id) 
        references BaseEntity;

    alter table roles 
        add constraint FK67A8EBD893AD2F3 
        foreign key (id) 
        references BaseEntity;

    alter table roles_users 
        add constraint FK2E94BE067817BB9E 
        foreign key (roles_id) 
        references roles;

    alter table roles_users 
        add constraint FK2E94BE06781ADFC8 
        foreign key (users_id) 
        references users;

    alter table users 
        add constraint FK6A68E08893AD2F3 
        foreign key (id) 
        references BaseEntity;

    create sequence hibernate_sequence;
