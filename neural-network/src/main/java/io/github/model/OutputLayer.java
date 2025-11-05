package io.github.model;

import java.util.ArrayList;
import java.util.List;

public class OutputLayer {

    private List<Neuron> neurons;

    public OutputLayer(int outputSize, int hiddenLayerSize) {
        this.neurons = new ArrayList<>();
        initializeNeurons(outputSize, hiddenLayerSize);
    }

    private void initializeNeurons(int outputSize, int hiddenLayerSize) {
        for (int i = 0; i < outputSize; i++) {
            Neuron neuronio = new Neuron(hiddenLayerSize, NeuronType.WITH_BIAS);
            neurons.add(neuronio);
        }
    }

    public List<Neuron> getNeurons() {
        return neurons;
    }

}
