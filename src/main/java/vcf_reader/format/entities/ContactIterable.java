package vcf_reader.format.entities;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import vcf_reader.format.models.Statistics;

/**
 * Class that represents an iterable of contacts.
 */
public class ContactIterable implements Iterable<Contact> {

	/**
	 * The valid contacts.
	 */
	private final List<Contact> validContacts = new ArrayList<>();
	
	/**
	 * The statistics.
	 */
	private final Statistics statistics = new Statistics();
	
	/**
	 * Adds a valid contact to this iterable.
	 * @param contact A valid contact.
	 */
	public void addValidContact(Contact contact) {
		this.validContacts.add(contact);
	}

	/**
	 * Returns the number of valid contacts hosted in this iterable.
	 * @return The number of valid contacts.
	 */
	public int getValidContactCount() {
		return validContacts.size();
	}
	
	/**
	 * Returns the statistics of this contact iterable.
	 * @return The statistics.
	 */
	public Statistics getStatistics() {
		return statistics;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Iterator<Contact> iterator() {
		return this.validContacts.iterator();
	}
}
