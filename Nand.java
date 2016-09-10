import java.util.ArrayList;
import java.util.Iterator;

/**
 * Klasse Nand
 */
public class Nand
{
    // Instanzvariablenn
    private int anzahlEingaenge;
    private ArrayList<Signal> listeEingangsbelegung;
    private Signal ausgangsbelegung;
    
    private int delay;
    private boolean iniDummy;
    private boolean lastValue;
    
    
    //Konsturkor
    /**
     * Konstruktor der Klasse Nand
     * 
     *
     * Der Konsturtor initalisiert die Instanz einer Klasse.
     * 
     * @param   anzahlEingaenge     Dieser Parameter gibt die Anzahl der Eingänge dieses 
     *                              Gatters an.
     */
    public Nand(int anzahlEingaenge, int delay)
    {
        this.anzahlEingaenge = anzahlEingaenge;
        this.delay = delay;
        
        listeEingangsbelegung = new ArrayList<Signal>();
        
        iniDummy = false;
    }

    
    //Methoden
    /**
     * Methode setInput 
     * 
     * 
     * Diese Methode belegt einen gewählten Eingang mit einem gewünschten Signal. 
     * Desweiteren wird in diesem zugeordneten Signal ein Eintrag zu diesem Gatter 
     * hier gemacht, damit das Signal dann auch beim Propagieren weiß, welche Gatter 
     * neu berechnet werden müssen.
     * 
     * @param   welcherEingang      Derjeneige Eingang, der mit einem gewünschten Signal 
     *                              belegt werden soll.
     * @param   welchesSignal       Das Signal, dass auf den gewünschten Eingang gelegt 
     *                              werden soll.
     */
    public void setInput(int welcherEingang, Signal welchesSignal)
    {
        listeEingangsbelegung.add(welcherEingang, welchesSignal);
        
        welchesSignal.woBinIchAngeschlossen(this);
    }
    
    /**
     * Methode setOutput 
     * 
     * 
     * Diese Methode belegt den Ausgang des Gatters mit einem gewünschten Signal.
     * 
     * @param   welchesSignal       Das Signal, dass auf den Ausgang gelegt 
     *                              werden soll.  
     */
    public void setOutput(Signal welchesSignal)
    {
        ausgangsbelegung = welchesSignal;
    }
    
    /**
     * Methode berechnen 
     * 
     * 
     * Diese Methode berechent den neuen Signalzustand des Ausgangs des Gatters. Sobald 
     * ein Signal eine Wertzustandsänderung wiederfährt, wird aus der Liste der 
     * angeschlossenen Gatter des Signals eine Neuberechnung eben dieser angestoßen. Die ist 
     * diese Methode hier. 
     * 
     * Machart: Bei einem Nand muss jeder Eingang den Wert true tragen, damit der Ausgang false werden kann. Dementsprechend
     */
    public void berechnen()
    {
        int i = 0;
        boolean hm = true;
        
        while(i < listeEingangsbelegung.size() && hm)
        {
            hm = listeEingangsbelegung.get(i).getValue();
            
            i++;
        }
        
        bedingtesPropagieren(!hm);
    }
    
    /**
     * Methode bedingtesPropagieren  
     */
    public void bedingtesPropagieren(boolean newValue)
    {
        lastValue = newValue;
        
        if(!EventQueue.getInistatus())
        {
            einschwingen(newValue);
        }
        else
        {
            createNewEvent(newValue);
        }
    }
    
    /**
     * Methode einschwingen  
     */
    public void einschwingen(boolean newValue)
    {
        if(iniDummy == false)
        {
            iniDummy = true;
            
            
            
            ausgangsbelegung.setValue(newValue);
            
            iniDummy = false;
        }
    }
    
    /**
     * Methode createNewEvent  
     */
    public void createNewEvent(boolean newValue)
    {
        if(lastValue != newValue)
        {
            new Event(ausgangsbelegung, (EventQueue.getGlobalTime() + delay), newValue); 
        }
    }
}
