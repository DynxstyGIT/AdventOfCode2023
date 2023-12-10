class Input < Struct.new(:instructions, :nodes)
end
class Node < Struct.new(:name, :left, :right)
  def to_s
    "#{name}: (#{left}, #{right})"
  end
end

def read_input(file_name)
  lines = File.readlines(file_name).map(&:chomp)
  nodes = {}
  lines.slice(2, lines.length).each do |line|
    first_split = line.split(" = ")
    second_split = first_split[1][1..-2].split(", ")
    name = first_split[0].strip
    nodes[name] = Node.new(name, second_split[0], second_split[1])
  end
  Input.new(lines[0], nodes)
end