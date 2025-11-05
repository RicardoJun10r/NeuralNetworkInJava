package io.github.model;

import java.util.ArrayList;
import java.util.List;

public class HiddenLayer {

    private List<Neuron> neurons;

    public HiddenLayer(int neuronsSize, int inputLayerSize) {
        this.neurons = new ArrayList<>();
        initializeNeurons(neuronsSize, inputLayerSize);
    }

    private void initializeNeurons(int neuronsSize, int inputLayerSize) {
        for (int i = 0; i < neuronsSize; i++) {
            Neuron neuronio = new Neuron(inputLayerSize, NeuronType.WITH_BIAS);
            neurons.add(neuronio);
        }
    }

    public List<Neuron> getNeurons() {
        return neurons;
    }

}
