package com.example.nickpham.tictactoe;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.nickpham.tictactoe.GAS.Bundle_ToPlay;
import com.example.nickpham.tictactoe.GAS.Guide_Caro;
import com.example.nickpham.tictactoe.Storge.Sound_Alow;

import java.util.ArrayList;
import java.util.List;

public class SelectPlayChoice extends AppCompatActivity {


    MediaPlayer md;
    int Sound_Data_alow = -1;

    FloatingActionButton alow_sound_background;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_play_choice);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Xu ly de bat nhac
        Handling_on_background_sound();

    }

    // Handling to on background music
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void Handling_on_background_sound()
    {
        final Sound_Alow alow = new Sound_Alow(SelectPlayChoice.this);
        if (alow.get_datat_from_player() == 0)
        {
            start_background_sound();

            alow.insert_data_sound_fPlayer(1, 1);

            dialog_remind_w_fist_time().show();

            Sound_Data_alow = 1;
        }else {
            Sound_Data_alow = alow.get_datat_from_player();
            if (Sound_Data_alow == 1)
            {
                start_background_sound();
            }
        }

        alow_sound_background = (FloatingActionButton) this.findViewById(R.id.alow_sound_background);

        if (Sound_Data_alow == 1)
        {
            alow_sound_background.setImageResource(R.mipmap.sound_on);
        }else {
            alow_sound_background.setImageResource(R.mipmap.sound_of);
        }

        alow_sound_background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Sound_Data_alow == 1)
                {
                    alow.change_data_alow_sound(1, -1);
                    md.stop();
                    Sound_Data_alow = -1;
                    alow_sound_background.setImageResource(R.mipmap.sound_of);
                }else if (Sound_Data_alow == -1){
                    alow.change_data_alow_sound(1, 1);
                    start_background_sound();
                    Sound_Data_alow = 1;
                    alow_sound_background.setImageResource(R.mipmap.sound_on);
                }
            }
        });
    }

    public Dialog dialog_remind_w_fist_time()
    {
        AlertDialog.Builder mDialog = new AlertDialog.Builder(SelectPlayChoice.this);

        mDialog.setTitle("Chú ý lần đầu: ");
        mDialog.setMessage(new Guide_Caro().get_Guide());
        mDialog.setNegativeButton("Done !", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface ndialog, int i) {
                ndialog.dismiss();
            }
        });

        return mDialog.create();
    }

    // Start background music
    public int start_background_sound()
    {
        md = MediaPlayer.create(SelectPlayChoice.this, R.raw.main_song);
        md.start();
        md.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                md.reset();
            }
        });
        return 0;
    }

    // Handling to start other activity
    public class start_activity extends AsyncTask<String, String, String>{

        int choice_Start;
        String RealName1, RealName2;
        int Time_Play, choiceFlag;
        Context context;
        AppCompatActivity getActivity;
        ProgressDialog show_dialog;
        Intent goTo_PlayWidthFriend;



        public start_activity(Context context, int choice_Start, Bundle_ToPlay dulieu)
        {

            this.choice_Start = choice_Start;
            this.context      = context;
            this.RealName1    = dulieu.getRealName1();
            this.RealName2    = dulieu.getRealName2();
            this.Time_Play    = dulieu.get_Minute_ToPlay();
            this.choiceFlag   = dulieu.getChoice_flags();
            this.getActivity  = (AppCompatActivity) context;

        }

        @Override
        protected void onPreExecute() {

            show_dialog = new ProgressDialog(context);
            show_dialog.setMessage("Đang tải dữ liệu, xin trong giây lát ");
            show_dialog.show();

            goTo_PlayWidthFriend = new Intent(SelectPlayChoice.this, BoardPlay.class);
            Bundle mBunlder = new Bundle();

            switch (choice_Start){
                case 1:

                    mBunlder.putInt("play_mode", 1);
                    mBunlder.putString("name_player1", RealName1);
                    mBunlder.putString("name_player2", RealName2);
                    mBunlder.putInt("time_name", Time_Play);
                    mBunlder.putInt("HaveMainSong", Sound_Data_alow);
                    break;

                case 2:

                    mBunlder.putInt("play_mode", 2);
                    mBunlder.putString("real_name", RealName2);
                    mBunlder.putInt("time_name", Time_Play);
                    mBunlder.putInt("choice_flags", choiceFlag);
                    mBunlder.putInt("HaveMainSong", Sound_Data_alow);
                    break;
            }


            goTo_PlayWidthFriend.putExtra("about_play_mode", mBunlder);

            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings)
        {


            getActivity.startActivity(goTo_PlayWidthFriend);
            getActivity.finish();

            return null;
        }


        @Override
        protected void onPostExecute(String s) {

            show_dialog.dismiss();

            if (md != null) md.stop();

            super.onPostExecute(s);
        }


    }


    // Show when play vs computer
    public class Custom_Dialog_show_PlayVSCom {
        Context context;

        Bundle_ToPlay dulieu_play = new Bundle_ToPlay();

        public Custom_Dialog_show_PlayVSCom(Context context)
        {
            this.context = context;

            dulieu_play.setChoice_flags(0);

            dialog_importPlayerName_playervscom().show();

        }

        public Dialog dialog_importPlayerName_playervscom()
        {
            final Dialog mDialog = new Dialog(context);
            mDialog.setContentView(R.layout.dialog_import_player_name_layout);
            mDialog.getWindow().setLayout(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);

            final EditText  importRealName          = (EditText)  mDialog.findViewById(R.id.import_real_playerName);
            final ImageView select_flag_mode        = (ImageView) mDialog.findViewById(R.id.select_flag_mode);
            final Button btn_importPlayerName       = (Button)    mDialog.findViewById(R.id.btn_importPlayerName_com);
            final Button btn_CancelimportPlayerName = (Button)    mDialog.findViewById(R.id.btn_CancelimportPlayerName_com);

            select_flag_mode.setTag(0);
            if(dulieu_play.getChoice_flags() != 0)
            {
                select_flag_mode.setTag(dulieu_play.getChoice_flags());
                select_flag_mode.setImageResource(R.mipmap.ts_o_flag);
            }

            if(dulieu_play.getRealName2() != null) importRealName.setText(dulieu_play.getRealName2());

            View.OnClickListener listener_play_friend = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String Real_Name = importRealName.getText().toString();
                    switch (view.getId()) {
                        case R.id.btn_importPlayerName_com:

                            if (Real_Name.isEmpty() != true) {

                                if (spec_char_check(Real_Name) == false)
                                {
                                    Real_Name = remove_empty_char_last(Real_Name);

                                    dulieu_play.setRealName2(Real_Name);

                                    dulieu_play.setChoice_flags((Integer) select_flag_mode.getTag());

                                    dialog_select_time().show();

                                    mDialog.dismiss();
                                }else{
                                    importRealName.setError("Mục này không được trống ");
                                }

                            } else {
                                importRealName.setError("Mục này không được trống ");
                            }

                            break;

                        case R.id.select_flag_mode:

                            if ((Integer)select_flag_mode.getTag() == 0)
                            {
                                select_flag_mode.setImageResource(R.mipmap.ts_o_flag);
                                select_flag_mode.setTag(1);

                            }else
                            {
                                select_flag_mode.setImageResource(R.mipmap.ts_x_flag);
                                select_flag_mode.setTag(0);

                            }

                            break;

                        default:
                            mDialog.dismiss();
                            break;
                    }
                }
            };

            btn_importPlayerName.setOnClickListener(listener_play_friend);
            btn_CancelimportPlayerName.setOnClickListener(listener_play_friend);
            select_flag_mode.setOnClickListener(listener_play_friend);


            return mDialog;
        }

        public Dialog dialog_select_time()
        {
            final String[] time_ = {"1 phút ","5 phút ","15 phút"};
            final int[] time_int = {1,5,15};
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Bạn muốn chơi trong bao lâu: ");

            builder.setNegativeButton("Back", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface mDialog, int i) {

                            mDialog.dismiss();
                            dialog_importPlayerName_playervscom().show();

                        }

                     });

            builder.setItems(time_, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int position) {

                            dulieu_play.set_Minute_ToPlay(time_int[position]);
                            dulieu_play.setRealName1("");
                            new start_activity(context, 2, dulieu_play).execute();


                        }
                    });



            return builder.create();
        }


    }

    // Show when player vs player
    public class Custom_Dialog_show_PlayVSPlayer{
        Context context;
        Bundle_ToPlay dulieu_play = new Bundle_ToPlay();

        public Custom_Dialog_show_PlayVSPlayer(Context context)
        {

            this.context = context;
            dialog_importPlayerName_playervsplayer().show();


        }

        public Dialog dialog_importPlayerName_playervsplayer()
        {

            final Dialog mDialog = new Dialog(SelectPlayChoice.this);

            mDialog.setContentView(R.layout.dialog_import_player_name);
            mDialog.getWindow().setLayout(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);

            final EditText edt_playerName1          = (EditText) mDialog.findViewById(R.id.et_importPlayerName1);
            final EditText edt_playerName2          = (EditText) mDialog.findViewById(R.id.et_importPlayerName2);
            final Button btn_importPlayerName       = (Button)   mDialog.findViewById(R.id.btn_importPlayerName);
            final Button btn_CancelimportPlayerName = (Button)   mDialog.findViewById(R.id.btn_CancelimportPlayerName);

            if (dulieu_play.getRealName1() != null) edt_playerName1.setText(dulieu_play.getRealName1());
            if (dulieu_play.getRealName2() != null) edt_playerName2.setText(dulieu_play.getRealName2());

            View.OnClickListener listener_play_friend = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String Real_Name1 = edt_playerName1.getText().toString();
                    String Real_Name2 = edt_playerName2.getText().toString();

                    switch (view.getId()){

                        case R.id.btn_importPlayerName:

                            if (Real_Name1.isEmpty() != true && Real_Name2.isEmpty() != true)
                            {
                                if (spec_char_check(Real_Name1) == false && spec_char_check(Real_Name2) == false)
                                {
                                    Real_Name1 = remove_empty_char_last(Real_Name1);
                                    Real_Name2 = remove_empty_char_last(Real_Name2);

                                    if (Real_Name1.equals(Real_Name2) == false)
                                    {
                                        dulieu_play.setRealName1(Real_Name1);
                                        dulieu_play.setRealName2(Real_Name2);
                                        mDialog.dismiss();
                                        dialog_select_time().show();
                                    }else
                                    {
                                        Toast.makeText(context, "Tên hai người chơi không được giống nhau !", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            }

                            break;

                        default:

                            mDialog.dismiss();

                            break;

                    }
                }
            };


            btn_importPlayerName.setOnClickListener(listener_play_friend);
            btn_CancelimportPlayerName.setOnClickListener(listener_play_friend);

            return mDialog;
        }

        public Dialog dialog_select_time()
        {
            final String[] time_ = {"1 phút ","5 phút ","15 phút"};
            final int[] time_int = {1, 5, 15};
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Bạn muốn chơi trong bao lâu: ");

            builder.setNegativeButton("Back", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface mDialog, int i) {

                    mDialog.dismiss();

//                    dulieu_play.setRealName1("");
//                    dulieu_play.setRealName2("");

                    dialog_importPlayerName_playervsplayer().show();

                }

            });

            builder.setItems(time_, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int position) {
                    dulieu_play.set_Minute_ToPlay(time_int[position]);
                    new start_activity(context, 1, dulieu_play).execute();
                }
            });


            return builder.create();
        }

    }

    // Introduction about Tic tac toe Game.
    public Dialog guide_game(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Guide to play !");
        builder.setMessage("" + new Guide_Caro().get_Guide());
        builder.setPositiveButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface mDialog, int i) {
                mDialog.dismiss();
            }
        });
        return builder.create();
    }

    // Kiểm tra chuỗi ký tự có rỗng hoàn toàn hay không
    public boolean spec_char_check(String check_text)
    {
        boolean result = true;
        Log.d("StringSize: ", String.valueOf(check_text.length()));

        for (int i  = 0; i < check_text.length(); i ++)
        {
            String check_check_s = String.valueOf(check_text.charAt(i));
            if(check_check_s.equals(" ") != true)
            {
                result = false;
                Log.d("Text_False", check_check_s);
            }
        }

        return result;
    }


    // Loại bỏ những ký tự rỗng ở đằng cuối
    public String remove_empty_char_last(String text_to_handling)
    {
        List<Character> cov = new ArrayList<>();

        for (int i = 0; i < text_to_handling.length(); i++) cov.add(text_to_handling.charAt(i));

        while(String.valueOf(text_to_handling.charAt(text_to_handling.length() - 1)).equals(" "))
        {
            cov.remove(text_to_handling.length() - 1);
            text_to_handling = "";
            for (int i = 0; i < cov.size(); i++) text_to_handling = text_to_handling + "" + cov.get(i);
        }

        return text_to_handling;
    }

    public void PlayerVSPlayerMode(View view)   { new Custom_Dialog_show_PlayVSPlayer(this); }

    public void PlayerVSComputerMode(View view) { new Custom_Dialog_show_PlayVSCom(this); }

    public void GuiderOnclick(View view) { guide_game().show(); }


}
