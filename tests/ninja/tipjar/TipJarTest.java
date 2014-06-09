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
        assertEquals("Subtotal", subAmount, bill.getSubAmount(), 0.001d);
        assertEquals("Tax %", taxPercent, bill.getTaxPercent(), 0.001d);
        assertEquals("Tip %", tipPercent, bill.getTipPercent(), 0.001d);

        assertEquals("Tax Amount", taxAmount, bill.getTaxAmount(), 0.001d);
        assertEquals("Tip Amount", tipAmount, bill.getTipAmount(), 0.001d);
        assertEquals("Total Amount", totalAmount, bill.getTotalAmount(), 0.001d);
    }

    @Test
    public void testMockupExample() {
        assertNotNull(bill);
        assertModelState(100.0d, 123.25d, 8.25d, 8.25d, 15d, 15d);
    }

    @Test
    public void testSetSubAmount() {
        bill.setSubAmount(0);
        assertModelState(0d, 0d, 8.25d, 0d, 15d, 0d);
        bill.setSubAmount(22.50);
        assertModelState(22.50d, 27.731d, 8.25d, 1.856d, 15d, 3.375d);
    }
}
