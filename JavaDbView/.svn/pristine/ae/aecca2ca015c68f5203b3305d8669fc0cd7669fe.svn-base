package actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import gui.MainFrame;


/** 
 * Klasa koja sluzi za podešavanje izgleda (look and feel)  - tema AluOxid.
 * 
 * @author user(Svetlana)
 */
public class AluOxideAction extends AbstractAction{

	
	
	private static final long serialVersionUID = 6445854140039379742L;


	public AluOxideAction() {
		super("AluOxide(default)");
		putValue(
				ACCELERATOR_KEY,
				KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.CTRL_MASK
						| ActionEvent.ALT_MASK));
		putValue(SHORT_DESCRIPTION, "AluOxide LnF");
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			UIManager.setLookAndFeel("de.javasoft.plaf.synthetica.SyntheticaAluOxideLookAndFeel");
			SwingUtilities.updateComponentTreeUI(MainFrame.getInstance());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e1) {			
			e1.printStackTrace();
		}
	}
}
