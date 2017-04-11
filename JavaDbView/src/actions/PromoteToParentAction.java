package actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;

import gui.MainFrame;
import model.TableModel1;

/**
 * Klasa koja implementira šetanju po hijerarhiji. Promovisanje roditeljske
 * tabele u dete, ukoliko ima roditelja.
 * 
 * @author Milena
 */


public class PromoteToParentAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4791508162315466713L;

	// promocija u parent
	public PromoteToParentAction() {
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_G, ActionEvent.ALT_MASK));
		putValue(MNEMONIC_KEY, KeyEvent.VK_G);
		putValue(SMALL_ICON, new ImageIcon("images/childToolBar/first.png"));
		putValue(NAME, MainFrame.getInstance().getResourceBundle().getString("PromoteToParent"));
		putValue(SHORT_DESCRIPTION, MainFrame.getInstance().getResourceBundle().getString("PromoteToParent"));
	}

	// @Override
	public void actionPerformed(ActionEvent e) {
		JTabbedPane tabbedChildView = MainFrame.getInstance().getWorkspaceView().getTabbedChildView();
		int selectedIndex = tabbedChildView.getSelectedIndex();
		String tabName = tabbedChildView.getTitleAt(selectedIndex);

		ArrayList<TableModel1> tableParentModels = MainFrame.getInstance().getWorkspaceView().getTemp();

		TableModel1 tableChildModel = null;
		// prodji kroz sve otvorene tabove roditelja
		// i pronadji to dete koje zelis da promovises
		for (int i = 0; i < tableParentModels.size(); i++) {
			TableModel1 tableParent = tableParentModels.get(i);
			// pronadji njenu decu
			HashMap<String, TableModel1> children = tableParent.getChildren();
			for (TableModel1 model : children.values()) {
				if (tabName.equals(model.getLabel())) {
					tableChildModel = model;
					break;
				}
			}

		}

		MainFrame.getInstance().getWorkspaceView().daaj(tableChildModel, tableChildModel.getChildren());

	}

}
