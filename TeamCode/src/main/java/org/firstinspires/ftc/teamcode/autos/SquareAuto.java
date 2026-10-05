package org.firstinspires.ftc.teamcode.autos;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import com.pedropathing.follower.Follower;
import org.firstinspires.ftc.teamcode.pedro.Constants;

import com.pedropathing.math.Pose;
import com.pedropathing.api.PoseFactory;

import static com.pedropathing.api.Paths.*;
import com.pedropathing.paths.Path;

import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.Scheduler;
import static com.pedropathing.ivy.Scheduler.schedule;
import static com.pedropathing.ivy.groups.Groups.sequential;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;

@Autonomous(name = "Square Auto")
public class SquareAuto extends OpMode {

  private Follower follower;

  private final PoseFactory p = PoseFactory.degrees();

  // Corners of the square: (x, y, heading in degrees)
  private final Pose start  = p.of(24, 24, 0);
  private final Pose pointA = p.of(48, 24, 0);
  private final Pose pointB = p.of(48, 48, 0);
  private final Pose pointC = p.of(24, 48, 90);

  // Leg 1: forward 24"
  private Path leg1() {
    return line(start, pointA).linear(start, pointA);
  }

  // Leg 2: strafe left 24", keep facing forward
  private Path leg2() {
    return line(pointA, pointB).linear(pointA, pointB);
  }

  // Leg 3: back up 24" while turning left 90 degrees
  private Path leg3() {
    return line(pointB, pointC).linear(pointB, pointC);
  }

  // Leg 4: return to start while turning back to 0 degrees
  private Path leg4() {
    return line(pointC, start).linear(pointC, start);
  }

  private Command autoRoutine() {
    return sequential(
        follow(follower, leg1()),
        follow(follower, leg2()),
        follow(follower, leg3()),
        follow(follower, leg4())
    );
  }

  @Override
  public void init() {
    Scheduler.reset();
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
}