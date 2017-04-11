package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.JTextField;

import gui.MainFrame;
import gui.SmallTable;
import model.TableFieldModel;
import model.TableModel1;
import tree.components.TableComponent;
import view.TableView1;


/**
 * Klasa koja sluzi za izbor stranih kljuceva 
 * prilikom dodavanja novog reda ili izmenu postojeceg.
 * 
 * @author Milena
 * 
 */
public class OpenSmallTableListener implements ActionListener {
	private String kodTabele;
	private String kodKolone;
	private JTextField textField = null;
	@SuppressWarnings("unused")
	private ArrayList<JTextField> textFields;

	private int selektovaniRed = -1;
	private int indexKolone = -1;

	public OpenSmallTableListener(String kodTabele, String kodKolone, JTextField textField,
			ArrayList<JTextField> textFields) {
		this.kodTabele = kodTabele;
		this.kodKolone = kodKolone;
		this.textField = textField;
		this.textFields = textFields;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// otvori prozor sa novom tabelom
		ArrayList<TableComponent> allTables = MainFrame.getInstance().getJsonDataReader().getAllTables();
		TableModel1 tableModel1 = new TableModel1();

		if (kodTabele != "") {
			for (int i = 0; i < allTables.size(); i++) {
				if (allTables.get(i).getTableModel().getCode().equals(kodTabele)) {
					tableModel1 = allTables.get(i).getTableModel();
					break;
				}
			}

			TableView1 tableView = new TableView1(tableModel1);

			SmallTable dialogNoveTabele = new SmallTable(tableView);
			dialogNoveTabele.setVisible(true);

			// dobijam informaciju koji je red selektovan u novoj tabeli
			selektovaniRed = dialogNoveTabele.getIndex();
			JTable table = tableView.getTabela();

			// prodjmem kroz kolone naseljenog mesta
			// pitam da li je kod kolone jednak mom kodu kolone
			// ako jeste index=taj i
			indexKolone = 0;
			ArrayList<TableFieldModel> kolone = tableModel1.getPolja();
			for (int i = 0; i < kolone.size(); i++) {
				if (kolone.get(i).getCode().equals(kodKolone)) {
					table.getValueAt(selektovaniRed, indexKolone);
					StringBuilder sb = new StringBuilder();
					sb.append("");
					sb.append(table.getValueAt(selektovaniRed, indexKolone));
					String strI = sb.toString();
					textField.setText(strI);
					// textFields.add(textField);
					indexKolone = 0;
					break;
				}
				indexKolone++;
			}
		}

	}

	public int getSelektovaniRed() {
		return selektovaniRed;
	}

	public void setSelektovaniRed(int selektovaniRed) {
		this.selektovaniRed = selektovaniRed;
	}

	public JTextField getTextField() {
		return textField;
	}

	public void setTextField(JTextField textField) {
		this.textField = textField;
	}

}
