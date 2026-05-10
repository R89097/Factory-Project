package factorysim.model;
import factorysim.config.MachineConfig;
import factorysim.config.PortConfig;

import factorysim.stats.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * FactoryNetwork models the overall factory, including connections 
 * between machines, belts, and sinks.
 * Implements the FactoryNetwork interface.
 */
public final class FactoryNetworkImpl implements FactoryNetwork {

    Sink sink;
    // YOUR FIELDS HERE. You probably want to track the other main factory components
    List<Machine> machines;

    /**
     * Establish a factory network.
     * Most of the logic for this class is in the constructor
     * (though you might find private methods useful to break it up)
     * 
     * @param machineConfigs a list of machine configs from the 
     * configuration file (in the same order they were originally in
     * i.e. machineConfigs.get(0) would have been the first listed machine in the config file)
     * @throws BeltValidationException If the list of machineConfigs tries to wire a sink to a
     * machine input port, or tries to connect a belt to two ports with different types, 
     * the constructor should throw a BeltValidationException with an informative error message.
     */
    public FactoryNetworkImpl(List<MachineConfig> machineConfigs) throws BeltValidationException{

        // The constructor should initialise your factory components (Sink has been initialised for you below). 
        // It should "wire" the components together (i.e. connect machine output ports to belts/sinks etc), 
        // based on the layout specified in the machine configs.
        // It should also check that the provided config doesn't suggest wiring a belt in 
        // an incorrect way (i.e. you can't  wire a sink to a machine input port)
        // a BeltValidationException. You might even find it useful to do this first,
        // It is up to you how to approach this/ what data structures you use.
        
        //Sink sink = new Sink();

        this.sink = new Sink();
        this.machines = new ArrayList<>();

        for (MachineConfig config : machineConfigs) {
            machines.add(new Machine(config));
        }
        for (Machine machine : machines) {
            sink.addSource(machine);
        }

        // YOUR CODE HERE (for all other components).
        
    }

    // This class should implement the FactoryNetwork specification.
    // You should think about what methods this involves.
    // If you've used a good object oriented design on factory components, these methods might be quite simple.

    @Override
    public void tock() {
        for (Machine source : machines) {
            if (!source.canPull()) {
                continue;
            }
            String item = source.itemType();
            boolean sentToMachine = false;

            for (Machine target : machines) {

                if (source == target) {
                    continue;
                }
                int before = target.canPull() ? 1 : 0;

                if (target.receiveInput(item)) {
                    sentToMachine = true;
                    source.pullItem();
                    break;
                }

                int after = target.canPull() ? 1 : 0;

                if (after > before) {
                    sentToMachine = true;
                    source.pullItem();
                    break;
                }
            }
        sink.tock();
        }
    }

    @Override
    public void tick() {
        // later: machines tick
        for (Machine machine : machines) {
            machine.tick();
        }
    }

    @Override
    public void resetStatistics() {
        sink.resetStatistics();
        for (Machine machine : machines) {
            machine.resetStatistics();
        }
    }

     @Override
    public List<SinkEntry> getSinkStats() {
        List<SinkEntry> stats = new ArrayList<>();

        for (String itemType : sink.getItemTypes()) {
            stats.add(
                new SinkEntry(
                    itemType,
                    sink.getAvgItemsPerMinute(itemType)
                )
            );
        }

        return stats;
    }

    @Override
    public List<MachineStats> getMachineStats() {
        List<MachineStats> stats = new ArrayList<>();

        for (Machine machine : machines) {
            stats.add(
                new MachineStats(
                    machine.getName(),
                    machine.getUtilisation()
                )
            );
        }
        return stats;
    }

    @Override
    public List<BeltStats> getBeltStats() {
        return new ArrayList<>();
    }

}
