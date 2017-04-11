package test.tree.components;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import gui.MainFrame;
import model.TableModel1;
import tree.components.TableComponent;

/**
 * Test klasa za klasu {@link tree.components.TableComponent TableComponent}
 * SVN Revizija klase 116
 * Za metodu GetTableName uspesan test. Metoda uspesno vraca naziv tabele koji je postavljen u konstruktoru.
 * Za metodu SetTableName test je uspesan. Metoda uspesno setuje naziv tableComponente na zadati.
 * Za metodu GetTableModel test uspesan. Metoda uspesno vraca model table componente.
 * Za metodu SetTableModel test uspesan. Metoda uspesno postavlja model tableComponente na zadati.
 * Za metodu translate test uspesan, stim da baca exception. Metoda uspesno vrsi preimenovanje, stim sto ima exception
 * javax.swing.JComponent.paintComponent(Unknown Source) zbog stabla.
 * @author Jasmina
 */

public class TableComponentTest {
	
	TableComponent tableComp;
	TableModel1 model;
	boolean isSerbian;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		this.tableComp = new TableComponent("Drzava");
		this.model = new TableModel1("Model","MODEL");
		this.tableComp.setTableModel(model);
		if (MainFrame.getInstance().isSerbian()){
			isSerbian = true;
		}else{
			isSerbian = false;
		}
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetTableName() {
		String name = this.tableComp.getTableName();
		assertEquals("Drzava", name);
	}

	@Test
	public void testSetTableName() {
		String newName = "Test Tabela";
		this.tableComp.setTableName(newName);
		assertEquals(newName, this.tableComp.getTableName());
	}

	@Test
	public void testGetTableModel() {
		assertTrue(this.tableComp.getTableModel() == model);
	}

	@Test
	public void testSetTableModel() {
		TableModel1 newModel = new TableModel1("New Model", "NEW_MODEL");
		this.tableComp.setTableModel(newModel);
		assertTrue(this.tableComp.getTableModel() == newModel);
	}

	@Test
	public void testTranslate() {
		this.tableComp.translate();
		if (this.isSerbian){
			assertEquals(tableComp.getTableName(), "Drzava");
		}else{
			assertEquals(tableComp.getTableName(), "Country");
		}
	}

}
