package com.xsx.perporty;

import com.xsx.perporty.ChangeListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018\3\30 0030.
 */

public abstract class Perporty<T> {

    private T value = null;
    private T oldValue = null;
    private List<ChangeListener<T>> listeners;

    public Perporty() {
        listeners = new ArrayList<>();
    }

    public Perporty(T value) {
        this.value = value;

        listeners = new ArrayList<>();
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.oldValue = this.value;
        this.value = value;

        //消息更新，通知所有观察者
        if(this.value == null){
            if(this.oldValue != null)
                notifyChangeListener();
        }
        else if(!this.value.equals(this.oldValue))
            notifyChangeListener();
    }

    public void addChangeListener(ChangeListener<T> listener){
        listeners.add(listener);
    }

    public void removeChangeListener(ChangeListener<T> listener){
        if(!listeners.isEmpty())
            listeners.remove(listener);
    }

    public void notifyChangeListener(){
        for (ChangeListener listener : listeners) {
            listener.changed(oldValue, value);
        }
    }

}
