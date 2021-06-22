package com.tdt4240.paint2win.container;

import java.util.List;
import java.util.stream.Stream;

public interface IContainer<T> {
    /**
     * add an instance to the container
     * @param toAdd an instance of the correct type T
     */
    void add(T toAdd);
    /**
     * Returns the List with all instances in the container
     * @return List of all instances
     */
    List<T> getAll();
    /**
     * Returns the contianers' stream
     * @return Stream<T> from Java 8
     */
    default Stream<T> stream() {
        return getAll().stream();
    }
    /**
     * Call to update instances in container
     * @param delta delta time used to make updating synchronized
     */
    void update(float delta);
}
