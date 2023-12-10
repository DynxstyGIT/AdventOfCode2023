import 'dart:io';
import 'dart:math';

const String input = 'input.txt';
final List<String> lines = File(input).readAsLinesSync();
final Map<int, int> cards = {for (int i = 0; i < lines.length; i++) i + 1: 1};

void main(List<String> arguments) {
  int totalPoints = 0;
  int i = 0;
  for (final String line in lines) {
    (List<int>, List<int>) split = splitCard(line);
    int matches = 0;
    for (int num in split.$2) {
      if (split.$1.contains(num)) {
        matches++;
      }
    }
    int points = matches == 1 ? 1 : pow(2, matches - 1).toInt();
    print('Card ${i + 1} (${cards[i + 1]} copies) is worth $points points ($matches matche(s))');
    final List<int> won = List.generate(matches, (index) => i + index + 2);
    for (int j = 0; j < (cards[i + 1] ?? 1); j++) {
      for (var num in won) {
        cards[num] = (cards[num] ?? 1) + 1;
      }
    }
    print('- You\'ve won the following Scratchcards: $won\n');
    totalPoints += points;
    i++;
  }
  print('Total Points: $totalPoints');
  print('Total Scratchcards: ${cards.values.fold(0, (a, b) => a + b)}');
}

(List<int>, List<int>) splitCard(String card) {
  final List<String> split = card.substring(card.indexOf(':') + 1).split('|');
  return (toInts(split[0]), toInts(split[1]));
}

List<int> toInts(String s) => s.trim().split(RegExp('\\s+')).map((e) => int.parse(e)).toList();
