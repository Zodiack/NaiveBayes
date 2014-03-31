package com.nbtrial.testingnb;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


public class timeBetweenPeaks extends ActionBarActivity {
    private static final String TAG = "TimeBetweenPeaks";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_between_peaks);
        // Arrays used for testing
        double[] array = {1.634,0.992,0.452,0.272,0.612,1.078,2.049,2.408,2.437,2.193,1.986,1.836,1.861,1.65,2.061,2.529,3.257,3.287,3.387,3.551,4.189,5.167,6.134,6.979,6.882,6.659,6.312,5.583,4.816,4.138,3.43,2.646,1.879,1.151,0.448,-0.111,-0.492,-0.835,-1.181,-1.651,-2.184,-2.775,-3.11,-3.388,-3.478,-3.498,-3.552,-3.761,-3.961,-4.141,-4.082,-3.674,-3.111,-2.836,-2.626,-2.302,-2.182,-2.025,-1.994,-1.795,-1.395,-1.341,-1.011,-0.554,-0.388,-1.587,0.139,-0.487,0.297,-0.112,-0.248,0.34,0.698,0.898,0.869,0.39,0.02,-0.251,-0.373,-0.666,-1.274,-1.477,-1.489,-1.524,-1.188,-1.338,-1.707,-1.561,-1.01,-0.459,-0.18,0.058,0.211,0.153,0.126,0.089,-0.496,-1.096,-1.832,-1.71,-1.588,-1.834,-1.454,-1.358,-0.204,0.649,0.902,0.628,0.038,-0.639,-1.151,-1.232,-0.741,-0.115,0.913,1.214,1.239,1.618,1.836,2.155,2.687,2.995,3.21,3.624,4.304,6.521,7.291,7.506,7.172,6.59,5.452,4.319,3.838,3.454,3.023,2.206,0.906,-1.01,-2.086,-2.747,-3.073,-3.085,-2.911,-2.657,-2.624,-2.374,-2.014,-1.69,-1.398,-1.479,-1.453,-0.707,0.122,-0.342,-0.455,-0.692,-1.174,-1.057,-0.89,0.449,1.47,2.623,3.378,3.175,1.668,0.165,-0.181,-0.396,0.072,1.07,1.968,2.359,1.952,1.426,1.075,0.857,0.44,-0.29,-0.616,-0.027,0.967,1.447,1.547,1.772,1.951,2.062,1.758,1.006,0.39,-1.107,-2.124,-2.72,-2.939,-2.694,-3.76,-2.158,-1.048,-0.453,-1.351,-3.251,-3.821,-3.537,-3.587,-0.507,-0.469,-0.9,-1.386,-2.142,-2.761,-2.89,-3.275,-2.445,-1.293,-0.465,0.242,0.721,0.759,1.504,2.187,3.574,4.896,5.853,6.236,5.269,3.896,2.085,-0.893,-2.103,-1.488,0.008,-0.507,-0.149,-0.233,0.783,1.219,1.134,-2.791,-4.424,-5.636,-7.169,-6.807,-6.176,-5.754,-5.473,-4.815,-6.957,-6.948,-4.5,-2.763,-1.567,1.187,2.736,-2.98,-5.587,-4.145,-0.409,1.261,1.626,1.255,0.148,-0.111,0.39,0.792,1.264,2.192};
        double[] time = {10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10};
        Log.d(TAG,"Created the arrays");
        double avgPeakTime = timeBetweenPeaks(array,time);
        Log.d(TAG,"The average time between peaks is: " + avgPeakTime);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.time_between_peaks, menu);
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

    // The actual function, works ok :)
    public double timeBetweenPeaks(double[] array,double[] time)
    {
        double result;
        double[] temp = array;
        double[] tot = cumsum(time);
        // I believe this was used in order to extract the top and buttom 5%, will leave it like this but might change it
        Log.d(TAG,"Finding out all the places that are peaks");
       // Arrays.sort(temp);
        double meanValue = mean(temp);
        for(int i = (array.length - 1); i >= 0 ; i--)
        {
            Log.d(TAG,"Currently dealing with index " + i + " value: " + array[i]);
            // Checking if a certain value is above average
            if(array[i] > meanValue)
            {
                temp[i] = 1;
            }
            else
            {
                temp[i] = 0;
            }
            // This is actually like finding the derivative.
            if((i < array.length - 1) && (temp[i] == 1) && (temp[i+1] == 1) && (i > 0))
            {
                temp[i + 1] = 0;
            }
        }

        // Dealing with a special edge case, Can it be removed?
        if(temp[0] == 1 && temp[1] == 1)
        {
            temp[0] = 0;
            temp[1] = 0;
        }
        int minIndex = -1;
        int maxIndex = -1;
        int numOfPeaks = 0;
        // Finding the number of peaks and the total distance between the first and the last peak.
        Log.d(TAG,"Finding the max and min index for the peaks");
        for(int i = 0; i <= temp.length - (i+1); i++)
        {
            // From the bottom.
            if(temp[i] == 1) {
                // WTF?!
                numOfPeaks = numOfPeaks + 1;
                if(minIndex == -1)
                    minIndex = i;
            }
            // From the top.
            // Also checking to see that we aren't on the same index and therefor counting it twice.
            if((temp.length - (i+1)) > i && (temp[temp.length - (i+1)] == 1))
            {
                // WTF?!
                numOfPeaks = numOfPeaks + 1;
                if(maxIndex == -1)
                    maxIndex = temp.length - (i+1);
            }
         //   Log.d(TAG,"The current indexes are: " + i + "and: " + (temp.length - (i+1)));
        }
        Log.d(TAG,"MinIndex: " + minIndex + "MaxIndex: " + maxIndex);
        Log.d(TAG,"Number of peaks: " + numOfPeaks);
        Log.d(TAG,"Time between peaks: " + (tot[maxIndex] - tot[minIndex]));
        result = (tot[maxIndex] - tot[minIndex]) /numOfPeaks;
        return result;
    }

    // A cumsum method
    private double[] cumsum(double[] time)
    {
        Log.d(TAG,"Sarted the cumsum");
        double[] tot = new double[time.length];
        tot[0] = time[0];
        for(int i = 1; i< time.length;i++)
            tot[i] = time[i] + tot[i-1];
        Log.d(TAG,"Finished setting the tot array");
        return tot;
    }

    // A simple mean method
    public static double mean(double[] m)
    {
        Log.d(TAG,"Calculating the mean");
        double sum = 0;
        for (int i = 0; i < m.length; i++) {
            sum += m[i];
        }
        Log.d(TAG,"The mean is: " + sum/m.length);
        return sum / m.length;
    }

}
