package com.beansontoast.pictag.model;

import android.net.Uri;
import android.provider.MediaStore;

/**
 * Created by renier on 9/21/2016.
 */

public class MediaStoreData {

    private long mId;
    private long mDateTaken;
    private Uri mUri;
    private String mMimeType;

    private MediaStoreData(long mId, long mDateTaken, Uri mUri, String mMimeType) {
        this.mId = mId;
        this.mDateTaken = mDateTaken;
        this.mUri = mUri;
        this.mMimeType = mMimeType;
    }

    public static MediaStoreData getInstance(long mId, long mDateTaken, Uri mUri, String mMimeType) {
        return new MediaStoreData(mId, mDateTaken, mUri, mMimeType);
    }

}
