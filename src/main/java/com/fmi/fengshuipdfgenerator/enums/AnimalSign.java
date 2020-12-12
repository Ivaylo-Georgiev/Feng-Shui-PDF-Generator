package com.fmi.fengshuipdfgenerator.enums;

public enum AnimalSign {
	RAT("src\\main\\resources\\rat.png"), OX("src\\main\\resources\\ox.png"), TIGER(
			"src\\main\\resources\\tiger.png"), RABBIT("src\\main\\resources\\rabbit.png"), DRAGON(
					"src\\main\\resources\\dragon.png"), SNAKE("src\\main\\resources\\snake.png"), HORSE(
							"src\\main\\resources\\horse.png"), SHEEP("src\\main\\resources\\sheep.png"), MONKEY(
									"src\\main\\resources\\monkey.png"), ROOSTER(
											"src\\main\\resources\\rooster.png"), DOG(
													"src\\main\\resources\\dog.png"), PIG(
															"src\\main\\resources\\pig.png");

	private String imagePath;

	private AnimalSign(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getImagePath() {
		return this.imagePath;
	}
}
