package gui.listeners;

import gui.helpers.FormEvent;

import java.util.EventListener;

/**
 * Interface for object,
 * that will preform action form when event listening occurs
 */
public interface FormListener extends EventListener {

    /**
     * Method should provide implementation for event
     *
     * @param e {@link FormEvent} object
     */
    void formEventOccurred(FormEvent e);
}
