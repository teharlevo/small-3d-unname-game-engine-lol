#version 430 core

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
    color = fColor * texture(texture, vec2(fTexCoords.x,fTexCoords.y ));
    vec3 ambient = light.ambient * color.xyz;

    vec3 norm = normalize(fNormal);
    vec3 lightDir = normalize(light.pos - fPos);  
    float diff = max(dot(norm, lightDir), 0.0);
    vec3 diffuse = diff * light.diffuse * color.xyz;

    vec3 viewDir = normalize(viewPos - fPos);
    vec3 reflectDir = reflect(-lightDir, norm);  
    float spec = pow(max(dot(viewDir, reflectDir), 0.0), material.shininess);
    vec3 specular = light.specular * spec 
    * texture(material.specular, vec2(fTexCoords.x,fTexCoords.y)).rgb * fColor.rgb; 

    vec3 result = (ambient + diffuse + specular);
    color = vec4(result,color.a);
}