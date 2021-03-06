package com.example.shokke_app.api;

import com.example.shokke_app.model.Value;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {

    Gson gson = new GsonBuilder()
            .setDateFormat("dd-MM-yyyy HH:mm:ss")
            .create();

    ApiService apiService = new Retrofit.Builder()
            .baseUrl("https://servernodettandroid.herokuapp.com/")
            .client(new OkHttpClient.Builder().build())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService.class);


    /**
     * Trả về danh sách sản phẩm
     * @param id
     * @param name
     * @param fbclid
     * @return
     */
    //https://servernodettandroid.herokuapp.com/product?id=5&name=Iphone&fbclid=IwAR0oCY_KuQKQ3gdYhNCItqQxnAISsJa5KS_pniNsCYUc8PHNruDuTz8yCLE
    @GET("product")
    Call<Value> convertValue(@Query("id") int id,
                             @Query("name") String name,
                             @Query("fbclid") String fbclid);


    /**
     * Trả về danh sách sản phẩm tìm kiếm theo tên sản phẩm
     * @param name
     * @return
     */
    //https://servernodettandroid.herokuapp.com/product/filter?name=
    @GET("product/filter")
    Call<Value> convertValueSearch(@Query("name") String name);

    /**
     * Trả về 1 sản phẩm khi biết mã sản phẩm
     * @param _id
     * @return
     */
    //https://servernodettandroid.herokuapp.com/product/search?_id=609bd406b68b050015ba4a19
    @GET("product/search")
    Call<Value> convertValueById(@Query("_id") String _id);

    //https://servernodettandroid.herokuapp.com/product/sortout/?sortOut=1
    @GET("product/sortout")
    Call<Value> convertValueBySortout(@Query("sortOut") int sortOut);

    //https://servernodettandroid.herokuapp.com/bill?username=admin@gmail.com&address=SN96TPNamĐịnh&phone=0975967824&id_product=60936c2ceb0f3e0015d26617&total=5&time=30/12/2021
    @POST("bill")
    Call<Void> getPost(@Query("username") String username,
                       @Query("address") String address,
                       @Query("phone") String phone,
                       @Query("id_product") String id_product,
                       @Query("total") int total,
                       @Query("time") String time);
}
