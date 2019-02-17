/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
public class Robot extends IterativeRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();

  private WPI_TalonSRX leftFrontMotor;
  private WPI_TalonSRX leftMiddleMotor;
  private WPI_TalonSRX rightFrontMotor;
  private WPI_TalonSRX rightMiddleMotor;
  private WPI_TalonSRX leftRearMotor;
  private WPI_TalonSRX rightRearMotor;

  private SpeedControllerGroup leftMotors;
  private SpeedControllerGroup rightMotors;

  private DifferentialDrive differentialDrive;

  private Joystick joystick;

  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);

    leftFrontMotor = new WPI_TalonSRX(3);
    rightFrontMotor = new WPI_TalonSRX(6);
    leftRearMotor = new WPI_TalonSRX(1);
    rightRearMotor = new WPI_TalonSRX(4);
    leftMiddleMotor = new WPI_TalonSRX(2);
    rightMiddleMotor = new WPI_TalonSRX(5);

    leftFrontMotor.setInverted(true);
    leftMiddleMotor.setInverted(true);
    leftRearMotor.setInverted(true);
    rightFrontMotor.setInverted(true);
    rightMiddleMotor.setInverted(true);
    rightRearMotor.setInverted(true);

    leftMotors = new SpeedControllerGroup(leftFrontMotor, leftRearMotor, leftMiddleMotor);
    rightMotors = new SpeedControllerGroup(rightFrontMotor, rightRearMotor, rightMiddleMotor);

    differentialDrive = new DifferentialDrive(leftMotors, rightMotors);

    joystick = new Joystick(0);
  }

  @Override
  public void robotPeriodic() {
  }

  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // autoSelected = SmartDashboard.getString("Auto Selector",
    // defaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
  }

  @Override
  public void autonomousPeriodic() {
    differentialDrive.tankDrive(joystick.getY(Hand.kLeft), joystick.getY(Hand.kRight));
  }

  @Override
  public void teleopPeriodic() {
    joystick.setXChannel(5);
    joystick.setYChannel(1);
    //System.out.println(joystick.getY(Hand.kRight));
    differentialDrive.tankDrive(joystick.getX(), joystick.getY());
  }

  @Override
  public void testPeriodic() {
  }
}
