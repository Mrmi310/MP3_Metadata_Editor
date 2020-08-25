package com.mrmi.musicmetaeditor;

public class ID3MetaData
{
    public String title, author, album, genre;

    public ID3MetaData(String title, String author, String album, String genre)
    {
        this.title = title;
        this.author = author;
        this.album = album;
        this.genre = genre;
    }

    public void PrintProperties()
    {
        System.out.println("[MRMI]: FILE METADATA:\nTitle: " + title + "\nAuthor: " + author + "\nAlbum: " + album + "\nGenre: " + genre);
    }
}
