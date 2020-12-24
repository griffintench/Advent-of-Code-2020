package main;

import java.io.FileNotFoundException;

import day1.Day1Main;
import day10.Day10Main;
import day11.Day11Main;
import day12.Day12Main;
import day13.Day13Main;
import day14.Day14Main;
import day15.Day15Main;
import day16.Day16Main;
import day17.Day17Main;
import day18.Day18Main;
import day19.Day19Main;
import day2.Day2Main;
import day20.Day20Main;
import day21.Day21Main;
import day22.Day22Main;
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
            Day22Main.run();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
	}
}
