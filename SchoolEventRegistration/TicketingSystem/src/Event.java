public class Event {
    String eventId;
    String eventName;
    String venue;
    String eventDate;
    int capacity = 0;
    int registeredCount = 0;

    Event( String eventId, String eventName,String venue, String eventDate,int capacity){
        this.eventId = eventId;
        this.eventName = eventName;
        this.venue = venue;
        this.eventDate = eventDate;
        this.capacity = capacity;
    }
    
    //getters
    String getEventId(){
        return eventId;
    }

    String getEventName(){
        return eventName;
    }

    String getVenue(){
        return venue;
    }

    String getEventdate(){
        return eventDate;
    }

    int getCapacity(){
        return capacity;
    }

    int getRegisteredCount(){
        return registeredCount;
    }

    //setters
    void setEventId(String eventId){
        this.eventId = eventId;
    }

    void setEventName(String eventName){
        this.eventName = eventName;
    }

    void setVenue(String venue){
        this.venue = venue;
    }

    void setEventDate(String eventDate){
        this.eventDate = eventDate;
    }

    void setCapacity(int capacity){
        this.capacity = capacity;
    }

    void setRegisteredCount(int registeredCount){
        this.registeredCount = registeredCount;
    }


    void incrementRegisteredCount(){  
        registeredCount++;
    }

    void decrementRegisteredCount(){
        if(registeredCount > 0){
        registeredCount--;
        }
        else{
            System.out.println("You have no registered Event.");
        }
     
    }

    void displayEventInfo(){
        System.out.println("Event ID         : " + eventId);
        System.out.println("Event Name       : " + eventName);
        System.out.println("Venue            : " + venue);
        System.out.println("Event Date       : " + eventDate);
        System.out.println("Capacity         : " + capacity);
        System.out.println("Registered Count : " + registeredCount);
    }

    





}
