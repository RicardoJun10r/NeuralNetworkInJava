import java.util.List;

public class RedeNeural {

    private CamadaEntrada camadaEntrada;

    private CamadaOculta camadaOculta;

    private CamadaSaida camadaSaida;

    private double learningRate = 0.005;

    private boolean mostrarErro = true;

    private Double[][] entradaTreinamento;

    private Double[] saidaTreinamento;

    public RedeNeural(Double[][] entrada, Double[] saida, int quantidadeNeuroniosOculta, double learningRate,
            boolean mostrarErro) {
        this.entradaTreinamento = entrada;
        this.saidaTreinamento = saida;
        int numNeuroniosEntrada = entrada[0].length;
        int numSaida = saida.length;
        this.camadaEntrada = new CamadaEntrada(numNeuroniosEntrada);
        this.camadaOculta = new CamadaOculta(quantidadeNeuroniosOculta, numNeuroniosEntrada);
        this.camadaSaida = new CamadaSaida(numSaida, quantidadeNeuroniosOculta);
        this.learningRate = learningRate;
        this.mostrarErro = mostrarErro;
    }

    public void train(int epocas) {
        for (int epoca = 0; epoca < epocas; epoca++) {
            System.out.println("Epoca: " + (epoca + 1));
            for (int i = 0; i < this.entradaTreinamento.length; i++) {
                feedFoward((this.entradaTreinamento[i]), this.saidaTreinamento);
            }
        }
    }

    private void feedFoward(Double[] entradas, Double[] saida) {

        List<Neuronio> neuroniosEntrada = camadaEntrada.getNeuronios();

        List<Neuronio> neuroniosOculta = camadaOculta.getNeuronios();

        List<Neuronio> neuroniosSaida = camadaSaida.getNeuronios();

        entrada(neuroniosEntrada, entradas);

        entradaOculta(neuroniosEntrada, neuroniosOculta);

        ocultaSaida(neuroniosOculta, neuroniosSaida);

        saida(neuroniosSaida, saida);

        backpropagation(neuroniosEntrada, neuroniosOculta, neuroniosSaida, entradas, saida);

    }

    private void entrada(List<Neuronio> neuroniosEntrada, Double[] entrada) {
        for (int i = 0; i < neuroniosEntrada.size(); i++) {
            Neuronio neuronioEntrada = neuroniosEntrada.get(i);
            neuronioEntrada.setOutput(entrada[i]);
        }
    }

    private void entradaOculta(List<Neuronio> neuroniosEntrada, List<Neuronio> neuroniosOculta) {
        for (int i = 0; i < neuroniosOculta.size(); i++) {
            Neuronio neuronioOculta = neuroniosOculta.get(i);
            double somatorioOculta = 0;
            for (int j = 0; j < neuroniosEntrada.size(); j++) {
                Neuronio neuronioEntrada = neuroniosEntrada.get(j);
                somatorioOculta += neuronioEntrada.getOutput() * neuronioOculta.getWeight(j);
            }
            somatorioOculta += neuronioOculta.getBias();
            somatorioOculta = sigmoid(somatorioOculta);
            neuronioOculta.setOutput(somatorioOculta);
        }
    }

    private void ocultaSaida(List<Neuronio> neuroniosOculta, List<Neuronio> neuroniosSaida) {
        for (int i = 0; i < neuroniosSaida.size(); i++) {
            Neuronio neuronioSaida = neuroniosSaida.get(i);
            double somatorioSaida = 0;
            for (int j = 0; j < neuroniosOculta.size(); j++) {
                Neuronio neuronioOculta = neuroniosOculta.get(j);
                somatorioSaida += neuronioOculta.getOutput() * neuronioSaida.getWeight(j);
            }
            somatorioSaida += neuronioSaida.getBias();
            somatorioSaida = sigmoid(somatorioSaida);
            neuronioSaida.setOutput(somatorioSaida);
        }
    }

    private void saida(List<Neuronio> neuroniosSaida, Double[] saida) {
        double erroTotal = 0;
        for (int i = 0; i < neuroniosSaida.size(); i++) {
            Neuronio neuronioSaida = neuroniosSaida.get(i);
            double erro = Math.pow((saida[i] - neuronioSaida.getOutput()), 2);
            erroTotal += erro;
            if (mostrarErro) {
                System.out.println("Saida esperada: " + saida[i] + " - Saida obtida: " + neuronioSaida.getOutput()
                        + " - Erro: " + erro);
            }
        }
        erroTotal = erroTotal / 2;
        System.out.println("Erro: " + erroTotal);
    }

    private void backpropagation(List<Neuronio> neuroniosEntrada, List<Neuronio> neuroniosOculta,
            List<Neuronio> neuroniosSaida, Double[] entradas, Double[] saida) {

        double[] deltaSaida = new double[neuroniosSaida.size()];
        for (int i = 0; i < neuroniosSaida.size(); i++) {
            Neuronio neuronioSaida = neuroniosSaida.get(i);
            double erroSaida = 2 * (neuronioSaida.getOutput() - saida[i]);

            deltaSaida[i] = erroSaida * sigmoidDerivada(neuronioSaida.getOutput());
        }

        double[] deltaOculta = new double[neuroniosOculta.size()];
        for (int i = 0; i < neuroniosOculta.size(); i++) {
            Neuronio neuronioOculta = neuroniosOculta.get(i);
            double erroOculta = 0;
            for (int j = 0; j < neuroniosSaida.size(); j++) {
                Neuronio neuronioSaida = neuroniosSaida.get(j);
                erroOculta += deltaSaida[j] * neuronioSaida.getWeight(i);
            }
            deltaOculta[i] = erroOculta * sigmoidDerivada(neuronioOculta.getOutput());
        }

        for (int i = 0; i < neuroniosSaida.size(); i++) {
            Neuronio neuronioSaida = neuroniosSaida.get(i);
            for (int j = 0; j < neuroniosOculta.size(); j++) {
                Neuronio neuronioOculta = neuroniosOculta.get(j);

                double weightGradient = deltaSaida[i] * neuronioOculta.getOutput();
                double novoWeight = neuronioSaida.getWeight(j) - (learningRate * weightGradient);
                neuronioSaida.setWeight(j, novoWeight);
            }
            double biasGradient = deltaSaida[i];
            double novoBias = neuronioSaida.getBias() - (learningRate * biasGradient);
            neuronioSaida.setBias(novoBias);
        }

        for (int i = 0; i < neuroniosOculta.size(); i++) {
            Neuronio neuronioOculta = neuroniosOculta.get(i);
            for (int j = 0; j < neuroniosEntrada.size(); j++) {
                Neuronio neuronioEntrada = neuroniosEntrada.get(j);

                double weightGradient = deltaOculta[i] * neuronioEntrada.getOutput();
                double novoWeight = neuronioOculta.getWeight(j) - (learningRate * weightGradient);
                neuronioOculta.setWeight(j, novoWeight);
            }
            double biasGradient = deltaSaida[i];
            double novoBias = neuronioOculta.getBias() - (learningRate * biasGradient);
            neuronioOculta.setBias(novoBias);
        }

    }

    private static double sigmoid(double x) {
        return 1 / (1 + Math.exp(-x));
    }

    private static double sigmoidDerivada(double x) {
        return x * (1 - x);
    }

}
