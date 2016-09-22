package com.beansontoast.pictag.model;

import android.net.Uri;
import android.provider.MediaStore;

/**
 * Created by renier on 9/21/2016.
 */

public class MediaStoreData {

    public enum Type {
        Image,
        Video
    }

    private long mId;
    private long mDateTaken;
    private long mDateModified;
    private Uri mUri;
    private String mMimeType;
    private int mOrientation;
    private Type mType;

    private MediaStoreData(long mId, long mDateTaken, long mDateModified, Uri mUri, String mMimeType, int mOrientation, Type mType) {
        this.mId = mId;
        this.mDateTaken = mDateTaken;
        this.mDateModified = mDateModified;
        this.mUri = mUri;
        this.mMimeType = mMimeType;
        this.mOrientation = mOrientation;
        this.mType = mType;
    }

    public static MediaStoreData getInstance(long mId, long mDateTaken, long mDateModified, Uri mUri, String mMimeType,
                                             int mOrientation, Type mType) {
        return new MediaStoreData(mId, mDateTaken, mDateModified, mUri, mMimeType, mOrientation, mType);
    }

    public long getDateTaken() {
        return mDateTaken;
    }

    public void setDateTaken(long mDateTaken) {
        this.mDateTaken = mDateTaken;
    }

    public long getDateModified() {
        return mDateModified;
    }

    public void setDateModified(long mDateModified) {
        this.mDateModified = mDateModified;
    }

    public long getId() {
        return mId;
    }

    public void setId(long mId) {
        this.mId = mId;
    }

    public String getMimeType() {
        return mMimeType;
    }

    public void setMimeType(String mMimeType) {
        this.mMimeType = mMimeType;
    }

    public Uri getUri() {
        return mUri;
    }

    public void setUri(Uri mUri) {
        this.mUri = mUri;
    }

    public int getOrientation() {
        return mOrientation;
    }

    public void setOrientation(int mOrientation) {
        this.mOrientation = mOrientation;
    }

    public Type getType() {
        return mType;
    }

    public void setType(Type mType) {
        this.mType = mType;
    }
}
