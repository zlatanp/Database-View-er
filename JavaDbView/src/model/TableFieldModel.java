package model;

import java.util.HashMap;

import gui.MainFrame;

/**
 * Klasa koja predstavlja kolonu u bazi
 * 
 * @author Jasmina
 *
 */
public class TableFieldModel {

	/**
	 * Naziv tabele koji je prikazan u view delu i koji se lokalizuje.
	 */
	private String label;

	/**
	 * Jedinstveni naziv kolone iz baze.
	 */
	private String code;

	/**
	 * Tip podatka koji se unosi u kolonu.
	 */
	private String type;

	/**
	 * Predstavlja vrednost koja nam govori da li je unos podataka u kolonu
	 * obavezan.
	 */
	private boolean mandatory;

	/**
	 * Polje koje modeluje duzinu tipova podataka koji imaju tu osobinu. Npr:
	 * char(3)= duzina je 3.
	 */
	private int lenght;

	/**
	 * Polje koje modeluje broj mesta iza zareza kod numerickih tipova podataka.
	 * U slucaju da tip nije numeric vrednost polja je -1.
	 */
	private int decimalPlaces;

	/**
	 * Polje koje modeluje ogranicenja stranog kljuca. Key = kod tabele odakle
	 * ogranicenje dolazi. Value = kod kolone na koju se trenutna kolona odnosi.
	 */
	private HashMap<String, String> foreignKeyConstraints;

	private boolean jesteDeoPK = false;

	@SuppressWarnings("unused")
	private boolean isForeignKey;

	private boolean isPrimaryKey;
	
	
	private String codeTable; // kod tabele kojoj pripada

	public TableFieldModel() {

	}

	public TableFieldModel(String name, String code, String type, boolean required) {
		super();
		this.label = name;
		this.code = code;
		this.type = type;
		this.mandatory = required;
		this.decimalPlaces = -1;
		this.lenght = -1;
		foreignKeyConstraints = new HashMap<String, String>();
		parseType(type.trim());
	}

	/**
	 * Metoda koja za dobijeni string popunjava polja:
	 * {@link model.TableFieldModel#type type} samo sa nazivom tipa,
	 * {@link model.TableFieldModel#length length} sa duzinom podatka (ako je
	 * navedeno), {@link model.TableFieldModel#decimalPlaces decimalPlaces} sa
	 * brojem cifara iza zareza (ako je navedeno).
	 * 
	 * @param typeForParse
	 *            - naziv tipa za kojeg treba da se popuni duzina, tip,
	 *            preciznost.
	 */
	private void parseType(String typeForParse) {

		int indexOdOpenBracket, indexOfClosedBracket, indexOfComma;

		if (typeForParse.contains("(")) {
			indexOdOpenBracket = typeForParse.indexOf("(");
			indexOfClosedBracket = typeForParse.indexOf(")");
			this.type = typeForParse.substring(0, indexOdOpenBracket);
			if (!typeForParse.contains(",")) {
				this.lenght = Integer.parseInt(typeForParse.substring(indexOdOpenBracket + 1, indexOfClosedBracket));
			} else {
				indexOfComma = typeForParse.indexOf(",");
				this.lenght = Integer.parseInt(typeForParse.substring(indexOdOpenBracket + 1, indexOfComma));
				this.decimalPlaces = Integer.parseInt(typeForParse.substring(indexOfComma + 1, indexOfClosedBracket));
			}
		} else {
			this.type = typeForParse;
		}
	}

	/**
	 * @param codeTable
	 *            Jedinstveni naziv tabele iz baze.
	 * @param codeColum
	 *            Jedinstveni naziv kolone iz baze.
	 * 
	 *
	 */
	public void addForeignKeyConstraint(String codeTable, String codeColum) {
		foreignKeyConstraints.put(codeTable, codeColum);
	}

	/**
	 * Metoda koja proverava da li je polje strani kljuc ili ne.
	 * 
	 * @return - true ili false ako jeste ili nije.
	 */
	public boolean isForeignKey() {
		return this.foreignKeyConstraints.size() > 0;
	}

	/**
	 * Metoda koja proverava da li je polje strani kljuc iz prosledjene tabele.
	 * 
	 * @param table
	 *            - model tabele za koju se proverava.
	 * @return - true ili false ako jeste ili ako nije strani kljuc.
	 */
	public boolean isForeignKeyFromTable(TableModel1 table) {
		boolean retVal;

		retVal = isForeignKey() ? this.foreignKeyConstraints.containsKey(table.getCode()) : false;

		return retVal;
	}

	/**
	 * Metoda koja vraca kod kolone iz tabele odakle dolazi strani kljuc.
	 * 
	 * @param table
	 *            - model tabele u kojoj je klona koja se prosledjuje kao strani
	 *            kljuc.
	 * @return - kod kolone koja je strani kljuc.
	 */
	public String getFieldCodeFromParent(TableModel1 table) {
		String code = "";

		code = this.foreignKeyConstraints.get(table.getCode());

		return code;
	}

	/**
	 * Metoda koja za tip polja vraca odgovarajuci tip filtera.
	 * 
	 * @return - string vrednost filtera koji treba da se upotrebi za tip.
	 */
	public String getFilterForField() {
		String filter = "";

		if (type.equalsIgnoreCase("string") || type.equalsIgnoreCase("char") || type.equalsIgnoreCase("varchar")) {
			filter = "like";
		} else if (type.equalsIgnoreCase("smallint") || type.equalsIgnoreCase("int") || type.equalsIgnoreCase("decimal")
				|| type.equalsIgnoreCase("numeric")) {
			filter = "equals";
		} else {
			filter = "range";
		}

		return filter;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isMandatory() {
		return mandatory;
	}

	public void setMandatory(boolean mandatory) {
		this.mandatory = mandatory;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getLabel() {
		String ret = "";
		if (MainFrame.getInstance().isSQLReader()) {
			ret = MainFrame.getInstance().getResourceBundle().getString(this.codeTable + "_" + this.code);
		} else {
			ret = MainFrame.getInstance().getResourceBundle().getString(label);
		}
		return ret;
	}

	public HashMap<String, String> getForeignKeyConstraints() {
		return foreignKeyConstraints;
	}

	public void setForeignKeyConstraints(HashMap<String, String> foreignKeyConstraints) {
		this.foreignKeyConstraints = foreignKeyConstraints;
	}

	public int getLenght() {
		return lenght;
	}

	public void setLenght(int lenght) {
		this.lenght = lenght;
	}

	public int getDecimalPlaces() {
		return decimalPlaces;
	}

	public void setDecimalPlaces(int decimalPlaces) {
		this.decimalPlaces = decimalPlaces;
	}

	public boolean isJesteDeoPK() {
		return jesteDeoPK;
	}

	public void setJesteDeoPK(boolean jesteDeoPK) {
		this.jesteDeoPK = jesteDeoPK;
	}

	public boolean isPrimaryKey() {
		return isPrimaryKey;
	}

	public void setPrimaryKey(boolean isPrimaryKey) {
		this.isPrimaryKey = isPrimaryKey;
	}

	public void setForeignKey(boolean isForeignKey) {
		this.isForeignKey = isForeignKey;
	}

	public String getCodeTable() {
		return codeTable;
	}

	public void setCodeTable(String codeTable) {
		this.codeTable = codeTable;
	}	

}
