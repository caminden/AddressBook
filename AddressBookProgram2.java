/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Chase Minden
 */

import java.util.Scanner;
import java.util.Iterator;

import java.io.IOException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/** OVERVIEW: A program that provides user with an interface 
      to use an AddressBook
*/ 
public class AddressBookProgram2 {
    public static void main(String[] args) {
        AddressBook book = null;

        // try to open the address book
        try {
            book = new AddressBook();
        } catch (IOException e) {
            System.out.println(e);
            System.out.println("Unable to read from the contact file. Program stops");
            return;
        }

        String firstName, lastName, email, phone;

        int instruction = -1; 
        var in = new Scanner(System.in);
        Contact[] result = null;
        int cnt;

        Method m = null;

        do {
            System.out.println();
            System.out.println("******************************************************");
            System.out.println("Please select one of the following instructions (0-4):");
            System.out.println("  0. Exit");
            System.out.println("  1. Add a new contact");
            System.out.println("  2. Delete a contact");
            System.out.println("  3. Search for contacts using a last name");
            System.out.println("  33. Search for contacts using a partial last name");
            System.out.println("  4. List all contacts");
            System.out.println("  5. Number of contacts");
            System.out.println("  6. List all contacts (sorted by name)");
            System.out.println("  7. Addressbook as a string (toString)");
            System.out.println("  8. Check the rep (repOk)");
            System.out.println("******************************************************");
            System.out.println();

            String line = in.nextLine();
            try {
                instruction = Integer.parseInt(line);      
            } catch (NumberFormatException e) {
                System.out.println("Invalid instruction!");
                continue;
            }

            switch (instruction) {
            case 0:
                break;
            case 1:
                try {
                    m = AddressBook.class.getMethod(
                            "addContact", Contact.class);
                }
                catch (NoSuchMethodException e) {
                    System.out.println("addContact not implemented in "
                            + "AddressBook!");
                    continue;
                }
                
                System.out.println();
                System.out.println("Please enter the first name of the contact: ");
                firstName = in.nextLine();
                if (firstName.isBlank()) {
                    System.out.println("First name cannot be empty!");
                    continue;
                }
                System.out.println();
                System.out.println("Please enter the last name of the contact: ");
                lastName = in.nextLine();
                if (lastName.isBlank()) {
                    System.out.println("Last name cannot be empty!");
                    continue;
                }
                System.out.println();
                System.out.println("Please enter the email address of the contact: ");
                email = in.nextLine();
                System.out.println();
                System.out.println("Please enter the phone number of the contact: ");
                phone = in.nextLine();

                var c = new Contact();
                c.firstName = firstName;
                c.lastName = lastName;
                c.email = email;
                c.phone = phone;

                System.out.println();
                try {
                    try {
                        m.invoke(book, c);
                    } catch (IllegalAccessException | IllegalArgumentException e) {
                        System.out.println(e);
                        System.out.println("Invocation Error: addContact");
                    }
                    catch (InvocationTargetException e) {
                        Throwable th = e.getCause();
                        if (th instanceof NullPointerException)
                            throw (NullPointerException) th;
                        else if (th instanceof EmptyNameException)
                            throw (EmptyNameException) th;
                        else if (th instanceof DuplicateContactException)
                            throw (DuplicateContactException) th;
                        else if (th instanceof IOException)
                            throw (IOException) th;
                        else {
                            System.out.println(e);
                            System.out.println("Invocation Error: addContact");
                        }
                    }
                    //book.addContact(c);
                } catch (IOException e) {
                    System.out.println(e);
                    System.out.println("Unable to write to disk!  New contact is not added.");
                    continue;
                } catch (DuplicateContactException e) {
                    System.out.println("Contact exists!  New contact is not added.");
                    continue;
                }

                System.out.println("New contact is added successfully.");

                break;
            case 2:
                try {
                    m = AddressBook.class.getMethod(
                            "deleteContact", String.class, String.class);
                }
                catch (NoSuchMethodException e) {
                    System.out.println("deleteContact not implemented in "
                            + "AddressBook!");
                    continue;
                }
                
                System.out.println();
                System.out.println("Please enter the first name of the contact to delete: ");
                firstName = in.nextLine();
                if (firstName.isBlank()) {
                    System.out.println("First name cannot be empty!");
                    continue;
                }
                System.out.println();
                System.out.println("Please enter the last name of the contact to delete: ");
                lastName = in.nextLine();
                if (lastName.isBlank()) {
                    System.out.println("Last name cannot be empty!");
                    continue;
                }

                System.out.println();
                try {
                    try {
                        m.invoke(book, firstName, lastName);
                    } catch (IllegalAccessException | IllegalArgumentException e) {
                        System.out.println(e);
                        System.out.println("Invocation Error: deleteContact");
                    }
                    catch (InvocationTargetException e) {
                        Throwable th = e.getCause();
                        if (th instanceof NullPointerException)
                            throw (NullPointerException) th;
                        else if (th instanceof EmptyNameException)
                            throw (EmptyNameException) th;
                        else if (th instanceof ContactNotFoundException)
                            throw (ContactNotFoundException) th;
                        else if (th instanceof IOException)
                            throw (IOException) th;
                        else {
                            System.out.println(e);
                            System.out.println("Invocation Error: deleteContact");
                        }
                    }
                    //book.deleteContact(firstName, lastName);
                } catch (IOException e) {
                    System.out.println(e);
                    System.out.println("Unable to write to disk!  Contact is not deleted");
                    continue;
                } catch (ContactNotFoundException e) {
                    System.out.println("Contact does not exist!  Contact is not deleted");
                    continue;
                }

                System.out.println("Contact is deleted successfully");

                break;
            case 3:
                try {
                    m = AddressBook.class.getMethod(
                            "searchByLastName", String.class);
                }
                catch (NoSuchMethodException e) {
                    System.out.println("searchByLastName not implemented in "
                            + "AddressBook!");
                    continue;
                }

                System.out.println();
                System.out.println("Please enter the last name to search: ");
                lastName = in.nextLine();
                if (lastName.isBlank()) {
                    System.out.println("Last name cannot be empty!");
                    continue;
                }

                System.out.println();
                try {
                    result = (Contact[]) m.invoke(book, lastName);
                } catch (IllegalAccessException | IllegalArgumentException e) {
                    System.out.println(e);
                    System.out.println("Invocation Error: searchByLastName");
                }
                catch (InvocationTargetException e) {
                    Throwable th = e.getCause();
                    if (th instanceof NullPointerException)
                        throw (NullPointerException) th;
                    else if (th instanceof EmptyNameException)
                        throw (EmptyNameException) th;
                    else {
                        System.out.println(e);
                        System.out.println("Invocation Error: searchByLastName");
                    }
                }
                //result = book.searchByLastName(lastName);
                if (result.length == 0)
                    System.out.println("There is no contact with the given last name.");        
                else {
                    for (var curContact : result) {
                        System.out.println(curContact.firstName + " " + curContact.lastName);
                        System.out.println("E-mail: " + curContact.email);
                        System.out.println("Phone: " + curContact.phone);        
                        System.out.println(); 
                    }
                }
                
                break;
            case 33:
                try {
                    m = AddressBook.class.getMethod(
                            "searchByLastNamePartial", String.class);
                }
                catch (NoSuchMethodException e) {
                    System.out.println("searchByLastNamePartial not implemented"
                            + " in AddressBook!");
                    continue;
                }
                
                System.out.println();
                System.out.println("Please enter the partial lastname to search: ");
                lastName = in.nextLine();
                if (lastName.isBlank()) {
                    System.out.println("Lastname cannot be empty!");
                    continue;
                }
                System.out.println();
                try {
                    result = (Contact[]) m.invoke(book, lastName);
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
                //result = book.searchByLastNamePartial(lastName);

                if (result.length == 0)
                    System.out.println("There is no contact with the given partial "
                            + "last name.");        
                else {
                    for (var curContact : result) {
                        System.out.println(curContact.firstName + " " + curContact.lastName);
                        System.out.println("E-mail: " + curContact.email);
                        System.out.println("Phone: " + curContact.phone);        
                        System.out.println(); 
                    }
                }
                
                break;
            case 4:
                try {
                    m = AddressBook.class.getMethod("list");
                }
                catch (NoSuchMethodException e) {
                    System.out.println("list not implemented in AddressBook!");
                    continue;
                }
                
                System.out.println();
                try {
                    result = (Contact[]) m.invoke(book);
                } catch (IllegalAccessException | IllegalArgumentException | 
                        InvocationTargetException e) {
                    System.out.println(e);
                    System.out.println("Invocation Error: list");
                }
                //result = book.list();
                if (result.length == 0)
                    System.out.println("There is no contact in the address book.");        
                else
                    for (var curContact : result) {
                        System.out.println(curContact.firstName + " " + 
                                curContact.lastName);
                        System.out.println("E-mail: " + curContact.email);
                        System.out.println("Phone: " + curContact.phone);        
                        System.out.println(); 
                    }

                break;
            case 5:
                try {
                    m = AddressBook.class.getMethod("count");
                }
                catch (NoSuchMethodException e) {
                    System.out.println("count not implemented in AddressBook!");
                    continue;
                }

                System.out.println();
                try {
                    cnt = (int) m.invoke(book);

                    if (cnt == 0) 
                        System.out.println("There is no contact in the address book.");        
                    else if (cnt == 1) 
                        System.out.println("There is one contact in the address book.");        
                    else 
                        System.out.println("There are " + cnt + " contacts in the address book.");        
                } catch (IllegalAccessException | IllegalArgumentException | 
                        InvocationTargetException e) {
                    System.out.println(e);
                    System.out.println("Invocation Error: count");
                }
//                cnt = book.count();
//                if (cnt == 0) 
//                    System.out.println("There is no contact in the address book.");        
//                else if (cnt == 1) 
//                    System.out.println("There is one contact in the address book.");        
//                else 
//                    System.out.println("There are " + cnt + " contacts in the address book.");        

                break;
            case 6:
                // check if count has been implemented
                try {
                    m = AddressBook.class.getMethod("count");
                }
                catch (NoSuchMethodException e) {
                    System.out.println("count not implemented in AddressBook!");
                    continue;
                }
                
                System.out.println();
                try {
                    cnt = (int) m.invoke(book);

                    if (cnt == 0) 
                      System.out.println("There is no contact in the address book.");        
                    else {
                        // check if contactsSortedByName has been implemented
                        try {
                            m = AddressBook.class.getMethod("contactsSortedByName");
                        }
                        catch (NoSuchMethodException e) {
                            System.out.println("contactsSortedByName not "
                                    + "implemented in AddressBook!");
                            continue;
                        }
                
                        try {
                            @SuppressWarnings("unchecked")
                            var g = (Iterator<Contact>) m.invoke(book);
                            while (g.hasNext()) {
                              c = g.next();
                              System.out.println(c.firstName + " " 
                                      + c.lastName);
                              System.out.println("E-mail: " + c.email);
                              System.out.println("Phone: " + c.phone);        
                              System.out.println(); 
                            }
                        } catch (IllegalAccessException | 
                                IllegalArgumentException |
                                InvocationTargetException e) {
                            System.out.println(e);
                            System.out.println("Invocation Error: "
                                    + "contactsSortedByName");
                        }
                    }
                } catch (IllegalAccessException | IllegalArgumentException | 
                        InvocationTargetException e) {
                    System.out.println(e);
                    System.out.println("Invocation Error: count");
                }

//                cnt = book.count();
//                if (cnt == 0) 
//                  System.out.println("There is no contact in the address book.");        
//                else {
//                  Iterator<Contact> g = book.contactsSortedByName();
//                  while (g.hasNext()) {
//                    c = g.next();
//                    System.out.println(c.firstName + " " + c.lastName);
//                    System.out.println("E-mail: " + c.email);
//                    System.out.println("Phone: " + c.phone);        
//                    System.out.println(); 
//                  }
//                }
                
                break;
            case 7:
                System.out.println(book);
                
                break;
            case 8:
                try {
                    m = AddressBook.class.getMethod("repOk");
                }
                catch (NoSuchMethodException e) {
                    System.out.println("repOk not implemented in AddressBook!");
                    continue;
                }

                System.out.println();
                try {
                    var ok = (boolean) m.invoke(book);

                    if (ok) 
                        System.out.println("Rep invariant is satisfied.");        
                    else {
                        System.out.println("Rep invariant is NOT satisfied!");
                    }
                } catch (IllegalAccessException | IllegalArgumentException | 
                        InvocationTargetException e) {
                    System.out.println(e);
                    System.out.println("Invocation Error: repOk");
                }

                break;
            default:
                System.out.println("Invalid instruction!");
                continue;
            }
        } while (instruction != 0);
    }
}
