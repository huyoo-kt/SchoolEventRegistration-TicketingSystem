abstract class Ticket {

    private String ticketId; 
    private String ticketType;
    private double baseFee;

    
    // constructor
    Ticket(String ticketId, String ticketType, double baseFee)
    {
        this.ticketId = ticketId;
        this.ticketType = ticketType;
        this.baseFee = baseFee;
    }


    // getters
    String getTicketId(){
        return ticketId; 

    }

    String getTicketType(){
        return ticketType;
    }

    double getBaseFee(){
        return baseFee;
    }

    //setters
    void setTicketId(String ticketId){
        this.ticketId = ticketId;
    }

    void setTicketType(String ticketType){
        this.ticketType = ticketType;
    }

    void setTicketId(double baseFee){
        this.baseFee = baseFee;
    }


    //display ng info
    // override mo depende if Vip or Regular
    abstract void displayTicketInfo();
    
 








}
