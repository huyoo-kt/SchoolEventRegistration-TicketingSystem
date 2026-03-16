abstract class Person {
    String personId;
    String fullName;
    int age;
    String gender; 
    
    
    //constructors
    Person(String personId, String fullName, int age, String gender){
        this.personId = personId;
        this.fullName = fullName;
        this.age = age;
        this.gender = gender;
    }
    
    //setters
    void setPersonId(String personId){
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
    String getPersonId(){
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


    void displayDetails(){
        
    }


}
