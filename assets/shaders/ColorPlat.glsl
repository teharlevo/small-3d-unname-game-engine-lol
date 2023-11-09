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

uniform sampler2D uTex_Sampler;
uniform sampler2D uTex_SamplerTwo;

in vec4 fColor;
in vec2 fTexCoords;
in float fTexId;

out vec4 color;

void main()
{
    vec4 thecolor;
    if(fTexId == -1){
        thecolor = fColor;
    }else{
        int id = int(fTexId);
        thecolor = fColor * texture(uTex_Sampler[id], fTexCoords);
    }
    thecolor = fColor;
    float k = ((thecolor.g + thecolor.r + thecolor.b)/(3.0));
    color = texture(uTex_Sampler[1], vec2(0,0));

}