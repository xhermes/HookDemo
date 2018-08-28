package xeno.hookdemo;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by xeno on 2018/8/28.
 */

public class HookInstrumentation extends Instrumentation {

    public HookInstrumentation(Instrumentation base) {
        this.mBase = base;
    }

    private Instrumentation mBase;

    public ActivityResult execStartActivity(
            Context who, IBinder contextThread, IBinder token, Activity target,
            Intent intent, int requestCode, Bundle options) {
        Log.d("xeno", "\n执行了startActivity, 参数如下: \n" + "who = [" + who + "], " +
                "\ncontextThread = [" + contextThread + "], \ntoken = [" + token + "], " +
                "\ntarget = [" + target + "], \nintent = [" + intent +
                "], \nrequestCode = [" + requestCode + "], \noptions = [" + options + "]");


        //调用原来的execStartActivity实现开启Activity功能
        //隐藏方法，需要使用反射调用
        try {
            Method execStartActivityMethod = Instrumentation.class.getDeclaredMethod("execStartActivity",
                    Context.class, IBinder.class, IBinder.class, Activity.class, Intent.class, int.class, Bundle.class);
            execStartActivityMethod.setAccessible(true);

            ActivityResult ar = (ActivityResult) execStartActivityMethod.invoke(mBase, who, contextThread, token, target, intent, requestCode, options);
            return ar;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;

    }
}
