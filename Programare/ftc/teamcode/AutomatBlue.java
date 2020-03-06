package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import org.firstinspires.ftc.robotcore.external.JavaUtil;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.CRServo;
import java.util.Locale;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import android.graphics.Color;
import android.view.View;
import android.app.Activity;

@Autonomous(name = "AutomatBlue", group = "Sensor")

public class AutomatBlue extends LinearOpMode {

   
    ColorSensor sensorColor;
    DistanceSensor sensorDistance;
    private DcMotor right_front;
    private DcMotor right_back;
    private DcMotor left_front;
    private DcMotor left_back;
    //private CRServo tail;
    private ElapsedTime Counter = new ElapsedTime(); 
    int colorHSV;
    float hue;
    double distanceToTgt;
   // double tailPow;
    int SkyStoneCounter;
    @Override 

    public void runOpMode() {
        initRobot();
        waitForStart();

        while (opModeIsActive()) 
    {   right_front.setDirection(DcMotorSimple.Direction.FORWARD);
        right_back.setDirection(DcMotorSimple.Direction.FORWARD);
        left_front.setDirection(DcMotorSimple.Direction.REVERSE);
        left_back.setDirection(DcMotorSimple.Direction.REVERSE);
       // tail.setDirection(DcMotorSimple.Direction.REVERSE);
        colorHSV = Color.argb(sensorColor.alpha(), sensorColor.red(), sensorColor.green(), sensorColor.blue());    
        hue = JavaUtil.colorToHue(colorHSV);
        distanceToTgt = sensorDistance.getDistance(DistanceUnit.CM);
      //  tailPow = tail.getPower();
        SkyStoneCounter = 0;
            telemetry.addData("Distance (cm)", sensorDistance.getDistance(DistanceUnit.CM));
            telemetry.addData("Hue", hue);
            telemetry.addData("SkyStonesCount", SkyStoneCounter );
            if ( SkyStoneCounter <= 2)
            {   
                if ((hue >= 0) && (distanceToTgt >= 4.7)){
                   // DriveForwardSlow();
                    telemetry.addData("Recognition: ","No stone/skystone found" );
                }
                 /*else if (((hue <= 75) && (hue >= 1)) && ((distanceToTgt <= 4.69) && (distanceToTgt >= 2.8))){
                    DriveForwardSlow();
                    telemetry.addData("Recognition: ","Stone found" );
                }*/
                else if ((hue <= 75) && (hue >= 1) && (distanceToTgt <= 4.69)){
                    //DriveLeftSlow();
                    telemetry.addData("Recognition: ","Stone found at distance limit" );
                    
                }
                else if(((hue <= 1) || ((hue >= 85) && (hue <= 150))) && (distanceToTgt <= 4.69)){
                    telemetry.addData("Recognition: ","SkyStone found");
                    while(Counter.milliseconds() <= 20)
                    {
                         DriveLeftSlow();
                    }
                    Counter.reset();
                   // DriveStop();
                    while (Counter.milliseconds() <= 50)
                    {
                       // TailCLOSE();
                       // DriveBackward();
                    }
                    Counter.reset();
                    while (Counter.milliseconds() <= 5000)
                    {
                       // DriveRightFast();
                    }
                    Counter.reset();  
                    if (SkyStoneCounter < 1  )
                     {
                        while (Counter.milliseconds() <= 7000) 
                        {
                           // TailOpen();
                           // DriveLeftFast();
                        }
                        Counter.reset();
                        SkyStoneCounter++;
                        while (Counter.milliseconds() <= 20)
                        {
                            //DriveForward();
                        }
                        Counter.reset();
                    }
                    else
                    {
                      //  TailOpen();
                        SkyStoneCounter++;
                    }
                }
                telemetry.update();
                if (SkyStoneCounter == 2)
                {
                    while (Counter.milliseconds() <= 1000)
                    {
                       // DriveLeft();
                    }
                   // DriveStop();
                }
                telemetry.update();
            }      
    }
    }
                private void initRobot(){ 
            sensorColor = hardwareMap.get(ColorSensor.class, "rev");
            sensorDistance = hardwareMap.get(DistanceSensor.class, "rev");
            right_front = hardwareMap.dcMotor.get("right_front");
            right_back = hardwareMap.dcMotor.get("right_back");
            left_front = hardwareMap.dcMotor.get("left_front");
            left_back = hardwareMap.dcMotor.get("left_back");
           // tail = hardwareMap.crservo.get("tail");
        }   
                private void DriveForward(){
            right_front.setPower(.6);
            right_back.setPower(.6);
            left_front.setPower(.6);
            left_back.setPower(.6);
        }
                private void DriveForwardSlow(){
            right_front.setPower(.3);
            right_back.setPower(.3);
            left_front.setPower(.3);
            left_back.setPower(.3);
        }
                private void DriveBackward(){
            right_front.setPower(.6);
            right_back.setPower(.6);
            left_front.setPower(-.6);
            left_back.setPower(-.6);
        }
                private void DriveRightSlow(){
            right_front.setPower(-.3);
            right_back.setPower(.3);
            left_front.setPower(.3);
            left_back.setPower(-.3);
        }
                private void DriveRight(){
            right_front.setPower(-.6);
            right_back.setPower(.6);
            left_front.setPower(.6);
            left_back.setPower(-.6);        
        }
                private void DriveRightFast(){
            right_front.setPower(-1);
            right_back.setPower(1);
            left_front.setPower(1);
            left_back.setPower(-1);
        }
                  private void DriveLeft(){
            right_front.setPower(.6);
            right_back.setPower(-.6);
            left_front.setPower(-.6);
            left_back.setPower(.6);
        }
                private void DriveLeftFast(){
            right_front.setPower(1);
            right_back.setPower(-1);
            left_front.setPower(-1);
            left_back.setPower(1);
        }
                private void DriveLeftSlow(){
            right_front.setPower(.3);
            right_back.setPower(-.3);
            left_front.setPower(-.3);
            left_back.setPower(.3);
        }
        
              //  private void TailCLOSE(){
           // tail.setPower(1);
       // }       
          //      private void TailOpen(){
           // tail.setPower(0);
      
        //}
                private void DriveStop(){
            right_front.setPower(0);
            right_back.setPower(0);
            left_front.setPower(0);
            left_back.setPower(0);
        }
}
