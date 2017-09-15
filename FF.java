
/**
 * Beschreiben Sie hier die Klasse Exor.
 * 
 * @author (Philipp Schneevoigt und Gerrit Bücken) 
 * @version (15.09.17)
 */
public class FF extends Gate
{
    boolean old_clk;
    /**
     * Konstruktor für Objekte der Klasse Exor
     */
    public FF(int delay_time)
    {
        super(2, delay_time);
    }

    public void calculate()
    {
        boolean clk = entrances[0].getValue();
        boolean result = old_exit_value;
        if (old_clk == false && clk == true)
        {
            result = entrances[1].getValue();
        }
        old_clk = clk;
        super.set_result(result);
    }
}
