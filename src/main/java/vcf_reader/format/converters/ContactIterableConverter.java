package vcf_reader.format.converters;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import vcf_reader.format.entities.Contact;
import vcf_reader.format.entities.ContactIterable;
import vcf_reader.format.exceptions.InvalidFieldException;
import vcf_reader.format.models.Statistics;
import vcf_reader.input_output.LineProcessor;

public class ContactIterableConverter extends LineProcessor {

	private ContactIterable contactIterable;
	
	public static ContactIterable vcfToModel(File vcfFile) throws FileNotFoundException {
		ContactIterableConverter converter = new ContactIterableConverter();
		converter.contactIterable = new ContactIterable();
		converter.process(vcfFile);
		return converter.contactIterable;
	}

	@Override
	public void read(BufferedReader bufferedReader) throws IOException {
		String line;
		List<String> vCardLines = null;
		Statistics statistics = this.contactIterable.getStatistics();
		while ((line = bufferedReader.readLine()) != null) {
			if (ContactConverter.BEGIN.equals(line)) {
				vCardLines = new ArrayList<>();
			} else if (vCardLines != null) {
				if (ContactConverter.END.equals(line)) {
					Contact contact;
					try {
						contact = ContactConverter.vcfToModel(vCardLines);
						this.contactIterable.addValidContact(contact);
						statistics.addUnrecognisedFields(contact.getUnrecognisedFields());
						if (contact.getAllTelephones().isEmpty()) {
							statistics.addContactWithoutPhone(contact);
						}
					} catch (InvalidFieldException e) {
						statistics.addInvalidContactVCardLines(vCardLines);
					}
					vCardLines = null;
				} else {
					vCardLines.add(line);
				}
			}
		}
	}
	
	
	/**
	 * Recalculates the statistics of the given contact iterable.
	 * @param contactIterable A contact iterable.
	 */
	public static void recalculateStats(ContactIterable contactIterable) {
		Statistics statistics = contactIterable.getStatistics();
		statistics.clear();
		for (Contact contact : contactIterable) {
			statistics.addUnrecognisedFields(contact.getUnrecognisedFields());
			if (contact.getAllTelephones().isEmpty()) {
				statistics.addContactWithoutPhone(contact);
			}
		}
	}
}
