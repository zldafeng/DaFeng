package com.zl.dafeng.novate;


import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;

/**
 * ApiService
 * Created by Tamic on 2016-06-03.
 */
public interface BaseApiService {
    @POST()
    @FormUrlEncoded
    Observable<ResponseBody> executePost(
            @Url() String url,
            @FieldMap Map<String , String> maps);

    @POST("{url}")
    Observable<ResponseBody> executePostBody(
            @Path("url") String url,
            @Body Object object);

    @GET()
    Observable<ResponseBody> executeGet(
            @Url String url,
            @QueryMap Map<String, String> maps);

    @DELETE("{url}")
    Observable<ResponseBody> executeDelete(
            @Path("url") String url,
            @QueryMap Map<String, String> maps);

    @PUT("{url}")
    Observable<ResponseBody> executePut(
            @Path("url") String url,
            @QueryMap Map<String, String> maps);

    @Multipart
    @POST()
    Observable<ResponseBody> upLoadImage(
            @Url() String url,
            @Part("image\"; filename=\"image.jpg") RequestBody requestBody);

    @Multipart
    @POST()
    Observable<ResponseBody> uploadFlie(
            @Url String fileUrl,
            @Part("description") RequestBody description,
            @Part("files") MultipartBody.Part file);

    @Multipart
    @POST()
    Observable<ResponseBody> uploadFiles(
            @Url() String url,
            @PartMap() Map<String, RequestBody> maps);

    @Streaming
    @GET
    Observable<ResponseBody> downloadFile(@Url String fileUrl);


    @GET
    Observable<ResponseBody> downloadSmallFile(@Url String fileUrl);


    @GET
    Observable<ResponseBody> getTest(@Url String fileUrl,
                                     @QueryMap Map<String, String> maps);

    @FormUrlEncoded
    @POST()
    Observable<ResponseBody> postForm(
            @Url() String url,
            @FieldMap Map<String , Object> maps);


    @POST()
    Observable<ResponseBody> postJson(
            @Url() String url,
            @Body RequestBody jsonBody);

}


