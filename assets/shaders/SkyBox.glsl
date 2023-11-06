#type vertex
#version 330 core
layout (location = 0) in vec3 aPos;
layout (location=1) in vec4 aColor;
layout (location=2) in vec2 aTexCoords;
layout (location=3) in float aTexId;

out vec3 TexCoords;

uniform mat4 uModel;
uniform mat4 uView;
uniform mat4 uProjection;

void main()
{
    //TexCoords = aTexCoords;
    TexCoords = aPos;
    vec4 pos = uProjection * uView * vec4(aPos,1);
    gl_Position = pos.xyzw;
} 

#type fragment
#version 330 core
out vec4 color;

in vec3 TexCoords;

uniform samplerCube skybox;

void main()
{    
    //color = texture(skybox, TexCoords);
    color = vec4 (TexCoords,1);
}