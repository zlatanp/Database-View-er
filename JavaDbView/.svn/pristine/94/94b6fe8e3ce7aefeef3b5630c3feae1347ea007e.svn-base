package listeners;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import gui.MainFrame;

/**
 * Klasa koja je zaduzena za obradu dogadjaja prozora. Osluskivac je
 * registoravan za glavni prozor aplikacije.
 * 
 * @author Milena
 */

public class MainWindowListener implements WindowListener {

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	/**
	 * Prilikom zatvaranja aplikacije, zatvara se konekcija sa bazom.
	 */
	@Override
	public void windowClosing(WindowEvent e) {
		String yes = new String("Yes");
		String no = new String("No");
		String ObjButtons[] = { MainFrame.getInstance().getResourceBundle().getString(yes),
				MainFrame.getInstance().getResourceBundle().getString(no) };
		String naslov = new String("ConfirmExit");
		String poruka = new String("AreYouSureYouWantToExit");
		int PromptResult = JOptionPane.showOptionDialog(null,
				MainFrame.getInstance().getResourceBundle().getString(poruka),
				MainFrame.getInstance().getResourceBundle().getString(naslov), JOptionPane.DEFAULT_OPTION,
				JOptionPane.WARNING_MESSAGE, null, ObjButtons, ObjButtons[1]);
		JFrame frame = (JFrame) e.getComponent();
		if (PromptResult == 0) {
			try {
				MainFrame.getConnection().close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			System.exit(0);
		} else {
			frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		}
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub

	}

}
