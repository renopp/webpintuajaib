/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package raspberry;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;

/**
 *
 * @author Nore
 */
public class Pintu {
    GpioController gpio;
    GpioPinDigitalInput myButton;
    
    GpioPinDigitalOutput led1;
    GpioPinDigitalOutput led2;
    GpioPinDigitalOutput led3;
}
