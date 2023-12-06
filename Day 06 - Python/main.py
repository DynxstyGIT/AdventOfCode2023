input_file = "input.txt"
kerning = True


def ext_nums(line):
    line = line.replace('Time:', '').replace('Distance:', '')
    return [int(s) for s in (line.replace(' ', '') if kerning else line).split() if s.isdigit()]


if __name__ == '__main__':
    with open(input_file) as f:
        lines = f.readlines()
        total = 0
        for (time, dist) in zip(ext_nums(lines[0]), ext_nums(lines[1])):
            amount = 0
            for i in range(time):
                if (time - i) * i > dist:
                    amount += 1
            total = amount if total == 0 else (total * amount)
        print(total)
