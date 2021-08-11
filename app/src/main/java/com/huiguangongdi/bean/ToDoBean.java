package com.huiguangongdi.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class ToDoBean implements Parcelable {

    private int id;
    private int uid;
    private String name;
    private String end_time;
    private String handle_people;
    private String check_people;
    private String solution;
    private String status;
    private String create_time;
    private String[] avatar;
    private String[] image;
    private String[] handle_people_list;
    private String[] check_people_list;

    public int getId() {
        return id;
    }

    public int getUid() {
        return uid;
    }

    public String getName() {
        return name;
    }

    public String getEnd_time() {
        return end_time;
    }

    public String getHandle_people() {
        return handle_people;
    }

    public String getCheck_people() {
        return check_people;
    }

    public String getSolution() {
        return solution;
    }

    public String getStatus() {
        return status;
    }

    public String getCreate_time() {
        return create_time;
    }

    public String[] getAvatar() {
        return avatar;
    }

    public String[] getImage() {
        return image;
    }

    public String[] getHandle_people_list() {
        return handle_people_list;
    }

    public String[] getCheck_people_list() {
        return check_people_list;
    }

    protected ToDoBean(Parcel in) {
        id = in.readInt();
        uid = in.readInt();
        name = in.readString();
        end_time = in.readString();
        handle_people = in.readString();
        check_people = in.readString();
        solution = in.readString();
        status = in.readString();
        create_time = in.readString();
        avatar = in.createStringArray();
        image = in.createStringArray();
        handle_people_list = in.createStringArray();
        check_people_list = in.createStringArray();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(uid);
        dest.writeString(name);
        dest.writeString(end_time);
        dest.writeString(handle_people);
        dest.writeString(check_people);
        dest.writeString(solution);
        dest.writeString(status);
        dest.writeString(create_time);
        dest.writeStringArray(avatar);
        dest.writeStringArray(image);
        dest.writeStringArray(handle_people_list);
        dest.writeStringArray(check_people_list);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ToDoBean> CREATOR = new Creator<ToDoBean>() {
        @Override
        public ToDoBean createFromParcel(Parcel in) {
            return new ToDoBean(in);
        }

        @Override
        public ToDoBean[] newArray(int size) {
            return new ToDoBean[size];
        }
    };
}
