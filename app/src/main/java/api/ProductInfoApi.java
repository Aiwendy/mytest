package api;

import bean.CourseInfoEntity;
import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Function:
 * Created by zhang di on 2017-09-07.
 */

public interface ProductInfoApi {
    @GET("/shop/product/productList")
    Observable<CourseInfoEntity> getCourseInfo();
}
