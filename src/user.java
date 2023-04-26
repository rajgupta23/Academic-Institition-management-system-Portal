import java.util.Locale;
import java.util.Scanner;

public class user {
    String userName,password;
    int type;
    Scanner scanner = new Scanner(System.in);
    public void loginIntoType(){
        String userType = null;
        if(type ==1)userType = "student";
        if(type ==2)userType = "faculty";
        if(type ==3)userType = "admin";
        login log = new login();
         boolean flag =  log.auth(this.type,this.userName,this.password);
         if(flag){
             System.out.println(String.format("logged in as a %s",userType));
             if(type ==1){
                 student st = new student();
                 st.userName = this.userName;
                 st.password = this.password;
                 st.assignStudentDetail();
                 while(true) {
                     System.out.println("\npress 1: to Add course\npress 2: to drop course\npress 3: to See grades\npress4: to see courses being offered in this semester \npress 5: to edit profile \npress 6: have i passed \npress 7: logout");
                     int workToDo = scanner.nextInt();
                     if (workToDo == 1) { //add
                         if(shared.addDropOpen){
                             System.out.println("Enter course code of the course you want to enrol in");
                             String courseCode = scanner.next();
                             st.addCourse1(courseCode.toLowerCase());
                         } else{
                             System.out.println("admin hasn't started the add-drop-course-date yet ;/ ");
                         }

                     } else if (workToDo == 2) {
                         System.out.println("Enter course code of the course you want to drop from");
                         String courseCode = scanner.next();
                         st.dropCourse(courseCode.toLowerCase());
                     } else if (workToDo == 3) {
                         st.getGrades();
                     } else if (workToDo == 4) {
                         if(shared.semStarted) {
                             st.showCourses();
                         } else {
                             System.out.println("Semester is not started yet ;/ ");
                         }
                     } else if (workToDo == 5) {
                         System.out.println("press 1: to change password\npress 2: to change mobile\npress 3: to exit from edit profile");
                         while (true) {
                             int type11 = scanner.nextInt();
                             if (type11 == 1) {
                                 System.out.println("Type new password");
                                 String xx = scanner.next();
                                 st.changePassword(xx);
                                 System.out.println("Password Changed");
                                 break;
                             } else if (type11 == 2) {
                                 System.out.println("Type new mobile");
                                 String xx = scanner.next();
                                 st.changeMobile(xx);
                                 System.out.println("Mobile Changed");
                                 break;
                             } else if(type11 == 3){
                                 break;
                             }else System.out.println("please type again :( ");
                         }
                     }else if(workToDo==6) {
                         st.haveIPassed();
                     } else if (workToDo ==7) {
                         break;
                     } else System.out.println("please type again :( ");
                 }

             } else if (type == 2) {
                 faculty ft = new faculty();
                 ft.userName = this.userName;
                 ft.password = this.password;
                 ft.assignFacultyDetail();
                 while(true) {
                     System.out.println("\npress 1: to offer a course\npress 2: to cancel course\npress 3: to See grades\npress 4: to see courses being offered in this semester\npress 5: to edit profile \npress 6: give grades for a course\n press 7 : to logout");
                     int workToDo = scanner.nextInt();
                     if (workToDo == 1) {
                         System.out.println("Enter course code of the course you want to offer");
                         String courseCode = scanner.next();
                         System.out.println("Enter batches eligible for the course like '2020,2021' or '2022' without quotes . 'Make sure if there are more than one batch eligible please write them comma separated without space after or before commas'");
                         String batchesEligible = scanner.next();
                         System.out.println("Enter departments eligible for the course like 'cs,ee' or 'cs' without quotes . 'Make sure if there are more than one departments eligible please write them comma separated without space after or before commas'");
                         String deptEligible = scanner.next();
                         System.out.println("Please enter cgpa criteria for students like 7.5 ");
                         String cgpReq = scanner.next();
                         ft.offerCourse(courseCode.toLowerCase(),batchesEligible,deptEligible.toLowerCase(),Double.parseDouble(cgpReq));
                     } else if (workToDo == 2) {
                         System.out.println("Enter course code of the course you want to cancel");
                         String courseCode = scanner.next();
                         ft.cancelOffering(courseCode.toLowerCase());
                     } else if (workToDo == 3) {
                         System.out.println("enter username/entryNumber of the student you want to see grades");
                         String stUname = scanner.next();
                         ft.getGrades(stUname.toLowerCase());

                     } else if (workToDo == 4) {
                         if(shared.semStarted) {
                             ft.showCourses();
                         }else{
                             System.out.println("semester is not started yet");
                         }
                     } else if (workToDo == 5) {
                         System.out.println("press 1: to change password\npress 2: to change mobile\npress 3: to exit from edit profile");
                         while (true) {
                             int type11 = scanner.nextInt();
                             if (type11 == 1) {
                                 System.out.println("Type new password");
                                 String xx = scanner.next();
                                 ft.changePassword(xx);
                                 break;
                             } else if (type11 == 2) {

                                 System.out.println("Type new mobile");
                                 String xx = scanner.next();
                                 ft.changeMobile(xx);
                                 break;
                             }else if(type11==3){
                                 break;
                             }else {
                                 System.out.println("please type again");
                             }
                         }
                     }
                     else if(workToDo==6) {
                         if(shared.GradeSubmissionAllowed) {
                             System.out.println("enter the course code of the course you want to give grades for");
                             String courseid = scanner.next();
                             System.out.println("enter filepath");
                             String path = scanner.next();
                             ft.giveGrades(courseid.toLowerCase(), path);
                         }else System.out.println("admin hasn't started grade submission in calender");
                     } else if (workToDo ==7) {
                         break;
                     } else System.out.println("please type again :( ");
                 }

             } else {
                 admin ad = new admin();
                 ad.userName = this.userName;
                 ad.password = this.password;
                 ad.assignAdminDetail();
                 while(true){
                     System.out.println("\npress 1: to Add a Student\npress 2: to Add a faculty\npress 3: for other admin work\npress 4: logout");
                     int workToDo = scanner.nextInt();
                     if (workToDo == 1) {
                         System.out.println("Enter name of student");
                         String name = scanner.next();
                         System.out.println("Enter user name/entry no of student ''Ps - It has to be unique''");
                         String userName = scanner.next();
                         System.out.println("Enter email of student");
                         String email = scanner.next();
                         System.out.println("Enter password of student");
                         String password = scanner.next();
                         System.out.println("Enter mobile of student");
                         String mobile = scanner.next();
                         System.out.println("Enter entry year of student");
                         String entryYear = scanner.next();
                         System.out.println("Enter Department(ee,cs,ch,me etc) of student");
                         String dept = scanner.next();
                         ad.addStudent(name, userName.toLowerCase(), email, password, mobile, Integer.parseInt(entryYear), dept.toLowerCase());

                     }
                     else if (workToDo == 2) {
                         System.out.println("Enter name of faculty");
                         String name = scanner.next();
                         System.out.println("Enter user name of faculty ''Ps - It has to be unique''");
                         String userName = scanner.next();
                         System.out.println("Enter email of faculty");
                         String email = scanner.next();
                         System.out.println("Enter password of faculty");
                         String password = scanner.next();
                         System.out.println("Enter mobile of faculty");
                         String mobile = scanner.next();
                         System.out.println("Enter joining year of faculty");
                         String entryYear = scanner.next();
                         System.out.println("Enter Department(ee,cs,ch,me etc) of faculty");
                         String dept = scanner.next();
                         ad.addFaculty(name, userName.toLowerCase(), email, password, mobile, Integer.parseInt(entryYear), dept.toLowerCase());
                     }
                     else if (workToDo == 3) { // other works to do
                         while (true) {
                             System.out.println("\npress 1: to start new semester\npress 2 : to stop the 'add and drop' of courses \npress 3 : to start grade submission \n press4: to end semester \npress 5: to add course in courseOffering in this semester\npress 6: to add new course in course catalog\npress 7: to make Core/mandatory Courses for a batch \npress 8 : to get grades\npress 9: to generate transcript\npress 10: Go back");
                             int wor1 = scanner.nextInt();
                             if (wor1 == 1) {
                                 ad.startSemester();
                                 System.out.println(String.format("semester %d of year %d has been started", shared.currentSemester, shared.currentYear));
                             } else if (wor1 == 2) {
                                 shared.addDropOpen = false;
                                 System.out.println(String.format("Add drop for semester %d of year %d has been stopped", shared.currentSemester, shared.currentYear));
                             } else if (wor1 == 3) {
                                 shared.GradeSubmissionAllowed = true;
                                 System.out.println(String.format("grade submission for semester %d of year %d has been started", shared.currentSemester, shared.currentYear));
                             } else if (wor1 == 4) {
                                 shared.semStarted = false;
                                 shared.addDropOpen=false;
                                 shared.GradeSubmissionAllowed=false;
                                 System.out.println(String.format("semester %d of year %d has been completed", shared.currentSemester, shared.currentYear));
                             } else if (wor1 == 5) {
                                 System.out.println("Enter course code");
                                 String coursecode = scanner.next();
                                 System.out.println("Enter course name");
                                 String coursename = scanner.next();
                                 System.out.println("l(lecture per week)");
                                 String l = scanner.next();
                                 System.out.println("t(tutorial per week)");
                                 String t = scanner.next();
                                 System.out.println("p(practical hour per week)");
                                 String p = scanner.next();
                                 System.out.println("c(credit)");
                                 String c = scanner.next();
                                 System.out.println("Enter name of faculty");
                                 String facultyname = scanner.next();
                                 System.out.println("Enter user name of faculty");
                                 String facultyUsername = scanner.next();
                                 System.out.println("Enter course pre requisites (like cs301,cs302)");
                                 String courseprereq = scanner.next();
                                 System.out.println("Enter cg criteria");
                                 String cgreq = scanner.next();
                                 System.out.println("Enter batches eligible (like 2021,2022)");
                                 String batches = scanner.next();
                                 System.out.println("Enter departments eligible");
                                 String dept = scanner.next();
                                 ad.addCourseInCourseOffering(coursecode, coursename, Integer.parseInt(l), Integer.parseInt(t), Integer.parseInt(p), Double.parseDouble(c), facultyUsername, facultyname, shared.currentYear, shared.currentSemester, courseprereq, Double.parseDouble(cgreq), batches, dept);
                             } else if (wor1 == 6) {
                                 System.out.println("Enter course code");
                                 String coursecode = scanner.next();
                                 System.out.println("Enter course name");
                                 String coursename = scanner.next();
                                 System.out.println("l(lecture per week)");
                                 String l = scanner.next();
                                 System.out.println("t(tutorial per week)");
                                 String t = scanner.next();
                                 System.out.println("p(practical hour per week)");
                                 String p = scanner.next();
                                 System.out.println("c(credit)");
                                 String c = scanner.next();
                                 System.out.println("Enter course pre requisites (like cs301,cs302)");
                                 String courseprereq = scanner.next();
                                 ad.addCoursesToCourseCatalog(coursecode, coursename, Integer.parseInt(l), Integer.parseInt(t), Integer.parseInt(l), Double.parseDouble(c), courseprereq);
                             } else if (wor1 == 7) {
                                 ad.makeCoreCourses();
                             } else if (wor1 == 8) {
                                 System.out.println("enter entry no of student you want to see grade for. ");
                                 String ss = scanner.next();
                                 ad.getGrades(ss);
                             } else if (wor1 == 9) {
                                 System.out.println("enter entry no of student you want to generate transcript");
                                 String ss = scanner.next();
                                 ad.generateTranscript(ss);
                             } else if (wor1 == 10) {
                                 break;
                             } else {
                                 System.out.println("Please try again :( ");
                             }
                         }
                     }
                     else if(workToDo ==4){
                         break;
                     }
                     else {
                         System.out.println("type again ;( ");
                     }
                 }
             }
         }
         else{
             System.out.println("wrong credentials");
         }
    }


}

