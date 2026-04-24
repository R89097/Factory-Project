package factorysim.model;
import factorysim.config.MachineConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * Simulation model for a machine.
 */
public class Machine {

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

}
