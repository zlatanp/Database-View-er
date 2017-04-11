package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JTabbedPane;
import javax.swing.JToolBar;

import model.TableModel1;

/**
 * Klasa koja predstavlja zatvaranje taba roditeljske tabele.
 * 
 * @author Zlatan
 *
 */
public class CloseTabListenerHandler implements ActionListener {

	/**
	 * Polje koje predstavlja naziv taba koji se zatvara.
	 */
	private String tabName;
	
	/**
	 * Polje koje predstavlja panel sa tabovima ciji se tab zatvara.
	 */
	private JTabbedPane panel;
	
	/**
	 * Polje koje predstavlja listu stringova naziva otvorenih tabova.
	 */
	private ArrayList<String> openTabs;
	
	/**
	 * Polje koje predstavlja toolbar.
	 */
	private JToolBar toolbar;
	
	/**
	 * Pomocna lista modela tabela koje su otvorene.
	 */
	private ArrayList<TableModel1> temp;

	public CloseTabListenerHandler(String tabName, JTabbedPane panel, ArrayList<String> openTabs, JToolBar toolbar,
			ArrayList<TableModel1> temp) {
		this.tabName = tabName;
		this.panel = panel;
		this.openTabs = openTabs;
		this.toolbar = toolbar;
		this.temp = temp;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		int index = panel.indexOfTab(tabName);
		if (index >= 0) {

			panel.removeTabAt(index);
			// obrisi iz liste onog na cije ime si kliknoo
			for (int z = 0; z < openTabs.size(); z++) {
				if (openTabs.get(z).equals(tabName)) {
					openTabs.remove(z);
				}
			}
			if (panel.getTabCount() == 0) {
				toolbar.setVisible(false);
			}

			for (int i = 0; i < temp.size(); i++) {
				if (temp.get(i).getLabel().equals(tabName)) {
					temp.remove(i);
				}
			}
		}

	}
}
