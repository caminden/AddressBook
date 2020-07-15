/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Chase Minden
 */
import java.io.IOException;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;

import java.util.Scanner;

import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;

/**
 * OVERVIEW: A program that tests the AddressBook class
 */
public class AddressBookTester2 {
    public static void main(String[] args) {
        System.out.println("The tester program will remove existing contacts.dat "
                + "file.");
        System.out.println("Presse y and then <Enter> to continue OR anything else "
                + "to stop:");
        var in = new Scanner(System.in);
        String line = in.nextLine();
        if (!line.equals("y")) {
            System.out.println("Exiting program...");
            System.exit(0);
        }
            
        final String CONTACTS_FILE_NAME = "contacts.dat";
        Path contactFilePath = Paths.get(CONTACTS_FILE_NAME);

        if (Files.exists(contactFilePath)) {
            try {
                Files.delete(contactFilePath); // delete file
            }
            catch (IOException e) {
                System.out.println("Error occured while deleting contact.dat ...");
            }
        }
        System.out.println();
        
        // try to open the address book
        System.out.println("Open the address book ...");
        AddressBook book = null;
        try {
            book = new AddressBook();
        } catch (IOException e) {
            System.out.println(e);
            System.out.println("Error occured while opening the address book. "
                    + "Program stops");
            return;
        }

        // check the empty address book
        Contact[] result = book.list();
        if (result.length == 0) {
            System.out.println("Currently, the address book is empty.");
        }
        else {
            System.out.println("Error reading contacts from the address book."
                    + "  It should be empty.");
            return;
        }
        System.out.println();

        // inserting a new contact
        System.out.println("Adding new contact aaa/aaa ...");
        try {
            book.addContact(new Contact("aaa", "aaa"));
        } 
        catch (DuplicateContactException | IOException e) {
            System.out.println(e);
            System.out.println("Error occured while adding aaa/aaa. "
                    + "Program stops");
            return;
        }
        System.out.println();
       
        // inserting a new contact
        System.out.println("Adding new contact bbb/bbb ...");
        try {
            book.addContact(new Contact("bbb", "bbb"));
        } 
        catch (DuplicateContactException | IOException e) {
            System.out.println(e);
            System.out.println("Error occured while adding bbb/bbb. "
                    + "Program stops");
            return;
        }
        System.out.println();
       
        // inserting a new contact
        System.out.println("Adding new contact ccc/ccc ...");
        try {
            book.addContact(new Contact("ccc", "ccc"));
        } 
        catch (DuplicateContactException | IOException e) {
            System.out.println(e);
            System.out.println("Error occured while adding ccc/ccc. "
                    + "Program stops");
            return;
        }
        System.out.println();
       
        // inserting a new contact
        System.out.println("Adding new contact aaa/baa ...");
        try {
            book.addContact(new Contact("aaa", "baa"));
        } 
        catch (DuplicateContactException | IOException e) {
            System.out.println(e);
            System.out.println("Error occured while adding aaa/baa. "
                    + "Program stops");
            return;
        }
        System.out.println();

        // inserting a new contact
        System.out.println("Adding new contact bbb/bba ...");
        try {
            book.addContact(new Contact("bbb", "bba"));
        } 
        catch (DuplicateContactException | IOException e) {
            System.out.println(e);
            System.out.println("Error occured while adding bbb/bba. "
                    + "Program stops");
            return;
        }
        System.out.println();

        // inserting a new contact
        System.out.println("Adding new contact ccc/aaa ...");
        try {
            book.addContact(new Contact("ccc", "aaa"));
        } 
        catch (DuplicateContactException | IOException e) {
            System.out.println(e);
            System.out.println("Error occured while adding ccc/aaa. "
                    + "Program stops");
            return;
        }
        System.out.println();

        // inserting a new contact with null
        System.out.println("Adding new contact null/aaa ...");
        boolean nullDetected = false;
        try {
            Contact c = new Contact();
            c.firstName = null;
            book.addContact(c);
        } 
        catch (NullPointerException e) {
            nullDetected = true;
        }
        catch (DuplicateContactException | IOException e) {
            System.out.println(e);
            System.out.println("Error occured while adding null/aaa. "
                    + "Program stops");
            return;
        }
        if (nullDetected) {
            System.out.println("null in contact detected.");
        }
        else {
            System.out.println("Error occured while adding null/aaa.  null in "
                    + "contact not deteced.  Program stops.");
            return;
        }
        System.out.println();

        // inserting a new contact with empty
        System.out.println("Adding new contact aaa/\"\" ...");
        boolean emptyDetected = false;
        try {
            Contact c = new Contact("aaa", "aaa");
            c.lastName = "";
            book.addContact(c);
        } 
        catch (EmptyNameException e) {
            emptyDetected = true;
        }
        catch (DuplicateContactException | IOException e) {
            System.out.println(e);
            System.out.println("Error occured while adding null/aaa. "
                    + "Program stops");
            return;
        }
        if (emptyDetected) {
            System.out.println("empty in contact detected.");
        }
        else {
            System.out.println("Error occured while adding aaa/\"\".  empty in "
                    + "contact not deteced.  Program stops.");
            return;
        }
        System.out.println();

        // closing the current address book and open a new one to check I/O
        System.out.println("Re-open the address book ...");
        try {
            book = new AddressBook();
        } catch (IOException e) {
            System.out.println(e);
            System.out.println("Error occured while opening the address book. "
                    + "Program stops");
            return;
        }

        result = book.list();
        if (result.length == 6) {
            System.out.println("Currently, the address book has six contacts.");
        }
        else {
            System.out.println("Error reading contacts from the address book."
                    + "  It should have six contacts.");
            return;
        }
        System.out.println();
        
        // inserting a duplicate contact
        boolean duplicate = false;
        System.out.println("Adding new contact aaa/aaa ...");
        try {
            book.addContact(new Contact("aaa", "aaa"));
        } 
        catch (DuplicateContactException e) {
            duplicate = true;
        }
        catch (IOException e) {
            System.out.println(e);
            System.out.println("Error occured while adding ccc/ccc. "
                    + "Program stops");
            return;
        }
        if (duplicate)
            System.out.println("Duplicate contact detected.");
        else {
            System.out.println("Error occured while adding aaa/aaa. Duplicate "
                    + "contact aaa/aaa not detected.  Program stops.");
            return;
        }
        System.out.println();
       
        // searching for contacts by lastname
        System.out.println("Searching for lastname aaa ...");
        result = book.searchByLastName("aaa");
        if (result.length == 2) {
            System.out.println("Found 2 records with lastname aaa");
        }
        else {
            System.out.println("Error occured while searching for lastname aaa.  "
                    + "Program stops.");
            return;
        }
        System.out.println();
       
        // searching for contacts by lastname
        System.out.println("Searching for lastname bbb ...");
        result = book.searchByLastName("bbb");
        if (result.length == 1) {
            System.out.println("Found 1 record with lastname bbb");
        }
        else {
            System.out.println("Error occured while searching for lastname bbb.  "
                    + "Program stops.");
            return;
        }
        System.out.println();
       
        // searching for contacts by lastname
        System.out.println("Searching for lastname zzz ...");
        result = book.searchByLastName("zzz");
        if (result.length == 0) {
            System.out.println("Found 0 record with lastname zzz");
        }
        else {
            System.out.println("Error occured while searching for lastname zzz.  "
                    + "Program stops.");
            return;
        }
        System.out.println();
       
        // searching with null in lastname
        System.out.println("Searching for lastname null ...");
        nullDetected = false;
        try {
            result = book.searchByLastName(null);
        } 
        catch (NullPointerException e) {
            nullDetected = true;
        }
        if (nullDetected) {
            System.out.println("null in argument detected.");
        }
        else {
            System.out.println("Error occured while searching by lastName.  null in "
                    + "argument not deteced.  Program stops.");
            return;
        }
        System.out.println();
       
        // searching with empty in lastname
        System.out.println("Searching for lastname empty ...");
        emptyDetected = false;
        try {
            result = book.searchByLastName("");
        } 
        catch (EmptyNameException e) {
            emptyDetected = true;
        }
        if (emptyDetected) {
            System.out.println("empty \"\" in argument detected.");
        }
        else {
            System.out.println("Error occured while searching by lastName.  empty "
                    + "\"\" in argument not deteced.  Program stops.");
            return;
        }
        System.out.println();
       
        // search lastNamePartial
        Method m = null;
        boolean partialImplemented = true;
        try {
            m = AddressBook.class.getMethod(
                    "searchByLastNamePartial", String.class);
        }
        catch (NoSuchMethodException e) {
            partialImplemented = false;
        }
        if (!partialImplemented) {
            System.out.println("searchByLastNamePartial not implemented in "
                    + "AddressBook.");
        }
        else {
            // search lastNamePartial "a"
            System.out.println("Searching for partial lastname a ...");
            try {
                result = (Contact[]) m.invoke(book, "a");
            } 
            catch (IllegalAccessException | IllegalArgumentException e) {
                System.out.println(e);
                System.out.println("Invocation Error: searchByLastNamePartial");
            }
            catch (InvocationTargetException e) {
                Throwable th = e.getCause();
                if (th instanceof NullPointerException)
                    throw (NullPointerException) th;
                else if (th instanceof EmptyNameException)
                    throw (EmptyNameException) th;
                else {
                    System.out.println(e);
                    System.out.println("Invocation Error: searchByLastNamePartial");
                }
            }
            //contacts = book.searchByLastNamePartial("a");
            if (result.length == 4) {
                System.out.println("Found 4 records with partial lastname a");
            }
            else {
                System.out.println("Error occured while searching for partial "
                        + "lastname a.  Program stops.");
                return;
            }
            System.out.println();

            // search lastNamePartial "z"
            System.out.println("Searching for partial lastname z ...");
            try {
                result = (Contact[]) m.invoke(book, "z");
            } 
            catch (IllegalAccessException | IllegalArgumentException e) {
                System.out.println(e);
                System.out.println("Invocation Error: searchByLastNamePartial");
            }
            catch (InvocationTargetException e) {
                Throwable th = e.getCause();
                if (th instanceof NullPointerException)
                    throw (NullPointerException) th;
                else if (th instanceof EmptyNameException)
                    throw (EmptyNameException) th;
                else {
                    System.out.println(e);
                    System.out.println("Invocation Error: searchByLastNamePartial");
                }
            }
            //contacts = book.searchByLastNamePartial("z");
            if (result.length == 0) {
                System.out.println("Found 0 records with partial lastname z");
            }
            else {
                System.out.println("Error occured while searching for partial "
                        + "lastname z.  Program stops.");
                return;
            }
            System.out.println();

            // search lastNamePartial null
            nullDetected = false;
            System.out.println("Searching for partial lastname null ...");
            try {
                try {
                    result = (Contact[]) m.invoke(book, (String) null);
                } 
                catch (IllegalAccessException | IllegalArgumentException e) {
                    System.out.println(e);
                    System.out.println("Invocation Error: searchByLastNamePartial");
                }
                catch (InvocationTargetException e) {
                    Throwable th = e.getCause();
                    if (th instanceof NullPointerException)
                        throw (NullPointerException) th;
                    else if (th instanceof EmptyNameException)
                        throw (EmptyNameException) th;
                    else {
                        System.out.println(e);
                        System.out.println("Invocation Error: searchByLastNamePartial");
                    }
                }
                //result = book.searchByLastNamePartial(null);
            } 
            catch (NullPointerException e) {
                nullDetected = true;
            }
            if (nullDetected) {
                System.out.println("null in argument detected.");
            }
            else {
                System.out.println("Error occured while searching by partial "
                        + "lastName.  null in argument not deteced.  Program stops.");
                return;
            }
            System.out.println();
            
            // search lastNamePartial empty
            emptyDetected = false;
            System.out.println("Searching for partial lastname empty \"\" ...");
            try {
                try {
                    result = (Contact[]) m.invoke(book, "");
                } 
                catch (IllegalAccessException | IllegalArgumentException e) {
                    System.out.println(e);
                    System.out.println("Invocation Error: searchByLastNamePartial");
                }
                catch (InvocationTargetException e) {
                    Throwable th = e.getCause();
                    if (th instanceof NullPointerException)
                        throw (NullPointerException) th;
                    else if (th instanceof EmptyNameException)
                        throw (EmptyNameException) th;
                    else {
                        System.out.println(e);
                        System.out.println("Invocation Error: searchByLastNamePartial");
                    }
                }
                //result = book.searchByLastNamePartial(null);
            } 
            catch (EmptyNameException e) {
                emptyDetected = true;
            }
            if (emptyDetected) {
                System.out.println("empty \"\" in argument detected.");
            }
            else {
                System.out.println("Error occured while searching by partial "
                        + "lastName.  empty \"\" in argument not deteced.  "
                        + "Program stops.");
                return;
            }
            System.out.println();
        }
        
        // delete ccc
        System.out.println("Deleting contact ccc/ccc ...");
        try {
            book.deleteContact("ccc", "ccc");
        } 
        catch (ContactNotFoundException | IOException e) {
            System.out.println(e);
            System.out.println("Error occured while deleting ccc/ccc. "
                    + "Program stops");
            return;
        }
        System.out.println();
       
        // delete null
        System.out.println("Deleting contact with aaa/null ...");
        nullDetected = false;
        try {
            book.deleteContact("aaa", null);
        } 
        catch (NullPointerException e) {
            nullDetected = true;
        }
        catch (ContactNotFoundException | IOException e) {
            System.out.println(e);
            System.out.println("Error occured while deleting aaa/null. "
                    + "Program stops");
            return;
        }
        if (nullDetected) {
            System.out.println("null in argument detected.");
        }
        else {
            System.out.println("Error occured while deleting aaa/null.  null in "
                    + "argument not deteced.  Program stops.");
            return;
        }
        System.out.println();

        // delete empty
        System.out.println("Deleting contact \"\"/aaa ...");
        emptyDetected = false;
        try {
            book.deleteContact("", "aaa");
        } 
        catch (EmptyNameException e) {
            emptyDetected = true;
        }
        catch (ContactNotFoundException | IOException e) {
            System.out.println(e);
            System.out.println("Error occured while deleting \"\"/aaa. "
                    + "Program stops");
            return;
        }
        if (emptyDetected) {
            System.out.println("empty in argument detected.");
        }
        else {
            System.out.println("Error occured while deleting \"\"/aaa.  empty in "
                    + "argument not deteced.  Program stops.");
            return;
        }
        System.out.println();

        // delete zzz
        System.out.println("Deleting contact zzz/zzz ...");
        boolean notFound = false;
        try {
            book.deleteContact("zzz", "zzz");
        } 
        catch (ContactNotFoundException e) {
            notFound = true;            
        }
        catch (IOException e) {
            System.out.println(e);
            System.out.println("Error occured while adding zzz/zzz. "
                    + "Program stops");
            return;
        }
        if (notFound) {
            System.out.println("Contact zzz/zzz not found.");
        }
        else {
            System.out.println("Error occured while deleting zzz/zzz. ContactNotFound"
                    + "not reported.  Program stops.");
            return;
        }
        System.out.println();

        // reopen
        // closing the current address book and open a new one to check I/O
        System.out.println("Re-open the address book ...");
        try {
            book = new AddressBook();
        } catch (IOException e) {
            System.out.println(e);
            System.out.println("Error occured while opening the address book. "
                    + "Program stops.");
            return;
        }
        
        // list
        result = book.list();
        if (result.length == 5) {
            System.out.println("Currently, the address book has five contacts.");
        }
        else {
            System.out.println("Error reading contacts from the address book."
                    + "  It should have five contacts.  Program stops.");
            return;
        }

        if (searchArray(result, "aaa", "aaa")) {
            System.out.println("Contact aaa/aaa is in.");
        }
        else {
            System.out.println("Error: Contact aaa/aaa is not in.  Program stops.");
            return;
        }

        if (searchArray(result, "bbb", "bbb")) {
            System.out.println("Contact bbb/bbb is in.");
        }
        else {
            System.out.println("Error: Contact bbb/bbb is not in.  Program stops.");
            return;
        }

        if (searchArray(result, "aaa", "baa")) {
            System.out.println("Contact aaa/baa is in.");
        }
        else {
            System.out.println("Error: Contact aaa/baa is not in.  Program stops.");
            return;
        }

        if (searchArray(result, "bbb", "bba")) {
            System.out.println("Contact bbb/bba is in.");
        }
        else {
            System.out.println("Error: Contact bbb/bba is not in.  Program stops.");
            return;
        }

        if (searchArray(result, "ccc", "aaa")) {
            System.out.println("Contact ccc/aaa is in.");
        }
        else {
            System.out.println("Error: Contact ccc/aaa is not in.  Program stops.");
            return;
        }

        if (!searchArray(result, "ccc", "ccc")) {
            System.out.println("Contact ccc/ccc is not in.");
        }
        else {
            System.out.println("Error: Contact ccc/ccc is in.  Program stops.");
            return;
        }
        System.out.println();
        
        // rep exposured: list
        System.out.println("Checking rep exposure: list ...");
        result[0].lastName = "zzz";
        // searching for contacts by lastname zzz
        System.out.println("Searching for lastname zzz ...");
        result = book.searchByLastName("zzz");
        if (result.length == 0) {
            System.out.println("Found 0 record with lastname zzz.  Rep not exposed"
                    + " through list.");
        }
        else {
            System.out.println("Error occured while searching for lastname zzz.  Rep "
                    + "exposed through list!  Program stops.");
            return;
        }
        System.out.println();

        // rep exposured: addContact
        System.out.println("Checking rep exposure: addContact ...");
        var c = new Contact("eee", "eee");
        try {
            book.addContact(c);
        }
        catch (Exception e) {
            // should not happen
            System.out.println(e);
            return;
        }
        c.lastName = "zzz";
        // searching for contacts by lastname zzz
        System.out.println("Searching for lastname zzz ...");
        result = book.searchByLastName("zzz");
        if (result.length == 0) {
            System.out.println("Found 0 record with lastname zzz.  "
                    + "Rep not exposed through addContact.");
        }
        else {
            System.out.println("Error occured while searching for lastname zzz.  Rep "
                    + "exposed through addContact!  Program stops.");
            return;
        }
        System.out.println();

        // rep exposured: searchByLastName
        System.out.println("Checking rep exposure: searchByLastName ...");
        result = book.searchByLastName("aaa");
        result[0].lastName = "zzz";
        // searching for contacts by lastname zzz
        System.out.println("Searching for lastname zzz ...");
        result = book.searchByLastName("zzz");
        if (result.length == 0) {
            System.out.println("Found 0 record with lastname zzz.  Rep not exposed"
                    + " through searchByLastName.");
        }
        else {
            System.out.println("Error occured while searching for lastname zzz.  Rep "
                    + "exposed through searchByLastName!  Program stops.");
            return;
        }
        System.out.println();

        // rep exposured: searchByLastNamePartial
        if (partialImplemented) {
            System.out.println("Checking rep exposure: searchByLastNamePartial ...");
            // search lastNamePartial "a"
            try {
                result = (Contact[]) m.invoke(book, "a");
            } 
            catch (IllegalAccessException | IllegalArgumentException e) {
                System.out.println(e);
                System.out.println("Invocation Error: searchByLastNamePartial");
                return;
            }
            catch (InvocationTargetException e) {
                Throwable th = e.getCause();
                if (th instanceof NullPointerException)
                    throw (NullPointerException) th;
                else if (th instanceof EmptyNameException)
                    throw (EmptyNameException) th;
                else {
                    System.out.println(e);
                    System.out.println("Invocation Error: searchByLastNamePartial");
                    return;
                }
            }
            result[0].lastName = "zzz";
            // searching for contacts by lastname zzz
            System.out.println("Searching for lastname zzz ...");
            result = book.searchByLastName("zzz");
            if (result.length == 0) {
                System.out.println("Found 0 record with lastname zzz.  Rep not exposed"
                        + " through searchByLastNamePartial.");
            }
            else {
                System.out.println("Error occured while searching for lastname zzz.  Rep "
                        + "exposed through searchByLastNamePartial!  Program stops.");
                return;
            }
            System.out.println();
        }
        
        System.out.println("*** Project Part 1 tests done successfully! ***");
        System.out.println();

        // count
        int cnt = book.count();
        if (cnt == 6) {
            System.out.println("Currently, the address book has six contacts.");
        }
        else {
            System.out.println("Error count() returns " + cnt 
                    + ".  It should have six contacts.  Program stops.");
            return;
        }
        System.out.println();

        // contactsSortedByName 
        System.out.println("Iterating over all the contacts in ascending "
                + "order ...");
        Contact[] contactArray = book.list();
        var g = book.contactsSortedByName();
        Contact prevContact;
        int counter = 0;
        if (g.hasNext()) {
            prevContact = g.next();
            System.out.println(prevContact.firstName);      //edit out
            System.out.println(prevContact.lastName);
            if (searchArray(contactArray, prevContact.firstName, 
                    prevContact.lastName)) {
                System.out.println(prevContact + " is in the addressbook.");
                counter++;
            }
            else {
                System.out.println("contactsSortedByName does not work "
                            + "properly! Contact " + counter + " returned "
                            + "does not exist. Program stops.");
                return;
            }

            while (g.hasNext()) {
                Contact curContact = g.next();
                System.out.println(curContact.firstName);       //edit out
                System.out.println(curContact.lastName);
                counter++;
                if (searchArray(contactArray, curContact.firstName, 
                        curContact.lastName)) {
                    System.out.println(curContact + " is in the addressbook.");
                }
                else {
                    System.out.println("contactsSortedByName does not work "
                            + "properly! Contact " + counter + " returned "
                            + "does not exist. Program stops.");
                    return;
                }
                if (curContact.lastName.compareTo(prevContact.lastName) > 0
                    || curContact.lastName.compareTo(prevContact.lastName) == 0
                    && curContact.firstName.compareTo(prevContact.firstName) > 0) {
//                    if (prevContact < curContact) {
                    prevContact = curContact;
                } else {
                    System.out.println("contactsSortedByName does not work "
                            + "properly! " + prevContact + " appears before"
                            + curContact + ". Program stops.");
                    return;
                }
            }

            if (counter != book.count()) {
                System.out.println("contactsSortedByName does not work "
                        + "properly! It returned " + counter + " contacts"
                        + " while the addressbook has " +  book.count() 
                        + " contactselements. Program stops.");
                return;
            }
        } else {
            System.out.println("contactsSortedByName does not work properly! "
                    + "Addressbook is not empty.  Execution stopped!");
            return;
        }
        System.out.println();

        // rep exposured: contactsSortedByName
        System.out.println("Checking rep exposure: contactsSortedByName ...");
        var g1 = book.contactsSortedByName();
        var g2 = book.contactsSortedByName();
        
        while (g1.hasNext()) {
            Contact c1 = g1.next();
            c1.email = "z@z";
            
            Contact c2 = g2.next();
            if (c2.email.equals("z@z")) {
                System.out.println("Error occured while checking " + c2 
                    + ".  Rep exposed through contactsSortedByName!  Program "
                    + "stops.");
                return;
            }
        }
        System.out.println("Checked all contacts.  Rep not exposed through "
                + "contactsSortedByName.");
        System.out.println();
        
        //toString
        String bookString = book.toString();
        g = book.contactsSortedByName();
        
        while (g.hasNext()) {
            c = g.next();

            if (!bookString.contains(c.firstName) 
                    || !bookString.contains(c.lastName)
                    || !bookString.contains(c.email)
                    || !bookString.contains(c.phone)) {
                System.out.println("Error occured while checking " + c 
                    + ".  Its information not found in the string provided by "
                    + "toString. Program stops.");
                return;
            }
        }
        System.out.println("Checked all contacts.  Info from toString is "
                + "complete.");
        System.out.println();

        if (book.repOk()) {
            System.out.println("Rep invariant is satisfied.");
        }
        else {
            System.out.println("Rep invariant is NOT satisfied! Program stops.");
            return;
        }
        System.out.println();
        
        System.out.println("*** Project Part 2 tests done successfully! ***");        
        System.out.println();
    }
    
    public static boolean searchArray(Contact[] arr, String fn, String ln) {
        for (Contact c : arr) {
            if (c.firstName.equals(fn) && c.lastName.equals(ln) )
                return true;
        }
        return false;
    }
}
