package com.example.nickpham.tictactoe;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

public class LoadGameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_game);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Handler loading_call = new Handler();
        loading_call.postDelayed(new Runnable() {
            @Override
            public void run()
            {
                new start_choice_select_mode().execute();
            }
        }, 1000);
    }


    public class start_choice_select_mode extends AsyncTask<String, String, String>
    {

        public start_choice_select_mode() {
            // Do something
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {

            LoadGameActivity.this.startActivity(new Intent(LoadGameActivity.this, SelectPlayChoice.class));

            return null;
        }


        @Override
        protected void onPostExecute(String s) {

            LoadGameActivity.this.finish();

            super.onPostExecute(s);
        }
    }
}
