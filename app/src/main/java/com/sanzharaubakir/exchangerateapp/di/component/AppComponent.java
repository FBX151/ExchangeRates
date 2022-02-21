package com.sanzharaubakir.exchangerateapp.di.component;

import com.sanzharaubakir.exchangerateapp.MainActivity;
import com.sanzharaubakir.exchangerateapp.di.module.AppModule;
import com.sanzharaubakir.exchangerateapp.di.module.NetworkModel;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules={AppModule.class, NetworkModel.class})
public interface AppComponent {
    void inject(MainActivity activity);
}
