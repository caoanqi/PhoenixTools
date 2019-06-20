package com.hvlin.myapplication.view;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.hvlin.myapplication.MainActivity;
import com.hvlin.myapplication.R;
import com.hvlin.myapplication.databinding.ActivityUsbSocketBinding;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class UsbSocketActivity extends AppCompatActivity {

    private static final String TAG = UsbSocketActivity.class.getSimpleName();
    ActivityUsbSocketBinding activityUsbSocketBinding;
    AndroidServer server;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityUsbSocketBinding = DataBindingUtil.setContentView(this, R.layout.activity_usb_socket);
        initData();
        initListener();
    }

    private void initListener() {
        activityUsbSocketBinding.btSendData.setOnClickListener(v -> {
            Log.e(TAG, "start service");
            if (server == null) {
                server = new AndroidServer();
            }
            server.start();
        });

        activityUsbSocketBinding.btAcceptData.setOnClickListener(v -> {
            if (server != null) {
                server.setStop(false);
            }
        });
    }

    private void initData() {

    }

    public class AndroidServer extends Thread {
        private boolean stop = false;

        public void setStop(boolean stop) {
            this.stop = stop;
        }

        @Override
        public void run() {
            ServerSocket serverSocket = null;
            try {
                serverSocket = new ServerSocket(9000);
                while (!stop) {
                    Socket socket = serverSocket.accept();
                    DataInputStream inputStream = new DataInputStream(socket.getInputStream());
                    DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
                    final String msg = inputStream.readUTF();
                    Log.d(TAG, "msg: " + msg);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(UsbSocketActivity.this, msg, Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                    socket.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (serverSocket != null) {
                    try {
                        serverSocket.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


}
