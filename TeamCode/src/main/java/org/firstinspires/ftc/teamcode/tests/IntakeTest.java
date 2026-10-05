package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "Intake Test")
public class IntakeTest extends OpMode {

  // These MUST match the names in the robot configuration exactly
  private static final String MAIN_NAME  = "intakemain";
  private static final String LEFT_NAME  = "leftintake";
  private static final String RIGHT_NAME = "rightintake";

  private DcMotor main;
  private CRServo left;
  private CRServo right;

  @Override
  public void init() {
    main  = hardwareMap.get(DcMotor.class, MAIN_NAME);
    left  = hardwareMap.get(CRServo.class, LEFT_NAME);
    right = hardwareMap.get(CRServo.class, RIGHT_NAME);
  }

  @Override
  public void loop() {
    // Main motor: hold A = forward, hold B = reverse
    double mainPower = 0;
    if (gamepad1.a) mainPower = 0.5;
    if (gamepad1.b) mainPower = -0.5;
    main.setPower(mainPower);

    // Left servo: hold X = forward, hold Y = reverse
    double leftPower = 0;
    if (gamepad1.x) leftPower = 1.0;
    if (gamepad1.y) leftPower = -1.0;
    left.setPower(leftPower);

    // Right servo: hold D-pad up = forward, hold D-pad down = reverse
    double rightPower = 0;
    if (gamepad1.dpad_up) rightPower = 1.0;
    if (gamepad1.dpad_down) rightPower = -1.0;
    right.setPower(rightPower);

    telemetry.addData("Main (A fwd / B rev)", mainPower);
    telemetry.addData("Left (X fwd / Y rev)", leftPower);
    telemetry.addData("Right (Up fwd / Down rev)", rightPower);
    telemetry.update();
  }
}