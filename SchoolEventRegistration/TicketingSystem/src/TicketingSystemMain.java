import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class TicketingSystemMain {
    
    private static Scanner sc = new Scanner(System.in);
    private static String ticketDTB = "jdbc:sqlite:TicketingSystem.db";

    // shortcut for sql connection
    private static Connection conn() throws SQLException {
        return DriverManager.getConnection(ticketDTB);
    }

    // shortcut for Scanner String
    private static String nLine(String que){
        System.out.print(que);
        return sc.nextLine().trim();
    }

    // shortcut for Scanner int
    private static int nInt(String que){
        while(true){
        System.out.print(que);
        String input = sc.nextLine();   
        try {         
           
             return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Invalid number. Please enter a valid integer."+ e.getMessage());
        }
       }
    }

    // main menu
    private static void mainMenu(){
        System.out.println("===========================================================");
        System.out.println("SCHOOL EVENT REGISTRATION AND TICKETING SYSTEM");
        System.out.println("===========================================================");
        System.out.println("1.  Add Event");
        System.out.println("2.  Register Participant");
        System.out.println("3.  Choose Ticket Type");
        System.out.println("4.  Compute Registration Fee");
        System.out.println("5.  Confirm Payment");
        System.out.println("6.  Print Receipt");
        System.out.println("7.  Display Participant Summary");
        System.out.println("8.  Search Participant");
        System.out.println("9.  Cancel Registration");
        System.out.println("10. Exit");
    }

    //event database
    static void createTables() {
      try (Connection conn = conn()) 
      {
         //para ma enable yung foreign key
        String preps = "PRAGMA foreign_keys = ON";
        try (PreparedStatement prepare = conn.prepareStatement(preps))
        {
            prepare.execute();
           System.out.println("Foreign keys enabled.");
        } 


        // table ng events
         String eventTable = """
        CREATE TABLE IF NOT EXISTS events (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            event_name TEXT NOT NULL,
            event_venue TEXT NOT NULL,
            event_date TEXT NOT NULL,
            event_capacity INTEGER NOT NULL
        )
        """;    
        try (PreparedStatement ps = conn.prepareStatement(eventTable))
        {
            ps.execute();
            System.out.println("Event table ready.");
        }



        // table ng mga participants
        String participantTable = """
            CREATE TABLE IF NOT EXISTS participants (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            participant_name TEXT NOT NULL,
            age INTEGER NOT NULL,
            participant_type TEXT NOT NULL,
            participant_organization TEXT NOT NULL,
            contact_no TEXT NOT NULL,
            earlybird INTEGER NOT NULL
        )
        """;
        try (PreparedStatement ps = conn.prepareStatement(participantTable)) 
        {
            ps.execute();
            System.out.println("Participant table ready.");
        }

        
        //table ng registration, eto yung may need ng foreign key, para ma get yung values sa ibang tables
        String registrationTable = """
        CREATE TABLE IF NOT EXISTS registrations (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            participant_id INTEGER NOT NULL,
            event_id INTEGER NOT NULL,
            ticket_type TEXT NOT NULL,
            original_fee REAL NOT NULL,
            discount_amount REAL NOT NULL,
            final_fee REAL NOT NULL,
            payment_status TEXT NOT NULL,
            registration_status TEXT NOT NULL,
            FOREIGN KEY (participant_id) REFERENCES participants(id),
            FOREIGN KEY (event_id) REFERENCES events(id)
        )
        """;
        try (PreparedStatement ps = conn.prepareStatement(registrationTable)) 
        {
            ps.execute();
            System.out.println("Registration table ready.");
        }

    }catch (SQLException e) {System.out.println("Error creating tables: " + e.getMessage());}


}


    
    public static void main(String[] args) {

        createTables();
        mainMenu();

        int choice;
        do{
        choice = 0;
        try{
        choice = nInt("Enter choice: ");
         }catch(NumberFormatException e) 
         {System.out.println(" ");
         System.out.println("Only number is allowed as an input.");
         }
        switch (choice) {
            case 1:
              
                break;
            case 2:
                // codes dito
                break;
            case 3:
                // codes dito
                break;
            case 4:
                // codes dito
                break;
            case 5:
                // codes dito
                break;
            case 6:
                // codes dito
                break;
            case 7:
                // codes dito
                break;
            case 8:
                // codes dito
                break;
            case 9:
                // codes dito
                break;
            case 10:
            System.out.println("Exit Sucessfully.");
            System.out.println("==========================================");
                break;
            default:
                System.out.println("Invalid choice. please try again.");
                break;
        }
  }while(choice != 10);




       
  





}
}
