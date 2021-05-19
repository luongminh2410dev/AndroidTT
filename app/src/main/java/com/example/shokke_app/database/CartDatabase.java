package com.example.shokke_app.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class CartDatabase extends SQLiteOpenHelper {

    public CartDatabase(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    //Truy vấn không trả kq
    public void QueryData(String sql){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }
    //Truy vấn trả về kq
    public Cursor GetData(String sql){
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql,null);
    }
    //Thêm vào giỏ hàng
    public void Insert(String userName,String idProduct,int count,int price,String timeCreate){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL("INSERT INTO Cart VALUES(null,'"+userName+"','"+idProduct+"',"+count+","+price+",'"+timeCreate+"')");
    }
    //Xóa 1 bản ghi khi biết id
    public void Delete(int id){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL("DELETE FROM Cart WHERE ID = "+id+"");
    }
    //Cập nhật giỏ hàng khi thay đổi số lượng
    public void Update(int count,int id){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL("UPDATE Cart SET count = "+count+" WHERE id = "+id+"");
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
