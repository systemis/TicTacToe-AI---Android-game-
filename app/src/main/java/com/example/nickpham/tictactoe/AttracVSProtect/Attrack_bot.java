package com.example.nickpham.tictactoe.AttracVSProtect;

import android.content.Context;
import android.util.Log;

import com.example.nickpham.tictactoe.GAS.Setup_flag;
import com.example.nickpham.tictactoe.Handling_BoardPlay.Save_CheckPoint;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by nickpham on 29/10/2016.
 */

public class Attrack_bot {

    Context context;
    String[][] input_matran;
    String S_QuanTao, S_QuanDich;

    Setup_flag style_flag = new Setup_flag();

    List<Save_CheckPoint> diemdanh_list = new ArrayList<>();
    Save_CheckPoint diemdanh_cuoi = null;


    public Attrack_bot(Context context, String[][] input_matran, String S_QuanTao, String S_QuanDich){
        this.context = context;
        this.input_matran = input_matran;
        this.S_QuanTao = S_QuanTao;
        this.S_QuanDich = S_QuanDich;

        check_hangngang(S_QuanTao, S_QuanDich );
        check_hangngang(S_QuanDich,S_QuanTao);

        check_hangdoc(S_QuanTao, S_QuanDich );
        check_hangdoc(S_QuanDich,S_QuanTao);


        check_taynam(S_QuanTao, S_QuanDich );
        check_taynam(S_QuanDich,S_QuanTao);

        return_diemdanhCuoi();

    }

    public boolean BangNhauHet(){
        boolean check_this = true;
        Save_CheckPoint diemDanh = diemdanh_list.get(0);
        for (int i = 0; i < diemdanh_list.size(); i++){
            if (diemDanh.getSoDiem_Danh() != diemdanh_list.get(i).getSoDiem_Danh())
            {
                check_this = false;
            }
        }
        return check_this;
    }

    public void return_diemdanhCuoi(){
        if (diemdanh_list.size() > 0){
            if(BangNhauHet() != true){
                Save_CheckPoint checkPoint = diemdanh_list.get(0);
                for (int i  = 0; i < diemdanh_list.size(); i ++){
                    if (diemdanh_list.get(i).getSoDiem_Danh() > checkPoint.getSoDiem_Danh())
                    {
                        checkPoint = diemdanh_list.get(i);
                    }
                }
                diemdanh_cuoi = checkPoint;
            }else {
                diemdanh_cuoi = diemdanh_list.get(new Random().nextInt(diemdanh_list.size()));
            }
        }
    }

    public int addto_diemDanhList(int soDiemDanh, int pos){
        Save_CheckPoint checkof = new Save_CheckPoint();
        checkof.setSoDiem_Danh(soDiemDanh);
        checkof.setVitri_Danh(pos);
        diemdanh_list.add(checkof);
        return 0;
    }

    public int check_hangngang(String quan_can1, String quan_can2){
        for(int dong = 0; dong < style_flag.getSo_dong(); dong++)
        {
            for (int cot = 0; cot < style_flag.getSo_cot() - 4; cot++)
            {
                if (input_matran[dong][cot].equals(quan_can1))
                {
                    int _dem = 0, diemtru = 0;
                    List<Integer> posList = new ArrayList<>();

                    for (int i = 0; i < 5; i ++){
                        String getS = input_matran[dong][cot + i];
                        if (getS.equals(quan_can1)) _dem ++;
                        if (getS.equals(quan_can2)) diemtru = -1;
                        if (getS.isEmpty()) posList.add((dong * style_flag.getSo_cot() + cot + i));
                    }

                    if (diemtru != -1){
                        if (_dem > 0 && posList.size() > 0){
                            int postLast = posList.get(0);
                            int Hieu = postLast - _dem;
                            for (int i = 0; i < posList.size(); i ++){
                                if(Hieu > (posList.get(i) - _dem)){
                                    Hieu = posList.get(i) - _dem;
                                    postLast = posList.get(i);
                                }
                            }
                            Log.d("Pi", String.valueOf(postLast));
                            addto_diemDanhList(20 + _dem, postLast);
                        }
                    }
                }
            }
        }
        return  0;
    }

    public int check_hangdoc(String quan_can1, String quan_can2){
        for(int dong = 0; dong < style_flag.getSo_dong() - 4; dong++)
        {
            for (int cot = 0; cot < style_flag.getSo_cot(); cot++)
            {
                if (input_matran[dong][cot].equals(quan_can1))
                {
                    int _dem = 0, diemtru = 0;
                    List<Integer> posList = new ArrayList<>();

                    for (int i = 0; i < 5; i ++)
                    {
                        // Nếu dòng này được thực thi tức là người chơi đã nhấn vào đâu
                        // đó mà theo hàng dọc chứa vị trí đó và gần nhất trên hàng dọc đấy có quân của người chơi.

                        String getS = input_matran[dong + i][cot];
                        if (getS.equals(quan_can1)) _dem ++;
                        if (getS.equals(quan_can2)) diemtru = -1;
                        if (getS.isEmpty()) posList.add((dong + i )  * (style_flag.getSo_cot()) + cot);
                    }

                    if (diemtru != -1){
                        if (_dem > 0 && posList.size() > 0){

                            int postLast = posList.get(0);
                            int Hieu = postLast - _dem;
                            for (int i = 0; i < posList.size(); i ++){
                                if(Hieu > (posList.get(i) - _dem)){
                                    Hieu = posList.get(i) - _dem;
                                    postLast = posList.get(i);
                                }
                            }
                            Log.d("Pi", String.valueOf(postLast));
                            addto_diemDanhList(20 + _dem, postLast);
                        }
                    }
                }
            }
        }
        return  0;
    }

    public int check_taynam(String quan_can1, String quan_can2){
        for(int dong = 0; dong < style_flag.getSo_dong() - 4; dong++)
        {
            for (int cot = 0; cot < style_flag.getSo_cot() - 4; cot++)
            {
                if (input_matran[dong][cot].equals(quan_can1))
                {
                    int _dem = 0, diemtru = 0;
                    List<Integer> posList = new ArrayList<>();

                    for (int i = 0; i < 5; i ++){
                        String getS = input_matran[dong + i][cot + i];
                        if (getS.equals(quan_can1)) _dem ++;
                        if (getS.equals(quan_can2)) diemtru = -1;
                        if (getS.isEmpty()) posList.add((dong + i )  * (style_flag.getSo_cot()) + cot + i);
                    }

                    if (diemtru != -1){
                        if (_dem > 0 && posList.size() > 0){
                            int postLast = posList.get(0);
                            int Hieu = postLast - _dem;
                            for (int i = 0; i < posList.size(); i ++){
                                if(Hieu > (posList.get(i) - _dem)){
                                    Hieu = posList.get(i) - _dem;
                                    postLast = posList.get(i);
                                }
                            }
                            Log.d("Pi", String.valueOf(postLast));
                            addto_diemDanhList(20 + _dem, postLast);
                        }
                    }
                }
            }
        }
        return  0;
    }


    public void check_taybac(String quan_can1, String quan_can2)
    {
        for (int dong = style_flag.getSo_dong() - 1; dong > 0; dong --)
        {
            for (int cot = 0; cot < style_flag.getSo_cot() - 4; cot ++)
            {
                if (input_matran[dong][cot].equals(quan_can1))
                {
                    int _dem = 0, diemtru = 0;
                    List<Integer> posList = new ArrayList<>();

                    for (int i = 0; i < 5; i ++){
                        String getS = input_matran[dong - i][cot + i];
                        if (getS.equals(quan_can1)) _dem ++;
                        if (getS.equals(quan_can2)) diemtru = -1;
                        if (getS.isEmpty()) posList.add((dong - i )  * (style_flag.getSo_cot()) + cot + i);
                    }

                    if (diemtru != -1){
                        if (_dem > 0 && posList.size() > 0){
                            int postLast = posList.get(posList.size() - 1);
                            int Hieu = postLast - _dem;
                            for (int i = posList.size() - 1; i >= 0; i --){
                                if(Hieu > (posList.get(i) - _dem))
                                {
                                    Hieu = posList.get(i) - _dem;
                                    postLast = posList.get(i);
                                }
                            }
                            Log.d("Pi", String.valueOf(postLast));
                            addto_diemDanhList(20 + _dem, postLast);
                        }
                    }
                }
            }
        }
    }

    public Save_CheckPoint getDiemdanh_cuoi() {
        return diemdanh_cuoi;
    }

}
