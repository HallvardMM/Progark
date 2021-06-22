package com.tdt4240.paint2win.model;

import java.util.UUID;

/**
 * Used to create ID different objects
 */
public interface IIdentifiable {
        UUID getId();

    /**
     * Returns true if two IDs are equal
     * @param otherId
     * @return boolean
     */
    default boolean isIdEqual(UUID otherId) {
            return getId().equals(otherId);
        }
}
