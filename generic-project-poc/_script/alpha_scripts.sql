create table if not exists ALPHA_TABLE_ONE(
    ID int auto_increment primary key,
    MESSAGE  varchar(20) not null,
    CREATED_AT timestamp default current_timestamp
);

create table if not exists ALPHA_TABLE_TWO(
    ID int auto_increment primary key,
    MESSAGE  varchar(20) not null,
    CREATED_AT timestamp default current_timestamp
);

insert into ALPHA_TABLE_ONE (MESSAGE) Values
('A1_ONE'),
('A2_ONE'),
('A3_ONE');

insert into ALPHA_TABLE_TWO (MESSAGE) Values
('A1_TWO'),
('A2_TWO'),
('A3_TWO');

select * from ALPHA_TABLE_ONE;
select * from ALPHA_TABLE_TWO;
