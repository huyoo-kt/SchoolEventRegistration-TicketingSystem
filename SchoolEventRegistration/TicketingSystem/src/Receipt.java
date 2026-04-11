public class Receipt {

    String receiptNo;
    Registration registration;
    
    Receipt(String receiptNo,Registration Registration){
        this.receiptNo = receiptNo;
        this.registration = Registration;
    }


    //6
    void printReceipt(){
        System.out.println("=================================================");
        System.out.println("         ===REGISTRATION RECEIPT===");
        System.out.println("=================================================");
        System.out.println("Receipt No         : " + receiptNo);
        System.out.println("Registration ID    : " + registration.getRegistrationId());
        System.out.println("Participant Name   : " + registration.getParticipant().getFullName());
        System.out.println("Event Name         : " + registration.getEvent().getEventName());
        System.out.println("Ticket Type        : " + registration.getTicket().getTicketType());
        System.out.println("Original Fee       : " + String.format("%.2f",registration.getOriginalFee()));
        System.out.println("Discount Amount    : " + String.format("%.2f",registration.getDiscountAmount()));
        System.out.println("Final Fee          : " + String.format("%.2f",registration.getFinalFee()));
        System.out.println("Payment Status     : " + registration.getPaymentStatus());
        System.out.println("Registration Status: " + registration.getRegistrationStatus());
        System.out.println("=================================================");
    }

}
