package actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;

import gui.MainFrame;


/**
 * Klasa koja sluzi za lokalizaciju - srpski jezik - latinica.
 * 
 * @author Svetlana
 *
 */

public class SerbianLatinLanguageAction implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		Locale.setDefault(new Locale("sr", "RS"));
		MainFrame.getInstance().setSerbian(true);
		MainFrame.getInstance().changeLanguage();			
	}

}
