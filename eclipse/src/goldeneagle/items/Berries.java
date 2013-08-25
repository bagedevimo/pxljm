package goldeneagle.items;

import goldeneagle.entities.PlayerEntity;

public class Berries extends Item{

	private static final double nutritionAmount = 5;
	
	public Berries() {
		texturePath = "./assets/entities/berrys.png";
	}
	
	@Override
	public void use(PlayerEntity player) {
		player.modifyNutrition(nutritionAmount);
	}

}

