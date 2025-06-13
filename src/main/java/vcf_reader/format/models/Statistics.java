package vcf_reader.format.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import vcf_reader.format.entities.AttributedContactField;
import vcf_reader.format.entities.Contact;

public class Statistics {

	private final List<List<String>> invalidContactVCardLines = new ArrayList<>();
	private final Set<AttributedContactField> unrecognisedFields = new HashSet<>();
	private final List<Contact> contactsWithoutAPhone = new ArrayList<>();
	
	public List<List<String>> getInvalidContactVCardLines() {
		return this.invalidContactVCardLines;
	}
	
	public void addInvalidContactVCardLines(List<String> vCardLines) {
		this.invalidContactVCardLines.add(vCardLines);
	}
	
	public Set<AttributedContactField> getUnrecognisedFields() {
		return unrecognisedFields;
	}
	
	public void addUnrecognisedFields(Collection<AttributedContactField> fields) {
		this.unrecognisedFields.addAll(fields);
	}

	public int getNumberOfContactsWithoutPhone() {
		return contactsWithoutAPhone.size();
	}

	public void addContactWithoutPhone(Contact contact) {
		this.contactsWithoutAPhone.add(contact);
	}
	
	public void clear() {
		this.unrecognisedFields.clear();
		this.contactsWithoutAPhone.clear();
	}
}
