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

uniform sampler2D[8] uTex_Sampler;
uniform vec3 lightpos;
uniform vec3 viewPos; 

in vec4 fColor;
in vec2 fTexCoords;
in vec3 fNormal;
in vec3 fPos;

out vec4 color;

void main()
{  
    float ambientStrength = 0.1;
    float specularStrength = 0.5;

    vec3 norm = normalize(fNormal);
    vec3 lightDir = normalize(lightpos - fPos);  
    float diff = max(dot(norm, lightDir), 0.0);
    vec3 diffuse = diff * vec3(1);
    color = fColor * texture(uTex_Sampler[0], vec2(fTexCoords.x,fTexCoords.y ));

    vec3 viewDir = normalize(viewPos - fPos);
    vec3 reflectDir = reflect(-lightDir, norm);  
    float spec = pow(max(dot(viewDir, reflectDir), 0.0), 32);
    vec3 specular = specularStrength * spec * vec3(1); 

    vec3 result = (vec3(ambientStrength)+ diffuse + specular) * vec3(color);
    color = vec4(result,color.a);
}