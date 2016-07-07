package mawashi.alex.sarjoystick;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    ImageView joyP;


    String Indirizzo = "";
    String Funzione = "";
    String VelInterMAX = "";
    String VelInterMIN = "";
    String Messaggio="";
    int FingerOn=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        Indirizzo = sharedPreferences.getString("Indirizzo",""); //"TAG", "DEFAULT_VALUE"
        Funzione = sharedPreferences.getString("Funzione","");
        VelInterMAX = sharedPreferences.getString("VelInterMAX","");
        VelInterMIN = sharedPreferences.getString("VelInterMIN","");
        joyP = (ImageView) findViewById(R.id.joystickView);



        findViewById(R.id.joystickLayout).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                int X = (int) event.getX();
                int Y = (int) event.getY();

                //Toast.makeText(getApplicationContext(),"X: " + X + ", Y: " + Y,Toast.LENGTH_LONG).show();
                int ViewSize = 590;
                int x=0;
                int y=0;

                if(X>2*ViewSize/3)
                    x=3;
                else if(X>ViewSize/3)
                    x=2;
                else
                    x=1;

                if(Y>2*ViewSize/3)
                    y=3;
                else if(Y>ViewSize/3)
                    y=2;
                else
                    y=1;

                if(x==1 && y==1){
                    try {
                        joyP.setImageResource(R.drawable.joystick_up_left);
                        if(FingerOn != 1) {
                            FingerOn = 1;
                            Messaggio = "AMOVE " + VelInterMIN + " " + VelInterMAX;
                            new AsyncHTTPPOST().execute();
                        }
                    }catch(Exception e){Log.e("ERRORJOYSTICK","ERRORJOYSTICK: " + e.toString());}
                }else if(x==1 && y==2){
                    try {
                        joyP.setImageResource(R.drawable.joystick_left);
                        if(FingerOn != 4) {
                            FingerOn = 4;
                            Messaggio = "L";
                            new AsyncHTTPPOST().execute();
                        }
                    }catch(Exception e){Log.e("ERRORJOYSTICK","ERRORJOYSTICK: " + e.toString());}
                }else if(x==1 && y==3){
                    try {
                        joyP.setImageResource(R.drawable.joystick_down_left);
                        if(FingerOn != 7) {
                            FingerOn = 7;
                            Messaggio = "AMOVE -" + VelInterMIN + " -" + VelInterMAX;
                            new AsyncHTTPPOST().execute();
                        }
                    }catch(Exception e){Log.e("ERRORJOYSTICK","ERRORJOYSTICK: " + e.toString());}
                }else if(x==2 && y==1){
                    try {
                        joyP.setImageResource(R.drawable.joystick_up);
                        if(FingerOn != 2) {
                            FingerOn = 2;
                            Messaggio = "F";
                            new AsyncHTTPPOST().execute();
                        }
                    }catch(Exception e){Log.e("ERRORJOYSTICK","ERRORJOYSTICK: " + e.toString());}
                }else if(x==2 && y==2){
                    try {
                        joyP.setImageResource(R.drawable.joystick);
                        if(FingerOn != 5) {
                            FingerOn = 5;
                            Messaggio = "STOP";
                            new AsyncHTTPPOST().execute();
                        }
                    }catch(Exception e){Log.e("ERRORJOYSTICK","ERRORJOYSTICK: " + e.toString());}
                }else if(x==2 && y==3){
                    try {
                        joyP.setImageResource(R.drawable.joystick_down);
                        if(FingerOn != 8) {
                            FingerOn = 8;
                            Messaggio = "B";
                            new AsyncHTTPPOST().execute();
                        }
                    }catch(Exception e){Log.e("ERRORJOYSTICK","ERRORJOYSTICK: " + e.toString());}
                }else if(x==3 && y==1){
                    try {
                        joyP.setImageResource(R.drawable.joystick_up_right);
                        if(FingerOn != 3) {
                            FingerOn = 3;
                            Messaggio = "AMOVE " + VelInterMAX + " " + VelInterMIN;
                            new AsyncHTTPPOST().execute();
                        }
                    }catch(Exception e){Log.e("ERRORJOYSTICK","ERRORJOYSTICK: " + e.toString());}
                }else if(x==3 && y==2){
                    try {
                        joyP.setImageResource(R.drawable.joystick_right);
                        if(FingerOn != 6) {
                            FingerOn = 6;
                            Messaggio = "R";
                            new AsyncHTTPPOST().execute();
                        }
                    }catch(Exception e){Log.e("ERRORJOYSTICK","ERRORJOYSTICK: " + e.toString());}
                }else if(x==3 && y==3){
                    try {
                        joyP.setImageResource(R.drawable.joystick_down_right);
                        if(FingerOn != 9) {
                            FingerOn = 9;
                            Messaggio = "AMOVE -" + VelInterMAX + " -" + VelInterMIN;
                            new AsyncHTTPPOST().execute();
                        }
                    }catch(Exception e){Log.e("ERRORJOYSTICK","ERRORJOYSTICK: " + e.toString());}
                }

                int eventaction = event.getAction();

                switch (eventaction) {
                    case MotionEvent.ACTION_DOWN:

                        try {

                            //joyP.setImageResource(R.drawable.staedyleft);
                            //   LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(ninjaSprite.getWidth(), ninjaSprite.getHeight());
                            //   layoutParams.setMargins(X, Y, 0, 0);
                            //   ninjaSprite.setLayoutParams(layoutParams);
                        }catch(Exception e){
                            Log.d("COLORE", "COLORE INESISTENTE");
                        }
                        // Toast.makeText(MainActivity.this, "X: "+X+" Y: "+Y, Toast.LENGTH_SHORT).show();
                        // Log.d("MOTION DOWN", "MOTION DOWN " + X + ", " + Y);
                        // isTouch = true;
                        break;

                    case MotionEvent.ACTION_MOVE:
                        // Toast.makeText(MainActivity.this, "MOVE "+"X: "+X+" Y: "+Y, Toast.LENGTH_SHORT).show();
                        //joyP.setImageResource(R.drawable.staedyupright);
                        break;

                    case MotionEvent.ACTION_UP:
                        // Toast.makeText(MainActivity.this, "ACTION_UP "+"X: "+X+" Y: "+Y, Toast.LENGTH_SHORT).show();
                        Log.d("MOTION UP", "MOTION UP " + X + ", " + Y);
                        joyP.setImageResource(R.drawable.joystick);
                        FingerOn = 0;
                        Messaggio = "STOP";
                        new AsyncHTTPPOST().execute();

                        break;
                }
                return true;
            }
        });


    } //end of onCreate()

    public void Preferenze(View v){
        // custom dialog
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.dialog_preferences);    //il suo layout personalizzato
        dialog.setTitle("Preferenze");
        Display display =((WindowManager)getSystemService(getApplicationContext().WINDOW_SERVICE)).getDefaultDisplay();
        int width = display.getWidth();
        int height=display.getHeight();
        dialog.getWindow().setLayout(width,height);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // set the custom dialog components - text, image and button
        final EditText IndirizzoEdit = (EditText) dialog.findViewById(R.id.indirizzoEdit);   //notare il dialog.
        final EditText FunzioneEdit = (EditText) dialog.findViewById(R.id.funzioneEdit);
        final EditText VelInterMAXEdit = (EditText) dialog.findViewById(R.id.velIntermMAXEdit);
        final EditText VelInterMINEdit = (EditText) dialog.findViewById(R.id.velIntermMINEdit);

        IndirizzoEdit.setText(Indirizzo);
        FunzioneEdit.setText(Funzione);
        VelInterMAXEdit.setText(VelInterMAX);
        VelInterMINEdit.setText(VelInterMIN);

        Button dialogButton = (Button) dialog.findViewById(R.id.SavePreferencesButton);
            // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Indirizzo   = IndirizzoEdit.getText().toString();
                Funzione    = FunzioneEdit.getText().toString();
                VelInterMAX = VelInterMAXEdit.getText().toString();
                VelInterMIN = VelInterMINEdit.getText().toString();


                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("Indirizzo", Indirizzo);
                editor.putString("Funzione", Funzione);
                editor.putString("VelInterMAX", VelInterMAX);
                editor.putString("VelInterMIN", VelInterMIN);
                editor.commit();
                Toast.makeText(getApplicationContext(),"Preferenze salvate",Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        });
        dialog.show();
    }


    private class AsyncHTTPPOST extends AsyncTask<Void,Void,Void> {
        String result = "";

        @Override
        protected void onPreExecute(){}

        @Override
        protected Void doInBackground(Void...params){
            //Operazioni da fare in background
            try{
                HttpPost post = new HttpPost(Indirizzo);
                HttpClient client = new DefaultHttpClient();
                StringEntity se = new StringEntity(Messaggio);
                se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/string"));
                post.setEntity(se);
                HttpResponse response = client.execute(post);
                result = EntityUtils.toString(response.getEntity());
            }catch(Exception e){
                Log.e("POST EXC", "POST EXCEPTION: " + e.toString());
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result){
            super.onPostExecute(result);

        }
    }
}





