package io.rhiot.steroids.bootstrap;

import io.rhiot.steroids.Steroids;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Bootstrap {

    private final List<Initializer> initializers = Steroids.beans(Initializer.class);

    public void start() {
        Collections.sort(initializers, new Comparator<Initializer>() {
            @Override
            public int compare(Initializer o1, Initializer o2) {
                return o1.order() - o2.order();
            }
        });
        for(Initializer initializer : initializers) {
            initializer.start();
        }
    }

}
