package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JTabbedPane;
import javax.swing.JToolBar;

import model.TableModel1;

/**
 * Klasa koja predstavlja zatvaranje taba roditeljske tabele koja ima i tabove u donjem delu.
 * 
 * @author Zlatan
 *
 */

public class CloseTabListenerHandlerSaDecom implements ActionListener {

	/**
	 * Polje koje predstavlja naziv taba koji se zatvara.
	 */
	private String tabName;
	
	/**
	 * Polja koja predstavljaju panele gornji-roditeljski i donji-za decu.
	 */
	private JTabbedPane roditeljiPane, decaPane;
	
	/**
	 * Polja koja predstavljaju liste naziva tabova koji su otvoreni u parent i child prikazu.
	 */
	private ArrayList<String> listaOtvorenihRoditelja, listaOtvoreneDece;
	
	/**
	 * Polja koja predstavljaju toolbarove za gornji i donji deo prikaza.
	 */
	private JToolBar parentToolbar, decaToolbar;
	
	/**
	 * Polje koje predstavlja model roditeljske tabele.
	 */
	private TableModel1 modelRoditelja;
	
	/**
	 * Lista svih modela tabela koje su otvorene.
	 */
	private ArrayList<TableModel1> temp;

	public CloseTabListenerHandlerSaDecom(String labela, JTabbedPane roditelji, JTabbedPane deca,
			ArrayList<String> listaOtvorenihRoditelja, ArrayList<String> listaOtvoreneDece, JToolBar topToolbar,
			JToolBar bottomToolbar, TableModel1 modelRoditelja, ArrayList<TableModel1> temp) {
		this.tabName = labela;
		this.roditeljiPane = roditelji;
		this.decaPane = deca;
		this.listaOtvorenihRoditelja = listaOtvorenihRoditelja;
		this.listaOtvoreneDece = listaOtvoreneDece;
		this.parentToolbar = topToolbar;
		this.decaToolbar = bottomToolbar;
		this.modelRoditelja = modelRoditelja;
		this.temp = temp;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		int indeksZatvorenogParenta = roditeljiPane.indexOfTab(tabName);

		if (indeksZatvorenogParenta >= 0) {
			roditeljiPane.removeTabAt(indeksZatvorenogParenta); // brisem
																// roditelja iz
																// view
			for (int z = 0; z < listaOtvorenihRoditelja.size(); z++) { // brisem
																		// roditelja
																		// iz
																		// liste
				if (listaOtvorenihRoditelja.get(z).equals(tabName)) {
					listaOtvorenihRoditelja.remove(z);
					break;
				}
			}

			///

			// listaOtvoreneDece.clear();//moraces pogledati, obrisati..

			ArrayList<String> pomocna = new ArrayList<String>(); // lista
																	// izbacenih
																	// childova

			ArrayList<TableModel1> list = new ArrayList<TableModel1>(modelRoditelja.getChildren().values()); // lista
																											// table
																											// modela
																											// deca
			for (TableModel1 model : list) { // deca za brisanje su
											// model.getLabel()
				for (int pp = 0; pp < decaPane.getTabCount(); pp++) {
					if (model.getLabel().equals(decaPane.getTitleAt(pp))) {
						decaPane.removeTabAt(pp);
						pomocna.add(listaOtvoreneDece.get(pp));
					}
				}
			}

			for (int r = 0; r < pomocna.size(); r++) {
				for (int rr = 0; rr < listaOtvoreneDece.size(); rr++) {
					if (listaOtvoreneDece.get(rr).equals((pomocna.get(r)))) {
						listaOtvoreneDece.remove(rr);
					}
				}
			}
			if (listaOtvorenihRoditelja.isEmpty()) {
				decaToolbar.setVisible(false);
			} else if (listaOtvoreneDece.isEmpty()) {
				decaToolbar.setVisible(false);
			}

			///
			if (roditeljiPane.getTabCount() == 0) {
				parentToolbar.setVisible(false);
			}
			
			for(int i=0;i<temp.size();i++){
				if(temp.get(i).getLabel().equals(tabName)){
					temp.remove(i);
				}
			}
			
		}

	}

}
