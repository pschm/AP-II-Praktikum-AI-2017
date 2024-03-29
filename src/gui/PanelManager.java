package gui;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Comparator;

import javax.swing.JFrame;

import app.Fabrik;
import app.Maschine;
import app.Maschinenliste;
import app.Produkt;

public class PanelManager {
	private ArrayList <Produkt> produktEntwuerfe;
	//private ArrayList <Maschine> maschinenEntwuerfe;
	private Maschinenliste maschinenEntwuerfe;
	private Fabrik fabrik;
	
	private InputPanel inputPanel;
	private MaschinenPanel maschinenPanel;
	private AuswahlPanel auswahlPanel;

	public PanelManager(Fabrik f) {
		produktEntwuerfe   = new ArrayList<Produkt>();
		//maschinenEntwuerfe = new ArrayList<Maschine>();
		maschinenEntwuerfe = new Maschinenliste();
	
		inputPanel     = new InputPanel(this);
		maschinenPanel = new MaschinenPanel(this);
		auswahlPanel   = new AuswahlPanel(this);
		
		fabrik = f;
	}
	
	public ArrayList<Produkt> getProduktEntwuerfe() {
		return produktEntwuerfe;
	}

	//public ArrayList<Maschine> getMaschinenEntwuerfe() {
	public Maschinenliste getMaschinenEntwuerfe() {
		return maschinenEntwuerfe;
	}
	
	/**
	 * Diese Methode erzeugt ein JFrame und bettet das InputPanel
	 * auf der linken Seite ein.
	 */
	public void start() {
		JFrame mainFrame = new JFrame();
		mainFrame.setSize(1000, 500);
		mainFrame.setLocation(50, 50);
		mainFrame.setVisible(true);
		
		mainFrame.setLayout(new BorderLayout());
		mainFrame.add(inputPanel, BorderLayout.WEST);
		mainFrame.add(maschinenPanel, BorderLayout.CENTER);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JFrame selectionFrame = new JFrame();
		selectionFrame.setVisible(true);
		selectionFrame.setSize(400, 500);
		selectionFrame.setLocation(1050, 50);
		selectionFrame.add(auswahlPanel);
	}

	/**
	 * Diese Methode nimmt ein Produkt (@param produkt) entgegen und
	 * fügt es der Liste von Produktentwürfen hinzu.
	 * Falls bereits ein Produkt mit dem Namen vorhanden ist,
	 * wird dieses lediglich ausgetauscht anstatt das neue Produkt hinzuzufügen.
	 */
	public void addOrReplaceProduktentwurf(Produkt produkt) {
		boolean contains = false;
		
		for(int i = 0; i < produktEntwuerfe.size(); i++) {
			if(produktEntwuerfe.get(i).getName().equals(produkt.getName()) ) {
				produktEntwuerfe.remove(i);
				produktEntwuerfe.add(i, produkt);
				contains = true;
				break;
			}
		}
		
		if( !contains ) produktEntwuerfe.add(produkt);
				
		inputPanel.updateData();
	}
	
	/**
	 * Identisch zu der addOrReplaceProduktentwurf, lediglich mit der Maschinenliste. 
	 */
	public void addOrReplaceMaschinenentwurf(Maschine maschine) {
		boolean contains = false;
		
		System.out.println(maschine.toString() + " (PanelManager/addOrReplace)");
		
		for(int i = 0; i < maschinenEntwuerfe.size(); i++) {
			if(maschinenEntwuerfe.get(i).getName().equals(maschine.getName())) {
				maschinenEntwuerfe.set(i, maschine);
				contains = true;
				break;
			}
		}
		
		if( !contains )	maschinenEntwuerfe.addLast(maschine);
		
		maschinenPanel.updateData();
		maschinenPanel.repaint();
	}
	
	// ==================================================
	// =	Methoden zur Kommunikation mit der Fabrik	=
	// ==================================================
	
	public void sort(Comparator<Maschine> comp) {
		fabrik.sort(comp);
		auswahlPanel.updateData();
	}
	
	/**
	 * Diese Methode fügt den Maschinenentwurf an der Position @param index der Fabrik hinzu.
	 */
	public void addMaschineToFabrik(int index) {
		fabrik.fuegeMaschineHinzu(maschinenEntwuerfe.get(index));
		
		inputPanel.updateData();
		maschinenPanel.updateData();
		maschinenPanel.repaint();
		auswahlPanel.updateData();
	}
	
	/**
	 * Entfernt die Maschine an der Position @param index aus der Fabrik
	 * (hier bezieht sich der Index auf die Position in der ArrayList der Fabrik
	 * und nicht die position in den Maschinenentwürfen). 
	 */
	public void removeMaschineFromFabrik(int index) {
		fabrik.entferneMaschine(index);
		
		inputPanel.updateData();
		maschinenPanel.updateData();
		maschinenPanel.repaint();
		auswahlPanel.updateData();
	}
	
	/**
	 * greift momentan lediglich auf die resetFabrik()-Methode der Fabrik zu.
	 */
	public void resetFabrik() {
		fabrik.resetFabrik();		
		inputPanel.updateData();
		auswahlPanel.updateData();
	}
	
	public void startFabrik(int runden) {
		fabrik.firmaTesten(runden);
	}
	
	/**
	 * @return: Gibt eine Liste der Maschinen zurück, die bereits der Firma hinzugefügt wurden.
	 */
	//public ArrayList<Maschine> getAktiveMaschinen() {
	public Maschinenliste getAktiveMaschinen() {
		return fabrik.getMaschine();
	}
	
	/**
	 * @return: Gibt das momentane Testguthaben der Fabrik zurück.
	 */
	public double getTestguthaben() {
		return fabrik.getTestguthaben();
	}
}
