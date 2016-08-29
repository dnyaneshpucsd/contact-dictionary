package com.pucsd.cd.service;

import java.util.List;

import com.pucsd.cd.exception.InvalidContactException;
import com.pucsd.cd.model.Contact;
/**
 * Provide API to contact dictionary.
 * <p>
 * Public API:
 * <ul>
 *  <li> add
 *  <li> search
 * </ul>
 * </p>
 * @author dnyanesh
 *
 */
public interface ContactDictionaryService {
	/**
	 * API to add contact in contact dictionary.
	 * @param contact which has to add in contact dictionary.
	 * @throws InvalidContactException if contact is invalid 
	 *         ex. (not supported character is found in contact)
	 */
	public void add(Contact contact) throws InvalidContactException;

	/**
	 * API to search contacts in contact dictionary by  first name / full contact / last name.
	 * @param searchText which is used to search contacts in contact dictionary.  
	 * @return list of contacts found for input search text.
	 */
	public List<Contact> search(String searchText);

}
