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
import com.pi4j.io.gpio.trigger.GpioBlinkStateTrigger;
import com.pi4j.io.gpio.trigger.GpioBlinkStopStateTrigger;
import com.pi4j.io.gpio.trigger.GpioSetStateTrigger;

/**
 *
 * @author fanyr
 */
public class BlinkTriggerGPIOExample {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("<--Pi4J--> GPIO Blink Trigger Example ... started.");

        // create gpio controller
        final GpioController gpio = GpioFactory.getInstance();

        // provision gpio pin #02 as an input pin with its internal pull down resistor enabled
        final GpioPinDigitalInput myButton = gpio.provisionDigitalInputPin(RaspiPin.GPIO_02,
                                                  PinPullResistance.PULL_DOWN);
        
        final GpioPinDigitalInput myButton1 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_02,
                                                  PinPullResistance.PULL_UP);
        System.out.println(" ... complete the GPIO #02 circuit and see the blink trigger take effect.");

        // setup gpio pins #04 an output pins and make sure they are all LOW at startup
        final GpioPinDigitalOutput myLed = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_04, PinState.LOW);
        final GpioPinDigitalOutput myLed1 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01, PinState.LOW);

        // create a gpio control trigger on the input pin ; when the input goes HIGH, turn on blinking
        myButton.addTrigger(new GpioBlinkStateTrigger(PinState.HIGH, myLed, 250));
        myButton1.addTrigger(new GpioBlinkStateTrigger(PinState.HIGH, myLed1, 250));
        // create a gpio control trigger on the input pin ; when the input goes LOW, turn off blinking
        myButton.addTrigger(new GpioBlinkStopStateTrigger(PinState.LOW, myLed));
        myButton1.addTrigger(new GpioBlinkStopStateTrigger(PinState.LOW, myLed1));
        // keep program running until user aborts (CTRL-C)
        while(true) {
            Thread.sleep(500);
        }
    }
    
}
