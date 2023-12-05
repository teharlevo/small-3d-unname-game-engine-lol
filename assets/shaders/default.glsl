#type vertex
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

void main()
{
    fColor = aModelColor;
    //fColor = aColor;
    fTexCoords = aTexCoords;
    gl_Position = uProjection * uView * aModel * vec4(aPos, 1.0);
}

#type fragment
#version 430 core

uniform sampler2D[8] uTex_Sampler;

in vec4 fColor;
in vec2 fTexCoords;

out vec4 color;

void main()
{
    color = fColor * texture(uTex_Sampler[0], vec2(fTexCoords.x,fTexCoords.y ));
}