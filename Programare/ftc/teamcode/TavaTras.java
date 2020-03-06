package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


@Autonomous(name = "TavaTras", group = "Sensor")

public class TavaTras extends LinearOpMode {
   

    TouchSensor touch; 
    private DcMotor right_front;
    private DcMotor right_back;
    private DcMotor left_front;
    private DcMotor left_back;
    private CRServo take1;
    private CRServo take2;
    private ElapsedTime Counter = new ElapsedTime();
    private ElapsedTime RunTime = new ElapsedTime();
    int Takes = 0;
    
    @Override
    public void runOpMode() {

        
        initRobot();
        waitForStart();

       
        while (opModeIsActive()) {
                if(!touch.isPressed() && Takes == 0)
                    { Counter.reset();
                    
                        while(Counter.milliseconds() <= 150)
                        {
                            DriveBackwords();
                            
                        }
                        Counter.reset();
                        
                        while(Counter.milliseconds() <= 100)
                        {
                           // RotateRight();
                            
                        }
                        Counter.reset();
                        
                        while(Counter.milliseconds() <= 300)
                        {
                            DriveBackwords();
                            
                        }
                        Counter.reset();
                        while(Counter.milliseconds() <= 100)
                        {
                            //RotateLeft();
                        }
                        Counter.reset();
                       while(true)
                       {
                           DriveBackwords();
                           
                       if(touch.isPressed())
                       { 
                           break;
                       }
                       }
                        telemetry.update();
                    }
                    else if (touch.isPressed() && Takes == 0)
                    {   
                        telemetry.addData("touch", touch.isPressed());
                        DriveStop();
                        TakesON();
                        sleep(3500);
                        Counter.reset();
                        while(Counter.milliseconds() < 1000)
                        {
                            DriveForword();
                        }
                        Counter.reset();
                        while(Counter.milliseconds() <= 700)
                        {
                           // RotateRight();
                        }
                        Counter.reset();
                        TakesOFF();
                    Takes++;
                    telemetry.update();
                        sleep(3500);
                        Counter.reset();
                    }
                    if(Takes == 1)
                        {
                             Counter.reset();
                        while(Counter.milliseconds() <= 2500)
                            {
                                DriveForword();
                            }
                         Counter.reset();
                         DriveStop();
                         break;
                        }
                    
                
        }
    }
    private void initRobot()
    {
        touch = hardwareMap.touchSensor.get("touch");
        right_front = hardwareMap.dcMotor.get("right_front");
        right_back = hardwareMap.dcMotor.get("right_back");
        left_front = hardwareMap.dcMotor.get("left_front");
        left_back = hardwareMap.dcMotor.get("left_back");
        take1 = hardwareMap.crservo.get("take1");
        take2 = hardwareMap.crservo.get("take2");
        TakesOFF();
    }
    private void DriveForword()
    {
        right_front.setPower(.5);
        right_back.setPower(.5);
        left_front.setPower(-.5);
        left_back.setPower(-.5);
    }
    private void DriveStop()
    {
        right_front.setPower(0);
        right_back.setPower(0);
        left_front.setPower(0);
        left_back.setPower(0);
    }
    private void TakesON()
    {
        take1.setPower(1);
        take2.setPower(-1);
    }
    private void TakesOFF()
    {
        take1.setPower(-.4);
        take2.setPower(1);
        
    }
    private void DriveBackwords()
    {
        right_front.setPower(-.5);
        right_back.setPower(-.5);
        left_front.setPower(.5);
        left_back.setPower(.5);
    }
}
