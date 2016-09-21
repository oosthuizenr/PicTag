package com.beansontoast.pictag.main.view;

import com.beansontoast.pictag.model.MediaStoreData;

import java.util.List;

/**
 * Created by renier on 9/21/2016.
 */

public interface IMainActivityView {
    void showError(String message);
    void setModel(List<MediaStoreData> data);
}
