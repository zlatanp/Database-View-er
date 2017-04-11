package test.actions;

import static org.junit.Assert.*;

import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import actions.DeleteRowAction;
import model.TableRowModel;


/**
 * Test klasa za {@link actions.DeleteRowAction DeleteRowAction}
 * SVN Revizija klase 126
 * Za metodu SelectedRowToTableRowModel uspesan test. Metoda brise selektovan red.
 * 
 * @author Jasmina
 *
 */
public class DeleteRowActionTest {
	
	private DeleteRowAction deleteTable;
	private JTable table;
	private int selectedRowCount;
	private ArrayList<String> fieldValues;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		DefaultTableModel model=new DefaultTableModel();
		deleteTable=new DeleteRowAction();
		selectedRowCount=2;
		fieldValues=new ArrayList<String>();
		
		model.addColumn("Column1");
		model.addColumn("Column2");
		model.addColumn("Column3");
		String[] fields1={"field1","field2","field3"};
		model.addRow(fields1);
		String[] fields2={"2Field1","2Field2","2Field3"};
		model.addRow(fields2);
		String[] fields3={"3Field1","3Field2","3Field3"};
		model.addRow(fields3);
		table=new JTable(model);
		
		fieldValues.add(fields3[0]);
		fieldValues.add(fields3[1]);
		fieldValues.add(fields3[2]);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSelectedRowToTableRowModel() {
		TableRowModel result=deleteTable.selectedRowToTableRowModel(table, selectedRowCount);
		assertEquals(fieldValues, result.getFieldValues());
	}

}
