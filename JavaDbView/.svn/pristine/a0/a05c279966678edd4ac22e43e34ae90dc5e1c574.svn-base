package test.model;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import validation.ValidateForm;

/**
 * Klasa koja testira validaciju.
 * 
 * 
 * 
 * Uspesno je kad nije prazan string.
 * 
 * 
 * @author user(Svetlana)
 * */

public class ValidationTest {

	ValidateForm validateForm;
	String car;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		validateForm = new ValidateForm();
		 car = new String("car");
		
		validateForm.setValue(car);
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCorrect() {
		boolean rez = validateForm.correct(car);
		assertTrue(rez);
	}

}
