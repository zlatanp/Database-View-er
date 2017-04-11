/**
 * 
 */
package tree.components;

import javax.swing.tree.DefaultMutableTreeNode;

import contoller.ILocalization;
import gui.MainFrame;
import model.TableModel1;

/**
 * Klasa koja ce predstavljati cvor tabelu u stablu aplikacije.
 * 
 * @author Jasmina
 *
 */
public class TableComponent extends DefaultMutableTreeNode implements ILocalization {

	private static final long serialVersionUID = -1891814274243448092L;
	
	/**
	 * Polje koje predstavlja naziv cvora koji je tabela.
	 */
	private String tableName;
	
	/**
	 * Polje koje predstavlja model tabele za odredjeni cvor.
	 */
	private TableModel1 tableModel;

	/**
	 * Polje koje predstavlja naziv paketa kojem tabela pripada.
	 */
	private String paketKomPripada;
	
	
	public TableComponent() {
		super();
		this.tableModel = new TableModel1();
	}

	public TableComponent(String tableName) {
		super(tableName);
		this.tableName = tableName;
		this.tableModel = new TableModel1();
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public TableModel1 getTableModel() {
		return tableModel;
	}

	public void setTableModel(TableModel1 tableModel) {
		this.tableModel = tableModel;
	}

	/**
	 * Metoda koja se poziva prilikom promene lokala.
	 */
	@Override
	public void translate() {
		if(MainFrame.getInstance().isSQLReader()){
			String kljuc = this.tableModel.getCode();
			this.setUserObject(MainFrame.getInstance().getResourceBundle().getString(kljuc));
		} else {
			this.setUserObject(MainFrame.getInstance().getResourceBundle().getString(tableName));
		}
	}

	public String getPaketKomPripada() {
		return paketKomPripada;
	}

	public void setPaketKomPripada(String paketKomPripada) {
		this.paketKomPripada = paketKomPripada;
	}

	
	
}
