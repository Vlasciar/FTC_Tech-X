package org.firstinspires.ftc.teamcodetest;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import java.util.Timer;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "TeleOp1", group = "")
public class TeleOp1 extends LinearOpMode {

    //////C1
   private DcMotor right_front;
   private DcMotor right_back;
   private DcMotor left_front;
   private DcMotor left_back;
   ///C1
   
   ////C2
   private DcMotor up_down;
   private DcMotor up_down1;
   private DcMotor right_left;
   
   private CRServo take1;
   private CRServo take2;
   private CRServo claw;
   ////C2
   
  /**
   * This function is executed when this Op Mode is selected from the Driver Station.
   */

   
    double axisMovedAmount = 0;
    double diagonalMovedAmount = 0;
    double rotationMovedAmount = 0;
    double step1 = 0.15;
    double speed = 1;
  @Override    
  public void runOpMode() {
    initStart();
    waitForStart();
    if (opModeIsActive()) {
      while (opModeIsActive()) {
        right_front.setDirection(DcMotorSimple.Direction.FORWARD);
        right_back.setDirection(DcMotorSimple.Direction.FORWARD);
        left_front.setDirection(DcMotorSimple.Direction.REVERSE);
        left_back.setDirection(DcMotorSimple.Direction.REVERSE);
        

       axisMovedAmount = 0;
       diagonalMovedAmount = 0;
       rotationMovedAmount = 0;
       rotationSpeed = 0;
       
        double catX = Math.sqrt(gamepad1.left_stick_x * gamepad1.left_stick_x);
        double catY = Math.sqrt(gamepad1.left_stick_y * gamepad1.left_stick_y);
       // diagonalMovedAmount = Math.sqrt(catX * catX + catY * catY);
        
       if(Math.sqrt(gamepad1.left_stick_x*gamepad1.left_stick_x) > Math.sqrt(gamepad1.left_stick_y*gamepad1.left_stick_y)) 
       {
            // axisMovedAmount = Math.sqrt(gamepad1.left_stick_x*gamepad1.left_stick_x);
       }
       else 
       {
           axisMovedAmount = Math.sqrt(gamepad1.left_stick_y*gamepad1.left_stick_y);
       }
       
        rotationMovedAmount = Math.abs(gamepad1.right_stick_x);
       
       speed = 0.75;
       if(gamepad1.right_bumper) speed -=0.25;
       if(gamepad1.left_bumper) speed -=0.25;
       //////////////////////////////////////////
       /*telemetry.addData("axisMovedAmount",axisMovedAmount);
       telemetry.addData("diagonalMovedAmount",diagonalMovedAmount);
       telemetry.addData("rotationMovedAmount",rotationMovedAmount);*/
       /////////////////////////////////////////
       if(axisMovedAmount > diagonalMovedAmount)
       {
       if((gamepad1.left_stick_x*gamepad1.left_stick_x)>=(gamepad1.left_stick_y*gamepad1.left_stick_y))
       {
          checkMovementX();
       }
       else 
       {          
           checkMovementY(); 
       }
       }
       
      // if(rotationMovedAmount > axisMovedAmount && rotationMovedAmount > diagonalMovedAmount)
       if (Math.abs(rotationMovedAmount)> step1)
       {
       if(gamepad1.right_stick_x > 0)
       {
           checkRotationRight();
       }
       else 
       {
           checkRotationLeft();
       }
       }
       
       
       if(diagonalMovedAmount > axisMovedAmount)
       {
           
           if((gamepad1.right_stick_x < step1 && gamepad1.right_stick_x > -step1) || (gamepad1.right_stick_y < step1 && gamepad1.right_stick_y > -step1))
           {
                right_front.setPower(0);
                right_back.setPower(0);
                left_front.setPower(0);
                left_back.setPower(0);  
                maxSpeedRF = 0;
                maxSpeedLF = 0;
                maxSpeedRB = 0;
                maxSpeedLB = 0;
                telemetry.addData("none",1);
           }
           else if (gamepad1.right_stick_x > step1 && gamepad1.right_stick_y > step1)
           {
               checkDiagonalUpRight();
               telemetry.addData("UR",1);
           }
           else if(gamepad1.right_stick_x < -step1 && gamepad1.right_stick_y > step1)
           {
               checkDiagonalUpLeft();
               telemetry.addData("UL",1);
           }
           else if(gamepad1.right_stick_x > step1 && gamepad1.right_stick_y < -step1)
           {
               checkDiagonalDownRight();
               telemetry.addData("DR",1);
           }
           else if(gamepad1.right_stick_x < -step1 && gamepad1.right_stick_y < -step1)
           {
               checkDiagonalDownLeft();
               telemetry.addData("DL",1);
           }
       }
       
        if(axisMovedAmount < 0.1 && diagonalMovedAmount < 0.1 && rotationMovedAmount < 0.1)
        {
            right_front.setPower(0);
            right_back.setPower(0);
            left_front.setPower(0);
            left_back.setPower(0);
            maxSpeedRF = 0;
            maxSpeedLF = 0;
            maxSpeedRB = 0;
            maxSpeedLB = 0;
        }
        else 
        {
            setForces();
        }
        rotationForceOffset();
        if(axisMovedAmount < 0.1 && diagonalMovedAmount < 0.1)
        {
            maxSpeedRF = 0;
            maxSpeedLF = 0;
            maxSpeedRB = 0;
            maxSpeedLB = 0;
        }
        checkUp_Down();
        checkTake();
        checkRight_Left();
        checkClaw();
        /*if(gamepad1.a) right_back.setPower(1);
        if(gamepad1.b) right_front.setPower(1);
        if(gamepad1.x) left_back.setPower(1);
        if(gamepad1.y) left_front.setPower(1);*/
        
        /*telemetry.addData("RightFront Pow", maxSpeedRF);
        telemetry.addData("RightBack Pow", maxSpeedRB);
        telemetry.addData("LeftFront Pow", maxSpeedLF);
        telemetry.addData("LeftBack Pow", maxSpeedLB);*/
        telemetry.update();
      }
    }
  }
  double maxSpeedRF = 0;
  double maxSpeedLF = 0;
  double maxSpeedRB = 0;
  double maxSpeedLB = 0;
  ElapsedTime timer = new ElapsedTime();
  double accelerationTime = 50; //35
  double accelerationSpeed = 20;//between 1-100 higher number means faster acc //10
  private void setForces()
  {
            /*right_front.setPower(maxSpeedRF);
            right_back.setPower(maxSpeedRB);
            left_front.setPower(maxSpeedLF);
            left_back.setPower(maxSpeedLB);*/
      if(timer.milliseconds() > accelerationTime)
      {
          double lb_pow = left_back.getPower();
          double rb_pow = right_back.getPower();
          double lf_pow = left_front.getPower();
          double rf_pow = right_front.getPower();
          
          double lb_acceleration = Math.abs(lb_pow - maxSpeedLB) / (100 / accelerationSpeed);
          double rb_acceleration = Math.abs(rb_pow - maxSpeedRB) / (100 / accelerationSpeed);
          double lf_acceleration = Math.abs(lf_pow - maxSpeedLF) / (100 / accelerationSpeed);
          double rf_acceleration = Math.abs(rf_pow - maxSpeedRF) / (100 / accelerationSpeed);
          
          if(lb_pow < maxSpeedLB){
          left_back.setPower(lb_pow + lb_acceleration);}
          else if(lb_pow > maxSpeedLB){
          left_back.setPower(lb_pow - lb_acceleration);}
          
          if(rb_pow < maxSpeedRB){
          right_back.setPower(rb_pow + rb_acceleration);}
          else if(rb_pow > maxSpeedRB){
          right_back.setPower(rb_pow - rb_acceleration);}
          
          if(lf_pow < maxSpeedLF){
          left_front.setPower(lf_pow + lf_acceleration);}
          else if(lf_pow > maxSpeedLF){
          left_front.setPower(lf_pow - lf_acceleration);}
          
          if(rf_pow < maxSpeedRF){
          right_front.setPower(rf_pow + rf_acceleration);}
          else if(rf_pow > maxSpeedRF){
          right_front.setPower(rf_pow - rf_acceleration);}
          
          timer.reset();
      }
  }
  private void rotationForceOffset()
  {
        maxSpeedRF -= rotationSpeed;
        maxSpeedLF -= -rotationSpeed;
        maxSpeedRB -= rotationSpeed;
        maxSpeedLB -= -rotationSpeed;
  }
  private void checkTake()
  {
        if (gamepad2.b)
        {
            take1.setPower(-.4);
            take2.setPower(1);
        }
        else if (gamepad2.a)
        {
            take1.setPower(1);
            take2.setPower(-1);
        }
  }
  private void checkClaw()
  {
      if(gamepad2.y)
      {
          claw.setPower(1);
      }
      if(gamepad2.x)
      {
          claw.setPower(0);
      }
  }
  private void checkRight_Left()
  {
     /* if (gamepad2.right_stick_x >= step3) 
    {
        right_left.setPower(speed3);
    } 
    else if (gamepad2.right_stick_x >= step2) 
    {
        right_left.setPower(speed2);
    } 
    else if (gamepad2.right_stick_x >= step1) 
    {
        right_left.setPower(speed1);
    }
    else if (gamepad2.right_stick_x <= -step3) 
    {
        right_left.setPower(-speed3);
    }
    else if (gamepad2.right_stick_x <= -step2) 
    {
        right_left.setPower(-speed2);
    }
    else if (gamepad2.right_stick_x <= -step1) 
    {
        right_left.setPower(-speed1);
    }
    else 
    {
        right_left.setPower(0);
    }*/
    if (gamepad2.dpad_left)
    {
        right_left.setPower(-1);
        
    }
    else if (gamepad2.dpad_right)
    {
        right_left.setPower(1);
    }
    else if (!gamepad2.dpad_left && !gamepad2.dpad_right)
    {
        right_left.setPower(0);
        
    }
  }
  private void checkUp_Down()
  {
    /*  if (gamepad2.right_stick_y >= step3) 
    {
        up_down.setPower(speed3);
    } 
    else if (gamepad2.right_stick_y >= step2) 
    {
        up_down.setPower(speed2);
    } 
    else if (gamepad2.right_stick_y >= step1) 
    {
        up_down.setPower(speed1);
    }
    else if (gamepad2.right_stick_y <= -step3) 
    {
        up_down.setPower(-speed3);
    }
    else if (gamepad2.right_stick_y <= -step2) 
    {
        up_down.setPower(-speed2);
    }
    else if (gamepad2.right_stick_y <= -step1) 
    {
        up_down.setPower(-speed1);
    }
    else 
    {
        up_down.setPower(0);
    }*/
         if (gamepad2.dpad_up)
         {
             up_down.setPower(.5);
             up_down1.setPower(-.40);
         }
         else if (gamepad2.dpad_down)
         {
             up_down.setPower(-.5);
             up_down1.setPower(.40);
         }
         else if (!gamepad2.dpad_up && !gamepad2.dpad_down)
         {
             up_down.setPower(0);
             up_down1.setPower(0);
         }
  } 
  private void checkDiagonalUpRight()
  {
    if (diagonalMovedAmount >= step1) 
    {
        maxSpeedLF = 0;
        maxSpeedRB = 0;
        maxSpeedLB = -speed;
        maxSpeedRF = -speed;
    }
    else 
    {
        maxSpeedLF = 0;
        maxSpeedRB = 0;
        maxSpeedLB = 0;
        maxSpeedRF = 0;
    }
  }
  
  private void checkDiagonalDownLeft()
  {
    if (diagonalMovedAmount >= step1) 
    {
        maxSpeedLF = 0;
        maxSpeedRB = 0;
        maxSpeedLB = speed;
        maxSpeedRF = speed;
    }
    else 
    {
        maxSpeedLF = 0;
        maxSpeedRB = 0;
        maxSpeedLB = 0;
        maxSpeedRF = 0;
    }
  }
  
  private void checkDiagonalUpLeft()
  {
      if (diagonalMovedAmount >= step1) 
    {
        maxSpeedRF = 0;
        maxSpeedLB = 0;
        maxSpeedLF = -speed;
        maxSpeedRB = -speed;
    }
    else 
    {
        maxSpeedRF = 0;
        maxSpeedLB = 0;
        maxSpeedLF = 0;
        maxSpeedRB = 0;
    }
  }
  
  private void checkDiagonalDownRight()
  {
      if (diagonalMovedAmount >= step1) 
    {
        maxSpeedRF = 0;
        maxSpeedLB = 0;
        maxSpeedLF = speed;
        maxSpeedRB = speed;
    }
    else 
    {
        maxSpeedRF = 0;
        maxSpeedLB = 0;
        maxSpeedLF = 0;
        maxSpeedRB = 0;
    }
  }
  
  private void checkMovementX()
  {
    if (gamepad1.left_stick_x >= step1) 
    {
        maxSpeedRF = speed;
        maxSpeedLF = -speed;
        maxSpeedRB = -speed;
        maxSpeedLB = speed;
    }
    else if (gamepad1.left_stick_x <= -step1) 
    {
        maxSpeedRF = -speed;
        maxSpeedLF = speed;
        maxSpeedRB = speed;
        maxSpeedLB = -speed;
    }
    else 
    {
        maxSpeedRF = 0;
        maxSpeedLF = 0;
        maxSpeedRB = 0;
        maxSpeedLB = 0;
    }
  }
  private void checkMovementY()
  {
    double ySpeed;
    if (gamepad1.left_stick_y >= step1) 
    {
        ySpeed = -speed;
    }
    else if (gamepad1.left_stick_y <= step1) 
    {
        ySpeed = speed;
    }
    else 
    {
        ySpeed = 0;
    }
    maxSpeedRF = ySpeed;
    maxSpeedLF = ySpeed;
    maxSpeedRB = ySpeed;
    maxSpeedLB = ySpeed;
  }
  
  private void checkRotationLeft()
  {
        if(gamepad1.left_stick_y > step1) speed = -speed;//revert direction
      
    if(gamepad1.right_stick_x < -step1)
    {
        maxSpeedRF += speed;
        maxSpeedLF += -speed;
        maxSpeedRB += speed;
        maxSpeedLB += -speed;
        
        rotationSpeed = speed;
    }
    else 
    {
        maxSpeedRF += 0;
        maxSpeedLF += 0;
        maxSpeedRB += 0;
        maxSpeedLB += 0;
        
        rotationSpeed = 0;
    }
    
            if(gamepad1.left_stick_y > step1) speed = -speed; //revert back
  }
  double rotationSpeed = 0;
  private void checkRotationRight()
  {
            if(gamepad1.left_stick_y > step1) speed = -speed;//revert direction
    
    if(gamepad1.right_stick_x > step1)
    {
        maxSpeedRF += -speed;
        maxSpeedLF += speed;
        maxSpeedRB += -speed;
        maxSpeedLB += speed;
        
        rotationSpeed = -speed;
    }
    else 
    {
        maxSpeedRF += 0;
        maxSpeedLF += 0;
        maxSpeedRB += 0;
        maxSpeedLB += 0;
        rotationSpeed = 0;
    }
    
            if(gamepad1.left_stick_y > step1) speed = -speed; //revert back
  }
  public void initStart()
  {
    right_front = hardwareMap.dcMotor.get("right_front");
    right_back = hardwareMap.dcMotor.get("right_back");
    left_front = hardwareMap.dcMotor.get("left_front");
    left_back = hardwareMap.dcMotor.get("left_back");
    up_down = hardwareMap.dcMotor.get("up/down");
    up_down1 = hardwareMap.dcMotor.get("up/down1");

    take1 = hardwareMap.crservo.get("take1");
    take2 = hardwareMap.crservo.get("take2");
    right_left = hardwareMap.dcMotor.get("right/left");
    claw = hardwareMap.crservo.get("claw");
    take1.setPower(0);
    take2.setPower(1);
    claw.setPower(0);
    
  }
}
