#include <iostream>
#include <fstream>
#include <map>
#include <regex>
#include <string>
#include <sstream>
using namespace std;

const string INPUT = "../input.txt";
const regex NUM_REGEX = regex("[0-9]+");

bool check_char(const char c) {
    //     exclude numbers      exclude "."
    return (c < '0' || c > '9') && c != '.';
}

vector<string> split_by_newline(const string& input) {
    vector<string> vec;
    std::istringstream f(input);
    string line;
    while (getline(f, line)) {
        vec.push_back(line);
    }
    return vec;
}

int main() {
    // read input file
    std::ifstream t(INPUT);
    std::stringstream buffer;
    buffer << t.rdbuf();
    vector<string> lines = split_by_newline(buffer.str());
    int i = 0;
    int total = 0;
    u_int totalGearRatios = 0;
    //  pos: line    char  , vector of nums
    map<pair<u_int, u_int>, vector<u_int>> gear_positions;
    for (const string& line : lines) {
        smatch sm;
        string tmp = line;
        u_int last_pos = 0;
        while (regex_search(tmp, sm, NUM_REGEX)) {
            string sub_match = sm[0];
            int start = sm.position() + last_pos;
            int end = start + sub_match.length() - 1;
            last_pos = end + 1;
            bool match = false;
            // check line before and after current line
            for (int j = i - 1; j <= i + 1; j++) {
                if (match || j < 0 || j > lines.size() - 1) continue;
                for (u_int k = start + (start == 0 ? 0 : -1); k <= end + 1; k++) {
                    if (k < 0 || k >= lines[j].length()) continue;
                    char c = lines[j].at(k);
                    if (check_char(c)) {
                        match = true;
                        // check for gears
                        if (c == '*') {
                            printf("%s is connected to a gear on %u:%u\n", sub_match.c_str(), j, k);
                            vector<u_int> v = gear_positions[make_pair(j, k)];
                            v.push_back(stoi(sub_match));
                            gear_positions[make_pair(j, k)] = v;
                        }
                    }
                }
            }
            if (match) {
                printf("%s (line %d) has adjacent symbols!\n", sub_match.c_str(), i + 1);
                total += stoi(sub_match);
            }
            tmp = sm.suffix();
        }
        i++;
    }
    for (pair<pair<u_int, u_int>, vector<u_int>> pos: gear_positions) {
        if (pos.second.size() < 2) continue;
        u_int temp = 0;
        for (u_int second : pos.second) {
            if (temp == 0) temp = second;
            else temp *= second;
        }
        totalGearRatios += temp;
    }
    printf("\nTotal part numbers: %d\n", total);
    printf("Total gear ratios: %u\n", totalGearRatios);
    return 0;
}
