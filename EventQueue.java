import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Klasse EventQueue
 */
public class EventQueue
{
    // Instanzvariablen
    private static LinkedList<Event> eventliste;
    private static boolean inistatus;
    private static int globalTime;

    
    //Konstruktor
    /**
     * Constructor for objects of class EventQueue
     */
    public EventQueue()
    {
        eventliste = new LinkedList<Event>();
        inistatus = false;
        globalTime = 0;
    }

    
    //Methoden
    /**
     * Methode setInistatus
     */
    public static void setInistatus(boolean newInistatus)
    {
        inistatus = newInistatus;
    }
    
    /**
     * Methode getInistatus
     */
    public static boolean getInistatus()
    {
        return inistatus;
    }
    
    /**
     * Methode setGlobalTime
     */
    public static void setGlobalTime(int newGlobalTime)
    {
        globalTime = newGlobalTime;
    }
    
    /**
     * Methode getglobalTime
     */
    public static int getGlobalTime()
    {
        return globalTime;
    }
    
    /**
     * Methode addEvent
     */
    public void addEvent(Event event)
    {
        eventliste.add(event);
        
        sortiereEventliste();
    }

    /**
     * Methode hasMore
     */
    public boolean hasMore()
    {
        if(!inistatus)
        {
            setInistatus(true);
        }
        
        boolean hm = true;
        
        if(eventliste.size() == 0)
        {
            hm = false;
        }
        
        return hm;
    }
    
    /**
     * Methode getFirst
     */
    public Event getFirst()
    {
        Event event = eventliste.get(0);
        
        setGlobalTime(event.getEventZeitpunkt());
        
        eventAusgeben(event);
        
        return eventliste.poll();
    }
    
    /**
     * Methode sortiereEventliste
     * 
     * Diese Methode sortiert die Eventliste nach dem Eventzeitpunkt. 
     * Es wird jeweils ein Paar von Events auf ihren Eventzeitpunkt 
     * verglichen, sollte ein Event mit späterer Eventzeit als das 
     * darauf folgende in der Liste stehen, werden diese beiden getauscht. 
     * So wandert der Sortieralgorythmus durch die Eventliste. Dieses 
     * tut er so oft, wie die Liste lang ist. 
     */
    public void sortiereEventliste()
    {
        for(int j = eventliste.size(); j !=0; j--)
        {
            for(int i = 1; i < eventliste.size() ; i++)
            {
                if(eventliste.get(i-1).getEventZeitpunkt() > 
                   eventliste.get(i).getEventZeitpunkt())
                {
                    Event helpEvent = eventliste.get(i-1);
                    eventliste.set(i-1, eventliste.get(i));
                    eventliste.set(i, helpEvent);
                }
            }
        }
    }
    
    /**
     * Methode eventAusgeben
     */
    public void eventAusgeben(Event event)
    {
        if(event.getEventSignal().getInfoSchaltungsausgang())
        {
            System.out.println(event.getEventZeitpunkt() +
            "  am Signal  " + event.getEventSignal().getName() +
            "  mit dem neuen Wert  " + event.getEventValue());
        }
    }
}
