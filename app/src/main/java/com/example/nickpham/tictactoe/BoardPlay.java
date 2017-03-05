package com.example.nickpham.tictactoe;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nickpham.tictactoe.GAS.Gamer_player;
import com.example.nickpham.tictactoe.GAS.Setup_flag;
import com.example.nickpham.tictactoe.Handling_BoardPlay.DrawBoard;

import java.util.Random;


public class BoardPlay extends AppCompatActivity implements View.OnClickListener {

    public static DrawBoard ban_co;
    public static LinearLayout show_board;
    public static ImageView showFlags1, showFlags2, btn_onSound;
    public static ImageButton reset_button;
    public static TextView showPlayerN1, showPlayerN2, txtS1, txtS2, showWin1, showWin2;

    // Khung player
    public static Gamer_player aboutPlayer1, aboutPlayer2;
    public static CountDownTimer time_player1, time_player2;
    public static long mili_player1 = 60000, mili_player2 = 60000;

    public static int HaveMainSong;

    Setup_flag style_flag = new Setup_flag();


    String S_QuanDich, S_QuanBot;
    int Res_QuanDich, Res_QuanBot;

    public static int Choice_play = 1, H_TimePlay = 0;
    public static int isStopGame = -1;
    public static boolean isCheck_X = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_play);
        reset_board_game();

        // Khoi dong che do choi
        get_bundle_to_mode();
        // Create new all
        Anhxa();

        show_board.removeAllViews();
        ban_co = null;

        // Xu ly ngau nhien luot di
        Handling_Time_Attrack();

        // Xu ly ve ban co
        Start_Mode_Play();

        if (HaveMainSong == 1) Custom_Sound_Game(BoardPlay.this);
    }

    public void Handling_Time_Attrack()
    {

        if (Choice_play == 1)
        {

            Random_Time(BoardPlay.this);

        } else if (Choice_play == 2)
        {

            time_player1 = Set_Time_Players(mili_player1, 1, BoardPlay.this);
            time_player1.start();

        }

    }

    public static MediaPlayer Sound_Game = null;
    public static void Custom_Sound_Game(Context context)
    {
        Sound_Game = MediaPlayer.create(context, R.raw.main_song);
        Sound_Game.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                Sound_Game.reset();
            }
        });
        Sound_Game.start();

    }

    // Đưa bàn cờ trắng lại
    public void reset_board_game() 
    {
        isStopGame = -1;
        mili_player1 = 60000;
        mili_player2 = 60000;
        pause_task_player1();
        pause_task_player2();
    }

    public void Anhxa(){
        custom_tools();

        show_board   = (LinearLayout)  this.findViewById(R.id.show_board);
        showFlags1   = (ImageView)     this.findViewById(R.id.show_flags1);
        showFlags2   = (ImageView)     this.findViewById(R.id.show_flags2);
        showPlayerN1 = (TextView)      this.findViewById(R.id.showPlayerName1);
        showPlayerN2 = (TextView)      this.findViewById(R.id.showPlayerName2);
        txtS1        = (TextView)      this.findViewById(R.id.show_test_timer_player1);
        txtS2        = (TextView)      this.findViewById(R.id.show_test_timer_player2);
        showWin1     = (TextView)      this.findViewById(R.id.show_win1);
        showWin2     = (TextView)      this.findViewById(R.id.show_win2);

        reset_button = (ImageButton)   tools_game.getContentView().findViewById(R.id.reset_button);
        btn_onSound  = (ImageView)     tools_game.getContentView().findViewById(R.id.btn_onSound);

        if (HaveMainSong != 1)
        {
            btn_onSound.setFocusable(false);
            btn_onSound.setBackgroundResource(R.mipmap.sound_of);
        }


        txtS1.setText(String.valueOf(60000));
        txtS2.setText(String.valueOf(60000));

        reset_button.setTag("IS_FIGHT");
        reset_button.setOnClickListener(this);
        
        setValue();
    }


    public static int Random_Time(Context context)
    {

        Random _Dom = new Random();
        int dom_count = _Dom.nextInt(2);

        Toast.makeText(context,String.valueOf(dom_count), Toast.LENGTH_SHORT).show();
        if (dom_count == 1)
        {
            isCheck_X = true;
            resume_task_player1(context);

        }else if (dom_count == 0)
        {
            isCheck_X = false;
            resume_task_player2(context);
        }

        return 0;
    }

    public void Start_Mode_Play() {

        Button bt_ex_test = (Button) getLayoutInflater().inflate(R.layout.test_for_boardplay, null).findViewById(R.id.buttonTest);

        ban_co = new DrawBoard(this, show_board, bt_ex_test, Choice_play, S_QuanDich, S_QuanBot);

    }

    public void get_bundle_to_mode() {
        Bundle get_dulieu = getIntent().getBundleExtra("about_play_mode");
        Choice_play = get_dulieu.getInt("play_mode");
        H_TimePlay = get_dulieu.getInt("time_name");
        HaveMainSong = get_dulieu.getInt("HaveMainSong");

        mili_player1 *= H_TimePlay;
        mili_player2 *= H_TimePlay;

        if (Choice_play == 1)
        {
            String[] names = new String[2];

            names[0] = get_dulieu.getString("name_player1");
            names[1] = get_dulieu.getString("name_player2");

            PlayerVSPlayerMode(names);

        } else if (Choice_play == 2)
        {
            String real_name = get_dulieu.getString("real_name");

            int Choice_play_flags = get_dulieu.getInt("choice_flags");

            get_Choice_flags(Choice_play_flags);

            Toast.makeText(this,"Choice Flags: " + String.valueOf(Choice_play_flags)+ " Symbol Plags: " + S_QuanBot, Toast.LENGTH_SHORT).show();

            PlayerVSComputerMode(real_name);

        }


    }

    // Mode play vs real player
    public void PlayerVSPlayerMode(final String[] names)
    {
        aboutPlayer1 = new Gamer_player();
        aboutPlayer1.setImag_flag(style_flag.getBieutuong_x());
        aboutPlayer1.setReal_name(names[0]);
        aboutPlayer1.setWineds(0);
        aboutPlayer1.setName_flag("x");

        aboutPlayer2 = new Gamer_player();
        aboutPlayer2.setImag_flag(style_flag.getBieutuong_o());
        aboutPlayer2.setReal_name(names[1]);
        aboutPlayer2.setWineds(0);
        aboutPlayer2.setName_flag("o");

    }

    // Mode play vs computer (ai)
    public void PlayerVSComputerMode(String real_name)
    {

        aboutPlayer1 = new Gamer_player();
        aboutPlayer1.setReal_name(real_name);
        aboutPlayer1.setWineds(0);
        aboutPlayer1.setName_flag(S_QuanDich);
        aboutPlayer1.setImag_flag(Res_QuanDich);


        aboutPlayer2 = new Gamer_player();
        aboutPlayer2.setReal_name("Skynet");
        aboutPlayer2.setWineds(0);
        aboutPlayer2.setName_flag(S_QuanBot);
        aboutPlayer2.setImag_flag(Res_QuanBot);

    }

    public static void setValue() {
        showFlags1.setImageResource(aboutPlayer1.getImag_flag());
        showPlayerN1.setText(aboutPlayer1.getReal_name());
        showWin1.setText(String.valueOf(aboutPlayer1.getWineds()));

        showFlags2.setImageResource(aboutPlayer2.getImag_flag());
        showPlayerN2.setText(aboutPlayer2.getReal_name());
        showWin2.setText(String.valueOf(aboutPlayer2.getWineds()));
    }


    public static CountDownTimer Set_Time_Players(Long Time, final int opp_toSet, final Context context) {
        //Log.d("Time_Pick", String.valueOf(Time));

        return new CountDownTimer(Time + 1000, 1000) {
            @Override
            public void onTick(long l) {
                switch (opp_toSet) {
                    case 1:
                        mili_player1 -= 1000;
                        txtS1.setText(String.valueOf(covert_minute((int) mili_player1)[0]) + ":" + String.valueOf(covert_minute((int) mili_player1)[1]));
                        break;

                    case 2:
                        mili_player2 = mili_player2 - 1000;
                        txtS2.setText(String.valueOf(covert_minute((int) mili_player2)[0]) + ":" + String.valueOf(covert_minute((int) mili_player2)[1]));
                        break;
                }

            }

            @Override

            public void onFinish() {
                ban_co.Lock_Board_Play(null, 0);

                if (opp_toSet == 1) {

                    Log.d("Winer", "Player 2 ");

                    isStopGame = 1;

                    aboutPlayer2.setWineds(aboutPlayer2.getWineds() + 1);

                    Show_W_End_Time(1, context).show();

                }
                if (opp_toSet == 2)
                {

                    Log.d("Winer", "Player 1 ");

                    isStopGame = 2;

                    aboutPlayer1.setWineds(aboutPlayer1.getWineds() + 1);

                    Show_W_End_Time(2, context).show();

                }

                setValue();

                Stop_Game();

                if (Sound_Game != null) Sound_Game.stop();

                if (Choice_play == 1) ban_co.win_sound.start();
                if (Choice_play == 2)
                {
                    if (opp_toSet == 1) ban_co.fail_sound .start();
                    if (opp_toSet == 2) ban_co.win_sound  .start();
                }

            }
        };

    }

    public static void Restart_Game(Context context) {
        if (reset_button.getTag().toString().equals("IS_STOP")) {
            reset_button.setTag("IS_FIGHT");
            reset_button.setImageResource(R.mipmap.pause_icon);
        }


        if (Choice_play == 2) {
            resume_task_player1(context);
        } else Random_Time(context);

        // Restart background music if music was alowed by player
        if (HaveMainSong == 1)
        {
            if(SoundAction % 2 == 0)
            {
                BoardPlay.Custom_Sound_Game(context);
            }
        }

    }

    public static void Stop_Game()
    {

        mili_player1 = H_TimePlay * 60000;
        mili_player2 = H_TimePlay * 60000;

        if(time_player1 != null) time_player1.cancel(); time_player1 = null;
        if(time_player2 != null) time_player2.cancel(); time_player2 = null;

        reset_button.setTag("IS_STOP");
        reset_button.setImageResource(R.mipmap.reset_icon);

    }


    // ------------ Pause Time Play --------------------------//
    public static void pause_task_player1()
    {
        if (time_player1 != null)
        {
            time_player1.cancel();
            time_player1 = null;
        }
    }

    public static void pause_task_player2()
    {
        if (time_player2 != null)
        {
            time_player2.cancel();
            time_player2 = null;
        }

    }
    // ------------ Pause Time Play --------------------------//


    // ------------ Start Time Play -------------------------//
    public static void resume_task_player1(Context context)
    {
        if (time_player1 == null)
        {
            time_player1 = Set_Time_Players(mili_player1, 1, context);
            time_player1.start();

        }else
        {
            Log.d("Action_Resume_Task1", "Fail");
        }
    }

    public static void resume_task_player2(Context context)
    {
        if (time_player2 == null)
        {
            time_player2 = Set_Time_Players(mili_player2, 2, context);
            time_player2.start();
        }else
        {
            Log.d("Action_Resume_Task2", "Fail");
        }
    }
    // ------------ Start Time Play -------------------------//


    void get_Choice_flags(int Choice)
    {
        if (Choice == 0)
        {
            S_QuanDich   = "x";
            S_QuanBot    = "o";
            Res_QuanDich = style_flag.getBieutuong_x();
            Res_QuanBot  = style_flag.getBieutuong_o();
        } else if(Choice == 1)
        {
            S_QuanDich   = "o";
            S_QuanBot    = "x";
            Res_QuanDich = style_flag.getBieutuong_o();
            Res_QuanBot  = style_flag.getBieutuong_x();
        }

    }

    Dialog dialog_remind_go_home() {
        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);

        mBuilder.setTitle("Notification !");
        mBuilder.setMessage("Hai người chơi có muốn thoát trận không ?");
        mBuilder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                reset_board_game();
                startActivity(new Intent(BoardPlay.this, SelectPlayChoice.class));
                if (Sound_Game != null) Sound_Game.stop();
                finish();

            }
        });
        mBuilder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mBuilder.create().dismiss();
            }
        });

        return mBuilder.create();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.reset_button:

                Handling_Action_Click_OF_Button_G();

                break;
        }
    }


    // Handling actions from player want when click resert button
    public void Handling_Action_Click_OF_Button_G()
    {
        String Action_View = reset_button.getTag().toString();

        if (Action_View.equals("IS_STOP"))
        {

            ban_co.Unlock_Board_Play();

            reset_button.setImageResource(R.mipmap.pause_icon);
            reset_button.setTag("IS_FIGHT");

        }else if (Action_View.equals("IS_FIGHT"))
        {
            pause_task_player1();
            pause_task_player2();

            ban_co.IS_STOP = true;
            ban_co.DissNable(null, 0);

            reset_button.setImageResource(R.mipmap.resume_button);
            reset_button.setTag("IS_PAUSE");

        }else if (Action_View.equals("IS_PAUSE")) {
            ban_co.IS_STOP = false;
            ban_co.Ennable();

            if (Choice_play == 2)
            {
                if (ban_co.isRealPlayer_Time == false)
                {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            ban_co.Skynet_Time();
                        }
                    }, 5000);

                    resume_task_player2(BoardPlay.this);
                } else
                {
                    resume_task_player1(BoardPlay.this);
                }

            } else
            {
                if (ban_co.isCheck_X)
                {
                    resume_task_player1(BoardPlay.this);
                } else
                {
                    resume_task_player2(BoardPlay.this);
                }
            }

            reset_button.setImageResource(R.mipmap.pause_icon);
            reset_button.setTag("IS_FIGHT");

        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
                dialog_remind_go_home().show();
                break;

            default:

                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public static int[] covert_minute(int mili_to)
    {
        int minute = mili_to/60/1000;
        int second = (mili_to/1000) % 60;
        return new int[]{minute, second};
    }

    public static Dialog Show_W_End_Time(int Name_Lose, Context context)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Notification !");
        String Name_Player = "";

        if (Name_Lose == 1){
            Name_Player = aboutPlayer1.getReal_name();
        }else {
            Name_Player = aboutPlayer2.getReal_name();
        }

        builder.setMessage(Name_Player + " đã thua vì đã hết thời gian rồi !");

        builder.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        return builder.create();
    }


    // Custom layout fo Manager tools Layout (PopupWindow)
    public static PopupWindow tools_game;
    public void custom_tools()
    {
        LayoutInflater inflater = (LayoutInflater) BoardPlay.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.tool_button_game_in_board_play, null, false);
        tools_game = new PopupWindow(popupView, ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
    }

    // Action to show Manager tools layout (popupWindow)
    int count_click = 0;
    public void ShowToolsClick(View view)
    {
        count_click++;
        if (count_click % 2 != 0)
        {
            tools_game.showAsDropDown(view, -160, +20);
        }else {
            tools_game.dismiss();
        }
    }


    // Action if player want come back app's home
    public void BackHomeAction(View view)
    {
        dialog_remind_go_home().show();
    }

    public static int SoundAction = 0;
    public void OffSoundAction(View view) {
        if (HaveMainSong == 1) {
            SoundAction++;

            ImageButton action_view = (ImageButton) view;

            if (SoundAction % 2 != 0) {
                Sound_Game.pause();
                action_view.setBackgroundResource(R.mipmap.sound_of);
            } else {
                Sound_Game.start();
                action_view.setBackgroundResource(R.mipmap.sound_on);
            }
        }
    }

}