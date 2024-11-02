SELECT '"alpha_user" user creation';
CREATE USER IF NOT EXISTS 'alpha_user'@'%' IDENTIFIED BY 'alpha';

SELECT 'Set "alpha_user" permission';
GRANT ALL PRIVILEGES ON alpha.* TO 'alpha_user'@'%';

FLUSH PRIVILEGES;
SELECT '"alpha_user" created';

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

delete from ALPHA_TABLE_ONE;
delete from ALPHA_TABLE_TWO;

insert into ALPHA_TABLE_ONE (MESSAGE) Values
('A1_ONE'),
('A2_ONE'),
('A3_ONE'),
('A4_ONE');

insert into ALPHA_TABLE_TWO (MESSAGE) Values
('A1_TWO'),
('A2_TWO'),
('A3_TWO');

SELECT 'Data in table: ALPHA_TABLE_ONE';
select * from ALPHA_TABLE_ONE;

SELECT 'Data in table: ALPHA_TABLE_TWO';
select * from ALPHA_TABLE_TWO;

SELECT '--------------';