package com.lifttracker.utilities;

import android.content.Context;

import java.io.FileOutputStream;

/**
 * Created by cameronridgewell on 6/29/16.
 */

public class MemoryRequisition {

    private String FILENAME = "lifttracker_saved_items";
    private static Context mContext;
    private static MemoryRequisition instance = null;

    protected MemoryRequisition(Context context){
        mContext = context;
    };

    public static MemoryRequisition getInstance(Context context) {
        if (instance == null) {
            mContext = context;
            return new MemoryRequisition(context);
        } else {
            return instance;
        }
    }
}
