
/**
 * Beschreiben Sie hier die Klasse Gate.
 * 
 * @author (Philipp Schneevoigt und Gerrit Bücken) 
 * @version (15.09.17)
 */
public abstract class Gate
/**
 * Gate ist die Superklasse zu allen vorhandenen Gattern
 * Sie beinhaltet jegliche Kommuniation mit den anderen Klassen des Programms
 * Ausschließlich die Berechnungsvorschrift wird von den Subklassen überschrieben
 * Gate ist abstract, da kein Objekt der Klasse erzeugt werden soll
 */
{
    protected Signal[] entrances;    
    private Signal exit;  
    protected boolean old_exit_value;
    private int delay_time;
    public Gate(int number_of_entrance, int delay_time)
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
     * eine Methode, die je nach status die Events zur weiteren Abarbeitung erzeugt
     */
    protected void set_result(boolean result)
    {
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

    abstract public void calculate();

}
