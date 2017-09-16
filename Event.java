
/**
 * Die Klasse EVENT beinhaltet ein auszuführendes Event 
 * (Signalzustandsaenderungen), womit daruch Gatterlaufzeiten 
 * berücksichtigt werden können.
 * 
 * @author (Philipp Schneevoigt und Gerrit Buecken) 
 * @version (13.09.2017)
 */
public class Event
{
    private static EventQueue eventqueue;
    private Signal event_signal;
    private int event_time;
    private boolean event_value;
    /**
     * Konstruktor fuer Objekte der Klasse Event
     * Ein Event bedeutet, dass zu einem bestimmten Zeitpunkt 
     * ein bestimmter Zustand eines bestimmten Signals gesetzt wird
     */
    public Event(Signal event_signal, int event_time, boolean event_value)
    {
        this.event_signal = event_signal;
        this.event_time = event_time;
        this.event_value = event_value;
        
        eventqueue.addEvent(this);
    }

    /**
     *  Die Methode setEventQueue trägt die Eventqueue des Projektes 
     *  in ein Statisches Feld der Klasse Event ein.
     */
    public static void setEventQueue(EventQueue queue)
    {
        eventqueue = queue;
    }
    
    /**
     *  Die Methode getEventQueue trägt die Eventqueue des Projektes 
     *  in ein Statisches Feld der Klasse Event ein.
     */
    public static EventQueue getEventQueue()
    {
        return eventqueue;
    }    

    /**
     * Die Methode getEventTime liefert den Zeitpunkt des jeweiligen 
     * Events zurück.
     */
    public int getEventTime()
    {
        return event_time;
    }     
    
    /**
     * Die Methode getEventSignal liefert das zugehörige Signal des 
     * jeweiligen Events zurück.
     */
    public Signal getEventSignal()
    {
        return event_signal;
    }
    
    /**
     * Die Mothode propagate verwaltet die Signalzustandsänderungen (Events). 
     * Dabei wird beim propagieren grundsetzlich zwischen Finden eines 
     * eingeschwungenen Zustandes und einer aus dem Betrieb heraus 
     * auftretenden Signalzustandsänderung unterschieden.
     * 
     * es wird bei einem auftretenden Ereignis:
     *- die neue globale Schaltungszeit aktualisiert
     *- die Signalzustandsänderung an sich eingeleitet
     *- das Event aus der Eventliste gelöscht
     */
    public void propagate()
    {
        if(Event.getEventQueue().getEventQueueStatus() == false)
        {
            Event.getEventQueue().setEventQueueStatusActiv();
        }
        
        Event.getEventQueue().setNewActualTime(event_time);
        
        event_signal.setValue(event_value);
        
        Event.getEventQueue().removeEvent();
        
        /*
        // Testanzeige aller Events in der Liste nach dem Ausführen eines Events.
        Event.getEventQueue().showCompleteEventList();
        */
    }   
}
