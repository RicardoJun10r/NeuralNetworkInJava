import java.util.ArrayList;
import java.util.List;

public class Main {
    static final int EPOCHS = 5000;
    static final double LR = 0.1;
    static final String ANSI_RESET = "\u001B[0m";

    static final String ANSI_RED = "\u001B[31m";
    static final String ANSI_GREEN = "\u001B[32m";
    static final String ANSI_YELLOW = "\u001B[33m";
    static final String ANSI_BLUE = "\u001B[34m";

    static List<String> cores = new ArrayList<>();

    public static void main(String[] args) {

        cores.add(ANSI_BLUE);
        cores.add(ANSI_GREEN);
        cores.add(ANSI_YELLOW);
        cores.add(ANSI_RED);

        int[][] and_input = {
                { 0, 0 },
                { 0, 1 },
                { 1, 0 },
                { 1, 1 }
        };

        int[] and_output = { 0, 0, 0, 1 };

        int[][] or_input = {
                { 0, 0 },
                { 0, 1 },
                { 1, 0 },
                { 1, 1 }
        };

        int[] or_output = { 0, 1, 1, 1 };

        int[][] xor_input = {
                { 0, 0 },
                { 0, 1 },
                { 1, 0 },
                { 1, 1 }
        };

        int[] xor_output = { 0, 1, 1, 0 };

        System.out.println("AND Gate Training:");
        train2(xor_input, xor_output);

    }

    static void train1(int[][] inputs, int[] outputs) {

        double weight1 = Math.random();
        double weight2 = Math.random();
        double weight3 = Math.random();

        double bias = Math.random();

        System.out.println("Initial Weights and Bias:");
        System.out.println("Weight 1: " + weight1);
        System.out.println("Weight 2: " + weight2);
        System.out.println("Weight 3: " + weight3);
        System.out.println("Bias: " + bias);

        double error_total = 0;

        for (int i = 0; i < EPOCHS; i++) {
            double error = 0;
            for (int j = 0; j < inputs.length; j++) {
                int input_1 = inputs[j][0];
                int input_2 = inputs[j][1];
                int expected_output = outputs[j];

                double output_1 = input_1 * weight1;
                double output_2 = input_2 * weight2;

                double output_3 = ((output_1 + output_2) * weight3) + bias;

                double predicted_output = sigmoid(output_3);

                error = Math.pow((expected_output - predicted_output), 2);
                error_total += error;
                System.out.println(cores.get(i % 4) + "Epoch: " + (i + 1) + ", Input: [" + input_1 + ", " +
                        input_2 + "], Expected: " + expected_output + ", Predicted: " +
                        predicted_output + ", Error: " + error + ANSI_RESET);
                double delta = (2 * (predicted_output - expected_output)) * (predicted_output * (1 - predicted_output));
                double weight3_gradient = delta * (output_1 + output_2);
                double weight1_gradient = delta * weight3 * input_1;
                double weight2_gradient = delta * weight3 * input_2;
                double bias_gradient = delta;
                weight3 -= LR * weight3_gradient;
                weight1 -= LR * weight1_gradient;
                weight2 -= LR * weight2_gradient;
                bias -= LR * bias_gradient;
            }
        }

        double rmqe = Math.sqrt((error_total / EPOCHS));
        System.out.println("RMQE: " + rmqe);

    }

    static void train2(int[][] inputs, int[] outputs) {

        double weight1 = Math.random();
        double weight2 = Math.random();
        double weight3 = Math.random();
        double weight4 = Math.random();

        double weight5 = Math.random();
        double bias1 = Math.random();
        double weight6 = Math.random();
        double bias2 = Math.random();

        double weight7 = Math.random();
        double bias3 = Math.random();

        double error_total = 0;

        for (int i = 0; i < EPOCHS; i++) {
            double error = 0;
            for (int j = 0; j < inputs.length; j++) {
                int input_1 = inputs[j][0];
                int input_2 = inputs[j][1];
                int expected_output = outputs[j];

                double output_1 = (input_1 * weight1) + (input_2 * weight4);
                double output_2 = (input_2 * weight3) + (input_1 * weight2);

                double sigmoid_output_1 = sigmoid((output_1 * weight5) + bias1);
                double sigmoid_output_2 = sigmoid((output_2 * weight6) + bias2);

                double output_3 = ((sigmoid_output_1 + sigmoid_output_2) * weight7) + bias3;

                double predicted_output = sigmoid(output_3);

                error = Math.pow((expected_output - predicted_output), 2);
                error_total += error;

                System.out.println(cores.get(i % 4) + "Epoch: " + (i + 1) + ", Input: [" + input_1 + ", " +
                        input_2 + "], Expected: " + expected_output + ", Predicted: " +
                        predicted_output + ", Error: " + error + ANSI_RESET);

                double delta_out = (2 * (predicted_output - expected_output))
                        * (predicted_output * (1 - predicted_output));

                double weight7_gradient = delta_out * (sigmoid_output_1 + sigmoid_output_2);
                double bias3_gradient = delta_out;

                double dE_h1 = delta_out * weight7;
                double dE_h2 = delta_out * weight7;
                double h1_n1 = sigmoid_output_1 * (1 - sigmoid_output_1);
                double h2_n1 = sigmoid_output_2 * (1 - sigmoid_output_2);
                double delta1 = dE_h1 * h1_n1;
                double delta2 = dE_h2 * h2_n1;

                double weight5_gradient = delta1 * output_1;
                double bias1_gradient = delta1;
                double weight6_gradient = delta2 * output_2;
                double bias2_gradient = delta2;

                double dE_doutput_1 = delta1 * weight5;
                double dE_doutput_2 = delta2 * weight6;

                double weight1_gradient = dE_doutput_1 * input_1;
                double weight4_gradient = dE_doutput_1 * input_2;
                double weight2_gradient = dE_doutput_2 * input_1;
                double weight3_gradient = dE_doutput_2 * input_2;

                weight7 -= LR * weight7_gradient;
                bias3 -= LR * bias3_gradient;

                weight5 -= LR * weight5_gradient;
                bias1 -= LR * bias1_gradient;
                weight6 -= LR * weight6_gradient;
                bias2 -= LR * bias2_gradient;

                weight1 -= LR * weight1_gradient;
                weight4 -= LR * weight4_gradient;
                weight2 -= LR * weight2_gradient;
                weight3 -= LR * weight3_gradient;
            }
        }

        double rmqe = Math.sqrt((error_total / EPOCHS));
        System.out.println("RMQE: " + rmqe);

    }

    static void ultimate_train(int[][] inputs, int[] outputs, int hidden_layers, int neurons_per_layer) {
    }

    static double sigmoid(double x) {
        return 1 / (1 + Math.exp(-x));
    }
}