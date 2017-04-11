package actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;

import gui.MainFrame;
import gui.SearchDialog;
import model.TableModel1;
import view.TableView1;
import view.WorkspaceView;

/**
 * Klasa koja sluzi za pretragu u okviru parent tabele.
 * 
 * @author Zlatan
 *
 */

public class FilterParentTable extends AbstractAction {

	private static final long serialVersionUID = -1869015788571402047L;

	/**
	 * Polje za prikaz tabele u kojoj se vrsi pretraga.
	 */
	private TableView1 tableView = null;
	
	/**
	 * Polje za model tabele u kojoj se vrsi pretraga.
	 */
	private TableModel1 tableSelectedModel = null;

	public FilterParentTable() {
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		putValue(MNEMONIC_KEY, KeyEvent.VK_S);
		putValue(SMALL_ICON, new ImageIcon("images/parentToolBar/search.png"));
		putValue(NAME, MainFrame.getInstance().getResourceBundle().getString("Search"));
		putValue(SHORT_DESCRIPTION, MainFrame.getInstance().getResourceBundle().getString("Search"));
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		WorkspaceView workspaceView = MainFrame.getInstance().getWorkspaceView();
		JTabbedPane tabbedParentView = workspaceView.getTabbedParentView();
		int selectedIndex = tabbedParentView.getSelectedIndex();
		String tabName = tabbedParentView.getTitleAt(selectedIndex);

		ArrayList<TableModel1> tableParentModels = workspaceView.getTemp();

		// pronadji model onog taba koji je selektovan
		for (int i = 0; i < tableParentModels.size(); i++) {
			if (tabName.equals(tableParentModels.get(i).getLabel())) {
				tableSelectedModel = tableParentModels.get(i);
				break;
			}
		}

		// pronadji view onog modela koji je selektovan
		ArrayList<TableView1> tableViewList = workspaceView.getTableViewList();
		for (int i = 0; i < tableViewList.size(); i++) {
			if (tableViewList.get(i).getTableModel().equals(tableSelectedModel)) {
				tableView = tableViewList.get(i);
				break;
			}
		}

		SearchDialog searchDijalog = new SearchDialog(tableView);
		searchDijalog.setVisible(true);
	}

}
