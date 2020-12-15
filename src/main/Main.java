package main;

import java.io.FileNotFoundException;

import day1.Day1Main;
import day10.Day10Main;
import day11.Day11Main;
import day12.Day12Main;
import day13.Day13Main;
import day14.Day14Main;
import day15.Day15Main;
import day2.Day2Main;
import day3.Day3Main;
import day4.Day4Main;
import day5.Day5Main;
import day6.Day6Main;
import day7.Day7Main;
import day8.Day8Main;
import day9.Day9Main;

public class Main {
	public static void main(String[] args) {
		try {
            Day15Main.run();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
	}
}
