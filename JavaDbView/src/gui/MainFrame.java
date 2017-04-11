package gui;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;

import actions.ActionManager;
import contoller.UpdateManager;
import data.reader.DBDataHandler;
import data.reader.JSONDataReader;
import listeners.MainWindowListener;
import tree.WorkspaceModel;
import tree.WorkspaceTree;
import view.WorkspaceView;

/**
 * Klasa koja predstavlja glavni prozor aplikacije.
 * 
 * @author Jasmina
 * @author Milena
 * @author Svetlana
 * @author Zlatan
 */
public class MainFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -58514644322375822L;
	
	/**
	 * Instanca klase.
	 */
	private static MainFrame instance = null;
	
	/**
	 * Glavni meni aplikacije.
	 */
	private MenuBar menuBar = null;
	
	/**
	 * Glavni toolbar aplikacije.
	 */
	private Toolbar toolbar = null;

	
	/**
	 * Polje koje predstavlja radni prostor aplikacije.
	 */
	private WorkspaceView workspaceView;

	/**
	 * Polje koje predstavlja citac seme baze iz .json fajla.
	 */
	private JSONDataReader jsonDataReader;
	
	/**
	 * Polje koje predstavlja model za stablo seme.
	 */
	private WorkspaceModel workspaceModel;
	
	/**
	 * Polje koje predstavlja prikaz seme u vidu stabla u aplikaciji.
	 */
	private WorkspaceTree tree = null;
	
	/**
	 * Polje koje predstavlja menadzera za akcije (sve na jednom mestu).
	 */
	private ActionManager actionManager = null;
	
	/**
	 * Polje koje predstavlja menadzera za promene na ostalim GUI komponentama.
	 */
	private UpdateManager updateManager = null;

	/**
	 * Polje koje modeluje podrsku za lokalizaciju.
	 */
	private ResourceBundle resourceBundle;

	/**
	 * Polje koje modeluje konekciu ka bazi
	 */
	private static Connection dbConnection = null;
	
	/**
	 * Polje koje modeluje centralizaciju operacija ka bazi.
	 */
	private DBDataHandler dbDataHandler = null;

	/**
	 * Polje koje govori da li je aktiviran jezik engleski.
	 */
	private boolean isEnglish = false;
	
	/**
	 * Polje koje govori da li je aktiviran jezik srpski.
	 */
	private boolean isSerbian = true;

	/**
	 * Polje koje govori da li je aktivirano citanje seme baze iz sql servera.
	 */
	private boolean isSQLReader = false;
	
	/**
	 * Polje koje modeluje dijalog za logovanje na pocetku aplikacije.
	 */
	private NoviLoginDijalog loginDijalog;
	
	private MainFrame() {
		jsonDataReader = new JSONDataReader("resources/stari.json");
		updateManager = new UpdateManager();
		dbDataHandler = new DBDataHandler();

		Locale.setDefault(new Locale("sr", "RS"));
		resourceBundle = ResourceBundle.getBundle(
				"gui.MessageResources.MessageResources", Locale.getDefault());

	}

	/**
	 * Metoda koja dobavlja instancu klase main frame ako postoji,
	 * ako ne postoji kreira je. - Singleton sablon.
	 * 
	 * @return
	 * 		- {@link MainFrame glavni prozor aplikacije}
	 */
	public static MainFrame getInstance() {
		if (instance == null) {
			instance = new MainFrame();
			try {
				instance.initialise();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return instance;
	}

	/**
	 * Metoda koja sluzi za dobavljanje konekcije ka bazi.
	 * 
	 * @return
	 * 		- {@link Connection konekcija} koja je aktivna i otvorena.
	 */
	public static Connection getConnection() {
		if (dbConnection == null) {
			try {
				dbConnection = DriverManager.getConnection(
						"jdbc:jtds:sqlserver://147.91.175.155/psw-2016-tim7-2",
						"psw-2016-tim7-2", "tim7-29676185");
//				dbConnection = DriverManager.getConnection(
//						"jdbc:jtds:sqlserver://192.168.77.230/psw-2016-tim7-2",
//						"psw-2016-tim7-2", "tim7-29676185");
			} catch (SQLException e) {
				DialogUpozorenje upozorenje = new DialogUpozorenje();
				upozorenje.setVisible(true);
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return dbConnection;
	}
	
	/**
	 * Metoda koja sluzi za namestanje nove konekcije u slucaju da korisnik zeli da promeni konekciju ka nekoj drugoj bazi.
	 * 
	 * @param url
	 * 		- url konekcije
	 * @param uname
	 * 		- korisnicko ime
	 * @param pass
	 * 		- lozinka za konekciju
	 */
	public static void setNewConnection(String url, String uname, String pass){
		if(dbConnection != null){
			try{
				dbConnection = DriverManager.getConnection("jdbc:jtds:sqlserver://"+url, uname, pass);
				
			} catch (SQLException e){
				DialogUpozorenje upozorenje = new DialogUpozorenje(e.getMessage());
				upozorenje.setVisible(true);
			}
		}
	}

	/**
	 * Metoda koja inicijalizuje delove glavnog prozora.
	 * 
	 * @throws IOException
	 */
	private void initialise() throws IOException {
		setTitle(resourceBundle.getString("naslovAplikacije"));

		loginDijalog = new NoviLoginDijalog(this, true);
		loginDijalog.setVisible(true);
		
		// Podesavanje velicine i pozicije prozora
		Toolkit kit = Toolkit.getDefaultToolkit();
		int height = kit.getScreenSize().height;
		int width = kit.getScreenSize().width;
		setSize((int) (width * 0.6), (int) (height * 0.8));
		setLocationRelativeTo(null);

		// Postavljanje ikone prozora
		Image img = kit.getImage("images/dbviewicon.png");
		setIconImage(img);

		// ActionManager
		actionManager = new ActionManager();

		// Dodavanje komponenti prozora

		// Menu
		menuBar = new MenuBar();
		setJMenuBar(menuBar);

		// Toolbar
		toolbar = new Toolbar();
		add(toolbar, BorderLayout.NORTH);

		

		initTree();


		// Split pane
		JScrollPane scrollPane = new JScrollPane(tree);
		JSplitPane splitpane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				scrollPane, workspaceView);

		add(splitpane, BorderLayout.CENTER);

		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		splitpane.setDividerLocation(0.2); // promeni kad dodas login

		jsonDataReader.parseJson(); // parsiranje se obavlja prilikom kreiranja

		tree.translate();

		dbConnection = getConnection();
		
		addWindowListener(new MainWindowListener());
	}

	/**
	 * Pomocna metoda koja sluzi za inicijalizaciju stabla.
	 */
	public void initTree() {
		tree = new WorkspaceTree();
		workspaceModel = new WorkspaceModel();
		tree.setModel(workspaceModel);
		workspaceView = new WorkspaceView();
	}

	public JSONDataReader getJsonDataReader() {
		return jsonDataReader;
	}

	public WorkspaceTree getTree() {
		return tree;
	}

	public void setTree(WorkspaceTree tree) {
		this.tree = tree;
	}

	public WorkspaceModel getWorkspaceModel() {
		return workspaceModel;
	}

	public void setWorkspaceModel(WorkspaceModel workspaceModel) {
		this.workspaceModel = workspaceModel;
	}

	public ActionManager getActionManager() {
		return actionManager;
	}

	public WorkspaceView getWorkspaceView() {
		return workspaceView;
	}

	public void setWorkspaceView(WorkspaceView workspaceView) {
		this.workspaceView = workspaceView;
	}

	public UpdateManager getUpdateManager() {
		return updateManager;
	}

	public void setUpdateManager(UpdateManager updateManager) {
		this.updateManager = updateManager;
	}

	public ResourceBundle getResourceBundle() {
		return resourceBundle;
	}

	/**
	 * Metoda koja se poziva prilikom promene lokala.
	 */
	@SuppressWarnings("static-access")
	public void changeLanguage() {
		
		if (isEnglish) {
			dbDataHandler.setDateFormat(dbDataHandler.ENGLISH_DATE_FORMAT);
		} else {
			dbDataHandler.setDateFormat(dbDataHandler.SERBIAN_DATE_FORMAT);
		}

		resourceBundle = ResourceBundle.getBundle(
				"gui.MessageResources.MessageResources", Locale.getDefault());
		// setTitle(resourceBundle.getString("naslovAplikacije"));
		menuBar.initComponents();
		tree.translate();
		workspaceView.translate();
	}
	

	public DBDataHandler getDbDataHandler() {
		return dbDataHandler;
	}

	public void setDbDataHandler(DBDataHandler dbDataHandler) {
		this.dbDataHandler = dbDataHandler;
	}

	public boolean isEnglish() {
		return isEnglish;
	}

	public void setEnglish(boolean isEnglish) {
		this.isEnglish = isEnglish;
	}

	public boolean isSerbian() {
		return isSerbian;
	}

	public void setSerbian(boolean isSerbian) {
		this.isSerbian = isSerbian;
	}

	public boolean isSQLReader() {
		return isSQLReader;
	}

	public void setSQLReader(boolean isSQLReader) {
		this.isSQLReader = isSQLReader;
	}
	
	
}

