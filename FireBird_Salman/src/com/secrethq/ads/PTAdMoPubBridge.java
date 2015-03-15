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

import com.mopub.mobileads.MoPubErrorCode;
import com.mopub.mobileads.MoPubInterstitial;
import com.mopub.mobileads.MoPubInterstitial.InterstitialAdListener;
import com.mopub.mobileads.MoPubView;
import com.mopub.mobileads.MoPubView.BannerAdListener;

public class PTAdMoPubBridge {
	private static PTAdMoPubBridge sInstance;
	private static final String TAG = "PTAdMoPubBridge";
	private static Cocos2dxActivity activity;
	private static WeakReference<Cocos2dxActivity> s_activity;

	private static String adkey;
	private static ViewGroup view;

	private static MoPubInterstitial mInterstitial;
	private static MoPubView mBanner;

	public static PTAdMoPubBridge instance() {

		if (sInstance == null)
			sInstance = new PTAdMoPubBridge();
		return sInstance;
	}

	
	public static void initBridge(Cocos2dxActivity activity){
		Log.v(TAG, "PTAdMoPubBridge  -- INIT");

		PTAdMoPubBridge.s_activity = new WeakReference<Cocos2dxActivity>(activity);
		PTAdMoPubBridge.activity = activity;
		PTAdMoPubBridge.s_activity.get().runOnUiThread( new Runnable() {
			public void run() {
				FrameLayout frameLayout = (FrameLayout)PTAdMoPubBridge.activity.findViewById(android.R.id.content);
		
				LinearLayout layout = new LinearLayout( PTAdMoPubBridge.activity );
				layout.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
				layout.setGravity( Gravity.BOTTOM );
				frameLayout.addView( layout );
		
				PTAdMoPubBridge.view = layout;
			}
		});
	}

	public static void startSession( String token ){
		Log.v(TAG, "PTAdMoPubBridge  -- START Session " + token);

		PTAdMoPubBridge.adkey = token;

		PTAdMoPubBridge.s_activity.get().runOnUiThread( new Runnable() {
			public void run() {
				PTAdMoPubBridge.mInterstitial = new MoPubInterstitial(PTAdMoPubBridge.activity, PTAdMoPubBridge.adkey);

				PTAdMoPubBridge.mBanner = new MoPubView(PTAdMoPubBridge.activity);
				PTAdMoPubBridge.mBanner.setAdUnitId( PTAdMoPubBridge.adkey );
				PTAdMoPubBridge.mBanner.loadAd();
			}
		});
	}

	public static void showFullScreen() {
		Log.v(TAG, "showFullScreen");

		PTAdMoPubBridge.mInterstitial.show();
	}

	public static void showBannerAd(){
		Log.v(TAG, "showBannerAd");

		PTAdMoPubBridge.s_activity.get().runOnUiThread( new Runnable() {
			public void run() {
				if(PTAdMoPubBridge.view != null){
					PTAdMoPubBridge.view.addView( PTAdMoPubBridge.mBanner );
				}
			}
		});

	}

	public static void hideBannerAd(){
		Log.v(TAG, "hideBannerAd");

		if(PTAdMoPubBridge.view != null){
			if(PTAdMoPubBridge.view.getChildCount() > 0){
				PTAdMoPubBridge.s_activity.get().runOnUiThread( new Runnable() {
					public void run() {
						PTAdMoPubBridge.view.removeAllViews();
					}
				});
			}
		}
	}

}
