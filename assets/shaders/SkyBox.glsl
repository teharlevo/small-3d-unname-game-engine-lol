#type vertex
#version 330 core
layout (location = 0) in vec3 aPos;
layout (location=1) in vec4 aColor;
layout (location=2) in vec2 aTexCoords;
layout (location=3) in float aTexId;

out vec3 TexCoords;

uniform mat4 uProjection;
uniform mat4 uView;

void main()
{
    TexCoords = aPos;
    vec4 pos = uProjection * uView * vec4(aPos, 1.0);
    gl_Position = pos.xyww;
}  

#type fragment
#version 330 core
out vec4 color;

in vec3 TexCoords;

uniform samplerCube skybox;

void main()
{    
    //color = texture(skybox, TexCoords);
    color = vec4(1);
}