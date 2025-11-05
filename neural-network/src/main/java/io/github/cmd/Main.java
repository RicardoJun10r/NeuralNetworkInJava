package io.github.cmd;

import io.github.model.NeuralNetwork;

public class Main {
    public static void main(String[] args) {
        Double[][] and_input = {
                { 0.0, 0.0 },
                { 0.0, 1.0 },
                { 1.0, 0.0 },
                { 1.0, 1.0 }
        };

        Double[][] and_output = {
                { 0.0 },
                { 0.0 },
                { 0.0 },
                { 1.0 }
        };

        Double[][] xor_input = {
                { 0.0, 0.0 },
                { 0.0, 1.0 },
                { 1.0, 0.0 },
                { 1.0, 1.0 }
        };

        Double[][] xor_output = {
                { 0.0 },
                { 1.0 },
                { 1.0 },
                { 0.0 }
        };

        NeuralNetwork neuralNetwork = new NeuralNetwork(and_input, and_output, 1, 0.1, true);
        neuralNetwork.train(5000);
    }
}