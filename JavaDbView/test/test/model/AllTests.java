package test.model;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import test.actions.DeleteRowActionTest;
import test.data.reader.JSONDataReaderTest;
import test.tree.components.TableComponentTest;

@RunWith(Suite.class)
@SuiteClasses({ DeleteRowActionTest.class, JSONDataReaderTest.class, TabbedPaneModelTest.class,
				TableFieldModelTest.class, TableModelTest.class, TableRowModelTest.class,
				ValidationTest.class, TableComponentTest.class})
public class AllTests {

}
