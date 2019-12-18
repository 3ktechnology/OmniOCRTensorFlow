package com.omni.omnisdk.utility;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;

import com.omni.omnisdk.R;
import com.omni.omnisdk.data.model.GalleryModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;



public class ImageFetcher extends AsyncTask<Cursor, Void, ImageFetcher.ModelList> {


    public int startingCount = 0;
    public String header = "";
    private ArrayList<GalleryModel> LIST = new ArrayList<>();
    private Context context;

    public ImageFetcher(Context context) {
        this.context = context;
    }

    public int getStartingCount() {
        return startingCount;
    }

    public void setStartingCount(int startingCount) {
        this.startingCount = startingCount;
    }


    @Override
    protected ModelList doInBackground(Cursor... cursors) {
        Cursor cursor = cursors[0];
        try {
            if (cursor != null) {
                int date = cursor.getColumnIndex(MediaStore.Images.Media.DATE_TAKEN);
                int data = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
                int contentUrl = cursor.getColumnIndex(MediaStore.Images.Media._ID);

                int limit = 100;
                if (cursor.getCount() < limit) {
                    limit = cursor.getCount() - 1;
                }
                cursor.move(limit);
                synchronized (context) {
                    int pos = getStartingCount();

                    for (int i = limit; i < cursor.getCount(); i++) {
                        cursor.moveToNext();
                        Uri path = Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "" + cursor.getInt(contentUrl));
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTimeInMillis(cursor.getLong(date));
                        String dateDifference = getDateDifference(context, calendar);


                        // CameraModel img = new CameraModel(getRealPathFromUri(context, path), false);

                        LIST.add(new GalleryModel(path, false));
                    }
                    cursor.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ModelList(LIST);
    }

    private String getDateDifference(Context context, Calendar calendar) {
        Date d = calendar.getTime();
        Calendar lastMonth = Calendar.getInstance();
        Calendar lastWeek = Calendar.getInstance();
        Calendar recent = Calendar.getInstance();
        lastMonth.add(Calendar.DAY_OF_MONTH, -(Calendar.DAY_OF_MONTH));
        lastWeek.add(Calendar.DAY_OF_MONTH, -7);
        recent.add(Calendar.DAY_OF_MONTH, -2);
        if (calendar.before(lastMonth)) {
            return new SimpleDateFormat("MMMM").format(d);
        } else if (calendar.after(lastMonth) && calendar.before(lastWeek)) {
            return context.getResources().getString(R.string.pix_last_month);
        } else if (calendar.after(lastWeek) && calendar.before(recent)) {
            return context.getResources().getString(R.string.pix_last_week);
        } else {
            return context.getResources().getString(R.string.pix_recent);
        }
    }


    public class ModelList {
        ArrayList<GalleryModel> LIST = new ArrayList<>();

        public ModelList(ArrayList<GalleryModel> LIST) {
            this.LIST = LIST;
        }

        public ArrayList<GalleryModel> getLIST() {
            return LIST;
        }

    }


}
