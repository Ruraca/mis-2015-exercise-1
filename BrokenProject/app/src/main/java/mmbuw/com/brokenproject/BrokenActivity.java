package mmbuw.com.brokenproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;

import java.io.IOException;


public class BrokenActivity extends Activity {

    public final static String EXTRA_MESSAGE = "CUIDADOR EXTRA MESSAGE";
    private EditText auntEdith;
    private Button boton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broken);
        boton = (Button) findViewById(R.id.boton);
        auntEdith = (EditText) findViewById(R.id.edittext);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.broken, menu);
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

    public void brokenFunction(View view)throws IOException{

        //I was once, perhaps, possibly a functioning function
        if (auntEdith.getText().toString().equals("Timmy")) {
            System.out.println("Timmy fixed a bug!");


            System.out.println("If this appears in your console, you fixed a bug.");
            Intent intent = new Intent(this, AnotherBrokenActivity.class);
            String message = "This string will be passed to the new activity";
            startActivity(intent);

        }
    }
}
