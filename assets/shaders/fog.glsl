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

float near = 0.1; 
float far  = 10.0; 
  
float LinearizeDepth(float depth) 
{
    float z = depth * 2.0 - 1.0; // back to NDC 
    return (2.0 * near * far) / (far + near - z * (far - near));	
}

void main()
{             
    float depth = LinearizeDepth(gl_FragCoord.z) / far; // divide by far for demonstration
    color = vec4(vec3(1- depth), 1.0);
}