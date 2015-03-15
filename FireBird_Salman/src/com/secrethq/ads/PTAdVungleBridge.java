package com.secrethq.ads;

import org.cocos2dx.lib.Cocos2dxActivity;

import android.util.Log;

import com.vungle.publisher.VunglePub;
import com.vungle.publisher.AdConfig;
import com.vungle.publisher.Orientation;


public class PTAdVungleBridge {
	private static PTAdVungleBridge sInstance;
	private static final String TAG = "Vungle";
	private static Cocos2dxActivity activity;
	private static final VunglePub vunglePub = VunglePub.getInstance();

	public static PTAdVungleBridge instance() {

		if (sInstance == null)
			sInstance = new PTAdVungleBridge();
		return sInstance;
	}

	public static void initBridge(Cocos2dxActivity activity){
		Log.v(TAG, "PTAdVungleBridge  -- INIT");
		PTAdVungleBridge.activity = activity;
	}

	public static void startSession( String sdkKey ){
		Log.v(TAG, "PTAdVungleBridge  -- startSession With Key: " + sdkKey);		
		PTAdVungleBridge.vunglePub.init(PTAdVungleBridge.activity, sdkKey);
		final AdConfig globalAdConfig = vunglePub.getGlobalAdConfig();
		globalAdConfig.setSoundEnabled(true);
		globalAdConfig.setOrientation(Orientation.autoRotate);
	}

	public static void showFullScreen() {
		Log.v(TAG, "showFullScreen");
		final AdConfig overrideConfig = new AdConfig();
		overrideConfig.setIncentivized(true);
		overrideConfig.setSoundEnabled(false);
		PTAdVungleBridge.vunglePub.playAd(overrideConfig);
	}

	public static void showBannerAd(){
		Log.v(TAG, "showBannerAd");

	}

	public static void hideBannerAd(){
		Log.v(TAG, "hideBannerAd");

	}

}
