package tree;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

import tree.components.PackageComponent;
import tree.components.ProjectComponent;
import tree.components.TableComponent;

/**
 * Klasa koja sluzi za renderovanje stabla- ulepsani prikaz cvorova u stablu.
 * 
 * @author Milena
 * @author Svetlana
 *
 */
public class WorkspaceTreeCellRenderer extends DefaultTreeCellRenderer {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3755780197262297373L;
	
	/**
	 * Polje koje predstavlja ikonicu za projekat cvor.
	 */
	private ImageIcon projectIcon = null;
	
	/**
	 * Polje koje predstavlja ikonicu za paket cvor.
	 */
	private ImageIcon packageIcon = null;
	
	/**
	 * Polje koje predstavlja ikonicu za projekat cvor.
	 */
	private ImageIcon tableIcon = null;

	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object object, boolean sel, boolean expanded,
			boolean leaf, int row, boolean hasFocus) {
		super.getTreeCellRendererComponent(tree, object, sel, expanded, leaf, row, hasFocus);

		if (object instanceof ProjectComponent) {
			projectIcon = new ImageIcon("images/project.png");
			if (projectIcon != null)
				setIcon(projectIcon);
		}
		if (object instanceof PackageComponent) {
			packageIcon = new ImageIcon("images/package.png");
			if (packageIcon != null)
				setIcon(packageIcon);
		}
		if (object instanceof TableComponent) {
			tableIcon = new ImageIcon("images/table.png");
			if (tableIcon != null)
				setIcon(tableIcon);
		}

		return this;
	}

}
