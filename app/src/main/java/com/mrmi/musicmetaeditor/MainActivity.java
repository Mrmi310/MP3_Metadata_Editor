package com.mrmi.musicmetaeditor;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Debug;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity
{
    TextView selectedFileText;
    Button browseFilesButton;
    Intent fileIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        selectedFileText = findViewById(R.id.selectedFileText);
        selectedFileText.setText("NO FILE SELECTED");
        browseFilesButton = findViewById(R.id.browseButton);

        browseFilesButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                fileIntent = new Intent(Intent.ACTION_GET_CONTENT);
                fileIntent.setType("*/*");
                startActivityForResult(fileIntent, 10);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 10)
        {
            if (resultCode == RESULT_OK)
            {
                //Get the file URI from the intent
                Uri fileURI = data.getData();

                selectedFileText.setText("FILE PATH: " + fileURI.toString());

                //Read the meta data of the MP3 file using the URI
                readMetaData(fileURI);
            }
        }
        else
        {
            selectedFileText.setText("REQUEST CODE ERROR");
        }
    }

    private void readMetaData(Uri fileURI)
    {
        try
        {
            //Create a media metadata retriever
            MediaMetadataRetriever retriever = new MediaMetadataRetriever();

            //Set the retriever's data source (the selected MP3 file)
            retriever.setDataSource(getBaseContext(), fileURI);
            System.out.println("[MRMI]: Set data source for media metadata retriever");

            //Extract the title, author, album and genre from the MP3 file using the media metadata retriever
            String title = "", author = "", album = "", genre = "";
            title = retriever.extractMetadata(7); //Titles are held at index 7
            author = retriever.extractMetadata(3);
            album = retriever.extractMetadata(1);
            genre = retriever.extractMetadata(6);

            //Save it in a ID3MetaData class
            System.out.println("[MRMI]: Set title, author, album and genre");
            ID3MetaData fileMetadata = new ID3MetaData(title, author, album, genre);

            //Print the track's properties
            fileMetadata.PrintProperties();

            //Temporarily display the track's properties
            selectedFileText.setText("FILE PATH: " + fileURI.toString() +
                    "\nTrack title: " + fileMetadata.title + "\nTrack author: " + fileMetadata.author + "\nTrack album: " + fileMetadata.album + "\nTrack genre: " + fileMetadata.genre);
        }
        catch (Exception e)
        {
            System.out.println("[MRMI]: Exception : " + e.getMessage());
        }
    }
}