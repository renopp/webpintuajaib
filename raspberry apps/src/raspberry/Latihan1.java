/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package raspberry;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
/**
 *
 * @author fanyr
 */
public class Latihan1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {
        System.out.println("<--Pi4J--> GPIO Control Example ... started.");

        // create gpio controller
        final GpioController gpio = GpioFactory.getInstance(); //GPIOController = mengenali pin2 pada raspberry

        // provision gpio pin #01 as an output pin and turn on
        final GpioPinDigitalOutput pin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01, "MyLED", PinState.HIGH); //Membuat output

        // set shutdown state for this pin
        pin.setShutdownOptions(true, PinState.LOW);
        
//        pin.blink(1000); //lampu kedip2
//        int jumlah = 0;
//        for (int i = 0; i < 20; i++) {
//            //System.out.println("--> Keadaan GPIO harus: NYALA");
//            //Thread.sleep(500);
//            // turn off gpio pin #01
//            //pin.low();
//            //System.out.println("--> Keadaan GPIO harus: MATI");
//
//            //Thread.sleep(500);
//
//            // toggle the current state of gpio pin #01 (should turn on)
//            pin.toggle();
//            System.out.println("--> Keadaan GPIO harus: NYALA");
//
//            Thread.sleep(500); //1/5 detik menahan selama 5 detik
//
//            // toggle the current state of gpio pin #01  (should turn off)
//            pin.toggle();
//            System.out.println("--> Keadaan GPIO harus: MATI");
//
//            Thread.sleep(500); //1/5 detik
//            jumlah++;
//            
//            //pin.low = mematikan nyala lampu
//        }
        
//        looping forever
        while(true){
            Thread.sleep(100);
        }
        // turn on gpio pin #01 for 1 =second and then off
//        System.out.println("LED nyala "+jumlah+" kali");
        //System.out.println("--> Keadaan GPIO harus: NYALA for only 1 second");
//        pin.pulse(500, true); // set second argument to 'true' use a blocking call = menambah pulsa selama 5 detik 

        // stop all GPIO activity/threads by shutting down the GPIO controller
        // (this method will forcefully shutdown all GPIO monitoring threads and scheduled tasks)
//        gpio.shutdown(); //seluruh service GPIO dimatikan

//        System.out.println("Exiting ControlGpioExample");
    }
    
}
