#version 430 core
layout (location=0) in vec3 aPos;
layout (location=1) in vec3 aNormal;
layout (location=2) in vec2 aTexCoords;
layout (location=3) in mat4 Model;
layout (location=7) in vec4 ModelColor;

uniform mat4 uView;
uniform mat4 uProjection;

out vec4 fColor;
out vec2 fTexCoords;
out vec3 fNormal;
out vec3 fPos;

void main()
{
    fPos = vec3(Model * vec4(aPos, 1.0));
    fColor = ModelColor;
    fTexCoords = aTexCoords;
    fNormal = mat3(transpose(inverse(Model))) * aNormal;  
    gl_Position = uProjection * uView * Model * vec4(aPos, 1.0);
}