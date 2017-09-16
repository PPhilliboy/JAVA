import java.util.LinkedList;
/**
 * Die Klasse EVENTQUEUE verwaltet die Warteschlange für 
 * die auftretenden Events (Signalzustandsaenderungen)
 * 
 * @author (Philipp Schneevoigt und Gerrit Buecken) 
 * @version (13.09.2017)
 */
public class EventQueue
{    
    private LinkedList event_list;
    private boolean event_queue_status;
    private static int actual_time;
    /**
     * Konstruktor fuer Objekte der Klasse Eventqueue
     */
    public EventQueue()
    {
        event_list = new LinkedList<Event>();
        event_queue_status = false;
        actual_time = 0;
    }

    /**
     * Die Methode addEvent ist dafür zustaendig, dass die Events 
     * sich selber in die Eventqueue des Projektes an der richtigen 
     * Stelle einsortieren.
     */
    public void addEvent(Event event)
    {
        boolean event_added = false;
        if(event_list.size() == 0)
        {
            event_list.addFirst(event);
            event_added = true;
        } else 
        {
            for(int i = 0; (i < event_list.size()) && !event_added; i++) 
            {
                if(((Event)event_list.get(i)).getEventTime() > event.getEventTime()) 
                {
                    event_list.add(i, event);
                    event_added = true;
                }
                
                if((((Event)event_list.get(i)).getEventTime() == event.getEventTime()) && 
                (event.getEventSignal() == ((Event)event_list.get(i)).getEventSignal()))
                {
                    event_list.set(i, event);
                    event_added = true;
                }
            }
        }
        
        if(event_added == false)
        {
            event_list.addLast(event);
        }
 
        /*
        //Testausgabe zur Überprüfung von korrekter Einsortierung der Anfangsevents 
        if(event_queue_status == true)
        {
            showCompleteEventList();
        }
        */
    }

    /**
     * Die Methode hasMore liefert eine Information, ob noch weitere 
     * Events in der Eventliste enthalten sind.
     */
    public boolean hasMore()
    {
        if(event_list.size() != 0)
        {
            return true;
        } else 
        {
            return false;
        }
    }

    /**
     * Die Methode getFirst liefert das nächste Event der 
     * Eventliste zurück. 
     */
    public Event getFirst()
    {
        /*
        //Testausgabe des nächsten abzuarbeitenden Events
        System.out.println("Event_" + actual_time + ": " + 
                           ((Event)event_list.getFirst()).getEventSignal().getName() + " --> " + 
                           ((Event)event_list.getFirst()).getEventSignal().getValue());
        */
       
        return (Event)event_list.getFirst();
    }

    /**
     * Die Methode getEventQueueStatus gibt den aktuellen Status 
     * der Eventqueue als boolscher Wert zurück.
     */
    public boolean getEventQueueStatus()
    {
        return event_queue_status;
    }

    /**
     * Die Methode setEventQueueStatusActiv aktiviert die Abarbeitung 
     * der Eventqueue. Sie muss fuer die Findung des Steadystate-Status 
     * der Schaltung deaktiviert sein.
     */
    public void setEventQueueStatusActiv()
    {
        event_queue_status = true;
    }
    
    /**
     * Die Methode setNewActualTime setzt die aktuelle Projektzeit 
     * auf einem ihr gelieferten Wert.
     */
    public void setNewActualTime(int new_actual_time)
    {
        actual_time = new_actual_time;
    }
    
    /**
     * Die Methode removeEvent entfernt das aktuellste Event aus 
     * der Eventliste.
     */
    public void removeEvent()
    {
        event_list.remove();
    }
    
    /**
     * Die Methode showCompleteEventList gibt auf der Konsole 
     * chronologisch die einsortierten Events so wie sie in der 
     * Liste sthene auf der Konsole aus.
     */
    public void showCompleteEventList()
    { 
        for(int j = 0; j < event_list.size(); j++)
        {System.out.println("Zeit: " + ((Event)event_list.get(j)).getEventTime() + "\n");
        }
        System.out.println("\n");
    }
    
    /**
     * Die Methode getActualTime liefert die aktuelle Simulationszeit 
     * zurueck.
     */
    public static int getActualTime()
    {
        return actual_time;
    }
}
