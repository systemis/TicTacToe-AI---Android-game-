package com.example.nickpham.tictactoe.Handling_BoardPlay;

import java.util.List;

/**
 * Created by nickpham on 16/10/2016.
 */

public class About_Winer  {

    String name_flag;
    int Win_kindl;
    List<Integer> position_items_win;

    public String getName_flag() {
        return name_flag;
    }

    public void setName_flag(String name_flag) {
        this.name_flag = name_flag;
    }

    public int getWin_kindl() {
        return Win_kindl;
    }

    public void setWin_kindl(int win_kindl) {
        Win_kindl = win_kindl;
    }

    public List<Integer> getPosition_items_win() {
        return position_items_win;
    }

    public void setPosition_items_win(List<Integer> position_items_win) {
        this.position_items_win = position_items_win;
    }
}
