package com.nbtrial.naivebayes;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import weka.classifiers.bayes.NaiveBayesUpdateable;
import weka.core.Instance;
import weka.core.Instances;


public abstract class nbMain extends IntentService {

    private static final String LOG_TAG = "nbMain";
    IBinder mBinder;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public nbMain(String name) {
        super(name);
        Log.d(LOG_TAG,"NBmain constructor called");
    }
    @Override
    public void onCreate()
    {
        Log.d(LOG_TAG, "on create called");
    }

    @Override
    public void onDestroy()
    {
        Log.d(LOG_TAG, "Destroy called");
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(LOG_TAG,"nbmain on bind");
        return mBinder;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        // Building a new nb model
        Log.d(LOG_TAG,"nbmain on handle intent");
        if(intent.getAction().equals("new"))
        {
            Log.d(LOG_TAG,"Create new NBmain called");
            NaiveBayesUpdateable nb = new NaiveBayesUpdateable();
            Log.d(LOG_TAG,"Getting the data");
            Bundle data = intent.getExtras();
            Instances buildingData = (Instances) data.get("data");
            Log.d(LOG_TAG,"Buidling a new nb model");
            try {
                nb.buildClassifier(buildingData);
            } catch (Exception e) {
                e.printStackTrace();
            }

            Log.d(LOG_TAG,"Updating the model with every instance");
            for(int i=0; i < buildingData.numInstances(); i++)
            {
                try {
                    nb.updateClassifier(buildingData.instance(i));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            // Writing the data to the memory
            writeModel(nb);
            Log.d(LOG_TAG,"Done writing to the memory");
            stopService(intent);
        }


        //Updating an existing nbModel
        if(intent.getAction().equals("update"))
        {
            Log.d(LOG_TAG,"Getting the data");
            Bundle data = intent.getExtras();
            NaiveBayesUpdateable nbModel = (NaiveBayesUpdateable) data.get("nb model");
            Instance update = (Instance) data.get("instance");
            Log.d(LOG_TAG,"Updating the model");
            try {
                nbModel.updateClassifier(update);
            } catch (Exception e) {
                e.printStackTrace();
            }
            // Writing the data to the memory
            writeModel(nbModel);
            Log.d(LOG_TAG,"Done writing to the memory");
            stopService(intent);
        }

        //Classifying an instance using a pre-built nb model
        if(intent.getAction().equals("classify"))
        {
            Log.d(LOG_TAG,"Getting the data");
            Bundle data = intent.getExtras();
            NaiveBayesUpdateable nbModel = (NaiveBayesUpdateable) data.get("nb model");
            Instance classify = (Instance) data.get("instance");
            Log.d(LOG_TAG,"Classifying the instance");
            double classification = 0;
            try {
                classification = nbModel.classifyInstance(classify);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Log.d(LOG_TAG,"The class is:" + classification);
            //TODO: send back to the original activity.
            stopService(intent);
        }
    }

    // A method used to write the nb model to the memory for future use.
    public void writeModel(NaiveBayesUpdateable nb)
    {
        Log.d(LOG_TAG,"Saving the nb model");
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(
                    new FileOutputStream("/storage/sdcard/nb.model"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            oos.writeObject(nb);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
