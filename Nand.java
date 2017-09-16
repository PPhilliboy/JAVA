/**
 *  Die Klasse NAND beinhaltet die Berechnungsvorschrift einens NAND-Gatters
 *  mit variabler Eingangsanzahl.
 * es gibt zwei gespeicherte Variablen:
 *  - entrances: ein Array, welches Objekte vom Typ Signal beinhaltet;
 *    die Länge gibt die Anzahl der Eingänge an
 *  - exit: Typ Signal; ist das Ausgangssignal
 * @author (Philipp Schneevoigt und Gerrit Bücken) 
 * @version 12.09.2017
 */
public class Nand extends Gate
{
    /**
     * Konstruktor für Objekte der Klasse Nand
     * es wird eine Anzahl an Eingängen übergeben
     * es wird generell von einem Ausgang ausgegangen
     * es wird jedem Gatter eine Gatterlaufzeit zugeordnet
     */
    public Nand(int number_of_entrance, int delay_time)
    {
        super(number_of_entrance, delay_time);
    }
    
    /**
     * Die Methode calculate berechnet den Wert des AusgangsSignals mittels 
     * logischer Verknüpfung der Eingänge der erste Eingang wird mit dem zweiten
     * UND-Verknüpft, das Ergebnis wiederum mit dem nächsten Eingang etc.
     * wichtig ist hierbei das vorherige Setzen des Ergebnisses auf True
     * 
     * Unterschieden wird bei der Weitergabe, ob ein eingeschwungener Zuschand 
     * der Schaltung gesucht wird, oder ob die Schaltung aus dem Betrieb heraus 
     * sich ändert. Im letzteren Fall wird nur ein neues Schaltereignis erzeugt, wenn 
     * auch tatsaechlich eine Änderung vom vorherigen Gatterzustand zum Zustand 
     * durch die neue Eingangsbelegung hervorgerufen wird.
     */
    public void calculate()
    {
        boolean result = true;
        for( int i = 0; i < entrances.length; i++)
        {
            result = result && entrances[i].getValue();
        }
        result = !result;
        super.setResult(result);
    }
}
