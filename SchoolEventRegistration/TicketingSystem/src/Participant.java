public class Participant extends Person {
    String participantType;
    String organization;
    String contactNumber;
    private String earlybird = "disable";


    Participant(String personId, String fullName, int age, String gender,String participantType,String organization,String contactNumber){
        super(personId,  fullName,  age, gender);
        this.participantType = participantType;
        this.organization = organization;
        this.contactNumber = contactNumber;
    }

    void setEarlyB(String earlyb){
        this.earlybird = earlyb;
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
    String getEarlyB(){
        return earlybird;
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

    @Override
    public void displayDetails(){
        System.out.println("Participant ID   : " + getPersonId());
        System.out.println("Name             : " + getFullName());
        System.out.println("Age              : " + getAge());
        System.out.println("Gender           : " + getGender());
        System.out.println("Type             : " + participantType);
        System.out.println("Organization     : " + organization);
        System.out.println("Contact Number   : " + contactNumber);
        System.out.println("Earlybird        : " + earlybird);
    }



}
