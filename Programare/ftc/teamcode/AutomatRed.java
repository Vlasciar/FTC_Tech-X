package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import org.firstinspires.ftc.robotcore.external.navigation.Rotation;
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

@Autonomous(name = "AutomatRed", group = "Sensor")

public class AutomatRed extends LinearOpMode {

   
    ColorSensor sensorColor;
    DistanceSensor sensorDistance;
    private DcMotor right_front;
    private DcMotor right_back;
    private DcMotor left_front;
    private DcMotor left_back;
    private CRServo tail;
    private ElapsedTime Counter = new ElapsedTime(); 
    private ElapsedTime RunTime = new ElapsedTime();
    int colorHSV;
    float hue;
    double distanceToTgt;
    int SkyStoneCounter;
    @Override 

    public void runOpMode() {
        initRobot();
        waitForStart();

        while (opModeIsActive()) 
    {  while (RunTime.milliseconds() <= 29900)
       { right_front.setDirection(DcMotorSimple.Direction.FORWARD);
        right_back.setDirection(DcMotorSimple.Direction.FORWARD);
        left_front.setDirection(DcMotorSimple.Direction.REVERSE);
        left_back.setDirection(DcMotorSimple.Direction.REVERSE);
        tail.setDirection(DcMotorSimple.Direction.REVERSE);
        colorHSV = Color.argb(sensorColor.alpha(), sensorColor.red(), sensorColor.green(), sensorColor.blue());    
        hue = JavaUtil.colorToHue(colorHSV);
        distanceToTgt = sensorDistance.getDistance(DistanceUnit.CM);
       
            telemetry.addData("Distance (cm)", sensorDistance.getDistance(DistanceUnit.CM));
            telemetry.addData("Hue", hue);
            telemetry.addData("SkyStonesCount", SkyStoneCounter );
           if ( SkyStoneCounter <= 2)
            {   
                if ((hue >= 90) && (distanceToTgt >= 7.2)){
                    DriveForward();
                    telemetry.addData("Recognition: ","No stone/skystone found" );
                    telemetry.update();
                }
                 else if (((hue <= 139) && (hue >= 70 )) && ((distanceToTgt >= 5.1) && (distanceToTgt <= 7.2)))
                 {
                    
                    telemetry.addData("Recognition: ","Stone found" );
                    telemetry.update();
                    
                }
                else if ((hue <= 119) && (hue >= 70) && (distanceToTgt <= 5)){
                    DriveRight();
                    telemetry.addData("Recognition: ","Stone found at limit" );
                    telemetry.update();
                     
                }
                 else if(((hue == 0) && (distanceToTgt <= 5)) || ((hue == 120) &&
                 (distanceToTgt <= 5)) || (hue == 85) &&(distanceToTgt <= 5 ))
                 {
                    telemetry.addData("Recognition: ","SkyStone found");
                    while(Counter.milliseconds() <= 50)
                    {
                        DriveStop();
                    }
                    Counter.reset();
                    while (Counter.milliseconds() <= 100)
                    {
                    DriveStop();
                    }
                    Counter.reset();
                    TailCLOSE();
                    sleep(2500);
                    telemetry.update();
                    while (Counter.milliseconds() <= 5)
                    {
                        DriveBackward();
                    }
                    Counter.reset();
                   
                    while (Counter.milliseconds() <= 5000)
                    {
                        DriveLeftFast();
                    }
                    Counter.reset();  
                    if (SkyStoneCounter < 1  )
                     {
                         TailOpen();
                         SkyStoneCounter++;
                         telemetry.update();
                        while (Counter.milliseconds() <= 7000) 
                        {
                            
                            DriveRightFast();
                        }
                        Counter.reset();
                        while (Counter.milliseconds() <= 20)
                        {
                            DriveForward();
                        }
                        Counter.reset();
                    }
                    else
                    {
                        TailOpen();
                        SkyStoneCounter++;
                    }
                }
                telemetry.update();
                if (SkyStoneCounter == 2)
                {
                    while (Counter.milliseconds() <= 1000)
                    {
                        DriveRight();
                    }
                    DriveStop(); 
                    break;
                }
                telemetry.update();
            
        telemetry.update();
                 }
       
    }}
    }
                private void initRobot(){ 
            sensorColor = hardwareMap.get(ColorSensor.class, "rev");
            sensorDistance = hardwareMap.get(DistanceSensor.class, "rev");
            right_front = hardwareMap.dcMotor.get("right_front");
            right_back = hardwareMap.dcMotor.get("right_back");
            left_front = hardwareMap.dcMotor.get("left_front");
            left_back = hardwareMap.dcMotor.get("left_back");
            tail = hardwareMap.crservo.get("tail");
            tail.setPower(0); 
            SkyStoneCounter = 0;
            
        }   
                private void DriveForward(){
            right_front.setPower(.7);
            right_back.setPower(.7);
            left_front.setPower(.7);
            left_back.setPower(.7);
        }
                private void DriveForwardSlow(){
            right_front.setPower(.5);
            right_back.setPower(.5);
            left_front.setPower(.5);
            left_back.setPower(.5);
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
        
                private void TailCLOSE(){
            tail.setPower(1);
        }       
                private void TailOpen(){
            tail.setPower(0);
      
        }
                private void Rotate(){
            right_front.setPower(-1);
            right_back.setPower(-1);
            left_front.setPower(1);
            left_back.setPower(1);
            
        }
                private void DriveStop(){
            right_front.setPower(0);
            right_back.setPower(0);
            left_front.setPower(0);
            left_back.setPower(0);
        }
}
