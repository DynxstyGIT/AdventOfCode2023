use std::cmp::Ordering;
use std::collections::HashMap;
use std::fs;

const INPUT: &str = "input.txt";

fn check_deck_score(hand: String, wildcard: bool) -> u8 {
    let mut cards = HashMap::new();
    for c in hand.chars() {
        *cards.entry(c).or_insert(0) += 1
    }
    if wildcard {
        if let Some(c) = cards.get(&'J') {
            let mut to_upgrade = '*';
            let mut max_amt = 0;
            // get card with highest amount
            for (card, amt) in cards.iter() {
                if *card != 'J' && *amt > max_amt {
                    to_upgrade = *card;
                    max_amt = *amt;
                }
            }
            *cards.entry(to_upgrade).or_insert(0) += *c;
            cards.remove(&'J');
        }
    }
    match cards.len() {
        1 => 7,
        2 => if cards.values().any(|c| *c == 4) { 6 } else { 5 },
        3 => if cards.values().any(|c| *c == 3) { 4 } else { 3 }
        4 => 2,
        5 => 1,
        _ => unimplemented!()
    }
}

fn camel_cards(bids: &mut Vec<(&str, u32)>, cards_order: &str) -> u32 {
    let wildcard = cards_order.starts_with('J');
    bids.sort_by(|a, b| {
        let score_a = check_deck_score(a.0.to_string(), wildcard);
        let score_b = check_deck_score(b.0.to_string(), wildcard);
        // check card order
        if score_a == score_b {
            let hand_a = a.0.to_string();
            let hand_b = b.0.to_string();
            for i in 0..5 {
                let card_a = hand_a.chars().nth(i).unwrap();
                let card_b = hand_b.chars().nth(i).unwrap();
                if card_a == card_b { continue };
                return if cards_order.find(card_a) > cards_order.find(card_b) {
                    Ordering::Greater
                } else {
                    Ordering::Less
                }
            }
        }
        score_a.cmp(&score_b)
    });
    // calc total
    let mut total = 0;
    for (index, hand_and_bid) in bids.iter().enumerate() {
        total += hand_and_bid.1 * (index as u32 + 1);
    }
    total
}

fn main() {
    let content = fs::read_to_string(INPUT)
        .expect("Could not read input!");
    // turn content into a vector of lines
    let lines: Vec<&str> = content.split("\n").collect();
    let mut bids: Vec<(&str, u32)> = Vec::new();
    for line in lines {
        let (hand, bid_raw) = line.split_once(" ").unwrap();
        bids.push((hand, bid_raw.parse::<u32>().unwrap()))
    }
    println!("Part 1: {}", camel_cards(&mut bids, "23456789TJQKA"));
    println!("Part 2: {}", camel_cards(&mut bids, "J23456789TQKA"));
}
