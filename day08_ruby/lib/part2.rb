require_relative 'model'

input = read_input("input.txt")
instructions = input.instructions
nodes = input.nodes

current_nodes = nodes.filter{|key, _| key.end_with?('A') }.values
i = 1
current_nodes.each do |n|
  current_node = n
  j = 0
  until current_node.name.end_with?('Z')
    inst = instructions[j % instructions.length]
    current_node = nodes[inst == 'L' ? current_node.left : current_node.right]
    j += 1
  end
  puts "#{n}: #{j} steps => #{current_node}"
  i = i.lcm(j)
end

puts "Part 2: #{i} steps"