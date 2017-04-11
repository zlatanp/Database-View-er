package tree;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import gui.MainFrame;

/**
 * Klasa koja predstavlja model stabla
 * 
 * @author Jasmina
 *
 */
public class WorkspaceModel extends DefaultTreeModel {

	private static final long serialVersionUID = -2602598479484106834L;
	private static String name = new String(MainFrame.getInstance().getResourceBundle().getString("Workspace"));

	public WorkspaceModel() {
		super(new DefaultMutableTreeNode(name));
	}
}
