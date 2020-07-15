import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Chase Minden
 */

//OVERVIEW: Mutable AddressBook that holds class contacts
//They hold contacts in an array [contact1, contact2, contact3... contactn]
//AddressBook cannot hold duplicate contacts, inputing duplicates will return
//an exception
//AddressBook must have contacts with FirstNames and LastNames, email and phone
//are optional
public class AddressBook {
    
    private ArrayList<Contact> conArray = new ArrayList<>();
    private boolean duplicate;
    private Contact conIn, conOut;
    private String input;
    private Path inputFilePath = Paths.get("contacts.dat");
    private Path outputFilePath = Paths.get("contacts.dat");
    private int count = 0;
    
    /*
    EFFECTS: Constructor. Reads in contacts from contacts.dat if it exists, 
    or creates contacts.dat if it doesn't
    Contacts read in from contacts.dat are put into ArrayList conArray.
    */
    public AddressBook()throws IOException{
        if (Files.notExists(inputFilePath)) {
            try ( var outputTextFile = 
                    new PrintWriter(Files.newBufferedWriter(outputFilePath))) {
            }
        } else {
            try ( var inputTextFile = Files.newBufferedReader(inputFilePath)) {
                while ((input = inputTextFile.readLine()) != null) {
                    conIn = new Contact();
                    conIn.firstName = input;
                    conIn.lastName = inputTextFile.readLine();
                    conIn.email = inputTextFile.readLine();
                    conIn.phone = inputTextFile.readLine();
                    count++;
                    conArray.add(conIn);
                    String skipLine = inputTextFile.readLine();
                }
            }
        }
    }
    
    /*
    EFFECTS: Mutator.
    -if any info in newContact is null, throws NullPointerException
    -if the first and/or last name in newContact is empty, throws EmptyNameException
    -otherwise passes the info from newContact to Contact conOut, which
    is then added to conArray. This is done to avoid exposing rep.
    */
    public void addContact(Contact newContact) throws IOException {
        duplicate = false;
        if (newContact.firstName == null || newContact.lastName == null 
                || newContact.email == null || newContact.phone == null) {
            throw new NullPointerException("contact info cannot be null, "
                    + "initialize contact in AddContact");
        }
        if ("".equals(newContact.firstName) || "".equals(newContact.lastName)) {
            throw new EmptyNameException("contact first and/or last name "
                    + "cannot be empty in AddContact");
        }
        for (int i = 0; i < conArray.size(); i++) {
            if (newContact.firstName.equals(conArray.get(i).firstName) 
                    && newContact.lastName.equals(conArray.get(i).lastName)) {
                duplicate = true;
            }
        }
        
        conOut = new Contact();
        conOut.firstName = newContact.firstName;
        conOut.lastName = newContact.lastName;
        conOut.email = newContact.email;
        conOut.phone = newContact.phone;

        if (duplicate == true) {
            throw new DuplicateContactException("duplicate detected in AddContact");
        } else {
            conArray.add(conOut);
            count++;
        }

        try ( var outputTextFile = 
                new PrintWriter(Files.newBufferedWriter(outputFilePath))) {
            for (int i = 0; i < conArray.size(); i++) {
                outputTextFile.println(conArray.get(i).firstName);
                outputTextFile.println(conArray.get(i).lastName);
                outputTextFile.println(conArray.get(i).email);
                outputTextFile.println(conArray.get(i).phone);
                outputTextFile.println("---");
            }
        }
    }
    
    /*
    EFFECTS: Mutator.
    -if the first and/or last name is null, throws NullPointerException
    -if the first and/or last name is empty, throws EmptyNameException
    -if index is -1 after searching the ArrayList, throws ContactNotFoundException
    -otherwise returns the index of where the contact was found in the ArrayList
    and removes it
    */
    public void deleteContact(String firstName, String lastName) throws IOException {
        int index = -1;
        if (firstName == null || lastName == null) {
            throw new NullPointerException("parameter must include first "
                    + "and last name in deleteContact");
        }
        if ("".equals(firstName) || "".equals(lastName)) {
            throw new EmptyNameException("first and/or last name "
                    + "cannot be empty in deleteContact");
        }
        for (int i = 0; i < conArray.size(); i++) {
            if (firstName.equals(conArray.get(i).firstName) 
                    && lastName.equals(conArray.get(i).lastName)) {
                index = i;
            }
        }
        
        if (index != -1) {
            conArray.remove(index);
            count--;
        } else {
            throw new ContactNotFoundException("Contact not in array for deleteContact");
        }
        
        try ( var outputTextFile = 
                new PrintWriter(Files.newBufferedWriter(outputFilePath))) {
            for (int i = 0; i < conArray.size(); i++) {
                outputTextFile.println(conArray.get(i).firstName);
                outputTextFile.println(conArray.get(i).lastName);
                outputTextFile.println(conArray.get(i).email);
                outputTextFile.println(conArray.get(i).phone);
                outputTextFile.println("---");
            }
        }
    }
    
    /*
    EFFECTS: Observer.
    -if the last name is null, throws NullPointerException
    -if the last name is empty, throws EmptyNameException
    -otherwise searches conArray for Contacts matching the last name, and adds
    that into into a new ArrayList. That new ArrayList size is used to create
    a Contact array, which takes in all the contacts of the new ArrayList and
    returns. This is done to change ArrayList to Contact array, and avoid
    exposing rep.
    */
    public Contact[] searchByLastName(String lastName) {
        ArrayList<Contact> returnArray = new ArrayList<>();
        int conCount = 0;
        if (lastName == null) {
            throw new NullPointerException("parameter is null, "
                    + "must include last name in searchByLastName");
        }
        if ("".equals(lastName)) {
            throw new EmptyNameException("parameter is empty, "
                    + "must include last name in searchByLastName");
        }

        for (int i = 0; i < conArray.size(); i++) {
            if (lastName.equals(conArray.get(i).lastName)) {
                conCount++;
                Contact newReturn = new Contact();
                newReturn.firstName = conArray.get(i).firstName;
                newReturn.lastName = conArray.get(i).lastName;
                newReturn.email = conArray.get(i).email;
                newReturn.phone = conArray.get(i).phone;
                returnArray.add(newReturn);
            }
        }
        Contact[] returnContact = new Contact[conCount];
        for (int i = 0; i < conCount; i++) {
            returnContact[i] = returnArray.get(i);
        }
        return returnContact;
    }
    
    /*
    EFFECTS: Observer.
    - Makes a Contact array the size of the conArray size, then moves all the 
    contacts into the Contact array and returns. This is done to change ArrayList
    to Contact array and avoid exposing rep.
    */
    public Contact[] list(){
         Contact[] returnContact = new Contact[conArray.size()];
         for(int i = 0; i < conArray.size(); i++){
             Contact sub = new Contact();
             sub.firstName = conArray.get(i).firstName;
             sub.lastName = conArray.get(i).lastName;
             sub.email = conArray.get(i).email;
             sub.phone = conArray.get(i).phone;
             returnContact[i] = sub;
         }
        return returnContact;
    }
    
    
    /**
     * EFFECTS: Observer
     * - Returns the number of contacts in the array
     */
    public Integer count(){
        return count;
    }
    
    
    /**
     * EFFECTS: Returns an iterator that produces all the elements of this
     * (a set of contacts), each exactly once, in alphabetical order.
     *
     * REQUIRES: this must not be modified while the iterator is in use.
     */
    public Iterator<Contact> contactsSortedByName(){
        return new AddressBookGen(this);
    }
    
    //inner class
    private static class AddressBookGen implements Iterator<Contact> {

        // The abstraction function for AddressBookGen is:
        //   AF(g) = [g.Addb.conArray.get(someIndex) < g.Addb.conArray.get(someIndex2!=someIndex1) < ... etc]
        // The rep invariant for AddressBookGen is 
        //   I(g) = g.Addb != null && (0 < index < conArray.size)
        //   
        private AddressBook Addb;
        private int index;
        private Contact smallestCt;
        private boolean similar;
        private int numReturned;
        private ArrayList<Contact> contactsPrinted;
        private int currentIndex;

        AddressBookGen(AddressBook a) {
            // REQUIRES: a != null
            index = 0;          //index being used to navigate set
            Addb = a;           //AddressBook class we use
            numReturned = 0;    //used to count number of contacts returned
            currentIndex = 0;   //used to preserve index during manipulation
            contactsPrinted = new ArrayList<>();    //holds list of contacts already printed
        }

        @Override
        public boolean hasNext() {
            return index < Addb.conArray.size();
        }

        //Check the arraylist ContactsPrinted and see if the contact passed (c)
        //is there. If so, return false because the contact has been printed already
        private boolean notPrinted(Contact c) {
            if (c == null) {
                return true;
            }
            for (int i = 0; i < contactsPrinted.size(); i++) {
                if (c == contactsPrinted.get(i)) {
                    return false;
                }
            }
            return true;
        }

        //REQUIRES: A and B not be null, A and B be UNIQUE 
        //but they can be similar or even printed already
        private Contact lessThan(Contact a, Contact b) {
            Contact ExpectedLess = a;
            Contact ExpectedGreater = b;
            Contact Return = ExpectedLess;
            int fnLength, lnLength;

            //check if b (actually smallestCT) is printed already
            //if so, return a (actually nextCT) because it has to move forward
            if (notPrinted(b) == false) {
                return ExpectedLess;
            }
            
            //check if the last names are similar. If so, start comparison
            //of first names
            if (ExpectedLess.lastName.equals(ExpectedGreater.lastName)) {
                //get the length of the shorter first name for the for loop 
                //to prevent comparing letters to blanks
                if (ExpectedLess.firstName.length() <= ExpectedGreater.firstName.length()) {
                    fnLength = ExpectedLess.firstName.length();
                } else {
                    fnLength = ExpectedGreater.firstName.length();
                }
                /*cycle through each letter, if one is smaller than the other
                        **that contact is passed out, if they are equal then 
                        **no contact is passed yet
                 */
                for (int i = 0; i < fnLength; i++) {
                    if (ExpectedLess.firstName.charAt(i) < ExpectedGreater.firstName.charAt(i)) {
                        similar = false;
                        Return = ExpectedLess;
                        break;
                    } else if (ExpectedLess.firstName.charAt(i) > ExpectedGreater.firstName.charAt(i)) {
                        Return = ExpectedGreater;
                        similar = false;
                        break;
                    } else {
                        similar = true;
                    }
                }
                /*if all letters match for the loop then one name is longer (ie Paul vs Pauline)
                        **because duplicates are prohibited, so pass the contact
                        **with the shorter name
                 */
                if (similar == true) {
                    if (ExpectedGreater.firstName.length() == fnLength) {
                        Return = ExpectedGreater;
                    }
                }
            }//end of first name comparison if this is run
             /* If not, that means the last names arent equal
             ** so begin this section to compare last names
             */
            else {
                //get the length of the shortest last name to use in the future for loop
                if (ExpectedLess.lastName.length() <= ExpectedGreater.lastName.length()) {
                    lnLength = ExpectedLess.lastName.length();
                } else {
                    lnLength = ExpectedGreater.lastName.length();
                }
                /*cycle through each letter, if one is smaller than the other
                **that contact is passed out, else the letters are equal and
                 **no contact is passed yet
                 */
                for (int i = 0; i < lnLength; i++) {
                    if (ExpectedLess.lastName.charAt(i) < ExpectedGreater.lastName.charAt(i)) {
                        Return = ExpectedLess;
                        similar = false;
                        break;
                    } else if (ExpectedLess.lastName.charAt(i) > ExpectedGreater.lastName.charAt(i)) {
                        Return = ExpectedGreater;
                        similar = false;
                        break;
                    } else {
                        similar = true;
                    }
                }
                /*if all letters match for the loop then one name is longer (ie Alex vs Alexis)
                **because they cannot be equal, so pass the contact
                **with the shorter name
                */
                if (similar == true) {
                    if (ExpectedGreater.lastName.length() == lnLength) {
                        Return = ExpectedGreater;
                    } else {
                        Return = ExpectedLess;
                    }
                }
            }
            return Return;
        }

        @Override
        public Contact next() {
            if (hasNext()) {
                index++;
                currentIndex = index;
                
                //if the number of contacts returns is less than the size and
                //the current smallestCt has already been printed, then reset
                //the index to one to restart the search
                if (numReturned < Addb.conArray.size() && notPrinted(smallestCt) == false) {
                    index = 1;
                }

                smallestCt = Addb.conArray.get(index - 1);    //assign smallestCt to the current contact
                index = 0;
                while (hasNext()) {                           //loop as long as there are contacts
                    index++;
                    Contact nextCt = Addb.conArray.get(index - 1);
                            //next < smallest                       and it has not been printed yet
                    if (nextCt == lessThan(nextCt, smallestCt) && notPrinted(nextCt) == true) {
                        smallestCt = nextCt;    //then assign as current smallest
                    }
                }

                index = currentIndex;
                contactsPrinted.add(smallestCt);
                numReturned++;
                //pass info to another contact to preserve rep
                Contact SafeReturn = new Contact();
                SafeReturn.firstName = smallestCt.firstName;
                SafeReturn.lastName = smallestCt.lastName;
                SafeReturn.email = smallestCt.email;
                SafeReturn.phone = smallestCt.phone;
                return SafeReturn;
            } else {
                throw new NoSuchElementException("Contacts");
            }
        }

        @Override
        public void remove() {
            throw new NoSuchElementException("Contacts");
        }
    }//end of private function
    
    public Boolean repOk(){
       if(conArray == null){
       return false;
       }
       for (int i = 0; i < conArray.size(); i++) {
            Contact x = conArray.get(i);
            for(int j = i + 1; j < conArray.size(); j++){ 
                if(x == conArray.get(j)){
                return false;
                }
            }
        }
        return true;
    }
    
    @Override
    public String toString() {
        String fn, ln, phone, email;
        String fullString = "Contact List:";
        fullString = fullString + "\n";
        for (int i = 0; i < conArray.size(); i++) {
            fn = conArray.get(i).firstName;
            ln = conArray.get(i).lastName;
            phone = conArray.get(i).phone;
            email = conArray.get(i).email;
            fullString = fullString + fn + " " + ln + " " + phone + " " + email + "\n";
        }
        return fullString;
    }
}


