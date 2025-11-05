import java.util.ArrayList;
import java.util.List;

public class Neuronio {

    private List<Double> weights;

    private Double bias;

    private Double output;

    public Neuronio(int numPesosCamadaAnterior, TipoNeuronio tipo) {
        this.weights = new ArrayList<>();
        this.output = 0.;
        for (int i = 0; i < numPesosCamadaAnterior; i++) {
            this.weights.add(Math.random());
        }
        if (tipo == TipoNeuronio.COM_BIAS) {
            this.bias = Math.random();
        } else {
            this.bias = 0.;
        }
    }

    public double getOutput() {
        return output;
    }

    public Double getWeight(int index) {
        return weights.get(index);
    }

    public double getBias() {
        return bias;
    }

    public void setWeight(int index, double weight) {
        this.weights.set(index, weight);
    }

    public void setBias(double bias) {
        this.bias = bias;
    }

    public void setOutput(double output) {
        this.output = output;
    }

}
