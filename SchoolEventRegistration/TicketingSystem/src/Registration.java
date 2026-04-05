public class Registration {

    int registrationId;
    Event event;
    Ticket ticket;
    double originalFee = 0.0;
    double discountAmount = 0.0;
    double finalFee = 0.0;
    String paymentStatus = "Unpaid";
    String registrationStatus = "Active";
    Participant participant;
    
   
    Registration(int registrationID, Participant participant, Event event, Ticket ticket){
        this.registrationId = registrationID;
        this.participant = participant;
        this.event = event;
        this.ticket = ticket;
    }


    //getters
    public int getRegistrationId() {
        return registrationId;
    }

    Event getEvent() {
        return event;
    }

    Ticket getTicket() {
        return ticket;
    }

    double getOriginalFee() {
        return originalFee;
    }

    double getDiscountAmount() {
        return discountAmount;
    }

    double getFinalFee() {
        return finalFee;
    }

    String getPaymentStatus() {
        return paymentStatus;
    }

    String getRegistrationStatus() {
        return registrationStatus;
    }

    Participant getParticipant(){
        return participant;
    }


    //setters
    public void setRegistrationId(int registrationId) {
        this.registrationId = registrationId;
    }

    void setEvent(Event event) {
        this.event = event;
    }

    void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    void setOriginalFee(double originalFee) {
        this.originalFee = originalFee;
    }

    void setDiscountAmount(double discountAmount) {
        this.discountAmount = discountAmount;
    }

    void setFinalFee(double finalFee) {
        this.finalFee = finalFee;
    }
    
    void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
    
    void setRegistrationStatus(String registrationStatus) {
        this.registrationStatus = registrationStatus;
    }


    //choice 6 to, eto
    void displayRegistrationSummary(){
        System.out.println("Registration ID    : " + registrationId);
        System.out.println("Participant Name   : " + participant.getFullName());
        System.out.println("Participant Type   : " + participant.getParticipantType());
        System.out.println("Organization       : " + participant.getOrganization());
        System.out.println("Contact Number     : " + participant.getContactNumber());
        System.out.println("Event Name         : " + event.getEventName());
        System.out.println("Ticket Type        : " + ticket.getTicketType());
        System.out.println("Original Fee       : " + String.format("%.2f", originalFee));
        System.out.println("Discount Amount    : " + String.format("%.2f", discountAmount));
        System.out.println("Final Fee          : " + String.format("%.2f", finalFee));
        System.out.println("Payment Status     : " + paymentStatus);
        System.out.println("Registration Status: " + registrationStatus);
    }



}
