import java.util.ArrayList;
import java.util.Iterator;

/**
 * Klasse Event
 */
public class Event
{
    // Instanzvariablen
    private static EventQueue eventQueue;
    
    private Signal signal;
    private int zeitpunkt;
    private boolean newValue;

    
    //Konstruktor
    /**
     * Constructor for objects of class Event
     */
    public Event(Signal signal, int zeitpunkt, boolean newValue)
    {
        this.signal = signal;
        this.zeitpunkt = zeitpunkt;
        this.newValue = newValue;
        
        eventQueue.addEvent(this);
    }

    
    //Methoden
    /**
     * Methode setEventQueue
     */
    public static void setEventQueue(EventQueue e)
    {
        eventQueue = e;
    }
    
    /**
     * Methode getEventSignal
     */
    public Signal getEventSignal()
    {
       return signal;
    }
    
    /**
     * Methode getEventZeitpunkt
     */
    public int getEventZeitpunkt()
    {
       return zeitpunkt;
    }
    
    /**
     * Methode getEventValue
     */
    public boolean getEventValue()
    {
       return newValue;
    }
    
    /**
     * Methode propagate
     */
    public void propagate()
    {
       signal.setValue(newValue);
    }
}
