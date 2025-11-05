public class Main {
    static final int EPOCHS = 5000;
    static final double LR = 0.1;

    public static void main(String[] args) {

        Double[][] and_input = {
                { 0., 0. },
                { 0., 1. },
                { 1., 0. },
                { 1., 1. }
        };

        Double[] and_output = { 0., 0., 0., 1. };

        Double[][] xor_input = {
                { 0., 0. },
                { 0., 1. },
                { 1., 0. },
                { 1., 1. }
        };

        Double[] xor_output = { 0., 1., 1., 0. };

        RedeNeural redeNeural = new RedeNeural(xor_input, xor_output, 2, LR, true);
        System.out.println("AND Gate Training:");
        redeNeural.train(EPOCHS);

    }

}