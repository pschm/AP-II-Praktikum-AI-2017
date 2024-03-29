package app;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Maschinenliste implements Iterable<Maschine> {
	Node first;
	Node last;
	boolean isSorted ;
	int currentSize;

	public Maschinenliste() {
		first = null;
		last  = null;
		isSorted = true;
		currentSize = 0;
	}

	/**
	 * @return true, wenn die Liste leer ist.
	 */
	public boolean isEmpty() {
		return first == null;
	}

	/**
	 * erstellt einen neuen Knoten(Node) und f�gt diesen am Anfang
	 * @param maschine - Daten des neuen Knoten
	 */
	public void addFirst(Maschine maschine) {
		first = new Node(maschine, first);
		currentSize++;

		if(currentSize == 1) last = first;
	}

	/**
	 * erstellt einen neuen Knoten (Node) und f�gt diesen am Ende der Liste ein
	 * @param maschine - Daten des neuen Knoten
	 */
	public void addLast(Maschine maschine) {
		if(isEmpty()) addFirst(maschine);
		else {
			Node tmp = last;
			last = new Node(maschine, null);
			tmp.next = last;
			currentSize++;
		}
	}

	/**
	 * @return Anzahl der Elemente in der Liste.
	 */
	public int size() {
		//return currentSize;
		if(isEmpty()) return 0;
		else return size(first);
	}

	public int size(Node tmp) {
		if(tmp.next != null) return size(tmp.next) + 1;
		else return 1;
	}

	// Methode zum Sortieren
	public void quickSort (Comparator <Maschine> comp) {
		// Abbruchbedingung der Rekursion. Eine Liste mit
		// 0 oder 1 Element ist immer sortiert
		if (size() < 2 ) return;
		// Referenzgr��e
		Maschine pivot = getFirst();

		// 3 Listen. Darin landen Elemente, die kleiner, gleich
		// oder gr��er im Vergleich zur Referenzgr��e sind:
		Maschinenliste less = new Maschinenliste ();
		Maschinenliste equal = new Maschinenliste();
		Maschinenliste greater = new Maschinenliste();

		// Durchlaufe alle Elemente dieser Liste und sortiere sie
		// in less, equal und greater ein.
		for (Maschine element : this ) {
			// Wo soll es einsortiert werden ???
			int compared = comp.compare(element, pivot);

			if (compared < 0 ) {
				less.addFirst(element);
			} else if (compared > 0 ) {
				greater.addFirst(element);
			} else {
				equal.addFirst(element);
			}
		}

		// Rekursiver Aufruf f�r die unsortierten Teillisten less und
		// greater. Achtung: equal enth�lt nur gleiche Elemente und
		// darf nicht noch einmal sortiert werden (Endlosrekursion!)
		less.quickSort(comp);
		greater.quickSort(comp);

		// Wir entfernen alle Elemete dieser Liste...
		clear ();

		// ... um nacheiander die sortierten Elemente der Teilliste
		// wieder hinzuzuf�gen
		for (Maschine element : less) {
			addLast (element);
		}

		for (Maschine element : equal) {
			addLast (element);
		}

		for (Maschine element : greater) {
			addLast (element);
		}
	}

	/**
	 * durchsucht die gesamte Liste nach dem Knoten mit der angegebenen @param maschine und gibt deren @return Index aus.
	 * Wird kein Knoten mit der entsprechenden Maschine gefunden, so soll der Wert -1 ausgegeben werden.
	 */
	public int indexOf(Maschine maschine) {
		if(isEmpty()) return -1; // Liste leer, maschine nicht vorhanden
		Node runPointer = first;

		for(int i = 0; runPointer.next != null; i++) {
			if(runPointer.maschine != null && runPointer.maschine.equals(maschine))
				return i;
			runPointer = runPointer.next;
		}

		return -1; // Element nicht in der Liste
	}

	/**
	 * @return Maschine des Knoten an der Stelle @param index zur�ck.
	 * Ist die Maschinenliste leer, so soll eine NoSuchElementException geworfen werden.
	 * Ist der Wert von index nicht innerhalb des m�glichen Bereiches (gr��er als die L�nge der Liste oder kleiner als 0),
	 * so soll eine IndexOutOfBoundsException geworfen werden.
	 */
	public Maschine get(int index) {
		if(isEmpty()) throw new NoSuchElementException();
		if(index > currentSize - 1 || index < 0) throw new IndexOutOfBoundsException();

		Node runPointer = first;	
		if(currentSize == 1 && index == 0) {
			return first.maschine;
		}

		int i = 0;
		while( runPointer != null ) {
			if(index == i) {return runPointer.maschine;}
			runPointer = runPointer.next;
			i++;
		}

		throw new NoSuchElementException();
	}

	/**
	 * @return Maschine des ersten Knotens zur�ck
	 */
	public Maschine getFirst() {
		if(first != null && first.maschine != null)
			return first.maschine;
		else throw new NoSuchElementException();
	}

	/**
	 * ermittelt den Knoten an der Stelle @param index und tauscht dort den Inhalt mit der neuen @param maschine aus.
	 * Ist die Maschinenliste leer, so soll eine NoSuchElementException geworfen werden.
	 * Ist der Wert von index nicht innerhalb des m�glichen Bereiches (gr��er als die L�nge der Liste oder kleiner als 0),
	 * so soll eine IndexOutOfBoundsException geworfen werden.
	 */
	public void set(int index, Maschine maschine) {
		if(isEmpty()) throw new NoSuchElementException();
		if(index > currentSize - 1 || index < 0) throw new IndexOutOfBoundsException();

		Node runPointer = first;	

		for(int i = 0; runPointer.next != null; i++) {
			if(index == i) {
				runPointer.maschine = maschine;
				return;
			}
			runPointer = runPointer.next;
		}
	}

	/**
	 * Entfernt den Knoten an der Stelle @param index aus der Liste.
	 * Ist die Maschinenliste leer, so soll eine NoSuchElementException geworfen werden.
	 * Ist der Wert von index nicht innerhalb des m�glichen Bereiches, so soll eine IndexOutOfBoundsException geworfen werden.
	 */
	public void remove(int index) {
		if(isEmpty()) throw new NoSuchElementException();
		if(index > currentSize - 1 || index < 0) throw new IndexOutOfBoundsException();

		// spezialfall index 0
		if(index == 0) {
			first = first.next;
			currentSize--;
			return;
		}

		Node runPointer = first;	
		Node prevNode   = first;

		for(int i = 0; runPointer.next != null; i++) {
			if(index == i) {
				prevNode.next = runPointer.next;
				currentSize--;
				return;
			}
			prevNode = runPointer;
			runPointer = runPointer.next;
		}
	}

	/**
	 * itieriert �ber die gesamte Liste und entfernt ALLE Knoten,
	 * deren Maschine mit der angegebenen Maschine @param m �bereinstimmen.
	 */
	public void remove(Maschine m) {
		if(isEmpty()) throw new NoSuchElementException();

		Node runPointer = first;

		// spezialfall index 0
		if(first.maschine.equals(m)) {
			first = first.next;
			currentSize--;
		}

		while(runPointer.next != null) {
			if(runPointer.next.maschine != null && runPointer.next.maschine.equals(m)) {
				runPointer.next = runPointer.next.next;
				currentSize--;
			}
		}
	}

	/**
	 * Entfernt alle Knoten aus der Liste
	 */
	public void clear() {
		first       = null;
		last        = null;
		isSorted    = true;
		currentSize = 0;
	}

	/**
	 * f�gt die @param neueMaschine in die Liste ein. Dabei soll das Ergebnis des @param comp f�r die Sortierung genutzt werden.
	 * (Schauen Sie sich hierzu das Beispiel im Skript an). Zu verwendende Comparator werden in dem n�chsten Aufgabenteil erstellt.
	 */
	public void addSorted(Maschine neueMaschine, Comparator<Maschine> comp) {
		if(!isSorted) throw new IllegalStateException(); // in eine unsortierte Liste kann nichts sortiert eingef�gt werden.

		if(isEmpty() || comp.compare(neueMaschine, getFirst()) <= 0 ) addFirst(neueMaschine);
		else {
			Node runPointer = first;
			while(runPointer.next != null &&
					runPointer.next.maschine != null &&
					comp.compare(neueMaschine, runPointer.next.maschine) > 0 ) {
				runPointer = runPointer.next;
			}

			runPointer.next = new Node(neueMaschine, runPointer.next);
			currentSize++;
		}

	}

	/**
	 * sortiert die Liste anhand des Comparators @param comp. �berlegen Sie besonders hier,
	 * welche Methoden Sie bereits erstellt haben und wiederverwenden k�nnen.
	 */
	public void sort(Comparator<Maschine> comp) {
		Node tmp = first;

		clear(); // unsortierte Liste leeren

		while(tmp != null) {
			addSorted(tmp.maschine, comp); // alle Elemente neue einf�gen (sortiert)
			tmp = tmp.next;
		}
	}

	private class Node {
		private Maschine maschine;
		private Node next = null;

		private Node(Maschine m, Node next) {
			this.maschine = m;
			this.next     = next;
		}
	}

	/**
	 * In dieser Methode m�ssen Sie ein neues Iterator<Maschine>-Objekt anlegen und zur�ckgeben.
	 * Dieses Objekt ben�tigt zun�chst einen Startknoten, welcher auf den Startknoten der Liste gesetzt wird.
	 * Anschlie�end m�ssen in diesem Objekt zwei weitere Funktionen definiert werden...
	 */
	@Override
	public Iterator<Maschine> iterator() {
		Iterator<Maschine> it = new Iterator<Maschine>() {
			private Node nextNode = first;

			/**
			 * @return true, wenn sich noch weitere Elemente in der Liste befinden (ansonsten false)
			 */
			@Override
			public boolean hasNext() {
				return nextNode != null;
			}

			@Override
			/**
			 * @return die Maschine des Knotens und setzt den aktuellen auf den n�chsten Knoten.
			 * Ist der aktuelle Knoten leer, so soll eine NoSuchElementException geworfen werden.
			 */
			public Maschine next() {
				if(!hasNext()) throw new NoSuchElementException();

				Maschine next = nextNode.maschine; // Maschine merken

				nextNode = nextNode.next; // neuen n�chsten Knoten merken

				return next;
			}

		};

		return it;
	}
}
