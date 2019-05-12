#version 330 core
out vec3 color;

in vec3 vert_color;

void main(){
  color = vert_color;
}