
/**
 *  Die Klasse NAND beinhaltet die Berechnungsvorschrift einens NAND-Gatters mit variabler Eingangsanzahl.
 * 
 * @author (Ihr Name) 
 * @version 05.10.2016
 */
public class Nand
{
    private Signal[] entrances;     /** die Eingänge des Gatters vom Typ Signal */
    private Signal exit;            /** der Ausgabg des Gatters */
    /**
     * Konstruktor für Objekte der Klasse Nand
     */
    public Nand(int numbEntrance)
    {
        entrances = new Signal [numbEntrance];
    }

    /**
     * die Methode setInput verknüpft die Gatter untereinander und weist den Eingängen Signale zu
     * das Gatter weist sich selbst als Ziel eines anderen Gatters zu
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
     * calculate berechnet den Wert des AusgangsSignals mittels logischer Verknüpfung der Eingänge
     * der erste Eingang wird mit dem zweiten UND-Verknüpft, das Ergebnis wiederum mit dem nächsten Eingang etc.
     * wichtig ist hierbei das vorherige Setzen des Ergebnisses auf True
     */
    public void calculate()
    {
        boolean result = true;
        for( int i = 0; i<entrances.length; i++)
        {
            result = result & entrances[i].getValue();
        }
        result = !result;
        exit.setValue(result);
    }
}
