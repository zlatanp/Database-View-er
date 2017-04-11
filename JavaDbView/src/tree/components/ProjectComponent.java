package tree.components;

import javax.swing.tree.DefaultMutableTreeNode;

import contoller.ILocalization;
import gui.MainFrame;

/**
 * Klasa koja ce obuhvatati pakete u jedan- projekat cvor u stablu. 
 * 
 * 
 * @author Jasmina
 *
 */
public class ProjectComponent extends DefaultMutableTreeNode implements ILocalization {

	private static final long serialVersionUID = -2271897204450626680L;

	/**
	 * Polje koje predstavlja naziv cvora koji je projekat u stablu.
	 */
	private String projectName;

	public ProjectComponent(String projectName) {
		super(projectName);
		this.projectName = projectName;
	}

	/**
	 * Metoda koja se poziva prilikom promene lokala.
	 */
	@Override
	public void translate() {
		this.setUserObject(MainFrame.getInstance().getResourceBundle().getString(projectName));

	}
}
