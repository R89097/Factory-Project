package factorysim.model;

import factorysim.config.MachineConfig;

public class MachineTest {

    // If your constructor takes a MachineConfig object, you might find it useful to write a 
    // few private methods like this which set up a MachineConfig
    private static MachineConfig processorConfig(
        String name,
        String inputItem,
        int inputAmount,
        String outputItem,
        int outputAmount,
        int cooldown
    ) {
        return new MachineConfig(name, cooldown)
            .addInput(inputItem, inputAmount, "in_belt")
            .addOutput(outputItem, outputAmount, "out_belt");
    }
}