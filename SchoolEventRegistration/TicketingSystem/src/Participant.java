public class Participant extends Person {
    String participantType;
    String organization;
    String contactNumber;

    Participant(String personId, String fullName, int age, String gender,String participantType,String organization,String contactNumber){
        super(personId,  fullName,  age, gender);
        this.participantType = participantType;
        this.organization = organization;
        this.contactNumber = contactNumber;
    }

    void setParticipantType(String participantType){
        this.participantType = participantType;
    }
    void setOrganization(String organization){
        this.organization = organization;
    }
    void setContactNumber(String contactNumber){
        this.contactNumber = contactNumber;
    }
    String getParticipantType(){
        return participantType;
    }
    String getOrganization(){
        return organization;
    }
    String getContactNumber(){
        return contactNumber;
    }

    
    void displayDetails(){



    }



}
