package data.reader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import contoller.UpdateManager;
import gui.DialogUpozorenje;
import gui.MainFrame;
import model.RangeFilterModel;
import model.TableColumnModel;
import model.TableFieldModel;
import model.TableModel1;
import model.TableRowModel;

/**
 * Klasa koja implementira komunikaciju sa bazom.
 * 
 * @author Jasmina
 *
 */
public class DBDataHandler {

	/**
	 * Konekcija ka bazi podataka koja se dobija iz 
	 * {@link gui.MainFrame	 MainFrame} klase.
	 */
	private Connection dbConnection;

	/**
	 * {@link java.lang.String String} vrednost upita za bazu.
	 */
	private String sqlQuery;

	/**
	 * {@link java.sql.PreparedStatement PreparedStatement} instanca koja sluzi
	 * za pokretanje upita nad bazom podataka, i izbegavanje SQL Injection-a.
	 */
	private PreparedStatement preparedStatement;

	/**
	 * Format parsiranja datuma na osnovu trenutnog jezika aplikacije. Moguce
	 * vrednosti su: {@link #SERBIAN_DATE_FORMAT srpski} ili
	 * {@link #ENGLISH_DATE_FORMAT engleski} format.
	 */
	private String dateFormat;

	/**
	 * Konstantna vrednost formata za Republiku Srbiju (dd.MM.yyyy.)
	 */
	public static final String SERBIAN_DATE_FORMAT = "dd.MM.yyyy";

	/**
	 * Konstantna vrednost formata za SAD(MM/dd/yyyy.)
	 */
	public static final String ENGLISH_DATE_FORMAT = "MM/dd/yyyy"; 

	public DBDataHandler() {
		dbConnection = MainFrame.getConnection();
		sqlQuery = "";
		dateFormat = DBDataHandler.SERBIAN_DATE_FORMAT;
	}

	/**
	 * Metoda koja sluzi za dodavanje jednog reda u tabelu.
	 * 
	 * @param table
	 *            - model tabele u koju se red dodaje.
	 * @param rowForInsert
	 *            - red koji se dodaje.
	 * @return true ili false ako je upisivanje uspelo ili ne.
	 */
	public boolean insert(TableModel1 table, TableRowModel rowForInsert) {
		// INSERT INTO drzava (P1, P2, P3) VALUES (?,?,?)

		boolean success = true;

		sqlQuery = "INSERT INTO " + table.getCode() + "(" + rowForInsert.getFieldsForQuery(table.getFieldModels())
				+ " )" + " VALUES " + rowForInsert.getQuestionMarksForQuery();

		try {
			preparedStatement = dbConnection.prepareStatement(sqlQuery);
			fillPreparedStatement(preparedStatement, rowForInsert, table, false, false);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			success = false;
			DialogUpozorenje upozorenje = new DialogUpozorenje("Neuspesan upis u bazu! " + e.getMessage());
			upozorenje.setVisible(true);
		}

		return success;
	}

	/**
	 * Metoda koja sluzi za brisanje reda iz tabele.
	 * 
	 * @param table
	 *            - model tabele iz koje se red brise.
	 * @param rowForDelete
	 *            - red za brisanje.
	 * @return - true ili false ako je brisanje uspesno ili ne.
	 */
	public boolean delete(TableModel1 table, TableRowModel rowForDelete) {
		// DELETE FROM Customers WHERE CustomerName='Alfreds Futterkiste' AND
		// ContactName='Maria Anders';
		boolean success = true;

		sqlQuery = "DELETE FROM " + table.getCode() + " WHERE " + rowForDelete.generateWhereClause(table);

		try {
			preparedStatement = dbConnection.prepareStatement(sqlQuery);
			fillPreparedStatement(preparedStatement, rowForDelete, table, false, false);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			success = false;
		}

		return success;
	}

	/**
	 * Metoda koja sluzi za azuriranje reda u tabeli.
	 * 
	 * @param table
	 *            - model tabele u kojoj se nalazi red za azuriranje.
	 * @param rowForUpdate
	 *            - red za azuriranje.
	 * @return true ili false ako je uspesno azuriranje.
	 */
	public boolean update(TableModel1 table, TableRowModel rowForUpdate) {
		// UPDATE Customers SET ContactName='Alfred Schmidt', City='Hamburg'
		// WHERE key1 = 'a' AND key2 = 'd';

		boolean success = true;
		TableRowModel modifiedRow = rowForUpdate.promeniRowZaUpdate(table);

		sqlQuery = "UPDATE " + table.getCode() + " SET " + rowForUpdate.generateSetClause(table) + " WHERE "
				+ rowForUpdate.generateWhereClause(table);

		try {
			preparedStatement = dbConnection.prepareStatement(sqlQuery);
			fillPreparedStatement(preparedStatement, modifiedRow, table, false, false);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			success = false;
			DialogUpozorenje upozorenje = new DialogUpozorenje("Neuspesan upis u bazu! " + e.getMessage());
			upozorenje.setVisible(true);
		}

		return success;
	}

	/**
	 * Metoda koja na osnovu vrednosti kljucnog obelezja vraca ceo red iz baze.
	 * 
	 * @param table
	 *            - model tabele iz koje se trazi red.
	 * @param key
	 *            - kljucno obelezje (nazivi i vrednosti, moze ih biti vise).
	 * @return vrednosti svih kolona trazenog reda iz baze.
	 */
	public TableRowModel getById(TableModel1 table, TableRowModel key) {
		// SELECT * FROM Customers WHERE Id = 1

		TableRowModel retValue = new TableRowModel();
		ResultSet result;

		sqlQuery = "SELECT * FROM " + table.getCode() + " WHERE " + key.generateWhereClause(table);

		try {
			preparedStatement = dbConnection.prepareStatement(sqlQuery);
			fillPreparedStatement(preparedStatement, key, table, false, false);
			result = preparedStatement.executeQuery();
			while (result.next()) {
				retValue = getRowModelFromResultSet(table.getFieldModels(), result);
				break;
			}
		} catch (SQLException e) {
			DialogUpozorenje upozorenje = new DialogUpozorenje("Ne postoji slog sa unetim ID- jem! " + e.getMessage());
			upozorenje.setVisible(true);
		}

		return retValue;
	}
	
	public TableRowModel getByIdValidate(TableModel1 table, TableRowModel key) {
		// SELECT * FROM Customers WHERE Id = 1

		TableRowModel retValue = new TableRowModel();
		ResultSet result;

		sqlQuery = "SELECT * FROM " + table.getCode() + " WHERE " + key.generateWhereClause(table);

		try {
			preparedStatement = dbConnection.prepareStatement(sqlQuery);
			fillPreparedStatement(preparedStatement, key, table, false, false);
			result = preparedStatement.executeQuery();
			while (result.next()) {
				retValue = getRowModelFromResultSet(table.getFieldModels(), result);
				break;
			}
		} catch (SQLException e) {
			//DialogUpozorenje upozorenje = new DialogUpozorenje("Ne postoji slog sa unetim ID- jem! " + e.getMessage());
			//upozorenje.setVisible(true);
		}

		return retValue;
	}

	/**
	 * Metoda koja za dobijen rezultat upita nad bazom pravi instancu klase
	 * {@link model.TableRowModel TableRowModel} koju popunjava dobijenim
	 * podacima.
	 * 
	 * @param fields
	 * 		- lista {@link TableFieldModel modela} polja tabele.
	 * @param resultSet
	 * 		- {@link ResultSet rezultat} upita nad bazom.
	 * @return
	 * 		- {@link TableRowModel model} reda za tabelu koji se upisuje.
	 */
	private TableRowModel getRowModelFromResultSet(ArrayList<TableFieldModel> fields, ResultSet resultSet) {
		TableRowModel retValue = new TableRowModel();
		BigDecimal numericValue;
		String stringValue;
		boolean boolValue;
		Date dateValue;
		Timestamp timestampValue;
		Byte slika;

		for (TableFieldModel field : fields) {
			switch (field.getType()) {
			case "smallint":
			case "int":
			case "decimal":
			case "numeric":
				try {
					numericValue = resultSet.getBigDecimal(field.getCode());
					if (numericValue != null) {
						stringValue = "" + numericValue;
					} else {
						stringValue = null;
					}
					retValue.addPair(field.getLabel(), stringValue);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				break;
			case "char":
			case "varchar":
			case "string":
				try {
					stringValue = resultSet.getString(field.getCode());
					stringValue = stringValue == null ? "" : stringValue;
					retValue.addPair(field.getLabel(), stringValue);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				break;
			case "boolean":
				try {
					boolValue = resultSet.getBoolean(field.getCode());
					retValue.addPair(field.getLabel(), boolValue ? "true" : "false");
				} catch (SQLException e) {

				}
				break;
			case "Date":
				try {
					dateValue = resultSet.getDate(field.getCode());
					stringValue = dateValue == null ? "" : convertToStringFromDate(dateValue);
					retValue.addPair(field.getLabel(), stringValue);
				} catch (SQLException e) {

				}
				break;
			case "DateTime":
				try {
					timestampValue = resultSet.getTimestamp(field.getCode());
					stringValue = timestampValue == null ? "" : convertToStringFromTimestramp(timestampValue);
					retValue.addPair(field.getLabel(), stringValue);
				} catch (SQLException e) {

				}
				break;
			case "image":
				try {
					slika = resultSet.getByte(field.getCode());
					// System.out.println(slika.toString());
					if (slika == null) {
						stringValue = "";
					} else {
						stringValue = slika.toString();
					}
					retValue.addPair(field.getLabel(), stringValue);
				} catch (SQLException e) {

				}
				break;
			}
		}

		return retValue;
	}

	/**
	 * Metoda koja konvertuje {@link java.sql.Timestamp Timestamp} vremena u
	 * string vrednost, potrebnu za generisanje upita.
	 * 
	 * @param value
	 *            - {@link java.sql.Timestamp Timestamp} vrednost vremena za
	 *            konverziju.
	 * @return - {@link java.util.String String} vrednost u odgovarajucem
	 *         formatu.
	 */
	private String convertToStringFromTimestramp(Timestamp value) {
		String retValue;
		String pom = dateFormat + "HH:mm";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pom);

		retValue = simpleDateFormat.format(value);

		return retValue;
	}

	/**
	 * Metoda koja konvertuje {@link java.sql.Date Date} datuma u string
	 * vrednost, potrebnu za generisanje upita.
	 * 
	 * @param value
	 *            - {@link java.sql.Date Date} vrednost datuma za konverziju.
	 * @return - {@link java.util.String String} vrednost u odgovarajucem
	 *         formatu.
	 */
	private String convertToStringFromDate(Date value) {
		String retValue;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);

		retValue = simpleDateFormat.format(value);

		return retValue;
	}

	/**
	 * Metoda koja iz baze uzima sve redove neke tabele.
	 * 
	 * @param table
	 *            - model tabele ciji se podaci traze.
	 * @return
	 * 		- lista {@link TableRowModel modela} reda koji se citaju iz tabele.
	 */
	public ArrayList<TableRowModel> getAllFromTable(TableModel1 table) {

		// select * from table

		ArrayList<TableRowModel> retVal = new ArrayList<>();
		ResultSet result;

		sqlQuery = "SELECT * FROM " + table.getCode();

		try {
			preparedStatement = dbConnection.prepareStatement(sqlQuery);
			result = preparedStatement.executeQuery();
			while (result.next()) {
				retVal.add(getRowModelFromResultSet(table.getPolja(), result));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return retVal;
	}

	/**
	 * Metoda koja popunjava vrednostima upitnike unutar
	 * {@link java.sql.PreparedStatement PreparedStatement}-a na osnovu tipova
	 * podataka koji se ubacuju.
	 * 
	 * @param stm
	 *            - instanca {@link java.sql.PreparedStatement
	 *            PreparedStatement}-a nad konekcijom prema bazi nad kojim treba
	 *            da se upitnici popune vrednostima.
	 * @param row
	 *            - red baze cijim se vrednostima popunjavaju upitnici.
	 * @param table
	 *            - tabela nad kojom se vrsi upit ciji se upitnici popunjavaju.
	 * @param isFilter
	 *            - da li je upit za filtriranje.
	 * @param isParentChildRowFilter
	 *            - da li je upit za filtriranje u parent-child odnosu.
	 */
	private void fillPreparedStatement(PreparedStatement stm, TableRowModel row, TableModel1 table, boolean isFilter,
			boolean isParentChildRowFilter) {

		ArrayList<String> fieldNames = row.getFieldNames();
		ArrayList<String> fieldValues = row.getFieldValues();
		BigDecimal numericValue = null;
		String currentFieldValue, stringValueToSet;
		boolean boolValue;
		Date dateValue = null;
		Timestamp timeStpamp = null;

		int type = -1;

		// prodji kroz sva polja
		for (int i = 0; i < fieldNames.size(); i++) {
			currentFieldValue = fieldValues.get(i).trim();
			switch (row.getFieldTypeForFieldName(fieldNames.get(i), table.getFieldModels())) {
			case "image": // DODALA
				if (currentFieldValue != null) {
					if (currentFieldValue.equals("NULL")) {
						// (INT, INPUTSTREAM, INT LENGTH)
						// KAKO DOCI DO INPUTSTREAM-A IZ OPENLISTENER-A
						// new FileInputStream(fieldValues.get(i)) - ovaj
						// deo napisan samo zbog testa
							try {
								stm.setBinaryStream(i + 1, new FileInputStream(fieldValues.get(i)),
										(int) fieldNames.get(i).length());
							} catch (FileNotFoundException | SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						//type = "image";
						continue;
					}
				}
				break;
			case "smallint":
			case "decimal":
			case "numeric":
			case "int":
				if (currentFieldValue != null) {
					if (!currentFieldValue.equals("")) {
						numericValue = new BigDecimal(currentFieldValue);
					}
				}
				try {
					stm.setBigDecimal(i + 1, numericValue); // i+1 jer prepared
															// ide od 1 a ne od
															// nula
				} catch (SQLException e) {
					switch (row.getFieldTypeForFieldName(fieldNames.get(i), table.getFieldModels())) {
					case "smallint":
						type = java.sql.Types.SMALLINT;
						break;
					case "decimal":
						type = java.sql.Types.DECIMAL;
						break;
					case "numeric":
						type = java.sql.Types.NUMERIC;
						break;
					case "int":
						type = java.sql.Types.INTEGER;
						break;
					}
					if (type != -1) {
						setNullValue(i + 1, type);
					}
				}
				break;
			case "char":
			case "varchar":
			case "strig":
				try {
					stringValueToSet = currentFieldValue;
					if (isFilter && !isParentChildRowFilter) {
						stringValueToSet = "%" + currentFieldValue + "%";
					}
					stm.setString(i + 1, stringValueToSet);
				} catch (SQLException e) {
					setNullValue(i + 1, java.sql.Types.VARCHAR);
				}
				break;
			case "boolean":
				try {
					boolValue = currentFieldValue.equals("true");
					stm.setBoolean(i + 1, boolValue);
				} catch (SQLException e) {
					setNullValue(i + 1, java.sql.Types.BOOLEAN);
				}
				break;
			case "date":
				try {
					if (currentFieldValue.equals(""))
						dateValue = null;
					else
						dateValue = convertToDateFromString(currentFieldValue);
					preparedStatement.setDate(i + 1, dateValue);
				} catch (SQLException e) {
					setNullValue(i + 1, java.sql.Types.DATE);
				}
				break;
			case "datetime":
				try {
					if (currentFieldValue.equals(""))
						timeStpamp = null;
					else
						timeStpamp = convertToTimeStampFromString(currentFieldValue);
					preparedStatement.setDate(i + 1, dateValue);
				} catch (SQLException e) {
					setNullValue(i + 1, java.sql.Types.TIMESTAMP);
				}
				break;
			default:
				break;
			}
		}
	}

	/**
	 * Metoda koja konvertuje {@link java.util.String String} vrednost vremena u
	 * trenutnom formatu i pravi instancu tipa Timestamp za upis u bazu.
	 * 
	 * @param value
	 *            - {@link java.util.String String} vrednost vremena trenutnog
	 *            formata.
	 * @return - {@link java.sql.Timestamp Timestamp} vrednost za upis u bazu.
	 */
	private Timestamp convertToTimeStampFromString(String value) {
		Timestamp retTimestamp = null;
		String pom = dateFormat + "HH:mm";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pom);

		try {
			retTimestamp = new Timestamp(simpleDateFormat.parse(value).getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return retTimestamp;
	}

	/**
	 * Metoda koja konvertuje {@link java.util.String String} vrednost datuma u
	 * trenutnom formatu i pravi instancu tipa Date za upis u bazu.
	 * 
	 * @param value
	 *            - {@link java.util.String String} vrednost datuma trenutnog
	 *            formata.
	 * @return - {@link java.sql.Date Date} vrednost za upis u bazu.
	 */
	private Date convertToDateFromString(String value) {
		Date retDate = null;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);

		try {
			retDate = new Date(simpleDateFormat.parse(value).getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return retDate;
	}

	/**
	 * Metoda koja na prosledjeni indeks stavlja nedostajajucu vrednost(null), u
	 * okviru {@link java.sql.PreparedStatement PreparedStatement}-a.
	 * 
	 * @param i
	 *            - indeks.
	 * @param tpye
	 *            - tip.
	 */
	private void setNullValue(int i, int tpye) {
		try {
			preparedStatement.setNull(i, tpye);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Metoda koja za prosledjene filtere i tabelu, iz baze dobavlja podatke
	 * koji zadovoljavaju date filtere.
	 * 
	 * @param tableModel
	 *            - tabela ciji se filtrirani podaci traze.
	 * @param equalsFilters
	 *            - skup parova naziv-vrednost polja koje nisu tipa String,
	 *            {@link java.sql.Date Date} ili Integer.
	 * @param likeFilters
	 *            - skup parova naziv-vrednost tipa String na osnovu kojih se
	 *            generise {@code LIKE} komanda {@code WHERE} klauzule sql
	 *            upita.
	 * @param rangeFilters
	 *            - skup parova naziv-vrednost {@link java.sql.Date Date} ili
	 *            Integer na osnovu kojih se generise {@code BETWEEN} komanda
	 *            {@code WHERE} klauzule sql upita. Ocekuje se
	 *            {@link java.util.ArrayList lista}
	 *            {@link model.RangeFilterModel RangeFilterModel}-a sa nazivom
	 *            polja, pocetnom i krajnjom vrednoscu.
	 *            
	 * @param isParentChildRowFilter
	 * 							- flag koji govori da li se filtrira red iz parent tabele.
	 * @return 
	 * 		-  {@link java.util.ArrayList Listu} redova koji zadovoljavaju
	 *         trazeni filter. U slucaju da nijedan red ne zadovoljava, vraca se
	 *         prazna lista.
	 */
	public ArrayList<TableRowModel> getFilteredTable(TableModel1 tableModel, TableRowModel equalsFilters,
			TableRowModel likeFilters, ArrayList<RangeFilterModel> rangeFilters, boolean isParentChildRowFilter) {
		TableRowModel preparedRowForFillingQMarks = new TableRowModel();
		ArrayList<TableRowModel> filteredData = new ArrayList<TableRowModel>();
		ResultSet resultSet = null;
		TableRowModel row = new TableRowModel();

		sqlQuery = "SELECT * FROM " + tableModel.getCode();

		if (equalsFilters != null || likeFilters != null || rangeFilters != null) {
			sqlQuery += " WHERE ";

			if (equalsFilters != null && equalsFilters.getFieldValues().size() > 0) {
				sqlQuery += equalsFilters.generateWhereClauseForAll(tableModel);
				preparedRowForFillingQMarks = equalsFilters;
			}

			if (likeFilters != null && likeFilters.getFieldValues().size() > 0) {
				if (equalsFilters != null && equalsFilters.getFieldValues().size() > 0) {
					sqlQuery += " AND ";
				}

				sqlQuery += " " + likeFilters.generateWhereClauseWithLike(tableModel);
				preparedRowForFillingQMarks.addPairs(likeFilters.getFieldNames(), likeFilters.getFieldValues());
			}

			if (rangeFilters != null && rangeFilters.size() > 0) {
				if ((equalsFilters != null && equalsFilters.getFieldValues().size() > 0)
						|| (likeFilters != null && likeFilters.getFieldValues().size() > 0)) {
					sqlQuery += " AND ";
				}
				sqlQuery += " " + generateBetweenStatements(rangeFilters, tableModel);
				for (RangeFilterModel filter : rangeFilters) {
					preparedRowForFillingQMarks.addPair(filter.getFieldName(), filter.getFromValue());
					preparedRowForFillingQMarks.addPair(filter.getFieldName(), filter.getToValue());
				}
			}
		}

		try {
			preparedStatement = dbConnection.prepareStatement(sqlQuery);
			fillPreparedStatement(preparedStatement, preparedRowForFillingQMarks, tableModel, true, false);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				row = getRowModelFromResultSet(tableModel.getFieldModels(), resultSet);
				filteredData.add(row);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return filteredData;

	}

	/**
	 * Metoda koja generise BETWEEN klauzule za WHERE klauzulu.
	 * 
	 * @param rangeFilters
	 *            - filteri po kojima se generisu BETWEEN klauzule.
	 * @param tableModel
	 *            - tabela koja se pretrazuje.
	 * @return {@code BETWEEN} klauzulu sql upita.
	 */
	private String generateBetweenStatements(ArrayList<RangeFilterModel> rangeFilters, TableModel1 table) {
		StringBuilder builder = new StringBuilder();

		if (rangeFilters.size() == 1) {
			builder.append(rangeFilters.get(0).generateWhereClauseForRangeFilter(table));
		} else {
			builder.append(" (");
			builder.append(rangeFilters.get(0).generateWhereClauseForRangeFilter(table));
			builder.append(")");

			for (int i = 1; i < rangeFilters.size(); i++) {
				builder.append(" AND (");
				builder.append(rangeFilters.get(i).generateWhereClauseForRangeFilter(table));
				builder.append(")");
			}
		}

		return builder.toString();
	}

	/**
	 * Metoda koja za iz baze uzima sve vrednosti odredjenih kolona.
	 * 
	 * @param table
	 *            - model tabele iz koje se traze podaci.
	 * @param fieldNames
	 *            - nazivi (ne kodovi) polja ciji se podaci traze.
	 *            
	 * @return {@link java.util.ArrayList Listu} {@link model.TableColumnModel
	 *         modela kolona} popunjenim podacima.
	 */
	public ArrayList<TableColumnModel> getColumnValues(TableModel1 table, ArrayList<String> fieldNames) {
		ArrayList<TableColumnModel> columnValues = new ArrayList<TableColumnModel>();
		ArrayList<TableRowModel> rows = new ArrayList<TableRowModel>();
		ArrayList<TableFieldModel> fields = table.getFieldsByNames(fieldNames);
		TableRowModel row;
		ResultSet result;

		sqlQuery = "SELECT DISTINCT " + generateSelectClause(table, fieldNames) + " FROM " + table.getCode();

		try {
			preparedStatement = dbConnection.prepareStatement(sqlQuery);
			result = preparedStatement.executeQuery();
			while (result.next()) {
				row = getRowModelFromResultSet(fields, result);
				rows.add(row);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		columnValues = transformRowsToColumns(rows, fields);

		return columnValues;
	}

	/**
	 * Metoda koja transformise listu
	 * {@link model.TableRowModel modela reda} u {@link java.util.ArrayList
	 * listu} {@link model.TableColumnModel modela kolona}.
	 * 
	 * @param rows
	 *            - {@link java.util.ArrayList lista} modela redova koja treba
	 *            da se transformise.
	 * @param fields
	 *            - {@link java.util.ArrayList lista} {@link model.TableColumnModel polja} koja se transformisu.
	 *            
	 * @return listu kolona koje se dobijaju transformacijom iz redova.
	 */
	private ArrayList<TableColumnModel> transformRowsToColumns(ArrayList<TableRowModel> rows,
			ArrayList<TableFieldModel> fields) {
		ArrayList<TableColumnModel> columns = new ArrayList<TableColumnModel>();
		TableColumnModel column = new TableColumnModel();
		String name;

		for (TableFieldModel field : fields) {
			column = new TableColumnModel();
			name = field.getLabel();
			column.setColumnName(name);

			for (TableRowModel row : rows) {
				column.addValue(row.getValue(name));
			}

			columns.add(column);
		}

		return columns;
	}

	/**
	 * Metoda koja na osnovu naziva kolona generise {@code SELECT} klauzulu
	 * {@code SQL} upita.
	 * 
	 * @param tableModel
	 *            - model tabele odakle se uzimaju vrednosti.
	 * @param fieldNames
	 *            - nazivi kolona cije se vrednosti uzimaju.
	 * @return {@link java.lang.String String} vrednost {@code SELECT} klauzule
	 *         {@code SQL} upita.
	 */
	private String generateSelectClause(TableModel1 tableModel, ArrayList<String> fieldNames) {
		StringBuilder selectClause;
		TableRowModel helper;

		selectClause = new StringBuilder();
		helper = new TableRowModel();

		selectClause.append(helper.getFieldCodeForFieldName(fieldNames.get(0), tableModel.getFieldModels()));
		if (fieldNames.size() > 1) {
			for (int i = 1; i < fieldNames.size(); i++) {
				selectClause.append(", ");
				selectClause.append(helper.getFieldCodeForFieldName(fieldNames.get(i), tableModel.getFieldModels()));
			}
		}

		return selectClause.toString();
	}

	/**
	 * Metoda koja iz baze vraca sve moguce vrednosti stranih kljuceva.
	 * 
	 * @param tableModel
	 *            - model tabele cije sve vrednosti stranih kljuceva traze.
	 * @return 
	 * 		 -vrednost stranih kljuceva iz tabela iz kojih strani kljucevi
	 *        dolaze.
	 */
	public ArrayList<TableColumnModel> getForeignKeyValues(TableModel1 tableModel) {
		ArrayList<TableColumnModel> foreignKeys = new ArrayList<TableColumnModel>();
		ArrayList<TableColumnModel> tempForeignKeys;
		HashMap<String, String> fkConstraints;
		TableColumnModel fkColumn;
		TableModel1 foreignTable;
		ArrayList<String> fieldNames;
		UpdateManager updateManager = MainFrame.getInstance().getUpdateManager();

		// za svaki strani kljuc u prosledjenoj tabeli
		for (TableFieldModel foreignKey : tableModel.getForeignKeys()) {
			fkConstraints = foreignKey.getForeignKeyConstraints();
			tempForeignKeys = new ArrayList<TableColumnModel>();
			// za svaki par tabela : polje odakle dolazi strani kljuc
			for (Entry<String, String> entry : fkConstraints.entrySet()) {
				fieldNames = new ArrayList<String>();
				foreignTable = updateManager.getModelForCode(entry.getKey());
				fieldNames.add(foreignTable.getFieldNameFromCode(entry.getValue()));
				fkColumn = getColumnValues(foreignTable, fieldNames).get(0);
				fkColumn.setColumnName(foreignKey.getLabel());
				tempForeignKeys.add(fkColumn);
			}
			foreignKeys.add(intersectValues(tempForeignKeys));
		}

		return foreignKeys;
	}

	/**
	 * Metoda koja od vise {@link model.TableColumnModel modela kolona} pravi
	 * jedan {@link model.TableColumnModel model kolone} koji sadrzi presek
	 * vrednosti svih vrednosti koje su dosle u modelima kolona.
	 * 
	 * @param fksToIntersect
	 *            - {@link java.util.ArrayList lista}
	 *            	{@link model.TableColumnModel modela kolona} nad cijim
	 *            	vrednostima treba da se uradi presek.
	 * @return 
	 *     -{@link model.TableColumnModel model kolone} koji sadrzi presek
	 *      vrednosti.
	 */
	private TableColumnModel intersectValues(ArrayList<TableColumnModel> fksToIntersect) {
		TableColumnModel intersected = new TableColumnModel();
		ArrayList<String> intersectedValues;

		if (fksToIntersect.size() == 1) {
			intersected = fksToIntersect.get(0);
		} else {
			intersected.setColumnName(fksToIntersect.get(0).getColumnName());
			intersectedValues = fksToIntersect.get(0).getValues();
			for (int i = 1; i < fksToIntersect.size(); i++) {
				intersectedValues.retainAll(fksToIntersect.get(i).getValues());
			}
			intersected.setValues(intersectedValues);
		}

		return intersected;
	}

	public Connection getDbConnection() {
		return dbConnection;
	}

	public void setDbConnection(Connection dbConnection) {
		this.dbConnection = dbConnection;
	}

	public String getSqlQuery() {
		return sqlQuery;
	}

	public void setSqlQuery(String sqlQuery) {
		this.sqlQuery = sqlQuery;
	}

	public PreparedStatement getPreparedStatement() {
		return preparedStatement;
	}

	public void setPreparedStatement(PreparedStatement preparedStatement) {
		this.preparedStatement = preparedStatement;
	}

	public String getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}
}
