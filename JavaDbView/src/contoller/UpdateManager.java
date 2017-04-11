package contoller;

import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import gui.MainFrame;
import model.TableModel1;
import tree.WorkspaceTree;
import tree.components.TableComponent;
import view.WorkspaceView;

/**
 * Klasa koja centralizuje komunikaciju izmedju GUI komponenti.
 * 
 * @author Jasmina
 *
 */
public class UpdateManager {

	

	/**
	 * Metoda koja za prosledjenu tabelu vraca listu njenih podredjenih child tabela.
	 * 
	 * @param parent
	 * 				- {@link TableModel1 model} table cija se deca(child) traze.
	 * @return
	 * 		- lista {@link TableModel1 modela tabela} koje su child - deca.
	 */
	@SuppressWarnings("rawtypes")
	public ArrayList<TableModel1> getChildrenForParent(TableModel1 parent) {
		ArrayList<TableModel1> childrenTables = new ArrayList<TableModel1>();
		Enumeration en = getTreeNodes();
		DefaultMutableTreeNode node;
		TableModel1 childTable;

		while (en.hasMoreElements()) {
			node = (DefaultMutableTreeNode) en.nextElement();
			if (node instanceof TableComponent) {

				childTable = ((TableComponent) node).getTableModel();
				for (String childCode : parent.getKodoviDece()) {
					if (childTable.getCode().equals(childCode)) {
						childrenTables.add(childTable);
					}
				}
			}
		}
		return childrenTables;
	}

	/**
	 * Pomocna metoda koja vraca sve cvorove iz stabla.
	 * 
	 * @return
	 * 		- enumeracija
	 */
	@SuppressWarnings("rawtypes")
	private Enumeration getTreeNodes() {
		DefaultMutableTreeNode root;
		root = (DefaultMutableTreeNode) MainFrame.getInstance().getTree().getModel().getRoot();

		Enumeration en = root.depthFirstEnumeration();

		return en;
	}

	/**
	 * Metoda koja sluzi za detekciju promene taba u prikazu nadredjenih tabela.
	 * @param parent
	 * 			- {@link TableModel1 model tabele} koja je trenutno aktivirana
	 */
	public void parentTabChanged(TableModel1 parent) {
		changeSelectionOnTree(parent.getLabel(), false);
	}
	

	/**
	 * Metoda koja sluzi za detekciju promene taba u prikazu podredjenih tabela.
	 * @param child
	 * 			- {@link TableModel1 model tabele} koja je trenutno aktivirana
	 */
	public void childTabChanged(TableModel1 child) {
		MainFrame.getInstance().getWorkspaceView().updateForBottomTabChanged(child);
	}
	
	
	/**
	 * Metoda koja poziva metodu za brisanje nadredjenih tabova iz {@link WorkspaceView radnog prostora}.
	 */
	public void brisiSveRoditelje() {
		MainFrame.getInstance().getWorkspaceView().ocistiParente();
	}

	
	
	/**
	 * Metoda koja sluzi za promenu prikaza u child delu.
	 * 
	 * @param parent
	 * 			- model tabele koja je u parent delu selektovana i aktivna.
	 */
	public void menjajDecuPreview(TableModel1 parent) {
		MainFrame.getInstance().getWorkspaceView().izmeniPrikazDece(parent);
	}
	
	

	/**
	 * Metoda koja registruje promene selekcije nad stablom.
	 * 
	 * @param tableName
	 * 		- naziv selektovane tabele.
	 * @param isLastTab
	 * 		-  flag koji govori da li se radi o poslednjem tabu.
	 */
	@SuppressWarnings("rawtypes")
	public void changeSelectionOnTree(String tableName, boolean isLastTab) {
		DefaultMutableTreeNode node;
		Enumeration en;
		TableModel1 tableModel;
		WorkspaceTree tree = MainFrame.getInstance().getTree();

		if (!isLastTab) {
			
			en = getTreeNodes();
			while (en.hasMoreElements()) {
				node = (DefaultMutableTreeNode) en.nextElement();
				if (node instanceof TableComponent) {
					tableModel = ((TableComponent) node).getTableModel();
					if (tableModel.getLabel().equals(tableName)) {
						tree.setSelectionPath(new TreePath(node.getPath()));
					}
				}
			}
		} else {
			// ako jeste poslednji tab zatvara se i selektuje cvor
			node = (DefaultMutableTreeNode) tree.getModel().getRoot();
			tree.setSelectionPath(new TreePath(node.getPath()));
		}
	}
	
	

	/**
	 * Metoda koja registruje zatvaranje taba u prikazu nadredjenih tabela.
	 * @param tableName
	 * 				- naziv taba koji je selektovan.
	 * @param isLastTab
	 * 				- da li je poslednji tab.
	 */
	public void parentTabClose(String tableName, boolean isLastTab) {
		changeSelectionOnTree(tableName, isLastTab);
	}
	
	

	/**
	 * Metoda koja sluzi za dobavljanje svih modela tabela iz stabla.
	 * @return
	 * 		- lista {@link TableModel1 modela} tabela.
	 */
	@SuppressWarnings("rawtypes")
	public ArrayList<TableModel1> getAllTableModels() {
		ArrayList<TableModel1> allTables;
		DefaultMutableTreeNode node;
		Enumeration en;
		TableModel1 model;

		allTables = new ArrayList<TableModel1>();
		en = getTreeNodes();

		while (en.hasMoreElements()) {
			node = (DefaultMutableTreeNode) en.nextElement();
			if (node instanceof TableComponent) {
				model = ((TableComponent) node).getTableModel();
				allTables.add(model);
			}
		}
		return allTables;
	}
	
	
	
	/**
	 * Metoda koja za prosledjeni kod tabele vraca model tabele koja poseduje taj kod.
	 * 
	 * @param tableCode - kod tabele u semi baze podataka.
	 * @return {@link model.TableModel1 Model} tabele ciji kod odgovara prosledjenom.
	 */
	@SuppressWarnings("rawtypes")
	public TableModel1 getModelForCode(String tableCode){		
		DefaultMutableTreeNode node;		
		Enumeration en;
		TableModel1 currentModel, model;

		en = getTreeNodes();
		model = null;
		
		while(en.hasMoreElements())
		{
			node = (DefaultMutableTreeNode) en.nextElement();
			if(node instanceof TableComponent)
			{
				currentModel = ((TableComponent) node).getTableModel();
				if(currentModel.getCode().equals(tableCode))
				{
					model = currentModel;
					break;
				}
			}
		}
		
		return model;
	}
}
