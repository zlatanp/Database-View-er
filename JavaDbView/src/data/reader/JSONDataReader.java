package data.reader;

import java.io.File;

import java.io.IOException;

import java.util.ArrayList;

import java.util.Iterator;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import gui.MainFrame;
import model.TableFieldModel;
import model.TableModel1;
import tree.components.PackageComponent;
import tree.components.ProjectComponent;
import tree.components.TableComponent;

/**
 * Klasa koja enkapsulira citanje seme baze iz .json fajla.
 * 
 * @author Jasmina
 *
 */
public class JSONDataReader {

	/**
	 * Polje koje predstavlja fajl .json formata iz kojeg se cita sema.
	 */
	private File file;
	
	/**
	 * Polje koje predstavlja koren za citanje json strukture.
	 */
	private JsonNode root;

	/**
	 * Polje koje predstavlja listu svih procitanih tabela.
	 */
	private ArrayList<TableComponent> sveTabele = new ArrayList<TableComponent>();
	
	/**
	 * Polje koje predstavlja listu svih procitanih tabela.
	 */
	private ArrayList<TableComponent> allTables = new ArrayList<TableComponent>(); 

	/**
	 * Polje koje predstavlja listu cvorova tabela u stablu tabela koje imaju roditelja.
	 */
	private ArrayList<TableComponent> cvoroviSaRoditeljem = new ArrayList<TableComponent>();
	
	/**
	 * Polje koje predstavlja listu kodova tabela koje su nekome dete- koje imaju roditelja.
	 */
	private ArrayList<String> deca = new ArrayList<String>();
	
	/**
	 * Polje koje predstavlja listu svih cvorova tabela u stablu.
	 */
	private ArrayList<TableComponent> tabele = new ArrayList<TableComponent>();
	
	/**
	 * Polje koje predstavlja listu svih cvorova paketa u stablu.
	 */
	private ArrayList<PackageComponent> paketi = new ArrayList<PackageComponent>();

	public JSONDataReader(String fileName) {
		this.file = new File(fileName);
	}

	/**
	 * Metoda koja cita .json fajl.
	 * 
	 * @throws IOException
	 * 				- input/output exception moguc prilikom citanja fajla.
	 */
	public void parseJson() throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		root = null;
		try {
			root = mapper.readTree(file);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (root == null) {
			System.out.println("Root je null");
			return;
		}

		JsonNode dbviewer = root.path("MPSDrzavnaAdministracija");
		ProjectComponent projectComponent = new ProjectComponent("MPSDrzavnaAdministracija");
		MainFrame mainFrame = MainFrame.getInstance();
		((DefaultMutableTreeNode) mainFrame.getWorkspaceModel().getRoot()).add(projectComponent);

		fillTree(dbviewer, projectComponent);
		SwingUtilities.updateComponentTreeUI(mainFrame.getTree());
	}

	/**
	 * Pomocna metoda koja popunjava stablo.
	 * 
	 * @param jsonNode
	 * 				- {@link JsonNode cvor} u json fajlu odajle krece citanje.
 	 * @param treeNode
 	 * 				- {@link DefaultMutableTreeNode cvor} u stablu aplikacije od kog krece citanje.
	 */
	private void fillTree(JsonNode jsonNode, DefaultMutableTreeNode treeNode) {
		fillPackages(jsonNode.path("packages"), treeNode);
		fillTables(jsonNode.path("tables"), treeNode);
		allTables.addAll(sveTabele);
		sveTabele.clear(); // ocisti listu zbog duplikata
		cvoroviSaRoditeljem.clear(); // ocisti listu dece
	}

	/**
	 * Pomocna metoda koja se poziva rekurzivno radi popunjavanja svih paketa u stablu.
	 * 
	 * @param packages
	 * 				- {@link JsonNode cvor} u .json fajlu od kog krece citanje paketa.
	 * @param treeNode
	 * 				- {@link DefaultMutableTreeNode cvor} u stablu od kog krece citanje paketa.
	 */
	private void fillPackages(JsonNode packages, DefaultMutableTreeNode treeNode) {
		for (JsonNode pack : packages) {
			Iterator<JsonNode> elementsIterator = pack.elements();
			Iterator<String> namesIterator = pack.fieldNames();

			String namePackage = namesIterator.next();
			PackageComponent packageNode = new PackageComponent(namePackage);
			paketi.add(packageNode);
			treeNode.add(packageNode);
			JsonNode temp = elementsIterator.next();
			fillTree(temp, packageNode);
		}
	}

	/**
	 * Pomocna metoda koja se poziva rekurzivno i sluzi za popunjavanje svih tabela u stablu.
	 * 
	 * @param tables
	 * 			- {@link JsonNode cvor} u .json fajlu od kog krecu da se citaju tabele.
	 * @param treeNode
	 * 			- {@link DefaultMutableTreeNode cvor} u stablu od kog krecu da se upisuju tabele.
	 * 			  Ovaj cvor se zbog rekurzije menja.
	 */
	private void fillTables(JsonNode tables, DefaultMutableTreeNode treeNode) {
		TableComponent tableNode;

		// prolazak kroz tabele
		for (JsonNode table : tables) {
			Iterator<JsonNode> elementsIterator = table.elements();
			Iterator<JsonNode> tableField = null;

			String label = elementsIterator.next().asText();
			String code = elementsIterator.next().asText();
			TableModel1 modelTabele = new TableModel1(label, code);
			tableNode = new TableComponent(label);

			tableNode.setTableModel(modelTabele);
			tabele.add(tableNode);
			sveTabele.add(tableNode); // dodajem u listu cvorova svih tabela

			// prolazim kroz polja tabele
			String tableFieldLabel, tableFieldCode, tableFieldType;
			boolean allowsNull;
			for (JsonNode fields : elementsIterator.next()) {
				tableField = fields.elements();
				tableFieldLabel = tableField.next().asText();
				tableFieldCode = tableField.next().asText();
				tableFieldType = tableField.next().asText();
				allowsNull = tableField.next().asBoolean();
				TableFieldModel tableFieldModel = new TableFieldModel(tableFieldLabel, tableFieldCode, tableFieldType,
						allowsNull);
				// popunjavam strane kljuceve
				for (JsonNode fk : tableField.next()) {
					Iterator<JsonNode> fkTableFieldName = fk.elements();
					Iterator<String> fkTableName = fk.fieldNames();
					String codeTable = fkTableName.next();
					String codeColumn = fkTableFieldName.next().asText();
					tableFieldModel.addForeignKeyConstraint(codeTable, codeColumn); // dodajem
																					// u
																					// mapu
																					// za
																					// konkreto
																					// polje
																					// njegovo
																					// fk
																					// ogranicenje
					tableFieldModel.setForeignKey(true);
				}
				tableFieldModel.setCodeTable(tableNode.getTableModel().getCode());
				tableNode.getTableModel().addField(tableFieldModel); // dodajem
																		// polje
																		// u
																		// tabelu
				tableNode.getTableModel().dodajPolje(tableFieldModel);
			}
			// prolazak kroz decu tabele-> ovde ulazi ako NIJE "children" = []
			for (JsonNode children : elementsIterator.next()) {
				Iterator<JsonNode> childrenIterator = children.elements();
				String childCode = childrenIterator.next().asText();
				ArrayList<String> keys = new ArrayList<String>();
				for (JsonNode key : childrenIterator.next()) {
					keys.add(key.asText());
				}
				tableNode.getTableModel().getKodoviDece().add(childCode);
				tableNode.getTableModel().getChildrenList().put(childCode, keys);
			}
			// prolazi kroz listu dece koju si napunio i popuni odma decu
			for (String s : tableNode.getTableModel().getChildrenList().keySet()) {
				TableComponent dete = fillChildren(s, tables, tableNode);
				if (dete != null) {
					tableNode.add(dete);

					tableNode.getTableModel().addChild(dete.getTableModel());

					tableNode.getTableModel().addChild(dete.getTableModel());
					tableNode.getTableModel().setParent(true); // dodato da
																// jeste
																// roditelj

				}
			}
			// popuni primarne kljuceve
			for (JsonNode primaryKeys : elementsIterator.next()) {
				tableNode.getTableModel().getPrimaryKeys().add(primaryKeys.asText());

				for (int i = 0; i < tableNode.getTableModel().getPolja().size(); i++) {
					for (int j = 0; j < tableNode.getTableModel().getPrimaryKeys().size(); j++) {
						if (tableNode.getTableModel().getPolja().get(i).getCode()
								.equals(tableNode.getTableModel().getPrimaryKeys().get(j))) {
							tableNode.getTableModel().getPolja().get(i).setPrimaryKey(true);
						}
					}
				}

			}
			// ako u deci ne postoji ovakva tabela znaci da treba da je dodas->
			// nema parenta = nije u deci
			if (!deca.contains(tableNode.getTableModel().getCode())) {
				treeNode.add(tableNode);
			}
		}
	}


	/**
	 * Pomocna metoda koja se poziva prilikom nailaska na tabelu sa decom.
	 * Sluzi da popuni tabelu koja je dete, formira njen cvor u stablu i zakaci je za roditeljski cvor.
	 * 
	 * @param childCode
	 * 			- {@link String kod} tabele koja je child.
	 * @param allTables
	 * 			- {@link JsonNode cvor} u .json fajlu od kog se citaju sve tabele.
	 * @param parentCvor
	 * 			- {@link DefaultMutableTreeNode covr} u stablu aplikacije koji predstavlja roditeljski cvor.
	 * @return
	 * 		- {@link TableComponent cvor} u stablu koji predstavlja tabelu dete.
	 */
	private TableComponent fillChildren(String childCode, JsonNode allTables, DefaultMutableTreeNode parentCvor) {
		TableComponent parent = (TableComponent) parentCvor;
		for (JsonNode table : allTables) {

			Iterator<JsonNode> elementsIterator = table.elements();
			Iterator<JsonNode> tableField = null;
			String label = elementsIterator.next().asText();
			String code = elementsIterator.next().asText();

			// ako sam u svim tabalama naisla na tu koju trazim
			// od nje pravim cvor i kreiram njen model sa svim
			if (childCode.equals(code)) {
				TableComponent childCvor = new TableComponent(label);
				cvoroviSaRoditeljem.add(childCvor); // dodajem je u listu
													// cvorova sa roditeljima
				TableModel1 model = new TableModel1(label, code);
				childCvor.setTableModel(model);
				childCvor.getTableModel().setCode(code);
				childCvor.getTableModel().setLabel(label);

				TableModel1 parentTable = parent.getTableModel();
				childCvor.getTableModel().addParent(parentTable); // za svako
																	// dete
																	// dodaj ko
																	// mu
																	// je parent

				deca.add(childCvor.getTableModel().getCode()); // kod tabele u
																// listu dece->
																// stringovi

				// polja
				String tableFieldLabel, tableFieldCode, tableFieldType;
				boolean tableFieldIsRequired;
				for (JsonNode fields : elementsIterator.next()) {
					tableField = fields.elements();
					tableFieldLabel = tableField.next().asText();
					tableFieldCode = tableField.next().asText();
					tableFieldType = tableField.next().asText();
					tableFieldIsRequired = tableField.next().asBoolean();
					TableFieldModel tableFieldModel = new TableFieldModel(tableFieldLabel, tableFieldCode,
							tableFieldType, tableFieldIsRequired);
					// strani kljucevi
					for (JsonNode fk : tableField.next()) {
						Iterator<JsonNode> fkTableFieldName = fk.elements();
						Iterator<String> fkTableName = fk.fieldNames();
						String codeTable = fkTableName.next();
						String codeColumn = fkTableFieldName.next().asText();
						tableFieldModel.addForeignKeyConstraint(codeTable, codeColumn);

						tableFieldModel.setForeignKey(true);
					}
					tableFieldModel.setCodeTable(childCvor.getTableModel().getCode());
					childCvor.getTableModel().addField(tableFieldModel);
					childCvor.getTableModel().dodajPolje(tableFieldModel);
				}
				// deca dece -> mozda dete ima dete?
				for (JsonNode children : elementsIterator.next()) {
					Iterator<JsonNode> childrenIterator = children.elements();
					String childCode1 = childrenIterator.next().asText();
					ArrayList<String> keys = new ArrayList<String>();
					for (JsonNode key : childrenIterator.next()) {
						keys.add(key.asText());
					}
					childCvor.getTableModel().getKodoviDece().add(childCode1);
					childCvor.getTableModel().getChildrenList().put(childCode1, keys);

					// prodji kroz popunjenu listu tj mapu pa njene kljuceve
					// i pozivaj istu metodu sa pitanjem da li ima dece za to
					// dete
					for (String s : childCvor.getTableModel().getChildrenList().keySet()) {
						TableComponent dete = fillChildren(s, allTables, childCvor);
						if (dete != null) {
							childCvor.add(dete);
							childCvor.getTableModel().addChild(dete.getTableModel());
						}
					}
				}
				// primarni kljucevi deteta
				for (JsonNode primaryKeys : elementsIterator.next()) {
					childCvor.getTableModel().getPrimaryKeys().add(primaryKeys.asText());

					for (int i = 0; i < childCvor.getTableModel().getPolja().size(); i++) {
						for (int j = 0; j < childCvor.getTableModel().getPrimaryKeys().size(); j++) {
							if (childCvor.getTableModel().getPolja().get(i).getCode()
									.equals(childCvor.getTableModel().getPrimaryKeys().get(j))) {
								childCvor.getTableModel().getPolja().get(i).setPrimaryKey(true);
							}
						}
					}
				}
				return childCvor; // vrati dete

			}

		}
		return null; // nije nasao dete ide dalje
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public JsonNode getRoot() {
		return root;
	}

	public void setRoot(JsonNode root) {
		this.root = root;
	}

	public ArrayList<TableComponent> getAllTables() {
		return allTables;
	}

	public ArrayList<TableComponent> getTabele() {
		return tabele;
	}

	public void setTabele(ArrayList<TableComponent> tabele) {
		this.tabele = tabele;
	}

	public ArrayList<PackageComponent> getPaketi() {
		return paketi;
	}

	public void setPaketi(ArrayList<PackageComponent> paketi) {
		this.paketi = paketi;
	}
	
	

}
