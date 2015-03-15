package com.secrethq.ads;

import java.lang.ref.WeakReference;

import org.cocos2dx.lib.Cocos2dxActivity;

import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.google.android.gms.ads.*;

public class PTAdAdMobBridge {
	private static final String TAG = "PTAdAdMobBridge";
	private static Cocos2dxActivity activity;
	private static WeakReference<Cocos2dxActivity> s_activity;
	private static AdView adView;
	private static InterstitialAd interstitial;
	private static LinearLayout layout;
	private static boolean isScheduledForShow;
	private static native String bannerId();
	private static native String interstitialId();
	
	public static void initBridge(Cocos2dxActivity activity){
		Log.v(TAG, "PTAdAdMobBridge  -- INIT");
		
		isScheduledForShow = false;
		
		PTAdAdMobBridge.s_activity = new WeakReference<Cocos2dxActivity>(activity);	
		PTAdAdMobBridge.activity = activity;
		PTAdAdMobBridge.s_activity.get().runOnUiThread( new Runnable() {
            public void run() {
				FrameLayout frameLayout = (FrameLayout)PTAdAdMobBridge.activity.findViewById(android.R.id.content);
				LinearLayout layout = new LinearLayout( PTAdAdMobBridge.activity );
				layout.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
				layout.setOrientation( LinearLayout.VERTICAL);
				
				frameLayout.addView( layout );
				
				PTAdAdMobBridge.adView = new AdView( PTAdAdMobBridge.activity );
				PTAdAdMobBridge.adView.setAdSize(AdSize.SMART_BANNER);
				PTAdAdMobBridge.adView.setAdUnitId( PTAdAdMobBridge.bannerId() );
				
				layout.addView( PTAdAdMobBridge.adView );
						
				PTAdAdMobBridge.interstitial = new InterstitialAd( PTAdAdMobBridge.activity );
				PTAdAdMobBridge.interstitial.setAdUnitId( PTAdAdMobBridge.interstitialId() );
				PTAdAdMobBridge.interstitial.setAdListener(new AdListener() {
		            @Override
		            public void onAdLoaded() {
		            	if(PTAdAdMobBridge.isScheduledForShow){
		            		PTAdAdMobBridge.showFullScreen();
		            	}
		            }
		
		            @Override
		            public void onAdClosed() {
					    AdRequest adRequest = new AdRequest.Builder().build();
					    PTAdAdMobBridge.interstitial.loadAd(adRequest);
		            }
		        });
		
				
			    AdRequest adRequest = new AdRequest.Builder().build();
			    PTAdAdMobBridge.interstitial.loadAd(adRequest);
            }
        });
	}

	public static void showFullScreen(){
		Log.v(TAG, "showFullScreen");
		
		if(PTAdAdMobBridge.interstitial != null){
			PTAdAdMobBridge.s_activity.get().runOnUiThread( new Runnable() {
				public void run() {
					if(PTAdAdMobBridge.interstitial.isLoaded()){
						PTAdAdMobBridge.interstitial.show();
						PTAdAdMobBridge.isScheduledForShow = false;
					}
					else{
						PTAdAdMobBridge.isScheduledForShow = true;
					}
				}
			});
 		}
	}


	public static void showBannerAd(){
		Log.v(TAG, "showBannerAd");
		
		if(PTAdAdMobBridge.adView != null){
			PTAdAdMobBridge.s_activity.get().runOnUiThread( new Runnable() {
				public void run() {
					
					AdRequest adRequest = new AdRequest.Builder().build();
					PTAdAdMobBridge.adView.loadAd(adRequest);
					PTAdAdMobBridge.adView.setVisibility( View.VISIBLE );
				}
			});			
		}


	}

	public static void hideBannerAd(){
		Log.v(TAG, "hideBannerAd");
		 if(PTAdAdMobBridge.adView != null){
		 		PTAdAdMobBridge.s_activity.get().runOnUiThread( new Runnable() {
		 			public void run() {
		 				PTAdAdMobBridge.adView.setVisibility( View.INVISIBLE );
		 			}
		 		});
		 }
	}

}
