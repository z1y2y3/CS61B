package lab14;

import lab14lib.Generator;

public class AcceleratingSawToothGenerator implements Generator {

    private int period;
    private int state;
    private double factor;

    public AcceleratingSawToothGenerator(int i, double v) {
        state = 0;
        period = i;
        factor = v;
    }

    private double normalize(int state) {
        double normalized = (double) state / period - 1;
        return normalized;
    }

    @Override
    public double next() {
        state = (state + 1) % period;
        if (state == 0) {
            period = (int) (period * factor);
        }
        return normalize(state);
    }
}
