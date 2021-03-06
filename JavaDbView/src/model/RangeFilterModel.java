package model;

/**
 * Klasa koja sluzi radi lakse komunikacije unutar aplikacije izmedju prozora za filtriranje i metoda koje 
 * komuniciraju sa bazom podataka. Ova klasa se koristi kao parametar u metodi 
 * koja pomocu ove klase lakse generise {@code BETWEEN} operator {@code WHERE} klauzule sql upita. 
 * 
 * 
 * @author Jasmina
 *
 */
public class RangeFilterModel {

	/**
	 * Naziv polja.
	 */
	private String fieldName;
	
	/**
	 * Donja vrednost polja.
	 */
	private String fromValue;
	
	/**
	 * Gornja vrednost polja.
	 */
	private String toValue;

	public RangeFilterModel(String fieldName, String fromValue, String toValue) {
		super();
		this.fieldName = fieldName;
		this.fromValue = fromValue;
		this.toValue = toValue;
	}
	
	
	/**
	 * Metoda koja za generise deo WHERE klauzule za BETWEEN operator.
	 * 
	 * @param table - model tabele koja se filtrira.
	 * @return {@link java.lang.String String} vrednost BETWEEN operatora za trenutne podatke.
	 */
	public String generateWhereClauseForRangeFilter(TableModel1 table){
		String query, fieldCode;
		
		query = "";
		fieldCode = "";
		
		fieldCode = new TableRowModel().getFieldCodeForFieldName(fieldName, table.getFieldModels());
		query = fieldCode + " BETWEEN ?  AND ? ";
		
		return query;
	}


	public String getFieldName() {
		return fieldName;
	}


	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}


	public String getFromValue() {
		return fromValue;
	}


	public void setFromValue(String fromValue) {
		this.fromValue = fromValue;
	}


	public String getToValue() {
		return toValue;
	}


	public void setToValue(String toValue) {
		this.toValue = toValue;
	}
	
	
}
