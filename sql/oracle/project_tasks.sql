CREATE TABLE PROJECT (ID NUMBER, NAME VARCHAR2(50));
DELETE FROM PROJECT;

INSERT INTO PROJECT VALUES (10, 'Project 1');
INSERT INTO PROJECT VALUES (20, 'Project 2');
INSERT INTO PROJECT VALUES (30, 'Project 3');
INSERT INTO PROJECT VALUES (40, 'Project 4');
INSERT INTO PROJECT VALUES (50, 'Project 5');
INSERT INTO PROJECT VALUES (60, 'Project 6');


CREATE TABLE TASK (ID NUMBER, PROJECT_ID NUMBER, NAME VARCHAR2(50));
DELETE FROM TASK;

INSERT INTO TASK VALUES(1, 10, 'TASK1_10');
INSERT INTO TASK VALUES(2, 10, 'TASK2_10');
INSERT INTO TASK VALUES(3, 40, 'TASK3_40');
INSERT INTO TASK VALUES(4, 60, 'TASK4_60');
INSERT INTO TASK VALUES(5, NULL, 'TASK5_NO');
INSERT INTO TASK VALUES(6, NULL, 'TASK6_NO');

select * from project p left join task t on p.id = t.project_id order by p.id;
select p.* from project p left join task t on p.id = t.project_id where t.project_id is null order by p.id;
select * from project p full join task t on p.id = t.project_id order by p.id;
