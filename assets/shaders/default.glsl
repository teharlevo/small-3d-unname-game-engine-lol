#type vertex
#version 430 core
layout (location=0) in vec3 aPos;
layout (location=1) in vec3 aNormal;
layout (location=2) in vec2 aTexCoords;
layout (location=3) in mat4 Model;
layout (location=7) in vec4 ModelColor;

uniform mat4 uView;
uniform mat4 uProjection;

out vec4 fColor;
out vec2 fTexCoords;
out vec3 fNormal;
out vec3 fPos;

void main()
{
    fPos = vec3(Model * vec4(aPos, 1.0));
    fColor = ModelColor;
    fTexCoords = aTexCoords;
    fNormal = mat3(transpose(inverse(Model))) * aNormal;  
    gl_Position = uProjection * uView * Model * vec4(aPos, 1.0);
}

#type fragment
#version 430 core

struct Material {
    vec3 ambient;
    vec3 diffuse;
    vec3 specular;
    float shininess;
}; 

struct Light {
    vec3 position;
    vec3 ambient;
    vec3 diffuse;
    vec3 specular;
}

uniform sampler2D[1] uTex_Sampler;
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

    vec3 ambient = material.ambient;

    vec3 norm = normalize(fNormal);
    vec3 lightDir = normalize(lightpos - fPos);  
    float diff = max(dot(norm, lightDir), 0.0);
    vec3 diffuse = diff * material.diffuse;
    color = fColor * texture(uTex_Sampler[0], vec2(fTexCoords.x,fTexCoords.y ));

    vec3 viewDir = normalize(viewPos - fPos);
    vec3 reflectDir = reflect(-lightDir, norm);  
    float spec = pow(max(dot(viewDir, reflectDir), 0.0), material.shininess);
    vec3 specular = spec * material.specular; 

    vec3 result = (ambient + diffuse + specular) * vec3(color);
    color = vec4(result,color.a);
}