import java.util.ArrayList;
import java.util.Iterator;

/**
 * Klasse Signal 
 * 
 * Der Nutzen der Klasse Signal liegt darin, dass wie der Name es schon angibt Signale 
 * zwischen den einzelnen Gattern übertragen soll. 
 */
public class Signal
{
    // Instanzvariablen
    private String name;
    private boolean value;
    private ArrayList<Nand> listeAngeschlosseneGatter;
    
    
    //Konstruktor
    /**
     * Konstruktor der Klasse Signal
     * 
     *
     * Der Konsturtor initalisiert die Instanz einer Klasse. 
     * 
     * @param name      Name des Signals wird beim Erstellen der Signalinstanz ihr selbst zugeordnet.
     */
    public Signal(String name)
    {
        this.name = name;
        listeAngeschlosseneGatter = new ArrayList<Nand>();
    }

    
    //Methoden
    /**
     * Methode setValue 
     * 
     * 
     * Die Methode überträgt zuerst den neuen Signalwert in den privaten Speicherbereich 
     * und leitet diesen an alle ihm angeschlossenen Gattereingänge weiter. Ist ein Signal 
     * an ein Gatter weitergeleitet, hat dies eine Wertänderung im Gatter zufolge. 
     * Dementsprechend wird von dieser Methode heraus die Neuberechnung des Gatters angestuppst.
     * 
     * @param value     Value ist der momentane Wertzustand des Signals. Da wir uns in der 
     *                  Digitalen Welt befinden ist dieser Zustand entweder logisch wahr oder 
     *                  logisch falsch.
     */
    public void setValue(boolean value)
    {
        this.value = value;
        
        int i =0;
        
        while(i < listeAngeschlosseneGatter.size())
        {
            listeAngeschlosseneGatter.get(i).berechnen();
            
            i++;
        }
    }
    
    /**
     * Methode getValue 
     * 
     * 
     * Die Methode liefert den aktuellen Wert des Signals zurück.
     */
    public boolean getValue()
    {
        return value;
    }
    
    /**
     * Methode getName 
     * 
     * 
     */
    public String getName()
    {
        return name;
    }
    
    /**
     * Methode woBinIchAngeschlossen 
     * 
     * 
     * Die Methode speichert die Gatter, die dem Signal angeschlossen sind. Aufgerufen wird
     * die Methode beim setzen der Eingänge eines Gatters.
     * 
     * @param nand      Nand ist das angeschlossene Gatter
     */
    public void woBinIchAngeschlossen(Nand nand)
    {
        listeAngeschlosseneGatter.add(nand);
    }
    
    /**
     * Methode getAnzahlAngeschlosseneGatter 
     * 
     * 
     * 
     */
    public boolean getInfoSchaltungsausgang()
    {
        boolean hm = false;
        
        if(listeAngeschlosseneGatter.isEmpty())
        {
            hm = true;
        }
        
        return hm;
    }
}
