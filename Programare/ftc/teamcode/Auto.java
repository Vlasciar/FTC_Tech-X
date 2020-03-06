package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import org.firstinspires.ftc.robotcore.external.JavaUtil;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import android.graphics.Color;
import android.view.View;
import android.app.Activity;

@Autonomous(name = "Auto", group = "Sensor")

public class Auto extends LinearOpMode {

    ColorSensor sensorColor;
    DistanceSensor sensorDistance;
    private DcMotor right_front;
    private DcMotor right_back;
    private DcMotor left_front;
    private DcMotor left_back;
    private CRServo take1;
    private CRServo take2;
    private CRServo claw;
    private CRServo tail;
    private ElapsedTime Counter = new ElapsedTime(); 
    private ElapsedTime RunTime = new ElapsedTime();
    int colorHSV;
    float hue;
    double distanceToTgt;
    int rotations = 0;
    
    @Override 

    public void runOpMode() {
        float hsvValues[] = {0F, 0F, 0F};
        final float values[] = hsvValues;
        final double SCALE_FACTOR = 255;
        initRobot();
        waitForStart();

        while (opModeIsActive()) 
        { 
            distanceToTgt = sensorDistance.getDistance(DistanceUnit.CM);
            telemetry.addData("Distance (cm)", sensorDistance.getDistance(DistanceUnit.CM));
            telemetry.addData("Hue", hsvValues[0]);
            //colorHSV = Color.argb(sensorColor.alpha(), sensorColor.red() , sensorColor.green() , sensorColor.blue());
            //hue = JavaUtil.colorToHue(colorHSV);
            Color.RGBToHSV((int) (sensorColor.red() * SCALE_FACTOR),
                    (int) (sensorColor.green() * SCALE_FACTOR),
                    (int) (sensorColor.blue() * SCALE_FACTOR),
                    hsvValues);
                    
        if(rotations == 0)
        {    
            telemetry.update();
        
            
        Counter.reset();
        
            while(Counter.milliseconds() <= 850)
            {
                DriveForward(.8,.8);
                telemetry.update();
            }
        rotations++;
        Counter.reset();
        while(Counter.milliseconds() <= 500)
        {
        DriveStop();
        telemetry.update();
        rotations++;
        }
        Counter.reset();
        while(Counter.milliseconds() <= 700)
        {
            RotateRight(.6,.6);
            telemetry.update();
        }
        Counter.reset();
        while(Counter.milliseconds() <= 150)
        {
            DriveForward(.5,.5);
        }
        Counter.reset();
       /* while(Counter.milliseconds() <= 500)
        {
            DriveStop();
        }*/
        }
        if(hsvValues[0] <= 98.99)
      // if(hue >= 103)
        {
            DriveForward(.25,.25);
            telemetry.update();
        }
        else {
            sleep(350);
            TakeTheCube();
            DriveStop();
           /* Counter,reset();
           while(Counter.milliseconds() <= 200)
            {
                DriveForward(.4,.4);
            }*/
            //Counter.reset();
              //   while(Counter.milliseconds() <= 870)
                // {
                  //   DriveBackward(.3,.3);
                    // telemetry.update();
                //}
                 //Counter.reset();
                /* while(Counter.milliseconds() <= 590)
                 {
                     RotateLeft(.7,.7);
                 }
                 Counter.reset();
                while(Counter.milliseconds() <= 700)
                 {
                     DriveForward(.6,.6);
                 }
                 Counter.reset();
                 while(Counter.milliseconds() <= 500)
                 {
                     DriveStop();
                 }
                 Counter.reset();
                 while(Counter.milliseconds() <= 800)
                 {
                     RotateRight(.55,.35);
                 }
                 Counter.reset();
                 while(Counter.milliseconds() <= 500)
                 {
                     DriveBackward(.5,.5);
                 }
                 Counter.reset();
                 
                 while(Counter.milliseconds() <= 400)
                 {
                     DriveForward(.7,.7);
                 }
                 Counter.reset();
                 
                while(Counter.milliseconds() <= 3000)
                 {
                     DriveStop();
                 }
                 Counter.reset();
                 while(Counter.milliseconds() <= 1000)
                 {
                     RotateLeft(.55,.55);
                 }
                 Counter.reset();
                 while(Counter.milliseconds() <= 680)
                 {
                     DriveBackward(1,1);
                 }
                 Counter.reset();
                 while(Counter.milliseconds() <= 500)
                 {
                     DriveStop();
                 
                 }*/
                 
        telemetry.update();
        
        
    }      
                   telemetry.update();
            
    }
    }
                private void initRobot(){ 
            sensorColor = hardwareMap.get(ColorSensor.class, "rev");
            sensorDistance = hardwareMap.get(DistanceSensor.class, "rev");
            right_front = hardwareMap.dcMotor.get("right_front");
            right_back = hardwareMap.dcMotor.get("right_back");
            left_front = hardwareMap.dcMotor.get("left_front");
            left_back = hardwareMap.dcMotor.get("left_back");
            take1 = hardwareMap.crservo.get("take1");
            take2 = hardwareMap.crservo.get("take2");
            claw = hardwareMap.crservo.get("claw");
            tail = hardwareMap.crservo.get("tail");
            tail.setPower(0);
            claw.setPower(-1);
        }   
                private void DriveForward(double r,double l){
            right_front.setPower(r);
            right_back.setPower(r);
            left_front.setPower(-l);
            left_back.setPower(-l);
        }
                private void DriveBackward(double r, double l){
            right_front.setPower(-r);
            right_back.setPower(-r);
            left_front.setPower(l);
            left_back.setPower(l);
        }
               private void DriveRight(double r , double l){
            right_front.setPower(-r);
            right_back.setPower(r);
            left_front.setPower(l);
            left_back.setPower(-l);        
        }
                  private void DriveLeft(double r, double l){
            right_front.setPower(r);
            right_back.setPower(-r);
            left_front.setPower(-l);
            left_back.setPower(l);
        }
                 private void RotateRight(double r, double l){
            right_front.setPower(-r);
            right_back.setPower(-r);
            left_front.setPower(-l);
            left_back.setPower(-l);
        }
            private void RotateLeft(double r , double l)
        {
            right_front.setPower(r);
            right_back.setPower(r);
            left_front.setPower(l);
            left_back.setPower(l);
        }
            private void DriveStop(){
            right_front.setPower(0);
            right_back.setPower(0);
            left_front.setPower(0);
            left_back.setPower(0);
        }
            private void TakeTheCube()
        {
            Counter.reset();
                while(Counter.milliseconds() <= 300)
                {
                    RotateRight(.15,.3);
                }
        }
}
