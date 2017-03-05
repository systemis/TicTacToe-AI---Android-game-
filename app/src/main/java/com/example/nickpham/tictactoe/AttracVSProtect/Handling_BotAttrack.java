package com.example.nickpham.tictactoe.AttracVSProtect;

import android.content.Context;
import android.util.Log;

import com.example.nickpham.tictactoe.GAS.Setup_flag;
import com.example.nickpham.tictactoe.Handling_BoardPlay.Save_CheckPoint;

import java.util.ArrayList;
import java.util.List;

public class Handling_BotAttrack {

    Context context;
    String[][] input_matran;
    String S_QuanTao, S_QuanDich;
    Setup_flag style_flag = new Setup_flag();
    List<Save_CheckPoint> diemDanh_list = new ArrayList<>();
    Save_CheckPoint Diem_Danh = null;

    public Handling_BotAttrack(Context context, String[][] input_matran, String S_QuanDich, String S_QuanTao)
    {

        this.context       = context;
        this.input_matran  = input_matran;
        this.S_QuanDich    = S_QuanDich;
        this.S_QuanTao     = S_QuanTao;

        Log.d("Yeah", S_QuanTao);
        Log.d("Yeah", S_QuanDich);


        Attrack_bot attrack_SS = new Attrack_bot(context, input_matran, S_QuanTao, S_QuanDich);
        if (attrack_SS.getDiemdanh_cuoi() != null)
        {
            diemDanh_list.add(attrack_SS.getDiemdanh_cuoi());
        }

        check_hangbon_ngangdoc();
        check_hangbon_cheo();
        check_hangba_ngangdoc();
        check_hangba_cheo_p1();
        check_hangba_ngangdoc_p2();
        check_hangba_cheo_p2();

        Handling_TanCong();
    }

    public void Handling_TanCong()
    {
        Diem_Danh = new Save_CheckPoint();
        Diem_Danh.setSoDiem_Danh(-1);
        Diem_Danh.setVitri_Danh(-1);

        if (diemDanh_list.size() > 0) {

            Save_CheckPoint checkPoint = diemDanh_list.get(0);

            for (int i = 0; i < diemDanh_list.size(); i++) {
                if (checkPoint.getSoDiem_Danh() < diemDanh_list.get(i).getSoDiem_Danh()) {
                    checkPoint = diemDanh_list.get(i);
                }
            }

            Diem_Danh = checkPoint;
        }

    }

    public void add_list_savepoit(int Diem_Danh, int pos)
    {
        Save_CheckPoint checkPoint = new Save_CheckPoint();
        checkPoint.setSoDiem_Danh(Diem_Danh);
        checkPoint.setVitri_Danh(pos);
        diemDanh_list.add(checkPoint);

    }

    // Neu co co hoi o duong bon thi danh
    public void check_hangbon_ngangdoc()
    {

        // Tay - Dong
        for (int dong = 0; dong < style_flag.getSo_dong(); dong ++)
        {

            for (int cot = 0; cot < style_flag.getSo_cot() - 4; cot++)
            {
                // Truong hop 1
                if(input_matran[dong][cot].equals(S_QuanTao))
                {

                    int _Dem = 0, _Dem_ = 0, pos = -1, empty = 0;

                    for (int i = 0; i < 5; i ++)
                    {

                        String get_S = input_matran[dong][cot + i];
                        if (get_S.equals(S_QuanTao)) _Dem++ ;
                        if (get_S.isEmpty()) pos = (dong * style_flag.getSo_cot()) + (cot + i); empty ++;
                        if (get_S.equals(S_QuanDich)) _Dem_ += 1;

                    }


                    if (_Dem >= 4 && _Dem_ < 1) {
                        Log.d("ChuanBiThangBot_B_DongTay", String.valueOf(pos));
                        add_list_savepoit(100, pos);
                    }
                }

                // Truong hop 2
                if (input_matran[dong][cot].isEmpty() && input_matran[dong][cot + 1].equals(S_QuanTao))
                {

                    int _Dem = 0, _Dem_ = 0, pos = (dong * style_flag.getSo_cot()) + (cot);

                    for (int i = 0; i < 5; i ++) {
                        String get_S = input_matran[dong][cot + i];
                        if (get_S.equals(S_QuanTao)) _Dem++ ;
                        if (get_S.equals(S_QuanDich)) _Dem_ += 1;
                    }
                   if (_Dem >= 4  && _Dem_ < 1){
                       Log.d("ChuanBiThangBot_B2_DongTay", String.valueOf(pos));
                       add_list_savepoit(100, pos);
                   }

                }

            }

        }

        // Bac - Nam

        for (int dong = 0; dong < style_flag.getSo_dong() - 4; dong ++)
        {

            for (int cot = 0; cot < style_flag.getSo_cot(); cot++)
            {

                // Truong hop 1
                if (input_matran[dong][cot].equals(S_QuanTao))
                {

                    int _Dem = 0, _Dem_ = 0, pos = 0;

                    for (int i = 0; i  < 5; i ++)
                    {

                        if (input_matran[dong + i][cot].equals(S_QuanTao)) _Dem ++;
                        if (input_matran[dong + i][cot].equals(S_QuanDich)) _Dem_ += 1;
                        if (input_matran[dong + i][cot].isEmpty()) pos = ((dong + i) * style_flag.getSo_cot()) + cot;

                    }

                    if (_Dem >= 4 && _Dem_ < 1) {
                        Log.d("ChuanBiThangBot_B_BacNam", String.valueOf(pos));
                        add_list_savepoit(100, pos);

                    }

                }

                // Truong hop 2
                if (input_matran[dong][cot].isEmpty() && input_matran[dong + 1][cot].equals(S_QuanTao))
                {

                    int _Dem = 0, pos = ((dong) * style_flag.getSo_cot()) + cot;

                    for (int i = 0; i  < 5; i ++)
                    {

                        if (input_matran[dong + i][cot].equals(S_QuanTao)) _Dem ++;

                        if (input_matran[dong + i][cot].equals(S_QuanDich)) _Dem --;

                    }

                    if (_Dem >= 4) {
                        Log.d("ChuanBiThangBot_B2_BacNam", String.valueOf(pos));
                        add_list_savepoit(100, pos);

                    }

                }
            }

        }

    }

    public void check_hangbon_cheo()
    {

        // Tay - Nam
        for (int dong = 0; dong < style_flag.getSo_dong() - 4; dong ++)
        {

            for (int cot = 0; cot < style_flag.getSo_cot() - 4; cot ++)
            {
                // Truong hop 1
                if (input_matran[dong][cot].equals(S_QuanTao))
                {
                    int _dem = 0, _dem_ = 0, pos = 0;
                    for (int i = 0; i < 5; i++)
                    {
                        String get_S = input_matran[dong + i][cot + i];
                        if (get_S.equals(S_QuanTao)) _dem ++;
                        if (get_S.equals(S_QuanDich)) _dem_ ++;
                        if (get_S.isEmpty()) pos = ((dong + i) * style_flag.getSo_cot()) + (cot + i);

                    }

                    if (_dem >= 4 && _dem_ < 1)
                    {
                        Log.d("ChuanBiThang_B_TayNam", String.valueOf(pos));
                        add_list_savepoit(100, pos);
                    }
                }

                // Truong hop 2

                if (input_matran[dong][cot].isEmpty())
                {

                    int _dem = 0,  pos =  ((dong ) * style_flag.getSo_cot()) + (cot );

                    for (int i = 0; i < 5; i++)
                    {

                        String get_S = input_matran[dong + i][cot + i];
                        if (get_S.equals(S_QuanTao)) _dem++;
                        if (get_S.equals(S_QuanDich)) _dem --;
                    }


                    if (_dem >= 4)
                    {

                        Log.d("ChuanBiThang_B2_TayNam", String.valueOf(pos));
                        add_list_savepoit(100, pos);

                    }

                }

            }

        }

        // Tay - Bac
        for (int dong = style_flag.getSo_dong() - 1; dong > 4; dong --)
        {

            // Truong hop 1
            for (int cot = 0; cot < style_flag.getSo_cot() - 4; cot ++)
            {

                if (input_matran[dong][cot].equals(S_QuanTao))
                {

                    int _dem = 0, pos = 0;

                    for (int i = 0; i < 5; i++)
                    {
                        String get_S = input_matran[dong - i][cot + i];
                        if (get_S.equals(S_QuanTao)) _dem++;
                        if (get_S.equals(S_QuanDich)) _dem --;
                        if (get_S.isEmpty()) pos = ((dong - i) * style_flag.getSo_cot()) + (cot + i);

                    }


                    if (_dem >= 4)
                    {

                        Log.d("ChuanBiThang_B_TayBac", String.valueOf(pos));
                        add_list_savepoit(100, pos);

                    }

                }

                // Truong hop 2
                if (input_matran[dong][cot].isEmpty())
                {

                    int _dem = 0, pos = ((dong) * style_flag.getSo_cot()) + (cot);

                    for (int i = 0; i < 5; i++)
                    {

                        String get_S = input_matran[dong - i][cot + i];
                        if (get_S.equals(S_QuanTao)) _dem++;
                        if (get_S.equals(S_QuanDich)) _dem --;
                    }

                    if (_dem >= 4)
                    {
                        Log.d("ChuanBiThang_B2_TayBac", String.valueOf(pos));
                        add_list_savepoit(100, pos);
                    }

                }

            }

        }

    }

    public void check_hangba_ngangdoc()
    {

        // Tay - dong
        for (int dong = 0; dong < style_flag.getSo_dong(); dong ++)
        {
            for(int cot = 1; cot < style_flag.getSo_cot() - 4; cot ++)
            {

                if(input_matran[dong][cot].equals(S_QuanTao) && input_matran[dong][cot - 1].isEmpty() && input_matran[dong][cot + 3].isEmpty())
                {

                    int _Dem = 0, pos = (dong * style_flag.getSo_cot()) + (cot - 1);

                    for (int i = 0; i < 3; i++)
                    {

                        if (input_matran[dong][cot + i].equals(S_QuanTao)) _Dem++;

                    }

                    if (_Dem >= 3)
                    {

                        Log.d("ChuanBiThang_Ba1_TayDong", String.valueOf(pos));
                        add_list_savepoit(79, pos);

                    }
                }
            }

        }


        // Bac - Nam
        for (int dong = 1; dong < style_flag.getSo_dong() - 3; dong ++)
        {
            for (int cot = 0; cot < style_flag.getSo_cot(); cot ++)
            {
                if(input_matran[dong][cot].equals(S_QuanTao) && input_matran[dong - 1][cot].isEmpty() && input_matran[dong + 3][cot].isEmpty())
                {
                    int _Dem = 0, pos = ((dong  - 1) * style_flag.getSo_cot()) + (cot);
                    for (int i = 0; i < 3; i++)
                    {
                        if (input_matran[dong + i][cot].equals(S_QuanTao)) _Dem++;
                    }
                    if (_Dem >= 3)
                    {
                        Log.d("ChuanBiThang_Ba1_BacNam", String.valueOf(pos));
                        add_list_savepoit(79, pos);
                    }
                }
            }
        }
    }

    public void check_hangba_ngangdoc_p2()
    {
        // Tay - dong
        for (int dong = 0; dong < style_flag.getSo_dong(); dong ++)
        {
            for(int cot = 1; cot < style_flag.getSo_cot() - 4; cot ++)
            {
                if(input_matran[dong][cot].equals(S_QuanTao) && input_matran[dong][cot - 1].isEmpty() && input_matran[dong][cot + 4].isEmpty())
                {
                    int _Dem = 0, pos = 0;
                    for (int i = 0; i < 4; i++)
                    {
                        if (input_matran[dong][cot + i].equals(S_QuanTao)) _Dem++;
                        if (input_matran[dong][cot + i].equals(S_QuanDich)) _Dem --;
                        if (input_matran[dong][cot + i].isEmpty()) pos = (dong * style_flag.getSo_cot()) + (cot + i);
                    }
                    if (_Dem >= 3)
                    {
                        Log.d("ChuanBiThang_Ba1_TayDong2", String.valueOf(pos));
                        add_list_savepoit(85, pos);
                    }
                }
            }
        }

        // Bac - Nam
        for (int dong = 1; dong < style_flag.getSo_dong() - 4; dong ++)
        {
            for (int cot = 0; cot < style_flag.getSo_cot(); cot ++)
            {
                if(input_matran[dong][cot].equals(S_QuanTao) && input_matran[dong - 1][cot].isEmpty() && input_matran[dong + 4][cot].isEmpty())
                {
                    int _Dem = 0, pos = 0;
                    for (int i = 0; i < 4; i++)
                    {
                        if (input_matran[dong + i][cot].equals(S_QuanTao)) _Dem++;
                        if (input_matran[dong + i][cot].equals(S_QuanDich)) _Dem --;
                        if (input_matran[dong + i][cot].isEmpty()) pos = ((dong + i) * style_flag.getSo_cot()) + (cot);
                    }

                    if (_Dem >= 3)
                    {
                        Log.d("ChuanBiThang_Ba1_BacNam2", String.valueOf(pos));
                        add_list_savepoit(85, pos);
                    }
                }
            }
        }
    }

    public void check_hangba_cheo_p1()
    {
        // Tay - Nam
        for (int dong = 1; dong < style_flag.getSo_dong() - 3; dong++ )
        {
            for (int cot = 1; cot < style_flag.getSo_cot() - 3; cot ++)
            {
                if (input_matran[dong][cot].equals(S_QuanTao) && input_matran[dong - 1][cot - 1].isEmpty() && input_matran[dong + 3][cot + 3].isEmpty())
                {
                    int _Dem = 0, pos = ((dong - 1) * style_flag.getSo_cot()) + (cot - 1);
                    for (int i = 0; i < 3; i++)
                    {
                        if (input_matran[dong + i][cot + i].equals(S_QuanTao))
                        {
                            _Dem ++;
                        }
                    }
                    if (_Dem >= 3)
                    {
                        Log.d("DuongBa_TayNam", String.valueOf(pos));
                        add_list_savepoit(79, pos);
                    }
                }
            }
        }


        // Tay - Bac
        for (int dong = style_flag.getSo_dong() - 2; dong > 3; dong --)
        {
            for (int cot = 1; cot < style_flag.getSo_cot() - 3; cot++)
            {
                if(input_matran[dong + 1][cot - 1].isEmpty() && input_matran[dong][cot].equals(S_QuanTao) && input_matran[dong - 3][cot + 3].isEmpty())
                {
                    int _dem = 0, pos = (style_flag.getSo_cot() * (dong + 1) ) + (cot - 1);

                    for (int i = 0; i < 3; i++) {
                        if (input_matran[dong - i][cot + i].equals(S_QuanTao)) _dem++;
                    }
                    
                    if (_dem >= 3){
                        Log.d("DuongBa_TayBac", String.valueOf(pos));
                        add_list_savepoit(79, pos);
                    }
                    
                }
            }
        }
    }

    public void check_hangba_cheo_p2()
    {
        for (int dong  = 1; dong < style_flag.getSo_dong() - 4; dong ++)
        {
            for (int cot = 1; cot < style_flag.getSo_cot() - 4; cot++)
            {
                if (input_matran[dong][cot].equals(S_QuanTao) && input_matran[dong - 1][cot - 1].isEmpty() && input_matran[dong + 4][cot + 4].isEmpty())
                {
                    int _Dem = 0, pos = 0;

                    for (int i = 0; i < 4; i++) {
                        if (input_matran[dong + i][cot + i].equals(S_QuanTao)) _Dem++;
                        if (input_matran[dong + i][cot + i].equals(S_QuanDich)) _Dem --;
                        if (input_matran[dong + i][cot + i].isEmpty()) pos = ((dong + i)* style_flag.getSo_cot()) + (cot + i);
                    }

                    if (_Dem >= 3) {
                        Log.d("ChuanBiThang_Ba2_TayNam", String.valueOf(pos));
                        add_list_savepoit(85, pos);

                    }
                }
            }
        }


        for (int dong  = style_flag.getSo_dong() - 2; dong < 4; dong ++)
        {
            for (int cot = 1; cot < style_flag.getSo_cot() - 4; cot++)
            {
                if (input_matran[dong][cot].equals(S_QuanTao) && input_matran[dong + 1][cot - 1].isEmpty() && input_matran[dong - 4][cot + 4].isEmpty())
                {
                    int _Dem = 0, pos = 0;
                    for (int i = 0; i < 4; i++) {
                        if (input_matran[dong - i][cot + i].equals(S_QuanTao)) _Dem++;
                        if (input_matran[dong - i][cot + i].equals(S_QuanDich)) _Dem --;
                        if (input_matran[dong - i][cot + i].isEmpty()) pos = ((dong - i)* style_flag.getSo_cot()) + (cot + i);
                    }

                    if (_Dem >= 3) {
                        Log.d("ChuanBiThang_Ba2_TayBac", String.valueOf(pos));
                        add_list_savepoit(85, pos);

                    }
                }
            }
        }



    }


    public Save_CheckPoint getDiem_Danh() {
        return Diem_Danh;
    }
}
