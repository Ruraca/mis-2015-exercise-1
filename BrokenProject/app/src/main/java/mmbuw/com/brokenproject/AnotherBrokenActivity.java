package mmbuw.com.brokenproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.logging.Handler;

import android.webkit.URLUtil;
import android.widget.TextView;

public class AnotherBrokenActivity extends Activity{
    private EditText editText;
    private Button button;
    private TextView bigText;
    private  AlertDialog.Builder builder,builderErrorURL,builderError;
    private boolean flag;
    private String cad,cad2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_another_broken);
        Intent intent = getIntent();
        String message = intent.getStringExtra(BrokenActivity.EXTRA_MESSAGE);
        button = (Button) findViewById(R.id.button);
        editText = (EditText) findViewById(R.id.editText);
        bigText=(TextView) findViewById(R.id.bigText);
        bigText.setMovementMethod(new ScrollingMovementMethod());
        builder = new AlertDialog.Builder(this);
        builder.setTitle("Warning");
        builder.setMessage("Error");
        builder.setPositiveButton("OK",null);
        builder.create();
        builderError= new AlertDialog.Builder(this);
        builderError.setTitle("URL Error");
        builderError.setMessage("URL incorrect, please write it again");
        builderError.setPositiveButton("OK",null);
        builderError.create();

        builderErrorURL = new AlertDialog.Builder(this);
        builderErrorURL.setTitle("URL Error");
        builderErrorURL.setMessage("URL incorrect, please write it again");
        builderErrorURL.setPositiveButton("OK",null);
        builderErrorURL.create();


        //What happens here? What is this? It feels like this is wrong.
        //Maybe the weird programmer who wrote this forgot to do something?

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.another_broken, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void fetchHTML(View view) throws IOException  {

        //According to the exercise, you will need to add a button and an EditText first.
        //Then, use this function to call your http requests
        //Following hints:
        //Android might not enjoy if you do Networking on the main thread, but who am I to judge?
        //An app might not be allowed to access the internet without the right (*hinthint*) permissions
        //Below, you find a staring point for your HTTP Requests - this code is in the wrong place and lacks the allowance to do what it wants
        //It will crash if you just un-comment it.
        if (!URLUtil.isValidUrl(editText.getText().toString())){
            builderErrorURL.show();
            flag=false;
        }else{

            new Thread(new Runnable()  {

                public void run() {


                    try {
                        HttpClient client = new DefaultHttpClient();
                        //HttpResponse response = client.execute(new HttpGet(editText.getText().toString()));
                        HttpResponse response = client.execute(new HttpGet(editText.getText().toString()));
                        StatusLine status = response.getStatusLine();
                        if (status.getStatusCode() == HttpStatus.SC_OK) {
                            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
                            response.getEntity().writeTo(outStream);
                            String responseAsString = outStream.toString();
                            //System.out.println("Response string: " + responseAsString);
                            flag=true;
                            cad=responseAsString;

                        } else {
                            //Well, this didn't work.
                            response.getEntity().getContent().close();
                            throw new IOException(status.getReasonPhrase());
                        }
                    }catch(Exception e){
                        cad2=e.toString().substring(0,50);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                builderError.setTitle("Error");
                                builderError.setMessage(cad2+"\nURL incorrect, please write it again");
                                builderError.setPositiveButton("OK",null);
                                builderError.create();
                                builderError.show();
                            }
                        });

                    }
                }


            }).start();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(flag){

                bigText.setText(cad);
                bigText.forceLayout();

            }
        }


    }

}
