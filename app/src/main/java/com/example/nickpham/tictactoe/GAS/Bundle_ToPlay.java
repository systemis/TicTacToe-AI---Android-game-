package com.example.nickpham.tictactoe.GAS;

/**
 * Created by nickpham on 03/11/2016.
 */

public class Bundle_ToPlay {

    String RealName1, RealName2;
    int _Minute_ToPlay;
    int choice_flags;


    public String getRealName1() {
        return RealName1;
    }

    public void setRealName1(String realName1) {
        RealName1 = realName1;
    }

    public String getRealName2() {
        return RealName2;
    }

    public void setRealName2(String realName2) {
        RealName2 = realName2;
    }

    public int get_Minute_ToPlay() {
        return _Minute_ToPlay;
    }

    public void set_Minute_ToPlay(int _Minute_ToPlay) {
        this._Minute_ToPlay = _Minute_ToPlay;
    }

    public int getChoice_flags() {
        return choice_flags;
    }

    public void setChoice_flags(int choice_flags) {
        this.choice_flags = choice_flags;
    }
}
