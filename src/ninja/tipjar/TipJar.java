package ninja.tipjar;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import ninja.tipjar.Bill;

public class TipJar extends Activity
{
    Bill state;
    EditText etBillSubAmount;
    EditText etTaxAmount;
    TextView tvTaxPercent;
    EditText etTipAmount;
    SeekBar sbTipPercent;
    TextView tvTipPercent;
    EditText etBillAmount;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tipjar);
        state = new Bill(0d, 8.25, 15);
        findWidgets();
        update();
    }

    private void findWidgets() {
        etBillSubAmount = (EditText) findViewById(R.id.etBillSubAmount);
        etTaxAmount = (EditText) findViewById(R.id.etTaxAmount);
        tvTaxPercent = (TextView) findViewById(R.id.tvTaxPercent);
        etTipAmount = (EditText) findViewById(R.id.etTipAmount);
        sbTipPercent = (SeekBar) findViewById(R.id.sbTipPercent);
        tvTipPercent = (TextView) findViewById(R.id.tvTipPercent);
        etBillAmount = (EditText) findViewById(R.id.etBillAmount);
    }

    /* function to update the various display widgets */
    public void update()
    {
        etBillSubAmount.setText
            (String.format("%.2f", state.getSubAmount()));
        etTaxAmount.setText
            (String.format("%.2f", state.getTaxAmount()));
        if (state.getTaxMode()) {
            tvTaxPercent.setText
                (String.format("%.2f%%", state.getTaxPercent()));
        }
        else {
            // TODO -grey out stuff
        }
        etTipAmount.setText
            (String.format("%.2f", state.getTipAmount()));
        tvTipPercent.setText
            (String.format("%.1f%%", state.getTipPercent()));
        sbTipPercent.setProgress((int)(state.getTipPercent() * 10));
        etBillAmount.setText
            (String.format("%.2f", state.getTotalAmount()));
    }

    public void setTip10pct(View x) {
        state.setTipPercent(10d);
        update();
    }
    public void setTip15pct(View x) {
        state.setTipPercent(15d);
        update();
    }
    public void setTip20pct(View x) {
        state.setTipPercent(20d);
        update();
    }
}
