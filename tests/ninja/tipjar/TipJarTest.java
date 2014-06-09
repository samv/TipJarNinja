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
    public void testMockupExample() {
        assertNotNull(bill);
        assertEquals(bill.getSubAmount(), 100.0d, 0.001d);
        assertEquals(bill.getTaxAmount(), 8.25d, 0.001d);
        assertEquals(bill.getTaxPercent(), 8.25d, 0.001d);
        assertEquals(bill.getTipAmount(), 15d, 0.001d);
        assertEquals(bill.getTipPercent(), 15.0d, 0.001d);
        assertEquals(bill.getTotalAmount(), 123.25d, 0.001d);
    }
}
