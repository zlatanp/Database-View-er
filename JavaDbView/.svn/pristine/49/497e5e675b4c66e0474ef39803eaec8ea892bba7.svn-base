package gui;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 * Pomocna klasa koja sluzi za proveru vrednosti koje korisnik unosi na odredjena polja.
 * 
 * @author Milena
 *
 */
public class LoginInputField extends PlainDocument {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3905787093875750232L;
	
	/**
	 * Polje koje predstavlja granicu u duzini teksta koji se moze uneti.
	 */
	private int limit;

	LoginInputField(int limit) {
		super();
		this.limit = limit;
	}

	LoginInputField(int limit, boolean upper) {
		super();
		this.limit = limit;
	}

	public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
		if (str == null)
			return;

		if ((getLength() + str.length()) <= limit) {
			super.insertString(offset, str, attr);
		}
	}
}
