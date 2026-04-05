import java.sql.*;
import java.util.*;

// notes hindi mo pa na memerge sa orginal sa github to.

public class TicketingSystemMain {
    
    private static Scanner sc = new Scanner(System.in);

    // para sa temporary storage
    private static List<Event> eventS = new ArrayList<>();
    private static List<Participant> participantS = new ArrayList<>();
    private static List<Registration> registrationS = new ArrayList<>();


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

    // find specifics per list
    static Event findEvent(int id) {
        for (Event e : eventS)
            if (e.getEventId() == id ) return e;
        return null;
    }
 
    static Participant findParticipant(int id) {
        for (Participant p : participantS)
            if (p.getPersonId() == id ) return p;
        return null;
    }

    // find using name
    static Participant findParticipantName(String name){
          for (Participant p : participantS)
            if (p.getFullName().equalsIgnoreCase(name)) return p;
        return null;
    }
 
    static Registration findRegistration(int id) {
        for (Registration r : registrationS)
            if (r.getRegistrationId() == id) return r;
        return null;
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
                String ctp = "create table if not exists participants (participant_id INTEGER PRIMARY KEY AUTOINCREMENT, participant_name TEXT NOT NULL, age INTEGER NOT NULL, participant_type TEXT NOT NULL, participant_organization TEXT NOT NULL, contact_no TEXT NOT NULL, earlybird INTEGER NOT NULL)";
                try(PreparedStatement CtableP = CdataB.prepareStatement(ctp,Statement.RETURN_GENERATED_KEYS)){
                    CtableP.execute();
                    System.out.println("Participant table created.");
                }

                //Registration table
                String ctr = " create table if not exists registrations (registration_id INTEGER PRIMARY KEY AUTOINCREMENT, participant_id INTEGER NOT NULL, event_id INTEGER NOT NULL, ticket_type TEXT NOT NULL, original_fee REAL, discount_amount REAL, final_fee REAL, payment_status TEXT, registration_status TEXT DEFAULT 'ACTIVE', FOREIGN KEY (participant_id) REFERENCES participants(id), FOREIGN KEY (event_id) REFERENCES events(id))";
                try(PreparedStatement CtableR = CdataB.prepareStatement(ctr,Statement.RETURN_GENERATED_KEYS)  ){
                    CtableR.execute();
                    System.out.println("Registration table created.");
                }





        }catch(SQLException e){
            System.out.println("Error creating database." + e.getMessage());
        }


        }
    


   private static void addEvent(List<Event> e1){
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
        PreparedStatement ps1 = con1.prepareStatement(sq1, Statement.RETURN_GENERATED_KEYS)){
        ps1.setString(1, ename);
        ps1.setString(2, evenue);
        ps1.setString(3, edate);
        ps1.setInt(4, ecapacity);
        int rows = ps1.executeUpdate();

        if (rows > 0) {
            ResultSet getId = ps1.getGeneratedKeys();

            if (getId.next()) {
                int eid = getId.getInt(1);
                e1.add(new Event(eid, ename, evenue, edate, ecapacity));
            }
            System.out.println("Event added successfully.");
        }
    } catch(SQLException e){
        System.out.println("Error inserting data: " + e.getMessage());
    }
}



    private static void addParticipant(List<Participant> p1){
        System.out.println("---REGISTER PARTICIPANT---");
 
        String pfn = nLine("Enter Full Name  : ");
        int page = nInt("Enter Age           : ");
        String pt = nLine("Enter participant type: ");
        String pg = nLine("Enter gender: ");
        String po = nLine("Enter participant_organization: ");
        String pc = nLine("Enter contact_no: "); 
        String eb = nLine("Earlybird?: ").toLowerCase().trim();


        String pql = "INSERT INTO participants(participant_name, age, participant_type, participant_organization, contact_no, earlybird ) VALUES(?, ?, ?, ?, ?, ?)";
        try(Connection con2 = conn();
            PreparedStatement  ps2 = con2.prepareStatement(pql,Statement.RETURN_GENERATED_KEYS)){
                ps2.setString(1, pfn);
                ps2.setInt(2, page);
                ps2.setString(3, pg);
                ps2.setString(4, pt);
                ps2.setString(5, po);
                ps2.setString(6, pc);
                ps2.setString(7, eb);
                int rows = ps2.executeUpdate();

          if (rows > 0) {
            ResultSet getId = ps2.getGeneratedKeys();
            if (getId.next()) {
                int pid = getId.getInt(1);
                p1.add(new Participant(pid, pfn, page, pg, pt, po, pc,eb));
            }
         System.out.println("Participant registered sucessfully.");  
        }


        }catch(SQLException e){
           System.out.println("Error adding Participant. "+e.getMessage());
        }

       
    }
  

    private static void chooseTicketType(){
    System.out.println("--- CHOOSE TICKET TYPE ---");
       
        // participant
        for (Participant p1 : participantS) {
        System.out.println(p1.getPersonId());
         }
        int cpd = nInt("Enter participant ID: ");

         // events
        for (Event e1 : eventS) {
        System.out.println(e1.getEventId());
         }
        int ced = nInt("Enter Event ID: ");

        Participant p1 = findParticipant(cpd);
        Event e1 = findEvent(ced);
        if(p1 == null) {System.out.println("Participant not found! "); return; }
        if(e1 == null) {System.out.println("Event not found! "); return; }


        Ticket t1;   
        System.out.println("---Choose ticket type (REGULAR or VIP)---");
        String tType = nLine("choose type: ").toUpperCase();

        String urlt = "insert into registrations(participant_id, event_id, ticket_type) values(?,?,?)";
        try(Connection cont = conn();
            PreparedStatement pst = cont.prepareStatement(urlt,Statement.RETURN_GENERATED_KEYS)){

        if (tType.equalsIgnoreCase("REGULAR")) {
                // insert to database
                // dito kana
                pst.setInt(1,cpd);
                pst.setInt(2,ced);
                pst.setString(3, tType);
                int rows = pst.executeUpdate();

                if(rows > 0){
                ResultSet getId = pst.getGeneratedKeys();
                if (getId.next()){
                int rid = getId.getInt(1);
                String tid = "T-"+rid;
                t1 = new RegularTicket(tid, "REGULAR",  500.00);
                Registration r1 = new Registration(rid, p1, e1, t1);
                registrationS.add(r1);
                e1.incrementRegisteredCount();
                }
                System.out.println("Ticket assigned successfully!");
            }
        }
         else if(tType.equalsIgnoreCase("VIP")){

                // insert to database
                // dito kana
                pst.setInt(1,cpd);
                pst.setInt(2,ced);
                pst.setString(3, tType);
                int rows = pst.executeUpdate();

                if(rows > 0){
                ResultSet getId = pst.getGeneratedKeys();
                if (getId.next()){
                int rid = getId.getInt(1);
                String tid = "T-"+rid;
                t1 = new VipTicket(tid, tType, 1000.00, 0.00);
                Registration r1 = new Registration(rid, p1, e1, t1);
                registrationS.add(r1);
                e1.incrementRegisteredCount();
                System.out.println("Ticket assigned successfully!");
         }
        }
        }else{System.out.println("Invalid, there is no such choice.");} 

    }catch(SQLException e){System.out.println("Error Assigning ticket" + e .getMessage());}
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
     

        //original_fee REAL, discount_amount REAL, final_fee REAL
        String rupdt = "update registrations set original_fee = ?,  discount_amount = ?, final_fee = ? where registration_id = ? ";
            try(Connection crf = conn();
            PreparedStatement pcrf = crf.prepareStatement(rupdt)){

                pcrf.setDouble(1, origs);
                pcrf.setDouble(2, disc);
                pcrf.setDouble(3,fFee);
                pcrf.setInt(4, rid);
                int rows = pcrf.executeUpdate();
                if(rows > 0 ){
                r1.setOriginalFee(origs);
                r1.setDiscountAmount(disc);
                r1.setFinalFee(fFee);
                System.out.println("Registration fee computed sucessfully");
                } else {System.out.println("Failed to compute registration fee.");}
        }catch(SQLException e){System.out.println("Error computing registration fee." + e.getMessage());}

        // display 
        System.out.println("Participant Name : " + r1.getParticipant().getFullName());
        System.out.println("Ticket Type      : " + r1.getTicket().getTicketType());
        System.out.println ("Base Fee        : "+String.format("%.2f", origs));
        System.out.println("Discount Amount  :  "+String.format("%.2f", disc));
        System.out.println("Final Fee        :  "+String.format("%.2f", fFee));
        System.out.println("Fee computed successfully!");
        }





        // payment_status - eto muna update mo, registration_status
        static void confirmPayment(){
            int rid = nInt("Enter Registration ID: ");
            Registration r1 = findRegistration(rid);
            if(r1 == null){System.out.println("No Registration id Found"); return;}

            if(!r1.getRegistrationStatus().equalsIgnoreCase("active"))
            {System.out.println("Registration is cancelled. Cannot confirm payment."); return;}

            if (r1.getFinalFee() == 0 && r1.getDiscountAmount() == 0 && r1.getOriginalFee() == 0) {
            System.out.println("compute the registration fee first."); return;}

            String sqlps = "update registrations set payment_status = ? where registration_id = ?";
            try(Connection conf = conn();
                PreparedStatement confp = conf.prepareStatement(sqlps)){
                confp.setString(1, "Paid");
                confp.setInt(2, rid);
                int rows = confp.executeUpdate();
                if(rows>0){
                    r1.setPaymentStatus("Paid");
                    System.out.println("Payment confirmed successfully!");
                }else {System.out.println("Failed to confirm payment.");}
               
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
            Receipt re1;
            String rno = "REC-"+rid;
            re1 = new Receipt(rno, r1);
            re1.printReceipt();
        }

        static void participantSummary(){
            System.out.println("--- PARTICIPANT SUMMARY ---");
            int rid = nInt("Input Registration id: ");
            Registration r1 = findRegistration(rid);
            if(r1 == null){System.out.println("No Registration id Found"); return;}
            r1.displayRegistrationSummary();
        }

        static void searchParticipant(){
            System.out.println("--- SEARCH PARTICIPANT ---");
            int choose = nInt("SEARCH USING NAME OR ID(1 for id and 2 for name): ");

            switch (choose) {
                case 1: 
                // search using id
                    int sid = nInt("Enter participants id: ");
                    Participant p1 = findParticipant(sid);
                    if(p1 == null){System.out.println("there is no such participant.");return;}
                    System.out.println("Participant found!");
                    // display whole
                    p1.displayDetails();
                    for(Registration r : registrationS){
                    if(r.getParticipant().getPersonId() == sid){
                    System.out.println("Registration ID: "+r.getRegistrationId());
                    System.out.println("Event Name: "+ r.getEvent().getEventName());
                    System.out.println("Ticket Type: "+ r.getTicket().getTicketType());
                    System.out.println("Payment Status:"+ r.getPaymentStatus());
                    System.out.println("Registration Status: "+ r.getRegistrationStatus());
                    }
                    }
                    break;
                case 2: 
                // search using name
                    String sname = nLine("Enter participants name: ");
                    Participant p2 = findParticipantName(sname);
                    if(p2 == null){System.out.println("there is no such participant.");return;}
                    System.out.println("Participant found!");
                    p2.displayDetails();
                    for(Registration r : registrationS){
                    if(r.getParticipant().getPersonId() == p2.getPersonId()){
                    System.out.println("Registration ID: "+r.getRegistrationId());
                    System.out.println("Event Name: "+ r.getEvent().getEventName());
                    System.out.println("Ticket Type: "+ r.getTicket().getTicketType());
                    System.out.println("Payment Status:"+ r.getPaymentStatus());
                    System.out.println("Registration Status: "+ r.getRegistrationStatus());
                    }
                    }
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

                String sqlcr = "update registrations set registration_status = ? where registration_id = ?";
                try(Connection concr = conn();
                    PreparedStatement pscr = concr.prepareStatement(sqlcr)){
                        pscr.setString(1, "cancelled");
                        pscr.setInt(2, rid);
                        int rows = pscr.executeUpdate();
                        if(rows > 0){
                        r1.setRegistrationStatus("cancelled");
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
                addEvent(eventS);
                break;
            case 2:
                addParticipant(participantS);
                break;
            case 3:
                chooseTicketType();
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
