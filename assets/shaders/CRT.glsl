#type vertex
#version 330 core
layout (location=0) in vec3 aPos;
layout (location=1) in vec4 aColor;
layout (location=2) in vec2 aTexCoords;
layout (location=3) in float aTexId;

uniform mat4 uModel;
uniform mat4 uView;
uniform mat4 uProjection;

out vec4 fColor;
out vec2 fTexCoords;
out float  fTexId;

void main()
{
    fColor = aColor;
    fTexCoords = aTexCoords;
    fTexCoords = fTexCoords * 2 - 1;
    vec2 offset = fTexCoords/1;
    fTexCoords = fTexCoords + fTexCoords * offset * offset;
    fTexCoords = fTexCoords * 0.5 + 0.5;
    fTexId = aTexId;
    gl_Position = uProjection * uView * uModel * vec4(aPos, 1.0);
}

#type fragment
#version 330 core

uniform sampler2D[8] uTex_Sampler;

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
        if(fTexCoords.x >= 0 && fTexCoords.x <= 1 && fTexCoords.y >= 0 && fTexCoords.y <= 1){
            color = fColor * texture(uTex_Sampler[id], fTexCoords);
        }
        else{
            color = vec4(0,0,0,1);
        }
    }
}