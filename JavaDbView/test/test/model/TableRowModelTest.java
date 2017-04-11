package test.model;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import model.TableRowModel;


/**
 * Klasa koja testira TableRowModel
 * 
 *
 * 
 * Uspesno je kad doda u listu red.
 * 
 * 
 *  @author Milena
 * */

public class TableRowModelTest {
	private TableRowModel tableRowModel;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		this.tableRowModel = new TableRowModel();

	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testDodajUListu() {
		this.tableRowModel.addPair("Name1", "Value1");
		this.tableRowModel.addPair("Name2", "Value2");
		this.tableRowModel.addPair("Name3", "Value3");

		assertTrue(tableRowModel.getFieldValues().get(0).equals("Value1")
				&& tableRowModel.getFieldNames().get(0).equals("Name1"));

	}

	@Test
	public void testSizeFieldNames() {
		this.tableRowModel.addPair("Name1", "Value1");
		int duzina = this.tableRowModel.getFieldNames().size();
		assertEquals(1, duzina);
	}

	@Test
	public void testSizeFieldValues() {
		this.tableRowModel.addPair("Name1", "Value1");
		int duzina = this.tableRowModel.getFieldValues().size();
		assertEquals(1, duzina);
	}
}
