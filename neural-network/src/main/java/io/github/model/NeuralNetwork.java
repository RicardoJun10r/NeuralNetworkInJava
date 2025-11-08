package io.github.model;

import java.util.ArrayList;
import java.util.List;

public class NeuralNetwork {

    private InputLayer inputLayer;

    private HiddenLayer hiddenLayer;

    private OutputLayer outputLayer;

    private double learningRate = 0.005;

    private boolean showError = true;

    private Double[][] inputTraining;

    private Double[][] outputTraining;

    private List<Double> errorHistory;

    private Double totalError;

    public NeuralNetwork(Double[][] inputs, Double[][] outputs, int hiddenLayerSize, double learningRate,
            boolean showError) {
        this.inputTraining = inputs;
        this.outputTraining = outputs;
        int inputLayerSize = inputTraining[0].length;
        int outputLayerSize = outputTraining[0].length;
        this.inputLayer = new InputLayer(inputLayerSize);
        this.hiddenLayer = new HiddenLayer(hiddenLayerSize, inputLayerSize);
        this.outputLayer = new OutputLayer(outputLayerSize, hiddenLayerSize);
        this.learningRate = learningRate;
        this.showError = showError;
        this.errorHistory = new ArrayList<>();
        this.totalError = 0.0;
    }

    public void train(int epochs) {
        for (int epoch = 0; epoch < epochs; epoch++) {
            System.out.println("Epoch: " + (epoch + 1));
            this.totalError = 0.0;
            for (int i = 0; i < this.inputTraining.length; i++) {
                feedFoward((this.inputTraining[i]), this.outputTraining[i]);
            }
            Double averageError = totalError / this.inputTraining.length;
            errorHistory.add(averageError);
        }
    }

    private void feedFoward(Double[] inputs, Double[] outputs) {

        List<Neuron> inputLayerNeurons = inputLayer.getNeurons();

        List<Neuron> hiddenLayerNeurons = hiddenLayer.getNeurons();

        List<Neuron> outputLayerNeurons = outputLayer.getNeurons();

        input(inputLayerNeurons, inputs);

        inputHidden(inputLayerNeurons, hiddenLayerNeurons);

        hiddenOutput(hiddenLayerNeurons, outputLayerNeurons);

        output(outputLayerNeurons, outputs);

        backpropagation(inputLayerNeurons, hiddenLayerNeurons, outputLayerNeurons, inputs, outputs);

    }

    private void input(List<Neuron> inputLayerNeurons, Double[] inputs) {
        for (int i = 0; i < inputLayerNeurons.size(); i++) {
            Neuron inputNeuron = inputLayerNeurons.get(i);
            inputNeuron.setOutput(inputs[i]);
        }
    }

    private void inputHidden(List<Neuron> inputLayerNeurons, List<Neuron> hiddenLayerNeurons) {
        for (int i = 0; i < hiddenLayerNeurons.size(); i++) {
            Neuron hiddenNeuron = hiddenLayerNeurons.get(i);
            double hiddenLayerSum = 0;
            for (int j = 0; j < inputLayerNeurons.size(); j++) {
                Neuron neuronioEntrada = inputLayerNeurons.get(j);
                hiddenLayerSum += neuronioEntrada.getOutput() * hiddenNeuron.getWeight(j);
            }
            hiddenLayerSum += hiddenNeuron.getBias();
            hiddenLayerSum = sigmoid(hiddenLayerSum);
            hiddenNeuron.setOutput(hiddenLayerSum);
        }
    }

    private void hiddenOutput(List<Neuron> hiddenLayerNeurons, List<Neuron> outputLayerNeurons) {
        for (int i = 0; i < outputLayerNeurons.size(); i++) {
            Neuron outputNeuron = outputLayerNeurons.get(i);
            double outputLayerSum = 0;
            for (int j = 0; j < hiddenLayerNeurons.size(); j++) {
                Neuron hiddenNeuron = hiddenLayerNeurons.get(j);
                outputLayerSum += hiddenNeuron.getOutput() * outputNeuron.getWeight(j);
            }
            outputLayerSum += outputNeuron.getBias();
            outputLayerSum = sigmoid(outputLayerSum);
            outputNeuron.setOutput(outputLayerSum);
        }
    }

    private void output(List<Neuron> outputLayerNeurons, Double[] outputs) {
        Double totalErrorTmp = 0.0;
        for (int i = 0; i < outputLayerNeurons.size(); i++) {
            Neuron outputNeuron = outputLayerNeurons.get(i);
            double erro = Math.pow((outputs[i] - outputNeuron.getOutput()), 2);
            totalErrorTmp += erro;
            if (showError) {
                System.out.println("Expected Output: " + outputs[i] + " - Predicted Output: " + outputNeuron.getOutput()
                        + " - Error: " + erro);
            }
        }
        this.totalError += totalErrorTmp;
    }

    private void backpropagation(List<Neuron> inputLayerNeurons, List<Neuron> hiddenLayerNeurons,
            List<Neuron> outputLayerNeurons, Double[] inputs, Double[] outputs) {

        double[] deltaOutputLayer = new double[outputLayerNeurons.size()];
        for (int i = 0; i < outputLayerNeurons.size(); i++) {
            Neuron outputNeuron = outputLayerNeurons.get(i);
            double outputError = 2 * (outputNeuron.getOutput() - outputs[i]);

            deltaOutputLayer[i] = outputError * sigmoidDerivative(outputNeuron.getOutput());
        }

        double[] deltaHiddenLayer = new double[hiddenLayerNeurons.size()];
        for (int i = 0; i < hiddenLayerNeurons.size(); i++) {
            Neuron hiddenNeuron = hiddenLayerNeurons.get(i);
            double hiddenLayerError = 0;
            for (int j = 0; j < outputLayerNeurons.size(); j++) {
                Neuron outputNeuron = outputLayerNeurons.get(j);
                hiddenLayerError += deltaOutputLayer[j] * outputNeuron.getWeight(i);
            }
            deltaHiddenLayer[i] = hiddenLayerError * sigmoidDerivative(hiddenNeuron.getOutput());
        }

        for (int i = 0; i < outputLayerNeurons.size(); i++) {
            Neuron outputNeuron = outputLayerNeurons.get(i);
            for (int j = 0; j < hiddenLayerNeurons.size(); j++) {
                Neuron hiddenNeuron = hiddenLayerNeurons.get(j);

                double weightGradient = deltaOutputLayer[i] * hiddenNeuron.getOutput();
                double newWeight = outputNeuron.getWeight(j) - (learningRate * weightGradient);
                outputNeuron.setWeight(j, newWeight);
            }
            double biasGradient = deltaOutputLayer[i];
            double newBias = outputNeuron.getBias() - (learningRate * biasGradient);
            outputNeuron.setBias(newBias);
        }

        for (int i = 0; i < hiddenLayerNeurons.size(); i++) {
            Neuron hiddenNeuron = hiddenLayerNeurons.get(i);
            for (int j = 0; j < inputLayerNeurons.size(); j++) {
                Neuron inputNeuron = inputLayerNeurons.get(j);

                double weightGradient = deltaHiddenLayer[i] * inputNeuron.getOutput();
                double newWeight = hiddenNeuron.getWeight(j) - (learningRate * weightGradient);
                hiddenNeuron.setWeight(j, newWeight);
            }
            double biasGradient = deltaHiddenLayer[i];
            double newBias = hiddenNeuron.getBias() - (learningRate * biasGradient);
            hiddenNeuron.setBias(newBias);
        }

    }

    private static double sigmoid(double x) {
        return 1 / (1 + Math.exp(-x));
    }

    private static double sigmoidDerivative(double x) {
        return x * (1 - x);
    }

    public ArrayList<Double> getErrorHistory() {
        return (ArrayList<Double>) errorHistory;
    }

}
