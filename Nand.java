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
public class Nand
{
    private Signal[] entrances;    
    private Signal exit;  
    private boolean old_exit_value;
    private int delay_time;
    /**
     * Konstruktor für Objekte der Klasse Nand
     * es wird eine Anzahl an Eingängen übergeben
     * es wird generell von einem Ausgang ausgegangen
     * es wird jedem Gatter eine Gatterlaufzeit zugeordnet
     */
    public Nand(int number_of_entrance, int delay_time)
    {
        entrances = new Signal [number_of_entrance];
        old_exit_value = false;
        this.delay_time = delay_time; 
    }

    /**
     * Die Methode setInput verknüpft die Gatter untereinander und weist den
     * Eingängen Signale zu das Gatter weist sich selbst als Ziel eines
     * anderen Gatters zu
     */
    public void setInput (int entrance,Signal signal)
    {
        entrances[entrance] = signal;
        signal.setTarget(this);
    }

    /**
     *  Die Methode setOutput weist dme Gatter das Ausgangssignal zu
     */
    public void setOutput (Signal signal)
    {
        exit = signal;
    }

    /**
     * Diue Methode calculate berechnet den Wert des AusgangsSignals mittels 
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
        
        if(Event.getEventQueue().getEventQueueStatus() == false)
        {
            old_exit_value = result;
            
            exit.setValue(result);
        }
        
        if((Event.getEventQueue().getEventQueueStatus() == true) && (result != old_exit_value))
        {
            old_exit_value = result;
            
            int new_event_time = Event.getEventQueue().getFirst().getEventTime() + delay_time;
            
            new Event(exit, new_event_time, result);
        }
    }
}
