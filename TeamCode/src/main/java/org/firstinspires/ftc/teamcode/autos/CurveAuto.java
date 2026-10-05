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

@Autonomous(name = "Curve Auto")
public class CurveAuto extends OpMode {

  private Follower follower;

  private final PoseFactory p = PoseFactory.degrees();

  // Places the robot actually goes: (x, y, heading in degrees)
  private final Pose start  = p.of(24, 24, 0);
  private final Pose pointB = p.of(48, 48, 90);

  // Control points: they bend the curves toward them, the robot never visits them.
  // Their heading value is ignored.
  private final Pose controlOut  = p.of(48, 24, 0);
  private final Pose controlBack = p.of(24, 48, 0);

  // Out: sweep forward then left, robot faces the direction it's driving
  private Path curveOut() {
    return curve(start, controlOut, pointB).tangent();
  }

  // Back: curve back to start while turning from 90 to 0 degrees
  // NOTE: linear() args swapped to work around reversed interpolation in this Pedro version
  private Path curveBack() {
    return curve(pointB, controlBack, start).linear(start, pointB);
  }

  private Command autoRoutine() {
    return sequential(
        follow(follower, curveOut()),
        follow(follower, curveBack())
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