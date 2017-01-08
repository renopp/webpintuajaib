/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package raspberry;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.trigger.GpioCallbackTrigger;
import com.pi4j.io.gpio.trigger.GpioSetStateTrigger;
import java.util.concurrent.Callable;

/**
 *
 * @author fanyr
 */
public class pemrog2 {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Praktikum Penggunaan Tombol");
        final GpioController gpio = GpioFactory.getInstance();
        final GpioPinDigitalInput myButton = gpio.provisionDigitalInputPin(RaspiPin.GPIO_02, PinPullResistance.PULL_DOWN);
        System.out.println(" Sistem siap ");
        GpioPinDigitalOutput myLed[] = {gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01, "LED #1", PinState.LOW)};
        myButton.addTrigger(new GpioSetStateTrigger(PinState.HIGH, myLed[0], PinState.HIGH));
        myButton.addTrigger(new GpioSetStateTrigger(PinState.LOW, myLed[0], PinState.LOW));
        myButton.addTrigger(new GpioCallbackTrigger(new Callable<Void>() {
            public Void call() throws Exception {
                System.out.println(" --> ada penekanan tombol");
                return null;
            }
        }));
        while (true) {
            Thread.sleep(500);
        }
    }
}
