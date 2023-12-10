require_relative 'model'

input = read_input("test_input1.txt")
instructions = input.instructions
nodes = input.nodes

current_node = nodes['AAA']
i = 0
while current_node.class == Node && current_node.name != 'ZZZ' do
  inst = instructions[i % instructions.length]
  current_node = nodes[inst == 'L' ? current_node.left : current_node.right]
  i += 1
end

puts "Part 1: #{i} steps"