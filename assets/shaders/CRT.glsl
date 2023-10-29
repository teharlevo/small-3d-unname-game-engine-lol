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
    vec2 uv = fTexCoords;
    uv = uv * 2 - 1;
    vec2 offset = uv/0.7;
    uv = uv + uv * offset * offset;
    uv *= 1.5;
    uv = uv * 0.5 + 0.5;
    if(fTexId == -1){
        color = fColor;
    }else{
        int id = int(fTexId);
        if(uv.x >= 0 && uv.x <= 1 && uv.y >= 0 && uv.y <= 1){
            color = fColor * texture(uTex_Sampler[id], uv);
        }
        else{
            color = vec4(0,0,0,1);
        }
        //color = vec4(uv.x,uv.y,0,1);
    }
}