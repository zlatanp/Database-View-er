package actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;

import gui.AddDialog;
import gui.MainFrame;
import model.TableModel1;
import view.TableView1;
import view.WorkspaceView;


/**
 * Klasa koja predstavlja dodavanje reda u roditeljsku tabelu.
 * 
 * @author Milena
 * @author user(Svetlana)
 *
 */

@SuppressWarnings("serial")
public class AddParentRowAction extends AbstractAction {
	
	/**
	 * Polje za prikaz roditeljske tabele.
	 */
	private TableView1 tableView = null;
	
	/**
	 * Polje za model tabele koja je selektovana. 
	 */
	private TableModel1 tableSelectedModel = null;
	
	/**
	 * Polje koje modeluje string za prikaz u okviru tooltipa.
	 */
	private String tooltipString = MainFrame.getInstance().getResourceBundle().getString("Add");

	public AddParentRowAction() {
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.ALT_MASK));
		putValue(MNEMONIC_KEY, KeyEvent.VK_A);
		putValue(SMALL_ICON, new ImageIcon("images/parentToolBar/add_table.png"));
		putValue(NAME, tooltipString);
		putValue(SHORT_DESCRIPTION, tooltipString);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
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

		AddDialog addDialog;
		addDialog = new AddDialog(tableView, false, -1);
		addDialog.setVisible(true);

	}

}
