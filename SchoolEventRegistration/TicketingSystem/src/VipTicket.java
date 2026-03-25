public class VipTicket extends Ticket  {
    
double premiumFee;

// consctructor
VipTicket(String ticketId, String ticketType, double baseFee, double premiumFee){
    super(ticketId,ticketType,baseFee);
    this.premiumFee = premiumFee;
}

//getters
double getPremiumFee(){
    return premiumFee;
}

//setters
void setPremiumFee(double premiumFee){
this.premiumFee = premiumFee;
}


double computeFee(){
    return getBaseFee() * 2;
}


void displayTicketInfo(){
    System.out.println("Ticket Type      : VIP");
    System.out.println("Base Fee            : " + String.format("%.2f",getBaseFee()));
    System.out.println("VIP Access       : Priority Seating");
    System.out.println("Event Kit        : Included");
}
    


}
