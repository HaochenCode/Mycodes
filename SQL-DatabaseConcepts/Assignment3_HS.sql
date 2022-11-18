-- Creating database with full name
CREATE DATABASE haocsun;

-- Connecting to database 
\c haocsun;



CREATE TABLE Westerosi(wid integer,
                    wname text,
                    wlocation text,
                    primary key (wid));

CREATE TABLE House(hname text,
                     kingdom text,
                     primary key (hname));

CREATE TABLE Skill(skill text,
                   primary key (skill));


CREATE TABLE OfHouse(wid integer,
                      hname text,
                      wages integer,
                      primary key (wid),
                      foreign key (wid) references Westerosi (wid),
                      foreign key (hname) references House(hname));


CREATE TABLE HouseAllyRegion(hname text,
                             region text,
                             primary key (hname, region),
                             foreign key (hname) references House (hname));


CREATE TABLE WesterosiSkill(wid integer,
                         skill text,
                         primary key (wid, skill),
                         foreign key (wid) references Westerosi (wid) on delete cascade,
                         foreign key (skill) references Skill (skill) on delete cascade);


CREATE TABLE Predecessor(succid integer,
                        predid integer,
                        primary key (succid, predid),
                        foreign key (succid) references Westerosi (wid),
                        foreign key (predid) references Westerosi (wid));

CREATE TABLE Knows(wid1 integer,
                   wid2 integer,
                   primary key(wid1, wid2),
                   foreign key (wid1) references Westerosi (wid),
                   foreign key (wid2) references Westerosi (wid));

INSERT INTO Westerosi VALUES
     (1001,'JonSnow','CastleBlack'),
     (1002,'Daenerys', 'CastleBlack'),
     (1003,'Sansa', 'KingsLanding'),
     (1004,'Cersei', 'KingsLanding'),
     (1005,'Jamie', 'CasterlyRock'),
     (1006,'Joffrey', 'BlackwaterBay'),
     (1007,'Stannis','Stormlands'),
     (1008,'Arya', 'Winterfell'),
     (1009,'Bran', 'Winterfell'),
     (1010,'Renly', 'BlackwaterBay'),
     (1011,'Catelyn', 'CasterlyRock'),
     (1012,'Samwell', 'CastleBlack'),
     (1013,'Tywin', 'Riverrun'), 
     (1014,'Brienne', 'Winterfell'), 
     (1015,'Olenna', 'Vale'),
     (1016,'Oberyn', 'BlackwaterBay'),
     (1017,'Robb', 'Stormlands'),
     (1018,'Theon', 'Winterfell'),
     (1019,'Tyrion', 'Highgarden'),
     (1020,'Varys', 'Oldtown');

INSERT INTO House VALUES
     ('NightsWatch', 'CastleBlack'),
     ('Baratheon', 'KingsLanding'),
     ('Lannister', 'CasterlyRock'),
     ('Stark', 'Winterfell'),
     ('Martell', 'Dorne'),
     ('Targaryen', 'KingsLanding'),
     ('Tyrell', 'Highgarden');


INSERT INTO OfHouse VALUES
     (1001,'NightsWatch', 65000),
     (1002,'Targaryen', 45000),
     (1003,'Stark', 55000),
     (1004,'Lannister', 55000),
     (1005,'Lannister', 60000),
     (1006,'Baratheon', 55000),
     (1007,'Baratheon', 50000),
     (1008,'Stark', 50000),
     (1009,'Stark',60000),
     (1010,'Baratheon', 55000),
     (1011,'Stark', 70000), 
     (1012,'NightsWatch', 50000),
     (1013,'Lannister', 55000),
     (1014,'NightsWatch', 50000), 
     (1015,'Tyrell', 60000),
     (1016,'Martell', 55000),
     (1017,'Stark', 60000),
     (1018,'NightsWatch', 50000),
     (1019,'Lannister', 50000);


INSERT INTO HouseAllyRegion VALUES
   ('NightsWatch', 'Winterfell'),
   ('Baratheon', 'BlackwaterBay'),
   ('Baratheon', 'Vale'),
   ('Baratheon', 'IronIslands'),
   ('Lannister', 'Highgarden'),
   ('Stark', 'Riverrun'),
   ('Stark', 'BlackwaterBay'),
   ('Martell', 'Winterfell'),
   ('NightsWatch', 'CastleBlack'),
   ('Baratheon', 'KingsLanding'),
   ('Lannister', 'CasterlyRock'),
   ('Stark', 'Stormlands'),
   ('Martell', 'Dorne'),
   ('Targaryen', 'Highgarden');

INSERT INTO Skill VALUES
   ('Archery'),
   ('Politics'),
   ('Swordsmanship'),
   ('HorseRiding'),
   ('Leadership');

INSERT INTO WesterosiSkill VALUES
 (1001,'Archery'),
 (1001,'Politics'),
 (1002,'Archery'),
 (1002,'Politics'),
 (1004,'Politics'),
 (1004,'Archery'),
 (1005,'Politics'),
 (1005,'Archery'),
 (1005,'Swordsmanship'),
 (1006,'Archery'),
 (1006,'HorseRiding'),
 (1007,'HorseRiding'),
 (1007,'Archery'),
 (1009,'HorseRiding'),
 (1009,'Swordsmanship'),
 (1010,'Swordsmanship'),
 (1011,'Swordsmanship'),
 (1011,'HorseRiding'),
 (1011,'Politics'),
 (1011,'Archery'),
 (1012,'Politics'),
 (1012,'HorseRiding'),
 (1012,'Archery'),
 (1013,'Archery'),
 (1013,'Politics'),
 (1013,'HorseRiding'),
 (1013,'Swordsmanship'),
 (1014,'HorseRiding'),
 (1014,'Politics'),
 (1014,'Swordsmanship'),
 (1015,'Archery'),
 (1015,'Politics'),
 (1016,'HorseRiding'),
 (1016,'Politics'),
 (1017,'Swordsmanship'),
 (1017,'Archery'),
 (1018,'Politics'),
 (1019,'Swordsmanship');

INSERT INTO Predecessor VALUES
 (1004, 1013),
 (1005, 1013),
 (1019, 1013),
 (1009, 1017),
 (1009, 1011),
 (1012, 1001),
 (1010, 1007),
 (1006, 1010),
 (1017, 1011),
 (1018, 1001),
 (1003, 1008),
 (1014, 1012),
 (1011, 1003),
 (1018, 1003),
 (1010, 1003);

 INSERT INTO Knows VALUES
 (1011,1009),
 (1007,1016),
 (1011,1010),
 (1003,1004),
 (1006,1004),
 (1002,1014),
 (1009,1005),
 (1018,1009),
 (1007,1017),
 (1017,1019),
 (1019,1013),
 (1016,1015),
 (1001,1012),
 (1015,1011),
 (1019,1006),
 (1013,1002),
 (1018,1004),
 (1013,1007),
 (1014,1006),
 (1004,1014),
 (1001,1014),
 (1010,1013),
 (1010,1014),
 (1004,1019),
 (1018,1007),
 (1014,1005),
 (1015,1018),
 (1014,1017),
 (1013,1018),
 (1007,1008),
 (1005,1015),
 (1017,1014),
 (1015,1002),
 (1018,1013),
 (1018,1010),
 (1001,1008),
 (1012,1011),
 (1002,1015),
 (1007,1013),
 (1008,1007),
 (1004,1002),
 (1015,1005),
 (1009,1013),
 (1004,1012),
 (1002,1011),
 (1004,1013),
 (1008,1001),
 (1008,1019),
 (1019,1008),
 (1001,1019),
 (1019,1001),
 (1004,1003),
 (1006,1003),
 (1015,1003),
 (1016,1004),
 (1016,1006),
 (1008,1015),
 (1010,1008),
 (1017,1013),
 (1002,1001),
 (1009,1001),
 (1011,1005),
 (1014,1012);

\qecho 'Problem 1'
-- Develop appropriate insert and delete triggers that implement the primary
-- key and foreign key constraints that are specified for the Westerosi,
-- House, and ofHouse relations.
-- Your triggers should report appropriate error conditions. For this problem,
-- implement the triggers such that foreign key constraints are maintained
-- using the cascading delete semantics.
-- For a reference on cascading deletes associated with foreign keys mainte-
-- nance consult the PostgreSQL manual page
-- https://www.postgresql.orgdocs9.2ddl-constraints.html
-- Test your triggers using appropriate inserts and deletes.
CREATE OR REPLACE FUNCTION check_Westerosi_addkey_constraint() RETURNS trigger AS
$$
BEGIN 
  IF NEW.wid IN(SELECT wid FROM Westerosi) THEN
    RAISE EXCEPTION 'wid already exists';
  END IF;
  RETURN NEW;
END;
$$ LANGUAGE 'plpgsql';
 
CREATE TRIGGER check_Westerosi_addkey_
  BEFORE INSERT
  ON Westerosi
  FOR EACH ROW
  EXECUTE PROCEDURE check_Westerosi_addkey_constraint();
  
 
CREATE OR REPLACE FUNCTION check_Westerosi_delkey_constraint() RETURNS trigger AS
$$
BEGIN 
  IF OLD.wid IN(SELECT wid FROM OfHouse) THEN
    DELETE FROM OfHouse WHERE wid = OLD.wid;
  END IF;
  RETURN old;
END;
$$ LANGUAGE 'plpgsql';
 
CREATE TRIGGER check_Westerosi_delkey_
  BEFORE delete
  ON Westerosi
  FOR EACH ROW
  EXECUTE PROCEDURE check_Westerosi_delkey_constraint();
  
  
CREATE OR REPLACE FUNCTION check_House_pkey_constraint() RETURNS trigger AS
$$
BEGIN 
  IF NEW.hname IN(SELECT hname FROM House) THEN
    RAISE EXCEPTION 'hname already exists';
  END IF;
  RETURN NEW;
END;
$$ LANGUAGE 'plpgsql';
 
CREATE TRIGGER check_House_pkey_
  BEFORE INSERT
  ON House
  FOR EACH ROW
  EXECUTE PROCEDURE check_House_pkey_constraint();
  
  
 
CREATE OR REPLACE FUNCTION check_House_fkey_constraint() RETURNS trigger AS
$$
BEGIN 
  IF OLD.hname IN(SELECT hname FROM House) THEN
    DELETE FROM Ofhouse WHERE hname = OLD.hname;
  END IF;
  RETURN old;
END;
$$ LANGUAGE 'plpgsql';
 
CREATE TRIGGER check_House_fkey_
  BEFORE delete
  ON House
  FOR EACH ROW
  EXECUTE PROCEDURE check_House_fkey_constraint();
 
 
CREATE OR REPLACE FUNCTION check_OfHouse_pkey_constraint() RETURNS trigger AS
$$
BEGIN 
  IF NEW.wid IN(SELECT wid FROM OfHouse) THEN
    RAISE EXCEPTION 'wid already exists in OfHouse';
  END IF;
  IF NEW.wid NOT IN(SELECT wid FROM Westerosi) THEN
    RAISE EXCEPTION 'wid does not in Westerosi';
  END IF;
  IF NEW.hname NOT IN(SELECT hname FROM House) THEN
    RAISE EXCEPTION 'hname is not in House';
  END IF;
  RETURN NEW;
END;
$$ LANGUAGE 'plpgsql';
 
 
CREATE TRIGGER check_OfHouse_pkey_
  BEFORE INSERT
  ON OfHouse
  FOR EACH ROW
  EXECUTE PROCEDURE check_OfHouse_pkey_constraint();
 
 
CREATE OR REPLACE FUNCTION check_OfHouse_fkey_constraint() RETURNS trigger AS
$$
BEGIN 
  IF OLD.hname IN(SELECT hname FROM House) THEN
   update House set hname='' WHERE hname = OLD.hname;
   delete from House where hname='';
  END IF;
  IF OLD.wid IN(SELECT wid FROM Westerosi) THEN
   update Westerosi set wid=='' WHERE wid = OLD.wid;
   delete from Westerosi where wid='';
  END IF;
  RETURN old;
END;
$$ LANGUAGE 'plpgsql';
 
CREATE or replace TRIGGER check_OfHouse_fkey_
  before delete
  ON OfHouse
  FOR EACH ROW
  EXECUTE PROCEDURE check_OfHouse_fkey_constraint();


\qecho 'Problem 2'
-- Consider two relations R(A:integer,B:integer) and S(B:integer) and a view
-- with the following definition:
-- select distinct r.A
-- from R r, S s
-- where r.A > 10 and r.B = s.B;
-- Suppose we want to maintain this view as a materialized view called
-- V(A:integer) upon the insertion of tuples in R and in S. (You do not
-- have to consider deletions in this question.) Define SQL insert triggers
-- and their associated trigger functions on the relations R and S that imple-
-- ment this materialized view. Write your trigger functions in the language
-- ‘plpgsql.’ Make sure that your trigger functions act in an incremental way
-- and that no duplicates appear in the materialized view.
CREATE MATERIALIZED VIEW V AS
SELECT DISTINCT r.A
FROM R r, S s
Where r.A > 10 and r.B = s.B;

CREATE TABLE R(A int,B int);
CREATE TABLE S(B int);
CREATE OR REPLACE FUNCTION insertintoR() RETURNS TRIGGER AS
     $$
          BEGIN  
		  	   IF NEW.A > 10 AND NEW.B IN (SELECT S.B FROM S) THEN
               REFRESH MATERIALIZED VIEW V;
               RETURN NEW;
			   END IF;
          END;
     $$ LANGUAGE 'plpgsql';

CREATE TRIGGER insert_R
     AFTER INSERT 
     ON R
     FOR EACH STATEMENT
     EXECUTE PROCEDURE insertintoR();
	 
CREATE OR REPLACE FUNCTION insertintoS() RETURNS TRIGGER AS
     $$
          BEGIN  
		  	   IF NEW.B NOT IN (SELECT B FROM S)THEN
               REFRESH MATERIALIZED VIEW V;
               RETURN NEW;
			   END IF;
          END;
     $$ LANGUAGE 'plpgsql';

CREATE TRIGGER insert_S
     AFTER INSERT 
     ON R
     FOR EACH STATEMENT
     EXECUTE PROCEDURE insertintoS();
	 
\qecho 'Problem 3'
-- Consider the following on the WesterosiSkill relation. ”Each skill of a Wes-
-- terosi who belongs to house Lannister must also be a skill of a Westerosi
-- who belongs to the house NightsWatch.”
-- Write a trigger that maintains this constraint when inserting pairs (wid, skill)
-- into the WesterosiSkill table.

CREATE OR REPLACE FUNCTION check_skill_when_insert() RETURNS trigger AS
$$
BEGIN 
 if new.wid in (select wid from ofhouse where hname='Lannister') then
  IF NEW.skill not IN(SELECT skill FROM WesterosiSkill,ofhouse where ofhouse.wid = WesterosiSkill.wid and hname='NightsWatch') THEN
    RAISE EXCEPTION 'Each skill of a Westerosi who belongs to house Lannister must also be a skill of a Westerosi who belongs to the house NightsWatch.';
  END IF;
  end if;
  RETURN NEW;
END;
$$ LANGUAGE 'plpgsql';
 
CREATE TRIGGER tri_skill_insert
  BEFORE INSERT
  ON WesterosiSkill
  FOR EACH ROW
  EXECUTE PROCEDURE check_skill_when_insert();
  
\qecho 'Problem 4'
-- Consider the view WesterosiHasSkills(wid ,skills) which associates with each Westerosi, identified by a wid, his or her set of skills.
-- \begin{verbatim}
--     Create view WesterosiHasSkills as 
--             select distinct W.wid from 
--             Westerosi W, WesterosiSkill WS
--             where W.wid = WS.wid 
--             order by 1;
-- \end{verbatim}

-- Write a trigger that will delete all the skill records from
--  $WesterosiSkill$ relation when a Westerosi entry(wid) is deleted 
--  from the above $WesterosiHasSkills$ view. Show appropriate delete statements.

Create or replace view WesterosiHasSkills as
select distinct W.wid from
Westerosi W, WesterosiSkill WS
where W.wid = WS.wid
order by 1;
 
 
CREATE OR REPLACE FUNCTION delete_skill_from_view() RETURNS trigger AS
$$
BEGIN 
  IF OLD.wid IN(SELECT wid FROM WesterosiHasSkills) THEN
     delete from WesterosiSkill where  wid = OLD.wid;
       delete from ofhouse where  wid = OLD.wid;
       delete from Westerosi where  wid = OLD.wid;
  END IF;
  RETURN old;
END;
$$ LANGUAGE 'plpgsql';
 
 
CREATE or replace TRIGGER tri_delete_wid
  INSTEAD OF delete
  ON WesterosiHasSkills
  FOR EACH ROW
  EXECUTE PROCEDURE delete_skill_from_view();


\qecho 'Problem 5'
-- Find the hname of each house who only has Westerosis currently stationed
-- in ‘Winterfell’ or ‘CastleBlack’ location.
CREATE VIEW W_C AS
  SELECT w.wid
  FROM westerosi w
  WHERE w.wlocation = 'Winterfell' OR w.wlocation ='CastleBlack';
  
SELECT DISTINCT o.hname
FROM ofhouse o
WHERE NOT EXISTS(SELECT oh.wid
				FROM ofhouse oh
				WHERE oh.hname = o.hname
				EXCEPT
				SELECT * FROM W_C);

\qecho 'Problem 6'
/*
Find the wid of each Westerosi who knows all Westerosis who belongs to ‘Lannister’ house and makes wages that is equal to 55000.
*/
CREATE VIEW get_wid AS
          SELECT oh.wid AS wid
          FROM  OfHouse oh
          WHERE oh.wages = 55000 AND oh.hname = 'Lannister';

SELECT wid1 FROM knows WHERE wid2 IN 
(SELECT * FROM get_wid) 
GROUP BY wid1 HAVING SUM(1)=(SELECT SUM(1) FROM OfHouse WHERE hname='Lannister' AND wages='55000');


\qecho 'Problem 7'
/*
Find the pairs (s1, s2) of different Successors(Westerosis) such that some Predecessors of Successor1 are Predecessors of Successor2.
*/
CREATE FUNCTION get_preds(sucid int)
     RETURNS TABLE(predid INTEGER) AS
     $$
          SELECT p.predid
          FROM Predecessor p
          WHERE sucid = P.succid;
     $$ LANGUAGE SQL;
	 
SELECT DISTINCT S1.succid AS s1, S2.succid AS s2
FROM Predecessor s1, Predecessor s2
WHERE s1.succid <> s2.succid
AND EXISTS (SELECT predid
            FROM get_preds(s2.succid)
            INTERSECT
            SELECT predid
            FROM get_preds(s1.succid))
ORDER BY S1.succid;


\qecho 'Problem 8'
/*
Find the hname of each house that not only has Westerosis with ‘Politics’ in their skills set.
*/

CREATE OR REPLACE VIEW Hskill AS
SELECT oh.wid, oh.hname, ws.skill
FROM ofhouse oh, westerosiskill ws
WHERE oh.wid = ws.wid;

CREATE OR REPLACE VIEW politicians AS
SELECT DISTINCT hs.*
FROM Hskill hs
WHERE hs.wid IN (SELECT hs2.wid
                 FROM Hskill hs2
                 WHERE hs2.skill = 'Politics');
				 
SELECT DISTINCT o.hname
FROM ofhouse o
WHERE EXISTS(
    SELECT DISTINCT HS.wid
    FROM Hskill HS
    WHERE hs.hname = o.hname
    EXCEPT
    SELECT DISTINCT p.wid
    FROM politicians p);
	
\qecho 'Problem 9'
/*
Find the pairs (wid1, wid2) of different Westerosis such that Westerosi with wid1 and the Westerosi with wid2 knows same number of Westerosis.
*/
CREATE OR REPLACE FUNCTION totalKnows(widd int)
     RETURNS int AS
     $$
          SELECT COUNT(k.wid2)
          FROM Knows k
          WHERE k.wid1 = widd
     $$ LANGUAGE SQL;
	 
SELECT wid1,wid2 FROM 
(SELECT knows.wid1 AS wid1,COUNT(*) AS count1 FROM knows GROUP BY knows.wid1) w1
CROSS JOIN 
(SELECT knows.wid1 AS wid2,COUNT(*) AS count2 FROM knows GROUP BY knows.wid1) w2
WHERE w1.count1=w2.count2 AND wid1<>wid2;

\qecho 'Problem 10'
CREATE OR REPLACE VIEW minwage AS
SELECT oh.hname, MIN(OH.wages) AS Wage
FROM OfHouse oh
GROUP BY oh.hname;

CREATE OR REPLACE VIEW numofminwage AS
SELECT oh.hname, count(oh.wid) AS numMinWage
FROM ofHouse oh, minwage mw
WHERE oh.hname = mw.hname
AND oh.wages = mw.Wage
GROUP BY oh.hname;

CREATE OR REPLACE FUNCTION numOfKnows(wid int)
     RETURNS int AS
     $$
          SELECT COUNT(K.wid2)
          FROM Knows K
          WHERE K.wid1 = wid
     $$ LANGUAGE SQL;
	 
SELECT hname FROM 
(SELECT ofhouse.hname,ofhouse.wages,wid FROM ofhouse 
JOIN 
(SELECT hname,MIN(wages) AS MIN FROM ofhouse GROUP BY hname
) t2 ON ofhouse.wages = t2.min AND ofhouse.hname = t2.hname AND wid IN (
SELECT wid1 FROM knows GROUP BY wid1 HAVING COUNT(*)>=3)) t3 GROUP BY t3.hname HAVING COUNT(*)=2;



-- Connect to default database
\c postgres;

-- Drop database created for this assignment
DROP DATABASE haocsun;
