package com.diplomna.update;

import com.diplomna.assets.AssetManager;
import com.diplomna.assets.sub.Asset;
import com.diplomna.singleton.CurrentData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@SuppressWarnings("BusyWait")
@Component
public class UpdateRunner implements CommandLineRunner {

    @Autowired
    private final UpdateService updateService;

    public UpdateRunner(){
        updateService = new UpdateService();
    }

    @Override
    public void run(String... args) throws Exception {

        /*
            Initialize singleton class with data from Database
            Done only once, on startup
         */
        AssetManager assetManager = updateService.setupInitialAssetManager();
        CurrentData currentData = CurrentData.getInstance();
        currentData.setAssetManager(assetManager);


        /*
            update and notification thread
         */
        /*
        int i=0; //counter
        while (true) {
            //update all data from API
            updateService.updateAllAssets();
            //check notification status with the new data
            updateService.sendNotifications();
            if(i==60){
                i=0;
                //update historical data every 60 seconds
                updateService.updateHistoricalData();
            }
            try {
                //sleep for 1 second
                //good enough update frequency
                Thread.sleep(1000);
            }
            catch (InterruptedException e){
                //shouldn't get in here
                e.printStackTrace();
            }
            i++;
        }
         */

    }
}
