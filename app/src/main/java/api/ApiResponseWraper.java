package api;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Function:
 * Created by zhang di on 2017-06-30.
 */

public class ApiResponseWraper<T> implements Serializable {

    @SerializedName("status")
    public  String status;
    @SerializedName("msg")
    public  String msg;

    @SerializedName("data")
    public  T data;
}
