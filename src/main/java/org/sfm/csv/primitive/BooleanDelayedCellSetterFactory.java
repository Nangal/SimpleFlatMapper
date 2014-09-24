package org.sfm.csv.primitive;

import org.sfm.csv.DelayedCellSetter;
import org.sfm.csv.DelayedCellSetterFactory;
import org.sfm.reflect.primitive.BooleanSetter;

public class BooleanDelayedCellSetterFactory<T> implements DelayedCellSetterFactory<T, Boolean> {

	private final BooleanSetter<T> setter;
	
	public BooleanDelayedCellSetterFactory(BooleanSetter<T> setter) {
		this.setter = setter;
	}

	@Override
	public DelayedCellSetter<T, Boolean> newCellSetter() {
		return new BooleanDelayedCellSetter<T>(setter);
	}


}
