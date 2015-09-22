package io.rhiot.steroids.bootstrap;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static io.rhiot.steroids.Steroids.beans;

public class Bootstrap {

    public void start() {
        List<Initializer> initializers = beans(Initializer.class);
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
