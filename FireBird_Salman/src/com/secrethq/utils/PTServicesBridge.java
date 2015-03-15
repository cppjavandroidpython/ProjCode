
package com.secrethq.utils;

import java.lang.ref.WeakReference;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.cocos2dx.lib.Cocos2dxActivity;

import android.content.*;

import android.app.AlertDialog;
import android.net.Uri;

import android.util.Log;

public class PTServicesBridge {
	private static PTServicesBridge sInstance;
	private static final String TAG = "PTServicesBridge";

	private static native String getLeaderboardId();
	private static native void warningMessageClicked(boolean accepted);
	
	private static Cocos2dxActivity activity;
	private static WeakReference<Cocos2dxActivity> s_activity;


	private static String urlString;
	private static int scoreValue;

    private static final int RC_SIGN_IN = 9001;	
	private static final int REQUEST_LEADERBOARD = 5000;
	
	public static PTServicesBridge instance() {
		if (sInstance == null)
			sInstance = new PTServicesBridge();
		return sInstance;
	}

	public static void initBridge(Cocos2dxActivity activity, String appId){
		Log.v(TAG, "PTServicesBridge  -- INIT");

		PTServicesBridge.s_activity = new WeakReference<Cocos2dxActivity>(activity);
		PTServicesBridge.activity = activity;

		if(appId == null || appId.length() == 0){
			return;
		}

	}

	
     public static void openShareWidget( String message ){
            Log.v(TAG, "PTServicesBridge  -- openShareWidget with text:" + message);
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, message);
            PTServicesBridge.activity.startActivity(Intent.createChooser(sharingIntent, "Share" ));
	}

	public static void showWarningMessage(final String message){
		Log.v(TAG, "Show warning with message: " + message);
		PTServicesBridge.s_activity.get().runOnUiThread( new Runnable() {
			public void run() {
				AlertDialog.Builder dlgAlert  = new AlertDialog.Builder( PTServicesBridge.activity );

				dlgAlert.setMessage(message);
				dlgAlert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface dialog, int which) {
			            PTServicesBridge.warningMessageClicked( false );
			          }
			      });
				dlgAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface dialog, int which) {
			        	PTServicesBridge.warningMessageClicked( true ); 
			          }
			      });
				dlgAlert.setCancelable(true);
				dlgAlert.create().show();
			}
		});
		
	}
	
	public static void openUrl( String url ){
		Log.v(TAG, "PTServicesBridge  -- Open URL " + url);

		PTServicesBridge.urlString = url;

		PTServicesBridge.s_activity.get().runOnUiThread( new Runnable() {
			public void run() {
				final Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(PTServicesBridge.urlString));
				PTServicesBridge.activity.startActivity(intent);
			}
		});
	}

	  
	public static void showLeaderboard( ){
		Log.v(TAG, "PTServicesBridge  -- Show Leaderboard ");

	}

	public static void submitScrore( int score ){
		Log.v(TAG, "PTServicesBridge  -- Submit Score " + score);

	}

	public static void loginGameServices( ){
		Log.v(TAG, "PTServicesBridge  -- Login Game Services ");
		

	}

	public static boolean isGameServiceAvialable( ){
		Log.v(TAG, "PTServicesBridge  -- Is Game Service Avialable ");

		return false;
	}



	public void  onActivityResult(int requestCode, int responseCode, Intent intent){

	}

	public static String sha1( byte[] data, int length) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		MessageDigest md = MessageDigest.getInstance("SHA-1");
        md.update(data, 0, length);
        byte[] sha1hash = md.digest();
        return convertToHex(sha1hash);
	}
		
    private static String convertToHex(byte[] data) {
        StringBuilder buf = new StringBuilder();
        for (byte b : data) {
            int halfbyte = (b >>> 4) & 0x0F;
            int two_halfs = 0;
            do {
                buf.append((0 <= halfbyte) && (halfbyte <= 9) ? (char) ('0' + halfbyte) : (char) ('a' + (halfbyte - 10)));
                halfbyte = b & 0x0F;
            } while (two_halfs++ < 1);
        }
        return buf.toString();
    }
}
