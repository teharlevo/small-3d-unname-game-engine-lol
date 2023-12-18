#version 330 core

struct Material {
    sampler2D specular; 
    float shininess;
}; 

struct Light {
    vec3 pos;
    vec3 ambient;
    vec3 diffuse;
    vec3 specular;
};

uniform sampler2D texture;
uniform vec3 viewPos; 
uniform Material material;
uniform Light light;

in vec4 fColor;
in vec2 fTexCoords;
in vec3 fNormal;
in vec3 fPos;

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