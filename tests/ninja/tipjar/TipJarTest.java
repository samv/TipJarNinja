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

    @Test
    public void testSetTax() {
        bill.setTaxPercent(7.25);
        assertModelState(100.0d, 122.25d, 7.25d, 7.25d, 15d, 15d);
        // un-checking the 'tax mode' button means that the tax-inclusive
        // figure is the basis for the tip.
        bill.disableTaxMode();
        assertModelState(107.25d, 123.338d, 0d, 0d, 15d, 16.088d);
        // checking it again puts it back
        bill.enableTaxMode();
        assertModelState(100.0d, 122.25d, 7.25d, 7.25d, 15d, 15d);

        // the tax amount can also be set, in which case the tax rate
        // adjusts.
        bill.setTaxAmount(8d);
        assertModelState(100.0d, 123d, 8d, 8d, 15d, 15d);

        // if no bill amount is entered, setting the tax amount sets
        // the bill amount
        bill.setSubAmount(0d);
        assertModelState(0.0d, 0d, 8d, 0d, 15d, 0d);
        bill.setTaxAmount(4d);
        assertModelState(50.0d, 61.5d, 8d, 4d, 15d, 7.5d);
    }

    @Test
    public void testSetTip() {
        // the buttons will call these...
        bill.setTipPercent(20);
        assertModelState(100.0d, 128.25d, 8.25d, 8.25d, 20d, 20d);
        bill.setTipPercent(25);
        assertModelState(100.0d, 133.25d, 8.25d, 8.25d, 25d, 25d);
        bill.setTipPercent(15);
        assertModelState(100.0d, 123.25d, 8.25d, 8.25d, 15d, 15d);

        // the slider calls this
        bill.setTipPercent(17d);
        assertModelState(100.0d, 125.25d, 8.25d, 8.25d, 17d, 17d);

        // setting the tip entry calls this
        bill.setTipAmount(12d);
        assertModelState(100.0d, 120.25d, 8.25d, 8.25d, 12d, 12d);

        // again, bill can be back-calculated.
        bill.setSubAmount(0);
        assertModelState(0.0d, 0d, 8.25d, 0d, 12d, 0d);
        bill.setTipAmount(12d);
        assertModelState(100.0d, 120.25d, 8.25d, 8.25d, 12d, 12d);
    }

    @Test
    public void testSetTotalAmount() {
        // setting the total adjusts the *tip*
        bill.setTotalAmount(125d);
        assertModelState(100.0d, 125d, 8.25d, 8.25d, 16.75d, 16.75d);

        // un-setting the bill subtotal does the back-calculation again
        bill.setSubAmount(0);
        assertModelState(0.0d, 0d, 8.25d, 0d, 16.75d, 0d);

        bill.setTotalAmount(150d);
        assertModelState(120.0d, 150d, 8.25d, 9.9d, 16.75d, 20.1d);
    }

}
