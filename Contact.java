/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Chase Minden
 */
//Overview: Mutable class that holds a full name, phone#, and email
public class Contact {
    String firstName, lastName, email, phone;
    /*
    EFFECTS: Constructor
    Initializes class with all values as empty strings
    */
    public Contact(){
        firstName = "";
        lastName = "";
        email = "";
        phone = "";
    }
    /*
    EFFECTS: Overloaded Constructor
    Initializes class with fname and lname taken as parameters
    Other values are still empty strings
    */
    public Contact(String fname, String lname){
        firstName = fname;
        lastName = lname;
        email = "";
        phone = "";
    }
}


