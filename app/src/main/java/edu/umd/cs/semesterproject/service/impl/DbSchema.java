package edu.umd.cs.semesterproject.service.impl;

public class DbSchema {

    public static class RuleTable {

        public static final String NAME = "rules";

        public static final class Columns {

            public static final String ID = "id";
            public static final String NAME = "name";
            public static final String RULE_TYPE = "rule_type";
            public static final String ACTION_TYPE = "action_type";
            public static final String IS_ENABLED = "is_enabled";
        }
    }

    public static final class TimeRuleTable {

        public static final String NAME = "time_rules";

        public static final class Columns {

            public static final String RULE_ID = "rule_id";
            public static final String START_TIME = "start_time";
            public static final String END_TIME = "end_time";
            public static final String DAYS = "days";
        }
    }

    public static final class LocationRuleTable {

        public static final String NAME = "location_rules";

        public static final class Columns {
            public static final String RULE_ID = "rule_id";
            public static final String LOCATION_NAME = "location_name";
            public static final String LATITUDE = "latitude";
            public static final String LONGITUDE = "longitude";
            public static final String RADIUS = "radius";
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

    public static final class WifiTable {

        public static final String NAME = "wifis";

        public static final class Columns {

            public static final String ID = "id";
            public static final String RULE_ID = "rule_id";
            public static final String START_ENABLED = "start_enabled";
            public static final String END_ENABLED = "end_enabled";
        }
    }

    public static final class BluetoothTable {

        public static final String NAME = "bluetooths";

        public static final class Columns {

            public static final String ID = "id";
            public static final String RULE_ID = "rule_id";
            public static final String START_ENABLED = "start_enabled";
            public static final String END_ENABLED = "end_enabled";
        }
    }

    public static final class ReminderTable {

        public static final String NAME = "reminders";

        public static final class Columns {

            public static final String ID = "id";
            public static final String RULE_ID = "rule_id";
            public static final String START_REMINDER = "start_reminder";
            public static final String END_REMINDER = "end_reminder";
        }
    }
}
