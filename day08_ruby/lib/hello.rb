# frozen_string_literal: true

class Node < Struct.new(:name, :left, :right)
end

lines = File.readlines('input.txt').map(&:chomp)
instructions = lines[0]
nodes = {}
lines.slice(2, lines.length).each do |line|
  first_split = line.split(" = ")
  second_split = first_split[1][1..-2].split(", ")
  name = first_split[0].strip
  nodes[name] = Node.new(name, second_split[0], second_split[1])
end

current_node = nodes["AAA"]
i = 0
while current_node.class == Node && current_node.name != "ZZZ" do
  inst = instructions[i % instructions.length]
  current_node = nodes[inst == 'L' ? current_node.left : current_node.right]
  i += 1
end

puts "Part 1: #{i} steps"