package bdt.world;

public class BlockPath extends BlockDefault {

	public BlockPath(String file, String name, int id) {
		super(Material.PATH, file, name, id);
	}

	public boolean isPath() {
		return true;
	}

}
