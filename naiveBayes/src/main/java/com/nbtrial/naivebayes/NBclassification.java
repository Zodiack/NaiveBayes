package com.nbtrial.naivebayes;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.Random;

import weka.classifiers.Evaluation;
import weka.classifiers.mi.MISVM;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

/*
Here we can test other classifiers.
(Name needs to be changed)
 */
public class NBclassification extends ActionBarActivity {
    private static final String TAG = "NBClassification";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nbclassification);

        //Loading the data - training set and test set
        Instances structure = null;
        try {
            //structure = DataSource.read("/storage/sdcard/axisInvariantFeatures.arff");
            structure = DataSource.read("/storage/sdcard/features.arff");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d(TAG, "Opened the file");
        assert structure != null;
        structure.setClassIndex(structure.numAttributes() - 1);
        Log.d(TAG, "Last attribute set as class");

        // Train the class.
        // This is the place to change if we want to try new classifiers

        MISVM svm = new MISVM();
        //IBk knn = new IBk();
        // NaiveBayesUpdateable nb = new NaiveBayesUpdateable();
        Log.d(TAG, "Created a new knn class");
        try {
            svm.buildClassifier(structure);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        //Building an eval class - this class will let us evaluate the model
        Log.d(TAG, "Building an eval class");
        Evaluation eval = null;
        try {
            eval = new Evaluation(structure);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Doing the actual evaluation
        // There are numerous options here and we can get all kinds of data from the eval
        double percentage = 0;
        for(int i = 1; i <= 10; i++) {
            Log.d(TAG, "Doing the cross validation");
            if (eval != null)
                try {
                    eval.crossValidateModel(svm, structure, 10, new Random(i));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            Log.d(TAG,"The guess percentage for round " + i + " is: " + eval.pctCorrect());
            percentage += eval.pctCorrect()/10;
        }
        Log.d(TAG, "Results used for debugger issues");
        String msg = eval.toSummaryString("\nResults\n\n", false);
        Log.d(TAG, msg);
        TextView text = (TextView) findViewById(R.id.textView1);
        text.setText("Correct guess percentage is: " + percentage);
        //text.setText("Correct guess percentage is: " + Double.toString(eval.pctCorrect()));
        Log.d(TAG, "End - for now");

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nbclassification, menu);
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
