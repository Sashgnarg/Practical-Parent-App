package com.cmpt276.iteration1practicalparent.Model;

import com.cmpt276.iteration1practicalparent.UI.ConfigureChildren.ConfigureChildren;

import java.util.Date;

import androidx.recyclerview.widget.RecyclerView;

public class CoinHistoryClass {
    // class the save history information...
    // pickersName -> children name
    // time -> current time
    // face -> 1 -> head 2 > tail
    // winner -> win and lose
    private ConfigureChildrenItem child;
    private final String pickersName;
    private final Date time;
    private final int face;
    private final String winner;

    public CoinHistoryClass(ConfigureChildrenItem child, String pickersName, Date time, int face, String winner) {
        this.child= child;
        this.pickersName = pickersName;
        this.time = time;
        this.face = face;
        this.winner = winner;
    }

    public String getPickersName() {
        return pickersName;
    }

    public ConfigureChildrenItem getChild() { return child; }

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
