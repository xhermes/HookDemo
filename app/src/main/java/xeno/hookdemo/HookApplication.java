package xeno.hookdemo;

import android.app.Application;
import android.app.Instrumentation;
import android.content.Context;

import java.lang.reflect.Field;

/**
 * Created by xeno on 2018/8/28.
 */

public class HookApplication extends Application {
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        hook();
    }

    private static void hook() {
        //拿到当前app进程在运行的ActivityThread实例

        try {

            //ActivityThread类是private的，不能通过ActivityThread.class拿到Class
            Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");
            //拿到ActivityThread类的变量类型
            Field activityThreadField = activityThreadClass.getDeclaredField("sCurrentActivityThread");
            activityThreadField.setAccessible(true);

            //拿到ActivityThread对象，拿静态变量不用传具体对象的实例
            Object sActivityThread = activityThreadField.get(null);


            //拿到ActivityThread中Instrmentation实例
            Field instrumentationField = activityThreadClass.getDeclaredField("mInstrumentation");
            instrumentationField.setAccessible(true);
            //拿到找到的ActivityThread对象持有的Instrumentation实例
            Instrumentation orginalInstrumentation = (Instrumentation) instrumentationField.get(sActivityThread);//传入ActivityThread对象

            //使用原有Instrumentation对象创建代理
            HookInstrumentation hookIns = new HookInstrumentation(orginalInstrumentation);

            //设置sActivityThread对象持有的mInstrumentation对象为偷换以后的代理
            instrumentationField.set(sActivityThread, hookIns);

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
