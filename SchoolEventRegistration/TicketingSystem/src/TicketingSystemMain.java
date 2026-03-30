import java.io.*;
import java.sql.*;

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

        //same consept
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

        //check if meron na nung id
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


    private static void chooseTicketType(){
    System.out.println("--- CHOOSE TICKET TYPE ---");

      
        // registration
        for (Registration r1 : registrationS) {
        System.out.println(r1.getRegistrationId());
         }
        String crd = nLine("Enter Registration ID: ");
        if (findRegistration(crd) != null) { System.out.println("Registration ID already exists!"); return; }

         // participant
        for (Participant p1 : participantS) {
        System.out.println(p1.getPersonId());
         }
        String cpd = nLine("Enter participant ID: ");

         // events
        for (Event e1 : eventS) {
        System.out.println(e1.getEventId());
         }
        String ced = nLine("Enter Event ID: ");

        Participant p1 = findParticipant(cpd);
        Event e1 = findEvent(ced);
        if(p1 == null) {System.out.println("Event not found! ");   return; }
        if(e1 == null) {System.out.println("Participant not found! "); return; }


        Ticket t1;
        String tid = "T-"+crd;

        System.out.println("---Choose ticket type (REGULAR or VIP)---");
        String tType = nLine("choose type: ").toUpperCase();

        if (tType.equalsIgnoreCase("REGULAR")) {
             t1 = new RegularTicket(tid, "REGULAR",  500.00);
                Registration r1 = new Registration(crd, p1, e1, t1);
                registrationS.add(r1);
                e1.incrementRegisteredCount();
                System.out.println("Ticket assigned successfully!");

         }else if(tType.equalsIgnoreCase("VIP")){
            t1 = new VipTicket(tid, tType, 1000.00, 0.00);
            Registration r1 = new Registration(crd, p1, e1, t1);
            registrationS.add(r1);
            e1.incrementRegisteredCount();
            System.out.println("Ticket assigned successfully!");

         }else{System.out.println("Invalid, there is no such choice.");}


    }
    

       static void computeRegistrationFee(){
        System.out.println("\n--- COMPUTE REGISTRATION FEE ---");
        String rid = nLine("Enter Registration ID: ");
        Registration r1 = findRegistration(rid);
        if(r1 == null){System.out.println("No Registration id Found"); return;}
        
        PaymentCalculator pay1 = new PaymentCalculator();
        pay1.computeDiscount(r1);

        double origs = pay1.computeBaseFee(r1);
        double disc = pay1.computeDiscount(r1);
        double fFee = pay1.computeFinalFee(r1);
        r1.setOriginalFee(origs);
        r1.setDiscountAmount(disc);
        r1.setFinalFee(fFee);


        // display 
        System.out.println("Participant Name : " + r1.getParticipant().getFullName());
        System.out.println("Ticket Type      : " + r1.getTicket().getTicketType());
        System.out.println ("Base Fee        : "+String.format("%.2f", origs));
        System.out.println("Discount Amount  :  "+String.format("%.2f", disc));
        System.out.println("Final Fee        :  "+String.format("%.2f", fFee));
        System.out.println("Fee computed successfully!");
        }



        static void confirmPayment(){
            String rid = nLine("Enter Registration ID: ");
            Registration r1 = findRegistration(rid);
            if(r1 == null){System.out.println("No Registration id Found"); return;}
            if(!r1.registrationStatus.equalsIgnoreCase("active"))
            {System.out.println("Registration is cancelled. Cannot confirm payment."); return;}
            if (r1.getFinalFee() == 0 && r1.getOriginalFee() == 0) {
            System.out.println("compute the registration fee first."); return;}
            r1.setPaymentStatus("Paid");
            System.out.println("Payment confirmed successfully!");
        }


        static void registrationReceipt(){
            System.out.println("--- PRINT RECEIPT ---");
            String rid = nLine("Input Registration id: ");
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
            String rid = nLine("Input Registration id: ");
            Registration r1 = findRegistration(rid);
            if(r1 == null){System.out.println("No Registration id Found"); return;}
            r1.displayRegistrationSummary();
        }

        static void searchParticipant(){
            System.out.println("--- SEARCH PARTICIPANT ---");
            String rid = nLine("Enter Participant ID or Name: ");
            Participant p1 = findParticipant(rid);
            if(p1 == null){System.out.println("No Participant id Found"); return;}


            for(Participant p : participantS){
                if(p.getPersonId().equalsIgnoreCase(rid)|| p.getFullName().equalsIgnoreCase(rid))
                {System.out.println("Participant found!");}}
                p1.displayDetails();
                for(Registration r : registrationS){
                    System.out.println("Registration ID: "+r.getRegistrationId());
                    System.out.println("Event Name: "+ r.getEvent().getEventName());
                    System.out.println("Ticket Type: "+ r.getTicket().getTicketType());
                    System.out.println("Payment Status:"+ r.getPaymentStatus());
                    System.out.println("Registration Status: "+ r.getRegistrationStatus());
                }
        }

        static void cancelRegistration(){
            System.out.println("--- CANCEL REGISTRATION ---");
            String rid =  nLine("Enter Registration ID: ");
            Registration r1 = findRegistration(rid);
            if(r1 == null){System.out.println("No Registration id Found"); return;}

            if(!(r1.registrationStatus.equalsIgnoreCase("active"))){
                System.out.println("Registration is already cancelled."); return;}
            
            r1.setRegistrationStatus("cancelled");
            r1.getEvent().decrementRegisteredCount();
            System.out.println("Registration cancelled successfully!");
            System.out.println("Slot has been reopened.");
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
