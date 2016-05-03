DELETE FROM students;
DELETE FROM groups;

INSERT INTO groups VALUES(nextval('group_id_seq'), 6107, 'Faculty №1');
INSERT INTO students VALUES(nextval('stud_id_seq'), currval('group_id_seq'), 'Student1', '1995-01-01');
INSERT INTO students VALUES(nextval('stud_id_seq'), currval('group_id_seq'), 'Student2', '1996-01-02');

INSERT INTO groups VALUES(nextval('group_id_seq'), 6108, 'Faculty №1');
INSERT INTO students VALUES(nextval('stud_id_seq'), currval('group_id_seq'), 'Student3', '1997-03-05');
INSERT INTO students VALUES(nextval('stud_id_seq'), currval('group_id_seq'), 'Student4', '1998-03-06');

INSERT INTO groups VALUES(nextval('group_id_seq'), 6207, 'Faculty №2');
INSERT INTO students VALUES(nextval('stud_id_seq'), currval('group_id_seq'), 'Student5', '1999-04-09');
INSERT INTO students VALUES(nextval('stud_id_seq'), currval('group_id_seq'), 'Student6', '2000-04-10');
INSERT INTO students VALUES(nextval('stud_id_seq'), currval('group_id_seq'), 'Student7', '2001-05-12');

INSERT INTO groups VALUES(nextval('group_id_seq'), 6209, 'Faculty №3');
INSERT INTO students VALUES(nextval('stud_id_seq'), currval('group_id_seq'), 'Student8', '2002-06-13');

INSERT INTO groups VALUES(nextval('group_id_seq'), 1125, 'Faculty of Magic');
INSERT INTO students VALUES(nextval('stud_id_seq'), currval('group_id_seq'), 'Student9', '2003-07-15');
INSERT INTO students VALUES(nextval('stud_id_seq'), currval('group_id_seq'), 'Student10', '2004-08-16');

INSERT INTO groups VALUES(nextval('group_id_seq'), 1126, 'Faculty of Black Magic');
INSERT INTO students VALUES(nextval('stud_id_seq'), currval('group_id_seq'), 'Student11', '2005-09-16');
INSERT INTO students VALUES(nextval('stud_id_seq'), currval('group_id_seq'), 'Student12', '2006-10-17');
INSERT INTO students VALUES(nextval('stud_id_seq'), currval('group_id_seq'), 'Student13', '2007-11-20');

INSERT INTO groups VALUES(nextval('group_id_seq'), 1127, 'Faculty of History');
INSERT INTO students VALUES(nextval('stud_id_seq'), currval('group_id_seq'), 'Student14', '2008-12-25');

--COMMIT;

--SELECT stud_name, faculty, group_number, enroll_date
--FROM students, groups
--WHERE students.group_id = groups.id;