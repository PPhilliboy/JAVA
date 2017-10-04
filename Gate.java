
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
    protected Signal[] exits;  
    protected boolean old_exit_value;
    private int delay_time;
    private boolean feedback_protection; 
    public Gate(int number_of_entrance, int number_of_exits, int delay_time)
    {
        entrances = new Signal [number_of_entrance];
        exits = new Signal [number_of_exits];
        old_exit_value = false;
        this.delay_time = delay_time; 
        feedback_protection = false;
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
    public void setOutput (int exit, Signal signal)
    {
        exits[exit] = signal;
    }

    /**
     * Die Methode setResult leiter entweder den Steady-State-Einschwingung 
     * weiter, oder erstellt im Betreibsmodus neue Events.
     */
    protected void setResult(boolean result)
    {
        if(Event.getEventQueue().getEventQueueStatus() == false)
        {
            if(feedback_protection == false)
            {
                //feedback_protection = true;
                
                old_exit_value = result;
    
                exits[0].setValue(result);
                
                if(exits.length == 2)
                {
                    if(exits[1] != null)
                    {
                        exits[1].setValue(!result);
                    }
                }
                
                feedback_protection = false;
            }    
        }

        if((Event.getEventQueue().getEventQueueStatus() == true) && (result != old_exit_value))
        {
            old_exit_value = result;

            int new_event_time = Event.getEventQueue().getFirst().getEventTime() + delay_time;

            new Event(exits[0], new_event_time, result);
            
            if(exits.length == 2)
            {
                if(exits[1] != null)
                {
                    new Event(exits[1], new_event_time, !result);
                }
            }
        }
    }

    abstract public void calculate();

}
