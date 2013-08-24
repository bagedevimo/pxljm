package goldeneagle;

public class Particle {
	
	public double X;
	public double Y;
	
	private double InitalLife;
	public double Life;
	
	public double dX;
	public double dY;
	
	public Particle(int i, int j, int k) {
		this.X = i;
		this.Y = j;
		this.dX = 0;
		this.dY = 0;
		this.InitalLife = k;
		this.Life = k;
	}
	
	public double trans() {
		return this.Life / this.InitalLife;
	}
}
