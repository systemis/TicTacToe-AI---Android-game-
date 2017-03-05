package com.example.nickpham.tictactoe.AttracVSProtect;

import android.content.Context;
import android.util.Log;
import com.example.nickpham.tictactoe.GAS.Setup_flag;
import com.example.nickpham.tictactoe.Handling_BoardPlay.Save_CheckPoint;

import java.util.ArrayList;
import java.util.List;

public class Handling_PhongThu
{

    Context context;
    String[][] input_matrans;
    String S_QuanDich= "x", S_QuanBot = "o";
    List<Save_CheckPoint> diemDanhList = new ArrayList<Save_CheckPoint>();
    Setup_flag style_flag = new Setup_flag();

    Save_CheckPoint diem_Danh = null;


    public Handling_PhongThu(Context context, String[][] input_matran, String S_QuanDich, String S_QuanTao)
    {

        this.context = context;
        this.input_matrans = input_matran;

        this.S_QuanDich = S_QuanDich;
        this.S_QuanBot  = S_QuanTao;
        this.S_QuanBot = return_flag_S();

        check_Duongba();
        check_KhoangCach2_DN();
        check_KhoangCach2_CH();
        check_DuongBa2_CH();
        check_DuongBa2_DN();

        Handling_PhongThu();

    }


    public void Handling_PhongThu()
    {
        diem_Danh = new Save_CheckPoint();

        diem_Danh.setSoDiem_Danh(-1);
        diem_Danh.setVitri_Danh(-1);

        if (diemDanhList.size() > 0)
        {
            diem_Danh = diemDanhList.get(0);

            for (int i = 0; i < diemDanhList.size(); i ++ )
            {
                if (diemDanhList.get(i).getSoDiem_Danh() > diem_Danh.getSoDiem_Danh()) {
                    diem_Danh = diemDanhList.get(i);
                }
            }
        }
    }

    public String return_flag_S()
    {
        String flag_S_bot = "o";

        if(S_QuanDich.equals("x"))
        {
            flag_S_bot = "o";
        }else
        {
            flag_S_bot = "x";
        }

        return flag_S_bot;
    }

    public void Them_DiemPhongNgu(int Diem_Danh, int vitri_Danh)
    {

        Save_CheckPoint checkPoint = new Save_CheckPoint();

        checkPoint.setSoDiem_Danh(Diem_Danh);

        checkPoint.setVitri_Danh(vitri_Danh);

        diemDanhList.add(checkPoint);

    }

    public void check_Duongba()
    {

        // Dong - Tay
        for (int dong = 0; dong < style_flag.getSo_dong(); dong ++){

            for (int cot = 0; cot < style_flag.getSo_cot(); cot++){

                if(input_matrans[dong][cot].equals(S_QuanDich)){

                    if (cot > 0 && cot < style_flag.getSo_cot() - 3 && input_matrans[dong][cot - 1].isEmpty() && input_matrans[dong][cot + 3].isEmpty()) {

                        int so_Dem = 0;
                        for (int i = 0; i < 3; i++){

                            if (input_matrans[dong][cot + i].equals(S_QuanDich)) so_Dem ++;

                        }

                        if (so_Dem == 3){

                            Log.d("VitriCanQuanTamDongTay ",String.valueOf((dong * style_flag.getSo_cot()) + (cot - 1)));
                            Them_DiemPhongNgu(82, (dong * style_flag.getSo_cot()) + (cot - 1));

                        }

                    }

                }

            }

        }

        // Nam - Bac
        for (int dong = 0; dong < style_flag.getSo_dong(); dong++){

            for (int cot = 0; cot < style_flag.getSo_cot(); cot++){

                if(input_matrans[dong][cot].isEmpty() == false && input_matrans[dong][cot].equals(S_QuanDich)){

                    if (dong > 0 && dong < style_flag.getSo_dong() - 3 && input_matrans[dong - 1][cot].isEmpty() && input_matrans[dong + 3][cot].isEmpty()){

                        int so_Dem = 0;

                        for (int i = 0; i < 3; i++){

                            if (input_matrans[dong + i][cot].equals(S_QuanDich)) so_Dem ++;

                        }

                        if (so_Dem == 3) {

                            Log.d("VitriCanQuanTam_NamBac ",String.valueOf((dong * style_flag.getSo_cot()) + (cot - 1)));
                            Them_DiemPhongNgu(82, ((dong - 1)* style_flag.getSo_cot()) + (cot));

                        }

                    }

                }

            }

        }

        // Tay -  Nam
        for (int dong = 1; dong < style_flag.getSo_dong() - 3; dong++){

            for (int cot = 1; cot < style_flag.getSo_cot() - 3; cot++){

                if (input_matrans[dong][cot].equals(S_QuanDich)) {

                    if (input_matrans[dong - 1][cot - 1].isEmpty() && input_matrans[dong + 3][cot + 3].isEmpty()) {

                        int so_dem = 0;

                        for (int i = 0; i < 3; i++) {

                            if (input_matrans[dong + i][cot + i].equals(S_QuanDich)) so_dem++;

                        }


                        if (so_dem >= 3) {

                            int posi = ((dong - 1) * style_flag.getSo_cot()) + (cot - 1);

                            Log.d("VitriCanQuanTam_TayBac ", String.valueOf(posi));

                            Them_DiemPhongNgu(82, posi);

                        }

                    }

                }

            }

        }




        // Tay - Bac
        for (int dong = style_flag.getSo_dong() - 2; dong > 3; dong --){

            for (int cot = 1; cot < style_flag.getSo_cot() - 3; cot ++){

                if (input_matrans[dong][cot].equals(S_QuanDich) && input_matrans[dong + 1][cot -  1].isEmpty() && input_matrans[dong - 3][cot + 3].isEmpty()){

                    int so_Dem = 0;

                    for (int i = 0; i < 3; i++)
                    {
                        if (input_matrans[dong - i][cot + i].equals(S_QuanDich)) so_Dem ++;
                    }

                    if (so_Dem >= 3){

                        int posi = ( (dong + 1) * style_flag.getSo_cot() ) + (cot - 1);

                        Log.d("VitriCanQuanTam_DongNam ",String.valueOf(posi));
                        Them_DiemPhongNgu(82, posi);

                    }

                }

            }

        }

    }

    public void check_KhoangCach2_DN()
    {

        for(int dong = 0; dong < style_flag.getSo_dong(); dong++)
        {
            for (int cot = 0; cot < style_flag.getSo_cot() - 4; cot++){

            }
        }

        // Dong - tay
        for (int dong = 0; dong < style_flag.getSo_dong(); dong ++){

            for (int cot = 0; cot < style_flag.getSo_cot() - 4; cot++){

                if (input_matrans[dong][cot].equals(S_QuanDich)){
                    int _Dem = 0, pos = -1;

                    for (int i = 0; i < 5; i ++){

                        if(input_matrans[dong][cot + i].equals(S_QuanDich)) _Dem++;

                        if (input_matrans[dong][cot + i].isEmpty()) pos = (dong * style_flag.getSo_cot())+(cot + i);

                        if(input_matrans[dong][cot + i].equals(S_QuanBot)) _Dem --;

                    }


                    if (_Dem >= 4 && pos != -1){

                        Log.d("Attack_bot_DongTay ", String.valueOf(pos));
                        Them_DiemPhongNgu(92, pos);

                    }

                }


                if (input_matrans[dong][cot + 1].equals(S_QuanDich) && input_matrans[dong][cot].isEmpty())
                {

                    int position_check = ((dong ) * style_flag.getSo_cot()) + (cot);

                    int so_dem = 0;

                    for (int i = 0; i < 5; i++)
                    {

                        String get_String = input_matrans[dong][cot + i];

                        if (get_String.equals(S_QuanDich)) so_dem ++;

                        if (get_String.equals(S_QuanBot)) so_dem --;

                    }

                    if(so_dem == 4)
                    {

                        Log.d("Attack_bot_DongTay ", String.valueOf(position_check));
                        Them_DiemPhongNgu(92, position_check);

                    }

                }

            }

        }


        // Nam - Bac
        for (int dong = 0; dong < style_flag.getSo_dong() - 4; dong ++)
        {

            for (int cot = 0; cot < style_flag.getSo_cot(); cot++)
            {

                if (input_matrans[dong][cot].equals(S_QuanDich))
                {

                    int _Dem = 0, pos = -1;

                    for (int i = 0; i < 5; i ++)
                    {

                        if(input_matrans[dong + i][cot].equals(S_QuanDich)) _Dem++;

                        if (input_matrans[dong + i][cot].isEmpty()) pos = ( ( dong + i) * style_flag.getSo_cot())+(cot);

                        if(input_matrans[dong + i][cot].equals(S_QuanBot)) _Dem --;

                    }

                    if (_Dem >= 4 && pos != -1)
                    {

                        Log.d("Attack_bot_DongTay ", String.valueOf(pos));
                        Them_DiemPhongNgu(92, pos);

                    }

                }

                if (input_matrans[dong + 1][cot].equals(S_QuanDich) && input_matrans[dong][cot].isEmpty())
                {

                    int position_check = ((dong ) * style_flag.getSo_cot()) + (cot);
                    int so_dem = 0;

                    for (int i = 0; i < 5; i++)
                    {

                        String get_String = input_matrans[dong + i][cot];

                        if (get_String.equals(S_QuanDich)) so_dem ++;

                        if (get_String.equals(S_QuanBot)) so_dem --;

                    }

                    if(so_dem == 4)
                    {

                        Log.d("Attack_bot_NamBac ", String.valueOf(position_check));
                        Them_DiemPhongNgu(92, position_check);

                    }

                }

            }

        }

    }

    public void check_KhoangCach2_CH()
    {

        // Tay - Nam
        for (int dong = 0; dong < style_flag.getSo_dong() - 4; dong ++){

            for (int cot = 0; cot < style_flag.getSo_cot() - 4; cot ++) {

                if (input_matrans[dong][cot].equals(S_QuanDich))
                {

                    int _Dem = 0, pos = -1;

                    for (int i = 0; i < 5; i++)
                    {

                        if (input_matrans[dong + i][cot + i].equals(S_QuanDich)) _Dem++;

                        if (input_matrans[dong + i][cot + i].isEmpty()) pos = ((dong + i) * style_flag.getSo_cot()) + (cot + i);

                        if (input_matrans[dong + i][cot + i].equals(S_QuanBot)) _Dem--;

                    }

                    if (_Dem == 4 && pos != -1)
                    {

                        Log.d("Attack_bot_Tay_Nam ", String.valueOf(pos));
                        Them_DiemPhongNgu(92, pos);

                    }

                }

                if (input_matrans[dong][cot].isEmpty() && input_matrans[dong + 1][cot + 1].equals(S_QuanDich))
                {

                    int _Dem = 0, pos = (dong * style_flag.getSo_cot()) + (cot);

                    for (int i = 0; i < 5; i++)
                    {
                        if (input_matrans[dong + i][cot + i].equals(S_QuanDich)) _Dem++;
                    }

                    if (_Dem >= 4)
                    {

                        Log.d("Attack_bot_Tay_namcm ", String.valueOf(pos));
                        Them_DiemPhongNgu(92, pos);

                    }

                }

            }

        }


        // Tay - Bac

        for (int dong = style_flag.getSo_dong() - 1; dong > 3; dong --)
        {


            for (int cot = 0; cot < style_flag.getSo_cot() - 4; cot ++)
            {

                if (input_matrans[dong][cot].equals(S_QuanDich))
                {

                    int _Dem = 0, pos = -1;
                    for (int i = 0; i < 5; i++)
                    {

                        if (input_matrans[dong - i][cot + i].equals(S_QuanDich)) _Dem++;

                        if (input_matrans[dong - i][cot + i].isEmpty()) pos = ((dong - i) * style_flag.getSo_cot()) + (cot + i);

                        if (input_matrans[dong - i][cot + i].equals(S_QuanBot)) _Dem--;

                    }


                    if (_Dem >= 4 && pos != -1)
                    {
                        Log.d("Attack_bot_Tay_bac ", String.valueOf(pos));
                        Them_DiemPhongNgu(92, pos);
                    }

                }

                // Things 2
                if (input_matrans[dong][cot].isEmpty() && input_matrans[dong - 1][cot + 1].equals(S_QuanDich))
                {

                    Log.d("I_LOVE_YOU", "OME"); // test !

                    int _Dem = 0, pos = (dong * style_flag.getSo_cot()) + (cot);

                    for (int i = 0; i < 5; i++)
                    {

                        if (input_matrans[dong - i][cot + i].equals(S_QuanDich)) _Dem++;

                    }


                    if (_Dem == 4)
                    {

                        Log.d("Attack_bot_Tay_bacm ", String.valueOf(pos));
                        Them_DiemPhongNgu(92, pos);

                    }

                }

            }

        }

    }

    public void check_DuongBa2_DN()
    {

        // Tay - Dong
        for (int dong = 0; dong < style_flag.getSo_dong(); dong ++)
        {

            for (int cot = 1; cot < style_flag.getSo_cot() - 4; cot ++)
            {

                if (input_matrans[dong][cot].equals(S_QuanDich) && input_matrans[dong][cot - 1].isEmpty() && input_matrans[dong][cot + 4].isEmpty())
                {

                    int _Dem = 0, pos = -1;

                    for (int i = 0; i < 4; i++)
                    {

                        String get_S = input_matrans[dong][cot + i];

                        if (get_S.equals(S_QuanDich)) _Dem ++;

                        if (get_S.equals(S_QuanBot)) _Dem --;

                        if (get_S.isEmpty()) pos = ((dong) * style_flag.getSo_cot()) + (cot + i);

                    }

                    if (_Dem == 3 && pos != -1)
                    {

                        Log.d("CheckBa_Ngang", String.valueOf(pos));
                        Them_DiemPhongNgu(85, pos);

                    }

                }

            }

        }

        // Bac - Nam
        for (int dong = 1; dong < style_flag.getSo_dong() - 4; dong ++)
        {

            for (int cot = 0; cot < style_flag.getSo_cot() - 1; cot ++)
            {

                if (input_matrans[dong][cot].equals(S_QuanDich) && input_matrans[dong - 1][cot].isEmpty() && input_matrans[dong + 4][cot].isEmpty())
                {

                    int _Dem = 0, pos = -1;

                    for (int i = 0; i < 4; i++)
                    {

                        String get_S = input_matrans[dong + i][cot];

                        if (get_S.equals(S_QuanDich)) _Dem ++;

                        if (get_S.equals(S_QuanBot)) _Dem --;

                        if (get_S.isEmpty()) pos = ((dong + i) * style_flag.getSo_cot()) + (cot);

                    }

                    if (_Dem == 3 && pos != -1)
                    {

                        Log.d("CheckBa_Doc", String.valueOf(pos));
                        Them_DiemPhongNgu(85, pos);

                    }

                }

            }

        }

    }

    public void check_DuongBa2_CH()
    {

        // Tay - Nam
        for (int dong = 1; dong < style_flag.getSo_dong() - 4; dong++) {

            for (int cot = 1; cot < style_flag.getSo_cot() - 4; cot ++){

                if (input_matrans[dong][cot].equals(S_QuanDich) && input_matrans[dong - 1][cot - 1].isEmpty() && input_matrans[dong + 4][cot + 4].isEmpty())
                {

                    int _Dem = 0, pos = -1;

                    for (int i = 0; i < 4; i++)
                    {

                        String getS = input_matrans[dong + i][cot + i];

                        if (getS.equals(S_QuanDich)) _Dem++;

                        if (getS.equals(S_QuanBot)) _Dem --;

                        if (getS.isEmpty()) pos = ((dong + i) * style_flag.getSo_cot()) + (cot + i);

                    }

                    if(_Dem == 3 && pos != -1)
                    {

                        Log.d("CheckCheo", String.valueOf(pos));
                        Them_DiemPhongNgu(82, pos);

                    }

                }

            }

        }


        // Tay - Bac
        for (int dong = style_flag.getSo_dong() - 2; dong > 3; dong --){

            for (int cot = 1; cot <  style_flag.getSo_cot() - 4; cot++)
            {

                if (input_matrans[dong][cot].equals(S_QuanDich) && input_matrans[dong + 1][cot - 1].isEmpty() && input_matrans[dong - 4][cot + 4].isEmpty())
                {

                    int _Dem = 0, pos = -1;

                    for (int i = 0; i < 4; i++)
                    {

                        String getS = input_matrans[dong - i][cot + i];

                        if (getS.equals(S_QuanDich)) _Dem++;

                        if (getS.equals(S_QuanBot)) _Dem--;

                        if (getS.isEmpty()) pos = ((dong - i) * style_flag.getSo_cot()) + (cot + i);

                    }

                    if (_Dem == 3 && pos != -1)
                    {

                        Log.d("checkCheo_TayBac", String.valueOf(pos));
                        Them_DiemPhongNgu(82, pos);

                    }

                }

            }

        }
    }

    public Save_CheckPoint getDiem_Danh() {
        return diem_Danh;
    }
}
