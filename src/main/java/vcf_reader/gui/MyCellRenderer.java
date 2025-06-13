package vcf_reader.gui;

import java.awt.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.swing.JList;
import javax.swing.ListCellRenderer;

import vcf_reader.format.converters.ContactValueElementConverter;
import vcf_reader.format.entities.Attribute;
import vcf_reader.format.entities.AttributeType;
import vcf_reader.format.entities.Contact;
import vcf_reader.format.entities.ContactValueElement;
import vcf_reader.gui.components.AdaptableListCellComponent;

public class MyCellRenderer implements ListCellRenderer<Contact> {
	
    public Component getListCellRendererComponent(
    	JList<? extends Contact> list, Contact contact, int index, boolean isSelected, boolean cellHasFocus
    ) {
    	List<ContactValueElement> elements;
    	List<String> values;
    	List<String> lines = new ArrayList<>();
    	String displayValue;
    	Optional<Attribute> opPhoneType;
    	StringBuilder phones = new StringBuilder("Phones: ");
    	StringBuilder emails = new StringBuilder();
    	boolean first;
    	
    	elements = contact.getFullName();
    	values = ContactValueElementConverter.parse(elements);
    	lines.add(String.join(" ", values));
    	
   		elements = contact.getAllTelephones();
   		first = true;
   		for (ContactValueElement element : elements) {
   			displayValue = element.getDisplayValue();
   			if (!displayValue.isEmpty()) {
   				if (!first) {
   					phones.append(", ");
   				}
   				phones.append(displayValue);
   				opPhoneType = element.getContactValue().getContactFieldWithValue().getAttributedContactField().getPhoneType();
   				phones.append(printPhoneType(opPhoneType.orElse(null)));
   				first = false;
   			}
   		}
   		lines.add(phones.toString());
   		
   		elements = contact.getAllEmails();
   		first = true;
   		for (ContactValueElement element : elements) {
   			displayValue = element.getDisplayValue();
   			if (!displayValue.isEmpty()) {
   				if (!first) {
   					emails.append(", ");
   				}
   				emails.append(displayValue);
   				opPhoneType = element.getContactValue().getContactFieldWithValue().getAttributedContactField().getPhoneType();
   				emails.append(printPhoneType(opPhoneType.orElse(null)));
   				first = false;
   			}
   		}
   		if (!emails.isEmpty()) {
   			lines.add("Emails: " + emails);
   		}
   		
        return new AdaptableListCellComponent(lines);
    }
    
    private static String printPhoneType(Attribute attribute) {
    	StringBuilder result = new StringBuilder();
    	String phoneType;
    	if (attribute == null) {
    		phoneType = null;
    	} else {
	    	Optional<AttributeType> opAttributeType = attribute.getType();
	    	if (opAttributeType.isEmpty()) {
	    		phoneType = null;
	    	} else {
	    		AttributeType attributeType = opAttributeType.get();
	        	switch (attributeType) {
		        	case AttributeType.CELL:
		        		phoneType = "mobile";
		        		break;
		        	case AttributeType.HOME:
		        		phoneType = "landline";
		        		break;
		        	case AttributeType.X:
	        		default:
	        			phoneType = attribute.getOther().orElse(null);
	        			break;
	        	}
	    	}
    	}
    	if (phoneType != null) {
    		result.append(" (");
    		result.append(phoneType);
    		result.append(")");
    	}
    	return result.toString();
    }
}