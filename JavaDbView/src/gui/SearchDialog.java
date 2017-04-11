package gui;


import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.jdatepicker.impl.*;


import model.TableFieldModel;
import model.TableModel1;
import view.TableView1;

/**
 * Klasa koja modeluje dijalog za pretragu.
 * 
 * @author Zlatan
 *
 */
public class SearchDialog extends JDialog{

	private static final long serialVersionUID = -1168498898051808200L;
	
	/**
	 * Polje koje predstavlja prikaz tabele koja se pretrazuje.
	 */
	private TableView1 tableView;
	
	/**
	 * Polje koje predstavlja model tabele koja se pretrazuje.
	 */
	private TableModel1 tableModel;
	
	/**
	 * Polje koje predstavlja listu svih polja tabele koje se pretrazuje.
	 */
	private ArrayList<TableFieldModel> polja;
	
	/**
	 * Polje koje predstavlja listu naziva svih polja odnosno labela za njihov prikaz.
	 */
	private ArrayList<JLabel> listaLabela;
	
	/**
	 * Polje koje predstavlja listu pomocnih komponenti za prikaz da bi korisnik bolje razumeo popunjavanje forme.
	 */
	private ArrayList<JComponent> listaKomponentiLeva;
	
	/**
	 * Polje koje predstavlja listu GUI komponnenti - kontrola za unos.
	 */
	private ArrayList<JComponent> listaKomponenti;
	
	/**
	 * Polje koje predstavlja dodatnu listu GUI komponnenti - kontrola za unos.
	 */
	private ArrayList<JComponent> listaKomponentiDesna;
	
	public SearchDialog(TableView1 tableView) {
		this.tableView = tableView;
		this.tableModel = tableView.getTableModel();
		this.polja = tableModel.getPolja();
		listaLabela = new ArrayList<JLabel>();
		listaKomponentiLeva = new ArrayList<JComponent>();
		listaKomponenti = new ArrayList<JComponent>();
		listaKomponentiDesna = new ArrayList<JComponent>();
		
		for(int i=0;i<polja.size();i++){
			String ime = polja.get(i).getLabel();
			JLabel labela = new JLabel(ime);
			listaLabela.add(labela);
			
			String tip = polja.get(i).getType();
			if(tip.contains("char")){
				JTextField od = new JTextField();
				JLabel deo = new JLabel("Unesite deo ili celu vrednost:");
				JLabel prazno = new JLabel();
				listaKomponentiLeva.add(deo);
				listaKomponenti.add(od);
				listaKomponentiDesna.add(prazno);
				
			}else if(tip.contains("image")){
				JLabel praznaLeva = new JLabel();
				JLabel prazno = new JLabel();
				JLabel praznoDesno = new JLabel();
				listaKomponentiLeva.add(praznaLeva);
				listaKomponenti.add(prazno);
				listaKomponentiDesna.add(praznoDesno);
			}else if(tip.contains("int") || tip.contains("numeric")){
				JLabel obavestenje = new JLabel("Unesite opseg (OD-DO):");
				JTextField od = new JTextField();
				JTextField doo = new JTextField();
				listaKomponentiLeva.add(obavestenje);
				listaKomponenti.add(od);
				listaKomponentiDesna.add(doo);
			}else if(tip.contains("datetime")){
				JLabel obavestenje = new JLabel("Unesite opseg (OD-DO):");
				
				
				UtilDateModel model = new UtilDateModel();

				Properties p = new Properties();
				p.put("text.today", "Today");
				p.put("text.month", "Month");
				p.put("text.year", "Year");
				
				JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
				JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
				datePicker.setBounds(220,350,180,50);

				
				UtilDateModel modelDo = new UtilDateModel();
				
				JDatePanelImpl datePanelDo = new JDatePanelImpl(modelDo, p);
				JDatePickerImpl datePickerDo = new JDatePickerImpl(datePanelDo, new DateLabelFormatter());
				listaKomponentiLeva.add(obavestenje);
				listaKomponenti.add(datePicker);
				listaKomponentiDesna.add(datePickerDo);
			}
			
		}
		
		for(int i =0;i<listaLabela.size();i++){
			add(listaLabela.get(i));
			add(listaKomponentiLeva.get(i));
			add(listaKomponenti.get(i));
			add(listaKomponentiDesna.get(i));
			}
		
		JLabel razmak1 = new JLabel();
		JLabel razmak2 = new JLabel();
		JLabel razmak3 = new JLabel();
		JLabel razmak4 = new JLabel();
		JLabel razmak5 = new JLabel();
		JButton potvrdi = new JButton(MainFrame.getInstance().getResourceBundle().getString("Search"));
		JButton otkazi = new JButton(MainFrame.getInstance().getResourceBundle().getString("Otkazi"));
		
		otkazi.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);

			}
		});
		
		add(razmak1);
		add(razmak2);
		add(razmak3);
		add(razmak4);
		add(razmak5);
		add(potvrdi);
		add(otkazi);
		
		
		this.setSize(600, 300);
		this.setLayout(new GridLayout(0, 4));
		this.setLocationRelativeTo(null);
		this.pack();
				
	}
}
