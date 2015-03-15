#include "PTPAppDelegate.h"
#include "cocos2d.h"
#include "PTPConfig.h"
#include "models/PTModelController.h"
#include "PTPSettingsController.h"
#include "platform/android/jni/JniHelper.h"
#include <jni.h>
#include <android/log.h>
#include "models/PTModelScreen.h"

#define  LOG_TAG    "main"
#define  LOGD(...)  __android_log_print(ANDROID_LOG_DEBUG,LOG_TAG,__VA_ARGS__)

using namespace cocos2d;

extern "C"
{

jint JNI_OnLoad(JavaVM *vm, void *reserved){
    JniHelper::setJavaVM(vm);
    return JNI_VERSION_1_4;
}

void Java_com_secrethq_utils_PTJniHelper_loadModelController(JNIEnv*  env, jobject thiz){
        PTLog("start laoding XML");

        PTModelController *mc = PTModelController::shared();
        mc->loadFromFile( CCString("data/main.xml") );

        PTLog("end loading XML");
}

jboolean Java_com_secrethq_utils_PTJniHelper_isAdNetworkActive(JNIEnv*  env, jobject thiz, jstring adNetworkNameString ){
    const char* adNetworkName = env->GetStringUTFChars(adNetworkNameString, 0);

    PTModelController *mc = PTModelController::shared();
    PTPSettingsController* sc = PTPSettingsController::shared();

    CCArray *screensArray  = mc->getModelArray("PTModelScreen");
    if(screensArray == NULL || sc->removeAds()){
        return false;
    }
    for(int i=0; i < screensArray->count(); i++){
        PTModelScreen* model = (PTModelScreen*)screensArray->objectAtIndex( i );
        if(model->adNetworkFullscreen().compare( adNetworkName ) == 0){
            return true;
        }
        if(model->adNetworkBanner().compare( adNetworkName ) == 0){
            return true;    
        }
    }

    return false;
}

void Java_org_cocos2dx_lib_Cocos2dxRenderer_nativeInit(JNIEnv*  env, jobject thiz, jint w, jint h){
    if (!CCDirector::sharedDirector()->getOpenGLView()){
        CCEGLView *view = CCEGLView::sharedOpenGLView();
        view->setFrameSize(w, h);
        
        //loading general info 
        PTModelController *mc = PTModelController::shared();
        mc->clean();
        mc->loadDataForClass( CCString::create("data/PTModelGeneralSettings.0.xml") );
        mc->loadDataForClass( CCString::create("data/PTModelFont.0.xml") );     
        mc->loadDataForClass( CCString::create("data/PTModelScreen.0.xml") );
        mc->loadDataForClass( CCString::create("data/PTModelObjectLabel.0.xml") );
        mc->loadConnectionsForClass(CCString::create("data/PTModelScreen.0.xml"));

        PTPAppDelegate *pAppDelegate = new PTPAppDelegate();
        CCApplication::sharedApplication()->run();

        //clean up main model controller before starting loading Objects form XML files
        mc->clean();

    }
    else {
        ccDrawInit();
        ccGLInvalidateStateCache();
        
        CCShaderCache::sharedShaderCache()->reloadDefaultShaders();
        CCTextureCache::reloadAllTextures();
        CCNotificationCenter::sharedNotificationCenter()->postNotification(EVNET_COME_TO_FOREGROUND, NULL);
        CCDirector::sharedDirector()->setGLDefaultValues(); 
    }
}

}
