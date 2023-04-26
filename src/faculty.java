import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class faculty extends user{

    String name,email,mobile,dept;
    int entryYear;
    tables t2 = new tables();
    Connection con = t2.getConnection();
    public short assignFacultyDetail(){
        try {
            String query = String.format("select * from faculties where username = '%s'", this.userName);
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(query);
            Integer tt = 0;
            while (rs.next()){
                tt++;
                System.out.println(rs.getString("email"));
                this.name = rs.getString("name");
                this.email = rs.getString("email");
                this.mobile = rs.getString("mobile");
                this.dept = rs.getString("dept");
                this.entryYear = Integer.parseInt(rs.getString("entryyear"));
            }
//            System.out.println(tt);
            return 1;
        } catch (Exception e){
            System.out.println(e);
        }

        return 0;
    }

    public short changePassword(String newPass){ // like phone,password ***Unique ,name ,email ,userName so cant be changed.
        this.password = newPass;
        try {
            String query = String.format("update faculties set password = '%s' where username = '%s'",newPass,this.userName);
            Statement statement = con.createStatement();
            statement.executeUpdate(query);
            return 1;
        } catch (Exception e){
            System.out.println(e);
        }
        return 0;
    }

    public short changeMobile(String newMobile){
        this.email = newMobile;
        try {
            String query = String.format("update faculties set email = '%s' where username = '%s'",newMobile,this.userName);
            Statement statement = con.createStatement();
            statement.executeUpdate(query);
            return 1;
        } catch (Exception e){
            System.out.println(e);
        }
        return 0;
    }

    public short offerCourse(String courseCode, String batch, String deptt, double cgpareq){  // and Remove course


        boolean inCatalog=false,someOneElseAlreadyOfferedIT=false;
        String courseName="",coursePreReq="";
        double c=0;
        int l=0,t=0,p=0;
        try {
            String query = String.format("select * from coursecatalog where courseid = '%s'",courseCode);
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()){
                inCatalog = true;
                c = Double.parseDouble(rs.getString("c"));
                l = Integer.parseInt(rs.getString("l"));
                t = Integer.parseInt(rs.getString("t"));
                p = Integer.parseInt(rs.getString("p"));
                courseName = rs.getString("coursename");
                coursePreReq = rs.getString("prerequisites");
            }
        } catch (Exception e){
            System.out.println(e);
        }
        try {
            String query = String.format("select * from courseoffering where courseid = '%s' and year = %d and semester = %d",courseCode,shared.currentYear,shared.currentSemester);
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()){
                someOneElseAlreadyOfferedIT = true;
            }
        } catch (Exception e){
            System.out.println(e);
        }

        if(inCatalog && !someOneElseAlreadyOfferedIT){
//            System.out.println("Yeah");
            try {
                String query = String.format("insert into courseOffering(courseid,coursename,l,t,p, c,facultyusername,facultyname,year,semester,courseprereq,cgpareq,batch,dept)values('%s','%s',%d,%d,%d,%f,'%s','%s',%d,%d,'%s',%f,'%s','%s')",courseCode,courseName,l,t,p,c,this.name,this.userName, shared.currentYear,shared.currentSemester,coursePreReq,cgpareq,batch,deptt);
                Statement statement = con.createStatement();
                statement.executeUpdate(query);
                System.out.println("you are offering the course " + courseCode);
                return 1;
            } catch (Exception e){
                System.out.println();
            }
        }else{
            System.out.println("NO");
            System.out.println("either course is not in course catalog or someone already offering this course in this semester :{ ");
        }

        return 0;
    }

    public void cancelOffering(String courseCode){
        boolean yesHeISOffering = false;
        try {
//            System.out.println("query is  >> ");
            String query = String.format("select * from courseoffering where courseid = '%s' and facultyusername = '%s' and year = %d and semester = %d",courseCode,this.userName,shared.currentYear,shared.currentSemester);
//            System.out.println(query);
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()){
                System.out.println("YYESS");
                yesHeISOffering = true;
            }
        } catch (Exception e){
            System.out.println(e);
        }

        if(yesHeISOffering){ //delete from courseOffering
            try {
                String query = String.format("delete from courseoffering where  courseid = '%s' and facultyusername = '%s' and year = %d and semester = %d", courseCode,this.userName, shared.currentYear,shared.currentSemester);
                Statement statement = con.createStatement();
                statement.executeUpdate(query);

            } catch (Exception e){
                System.out.println(e);
            }
            System.out.println(String.format("Course = '%s' has been canceled for the year = %d and semester = %d",courseCode,shared.currentYear,shared.currentSemester));

            try { // delete already enrolled students.
                String query = String.format("delete from enroling where  courseid = '%s' and year = %d and semester = %d", courseCode,shared.currentYear,shared.currentSemester);
                Statement statement = con.createStatement();
                statement.executeUpdate(query);

            } catch (Exception e){
                System.out.println(e);
            }

        }

        else{
            System.out.println("you are not offering this course in this semester so you can't cancel it :) ");
        }



    }

    public void showCourses(){
        System.out.println("courseid | coursename  | l | t | p |  c  | facultyusername | facultyname | year | semester | courseprereq | cgpareq |   batch   | dept");
        try {
            tables t2 = new tables();
            Connection con = t2.getConnection();
            String query = String.format("select * from courseOffering where year = %d and semester = %d", shared.currentYear,shared.currentSemester);
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()){
                System.out.println(rs.getString("courseid") + " | " + rs.getString("coursename") +" | " + rs.getString("l") +" | " + rs.getString("t") +" | " + rs.getString("p") +" | " + rs.getString("c") +" | " + rs.getString("facultyusername") +" | " + rs.getString("facultyname") +" | " + rs.getString("year") +" | " + rs.getString("semester") + " | " + rs.getString("courseprereq") +" | " + rs.getString("cgpareq") +" | " + rs.getString("batch") +" | " + rs.getString("dept"));
            }
        } catch (Exception e){
            System.out.println();
        }
    }

    public void giveGrades(String courseCode,String filePath){ //grades can only be given to current year+sem

        boolean yesHeISOffering = false;
        try {
//            System.out.println("query is  >> ");
            String query = String.format("select * from courseoffering where courseid = '%s' and facultyusername = '%s' and year = %d and semester = %d",courseCode,this.userName,shared.currentYear,shared.currentSemester);
//            System.out.println(query);
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()){
                System.out.println("YYESS");
                yesHeISOffering = true;
            }
        } catch (Exception e){
            System.out.println();
        }
        if(yesHeISOffering){
            try {
//                System.out.println("query is  >> ");
                String query = String.format("select * from enroling where courseid = '%s' and facultyusername = '%s' and year = %d and semester = %d",courseCode,this.userName,shared.currentYear,shared.currentSemester);
//                System.out.println(query);
                Statement statement = con.createStatement();
                ResultSet rs = statement.executeQuery(query);
                while (rs.next()){
                    Map<String, Integer> map = new HashMap<>();
                    String line = "";
                    String splitBy = ",";
                    try {
                        //parsing a CSV file into BufferedReader class constructor
                        BufferedReader br = new BufferedReader(new FileReader(filePath));
                        while ((line = br.readLine()) != null)
                        //returns a Boolean value
                        {
                            String[] employee = line.split(splitBy);
//                            System.out.println(employee[0] + " " + employee [1]);
                            map.put(employee[0],Integer.parseInt(employee[1]));
                        }
                    }
                    catch(Exception e) {
                        e.printStackTrace();
                    }
                    String studentid = rs.getString("studentusername");
                    double cred = Double.parseDouble(rs.getString("credit"));
                    try {
                        String query1 = String.format("insert into coursetaken(courseid , studentid ,credit , gradepoint, year, semester)values('%s','%s',%f,%d,%d,%d)",courseCode,studentid,cred,map.get(studentid),shared.currentYear,shared.currentSemester);
                        System.out.println("q1 = " +  query1);
                        Statement statement1 = con.createStatement();
                        statement1.executeUpdate(query1);
                        System.out.println("grades has been assigned :) ");
                    }catch (Exception e){
                        System.out.println(e);
                    }
                }
            } catch (Exception e){
                System.out.println(e);
            }

        }else {
            System.out.println("You are not offering this course so you can't give grades to the students enrolled in this course :( ");
        }


    }
    public void getGrades(String userNameOfStudent){
        try {
            System.out.println("courseid  |  credit  |  gradepoint");
            String query = String.format("select * from coursetaken where studentid = '%s'",userNameOfStudent);
            System.out.println(query);
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()){
                System.out.println(rs.getString("courseid") + " | " + rs.getString("credit") + " | " + rs.getString("gradepoint"));
            }
        } catch (Exception e){
            System.out.println(e);
        }
    }

}
