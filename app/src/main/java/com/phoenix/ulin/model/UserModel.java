package com.phoenix.ulin.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;


import com.phoenix.ulin.BR;

import java.io.Serializable;

public class UserModel extends BaseObservable implements Serializable {

    private String name;

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }
}
