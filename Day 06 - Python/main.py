input_file = "input.txt"
kerning = True


class Race:
    def __init__(self, time, distance):
        self.time = time
        self.distance = distance


def extract_nums(line):
    line = line.replace('Time:', '').replace('Distance:', '')
    if kerning:
        line = line.replace(' ', '')
    return [int(s) for s in line.split() if s.isdigit()]


if __name__ == '__main__':
    f = open(input_file)
    lines = f.readlines()
    races = [Race(time, distance) for (time, distance) in zip(extract_nums(lines[0]), extract_nums(lines[1]))]
    total = 0
    for race in races:
        ways_to_win = []
        for i in range(race.time):
            time_left = race.time - i
            meters = time_left * i
            if meters > race.distance:
                ways_to_win.append(i)
        amount = len(ways_to_win)
        total = amount if total == 0 else (total * amount)
    print(total)
