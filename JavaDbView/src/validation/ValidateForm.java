package validation;

import data.reader.DBDataHandler;
import model.TableModel1;
import model.TableRowModel;

/**
 * Klasa koja sluzi za validaciju formi.
 * 
 * @author Svetlana
 * @author Milena
 */
public class ValidateForm {

	/**
	 * Polje koje predstavlja sting vrednost koja se validira.
	 */
	private String value;
	
	/**
	 * 
	 */
	private boolean correct = true;
	
	/**
	 * Model tabele za koju se vrsi validacija
	 */
	private TableModel1 tableModel;
	
	/**
	 * Model reda koji se unosi/menja tj. validira.
	 */
	private TableRowModel rowModel;
	
	/**
	 * Polje koje sluzi za komunikaciju sa bazom.
	 */
	private DBDataHandler dbDataHandler;

	public ValidateForm() {
		
	}
	
	public ValidateForm(TableModel1 tableModel, TableRowModel rowModel, DBDataHandler dbDataHandler) {
		this.tableModel = tableModel;
		this.rowModel = rowModel;
		this.dbDataHandler = dbDataHandler;
	}

	/**
	 * Metoda koja služi za proveru ispravnosti unesenog teksta - primarnog kljuca.
	 * 
	 * @param value
	 *            - tekst koji je unet
	 * 
	 * @return true ili false ako je upisivanje ispravno ili ne.
	 */
	public boolean correctPK(String value) {
		if (value == null) {
			correct = false;
			return correct;
		} else if (value == "") {
			correct = false;
			return correct;
		} else if (value.isEmpty()) {
			correct = false;
			return correct;
		}

		TableRowModel tableRowModel = dbDataHandler.getByIdValidate(tableModel, rowModel);
		if (tableRowModel.getFieldNames().size() > 0) {
			correct = false;
			return false;
		}

		return correct;
	}

	
	/**
	 * Metoda koja služi za proveru ispravnosti unesenog teksta - koji nije primarni kljuc.
	 * 
	 * @param value
	 *            - tekst koji je unet
	 * 
	 * @return true ili false ako je upisivanje ispravno ili ne.
	 */
	public boolean correct(String value) {
		if (value == null) {
			correct = false;
			return correct;
		} else if (value == "") {
			correct = false;
			return correct;
		} else if (value.isEmpty()) {
			correct = false;
			return correct;
		} else {
			correct = true;
			return correct;
		}

	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
