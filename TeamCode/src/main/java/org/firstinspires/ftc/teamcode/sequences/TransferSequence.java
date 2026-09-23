package org.firstinspires.ftc.teamcode.sequences;

import com.seattlesolvers.solverslib.command.InstantCommand;
import com.seattlesolvers.solverslib.command.SequentialCommandGroup;
import com.seattlesolvers.solverslib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.subsystems.StopperSubsystem;

public class TransferSequence extends SequentialCommandGroup {

    public TransferSequence(StopperSubsystem stopper) {
        addCommands(
                new InstantCommand(stopper::open),
                new WaitCommand(500),
                new InstantCommand(stopper::close)
        );
    }
}