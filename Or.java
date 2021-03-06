
/**
 * Beschreiben Sie hier die Klasse Or.
 * 
 * @author (Philipp Schneevoigt und Gerrit Bücken) 
 * @version (15.09.17)
 */
public class Or extends Gate
{
    public Or(int number_of_entrance, int delay_time)
    {
        super(number_of_entrance, delay_time);
    }
    
    public void calculate()
    {
        boolean result = true;
        for( int i = 0; i < entrances.length; i++)
        {
            result = result || entrances[i].getValue();
        }
        result = result;
        super.set_result(result);
    }
}
