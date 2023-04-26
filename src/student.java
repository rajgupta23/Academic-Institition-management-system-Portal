import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Vector;

public class student extends user{
    String name,email,mobile,dept;
    int entryYear;
    tables t2 = new tables();
    Connection con = t2.getConnection();
    public short changePassword(String newPass){
        this.password = newPass;
        try {
            String query = String.format("update students set password = '%s' where username = '%s'",newPass,this.userName);
            Statement statement = con.createStatement();
            statement.executeUpdate(query);
            return 1;
        } catch (Exception e){
            System.out.println(e);
        }
        return 0;
    }
    public short changeMobile(String newMobile){
        this.mobile = newMobile;
        try {
            String query = String.format("update students set mobile = '%s' where username = '%s'",newMobile,this.userName);
            Statement statement = con.createStatement();
            statement.executeUpdate(query);
            return 1;
        } catch (Exception e){
            System.out.println(e);
        }
        return 0;
    }
    public short assignStudentDetail(){
        try {
            String query = String.format("select * from students where username = '%s'", this.userName);
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
    public double getCredits(int year,int sem){
        double ret = 0;
        try {
            String query = String.format("select * from courseTaken where studentid = '%s' and year = %d and semester = %d", this.userName,year,sem);
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(query);
            int tt = 0;

            while (rs.next()){
                tt++;
                ret += Double.parseDouble(rs.getString("credit"));
            }
//            System.out.println(tt);
        } catch (Exception e){

            System.out.println(e);
        }
        return ret;
    }
    public double getGradePoint(int year,int sem){
        double ret = 0;
        try {
            String query = String.format("select * from courseTaken where studentid = '%s' and year = %d and semester = %d", this.userName,year,sem);
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()){
                ret = ret + Double.parseDouble(rs.getString("credit")) * Double.parseDouble(rs.getString("gradepoint"));
            }
        } catch (Exception e){

            System.out.println();
        }
        return ret;
    }

    public double getCurrentSemCredits(){
        double ret = 0;
        try {
            String query = String.format("select * from enroling where studentusername = '%s' and year = %d and semester = %d", this.userName,shared.currentYear,shared.currentSemester);
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(query);
            int tt = 0;

            while (rs.next()){
                tt++;
                ret += Double.parseDouble(rs.getString("credit"));
            }
//            System.out.println(tt);
        } catch (Exception e){

            System.out.println();
        }
        return ret;
    }

    public short addCourse1(String courseCode){
        String batches="",depts="",coursesPreReq = "",facultyUName="";
        double credit = 0,cg = 0 ,cgpaReq = 0,creditsAlreadyEnrolledFor = getCurrentSemCredits();
        boolean courseAvailableInThisSem = false,batchCheck = false,creditLimitCheck = false,cgpaCheck = false,coursesPreReqCheck = false,deptCheck = false, finalCheck = false;

        try{
            String query = String.format("select * from courseOffering where courseid = '%s' and year = %d and semester = %d", courseCode, shared.currentYear,shared.currentSemester);
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()){
                courseAvailableInThisSem = true;
                batches = batches + rs.getString("batch");
                depts = depts + rs.getString("dept");
                coursesPreReq+=rs.getString("courseprereq");
                facultyUName+=rs.getString("facultyusername");
                credit = Double.parseDouble(rs.getString("c"));
                cgpaReq =  Double.parseDouble(rs.getString("cgpareq"));
            }
        }catch (Exception e){
            System.out.println(e);
        }
        String[] batchArr = batches.split(",");
        String[] deptArr = depts.split(",");
        if(!courseAvailableInThisSem){
            System.out.println("course is not available to be enrolled in");
            return 0;
        }
        for (String x:batchArr)if(Integer.parseInt(x) == this.entryYear)batchCheck=true;
        for (String x:deptArr)
            if (x.equals(this.dept)) {
                deptCheck = true;
                break;
            }
        if(checkCoursesPreReq(coursesPreReq)){
            coursesPreReqCheck = true;
        }
        System.out.println("CurrentSemCredits = " + creditsAlreadyEnrolledFor);

        if(creditsAlreadyEnrolledFor + credit <= getCreditLimit())creditLimitCheck = true;

        if((cg = cgpa())>=cgpaReq)cgpaCheck = true;
        System.out.println("cg = " + cg);
        if(!courseAvailableInThisSem){
            System.out.println("This course is not available in this year-semester you will able to add it once any prof offers it. :( ");
        } else if(!batchCheck){
            System.out.printf("This course is not available for batch %d :( %n", this.entryYear);
        }else if(!deptCheck){
            System.out.printf("This course is not available for dept '%s' :( %n", this.dept);
        } else if (!coursesPreReqCheck) {
            System.out.println("you are not satisfying course's pre requisites :( ");
        } else if (creditLimitCheck) {
            System.out.printf("credit limit exceeded you've enrolled in %,.2f credits of courses but your credit limit is %,.2f :( %n", creditsAlreadyEnrolledFor,getCreditLimit());
        } else if (!cgpaCheck) {
            System.out.printf("you are not satisfying cgpa requirement, your cgpa = %f but required is %,.2f :( %n", cg,cgpaReq);
        } else if (hasTakenThisCourse(courseCode)) {
            System.out.println("You already have done this course with a passing grade :) ");

        } else{
            finalCheck = true;
            System.out.println("finalcheck = " + finalCheck);

            try{
                String query = String.format("insert into enroling(courseid,credit,year,semester,facultyusername,studentusername)values('%s',%f,%d,%d,'%s','%s')", courseCode,credit,shared.currentYear, shared.currentSemester,facultyUName,this.userName);
                Statement statement = con.createStatement();
                statement.executeUpdate(query);
                System.out.println("you have been enrolled in course "+ courseCode);
                return 1;
            }
            catch (Exception e){
                System.out.println(e);
            }
        }


        return 0;
    }

    public double getCreditLimit(){
        double creditsLimit;
        if(shared.currentYear==this.entryYear)creditsLimit = 18; // first 2 sems has maximum 18 credits limit.
        else {
            int prevYear = shared.currentYear -1;
            if(shared.currentSemester == 1){ // mansoon sem
                creditsLimit = 1.25*(getCredits(prevYear,1) + getCredits(prevYear,2))/2;
            }else{ // winter sem
                creditsLimit = 1.25*(getCredits(prevYear ,2) + getCredits(shared.currentYear ,1))/2;
            }
        }
        return creditsLimit;
    }

    public boolean checkCoursesPreReq(String preReqCourses){ // with non F grade
        String[] x = preReqCourses.split(",");
        Arrays.sort(x);
        String ret="";
        try {
            String query = String.format("select * from courseTaken where studentid = '%s' and gradepoint<>0", this.userName);
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(query);
            int tt = 0;

            while (rs.next()){
                if(tt!=0)
                ret +=","+ rs.getString("courseid") ;
                else {
                    ret += rs.getString("courseid") ;
                }
                tt++;
            }
//            System.out.println(tt);
        } catch (Exception e){

            System.out.println();
        }
        System.out.println("Courses completed without F = " + ret);

        String[] y = ret.split(",");
        Arrays.sort(y);
//        System.out.println(Arrays.equals(x,y));
        int f = 0;
        for(String a:x){
            for(String b:y){
                if(a.equals(b)) f++;
            }
        }
        System.out.println(f +"  " + x.length);
        return (f==x.length);
//        return Arrays.equals(x,y);
    }

    public double cgpa(){
        double totalCredits = 0,totalGradepoint=0;
        if(shared.currentSemester==1){

            for(int y = this.entryYear;y<shared.currentYear;y++) {
                totalCredits += getCredits(y, 1) + getCredits(y, 2);
                totalGradepoint  += getGradePoint(y, 1) + getGradePoint(y, 2);
            }
        }else{

            for(int y = this.entryYear;y<shared.currentYear;y++){
                totalCredits+= getCredits(y,1)+getCredits(y,2);
                totalGradepoint += getGradePoint(y,1) + getGradePoint(y,2);
            }
            totalCredits+=getCredits(shared.currentYear,1);
            totalGradepoint+=getGradePoint(shared.currentYear,1);

        }
        System.out.println(this.entryYear);
        System.out.println("totalCredits = " + totalCredits +  "  totalGradepoint = "  + totalGradepoint);
        double ret = (totalCredits==0)?0:totalGradepoint/totalCredits;
        System.out.println("cgpa = " + ret);
        return ret;
    }

    public boolean hasTakenThisCourse(String courseCode){
        int tt = 0;
        try {
            String query = String.format("select * from courseTaken where (studentid = '%s') and (courseid = '%s')and ( year <> %d or (year = %d and semester <>%d) ) and (gradepoint<>%d) ",this.userName,courseCode,shared.currentSemester,shared.currentSemester,shared.currentSemester,0);
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()){
                tt++;
            }
        } catch (Exception e){

            System.out.println();
        }
        return  tt!=0;
    }
    public void dropCourse(String courseCode){// to drop it must be in enroling table.
        int tt = 0;
        try {
            String query = String.format("select * from enroling where studentusername = '%s' and courseid = '%s' and year = %d and semester = %d", this.userName,courseCode, shared.currentYear,shared.currentSemester);
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(query);

            while (rs.next()){
                tt++;
            }

        } catch (Exception e){
            System.out.println();
        }

        System.out.println(tt);
        if(tt!=0){
            try {
                String query = String.format("delete from enroling where studentusername = '%s' and courseid = '%s' and year = %d and semester = %d", this.userName,courseCode, shared.currentYear,shared.currentSemester);
                Statement statement = con.createStatement();
                statement.executeUpdate(query);
                System.out.println("you have dropped the course " + courseCode);
            } catch (Exception e){
                System.out.println();
            }
        } else{
            System.out.println("You've not enrolled for this course :)");
        }


    }
    public short showCourses(){ // all course available in this semester
        System.out.println("courseid | coursename  | l | t | p |  c  | facultyusername | facultyname | year | semester | courseprereq | cgpareq |   batch   | dept");
        try {
            String query = String.format("select * from courseOffering where year = %d and semester = %d", shared.currentYear,shared.currentSemester);
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()){
                System.out.println(rs.getString("courseid") + " | " + rs.getString("coursename") +" | " + rs.getString("l") +" | " + rs.getString("t") +" | " + rs.getString("p") +" | " + rs.getString("c") +" | " + rs.getString("facultyusername") +" | " + rs.getString("facultyname") +" | " + rs.getString("year") +" | " + rs.getString("semester") + " | " + rs.getString("courseprereq") +" | " + rs.getString("cgpareq") +" | " + rs.getString("batch") +" | " + rs.getString("dept"));
            }
            return 1;
        } catch (Exception e){
            System.out.println(e);
        }

        return 0;
    }

    public void getGrades(){
        double ret = 0;
        System.out.println( "courseid | credit | gradepoint");
        try {
            String query = String.format("select * from coursetaken where studentid = '%s'", this.userName);
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()){
                System.out.println(rs.getString("courseid") + " | " + rs.getString("credit") + " | " + rs.getString("gradepoint"));
            }
        } catch (Exception e){
            System.out.println(e);
        }
        double cg = cgpa();
    }

    public void haveIPassed(){
        Vector<String> coreCoursesNeedsToBeCompleted = new Vector<String>();
        try {
            String query = String.format("select * from corecourses%d where dept = '%s'",this.entryYear, this.dept);
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()){
//                System.out.println(rs.getString("courseid") + " | " + rs.getString("credit"));
                coreCoursesNeedsToBeCompleted.add(rs.getString("courseid"));
            }
        } catch (Exception e){
            System.out.println(e);
        }

        String ret="";
        try {
            tables t2 = new tables();
            Connection con = t2.getConnection();
            String query = String.format("select * from courseTaken where studentid = '%s' and gradepoint<>0", this.userName);
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(query);
            Integer tt = 0;

            while (rs.next()){
                if(tt!=0)
                    ret +=","+ rs.getString("courseid") ;
                else {
                    ret += rs.getString("courseid") ;
                }
                tt++;
            }
        } catch (Exception e){

            System.out.println(e);
        }
//        System.out.println("Courses completed without F = " + ret);

        String[] completedCourses = ret.split(",");
        int cnt = 0;
        for(String x:coreCoursesNeedsToBeCompleted){
            for(String y:completedCourses){
                if(x.equals(y)){
                    cnt++;
                }
            }
        }
        System.out.println(cnt);
        if(cnt==coreCoursesNeedsToBeCompleted.size()){
            System.out.println("completed core/mandatory courses");
            double totalCredits = 0,totalGradepoint=0;
            if(shared.currentSemester==1){

                for(int y = this.entryYear;y<shared.currentYear;y++) {
                    totalCredits += getCredits(y, 1) + getCredits(y, 2);
                    totalGradepoint  += getGradePoint(y, 1) + getGradePoint(y, 2);
                }
            }else{

                for(int y = this.entryYear;y<shared.currentYear;y++){
                    totalCredits+= getCredits(y,1)+getCredits(y,2);
                    totalGradepoint += getGradePoint(y,1) + getGradePoint(y,2);
                }
                totalCredits+=getCredits(shared.currentYear,1);
                totalGradepoint+=getGradePoint(shared.currentYear,1);

            }
            System.out.println(this.entryYear);
            double ret1 = (totalCredits==0)?0:totalGradepoint/totalCredits;
            System.out.println("totalCredits = " + totalCredits +  " totalGradepoint "  + totalGradepoint);
            System.out.println("cgpa = " + ret1);

            if(totalCredits>=shared.minNoOfCreditsReqToGraduate){
                System.out.println("you have passed all the mandatory/core courses and total min credits required to graduate B-tech");
            } else{
                System.out.println("no you haven't completed min credit to complete graduation");
            }
        }else{
            System.out.println("not completed core/mandatory courses");
        }

    }
}
