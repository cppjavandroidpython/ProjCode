package com.secrethq.ads;
import java.lang.ref.WeakReference;

import org.cocos2dx.lib.Cocos2dxActivity;

import android.R;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.terqxnxpgcond.AdController;
import com.leadbolt.admob.LeadboltInterstitial;

public class PTAdLeadBoltBridge {
	private static PTAdLeadBoltBridge sInstance;
	private static final String TAG = "PTAdLeadBoltBridge";
	private static Cocos2dxActivity activity;
	private static WeakReference<Cocos2dxActivity> s_activity;

	private static AdController interstitial;
	private static String LB_INTERSTITIAL_ID;

	public static PTAdLeadBoltBridge instance() {

		if (sInstance == null)
			sInstance = new PTAdLeadBoltBridge();
		return sInstance;
	}

	
	public static void initBridge(Cocos2dxActivity activity){
		Log.v(TAG, "PTAdLeadBoltBridge  -- INIT");

		PTAdLeadBoltBridge.s_activity = new WeakReference<Cocos2dxActivity>(activity);
		PTAdLeadBoltBridge.activity = activity;
	}

	public static void startSession( String sdkKey ){
		PTAdLeadBoltBridge.LB_INTERSTITIAL_ID = sdkKey;
	}

	public static void showFullScreen() {
		Log.v(TAG, "showFullScreen");

		PTAdLeadBoltBridge.s_activity.get().runOnUiThread( new Runnable() {
			public void run() {
				interstitial = new AdController(PTAdLeadBoltBridge.activity, LB_INTERSTITIAL_ID);
				interstitial.loadAd();				
			}
		});			
	}

	public static void showBannerAd(){
		Log.v(TAG, "showBannerAd");

	}

	public static void hideBannerAd(){
		Log.v(TAG, "hideBannerAd");

	}

}
