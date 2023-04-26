# How to run --
1. I used intelliJ ide build script, so it would be better to have it otherwise you have to use plugins to easily run on different IDE.
2. Once unzipped and opened run main
3. Also, I tried to make project as interactive as possible so all the instructions will be there as you go through.


# Assumptions - 

1. Student has no access to course catalog since only admin is allowed to make new courses (via senate meeting and all as discussed in class)
2. transcript contains only completed courses not running ones also it will be called at the end of sem or if called earlier will just contain the courses till prev sem
3. grade point is taken not ABC ..
4. user names has to be unique
5. username can never be changed
6. only F(0) was considered as fail
7. everything is comma separated(and no space in between them)
8. batch ,dept and cg creterian is assigned by prof.
9. there is only one admin. whose credentials are as **adminU, pass** but admin can change it of course.
10. csv formate is studentid,grade point 

    ![img_1.png](img_1.png)


11. admin has to write btp as mandatory course to be completed too, while making core/mandtory course for a student
12. passing = all mendatory courses and minimum of total credits earned.
13. Tried to make everything as case-insensitive as possible but still please try to input everything in lowercase if possible
14. add drop starts as soon as new sem start
15. assumming if admin adds a course in course offering of current semester it was already in catalog
16. no cg criteria implies cgpaReq = 0
17. usernames should not have space in between
18. please enter **,** if no course pre req
19. please give absoulute path whenever instructor is asked to give grade file path like -->  C:\\Users\\rg230\\IdeaProjects\\cs3051\\src\\file.csv

# Database requirement 
1. Must have a database named cs305-mini-project,
2. Please connect to it in table class itself since everyone has different username and password
3. Now run following command to make required tables in database -

```sql
create table students(name varchar(50),username varchar(50),email varchar(20),password varchar(50),mobile varchar(20),entryYear integer);
create table faculties((name varchar(50),username varchar(50),email varchar(20),password varchar(50),mobile varchar(20));
create table admin(name varchar(50),username varchar(50),email varchar(20),password varchar(50),mobile varchar(20));
create table courseoffering(courseid varchar, coursename varchar, l integer, t integer, p integer, c double precision, facultyusername varchar, facultyname varchar, year integer, semester integer, courseprereq varchar, cgpareq real, batch varchar, dept varchar);
create table coursecatalog(courseid varchar, coursename varchar, l integer, t integer, p integer, c double precision,courseprereq varchar);
create table coursetaken(courseid varchar, studentid varchar, credit double precision, gradepoint double precision, year integer,semester integer);
create table enroling(courseid varchar, credit double precision, year integer,semester integer,facultyusername varchar, studentusername varchar);
```