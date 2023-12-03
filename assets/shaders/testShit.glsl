#type vertex
#version 430 core
layout (location=0) in vec3 aPos;
layout (location=1) in vec4 aColor;
layout (location=2) in vec2 aTexCoords;
layout (location=3) in float aTexId;
layout (location=4) in vec3 aModelPos;

uniform mat4 uView;
uniform mat4 uProjection;

out vec4 fColor;
out vec2 fTexCoords;
out float  fTexId;

void main()
{
    fColor = aColor;
    //fColor = aColor;
    fTexCoords = aTexCoords;
    fTexId = aTexId;
    gl_Position = uProjection * uView *vec4(aPos + aModelPos, 1.0);
}

#type fragment
#version 430 core

uniform sampler2D[8] uTex_Sampler;
uniform float time;

in vec4 fColor;
in vec2 fTexCoords;
in float fTexId;

out vec4 color;

void main()
{
    if(fTexId == -1){
        color = fColor;
    }else{
        int id = int(fTexId);
        color = fColor * texture(uTex_Sampler[id], vec2(fTexCoords.x,fTexCoords.y ));
    }
}