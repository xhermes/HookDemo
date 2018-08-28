package xeno.hookdemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import java.lang.reflect.Field;

/**
 * Created by xeno on 2018/8/28.
 */

public class AnotherActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //尝试拿WindowManagerImpl
        try {
            Class<?> windowManagerImplClass = Class.forName("android.view.WindowManagerImpl");
            try {
                Field contextField = windowManagerImplClass.getDeclaredField("mContext");
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
