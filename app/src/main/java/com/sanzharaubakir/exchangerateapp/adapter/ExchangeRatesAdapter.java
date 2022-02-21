package com.sanzharaubakir.exchangerateapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.sanzharaubakir.exchangerateapp.R;
import com.sanzharaubakir.exchangerateapp.model.ExchangeRate;

import java.util.List;

public class ExchangeRatesAdapter extends ArrayAdapter<ExchangeRate> {
    public ExchangeRatesAdapter(@NonNull Context context, int resource, @NonNull List<ExchangeRate> exchangeRates) {
        super(context, resource, exchangeRates);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ExchangeRate exchangeRate = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_listview, parent, false);
        }
        TextView currencyName = convertView.findViewById(R.id.currencyCode);
        TextView currencyValue = convertView.findViewById(R.id.currencyValue);
        currencyName.setText(exchangeRate.getCurrencyCode().toString());
        currencyValue.setText(String.valueOf(exchangeRate.getValue()));
        return convertView;
    }
}
