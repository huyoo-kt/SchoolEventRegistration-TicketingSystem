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
    return
}


void displayTicketInfo(){

}
    


}
