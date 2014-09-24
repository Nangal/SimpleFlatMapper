package org.sfm.csv.primitive;

import org.sfm.csv.DelayedCellSetter;
import org.sfm.csv.cell.LongCellValueReader;
import org.sfm.reflect.primitive.LongSetter;

public class LongDelayedCellSetter<T> implements DelayedCellSetter<T, Long> {

	private final LongSetter<T> setter;
	private long value;
	
	public LongDelayedCellSetter(LongSetter<T> setter) {
		this.setter = setter;
	}

	@Override
	public Long getValue() {
		return new Long(getLong());
	}
	
	public long getLong() {
		long v = value;
		value = 0;
		return v;
	}

	@Override
	public void set(T t) throws Exception {
		long v = value;
		value = 0;
		setter.setLong(t, v);
	}

	@Override
	public boolean isSettable() {
		return setter != null;
	}

	@Override
	public void set(byte[] bytes, int offset, int length) throws Exception {
		this.value = LongCellValueReader.parseLong(bytes, offset, length);
	}

	@Override
	public void set(char[] chars, int offset, int length) throws Exception {
		this.value = LongCellValueReader.parseLong(chars, offset, length);
	}
}
