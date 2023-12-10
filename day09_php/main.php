<?php
$file = fopen("input.txt", "r");
if (!$file) {
    exit(-1);
}
$total = 0;
while (($line = fgets($file)) !== false) {
    preg_match_all('!\d+!', $line, $matches);
    $arr = $matches[0];
    print("<h3>Solving array:</h3>\n");
    print_arr($arr);
    while (!array_all_match($arr, 0)) {
        $arr = solve($arr);
        print_arr($arr);
    }
}
fclose($file);

function print_arr($arr) {
    $as_str = implode(', ', $arr);
    print("$as_str<br>\n");
}

function solve($arr): array
{
    $new_arr = [];
    $arr_size = sizeof($arr);
    for ($i = 0; $i < $arr_size; $i++) {
        $next = $i + 1;
        if ($next >= $arr_size) continue;
        $max = max($arr[$i] ?? 0, $arr[$next] ?? 0);
        $min = min($arr[$i] ?? 0, $arr[$next] ?? 0);
        $new_arr[] = $max - $min;
    }
    return $new_arr;
}

function array_all_match($arr, $match): bool {
    foreach ($arr as $value) {
        if ($value != $match) return false;
    }
    return true;
}