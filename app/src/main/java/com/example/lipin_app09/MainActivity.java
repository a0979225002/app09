package com.example.lipin_app09;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;

import ir.sohreco.androidfilechooser.ExternalStorageNotAvailableException;
import ir.sohreco.androidfilechooser.FileChooser;

public class MainActivity extends AppCompatActivity
        implements FileChooser.ChooserListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    123);
        }else{
            init();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        init();
    }
    private void init(){
    }

    //選擇檔案按鈕
    public void test1(View view) {
        FileChooser.Builder builder = new FileChooser.Builder(
                FileChooser.ChooserType.FILE_CHOOSER, this)
                .setMultipleFileSelectionEnabled(true)
                .setFileFormats(new String[] {".jpg", ".png"})
                .setListItemsTextColor(R.color.colorPrimary)
                .setPreviousDirectoryButtonIcon(R.drawable.ic_prev_dir)
                .setDirectoryIcon(R.drawable.ic_directory)
                .setFileIcon(R.drawable.ic_file)
                // And more...
                ;


        try {
            FileChooser fileChooserFragment = builder.build();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, fileChooserFragment)
                    .commit();

        } catch (ExternalStorageNotAvailableException e) {
            e.printStackTrace();
        }

    }

    //上傳檔案按鈕
    public void upload(View view) {

    }


    @Override
    public void onSelect(String path) {

    }
}
