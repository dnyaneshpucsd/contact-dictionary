package com.pucsd.cd.bean;

import java.util.List;
import java.util.Scanner;

import com.pucsd.cd.exception.InvalidContactException;
import com.pucsd.cd.model.Contact;
import com.pucsd.cd.service.ContactDictionaryService;
import com.pucsd.cd.service.impl.ContactDictionaryServiceImpl;

/**
 * Main class to run dictionary application
 * 
 * Assumption / constrain :
 * The application will be run in single thread. it doesn't support concurrency
 * add allows multiple entries for same contact in contact dictionary.
 * allowed characters in contacts are 'a-z', 'A-Z', '0-9', ' ' and '.'
 * contact can't be more than 50 characters.
 * prefix and suffix spaces of contact are ignored while adding into dictionary
 * all contacts are store in dictionary in lower case format
 * provided factory methods to create contact object for user input. it validates user input before creating object
 * search supports search by first name / full name / last name.
 * 
 * @author dnyanesh
 *
 */


public class ContactDictionaryMain {

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		String userInputOption = "1) Add contact 2) Search 3) Exit";
		Scanner sc = new Scanner(System.in).useDelimiter("\n");

		ContactDictionaryService contactDictionary = new ContactDictionaryServiceImpl();
		boolean exitFlag = true;
		do {
			System.out.println(userInputOption);
			String strInput = sc.next();
			int intInput = 0;
			try {
				intInput = Integer.parseInt(strInput);
			} catch (NumberFormatException e) {
			}

			switch (intInput) {
			case 1:
				System.out.print("Enter name: ");
				String strContact = sc.next();
				try {
					Contact contact = Contact.newInstance(strContact);
					contactDictionary.add(contact);
				} catch (InvalidContactException e) {
					System.out.println(e.getMessage());
				}
				break;

			case 2:
				System.out.print("Enter name: ");
				String searchText = sc.next();
				List<Contact> searchResult = contactDictionary
						.search(searchText);
				if (searchResult.isEmpty()) {
					System.out.println(searchResult);
				}
				for (Contact contact : searchResult) {
					System.out.println(contact);
				}
				break;
			case 3:
				System.out.println("Happy Searching");
				exitFlag = false;
				break;

			default:
				System.out.println("Invalid input");
				break;
			}

		} while (exitFlag);

	}

}
