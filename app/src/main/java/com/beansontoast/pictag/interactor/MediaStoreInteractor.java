package com.beansontoast.pictag.interactor;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.beansontoast.pictag.model.MediaStoreData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import rx.Observable;

/**
 * Created by renier on 9/21/2016.
 */

public class MediaStoreInteractor {
    private Context mContext;

    private String[] IMAGE_PROJECTION = new String[] {
            MediaStore.Images.ImageColumns._ID,
            MediaStore.Images.ImageColumns.DATE_TAKEN,
            MediaStore.Images.ImageColumns.DATE_MODIFIED,
            MediaStore.Images.ImageColumns.MIME_TYPE,
            MediaStore.Images.ImageColumns.ORIENTATION,
    };

    private String[] VIDEO_PROJECTION = new String[] {
            MediaStore.Video.VideoColumns._ID,
            MediaStore.Video.VideoColumns.DATE_TAKEN,
            MediaStore.Video.VideoColumns.DATE_MODIFIED,
            MediaStore.Video.VideoColumns.MIME_TYPE,
            "0 AS " + MediaStore.Images.ImageColumns.ORIENTATION,
    };

    public MediaStoreInteractor(Context mContext) {
        this.mContext = mContext;
    }

    public Observable<List<MediaStoreData>> getMediaStoreData() {
        return Observable.defer(() -> Observable.create(subscriber -> {
            List<MediaStoreData> toReturn = queryImages();
            toReturn.addAll(queryVideos());

            Collections.sort(toReturn, (mediaStoreData, mediaStoreData2) ->
                    Long.valueOf(mediaStoreData2.getDateTaken()).compareTo(mediaStoreData.getDateTaken()));

            subscriber.onNext(toReturn);
            subscriber.onCompleted();
        }));
    }

    private List<MediaStoreData> queryImages() {
        return query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_PROJECTION,
                MediaStore.Images.ImageColumns.DATE_TAKEN, MediaStore.Images.ImageColumns._ID,
                MediaStore.Images.ImageColumns.DATE_TAKEN, MediaStore.Images.ImageColumns.DATE_MODIFIED,
                MediaStore.Images.ImageColumns.MIME_TYPE, MediaStore.Images.ImageColumns.ORIENTATION,
                MediaStoreData.Type.Image);
    }

    private List<MediaStoreData> queryVideos() {
        return query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, VIDEO_PROJECTION,
                MediaStore.Video.VideoColumns.DATE_TAKEN, MediaStore.Video.VideoColumns._ID,
                MediaStore.Video.VideoColumns.DATE_TAKEN, MediaStore.Video.VideoColumns.DATE_MODIFIED,
                MediaStore.Video.VideoColumns.MIME_TYPE, MediaStore.Images.ImageColumns.ORIENTATION,
                MediaStoreData.Type.Video);
    }

    private List<MediaStoreData> query(Uri contentUri, String[] projection, String sortByCol,
                                       String idCol, String dateTakenCol, String dateModifiedCol,
                                       String mimeTypeCol, String orientationCol, MediaStoreData.Type type) {
        List<MediaStoreData> toReturn = new ArrayList<>();

        Cursor cursor = mContext.getContentResolver()
                .query(contentUri, projection, null, null, sortByCol + " DESC");

        if (cursor != null) {
            try {
                cursor.moveToFirst();

                final int idColNum = cursor.getColumnIndexOrThrow(idCol);
                final int dateTakenColNum = cursor.getColumnIndexOrThrow(dateTakenCol);
                final int dateModifiedColNum = cursor.getColumnIndexOrThrow(dateModifiedCol);
                final int mimeTypeColNum = cursor.getColumnIndex(mimeTypeCol);
                final int orientationColNum = cursor.getColumnIndexOrThrow(orientationCol);

                while (cursor.moveToNext()) {
                    long id = cursor.getLong(idColNum);
                    long dateTaken = cursor.getLong(dateTakenColNum);
                    String mimeType = cursor.getString(mimeTypeColNum);
                    long dateModified = cursor.getLong(dateModifiedColNum);
                    int orientation = cursor.getInt(orientationColNum);

                    toReturn.add(MediaStoreData.getInstance(id, dateTaken, dateModified,
                            Uri.withAppendedPath(contentUri, Long.toString(id)),
                            mimeType, orientation, type));
                }
            } finally {
                cursor.close();
            }
        }

        return toReturn;
    }

}
