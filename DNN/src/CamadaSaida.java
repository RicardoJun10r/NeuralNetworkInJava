import java.util.ArrayList;
import java.util.List;

public class CamadaSaida {

    private List<Neuronio> neuronios;

    public CamadaSaida(int numSaida, int numPesosOculta) {
        this.neuronios = new ArrayList<>();
        inicializarNeuronios(numSaida, numPesosOculta);
    }

    private void inicializarNeuronios(int numSaida, int numPesosOculta) {
        for (int i = 0; i < numSaida; i++) {
            Neuronio neuronio = new Neuronio(numPesosOculta, TipoNeuronio.COM_BIAS);
            neuronios.add(neuronio);
        }
    }

    public List<Neuronio> getNeuronios() {
        return neuronios;
    }

}
