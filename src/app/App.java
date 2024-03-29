package app;

import gui.PanelManager;

/**
 * Anwendung zur Simulation einer Fabrik (Aufbau und Inbetriebnahme).
 * 
 * @author Philipp Schmeier
 * @version v1
 * @since 2017-06-06
 */
public class App {

	/**
	 * Dies ist die Main-Methode, welche eine neue Fabrik erstellt,
	 * dieser Fabrik drei Maschinen hinzufügt und diese anschließend zum laufen bringt.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// Fabrik
		Fabrik holz = new Fabrik(new Warenspeicher(), 1001.0, "Holz");

		Erzeuger e = new Erzeuger("erz1", 10.0, new Produkt("t1", 1.0, 5.0));
		holz.getMaschine().addFirst(e);
		System.out.println("Maschine anlegen: " + holz.getMaschine().getFirst().getName() + "hinzugefuegt.");
		System.out.println("Size: " + holz.getMaschine().size() );
		holz.getMaschine().remove(e);
		System.out.println("Size: " + holz.getMaschine().size() );

		
		
		PanelManager pm = new PanelManager(holz);
		pm.start();
		
//		// Maschinen
//		Erzeuger birnenpfluecker = new Erzeuger("Birnenpfluecker", 500.0, new Produkt("Brine", 0.815, 42.0));
//		Erzeuger apfelpfluecker  = new Erzeuger("Apfelpluecker", 450.0, new Produkt("Apfel", 0.415, 21.0));
//		Verwerter apfelpresse    = new Verwerter("Apfelpresse", 2.500, 2, new Produkt("Apfelsaft", 16.0, 25.0), new Produkt("Apfel", 0.415, 21.0));
//		
//		// Fabrik bestuecken
//		f.fuegeMaschineHinzu(birnenpfluecker);
//		f.fuegeMaschineHinzu(apfelpfluecker);
//		f.fuegeMaschineHinzu(apfelpresse);
//		
//		// Fabrik testen
//		double testguthaben = f.firmaTesten(3);
//		System.out.println("Das aktuelle Guthaben der Fabrik " + f.getName() + " betraegt: " + testguthaben );
	}

}
