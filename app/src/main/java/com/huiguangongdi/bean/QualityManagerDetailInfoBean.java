package com.huiguangongdi.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class QualityManagerDetailInfoBean implements Parcelable {

    private int id;
    private int pid;
    private int uid;
    private int day;
    private int role;
    private int type; //1轴线 2自动创建
    private String name;
    private String[] address;
    private String[] image;
    private String end_time;
    private String begin_time;
    private String handle_people;
    private String check_people;
    private String status;
    private String solution;
    private String question_supple;
    private int work_type_id;
    private String work_type;
    private String[] avatar;
    private boolean handler; //true表示处理人
    private boolean checker; //true表示核查人
    private boolean creater; //true表示创建者

    public int getId() {
        return id;
    }

    public int getPid() {
        return pid;
    }

    public int getUid() {
        return uid;
    }

    public int getDay() {
        return day;
    }

    public int getRole() {
        return role;
    }

    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String[] getAddress() {
        return address;
    }

    public String[] getImage() {
        return image;
    }

    public String getEnd_time() {
        return end_time;
    }

    public String getBegin_time() {
        return begin_time;
    }

    public String getHandle_people() {
        return handle_people;
    }

    public String getCheck_people() {
        return check_people;
    }

    public String getStatus() {
        return status;
    }

    public String getSolution() {
        return solution;
    }

    public String getQuestion_supple() {
        return question_supple;
    }

    public int getWork_type_id() {
        return work_type_id;
    }

    public String getWork_type() {
        return work_type;
    }

    public String[] getAvatar() {
        return avatar;
    }

    public boolean isHandler() {
        return handler;
    }

    public boolean isChecker() {
        return checker;
    }

    public boolean isCreater() {
        return creater;
    }

    protected QualityManagerDetailInfoBean(Parcel in) {
        id = in.readInt();
        pid = in.readInt();
        uid = in.readInt();
        day = in.readInt();
        role = in.readInt();
        type = in.readInt();
        name = in.readString();
        address = in.createStringArray();
        image = in.createStringArray();
        end_time = in.readString();
        begin_time = in.readString();
        handle_people = in.readString();
        check_people = in.readString();
        status = in.readString();
        solution = in.readString();
        question_supple = in.readString();
        work_type_id = in.readInt();
        work_type = in.readString();
        avatar = in.createStringArray();
        handler = in.readByte() != 0;
        checker = in.readByte() != 0;
        creater = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(pid);
        dest.writeInt(uid);
        dest.writeInt(day);
        dest.writeInt(role);
        dest.writeInt(type);
        dest.writeString(name);
        dest.writeStringArray(address);
        dest.writeStringArray(image);
        dest.writeString(end_time);
        dest.writeString(begin_time);
        dest.writeString(handle_people);
        dest.writeString(check_people);
        dest.writeString(status);
        dest.writeString(solution);
        dest.writeString(question_supple);
        dest.writeInt(work_type_id);
        dest.writeString(work_type);
        dest.writeStringArray(avatar);
        dest.writeByte((byte) (handler ? 1 : 0));
        dest.writeByte((byte) (checker ? 1 : 0));
        dest.writeByte((byte) (creater ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<QualityManagerDetailInfoBean> CREATOR = new Creator<QualityManagerDetailInfoBean>() {
        @Override
        public QualityManagerDetailInfoBean createFromParcel(Parcel in) {
            return new QualityManagerDetailInfoBean(in);
        }

        @Override
        public QualityManagerDetailInfoBean[] newArray(int size) {
            return new QualityManagerDetailInfoBean[size];
        }
    };
}
