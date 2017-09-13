CREATE TABLE votes ( name VARCHAR(10), votes INT ); 
delete from votes;

INSERT INTO votes VALUES ('Smith',10);
INSERT INTO votes VALUES ('Jones',15);
INSERT INTO votes VALUES ('White',20);
INSERT INTO votes VALUES ('Black',40);
INSERT INTO votes VALUES ('Green',50);
INSERT INTO votes VALUES ('Brown',20);

-- select with row number
SELECT rownum as rank, a.* from (select * from votes order by votes) a;
select row_number() over (order by votes) as rank, a.* from votes a order by votes;

create table bugs (id number(10),   open_date timestamp,   close_date timestamp,   severity number(1));
delete from bugs;

insert into bugs values (10, TO_DATE('07.09.2017', 'dd.mm.yyyy'),TO_DATE('07.09.2017', 'dd.mm.yyyy'), 1);
insert into bugs values (20, TO_DATE('07.09.2017', 'dd.mm.yyyy'),TO_DATE('08.09.2017', 'dd.mm.yyyy'), 1);
insert into bugs values (30, TO_DATE('09.09.2017', 'dd.mm.yyyy'),NULL, 1);
insert into bugs values (40, TO_DATE('08.09.2017', 'dd.mm.yyyy'),TO_DATE('09.09.2017', 'dd.mm.yyyy'), 1);
insert into bugs values (777, TO_DATE('06.12.2017', 'dd.mm.yyyy'),TO_DATE('06.12.2017', 'dd.mm.yyyy'), 1);


commit;

define start_date = TO_DATE('01.09.2017','DD.MM.YYYY');
define end_date = TO_DATE('30.09.2017','DD.MM.YYYY');

select count(*), MONTH_DAY from 
(SELECT * FROM  bugs T,
(SELECT &start_date + ROWNUM-1 MONTH_DAY FROM DUAL CONNECT BY LEVEL<=(&end_date-&start_date)+1) V
WHERE
    (T.OPEN_DATE BETWEEN &start_date AND &end_date
    AND (T.CLOSE_DATE BETWEEN &start_date AND &end_date or T.CLOSE_DATE IS NULL))
    AND V.MONTH_DAY BETWEEN T.OPEN_DATE AND T.CLOSE_DATE or (T.CLOSE_DATE IS NULL AND V.MONTH_DAY>=T.OPEN_DATE)
ORDER BY V.MONTH_DAY) group by month_day order by month_day;


select count(*), MONTH_DAY from 
(SELECT * FROM  bugs T,
(SELECT &start_date + ROWNUM-1 MONTH_DAY FROM DUAL CONNECT BY LEVEL<=(&end_date-&start_date)+1) V
LEFT JOIN BUGS ON V.MONTH_DAY BETWEEN OPEN_DATE AND CLOSE_DATE or (CLOSE_DATE IS NULL AND V.MONTH_DAY>=OPEN_DATE)
WHERE
  (T.OPEN_DATE BETWEEN &start_date AND &end_date
   AND (T.CLOSE_DATE BETWEEN &start_date AND &end_date or T.CLOSE_DATE IS NULL))
ORDER BY V.MONTH_DAY) group by month_day order by month_day;


SELECT MONTH_DAY,count(id) from
(SELECT MONTH_DAY, T.* FROM (SELECT &start_date + ROWNUM-1 MONTH_DAY FROM DUAL CONNECT BY LEVEL<=(&end_date-&start_date)+1) V
LEFT JOIN BUGS T ON V.MONTH_DAY BETWEEN OPEN_DATE AND CLOSE_DATE or (CLOSE_DATE IS NULL AND V.MONTH_DAY>=OPEN_DATE)
)
GROUP BY MONTH_DAY
ORDER BY MONTH_DAY;

