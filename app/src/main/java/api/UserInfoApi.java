package api;

import bean.UserInfoEntity;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Function:
 * Created by zhang di on 2017-06-30.
 */

public interface UserInfoApi {
    @GET("BabyheartServer/user/userinfo/{userId}/")
    Observable<UserInfoEntity> getUserInfo(@Path("userId") String userId);
}
