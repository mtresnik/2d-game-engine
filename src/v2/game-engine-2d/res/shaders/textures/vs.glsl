#version 330 core 

layout(location = 0) in vec3 position;
layout (location=1) in vec2 tex_coord;

uniform mat4 scalingMatrix;
uniform mat4 rotationMatrix;
uniform mat4 translationMatrix;

out vec2 pass_coord;

void main() {
    gl_Position =   translationMatrix*rotationMatrix *  scalingMatrix * vec4(position, 1.0);
    pass_coord = tex_coord;
}
