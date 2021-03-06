package test.model;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import model.TableFieldModel;



/**
 * Klasa koja testira TableFieldModel
 * 
 *
 * 
 * Uspesno je ako nadje dobar filter za bazu.
 * 
 * 
 *  @author Milena
 * */

public class TableFieldModelTest {
	private TableFieldModel tableFieldModel;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		this.tableFieldModel = new TableFieldModel();
		this.tableFieldModel.setLabel("Prva");
		this.tableFieldModel.setCode("PRVA");
		this.tableFieldModel.setType("int");
		this.tableFieldModel.setMandatory(true);
		this.tableFieldModel.setPrimaryKey(true);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void tesFilter() {
		String filter = this.tableFieldModel.getFilterForField();
		assertTrue(filter.equals("equals"));
	}

}
