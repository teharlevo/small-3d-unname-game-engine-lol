#type compute
#version 430 core

layout(local_size_x = 8, local_size_y = 4, local_size_z = 1) in;
layout(rgba32f, binding = 0) uniform image2D colorbuffer;

void main() {

    ivec2 pixelPos = ivec2(gl_GlobalInvocationID.xy);
    ivec2 screen_size = imageSize(colorbuffer);
    if (pixelPos.x >= screen_size.x || pixelPos.y >= screen_size.y) {
        return;
    }
    
    imageStore(colorbuffer, pixelPos, vec4(0.0, 0.75, 0.5, 1.0));
}