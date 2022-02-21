package com.sanzharaubakir.exchangerateapp.model;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/*

    Free version of https://free.currconv.com/ supports only 2 pairs maximum,
    We can extend this list by purchasing paid version of the API
    By fetching all rates with single network call, we can save time
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
public  class ExchangeRate {
    private CurrencyCode currencyCode;
    private Float value;
}
