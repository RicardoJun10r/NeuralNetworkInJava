package io.github.cmd;

import io.github.model.GerarGrafico;
import io.github.model.NeuralNetwork;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

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

        NeuralNetwork neuralNetwork = new NeuralNetwork(xor_input, xor_output, 2, 0.5, true);
        neuralNetwork.train(5000);
        try {
            BufferedImage grafico = GerarGrafico.gerarGrafico("Erro da rede neural por epócas", "Epóca", "Valor",
                    neuralNetwork.getErrorHistory());
            ImageIO.write(grafico, "png", new File("neural-network/tmp/erro.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}