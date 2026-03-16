public class Registration {

    String registrationId;
    Event event;
    Ticket ticket;
    double originalFee;
    double discountAmount;
    double finalFee;
    String paymentStatus;
    String registrationStatus;
    


    //getters
    public String getRegistrationId() {
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


    //setters
    public void setRegistrationId(String registrationId) {
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


    void displayRegistrationSummary(){
        
    }



}
