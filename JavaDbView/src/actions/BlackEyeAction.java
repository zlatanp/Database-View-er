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
 * Klasa koja sluzi za podešavanje izgleda (look and feel)  - tema blackEye.
 * 
 * @author Svetlana
 */
public class BlackEyeAction extends AbstractAction{

	
	private static final long serialVersionUID = 6445854140039379742L;


	public BlackEyeAction() {
		super("BlackEye");
		putValue(
				ACCELERATOR_KEY,
				KeyStroke.getKeyStroke(KeyEvent.VK_2, ActionEvent.CTRL_MASK
						| ActionEvent.ALT_MASK));
		putValue(SHORT_DESCRIPTION, "BlackEye LnF");
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			UIManager.setLookAndFeel("de.javasoft.plaf.synthetica.SyntheticaBlackEyeLookAndFeel");
			SwingUtilities.updateComponentTreeUI(MainFrame.getInstance());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e1) {			
			e1.printStackTrace();
		}
	}
}

