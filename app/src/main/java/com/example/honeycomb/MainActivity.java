package com.example.honeycomb;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.example.honeycomb.ImageCapturing;
import com.example.honeycomb.MyAdapter;


public class MainActivity extends AppCompatActivity  {

    private ListView displayStack;
    private String[] displayStackArray;
    private String[] displayDiscription;
    private MyAdapter myAdapter;
    private int[] displayPictures;

    public MyAdapter getMyAdapter() {
        return myAdapter;
    }

    public void setMyAdapter(String[] displayStackArray, String[] displayDiscriptionStack, int[] displayPicturesStack) {
        this.myAdapter = new MyAdapter(this, displayStackArray, displayDiscriptionStack, displayPicturesStack);
    }


    public int[] getDisplayPictures() {
        return displayPictures;
    }

    public void setDisplayPictures(int[] displayPictures) {
        this.displayPictures = displayPictures;
    }

    public ListView getDisplayStack() {
        return displayStack;
    }

    public void setDisplayStack(ListView displayStack) {
        this.displayStack = displayStack;
    }

    public String[] getDisplayStackArray() {
        return displayStackArray;
    }

    public void setDisplayStackArray(String[] displayStackArray) {
        this.displayStackArray = displayStackArray;
    }

    public String[] getDisplayDiscription() {
        return displayDiscription;
    }

    public void setDisplayDiscription(String[] displayDiscription) {
        this.displayDiscription = displayDiscription;
    }
    public void capturePhoto(){
        Intent intent = new Intent(this, ImageCapturing.class);
        this.startActivity(intent);
    }
    public void findLocation(){
        Intent intent = new Intent(this, MapsActivity.class);
        this.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);
        this.setDisplayStackArray(new String[]{"Data transfer", "Freeze", "where are you", "Generate pdf"});
        this.setDisplayDiscription(new String[]{"want to transfer messages using our bluetooth!", "want to freeze this movement?", "want to know the place you are in?", "want to generate pdf?"});
        this.setDisplayPictures(new int[]{R.drawable.bluetooth, R.drawable.freeze, R.drawable.loc, R.drawable.pdf});
        this.setDisplayStack((ListView)findViewById(R.id.select_operations));
        this.setMyAdapter(this.getDisplayStackArray(), this.getDisplayDiscription(), this.getDisplayPictures());
        displayStack.setAdapter(this.getMyAdapter());

            displayStack.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                switch (position){
                    case 0: Log.v("bluetooth", "bluetooth service");break;
                    case 1: capturePhoto();break;
                    case 2: findLocation();break;
                    case 3: Log.v("pdf", "pdf service");break;
                }
            }
        });

}

}
