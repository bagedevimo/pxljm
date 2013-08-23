package map;

import goldeneagle.BoundingSphere;
import goldeneagle.util.Perlin;
import goldeneagle.Vec3;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;



public class MapCreator {

	private static double[][] heights = new double[100][100];
	private static Perlin perlin = new Perlin(System.currentTimeMillis());;
	private static Random plantRandom = new Random();
	private static int width = 100;
	private static int height = 100;
	
	public static void setSeed(long seed){
		perlin = new Perlin(seed);
		plantRandom = new Random(seed);
	}
	
	public static void newMap(int w, int h){
		width = w;
		height = h;
		heights = new double[width][height];
	}
	
	public static double[][] getMap(){
		return heights;
	}

	public static void AddPerlinNoise(double f){
		for (int i = 0; i < width; i++){
			for (int j = 0; j < height; j++){
				heights[i][j] += perlin.noise(f * i / (double)width, f * j / (double)height, 0);
			}
		}
	}

	public static void Perturb(double f, double d){
		int u, v;
		double[][] temp = new double[width][height];
		for (int i = 0; i < width; ++i){
			for (int j = 0; j < height; ++j){
				u = i + (int)(perlin.noise(f * i / (double)width, f * j / (double)height, 0) * d);
				v = j + (int)(perlin.noise(f * i / (double)width, f * j / (double)height, 1) * d);
				if (u < 0) u = 0; if (u >= width) u = width - 1;
					if (v < 0) v = 0; if (v >= height) v = height - 1;
						temp[i][j] = heights[u][v];
			}
		}
		heights = temp;
	}

	public static void Erode(double smoothness){
		for (int i = 1; i < width - 1; i++){
			for (int j = 1; j < height - 1; j++){
				double d_max = 0.0;
				int[] match = { 0, 0 };
			
				for (int u = -1; u <= 1; u++){
					for (int v = -1; v <= 1; v++){
						if(Math.abs(u) + Math.abs(v) > 0){
							double d_i = heights[i][j] - heights[i + u][j + v];
							if (d_i > d_max){
								d_max = d_i;
								match[0] = u; match[1] = v;
							}
						}
					}
				}
			
				if(0 < d_max && d_max <= (smoothness / (double)(width+height)/2)){
					double d_h = 0.5f * d_max;
					heights[i][j] -= d_h;
					heights[i + match[0]][j + match[1]] += d_h;
				}
			}
		}
	}

	public static void Smoothen(){
		for (int i = 1; i < width - 1; ++i){
			for (int j = 1; j < height - 1; ++j){
				double total = 0.0f;
				for (int u = -1; u <= 1; u++){
					for (int v = -1; v <= 1; v++){
						total += heights[i + u][j + v];
					}
				}
				heights[i][j] = total / 9.0;
			}
		}
	}

	//edge closer to 0 are reduced to almost 0 while others are expanded
	public static void taperEdges(){
		for (int i = 1; i < width - 1; ++i){
			for (int j = 1; j < height - 1; ++j){
				heights[i][j] = Math.pow(heights[i][j]-0.5, 3) + 0.5;
			}
		}
	}
	
	//desinty bettwen 0 and 1;
	public static void placeTrees(double density){
		int numTrees = (int)(width * height * density);
		ArrayList<BoundingSphere> trees = new ArrayList<BoundingSphere>();
		
		double posX = plantRandom.nextDouble()*width;
		double posY = plantRandom.nextDouble()*height;
		for(int i = 0; i < numTrees; i++){
			trees.add(new BoundingSphere(new Vec3(posX, posY, heights[(int)width][(int)height]), 0.9));
		}
	}

	public static void main(String[] args) {
//	
//		int WIDTH = 400, HIEGHT = 400;
//		double fq = 4.0f;
//		
//		
//		final BufferedImage bi = new BufferedImage(WIDTH, HIEGHT, BufferedImage.TYPE_INT_RGB);
//		Perlin noise = new Perlin(1);
//		
//		MapCreator.newMap(WIDTH, HIEGHT);
//		MapCreator.AddPerlinNoise(6.0f);
//		MapCreator.Perturb(32.0f, 32.0f);
//		for (int i = 0; i < 10; i++ )
//			MapCreator.Erode(16.0f);
//		MapCreator.Smoothen();
//	
//		
//		
//	double[][] map = MapCreator.getMap();
//		
//		for(int y = 0; y<HIEGHT; y++){
//			for(int x = 0; x<WIDTH; x++){
//				bi.setRGB(x, y, (int)((map[x][y]+1)*127));
//				
//				//int color = (int)((noise.getNoise(x/(double)WIDTH, y/(double)HIEGHT, 0, 2)+1)*127);
//				//bi.setRGB(x, y, (int)((noise.getNoise(x/(double)WIDTH, y/(double)HIEGHT, 0, 2)+1)*127));
//				//System.out.printf("%5f ", noise.Noise((fq*x)/(double)WIDTH,(fq* y)/(double)HIEGHT, 0));
//			}
//			//System.out.println();
//		}
//		
//		JFrame j = new JFrame();
//		j.setLayout(new BorderLayout());
//		JPanel pic = new JPanel(){
//		@Override
//		public void paintComponent(Graphics g){
//			g.drawImage(bi, 0, 0, getWidth(), getHeight(), null);
//			}
//		};
//		
//		pic.setPreferredSize(new Dimension(WIDTH, HIEGHT));
//		
//		
//		j.add(pic, BorderLayout.CENTER);
//		j.pack();
//		j.setLocationRelativeTo(null);
//		j.setVisible(true);
//	
	}
}