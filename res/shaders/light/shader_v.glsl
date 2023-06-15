[Vertex_Shader]
#version 420

uniform mat4 in_modelview;
uniform mat4 in_projection;
uniform float tick;

in vec3 in_position;
in vec3 in_normal;
in vec2 in_tex_coord;
in float in_blend;

out vec3 normal;
out vec2 texcoords;
out float coef;

void main() {
	coef = in_blend;
	texcoords = in_tex_coord;
	normal = vec3(in_normal.x * cos(tick*100), in_normal.y * sin(tick), in_normal.z);
	//vec3 pos = vec3(in_position.x, in_position.y, in_position.z + 5*sin(cos(in_position.x) + cos(in_position.y) + tick));
	gl_Position = vec4(in_position, 1) * in_modelview * in_projection ;
}

