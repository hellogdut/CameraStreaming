package cn.jclive.CameraStreaming;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.Button;

import tv.inhand.capture.Session;
import tv.inhand.capture.SessionBuilder;


public class MyActivity extends Activity implements SurfaceHolder.Callback {
    private static final String TAG = "JCameara";

    private Button startStop;
    private Session session;
    private SurfaceView surfaceview;
    private SurfaceHolder surfaceHolder;
    private boolean recording;
  
    public void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.main);
        surfaceview = (SurfaceView) this.findViewById(R.id.surfaceview);
        surfaceHolder = surfaceview.getHolder();
        surfaceHolder.addCallback(this);
        // We still need this line for backward compatibility reasons with android 2
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        startStop = (Button) this.findViewById(R.id.start);
        startStop.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Button btn = (Button)v;
                if (!recording) {
                    try {
                        session = SessionBuilder.getInstance().build();
                        session.startPublisher("android001");
                        session.start();
                        recording = true;
                        btn.setText("Stop");;
                    } catch (Exception e) {
                        Log.e(TAG, "video session", e);
                    }
                }
                else {
                    if (session != null) {
                        session.stopPublisher();
                        session.stop();
                        recording = false;
                        btn.setText("Start");
                    }
                }
            }
        });
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        surfaceHolder = holder;
        try {
            SessionBuilder.getInstance()
                    // 璁剧疆涓�涓猚ontext鍙互涓嶇敤姣忔閮界洃娴婱PEG鐨勫弬鏁�                    .setContext(getApplicationContext())
                    .setSurfaceHolder(surfaceHolder)
                    .setHost("192.168.1.140")
                    .setAppName("live1/room1").build();
            ;
        } catch (Exception e) {
            Log.e(TAG, "Can't build session", e);
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
}  