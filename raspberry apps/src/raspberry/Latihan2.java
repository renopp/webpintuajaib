package raspberry;


import com.pi4j.wiringpi.Gpio;
import com.pi4j.wiringpi.SoftPwm;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author fanyr
 */
public class Latihan2 {
    //lampu menyala dari redup ke terang kembali redup perlahan
    private static int PIN_NUMBER=1;
    
    public static void main(String[] args) throws InterruptedException {
        Gpio.wiringPiSetup(); //penamaan pin tanpa menulis GPIO 1 dll
        
        SoftPwm.softPwmCreate(PIN_NUMBER, 0, 100);
        for (int i = 0; i <= 100; i++) {
            SoftPwm.softPwmWrite(PIN_NUMBER, i);
            Thread.sleep(100);
            System.out.println("Power = " + i + " %");
        }
        Thread.sleep(5000);
        for (int i = 100; i >= 0; i--) {
            SoftPwm.softPwmWrite(PIN_NUMBER, i);
            Thread.sleep(25);
            System.out.println("Power = " + i + " %");

        }
    }
}
