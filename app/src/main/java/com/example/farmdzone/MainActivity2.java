package com.example.farmdzone;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity2 extends AppCompatActivity {
    Button b1,b2,b3,b4,b5,b6,b7,b8,b9,b10;
    FusedLocationProviderClient fusedLocationProviderClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        String str1[],str2[];
        int imgs[]={R.drawable.bacterialspot,R.drawable.earlybright,
                R.drawable.latebright,R.drawable.leafmold,R.drawable.mosiacvirus,
                R.drawable.septorialeafspot,R.drawable.spidermitestwospottedspidermite,
                R.drawable.targetspot,R.drawable.healthy,R.drawable.yellowleafcurlvirus};
        str1= getResources().getStringArray(R.array.title);
        str2= getResources().getStringArray(R.array.content);
        b1=findViewById(R.id.button);
        b2=findViewById(R.id.button2);
        b3=findViewById(R.id.button3);
        b4=findViewById(R.id.button4);
        b5=findViewById(R.id.button5);
        b6=findViewById(R.id.button6);
        b7=findViewById(R.id.button7);
        b8=findViewById(R.id.button8);
        b9=findViewById(R.id.button9);
        b10=findViewById(R.id.button10);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setContentView(R.layout.diseasedisp);
                TextView t1 = findViewById(R.id.diseasesname);
                TextView t2 = findViewById(R.id.treatment);
                getLOC();
                ImageView im1 = findViewById(R.id.imageView);
                t1.setText(str1[0]);
                t2.setText(str2[0]);

                im1.setImageResource(imgs[0]);
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setContentView(R.layout.diseasedisp);
                TextView t1 = findViewById(R.id.diseasesname);
                TextView t2 = findViewById(R.id.treatment);
                getLOC();
                ImageView im1 = findViewById(R.id.imageView);
                t1.setText(str1[1]);
                t2.setText(str2[1]);
                im1.setImageResource(imgs[1]);
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setContentView(R.layout.diseasedisp);
                TextView t1 = findViewById(R.id.diseasesname);
                TextView t2 = findViewById(R.id.treatment);
                getLOC();
                ImageView im1 = findViewById(R.id.imageView);
                t1.setText(str1[2]);
                t2.setText(str2[2]);
                im1.setImageResource(imgs[2]);
            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setContentView(R.layout.diseasedisp);
                TextView t1 = findViewById(R.id.diseasesname);
                TextView t2 = findViewById(R.id.treatment);
                getLOC();
                ImageView im1 = findViewById(R.id.imageView);
                t1.setText(str1[3]);
                t2.setText(str2[3]);
                im1.setImageResource(imgs[3]);
            }
        });
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setContentView(R.layout.diseasedisp);
                TextView t1 = findViewById(R.id.diseasesname);
                TextView t2 = findViewById(R.id.treatment);
                getLOC();
                ImageView im1 = findViewById(R.id.imageView);
                t1.setText(str1[4]);
                t2.setText(str2[4]);
                im1.setImageResource(imgs[4]);
            }
        });
        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setContentView(R.layout.diseasedisp);
                TextView t1 = findViewById(R.id.diseasesname);
                TextView t2 = findViewById(R.id.treatment);
                getLOC();
                ImageView im1 = findViewById(R.id.imageView);
                t1.setText(str1[5]);
                t2.setText(str2[5]);
                im1.setImageResource(imgs[5]);
            }
        });
        b7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setContentView(R.layout.diseasedisp);
                TextView t1 = findViewById(R.id.diseasesname);
                TextView t2 = findViewById(R.id.treatment);
                getLOC();
                ImageView im1 = findViewById(R.id.imageView);
                t1.setText(str1[6]);
                t2.setText(str2[6]);
                im1.setImageResource(imgs[6]);
            }
        });
        b8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setContentView(R.layout.diseasedisp);
                TextView t1 = findViewById(R.id.diseasesname);
                TextView t2 = findViewById(R.id.treatment);
                getLOC();
                ImageView im1 = findViewById(R.id.imageView);
                t1.setText(str1[7]);
                t2.setText(str2[7]);
                im1.setImageResource(imgs[7]);
            }
        });
        b9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setContentView(R.layout.diseasedisp);
                TextView t1 = findViewById(R.id.diseasesname);
                getLOC();
                TextView t2 = findViewById(R.id.treatment);
                ImageView im1 = findViewById(R.id.imageView);
                t1.setText(str1[8]);
                t2.setText(str2[8]);
                im1.setImageResource(imgs[8]);
            }
        });
        b10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setContentView(R.layout.diseasedisp);
                TextView t1 = findViewById(R.id.diseasesname);
                getLOC();
                TextView t2 = findViewById(R.id.treatment);
                ImageView im1 = findViewById(R.id.imageView);
                t1.setText(str1[9]);
                t2.setText(str2[9]);
                im1.setImageResource(imgs[9]);
            }
        });
    }

    @SuppressLint("MissingPermission")
    private void getLOC() {

        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location = task.getResult();
                if (location != null) {
                    try {
                        Geocoder geocoder = new Geocoder(MainActivity2.this, Locale.getDefault());
                        List<Address> address = geocoder.getFromLocation(
                                location.getLatitude(), location.getLongitude(), 1
                        );
                        TextView loc=findViewById(R.id.location);
                        loc.setText(( address.get(0).getLocality())
                                +", "+address.get(0).getAdminArea()+", "+address.get(0).getPostalCode()+", "
                                +address.get(0).getCountryName());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
    }
}