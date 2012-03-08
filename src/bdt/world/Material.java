package bdt.world;

import java.util.Map;
import java.util.HashMap;

public enum Material {

	NORMAL(0xA), PLANT(0xB), PATH(0xC), WATER(0xD), CUSTOM(0xE);

	private Material(int id) {
		this.id = id;
	}
	
	public static Material getMaterial(int id) {
		return materials.get(id);
	}

	private static final Map<Integer, Material> materials;
	public final int id;

	static {
		materials = new HashMap<Integer, Material>();
		
		for(Material material : Material.values()) {
			materials.put(material.id, material);
		}
		
	}

}
