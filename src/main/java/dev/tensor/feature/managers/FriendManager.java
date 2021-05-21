package dev.tensor.feature.managers;

import dev.tensor.Tensor;
import dev.tensor.misc.imp.Manager;

import java.util.ArrayList;

/**
 * @author IUDevman
 * @since 05-20-2021
 */

public enum FriendManager implements Manager {

    INSTANCE;

    private final ArrayList<String> friends = new ArrayList<>();

    @Override
    public void load() {
        Tensor.INSTANCE.LOGGER.info("FriendManager");
    }

    public ArrayList<String> getFriends() {
        return this.friends;
    }

    public void addFriend(String name) {
        this.friends.add(name);
    }

    public void removeFriend(String name) {
        this.friends.remove(name);
    }

    public void clearFriends() {
        this.friends.clear();
    }

    public boolean isFriend(String name) {
        return this.friends.contains(name);
    }
}
