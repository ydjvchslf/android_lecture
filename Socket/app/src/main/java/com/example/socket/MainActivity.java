package com.example.socket;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    final static String TAG = "MIA";

    EditText input1;
    TextView output1;

    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        input1 = findViewById(R.id.input1);
        output1 = findViewById(R.id.output1);

        Button sendButton = findViewById(R.id.send);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String data = input1.getText().toString();
                //받은 data를 socket으로 전송해야 함
                // 1.쓰레드 생성, 2. UI업뎃 위해 핸들러 사용

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d(TAG, "전송 클릭 onClick 호출");

                        send(data); //socket으로 전송하는 함수 //밑에 만들어줌
                    }
                }).start();

            }
        });

        Button startServerButton = findViewById(R.id.startServerButton);
        startServerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Log.d(TAG, "서버 시작 버튼 클릭 onClick 호출");

                            startServer(); //버튼 클릭시, 서버 시작 하는 함수
                        } catch (IOException | ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

            }
        });
    }


    public void startServer() throws IOException, ClassNotFoundException {
        int port = 5001; // 서버는 포트만 지정하는 것이 일반적, 해당ip를 사용한다는 전제

        ServerSocket server = new ServerSocket(port);

        while (true){

            Log.d(TAG, "startServer() 호출");
            //서버 시작만 누르면 로그가 여기까지만 뜨는데, 그 이유는 서버만 열여줬고 client에서 소켓을 통해 요청을 따로 주지 않았기 때문
            //그래서 전송 버튼을 눌러야지만

            Socket socket = server.accept();
            InetAddress clientHost = socket.getLocalAddress();
            int clientPort = socket.getPort();

            Log.d(TAG, "startServer() 호출2222222");

            println("클라이언트 연결됨 : " + clientHost + ", " + clientPort);

            ObjectInputStream inStream = new ObjectInputStream(socket.getInputStream());
            String input = (String) inStream.readObject();
            println("데이터 받음 : " + input);

            ObjectOutputStream outStream = new ObjectOutputStream(socket.getOutputStream());
            outStream.writeObject(input + " from server" );
            outStream.flush();

            println("데이터 보냄");

            Log.d(TAG, "startServer() 33333333");

            socket.close();
        }
    }

    public void println(String data){

        handler.post(new Runnable() {
            @Override
            public void run() {

                Log.d(TAG, "println() 호출");

                output1.append(data + "\n");
            }
        });
    }


    public void send(String data){
        //포트를 지정해서 주고 받는다
        int port = 5001; // 포트 지정

        try { //예외처리를 위한 try-catch

            Log.d(TAG, "send(String data) 호출");

            Socket socket = new Socket("localhost", port);
            ObjectOutputStream outStream = new ObjectOutputStream(socket.getOutputStream());

            outStream.writeObject(data);
            outStream.flush();

            ObjectInputStream inStream = new ObjectInputStream(socket.getInputStream());
            String input = (String) inStream.readObject();
            socket.close(); //파일과 네트워킹에 있어서는 자원이 한정적이기 때문에 꼭 close() 필요

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


}