package com.hudapc.iaksunshine.model;

/**
 * Created by lilmechine on 11/18/17.
 */

public class Temperature
{
     private float day;
     private float min;
     private float max;
     private float night;
     private float eve;
     private float morning;

     public float getDay() {
          return day;
     }

     public void setDay(float day) {
          this.day = day;
     }

     public float getMin() {
          return min;
     }

     public void setMin(float min) {
          this.min = min;
     }

     public float getMax() {
          return max;
     }

     public void setMax(float max) {
          this.max = max;
     }

     public float getNight() {
          return night;
     }

     public void setNight(float night) {
          this.night = night;
     }

     public float getEve() {
          return eve;
     }

     public void setEve(float eve) {
          this.eve = eve;
     }

     public float getMorning() {
          return morning;
     }

     public void setMorning(float morning) {
          this.morning = morning;
     }
}
