-- These objects are meant to be createrd in Oracle 10g.

CREATE TABLE department(DNO NUMBER (10),NAME VARCHAR2 (50),LOCATION VARCHAR2 (50));

create or replace
PACKAGE BODY PAQPRUEBA
AS
	PROCEDURE insert_object (d dept_array)
	AS
		BEGIN
		FOR i IN d.FIRST .. d.LAST
		LOOP
		INSERT INTO department
		VALUES (d (i).dno,d (i).name,d (i).location);
		END LOOP;
	END insert_object;
END PAQPRUEBA;

create or replace
TYPE dept_array AS TABLE OF ITO_department_type;

create or replace
TYPE ITO_department_type AS OBJECT (
	DNO NUMBER (10),
	NAME VARCHAR2 (50),
	LOCATION VARCHAR2 (50)
);
