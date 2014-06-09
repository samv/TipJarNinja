package ninja.tipjar;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
    CheckBox cbTaxMode;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tipjar);
        // create the default model
        state = new Bill(0d, 8.25, 15);
        findWidgets();
        connectWidgets();
        update(false);
    }

    private void findWidgets() {
        etBillSubAmount = (EditText) findViewById(R.id.etBillSubAmount);
        etTaxAmount = (EditText) findViewById(R.id.etTaxAmount);
        tvTaxPercent = (TextView) findViewById(R.id.tvTaxPercent);
        etTipAmount = (EditText) findViewById(R.id.etTipAmount);
        sbTipPercent = (SeekBar) findViewById(R.id.sbTipPercent);
        tvTipPercent = (TextView) findViewById(R.id.tvTipPercent);
        etBillAmount = (EditText) findViewById(R.id.etBillAmount);
        cbTaxMode = (CheckBox) findViewById(R.id.cbTaxMode);
    }

    public interface StateField{
        public double getState();
        public void setState(double Value);
    };

    class MoneyInputWatcher implements TextWatcher {
        StateField field;
        EditText et;
        public MoneyInputWatcher(EditText editText, StateField stateField) {
            field = stateField;
            et = editText;
        }

        @Override
        public void afterTextChanged(Editable e) {
            try {
                // could use parseDouble
                Double entered = new Double(et.getText().toString());
                double diff = entered.doubleValue() - field.getState();
                if ((diff >= 0.01) || (diff <= -0.01)) {
                    field.setState(entered.doubleValue());
                    update(true);
                }
            }
            catch (NumberFormatException nfe) {
            }
        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) { }
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
    };

    private void connectWidgets() {
        etBillSubAmount.addTextChangedListener
            (new MoneyInputWatcher
             (etBillSubAmount, new StateField() {
                     @Override public double getState() { return state.getSubAmount(); }
                     @Override public void setState(double val) { state.setSubAmount(val); }
              })
             );
        etTaxAmount.addTextChangedListener
            (new MoneyInputWatcher
             (etTaxAmount, new StateField() {
                     @Override public double getState() { return state.getTaxAmount(); }
                     @Override public void setState(double val) { state.setTaxAmount(val); }
              })
             );
        etTipAmount.addTextChangedListener
            (new MoneyInputWatcher
             (etTipAmount, new StateField() {
                     @Override public double getState() { return state.getTipAmount(); }
                     @Override public void setState(double val) { state.setTipAmount(val); }
              })
             );
        etBillAmount.addTextChangedListener
            (new MoneyInputWatcher
             (etBillAmount, new StateField() {
                     @Override public double getState() { return state.getTotalAmount(); }
                     @Override public void setState(double val) { state.setTotalAmount(val); }
              })
             );
        sbTipPercent.setOnSeekBarChangeListener
            (new SeekBar.OnSeekBarChangeListener() {
                    @Override public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        state.setTipPercent((double)(seekBar.getProgress()) / 10);
                        update(false);
                    }
                    @Override public void onStartTrackingTouch(SeekBar seekBar) {}
                    @Override public void onStopTrackingTouch(SeekBar seekBar) {}
             });
        cbTaxMode.setOnCheckedChangeListener
            (new CompoundButton.OnCheckedChangeListener() {
                    @Override public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked != state.getTaxMode()) {
                            if (isChecked) {
                                state.enableTaxMode();
                            }
                            else {
                                state.disableTaxMode();
                            }
                            update(false);
                        }
                    }
             });
    }

    void maybeSetText(TextView tv, boolean numeric, String newval) {
        if (tv.getText().toString() != newval) {
            if (!numeric || (Double.parseDouble(newval) != Double.parseDouble(tv.getText().toString()))) {
                tv.setText(newval);
            }
        }
    }

    /* function to update the various display widgets */
    public void update(boolean numeric)
    {
        maybeSetText(etBillSubAmount, numeric,
                     String.format("%.2f", state.getSubAmount()));
        maybeSetText(etTaxAmount, numeric,
                     String.format("%.2f", state.getTaxAmount()));
        cbTaxMode.setChecked(state.getTaxMode());
        if (state.getTaxMode()) {
            maybeSetText(tvTaxPercent, false,
                         String.format("%.2f%%", state.getTaxPercent()));
        }
        else {
            // TODO -grey out stuff
        }
        maybeSetText(etTipAmount, numeric,
                     String.format("%.2f", state.getTipAmount()));
        maybeSetText(tvTipPercent, false,
                     String.format("%.1f%%", state.getTipPercent()));
        int new_progress = Math.round(Math.round(state.getTipPercent() * 10));
        if (!numeric && (new_progress != sbTipPercent.getProgress())) {
            if (new_progress > sbTipPercent.getMax()) {
                sbTipPercent.setMax(new_progress + 1);
            }
            sbTipPercent.setProgress(new_progress);
        }
        maybeSetText(etBillAmount, numeric,
                     String.format("%.2f", state.getTotalAmount()));
    }

    public void setTip10pct(View x) {
        state.setTipPercent(10d);
        update(false);
    }
    public void setTip15pct(View x) {
        state.setTipPercent(15d);
        update(false);
    }
    public void setTip20pct(View x) {
        state.setTipPercent(20d);
        update(false);
    }
}
