package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import fileFilters.DBVFileFilter;
import gui.MainFrame;

/**
 * Pruža jednostavan mehanizam za korisnika da odabere datoteku.
 * 
 * @see JFileChooser
 * @author Milena
 */

public class OpenListener implements ActionListener {
	private JTextField textField;
	private FileInputStream fis;
	private File image;

	public OpenListener(JTextField textField) {
		this.textField = textField;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser jfc = new JFileChooser();
		jfc.setFileFilter(new DBVFileFilter());

		if (jfc.showOpenDialog(MainFrame.getInstance()) == JFileChooser.APPROVE_OPTION) {
			try {
				File pic = jfc.getSelectedFile();
				String path = pic.getAbsolutePath();
				image = new File(path);
				fis = new FileInputStream(image);

				JOptionPane.showMessageDialog(MainFrame.getInstance(), "FIS: " + fis + " ;LENGTH: " + image.length(),
						"Info", JOptionPane.INFORMATION_MESSAGE);
				fis.close();
				textField.setText(image.toString());
			} catch (IOException e3) {
				JOptionPane.showMessageDialog(MainFrame.getInstance(), "Doslo je do greske prilikom izbora datoreke.",
						"Greska", JOptionPane.ERROR_MESSAGE);
				e3.printStackTrace();
			}
		}
	}

	public FileInputStream getFis() {
		return fis;
	}

	public void setFis(FileInputStream fis) {
		this.fis = fis;
	}

	public File getImage() {
		return image;
	}

	public void setImage(File image) {
		this.image = image;
	}

}
