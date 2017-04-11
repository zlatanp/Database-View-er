package view;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import contoller.ILocalization;
import gui.MainFrame;
import model.TableFieldModel;
import model.TableModel1;
import model.TableRowModel;

/**
 * Klasa koja predstavlja prikaz tabele koja je otovrena u tabu i radnom delu. 
 * Sadrzi podatke o modelu.
 * 
 * @author Zlatan
 * @author Milena
 * @author Svetlana
 * @author Jasmina
 */
public class TableView1 extends JPanel implements ILocalization {

	private static final long serialVersionUID = 3939576385154700713L;

	/**
	 * Model tabele koja se prikazuje.
	 */
	private TableModel1 tableModel;
	
	/**
	 * Polje koje predstavlja JTable prikaz za tabelu.
	 */
	private JTable tabela;
	
	/**
	 * Lista {@link TableFieldModel modela} kolona za tabelu koja se prikazuje.
	 */
	private ArrayList<TableFieldModel> kolone;

	/**
	 * Lista {@link TableRowModel modela} redova za tabelu koja se prikazuje.
	 */
	private ArrayList<TableRowModel> redovi;
	
	/**
	 * Polje koje omogucava skrolovanje kroz prikaz.
	 */
	private JScrollPane skrol;
	
	/**
	 * Naziv prikaza tabele.
	 */
	private String naziv;

	/**
	 * Model tabele sa kolonama i redovima koji se trebaju prikazati.
	 */
	private DefaultTableModel model = null;

	@SuppressWarnings("serial")
	public TableView1(TableModel1 tableModel) {
		this.setLayout(new BorderLayout());
		this.tableModel = tableModel;

		this.kolone = new ArrayList<TableFieldModel>();		
		this.kolone = tableModel.getPolja(); 
		
		this.tabela = initTable(kolone);
		this.redovi = new ArrayList<TableRowModel>();
		this.redovi = tableModel.getRows();
		
		// novo
		model = new DefaultTableModel() {
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		for (TableFieldModel kolona : this.kolone) {
			model.addColumn(kolona.getLabel());
		}

		this.tabela = new JTable(model);

		this.redovi = popuniModelTabele(MainFrame.getInstance().getDbDataHandler().getAllFromTable(tableModel));
		for (int i = 0; i < redovi.size(); i++) {
			this.tableModel.addRow(redovi.get(i));
		}
		this.skrol = new JScrollPane(tabela);
		this.naziv = tableModel.getLabel();

		add(this.skrol);
	}

	public JScrollPane getSkrol() {
		return skrol;
	}

	public void setSkrol(JScrollPane skrol) {
		this.skrol = skrol;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public JTable getTabela() {
		return tabela;
	}

	public void setTabela(JTable tabela) {
		this.tabela = tabela;
	}

	public ArrayList<TableFieldModel> getKolone() {
		return kolone;
	}

	public void setKolone(ArrayList<TableFieldModel> kolone) {
		this.kolone = kolone;
	}

	public TableModel1 getTableModel() {
		return tableModel;
	}

	public void setTableModel(TableModel1 tableModel) {
		this.tableModel = tableModel;
	}

	/**
	 * Metoda koja inicijalizuje JTable prikaz.
	 * 
	 * @param fields
	 * 			- {@link ArrayList lista} {@link TableFieldModel modela} polja date tabele.
	 * @return
	 * 		- {@link JTable JTable} instanca tabele.
	 */
	private JTable initTable(ArrayList<TableFieldModel> fields) {
		JTable temp;
		Object[] columns = new Object[] {};
		for (int i = 0; i < fields.size(); i++) {
			columns = appendValue(columns, fields.get(i).getLabel());
		}
		model = new DefaultTableModel(columns, 0);
		temp = new JTable(model);
		return temp;
	}

	/**
	 * Metoda koja dodaje vrednosti u red.
	 * 
	 * @param inputValues
	 * 			- lista stringova vrednosti koje se unose.
	 */
	public void addRow(ArrayList<String> inputValues) {
		Object[] rowData = inputValues.toArray();
		model.addRow(rowData); // popunjavam view deo
	}

	/**
	 * Metoda koja brise vrednosti na odredjenoj poziciji.
	 * 
	 * @param inputValues
	 * 			- lista stringova vrednosti koje se brisu.
	 * @param index
	 * 			- pozicija na kojoj se menja.
	 */
	public void deleteRow(ArrayList<String> inputValues, int index) {
		model.removeRow(index); // brisem row iz view dela
	}

	/**
	 * Metoda koja menja odredjene vrednosti iz prikaza tabele.
	 * 
	 * @param index
	 * 			- pozicija promene.
	 * @param inputValues
	 * 			- vrednosti koje se menjaju.
	 */
	public void editRow(int index, ArrayList<String> inputValues) {
		model.removeRow(index);
		Object[] rowData = inputValues.toArray();
		model.insertRow(index, rowData);
	}

	/**
	 * Pomocna metoda
	 * @param obj
	 * @param newObj
	 * @return
	 */
	private Object[] appendValue(Object[] obj, Object newObj) {
		ArrayList<Object> temp = new ArrayList<Object>(Arrays.asList(obj));
		temp.add(newObj);
		return temp.toArray();
	}

	@Override
	public void translate() {

	}

	/**
	 * Metoda koja popunjava model tabele sa odredjenim redovima koji su procitani iz baze.
	 * 
	 * @param rows
	 * 		- {@link ArrayList lista} {@link TableRowModel modela} redova koji se citaju iz baze.
	 * @return
	 * 		- povratna lista.
	 */
	public ArrayList<TableRowModel> popuniModelTabele(ArrayList<TableRowModel> rows) {
		if (model.getRowCount() > 0) {
			for (int i = model.getRowCount() - 1; i >= 0; i--) {
				model.removeRow(i);
			}
		}
		for (TableRowModel tableRow : rows) {
			model.addRow(tableRow.getFieldValues().toArray());
		}

		return rows;
	}

}
