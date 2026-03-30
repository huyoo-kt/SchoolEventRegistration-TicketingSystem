public class PaymentCalculator implements Payable{

    Participant participants;
    Ticket ticket;
    Registration registration;

    double computeBaseFee(){
        return ticket.computeFee();
    }

    double computeDiscount(){
        double earlybird = 0.10; // early b dicount
        double disc = 0.0;
        double comp = ticket.computeFee();
          // check kung student
        if(participants.getParticipantType().equalsIgnoreCase("Student"))
        {disc += comp * 0.20;}
          // check kung early bird sya
        if(participants.getEarlyB().equalsIgnoreCase("enable"))
        {disc += comp * earlybird;}

        return disc;
    }
    
      @Override
        public double computeFinalFee(){
        double base     = computeBaseFee();
        double discount = computeDiscount();
        return base - discount;
    }



}
