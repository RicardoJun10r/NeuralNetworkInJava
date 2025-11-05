import java.util.ArrayList;
import java.util.List;

public class CamadaEntrada {

    private List<Neuronio> neuronios;

    public CamadaEntrada(int quantidadeNeuronios) {
        this.neuronios = new ArrayList<>();
        inicializarNeuronios(quantidadeNeuronios);
    }

    private void inicializarNeuronios(int quantidadeNeuronios) {
        for (int i = 0; i < quantidadeNeuronios; i++) {
            Neuronio neuronio = new Neuronio(0, TipoNeuronio.SEM_BIAS);
            neuronios.add(neuronio);
        }
    }

    public List<Neuronio> getNeuronios() {
        return neuronios;
    }

}
