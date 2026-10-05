package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.Intake;

@TeleOp(name = "Intake Test")
public class IntakeTest extends OpMode {

  private Intake intake;

  @Override
  public void init() {
    intake = new Intake(hardwareMap);
  }

  @Override
  public void loop() {
    // Hold Cross (A) = intake in, hold Circle (B) = intake out, otherwise stop
    if (gamepad1.a) {
      intake.in();
      telemetry.addData("Intake", "IN");
    } else if (gamepad1.b) {
      intake.out();
      telemetry.addData("Intake", "OUT");
    } else {
      intake.stop();
      telemetry.addData("Intake", "stopped");
    }
    telemetry.update();
  }

  @Override
  public void stop() {
    intake.stop();
  }
}