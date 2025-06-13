package vcf_reader.tools;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import vcf_reader.format.entities.ContactFieldWithValue;
import vcf_reader.format.entities.ContactValue;
import vcf_reader.format.entities.ContactValueElement;

public class ListTools {

	public static List<ContactValueElement> getNonEmpty(List<ContactFieldWithValue> contactFieldsWithValues) {
		List<ContactValueElement> result = new ArrayList<>();
		if (contactFieldsWithValues != null) {
			Optional<ContactValue> opContactValue;
			for (ContactFieldWithValue contactFieldWithValue : contactFieldsWithValues) {
				opContactValue = contactFieldWithValue.getValue();
				if (opContactValue.isPresent()) {
					result.addAll(opContactValue.get().getNonEmptyElements());
				}
			}
		}
		return result;
	}
}
