package com.nbtrial.naivebayes;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import weka.classifiers.bayes.NaiveBayesUpdateable;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
class nbMain extends AsyncTask<Intent, Integer, Integer>
{
    private static final String LOG_TAG = "nbMain";
    Instances TrainSet = null;
    Instances TestSet = null;

@Override
protected void onPreExecute()
{
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
    protected Integer doInBackground(Intent... params) {
        Log.d(LOG_TAG,"nbmain on handle intent");
        // Copying the intents sent as parameters.
        Intent[] intent = params.clone();
        // Building a new nb model
        if(intent[0].getAction().equals(nbMenu.NEW))
        {
            Log.d(LOG_TAG,"Create new NBmain called");
            NaiveBayesUpdateable nb = new NaiveBayesUpdateable();
            /*
            Log.d(LOG_TAG,"Getting the data");
            Bundle data = intent[0].getExtras();
            Instances buildingData = (Instances) data.get("data");
            */
            Log.d(LOG_TAG,"Buidling a new nb model");

            try {nb.buildClassifier(TrainSet);}
            catch (Exception e) {e.printStackTrace();}

            // Here we update the model with every instance available in our set.
            Log.d(LOG_TAG,"Updating the model with every instance");
            for(int i=0; i < TrainSet.numInstances(); i++)
            {
                try {nb.updateClassifier(TrainSet.instance(i));}
                catch (Exception e) {e.printStackTrace();}
            }


            // Writing the data to the memory
            writeModel(nb);
            Log.d(LOG_TAG,"Done writing to the memory");
           }


        //Updating an existing nbModel
        if(intent[0].getAction().equals(nbMenu.Update))
        {
            Log.d(LOG_TAG,"Getting the model");
            NaiveBayesUpdateable nbModel;
            // Loading the nb model stored in our memory
            //TODO: set the address of the nb model to match the string sent.
            nbModel = loadModel(intent[0].getExtras().getString("nb model"));
            // Getting the data.
            Instance update = TestSet.instance(intent[0].getExtras().getInt("index"));

            /*
            Bundle data = intent[0].getExtras();
            NaiveBayesUpdateable nbModel = (NaiveBayesUpdateable) data.get("nb model");
            Instance update = (Instance) data.get("instance");
            */

            Log.d(LOG_TAG,"Updating the model");
            try {nbModel.updateClassifier(update);}
            catch (Exception e) {e.printStackTrace();}

            // Writing the data to the memory
            writeModel(nbModel);
            Log.d(LOG_TAG,"Done writing to the memory");
        }

        //Classifying an instance using a pre-built nb model
        if(intent[0].getAction().equals(nbMenu.Classify))
        {
            NaiveBayesUpdateable nbModel;
            Log.d(LOG_TAG,"The model is in: " + intent[0].getExtras().getString("nb model"));
            nbModel = loadModel(intent[0].getExtras().getString("nb model"));
            intent[0].getIntExtra("index",0);
            Log.d(LOG_TAG,"The index is: " + intent[0].getExtras().getInt("index"));
            Instance classify = TestSet.instance(intent[0].getExtras().getInt("index"));
            int something = intent[0].getIntExtra("index",0);
            Log.d(LOG_TAG,"The index is: " + something);


            Log.d(LOG_TAG,"Getting the data");
            /*
            Bundle data = intent[0].getExtras();
            NaiveBayesUpdateable nbModel = (NaiveBayesUpdateable) data.get("nb model");
            Instance classify = (Instance) data.get("instance");
            */
            Log.d(LOG_TAG,"Classifying the instance");
            double classification = 0;


            try {classification = nbModel.classifyInstance(classify);}
            catch (Exception e) {e.printStackTrace();}


            Log.d(LOG_TAG, "The class is:" + classification);
            //TODO: send back to the original activity.
        }
        return null;
    }

    // A method used to write the nb model to the memory for future use.
    public void writeModel(NaiveBayesUpdateable nb)
    {
        try {
            weka.core.SerializationHelper.write("/some/where/nBayes.model", nb);
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*
        Log.d(LOG_TAG,"Saving the nb model");
        ObjectOutputStream oos = null;

        try {
            oos = new ObjectOutputStream(new FileOutputStream("/storage/sdcard/nb.model"));
        }
        catch (IOException e) {e.printStackTrace();}


        try {oos.writeObject(nb);}
        catch (IOException e) {e.printStackTrace();}


        try {oos.flush();}
        catch (IOException e) {e.printStackTrace();}


        try {oos.close();}
        catch (IOException e) {e.printStackTrace();}
        */

    }

    public NaiveBayesUpdateable loadModel(String string)
    {
        NaiveBayesUpdateable nb = null;
        try {
            nb = (NaiveBayesUpdateable) weka.core.SerializationHelper.read(string);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nb;
        /*
        ObjectInputStream ois = null;
        try {ois = new ObjectInputStream( new FileInputStream(string)); }
        catch (IOException e) {e.printStackTrace();}


        NaiveBayesUpdateable nb = null;
        try {nb = (NaiveBayesUpdateable) ois.readObject();}
        catch (ClassNotFoundException e) {e.printStackTrace();}
        catch (IOException e) {e.printStackTrace();}


        try {ois.close();}
        catch (IOException e) {e.printStackTrace();}

        return nb;
        */
    }

}

/*
public abstract class nbMain extends IntentService {

    private static final String LOG_TAG = "nbMain";
    IBinder mBinder;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
/*
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

*/