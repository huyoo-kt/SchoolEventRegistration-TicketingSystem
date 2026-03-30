public class PaymentCalculator implements Payable{

    
    Registration registration;

    double computeBaseFee(Registration registration){
        return registration.getTicket().computeFee();
    }

    double computeDiscount(Registration registration){
        double earlybird = 0.10; // early b dicount
        double disc = 0.0;
        double comp =  registration.getTicket().computeFee();
          // check kung student
        if(registration.getParticipant().getParticipantType().equalsIgnoreCase("Student"))
        {disc += comp * 0.20;}
          // check kung early bird sya
        if(registration.getParticipant().getEarlyB().equalsIgnoreCase("enable"))
        {disc += comp * earlybird;}

        return disc;
    }
    
      @Override
        public double computeFinalFee(Registration registration){
        double base     = computeBaseFee(registration);
        double discount = computeDiscount(registration);
        double finl = base - discount;
        return  finl;
    }



}
