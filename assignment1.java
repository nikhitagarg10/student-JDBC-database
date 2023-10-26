/*--------------------------STUDENT MANAGEMENT SYSTEM---------------------------------------*/
//    registration number => string => primary key
//    name => string
//    gender => string
//    date of birth => date
//    year of admission => 4-digit number
//    fee => number
//    course => choices (BTech, MBA, MBBS, B.Com)
//    cgpa => number (between 1 and 10)

/*------------------------------------------------------------------------------------------------------*/

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.sql.*;
import java.util.*;
import java.util.regex.Pattern;
//import java.util.regex.Matcher;
import java.sql.Date;

public class assignment1 {

    // All the Queries

    //CREATE THE TABLE
    public static final String CREATE = "DROP TABLE STUDENT IF EXISTS; create table STUDENT(" +
            "StId INTEGER AUTO_INCREMENT PRIMARY KEY , " +
            "StName varchar2(20)," +
            "StGender varchar2(50), " +
            "StDob Date, " +
            "StYoa Date, " +
            "StFee NUMBER, " +
            "StCourse varchar2(10)," +
            "StCgpa NUMBER )";

    // DISPLAY DETAILS (CHOICE 1)
    public static final String SELECT = "select * from STUDENT where StId=?";


    // INSERT DATA (CHOICE 2)
    public static final String INSERT="INSERT INTO STUDENT(StName, StGender, StDob, StYoa, StFee, StCourse, StCgpa) VALUES(?, ?, ?, ?, ?, ?, ?)";
    public static final String INS_SELECT = "select * from STUDENT";


    //UPDATE (CHOICE 3)
    public static final String UPDATE_NAME ="update STUDENT set StName=? where StId=?";
    public static final String UPDATE_GENDER ="update STUDENT set StGender=? where StId=?";
    public static final String UPDATE_DOB ="update STUDENT set StDob=? where StId=?";
//    public static final String UPDATE_YOA ="update STUDENT set StYoa=? where StId=?";
    public static final String UPDATE_FEE ="update STUDENT set StFee=? where StId=?";
    public static final String UPDATE_COURSE ="update STUDENT set StCourse=? where StId=?";
    public static final String UPDATE_CGPA ="update STUDENT set StCgpa=? where StId=?";


    //DELETE (CHOICE 4)
    public static final String DELETE = "delete from STUDENT where StId=?";



    //input validation functions;
    public static int input_choice() {
        Scanner sc = new Scanner(System.in);
        int choice;
        do {
            try {
                System.out.println("Choose of the following options by entering the any number between 1-5");
                choice = sc.nextInt();
                sc.nextLine();
                if(choice < 1 || choice > 5){
                    System.out.println("please enter a valid integer from 1 to 5");
                }
            }
            catch (Exception e){
                System.out.println("please enter a valid integer from 1 to 5");
                sc.next();
                choice = -1;
            }
        }
        while(choice < 1 || choice  > 5);

        return choice;
    }
    public static String input_gender() {
        System.out.println("Enter the Student Gender [\"male\", \"female\", \"other\"]");
        Scanner sc = new Scanner(System.in);
        String[] gender_arr = {"male", "female", "other"};
        String gender;
        do{
            gender = sc.nextLine();
            if(!Arrays.asList(gender_arr).contains(gender.toLowerCase())){
                System.out.println("please enter a valid gender type");
            }
        }
        while(!Arrays.asList(gender_arr).contains(gender.toLowerCase()));

        return gender;
    }
    public static String input_date(String date_name) throws ParseException {
        System.out.printf("Enter the %S in the format YYYY-MM-DD", date_name);

        //to check if the dates are in the proper format
        String regex = "\\d{4}-\\d{2}-\\d{2}";
        Pattern p = Pattern.compile(regex);

        Scanner sc = new Scanner(System.in);
        String date_inp;
        do{
            date_inp = sc.nextLine();
            if(!p.matcher(date_inp).matches()){
                System.out.println("please enter the date in the correct format");
            }
        }
        while(!p.matcher(date_inp).matches());

        return date_inp;
    }
    public static int input_fee(){
        System.out.println("Enter the fee");
        Scanner sc = new Scanner(System.in);
        int fee;
        while(true)
        {
            try{
                fee = sc.nextInt();
                break;
            }catch(Exception e){
                System.out.println("Please enter a number");
                sc.next();
            }
        };
        return fee;
    }
    public static String input_course(){
        System.out.println("Enter the Student Course [\"Btech\", \"MBA\", \"Bcom\", \"MBBS\"]");
        String[] course_arr = {"btech", "mba", "bcom", "mbbs"};
        Scanner sc = new Scanner(System.in);
        String course;
        do{
            course = sc.nextLine();
            if(!Arrays.asList(course_arr).contains(course.toLowerCase())){
                System.out.println("please enter a valid course");
            }
        }
        while(!Arrays.asList(course_arr).contains(course.toLowerCase()));
        return course;
    }
    public static int input_cgpa() {
        Scanner sc = new Scanner(System.in);
        int cgpa;
        do {
            try {
                System.out.println("Enter the CGPA in the format. It should be between 1 to 10");
                cgpa = sc.nextInt();
                if(cgpa < 1 || cgpa > 10){
                    System.out.println("please enter a valid CGPA between 1 to 10");
                }
            }
            catch (Exception e){
                System.out.println("please enter a valid integer from 1 to 5");
                sc.next();
                cgpa = -1;
            }
        }
        while(cgpa < 1 || cgpa  > 10);
        return cgpa;
    }


    public static void main(String[] args){

        //intro message
        System.out.println("Welcome to the student management system");
        System.out.println("To proceed please enter your Registration ID");


        //take the input for the registration ID
        Scanner sc = new Scanner(System.in);
        int RegNo;

        System.out.println("Registration NUmber: ");
        RegNo = sc.nextInt();
        sc.nextLine();


        //displaying the various options to the user
        /* 1 => display the user details
        *  2 => insert a new user
        *  3 => update the values of the user
        *  4 => delete the user
        *  5 => exit */
        System.out.println("1 => display the user details");
        System.out.println("2 => insert a new user");
        System.out.println("3 => update the values of the user");
        System.out.println("4 => delete the user");
        System.out.println("5 => exit");

        //creating the connection with the database
        try{
            //set the connection object with the H2 database
            Connection conn= DriverManager.getConnection("jdbc:h2:./db","sa","");

            // execute the create table query;
            PreparedStatement ps = conn.prepareStatement(CREATE);
            ps.execute();

            //making sure user inputs the correct number between 1 and 5
            int choice = input_choice();

            switch(choice)
            {
                case 1:
                    System.out.println("checking if the user exists......");
                    PreparedStatement ps1 = conn.prepareStatement(SELECT);
                    ps1.setInt(1, RegNo);
                    ResultSet rs1 = ps1.executeQuery();
                    if(rs1.next()){
                        System.out.printf("displaying the [%S] student data", RegNo);
                        while(rs1.next()){
                            System.out.println(rs1.getInt("StId")+" "+
                                    rs1.getString("StName")+" "+
                                    rs1.getString("StGender")+" "+
                                    rs1.getDate("StDob")+" "+
                                    rs1.getDate("StYoa")+" "+
                                    rs1.getInt("StFee")+" "+
                                    rs1.getString("StCourse")+" "+
                                    rs1.getInt("StCgpa"));
                        }
                    }
                    else {
                        System.out.println("no records are found with this registration number");
                    }
                    break;
                case 2:
                    System.out.println("To insert a new user provide the following details");
                    //name
                    System.out.println("Enter the Student Name");
                    String name = sc.nextLine();
                    //gender
                    String gender = input_gender();
                    //DOB
                    String dob = input_date("Date of Birth");
                    //YOA
                    String yoa = input_date("year of Admission");
                    //FEE
                    int fee = input_fee();
                    //course
                    String course = input_course();
                    //cgpa
                    int cgpa = input_cgpa();

                    Date date1 =Date.valueOf(dob);
                    Date date2 =Date.valueOf(yoa);

                    PreparedStatement ps2 = conn.prepareStatement(INSERT);
                    ps2.setString(1, name);
                    ps2.setString(2, gender);
                    ps2.setDate(3, date1);
                    ps2.setDate(4, date2);
                    ps2.setInt(5, fee);
                    ps2.setString(6, course);
                    ps2.setInt(7, cgpa);
                    int k = ps2.executeUpdate();
                    System.out.println(k+"rows added");

                    PreparedStatement ps2_dis = conn.prepareStatement(INS_SELECT);
                    ResultSet rs_in = ps2_dis.executeQuery();
                    System.out.println("displaying the table");
                    while(rs_in.next()){
                            System.out.println(rs_in.getInt(1)+" "+
                                    rs_in.getString(2)+" "+
                                    rs_in.getString(3)+" "+
                                    rs_in.getDate(4)+" "+
                                    rs_in.getDate(5)+" "+
                                    rs_in.getInt(6)+" "+
                                    rs_in.getString(7)+" "+
                                    rs_in.getInt(8));
                    }


                    break;
                case 3:
                    System.out.println("Select the parameters [\"name\", \"gender\", \"DOB\", \"fee\", \"course\", \"cgpa\"] you wish to update...");

                    String [] parameters = {"name", "gender", "DOB", "fee", "course", "cgpa"};
                    String update_choice;
                    do {
                        update_choice = sc.nextLine();
                        if(!Arrays.asList(parameters).contains(update_choice)){
                            System.out.println("Please choose a valid parameter");
                        }
                    }
                    while(!Arrays.asList(parameters).contains(update_choice.toLowerCase()));

                    switch(update_choice)
                    {
                        case "name":
                            String Name = sc.nextLine();
                            PreparedStatement pud1 = conn.prepareStatement(UPDATE_NAME);
                            pud1.setString(1, Name);
                            pud1.setInt(2, RegNo);
                            pud1.executeUpdate();
                            break;
                        case "gender":
                            String gen = input_gender();
                            PreparedStatement pud2 = conn.prepareStatement(UPDATE_GENDER);
                            pud2.setString(1, gen);
                            pud2.setInt(2, RegNo);
                            pud2.executeUpdate();
                            break;
                        case "DOB":
                            String date_of_birth = input_date("date of Birth");
                            PreparedStatement pud3 = conn.prepareStatement(UPDATE_DOB);
                            pud3.setDate(1, Date.valueOf(date_of_birth));
                            pud3.setInt(2, RegNo);
                            pud3.executeUpdate();
                            break;
                        case "fee":
                            int Fee = input_fee();
                            PreparedStatement pud4 = conn.prepareStatement(UPDATE_FEE);
                            pud4.setInt(1, Fee);
                            pud4.setInt(2, RegNo);
                            pud4.executeUpdate();
                            break;
                        case "course":
                            String Course = input_course();
                            PreparedStatement pud5 = conn.prepareStatement(UPDATE_COURSE);
                            pud5.setString(1, Course);
                            pud5.setInt(2, RegNo);
                            pud5.executeUpdate();
                            break;
                        case "cgpa":
                            int Cgpa = input_cgpa();
                            PreparedStatement pud6 = conn.prepareStatement(UPDATE_CGPA);
                            pud6.setInt(1, Cgpa);
                            pud6.setInt(2, RegNo);
                            pud6.executeUpdate();
                            break;
                    }
                    break;
                case 4:
                   System.out.printf("Delete the user with Registration number %S", RegNo);
                    PreparedStatement ps4 = conn.prepareStatement(DELETE);
                    ps4.setInt(1, RegNo);
                    int j = ps4.executeUpdate();
                    if(j == 1) {
                        System.out.println("Student Deleted successfully");
                    }
                    break;
                case 5:
                    System.out.println("exiting....");
                    break;
            }

            sc.close();
        }
        catch(SQLException sqlException){
            System.out.println("SQLException handler"+sqlException.getMessage());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }


}
