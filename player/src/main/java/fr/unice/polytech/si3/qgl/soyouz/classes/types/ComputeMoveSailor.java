package fr.unice.polytech.si3.qgl.soyouz.classes.types;

import fr.unice.polytech.si3.qgl.soyouz.classes.marineland.Marin;
import fr.unice.polytech.si3.qgl.soyouz.classes.marineland.entities.onboard.Gouvernail;
import fr.unice.polytech.si3.qgl.soyouz.classes.marineland.entities.onboard.OnboardEntity;
import fr.unice.polytech.si3.qgl.soyouz.classes.marineland.entities.onboard.Rame;
import fr.unice.polytech.si3.qgl.soyouz.classes.utilities.Util;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class ComputeMoveSailor {
	private final Marin sailor;
	private final Set<? extends OnboardEntity> entities;

	public ComputeMoveSailor(Marin sailor, Collection<? extends OnboardEntity> entities) {
		this.sailor = sailor;
		this.entities = entities.stream().filter(ent -> sailor.isAbsPosReachable(ent.getPos())).collect(Collectors.toSet());
	}

	public Marin getSailor() {
		return sailor;
	}

	public Set<Rame> getOars(){
		return Util.filterType(entities.stream(), Rame.class).collect(Collectors.toSet());
	}

	public Set<? extends OnboardEntity> getEntities() {
		return Collections.unmodifiableSet(entities);
	}

	public Set<? extends OnboardEntity> getLonelyEntities(){
		return Util.filterType(entities.stream(), Gouvernail.class).collect(Collectors.toSet());
	}
}
