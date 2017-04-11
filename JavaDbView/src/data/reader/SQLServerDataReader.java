package data.reader;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import gui.DialogUpozorenje;
import gui.MainFrame;
import model.TableFieldModel;
import model.TableModel1;
import tree.components.PackageComponent;
import tree.components.TableComponent;

/**
 * Klasa koja enkapsulira citanje seme baze iz SQL servera
 * na zadati konekcioni string (ili defaultni).
 * 
 * @author Jasmina
 *
 */
public class SQLServerDataReader {

	/**
	 * Konekcija ka bazi
	 */
	private Connection connection;
	
	public SQLServerDataReader() {
	}
	
	/**
	 * Metoda koja namešta konekcioni string ili prosleđuje defaultni u zavisnosti
	 * od opcije koju korisnik odabere.
	 * 
	 * @return - true ili false ako je promena konekcionog stringa uspešna ili neuspešna.
	 */
	public boolean setConnectionString(){
		boolean success = false;
		Object[] options = {
			MainFrame.getInstance().getResourceBundle().getString("ReadSchemaOption0"), 
			MainFrame.getInstance().getResourceBundle().getString("ReadSchemaOption1"),
		};
		
		int code = JOptionPane.showOptionDialog(MainFrame.getInstance(), MainFrame.getInstance().getResourceBundle().getString("ReadSchemaSQLQuestion"),
				MainFrame.getInstance().getResourceBundle().getString("ReadSchemaSQL"), 
				JOptionPane.YES_NO_OPTION, 
				JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
		
		
		if(code == JOptionPane.NO_OPTION){
			
			connection = MainFrame.getConnection();
			success = true;
		
		}
		if(code == JOptionPane.YES_OPTION){
			
			String url = JOptionPane.showInputDialog(MainFrame.getInstance().getResourceBundle().getString("ReadSchemaInputUrl"));
			String username = JOptionPane.showInputDialog(MainFrame.getInstance().getResourceBundle().getString("ReadSchemaInputUsername"));
			String pass = JOptionPane.showInputDialog(MainFrame.getInstance().getResourceBundle().getString("ReadSchemaInputPassword"));	
			
			if(url == null || url.equals("") || username == null || username.equals("") || pass == null || pass.equals("")){
				success = false;
			}else if(!url.contains(".") || !url.contains("/")){				
				success = false;				
			}else if(username.length() != 15 || !username.contains("psw")){
				success = false;
			}else if(pass.length() != 13 || !pass.contains("tim")){
				success = false;
			}else {
				MainFrame.setNewConnection(url, username, pass);
				connection = MainFrame.getConnection();
				success = true;
			}
		}
		
		return success;
	}
	
	/**
	 * Metoda koja iscitava sve pakete.
	 * 
	 * @return - lista {@link PackageComponent cvorova} u stablu koji su paketi
	 */
	public ArrayList<PackageComponent> getAllPackages(){
		
		ArrayList<PackageComponent> retVal = new ArrayList<>();
		ArrayList<String> naziviPaketa = new ArrayList<>();
		ResultSet result;
		PreparedStatement preparedStatement;
		String sqlQuery = "SELECT * FROM TABELE";
		
		try {
			preparedStatement = connection.prepareStatement(sqlQuery);
			result = preparedStatement.executeQuery();
			while (result.next()) {
				String oznakaPaketa = result.getString("PO_OZNAKA");
				if(!naziviPaketa.contains(oznakaPaketa)){
					naziviPaketa.add(oznakaPaketa);
				}
			}
		} catch (SQLException e) {
			DialogUpozorenje upozorenje = new DialogUpozorenje();
			upozorenje.setVisible(true);
		}
		
		for(String s : naziviPaketa){
			PackageComponent paket = new PackageComponent(s);
			retVal.add(paket);
		}
		return retVal;		
	}
	
	/**
	 * Metoda koja za prosleđenu listu paketa traži tabele koje pripadaju tim paketima.
	 * 
	 * @param paketi 
	 * 				- lista {@link PackageComponent cvorova} koji su paketi
	 * @return
	 * 		- lista {@link TableComponent cvorova} tabela
	 */
	public ArrayList<TableComponent> getAllTables(ArrayList<PackageComponent> paketi){
		ArrayList<String> naziviPaketa = new ArrayList<>();
		ArrayList<TableComponent> tabele = new ArrayList<>();
		PreparedStatement preparedStatement;
		ResultSet result;
		
		for(PackageComponent paket : paketi){
			naziviPaketa.add(paket.getPackageName());
		}
		
		//SELECT * FROM TABELE WHERE PO_OZNAKA = '01_TERITORIJALNO_POLITICKA_ORGANIZACIJA'
		for(String s : naziviPaketa){
			String sqlQuery = "SELECT * FROM TABELE WHERE PO_OZNAKA= '" + s + "'";
			try {
				preparedStatement = connection.prepareStatement(sqlQuery);
				result = preparedStatement.executeQuery();
				while(result.next()){
					String tabCode = result.getString("TAB_KOD");
					String tabNaziv = result.getString("TAB_NAZIV");
					TableComponent tableComponent = new TableComponent();
					tableComponent.setTableName(tabNaziv);
					tableComponent.setPaketKomPripada(s);
					TableModel1 model = new TableModel1(tabNaziv, tabCode);
					model.setPomocniNaziv(tabNaziv);
					tableComponent.setTableModel(model);
					tabele.add(tableComponent);
				}
			} catch (SQLException e) {
				DialogUpozorenje upozorenje = new DialogUpozorenje();
				upozorenje.setVisible(true);
			}
		}
		
		return tabele;
	}
	
	/**
	 * Metoda koja za prosledjeni cvor tabelu vraca sve kolone iz te tabele
	 * i postavlja ih u okvir modela tabele.
	 * 
	 * @param tableCvor
	 *             - {@linkplain TableComponent cvor} tabela
	 * @return
	 * 		- lista {@link TableFieldModel polja} za prosledjenu tabelu
	 */
	public ArrayList<TableFieldModel> getAtributi(TableComponent tableCvor){
		ArrayList<TableFieldModel> retVal = new ArrayList<>();
		TableModel1 tableModel = tableCvor.getTableModel();
		String sqlQuery = "SELECT * FROM ATRIBUTI WHERE TAB_KOD= '" + tableModel.getCode() + "'";
		PreparedStatement preparedStatement;
		ResultSet result;
		
		try{
			preparedStatement = connection.prepareStatement(sqlQuery);
			result = preparedStatement.executeQuery();
			while(result.next()){
				String atrKod = result.getString("ATR_KOD");
				String atrLabela = result.getString("ATR_LABELA");
				String tip = result.getString("ATR_TIP");
				boolean obavezno = result.getBoolean("ATR_OBAVEZNO");
				int duzina = result.getInt("ATR_DUZINA");
				int preciznost = result.getInt("ATR_PRECIZNOST");
				boolean jesteUPK = result.getBoolean("ATR_DEO_PK");
				TableFieldModel polje = new TableFieldModel(atrLabela, atrKod, tip, obavezno);
				polje.setJesteDeoPK(jesteUPK);
				if(duzina != 0){
					polje.setLenght(duzina);
				}
				if(preciznost != 0){
					polje.setDecimalPlaces(preciznost);
				}
				retVal.add(polje);
			}
		} catch (SQLException e) {
			DialogUpozorenje upozorenje = new DialogUpozorenje();
			upozorenje.setVisible(true);
		}
		
		for (TableFieldModel tableFieldModel : retVal) {
			tableFieldModel.setCodeTable(tableModel.getCode());
			tableModel.addField(tableFieldModel);
			tableModel.dodajPolje(tableFieldModel);
		}
		
		return retVal;
	}
	
	/**
	 * Metoda koja cita primarne kljuceve za prosledjenu tabelu odnosno njen cvor u stablu.
	 * @param cvor
	 * 			- {@link TableComponent cvor} tabele u stablu.
	 * @return
	 *  	 - lista {@link java.lang.String stringova} koji su kodovi kolona koje su deo primarnog kljuca.
	 */
	public ArrayList<String> getPKForTable(TableComponent cvor){
		ArrayList<String> retVal = new ArrayList<>();
		TableModel1 model = cvor.getTableModel();
		String sqlQuery = "SELECT ATR_KOD, ATR_DEO_PK FROM ATRIBUTI WHERE TAB_KOD= '" + model.getCode() + "'";
		ResultSet result;
		PreparedStatement preparedStatement;
		
		try{
			preparedStatement = connection.prepareStatement(sqlQuery);
			result = preparedStatement.executeQuery();
			while(result.next()){
				String atrKod = result.getString("ATR_KOD");
				boolean isPk = result.getBoolean("ATR_DEO_PK");
				if(isPk){
					retVal.add(atrKod); // dodaj kod u listu i posle ovu listu zakaci kao listu PK
					for(TableFieldModel polje : model.getPolja()){
						if(polje.getCode().equals(atrKod)){
							polje.setJesteDeoPK(true);
							polje.setPrimaryKey(true);
						}
					}
				}
			}
			
		} catch (Exception e){
			DialogUpozorenje upozorenje = new DialogUpozorenje();
			upozorenje.setVisible(true);
		}
		return retVal;
	}
	
	/**
	 * Metoda koja cita decu tabele za prosledjeni cvor iz stabla.
	 * 
	 * @param cvor
	 * 			- {@link TableComponent cvor}tabele iz stabla cija se deca traze.
	 * @return
	 * 		 - lista {@link java.lang.String stringova} kodova tabele koje su deca za prosledjenu tabelu.
	 */
	public ArrayList<String> getDeca(TableComponent cvor){
		ArrayList<String> deca = new ArrayList<>(); // lista stringova-kodova tabela koja su deca za prosledjeni cvor
		TableModel1 model = cvor.getTableModel();
		String sqlQuery = "SELECT TAB_TAB_KOD, SK_JAKA_VEZA, SK_PRIKAZ_U_HIJERARHIJI FROM STRANI_KLJUC WHERE TAB_KOD= '" + model.getCode() + "'";
		ResultSet result;
		PreparedStatement preparedStatement;
		
		try{
			preparedStatement = connection.prepareStatement(sqlQuery);
			result = preparedStatement.executeQuery();
			while(result.next()){
				String kodPotomakTabele = result.getString("TAB_TAB_KOD");
				boolean jakaVeza = result.getBoolean("SK_JAKA_VEZA");
				boolean prikazUHij = result.getBoolean("SK_JAKA_VEZA");
				if(jakaVeza && prikazUHij){
					deca.add(kodPotomakTabele);
					model.addKodDeteta(kodPotomakTabele);
				}
				
			}
		} catch (SQLException e){
			DialogUpozorenje upozorenje = new DialogUpozorenje();
			upozorenje.setVisible(true);
		}
		
		
		return deca;
	}
	
	/**
	 * Metoda koja cita strane kljuceve za prosledjeni cvor tabelu iz stabla.
	 * 
	 * @param cvor
	 * 			- {@link TableComponent cvor} tabela ciji se strani kljucevi traze
	 */
	public void setFKs(TableComponent cvor){
		TableModel1 model = cvor.getTableModel();
		String sqlQuery = "SELECT TAB_KOD, ATR_KOD, ATR_ATR_KOD FROM KOLONE_U_STRANOM_KLJUCU WHERE ATR_TAB_KOD = '" + model.getCode() + "'";
		ResultSet result;
		PreparedStatement preparedStatement;
		
		try{
			preparedStatement = connection.prepareStatement(sqlQuery);
			result = preparedStatement.executeQuery();
			while(result.next()){
				String odakleJeKljuc = result.getString("TAB_KOD");  // ovo je tabela u kojoj je strani kljuc
				String naKojuKolonu = result.getString("ATR_KOD");  // ovo je kolona koju upisujes u fk
				String kolonaKojaJeFK = result.getString("ATR_ATR_KOD");
				for(TableFieldModel polje : model.getPolja()){
					if(polje.getCode().equals(kolonaKojaJeFK)){
						polje.setForeignKey(true);
						polje.addForeignKeyConstraint(odakleJeKljuc, naKojuKolonu);
					}
				}
				
			}
		} catch (Exception e){
			DialogUpozorenje upozorenje = new DialogUpozorenje();
			upozorenje.setVisible(true);
		}
		
	}
	
	/**
	 * Dodatna metoda koja popunjava paket cije su sve tabele zbog parent-child hijerarhije prebacene u neki drugi paket.
	 * 
	 * @return
	 * 		- lista kodova tabela koje su nedostajajuce u paketu 02_MATERIJALNI_RESURSI.
	 */
	public ArrayList<String> namestiZaMaterijalneRes(){
		String sqlQuery = "SELECT * FROM TABELE WHERE PO_OZNAKA = '02_MATERIJALNI_RESURSI'";
		ResultSet result;
		PreparedStatement preparedStatement;
		ArrayList<String> pomocna = new ArrayList<>();
		
		
		try {
			preparedStatement = connection.prepareStatement(sqlQuery);
			result = preparedStatement.executeQuery();
			while (result.next()) {
				String tabelaKod = result.getString("TAB_KOD");
				pomocna.add(tabelaKod);
			}
		} catch (SQLException e) {
			DialogUpozorenje upozorenje = new DialogUpozorenje();
			upozorenje.setVisible(true);
		}
		
		return pomocna;
	}
}
