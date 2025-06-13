package vcf_reader.format.exceptions;

/**
 * Exception thrown when you try to create a contact field from
 * a raw string that doesn't represent a valid field. A contact where
 * any of its fields causes this exception must be considered as not valid.
 */
public class InvalidFieldException extends Exception {

	private static final long serialVersionUID = -5655566659167899416L;

	public InvalidFieldException() {
		super();
	}
	
	public InvalidFieldException(String message) {
		super(message);
	}
}
