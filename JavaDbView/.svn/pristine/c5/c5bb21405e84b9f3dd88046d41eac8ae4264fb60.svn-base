package model;

import java.util.ArrayList;

/**
 * Klasa koja modeluje jednu kolonu u tabeli, tacnije enkapsulira naziv kolone zajedno
 * sa svim vrednostima iz tabele.
 * 
 * @author Jasmina
 *
 */
public class TableColumnModel {

	/**
	 * Naziv kolone (ne kod vec labela).
	 */
	private String columnName;
	
	/**
	 * Vrednosti svih redova kolone sa {@link #columnName nazivom}.
	 */
	private ArrayList<String> values;
	
	public TableColumnModel() {
		values = new ArrayList<String>();
	}

	public TableColumnModel(String columnName, ArrayList<String> values) {
		super();
		this.columnName = columnName;
		this.values = values;
	}
	
	/**
	 * Metoda kojom se dodaje jedna vrednost.
	 * @param newValue - vrednost koja treba da se doda.
	 */
	public void addValue(String newValue)
	{
		values.add(newValue);
	}

	@Override
	public String toString() {
		StringBuilder retVal;
		
		retVal = new StringBuilder();
		
		retVal.append("Naziv: ");
		retVal.append(this.columnName);
		retVal.append("\n");
		retVal.append("Vrednosti: \n");
		for (String val : this.values) 
		{
			retVal.append(val);
			retVal.append("\n");
		}
		return retVal.toString();
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public ArrayList<String> getValues() {
		return values;
	}

	public void setValues(ArrayList<String> values) {
		this.values = values;
	}
	
	
}
