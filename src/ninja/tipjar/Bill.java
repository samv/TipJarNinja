
package ninja.tipjar;

import java.util.ArrayList;
import ninja.tipjar.Split;

public class Bill {
    /* the total of the bill, before tax is added */
    double sub_amount;

    /* tax mode: don't tip the tax! */
    boolean tax_exclusive;
    double tax_rate_pct;

    /* The selected tip */
    double tip_target_pct;

    /* The parties splitting the bill */
    public ArrayList<Split> splits;

    /* default constructor */
    public Bill(double subAmount, double taxRatePct, double tipTargetPct) {
        sub_amount = subAmount;
        tax_rate_pct = taxRatePct;
        tax_exclusive = (tax_rate_pct > 0);
        tip_target_pct = tipTargetPct;
        splits = new ArrayList<Split>();
        splits.add(new Split());
    }

    public double getSubAmount() {
        return sub_amount;
    };
}