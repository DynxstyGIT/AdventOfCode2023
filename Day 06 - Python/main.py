input_file = "input.txt"
kerning = True


class Race:
    def __init__(self, time, dist):
        self.time = time
        self.distance = dist


def extract_nums(line):
    line = line.replace('Time:', '').replace('Distance:', '')
    if kerning:
        line = line.replace(' ', '')
    return [int(s) for s in line.split() if s.isdigit()]


if __name__ == '__main__':
    f = open(input_file)
    lines = f.readlines()
    races = [Race(time, dist) for (time, dist) in zip(extract_nums(lines[0]), extract_nums(lines[1]))]
    total = 0
    for race in races:
        amount = 0
        for i in range(race.time):
            if (race.time - i) * i > race.distance:
                amount += 1
        total = amount if total == 0 else (total * amount)
    print(total)
