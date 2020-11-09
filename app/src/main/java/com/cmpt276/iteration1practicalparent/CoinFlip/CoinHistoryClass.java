package com.cmpt276.iteration1practicalparent.CoinFlip;

import java.util.Date;

public class CoinHistoryClass {
    // class the save history information...
    // pickersName -> children name
    // time -> current time
    // face -> 1 -> head 2 > tail
    // winner -> win and lose
    private final String pickersName;
    private final Date time;
    private final int face;
    private final String winner;

    public CoinHistoryClass(String pickersName, Date time, int face, String winner) {
        this.pickersName = pickersName;
        this.time = time;
        this.face = face;
        this.winner = winner;
    }

    public String getPickersName() {
        return pickersName;
    }

    public Date getTime() {
        return time;
    }

    public int getFace() {
        return face;
    }

    public String getWinner() {
        return winner;
    }

}
