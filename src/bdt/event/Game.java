package bdt.event;

import java.util.List;

import bdt.tower.TowerState;
import bdt.tower.Tower;
import bdt.enemy.Enemy;

public interface Game extends Capturable
{

public abstract boolean addTower(int towerID, int x, int y);

public abstract Tower removeTower(int x, int y);

public abstract void addTowerState(TowerState storage, int x, int y);

public abstract TowerState getTowerState(int x, int y);

public abstract void removeTowerState(int x, int y);

public abstract void spawnEnemy(Enemy enemy);

public abstract void killEnemy(Enemy enemy);

public abstract List<Enemy> getEnemyList();

}
