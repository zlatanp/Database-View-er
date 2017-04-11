package gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.jdatepicker.impl.UtilDateModel;

import data.reader.DBDataHandler;
import listeners.OpenListener;
import listeners.OpenSmallTableListener;
import model.TableFieldModel;
import model.TableModel1;
import model.TableRowModel;

import validation.ValidateForm;
import view.TableView1;


/**
 * Klasa koja sluzi za otvaranje dijaloga za dodavanje i izmenu reda u tabeli.
 * 
 * @author Milena
 * @author Svetlana
 * 
 * 
 * */

@SuppressWarnings("serial")
public class AddDialog extends JDialog {
	private String dialogTitle;
	@SuppressWarnings("unused")
	private boolean edit;

	/**
	 * Mapa kolona.
	 */
	private HashMap<String, TableFieldModel> fields = null;
	
	/**
	 * Mapa kolona bez kolona koje su primarni kljucevi.
	 */
	private HashMap<String, TableFieldModel> fieldsBezPK = null;

	private ArrayList<TableFieldModel> polja = null;

	private ArrayList<JTextField> textFields = null;
	private ArrayList<JTextField> pktextFields = null;

	private TableModel1 tableModel = null;
	private ArrayList<String> primarniKljucevi = null;
	private HashMap<String, String> straniKljucevi = null;

	private ArrayList<String> naziviKolona = null;
	private TableRowModel tableRowModel = null;
	private DBDataHandler daDbDataHandler = null;

	
	/**
	 * Za validaciju..
	 */
	private ValidateForm validateForm = null;

	public AddDialog(TableView1 tableView, boolean edit, int selectedIndex) {
		this.edit = edit;

		this.tableModel = tableView.getTableModel();
		this.fields = tableModel.getFields();
		this.primarniKljucevi = tableModel.getPrimaryKeys();
		this.daDbDataHandler = MainFrame.getInstance().getDbDataHandler();

		this.fieldsBezPK = new HashMap<String, TableFieldModel>(); // sadrzi
		// svekolone
		// bez
		// PK

		this.polja = tableModel.getPolja();

		this.tableRowModel = new TableRowModel();
		this.naziviKolona = tableModel.getFieldNames();
		this.straniKljucevi = new HashMap<String, String>();
		// nadji sve strane kljuceve selektovane tabele
		for (TableFieldModel value : fields.values()) {
			this.straniKljucevi.putAll(value.getForeignKeyConstraints());
		}

		// treba nam da bismo mogli da pokupimo unete vrednosti
		this.textFields = new ArrayList<JTextField>();
		this.pktextFields = new ArrayList<JTextField>();

		for (String code : fields.keySet()) {
			fieldsBezPK.put(code, fields.get(code));
		}

		if (!edit) {
			// kreiramo prozor
			dialogTitle = new String("Add row");
			this.setTitle(dialogTitle);

		} else {
			dialogTitle = new String("Edit row");
			this.setTitle(dialogTitle);
		}
		this.setSize(600, 300);
		this.setLayout(new GridLayout(0, 3));
		this.setLocationRelativeTo(MainFrame.getInstance());

		for (int h = 0; h < polja.size(); h++) {
			TableFieldModel fieldModel = polja.get(h);
			JLabel label = null;
			if (fieldModel.isPrimaryKey()) {
				label = new JLabel(fieldModel.getLabel() + "*");
				add(label);

				JTextField pkTextField = new JTextField();
				label.setLabelFor(pkTextField);

				if (fieldModel.getLenght() != -1) {
					pkTextField.setDocument(new LoginInputField(fieldModel.getLenght()));
				}

				if (edit) {
					if (selectedIndex != -1) {
						String vrednost = (String) tableView.getTabela().getValueAt(selectedIndex, h);
						pkTextField.setText(vrednost);
						pkTextField.setEditable(false);
					}
				}

				add(pkTextField);
				textFields.add(pkTextField);
				pktextFields.add(pkTextField);

				JLabel poruke = new JLabel();
				add(poruke);
			} else if (fieldModel.isForeignKey()) {
				label = new JLabel(fieldModel.getLabel());
				add(label);

				JTextField textField = new JTextField();
				label.setLabelFor(textField);

				if (fieldModel.getLenght() != -1) {
					textField.setDocument(new LoginInputField(fieldModel.getLenght()));
				}

				if (edit) {
					if (selectedIndex != -1) {
						for (int i = primarniKljucevi.size(); i < naziviKolona.size(); i++) {
							if (tableView.getTabela().getColumnName(i).equals(fieldModel.getLabel())) {
								String vrednost = (String) tableView.getTabela().getValueAt(selectedIndex, i);
								textField.setText(vrednost);
								break;
							}
						}

					}
				}

				add(textField);

				JButton button = new JButton("...");
				textField.setEditable(false);
				OpenSmallTableListener openSmallTableListener;
				for (String kodTabele : straniKljucevi.keySet()) {
					String kodKolone = straniKljucevi.get(kodTabele);
					if (fieldModel.getCode().contains(kodKolone)) {
						openSmallTableListener = new OpenSmallTableListener(kodTabele, kodKolone, textField,
								textFields);
						button.addActionListener(openSmallTableListener);
						textFields.add(openSmallTableListener.getTextField());
						break;
					}
				}
				add(button);

			} else if (fieldModel.getType().equals("image")) {
				label = new JLabel(fieldModel.getLabel());

				JTextField textField = new JTextField();
				label.setLabelFor(textField);
				textField.setEditable(false);

				if (edit) {
					if (selectedIndex != -1) {
						for (int i = primarniKljucevi.size(); i < naziviKolona.size(); i++) {
							if (tableView.getTabela().getColumnName(i).equals(fieldModel.getLabel())) {
								String vrednost = (String) tableView.getTabela().getValueAt(selectedIndex, i);
								textField.setText(vrednost);
								break;
							}
						}

					}
				}

				textFields.add(textField);

				add(label);
				add(textField);

				JButton openDialog = new JButton("Open");
				OpenListener openListener = new OpenListener(textField);
				openDialog.addActionListener(openListener);

				add(openDialog);
			} else if (fieldModel.getType().equals("datetime") && fieldModel.getType().equals("date")) {
				UtilDateModel model = new UtilDateModel();
				model.setDate(1990, 8, 24);
				model.setSelected(true);
				// add(model);
			} else {
				label = new JLabel(fieldModel.getLabel());

				JTextField textField = new JTextField();
				label.setLabelFor(textField);

				if (fieldModel.getLenght() != -1) {
					textField.setDocument(new LoginInputField(fieldModel.getLenght()));
				}
				JLabel poruke = new JLabel();

				if (edit) {
					if (selectedIndex != -1) {
						for (int i = primarniKljucevi.size(); i < naziviKolona.size(); i++) {
							if (tableView.getTabela().getColumnName(i).equals(fieldModel.getLabel())) {
								String vrednost = (String) tableView.getTabela().getValueAt(selectedIndex, i);
								textField.setText(vrednost);
								break;
							}
						}

					}
				}

				textFields.add(textField);

				add(label);
				add(textField);
				add(poruke);
			}
		}

		String potvrdiString = new String("Potvrdi");
		JButton potvrdi = new JButton(potvrdiString);
		validateForm = new ValidateForm(tableModel, tableRowModel, daDbDataHandler);
		potvrdi.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!edit) {
					boolean rezPKValidate = true;
					boolean rezValidate = true;
					for (int i = 0; i < pktextFields.size(); i++) {
						rezPKValidate = validateForm.correctPK(pktextFields.get(i).getText());
						if (rezPKValidate) {
							pktextFields.get(i).getText().toLowerCase();
							textFields.get(i).setText(pktextFields.get(i).getText());
							continue;
						} else {
							DialogUpozorenje dialogUpozorenje = new DialogUpozorenje("Vrednost vec postoji u bazi!");
							dialogUpozorenje.setVisible(true);
							break;
						}
					}

					for (int i = 1; i < textFields.size(); i++) {
						rezValidate = validateForm.correct(textFields.get(i).getText());
						if (!rezValidate) {
							textFields.get(i).setText("NULL");
							// rezValidate = true;
						}
					}

					if (rezPKValidate) {
						// UPIS U BAZU
						for (int i = 0; i < textFields.size(); i++) {
							// popuni rowModel
							tableRowModel.addPair(naziviKolona.get(i), textFields.get(i).getText());
						}

						tableView.getTableModel().addRow(tableRowModel); // dodaj
						// taj
						// red u
						// tableModel
						tableView.addRow(tableRowModel.getFieldValues()); // popuni
						// tableView

						boolean rezultatZaTest = daDbDataHandler.insert(tableModel, tableRowModel);
						setVisible(false);
					}
				} else {
					tableRowModel = tableModel.getRows().get(selectedIndex);

					ArrayList<String> uneteVrednosti = new ArrayList<String>();
					for (int i = 0; i < textFields.size(); i++) {
						// izmeni redove u tom modelu
						tableRowModel.editPair(polja.get(i).getLabel(), textFields.get(i).getText());

						// treba mi zbog view dela
						uneteVrednosti.add(textFields.get(i).getText());
					}
					tableView.editRow(selectedIndex, uneteVrednosti); // izmeni
																		// view

					// UPIS U BAZU
					daDbDataHandler.update(tableModel, tableRowModel);
					setVisible(false);
				}
			}
		});
		add(potvrdi);

		String otkaziString = new String("Otkazi");
		JButton otkazi = new JButton(otkaziString);
		otkazi.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);

			}
		});
		add(otkazi);

		setModal(true);
		setResizable(false);

	}
}
