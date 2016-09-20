package com.beansontoast.pictag;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.apache.sanselan.ImageReadException;
import org.apache.sanselan.ImageWriteException;
import org.apache.sanselan.Sanselan;
import org.apache.sanselan.common.IImageMetadata;
import org.apache.sanselan.common.ImageMetadata;
import org.apache.sanselan.formats.jpeg.JpegImageMetadata;
import org.apache.sanselan.formats.jpeg.exifRewrite.ExifRewriter;
import org.apache.sanselan.formats.tiff.TiffImageMetadata;
import org.apache.sanselan.formats.tiff.constants.ExifTagConstants;
import org.apache.sanselan.formats.tiff.constants.TiffConstants;
import org.apache.sanselan.formats.tiff.write.TiffOutputDirectory;
import org.apache.sanselan.formats.tiff.write.TiffOutputField;
import org.apache.sanselan.formats.tiff.write.TiffOutputSet;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ContentResolver cr = getContentResolver();

        String[] columns = new String[]{
                MediaStore.Images.ImageColumns._ID,
                MediaStore.Images.ImageColumns.TITLE,
                MediaStore.Images.ImageColumns.DATA,
                MediaStore.Images.ImageColumns.MIME_TYPE,
                MediaStore.Images.ImageColumns.SIZE};
        Cursor cur = cr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                columns, MediaStore.Images.ImageColumns.MIME_TYPE + " = 'image/jpeg'", null, null);

        if (cur != null && cur.getCount() > 0) {
            cur.moveToFirst();
            do {
                int id = cur.getInt(cur.getColumnIndex(MediaStore.Images.ImageColumns._ID));
                String title = cur.getString(cur.getColumnIndex(MediaStore.Images.ImageColumns.TITLE));
                String data = cur.getString(cur.getColumnIndex(MediaStore.Images.ImageColumns.DATA));
                String mimeType = cur.getString(cur.getColumnIndex(MediaStore.Images.ImageColumns.MIME_TYPE));
                long size = cur.getLong(cur.getColumnIndex(MediaStore.Images.ImageColumns.SIZE));

                File f = new File(data);
                try {
                    changeExifMetadata(f, f);

                    String here = "";
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ImageReadException e) {
                    e.printStackTrace();
                } catch (ImageWriteException e) {
                    e.printStackTrace();
                }

                String here = "";
            } while (cur.moveToNext());
        } else {
            String here = "Empty";
        }
    }

    /**
     * This example illustrates how to add/update EXIF metadata in a JPEG file.
     *
     * @param jpegImageFile A source image file.
     * @param dst           The output file.
     * @throws java.io.IOException
     * @throws org.apache.sanselan.ImageReadException
     * @throws org.apache.sanselan.ImageWriteException
     */
    public void changeExifMetadata(final File jpegImageFile, final File dst)
            throws IOException, ImageReadException, ImageWriteException {

        FileOutputStream fos = new FileOutputStream(dst);
        OutputStream os = new BufferedOutputStream(fos);

        TiffOutputSet outputSet = null;

        // note that metadata might be null if no metadata is found.
        final IImageMetadata metadata = Sanselan.getMetadata(jpegImageFile);
        final JpegImageMetadata jpegMetadata = (JpegImageMetadata) metadata;
        if (null != jpegMetadata) {
            // note that exif might be null if no Exif metadata is found.
            final TiffImageMetadata exif = jpegMetadata.getExif();

            if (null != exif) {
                // TiffImageMetadata class is immutable (read-only).
                // TiffOutputSet class represents the Exif data to write.
                //
                // Usually, we want to update existing Exif metadata by
                // changing
                // the values of a few fields, or adding a field.
                // In these cases, it is easiest to use getOutputSet() to
                // start with a "copy" of the fields read from the image.
                outputSet = exif.getOutputSet();
            }
        }

        // if file does not contain any exif metadata, we create an empty
        // set of exif metadata. Otherwise, we keep all of the other
        // existing tags.
        if (null == outputSet) {
            outputSet = new TiffOutputSet();
        }

        {
            // Example of how to add a field/tag to the output set.
            //
            // Note that you should first remove the field/tag if it already
            // exists in this directory, or you may end up with duplicate
            // tags. See above.
            //
            // Certain fields/tags are expected in certain Exif directories;
            // Others can occur in more than one directory (and often have a
            // different meaning in different directories).
            //
            // TagInfo constants often contain a description of what
            // directories are associated with a given tag.
            //
            final TiffOutputDirectory exifDirectory = outputSet.getOrCreateExifDirectory();
            // make sure to remove old value if present (this method will
            // not fail if the tag does not exist).
            TiffOutputField longitudeRefField = TiffOutputField.create(
                    TiffConstants.EXIF_TAG_IMAGE_DESCRIPTION, TiffConstants.DEFAULT_TIFF_BYTE_ORDER,
                    "Test");
            exifDirectory.removeField(TiffConstants.EXIF_TAG_IMAGE_DESCRIPTION);
            exifDirectory.add(longitudeRefField);
        }

        {
            // Example of how to add/update GPS info to output set.

            // New York City
            final double longitude = -74.0; // 74 degrees W (in Degrees East)
            final double latitude = 40 + 43 / 60.0; // 40 degrees N (in Degrees
            // North)

            outputSet.setGPSInDegrees(longitude, latitude);
        }

        // printTagValue(jpegMetadata, TiffConstants.TIFF_TAG_DATE_TIME);

        new ExifRewriter().updateExifMetadataLossless(jpegImageFile, os,
                outputSet);

    }
}
