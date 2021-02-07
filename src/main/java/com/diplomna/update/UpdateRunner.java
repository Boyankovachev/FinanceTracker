package com.diplomna.update;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class UpdateRunner implements CommandLineRunner {

    @Autowired
    private UpdateService updateService;

    public UpdateRunner(){
        updateService = new UpdateService();
    }

    @Override
    public void run(String... args) throws Exception {

        /*
        //free api limitations - 500 requests per month (YahooFinance free subscription sucks)
        //31*24*60*60*1000 - milliseconds in a month = a
        //wait time = a/500 = 5356800 milliseconds to wait between updating all assets ;(

        !не е взето в предвид брой акции за които трябва да бъде извикано api'то, така че
        реално няма да може да се обновява цената толкова пъти месечно,
        но примаме че сървъра няма да си стои пуснат постоянно!
         */
        /*
        while (true) {
            updateService.updateAllAssets();
            updateService.sendNotifications();
            try {
                Thread.sleep(5356800);
            }
            catch (InterruptedException e){
                //shouldn't get in here
                e.printStackTrace();
            }

        }
        */
        System.out.println("in update thread");

    }
}
