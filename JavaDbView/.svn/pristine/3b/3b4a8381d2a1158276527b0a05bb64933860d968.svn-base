package test.data.reader;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import data.reader.JSONDataReader;

/**
 * Test klasa za klasu {@link data.reader.JSONDataReader JSONDataReader}
 * 
 * SVN Revizija klase 131
 * Za metodu GetTabele uspesan test. Vraca iscitan broj tabela iz json fajla, 14.
 * Za metodu GetPaketi usepesan test. Vraca iscitan broj paketa iz json fajla, 2. 
 * 
 * @author Zlatan
 */

public class JSONDataReaderTest {

	JSONDataReader reader;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		this.reader = new JSONDataReader("resources/stari.json");
		this.reader.parseJson();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetTabele() {
		int brojTabela = reader.getTabele().size();
		assertEquals(14, brojTabela);
	}

	@Test
	public void testGetPaketi() {
		int brojPaketa = reader.getPaketi().size();
		assertEquals(2, brojPaketa);
	}

}
