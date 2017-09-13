package api;

import java.util.Map;

import bean.CourseInfoEntity;
import bean.InformationEntitiy;
import bean.UserInfoEntity;
import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * Function:
 * Created by zhang di on 2017-09-08.
 */

public interface Api {
    @GET("/cms/ketang/courseList")
    Observable<InformationEntitiy> getInfo(@QueryMap Map<String,String> map);

    @GET("/cms/ketang/courseList")
    Observable<InformationEntitiy> getInfo1(@Body RequestBody requestBody);

    @GET("/shop/product/productList")
    Observable<CourseInfoEntity> getCourseInfo();

    @GET("BabyheartServer/user/userinfo/{userId}/")
    Observable<UserInfoEntity> getUserInfo(@Path("userId") String userId);

}
