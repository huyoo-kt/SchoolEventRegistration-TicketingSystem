abstract class Person {
    int  personId;
    private String fullName;
    private int age;
    private String gender; 
    
    
    //constructors
    Person(int personId, String fullName, int age, String gender){
        this.personId = personId;
        this.fullName = fullName;
        this.age = age;
        this.gender = gender;
    }
    
    //setters
    void setPersonId(int personId){
        this.personId = personId;
    }
    void setFullName(String fullname){
        this.fullName = fullname;
    }
    void setAge(int age){
        this.age = age;
    }
    void setGender(String gender){
        this.gender = gender;
    }

    //getters
    int getPersonId(){
        return personId;
    }
    String getFullName(){
        return fullName;
    }
    int getAge(){
        return age;
    }
    String getGender(){
        return gender;
    }

    public abstract void displayDetails();


}
