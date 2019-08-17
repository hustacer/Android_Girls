package com.example.android_girls;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.LruCache;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ImageLoader {
    private LruCache<String, Bitmap> mCache;

    public ImageLoader() {
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cacheSize = maxMemory;
        mCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };
    }

    public void showImageByAsyncTask(ImageView imageView, String url) {
        Bitmap bitmap = getBitmapFromCache(url);
        if (bitmap == null){
            new GirlsImageAsyncTask(imageView, url).execute(url);
        } else {
            imageView.setImageBitmap(bitmap);
        }
    }

    private Bitmap getBitmapFromCache(String url) {
        return mCache.get(url);
    }

    class GirlsImageAsyncTask extends AsyncTask<String, Void, Bitmap > {
        private ImageView mImageView;
        private String mUrl;

        public GirlsImageAsyncTask(ImageView mImageView, String mUrl) {
            this.mImageView = mImageView;
            this.mUrl = mUrl;
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            Bitmap bitmap = getBitmapFromUrl(strings[0]);
            if(bitmap!=null) {
                addBitmapToCache(strings[0], bitmap);
            }

            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if (mImageView.getTag().equals(mUrl))  {
                mImageView.setImageBitmap(bitmap);
            }
        }
    }

    private Bitmap getBitmapFromUrl(String urlString) {
        Bitmap bitmap;
        InputStream inputStream = null;

        try {
            URL url = new URL(urlString);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            inputStream =new BufferedInputStream(httpURLConnection.getInputStream());
            bitmap = BitmapFactory.decodeStream(inputStream);
            httpURLConnection.disconnect();
            return bitmap;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    private void addBitmapToCache(String string, Bitmap bitmap) {
        if(getBitmapFromUrl(string) == null) {
            mCache.put(string, bitmap);
        }
    }
}
