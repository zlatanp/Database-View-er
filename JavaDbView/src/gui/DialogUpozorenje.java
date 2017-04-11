package gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;

/**
 * Klasa koja sluzi za prikaz dijaloga za upozorenje. 
 * Koristi se prilikom rada sa bazom, kada ne moze da se konektuje na bazu ili kada ne mogu da
 * se izvrse operacije nad bazom (CRUD).
 * 
 * @author Milena
 */

public class DialogUpozorenje extends JDialog {


	private static final long serialVersionUID = 3434300869120830219L;
	/**
	 * Polje koje predstavlja poruku koja ce se ispisivati.
	 */
	private JLabel labelPoruka = null;
	
	/**
	 * Polje koje predstavlja dugme  na koje se izlazi iz dijaloga.
	 */
	private JButton buttonOK = null;

	public DialogUpozorenje() {
		this.setSize(200, 120);
		this.setLayout(new GridLayout(2, 2));
		this.setLocationRelativeTo(null);

		String name = new String("Upozorenje");
		this.setTitle(name);

		String poruka = new String("KonekcijaSaBazomNijeMoguca");
		labelPoruka = new JLabel(poruka);
		this.add(labelPoruka);

		buttonOK = new JButton("OK");
		this.add(buttonOK);
		buttonOK.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});

		this.setModal(true);
		// this.setVisible(true);
	}

	public DialogUpozorenje(String poruka) {
		this.setSize(500, 120);
		this.setLayout(new GridLayout(2, 2));
		this.setLocationRelativeTo(null);

		String name = new String("Upozorenje");
		this.setTitle(name);

		labelPoruka = new JLabel(poruka);
		this.add(labelPoruka);

		buttonOK = new JButton("OK");
		this.add(buttonOK);
		buttonOK.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});

		this.setModal(true);
		// this.setVisible(true);
	}
}
