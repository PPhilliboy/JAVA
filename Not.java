
/**
 * Beschreiben Sie hier die Klasse Not.
 * 
 * @author (Philipp Schneevoigt und Gerrit Bücken) 
 * @version (15.09.17)
 */
public class Not extends Gate
{
    public Not(int delay_time)
    {
        super(1, delay_time);
    }
    
    public void calculate()
    {
        boolean result = !entrances[0].getValue();
        super.set_result(result);
    }
}
