package test.model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import model.TableFieldModel;
import model.TableModel1;


/**
 * Klasa koja testira TableModel.
 * 
 *
 * 
 * Uspesno je kad doda field u tabelu.
 * 
 * 
 *  @author user(Svetlana)
 * */

public class TableModelTest {

	TableModel1 tableModel;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		
		this.tableModel = new TableModel1();
		this.tableModel.setFields(new HashMap<String, TableFieldModel>());
		this.tableModel.setPolja(new ArrayList<TableFieldModel>());
		TableFieldModel field = new TableFieldModel("prvi", "PRVI", "char", false);
		
		tableModel.addField(field);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testAddField() {
		int numberFields = tableModel.getFields().size();
		assertEquals(1, numberFields);
	}

}
