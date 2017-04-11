package fileFilters;

import java.io.File;

import javax.swing.filechooser.FileFilter;

/**
 * Klasa koju koristi <code>JFileChooser</code>, za prikazivanje fajlova samo
 * odredjenog formata.
 *
 * @author Milena
 */

public class DBVFileFilter extends FileFilter {

	@Override
	public boolean accept(File file) {
		return (file.isDirectory() || file.getName().toLowerCase().endsWith(".jpg")
				|| file.getName().toLowerCase().endsWith(".png") || file.getName().toLowerCase().endsWith(".ico")
				|| file.getName().toLowerCase().endsWith(".mp3"));
	}

	@Override
	public String getDescription() {
		return ".png, .jpg, .ico, .mp3";
	}

}
