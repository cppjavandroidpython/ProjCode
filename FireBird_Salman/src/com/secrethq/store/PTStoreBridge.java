package com.secrethq.store;

import org.cocos2dx.lib.Cocos2dxActivity;

public class PTStoreBridge {
	private static boolean readyToPurchase;
	
	private static final String TAG = "PTStoreBridge";
	
    private static native String licenseKey();
    
	static public void initBridge(Cocos2dxActivity activity){


	}
	
	static public void purchase( String storeId ){

	}
	
	static public void consumePurchase( String storeId ){

	}
}
