package com.beansontoast.pictag.interactor;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.beansontoast.pictag.model.MediaStoreData;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

/**
 * Created by renier on 9/21/2016.
 */

public class MediaStoreInteractor {
    private Context mContext;

    public MediaStoreInteractor(Context mContext) {
        this.mContext = mContext;
    }

    public Observable<List<MediaStoreData>> getMediaStoreData() {
        return Observable.defer(() -> Observable.create(subscriber -> {
            List<MediaStoreData> toReturn = new ArrayList<>();

            String[] PROJECTION = new String[]{
                    MediaStore.Images.ImageColumns._ID,
                    MediaStore.Images.Media.DATA,
                    MediaStore.Images.ImageColumns.MIME_TYPE,
                    MediaStore.Images.ImageColumns.DATE_TAKEN};
            Cursor cur = mContext.getContentResolver().query(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    PROJECTION,
                    MediaStore.Images.ImageColumns.MIME_TYPE + " = 'image/jpeg'",
                    null,
                    null);

            if (cur != null && cur.getCount() > 0) {
                cur.moveToFirst();

                do {
                    long id = cur.getLong(cur.getColumnIndex(MediaStore.Images.ImageColumns._ID));
                    long dateTaken = cur.getLong(cur.getColumnIndex(MediaStore.Images.ImageColumns.DATE_TAKEN));
                    String data = cur.getString(cur.getColumnIndex(MediaStore.Images.ImageColumns.DATA));
                    String mimeType = cur.getString(cur.getColumnIndex(MediaStore.Images.ImageColumns.MIME_TYPE));

                    toReturn.add(MediaStoreData.getInstance(id, dateTaken, Uri.parse("file://" + data), mimeType));

                } while (cur.moveToNext());
            }

            subscriber.onNext(toReturn);
            subscriber.onCompleted();
        }));
    }

}
