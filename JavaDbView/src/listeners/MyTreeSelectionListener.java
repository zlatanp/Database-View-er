package listeners;

import gui.MainFrame;
import model.TableModel1;
import tree.components.TableComponent;
import view.WorkspaceView;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

/**
 * Klasa koja predstavlja osluskivac za dogadjaje nad stablom.
 * 
 * @author Zlatan
 *
 */
public class MyTreeSelectionListener implements TreeSelectionListener {

	@Override
	public void valueChanged(TreeSelectionEvent e) {

		Object lastSelected = e.getPath().getLastPathComponent();

		if (lastSelected instanceof TableComponent) {

			TableComponent tableComponent = (TableComponent) lastSelected;
			TableModel1 tableModel = tableComponent.getTableModel();
			WorkspaceView workspaceView = MainFrame.getInstance().getWorkspaceView();
			workspaceView.updateForTreeChange(tableModel, tableModel.getChildren());
		}

	}

}
