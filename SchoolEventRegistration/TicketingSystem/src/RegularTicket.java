public class RegularTicket extends Ticket {
    

RegularTicket(String ticketId, String ticketType, double baseFee){
    super(ticketId,ticketType,baseFee);
}

RegularTicket(){}

@Override
  double computeFee(){
    return getBaseFee();
}

@Override
void displayTicketInfo(){
    System.out.println("Ticket Type      : Regular");
    System.out.println("Base Fee           :" + String.format("%.2f",getBaseFee()));
}




}
