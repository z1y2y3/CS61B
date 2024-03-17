import org.junit.Test;

import static org.junit.Assert.*;

public class FlikTest {
    //boolean isSameNumber(Integer a, Integer b)
    // determine whether two Integers are the same or not.
    @Test
    public void isSameNumberTest() {
        assertTrue("100", Flik.isSameNumber(100, 100));
        assertTrue("128", Flik.isSameNumber(128, 128));
    }

}
