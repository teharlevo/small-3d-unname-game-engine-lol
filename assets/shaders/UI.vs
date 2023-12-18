#version 430 core
layout (location=0) in vec3 aPos;
layout (location=1) in vec3 aColor;
layout (location=2) in vec2 aTexCoords;
layout (location=3) in mat4 aModel;
layout (location=7) in vec4 aModelColor;

uniform mat4 uView;
uniform mat4 uProjection;

out vec4 fColor;
out vec2 fTexCoords;
out vec3 fNormal;
out vec3 fPos;

void main()
{
    fColor = aModelColor;
    fTexCoords = aTexCoords;

    vec4 newPos = uProjection * uView * aModel * vec4(aPos, aPos.z);
    gl_Position = vec4(newPos.x,newPos.y,newPos.z,1);
}