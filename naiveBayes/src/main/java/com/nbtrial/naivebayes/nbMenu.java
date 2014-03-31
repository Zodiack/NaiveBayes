package com.nbtrial.naivebayes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import weka.core.Instance;
import weka.core.Instances;
import weka.core.SparseInstance;
import weka.core.converters.ConverterUtils.DataSource;


public class nbMenu extends ActionBarActivity {
    private static final String LOG_TAG = "nbMenu";
    public static final String NEW="new";
    public static final String Update="update";
    public static final String Classify="classify";
    Instances TrainSet = null;
    Instances TestSet = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nb_menu);

        //Loading the data
        // This part is not really needed, but can be used for further applications.
        //Instances structure = null;
        Log.d(LOG_TAG,"Loading the training data");
        try {
            TrainSet = DataSource.read("/storage/sdcard/TrainSet.arff");
        } catch (Exception e) {
            e.printStackTrace();
        }
        TrainSet.setClassIndex(TrainSet.numAttributes() - 1);

        Log.d(LOG_TAG,"Loading the testing data");
        try {
            TestSet = DataSource.read("/storage/sdcard/TestSet.arff");
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
    //TODO: Fix The parameters as required for this function - this includes sending the instances for building the model.
    public void newNB(View view) {
        Log.d(LOG_TAG,"newNB called");
        Intent intent = new Intent(this,nbMain.class);
        Log.d(LOG_TAG,"new intent created");
        //Setting the action
        intent.setAction(NEW);
        Log.d(LOG_TAG,"Action set");
       // intent.putExtra("data",TrainSet);
       // Log.d(LOG_TAG,"Data set" + intent);
       // startService(intent);
        new nbMain().execute(intent);
        Log.d(LOG_TAG,"Service started after this");
    }

    /*
    Update an existing nb model.
    Receives the model and an instance (currently represented by an index to the instances structure)
    and updates it accordingly.
     */
    //TODO: Fix The parameters as required for this function - this includes sending the instance for classification as we need it
    public void UpdateNB(View view) {
        Log.d(LOG_TAG,"Update NB called");
        Intent intent = new Intent(this,nbMain.class);
        //This is only for getting the index used for this test.
        //In the real deal - we'll send an instance directly.
        EditText editText = (EditText) findViewById(R.id.updateIndex);
        String index = editText.getText().toString();
        //Setting the action
        intent.setAction(Update);
        intent.putExtra("index",index);
        //TODO: Here we sent an arbitrary string as the location of the model. This needs to be addressed.
        intent.putExtra("nb model","/storage/sdcard/nb.model");
        new nbMain().execute(intent);

       // startService(intent);
    }

    /*
    Classifies an instance using an existing nb model.
    Receives an instance and a nb model and returns the class of that instance.
     */
    //TODO: Fix The parameters as required for this function - this includes sending the instance for classification as we need it

    public void ClassifyNB(View view) {
        Log.d(LOG_TAG,"Classify called");
        Intent intent = new Intent(this,nbMain.class);
        //This is only for getting the index used for this test.
        //In the real deal - we'll send an instance directly.
        EditText editText = (EditText) findViewById(R.id.classifyIndex);
        String index = editText.getText().toString();
        //Setting the action
        intent.setAction(Classify);
        Log.d(LOG_TAG,"The index is:" + index);
        intent.putExtra("index", Integer.parseInt(index));
        //TODO: Here we sent an arbitrary string as the location of the model. This needs to be addressed.
        intent.putExtra("nb model","/storage/sdcard/nb.model");
        new nbMain().execute(intent);


    }

    // A function for converting double arrays into instances.
    public Instance converter(double[] data)
    {
        // Create a new sparse instance of the desired size
        Instance instance = new SparseInstance(41);
        for(int i=0;i< data.length; i++)
        {
            // Put in the data
            instance.setValue(i,data[i]);
        }
        return instance;
    }
}
