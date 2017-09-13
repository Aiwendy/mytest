package bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Function:
 * Created by zhang di on 2017-09-06.
 */

public class InformationEntitiy  extends T implements Parcelable {
    private int status;
    private String msg;

    private List<DataBean> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Parcelable {
        private int origin;
        private int id;
        private String imageUrl;
        private String title;
        /**
         * shareContent : 如何教育孩子
         * shareImageUrl : http://120.24.153.104:8096/upload/null
         * shareLinkUrl : https://cms1.ihealthbaby.cn/product/index.do
         * url : https://cms1.ihealthbaby.cn/product/index.do
         */

        private ShareModelBean shareModel;
        private String url;
        private List<String> tags;

        public int getOrigin() {
            return origin;
        }

        public void setOrigin(int origin) {
            this.origin = origin;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public ShareModelBean getShareModel() {
            return shareModel;
        }

        public void setShareModel(ShareModelBean shareModel) {
            this.shareModel = shareModel;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public List<String> getTags() {
            return tags;
        }

        public void setTags(List<String> tags) {
            this.tags = tags;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.origin);
            dest.writeInt(this.id);
            dest.writeString(this.imageUrl);
            dest.writeString(this.title);
            dest.writeParcelable(this.shareModel, flags);
            dest.writeString(this.url);
            dest.writeStringList(this.tags);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.origin = in.readInt();
            this.id = in.readInt();
            this.imageUrl = in.readString();
            this.title = in.readString();
            this.shareModel = in.readParcelable(ShareModelBean.class.getClassLoader());
            this.url = in.readString();
            this.tags = in.createStringArrayList();
        }

        public static final Creator<DataBean> CREATOR = new Creator<DataBean>() {
            @Override
            public DataBean createFromParcel(Parcel source) {
                return new DataBean(source);
            }

            @Override
            public DataBean[] newArray(int size) {
                return new DataBean[size];
            }
        };
    }

    public static class ShareModelBean implements Parcelable {
        private String shareContent;
        private String shareImageUrl;
        private String shareLinkUrl;
        private String url;

        public String getShareContent() {
            return shareContent;
        }

        public void setShareContent(String shareContent) {
            this.shareContent = shareContent;
        }

        public String getShareImageUrl() {
            return shareImageUrl;
        }

        public void setShareImageUrl(String shareImageUrl) {
            this.shareImageUrl = shareImageUrl;
        }

        public String getShareLinkUrl() {
            return shareLinkUrl;
        }

        public void setShareLinkUrl(String shareLinkUrl) {
            this.shareLinkUrl = shareLinkUrl;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.shareContent);
            dest.writeString(this.shareImageUrl);
            dest.writeString(this.shareLinkUrl);
            dest.writeString(this.url);
        }

        public ShareModelBean() {
        }

        protected ShareModelBean(Parcel in) {
            this.shareContent = in.readString();
            this.shareImageUrl = in.readString();
            this.shareLinkUrl = in.readString();
            this.url = in.readString();
        }

        public static final Creator<ShareModelBean> CREATOR = new Creator<ShareModelBean>() {
            @Override
            public ShareModelBean createFromParcel(Parcel source) {
                return new ShareModelBean(source);
            }

            @Override
            public ShareModelBean[] newArray(int size) {
                return new ShareModelBean[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.status);
        dest.writeString(this.msg);
        dest.writeTypedList(this.data);
    }

    public InformationEntitiy() {
    }

    protected InformationEntitiy(Parcel in) {
        this.status = in.readInt();
        this.msg = in.readString();
        this.data = in.createTypedArrayList(DataBean.CREATOR);
    }

    public static final Creator<InformationEntitiy> CREATOR = new Creator<InformationEntitiy>() {
        @Override
        public InformationEntitiy createFromParcel(Parcel source) {
            return new InformationEntitiy(source);
        }

        @Override
        public InformationEntitiy[] newArray(int size) {
            return new InformationEntitiy[size];
        }
    };
}
