package com.secrethq.ads;
import java.lang.ref.WeakReference;

import org.cocos2dx.lib.Cocos2dxActivity;

import v2.com.playhaven.configuration.PHConfiguration;
import android.R;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.playhaven.src.common.PHConfig;
import com.playhaven.src.publishersdk.content.PHPublisherContentRequest;
import com.playhaven.src.publishersdk.open.PHPublisherOpenRequest;

public class PTAdUpsightBridge {
	private static PTAdUpsightBridge sInstance;
	private static final String TAG = "Upsight";
	private static Cocos2dxActivity activity;
	private static WeakReference<Cocos2dxActivity> s_activity;

	private static ViewGroup view;

	public static PTAdUpsightBridge instance() {

		if (sInstance == null)
			sInstance = new PTAdUpsightBridge();
		return sInstance;
	}

	public static void initBridge(Cocos2dxActivity activity){
		Log.v(TAG, "PTAdUpsightBridge  -- INIT");

		PTAdUpsightBridge.s_activity = new WeakReference<Cocos2dxActivity>(activity);
		PTAdUpsightBridge.activity = activity;

	}

	public static void startSession( String token, String key ){

		PHConfig.token = token;
		PHConfig.secret = key;
		
		PTAdUpsightBridge.s_activity.get().runOnUiThread( new Runnable() {
			public void run() {
				PHPublisherContentRequest request = new PHPublisherContentRequest(PTAdUpsightBridge.activity, "showinterstitial");
				request.preload();	
			}
		});
	}

	public static void showFullScreen() {
		Log.v(TAG, "showFullScreen");

//		PTAdUpsightBridge.activity.startActivity(FullScreen.createIntent(PTAdUpsightBridge.activity, "showinterstitial"));
		
		PHPublisherContentRequest request = new PHPublisherContentRequest(PTAdUpsightBridge.activity, "showinterstitial");
//		request.setOnContentListener(PlayHavenXBridge.instance());		
		request.send();
	}

	public static void showBannerAd(){
		Log.v(TAG, "showBannerAd");

	}

	public static void hideBannerAd(){
		Log.v(TAG, "hideBannerAd");

	}

}
