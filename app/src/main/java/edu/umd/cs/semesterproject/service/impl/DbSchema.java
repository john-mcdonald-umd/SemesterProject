package edu.umd.cs.semesterproject.service.impl;

public class DbSchema {

    public static class RuleTable {

        public static final String NAME = "rules";

        public static final class Columns {

            public static final String ID = "id";
            public static final String NAME = "name";
            public static final String IS_ENABLED = "is_enabled";
        }
    }

    public static final class TimeRuleTable {

        public static final String NAME = "time_rules";

        public static final class Columns {

            public static final String ID = "id";
            public static final String RULE_ID = "rule_id";
            public static final String START_TIME = "start_time";
            public static final String END_TIME = "end_time";
        }
    }

    public static final class VolumeTable {

        public static final String NAME = "volumes";

        public static final class Columns {

            public static final String ID = "id";
            public static final String RULE_ID = "rule_id";
            public static final String START_VOLUME = "start_volume";
            public static final String END_VOLUME = "end_volume";
            public static final String START_MODE = "start_mode";
            public static final String END_MODE = "end_mode";
        }
    }
}
