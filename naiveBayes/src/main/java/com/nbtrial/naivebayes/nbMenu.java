package com.nbtrial.naivebayes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import weka.classifiers.bayes.NaiveBayesUpdateable;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;


public class nbMenu extends ActionBarActivity {
    private static final String LOG_TAG = "nbMenu";
    public final static String TAG = "com.nbtrial.naivebayes.MESSAGE";
    Instances TrainSet = null;
    Instances TestSet = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nb_menu);

        //Loading the data
        //Instances structure = null;
        Log.d(LOG_TAG,"Loading the training data");
        try {
            TrainSet = ConverterUtils.DataSource.read("/storage/sdcard/TrainSet.arff");
        } catch (Exception e) {
            e.printStackTrace();
        }
        TrainSet.setClassIndex(TrainSet.numAttributes() - 1);

        Log.d(LOG_TAG,"Loading the testing data");
        try {
            TestSet = ConverterUtils.DataSource.read("/storage/sdcard/TestSet.arff");
        } catch (Exception e) {
            e.printStackTrace();
        }
        TestSet.setClassIndex(TestSet.numAttributes() - 1);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nb_menu, menu);
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


    /*
    Create a new nb model.
    Required - data to be sent to build a new nb in the form of instances
     */
    public void newNB(View view) {
        Log.d(LOG_TAG,"newNB called");
        Intent intent = new Intent(this,nbMain.class);
        Log.d(LOG_TAG,"new intent created");
        //Setting the action
        intent.setAction("new");
        Log.d(LOG_TAG,"Action set");
        intent.putExtra("data",TrainSet);
        Log.d(LOG_TAG,"Data set" + intent);
        startService(intent);
        Log.d(LOG_TAG,"Service started after this");
    }

    /*
    Update an existing nb model.
    Receives the model and an instance (currently represented by an index to the instances structure)
    and updates it accordingly.
     */
    //TODO: Fix the nb parameter
    public void UpdateNB(View view,NaiveBayesUpdateable nb) {
        Log.d(LOG_TAG,"Update NB called");
        Intent intent = new Intent(this,nbMain.class);
        //This is only for getting the index used for this test.
        //In the real deal - we'll send an instance directly.
        EditText editText = (EditText) findViewById(R.id.updateIndex);
        String index = editText.getText().toString();
        //Setting the action
        intent.setAction("update");
        intent.putExtra("instance",TestSet.instance(Integer.parseInt(index)));
        intent.putExtra("nb model",nb);
        startService(intent);
    }

    /*
    Classifies an instance using an existing nb model.
    Receives an instance and a nb model and returns the class of that instance.
     */
    //TODO: Fix the nb parameter
    public void ClassifyNB(View view,NaiveBayesUpdateable nb) {
        Log.d(LOG_TAG,"Classify called");
        Intent intent = new Intent(this,nbMain.class);
        //This is only for getting the index used for this test.
        //In the real deal - we'll send an instance directly.
        EditText editText = (EditText) findViewById(R.id.classifyIndex);
        String index = editText.getText().toString();
        //Setting the action
        intent.setAction("classify");
        intent.putExtra("instance",TestSet.instance(Integer.parseInt(index)));
        intent.putExtra("nb model",nb);
        startService(intent);
    }
}
