package vcf_reader.tools;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.UnsupportedCharsetException;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeUtility;
import vcf_reader.format.exceptions.InvalidFieldException;

public class QuotedPrintableTools {
	
	/**
	 * Tries to get the charset that has the given name or throws an exception that
	 * can be controlled to consider the contact that had the problemmatic charset in
	 * some of their fields as invalid.
	 * @param name The charset name.
	 * @return The charset.
	 * @throws InvalidFieldException If the charset is illegal or not supported.
	 */
	public static Charset tryToGetCharset(String name) throws InvalidFieldException {
		try {
			return Charset.forName(name);
		} catch (IllegalCharsetNameException e) {
			throw new InvalidFieldException("Charset \"" + name + "\" is illegal.");
		} catch (UnsupportedCharsetException e) {
			throw new InvalidFieldException("Charset \"" + name + "\" is not supported.");
		}
	}
	
	/**
	 * Decodes the strings found in VCF files in quoted-printable encoding.
	 * @param string An encoded string.
	 * @param charset The charset that maps the bytes as represented in the encoded string
	 * to actual characters.
	 * @return The decoded string. This method never returns null. If there's any problem
	 * it will throw an exception instead.
	 * @throws InvalidFieldException If the encoding of the given string isn't what this method expects.
	 */
	public static String decode(String string, Charset charset) throws InvalidFieldException {
		InputStream byteArrayInputStream = new ByteArrayInputStream(string.getBytes());
		InputStream decodedStream;
		try {
			decodedStream = MimeUtility.decode(byteArrayInputStream, "quoted-printable");
			return new String(decodedStream.readAllBytes(), charset);
		} catch (MessagingException e) {
			throw new RuntimeException("Check the encoding in the code that caused this exception.", e);
		} catch (IOException e) {
			throw new InvalidFieldException();
		}
	}
}
