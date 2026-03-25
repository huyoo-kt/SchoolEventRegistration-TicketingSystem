public class PaymentCalculator implements Payable{

    double computeBaseFee(Ticket ticket){
        return ticket.computeFee();
    }

    double ealyBird(){
        return 0.10;
    }

    
    double computeDiscount(Participant p1,Ticket ticket,Participant p2){
        double earlybird = 0.10;
        double disc = 0.0;
        double comp = ticket.computeFee();
          // check kung student
        if(p1.getParticipantType().equalsIgnoreCase("Student"))
        {disc += comp * 0.20;}
          // check kung early bird sya
        if(p2.getEarlyB().equalsIgnoreCase("enable"))
        {disc += comp * earlybird;}

        return disc;
    }
    

   @Override
        public double computeFinalFee(Participant p, Ticket t,Participant e){
        double base     = computeBaseFee(t);
        double discount = computeDiscount(p, t, e);
        return base - discount;
    }
    


}
