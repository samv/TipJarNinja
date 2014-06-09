
package ninja.tipjar;

import java.util.ArrayList;
import ninja.tipjar.Split;

public class Bill {
    /* the total of the bill, before tax is added */
    double sub_amount;

    /* tax mode: don't tip the tax! */
    boolean tax_exclusive;
    double tax_rate_pct;
    double saved_tax_rate_pct;

    /* The selected tip */
    double tip_target_pct;

    /* The parties splitting the bill */
    public ArrayList<Split> splits;

    /* default constructor */
    public Bill(double subAmount, double taxRatePct, double tipTargetPct) {
        sub_amount = subAmount;
        tax_rate_pct = taxRatePct;
        tax_exclusive = (tax_rate_pct == 0);
        tip_target_pct = tipTargetPct;
        splits = new ArrayList<Split>();
        splits.add(new Split());
    }

    public double getSubAmount() {
        return sub_amount;
    };

    public void setSubAmount(double subAmount) {
        sub_amount = subAmount;
    };

    public double getTipAmount() {
        return sub_amount * tip_target_pct / 100d;
    };

    public double getTipPercent() {
        return tip_target_pct;
    };

    public void setTipPercent(double tipPercent) {
        tip_target_pct = tipPercent;
    };

    public void setTipAmount(double tipAmount) {
        if (sub_amount == 0) {
            // avoid divide by zero and add a feature:
            // calculate subtotal from tip amount :)
            sub_amount = tipAmount * 100 / tip_target_pct;
        }
        else {
            tip_target_pct = (tipAmount / sub_amount) * 100;
        }
    };

    public double getTaxAmount() {
        return sub_amount * tax_rate_pct / 100d;
    };

    public double getTaxPercent() {
        return tax_rate_pct;
    };

    public void setTaxAmount(double taxAmount) {
        if (sub_amount == 0) {
            if (tax_rate_pct == 0) {
                tax_rate_pct = 10;  // FIXME: get from settings
            }
            sub_amount = taxAmount * 100 / tax_rate_pct;
        }
        else {
            tax_rate_pct = (taxAmount / sub_amount) * 100;
        }
    };

    public void setTaxPercent(double taxPercent) {
        tax_rate_pct = taxPercent;
    }

    public boolean getTaxMode() {
        return !tax_exclusive;
    }

    public void disableTaxMode() {
        sub_amount += getTaxAmount();
        saved_tax_rate_pct = tax_rate_pct;
        tax_rate_pct = 0;
        tax_exclusive = true;
    }

    public void enableTaxMode() {
        tax_rate_pct = saved_tax_rate_pct;
        sub_amount = sub_amount /
            ((100 + tax_rate_pct) / 100);
        tax_exclusive = false;
    }

    public double getTotalAmount() {
        return sub_amount + getTaxAmount() + getTipAmount();
    };

    public void setTotalAmount(double totalAmount) {
        if (sub_amount == 0) {
            // reverse mode, as others.
            sub_amount = totalAmount /
                (100 + tip_target_pct + tax_rate_pct) * 100;
        }
        else {
            // setting total amount paid: update the tip percentage.
            double tip = totalAmount - sub_amount - getTaxAmount();
            tip_target_pct = (tip / sub_amount) * 100;
        }
    };
}
