
import java.util.Scanner;

import java.io.IOException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/** OVERVIEW: A program that provides user with an interface 
      to use an AddressBook
*/ 
public class AddressBookProgram {
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
                    System.out.println("addContact not implement in "
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
                    System.out.println("deleteContact not implement in "
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
                    System.out.println("searchByLastName not implement in "
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
                    System.out.println("searchByLastNamePartial not implement in "
                            + "AddressBook!");
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
                    System.out.println("list not implement in AddressBook!");
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
            default:
                System.out.println("Invalid instruction!");
            }
        } while (instruction != 0);
    }
}