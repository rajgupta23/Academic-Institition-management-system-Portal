# How to run --
1. I used intelliJ ide build script, so it would be better to have it otherwise you have to use plugins to easily run on different IDE.


```sql
create table students(name varchar(50),username varchar(50),email varchar(20),password varchar(50),mobile varchar(20),entryYear integer);
create table faculties((name varchar(50),username varchar(50),email varchar(20),password varchar(50),mobile varchar(20));
create table admin(name varchar(50),username varchar(50),email varchar(20),password varchar(50),mobile varchar(20));
create table courseoffering(courseid varchar, coursename varchar, l integer, t integer, p integer, c double precision, facultyusername varchar, facultyname varchar, year integer, semester integer, courseprereq varchar, cgpareq real, batch varchar, dept varchar);
create table coursecatalog(courseid varchar, coursename varchar, l integer, t integer, p integer, c double precision,courseprereq varchar);
create table coursetaken(courseid varchar, studentid varchar, credit double precision, gradepoint double precision, year integer,semester integer);
create table enroling(courseid varchar, credit double precision, year integer,semester integer,facultyusername varchar, studentusername varchar);
```
