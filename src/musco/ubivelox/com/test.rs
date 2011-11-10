#pragma version(1)
#pragma rs java_package_name(musco.ubivelox.com)

rs_allocation in;
rs_allocation out;
rs_script script;

const static float3 monoMult = { 0.299f, 0.587f, 0.114f };

void root(const uchar4 *v_in, uchar4 *v_out, const void *userData,
	 	uint32_t x, uint32_t y) {
	 float4 f4 = rsUnpackColor8888(*v_in);
	 
	 float3 mono = dot(f4.rgb, monoMult);
	 *v_out = rsPackColorTo8888(mono); 
}

void filter() {
	rsForEach(script, in, out, 0);
}