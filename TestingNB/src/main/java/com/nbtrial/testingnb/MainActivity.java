package com.nbtrial.testingnb;
        import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayesUpdateable;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class MainActivity extends ActionBarActivity {
    private static final String TAG = "Record for debugger";
    Instances TestSet = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Loading the data
        Log.d(TAG,"Loading the testing data");
        try {
            TestSet = DataSource.read("/storage/sdcard/TestSet.arff");
        } catch (Exception e) {
            e.printStackTrace();
        }
        TestSet.setClassIndex(TestSet.numAttributes() - 1);


       // NaiveBayesUpdateable nb = loadModel("/storage/sdcard/nb.model");
        NaiveBayesUpdateable nb = null;
        try {
            nb = (NaiveBayesUpdateable) weka.core.SerializationHelper.read("/storage/sdcard/nb.model");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d(TAG, "Opened the file");
        assert TestSet != null;
        TestSet.setClassIndex(TestSet.numAttributes() - 1);
        Log.d(TAG, "Last attribute set as class");


        Log.d(TAG, "Building an eval class");
        Evaluation eval = null;
        try {
            eval = new Evaluation(TestSet);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d(TAG, "Doing the cross validation");
        if(eval != null)
            try {
                eval.evaluateModel(nb,TestSet);
            } catch (Exception e) {
                e.printStackTrace();
            }

        Log.d(TAG, "Results used for debugger issues");
        String msg = eval.toSummaryString("\nResults\n\n", false);
        Log.d(TAG, msg);
        Log.d(TAG, "Setting the view");
        TextView text = (TextView) findViewById(R.id.textView1);
        text.setText("Correct guess percentage is: " + Double.toString(eval.pctCorrect()));
        Log.d(TAG, "End - for now");
    }


    public NaiveBayesUpdateable loadModel(String string)
    {
        Log.d(TAG, "Got into the loadmodel function");
        ObjectInputStream ois = null;
        try {ois = new ObjectInputStream( new FileInputStream(string)); }
        catch (IOException e) {e.printStackTrace();}

        Log.d(TAG, "Openned a new ois object");
        NaiveBayesUpdateable nb = null;
        try {nb = (NaiveBayesUpdateable) ois.readObject();}
        catch (ClassNotFoundException e) {e.printStackTrace();}
        catch (IOException e) {e.printStackTrace();}
        Log.d(TAG, "Got the nb model");


        try {ois.close();}
        catch (IOException e) {e.printStackTrace();}
        Log.d(TAG, "Closed the connection");

        return nb;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }

}
