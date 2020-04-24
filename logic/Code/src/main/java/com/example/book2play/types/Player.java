package com.example.book2play.types;

import com.google.gson.annotations.Expose;

import java.util.Objects;

/**
 * Simple class encapsulates the attributes for a player
 */
public class Player {
    @Expose
    private String playerId;

    public Player(String playerId) {
        this.playerId = playerId;
    }

    public String getPlayerId() {
        return playerId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerId);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (this == obj) {
            return true;
        }

        if (obj.getClass() != this.getClass()) {
            return false;
        }

        var other = (Player) obj;
        return playerId.equals(other.getPlayerId());
    }
}
