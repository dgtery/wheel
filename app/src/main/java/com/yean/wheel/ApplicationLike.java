package com.yean.wheel;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Environment;
import android.support.multidex.MultiDex;
import android.util.Log;
import android.widget.Toast;

import com.tencent.tinker.anno.DefaultLifeCycle;
import com.tencent.tinker.lib.tinker.Tinker;
import com.tencent.tinker.lib.tinker.TinkerInstaller;
import com.tencent.tinker.lib.util.UpgradePatchRetry;
import com.tencent.tinker.loader.app.DefaultApplicationLike;
import com.tencent.tinker.loader.shareutil.ShareConstants;

import java.io.File;

/**
 * Created by Administrator on 2017-11-6.
 */
@DefaultLifeCycle(
        application = "com.yean.wheel.Application",             //application name to generate
        flags = ShareConstants.TINKER_ENABLE_ALL,
        loadVerifyFlag = false)                                //tinkerFlags above
public class ApplicationLike extends DefaultApplicationLike {
    public ApplicationLike(Application application, int tinkerFlags, boolean tinkerLoadVerifyFlag, long applicationStartElapsedTime, long applicationStartMillisTime, Intent tinkerResultIntent) {
        super(application, tinkerFlags, tinkerLoadVerifyFlag, applicationStartElapsedTime, applicationStartMillisTime, tinkerResultIntent);
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    public void onBaseContextAttached(Context base) {
        super.onBaseContextAttached(base);
        MultiDex.install(base);
        String patchPath  = Environment.getExternalStorageDirectory().getAbsolutePath() + "/tencent/QQfile_recv/patch_signed_7zip.apk";
        Log.v("ApplicationLike",patchPath);
        File file = new File(patchPath);
        Log.v("ApplicationLike","file is exist?"+file.exists());
        if (file.exists()) {
            TinkerInstaller.onReceiveUpgradePatch(getApplication(), patchPath);
            file.delete();
            Toast.makeText(getApplication(),"更新成功",Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplication(),"不需要更新",Toast.LENGTH_LONG).show();
        }
    }
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public void registerActivityLifecycleCallbacks(Application.ActivityLifecycleCallbacks callback) {
        getApplication().registerActivityLifecycleCallbacks(callback);
    }
}
