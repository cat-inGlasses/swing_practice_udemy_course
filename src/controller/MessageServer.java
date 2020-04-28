package controller;

import model.MessageModel;

import java.util.*;

/**
 * This is a sort of simulated message server.
 */
public class MessageServer implements Iterable<MessageModel> {

    /**
     * Messages dictionary
     * Integer - server ID
     * List&lt;MessageMode&gt; - list of messages ont this server
     */
    private final Map<Integer, List<MessageModel>> messages;

    /**
     * List of messages from selected servers
     */
    private final List<MessageModel> selected;

    /**
     * Constructor.
     * Initialization Servers's messages list
     */
    public MessageServer() {
        messages = new TreeMap<>();
        selected = new ArrayList<>();

        List<MessageModel> list = new ArrayList<>();
        list.add(new MessageModel("The cat is missing", "Have you seen Felix anywhere"));
        list.add(new MessageModel("See you later?", "Are we still meeting in the pub"));
        messages.put(0, list);

        list = new ArrayList<>();
        list.add(new MessageModel("All personnel to deck 5", "An emergency has arisen"));
        list.add(new MessageModel("Database restart", "The database will be restarted at 6pm"));
        messages.put(1, list);

        list = new ArrayList<>();
        list.add(new MessageModel("How about dinner later?", "Are you doing anything later on?"));
        messages.put(2, list);

        list = new ArrayList<>();
        list.add(new MessageModel("How many times can a man look up", "Before he can see the sky? More on that story later."));
        list.add(new MessageModel("Company policy", "Please do not talk to the journalists outside."));
        list.add(new MessageModel("New update schedule", "From now on we will be updating the codebase every night at 1 a.m."));
        messages.put(3, list);

        list = new ArrayList<>();
        list.add(new MessageModel("Haggis available", "Free haggis at reception."));
        list.add(new MessageModel("Team building event", "There will be a team building event at the weekend. All employees must attend. Please ensure your life insurance is up to date before attending."));
        list.add(new MessageModel("Desk policy", "Please do not take your desks home with you. They are company property."));
        list.add(new MessageModel("Vending machine", "The coffee in the vending machine has been found to be contaminated with faeces. Please moderate your intake."));
        messages.put(4, list);
    }

    /**
     * Method loads messages from selected servers to selected messages list
     *
     * @param selectedServers list of selected servers
     */
    public void setSelectedServers(Set<Integer> selectedServers) {

        selected.clear();

        for (Integer id : selectedServers) {
            if (messages.containsKey(id)) {
                selected.addAll(messages.get(id));
            }
        }
    }

    /**
     * Method returns selected messages quantity
     *
     * @return size of selected messages list
     */
    public int getMessageCount() {
        return selected.size();
    }

    /**
     * {@inheritDoc}
     *
     * @return iterator over selected messages
     */
    @Override
    public Iterator<MessageModel> iterator() {
        return new MessageIterator<>(selected);
    }
}

/**
 * Implementation of this class is needed to simulate delay in message retrieving
 *
 * @param <T> the type of elements returned by this iterator
 */
class MessageIterator<T> implements Iterator<T> {

    /**
     * Field for object iterator
     */
    private final Iterator<T> iterator;

    /**
     * Constructor. Initialize iterator
     *
     * @param messages list of messages
     */
    public MessageIterator(List<T> messages) {
        iterator = messages.iterator();
    }

    /**
     * {@inheritDoc}
     *
     * @return if there is next element
     */
    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    /**
     * {@inheritDoc}
     *
     * @return next element
     */
    @Override
    public T next() {
        try {
            Thread.sleep(500); // simulate slow message retrieval from server
        } catch (InterruptedException e) {
            // do nothing
        }
        return iterator.next();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void remove() {
        iterator.remove();
    }
}
