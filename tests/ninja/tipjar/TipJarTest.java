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

    private void assertModelState(double subAmount, double totalAmount,
                                  double taxPercent, double taxAmount,
                                  double tipPercent, double tipAmount) {
        assertEquals("Subtotal", bill.getSubAmount(), subAmount, 0.001d);
        assertEquals("Tax %", bill.getTaxPercent(), taxPercent, 0.001d);
        assertEquals("Tip %", bill.getTipPercent(), tipPercent, 0.001d);

        assertEquals("Tax Amount", bill.getTaxAmount(), taxAmount, 0.001d);
        assertEquals("Tip Amount", bill.getTipAmount(), tipAmount, 0.001d);
        assertEquals("Total Amount", bill.getTotalAmount(), totalAmount, 0.001d);
    }

    @Test
    public void testMockupExample() {
        assertNotNull(bill);
        assertModelState(100.0d, 123.25d, 8.25d, 8.25d, 15d, 15d);
    }
    }
}
