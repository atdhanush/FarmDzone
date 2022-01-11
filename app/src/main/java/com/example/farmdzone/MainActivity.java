package com.example.farmdzone;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.farmdzone.ml.Model;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    TextView confidence;
    Button result;
    FusedLocationProviderClient fusedLocationProviderClient;
    ImageView imageView,tomato;
    ImageButton picture,select;
    private int IMAGE_MEAN = 0;
    private float IMAGE_STD = 1;
    int imageSize = 224;
    String[] classes = {"Bacterial_spot",
            "Early_blight",
            "Late_blight",
            "Leaf_Mold",
            "Mosaic_virus",
            "Septoria_leaf_spot",
            "Spider_mites Two-spotted_spider_mite",
            "Target_Spot",
            "Tomato_healthy",
            "Yellow_Leaf_Curl_Virus"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        result = findViewById(R.id.result);
        tomato=findViewById(R.id.tomato);
        confidence = findViewById(R.id.confidence);
        imageView = findViewById(R.id.imageView);
        picture = findViewById(R.id.button);
        select = findViewById(R.id.button2);
        String s1[],s2[];
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        int images[]={R.drawable.bacterialspot,R.drawable.earlybright,
                R.drawable.latebright,R.drawable.leafmold,R.drawable.mosiacvirus,
                R.drawable.septorialeafspot,R.drawable.spidermitestwospottedspidermite,
                R.drawable.targetspot,R.drawable.healthy,R.drawable.yellowleafcurlvirus};
        s1= getResources().getStringArray(R.array.title);
        s2= getResources().getStringArray(R.array.content);
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(MainActivity.this
                    , new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
            ActivityCompat.requestPermissions(MainActivity.this
                    , new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 44);
        }
        tomato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    Intent i=new Intent(getApplicationContext(),MainActivity2.class);
                    startActivity(i);
                }
                else{
                    ActivityCompat.requestPermissions(MainActivity.this
                            , new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
                    ActivityCompat.requestPermissions(MainActivity.this
                            , new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 44);
                }

            }
        });
        picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Launch camera if we have permission
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(cameraIntent, 1);
                    } else {
                        //Request camera permission if we don't have it.
                        requestPermissions(new String[]{Manifest.permission.CAMERA}, 100);
                    }
                }
            }
        });

        result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setContentView(R.layout.diseasedisp);


                for(int i=0;i<classes.length;i++){
                    String sts=result.getText().toString();
                    if(sts.equals(classes[i])){
                        getLOaction();
                        TextView t1=findViewById(R.id.diseasesname);
                        TextView t2=findViewById(R.id.treatment);

                        ImageView im1=findViewById(R.id.imageView);
                        t1.setText(s1[i]);
                        t2.setText(s2[i]);
                        im1.setImageResource(images[i]);
                        break;
                    }
                }
            }
        });
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Launch camera if we have permission
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                        intent.setType("image/*");
                        startActivityForResult(Intent.createChooser(intent, "Select"),2);
                    }
                    else{
                        requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 200);
                    }
                }

            }
        });
    }

    @SuppressLint("MissingPermission")
    private void getLOaction() {

        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location = task.getResult();
                if (location != null) {
                    try {
                        Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
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

    public void classifyImage(Bitmap image) {
        try {
            Model model = Model.newInstance(getApplicationContext());

            TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 224, 224, 3}, DataType.FLOAT32);
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4 * imageSize * imageSize * 3);
            byteBuffer.order(ByteOrder.nativeOrder());
            int [] intValues = new int[imageSize * imageSize];
            image.getPixels(intValues, 0, image.getWidth(), 0, 0, image.getWidth(), image.getHeight());
            int pixel = 0;
            for(int i = 0; i < imageSize; i++){
                for(int j = 0; j < imageSize; j++){
                    int val = intValues[pixel++]; // RGB
                        byteBuffer.putFloat((((val >> 16) & 0xFF)-IMAGE_MEAN)/IMAGE_STD);
                        byteBuffer.putFloat((((val >> 8) & 0xFF)-IMAGE_MEAN)/IMAGE_STD);
                        byteBuffer.putFloat((((val) & 0xFF)-IMAGE_MEAN)/IMAGE_STD);
                }
            }

            inputFeature0.loadBuffer(byteBuffer);
            Model.Outputs outputs = model.process(inputFeature0);
            TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();

            float[] confidences = outputFeature0.getFloatArray();
            // find the index of the class with the biggest confidence.
            int maxPos = 0;
            float maxConfidence = 0;
            for(int i = 0; i < confidences.length; i++){
                if(confidences[i] > maxConfidence){
                    maxConfidence = confidences[i];
                    maxPos = i;
                }
            }

            result.setText(classes[maxPos]);
            result.setVisibility(View.VISIBLE);
            String s = "";
            for(int i = 0; i < classes.length; i++){
                if(classes[maxPos]== classes[i])
                {
                    s += String.format("%s: %.1f%%\n", classes[i], confidences[i] * 100);
                }
            }
            confidence.setText(s);
            model.close();
        } catch (IOException e) {
            // TODO Handle the exception
        }


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if(requestCode == 1 ){
                Bitmap image = (Bitmap) data.getExtras().get("data");
                int dimension = Math.min(image.getWidth(), image.getHeight());
                image = ThumbnailUtils.extractThumbnail(image, dimension, dimension);
                imageView.setImageBitmap(image);

                image = Bitmap.createScaledBitmap(image, imageSize, imageSize, false);
                classifyImage(image);
            }
            if(requestCode == 2){
                Uri datapath = data.getData();
                try {
                    Bitmap image=MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(),datapath);
                    int dimension = Math.min(image.getWidth(), image.getHeight());
                    image = ThumbnailUtils.extractThumbnail(image, dimension, dimension);
                    imageView.setImageBitmap(image);

                    image = Bitmap.createScaledBitmap(image, imageSize, imageSize, false);
                    classifyImage(image);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        }
    }
}