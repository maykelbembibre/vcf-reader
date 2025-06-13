package vcf_reader.improvements;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import vcf_reader.format.converters.ContactIterableConverter;
import vcf_reader.format.entities.Contact;
import vcf_reader.format.entities.ContactIterable;

public class ImprovementApplier {

	private final Set<Improvement> improvementsToApply = new HashSet<>();
	
	public void addImprovement(Improvement improvement) {
		this.improvementsToApply.add(improvement);
	}
	
	/**
	 * Applies the improvement on the given contacts and returns the contacts that
	 * have changed as a result.
	 * @param contacts The contacts.
	 * @return The contacts that have changed; or an empty iterable if none has.
	 */
	public Collection<Contact> apply(ContactIterable contacts) {
		Collection<Contact> changedContacts = new ArrayList<>();
		for (Contact contact : contacts) {
			for (Improvement improvement : this.improvementsToApply) {
				if (improvement.apply(contact)) {
					changedContacts.add(contact);
				}
			}
		}
		ContactIterableConverter.recalculateStats(contacts);
		return changedContacts;
	}
}
