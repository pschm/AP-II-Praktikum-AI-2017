package gui;

import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;

import app.Erzeuger;
import app.Produkt;
import app.Verwerter;

/**
 * Erstellen Sie eine Datenstruktur MaschinenPanel,
 * welche von JPanel erbt und f�r die Auflistung der Maschinenentw�rfe zust�ndig ist (Abbildungen 1, 2, 3 - rechte Seite).
 * In dieser Klasse werden Sie vermehrt in Kontakt mit der Verschachtelung von JPanel-Objekten kommen.
 * Orientieren Sie sich an Abbildung 3 und w�hlen Sie f�r jedes Panel ein geeignetes Layout. 
 */
@SuppressWarnings("serial")
public class MaschinenPanel extends JPanel {

	private PanelManager panelManager;
	
	public MaschinenPanel(PanelManager panelManager) {
		this.panelManager = panelManager;
		
		buildGui();
	}
	
	/**
	 * Hier sollten zun�chst alle Elemente innerhalb des MaschinenPanels
	 * entfernt werden.
	 */
	public void updateData() {
		this.removeAll(); // TODO kann eignentlich nicht richtig sein --> auch im AuswahlPanel aendern
		buildGui();
	}
	
	public void buildGui() {
		JPanel outerPanel = new JPanel();
		outerPanel.setLayout(new GridLayout(0, 1));
		
		JPanel headerPanel = new JPanel();
		headerPanel.setLayout(new GridLayout(1, 3));
		headerPanel.add(new JLabel("Maschine"));
		headerPanel.add(new JLabel("Erzeugnis:"));
		headerPanel.add(new JLabel("Abhaenigkeit:"));
		
		outerPanel.add(headerPanel);
		
		for(int i = 0; i < panelManager.getMaschinenEntwuerfe().size(); i++) {
			Erzeuger e = (Erzeuger) panelManager.getMaschinenEntwuerfe().get(i);
			
			// itemPanels erzeugen
			JPanel maschinenDaten      = new JPanel();
			JPanel erzeugnisDaten      = new JPanel();
			JPanel abhaengigkeitsDaten = new JPanel();
			
			// itemPannels fuellen
			maschinenDaten.setLayout(new GridLayout(3, 1));
			maschinenDaten.add(new JLabel("Typ: "    + e.getTyp()));
			maschinenDaten.add(new JLabel("Name: "   + e.getName()));
			maschinenDaten.add(new JLabel("Kosten: " + e.getKosten()));
			
			erzeugnisDaten.setLayout(new GridLayout(3, 1));
			erzeugnisDaten.add(new JLabel("Name: "               + e.getErzeugnis().getName()));
			erzeugnisDaten.add(new JLabel("Herstellungskosten: " + e.getErzeugnis().getKosten()));
			erzeugnisDaten.add(new JLabel("Verkaufswert: "       + e.getErzeugnis().getVerkaufswert()));
			
			if(e instanceof Verwerter) {
				Produkt p = ((Verwerter) e).getAbhaenigkeit();
				abhaengigkeitsDaten.setLayout(new GridLayout(4, 1));
				abhaengigkeitsDaten.add(new JLabel("Name: " + p.getName()));
				abhaengigkeitsDaten.add(new JLabel("Herstellungskosten: " + p.getKosten()));
				abhaengigkeitsDaten.add(new JLabel("Verkaufswert: " + p.getVerkaufswert()));
				abhaengigkeitsDaten.add(new JLabel("Anzahl: " + ((Verwerter)e).getAnzahl()));
			}
			
			// innerPanel
			JPanel maschinenPanel = new JPanel();
			
			// Layout festlegen
			maschinenPanel.setLayout(new GridLayout(1, 3));
			
			// itemPanels hinzufuegen
			maschinenPanel.add(maschinenDaten);
			maschinenPanel.add(erzeugnisDaten);
			maschinenPanel.add(abhaengigkeitsDaten);
			
			final int index = i; // sonst meckert der Mouse listener
			maschinenPanel.addMouseListener( new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					panelManager.addMaschineToFabrik(index);
				}
			});
			
			
			outerPanel.add(maschinenPanel); // in MouseListener??????
			
			
		} // Ende for (MaschinenEntwuerfe)
		
		this.add(outerPanel);
		revalidate();
	}

}
