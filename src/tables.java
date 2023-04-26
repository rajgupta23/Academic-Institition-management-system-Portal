import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class tables {
    public Connection getConnection(){
        Connection conn = null;
        try{
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/"+"cs305-mini-project","postgres","GUPta@123");
            if(conn !=null){
                System.out.println("Connection done");
            }else {
                System.out.println("Conn failed");
            }
        }catch (Exception e){
            System.out.println();

        }
        return conn;
    }
    public void createTables(Connection con, String tableName,String attr){
        Statement statement;
        try {
            String query = "create table " + tableName +" "+ attr;
            statement = con.createStatement();
            statement.executeUpdate(query);
        }catch (Exception e){
//            System.out.println("here\n");
            System.out.println();
        }
    }
}
