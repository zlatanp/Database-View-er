/**
 * 
 */
package core;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import gui.MainFrame;

/**
 * Klasa koja sluzi za pokretanje aplikacije. U ovoj klasi se postavlja osnovna
 * tema i inicijalizuje glavni prozor.
 * 
 * @author Zlatan
 *
 */
public class Main {

	/**
	 * @param args
	 * 			- argumenti za main
	 */
	public static void main(String[] args) {

		try {
			UIManager.setLookAndFeel("de.javasoft.plaf.synthetica.SyntheticaAluOxideLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e1) {
			e1.printStackTrace();
		}

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				MainFrame mainFrame = MainFrame.getInstance();
			}
		});
	}
}
