
/**
 * Beschreiben Sie hier die Klasse Exor.
 * 
 * @author (Philipp Schneevoigt und Gerrit Bücken) 
 * @version (15.09.17)
 */
public class Exor extends Gate
{
    /**
     * Konstruktor für Objekte der Klasse Exor
     */
    public Exor(int number_of_entrance, int delay_time)
    {
        super(number_of_entrance, delay_time);
    }

    public void calculate()
    {
        boolean result = false;
        for(int i = 0; i < entrances.length;i++)
        {
            if (entrances[i].getValue() == true)
            {
                result = !result;
            }
        }
        super.set_result(result);
    }
}
