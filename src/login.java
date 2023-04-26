import javafx.beans.property.Property;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class login {
    public boolean auth(int type,String userName, String password){
        tables t1 = new tables();
        Connection con = t1.getConnection();
        Statement statement;
        ResultSet rs = null;
        String userType = "" ;
        if(type ==1)userType = "students";
        else if(type ==2)userType = "faculties";
        else userType = "admin";

        try {
            System.out.println("userName in auth = "+ userName);
            String query = String.format("select password from %s where username = '%s'",userType, userName) ;
            statement = con.createStatement();
            rs = statement.executeQuery(query);
            System.out.println(query);
            String pas = "" ;
            while(rs.next())
            pas = pas + rs.getString("password");

            System.out.println(pas);

            if(pas.equals(password)){
//                System.out.println("Logged In As a student");
                return true;

            }else {
//                System.out.println("wrong pass");
                return false;
            }
        }catch (Exception e){
//            System.out.println("here\n");
            System.out.println(e);
        }
        return false;
    }

}
