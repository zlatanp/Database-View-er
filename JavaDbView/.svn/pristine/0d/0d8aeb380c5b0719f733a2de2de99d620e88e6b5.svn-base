package actions;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.KeyStroke;

import gui.MainFrame;


/**
 * Klasa koja sadrzi informacije o autorima.
 * 
 * @author Milena
 *
 */
@SuppressWarnings("serial")
public class AboutAction extends AbstractAction {

	public AboutAction() {
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
		putValue(MNEMONIC_KEY, KeyEvent.VK_A);
		putValue(SMALL_ICON, new ImageIcon("images/help.png"));
		putValue(NAME, MainFrame.getInstance().getResourceBundle().getString("About"));
		putValue(SHORT_DESCRIPTION, MainFrame.getInstance().getResourceBundle().getString("About"));
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		JDialog dialog = new JDialog(MainFrame.getInstance(), "About", false);
		dialog.setSize(800, 450);
		dialog.setLocationRelativeTo(MainFrame.getInstance());

		ImageIcon icon2 = new ImageIcon("images/aboutUs.jpg");
		JLabel labelMP = new JLabel();
		labelMP.setIcon(icon2);
		labelMP.setPreferredSize(new Dimension(800, 450));
		dialog.add(labelMP);

		dialog.setModal(true);
		dialog.setResizable(false);
		dialog.setVisible(true);

	}

}
