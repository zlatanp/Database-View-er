package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import gui.MainFrame;


/**
 * Klasa koja sluzi da pikaze poruku o tome da je odredjena komponenta u izradi.
 * 
 * @author Milena
 * 
 */
public class UnderConcstructionListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		String message = new String(MainFrame.getInstance().getResourceBundle().getString("UnderConstruction"));
		JOptionPane.showMessageDialog(new JFrame(), message);

	}

}
