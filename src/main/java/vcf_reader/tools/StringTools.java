package vcf_reader.tools;

import java.util.Optional;

/**
 * Class with general tools related to strings.
 */
public class StringTools {

	/**
	 * Splits the given string considering all empty strings.
	 * @param string A string.
	 * @param delim The delimiter to split the string by.
	 * @return The parts, including all empty strings.
	 */
	public static String[] splitWithEmptyStrings(String string, String delim) {
		// A negative limit parameter to the String.split() method makes it consider all empty strings.
		return string.split(delim, -1);
	}
	
	public static boolean isEmpty(Optional<String> string) {
		return string.isEmpty() || string.get().isEmpty();
	}
	
	public static String notNull(String nullableString) {
		String result;
		if (nullableString == null) {
			result = "";
		} else {
			result = nullableString;
		}
		return result;
	}
}
