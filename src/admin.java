import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
//TODO:specify min cred otherwise default 145
public class admin extends user{

    String name,email,mobile;

    tables t3 = new tables();
    Connection con = t3.getConnection();
    Statement statement;

    public short assignAdminDetail(){
        try {
            String query = String.format("select * from admin where username = '%s'", this.userName);
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()){
                this.name = rs.getString("name");
                this.email = rs.getString("email");
                this.mobile = rs.getString("mobile");
            }
        } catch (Exception e){
            System.out.println();
        }
        return 0;
    }
    public short changePassword(String newPass){ // like phone,email
        this.password = newPass;
        try {
            String query = String.format("update faculties set password = '%s' where username = '%s'",newPass,this.userName);
            Statement statement = con.createStatement();
            statement.executeUpdate(query);
        } catch (Exception e){
            System.out.println();
        }
        return 0;
    }

    public short changeMobile(String newMobile){ // like phone,email
        this.password = newMobile;
        try {
            String query = String.format("update faculties set password = '%s' where username = '%s'",newMobile,this.userName);
            Statement statement = con.createStatement();
            statement.executeUpdate(query);
        } catch (Exception e){
            System.out.println(e);
        }
        return 0;
    }
    public void addStudent(String name,String userName,String email,String password,String mobile,Integer entryYear,String dept){
        try {
            statement = con.createStatement();
            String query = String.format("insert into students (name,userName,email,password,mobile,entryYear,dept)values('%s','%s','%s','%s','%s',%d,'%s')",name,userName,email,password,mobile,entryYear,dept);
            statement.executeUpdate(query);
            System.out.println("student added");
        }catch (Exception e){
            System.out.println();
        }
    }
    public void addFaculty(String name,String userName,String email,String password,String mobile,Integer entryYear,String dept){
        try {
            statement = con.createStatement();
            String query = String.format("insert into faculties (name,userName,email,password,mobile,entryYear,dept)values('%s','%s','%s','%s','%s',%d,'%s')",name,userName,email,password,mobile,entryYear,dept);
            statement.executeUpdate(query);
            System.out.println("faculty added");
        }catch (Exception e){
            System.out.println();
        }
    }

    public short startSemester(){
        shared.currentYear = (shared.currentSemester==1)?shared.currentYear:shared.currentYear+1;
        shared.currentSemester = (shared.currentSemester==1)?2:1;
        shared.semStarted = true;
        shared.addDropOpen=true;
        return 0;
    }

    public void makeCoreCourses(){
        System.out.println("enter year of batch for which you want decide core/mandatory courses table:");
        int yy = scanner.nextInt();
        String query = String.format("create table corecourses%d (dept varchar(50), courseid varchar(50),credit real)",yy);
        try {
            statement = con.createStatement();
            statement.executeUpdate(query);
            System.out.println(String.format("core courses for the batch %d",yy));
        }catch (Exception e){
            System.out.println("LOL");
            System.out.println(e);
        }
        String table = String.format("corecourses%d",yy);
        System.out.println("Now keep adding courses as space separated to add into core courses for each department.\n for example if you want to add a course you have to add like 'dept course_code credit' without quotes like:  cs cs301 3 \n Once you are done press 0 0 0");
        String dept,cid;Integer cred;
        while(true){
            dept = scanner.next();
            cid = scanner.next();
            if(dept.equals("0")){
                break;
            }
            cred = scanner.nextInt();

            String query1 = String.format("insert into %s(dept,courseid,credit)values('%s','%s',%d)",table,dept,cid,cred);
            try {
                statement = con.createStatement();
                statement.executeUpdate(query1);
                System.out.println(String.format("core courses %s for the batch %d is added",cid,yy));
            }catch (Exception e){
//                System.out.println("LOL");
                System.out.println(e);
            }

        }
    }

    public void addCoursesToCourseCatalog(String courseId,String courseName,Integer l,Integer t,Integer p,double c,String coursesPreRequisites){

        String query = String.format("insert into courseCatalog(courseId,courseName,l,t,p, c,preRequisites)values('%s','%s',%d,%d,%d,%f,'%s')",courseId,courseName,l,t,p,c,coursesPreRequisites);
        try {
            statement = con.createStatement();
            statement.executeUpdate(query);
            System.out.println("course added to catalog");
        }catch (Exception e){
//            System.out.println("LOL");
            System.out.println(e);
        }
    }

    public void addCourseInCourseOffering(String courseId,String courseName,Integer l,Integer t,Integer p,double c,String facultyUserName,String facultyName,Integer year,Integer semester,String coursePreRequisites, double cgpaReq,String batch, String dept){

        String query = String.format("insert into courseOffering(courseId,courseName,l,t,p, c,facultyUserName,facultyName,year,semester,coursePreReq,cgpaReq,batch,dept)values('%s','%s',%d,%d,%d,%f,'%s','%s',%d,%d,'%s',%f,'%s','%s')",courseId,courseName,l,t,p,c,facultyUserName,facultyName,year,semester,coursePreRequisites,cgpaReq,batch,dept);

        try {
            System.out.println("YES");
            statement = con.createStatement();
            statement.executeUpdate(query);
            System.out.println("course added to course offering");
        }catch (Exception e){
            System.out.println("NO");
            System.out.println(e);
        }

    }
    public short getGrades(String userNameOfStudent){
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
            System.out.println();
        }
        return 0;
    }

    public void generateTranscript(String studentId){

        try {
            File myObj = new File("transcript_" + studentId+"_.txt");
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            FileWriter myWriter = new FileWriter("transcript_" + studentId+"_.txt",true);
            myWriter.write("courseid  |  credit  |  gradepoint | year| semsester\n");
            myWriter.close();
        } catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }


        try {
            System.out.println("courseid  |  credit  |  gradepoint | year| semsester");
            String query = String.format("select * from coursetaken where studentid = '%s'",studentId);
            System.out.println(query);
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()){

                System.out.println(rs.getString("courseid") + " | " + rs.getString("credit") + " | " + rs.getString("gradepoint") +" | " +  rs.getString("year") + " | " + rs.getString("semester"));
                try {
                    FileWriter myWriter = new FileWriter("transcript_" + studentId+"_.txt",true);
                    myWriter.write(rs.getString("courseid") + " | " + rs.getString("credit") + " | " + rs.getString("gradepoint") +" | " +  rs.getString("year") + " | " + rs.getString("semester") + "\n");
                    myWriter.close();
                } catch (Exception e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }
            }
        } catch (Exception e){
            System.out.println(e);
        }
//        return ret;
    }
}
