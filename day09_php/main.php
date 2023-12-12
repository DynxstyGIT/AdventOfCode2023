<?php
$file = fopen("input.txt", "r");
if (!$file) {
    exit(-1);
}
$part_1 = 0;
$part_2 = 0;
while (($line = fgets($file)) !== false) {
    preg_match_all('![-+]?\d+!', $line, $matches);
    $arr_2d = [$matches[0]];
    while (!array_all_match(end($arr_2d), 0)) {
        $arr_2d[] = get_differences(end($arr_2d));
    }
    # extrapolate
    extrapolate($arr_2d, true);
    print_2d_arr($arr_2d);
    $part_1 += end($arr_2d[0]);
    for ($i = 0; $i < sizeof($arr_2d); $i++) {
        $arr_2d[$i] = array_reverse($arr_2d[$i]);
    }
    extrapolate($arr_2d, false);
    print_2d_arr($arr_2d);
    $part_2 += end($arr_2d[0]);
}
print("\nPart 1: $part_1");
print("\nPart 2: $part_2");
fclose($file);

function print_2d_arr($arr): void {
    foreach ( $arr as $var ) {
        print(implode(", ", $var) . "\n");
    }
}

function get_differences($arr): array
{
    $new_arr = [];
    $arr_size = sizeof($arr);
    for ($i = 0; $i < $arr_size; $i++) {
        $next = $i + 1;
        if ($next >= $arr_size) continue;
        $new_arr[] = ($arr[$next] ?? 0) - ($arr[$i] ?? 0);
    }
    return $new_arr;
}

function extrapolate(&$arr_2d, $forward): void
{
    $arr_size = sizeof($arr_2d);
    for ($i = $arr_size - 1; $i >= 0; $i--) {
        $element = $arr_2d[$i];
        if ($i == $arr_size - 1) {
            $element[] = 0;
        } else {
            $previous = $arr_2d[$i + 1];
            $element[] = $forward
                ? (end($element) + end($previous))
                : (end($element) - end($previous));
        }
        $arr_2d[$i] = $element;
    }
}

function array_all_match($arr, $match): bool {
    foreach ($arr as $value) {
        if ($value != $match) return false;
    }
    return true;
}