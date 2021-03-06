package actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.tree.DefaultMutableTreeNode;

import data.reader.SQLServerDataReader;
import gui.MainFrame;
import model.TableFieldModel;
import model.TableModel1;
import tree.WorkspaceModel;
import tree.components.PackageComponent;
import tree.components.ProjectComponent;
import tree.components.TableComponent;
import view.WorkspaceView;

/**
 * Klasa koja enkapsulira akciju za citanje seme baze preko SQL servera.
 * 
 * @author Jasmina
 *
 */
public class ReadFromSQLServerAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4536632852219309943L;

	public ReadFromSQLServerAction() {
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.CTRL_MASK));
		putValue(MNEMONIC_KEY, KeyEvent.VK_R);
		putValue(SMALL_ICON, new ImageIcon("images/tree.png"));
		putValue(NAME, MainFrame.getInstance().getResourceBundle().getString("ReadSchemaSQL"));
		putValue(SHORT_DESCRIPTION, MainFrame.getInstance().getResourceBundle().getString("ReadSchemaSQL"));
	}

	@SuppressWarnings("unchecked")
	@Override
	public void actionPerformed(ActionEvent e) {

		// vec aktivirano
		if(MainFrame.getInstance().isSQLReader()){
			JOptionPane.showMessageDialog(MainFrame.getInstance(), 
					MainFrame.getInstance().getResourceBundle().getString("UpozorenjePoruka"),
					MainFrame.getInstance().getResourceBundle().getString("UpozorenjeNaslov"), 
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		SQLServerDataReader reader = new SQLServerDataReader();
		boolean result = reader.setConnectionString();

		// kako je namestio string
		// uspesno
		if(result){
			MainFrame.getInstance().setSQLReader(true);
	
			WorkspaceModel model = MainFrame.getInstance().getWorkspaceModel();
			DefaultMutableTreeNode root = (DefaultMutableTreeNode) MainFrame.getInstance().getWorkspaceModel().getRoot();
			root.removeAllChildren();
			model.reload();
	
			// skloni sve iz view dela ako je nesto bilo
			WorkspaceView view = MainFrame.getInstance().getWorkspaceView();
			if(view.getTabbedParentView().getTabCount() != 0){
				view.getToolbarTop().setVisible(false);
				view.getToolbarBottom().setVisible(false);
				view.getTabbedParentView().removeAll();
				view.getTabbedChildView().removeAll();
			}
	
			// namesti pakete
			ArrayList<PackageComponent> paketi = reader.getAllPackages();
	
			// namesti projekat
			ProjectComponent projectComponent = new ProjectComponent("MPSDrzavnaAdministracija");
			((DefaultMutableTreeNode) MainFrame.getInstance().getWorkspaceModel().getRoot()).add(projectComponent);
	
			// dodaj pakete u stablo
			Enumeration<DefaultMutableTreeNode> en = root.depthFirstEnumeration();
			while (en.hasMoreElements()) {
				DefaultMutableTreeNode node = en.nextElement();
				if (node instanceof ProjectComponent) {
					for (PackageComponent pack : paketi) {
						node.add(pack);
					}
				}
			}
	
			// tabele
			ArrayList<TableComponent> tableComponents = reader.getAllTables(paketi);
	
			// dodaj tabele u stablo
			en = root.depthFirstEnumeration();
			while (en.hasMoreElements()) {
				DefaultMutableTreeNode node = en.nextElement();
				if (node instanceof PackageComponent) {
					for (TableComponent table : tableComponents) {
						PackageComponent paket = (PackageComponent) node;
						if (table.getPaketKomPripada().equals(paket.getPackageName())) {
							node.add(table);
						}
					}
				}
			}
			
			// kolone
			for (TableComponent tableCvor : tableComponents) {
				TableModel1 model1 = tableCvor.getTableModel();
				ArrayList<TableFieldModel> poljaTabele = reader.getAtributi(tableCvor);
				model1.setPolja(poljaTabele);
				ArrayList<String> primarniKljuceviUTabeli = reader.getPKForTable(tableCvor);
				model1.setPrimaryKeys(primarniKljuceviUTabeli);
			}
			
			// deca
			for (TableComponent tableComponent : tableComponents) {
				ArrayList<String> kodoviDece = reader.getDeca(tableComponent);
				for(TableComponent pom : tableComponents){
					TableModel1 modelPom = pom.getTableModel();
					String kodPom = modelPom.getCode();
					if(kodoviDece.contains(kodPom)){
						tableComponent.add(pom);
						tableComponent.getTableModel().setParent(true);
						tableComponent.getTableModel().getChildren().put(kodPom, modelPom);
						modelPom.getParents().put(tableComponent.getTableModel().getCode(), tableComponent.getTableModel());
					}
				}
			}
			
			// strani kljucevi
			for(TableComponent tableComponent : tableComponents){
				reader.setFKs(tableComponent);
			}
			
			for(TableComponent cvorTabele : tableComponents){
				ArrayList<String> kodoviDece = cvorTabele.getTableModel().getKodoviDece();
				ArrayList<String> listaKodovaPodCvorova = new ArrayList<>();
				if(cvorTabele.getChildCount() != kodoviDece.size()){
					
					for(int i=0; i<cvorTabele.getChildCount(); i++){
						DefaultMutableTreeNode deteCvor = (DefaultMutableTreeNode) cvorTabele.getChildAt(i);
						TableModel1 modelDete = ((TableComponent)deteCvor).getTableModel();
						listaKodovaPodCvorova.add(modelDete.getCode());
					}
				}

				Set<String> kodoviDeceJedinstveni = new HashSet<>(kodoviDece);
				for(String s : kodoviDeceJedinstveni){
					if(!listaKodovaPodCvorova.contains(s)){
						
						// nadji cvor medju svim cvorovima sa tim nazivom i dodaj ga
						nadjiCvorIDodaj(s, cvorTabele, tableComponents);
					}
				}
				
			}
			
			ocistiDuplikate(tableComponents);
			
			namestiKojeNedostaju(reader, tableComponents);
			
			// prevedi
			MainFrame.getInstance().getTree().translate();
			
			
		} else {
			JOptionPane.showMessageDialog(MainFrame.getInstance(), 
					MainFrame.getInstance().getResourceBundle().getString("UpozorenjeUnosStringa"),
					MainFrame.getInstance().getResourceBundle().getString("UpozorenjeNaslov"), 
					JOptionPane.WARNING_MESSAGE);
		}
	}

	/**
	 * Pomocna metoda koja za sve tabele pronalazi decu koja su potencijalno premestena u druge pakete.
	 * @param reader
	 * 			- {@link SQLServerDataReader citac} seme baze.
	 * @param tabeleCvorovi
	 * 			- lista svih procitanih tabela odnosno njihovih cvorova u stablu.
	 */
	@SuppressWarnings("unchecked")
	private void namestiKojeNedostaju(SQLServerDataReader reader,  ArrayList<TableComponent> tabeleCvorovi) {
		ArrayList<TableComponent> kopija = new ArrayList<>(tabeleCvorovi);
		ArrayList<String> kodoviTabela = reader.namestiZaMaterijalneRes();
		
		DefaultMutableTreeNode root = (DefaultMutableTreeNode) MainFrame.getInstance().getWorkspaceModel().getRoot();
		
		Enumeration<DefaultMutableTreeNode> en = root.depthFirstEnumeration();
		
		while (en.hasMoreElements()) {
			DefaultMutableTreeNode node = en.nextElement();
			if (node instanceof PackageComponent) {
				PackageComponent pak = (PackageComponent)node;
				if(pak.getPackageName().equals("02_MATERIJALNI_RESURSI")){
					for(TableComponent tc : kopija){
						if(kodoviTabela.contains(tc.getTableModel().getCode())){
							//taj tc komponent treba dodati za paket 
							pak.add(tc);
						}
					}
				}
			}
		}
		
		
	}

	/**
	 * Metoda koja za pronalazi cvor koji nedostaje kao child i dodaje ga gde treba.
	 * 
	 * @param s
	 * 			- kod cvora koji nedostaje.
	 * @param cvorTabele
	 * 		   - cvor parent tabele za koju se nadjeni treba dodati.
	 * @param tableComponents
	 * 			- lista svih cvorova tabela iz stabla.
	 */
	private void nadjiCvorIDodaj(String s, TableComponent cvorTabele, ArrayList<TableComponent> tableComponents) {
		
		for(TableComponent t : tableComponents){
			if(t.getTableModel().getCode().equals(s)){
				TableComponent novi = new TableComponent();
				novi.setPaketKomPripada(t.getPaketKomPripada());
				novi.setTableModel(t.getTableModel());
				novi.setTableName(t.getTableName());
				cvorTabele.add(novi);
			}
		}
	}
	
	/**
	 * Pomocna metoda koja cisti duplirane tabele iz stabla.
	 * 
	 * @param tableComponents
	 * 					- lista svi cvorova tabela u stablu.
	 */
	private void ocistiDuplikate(ArrayList<TableComponent> tableComponents){
		
		ArrayList<TableComponent> duplikat = new ArrayList<>(tableComponents);
		
		for(int z = 0; z < duplikat.size(); z++){
			for(int i=0; i<duplikat.get(z).getChildCount()-1; i++){
				for(int j=i+1; j<duplikat.get(z).getChildCount(); j++){
					DefaultMutableTreeNode dete = (DefaultMutableTreeNode)duplikat.get(z).getChildAt(i);
					DefaultMutableTreeNode dete2 = (DefaultMutableTreeNode)duplikat.get(z).getChildAt(j);
					TableComponent deteTabela = (TableComponent)dete;
					TableComponent deteTabela2 = (TableComponent)dete2;
					if(deteTabela.getTableModel().getCode().equals(deteTabela2.getTableModel().getCode())){
						tableComponents.get(z).remove(j);
					}
				
				}
				
			}
		}
	}

}
