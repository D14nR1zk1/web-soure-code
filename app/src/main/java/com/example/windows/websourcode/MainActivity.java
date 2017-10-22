package com.example.windows.websourcode;

import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {

    TextView viewt;
    Spinner spinner;
    Button bget;
    EditText edit;
    public String url = null;
    ProgressBar pbload;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewt = (TextView)findViewById(R.id.viewtext);
        spinner =(Spinner)findViewById(R.id.spin);
        bget = (Button)findViewById(R.id.button);
        edit = (EditText)findViewById(R.id.input);
        pbload = (ProgressBar)findViewById(R.id.load);

        pbload.setVisibility(View.GONE);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.Spinner, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        bget.setOnClickListener(new View.OnClickListener(){
            @Override

            public void onClick(View view){
                url = spinner.getSelectedItem() + edit.getText().toString();
                boolean valid = Patterns.WEB_URL.matcher(url).matches();
                if (valid){
                    getSupportLoaderManager().restartLoader(0, null, MainActivity.this);
                    pbload.setVisibility(View.VISIBLE);
                    viewt.setVisibility(View.GONE);
                } else {
                    Loader loader = getSupportLoaderManager().getLoader(0);
                    if (loader != null) {
                        loader.cancelLoad();
                    }
                    pbload.setVisibility(View.GONE);
                    viewt.setVisibility(View.VISIBLE);
                    viewt.setText("This Website is not Valid");
                }
            }
        });
    }
    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoaderWeb(this, url);
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
        pbload.setVisibility(View.GONE);
        viewt.setVisibility(View.VISIBLE);
        viewt.setText(data);
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

    }
}
