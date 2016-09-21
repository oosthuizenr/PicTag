package com.beansontoast.pictag.main.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.beansontoast.pictag.MainApplication;
import com.beansontoast.pictag.R;
import com.beansontoast.pictag.main.presenter.IMainActivityPresenter;
import com.beansontoast.pictag.model.MediaStoreData;

import org.apache.sanselan.ImageReadException;
import org.apache.sanselan.ImageWriteException;
import org.apache.sanselan.Sanselan;
import org.apache.sanselan.common.IImageMetadata;
import org.apache.sanselan.formats.jpeg.JpegImageMetadata;
import org.apache.sanselan.formats.tiff.TiffImageMetadata;
import org.apache.sanselan.formats.tiff.constants.TiffConstants;
import org.apache.sanselan.formats.tiff.write.TiffOutputDirectory;
import org.apache.sanselan.formats.tiff.write.TiffOutputField;
import org.apache.sanselan.formats.tiff.write.TiffOutputSet;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements IMainActivityView {

    @Inject
    IMainActivityPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainApplication.from(this).getGraph().inject(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        mPresenter.setView(this);
        mPresenter.onResume();
        /*String[] PERMISSIONS = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

        if (!Utils.hasPermissions(MainActivity.this, PERMISSIONS)) {
            Intent i = new Intent(MainActivity.this, IntroActivity.class);
            startActivity(i);
        } else {



        }*/
    }

    @Override
    protected void onPause() {
        mPresenter.onPause();
        mPresenter.clearView();
        super.onPause();
    }

    /**
     * This example illustrates how to add/update EXIF metadata in a JPEG file.
     *
     * @param src source byte array.
     * @param dst           The output file.
     * @throws java.io.IOException
     * @throws org.apache.sanselan.ImageReadException
     * @throws org.apache.sanselan.ImageWriteException
     */
    public void changeExifMetadata(File src, final File dst)
            throws IOException, ImageReadException, ImageWriteException {

     //   FileOutputStream fos = new FileOutputStream(dst);
     //   OutputStream os = new BufferedOutputStream(fos);

        TiffOutputSet outputSet = null;

        // note that metadata might be null if no metadata is found.
        final IImageMetadata metadata = Sanselan.getMetadata(src);
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

        // printTagValue(jpegMetadata, TiffConstants.TIFF_TAG_DATE_TIME);

        /*new ExifRewriter().updateExifMetadataLossless(jpegImageFile, os,
                outputSet);*/

    }

    @Override
    public void showError(String message) {

    }

    @Override
    public void setModel(List<MediaStoreData> data) {

    }
}
