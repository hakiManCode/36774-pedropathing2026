package org.firstinspires.ftc.teamcode.autos;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import com.pedropathing.follower.Follower;
import org.firstinspires.ftc.teamcode.pedro.Constants;
import org.firstinspires.ftc.teamcode.subsystems.Intake;

import com.pedropathing.math.Pose;
import com.pedropathing.api.PoseFactory;

import static com.pedropathing.api.Paths.*;
import com.pedropathing.paths.Path;

import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.Scheduler;
import static com.pedropathing.ivy.Scheduler.schedule;
import static com.pedropathing.ivy.groups.Groups.sequential;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;
import static com.pedropathing.ivy.commands.Commands.waitMs;

@Autonomous(name = "Intake Curve Auto")
public class IntakeCurveAuto extends OpMode {

  private Follower follower;
  private Intake intake;

  private final PoseFactory p = PoseFactory.degrees();

  private final Pose start  = p.of(24, 24, 0);
  private final Pose pointB = p.of(48, 48, 90);

  private final Pose controlOut  = p.of(48, 24, 0);
  private final Pose controlBack = p.of(24, 48, 0);

  // Out: sweep forward then left through the pollen, intake leading
  private Path curveOut() {
    return curve(start, controlOut, pointB).tangent();
  }

  // Back: carry the pollen home, turning from 90 back to 0 degrees
  // NOTE: curve() needs no linear() swap; only line() has the reversed-heading bug
  private Path curveBack() {
    return curve(pointB, controlBack, start).linear(pointB, start);
  }

  private Command autoRoutine() {
    return sequential(
        intake.inCommand(),               // 1. start intake
        follow(follower, curveOut()),     // 2. curve out, picking up pollen
        waitMs(500),                      // 3. let pollen finish going in
        intake.stopCommand(),             // 4. stop intake
        follow(follower, curveBack())     // 5. carry it back to start
    );
  }

  @Override
  public void init() {
    Scheduler.reset();
    intake = new Intake(hardwareMap);
    follower = Constants.create(hardwareMap);
    follower.setPose(start);
    follower.update();
  }

  @Override
  public void start() {
    schedule(autoRoutine());
  }

  @Override
  public void loop() {
    follower.update();
    Scheduler.execute();

    telemetry.addData("X", follower.pose().x());
    telemetry.addData("Y", follower.pose().y());
    telemetry.addData("Heading", Math.toDegrees(follower.pose().heading()));
    telemetry.update();
  }

  @Override
  public void stop() {
    intake.stop();
  }
}