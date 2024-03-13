package com.dream.spi.Iterable;

import java.util.*;
import java.util.function.Consumer;

/**
 *
 * @author 思维穿梭
 * @param <T>
 *
 */
public class MyIterable <T> implements Iterable<T>{


    private List<T> providers = new ArrayList<>();

    public MyIterable() {}
    public MyIterable(List<T> list) {
        this.providers = list;
    }

    public List<T> getProviders() {
        return providers;
    }

    public void setProviders(List<T> providers) {
        this.providers = providers;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {

            Iterator<T> knownProviders
                    = providers.iterator();

            @Override
            public boolean hasNext() {
                if (knownProviders.hasNext()){
                    return true;
                }
                return false;
            }

            @Override
            public T next() {
                if (knownProviders.hasNext()){
                    return knownProviders.next();
                }
                return null;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }

        };
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        Iterable.super.forEach(action);
    }
}
