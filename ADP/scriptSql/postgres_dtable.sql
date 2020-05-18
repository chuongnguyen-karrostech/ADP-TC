CREATE TABLE if not exists mam.BillingType_ActivityCode(	
	BillingID character varying(4) ,
    StartID int
 );

CREATE TABLE if not exists mam.MAM_Application(
	app_id bigserial,
	app_name character varying(50) ,
	app_version character varying(20) ,
 CONSTRAINT Key11 PRIMARY KEY (app_id)
 );

CREATE TABLE if not exists mam.MAM_Dev_Module(
	dev_module_id bigserial,
	dev_id int ,
	module_id int ,
	dev_module_enable boolean ,
	dev_module_updated timestamp ,
 CONSTRAINT Key8 PRIMARY KEY (dev_module_id )
 );

CREATE TABLE if not exists mam.MAM_Dev_Route_Historical(
	dev_route_id bigserial,
	dev_id int ,
	route_id character varying(20) ,
	route_description character varying(100) ,
	route_beginplan character varying(20) ,
	route_endplan character varying(20) ,
	dev_route_created timestamp ,
 CONSTRAINT Key10 PRIMARY KEY (	dev_route_id )
 );

 CREATE TABLE if not exists mam.MAM_Dev_Run_Historical(
	dev_run_id bigserial,
	dev_route_id bigserial ,
	run_id character varying(20) ,
	run_description character varying(100) ,
	run_beginplan character varying(20) ,
	run_endplan character varying(20) ,
	dev_run_created timestamp ,
 CONSTRAINT KeyRunHist PRIMARY KEY (dev_run_id )
 );
 DO $$
BEGIN
BEGIN
	ALTER TABLE mam.MAM_Dev_Run_Historical ADD CONSTRAINT run_route_historical FOREIGN KEY(dev_route_id) REFERENCES mam.MAM_Dev_Route_Historical (dev_route_id);
  EXCEPTION
    WHEN duplicate_object THEN RAISE NOTICE 'Pass create constraint MAM_Dev_Run_Historical.run_route_historical already exists';
  END;
END $$;




 CREATE TABLE if not exists mam.MAM_Module_Setting(
	module_setting_id bigserial,
	module_id int ,
	setting_id int ,
 CONSTRAINT Key9 PRIMARY KEY (	module_setting_id )
 );

CREATE TABLE if not exists mam.MAM_Modules(
	module_id bigserial,
	module_name character varying(50) ,
	module_description character varying(250) ,
	app_id int ,
 CONSTRAINT Key4 PRIMARY KEY (	module_id )
 );

ALTER TABLE mam.MAM_Modules ADD IF NOT EXISTS dev_module_enable boolean;

CREATE TABLE if not exists mam.MAM_Settings(
	setting_id bigserial,
	app_id int ,
	setting_name character varying(50) ,
	setting_value character varying(250) ,
	setting_description character varying(250) ,
	setting_type character varying(50) ,
	setting_updated timestamp ,
 CONSTRAINT Key5 PRIMARY KEY (	setting_id )
 );

CREATE TABLE if not exists mam.MAM_Sync_Info(
	id bigserial,
	name character varying(100) ,
	type character varying(50) ,
	date_create date ,
	url character varying(200) ,
	file_type character varying(20) ,
 CONSTRAINT PK_MAM_Sync_Info PRIMARY KEY (	id )
 );

CREATE TABLE if not exists mam.MDM_Device_Mode(
	dev_mode_id bigserial,
	dev_id int ,
	mode_id int ,
	dev_bus_id character varying(50) ,
	dev_mode_updated timestamp ,
 CONSTRAINT Key6 PRIMARY KEY (	dev_mode_id )
 );

CREATE TABLE if not exists mam.MDM_Devices(
	dev_id bigserial,
	dev_model character varying(50) ,
	dev_serialnumber character varying(50) ,
	dev_name character varying(50) ,
	dev_macaddress character varying(50) ,
	dev_os character varying(50) ,
	dev_updated timestamp ,
 CONSTRAINT Key1 PRIMARY KEY (	dev_id )
 );

CREATE TABLE if not exists mam.MDM_Modes(
	mode_id bigserial,
	mode_name character varying(50) ,
	mode_description character varying(250) ,
 CONSTRAINT Key2 PRIMARY KEY (	mode_id )
 );

CREATE TABLE if not exists mam.MAM_Default_Module(
   default_module_id bigserial,
   app_id int ,
   module_id int ,
   default_module_created timestamp  ,
   CONSTRAINT KeyDefaultModule PRIMARY KEY ( default_module_id )
   );

CREATE TABLE if not exists mam.MAM_DEV_SyncInfo(
	id bigserial,
	app_id int ,
	dev_id int ,
	sync_date timestamp ,
 CONSTRAINT Key_MAM_DEV_SyncInfo PRIMARY KEY (	id )
 );

CREATE TABLE if not exists mam.MAM_Device_Settings(
	dev_setting_id bigserial,
	app_id int ,
	dev_id int ,
	setting_name character varying(50) ,
	setting_value character varying(250) ,
	setting_description character varying(250) ,
	setting_type character varying(50) ,
	setting_updated timestamp ,
 CONSTRAINT Key_MAM_Device_Settings PRIMARY KEY (dev_setting_id )
 );


ALTER TABLE mam.MAM_Dev_Module ADD IF NOT EXISTS dev_module_default boolean ;

-- Update device module have duplicate dev_module_default
update mam.MAM_Dev_Module set dev_module_default = '0' where dev_id in (select dev_id from mam.MAM_Dev_Module where dev_module_default = '1' group by dev_id having count(dev_id) > 1);

-- Update dev_module_default
update mam.MAM_Dev_Module set dev_module_default = '1' where module_id in
(select module_id from mam.MAM_Default_Module order by default_module_created limit 1) and dev_id not in (select dev_id from mam.MAM_Dev_Module where dev_module_default = '1');

-- Update dev_module_default have dev_module_default is Null or Empty
update mam.MAM_Dev_Module set dev_module_default = '0' where dev_id not in (select dev_id from mam.MAM_Dev_Module where (dev_module_default = '1' Or dev_module_default = '0'));


CREATE TABLE if not exists mam.MAM_Files(
	file_id bigserial,
	file_type character varying(50) ,
	file_version character varying(50) ,
	file_url character varying(250) ,
	file_updated timestamp ,
 CONSTRAINT Key_MAM_Files PRIMARY KEY (	file_id )
 );

CREATE TABLE if not exists mam.DP_Routes(
	routeid bigserial,
	routeNumber character varying(50) ,
	dev_id int ,
	routeDesc character varying(8000) ,
	routeDate timestamp ,
	lastUpdated timestamp ,
 CONSTRAINT Key_DP_Routes PRIMARY KEY (	routeid )
 );

CREATE TABLE if not exists mam.DP_Runs(
	runroute bigserial,
	runid character varying(50) ,
	routeid int ,
	toFrom character varying(50) ,
	beginPlannedTime timestamp ,
	endPlannedTime timestamp ,
	actualRunStart timestamp ,
	actualRunEnd timestamp ,
	runDate timestamp ,
	lastUpdated timestamp ,
 CONSTRAINT Key_DP_Runs PRIMARY KEY (	runroute )
 );

CREATE TABLE if not exists mam.DP_Stops(
	stoprunid bigserial,
	stopid character varying(50) ,
	runroute int ,
	location character varying(8000) ,
	latitude float ,
	longitude float ,
	numberOfStudents int  ,
	stopDesc character varying(8000) ,
	stopTime timestamp ,
	lastUpdated timestamp ,
 CONSTRAINT Key_DP_Stops PRIMARY KEY (	stoprunid )
 );
DO $$
BEGIN
BEGIN
    ALTER TABLE mam.DP_Stops ADD CONSTRAINT run_stop FOREIGN KEY(runroute) REFERENCES mam.DP_Runs (runroute);
  EXCEPTION
    WHEN duplicate_object THEN RAISE NOTICE 'Pass create constraint DP_Stops.run_stop already exists';
  END;
END $$;

DO $$
BEGIN
BEGIN
    ALTER TABLE mam.DP_Runs ADD CONSTRAINT route_run FOREIGN KEY(routeid) REFERENCES mam.DP_Routes (routeid);
  EXCEPTION
    WHEN duplicate_object THEN RAISE NOTICE 'Pass create constraint DP_Runs.route_run already exists';
  END;
END $$;
--ALTER TABLE mam.DP_Stops ADD CONSTRAINT run_stop FOREIGN KEY(runroute) REFERENCES mam.DP_Runs (runroute);
--ALTER TABLE mam.DP_Runs ADD CONSTRAINT route_run FOREIGN KEY(routeid) REFERENCES mam.DP_Routes (routeid);



CREATE TABLE if not exists mam.MAM_Events(
    event_id bigserial,
    event_name character varying(50),
    event_description character varying(250),
    event_updated timestamp,
    CONSTRAINT MAM_Events_pkey PRIMARY KEY (event_id)
);
CREATE TABLE if not exists mam.MAM_Device_Event(
    device_event_id bigserial,
    dev_id int,
    bus_id character varying(50),
    latitude float,
    longitude float,
    event_time timestamp,
    event_send timestamp,
    event_write_tstamp timestamp,
    event_id int,
    event_name character varying(50),
    status character varying(50)) PARTITION BY RANGE (event_write_tstamp);


CREATE TABLE if not exists mam.MAM_Device_Run_Event(
	dev_run_event_id bigserial,
	dev_id int,
	bus_id character varying(50),
	latitude float,
	longitude float,
	event_time timestamp,
	event_send timestamp,
	event_write_tstamp timestamp,
	event_id int,
	event_name character varying(50) ,
	status character varying(50),
	run_id character varying(50) ,
	runroute character varying(50),
	beginactual timestamp ,
	endactual timestamp ,
	earlylate float  ) PARTITION BY RANGE (event_write_tstamp);

CREATE TABLE if not exists mam.MAM_Device_Stop_Event(
	dev_stop_event_id bigserial,
	dev_id int,
	bus_id character varying(50),
	latitude float,
	longitude float,
	event_time timestamp,
	event_send timestamp,
	event_write_tstamp timestamp,
	event_id int,
	event_name character varying(50),
	status character varying(50) ,
	condition character varying(50) ,
	duration float ,
	actualarrival timestamp ,
	actualdeparture timestamp ,
	stoprunid character varying(50),
	run_id character varying(50) ,
	runroute character varying(50) ,
	loadCount int ,
	onBoardStudent int ,
	disembarkStudent int ) PARTITION BY RANGE (event_write_tstamp);

CREATE TABLE if not exists mam.MAM_Device_Student_Event(
	dev_stu_event_id bigserial,
	dev_id int,
	bus_id character varying(50),
	latitude float,
	longitude float,
	event_time timestamp,
	event_send timestamp,
	event_write_tstamp timestamp,
	event_id int,
	event_name character varying(50) ,
	student_id character varying(50),
	student_name character varying(50),
	status character varying(50),
	stoprunid character varying(50),
	run_id character varying(50),
	runroute character varying(50)) PARTITION BY RANGE (event_write_tstamp);

CREATE TABLE if not exists mam.MAM_Device_RawEvent(
	device_rawevent_id bigserial ,
	dev_id int,
	bus_id character varying(50),
	latitude float,
	longitude float,
	event_time timestamp,
	event_send timestamp,
	event_write_tstamp timestamp,
	event_id int,
	event_name character varying(50)) PARTITION BY RANGE (event_write_tstamp);
--minh add tracking error
CREATE TABLE if not exists mam.DevMessage(
	id bigserial ,
	dev_id int,
	modulename character varying(50),
	functionname character varying(50),
	message character varying(20000),
	messagetime timestamp ,
	lastUpdated timestamp
	)PARTITION BY RANGE (lastUpdated);

CREATE OR REPLACE FUNCTION mam.createpartition(livedate date)
    RETURNS boolean
    LANGUAGE 'plpgsql'

AS $BODY$

DECLARE
_tablename text;
_startdate text;
_zipstartdate text;
_enddate text;
_zipenddate text;
_result record;
_sql text;
BEGIN
        _zipstartdate := to_char(livedate, 'YYYYMMDD');
        _zipenddate := to_char(livedate + integer '1', 'YYYYMMDD');
        _startdate := to_char(livedate, 'YYYY-MM-DD');
        _enddate := to_char(livedate + integer '1', 'YYYY-MM-DD');

        EXECUTE 'CREATE TABLE if not exists mam.mam_device_event_' || _zipstartdate || ' partition of mam.mam_device_event for values from (''' || _startdate || ''') to (''' || _enddate || ''');';
        EXECUTE 'create index if not exists index_mam_device_event_tstamp_' || _zipstartdate || ' on mam.mam_device_event_' || _zipstartdate || ' (event_write_tstamp,dev_id);';

        EXECUTE 'CREATE TABLE if not exists mam.mam_device_rawevent_' || _zipstartdate || ' partition of mam.mam_device_rawevent for values from (''' || _startdate || ''') to (''' || _enddate || ''');';
        EXECUTE 'create index if not exists index_mam_device_rawevent_tstamp_' || _zipstartdate || ' on mam.mam_device_rawevent_' || _zipstartdate || ' (event_write_tstamp,dev_id);';

        EXECUTE 'CREATE TABLE if not exists mam.mam_device_run_event_' || _zipstartdate || ' partition of mam.mam_device_run_event for values from (''' || _startdate || ''') to (''' || _enddate || ''');';
        EXECUTE 'create index if not exists index_mam_device_run_event_tstamp_' || _zipstartdate || ' on mam.mam_device_run_event_' || _zipstartdate || ' (event_write_tstamp,dev_id);';

        EXECUTE 'CREATE TABLE if not exists mam.mam_device_stop_event_' || _zipstartdate || ' partition of mam.mam_device_stop_event for values from (''' || _startdate || ''') to (''' || _enddate || ''');';
        EXECUTE 'create index if not exists index_mam_device_stop_event_tstamp_' || _zipstartdate || ' on mam.mam_device_stop_event_' || _zipstartdate || ' (event_write_tstamp,dev_id);';

        EXECUTE 'CREATE TABLE if not exists mam.mam_device_student_event_' || _zipstartdate || ' partition of mam.mam_device_student_event for values from (''' || _startdate || ''') to (''' || _enddate || ''');';
        EXECUTE 'create index if not exists index_mam_device_student_event_tstamp_' || _zipstartdate || ' on mam.mam_device_student_event_' || _zipstartdate || ' (event_write_tstamp,dev_id);';

        EXECUTE 'CREATE TABLE if not exists mam.DevMessage_' || _zipstartdate || ' partition of mam.DevMessage for values from (''' || _startdate || ''') to (''' || _enddate || ''');';
        EXECUTE 'create index if not exists index_DevMessage_tstamp_' || _zipstartdate || ' on mam.DevMessage_' || _zipstartdate || ' (lastUpdated,dev_id);';
        return true;

exception
		when others then
		return false;

END;

$BODY$;
--minh add rundesc for OHA
ALTER TABLE mam.DP_Runs ADD COLUMN IF NOT EXISTS runDesc character varying(8000);

ALTER TABLE mam.BillingType_ActivityCode ADD COLUMN IF NOT EXISTS id bigserial;
ALTER TABLE mam.DP_Routes ADD COLUMN IF NOT EXISTS lastUpdated timestamp;
ALTER TABLE mam.DP_Runs ADD COLUMN IF NOT EXISTS lastUpdated timestamp;
ALTER TABLE mam.DP_Stops ADD COLUMN IF NOT EXISTS lastUpdated timestamp;
--minh add rudirs for OHA
CREATE TABLE if not exists mam.DP_RunDirs(
	rundirsid bigserial ,
	runroute int,
	sequence int,
	latitude float,
	longitude float,
	bus_id character varying(50),
	dev_id int,
	tstamp timestamp ,
	lastUpdated timestamp ,
	CONSTRAINT Key_DP_RunDirs PRIMARY KEY (rundirsid )
	);

DO $$
BEGIN
BEGIN
    ALTER TABLE mam.DP_RunDirs ADD CONSTRAINT run_rundirs FOREIGN KEY(runroute) REFERENCES mam.DP_Runs (runroute);
  EXCEPTION
    WHEN duplicate_object THEN RAISE NOTICE 'Pass create constraint DP_RunDirs.run_rundirs already exists';
  END;
END $$;


--minh add filesize for files
ALTER TABLE mam.mam_files ADD COLUMN IF NOT EXISTS filesize int ;
-- chuong add APIConfig Table
CREATE TABLE if not exists mam.API_Config(
	setting_id bigserial,
	setting_name character varying(50) ,
	setting_value int ,
	setting_updated timestamp ,
 CONSTRAINT Key_API_config PRIMARY KEY ( setting_id )
 );
 -- chuong add Condition Table 07/30/2019
CREATE TABLE if not exists mam.condition(
	condition_id bigserial,
	condition_operator character varying(50) ,
	condition_type character varying(50),
	condition_value character varying(150) ,
	condition_description character varying(100) ,
	condition_updated timestamp ,
 CONSTRAINT Key_condition PRIMARY KEY ( condition_id )
 );
 -- chuong add column condition_id , default_value for mam_settings 07/30/2019
 ALTER TABLE mam.mam_settings ADD COLUMN IF NOT EXISTS condition_id int ;
 ALTER TABLE mam.mam_settings ADD COLUMN IF NOT EXISTS default_value character varying(50) ;
 -- chuong add table check_hardware 09/12/2019
 CREATE TABLE if not exists mam.check_hardware(
	id bigserial,
	name character varying(100) ,
	eventcode int,
	description character varying(150) ,
	critical character varying(50) ,
	updated timestamp ,
 CONSTRAINT Key_chkhardware PRIMARY KEY ( id )
 );
 -- chuong add table punch_log 10/21/2019
CREATE TABLE if not exists mam.punch_log
(
    id bigserial,
    dev_id integer,
    activitycode integer,
    bus_id character varying(50) ,
    driver_id character varying(50) ,
    billing_id character varying(4) ,
    latitude float ,
    longtitude float,
    internal_time timestamp ,
    lastSync_time timestamp ,
    lastSync_type character varying(50),
	lastSync_latitude float ,
    lastSync_longtitude float,
    receive_time timestamp,
    punch_time timestamp,
    send_time timestamp,
    CONSTRAINT key_punchlog PRIMARY KEY (id)
);
-- chuong, add column update_time in table mam_dev_syncinfo for DashBoard 11/15/2019
ALTER TABLE mam.mam_dev_syncinfo ADD COLUMN IF NOT EXISTS update_time timestamp ;
ALTER TABLE mam.api_config ADD COLUMN IF NOT EXISTS description character varying(200) ;
INSERT INTO mam.api_config(setting_name, setting_value, setting_updated) SELECT 'ETL_INTERVAL', 10, now() WHERE NOT EXISTS (SELECT setting_name FROM mam.api_config WHERE setting_name = 'ETL_INTERVAL');
INSERT INTO mam.api_config(setting_name, setting_value, setting_updated,description) SELECT 'SITE_ID', 49 , now(),'Site ID MDM WEB' WHERE NOT EXISTS (SELECT setting_name FROM mam.api_config WHERE setting_name = 'SITE_ID');
ALTER TABLE mam.api_config ALTER COLUMN setting_value TYPE character varying(200) ;
-- chuong, add column busid into table mam_dev_route_historical 02/04/2020
ALTER TABLE mam.mam_dev_route_historical ADD COLUMN IF NOT EXISTS bus_id character varying (50) ;
-- chuong , add  Color theme TABLE , Color by Driver TABLE  02/21/2020
CREATE TABLE if not exists mam.dp_driver_theme
(
    id bigserial,
    driver_id character varying(50) ,
    theme_id integer,
    send_time timestamp,
    CONSTRAINT key_drivercolortheme PRIMARY KEY (id)
);
CREATE TABLE if not exists mam.dp_color_theme
(
    id bigserial,
    theme_id integer,
    color_type character varying(50),
    color_value character varying(50),
    CONSTRAINT key_drivercolor PRIMARY KEY (id)
);
CREATE TABLE if not exists mam.dp_theme
(
    id bigserial,
    name character varying(50),
    is_default boolean,
    CONSTRAINT key_theme PRIMARY KEY (id)
);
-- chuong , add column order into check_hardware table  02/26/2020 --
ALTER TABLE mam.check_hardware ADD COLUMN IF NOT EXISTS check_order integer ;
-- chuong , create table cache for athena receive 03/16/2020
CREATE TABLE if not exists mam.athena_container
(
    id bigserial,
    container character varying(2000) ,
    container_type character varying(50),
    received_time timestamp,
    CONSTRAINT key_athenacontainer PRIMARY KEY (id)
);
-- chuong, add column swipe card type 04/29/2020
 ALTER TABLE mam.mam_device_student_event ADD COLUMN IF NOT EXISTS swipe_card_type integer ;