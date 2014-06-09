package ninja.tipjar;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import ninja.tipjar.Bill;

public class TipJarTest {
    Bill bill;

    @Before
    public void setUp() {
        bill = new Bill(100.0d, 8.25d, 15.0d);
    }

    @Test
    public void thisAlwaysPasses() {
        assertNotNull(bill);
        assertEquals(bill.getSubAmount(), 100.0d, 0.001d);
    }
}
