import java.util.ArrayList;
/**
 * Signal beinhaltet die Information über den Wert
 * weiterhin wird ist die Klasse für das Ausdrucken auf die Konsole verantwortlich
 * gespeicherte Variablen:
 *                        - name: Typ String; Name des Signals
 *                        - value: Typ boolean; Wert des Signals
 *                        - target_list: Typ ArrayList<Nand>; Liste, welche die
 *                                      Zielanschlüsse beinhaltet
 * @author (Ihr Name) 
 * @version (eine Versionsnummer oder ein Datum)
 */
public class Signal
{
    private String name;  // name des Signals, dient der identifikation
    private boolean value;  // Wert des Signals, kann etweder wahr oder falsch sein
    private ArrayList<Nand> target_list;  /** Liste, die die Zielanschlüsse des
                                              nächsetn Gatters enthält
                                            */
    /**
     * Konstruktor für Objekte der Klasse Signal
     */
    public Signal(String name)
    {
        this.name = name;  // setzt den Namen des Objekts Signal
        value = false;
        target_list = new ArrayList<Nand>();
    }

    /**
     * setValue setzt den neuen Wert des jeweiligen Signals mittels
     * berechnung und druckt die Ergebnisse des letzten Gatters aus
     */
    public void setValue(boolean value)
    {
        this.value = value;  // setzt den Wert des Objekts Signal
        /**
         * falls es keine Ziele mehr in der Liste gibt soll die Berechnung beendet
         * und das Ergebnis ausgedruckt werden
         */
        if (target_list.size() == 0)
        {
            System.out.println(name+ " -> "+value);
        }
        else
        {
            for (int i=0; i<target_list.size();i++)
            {
                target_list.get(i).calculate();
            }
        }
    }

    public boolean getValue()
    /**
     * gibt den Wert des Signals zurück
     * eine __getter__ Methode
     */
    {
        return value;
    }

    public void setTarget (Nand gate)
    /**
     * fügt ein Ziel (Gatter, hier NAND) hinzu, an welches das berechnete Signal
     * übergeben werden soll
     */
    {
        target_list.add(gate);
    }
}
