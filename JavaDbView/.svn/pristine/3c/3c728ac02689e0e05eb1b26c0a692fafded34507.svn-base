package gui;

import java.awt.event.KeyEvent;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import actions.ActionManager;

/**
 * Klasa koja prestavlja meni.
 * 
 * @author Zlatan
 * @author Svetlana - lokalizacija
 */
public class MenuBar extends JMenuBar {

	
	private static final long serialVersionUID = -114285211049317977L;

	private JMenuItem item1;
	private JMenuItem item2;
	private JMenuItem item3;
	private JMenuItem item4;
	private JMenuItem item5;
	private JMenuItem item6;
	private JMenuItem item7;
	private JMenuItem item8;
	private JMenuItem item9;
	private JMenu help;
	private JMenu file;
	private JMenu edit;
	private JMenu window;
	private JMenu settings;
	private JMenu language;
	private JCheckBoxMenuItem mniSrpski;
	private JCheckBoxMenuItem mniEngleski;
	private JCheckBoxMenuItem mniSrpskiCrly;

	/**
	 * Polje koje modeluje menadzer akcija.
	 */
	private ActionManager actionManager = MainFrame.getInstance().getActionManager();

	public MenuBar() {

		file = new JMenu(MainFrame.getInstance().getResourceBundle().getString("File"));
		file.setMnemonic(KeyEvent.VK_F);
		item1 = new JMenuItem(MainFrame.getInstance().getResourceBundle().getString("Item1"));
		item2 = new JMenuItem(MainFrame.getInstance().getResourceBundle().getString("Item2"));
		item3 = new JMenuItem(MainFrame.getInstance().getResourceBundle().getString("Item3"));

		file.add(item1);
		file.add(item2);
		file.add(item3);

		edit = new JMenu(MainFrame.getInstance().getResourceBundle().getString("Edit"));
		edit.setMnemonic(KeyEvent.VK_E);
		item4 = new JMenuItem(MainFrame.getInstance().getResourceBundle().getString("Item4"));
		item5 = new JMenuItem(MainFrame.getInstance().getResourceBundle().getString("Item5"));

		edit.add(item4);
		edit.add(item5);

		window = new JMenu(MainFrame.getInstance().getResourceBundle().getString("Window"));
		window.setMnemonic(KeyEvent.VK_W);
		item6 = new JMenuItem(MainFrame.getInstance().getResourceBundle().getString("Item6"));
		item7 = new JMenuItem(MainFrame.getInstance().getResourceBundle().getString("Item7"));
		item8 = new JMenuItem(MainFrame.getInstance().getResourceBundle().getString("Item8"));

		window.add(item6);
		window.add(item7);
		window.add(item8);

		settings = new JMenu(MainFrame.getInstance().getResourceBundle().getString("Settings"));
		settings.add(MainFrame.getInstance().getActionManager().getAluOxideAction());
		settings.add(MainFrame.getInstance().getActionManager().getBlackEyeAction());
		settings.add(MainFrame.getInstance().getActionManager().getBlackMoonAction());

		language = new JMenu(MainFrame.getInstance().getResourceBundle().getString("Language"));

		// PROMENA JEZIKA
		mniSrpski = new JCheckBoxMenuItem(MainFrame.getInstance().getResourceBundle().getString("Srpski"));
		mniSrpski.setSelected(true);
		mniSrpski.addActionListener(actionManager.getSerbianLatinLanguageAction());
		language.add(mniSrpski);

		mniSrpskiCrly = new JCheckBoxMenuItem(MainFrame.getInstance().getResourceBundle().getString("SrpskiCirilica"));
		mniSrpskiCrly.setSelected(true);
		mniSrpskiCrly.addActionListener(actionManager.getSerbianCyrlLanguageAction());
		language.add(mniSrpskiCrly);

		mniEngleski = new JCheckBoxMenuItem(MainFrame.getInstance().getResourceBundle().getString("English"));
		mniEngleski.setSelected(true);
		mniEngleski.addActionListener(actionManager.getEnglishLanguageAction());
		language.add(mniEngleski);
		language.add(mniEngleski);

		ButtonGroup bg = new ButtonGroup();
		bg.add(mniSrpski);
		bg.add(mniSrpskiCrly);
		bg.add(mniEngleski);
		settings.add(language);

		help = new JMenu(MainFrame.getInstance().getResourceBundle().getString("Help"));
		help.setMnemonic(KeyEvent.VK_H);
		item9 = new JMenuItem(MainFrame.getInstance().getResourceBundle().getString("Item9"));
		help.add(item9);

		add(file);
		add(edit);
		add(window);
		add(settings);
		add(help);

	}

	/**
	 * Metoda koja inicijalizuje komponente menija.
	 * 
	 * */
	public void initComponents() {

		// FILE
		file.setText(MainFrame.getInstance().getResourceBundle().getString("File"));

		file.getItem(0).setText(MainFrame.getInstance().getResourceBundle().getString("Item1"));

		file.getItem(1).setText(MainFrame.getInstance().getResourceBundle().getString("Item2"));

		file.getItem(2).setText(MainFrame.getInstance().getResourceBundle().getString("Item3"));

		// EDIT
		edit.setText(MainFrame.getInstance().getResourceBundle().getString("Edit"));
		edit.getItem(0).setText(MainFrame.getInstance().getResourceBundle().getString("Item4"));

		edit.getItem(1).setText(MainFrame.getInstance().getResourceBundle().getString("Item5"));

		// WINDOW
		window.setText(MainFrame.getInstance().getResourceBundle().getString("Window"));
		window.getItem(0).setText(MainFrame.getInstance().getResourceBundle().getString("Item6"));

		window.getItem(1).setText(MainFrame.getInstance().getResourceBundle().getString("Item7"));

		window.getItem(2).setText(MainFrame.getInstance().getResourceBundle().getString("Item8"));

		// HELP
		help.setText(MainFrame.getInstance().getResourceBundle().getString("Help"));
		help.getItem(0).setText(MainFrame.getInstance().getResourceBundle().getString("Item9"));

		// SETTINGS
		settings.setText(MainFrame.getInstance().getResourceBundle().getString("Settings"));

		// LANGUAGE
		language.setText(MainFrame.getInstance().getResourceBundle().getString("Language"));
		mniSrpski.setText(MainFrame.getInstance().getResourceBundle().getString("Srpski"));
		mniEngleski.setText(MainFrame.getInstance().getResourceBundle().getString("English"));
		mniSrpskiCrly.setText(MainFrame.getInstance().getResourceBundle().getString("SrpskiCirilica"));

	}

}
