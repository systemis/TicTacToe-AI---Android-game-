package com.example.nickpham.tictactoe.GAS;

import com.example.nickpham.tictactoe.R;

/**
 * Created by nickpham on 18/10/2016.
 */

public class Setup_flag {

    // Class này có nhiệm vụ sẽ setup số cột, số dòng, hình ảnh cho quân X, hình ảnh cho quân O

    // Row and Colum ( show flag amount )
    int so_dong = 19;//14;
    int so_cot  = 19;

    int bieutuong_x = R.mipmap.flag_x;
    int bieutuong_o = R.mipmap.flag_o;

    int image_oco = R.mipmap.o_co_background_4;

    // Fag
    int image_x_flag = R.mipmap.o_co_backgtoound_x_4;
    int image_o_flag = R.mipmap.o_co_background_o_4;

    // Win X
    int image_x_flag_win0 = R.mipmap.o_co_background_x_win_4;
    int image_x_flag_win1 = R.mipmap.o_co_background_x_win_4;
    int image_x_flag_win2 = R.mipmap.o_co_background_x_win_4;
    int image_x_flag_win3 = R.mipmap.o_co_background_x_win_4;


    // Win O
    int image_o_flag_win0 = R.mipmap.o_co_background_o_win_4;
    int image_o_flag_win1 = R.mipmap.o_co_background_o_win_4;
    int image_o_flag_win2 = R.mipmap.o_co_background_o_win_4;
    int image_o_flag_win3 = R.mipmap.o_co_background_o_win_4;


    public int getImage_oco() {
        return image_oco;
    }


    public int getSo_dong() {
        return so_dong;
    }

    public int getSo_cot() {
        return so_cot;
    }

    public int getImage_x_flag() {
        return image_x_flag;
    }

    public int getImage_o_flag()          {
        return image_o_flag;
    }

    public int getImage_x_flag_win0() {
        return image_x_flag_win0;
    }

    public int getImage_x_flag_win1() {
        return image_x_flag_win1;
    }

    public int getImage_x_flag_win2() {
        return image_x_flag_win2;
    }

    public int getImage_x_flag_win3() {
        return image_x_flag_win3;
    }

    public int getImage_o_flag_win0() {
        return image_o_flag_win0;
    }

    public int getImage_o_flag_win1() {
        return image_o_flag_win1;
    }

    public int getImage_o_flag_win2() {
        return image_o_flag_win2;
    }

    public int getImage_o_flag_win3() {
        return image_o_flag_win3;
    }

    public int getBieutuong_x() {
        return bieutuong_x;
    }

    public int getBieutuong_o() {
        return bieutuong_o;
    }
}



