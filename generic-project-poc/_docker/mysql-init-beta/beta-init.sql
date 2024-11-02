SELECT '"beta_user" user creation';
CREATE USER IF NOT EXISTS 'beta_user'@'%' IDENTIFIED BY 'beta';

SELECT 'Set "beta_user" permission';
GRANT ALL PRIVILEGES ON beta.* TO 'beta_user'@'%';

FLUSH PRIVILEGES;
SELECT '"beta_user" created';

create table if not exists BETA_TABLE(
    ID int auto_increment primary key,
    MESSAGE  varchar(20) not null,
    CREATED_AT timestamp default current_timestamp
);

insert into BETA_TABLE (MESSAGE) Values
('B1'),
('B2'),
('B3');

SELECT 'Data in table: BETA_TABLE';
select * from BETA_TABLE;
