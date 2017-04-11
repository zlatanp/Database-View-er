package tree.components;

import javax.swing.tree.DefaultMutableTreeNode;

import contoller.ILocalization;
import gui.MainFrame;

/**
 * Klasa koja ce predstavljati paket cvor u stablu aplikacije.
 * 
 * @author Jasmina
 *
 */
public class PackageComponent extends DefaultMutableTreeNode implements ILocalization {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7427629111125913595L;

	/**
	 * Polje koje predstavlja naziv cvora koji je paket.
	 */
	private String packageName;

	public PackageComponent(String pack) {
		super(pack);
		this.packageName = pack;
	}

	/**
	 * Metoda koja se poziva prilikom promene lokala.
	 */
	@Override
	public void translate() {
		this.setUserObject(MainFrame.getInstance().getResourceBundle().getString(packageName));
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	
	
}
