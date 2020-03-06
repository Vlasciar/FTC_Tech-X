package org.firstinspires.ftc.teamcodetest;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.TouchSensor;
import java.util.Timer;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "Automatus", group = "")
public class A_Move_Park extends LinearOpMode {

TouchSensor touch; 
    private DcMotor right_front;
    private DcMotor right_back;
    private DcMotor left_front;
    private DcMotor left_back;
    private CRServo take1;
    private CRServo take2;
    private CRServo claw;
    private ElapsedTime Counter = new ElapsedTime();
    private ElapsedTime RunTime = new ElapsedTime();
    private Boolean isBlue;
    @Override
    public void runOpMode() {

        
        initRobot();
        waitForStart();

       
        while(opModeIsActive()) {
            while(RunTime.milliseconds() <= 29900)
               { if(!touch.isPressed())
                    { 
                       DriveBackwords();
                 if (touch.isPressed())
                    {   
                        
                        telemetry.addData("touch", touch.isPressed());
                        TakesON();
                        Counter.reset();
                       

}}}}}
    private void initRobot()
    {
        touch = hardwareMap.touchSensor.get("touch");
        right_front = hardwareMap.dcMotor.get("right_front");
        right_back = hardwareMap.dcMotor.get("right_back");
        left_front = hardwareMap.dcMotor.get("left_front");
        left_back = hardwareMap.dcMotor.get("left_back");
        take1 = hardwareMap.crservo.get("take1");
        take2 = hardwareMap.crservo.get("take2");
        claw = hardwareMap.crservo.get("claw");
        TakesOFF();
        claw.setPower(1);
        isBlue = false;
    }
    private void RotateRight()
    {
        right_front.setPower(.5);
        left_front.setPower(-.5);
        right_back.setPower(.5);
        left_back.setPower(-.5);
    }
    private void RotateLeft()
    {
        right_front.setPower(-.5);
        left_front.setPower(.5);
        right_back.setPower(-.5);
        left_back.setPower(.5);
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