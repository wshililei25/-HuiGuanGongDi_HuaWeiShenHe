package com.huiguangongdi.req;

import android.os.Parcel;
import android.os.Parcelable;

public class QualityManagerReq implements Parcelable {
    private int pid;
    private int status;
    private int deal_company;
    private int work_type;
    private String start_date;
    private String end_date;
    private int share;

    public QualityManagerReq() {

    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getDeal_company() {
        return deal_company;
    }

    public void setDeal_company(int deal_company) {
        this.deal_company = deal_company;
    }

    public int getWork_type() {
        return work_type;
    }

    public void setWork_type(int work_type) {
        this.work_type = work_type;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public int getShare() {
        return share;
    }

    public void setShare(int share) {
        this.share = share;
    }

    protected QualityManagerReq(Parcel in) {
        pid = in.readInt();
        status = in.readInt();
        deal_company = in.readInt();
        work_type = in.readInt();
        start_date = in.readString();
        end_date = in.readString();
        share = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(pid);
        dest.writeInt(status);
        dest.writeInt(deal_company);
        dest.writeInt(work_type);
        dest.writeString(start_date);
        dest.writeString(end_date);
        dest.writeInt(share);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<QualityManagerReq> CREATOR = new Creator<QualityManagerReq>() {
        @Override
        public QualityManagerReq createFromParcel(Parcel in) {
            return new QualityManagerReq(in);
        }

        @Override
        public QualityManagerReq[] newArray(int size) {
            return new QualityManagerReq[size];
        }
    };
}
