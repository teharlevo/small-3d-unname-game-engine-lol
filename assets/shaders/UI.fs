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

void main()
{
    color = fColor * texture(texture, fTexCoords);
}