//package com.huiguangongdi.bean;
//
//import android.os.Parcel;
//import android.os.Parcelable;
//
//public class DealerBean implements Parcelable {
//
//    private int uid;
//    private int role;
//    private int cid;
//    private int wid;
//    private String cname;
//    private String wname;
//    private String tag;
//    private String avatar;
//
//    public int getUid() {
//        return uid;
//    }
//
//    public int getRole() {
//        return role;
//    }
//
//    public int getCid() {
//        return cid;
//    }
//
//    public int getWid() {
//        return wid;
//    }
//
//    public String getCname() {
//        return cname;
//    }
//
//    public String getWname() {
//        return wname;
//    }
//
//    public String getTag() {
//        return tag;
//    }
//
//    public String getAvatar() {
//        return avatar;
//    }
//
//    protected DealerBean(Parcel in) {
//        uid = in.readInt();
//        role = in.readInt();
//        cid = in.readInt();
//        wid = in.readInt();
//        cname = in.readString();
//        wname = in.readString();
//        tag = in.readString();
//        avatar = in.readString();
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeInt(uid);
//        dest.writeInt(role);
//        dest.writeInt(cid);
//        dest.writeInt(wid);
//        dest.writeString(cname);
//        dest.writeString(wname);
//        dest.writeString(tag);
//        dest.writeString(avatar);
//    }
//
//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    public static final Creator<DealerBean> CREATOR = new Creator<DealerBean>() {
//        @Override
//        public DealerBean createFromParcel(Parcel in) {
//            return new DealerBean(in);
//        }
//
//        @Override
//        public DealerBean[] newArray(int size) {
//            return new DealerBean[size];
//        }
//    };
//}
