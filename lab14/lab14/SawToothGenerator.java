package lab14;

import lab14lib.Generator;

public class SawToothGenerator implements Generator {
    private int period;
    private int state;

    public SawToothGenerator(int period) {
        state = 0;
        this.period = period;
    }

    private double normalize(int state) {
        double normalized = (double) state / period - 1;
        return normalized;
    }

    @Override
    public double next() {
    /* Try creating a state variable that varies between 0 and period - 1,
    and write a helper function called normalize that converts values
    between 0 and period - 1 to values between -1.0 and 1.0.
    You should use the % operator, with the period as the argument to the right of the %.
    The state of your generator should be an integer that increments by 1 each time.
    */
        state = (state + 1) % period;
        return normalize(state);
    }
}
