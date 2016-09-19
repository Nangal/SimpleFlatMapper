package org.simpleflatmapper.jooq;

import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record2;
import org.jooq.Row2;
import org.jooq.SQLDialect;
import org.jooq.impl.CustomField;
import org.jooq.impl.CustomTable;
import org.jooq.impl.DefaultDataType;
import org.jooq.impl.UpdatableRecordImpl;
import org.junit.Test;
import org.simpleflatmapper.reflect.ReflectionService;

public class Issue337Test {

    @Test
    public void testGetClassMeta() {

        ReflectionService.newInstance().getClassMeta(GlobalMapRecord.class);

    }



    /**
     * This class is generated by jOOQ.
     */
    public class GlobalMapRecord extends UpdatableRecordImpl<GlobalMapRecord> implements Record2<String, String> {

        private static final long serialVersionUID = 857041357;

        /**
         * Setter for <code>ivdb_2.global_map.map_key</code>.
         */
        public void setMapKey(String value) {
            set(0, value);
        }

        /**
         * Getter for <code>ivdb_2.global_map.map_key</code>.
         */
        public String getMapKey() {
            return (String) get(0);
        }

        /**
         * Setter for <code>ivdb_2.global_map.value</code>.
         */
        public void setValue(String value) {
            set(1, value);
        }

        /**
         * Getter for <code>ivdb_2.global_map.value</code>.
         */
        public String getValue() {
            return (String) get(1);
        }

        // -------------------------------------------------------------------------
        // Primary key information
        // -------------------------------------------------------------------------

        /**
         * {@inheritDoc}
         */
        @Override
        public Record1<String> key() {
            return (Record1) super.key();
        }

        // -------------------------------------------------------------------------
        // Record2 type implementation
        // -------------------------------------------------------------------------

        /**
         * {@inheritDoc}
         */
        @Override
        public Row2<String, String> fieldsRow() {
            return (Row2) super.fieldsRow();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Row2<String, String> valuesRow() {
            return (Row2) super.valuesRow();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Field<String> field1() {
            return GlobalMap.GLOBAL_MAP.MAP_KEY;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Field<String> field2() {
            return GlobalMap.GLOBAL_MAP.MAP_VALUE;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String value1() {
            return getMapKey();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String value2() {
            return getValue();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public GlobalMapRecord value1(String value) {
            setMapKey(value);
            return this;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public GlobalMapRecord value2(String value) {
            setValue(value);
            return this;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public GlobalMapRecord values(String value1, String value2) {
            value1(value1);
            value2(value2);
            return this;
        }

        // -------------------------------------------------------------------------
        // Constructors
        // -------------------------------------------------------------------------

        /**
         * Create a detached GlobalMapRecord
         */
        public GlobalMapRecord() {
            super(GlobalMap.GLOBAL_MAP);
        }

        /**
         * Create a detached, initialised GlobalMapRecord
         */
        public GlobalMapRecord(String mapKey, String value) {
            super(GlobalMap.GLOBAL_MAP);

            set(0, mapKey);
            set(1, value);
        }
    }

    public static class GlobalMap extends CustomTable<GlobalMapRecord> {
        public static final GlobalMap GLOBAL_MAP = new GlobalMap("Table");

        protected GlobalMap(String name) {
            super(name);
        }

        @Override
        public Class<? extends GlobalMapRecord> getRecordType() {
            return GlobalMapRecord.class;
        }

        public final Field<String> MAP_KEY = new CustomField<String>("key", new DefaultDataType<String>(SQLDialect.DEFAULT, String.class, "varchar")) {
            @Override
            public void accept(Context<?> context) {
            }
        };
        public final Field<String> MAP_VALUE = new CustomField<String>("key", new DefaultDataType<String>(SQLDialect.DEFAULT, String.class, "varchar")) {
            @Override
            public void accept(Context<?> context) {
            }
        };
    }

}
