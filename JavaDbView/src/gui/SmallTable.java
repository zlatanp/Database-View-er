package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;

import view.TableView1;

/**
 * Klasa koja sluzi za prikaz pomocne tabele 
 * za izbor stranog kljuca prilikom insert naredbe.
 * 
 * @author Milena
 *
 */
public class SmallTable extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6568269597391794051L;
	
	/**
	 * Selektovani indeks iz prikaza tabele.
	 */
	private int index;

	public SmallTable(TableView1 tableView) {
		this.setSize(1000, 500);
		this.setLayout(new BorderLayout());
		this.setLocationRelativeTo(null);
		this.add(tableView, BorderLayout.NORTH);

		String title = new String("Izaberite vrednost iz tabele");
		this.setTitle(title);

		JButton okButton = new JButton("OK");

		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				index = tableView.getTabela().getSelectedRow();
				setVisible(false);
			}
		});
		this.add(okButton, BorderLayout.WEST);

		JButton otkazi = new JButton("Otkazi");
		otkazi.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		this.add(otkazi, BorderLayout.EAST);

		this.setModal(true);
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
}
