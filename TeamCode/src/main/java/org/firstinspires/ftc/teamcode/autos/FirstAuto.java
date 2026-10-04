package org.firstinspires.ftc.teamcode.autos;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import com.pedropathing.follower.Follower;
import org.firstinspires.ftc.teamcode.pedro.Constants;

import com.pedropathing.math.Pose;
import com.pedropathing.api.PoseFactory;

import static com.pedropathing.api.Paths.*;
import com.pedropathing.paths.Path;

import com.pedropathing.ivy.Scheduler;
import static com.pedropathing.ivy.Scheduler.schedule;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;

@Autonomous(name = "First Auto")
public class FirstAuto extends OpMode {

  private Follower follower;

  private final PoseFactory p = PoseFactory.degrees();

  private final Pose startPose = p.of(24, 24, 0);
  private final Pose endPose = p.of(48, 24, 0);

  private Path driveForward() {
    return line(startPose, endPose).linear(startPose, endPose);
  }

  @Override
  public void init() {
    Scheduler.reset();
    follower = Constants.create(hardwareMap);
    follower.setPose(startPose);
  }

  @Override
  public void start() {
    schedule(follow(follower, driveForward()));
  }

  @Override
  public void loop() {
    follower.update();
    Scheduler.execute();
  }
}