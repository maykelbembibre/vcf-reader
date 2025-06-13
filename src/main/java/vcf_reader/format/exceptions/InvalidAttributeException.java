package vcf_reader.format.exceptions;

/**
 * Exception thrown when you try to create a contact field attribute from
 * a raw string that doesn't represent a valid attribute. A contact where
 * any of its fields causes this exception must be considered as not valid.
 */
public class InvalidAttributeException extends InvalidFieldException {

	private static final long serialVersionUID = 9201802882601319297L;
	
	public InvalidAttributeException() {
		super();
	}
	
	public InvalidAttributeException(String message) {
		super(message);
	}
}
