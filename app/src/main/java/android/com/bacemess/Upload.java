package android.com.bacemess;


import android.com.bacemess.Modal.names_wrapper;
import android.com.bacemess.names.names_modal;

import java.util.*;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface Upload {

    @FormUrlEncoded
    @POST("/bace/login.php")
    Call<UploadObject> login(@Field("username")String username,@Field("password")String password);


    @POST("/bace/get_data.php")
    Call<List<names_modal>> get_data();

    @POST("/bace/get_count.php")
    Call<List<Integer>> get_count();


    @POST("/bace/commit_data.php")
    Call<UploadObject> commit_data(@Body names_wrapper nw);

    @FormUrlEncoded
    @POST("/bace/save_extra_count.php")
    Call<UploadObject> save_extra_count(@Field("breakfast")String breakfast,@Field("lunch")String lunch,
                                        @Field("dinner")String dinner);


    @POST("/bace/get_extras.php")
    Call<List<Integer>> get_extras();

    @FormUrlEncoded
    @POST("/bace/add_person.php")
    Call<UploadObject> add_person(@Field("person")String person);



}
