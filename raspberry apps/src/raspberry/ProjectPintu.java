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
import com.pubnub.api.Callback;
import com.pubnub.api.Pubnub;
import com.pubnub.api.PubnubError;
import com.pubnub.api.PubnubException;
import java.util.concurrent.Callable;

/**
 *
 * @author Salman Al Farizi
 */
public class ProjectPintu {

    static boolean stat = false;

    public static void main(String[] args) throws InterruptedException, PubnubException {
        String pubKey = "pub-c-e4956978-c469-4802-b0f0-89761d6b2e5d";
        String subKey = "sub-c-6ac3b2ea-cf31-11e6-bbe2-02ee2ddab7fe";

        Pubnub pubnub = new Pubnub(pubKey, subKey);

        final GpioController gpio = GpioFactory.getInstance();

        System.out.println("Pintu Anti Maling");
        
        //inisialisasi tombol
        final GpioPinDigitalInput myButton = gpio.provisionDigitalInputPin(RaspiPin.GPIO_00, PinPullResistance.PULL_DOWN);
        //inisialisasi variabel untuk masing2 lampu beserta kondisi awal lampu
        GpioPinDigitalOutput myLed1 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01, "LED Kuning", PinState.HIGH);
        GpioPinDigitalOutput myLed2 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_02, "LED Hijau", PinState.LOW);
        GpioPinDigitalOutput myLed3 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_03, "LED Merah", PinState.LOW);
        //kondisi menset semua lampu mati jika program berhenti
        myLed1.setShutdownOptions(true, PinState.LOW);
        myLed2.setShutdownOptions(true, PinState.LOW);
        myLed3.setShutdownOptions(true, PinState.LOW);

        //koding remote LED
        try {
            pubnub.subscribe("workshop_iot", new Callback() {
                @Override
                public void connectCallback(String channel, Object message) {
                    System.out.println("sistem subscriber terkoneksi dan aktif");
                    System.out.println("All System Functioning");
                }

                @Override
                public void disconnectCallback(String channel, Object message) {
                    System.out.println("Koneksi putus");
                }

                @Override
                public void reconnectCallback(String channel, Object message) {
                    System.out.println("Mencoba tersambung kembali");
                }

                @Override
                public void successCallback(String channel, Object message) {

                    System.out.println("Pesannya adalah : " + message);
                    if (message.toString().equalsIgnoreCase("kunci")) {
                        myLed1.setState(PinState.LOW);
                        myLed2.setState(PinState.LOW);
                        myLed3.setState(PinState.HIGH);
                        stat = true;
                        myButton.removeAllTriggers();
                    } else if (message.toString().equalsIgnoreCase("buka")) {
                        myLed1.setState(PinState.LOW);
                        myLed2.setState(PinState.HIGH);
                        myLed3.setState(PinState.LOW);
                    } else if (message.toString().equalsIgnoreCase("tutup")) {
                        myLed1.setState(PinState.HIGH);
                        myLed2.setState(PinState.LOW);
                        myLed3.setState(PinState.LOW);
                    } else if (message.toString().equalsIgnoreCase("bukakunci")) {
                        myLed1.setState(PinState.HIGH);
                        myLed2.setState(PinState.LOW);
                        myLed3.setState(PinState.LOW);
                        stat = false;
                        
                    }

                    if (stat) {
                        System.out.println("pintu terkunci");
                    } else {
                        //kondisi mengatur LED kuning mati ketika tombol ditekan
                        myButton.addTrigger(new GpioSetStateTrigger(PinState.HIGH, myLed1, PinState.LOW));
                        myButton.addTrigger(new GpioSetStateTrigger(PinState.LOW, myLed1, PinState.HIGH));
                        //kondisi mengatur LED hijau nyala ketika tombol ditekan
                        myButton.addTrigger(new GpioSetStateTrigger(PinState.HIGH, myLed2, PinState.HIGH));
                        myButton.addTrigger(new GpioSetStateTrigger(PinState.LOW, myLed2, PinState.LOW));
                        //kondisi mengatur LED merah mati di semua kondisi tombol
                        myButton.addTrigger(new GpioSetStateTrigger(PinState.LOW, myLed3, PinState.LOW));
                        myButton.addTrigger(new GpioSetStateTrigger(PinState.HIGH, myLed3, PinState.LOW));
                        //untuk menghasilkan tulisan ketika ada penekanan tombol
                        myButton.addTrigger(new GpioCallbackTrigger(() -> {
                            System.out.println("Pintu terbuka");
                            Callback callback = new Callback() {
                            };
                            pubnub.publish("workshop", "pintu terbuka", callback);
                            return null;
                        }));
                    }

                }

                @Override
                public void errorCallback(String channel, PubnubError error) {
                    System.out.println("Sistem error");
                }
            }
            );
        } catch (PubnubException e) {
            System.out.println(e.toString());
        }

    }
}
