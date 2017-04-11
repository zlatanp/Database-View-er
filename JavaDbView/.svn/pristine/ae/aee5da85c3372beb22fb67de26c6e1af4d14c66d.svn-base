package model;

import java.util.HashMap;

import javax.swing.JTabbedPane;

/**
 * Klasa koja predstavlja sadrzaj taba, unutar kog se nalazi mapa tabela, koje
 * su otvorene tabovima.
 */

public class TabbedPaneModel extends JTabbedPane {

	private static final long serialVersionUID = -3787126140518591425L;
	private HashMap<String, TableModel1> tabovi;

	public TabbedPaneModel() {
		super();
		tabovi = new HashMap<String, TableModel1>();

	}

	public void dodajUMapu(String i, TableModel1 model) {
		this.tabovi.put(i, model);
	}

	public HashMap<String, TableModel1> getTabovi() {
		return tabovi;
	}

	public void setTabovi(HashMap<String, TableModel1> tabovi) {
		this.tabovi = tabovi;
	}

}
