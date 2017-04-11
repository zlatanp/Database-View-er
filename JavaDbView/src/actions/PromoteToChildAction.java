package actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;

import gui.MainFrame;
import model.TableModel1;
import tree.components.TableComponent;
import view.WorkspaceView;

/**
 * Klasa koja implementira šetanju po hijerarhiji. Promovisanje tabele deteta u
 * roditeljsku tabelu.
 * 
 * @author Milena
 */

public class PromoteToChildAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8446626523155585393L;

	public PromoteToChildAction() {
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.ALT_MASK));
		putValue(MNEMONIC_KEY, KeyEvent.VK_Q);
		putValue(SMALL_ICON, new ImageIcon("images/parentToolBar/last.png"));
		putValue(NAME, MainFrame.getInstance().getResourceBundle().getString("PromoteToChild"));
		putValue(SHORT_DESCRIPTION, MainFrame.getInstance().getResourceBundle().getString("PromoteToChild"));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		WorkspaceView workspaceView = MainFrame.getInstance().getWorkspaceView();
		JTabbedPane tabbedParentView = workspaceView.getTabbedParentView();
		int selectedIndex = tabbedParentView.getSelectedIndex();
		String tabName = tabbedParentView.getTitleAt(selectedIndex);

		ArrayList<TableModel1> tableParentModels = workspaceView.getTemp();
		TableModel1 tableSelectedModel = null;
		// pronadji model onog taba koji je selektovan
		for (int i = 0; i < tableParentModels.size(); i++) {
			if (tabName.equals(tableParentModels.get(i).getLabel())) {
				tableSelectedModel = tableParentModels.get(i);
				break;
			}
		}

		// pronadji njene parente
		HashMap<String, TableModel1> parents = tableSelectedModel.getParents();

		// ako ih nema, ne mogu promovisati u dete
		if (parents.size() == 0) {
			JOptionPane.showMessageDialog(MainFrame.getInstance(),
					MainFrame.getInstance().getResourceBundle().getString("PromovisanjeUDete"));
		} else {

			// nadji sve roditelje i dodaj ih u listu za izbor
			ArrayList<String> optionList = new ArrayList<String>();
			for (TableModel1 model : parents.values()) {
				optionList.add(model.getLabel());
			}
			Object[] options = optionList.toArray();

			// kao rezultat dobijam naziv labele parenta
			String selectedName = (String) JOptionPane.showInputDialog(null,
					MainFrame.getInstance().getResourceBundle().getString("ParentTable"),
					MainFrame.getInstance().getResourceBundle().getString("SelectParentTable"),
					JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

			if (selectedName != null) {
				ArrayList<TableComponent> allTables = new ArrayList<>();
				ArrayList<TableModel1> modeliZaServer = new ArrayList<>();
				if (!MainFrame.getInstance().isSQLReader()) {
					allTables = MainFrame.getInstance().getJsonDataReader().getAllTables();
					ArrayList<TableModel1> allModels = new ArrayList<TableModel1>();
					// nadji modele svih tabela
					for (int i = 0; i < allTables.size(); i++) {
						allModels.add(allTables.get(i).getTableModel());
					}

					// na osnovu selektovanog imena pronadji odgovarajucu
					// roditeljsku tabelu
					TableModel1 tableParentModel = null;
					for (int i = 0; i < allModels.size(); i++) {
						String modelName = allModels.get(i).getLabel();
						if (modelName.equals(selectedName)) {
							tableParentModel = allModels.get(i);
						}
					}

					// otvori parent tabelu
					workspaceView.daaj(tableParentModel, tableParentModel.getChildren());

					// fokusiraj odgovarajuce dete
					JTabbedPane tabbedChildView = workspaceView.getTabbedChildView();
					for (int i = 0; i < tabbedChildView.getTabCount(); i++) {
						String pom = tabbedChildView.getTitleAt(i);
						if (tabName.equals(pom))
							tabbedChildView.setSelectedIndex(i);
					}

					// tab koji smo promovisali u dete treba da zatvorimo
					ArrayList<String> openTabsRoditelji = workspaceView.getOpenTabsRoditelji();
					for (int i = 0; i < tabbedParentView.getTabCount(); i++) {
						if (tabbedParentView.getTitleAt(i).equals(tabName)) {
							tabbedParentView.remove(i);// brisem roditelja iz
														// view

							// brisem roditelja iz liste
							for (int z = 0; z < openTabsRoditelji.size(); z++) {
								if (openTabsRoditelji.get(z).equals(tabName)) {
									openTabsRoditelji.remove(z);
									break;
								}
							}
							break;
						}
					}

				} else {
					modeliZaServer = MainFrame.getInstance().getUpdateManager().getAllTableModels();

					TableModel1 tableParentModel = null;
					for (int i = 0; i < modeliZaServer.size(); i++) {
						String modelName = modeliZaServer.get(i).getLabel();
						if (modelName.equals(selectedName)) {
							tableParentModel = modeliZaServer.get(i);
						}
					}

					// otvori parent tabelu
					workspaceView.daaj(tableParentModel, tableParentModel.getChildren());

					// fokusiraj odgovarajuce dete
					JTabbedPane tabbedChildView = workspaceView.getTabbedChildView();
					for (int i = 0; i < tabbedChildView.getTabCount(); i++) {
						String pom = tabbedChildView.getTitleAt(i);
						if (tabName.equals(pom))
							tabbedChildView.setSelectedIndex(i);
					}

					// tab koji smo promovisali u dete treba da zatvorimo
					ArrayList<String> openTabsRoditelji = workspaceView.getOpenTabsRoditelji();
					for (int i = 0; i < tabbedParentView.getTabCount(); i++) {
						if (tabbedParentView.getTitleAt(i).equals(tabName)) {
							tabbedParentView.remove(i);// brisem roditelja iz
														// view

							// brisem roditelja iz liste
							for (int z = 0; z < openTabsRoditelji.size(); z++) {
								if (openTabsRoditelji.get(z).equals(tabName)) {
									openTabsRoditelji.remove(z);
									break;
								}
							}
							break;
						}
					}

				}

			}
		}
	}

}
