#version 330 core 

layout(location = 0) in vec3 position;
out vec3 vert_color;

void main() {
    gl_Position.xyz = position;
    gl_Position.w = 1.0;
    vert_color = vec3(1, 0, 0);
}
