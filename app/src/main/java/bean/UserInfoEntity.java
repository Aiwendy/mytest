package bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Function:
 * Created by zhang di on 2017-06-30.
 */

public class UserInfoEntity implements Serializable {
    @SerializedName("babyInfo")
    public BabyInfo babyInfo;

    public class BabyInfo implements Serializable {
        public String babyHeight;
        public String babyHeadPic;
        public String bbDescription;
        public String babyBirthday;
        public String babySex;
        public String babyWeight;
        public String babyName;
        public String babyDays;

        @Override
        public String toString() {
            return "BabyInfo{" +
                    "babyHeight='" + babyHeight + '\'' +
                    ", babyHeadPic='" + babyHeadPic + '\'' +
                    ", bbDescription='" + bbDescription + '\'' +
                    ", babyBirthday='" + babyBirthday + '\'' +
                    ", babySex='" + babySex + '\'' +
                    ", babyWeight='" + babyWeight + '\'' +
                    ", babyName='" + babyName + '\'' +
                    ", babyDays='" + babyDays + '\'' +
                    '}';
        }
    }

    @SerializedName("unableBook")
    public boolean unableBook; //是否显示母子工具栏
    @SerializedName("state")
    public int state;
    @SerializedName("userinfo")
    public UserInfo userinfo;

    public class UserInfo implements Serializable {
        public String birthday;
        public String area;
        public int gid;
        public String serialnum;
        public int asktimes;
        public String yunjilunum;
        public String city;
        public String headpic;
        public int jiancetimes;
        public String doctor;
        public String userpinyin;
        public int askedtimes;
        public String jiancetime;
        public String provinceId;
        public int hospitalid;
        public int doctorid;
        public String yuchanqi;
        public String tel;
        public int id;
        public String hospital;
        public String username;
        public String timestamp;
        public String themePic;
        public String nickName;
        public int personStatus;// 0 孕期  1 育儿

        @Override
        public String toString() {
            return "UserInfo{" +
                    "birthday='" + birthday + '\'' +
                    ", area='" + area + '\'' +
                    ", gid=" + gid +
                    ", serialnum='" + serialnum + '\'' +
                    ", asktimes=" + asktimes +
                    ", yunjilunum='" + yunjilunum + '\'' +
                    ", city='" + city + '\'' +
                    ", headpic='" + headpic + '\'' +
                    ", jiancetimes=" + jiancetimes +
                    ", doctor='" + doctor + '\'' +
                    ", userpinyin='" + userpinyin + '\'' +
                    ", askedtimes=" + askedtimes +
                    ", jiancetime='" + jiancetime + '\'' +
                    ", provinceId='" + provinceId + '\'' +
                    ", hospitalid=" + hospitalid +
                    ", doctorid=" + doctorid +
                    ", yuchanqi='" + yuchanqi + '\'' +
                    ", tel='" + tel + '\'' +
                    ", id=" + id +
                    ", hospital='" + hospital + '\'' +
                    ", username='" + username + '\'' +
                    ", timestamp='" + timestamp + '\'' +
                    ", themePic='" + themePic + '\'' +
                    ", nickName='" + nickName + '\'' +
                    ", personStatus=" + personStatus +
                    '}';
        }
    }


}
