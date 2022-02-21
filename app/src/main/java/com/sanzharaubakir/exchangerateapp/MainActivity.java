package com.sanzharaubakir.exchangerateapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.sanzharaubakir.exchangerateapp.adapter.ExchangeRatesAdapter;
import com.sanzharaubakir.exchangerateapp.api.CurrencyConverterApiInterface;
import com.sanzharaubakir.exchangerateapp.model.CurrencyCode;
import com.sanzharaubakir.exchangerateapp.model.ExchangeRate;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private static final CurrencyCode DEFAULT_CURRENCY = CurrencyCode.USD;
    private static final String API_KEY = "ad157dc804501a8ecfda";
    @Inject
    CurrencyConverterApiInterface currencyConverterApiInterface;

    private TextView textView;

    private EditText amountEditText;

    private Spinner spinner;

    private ListView listView;

    private ArrayAdapter listViewAdapter;

    private ArrayAdapter spinnerAdapter;

    private CurrencyCode currentCurrencyCode = DEFAULT_CURRENCY;

    private List<ExchangeRate> exchangeRatesList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((MyApplication) getApplication()).getAppComponent().inject(this);

        setContentView(R.layout.activity_main);
        spinner = findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);
        listView = findViewById(R.id.list_View);
        amountEditText = findViewById(R.id.amount);
        exchangeRatesList = new LinkedList<>();

        updateExchangeRates(null);

        spinnerAdapter = new ArrayAdapter(this,  android.R.layout.simple_spinner_item,CurrencyCode.values());
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(spinnerAdapter);

        listViewAdapter = new ExchangeRatesAdapter(this, R.layout.activity_listview, exchangeRatesList);
        listView.setAdapter(listViewAdapter);

        textView = findViewById(R.id.rates);

        Observable<Map<String,Float>> exchangeRates = currencyConverterApiInterface.getExchangeRates(buildPayload(currentCurrencyCode), API_KEY);

        Disposable disposable = exchangeRates
                .delay(10000, TimeUnit.MILLISECONDS)
                .repeat(5)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> {
                    throwable.printStackTrace();
                })
                .subscribe(exchangeRatesOutput -> {
                        textView.setText(exchangeRatesOutput.toString());
                        updateExchangeRates(exchangeRatesOutput);
                    }
                );
    }

    private String buildPayload(CurrencyCode base) {
        StringBuilder rateNamesBuilder = new StringBuilder();
        for (CurrencyCode currencyCode : CurrencyCode.values()) {
            if (!currencyCode.equals(base)) {
                rateNamesBuilder.append(base + "_" + currencyCode + ",");
            }
        }
        rateNamesBuilder.deleteCharAt(rateNamesBuilder.lastIndexOf(","));
        return rateNamesBuilder.toString();
    }

    private void updateExchangeRates(Map<String,Float> exchangeRates) {
        exchangeRatesList.clear();
        float amount = 1.0f;
        String amountEntered = amountEditText.getText() != null ? amountEditText.getText().toString() : "";
        if (!amountEntered.isEmpty()) {
            amount = Float.parseFloat(amountEntered);
        }
        for (CurrencyCode code : CurrencyCode.values()) {
            if (!code.equals(currentCurrencyCode)) {
                String currenciesPair = currentCurrencyCode + "_" + code;
                if (exchangeRates != null && exchangeRates.containsKey(currenciesPair)){
                    exchangeRatesList.add(new ExchangeRate(code, exchangeRates.get(currenciesPair) * amount));
                }
                else {
                    exchangeRatesList.add(new ExchangeRate(code, 0.0f));
                }
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        currentCurrencyCode = (CurrencyCode) adapterView.getSelectedItem();
        updateExchangeRates(null);
        listViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}