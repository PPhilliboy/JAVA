
/**
 *  Die Klasse NAND beinhaltet die Berechnungsvorschrift einens NAND-Gatters
 *  mit variabler Eingangsanzahl.
 * es gibt zwei gespeicherte Variablen:
 *  - entrances: ein Array, welches Objekte vom Typ Signal beinhaltet;
 *    die Länge gibt die Anzahl der Eingänge an
 *  - exit: Typ Signal; ist das Ausgangssignal
 * @author (Phillip Schneevoigt und Gerrit Bücken) 
 * @version 12.09.2017
 */
public class Nand
{
    private Signal[] entrances;    
    private Signal exit;            
    /**
     * Konstruktor für Objekte der Klasse Nand
     * es wird eine Anzahl an Eingängen übergeben
     * es wird generell von einem Ausgang ausgegangen
     */
    public Nand(int number_of_entrance)
    {
        entrances = new Signal [number_of_entrance];
    }

    /**
     * die Methode setInput verknüpft die Gatter untereinander und weist den
     * Eingängen Signale zu das Gatter weist sich selbst als Ziel eines
     * anderen Gatters zu
     */
    public void setInput (int entrance,Signal signal)
    {
        entrances[entrance] = signal;
        signal.setTarget(this);
    }

    /**
     *  die Methode setOutput weist das Ausgangssignal zu
     */
    public void setOutput (Signal signal)
    {
        exit = signal;
    }

    /**
     * calculate berechnet den Wert des AusgangsSignals mittels logischer
     * Verknüpfung der Eingänge der erste Eingang wird mit dem zweiten
     * UND-Verknüpft, das Ergebnis wiederum mit dem nächsten Eingang etc.
     * wichtig ist hierbei das vorherige Setzen des Ergebnisses auf True
     */
    public void calculate()
    {
        boolean result = true;
        for( int i = 0; i < entrances.length; i++)
        {
            result = result & entrances[i].getValue();
        }
        result = !result;
        exit.setValue(result);
    }
}
