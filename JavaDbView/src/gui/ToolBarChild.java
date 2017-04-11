package gui;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolBar;

import actions.ActionManager;
import listeners.UnderConcstructionListener;

/**
 * Klasa koja predstavlja tulbar u donjem delu - delu dece - služi za prikazivanje najcesce koirscenih radnji i kontrola.
 *  @author Zlatan
 */

public class ToolBarChild extends JToolBar {
	
	private static final long serialVersionUID = 3139539449575742666L;

	/**
	 * Polje koje modeluje menadzer akcija.
	 */
	private ActionManager actionManager = null;

	private JButton button1 = null;
	private JButton button2 = null;
	private JButton button3 = null;

	public ToolBarChild() {
		super(JToolBar.HORIZONTAL);
		setBackground(Color.decode("#eeeeee"));
		setFloatable(false);

		actionManager = MainFrame.getInstance().getActionManager();

		add(actionManager.getAddChildRowAction());
		add(actionManager.getEditChildRowAction());
		add(actionManager.getDeleteChildTableAction());
		addSeparator();

		// ikonica: jedna strelica gore
		button1 = new JButton();
		button1.setIcon(new ImageIcon("images/parentToolBar/up.png"));
		add(button1);
		button1.addActionListener(new UnderConcstructionListener());

		// ikonica: jedna strelica dole
		button2 = new JButton();
		button2.setIcon(new ImageIcon("images/parentToolBar/down.png"));
		add(button2);
		button2.addActionListener(new UnderConcstructionListener());

		add(actionManager.getPromoteToParentAction());

		addSeparator();

		button3 = new JButton();
		button3.setIcon(new ImageIcon("images/parentToolBar/refresh.png"));
		add(button3);
		button3.addActionListener(new UnderConcstructionListener());

		addSeparator();

		add(actionManager.getFilterChild());

		addSeparator();

	}
}
