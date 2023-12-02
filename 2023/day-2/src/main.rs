// Determine which games would have been possible if the bag had been loaded
// with only 12 red cubes, 13 green cubes, and 14 blue cubes. What is the sum of
// the IDs of those games?

use std::fs::File;
use std::io::{BufRead, BufReader};

const RED: u32 = 12;
const GREEN: u32 = 13;
const BLUE: u32 = 14;

fn main() {
    let mut sum1: u32 = 0;
    let mut sum2: u32 = 0;

    // Read each game from the file.
    let file = File::open("input.txt").expect("Could not open file: input.txt");
    let reader = BufReader::new(file);
    for line in reader.lines() {
        let l = line.unwrap().to_string();
        if l.is_empty() {
            break;
        }

        let (part1, part2) = process_line(l);
        sum1 += part1.map_or(0, |x| x);
        sum2 += part2;
    }

    println!("RESULT: {}", sum1);
    println!("RESULT: {}", sum2);
}

fn process_line(line: String) -> (Option<u32>, u32) {
    let game_line: Vec<String> = line.split(":").map(|x| x.to_string()).collect();
    let game_id: u32 = game_line[0]
        .to_string()
        .split_off(5)
        .parse()
        .expect("Could not parse game id!");

    let mut part1 = Some(game_id);
    let mut r: Option<u32> = None;
    let mut g: Option<u32> = None;
    let mut b: Option<u32> = None;

    let rounds = game_line[1].split(";");
    for round in rounds {
        if !process_round_part_1(round.to_string()) {
            part1 = None;
        }

        let (r1, g1, b1) = process_round_part_2(round.to_string());
        r = r1.map_or(r, |x1| r.map_or(r1, |x| Some(std::cmp::max(x, x1))));
        g = g1.map_or(g, |x1| g.map_or(g1, |x| Some(std::cmp::max(x, x1))));
        b = b1.map_or(b, |x1| b.map_or(b1, |x| Some(std::cmp::max(x, x1))));
    }

    return (part1, r.unwrap() * g.unwrap() * b.unwrap());
}

fn process_round_part_1(round: String) -> bool {
    let dice = round.split(",");
    for die in dice {
        if !process_die_part_1(die.to_string()) {
            return false;
        }
    }

    return true;
}

fn process_round_part_2(round: String) -> (Option<u32>, Option<u32>, Option<u32>) {
    let dice = round.split(",");
    let mut r: Option<u32> = None;
    let mut g: Option<u32> = None;
    let mut b: Option<u32> = None;

    for die in dice {
        let d: Vec<String> = die
            .trim()
            .split_whitespace()
            .map(|x| x.to_string())
            .collect();

        let die_num: u32 = d[0].parse().expect("Could not parse die qty!");
        match d[1].trim().len() {
            3 => r = Some(die_num),
            4 => b = Some(die_num),
            5 => g = Some(die_num),
            _ => panic!("Could not parse die color! {}", die),
        }
    }

    return (r, g, b);
}

fn process_die_part_1(die: String) -> bool {
    let d: Vec<String> = die
        .trim()
        .split_whitespace()
        .map(|x| x.to_string())
        .collect();

    let die_num: u32 = d[0].parse().expect("Could not parse die qty!");
    match d[1].trim().len() {
        3 => {
            if die_num > RED {
                return false;
            }
        }
        4 => {
            if die_num > BLUE {
                return false;
            }
        }
        5 => {
            if die_num > GREEN {
                return false;
            };
        }
        _ => panic!("Could not parse die color! {}", die),
    }

    return true;
}
