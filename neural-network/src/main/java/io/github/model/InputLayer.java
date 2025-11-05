package io.github.model;

import java.util.ArrayList;
import java.util.List;

public class InputLayer {

    private List<Neuron> neurons;

    public InputLayer(int neuronsSize) {
        this.neurons = new ArrayList<>();
        initializeNeurons(neuronsSize);
    }

    private void initializeNeurons(int neuronsSize) {
        for (int i = 0; i < neuronsSize; i++) {
            Neuron neuronio = new Neuron(0, NeuronType.WITHOUT_BIAS);
            neurons.add(neuronio);
        }
    }

    public List<Neuron> getNeurons() {
        return neurons;
    }

}
