import java.util.ArrayList;
/**
 * Signal beinhaltet die Information über den Wert
 * weiterhin wird ist die Klasse für das Ausdrucken auf die Konsole verantwortlich
 * @author (Ihr Name) 
 * @version (eine Versionsnummer oder ein Datum)
 */
public class Signal
{
    private String name;
    private boolean value;
    private ArrayList<Nand> target;
    /**
     * Konstruktor für Objekte der Klasse Signal
     */
    public Signal(String name)
    {
        this.name = name;
        value = false;
        target = new ArrayList<Nand>();
    }

    /**
     * setValue setzt den neuen Wert des jeweiligen Signals mittels berechnung und druckt die Ergebnisse des letzten Gatters aus
     */
    public void setValue(boolean value)
    {
        this.value = value;
        if (target.size() == 0)
        {
            System.out.println(name+ " -> "+value);
        }
        else
        {
            for (int i=0; i<target.size();i++)
            {
                target.get(i).calculate();
            }
        }
    }

    public boolean getValue()
    {
        return value;
    }

    public void setTarget (Nand gate)
    {
        target.add(gate);
    }
}
