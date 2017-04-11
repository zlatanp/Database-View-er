package contoller;

import java.util.HashMap;

import model.TableModel1;

/**
 * Interfejs za pracene promena na GUI komponentama i registrovanje odredjenih dogadjaja.
 * 
 * @author Jasmina
 *
 */
public interface IUpdateable {

	
	
    /**
     * Metoda koja se poziva prilikom promene selekcije na stablu.
     * 
     * @param table
     * 			- {@link TableModel1 model} tabele koja je selektovana
     * 
     * @param children
     * 			- {@link HashMap mapa} modela tabela koje su deca-podredjena za selektovanu tabelu.
     */
	public void updateForTreeChange(TableModel1 table, HashMap<String, TableModel1> children);

	
	/**
	 * Metoda koja registruje promene na selekciji u okviru prikaza roditeljskih-parent tabela.
	 * 
	 * @param parentTable
	 * 			- {@link TableModel1 model} tabele koja je trenutno u selektovanom tabu.
	 */
	public void updateForTopTabChanged(TableModel1 parentTable);

	
	/**
	 * Metoda koja registruje promene na selekciji u okviru prikaza podredjenih-child tabela.
	 * 
	 * @param childTable
	 * 			- {@link TableModel1 model} tabele koja je trenutno u selektovanom tabu.
	 */
	public void updateForBottomTabChanged(TableModel1 childTable);

	
	/**
	 * Metoda koja sluzi za osvezavanje prikaza u donjem delu radnog prostora (delu koji sluzi za prikaz podredjenih tabela).
	 * 
	 * @param parentTable
	 * 			- {@link TableModel1 model} tabele koja je trenutno selektovani roditelj, parent(nadredjena).
	 */
	public void izmeniPrikazDece(TableModel1 parentTable);
}
