import java.util.ArrayList;
import java.util.List;

public class CamadaOculta {

    private List<Neuronio> neuronios;

    public CamadaOculta(int quantidadeNeuronios, int numPesosEntrada) {
        this.neuronios = new ArrayList<>();
        inicializarNeuronios(quantidadeNeuronios, numPesosEntrada);
    }

    private void inicializarNeuronios(int quantidadeNeuronios, int numPesosEntrada) {
        for (int i = 0; i < quantidadeNeuronios; i++) {
            Neuronio neuronio = new Neuronio(numPesosEntrada, TipoNeuronio.COM_BIAS);
            neuronios.add(neuronio);
        }
    }

    public List<Neuronio> getNeuronios() {
        return neuronios;
    }

}
