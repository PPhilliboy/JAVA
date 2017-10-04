import java.util.StringTokenizer;
import java.util.HashMap;
import java.util.LinkedList;
/**
 * Die Klasse DATEISIMULTOR koordiniert die Simulation. Mit dem Erstellen 
 * einer Instanz dieser Klasse wird dazu aufgefordert einen Satz an 
 * Daten (Schaltung und Signaleingangsänderungen) eingelesen und daraus 
 * die Schaltung erstellt. Nach dem Erstellen wird ein eingeschwungener 
 * Zustand der Schaltung hergestellt und doie Schaltung kann simuliert werden.
 * 
 * @author (Philipp Schneevoigt und Gerrit Buecken) 
 * @version (16.09.2017)
 */
public class DateiSimulator
{
    // EventQueue f�r diesen Simulator, wird im Konstruktor initialisiert
    private EventQueue  queue;
    private HashMap<String, Signal> input_signal_collection;
    private LinkedList<String> input_signal_keys;
    private HashMap<String, Signal> internal_signal_collection;
    private HashMap<String, Signal> output_signal_collection;
    private LinkedList<String> output_signal_keys;
    private HashMap<String, Gate> gate_collection;
    private HashMap<String, String> simulation_data;
        
    /**
     * Konstruktor für Objekte der Klasse Dateisimulator
     */
    public DateiSimulator(String circuit_file, String event_file)
    {
        queue=new EventQueue();
        Event.setEventQueue(queue);
        
        input_signal_collection = new HashMap<String, Signal>();
        input_signal_keys = new LinkedList<String>();
        internal_signal_collection = new HashMap<String, Signal>();
        output_signal_collection = new HashMap<String, Signal>();
        output_signal_keys = new LinkedList<String>();
        gate_collection = new HashMap<String, Gate>(); 
        simulation_data = new HashMap<String, String>();
        
        //prepareSimulation("4_C.txt", "4_E.txt");
        prepareSimulation(circuit_file, event_file);
    }
    
    /**
     * Die Methode prepareSimulation bereitet die Simulation vor. Zum 
     * einen wird die Schaltung aufgebaut, dann die Signalzustandsänderungen 
     * eingelesen und abschließend ein eingeschwungener Zustand der 
     * Schaltung gefunden.
     */
    private void prepareSimulation(String circuit_file, String event_file) 
    {
        createCircuit(circuit_file);
        createEventList(event_file);
        findSteadyState();
    }
    
    /**
     *  Die Methode createCircuit baut die Schaltung aus dem eingelesenen 
     *  Textfile auf.
     */
    private void createCircuit (String circuit_file) 
    {
        DateiLeser circuit_reader = new DateiLeser(circuit_file);
        
        while(circuit_reader.nochMehrZeilen())
        {
            String line = circuit_reader.gibZeile();
            
            StringTokenizer tokenizer = new StringTokenizer(line);
            if(tokenizer.hasMoreTokens())
            {
                String token = tokenizer.nextToken("=;,. ");

                if(token.substring(0,1).equals("#") || token.substring(0,1).equals(" "))
                {
                }else
                {
                    //Input-Signale in zugehoerige Hashmap eintragen
                    if(token.toLowerCase().equals("input"))
                    {
                        while(!token.substring(0,1).equals(";") && tokenizer.hasMoreTokens())
                        {
                            String key = tokenizer.nextToken("=;,. ");
                            input_signal_keys.add(key);
                            Signal input = new Signal(key);
                            input.setSignalAsOutputOrInput();
                            input_signal_collection.put(key, input);
                        }
                    }

                    //Intern-Signale in zugehoerige Hashmap eintragen
                    else if(token.toLowerCase().equals("signal"))
                    {
                        while(tokenizer.hasMoreTokens())
                        {
                            String key = tokenizer.nextToken("=;,. ");
                            Signal internal = new Signal(key);
                            internal_signal_collection.put(key, internal);
                        }
                    }
                    
                    //Output-Signale in zugehoerige Hashmap eintragen
                    else if(token.toLowerCase().equals("output"))
                    {
                        while(tokenizer.hasMoreTokens())
                        {
                            String key = tokenizer.nextToken("=;,. ");
                            output_signal_keys.add(key);
                            Signal output = new Signal(key);
                            output.setSignalAsOutputOrInput();
                            output_signal_collection.put(key, output);
                        }
                    }
                    
                    //Gatter in zugehoerige Hashmap eintragen
                    else if(token.toLowerCase().equals("gate"))
                    {
                        String key = tokenizer.nextToken("=;,. ");
                        String gate_type_with_numbers = tokenizer.nextToken("=;,. ");
                        
                        tokenizer.nextToken();
                        token = tokenizer.nextToken("=;,. ");
                        
                        int gate_delay_time = Integer.parseInt(token);
                        
                        int number_chars_gate_type = gate_type_with_numbers.length();
                        
                        //Letztes Zeichen isolieren und gucken, ob es die Eingangsanzahl
                        //repraesentiert oder nicht
                        String last_char_gate_type = gate_type_with_numbers
                               .substring(number_chars_gate_type - 1,number_chars_gate_type);
                        
                        String gate_type = "";
                        int number_gate_entrances = 1;
                        boolean no_number_gate_entrances = false;
                        try 
                        {
                            number_gate_entrances = Integer.parseInt(last_char_gate_type);
                        } catch (NumberFormatException ex) {
                            no_number_gate_entrances = true;
                        }
                               
                        if(no_number_gate_entrances == false)
                        {
                            gate_type = gate_type_with_numbers
                                   .substring(0,number_chars_gate_type - 1).toLowerCase();
                        }else
                        {
                            gate_type = gate_type_with_numbers.toLowerCase();
                        }
                          
                        /*
                        //Testausgabe, ob die Gatter in Bezeichnung und Eingaenge richtig 
                        //getrennt werden       
                        System.out.println(gate_type + " " + number_gate_entrances + "\n");
                        */
                        
                        //Gatter erstellen
                        if(gate_type.equals("and"))
                        {
                            And and = new And(number_gate_entrances, gate_delay_time);
                            gate_collection.put(key, and);    
                        }else if(gate_type.equals("nand"))
                        {
                            Nand nand = new Nand(number_gate_entrances, gate_delay_time);
                            gate_collection.put(key, nand);    
                        }else if(gate_type.equals("or"))
                        {
                            Or or = new Or(number_gate_entrances, gate_delay_time);
                            gate_collection.put(key, or);    
                        }else if(gate_type.equals("nor"))
                        {
                            Nor nor = new Nor(number_gate_entrances, gate_delay_time);
                            gate_collection.put(key, nor);    
                        }else if(gate_type.equals("exor"))
                        {
                            Exor exor = new Exor(number_gate_entrances, gate_delay_time);
                            gate_collection.put(key, exor);    
                        }else if(gate_type.equals("not"))
                        {
                            Not not = new Not(gate_delay_time);
                            gate_collection.put(key, not);    
                        }else if(gate_type.equals("ff"))
                        {
                            FF ff = new FF(gate_delay_time);
                            gate_collection.put(key, ff);    
                        }else if(gate_type.equals("latch"))
                        {
                            Latch latch = new Latch(gate_delay_time);
                            gate_collection.put(key, latch);    
                        }else if(gate_type.equals("buf"))
                        {
                            Buf buf = new Buf(gate_delay_time);
                            gate_collection.put(key, buf);    
                        }else 
                        {
                            System.out.println("Die Gattereinordnung ist nicht richtig abgelaufen.");
                        } 
                    }

                    //Signale den Gattern zuordnen
                    else
                    {
                        String choosen_gate = token;
                        String destination_gate = tokenizer.nextToken("=;,. ");
                        String choosen_signal = tokenizer. nextToken("=;,. ");
                        
                        if(destination_gate.substring(0,1).equals("i"))
                        {
                            int number_of_entrance = 
                                Integer.parseInt(destination_gate.substring(1,2)) - 1;
                            
                            Signal signal = null;
                            if(input_signal_collection.containsKey(choosen_signal))
                            {
                                signal = input_signal_collection.get(choosen_signal);
                            }else if(internal_signal_collection.containsKey(choosen_signal))
                            {
                                signal = internal_signal_collection.get(choosen_signal);
                            }else if(output_signal_collection.containsKey(choosen_signal))
                            {
                                signal = output_signal_collection.get(choosen_signal);
                            }else
                            {
                                System.out.println("Das zu verknüpfende Signal ist nicht in den" +
                                                   " Signallisten enthalten");
                            }
                            
                            gate_collection.get(choosen_gate).setInput(number_of_entrance, signal);
                        
                        }else if(destination_gate.substring(0,1).equals("c") ||
                                 destination_gate.substring(0,1).equals("e"))
                        {                            
                            Signal signal = null;
                            if(input_signal_collection.containsKey(choosen_signal))
                            {
                                signal = input_signal_collection.get(choosen_signal);
                            }else if(internal_signal_collection.containsKey(choosen_signal))
                            {
                                signal = internal_signal_collection.get(choosen_signal);
                            }else if(output_signal_collection.containsKey(choosen_signal))
                            {
                                signal = output_signal_collection.get(choosen_signal);
                            }else
                            {
                                System.out.println("Das zu verknüpfende Signal ist nicht in den" +
                                                   " Signallisten enthalten");
                            }
                            
                            gate_collection.get(choosen_gate).setInput(0, signal);
                            
                        }else if(destination_gate.substring(0,1).equals("d"))
                        {                            
                            Signal signal = null;
                            if(input_signal_collection.containsKey(choosen_signal))
                            {
                                signal = input_signal_collection.get(choosen_signal);
                            }else if(internal_signal_collection.containsKey(choosen_signal))
                            {
                                signal = internal_signal_collection.get(choosen_signal);
                            }else if(output_signal_collection.containsKey(choosen_signal))
                            {
                                signal = output_signal_collection.get(choosen_signal);
                            }else
                            {
                                System.out.println("Das zu verknüpfende Signal ist nicht in den" +
                                                   " Signallisten enthalten");
                            }
                            
                            gate_collection.get(choosen_gate).setInput(1, signal);
                        
                        }else if(destination_gate.substring(0,1).equals("o"))
                        {                            
                            Signal signal = null;
                            if(input_signal_collection.containsKey(choosen_signal))
                            {
                                signal = input_signal_collection.get(choosen_signal);
                            }else if(internal_signal_collection.containsKey(choosen_signal))
                            {
                                signal = internal_signal_collection.get(choosen_signal);
                            }else if(output_signal_collection.containsKey(choosen_signal))
                            {
                                signal = output_signal_collection.get(choosen_signal);
                            }else
                            {
                                System.out.println("Das zu verknüpfende Signal ist nicht in den" +
                                                   " Signallisten enthalten");
                            }
                            
                            gate_collection.get(choosen_gate).setOutput(0, signal);
                        
                        }else if(destination_gate.substring(0,1).equals("q"))
                        {                            
                            Signal signal = null;
                            if(input_signal_collection.containsKey(choosen_signal))
                            {
                                signal = input_signal_collection.get(choosen_signal);
                            }else if(internal_signal_collection.containsKey(choosen_signal))
                            {
                                signal = internal_signal_collection.get(choosen_signal);
                            }else if(output_signal_collection.containsKey(choosen_signal))
                            {
                                signal = output_signal_collection.get(choosen_signal);
                            }else
                            {
                                System.out.println("Das zu verknüpfende Signal ist nicht in den" +
                                                   " Signallisten enthalten");
                            }
                            
                            gate_collection.get(choosen_gate).setOutput(0, signal);
                        
                        }else if(destination_gate.substring(0,2).equals("nq"))
                        {                            
                            Signal signal = null;
                            if(input_signal_collection.containsKey(choosen_signal))
                            {
                                signal = input_signal_collection.get(choosen_signal);
                            }else if(internal_signal_collection.containsKey(choosen_signal))
                            {
                                signal = internal_signal_collection.get(choosen_signal);
                            }else if(output_signal_collection.containsKey(choosen_signal))
                            {
                                signal = output_signal_collection.get(choosen_signal);
                            }else
                            {
                                System.out.println("Das zu verknüpfende Signal ist nicht in den" +
                                                   " Signallisten enthalten");
                            }
                            
                            gate_collection.get(choosen_gate).setOutput(1, signal);
                        
                        }
                    }
                }
            }
        }
    }
    
    /**
     * Die Methode createEventList erzeugt die anfängliche Eventlist -Liste 
     * der Signalzustandsänderungen- aus der eingelesen Datei.
     */
    private void createEventList (String event_file) 
    {       
        DateiLeser event_reader = new DateiLeser(event_file);
        
        while(event_reader.nochMehrZeilen())
        {
            String line = event_reader.gibZeile();
            
            StringTokenizer tokenizer = new StringTokenizer(line);
            if(tokenizer.hasMoreTokens())
            {
                String token = tokenizer.nextToken("=;,. ");

                if(token.substring(0,1).equals("#") || token.substring(0,1).equals(" "))
                {
                }else
                {
                    int event_time = Integer.parseInt(token);
                    String choosen_signal = tokenizer.nextToken("=;,. ");
                    String event_value = tokenizer.nextToken("=;,. ");
                   
                    Signal signal = null;
                    if(input_signal_collection.containsKey(choosen_signal))
                    {
                        signal = input_signal_collection.get(choosen_signal);
                    }else if(internal_signal_collection.containsKey(choosen_signal))
                    {
                        signal = internal_signal_collection.get(choosen_signal);
                    }else if(output_signal_collection.containsKey(choosen_signal))
                    {
                        signal = output_signal_collection.get(choosen_signal);
                    }else
                    {
                        System.out.println("Das Eventsignal ist nicht in den" +
                                           " Signallisten enthalten");
                    }
                    
                    boolean value = false;
                    if(event_value.equals("0"))
                    {
                        value = false;
                    }else if(event_value.equals("1"))
                    {
                        value = true;
                    }else
                    {
                        System.out.println("Der Eventwert entspricht nicht den angegeben konventionen.");
                    }
                    
                    new Event(signal, event_time, value);
                }
            }
        }
    }
    
    /**
     * Die Methode finbdSteadyState erzeugt den eingeschwungenen Zustand der 
     * Schaltung indem alle Eingangssignale einmal auf false gesetzt und durch 
     * die Schaltung propagiert werden.
     */
    private void findSteadyState() 
    {
        for(int i = 0; i < input_signal_keys.size(); i++)
        {
            input_signal_collection.get(input_signal_keys.get(i)).setValue(false);
        }
    }
    
        /**
     * Diese Methode f�hrt die eigentliche Simulation durch. Dazu wird
     * gepr�ft, ob in der EventQueue noch weitere Events vorhanden sind. Ist
     * dies der Fall, dann wird das n�chste anstehende Event behandelt. Dazu
     * muss das Event die Methode propagate() zur Verf�gung stellen, die
     * dann das betroffene Signal informiert.
     */
    public void simulate() {
        while (queue.hasMore()) {
            Event e=queue.getFirst();
    
            e.propagate();
 
            collectSimmulationData(e);
        }
        
        if(!queue.hasMore())
            {
                for(int i = 0; i <= queue.getActualTime(); i++)
                {
                    if(simulation_data.containsKey(Integer.toString(i)))
                    {
                        System.out.println(simulation_data.get(Integer.toString(i)));
                    }
                }
        }
    }
     
     /**
     * Die Methode collectSimmulationData sammelt alle relevanten Daten der Simulation.
     */
    private void collectSimmulationData (Event event)
    {    
        String line = queue.getActualTime()+ ": ";
            
        if ((queue.getEventQueueStatus() == true) && event.getEventSignal().getInfoSignalIsOutputOrInput ())
        {
            line = line + "INPUTS       ";
            for(int i = 0; i < input_signal_keys.size(); i++)
            {
                Signal input = input_signal_collection.get(input_signal_keys.get(i));
                
                String name = input.getName();
                boolean value = input.getValue();
                
                line = line + name + " " + value + "    ";
            }
            line = line + "     OUTPUTS       ";
            for(int i = 0; i < output_signal_keys.size(); i++)
            {
                Signal output = output_signal_collection.get(output_signal_keys.get(i));
                
                String name = output.getName();
                boolean value = output.getValue();
                
                line = line + name + " " + value + "    ";
            }
            
            if(simulation_data.containsKey(queue.getActualTime()))
            {
                simulation_data.remove(queue.getActualTime());
            }
            
            simulation_data.put(Integer.toString(queue.getActualTime()), line);            
        }
    }  
}
