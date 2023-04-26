import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {

        System.out.println("Hello world!");
        tables tables = new tables();
        Connection conn = tables.getConnection();
//        tables.createTables(conn,"students","(name varchar(50),username varchar(20),email varchar(20),password varchar(50),mobile varchar(10),entryYear integer );");
//        tables.createTables(conn,"faculties","(name varchar(50),username varchar(20),email varchar(20),password varchar(50),mobile varchar(10));");
//        tables.createTables(conn,"admin","(name varchar(50),username varchar(20),email varchar(20),password varchar(50),mobile varchar(10));");
        System.out.println("Welcome to AIMS portal\npress 1: to login\npress anything else: to exit");
        Scanner scanner = new Scanner(System.in);
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        int a = scanner.nextInt();
        if(a == 1){
//            System.out.println("Go to login");
            while(true){
                System.out.println("\npress 1: to login as Student\npress 2: to login as faculty\npress 3: to login as admin\npress 4: exit");
                int type = scanner.nextInt();
                if (type == 1) { // student
                    user nu = new user();

                    nu.type = 1;

                    System.out.println("Enter your user name");
                    String uname = scanner.next();

                    System.out.println("Enter your password");
                    String uPass = scanner.next();

                    nu.userName = uname;
                    nu.password = uPass;

                    nu.loginIntoType();


                } else if (type == 2) { // faculty

                    user nu = new user();

                    nu.type = 2;

                    System.out.println("Enter your user name");
                    String uname = scanner.next();

                    System.out.println("Enter your password");
                    String uPass = scanner.next();

                    nu.userName = uname;
                    nu.password = uPass;

                    nu.loginIntoType();

                } else if (type == 3) { // admin
                    user nu = new user();
                    nu.type = 3;

                    System.out.println("Enter your user name");
                    String uname = scanner.next();

                    System.out.println("Enter your password");
                    String uPass = scanner.next();

                    nu.userName = uname;
                    nu.password = uPass;

                    nu.loginIntoType();


                } else if(type==4) {
                    System.out.println("Existing from AIMS portal");
                    break;
                } else System.out.println("Wrong input");
            }

        }else {
            System.out.println("Existing from AIMS portal");
        }

    }
}