package actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;

import gui.AddDialog;
import gui.MainFrame;
import model.TableModel1;
import view.TableView1;
import view.WorkspaceView;


/**
 * Klasa koja sluzi za izmenu sloga - u gornjem  delu - roditelja.
 * 
 * @author Svetlana
 *
 */

public class EditParentRowAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1720624567650899149L;
	
	/**
	 * Polje za prikaz roditeljske nadredjene tabele.
	 */
	private TableView1 tableView = null;
	
	/**
	 * Polje za model tabele koja je selektovana u parent delu prikaza u kojoj se edituje red. 
	 */
	private TableModel1 tableSelectedModel = null;

	public EditParentRowAction() {
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.ALT_MASK));
		putValue(MNEMONIC_KEY, KeyEvent.VK_E);
		putValue(SMALL_ICON, new ImageIcon("images/parentToolBar/edit_table.png"));
		putValue(NAME, MainFrame.getInstance().getResourceBundle().getString("Edit"));
		putValue(SHORT_DESCRIPTION, MainFrame.getInstance().getResourceBundle().getString("Edit"));
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
		int indexRow = tableView.getTabela().getSelectedRow();
		if (indexRow != -1) {
			AddDialog addDialog;
			addDialog = new AddDialog(tableView, true, indexRow);
			addDialog.setVisible(true);
		} else {
			JOptionPane.showMessageDialog(MainFrame.getInstance(), "Oznacite red u tabeli!", "Upozorenje",
					JOptionPane.WARNING_MESSAGE);
		}
	}

}
