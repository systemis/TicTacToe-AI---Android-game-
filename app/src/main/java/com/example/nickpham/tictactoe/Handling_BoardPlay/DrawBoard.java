package com.example.nickpham.tictactoe.Handling_BoardPlay;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.nickpham.tictactoe.BoardPlay;
import com.example.nickpham.tictactoe.GAS.Setup_flag;
import com.example.nickpham.tictactoe.R;

import java.util.ArrayList;
import java.util.List;

public class DrawBoard implements View.OnClickListener{

    Context context;
    AppCompatActivity getActivty;
    Button bt_examtest;

    public static LinearLayout ln_ShowLoaction;
    public static List<Button> btn_check   = new ArrayList<>();
    static Setup_flag   style_flag  = new Setup_flag();

    public boolean IS_STOP = false, isCheck_X = true, isRealPlayer_Time = true;
    public int position_list = 0;
    public int choice_play;

    String S_QuanDich , S_QuanBot;

    public DrawBoard(Context context, LinearLayout getShowLoaction, Button getexamTest, int mode_play, String S_QuanDich, String S_QuanBot){

        // Reset ban co
        btn_check = new ArrayList<Button>();

        this.context         = context;
        this.getActivty      = (AppCompatActivity) context;
        this.ln_ShowLoaction = getShowLoaction;
        this.bt_examtest     = getexamTest;
        this.choice_play     = mode_play;

        error_sound = custom_sound_w_c(0);
        check_sound = custom_sound_w_c(1);
        fail_sound  = custom_sound_w_c(2);
        win_sound   = custom_sound_w_c(3);

        if(mode_play == 2)
        {
            this.S_QuanDich = S_QuanDich;
            this.S_QuanBot  = S_QuanBot;
        }else if(mode_play == 1)
        {
            isCheck_X = BoardPlay.isCheck_X;
            Toast.makeText(context, String.valueOf(isCheck_X) , Toast.LENGTH_SHORT).show();
        }

        Handling_VeBanCo();

    }


    public void Handling_VeBanCo(){
        ln_ShowLoaction.removeAllViews();

        for (int i = 0; i < style_flag.getSo_dong(); i++)
        {
            ln_ShowLoaction.addView(Draw_row_board());
        }

    }

    public LinearLayout Draw_row_board()
    {
        LinearLayout row_sing = new LinearLayout(context);
        row_sing.setOrientation(LinearLayout.HORIZONTAL);

            for (int i = 0; i < style_flag.getSo_cot(); i++)
            {

                position_list ++;
                btn_check.add(single_check());
                row_sing.addView(btn_check.get(position_list - 1));
            }


        row_sing.setGravity(Gravity.CENTER_HORIZONTAL);

        return row_sing;
    }


    public Button single_check()
    {
        Button single_check_button = new Button(context);
        single_check_button.setLayoutParams(bt_examtest.getLayoutParams());
        single_check_button.setTag("");
        single_check_button.setBackgroundResource(style_flag.getImage_oco());
        single_check_button.setOnClickListener(this);
        return single_check_button;
    }



    @Override
    public void onClick(View actionID)
    {

        boolean error = true;

        if (BoardPlay.tools_game.isShowing()) BoardPlay.tools_game.dismiss();

        if (BoardPlay.isStopGame == -1 && IS_STOP == false)
        {
            Button checking_button = (Button) actionID;
            switch (choice_play)
            {
                case 1:
                    if (checking_button.getTag().toString().isEmpty())
                    {
                        PlayerVSPlayer(checking_button);
                        error = false;
                    }

                    break;

                case 2:
                    if (checking_button.getTag().toString().isEmpty() && isRealPlayer_Time == true)
                    {
                        PlayerVSComputer(checking_button);
                        error = false;
                    }
                    break;
            }
        }

        // Start sound when check
        if (error){
            error_sound.start();
        }else {
            check_sound.start();
        }

    }

    public MediaPlayer error_sound, check_sound, fail_sound, win_sound;
    MediaPlayer custom_sound_w_c(int mode_sound)
    {
        int res_sound[] = {R.raw.error_sound_check, R.raw.check_sound,R.raw.fail_sound, R.raw.win_sound};
        MediaPlayer sound_w_c = MediaPlayer.create(context, res_sound[mode_sound]);
        return sound_w_c;
    }

    /** ------------------------------------------------------------------------   Xu ly doi voi hai nguoi choi ---------------------------------------------------------- */

    public void PlayerVSPlayer(Button checking_button)
    {
        Log.d("Test_Check" , String.valueOf(isCheck_X));

        if (isCheck_X)
        {
            checking_button.setTag("x");
            isCheck_X = false;

            BoardPlay.pause_task_player1();
            BoardPlay.resume_task_player2(context);

            if (btn_check != null || btn_check.size() > 0 || btn_check.get(0) != null) Check_Win();
            Show_Flags_Attrack_Soon(checking_button, 1);

        } else
        {
            checking_button.setTag("o");

            BoardPlay.pause_task_player2();
            BoardPlay.resume_task_player1(context);

            isCheck_X = true;
            if (btn_check != null || btn_check.size() > 0 || btn_check.get(0) != null) Check_Win();
            Show_Flags_Attrack_Soon(checking_button, 2);
        }

        btn_check.set(btn_check.indexOf(checking_button), checking_button);

    }

    /** ------------------------------------------------------------------------   Xu ly doi voi hai nguoi choi ---------------------------------------------------------- */



    /** ------------------------------------------------------------------------   Xu ly khi choi voi may ---------------------------------------------------------- */
    public void PlayerVSComputer(final Button checking_button)
    {
        Real_Player_Time(checking_button);

        new Handler().postDelayed(new Runnable() {
                @Override
                public void run()
                {
                    Skynet_Time();
                }
            }, 1000);

    }

    public void Real_Player_Time(Button checking_button)
    {
        if (isRealPlayer_Time)
        {
            int position_check = btn_check.indexOf(checking_button);

            if (S_QuanDich.equals("o")){
                Show_Flags_Attrack_Soon(checking_button, 2);
            }else {
                Show_Flags_Attrack_Soon(checking_button, 1);
            }

            if (position_check != -1){

                if (S_QuanDich.equals("x"))
                {
                    checking_button.setTag("x");
                } else {
                    checking_button.setTag("o");
                }

                btn_check.set(position_check, checking_button);
            }

            isRealPlayer_Time = false;

            BoardPlay.pause_task_player1();
            BoardPlay.resume_task_player2(context);

        }
    }

    public void Skynet_Time()
    {

        if (Check_Win() == false)
        {

            Manager Skynet_Manager = new Manager(context, btn_check, S_QuanDich, S_QuanBot);
            btn_check = Skynet_Manager.getInput_matran();

            BoardPlay.resume_task_player1(context);
            BoardPlay.pause_task_player2();

            if (Check_Win() == false) isRealPlayer_Time = true;

            if (S_QuanBot.equals("o")){
                Show_Flags_Attrack_Soon(btn_check.get(Skynet_Manager.getDiem_Danh_moi()), 2);
            }else {
                Show_Flags_Attrack_Soon(btn_check.get(Skynet_Manager.getDiem_Danh_moi()), 1);
            }


        }

    }

    /** ------------------------------------------------------------------------   Xu ly khi choi voi may ---------------------------------------------------------- */

    // Kiem tra coi thu co ai thang chua
    public boolean Check_Win() {
        boolean Has_Change = false;

        Check_Win checkWin = new Check_Win(context, btn_check);

        if (checkWin.isBalance() == false)
        {
            if (checkWin.isHaveWiner())
            {
                Has_Change = true;

                if (BoardPlay.aboutPlayer1.getName_flag().equals(checkWin.about_winer.getName_flag()))
                {
                    BoardPlay.aboutPlayer1.setWineds(BoardPlay.aboutPlayer1.getWineds() + 1);
                } else
                {
                    BoardPlay.aboutPlayer2.setWineds(BoardPlay.aboutPlayer2.getWineds() + 1);
                }

                BoardPlay.setValue();

                Lock_Board_Play(checkWin.getAbout_winer(), 2);

                Play_Again_Dialog(checkWin.getAbout_winer()).show();


            }
        } else
        {
            Has_Change = true;
            DissNable(null, 0);
            Remind_Balance().show();

            Lock_Board_Play(null, 0);

        }

        if (Has_Change)
        {

        }

        return Has_Change;
    }

    // Hien thi thong bao khi hai nguoi choi hoa nau
    public Dialog Remind_Balance()
    {
        final AlertDialog.Builder builder_dialog = new AlertDialog.Builder(context);

        builder_dialog.setTitle("Notification !");
        builder_dialog.setMessage("Hai người thật cân bằng, cân sức, hoà rồi, chơi lại không các kỳ thụ !");

        final int[] count = {0};

        builder_dialog.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface mDialog, int i)
            {

                if (count[0] == 0)
                {
                    Unlock_Board_Play();
                    count[0] += 1;
                }

                mDialog.dismiss();

            }
        })

        .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface mDialog, int i) { mDialog.dismiss(); count[0] ++; }
        });

        return builder_dialog.create();
    }

    // Show dialog when have winer
    private AlertDialog Play_Again_Dialog(About_Winer about_winers)
    {
        final int[] count = {0};

        AlertDialog.Builder mDialog = new AlertDialog.Builder(context);
        mDialog.setTitle("Notification: ");

         mDialog.setMessage(return_winer_real_name(about_winers.getName_flag()) + " đã thắng, hai kì thủ có muốn chơi lại không ?");

        mDialog.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if (count[0] == 0)
                {
                    Unlock_Board_Play();
                    count[0] += 1;
                }
            }
        });


        mDialog.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface event_dialog, int i) {if (count[0] == 0) { event_dialog.dismiss(); count[0] ++; }

            }
        });

        return mDialog.create();
    }

    public  void DissNable(About_Winer about_winers, int choice_to_dis)
    {
        for (int i = 0; i < btn_check.size(); i++)
        {

            Button btnE = btn_check.get(i);
            btnE.setEnabled(false);
            btn_check.set(i, btnE);

        }

        if (choice_to_dis == 2) {
            List<Integer> o_thang = about_winers.getPosition_items_win();
            for (int i = 0; i < o_thang.size(); i++)
            {
                int position = o_thang.get(i);
                Button btnE = btn_check.get(position);
                btnE.setBackgroundResource(applyWinKind(about_winers.getWin_kindl(), about_winers.getName_flag()));
                btn_check.set(position, btnE);

            }
        }
    }

    public void Ennable(){
        for (int i = 0; i < btn_check.size(); i++)
        {

            Button btnE = btn_check.get(i);
            btnE.setEnabled(true);
            btn_check.set(i, btnE);

        }
    }

    public void Unlock_Board_Play()
    {
        IS_STOP = false;
        isRealPlayer_Time = true;

        ln_ShowLoaction.removeAllViews();
        btn_check = new ArrayList<Button>();
        position_list = 0;

        BoardPlay.isStopGame = -1;

        Handling_VeBanCo();

        BoardPlay.Restart_Game(context);

        if (choice_play == 1)
        {
            this.isCheck_X = BoardPlay.isCheck_X;
        }

    }

    public void Lock_Board_Play(About_Winer about_winer,int choice_to_lock)
    {
        if (about_winer != null)
        {
            if (choice_play == 1)
            {
                win_sound.start();
            }else
            {
                if (about_winer.getName_flag().equals(S_QuanDich))
                {
                    win_sound.start();
                }else
                {
                    fail_sound.start();
                }
            }
        }

        if (BoardPlay.Sound_Game != null) BoardPlay.Sound_Game.stop();

        DissNable(about_winer, choice_to_lock);
        IS_STOP = true;
        BoardPlay.Stop_Game();
    }



    public void Show_Flags_Attrack_Soon(final Button checking_button, int choice_flags)
    {
        if (IS_STOP == false)
        {
            final int position_button = btn_check.indexOf(checking_button);

            if (choice_flags == 1) {
                checking_button.setBackgroundResource(R.mipmap.light_flags_x);
                btn_check.set(position_button, checking_button);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        checking_button.setBackgroundResource(style_flag.getImage_x_flag());
                        btn_check.set(position_button, checking_button);
                    }
                }, 500);
            } else {
                checking_button.setBackgroundResource(R.mipmap.light_flags_o);
                btn_check.set(position_button, checking_button);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        checking_button.setBackgroundResource(style_flag.getImage_o_flag());
                        btn_check.set(position_button, checking_button);
                    }
                }, 500);
            }
        }
    }

    // Vẽ đường gạch ngang những ô đã thắng
    public  int applyWinKind(int choice, String flag_name)
    {

        int res = 0;
        switch (choice){

            case 0 :
                if (flag_name.equals("o")) {
                    res = style_flag.getImage_o_flag_win0();
                }else {
                    res = style_flag.getImage_x_flag_win0();
                }
                break;

            case 1 :
                if (flag_name.equals("o")) {
                    res = style_flag.getImage_o_flag_win1();
                }else {
                    res = style_flag.getImage_x_flag_win1();
                }
            break;

            case 2 :
                if (flag_name.equals("o")) {
                    res = style_flag.getImage_o_flag_win2();
                }else {
                    res = style_flag.getImage_x_flag_win2();
                }
                break;

            case 3 :
                if (flag_name.equals("o")) {
                    res = style_flag.getImage_o_flag_win3();
                }else {
                    res = style_flag.getImage_x_flag_win3();
                }
                break;

        }


        return res;
    }

    public String return_winer_real_name(String win_flags)
    {
        if(BoardPlay.aboutPlayer1.getName_flag().equals(win_flags))
        {
            return BoardPlay.aboutPlayer1.getReal_name();
        }
        else
        {
            return BoardPlay.aboutPlayer2.getReal_name();
        }
    }
}
