package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;

import contoller.ILocalization;
import contoller.IUpdateable;
import gui.ToolBarChild;
import gui.ToolBarParent;
import listeners.CloseTabListenerHandler;
import listeners.CloseTabListenerHandlerSaDecom;
import listeners.TabChangedListener;
import model.TabbedPaneModel;
import model.TableModel1;

/**
 * Klasa koja predstavlja prikaz radnog dela.
 * 
 * @author Milena
 * @author Zlatan
 */


public class WorkspaceView extends JPanel implements IUpdateable, ILocalization {

	private static final long serialVersionUID = 4504460464097510536L;

	/**
	 * Polja koja modeluju gornji panel- roditeljski i donji - za decu.
	 */
	private JPanel workspaceTop, workspaceBottom;

	/**
	 * Polje za toolbar u roditeljskom delu prikaza.
	 */
	private ToolBarParent toolbarTop;
	
	/**
	 * Polje za toolbar u child delu prikaza.
	 */
	private ToolBarChild toolbarBottom;

	/**
	 * Panel sa tabovima za parent prikaz.
	 */
	private JTabbedPane tabbedParentView;
	
	/**
	 * Panel sa tabovima za child prikaz.
	 */
	private JTabbedPane tabbedChildView;

	/**
	 * Lista stringova otvorenih tabova u parent delu.
	 */
	private ArrayList<String> openTabsRoditelji;
	
	/**
	 * Lista stringova otvorenih tabova u child delu.
	 */
	private ArrayList<String> openTabsDeca;

	/**
	 * Lista stringova  dece za odredjenu tabelu.
	 */
	private ArrayList<String> listaImenaDece;

	/**
	 * Lista prikaza tabela u roditeljskom delu.
	 */
	private ArrayList<TableView1> tableViewList;
	
	/**
	 * Lista prikaza tabela u child delu.
	 */
	private ArrayList<TableView1> tableViewChildList;

	/**
	 * Magicna lista svih otvorenih modela tabela.
	 */
	private ArrayList<TableModel1> temp;

	public WorkspaceView() {
		initialiseParts();
		initialiseWorkspace();
	}

	/**
	 * Pomocna metoda koja inicijalizuje sve neophodne delove za prikaz radnog prostora.
	 */
	private void initialiseParts() {
		workspaceTop = new JPanel();
		workspaceBottom = new JPanel();

		toolbarTop = new ToolBarParent();
		toolbarBottom = new ToolBarChild();
		tabbedParentView = new JTabbedPane();
		tabbedParentView.addChangeListener(new TabChangedListener());
		tabbedChildView = new JTabbedPane();
		openTabsRoditelji = new ArrayList<String>();
		openTabsDeca = new ArrayList<String>();
		listaImenaDece = new ArrayList<String>();
		
		tableViewList = new ArrayList<TableView1>();
		tableViewChildList = new ArrayList<TableView1>();

		temp = new ArrayList<TableModel1>();

	}

	/**
	 * Pomocna metoda koja inicijalizuje GUI deo radnog prostora.
	 */
	public void initialiseWorkspace() {

		this.setLayout(new GridLayout(2, 1));
		// gornji panel za parent tabele
		workspaceTop.setLayout(new BorderLayout());
		workspaceTop.add(toolbarTop, BorderLayout.NORTH);
		workspaceTop.add(tabbedParentView);
		toolbarTop.setVisible(false);
		add(workspaceTop);

		// donji panel za child tabele
		workspaceBottom.setLayout(new BorderLayout());
		workspaceBottom.add(toolbarBottom, BorderLayout.NORTH);
		workspaceBottom.add(tabbedChildView);
		toolbarBottom.setVisible(false);
		add(workspaceBottom);

	}

	@Override
	public void updateForTopTabChanged(TableModel1 parentTable) {

	}

	@Override
	public void updateForBottomTabChanged(TableModel1 childTable) {

	}

	/**
	 * Metoda koja se aktivira prilikom promene selekcije na stablu.
	 */
	@Override
	public void updateForTreeChange(TableModel1 parent, HashMap<String, TableModel1> children) {
		if (temp.isEmpty()) {
			temp.add(parent);
		} else {
			for (int e = 0; e < temp.size(); e++) {
				if (temp.get(e).getCode().equals(parent.getCode())) {
					break;
				} else if (e == temp.size() - 1) {
					temp.add(parent);
				}
			}
		}
		listaImenaDece.clear();
		TableView1 roditelj = new TableView1(parent);
		tableViewList.add(roditelj);

		if (children.size() == 0) {

			this.remove(workspaceBottom);
			this.remove(workspaceTop);
			this.setLayout(new GridLayout(1, 1));
			add(workspaceTop);
			toolbarTop.setVisible(true);

			if (openTabsRoditelji.isEmpty()) {
				openTabsRoditelji.add(parent.getLabel());
				toolbarBottom.setVisible(true);
				tabbedParentView.addTab(parent.getLabel(), roditelj);
			} else {
				for (int i = 0; i < openTabsRoditelji.size(); i++) {
					if (openTabsRoditelji.get(i).equals(parent.getLabel())) {

						break;
					} else if (i == openTabsRoditelji.size() - 1) {
						openTabsRoditelji.add(parent.getLabel());
						toolbarBottom.setVisible(true);
						tabbedParentView.addTab(parent.getLabel(), roditelj);

						break;
					}
				}

			}

			int index = tabbedParentView.indexOfTab(roditelj.getNaziv());
			JPanel dugme = new JPanel();
			dugme = makeButton(roditelj.getNaziv(), tabbedParentView, openTabsRoditelji, toolbarTop, temp);

			tabbedParentView.setTabComponentAt(index, dugme);

			// ako ima dece
		} else {

			this.setLayout(new GridLayout(2, 1));
			add(workspaceTop);
			add(workspaceBottom);
			toolbarTop.setVisible(true);
			toolbarBottom.setVisible(true);

			if (openTabsRoditelji.isEmpty()) {
				openTabsRoditelji.add(parent.getLabel());
				toolbarBottom.setVisible(true);
				tabbedParentView.addTab(parent.getLabel(), roditelj);
				// dodao roditelja, dodaj sad decu

				tabbedChildView.removeAll();
				openTabsDeca.clear();

				for (TableModel1 m : children.values()) {
					TableView1 dete = new TableView1(m);
					openTabsDeca.add(m.getLabel());
					tabbedChildView.addTab(m.getLabel(), dete);

					this.tableViewChildList.add(dete);
				}

			} else {

				// ako vec ima otvorena neka tabela
				for (int i = 0; i < openTabsRoditelji.size(); i++) {
					if (openTabsRoditelji.get(i).equals(parent.getLabel())) {

						break;
					} else if (i == openTabsRoditelji.size() - 1) {
						openTabsRoditelji.add(parent.getLabel());
						toolbarBottom.setVisible(true);
						tabbedParentView.addTab(parent.getLabel(), roditelj);

						tabbedChildView.removeAll();
						openTabsDeca.clear();
						// deca
						for (TableModel1 m : children.values()) {
							TableView1 dete = new TableView1(m);
							openTabsDeca.add(m.getLabel());
							tabbedChildView.addTab(m.getLabel(), dete);
							
							this.tableViewChildList.add(dete);
						}
						break;
					}
				}

			}

			// dodaj u tab x
			int index = tabbedParentView.indexOfTab(roditelj.getNaziv());
			JPanel dugme = new JPanel();
			dugme = makeButtonSaDecom(roditelj.getNaziv(), tabbedParentView, tabbedChildView, openTabsRoditelji,
					openTabsDeca, toolbarTop, toolbarBottom, roditelj.getTableModel(), temp);

			tabbedParentView.setTabComponentAt(index, dugme);

		}

		// fokus
		for (int i = 0; i < tabbedParentView.getTabCount(); i++) {
			String tabName = tabbedParentView.getTitleAt(i);
			if (tabName.equals(parent.getLabel()))
				tabbedParentView.setSelectedIndex(i);
		}

	}

	/**
	 * Pomocna metoda za lokalizaciju, koja zatvara sve postojece modele tabela i njihove prikaze
	 * i na otvara ih na novom jeziku.
	 * 
	 * @param parent
	 * 		- {@link TableModel1 model} roditeljske tabele.
	 * @param children
	 * 		- mapa {@link TableModel1 modela} svih tabela koje su deca za roditeljsku tabelu koja se takodje prosledjuje.
	 */
	public void daaj(TableModel1 parent, HashMap<String, TableModel1> children) {
		listaImenaDece.clear();
		TableView1 roditelj = new TableView1(parent);
		tableViewList.add(roditelj);

		if (children.size() == 0) {

			this.remove(workspaceBottom);
			this.remove(workspaceTop);
			this.setLayout(new GridLayout(1, 1));
			add(workspaceTop);
			toolbarTop.setVisible(true);

			if (openTabsRoditelji.isEmpty()) {
				openTabsRoditelji.add(parent.getLabel());
				toolbarBottom.setVisible(true);
				tabbedParentView.addTab(parent.getLabel(), roditelj);
			} else {
				for (int i = 0; i < openTabsRoditelji.size(); i++) {
					if (openTabsRoditelji.get(i).equals(parent.getLabel())) {

						break;
					} else if (i == openTabsRoditelji.size() - 1) {
						openTabsRoditelji.add(parent.getLabel());
						toolbarBottom.setVisible(true);
						tabbedParentView.addTab(parent.getLabel(), roditelj);

						break;
					}
				}

			}

			int index = tabbedParentView.indexOfTab(roditelj.getNaziv());
			JPanel dugme = new JPanel();
			dugme = makeButton(roditelj.getNaziv(), tabbedParentView, openTabsRoditelji, toolbarTop, temp);

			tabbedParentView.setTabComponentAt(index, dugme);

			// ako ima dece
		} else {
			this.setLayout(new GridLayout(2, 1));
			add(workspaceTop);
			add(workspaceBottom);
			toolbarTop.setVisible(true);
			toolbarBottom.setVisible(true);

			if (openTabsRoditelji.isEmpty()) {
				openTabsRoditelji.add(parent.getLabel());
				toolbarBottom.setVisible(true);
				tabbedParentView.addTab(parent.getLabel(), roditelj);
				// dodao roditelja, dodaj sad decu

				tabbedChildView.removeAll();
				openTabsDeca.clear();

				for (TableModel1 m : children.values()) {
					TableView1 dete = new TableView1(m);
					openTabsDeca.add(m.getLabel());
					tabbedChildView.addTab(m.getLabel(), dete);
				}

			} else {

				// ako vec ima otvorena neka tabela
				for (int i = 0; i < openTabsRoditelji.size(); i++) {
					if (openTabsRoditelji.get(i).equals(parent.getLabel())) {

						break;
					} else if (i == openTabsRoditelji.size() - 1) {
						openTabsRoditelji.add(parent.getLabel());
						toolbarBottom.setVisible(true);
						tabbedParentView.addTab(parent.getLabel(), roditelj);

						tabbedChildView.removeAll();
						openTabsDeca.clear();
						// deca
						for (TableModel1 m : children.values()) {
							TableView1 dete = new TableView1(m);
							openTabsDeca.add(m.getLabel());
							tabbedChildView.addTab(m.getLabel(), dete);
						}
						break;
					}
				}

			}

			// dodaj u tab x
			int index = tabbedParentView.indexOfTab(roditelj.getNaziv());
			JPanel dugme = new JPanel();
			dugme = makeButtonSaDecom(roditelj.getNaziv(), tabbedParentView, tabbedChildView, openTabsRoditelji,
					openTabsDeca, toolbarTop, toolbarBottom, roditelj.getTableModel(), temp);

			tabbedParentView.setTabComponentAt(index, dugme);

		}

		// fokus
		for (int i = 0; i < tabbedParentView.getTabCount(); i++) {
			String tabName = tabbedParentView.getTitleAt(i);
			if (tabName.equals(parent.getLabel()))
				tabbedParentView.setSelectedIndex(i);
		}

	}

	@Override
	public void izmeniPrikazDece(TableModel1 parentTable) {

		tabbedChildView.removeAll();
		openTabsDeca.clear();
		// deca
		for (TableModel1 m : parentTable.getChildren().values()) {
			TableView1 dete = new TableView1(m);
			openTabsDeca.add(m.getLabel());
			tabbedChildView.addTab(m.getLabel(), dete);
		}

	}

	/**
	 * Pomocna metoda za kreiranje panela sa dugmetom za zatvaranje taba.
	 * 
	 * @param label
	 * 			- naziv panela koji se treba ispisati u okviru taba.
	 * @param tabbedParentView
	 * 			- prikaz tabova za koji se pravi panel.
	 * @param openTabs
	 * 			- lista tabova koji su otvoreni u tom prikazu.
	 * @param toolbar
	 * 			- toolbar child ili parent.
	 * @param temp
	 * 			- lista svih otvorenih modela tabela.
	 * @return
	 * 		- panel sa dugmetom za zatvaranje i nazivom tabele koja se prikazuje u odredjenom tabu.
	 */
	private JPanel makeButton(String label, JTabbedPane tabbedParentView, ArrayList<String> openTabs, JToolBar toolbar,
			ArrayList<TableModel1> temp) {
		JPanel btnPanel = new JPanel(new GridBagLayout());
		JLabel lbl = new JLabel(label);
		btnPanel.setOpaque(false);
		JButton btnClose = new JButton("x");
		btnClose.setPreferredSize(new Dimension(20, 15));

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1;

		btnPanel.add(lbl, gbc);

		gbc.gridx++;
		gbc.weightx = 0;
		btnPanel.add(btnClose, gbc);

		btnClose.setContentAreaFilled(false);
		btnClose.setBorder(BorderFactory.createEtchedBorder());
		btnClose.setBorderPainted(false);
		// na dugme dati lisener za gasenje
		btnClose.addActionListener(new CloseTabListenerHandler(label, tabbedParentView, openTabs, toolbar, temp));

		return btnPanel;
	}

	/**
	 * Ista metoda kao i make button samo za tabove u donjem prikazu.
	 * 
	 * @param labela
	 * @param tabSaRoditeljima
	 * @param tabSaDecom
	 * @param listaTabovaSaRoditeljima
	 * @param listaTabovaSaDecom
	 * @param toolbarTop
	 * @param toolbarBottom
	 * @param modelRoditelja
	 * @param temp
	 * @return
	 */
	private JPanel makeButtonSaDecom(String labela, JTabbedPane tabSaRoditeljima, JTabbedPane tabSaDecom,
			ArrayList<String> listaTabovaSaRoditeljima, ArrayList<String> listaTabovaSaDecom, ToolBarParent toolbarTop,
			ToolBarChild toolbarBottom, TableModel1 modelRoditelja, ArrayList<TableModel1> temp) {

		JPanel btnPanel = new JPanel(new GridBagLayout());
		JLabel lbl = new JLabel(labela);
		btnPanel.setOpaque(false);
		JButton btnClose = new JButton("x");
		btnClose.setPreferredSize(new Dimension(20, 15));

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1;

		btnPanel.add(lbl, gbc);

		gbc.gridx++;
		gbc.weightx = 0;
		btnPanel.add(btnClose, gbc);

		btnClose.setContentAreaFilled(false);
		btnClose.setBorder(BorderFactory.createEtchedBorder());
		btnClose.setBorderPainted(false);
		// na dugme dati lisener za gasenje
		btnClose.addActionListener(new CloseTabListenerHandlerSaDecom(labela, tabSaRoditeljima, tabSaDecom,
				listaTabovaSaRoditeljima, listaTabovaSaDecom, toolbarTop, toolbarBottom, modelRoditelja, temp));

		return btnPanel;

	}

	

	/**
	 * Metoda koja se poziva prilikom promene lokala na kojem aplikacija radi.
	 */
	@Override
	public void translate() {
		tabbedParentView.removeAll();
		tabbedChildView.removeAll();

		openTabsRoditelji.clear();
		openTabsDeca.clear();

		toolbarBottom.setVisible(false);
		toolbarTop.setVisible(false);

		for (int i = 0; i < temp.size(); i++) {
			daaj(temp.get(i), temp.get(i).getChildren());
		}
	}

	public ArrayList<TableModel1> getTemp() {
		return temp;
	}

	public void setTemp(ArrayList<TableModel1> temp) {
		this.temp = temp;
	}

	public JPanel getWorkspaceTop() {
		return workspaceTop;
	}

	public void setWorkspaceTop(JPanel workspaceTop) {
		this.workspaceTop = workspaceTop;
	}

	public JPanel getWorkspaceBottom() {
		return workspaceBottom;
	}

	public void setWorkspaceBottom(JPanel workspaceBottom) {
		this.workspaceBottom = workspaceBottom;
	}

	public ArrayList<String> getOpenTabsDeca() {
		return openTabsDeca;
	}

	public void setOpenTabsDeca(ArrayList<String> openTabsDeca) {
		this.openTabsDeca = openTabsDeca;
	}

	public ArrayList<TableView1> getTableViewChildList() {
		return tableViewChildList;
	}

	public void setTableViewChildList(ArrayList<TableView1> tableViewChildList) {
		this.tableViewChildList = tableViewChildList;
	}

	public ToolBarParent getToolbarTop() {
		return toolbarTop;
	}
	
	public JTabbedPane getTabbedParentView() {
		return tabbedParentView;
	}

	public void setTabbedParentView(TabbedPaneModel tabbedParentView) {
		this.tabbedParentView = tabbedParentView;
	}

	public JTabbedPane getTabbedChildView() {
		return tabbedChildView;
	}

	public void setTabbedChildView(TabbedPaneModel tabbedChildView) {
		this.tabbedChildView = tabbedChildView;
	}

	public ArrayList<String> getOpenTabsRoditelji() {
		return openTabsRoditelji;
	}

	public void setOpenTabsRoditelji(ArrayList<String> openTabsRoditelji) {
		this.openTabsRoditelji = openTabsRoditelji;
	}

	public void ocistiParente() {
		this.openTabsRoditelji.clear();
	}

	public ArrayList<TableView1> getTableViewList() {
		return tableViewList;
	}

	public void setTableViewList(ArrayList<TableView1> tableViewList) {
		this.tableViewList = tableViewList;
	}

	public void setToolbarTop(ToolBarParent toolbarTop) {
		this.toolbarTop = toolbarTop;
	}

	public ToolBarChild getToolbarBottom() {
		return toolbarBottom;
	}

	public void setToolbarBottom(ToolBarChild toolbarBottom) {
		this.toolbarBottom = toolbarBottom;
	}
	
	

}
