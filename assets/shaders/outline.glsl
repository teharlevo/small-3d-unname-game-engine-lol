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
    if (fTexId > -1) {
        int id = int(fTexId);
        float withPixal =  1.0/textureSize(uTex_Sampler[id],0).x;
        float hightPixal = 1.0/textureSize(uTex_Sampler[id],0).y;
        vec4 texColor = texture(uTex_Sampler[id], fTexCoords + vec2( withPixal * 1, hightPixal * 0)) * 0.2f;
        texColor += texture(uTex_Sampler[id], fTexCoords + vec2( withPixal * 1, hightPixal * 1)) * 0.1f;
        texColor += texture(uTex_Sampler[id], fTexCoords + vec2( withPixal * 1, hightPixal * -1)) * 0.1f;
        texColor += texture(uTex_Sampler[id], fTexCoords + vec2( withPixal * -1, hightPixal * 0)) * -0.2f;
        texColor += texture(uTex_Sampler[id], fTexCoords + vec2( withPixal * -1, hightPixal * 1)) * -0.1f;
        texColor += texture(uTex_Sampler[id], fTexCoords + vec2( withPixal * -1, hightPixal * -1)) *-0.1f;

        texColor += texture(uTex_Sampler[id], fTexCoords + vec2( withPixal * 0, hightPixal * -1)) * 0.2f;
        texColor += texture(uTex_Sampler[id], fTexCoords + vec2( withPixal * 1, hightPixal * -1)) * 0.1f;
        texColor += texture(uTex_Sampler[id], fTexCoords + vec2( withPixal * -1, hightPixal * -1)) * 0.1f;
        texColor += texture(uTex_Sampler[id], fTexCoords + vec2( withPixal * 0, hightPixal *   1)) * -0.2f;
        texColor += texture(uTex_Sampler[id], fTexCoords + vec2( withPixal * 1, hightPixal *   1)) * -0.1f;
        texColor += texture(uTex_Sampler[id], fTexCoords + vec2( withPixal * -1, hightPixal *  1)) * -0.1f;

        texColor  = texColor/0.8f;
        float avg = (texColor.r + texColor.b + texColor.g) / 3;

        if(avg < 0.1f){
            int id = int(fTexId);
            color = fColor * texture(uTex_Sampler[id], fTexCoords);
        }
        else{
            color = vec4(1.0f,0.0f,0.0f,1.0f);
        }
        //color = vec4(0.0f,0.0f,0.0f,1.0f);
    } 
    else {
        color = fColor;
    }
}