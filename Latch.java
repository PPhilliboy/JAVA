
/**
 * Beschreiben Sie hier die Klasse Exor.
 * 
 * @author (Philipp Schneevoigt und Gerrit Bücken) 
 * @version (15.09.17)
 */
public class Latch extends Gate
{
    /**
     * Konstruktor für Objekte der Klasse Exor
     */
    public Latch(int delay_time)
    {
        super(2, delay_time);
    }

    public void calculate()
    {
        boolean clk = entrances[0].getValue();
        boolean result = old_exit_value;
        if (clk == true)
        {
            result = entrances[1].getValue();
        }
        super.set_result(result);
    }
}
