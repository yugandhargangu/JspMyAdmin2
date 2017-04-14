package com.tracknix.jspmyadmin.framework.connection.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tracknix.jspmyadmin.framework.constants.Constants;
import lombok.Data;

/**
 * @author Yugandhar Gangu
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public final class DataTypes {
    private static final DataTypes DATA_TYPES = new DataTypes();

    /**
     * @return DataTypes
     */
    public static DataTypes getInstance() {
        return DATA_TYPES;
    }

    private final DataType[] datatypes;

    /**
     *
     */
    private DataTypes() {
        this.datatypes = new DataType[5];
        this.init();
    }

    /**
     * @return DataTypeInfo[]
     */
    public DataType[] getDatatypes() {
        return this.datatypes;
    }

    public DataTypeInfo findDataTypeInfo(String id) {
        try {
            int value = Integer.parseInt(id);
            for (DataType dataType : this.datatypes) {
                for (DataTypeInfo dataTypeInfo : dataType.getValue()) {
                    if (dataTypeInfo.id == value) {
                        return dataTypeInfo;
                    }
                }
            }
        } catch (NumberFormatException ignored) {
        }
        return DataTypeInfo.DATA_TYPE_INFO;
    }

    /**
     *
     */
    private void init() {

        DataTypeOptions intOptions = new DataTypeOptions(false, 2, true, true, true, false, "0", false);
        DataTypeOptions floatOptions = new DataTypeOptions(false, 1, false, true, true, false, "0", false);
        DataTypeOptions decimalOptions = new DataTypeOptions(false, 3, false, false, false, false, "0", false);
        DataTypeOptions binaryOptions = new DataTypeOptions(false, 0, false, false, false, false, "0", false);
        DataTypeOptions charOptions = new DataTypeOptions(false, 0, false, false, false, true, "", true);
        DataTypeOptions textOptions = new DataTypeOptions(false, 2, false, false, false, true, "", true);
        DataTypeOptions blobOptions = new DataTypeOptions(false, 2, false, false, false, false, "0", false);
        DataTypeOptions listOptions = new DataTypeOptions(false, -1, false, false, false, false, "-1", true);
        DataTypeOptions dateOptions = new DataTypeOptions(false, -1, false, false, false, false, "0000-00-00", true);
        DataTypeOptions dateTimeOptions = new DataTypeOptions(false, -1, false, false, false, false, "0000-00-00 00:00:00", true);
        DataTypeOptions timeOptions = new DataTypeOptions(false, -1, false, false, false, false, "00:00:00", true);
        DataTypeOptions timestampOptions = new DataTypeOptions(false, -1, false, false, false, false, "CURRENT_TIMESTAMP", true);
        DataTypeOptions yearOptions = new DataTypeOptions(false, -1, false, false, false, false, "0000", true);

        this.datatypes[0] = new DataType("Common", new DataTypeInfo[]{
                new DataTypeInfo(1, "INT", intOptions),
                new DataTypeInfo(2, "VARCHAR", charOptions),
                new DataTypeInfo(3, "DECIMAL", decimalOptions),
                new DataTypeInfo(4, "TIMESTAMP", timestampOptions)
        });
        this.datatypes[1] = new DataType("Numeric", new DataTypeInfo[]{
                new DataTypeInfo(5, "TINYINT", intOptions),
                new DataTypeInfo(6, "SMALLINT", intOptions),
                new DataTypeInfo(7, "MEDIUMINT", intOptions),
                new DataTypeInfo(8, "INT", intOptions),
                new DataTypeInfo(9, "BIGINT", intOptions),
                new DataTypeInfo(10, "FLOAT", floatOptions),
                new DataTypeInfo(11, "DOUBLE", floatOptions),
                new DataTypeInfo(12, "DECIMAL", decimalOptions),
        });
        this.datatypes[2] = new DataType("Bit", new DataTypeInfo[]{
                new DataTypeInfo(14, "BIT", binaryOptions)
        });
        this.datatypes[3] = new DataType("String", new DataTypeInfo[]{
                new DataTypeInfo(15, "CHAR", charOptions),
                new DataTypeInfo(16, "VARCHAR", charOptions),
                new DataTypeInfo(17, "TINYTEXT", textOptions),
                new DataTypeInfo(18, "TEXT", textOptions),
                new DataTypeInfo(19, "MEDIUMTEXT", textOptions),
                new DataTypeInfo(20, "LONGTEXT", textOptions),
                new DataTypeInfo(21, "BINARY", binaryOptions),
                new DataTypeInfo(22, "VARBINARY", binaryOptions),
                new DataTypeInfo(23, "TINYBLOB", blobOptions),
                new DataTypeInfo(24, "BLOB", blobOptions),
                new DataTypeInfo(25, "MEDIUMBLOB", blobOptions),
                new DataTypeInfo(26, "ENUM", listOptions),
                new DataTypeInfo(27, "SET", listOptions)
        });
        this.datatypes[4] = new DataType("Date & Time", new DataTypeInfo[]{
                new DataTypeInfo(28, "DATE", dateOptions),
                new DataTypeInfo(29, "DATETIME", dateTimeOptions),
                new DataTypeInfo(30, "TIME", timeOptions),
                new DataTypeInfo(31, "TIMESTAMP", timestampOptions),
                new DataTypeInfo(32, "YEAR", yearOptions)
        });
    }

    @Data
    public static class DataType {
        private final String key;
        private final DataTypeInfo[] value;

        /**
         * @param key   {@link String}
         * @param value {@link DataTypeInfo}[]
         */
        private DataType(String key, DataTypeInfo[] value) {
            this.key = key;
            this.value = value;
        }
    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class DataTypeInfo {

        private static final DataTypeInfo DATA_TYPE_INFO = new DataTypeInfo(0, Constants.BLANK, null);

        private final int id;
        private final String datatype;
        private final DataTypeOptions datatype_options;


        /**
         * @param id       int
         * @param datatype {@link String}
         */
        private DataTypeInfo(int id, String datatype, DataTypeOptions datatype_options) {
            this.id = id;
            this.datatype = datatype;
            this.datatype_options = datatype_options;
        }

        @Override
        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            if (o instanceof DataTypeInfo) {
                DataTypeInfo dataTypeInfo = (DataTypeInfo) o;
                return dataTypeInfo.id == this.id;
            }
            return false;
        }

        @Override
        public int hashCode() {
            return 31 * 17 + id;
        }
    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class DataTypeOptions {
        private final boolean has_list; // has list
        private final int length; // has length -1:no length, 0:length1 and mandatory, 1:length2 and mandatory, 2:length1 and not mandatory, 3:length2 and not mandatory
        private final boolean ai; // auto increment
        private final boolean us; // unsigned
        private final boolean zf; // zero fill
        private final boolean bin; // binary
        private final String def; // default value -1:list
        private final boolean charset; // character set

        /**
         * @param has_list boolean
         * @param length   int
         * @param ai       boolean
         * @param us       boolean
         * @param zf       boolean
         * @param bin      boolean
         * @param def      {@link String}
         * @param charset  boolean
         */
        private DataTypeOptions(boolean has_list, int length, boolean ai, boolean us, boolean zf, boolean bin, String def, boolean charset) {
            this.has_list = has_list;
            this.length = length;
            this.ai = ai;
            this.us = us;
            this.zf = zf;
            this.bin = bin;
            this.def = def;
            this.charset = charset;
        }
    }
}
