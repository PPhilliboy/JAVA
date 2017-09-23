
/**
 * Beschreiben Sie hier die Klasse And.
 * 
 * @author (Philipp Schneevoigt und Gerrit Bücken) 
 * @version (15.09.17)
 */
public class And extends Gate
{
    public And(int number_of_entrance, int delay_time)
    {
        super(number_of_entrance, 1,  delay_time);
    }
    
    public void calculate()
    {
        boolean result = true;
        for( int i = 0; i < entrances.length; i++)
        {
            result = result & entrances[i].getValue();
        }
        result = result;
        super.setResult(result);
    }
}

