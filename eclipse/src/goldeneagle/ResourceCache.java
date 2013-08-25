package goldeneagle;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.opengl.ARBFragmentShader;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.ARBVertexShader;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL14;

public class ResourceCache {
	private static final Map<String, ResourceInfo> textures = new HashMap<String, ResourceInfo>();
	private static final Map<String, Integer> shaders = new HashMap<String, Integer>();
	private static final Map<String, Integer> programs = new HashMap<String, Integer>();
	
	public static void AddTexture(String path, ByteBuffer texture, int width, int height) {
		if(textures.containsKey(path))
			return;
		
		textures.put(path, new ResourceInfo(path, texture, width, height));
	}
	
	public static int GetGLTexture(String path) throws Exception {
		if(!textures.containsKey(path))
			throw new Exception("Asset not found: " + path);
		
		ResourceInfo ri = textures.get(path);
		if(!ri.isGLReady())
		{
			ri.setTextureID(bindGLTexture(ri));
		}
		
		return ri.getTextureID();
					
	}
	
	private static int bindGLTexture(ResourceInfo res) {
		int textureID = glGenTextures(); //Generate texture ID
		glBindTexture(GL_TEXTURE_2D, textureID); //Bind texture ID
		
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
		
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		
		glTexParameteri(GL_TEXTURE_2D, GL12.GL_TEXTURE_MAX_LEVEL, 10);
		glTexParameteri(GL_TEXTURE_2D, GL14.GL_GENERATE_MIPMAP, GL_TRUE);
		
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, res.getWidth(), res.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, res.getBuffer());
		
		return textureID;
	}

	public static void AddShader(String path, String fileString, boolean isFragment) {		
		if(shaders.containsKey(path))
			return;
		
		int shader = 0;
		
		try {
			if(!isFragment)
				shader = ARBShaderObjects.glCreateShaderObjectARB(ARBVertexShader.GL_VERTEX_SHADER_ARB);
			else
				shader = ARBShaderObjects.glCreateShaderObjectARB(ARBFragmentShader.GL_FRAGMENT_SHADER_ARB);
			
			if(shader == 0) {
				System.out.println("Unable to create shader object");
				return;
			}
			
			ARBShaderObjects.glShaderSourceARB(shader, fileString);
			ARBShaderObjects.glCompileShaderARB(shader);
			
			if(ARBShaderObjects.glGetObjectParameteriARB(shader,  ARBShaderObjects.GL_OBJECT_COMPILE_STATUS_ARB) == GL11.GL_FALSE) {
				System.out.println("unable to compile shader");
				return;
			}
		} catch(Exception ex) {
			ARBShaderObjects.glDeleteObjectARB(shader);
			System.out.println("couldn't load the shader");
			ex.printStackTrace();
		}
		
		shaders.put(path, shader);
	}
	
	public static void AddShaderProgram(String name) {
		String path = "assets/shaders/" + name;
		
		try {
			AddShader(path + ".vert", readFileAsString(path + ".vert"), false);
			AddShader(path + ".frag", readFileAsString(path + ".frag"), true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static String readFileAsString(String filename) throws Exception {
        StringBuilder source = new StringBuilder();
        
        FileInputStream in = new FileInputStream(filename);
        
        Exception exception = null;
        
        BufferedReader reader;
        try{
            reader = new BufferedReader(new InputStreamReader(in,"UTF-8"));
            
            Exception innerExc= null;
            try {
            	String line;
                while((line = reader.readLine()) != null)
                    source.append(line).append('\n');
            }
            catch(Exception exc) {
            	exception = exc;
            }
            finally {
            	try {
            		reader.close();
            	}
            	catch(Exception exc) {
            		if(innerExc == null)
            			innerExc = exc;
            		else
            			exc.printStackTrace();
            	}
            }
            
            if(innerExc != null)
            	throw innerExc;
        }
        catch(Exception exc) {
        	exception = exc;
        }
        finally {
        	try {
        		in.close();
        	}
        	catch(Exception exc) {
        		if(exception == null)
        			exception = exc;
        		else
					exc.printStackTrace();
        	}
        	
        	if(exception != null)
        		throw exception;
        }
        
        return source.toString();
    }

	
	public static int GetProgram(String name) throws Exception {
		if(!programs.containsKey(name)) {			
			
			if(!(shaders.containsKey("assets/shaders/" + name + ".vert") && shaders.containsKey("assets/shaders/" + name + ".frag")))
				throw new Exception("Assets not found");
			
			int program = 0;
			program = ARBShaderObjects.glCreateProgramObjectARB();
			if(program == 0)
				throw new Exception("Can't create shader program");
			
			
			
			ARBShaderObjects.glAttachObjectARB(program, shaders.get("assets/shaders/" + name + ".vert"));
			ARBShaderObjects.glAttachObjectARB(program, shaders.get("assets/shaders/" + name + ".frag"));
			
			ARBShaderObjects.glLinkProgramARB(program);
			if (ARBShaderObjects.glGetObjectParameteriARB(program, ARBShaderObjects.GL_OBJECT_LINK_STATUS_ARB) == GL11.GL_FALSE) {
				System.err.println(getLogInfo(program));
				throw new Exception("Unable to link program");
			}
			
			ARBShaderObjects.glValidateProgramARB(program);
			if (ARBShaderObjects.glGetObjectParameteriARB(program, ARBShaderObjects.GL_OBJECT_VALIDATE_STATUS_ARB) == GL11.GL_FALSE) {
				System.err.println(getLogInfo(program));
				throw new Exception("Unable to validate program");
			}
			
			programs.put(name, program);
		}
		return programs.get(name);
	}
	
	private static String getLogInfo(int obj) {
		return ARBShaderObjects.glGetInfoLogARB(obj, ARBShaderObjects.glGetObjectParameteriARB(obj, ARBShaderObjects.GL_OBJECT_INFO_LOG_LENGTH_ARB));
	}
}
