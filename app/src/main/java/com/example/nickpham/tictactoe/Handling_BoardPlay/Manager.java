package com.example.nickpham.tictactoe.Handling_BoardPlay;

import android.content.Context;
import android.widget.Button;

import com.example.nickpham.tictactoe.AttracVSProtect.Attrack_bot;
import com.example.nickpham.tictactoe.AttracVSProtect.Handling_BotAttrack;
import com.example.nickpham.tictactoe.AttracVSProtect.Handling_PhongThu;
import com.example.nickpham.tictactoe.GAS.Setup_flag;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nickpham on 23/10/2016.
 */

public class Manager
{

    Context context;
    List<Button> input_matran = new ArrayList<>();
    String[][] S_input_matran;
    Setup_flag flag_style = new Setup_flag();
    int Diem_Danh_moi;

    String S_QuanDich, S_QuanTao;
    int Res_QuanTao;

    public Manager(Context context, List<Button> input_matran, String S_QuanDich, String S_QuanTao) {

        this.context = context;
        this.input_matran = input_matran;

        this.S_QuanDich = S_QuanDich;
        this.S_QuanTao  = S_QuanTao ;

        // Te bao nao
        Handling_Theme();

        // Vien Nao
        cover_input();

        // Bo Nao
        Handling_Bot();

    }

    public int Handling_Theme()
    {
        // Res
        if (S_QuanTao.equals("x"))
        {
            Res_QuanTao  = flag_style.getImage_x_flag();
        } else
        {
            Res_QuanTao  = flag_style.getImage_o_flag();
        }
        return  0;
    }

    public void Handling_Bot() {

        Handling_PhongThu   PhongThu = new Handling_PhongThu  (context, S_input_matran, S_QuanDich, S_QuanTao);
        Handling_BotAttrack TanCong  = new Handling_BotAttrack(context, S_input_matran, S_QuanDich, S_QuanTao);


        // So sánh điểm phòng thủ và điểm tấn công, nếu điểm nào lớn hơn (có lợi thế cho bot hơn) thì sẽ đánh.
        if (PhongThu.getDiem_Danh().getSoDiem_Danh() != -1 || TanCong.getDiem_Danh().getSoDiem_Danh() != -1) {
            if (PhongThu.getDiem_Danh().getSoDiem_Danh() < TanCong.getDiem_Danh().getSoDiem_Danh()) {
                Save_CheckPoint diemdanh_last = TanCong.getDiem_Danh();
                Change_Symbol((int) diemdanh_last.getVitri_Danh());

            } else {
                Save_CheckPoint diemdanh_last = PhongThu.getDiem_Danh();
                Change_Symbol((int) diemdanh_last.getVitri_Danh());
            }
        }
        // Nếu không có nguy hiểm để bảo vệ hay một điểm yếu tấn công người chơi thì bot sẽ tìm đường tạo ra điểm yếu
        else
        {
            Attrack_bot bot = new Attrack_bot(context, S_input_matran, S_QuanTao, S_QuanDich);
            Save_CheckPoint     diemdanh_last = bot.getDiemdanh_cuoi();
            Change_Symbol((int) diemdanh_last.getVitri_Danh());
        }

    }

    public void Change_Symbol(int position_check) {
        if (input_matran.get(position_check).getTag().toString().isEmpty())
        {
            Diem_Danh_moi = position_check;
            Button dmChange = input_matran.get(position_check);
            dmChange.setBackgroundResource(Res_QuanTao);
            dmChange.setTag(S_QuanTao);
            input_matran.set(position_check, dmChange);
        }
    }

    public void cover_input() {
        S_input_matran = new String[flag_style.getSo_dong()][flag_style.getSo_cot()];

        int so_Dem = 0;
        for (int i = 0; i < this.flag_style.getSo_dong(); i++){
            for (int j = 0; j < this.flag_style.getSo_cot(); j++){
                S_input_matran[i][j] = input_matran.get(so_Dem).getTag().toString();
                so_Dem ++;
            }
        }

    }



    public int getDiem_Danh_moi() { return Diem_Danh_moi; }

    public List<Button> getInput_matran() {
        return input_matran;
    }
}

