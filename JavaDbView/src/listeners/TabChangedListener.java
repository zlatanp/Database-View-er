package listeners;

import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import contoller.UpdateManager;
import gui.MainFrame;
import model.TableModel1;

import view.TableView1;

/**
 * Osluskivac za dogadjaje i promene nad tabovima.
 * 
 * @author Zlatan
 * @author Milena
 *
 */
public class TabChangedListener implements ChangeListener {

	@Override
	public void stateChanged(ChangeEvent e) {

		JTabbedPane pane = (JTabbedPane) e.getSource();
		TableView1 scrollPane = (TableView1) pane.getSelectedComponent();
		UpdateManager um = MainFrame.getInstance().getUpdateManager();
		if (scrollPane == null) {
			pane.removeAll();
			um.brisiSveRoditelje();
		} else {

			TableModel1 model = scrollPane.getTableModel();

			if (!model.getChildren().isEmpty()) {

				um.menjajDecuPreview(model);
			}

			um.parentTabChanged(model);

		}
	}
}
