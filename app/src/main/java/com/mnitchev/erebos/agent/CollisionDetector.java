package com.mnitchev.erebos.agent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CollisionDetector {


    public int collide(Collection<Projectile> projectiles, Agent agent){
        final List<Agent> agents = new ArrayList<>();
        agents.add(agent);
        return collide(projectiles, agents);
    }

    public int collide(Collection<Projectile> projectiles, Collection<Agent> agents){
        int collisions = 0;
        for (Projectile projectile : projectiles) {
            for (Agent agent : agents) {
                if (areIntersecting(projectile, agent)) {
                    agent.takeDamage(projectile.getDamage());
                    projectile.destroy();
                    collisions++;
                }
            }
        }
        return collisions;
    }

    private boolean areIntersecting(Projectile projectile, Agent agent){
        if (projectile.getPosition().x > agent.getPosition().x + agent.getWidth() ||
                agent.getPosition().x > projectile.getPosition().x + projectile.getWidth()){
            return false;
        }

        if (projectile.getPosition().y > agent.getPosition().y + agent.getHeight() ||
                agent.getPosition().y > projectile.getPosition().y + projectile.getHeight()){
            return false;
        }

        return true;
    }

}
