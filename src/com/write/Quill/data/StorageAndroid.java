package com.write.Quill.data;

import java.io.File;
import java.util.UUID;

import junit.framework.Assert;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.text.format.DateUtils;
import android.util.Log;
import android.widget.Toast;

public class StorageAndroid extends Storage {
	public final static String TAG = "StorageAndroid";
	
	private final Context context;
	private final Handler handler;
	
	public static void initialize(Context context) {
		if (instance != null) return;
		new StorageAndroid(context);
	}
	
	protected StorageAndroid(Context context) {
		Assert.assertNull(Storage.instance); // only construct once
		this.context = context.getApplicationContext();
		handler = new Handler();
		instance = this;
		postInitializaton();
	}
	
	public File getFilesDir() {
		return context.getFilesDir();
	}
	
	public String formatDateTime(long millis) {
		int fmt = DateUtils.FORMAT_SHOW_DATE + DateUtils.FORMAT_SHOW_TIME + 
				DateUtils.FORMAT_SHOW_YEAR + DateUtils.FORMAT_SHOW_WEEKDAY;
		return  DateUtils.formatDateTime(context, millis, fmt);
	}


	private static final String KEY_CURRENT_BOOK_UUID = "current_book_uuid"; 
	
	protected UUID loadCurrentBookUUID() {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        String s = settings.getString(KEY_CURRENT_BOOK_UUID, null);
        Log.e(TAG, "KEY = " + s + " bool = "+(s == null));
        if (s == null)
        	return null;
        else
        	return UUID.fromString(s); 
	}

	protected void saveCurrentBookUUID(UUID uuid) {
        SharedPreferences settings= PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(KEY_CURRENT_BOOK_UUID, uuid.toString());
        editor.commit();
	}

	public void LogMessage(String TAG, String message) {
		showToast(message, Toast.LENGTH_LONG);
	}

	public void LogError(String TAG, String message) {
		Log.e(TAG, message);
		showToast(message, Toast.LENGTH_LONG);
	}
	
	private void showToast(final String message, final int length) {
		handler.post(new Runnable() {
		    public void run() {
		        Toast.makeText(context, message, length).show();
		    }
		});
	}

}