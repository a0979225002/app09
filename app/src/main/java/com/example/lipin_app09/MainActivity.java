package com.example.lipin_app09;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.File;

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
                .setMultipleFileSelectionEnabled(false)//多重選擇文件按鈕
                .setFileFormats(new String[] {".jpg"})
                .setListItemsTextColor(R.color.colorPrimary)
                .setPreviousDirectoryButtonIcon(R.drawable.ic_prev_dir)
                .setDirectoryIcon(R.drawable.ic_directory)
                .setFileIcon(R.drawable.ic_file)

                // And more...
                ;


        try {
            FileChooser fileChooserFragment = builder.build();
            getSupportFragmentManager().beginTransaction()
                    //建立一個FrameLayout 將Fragment放入
                    .add(R.id.container, fileChooserFragment)
                    //commit()開始執行Fragment的切換
                    .commit();

        } catch (ExternalStorageNotAvailableException e) {
            e.printStackTrace();
        }

    }

    //上傳檔案按鈕
    public void upload(View view) {
        new Thread(){
            @Override
            public void run() {
                doupload();
            }
        }.start();
    }
    //上傳方法
    private void doupload(){
       try {

        MultipartUtility mu = new MultipartUtility(
                "http://192.168.1.181:8080/lipin01",
                "",
                "UTF-8");
        //使用html中命名的表單名稱欄位
           //html功能更換檔案的名子
        mu.addFormField("prefix","android");
        //使用html中命名的檔案上傳欄位
        mu.addFilePart("upload", uploadFile);

        mu.finish();
        Log.v("brad","upload ok");

       }catch (Exception e){
        Log.v("brad",e.toString());
       }
    }

    //拿取上傳檔案的路徑
    File uploadFile;
    @Override
    public void onSelect(String path) {
       uploadFile = new File(path);
        Log.v("brad",path);
    }
}
