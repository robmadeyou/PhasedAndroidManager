package github.robmadeyou.phasedmanager;

import android.os.Bundle;
import android.os.Looper;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        final TextView t = (TextView) findViewById( R.id.text );

        new Thread(new Runnable() {
            @Override
            public void run() {
                while( true )
                {
                    try
                    {
                        Thread.sleep( 1000 );
                    }
                    catch ( Exception ex )
                    {}
                    final String stuff = MainActivity.getStuff();
                    t.post(new Runnable() {
                        @Override
                        public void run() {
                            t.setText(stuff);
                        }
                    });
                }
            }
        }).start();

    }

    static Socket sock;

    public static String getStuff ()
    {
        try
        {
            sock = new Socket ( "192.168.0.44", 4412 );
            PrintWriter os = new PrintWriter(sock.getOutputStream(), true);
            os.println ( "uptime" );
            BufferedReader is = new BufferedReader(
                    new InputStreamReader( sock.getInputStream () ) );
            String fromServer;
            while ((fromServer = is.readLine()) != null)
            {
                return fromServer;
            }
            is.close ();
            os.close ();
        } catch ( Exception ex )
        {
            return ex.getMessage();
        }
        return "look at all the pepple";
    }

    public static String combine (String[] s, String glue)
    {
        int k = s.length;
        if ( k == 0 )
        {
            return "";
        }
        StringBuilder out = new StringBuilder ();
        out.append(s[0]);
        for ( int x = 1; x < k; ++x )
        {
            out.append ( glue ).append ( s[ x ] );
        }
        return out.toString ();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
