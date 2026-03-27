import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.*;


public class TicketingSystemMain {
    
    private static Scanner sc = new Scanner(System.in);

    // para sa temporary storage
    private static List<Event> eventS = new ArrayList<>();
    private static List<Participant> participantS = new ArrayList<>();
    private static List<Registration> registrationS = new ArrayList<>();


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
    static Event findEvent(String id) {
        for (Event e : eventS)
            if (e.getEventId().equalsIgnoreCase(id)) return e;
        return null;
    }
 
    static Participant findParticipant(String id) {
        for (Participant p : participantS)
            if (p.getPersonId().equalsIgnoreCase(id)) return p;
        return null;
    }
 
    static Registration findRegistration(String id) {
        for (Registration r : registrationS)
            if (r.getRegistrationId().equalsIgnoreCase(id)) return r;
        return null;
    }
    

    private static void addEvent(List<Event> e1){
        System.out.println("---ADD EVENT---");
        String eid = nLine("Event id: ");
        if( findEvent(eid) != null){System.out.println("Event ID already on Exist"); return;}
        String ename = nLine("Event name: ");
        String evenue = nLine("Event venue: ");
        String edate = nLine("Event date: ");
        int ecapacity = 0;
        while(ecapacity < 0){
             ecapacity = nInt("Event capacity: ");
            if(ecapacity>0){break;}
        }
        e1.add(new Event(eid, ename, evenue, edate, ecapacity));
        System.out.println("Event added sucessfully.");  
    }

    // dito kana yaw, bukas mo nalang tuloy
    private static void addParticipant(List<Participant> p1){
        System.out.println("---REGISTER PARTICIPANT---");
        String pid = nLine("Enter id: ");
        if( findParticipant(pid) != null){System.out.println("Participant ID already on Exist"); return;}
        String pfn = nLine("Enter Full Name  : ");
        int page = nInt("Enter Age           : ");
        String pt = nLine("Enter participant type: ");
        String po = nLine("Enter participant_organization: ");
        String pc = nLine("Enter contact_no: "); 
        String eb = nLine("Earlybird?: ").toLowerCase().trim();
        p1.add(new Participant(pid, pfn, page, pt, po, pc, eb));
        System.out.println("Participant registered sucessfully.");  
    }

    private static void addRegistration(List<Registration> r1){

    }



   

    private static void chooseTicketType(){
    System.out.println("--- CHOOSE TICKET TYPE ---");

        
        for (Registration r1 : registrationS) {
        System.out.println(r1.getRegistrationId());
         }
        String crd = nLine("Choose Registration ID: ");


        try{
        FileWriter ticketFIle = new FileWriter("Ticket.txt");
        System.out.println("Error writing file");
        for (Participant p1 : participantS) {
        System.out.println(p1.getPersonId());
         }
        String cpd = nLine("Choose participant ID: ");
        for (Event e1 : eventS) {
        System.out.println(e1.getEventId());
         }
        String ced = nLine("Choose Event ID: ");
        ticketFIle.write(crd +cpd + ced + "\n");
        ticketFIle.close();
        System.out.println("Data written successfully.");

        System.out.println("Choose ticket type");
        System.out.println("R for regular");
        System.out.println("V for vip");
        String tType = nLine("choose type (R or V): ").toUpperCase();

  


        }catch(IOException e){
            System.out.println("Error creating ticket" + e.getMessage());
        }
    }


    

    
    public static void main(String[] args) {
      
        
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
