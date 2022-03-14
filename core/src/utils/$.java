package utils;

import com.badlogic.gdx.math.Vector2;
import com.uwsoft.editor.renderer.utils.PolygonUtils;

import java.util.*;

/**
 * Created by David on 31.05.2017.
 *
 * various utils
 */
public class $ {

    //    public static void print(String... str) {;
//        for (int i=0; i<str.length; i++)
//            System.out.print(str[i]+", ");
//        System.out.println();
//    }
    public static void print(String str) {
        System.out.println(str);
    }
    public static void print(Object o) {
        print(o.toString());
    }
    public static void print(int... intNumbers) {
        for (int i=0; i< intNumbers.length; i++)
            System.out.print("n"+intNumbers[i]+":"+intNumbers+" ");
        System.out.println();
    }
    public static void print(float... floatNumbers) {
        for (int i=0; i< floatNumbers.length; i++)
            System.out.print("n"+floatNumbers[i]+":"+floatNumbers+" ");
        System.out.println();
    }
    public static void print(String tag, Vector2[][] array) {
        System.out.print(tag+": ");
        Vector2[] verticesArray = PolygonUtils.mergeTouchingPolygonsToOne(array);
        for (int i=0; i<verticesArray.length; i++) {
            System.out.print("v"+i +": " +verticesArray[i].x +" "+ verticesArray[i].y +"  ");
        }
        System.out.println();
    }

    public static class PrintPerSecond {
        private String string;
        public PrintPerSecond(String str) {this(str, 1000);}
        public PrintPerSecond(String str, long period) {
            string = str;
            Timer timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    print("PrintPerSecond : "+string);
                }
            }, 0, 1000);
        }
    }

}
