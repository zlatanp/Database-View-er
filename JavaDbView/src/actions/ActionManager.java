
package actions;
/**
 * Klasa koja sadrzi sve akcije. Upravlja akcijama, obezbedjuje pronalazak odgovarajuće akcije.
 * 
 * @author Milena
 * @author user(Svetlana)
 *
 */
public class ActionManager {

	// toolbar
	private AboutAction aboutAction = null;

	// ToolBarParent & ToolBarChild
	private AddParentRowAction addRowParentAction = null;
	private AddChildRowAction addChildRowAction = null;
	private EditParentRowAction editTableAction = null;
	private EditChildRowAction editChildRowAction = null;
	private DeleteRowAction deleteTableAction = null;
	private DeleteChildRowAction deleteChildTableAction = null;
	private PromoteToParentAction promoteToParentAction = null;
	private PromoteToChildAction promoteToChildAction = null;

	private FilterParentTable filterParent = null;
	private FilterChildTable filterChild = null;

	private AluOxideAction aluOxideAction = null;
	private BlackEyeAction blackEyeAction = null;
	private BlackMoonAction blackMoonAction = null;

	// promena jezika
	private EnglishLanguageAction englishLanguageAction = null;
	private SerbianCyrlLanguageAction serbianCyrlLanguageAction = null;
	private SerbianLatinLanguageAction serbianLatinLanguageAction = null;

	// citanje seme
	private ReadFromSQLServerAction readSchema = null;

	public ActionManager() {
		initialise();
	}

	/**
	 * Metoda koja kreira akcije.
	 * 
	 */
	private void initialise() {
		// ToolBarParent & ToolBarChild
		addRowParentAction = new AddParentRowAction();
		addChildRowAction = new AddChildRowAction();
		editTableAction = new EditParentRowAction();
		editChildRowAction = new EditChildRowAction();
		deleteTableAction = new DeleteRowAction();
		deleteChildTableAction = new DeleteChildRowAction();
		promoteToParentAction = new PromoteToParentAction();
		promoteToChildAction = new PromoteToChildAction();

		filterParent = new FilterParentTable();
		filterChild = new FilterChildTable();

		aluOxideAction = new AluOxideAction();
		blackEyeAction = new BlackEyeAction();
		blackMoonAction = new BlackMoonAction();

		englishLanguageAction = new EnglishLanguageAction();
		serbianCyrlLanguageAction = new SerbianCyrlLanguageAction();
		serbianLatinLanguageAction = new SerbianLatinLanguageAction();

		aboutAction = new AboutAction();

		readSchema = new ReadFromSQLServerAction();
	}

	public AddParentRowAction getAddRowParentAction() {
		return addRowParentAction;
	}

	public EditParentRowAction getEditTableAction() {
		return editTableAction;
	}

	public DeleteRowAction getDeleteTableAction() {
		return deleteTableAction;
	}

	public PromoteToParentAction getPromoteToParentAction() {
		return promoteToParentAction;
	}

	public PromoteToChildAction getPromoteToChildAction() {
		return promoteToChildAction;
	}

	public AluOxideAction getAluOxideAction() {
		return aluOxideAction;
	}

	public BlackEyeAction getBlackEyeAction() {
		return blackEyeAction;
	}

	public BlackMoonAction getBlackMoonAction() {
		return blackMoonAction;
	}

	public EnglishLanguageAction getEnglishLanguageAction() {
		return englishLanguageAction;
	}

	public SerbianCyrlLanguageAction getSerbianCyrlLanguageAction() {
		return serbianCyrlLanguageAction;
	}

	public SerbianLatinLanguageAction getSerbianLatinLanguageAction() {
		return serbianLatinLanguageAction;
	}

	public AboutAction getAboutAction() {
		return aboutAction;
	}

	public AddChildRowAction getAddChildRowAction() {
		return addChildRowAction;
	}

	public DeleteChildRowAction getDeleteChildTableAction() {
		return deleteChildTableAction;
	}

	public void setDeleteChildTableAction(DeleteChildRowAction deleteChildTableAction) {
		this.deleteChildTableAction = deleteChildTableAction;
	}

	public FilterParentTable getFilterParent() {
		return filterParent;
	}

	public void setFilterParent(FilterParentTable filterParent) {
		this.filterParent = filterParent;
	}

	public FilterChildTable getFilterChild() {
		return filterChild;
	}

	public void setFilterChild(FilterChildTable filterChild) {
		this.filterChild = filterChild;
	}

	public ReadFromSQLServerAction getReadSchema() {
		return readSchema;
	}

	public void setReadSchema(ReadFromSQLServerAction readSchema) {
		this.readSchema = readSchema;
	}

	public EditChildRowAction getEditChildRowAction() {
		return editChildRowAction;
	}

}
