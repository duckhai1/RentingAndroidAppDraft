package com.example.book2play.types;

import java.util.Objects;

public class Player {
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
