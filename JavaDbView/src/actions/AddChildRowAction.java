package actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;

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
 * Klasa koja predstavlja dodavanje reda deteta u tabelu. 
 * 
 * @author Milena
 * @author user(Svetlana)
 *
 */

@SuppressWarnings("serial")
public class AddChildRowAction extends AbstractAction {
	
	/**
	 * Veza sa view delom.
	 */
	private TableView1 tableView = null;
	
	/**
	 * Veza ka modelu roditelja.
	 */
	private TableModel1 tableParentModel = null;
	
	/**
	 * Veza ka modelu deteta.
	 */
	private TableModel1 tableChildModel = null;

	public AddChildRowAction() {
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.ALT_MASK));
		putValue(MNEMONIC_KEY, KeyEvent.VK_A);
		putValue(SMALL_ICON, new ImageIcon("images/childToolBar/add_table.png"));
		putValue(NAME, MainFrame.getInstance().getResourceBundle().getString("Add"));
		putValue(SHORT_DESCRIPTION, MainFrame.getInstance().getResourceBundle().getString("Add"));
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		WorkspaceView workspaceView = MainFrame.getInstance().getWorkspaceView();

		// roditelji
		JTabbedPane tabbedParentView = workspaceView.getTabbedParentView();
		int selectedIndexParent = tabbedParentView.getSelectedIndex();
		String tabNameParent = tabbedParentView.getTitleAt(selectedIndexParent);

		// deca
		JTabbedPane tabbedChildView = workspaceView.getTabbedChildView();
		int selectedIndex = tabbedChildView.getSelectedIndex();
		String tabName = tabbedChildView.getTitleAt(selectedIndex);

		ArrayList<TableModel1> tableParentModels = workspaceView.getTemp();

		// pronadji roditelja onog taba koji je selektovan
		for (int i = 0; i < tableParentModels.size(); i++) {
			if (tabNameParent.equals(tableParentModels.get(i).getLabel())) {
				tableParentModel = tableParentModels.get(i);
				break;
			}
		}

		// pronadji decu tog roditelja
		HashMap<String, TableModel1> deca = tableParentModel.getChildren();
		for (String key : deca.keySet()) {
			TableModel1 tableModel1 = deca.get(key);
			if (tableModel1.getLabel().equals(tabName)) {
				tableChildModel = tableModel1;
				break;
			}
		}

		// pronadji view onog modela koji je selektovan
		ArrayList<TableView1> tableViewList = workspaceView.getTableViewChildList();
		for (int i = 0; i < tableViewList.size(); i++) {
			if (tableViewList.get(i).getTableModel().getCode().equals(tableChildModel.getCode())) {
				tableView = tableViewList.get(i);
				break;
			}
		}

		AddDialog addDialog;
		addDialog = new AddDialog(tableView, false, -1);
		addDialog.setVisible(true);

	}

}
