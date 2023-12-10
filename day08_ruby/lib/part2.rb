require_relative 'model'

input = read_input("input.txt")
instructions = input.instructions
nodes = input.nodes

current_nodes = nodes.filter{|key, _| key.end_with?('A') }.values
i = 0
until current_nodes.all? { |n| n.name.end_with?('Z') } do
  inst = instructions[i % instructions.length]
  new_nodes = Array.new
  current_nodes.each do |n|
    new_nodes.push(nodes[inst == 'L' ? n.left : n.right])
  end
  current_nodes = new_nodes
  i += 1
end

puts "Part 2: #{i} steps"