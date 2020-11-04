package com.cmpt276.iteration1practicalparent.coinFlip;

import java.util.Date;

public class CoinHistoryClass {
    private String pickersName;
    private Date time;
    private String face;
    private String winner;

    public CoinHistoryClass(String pickersName, Date time, String face, String winner) {
        this.pickersName = pickersName;
        this.time = time;
        this.face = face;
        this.winner = winner;
    }

    public String getPickersName() {
        return pickersName;
    }

    public void setPickersName(String pickersName) {
        this.pickersName = pickersName;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }
}
