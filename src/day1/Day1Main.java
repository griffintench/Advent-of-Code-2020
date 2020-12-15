package day1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.TreeSet;

public class Day1Main {
	public static void run() throws FileNotFoundException {
		File inputFile = new File("src/day1/input.txt");
		Scanner scan = new Scanner(inputFile);
		
		TreeSet<Integer> entries = new TreeSet<Integer>();
		
		while(scan.hasNextLine()) {
		    String line = scan.nextLine();
		    Integer entry = Integer.parseInt(line);
		    entries.add(entry);
		}
		
		scan.close();
		
		System.out.println(part2(entries));
	}
	
	private static int part1(TreeSet<Integer> entries) {
	    for(int entry : entries) {
	        int addend = 2020 - entry;
	        if(entries.contains(addend)) {
	            return entry * addend;
	        }
	    }
	    System.out.println("Addends not found");
	    return -1;
	}
	
	private static int part2(TreeSet<Integer> entries) {
	    for(int entry1 : entries) {
	        int remainder = 2020 - entry1;
	        for(int entry2 : entries) {
	            if(entry2 != entry1) {
	                int entry3 = remainder - entry2;
	                if(entries.contains(entry3)) {
	                    return entry1 * entry2 * entry3;
	                }
	            }
	        }
	    }
	    
	    System.out.println("Addends not found");
	    return -1;
	}
}
