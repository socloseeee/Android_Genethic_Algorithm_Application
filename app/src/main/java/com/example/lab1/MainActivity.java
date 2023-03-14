package com.example.lab1;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Gravity;
import android.view.View;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import android.widget.Toast;
import com.example.lab1.RetrofitAPI;
import com.example.lab1.loginModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final int NOTIFY_ID = 101;
    private static final String CHANNEL_ID = "MyChannel";

    private static final String ACTION_SNOOZE = "Кнопка";

    private static final String EXTRA_NOTIFIC_ID = "Кнопочка";
    private void createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance  = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,name,importance);

            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        postData("userName", "password");
        Context context = getApplicationContext();
        Toast toast = Toast.makeText(context,"Тестовое уведомление", Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 500,500);
        toast.show();   //first_test

        createNotificationChannel();

        //Intent notificationintent = new Intent(context, MainActivity.class); //пример 2 для уведомления в статус баре

        Intent notificationintent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://vk.com/freaky_timelapse"));

        Intent snoozeIntent = new Intent(this, BroadcastReceiver.class);
        snoozeIntent.setAction(ACTION_SNOOZE);
        snoozeIntent.putExtra(EXTRA_NOTIFIC_ID,0);

        PendingIntent snoozePendingIntent = PendingIntent.getBroadcast(this,0,snoozeIntent,0);


        PendingIntent contentintent = PendingIntent.getActivity(context,
                0, notificationintent,
                PendingIntent.FLAG_CANCEL_CURRENT);
        Resources res = context.getResources();
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this,CHANNEL_ID)
                .setSmallIcon(R.drawable.notification_car11)
                .setColor(Color.GREEN)
                .setContentTitle("Здравствуйте")
                .setContentText("https://vk.com/freaky_timelapse")
                .setTicker("Info").setWhen(System.currentTimeMillis())
                .setContentIntent(contentintent) //для примера с сайтом
                .addAction(R.drawable.sharp_lock11,getString(R.string.snooze),snoozePendingIntent)
                .setPriority(NotificationCompat.PRIORITY_MAX);



        Notification notification = mBuilder.build();


        NotificationManager notificationManager =(NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFY_ID,notification);
    }

    public void startNewActivity(View v) {
        Intent intent = new Intent(this, SecondActivity.class);
        startActivity(intent);
    }

    public void menu_click(View v){
        Intent intent = new Intent(MainActivity.this, MenuActivity.class);
        startActivity(intent);
    }

    public void transitions_click(View v){
        Intent intent = new Intent(MainActivity.this, com.example.lab1.TransitionActivity.class);
        startActivity(intent);
    }

    public void files_click(View v){
        Intent intent = new Intent(MainActivity.this, FilesActivity.class);
        startActivity(intent);
    }

    public void Namefiles_click(View v){
        Intent intent = new Intent(MainActivity.this, NameFilesActivity.class);
        startActivity(intent);
    }

    private void postData(String name, String password) {

        // below line is for displaying our progress bar.

        // on below line we are creating a retrofit
        // builder and passing our base url
        Retrofit retrofit = new Retrofit.Builder()
                // .baseUrl("https://login1.requestcatcher.com")
                .baseUrl("https://reqres.in/api/")
                // as we are sending data in json format so
                // we have to add Gson converter factory
                .addConverterFactory(GsonConverterFactory.create())
                // at last we are building our retrofit builder.
                .build();
        // below line is to create an instance for our retrofit api class.
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

        // passing data from our text fields to our modal class.
        loginModel modal = new loginModel(name, password);

        // calling a method to create a post and passing our modal class.
        Call<loginModel> call = retrofitAPI.createPost(modal);

        // on below line we are executing our method.
        call.enqueue(new Callback<loginModel>() {
            @Override
            public void onResponse(Call<loginModel> call, Response<loginModel> response) {
                // this method is called when we get response from our api.

                // on below line we are setting empty text
                // to our both edit text.

                // we are getting response from our body
                // and passing it to our modal class.
                loginModel responseFromAPI = response.body();

                // on below line we are getting our data from modal class and adding it to our string.
                String responseString = "Response Code : " + response.code()
                        + "\nlogin : " + responseFromAPI.getLogin() + "\n"
                        + "password : " + responseFromAPI.getPassword()+"\n"
                        + "botToken : "+ responseFromAPI.getBotToken();

                // below line we are setting our
                // string to our text view.
                Toast.makeText(MainActivity.this,responseString,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<loginModel> call, Throwable t) {
                // setting text to our text view when
                // we get error response from API.
                Toast.makeText(MainActivity.this,"Error found is : " + t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

}