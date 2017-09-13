package api;

import java.util.Map;

import bean.CourseInfoEntity;
import bean.InformationEntitiy;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Function:
 * Created by zhang di on 2017-06-30.
 */

public interface InfomationApi {
    @GET("/cms/ketang/courseList")
    Observable<InformationEntitiy> getInfo(@QueryMap Map<String,String> map);
}
