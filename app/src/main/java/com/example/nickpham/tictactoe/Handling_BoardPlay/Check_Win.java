package com.example.nickpham.tictactoe.Handling_BoardPlay;

import android.content.Context;
import android.content.Intent;
import android.graphics.LightingColorFilter;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.example.nickpham.tictactoe.GAS.Setup_flag;

import java.util.ArrayList;
import java.util.List;


// Class này có nhiệm vụ kiểm tra coi có người thắng hay không và tìm người thắng đó

public class Check_Win {

    Context context;
    List<Button> btCheck_List = new ArrayList<>();
    List<Integer> position_item_win = new ArrayList<>();
    String[][] strings_check;
    boolean isHaveWiner = false, isBalance = false;
    String WinerName = "";
    About_Winer about_winer;
    Setup_flag style_Flag = new Setup_flag();

    public Check_Win(Context context, List<Button> btCheck_list) {

        this.context = context;
        this.btCheck_List = btCheck_list;

        cover_to_string(btCheck_list);
        if (check_hoa() == false) {
            about_winer = new About_Winer();

            CheckRow();

            CheckColum();

            CheckXY();

            CheckYX();


        } else {
            Toast.makeText(context, "Hoa roi", Toast.LENGTH_SHORT).show();
            isBalance = true;
        }

    }


    // Kiểm tra coi hoà hay không bằng cách kiểm tra coi còn ô trống trên bàn cờ.
    public boolean check_hoa(){
        boolean check = true;

        for (int dong = 0; dong < style_Flag.getSo_dong(); dong++)
        {
            for (int cot = 0; cot < style_Flag.getSo_cot(); cot++)
            {
                if (strings_check[dong][cot] != null) {
                    if (strings_check[dong][cot].isEmpty()) check = false;
                }
            }
        }
        
        return check;
    }

    // Lấy tất cả các giá trị trong button (ô cờ), giá trị có kiểu (X - )
    public void cover_to_string(List<Button> btnCheck){
        strings_check = new String[style_Flag.getSo_dong()][style_Flag.getSo_cot()];
        for (int dong = 0; dong < style_Flag.getSo_dong(); dong++)
        {
            for (int cot = 0; cot < style_Flag.getSo_cot(); cot++)
            {
                int _position = dong * style_Flag.getSo_cot() + cot;
                if (btnCheck.get(_position).getTag() != null)
                {
                    strings_check[dong][cot] = (String) btnCheck.get(_position).getTag();
                }else
                {
                    Log.d("Vitri_null", String.valueOf(_position));
                }
            }
        }
    }


    // Kiểm tra hàng dọc
    public void CheckRow(){
        for (int dong = 0; dong <  style_Flag.getSo_dong(); dong++){

            String[] dongString = strings_check[dong];

            for (int cot = 0; cot < style_Flag.getSo_cot() - 4; cot ++){

                if (dongString[cot].isEmpty() != true){

                    if (dongString[cot].equals(dongString[cot + 1])){
                        List<Integer> o_thang = new ArrayList<Integer>();

                        String result = dongString[cot];

                        boolean win_player = true;

                        for (int i = 0; i < 5; i++){

                            if (dongString[cot + i].equals(result) != true){
                                win_player = false;
                            }

                            o_thang.add( ( (cot + i) + style_Flag.getSo_cot() * dong) );

                        }

                        if (win_player){

                            isHaveWiner = true;
                            WinerName = result;

                            position_item_win = o_thang;

                            // Lấy thông tin người thắng (Người chơi X hay người chơi O) tương tự trong các trường hợp phía dưới.
                            set_about_winer(result, 0, o_thang);

                        }
                    }
                }
            }
        }
    }


    // Kiểm tra hàng ngang
    public void CheckColum(){
        for (int cot = 0; cot < style_Flag.getSo_cot(); cot ++){
            for (int dong = 0; dong < style_Flag.getSo_dong() - 4; dong ++){
                if (strings_check[dong][cot].isEmpty() != true) {

                    if (strings_check[dong][cot].equals(strings_check[dong + 1 ][cot])) {

                        // Hanlding when show win

                        List<Integer> o_thang = new ArrayList<>();

                        String stringResult = strings_check[dong][cot];

                        boolean win_player = true;


                        for (int i = 0; i < 5; i++) {
                            if (strings_check[dong + i][cot].equals(stringResult) != true) {
                                win_player = false;
                            }

                            o_thang.add( ( (cot) +  style_Flag.getSo_cot() * ( dong + i) ));
                        }

                        if (win_player) {
                            isHaveWiner = true;
                            WinerName = stringResult;

                            set_about_winer(stringResult, 1, o_thang);
                        }
                    }

                }
            }
        }
    }


    // kiểm tra hướng chéo từ tên xuống
    public void CheckXY(){

        for (int donga = 0; donga < style_Flag.getSo_dong() - 4; donga++){

            for(int cota = 0; cota < style_Flag.getSo_cot() - 4; cota ++){

                if(strings_check[donga][cota].isEmpty() != true)
                {

                    if (strings_check[donga][cota].equals(strings_check[donga + 1][cota + 1])){
                        String resultCheck = strings_check[donga][cota];

                        boolean aisHaveWiner = true;

                        List<Integer> o_thang = new ArrayList<>();

                        for (int i  = 0; i < 5; i++){

                            if (strings_check[donga + i][cota + i].equals(resultCheck) == false) {
                                aisHaveWiner = false;
                            }

                            o_thang.add((style_Flag.getSo_cot() * (donga + i)) + (cota + i));
                        }

                        if (aisHaveWiner){

                            Log.d("NguoiThang", resultCheck);
                            isHaveWiner = aisHaveWiner;
                            WinerName = resultCheck;

                            set_about_winer(WinerName, 2, o_thang);
                        }
                    }
                }
            }
        }
    }


    //Kiểm tra hướng chéo từ duói lên
    public void CheckYX(){

        for (int donga = style_Flag.getSo_dong() - 1; donga > 5; donga --){
            for (int cota = 0; cota < style_Flag.getSo_cot() - 4; cota++){

                if (strings_check[donga][cota].isEmpty() != true){
                    if (strings_check[donga][cota].equals(strings_check[donga - 1][cota + 1])){
                        String resultCheck = strings_check[donga][cota];

                        boolean aisHaveWiner = true;

                        List<Integer> o_thang = new ArrayList<>();

                        for (int i = 0; i < 5; i++) {

                            if (strings_check[donga - i][cota + i].equals(resultCheck) != true){
                                aisHaveWiner = false;
                            }

                            o_thang.add( style_Flag.getSo_cot() * ( donga - i) + (cota + i));

                        }


                        if (aisHaveWiner){
                            isHaveWiner = true;
                            set_about_winer(resultCheck, 3, o_thang);
                        }
                    }
                }
            }
        }
    }

    // Lấy thông tin người thắng (Người chơi X hay người chơi O).
    public void set_about_winer(String name_flag, int win_kind, List<Integer> position_item_wins )
    {

        // win_kind có nghĩa là hướng thằng của người chiến thắng (ngang dọc, chéo)
        // 0: Hướng từ trái sang phải
        // 1: Hướng từ trên xuống dưới
        // 2: Hướng chéo từ trên xuống dưới
        // 3: Hướng chéo từ dưới lên trên

        about_winer.setName_flag(name_flag);
        about_winer.setWin_kindl(win_kind);
        about_winer.setPosition_items_win(position_item_wins);
    }



    public List<Integer> getPosition_item_win() {
        return position_item_win;
    }

    public String getWinerName() {
        return WinerName;
    }

    public boolean isHaveWiner() {
        return isHaveWiner;
    }

    public About_Winer getAbout_winer() {
        return about_winer;
    }

    public boolean isBalance() { return isBalance; }
}
