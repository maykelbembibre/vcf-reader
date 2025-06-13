package vcf_reader.improvements;

import java.util.Arrays;
import java.util.Iterator;

import vcf_reader.format.entities.Attribute;
import vcf_reader.format.entities.AttributeType;
import vcf_reader.format.entities.AttributeWithValue;
import vcf_reader.format.entities.AttributedContactField;
import vcf_reader.format.entities.Contact;
import vcf_reader.format.entities.ContactFieldWithValue;
import vcf_reader.format.entities.ContactValueElement;

/**
 * Base improvement for all improvements that find unrecognised attributes
 * and replace them all by a {@link vcf_reader.format.entities.AttributeType#HOME
 * AttributeType.HOME} attribute.
 */
public abstract class ReplacementByHomeImprovement implements Improvement {

	/**
	 * Applies this improvement to the given contact value elements.
	 * @param elements The elements.
	 * @return Whether the improvement has made any changes.
	 */
	protected boolean apply(Iterable<ContactValueElement> elements) {
		AttributedContactField attributedContactField;
		Iterator<AttributeWithValue> attributesWithValuesIterator;
		AttributeWithValue attributeWithValue;
		ContactFieldWithValue contactFieldWithValue;
		Contact contact;
		boolean attributesOk;
		boolean changes = false;
		for (ContactValueElement telephone : elements) {
			contactFieldWithValue = telephone.getContactValue().getContactFieldWithValue();
			attributedContactField = contactFieldWithValue.getAttributedContactField();
			attributesWithValuesIterator = attributedContactField.getAttributesWithValues();
			attributesOk = true;
			while (attributesOk && attributesWithValuesIterator.hasNext()) {
				attributeWithValue = attributesWithValuesIterator.next();
				attributesOk = attributeWithValue.getAttribute().getType().isPresent() &&
				attributeWithValue.getAttribute().getType().get() != AttributeType.X;
			}
			if (!attributesOk) {
				contact = contactFieldWithValue.getContact();
				contact.replaceFieldAttributes(attributedContactField, Arrays.asList(new AttributeWithValue(Attribute.HOME)));
				changes = true;
			}
		}
		return changes;
	}
}
