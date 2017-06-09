#version 430 core

layout (location = 0) in vec3 vc;
layout (location = 1) in vec2 tc;
layout (location = 3) uniform mat4 mv;
layout (location = 4) uniform mat4 pr;

out DATA
{
	vec2 tc;
} vs_out;

void main(void)
{
	gl_Position = pr * mv * vec4(vc, 1.0f);
	vs_out.tc = tc;
}