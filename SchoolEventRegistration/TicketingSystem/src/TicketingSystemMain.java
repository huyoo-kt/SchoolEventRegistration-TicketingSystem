import java.sql.*;
import java.util.*;


// notes hindi mo pa na memerge sa orginal sa github to.

public class TicketingSystemMain {
    private static Scanner sc = new Scanner(System.in);


    // for database, 
    private static String TsDB = "jdbc:sqlite:TicketingSystem.db";
      private static Connection conn() throws SQLException {
        return DriverManager.getConnection(TsDB);
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
        String input = sc.nextLine().trim();   
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


    // creating tables
    private static void createDB(){
        try(Connection CdataB = conn()){

        
        // para ma access ko yung autoincremented na id num
        String preps = "PRAGMA foreign_keys = ON";
        try (PreparedStatement prepare = CdataB.prepareStatement(preps))
        {
            prepare.execute();
           System.out.println("Foreign keys enabled.");
        } 

                // Event table
                String cte = "create table if not exists events (event_id INTEGER PRIMARY KEY AUTOINCREMENT, event_name TEXT NOT NULL, event_venue TEXT NOT NULL, event_date TEXT NOT NULL, event_capacity INTEGER NOT NULL)";
                try(PreparedStatement CtableE = CdataB.prepareStatement(cte,Statement.RETURN_GENERATED_KEYS)){
                    CtableE.execute();
                    System.out.println("Event table created.");
                }

                //  Participant table
                String ctp = "create table if not exists participants (participant_id INTEGER PRIMARY KEY AUTOINCREMENT, participant_name TEXT NOT NULL, age INTEGER NOT NULL, participant_gender TEXT ,participant_type TEXT NOT NULL, participant_organization TEXT NOT NULL, contact_no TEXT NOT NULL, earlybird TEXT NOT NULL)";
                try(PreparedStatement CtableP = CdataB.prepareStatement(ctp,Statement.RETURN_GENERATED_KEYS)){
                    CtableP.execute();
                    System.out.println("Participant table created.");
                }

                //Registration table
                String ctr = " create table if not exists registrations (registration_id INTEGER PRIMARY KEY AUTOINCREMENT, participant_id INTEGER NOT NULL, event_id INTEGER NOT NULL, ticket_type TEXT NOT NULL, original_fee REAL, discount_amount REAL, final_fee REAL, payment_status TEXT, registration_status TEXT DEFAULT 'ACTIVE', FOREIGN KEY (participant_id) REFERENCES participants(participant_id), FOREIGN KEY(event_id) REFERENCES events(event_id))";
                try(PreparedStatement CtableR = CdataB.prepareStatement(ctr,Statement.RETURN_GENERATED_KEYS)  ){
                    CtableR.execute();
                    System.out.println("Registration table created.");
                }

        }catch(SQLException e){
            System.out.println("Error creating database." + e.getMessage());
        }
        }

    static Participant findParticipant(int id) {
    String sql = "SELECT * FROM participants WHERE participant_id = ?";

    try (Connection con = conn();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            return new Participant(
                rs.getInt("participant_id"),
                rs.getString("participant_name"),
                rs.getInt("age"),
                rs.getString("participant_gender"),
                rs.getString("participant_type"),
                rs.getString("participant_organization"),
                rs.getString("contact_no"),
                rs.getString("earlybird")
            );
        }

    } catch (SQLException e) {
        System.out.println("Error: " + e.getMessage());
    }

    return null;
}

    // find participants sa database
    static Event findEvent(int id) {
    String sql = "SELECT * FROM events WHERE event_id = ?";

    try (Connection con = conn();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            return new Event(
                rs.getInt("event_id"),
                rs.getString("event_name"),
                rs.getString("event_venue"),
                rs.getString("event_date"),
                rs.getInt("event_capacity")
            );
        }

    } catch (SQLException e) {
        System.out.println("Error: " + e.getMessage());
    }

    return null;
}


    // find participants sa database
  static Registration findRegistration(int id) {
    String sql = "SELECT * FROM registrations WHERE registration_id = ?";
   

    try (Connection con = conn();
        PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();

        if (rs.next()){
            Participant p = findParticipant(rs.getInt("participant_id"));
            Event e = findEvent(rs.getInt("event_id"));

            Ticket t1;
            String type = rs.getString("ticket_type");
            if (type.equalsIgnoreCase("REGULAR")) 
            {t1 = new RegularTicket("T-" + id, "REGULAR", 500.00);}
            else { t1 = new VipTicket("T-" + id, "VIP", 1000.00, 0.00);}

                Registration r1 =  new Registration(id,p,e,t1);
                r1.setOriginalFee(rs.getDouble("original_fee"));
                r1.setDiscountAmount(rs.getDouble("discount_amount"));
                r1.setFinalFee(rs.getDouble("final_fee"));
                r1.setPaymentStatus(rs.getString("payment_status"));
                r1.setRegistrationStatus(rs.getString("registration_status"));
               return r1;
        }
    } catch (SQLException e) {
        System.out.println("Error: " + e.getMessage());
    }
    return null;
}

    
    private static Event addEvent(){
    System.out.println("---ADD EVENT---");
    String ename = nLine("Event name: ");
    String evenue = nLine("Event venue: ");
    String edate = nLine("Event date: ");
    int ecapacity;
    do {
        ecapacity = nInt("Event capacity: ");
    } while (ecapacity <= 0);
    String sq1 = "INSERT INTO events(event_name, event_venue, event_date, event_capacity) VALUES(?, ?, ?, ?)";
    try(Connection con1 = conn();
        PreparedStatement ps1 = con1.prepareStatement(sq1,Statement.RETURN_GENERATED_KEYS)){
        ps1.setString(1, ename);
        ps1.setString(2, evenue);
        ps1.setString(3, edate);
        ps1.setInt(4, ecapacity);
        ps1.executeUpdate();
        ResultSet rs = ps1.getGeneratedKeys();
        if (rs.next()) {
            int eid = rs.getInt(1);
            System.out.println("Event added successfully.");
            return new Event(eid, ename, evenue, edate, ecapacity);
        }
        } catch(SQLException e){
        System.out.println("Error inserting data: " + e.getMessage());
    }
    return null;
}



    private static Participant addParticipant(){
        System.out.println("---REGISTER PARTICIPANT---");
 
        String pfn = nLine("Enter Full Name: ");
        int page = nInt("Enter Age: ");
        String pg;
                do {
            pg = nLine("Enter participant gender(male or female): ");
            if(!pg.equalsIgnoreCase("male") && !pg.equalsIgnoreCase("female")){
                System.out.println("you can only input the given choices, try again.");}
        } while(!pg.equalsIgnoreCase("male") && !pg.equalsIgnoreCase("female"));

        String pt;
        do {
            pt = nLine("Enter participant type(student or regular): ");
            if(!pt.equalsIgnoreCase("student") && !pt.equalsIgnoreCase("regular")){
                System.out.println("you can only input the given choices, try again.");}
        } while(!pt.equalsIgnoreCase("student") && !pt.equalsIgnoreCase("regular"));


        String po = nLine("Enter participant_organization: ");
        String pc = nLine("Enter contact_no: "); 
        String eb;
        do {
            eb = nLine("Earlybird(enable or disable): ").toLowerCase().trim();
            if(!eb.equalsIgnoreCase("enable") && !eb.equalsIgnoreCase("disable")){
                System.out.println("you can only input the given choices, try again.");}
        } while(!eb.equalsIgnoreCase("enable") && !eb.equalsIgnoreCase("disable"));
        
      
      

        String pql = "INSERT INTO participants(participant_name, age, participant_gender, participant_type, participant_organization, contact_no, earlybird ) VALUES(?, ?, ?, ?, ?, ?,?)";
        try(Connection con2 = conn();
            PreparedStatement  ps2 = con2.prepareStatement(pql,Statement.RETURN_GENERATED_KEYS)){
                ps2.setString(1, pfn);
                ps2.setInt(2, page);
                ps2.setString(3, pg);
                ps2.setString(4, pt);
                ps2.setString(5, po);
                ps2.setString(6, pc);
                ps2.setString(7, eb);
                ps2.executeUpdate();
                ResultSet rs = ps2.getGeneratedKeys();
        if (rs.next()) {
            int pid = rs.getInt(1);
            System.out.println("Participant added successfully.");
            return new Participant(pid, pfn, page, pg, pt, po, pc, eb);
        }
         System.out.println("Participant registered sucessfully.");  
        }catch(SQLException e){
           System.out.println("Error adding Participant. "+e.getMessage());
        }
        return null;
    }

    // to check if the event capacity is already full
    private static boolean checkCAPA(int eid){
        // count lahat ng registered id as reg_count then compare it to capacity ng event while also validating that only the participant who is not "cancled" yung ma cacall, theni group using event id para mas goods tingnan.
        String sql = "select e.event_capacity, COUNT(r.registration_id) as reg_count from events e left join registrations r on r.event_id = e.event_id where e.event_id = ? AND r.registration_status != 'CANCELLED' GROUP BY e.event_id";

        try(Connection conn = conn();
            PreparedStatement preps1 = conn.prepareStatement(sql)){
                preps1.setInt(1, eid);
                ResultSet r1 = preps1.executeQuery();

                if(r1.next()){
                    int count = r1.getInt("reg_count");
                    int capacity = r1.getInt("event_capacity");
                    return count >= capacity; // if yung capacity is
                }
            
        } catch (SQLException e) {
           System.out.println("Error checking the capacity"+ e.getMessage());
        }
    return false;


    }



  private static void chooseTType() {
    System.out.println("---CHOOSE TICKET TYPE---");
    int pid = nInt("Enter Participant ID: ");
     Participant p = findParticipant(pid);
    if (p == null) { System.out.println("Participant not found."); return;}
    int eid = nInt("Enter Event ID: ");
    if(checkCAPA(eid)){System.out.println("Sorry, event registration is already full."); return;}
    String type;
    do {
        type = nLine("Ticket Type (REGULAR/VIP): ");
        System.out.println("you can only input the given choices, try again.");
    } while (!type.equalsIgnoreCase("regular") && !type.equalsIgnoreCase("vip"));


    String sql = "INSERT INTO registrations(participant_id, event_id, ticket_type) VALUES(?, ?, ?)";
    try (Connection con = conn();
        PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
        ps.setInt(1, pid);
        ps.setInt(2, eid);
        ps.setString(3, type);
        ps.executeUpdate();
        ResultSet rs = ps.getGeneratedKeys();
        if(rs.next()){
            System.out.println("Registration created ID: " + rs.getInt(1));
        }       
    } catch (SQLException ex) {
        System.out.println("Error: " + ex.getMessage());
    }
}
  



    static void computeRegistrationFee(){
        System.out.println("--- COMPUTE REGISTRATION FEE ---");
        int rid = nInt("Enter Registration ID: ");
        Registration r1 = findRegistration(rid);
        if(r1 == null){System.out.println("No Registration id Found"); return;}

        PaymentCalculator pay1 = new PaymentCalculator();
        double origs = pay1.computeBaseFee(r1);
        double disc = pay1.computeDiscount(r1);
        double fFee = pay1.computeFinalFee(r1);
        r1.setOriginalFee(origs);
        r1.setDiscountAmount(disc);
        r1.setFinalFee(fFee);
     
        //original_fee REAL, discount_amount REAL, final_fee REAL
        String rupdt = "update registrations set original_fee = ?,  discount_amount = ?, final_fee = ? where registration_id = ? ";
            try(Connection crf = conn();
            PreparedStatement pcrf = crf.prepareStatement(rupdt)){
                pcrf.setDouble(1, origs);
                pcrf.setDouble(2, disc);
                pcrf.setDouble(3,fFee);
                pcrf.setInt(4, rid);
                pcrf.executeUpdate();
                System.out.println("Registration fee computed sucessfully");
        }catch(SQLException e){System.out.println("Error computing registration fee." + e.getMessage());}
        }





        // payment_status - eto muna update mo, registration_status
        static void confirmPayment(){
            int rid = nInt("Enter Registration ID: ");
            Registration r = findRegistration(rid);
            if (r == null) {System.out.println("Registration not found."); return;}
            if(r.getOriginalFee() == 0.00){System.out.println("Registration fee is not computed."); return;}

            String sqlps = "update registrations set payment_status = 'PAID' where registration_id = ?";
            try(Connection conf = conn();
                PreparedStatement confp = conf.prepareStatement(sqlps)){
                confp.setInt(1, rid);
                confp.executeUpdate();
                System.out.println("Payment confirmed successfully!");
            } catch (SQLException e) {
                System.out.println("Error confirming payment. "+ e.getMessage());
            }
        }

        // dito kana yaw
        static void registrationReceipt(){
            System.out.println("--- PRINT RECEIPT ---");
            int rid = nInt("Input Registration id: ");
            Registration r1 = findRegistration(rid);

            if(r1 == null){System.out.println("No Registration id Found"); return;}
            if(!(r1.getPaymentStatus().equalsIgnoreCase("paid")))
            {System.out.println("Payment must be confirmed before printing the receipt!"); return;}
            String rno = "REC-" + rid;
            new Receipt(rno, r1).printReceipt();
        }

    static void participantSummary() {
    System.out.println("------------------------------- PARTICIPANT SUMMARY ------------------------------");
  
    // Step 1 — show lahat ng event
    String eall = "SELECT * FROM events";
    try (Connection con = conn();
         PreparedStatement ps = con.prepareStatement(eall);
         ResultSet rse = ps.executeQuery()) {

        System.out.printf("%-5s %-30s %-20s %-15s %-10s%n","ID", "Event Name", "Venue", "Date", "Capacity");
        System.out.println("-".repeat(82));

        while (rse.next()) {
            System.out.printf("%-5d %-30s %-15s %-20s %-10d%n",
            rse.getInt("event_id"),
            rse.getString("event_name"),
            rse.getString("event_venue"),
            rse.getString("event_date"),
            rse.getInt("event_capacity"));
        }
         System.out.println("-".repeat(82));
    } catch (SQLException e) {
        System.out.println("Error fetching events: " + e.getMessage());
        return;
    }

    // Step 2 — user picks an event
    int eid = nInt("Enter Event ID: ");
    System.out.println(" ");
    Event ev = findEvent(eid);
    if (ev == null) { System.out.println("Event not found."); return; }

    // Step 3 — select lahat ng participant then 1 by 1 output
    String sql = "SELECT p.participant_id, p.participant_name, p.age, p.participant_type, p.participant_organization, p.contact_no, p.earlybird, e.event_name, r.ticket_type, r.original_fee, r.discount_amount, r.final_fee, r.payment_status, r.registration_status FROM participants p LEFT JOIN registrations r ON r.participant_id = p.participant_id LEFT JOIN events e ON r.event_id = e.event_id WHERE r.event_id = ?";                                                                                                                


    try (Connection con = conn();
        PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setInt(1, eid);
        ResultSet rs = ps.executeQuery();
        System.out.println("Participants for event: " + ev.getEventName());
        System.out.println("-".repeat(152));
        System.out.printf("%-5s %-8s %-6s %-12s %-7s %-8s %-10s %-13s %-7s %-12s %-13s %-10s %-14s %-10s%n","pID", "Name", "Age", "pType", "Org", "Contact", "Earlyb", "E Name", "T type", "Orig Fee", "Disc Amount", "Fnl Fee", "Pymnt Status", "RegStatus");
        System.out.println("-".repeat(152));
        System.out.println("-".repeat(152));

boolean found = false;
while (rs.next()) {
    found = true;
    System.out.printf("%-5s %-8s %-6s %-8s %-8s %-7s %-7s %-7s %-8s %-13s %-13s %-13s %-10s %-10s%n",
        rs.getInt("participant_id"),
        rs.getString("participant_name"),
        rs.getInt("age"),
        rs.getString("participant_type"),
        rs.getString("participant_organization"),
        rs.getString("contact_no"),
        rs.getString("earlybird"),
        rs.getString("event_name"),
        rs.getString("ticket_type"),
        rs.getDouble("original_fee"),
        rs.getDouble("discount_amount"),
        rs.getDouble("final_fee"),
        rs.getString("payment_status"),
        rs.getString("registration_status"));
        }
        System.out.println("-".repeat(152));
        System.out.println(" ");
        if (!found) System.out.println("No participants registered for this event yet.");
    } catch (SQLException e) {
        System.out.println("Error finding participants: " + e.getMessage());
    }
}


       
    // SELECT p.participant_id, p.participant_name, p.age, p.participant_gender, p.participant_type, p.participant_organization, p.contact_no, p.earlybird, r.registration_id, r.ticket_type, r.payment_status, r.registration_status, e.event_name FROM participants p LEFT JOIN registrations r ON r.participant_id = p.participant_id LEFT JOIN events e ON r.event_id = e.event_id;
    // dito kana
    static void searchParticipant(){
    System.out.println("--- SEARCH PARTICIPANT ---");
    int choose = nInt("SEARCH USING NAME OR ID(1 for id and 2 for name): ");
    switch (choose) {
            case 1: 
            // search using id
            String sqlids = "SELECT p.participant_id, p.participant_name, p.age, p.participant_gender, p.participant_type, p.participant_organization, p.contact_no, p.earlybird, r.registration_id, r.ticket_type, r.payment_status, r.registration_status, e.event_name FROM participants p LEFT JOIN registrations r ON r.participant_id = p.participant_id LEFT JOIN events e ON r.event_id = e.event_id where p.participant_id = ?;";
            int sid = nInt("Enter participant id: ");
            try (Connection consp = conn();
                PreparedStatement prepsp = consp.prepareStatement(sqlids)) {
                prepsp.setInt(1, sid);
                ResultSet sp = prepsp.executeQuery();
                System.out.println("-".repeat(152));
                System.out.printf("%-5s %-8s %-6s %-10s %-9s %-18s %-10s %-15s %-9s %-14s %-12s %-12s %-14s%n","ID", "Name", "Age", "Gender", "Type", "Organization", "Contact", "Earlybird", "Reg ID", "Event Name", "Ticket", "Payment", "Status");
                System.out.println("-".repeat(152));
                System.out.println("-".repeat(152));
                boolean found = false;
            while (sp.next()) {
            found = true;
                System.out.printf("%-5s %-8s %-6s %-8s %-10s %-19s %-11s %-15s %-4s %-17s %-12s %-10s %-10s%n",
                sp.getInt("participant_id"),
                sp.getString("participant_name"),
                sp.getInt("age"),
                sp.getString("participant_gender"),
                sp.getString("participant_type"),
                sp.getString("participant_organization"),
                sp.getString("contact_no"),
                sp.getString("earlybird"),
                sp.getInt("registration_id"),
                sp.getString("event_name"),
                sp.getString("ticket_type"),
                sp.getString("payment_status"),
                sp.getString("registration_status"));
                }
                if (!found) System.out.println("Participant not found!");
                System.out.println("-".repeat(152));
                    System.out.println(" ");
                }catch(SQLException e){System.out.println("Error searching participant." + e.getMessage());}
                break;

                case 2: 
                // search using name
                String sqlns = "SELECT p.participant_id, p.participant_name, p.age, p.participant_gender, p.participant_type, p.participant_organization, p.contact_no, p.earlybird, r.registration_id, r.ticket_type, r.payment_status, r.registration_status, e.event_name FROM participants p LEFT JOIN registrations r ON r.participant_id = p.participant_id LEFT JOIN events e ON r.event_id = e.event_id where p.participant_name like ?;";
                String sname = nLine("Enter participants name: ");
                  try(Connection consp = conn();
                        PreparedStatement prepsp = consp.prepareStatement(sqlns)){
                        prepsp.setString(1,"%"+sname+"%");
                        ResultSet sp = prepsp.executeQuery();
                        
                         // header
                        System.out.println("-".repeat(152));
                        System.out.printf("%-5s %-8s %-6s %-10s %-9s %-18s %-10s %-15s %-9s %-14s %-12s %-12s %-14s%n","ID", "Name", "Age", "Gender", "Type", "Organization", "Contact", "Earlybird", "Reg ID", "Event Name", "Ticket", "Payment", "Status");
                        System.out.println("-".repeat(152));
                        System.out.println("-".repeat(152));
                        boolean found = false;
                        // laman
                        while(sp.next()){
                        found = true;
                        System.out.printf("%-5s %-8s %-6s %-8s %-10s %-19s %-11s %-15s %-4s %-17s %-12s %-10s %-10s%n",
                        sp.getInt("participant_id"),
                        sp.getString("participant_name"),
                        sp.getInt("age"),
                        sp.getString("participant_gender"),
                        sp.getString("participant_type"),
                        sp.getString("participant_organization"),
                        sp.getString("contact_no"),
                        sp.getString("earlybird"),
                        sp.getInt("registration_id"),
                        sp.getString("event_name"),
                        sp.getString("ticket_type"),
                        sp.getString("payment_status"),
                        sp.getString("registration_status"));
                    }

                    if(!found){System.out.println("Participant not found.");}
                     System.out.println("-".repeat(152));
                     System.out.println(" ");
                     }catch(SQLException e){
                        System.out.println("Error searching participant." + e.getMessage()); }
                    break;
                default:
                System.out.println("Invalid input, please choose between 1 or 2 only.");
                 return;
            }
        }





            static void cancelRegistration(){
                System.out.println("--- CANCEL REGISTRATION ---");
                int rid =  nInt("Enter Registration ID: ");
                Registration r1 = findRegistration(rid);
                if(r1 == null){System.out.println("No Registration id Found"); return;}
                if(!(r1.getRegistrationStatus().equalsIgnoreCase("active"))){
                    System.out.println("Registration is already cancelled."); return;}

                String sqlcr = "update registrations set registration_status = 'CANCELLED' where registration_id = ?";
                try(Connection concr = conn();
                    PreparedStatement pscr = concr.prepareStatement(sqlcr)){
                        pscr.setInt(1, rid);
                        int rows = pscr.executeUpdate();
                        if(rows > 0){
                        r1.setRegistrationStatus("CANCELLED");
                        r1.getEvent().decrementRegisteredCount();
                        System.out.println("Registration cancelled successfully!");
                        System.out.println("Slot has been reopened.");
                        } else {System.out.println("Cancellation failed. Try again.");}
                }catch(SQLException e){
                    System.out.println("Error cancelling registration." + e.getMessage());
                }
            }



    public static void main(String[] args) {
        createDB();
        int choice;
        do{
        mainMenu();
        choice = 0;
        choice = nInt("Enter choice: ");
        switch (choice) {
            case 1:
                addEvent();
                break;
            case 2:
                addParticipant();
                break;
            case 3:
               chooseTType();
                break;
            case 4:
               computeRegistrationFee();
                break;
            case 5:
               confirmPayment();
                break;
            case 6:
                registrationReceipt();
                break;
            case 7:
                 participantSummary();
                break;
            case 8:
                searchParticipant();
                break;
            case 9:
                cancelRegistration();
                break;
            case 10:
            System.out.println("Thank you for using the system.");
                break;
            default:
                System.out.println("Invalid choice. please try again.");
                break;
        }

  } while(choice != 10);

}
}
