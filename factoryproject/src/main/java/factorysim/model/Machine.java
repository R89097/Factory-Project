package factorysim.model;
import factorysim.config.MachineConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * Simulation model for a machine.
 */
public class Machine implements Tickable, StatResettable, OutputSource {

    /**
     * This is a suggested design for the Machine class if you are stuck
     * 
     * You do not need to follow this design, if you have a different
     * idea in mind, as long as what you do is compatible with the 
     * Sink and FactoryNetwork classes!
     * 
     * Design considerations:
     * - The spec states a machine should have a name and a cooldown period.
     * - It also needs to have some way of tracking its input and output ports (their capacity, what they currently hold, and what items they store), and its state (e.g. is it in cooldown? For how many moreticks?)
     * - A machine constructor could either take in a MachineConfig object, or 
     * these fields individually.
     * - A machine activates on a tick, so what provided interface could be useful here?
     * - On a tick, a machine will need to check things like if it can activate/if it is blocked/if it is currently in a cooldown state, and then implement activation (take items from input port, and produce a batch of output), or decrement cooldown remaining etc. 
     * - remember you can use private methods and other classes (e.g. to represent sub-components of a machine) to break down the machine component and the activation logic!
     * - You will also need to track machine statistics. You might need some more class fields to help! Read the section of the spec around machine statistics.
     * - The Sink class had a method for calculating avg items consumed. What is the equivalent here?
     * - If we're tracking statistics, what other interface should this class perhaps implement?
     * 
     */

    private String name;
    private int cooldownPeriod;
    private int cooldownRemaining;
    private long utilisedTicks;
    private long totalTicks;
    private List<Port> inputPorts;
    private List<Port> outputPorts;

    public Machine(MachineConfig config) {
        this.name = config.getName();
        this.cooldownPeriod = config.getCooldown();
        this.cooldownRemaining = 0;
        this.utilisedTicks = 0;
        this.totalTicks = 0;
        this.inputPorts = new ArrayList<>();
        this.outputPorts = new ArrayList<>();

        for (var input: config.getInputConfigs()) {
            inputPorts.add(
                new Port(input.getItemName(), 
                input.getAmount(), 
                input.getBeltName()));
        }

        for (var output: config.getOutputConfigs()) {
            outputPorts.add(
                new Port(output.getItemName(), 
                output.getAmount(), 
                output.getBeltName()));
        }
    }

    @Override
    public void tick() {
        totalTicks++;

        if (cooldownRemaining > 0) {
            utilisedTicks++;
            cooldownRemaining--;
        } else {
            if (canActivate()) {
                for (Port input : inputPorts) {
                    while (!input.isEmpty()) {
                        input.removeItem();
                    }
                }

                for (Port output : outputPorts) {
                    while (!output.isFull()) {
                        output.addItem();
                    }
                }
                utilisedTicks++;
                cooldownRemaining = cooldownPeriod;
            }
        }
    }

    @Override
    public void resetStatistics() {
        cooldownRemaining = 0;
        utilisedTicks = 0;
        totalTicks = 0;
    }
    

    @Override
    public String itemType() {
        if (outputPorts.isEmpty()) {
            return "";
        }
        return outputPorts.get(0).getItemType();
    }

    @Override
    public boolean canPull() {
        if(outputPorts.isEmpty()) {
            return false;
        }
        return !outputPorts.get(0).isEmpty();
    }

    @Override
    public void pullItem() {
        if (!outputPorts.isEmpty()) {
            outputPorts.get(0).removeItem();
        }    
    }

    public String getName() {
        return name;
    }

    public double getUtilisation() {
        if (totalTicks == 0) {
            return 0.0;
        }
        return (double) utilisedTicks / totalTicks;
    }   

    private boolean canActivate() {
        for (Port port : inputPorts) {
            if (!port.isFull()) {
                return false;
            }
        }
        for (Port port : outputPorts) {
            if (!port.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public boolean receiveInput(String itemType) {
    for (Port port : inputPorts) {
        if (port.getItemType().equals(itemType) && !port.isFull()) {
            port.addItem();
            return true;
        }
    }
    return false;
    }
}
