import java.util.ArrayList;
/**
 * Signal beinhaltet die Information über den Wert
 * weiterhin wird ist die Klasse für das Ausdrucken auf die Konsole verantwortlich
 * gespeicherte Variablen:
 *                        - name: Typ String; Name des Signals
 *                        - value: Typ boolean; Wert des Signals
 *                        - target_list: Typ ArrayList<Nand>; Liste, welche die
 *                                      Zielanschlüsse beinhaltet
 * @author (Philipp Schneevoigt und Gerrit Buecken) 
 * @version (13.09.2017)
 */
public class Signal
{
    private String name; 
    private boolean value;
    private ArrayList<Nand> target_list;  
    private int steady_state_counter;
    /**
     * Konstruktor für Objekte der Klasse Signal
     * es wird jedem Signal ein eindeutiger Namne zugeordnet
     * es wird jedem Signal ein eindeutiger binaerer Signalwert zugeordnet
     * es werden alle an einem Signal angeschlossenen Ziele (Gatter) in einer Liste gesammelt
     */
    public Signal(String name)
    {
        this.name = name;
        value = false;
        target_list = new ArrayList<Nand>();
        steady_state_counter = 0;
    }

    /**
     * Die Methode setValue setzt den neuen Wert des jeweiligen Signals
     * und propagiert diese Aenderung an alle angeschlossenen Gatter.
     * Unterschieden wird dabei ob ein eingschwungener Anfangszustand 
     * erreicht werden, oder ein regulaeres Schaltungsereignis auftritt 
     * und propagiert werden soll.
     * Weiterhin wird jedes Signal, was einen Ausgang der Schaltung darstellt auf der Konsole ausgegeben.
     */
    public void setValue(boolean new_value)
    {
        value = new_value;
        
        /*
        //Testausgabe zur Überprüfung jeder Signaländerung
        System.out.println("Name: " + name + " --> " + value);
        */
        
        if((Event.getEventQueue().getEventQueueStatus() == false) && (steady_state_counter < 5))
        {
            steady_state_counter ++;
            
            propagateToTargetList ();
        }
        
        if ((Event.getEventQueue().getEventQueueStatus() == true) && (target_list.size() == 0))
        {
            System.out.println(Event.getEventQueue().getFirst().getEventTime() 
                               + ": " + name + " -> " + value);
        }else if ((Event.getEventQueue().getEventQueueStatus() == true) && (target_list.size() != 0))
        {
            propagateToTargetList ();
        }
    }


    /**
     * Die Methode getValue liefert den Wert des Signals zurück.
     */
    public boolean getValue()
    {
        return value;
    }


    /**
     * Die Methode setTarget fügt ein Ziel (Gattereingang) 
     * hinzu, an welches das berechnete Signal übergeben 
     * werden soll.
     */
    public void setTarget (Nand gate)
    {
        target_list.add(gate);
    }
    

    /**
     * Die Methode propagateToTargetList leitet an allen am 
     * Signal angeschlossenen Gattern eine Neuberechnung ein.
     */
    public void propagateToTargetList ()
    {
        for (int i=0; i<target_list.size();i++)
        {
            target_list.get(i).calculate();
        }
    }    
}
