package day8;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Day8Main {
    
    private static enum InstructionType {
        acc, jmp, nop;
    }
    
    private static class Instruction {
        public InstructionType op;
        public final int arg;
        public Instruction(final String line) {
            final String[] splitLine = line.split(" ");
            if (splitLine[0].equals("acc")) {
                op = InstructionType.acc;
            } else if (splitLine[0].equals("jmp")) {
                op = InstructionType.jmp;
            } else {
                op = InstructionType.nop;
            }
            arg = Integer.parseInt(splitLine[1]);
        }
        public Instruction(final Instruction inst) {
            op = inst.op;
            arg = inst.arg;
        }
    }
    
    public static void run() throws FileNotFoundException {
        final File inputFile = new File("src/day8/input.txt");
        final Scanner scan = new Scanner(inputFile);
        
        final List<Instruction> instructions = new ArrayList<>();
       
        while(scan.hasNextLine()) {
            final String line = scan.nextLine();
            Instruction instruction = new Instruction(line);
            instructions.add(instruction);
        }
        
        scan.close();
        
        System.out.println(part2(instructions));
    }
    
    private static int part1(final List<Instruction> instructions) {
        int accumulator = 0;
        int nextInstructionIndex = 0;
        
        final boolean[] usedInstructions = new boolean[instructions.size()];
        while (!usedInstructions[nextInstructionIndex]) {
            usedInstructions[nextInstructionIndex] = true;
            final Instruction nextInstruction = instructions.get(nextInstructionIndex);
            final InstructionType nextInstructionType = nextInstruction.op;
            if (nextInstructionType == InstructionType.acc) {
                accumulator += nextInstruction.arg;
                ++nextInstructionIndex;
            } else if (nextInstructionType == InstructionType.jmp) {
                nextInstructionIndex += nextInstruction.arg;
            } else if (nextInstructionType == InstructionType.nop) {
                ++nextInstructionIndex;
            }
        }
        
        return accumulator;
    }
    
    private static int part2(final List<Instruction> instructions) {
        for (int i = 0; i < instructions.size(); ++i) {
            final Instruction instruction = instructions.get(i);
            if (instruction.op == InstructionType.jmp) {
                final List<Instruction> instructionCopies = new ArrayList<>();
                for (int j = 0; j < instructions.size(); ++j) {
                    final Instruction newInstruction = new Instruction(instructions.get(j));
                    if (i == j) {
                        newInstruction.op = InstructionType.nop;
                    }
                    instructionCopies.add(newInstruction);
                }
                final int result = runInstructions(instructionCopies);
                if (result != Integer.MAX_VALUE) {
                    return result;
                }
            }
        }
        System.out.println("Nothing works!");
        return -1;
    }
    
    private static int runInstructions(final List<Instruction> instructions) {
        int accumulator = 0;
        int nextInstructionIndex = 0;
        
        final boolean[] usedInstructions = new boolean[instructions.size()];
        while (nextInstructionIndex >= 0 && nextInstructionIndex < usedInstructions.length 
                && !usedInstructions[nextInstructionIndex]) {
            usedInstructions[nextInstructionIndex] = true;
            final Instruction nextInstruction = instructions.get(nextInstructionIndex);
            final InstructionType nextInstructionType = nextInstruction.op;
            if (nextInstructionType == InstructionType.acc) {
                accumulator += nextInstruction.arg;
                ++nextInstructionIndex;
            } else if (nextInstructionType == InstructionType.jmp) {
                nextInstructionIndex += nextInstruction.arg;
            } else if (nextInstructionType == InstructionType.nop) {
                ++nextInstructionIndex;
            }
        }
        
        if (nextInstructionIndex >= 0 && nextInstructionIndex < usedInstructions.length) {
            // infinite loop
            return Integer.MAX_VALUE;
        }
        
        return accumulator;
    }
}
