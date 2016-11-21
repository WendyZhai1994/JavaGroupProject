package teamproject;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author joyzhang
 */
public class TeamProject {

    /**
     * @param args the command line arguments
     */
    
    private String framework = "network";
    private String protocol = "jdbc:derby:";    
    Connection conn = null;
    Statement s;
    Statement s2;
    Statement s3;
    ResultSet rs = null;
    ResultSet rs1 = null;
    ResultSet rs2 = null;
    ResultSet rs3 = null;
    ResultSet rs4 = null;
    ResultSet rs5 = null;
    ResultSet rs6 = null;
    ResultSet rs7 = null;
    
    public static void main(String[] args) {
      TeamProject questionsBank = new TeamProject();
      questionsBank.testDerby(args);
    }
    
    
    public void testDerby(String[] args) {            
      ArrayList<Statement> statements = new ArrayList<Statement>(); // list of Statements, PreparedStatements
      PreparedStatement psInsert;
      PreparedStatement psInsert1;
      PreparedStatement psInsert2;
      
      System.out.println("Exam question starting in " + framework + " mode");     
      try
        {
            Properties props = new Properties(); // connection properties
            props.put("user", "user1");
            props.put("password", "user1");   
            //String dbName = "TeamProject"; // the name of the database
            conn = DriverManager.getConnection("jdbc:derby://localhost:1527/TeamProject", props);
            //System.out.println("Connected to and created database " + dbName);
            
            /* Creating a statement object that we can use for running various
             * SQL statements commands against the database.*/
            s = conn.createStatement();
            statements.add(s);
            s.execute("drop table Questions");
            //create a table below...
            s.execute("create table Questions(questionType varchar(400), questionLevel varchar(400), description varchar(400), choice1 varchar(400), a1 varchar(400),choice2 varchar(400), a2 varchar(400), choice3 varchar(400), a3 varchar(400),choice4 varchar(400), a4 varchar(400))");
            //s.execute("create table Questions(questionType varchar(400), questionLevel varchar(400), description varchar(400), answer varchar(400))");
            System.out.println("Created table Questions");
            
          
            psInsert = conn.prepareStatement("insert into Questions values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            statements.add(psInsert);           
  
            String[] questionInfomation = readQuestionInfo();
            System.out.print(Arrays.toString(questionInfomation));
            for(int i=0;i<80;i++){
                String strr = questionInfomation[i].trim();
                String [] content = strr.split(",");
                String questionType = content[0];
                String questionLevel = content[1];
                String description = content[2];
                String choice1 = content[3];
                psInsert.setString(1,questionType);
                psInsert.setString(2, questionLevel);
                psInsert.setString(3, description);
                psInsert.setString(4, choice1);
                //if(content.length>4){
                String answer1 = content[4];
                String choice2 = content[5];
                String answer2 = content[6];
                String choice3 = content[7];
                String answer3 = content[8];
                String choice4 = content[9];
                String answer4 = content[10];
                psInsert.setString(5, answer1);
                psInsert.setString(6, choice2);
                psInsert.setString(7, answer2);
                psInsert.setString(8, choice3);
                psInsert.setString(9, answer3);
                psInsert.setString(10, choice4);
                psInsert.setString(11, answer4);
                
                psInsert.executeUpdate();           
            }
            
           
           
            //create another table below...
            s.execute("drop table usersInfo");
            s.execute("create table usersInfo( username varchar(400), pswd varchar(400), position varchar(400))");
            System.out.println("Created table UsersInfo");
            
            psInsert1 = conn.prepareStatement("insert into usersInfo values (?, ?, ?)");
            statements.add(psInsert1);  
            
            String[] userInfomation = readUserInfo();
            //System.out.print(Arrays.toString(userInfomation));
            for(int i=0;i<100;i++){
                String strr = userInfomation[i].trim();
                String [] content = strr.split(",");
                String username = content[0];
                String pswd = content[1];
                String position = content[2];
            
                psInsert1.setString(1,username);
                psInsert1.setString(2, pswd);
                psInsert1.setString(3,position);
                psInsert1.executeUpdate();  
            }
            
            //create another table below for students...
            s.execute("drop table studentInfo");
            s.execute("create table studentInfo( name varchar(400), studentID varchar(400), username varchar(400), pswd varchar(400))");
            System.out.println("Created table studentsInfo");
            
            psInsert2 = conn.prepareStatement("insert into studentInfo values (?, ?, ?, ?)");
            statements.add(psInsert2);  
            
            String[] studentInfomation = readStudentInfo();
            //System.out.print(Arrays.toString(studentInfomation));
            for(int i=0;i<85;i++){
                String strr = studentInfomation[i].trim();
                String [] content = strr.split(",");
                String name = content[0];
                String studentID = content[1];
                String username = content[2];
                String pswd = content[3];
                psInsert2.setString(1,name);
                psInsert2.setString(2, studentID);
                psInsert2.setString(3,username);
                psInsert2.setString(4, pswd);
                psInsert2.executeUpdate();  
            }
            
            
            /*
               We select the rows and verify the results.
             */
            System.out.println("Here are all the users we have: ");
            String ls_sql="select * from usersInfo";
            rs=s.executeQuery(ls_sql);
                while(rs.next()){
                    for(int j=1;j<4;j++){
                        System.out.print(rs.getString(j)+" "); 
                    } 
               
                    System.out.println();
                }
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"); 
            
            System.out.println("Here are all the questions we have: ");
            String ls_sql2="select * from Questions";
            rs=s.executeQuery(ls_sql2);
                while(rs.next()){
                    for(int j=1;j<5;j++){
                        System.out.print(rs.getString(j)+" "); 
                    } 
               
                    System.out.println();
                }
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"); 
            
            System.out.println("Here are all the students we have: ");
            String ls_sql3="select * from studentInfo";
            rs=s.executeQuery(ls_sql3);
                while(rs.next()){
                    for(int j=1;j<5;j++){
                        System.out.print(rs.getString(j)+" "); 
                    } 
               
                    System.out.println();
                }
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"); 
            //s.execute("drop table Questions");
            //s.execute("drop table usersInfo");
          
        }
      
        catch (SQLException sqle)
        {
            printSQLException(sqle);
        } finally {
            this.close();
        }
      
    }
    
    //to print out the exceptions
    private void printSQLException(SQLException e) {
        while (e != null)
        {
            System.err.println("\n----- SQLException -----");
            System.err.println("  SQL State:  " + e.getSQLState());
            System.err.println("  Error Code: " + e.getErrorCode());
            System.err.println("  Message:    " + e.getMessage());
            e = e.getNextException();
        }
    }
    
    
    //closes the resultsets, and the statement connections
    private void close() {
        try{
            if(rs!=null){
            rs.close();
            rs = null;
	    }
	    if(s!=null){
	     s.close();
	     s = null;
			}
            if(conn!=null){
                conn.close();
                conn = null;
	}
			
        }catch(Exception e){
	  e.printStackTrace();
	}
    }
    
    
    //method for reading questions from txt file into database;
    private String[] readQuestionInfo() {
        int i = 0;
        String [] question = new String[80];              
        Scanner inputPort;
        try{
            inputPort = new Scanner(new File("QuestionBank.txt"));
            while(inputPort.hasNextLine()){
                String str =inputPort.nextLine();
                question[i] = str;
                i++; 
            }
        }catch(FileNotFoundException e){
        }
        return question;
    }

    private String[] readUserInfo() {
       int i = 0;
        String [] user = new String[100];              
        Scanner inputUser;
        try{
            inputUser = new Scanner(new File("usernameAndPassword.txt"));
            while(inputUser.hasNextLine()){
                String str =inputUser.nextLine();
                user[i] = str;
                i++; 
            }
        }catch(FileNotFoundException e){
        }
        //System.out.print(Arrays.toString(user));
        return user; 
    }

    private String[] readStudentInfo() {
        int i = 0;
        String [] user = new String[100];              
        Scanner inputUser;
        try{
            inputUser = new Scanner(new File("studentInfo.txt"));
            while(inputUser.hasNextLine()){
                String str =inputUser.nextLine();
                user[i] = str;
                i++; 
            }
        }catch(FileNotFoundException e){
        }
        //System.out.print(Arrays.toString(user));
        return user; 
    }

} 