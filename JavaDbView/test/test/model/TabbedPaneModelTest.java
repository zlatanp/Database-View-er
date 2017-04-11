package test.model;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import model.TabbedPaneModel;
import model.TableModel1;

/**
 * Test klasa za klasu {@link model.TabbedPaneModel TabbedPaneModel}
 * SVN Revizija klase 92
 * Za metodu DodajUMapu uspesan test. Metoda uspesno dodaje u mapu TableModel1.
 * Za metodu GetTabovi test uspesan. Metoda vraca sve tabove (uporedjen broj tabova kojih ima u mapi).
 * Za metodu SetTabovi uspesan test. Metoda ubacuje 3 nova taba u mapu tabova.
 * 
 * @author Zlatan
 */

public class TabbedPaneModelTest {
	
	TabbedPaneModel tabbedPane;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		this.tabbedPane = new TabbedPaneModel();
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testDodajUMapu() {
		TableModel1 model = new TableModel1("Test Tab", "TEST_TAB");
		this.tabbedPane.dodajUMapu("Testni Tab", model);
		assertTrue(tabbedPane.getTabovi().get("Testni Tab") == model && tabbedPane.getTabovi().size() == 1);
		
	}

	@Test
	public void testGetTabovi() {
		TableModel1 model = new TableModel1("Test Tab", "TEST_TAB");
		this.tabbedPane.dodajUMapu("Testni Tab", model);
		int duzina = this.tabbedPane.getTabovi().size();
		assertEquals(1, duzina);
	}

	@Test
	public void testSetTabovi() {
		TableModel1 model1 = new TableModel1("First tab", "FIRST_TAB");
		TableModel1 model2 = new TableModel1("Second tab", "SECOND_TAB");
		TableModel1 model3 = new TableModel1("Third tab", "THIRD_TAB");
		HashMap<String, TableModel1> tabovi = new HashMap<String, TableModel1>();
		tabovi.put("Prvi", model1);
		tabovi.put("Drugi", model2);
		tabovi.put("Treci",model3);
		
		this.tabbedPane.setTabovi(tabovi);
		assertTrue(tabbedPane.getTabovi().size() == 3);
		
		
	}

}
