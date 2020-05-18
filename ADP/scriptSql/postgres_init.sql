INSERT INTO mam.MDM_Modes ( mode_name, mode_description) SELECT 'Floating', 'Floating mode' WHERE NOT EXISTS (SELECT mode_name FROM mam.MDM_Modes WHERE mode_name = 'Floating');
INSERT INTO mam.MDM_Modes ( mode_name, mode_description) SELECT 'Permanently Mounted', 'Permanently mounted mode' WHERE NOT EXISTS (SELECT mode_name FROM mam.MDM_Modes WHERE mode_name = 'Permanently Mounted');

ALTER SEQUENCE mam.mam_application_app_id_seq RESTART WITH 1;
INSERT INTO mam.MAM_Application ( app_name, app_version) SELECT 'Driver portal', '2.0.7' WHERE NOT EXISTS (SELECT app_name FROM mam.MAM_Application WHERE app_name = 'Driver portal');

INSERT INTO mam.MAM_Modules ( module_name, module_description, app_id) SELECT 'Route Builder', 'Route build', 1 WHERE NOT EXISTS (SELECT module_name FROM mam.MAM_Modules WHERE module_name = 'Route Builder');
INSERT INTO mam.MAM_Modules ( module_name, module_description, app_id) SELECT 'eDTA', 'Driver Time Attendance', 1 WHERE NOT EXISTS (SELECT module_name FROM mam.MAM_Modules WHERE module_name = 'eDTA');
INSERT INTO mam.MAM_Modules ( module_name, module_description, app_id) SELECT 'Select Route', 'Select Route', 1 WHERE NOT EXISTS (SELECT module_name FROM mam.MAM_Modules WHERE module_name = 'Select Route');
INSERT INTO mam.MAM_Modules ( module_name, module_description, app_id) SELECT 'Driving Mode', 'Driving Mode', 1 WHERE NOT EXISTS (SELECT module_name FROM mam.MAM_Modules WHERE module_name = 'Driving Mode');
INSERT INTO mam.MAM_Modules ( module_name, module_description, app_id) SELECT 'Assigned Route', 'Assigned Route', 1 WHERE NOT EXISTS (SELECT module_name FROM mam.MAM_Modules WHERE module_name = 'Assigned Route');
INSERT INTO mam.MAM_Modules ( module_name, module_description, app_id) SELECT 'Additional Employee', 'Additional Employee', 1 WHERE NOT EXISTS (SELECT module_name FROM mam.MAM_Modules WHERE module_name = 'Additional Employee');
INSERT INTO mam.MAM_Modules ( module_name, module_description, app_id) SELECT 'Switch Session Owner', 'Switch Session Owner', 1 WHERE NOT EXISTS (SELECT module_name FROM mam.MAM_Modules WHERE module_name = 'Switch Session Owner');
INSERT INTO mam.MAM_Modules ( module_name, module_description, app_id) SELECT 'Settings', 'Settings', 1 WHERE NOT EXISTS (SELECT module_name FROM mam.MAM_Modules WHERE module_name = 'Settings');

INSERT INTO mam.MAM_Settings ( app_id, setting_name, setting_value, setting_description, setting_type, setting_updated) SELECT 1, 'TRANSMISSION_FREQUENCY', '10', 'TRANSMISSION_FREQUENCY', 'All', now() WHERE NOT EXISTS (SELECT setting_name FROM mam.MAM_Settings WHERE setting_name = 'TRANSMISSION_FREQUENCY');
INSERT INTO mam.MAM_Settings ( app_id, setting_name, setting_value, setting_description, setting_type, setting_updated) SELECT 1, 'STOP_RADIUS', '10', 'STOP_RADIUS', 'All', now() WHERE NOT EXISTS (SELECT setting_name FROM mam.MAM_Settings WHERE setting_name = 'STOP_RADIUS');
INSERT INTO mam.MAM_Settings ( app_id, setting_name, setting_value, setting_description, setting_type, setting_updated) SELECT 1, 'NO_MOTION_SPEED_THRESHOLD', '5', 'NO_MOTION_SPEED_THRESHOLD', 'All', now() WHERE NOT EXISTS (SELECT setting_name FROM mam.MAM_Settings WHERE setting_name = 'NO_MOTION_SPEED_THRESHOLD');
INSERT INTO mam.MAM_Settings ( app_id, setting_name, setting_value, setting_description, setting_type, setting_updated) SELECT 1, 'NO_MOTION_TIME_THRESHOLD', '30', 'NO_MOTION_TIME_THRESHOLD', 'All', now() WHERE NOT EXISTS (SELECT setting_name FROM mam.MAM_Settings WHERE setting_name = 'NO_MOTION_TIME_THRESHOLD');
INSERT INTO mam.MAM_Settings ( app_id, setting_name, setting_value, setting_description, setting_type, setting_updated) SELECT 1, 'AUDIO_IN_MOTION', 'ON', 'AUDIO_IN_MOTION', 'All', now() WHERE NOT EXISTS (SELECT setting_name FROM mam.MAM_Settings WHERE setting_name = 'AUDIO_IN_MOTION');
INSERT INTO mam.MAM_Settings ( app_id, setting_name, setting_value, setting_description, setting_type, setting_updated) SELECT 1, 'VIDEO_IN_MOTION', 'OFF', 'VIDEO_IN_MOTION', 'All', now() WHERE NOT EXISTS (SELECT setting_name FROM mam.MAM_Settings WHERE setting_name = 'VIDEO_IN_MOTION');
INSERT INTO mam.MAM_Settings (app_id,setting_name,setting_type,setting_updated,setting_value,setting_description) SELECT 1,'LIMIT_NUMBER_OF_DEVICES','All',now(),300,'Limit number of devices' WHERE NOT EXISTS (SELECT setting_name FROM mam.MAM_Settings WHERE setting_name = 'LIMIT_NUMBER_OF_DEVICES');
INSERT INTO mam.MAM_Settings ( app_id, setting_name, setting_value, setting_description, setting_type, setting_updated) SELECT 1, 'IDLE_TIME_THRESHOLD', '300', 'IDLE_TIME_THRESHOLD', 'All', now() WHERE NOT EXISTS (SELECT setting_name FROM mam.MAM_Settings WHERE setting_name = 'IDLE_TIME_THRESHOLD');
INSERT INTO mam.MAM_Settings ( app_id, setting_name, setting_value, setting_description, setting_type, setting_updated) SELECT 1, 'SCHOOL_RADIUS', '30', 'SCHOOL_RADIUS', 'All', now() WHERE NOT EXISTS (SELECT setting_name FROM mam.MAM_Settings WHERE setting_name = 'SCHOOL_RADIUS');
INSERT INTO mam.MAM_Settings ( app_id, setting_name, setting_value, setting_description, setting_type, setting_updated) SELECT 1, 'DISPLAY_UNITS', 'IMPERIAL', 'DISPLAY_UNITS', 'All', now() WHERE NOT EXISTS (SELECT setting_name FROM mam.MAM_Settings WHERE setting_name = 'DISPLAY_UNITS');
INSERT INTO mam.MAM_Settings ( app_id, setting_name, setting_value, setting_description, setting_type, setting_updated) SELECT 1, 'KIOSK_MODE', 'ON', 'KIOSK_MODE', 'All', now() WHERE NOT EXISTS (SELECT setting_name FROM mam.MAM_Settings WHERE setting_name = 'KIOSK_MODE');
INSERT INTO mam.MAM_Settings ( app_id, setting_name, setting_value, setting_description, setting_type, setting_updated) SELECT 1, 'MATCHTIMEWINDOWINSECONDS', '1800', 'MatchTimeWindowInSeconds', 'All', now() WHERE NOT EXISTS (SELECT setting_name FROM mam.MAM_Settings WHERE setting_name = 'MATCHTIMEWINDOWINSECONDS');
INSERT INTO mam.MAM_Settings ( app_id, setting_name, setting_value, setting_description, setting_type, setting_updated) SELECT 1, 'AUTOSWIPECARDSATSCHOOL', 'ON', 'Auto swipe cards at school', 'All', now() WHERE NOT EXISTS (SELECT setting_name FROM mam.MAM_Settings WHERE setting_name = 'AUTOSWIPECARDSATSCHOOL');
INSERT INTO mam.MAM_Settings ( app_id, setting_name, setting_value, setting_description, setting_type, setting_updated) SELECT 1, 'CEILING_TIME_VALUE', '06:00', 'CEILING_TIME_VALUE', 'All', now() WHERE NOT EXISTS (SELECT setting_name FROM mam.MAM_Settings WHERE setting_name = 'CEILING_TIME_VALUE');


Insert into mam.MAM_Default_Module(app_id,module_id,default_module_created)  SELECT 1, 2, now() WHERE NOT EXISTS (SELECT module_id FROM mam.MAM_Default_Module);

insert into mam.MAM_Dev_Module(dev_id,module_id,dev_module_enable,dev_module_updated,dev_module_default)
select d.dev_id,m.module_id, true as dev_module_enable, now() as dev_module_updated, false as dev_module_default
from mam.MDM_Devices d, mam.MAM_Modules m where m.module_name<>'Settings'
except
select dev_id,module_id, true as dev_module_enable, now() as dev_module_updated, false as dev_module_default
from mam.MAM_Dev_Module  ;

insert into mam.MAM_Dev_Module (dev_id,module_id,dev_module_enable,dev_module_updated,dev_module_default)
select d.dev_id,m.module_id, false as dev_module_enable, now() as dev_module_updated, false as dev_module_default
from mam.MDM_Devices d, mam.MAM_Modules m where m.module_name ='Settings'
except
select dev_id,module_id,false as dev_module_enable, now() as dev_module_updated, false as dev_module_default
from mam.MAM_Dev_Module ;

/*insert into mam.MAM_Device_Settings ( app_id, dev_id, setting_name, setting_value, setting_description, setting_type, setting_updated) 
select app_id, dev_id, setting_name, setting_value, setting_description, setting_type, now() as  setting_updated
from mam.MDM_Devices, mam.MAM_Settings where setting_name in ('NO_MOTION_SPEED_THRESHOLD', 'NO_MOTION_TIME_THRESHOLD', 'KIOSK_MODE')
except select app_id, dev_id, setting_name, setting_value, setting_description, setting_type, now() as  setting_updated
from mam.MAM_Device_Settings where setting_name in ('NO_MOTION_SPEED_THRESHOLD', 'NO_MOTION_TIME_THRESHOLD', 'KIOSK_MODE')
order by dev_id, setting_name;*/

--ALTER SEQUENCE mam.mam_events_event_id_seq RESTART WITH 101;
SELECT setval('mam.mam_events_event_id_seq', COALESCE((SELECT MAX(event_id)+1 FROM mam.MAM_Events), 1), false);
INSERT INTO mam.MAM_Events(event_name,event_description,event_updated) SELECT 'RAW_EVENT','Live Cookie',now() WHERE NOT EXISTS (SELECT event_name FROM mam.MAM_Events WHERE event_name = 'RAW_EVENT');
INSERT INTO mam.MAM_Events(event_name,event_description,event_updated) SELECT 'START_RUN','Start Run',now() WHERE NOT EXISTS (SELECT event_name FROM mam.MAM_Events WHERE event_name = 'START_RUN');
INSERT INTO mam.MAM_Events(event_name,event_description,event_updated) SELECT 'END_RUN','End Run',now() WHERE NOT EXISTS (SELECT event_name FROM mam.MAM_Events WHERE event_name = 'END_RUN');
INSERT INTO mam.MAM_Events(event_name,event_description,event_updated) SELECT 'NO_MOTION_BEGIN','No Motion Begin',now() WHERE NOT EXISTS (SELECT event_name FROM mam.MAM_Events WHERE event_name = 'NO_MOTION_BEGIN');
INSERT INTO mam.MAM_Events(event_name,event_description,event_updated) SELECT 'NO_MOTION_END','No Motion End',now() WHERE NOT EXISTS (SELECT event_name FROM mam.MAM_Events WHERE event_name = 'NO_MOTION_END');
INSERT INTO mam.MAM_Events(event_name,event_description,event_updated) SELECT 'STOP_BEGIN','Stop Begin',now() WHERE NOT EXISTS (SELECT event_name FROM mam.MAM_Events WHERE event_name = 'STOP_BEGIN');
INSERT INTO mam.MAM_Events(event_name,event_description,event_updated) SELECT 'STOP_END','Stop End',now() WHERE NOT EXISTS (SELECT event_name FROM mam.MAM_Events WHERE event_name = 'STOP_END');
INSERT INTO mam.MAM_Events(event_name,event_description,event_updated) SELECT 'EXCESSIVE_IDLE_BEGIN','Excessive Idle Begin',now() WHERE NOT EXISTS (SELECT event_name FROM mam.MAM_Events WHERE event_name = 'EXCESSIVE_IDLE_BEGIN');
INSERT INTO mam.MAM_Events(event_name,event_description,event_updated) SELECT 'EXCESSIVE_IDLE_END','Excessive Idle End',now() WHERE NOT EXISTS (SELECT event_name FROM mam.MAM_Events WHERE event_name = 'EXCESSIVE_IDLE_END');
INSERT INTO mam.MAM_Events(event_name,event_description,event_updated) SELECT 'STUDENT_BOARD','Student Board',now() WHERE NOT EXISTS (SELECT event_name FROM mam.MAM_Events WHERE event_name = 'STUDENT_BOARD');
INSERT INTO mam.MAM_Events(event_name,event_description,event_updated) SELECT 'STUDENT_DISEMBARK','Student Disembark',now() WHERE NOT EXISTS (SELECT event_name FROM mam.MAM_Events WHERE event_name = 'STUDENT_DISEMBARK');
INSERT INTO mam.MAM_Events(event_name,event_description,event_updated) SELECT 'LOG_IN','Log In',now() WHERE NOT EXISTS (SELECT event_name FROM mam.MAM_Events WHERE event_name = 'LOG_IN');
INSERT INTO mam.MAM_Events(event_name,event_description,event_updated) SELECT 'LOG_OUT','Log Out',now() WHERE NOT EXISTS (SELECT event_name FROM mam.MAM_Events WHERE event_name = 'LOG_OUT');
INSERT INTO mam.MAM_Events(event_name,event_description,event_updated) SELECT 'STOP_STATUS','Stop Status',now() WHERE NOT EXISTS (SELECT event_name FROM mam.MAM_Events WHERE event_name = 'STOP_STATUS');
INSERT INTO mam.MAM_Events(event_name,event_description,event_updated) SELECT 'STOP_MISSED','Stop Missed',now() WHERE NOT EXISTS (SELECT event_name FROM mam.MAM_Events WHERE event_name = 'STOP_MISSED');


/* minh vo 11/17/2017*/
INSERT INTO mam.MAM_Settings ( app_id, setting_name, setting_value, setting_description, setting_type, setting_updated) SELECT  1, 'AUTO_LOG_OUT_THRESHOLD', '600', 'Auto Log Out Threshold', 'All', now() WHERE NOT EXISTS (SELECT setting_name FROM mam.MAM_Settings WHERE setting_name = 'AUTO_LOG_OUT_THRESHOLD');
--Thanh 12/13/2017 Add enable job
INSERT INTO mam.MAM_Settings ( app_id, setting_name, setting_value, setting_description, setting_type, setting_updated) SELECT  1, 'ENABLED_JOBCODE_PERFORMINGTASK', 'ON', 'Enabled Jobcode Performingtask', 'All', now() WHERE NOT EXISTS (SELECT setting_name FROM mam.MAM_Settings WHERE setting_name = 'ENABLED_JOBCODE_PERFORMINGTASK');


update mam.mam_modules set dev_module_enable = '1' where  module_name in ('eDTA','Additional Employee','Switch Session Owner');

update mam.mam_modules set dev_module_enable = '1' where  module_name not in ('eDTA','Additional Employee','Switch Session Owner');

/*Minh vo 04/05/2018 - add new setting*/
INSERT INTO mam.MAM_Settings ( app_id, setting_name, setting_value, setting_description, setting_type, setting_updated) SELECT 1, 'SCREEN_ACTIVE_DURING_DRIVING', 'ON', 'SCREEN_ACTIVE_DURING_DRIVING', 'All', now() WHERE NOT EXISTS (SELECT setting_name FROM mam.MAM_Settings WHERE setting_name = 'SCREEN_ACTIVE_DURING_DRIVING');

/*Minh vo 04/11/2018 - ađ new setting*/
INSERT INTO mam.MAM_Settings ( app_id, setting_name, setting_value, setting_description, setting_type, setting_updated) SELECT 1, 'SHOW_STUDENT_CONFIRM_CORRECT_STOP', 'ON', 'SHOW_STUDENT_CONFIRM_CORRECT_STOP', 'All', now() WHERE NOT EXISTS (SELECT setting_name FROM mam.MAM_Settings WHERE setting_name = 'SHOW_STUDENT_CONFIRM_CORRECT_STOP');
INSERT INTO mam.MAM_Settings ( app_id, setting_name, setting_value, setting_description, setting_type, setting_updated) SELECT 1, 'SHOW_STUDENT_CONFIRM_INCORRECT_STOP', 'ON', 'SHOW_STUDENT_CONFIRM_INCORRECT_STOP', 'All', now() WHERE NOT EXISTS (SELECT setting_name FROM mam.MAM_Settings WHERE setting_name = 'SHOW_STUDENT_CONFIRM_INCORRECT_STOP');
INSERT INTO mam.MAM_Settings ( app_id, setting_name, setting_value, setting_description, setting_type, setting_updated) SELECT 1, 'SHOW_STUDENT_CONFIRM_SCHOOL', 'ON', 'SHOW_STUDENT_CONFIRM_SCHOOL', 'All', now() WHERE NOT EXISTS (SELECT setting_name FROM mam.MAM_Settings WHERE setting_name = 'SHOW_STUDENT_CONFIRM_SCHOOL');


/*Thanh script set file to download dp*/
--INSERT INTO mam.mam_files(file_type, file_version, file_url, file_updated)
--	VALUES ('maps', '1.1.0', 'http://113.20.115.84:9995/live/dphomer/map.mbtiles', now());    
--INSERT INTO mam.mam_files(file_type, file_version, file_url, file_updated)
--	VALUES ('database', '1.0.5.5', 'http://113.20.115.84:9995/live/dphomer/encypteddb.sqlite', now());
--INSERT INTO mam.mam_files(file_type, file_version, file_url, file_updated)
--	VALUES ('osmandmap', '1.1.0', 'http://113.20.115.84:9995/live/dphomer/osmandmap.obf', now());
--INSERT INTO mam.mam_files(file_type, file_version, file_url, file_updated)
--	VALUES ('driverportal', '109', 'http://113.20.115.84:9995/live/dphomer/driver-portal-7.4v4 calamp.apk', now());
--INSERT INTO mam.mam_files(file_type, file_version, file_url, file_updated)
--	VALUES ('osmand', '330', 'http://113.20.115.84:9995/live/dphomer/osmand-calamp-3.2v10.apk', now());
--INSERT INTO mam.mam_files(file_type, file_version, file_url, file_updated)
--	VALUES ('rundirsdata', '1.0.7.4', 'http://113.20.115.84:9995/live/dphomer/rundirs.sqlite', now());

INSERT INTO mam.MAM_Settings ( app_id, setting_name, setting_value, setting_description, setting_type, setting_updated) SELECT 1, 'USE_OSMDROID_SDK', 'ON', 'USE_OSMDROID_SDK', 'Device', now() WHERE NOT EXISTS (SELECT setting_name FROM mam.MAM_Settings WHERE setting_name = 'USE_OSMDROID_SDK');
INSERT INTO mam.MAM_Settings ( app_id, setting_name, setting_value, setting_description, setting_type, setting_updated) SELECT 1, 'PREFIX_STOP_COMMAND', '', 'PREFIX_STOP_COMMAND', 'All', now() WHERE NOT EXISTS (SELECT setting_name FROM mam.MAM_Settings WHERE setting_name = 'PREFIX_STOP_COMMAND');
INSERT INTO mam.MAM_Settings ( app_id, setting_name, setting_value, setting_description, setting_type, setting_updated) SELECT 1, 'PREFIX_RAILCROSSING_COMMAND', '', 'PREFIX_RAILCROSSING_COMMAND', 'All', now() WHERE NOT EXISTS (SELECT setting_name FROM mam.MAM_Settings WHERE setting_name = 'PREFIX_RAILCROSSING_COMMAND');

/*insert into mam.MAM_Device_Settings ( app_id, dev_id, setting_name, setting_value, setting_description, setting_type,  setting_updated) 
select app_id, dev_id, setting_name, setting_value, setting_description, setting_type, now() as setting_updated
from mam.MDM_Devices, mam.MAM_Settings where setting_name in ('USE_OSMDROID_SDK')
except select app_id, dev_id, setting_name, setting_value, setting_description, setting_type, now() as setting_updated
from mam.MAM_Device_Settings where setting_name in ('USE_OSMDROID_SDK')
order by dev_id, setting_name;*/

update mam.MAM_Settings set setting_type='Device' where setting_name in ('NO_MOTION_SPEED_THRESHOLD', 'NO_MOTION_TIME_THRESHOLD', 'KIOSK_MODE');
--minhvo, add new settting ScreenInActiveColor
INSERT INTO mam.MAM_Settings ( app_id, setting_name, setting_value, setting_description, setting_type, setting_updated) SELECT 1, 'SCREENINACTIVECOLOR', 'Black', 'SCREENINACTIVECOLOR', 'All', now() WHERE NOT EXISTS (SELECT setting_name FROM mam.MAM_Settings WHERE setting_name = 'SCREENINACTIVECOLOR');
--turn off 2 modules
update  mam.mam_modules set dev_module_enable =false where module_name in ('Driving Mode','Assigned Route');    
update  mam.mam_dev_module set dev_module_enable =false where module_id in (select module_id from mam.mam_modules where module_name in ('Driving Mode','Assigned Route'));
--rename 2 modules
update  mam.mam_modules set module_description ='Route Sheet' where module_name='Select Route';
update  mam.mam_modules set module_description ='Route Builder' where module_name='Route Builder';
--add module student ridership
INSERT INTO mam.MAM_Modules ( module_name, module_description, app_id,dev_module_enable) SELECT 'Student Ridership', 'Student Ridership', 1,'1' WHERE NOT EXISTS (SELECT module_name FROM mam.MAM_Modules WHERE module_name = 'Student Ridership');

insert into mam.MAM_Dev_Module(dev_id,module_id,dev_module_enable,dev_module_updated,dev_module_default)
select d.dev_id,m.module_id, true as dev_module_enable, now() as dev_module_updated, false as dev_module_default
from mam.MDM_Devices d, mam.MAM_Modules m where m.module_name='Student Ridership'
except
select dev_id,module_id, true as dev_module_enable, now() as dev_module_updated, false as dev_module_default
from mam.MAM_Dev_Module  ;
--add setting for osm\
INSERT INTO mam.MAM_Settings ( app_id, setting_name, setting_value, setting_description, setting_type, setting_updated) SELECT 1, 'OSM_DISTANCE_TO_STOP', '60', 'OSM_DISTANCE_TO_STOP', 'All', now() WHERE NOT EXISTS (SELECT setting_name FROM mam.MAM_Settings WHERE setting_name = 'OSM_DISTANCE_TO_STOP');
INSERT INTO mam.MAM_Settings ( app_id, setting_name, setting_value, setting_description, setting_type, setting_updated) SELECT 1, 'OSM_DISTANCE_TO_TURN', '40', 'OSM_DISTANCE_TO_TURN', 'All', now() WHERE NOT EXISTS (SELECT setting_name FROM mam.MAM_Settings WHERE setting_name = 'OSM_DISTANCE_TO_TURN');
INSERT INTO mam.MAM_Settings ( app_id, setting_name, setting_value, setting_description, setting_type, setting_updated) SELECT 1, 'OSM_DEFAULT_SPEED', '11', 'OSM_DEFAULT_SPEED', 'All', now() WHERE NOT EXISTS (SELECT setting_name FROM mam.MAM_Settings WHERE setting_name = 'OSM_DEFAULT_SPEED');

INSERT INTO mam.MAM_Settings ( app_id, setting_name, setting_value, setting_description, setting_type, setting_updated) SELECT 1, 'DP_DEFAULT_VOICE_OSM', 'DISABLE', 'DP_DEFAULT_VOICE_OSM', 'All', now() WHERE NOT EXISTS (SELECT setting_name FROM mam.MAM_Settings WHERE setting_name = 'DP_DEFAULT_VOICE_OSM');

--add auto log out 12/21/2018
INSERT INTO mam.MAM_Events(event_name,event_description,event_updated) SELECT 'AUTO_LOG_IN','Log In',now() WHERE NOT EXISTS (SELECT event_name FROM mam.MAM_Events WHERE event_name = 'AUTO_LOG_IN');
INSERT INTO mam.MAM_Events(event_name,event_description,event_updated) SELECT 'AUTO_LOG_OUT','Log Out',now() WHERE NOT EXISTS (SELECT event_name FROM mam.MAM_Events WHERE event_name = 'AUTO_LOG_OUT');

INSERT INTO mam.MAM_Settings ( app_id, setting_name, setting_value, setting_description, setting_type, setting_updated) SELECT 1, 'SHOW_LIST_STUDENT', 'ON', 'SHOW_LIST_STUDENT', 'All', now() WHERE NOT EXISTS (SELECT setting_name FROM mam.MAM_Settings WHERE setting_name = 'SHOW_LIST_STUDENT');

INSERT INTO mam.MAM_Settings ( app_id, setting_name, setting_value, setting_description, setting_type, setting_updated) SELECT 1, 'INTERVAL_DOWNLOAD_PROCESS', '5', 'INTERVAL_DOWNLOAD_PROCESS', 'All', now() WHERE NOT EXISTS (SELECT setting_name FROM mam.MAM_Settings WHERE setting_name = 'INTERVAL_DOWNLOAD_PROCESS');
INSERT INTO mam.MAM_Settings ( app_id, setting_name, setting_value, setting_description, setting_type, setting_updated) SELECT 1, 'DEBUGMODE', 'OFF', 'DEBUGMODE', 'Device', now() WHERE NOT EXISTS (SELECT setting_name FROM mam.MAM_Settings WHERE setting_name = 'DEBUGMODE');
INSERT INTO mam.MAM_Settings ( app_id, setting_name, setting_value, setting_description, setting_type, setting_updated) SELECT 1, 'SYNC_TIME', 'ON', 'SYNC_TIME', 'All', now() WHERE NOT EXISTS (SELECT setting_name FROM mam.MAM_Settings WHERE setting_name = 'SYNC_TIME');

/*insert into mam.MAM_Device_Settings ( app_id, dev_id, setting_name, setting_value, setting_description, setting_type,  setting_updated) 
select app_id, dev_id, setting_name, setting_value, setting_description, setting_type, now() as setting_updated
from mam.MDM_Devices, mam.MAM_Settings where setting_name in ('DEBUGMODE')
except select app_id, dev_id, setting_name, setting_value, setting_description, setting_type, now() as setting_updated
from mam.MAM_Device_Settings where setting_name in ('DEBUGMODE')
order by dev_id, setting_name;*/

--minh vo add new setting
INSERT INTO mam.MAM_Settings ( app_id, setting_name, setting_value, setting_description, setting_type, setting_updated) SELECT 1, 'IGNORE_UNPLANNED_STOP', 'OFF', 'IGNORE_UNPLANNED_STOP', 'All', now() WHERE NOT EXISTS (SELECT setting_name FROM mam.MAM_Settings WHERE setting_name = 'IGNORE_UNPLANNED_STOP');

--4/2/2019, minh add setting DOWNLOAD_VIA_LMU
INSERT INTO mam.MAM_Settings ( app_id, setting_name, setting_value, setting_description, setting_type, setting_updated) SELECT 1, 'DOWNLOAD_VIA_LMU', 'OFF', 'DOWNLOAD_VIA_LMU', 'All', now() WHERE NOT EXISTS (SELECT setting_name FROM mam.MAM_Settings WHERE setting_name = 'DOWNLOAD_VIA_LMU');

--06/24/2019, Chuong add setting AUTO_PUNCHES_OUT to MAM_Settings
INSERT INTO mam.MAM_Settings ( app_id, setting_name, setting_value, setting_description, setting_type, setting_updated) SELECT 1, 'AUTO_PUNCHES_OUT', 'ON', 'AUTO_PUNCHES_OUT', 'All', now() WHERE NOT EXISTS (SELECT setting_name FROM mam.MAM_Settings WHERE setting_name = 'AUTO_PUNCHES_OUT');

--06/25/2019, Chuong add setting RFID_TO_ETGPS to API_Config
INSERT INTO mam.API_Config ( setting_name, setting_value, setting_updated) SELECT 'RFID_TO_ETGPS', 0 , now() WHERE NOT EXISTS (SELECT setting_name FROM mam.API_Config WHERE setting_name = 'RFID_TO_ETGPS');

--08/26/2019 , Chuong add condition and change MAM_Device_Settings
INSERT INTO mam.condition (condition_id, condition_operator, condition_type, condition_value, condition_updated, condition_description) SELECT 1, 'IN', 'TEXT', 'OFF;ON', now(), ''  WHERE NOT EXISTS (SELECT condition_id FROM mam.condition where condition_id = 1)  ;
INSERT INTO mam.condition (condition_id, condition_operator, condition_type, condition_value, condition_updated, condition_description) SELECT 2, 'BETWEEN', 'NUMBER', '1;1000', now(), '' WHERE NOT EXISTS (SELECT condition_id FROM mam.condition where condition_id = 2)  ;
INSERT INTO mam.condition (condition_id, condition_operator, condition_type, condition_value, condition_updated, condition_description) SELECT 3, 'IN', 'TEXT', 'ON;OFF;DISABLE', now(), '' WHERE NOT EXISTS (SELECT condition_id FROM mam.condition where condition_id = 3)  ;
INSERT INTO mam.condition (condition_id, condition_operator, condition_type, condition_value, condition_updated, condition_description) SELECT 4, 'IN', 'TEXT', 'Red; Blue; Green; Black; White; Gray; Cyan; Magenta; Yellow; Lightgray; Darkgray', now(), '' WHERE NOT EXISTS (SELECT condition_id FROM mam.condition where condition_id = 4)  ;
INSERT INTO mam.condition (condition_id, condition_operator, condition_type, condition_value, condition_updated, condition_description) SELECT 5, 'BETWEEN', 'NUMBER', '10;100000', now(), 'use for TIME_THRESHOLD' WHERE NOT EXISTS (SELECT condition_id FROM mam.condition where condition_id = 5)  ;
INSERT INTO mam.condition (condition_id, condition_operator, condition_type, condition_value, condition_updated, condition_description) SELECT 6, 'IN', 'TEXT', 'METRIC;IMPERIAL', now(), 'unit measure' WHERE NOT EXISTS (SELECT condition_id FROM mam.condition where condition_id = 6)  ;
INSERT INTO mam.condition (condition_id, condition_operator, condition_type, condition_value, condition_updated, condition_description) SELECT 7, 'BETWEEN', 'TIME', '6:00;20:00', now(), 'use for TIME_VALUE' WHERE NOT EXISTS (SELECT condition_id FROM mam.condition where condition_id = 7)  ;
INSERT INTO mam.condition (condition_id, condition_operator, condition_type, condition_value, condition_updated, condition_description) SELECT 8, 'FREE', 'TEXT', '', now(), 'use for free input' WHERE NOT EXISTS (SELECT condition_id FROM mam.condition where condition_id = 8)  ;
ALTER SEQUENCE mam.condition_condition_id_seq RESTART with 9;
	
update mam.mam_settings set condition_id = 2 , default_value = 264 where setting_name like 'TRANSMISSION_FREQUENCY' ;
update mam.mam_settings set condition_id = 2 , default_value = 10 where setting_name like 'STOP_RADIUS' ;
update mam.mam_settings set condition_id = 2 , default_value = 5 where setting_name like 'NO_MOTION_SPEED_THRESHOLD' ;
update mam.mam_settings set condition_id = 5 , default_value = 30 where setting_name like 'NO_MOTION_TIME_THRESHOLD' ;
update mam.mam_settings set condition_id = 1 , default_value = 'ON' where setting_name like 'AUDIO_IN_MOTION' ;
update mam.mam_settings set condition_id = 1 , default_value = 'OFF' where setting_name like 'VIDEO_IN_MOTION' ;
update mam.mam_settings set condition_id = 5 , default_value = 300 where setting_name like 'LIMIT_NUMBER_OF_DEVICES';
update mam.mam_settings set condition_id = 5 , default_value = 300 where setting_name like 'IDLE_TIME_THRESHOLD' ;
update mam.mam_settings set condition_id = 5 , default_value = 650 where setting_name like 'SCHOOL_RADIUS' ;
update mam.mam_settings set condition_id = 6 , default_value = 'IMPERIAL' where setting_name like 'DISPLAY_UNITS' ;
update mam.mam_settings set condition_id = 1 , default_value = 'ON' where setting_name like 'KIOSK_MODE' ;
update mam.mam_settings set condition_id = 5 , default_value = 1800 where setting_name like 'MATCHTIMEWINDOWINSECONDS' ;
update mam.mam_settings set condition_id = 1 , default_value = 'ON' where setting_name like 'AUTOSWIPECARDSATSCHOOL' ;
update mam.mam_settings set condition_id = 7 , default_value = '06:00' where setting_name like 'CEILING_TIME_VALUE' ;
update mam.mam_settings set condition_id = 5 , default_value = 600 where setting_name like 'AUTO_LOG_OUT_THRESHOLD' ;
update mam.mam_settings set condition_id = 1 , default_value = 'ON' where setting_name like 'ENABLED_JOBCODE_PERFORMINGTASK' ;
update mam.mam_settings set condition_id = 1 , default_value = 'ON' where setting_name like 'SCREEN_ACTIVE_DURING_DRIVING' ;
update mam.mam_settings set condition_id = 1 , default_value = 'ON' where setting_name like 'SHOW_STUDENT_CONFIRM_CORRECT_STOP' ;
update mam.mam_settings set condition_id = 1 , default_value = 'ON' where setting_name like 'SHOW_STUDENT_CONFIRM_INCORRECT_STOP' ;
update mam.mam_settings set condition_id = 1 , default_value = 'ON' where setting_name like 'SHOW_STUDENT_CONFIRM_SCHOOL' ;
update mam.mam_settings set condition_id = 1 , default_value = 'ON' where setting_name like 'USE_OSMDROID_SDK' ;
update mam.mam_settings set condition_id = 8 , default_value = '' where setting_name like 'PREFIX_STOP_COMMAND';
update mam.mam_settings set condition_id = 8 , default_value = '' where setting_name like 'PREFIX_RAILCROSSING_COMMAND' ;
update mam.mam_settings set condition_id = 4 , default_value = 'Black' where setting_name like 'SCREENINACTIVECOLOR' ;
update mam.mam_settings set condition_id = 2 , default_value = 60 where setting_name like 'OSM_DISTANCE_TO_STOP' ;
update mam.mam_settings set condition_id = 2 , default_value = 40 where setting_name like 'OSM_DISTANCE_TO_TURN' ;
update mam.mam_settings set condition_id = 2 , default_value = 11 where setting_name like 'OSM_DEFAULT_SPEED' ;
update mam.mam_settings set condition_id = 3 , default_value = 'DISABLE' where setting_name like 'DP_DEFAULT_VOICE_OSM' ;
update mam.mam_settings set condition_id = 1 , default_value = 'ON' where setting_name like 'SHOW_LIST_STUDENT' ;
update mam.mam_settings set condition_id = 2 , default_value = 60 where setting_name like 'INTERVAL_DOWNLOAD_PROCESS';
update mam.mam_settings set condition_id = 1 , default_value = 'OFF' where setting_name like 'DEBUGMODE' ;
update mam.mam_settings set condition_id = 1 , default_value = 'ON' where setting_name like 'SYNC_TIME' ;
update mam.mam_settings set condition_id = 1 , default_value = 'OFF' where setting_name like 'IGNORE_UNPLANNED_STOP' ;
update mam.mam_settings set condition_id = 1 , default_value = 'OFF' where setting_name like 'DOWNLOAD_VIA_LMU' ;
update mam.mam_settings set condition_id = 1 , default_value = 'ON' where setting_name like 'AUTO_PUNCHES_OUT' ;

delete from mam.mam_device_settings USING (SELECT max(dev_setting_id) as dev_setting_id, dev_id, setting_name FROM mam.mam_device_settings group by dev_id,setting_name) a WHERE mam.mam_device_settings.dev_id = a.dev_id AND mam.mam_device_settings.setting_name = a.setting_name and mam.mam_device_settings.dev_setting_id < a.dev_setting_id;
insert into mam.MAM_Device_Settings ( app_id, dev_id, setting_name, setting_value, setting_description, setting_type,  setting_updated) select app_id, dev_id, setting_name, setting_value, setting_description, setting_type, now() as setting_updated from mam.MDM_Devices, mam.MAM_Settings where setting_type = 'Device' except select a.app_id, b.dev_id, b.setting_name, a.setting_value, a.setting_description, a.setting_type, now() as setting_updated from mam.MAM_Device_Settings b, mam.MAM_Settings a where a.setting_type = 'Device' and a.setting_name=b.setting_name order by dev_id, setting_name;
-- 08/27/2019 delete 2 module not use
delete from  mam.mam_dev_module where module_id in (select module_id from mam.mam_modules where module_name in ('Driving Mode','Assigned Route'));
delete from  mam.mam_modules where module_name in ('Driving Mode','Assigned Route');
-- 09/12/2019 add MAM_Setting ShortTag 
INSERT INTO mam.MAM_Settings ( app_id, setting_name, setting_value, setting_description, setting_type, setting_updated,condition_id, default_value ) SELECT 1, 'SHORT_TAG', 'OFF', 'SHORT_TAG', 'All', now(), 1 , 'OFF' WHERE NOT EXISTS (SELECT setting_name FROM mam.MAM_Settings WHERE setting_name = 'SHORT_TAG');
-- 09/12/2019 insert into table check_hardware
INSERT INTO mam.check_hardware(name, eventcode, description, critical, updated) SELECT 'LOCATION',3, 'Please check location', 'true', now() WHERE NOT EXISTS (SELECT name FROM mam.check_hardware WHERE name = 'LOCATION');
INSERT INTO mam.check_hardware(name, eventcode, description, critical, updated) SELECT 'DOOR OPEN',52, 'Please open the bus door', 'true', now() WHERE NOT EXISTS (SELECT name FROM mam.check_hardware WHERE name = 'DOOR OPEN');
INSERT INTO mam.check_hardware(name, eventcode, description, critical, updated) SELECT 'DOOR CLOSE',53, 'Please close the bus door', 'true', now() WHERE NOT EXISTS (SELECT name FROM mam.check_hardware WHERE name = 'DOOR CLOSE');
INSERT INTO mam.check_hardware(name, eventcode, description, critical, updated) SELECT 'STOP ARM OPEN',41, 'Please open the stop arm', 'true', now() WHERE NOT EXISTS (SELECT name FROM mam.check_hardware WHERE name = 'STOP ARM OPEN');
INSERT INTO mam.check_hardware(name, eventcode, description, critical, updated) SELECT 'STOP ARM CLOSE',40, 'Please close the stop arm', 'true', now() WHERE NOT EXISTS (SELECT name FROM mam.check_hardware WHERE name = 'STOP ARM CLOSE');
INSERT INTO mam.check_hardware(name, eventcode, description, critical, updated) SELECT 'WC LIFT DOWN',51, 'Please lift down the wheel chair', 'false', now() WHERE NOT EXISTS (SELECT name FROM mam.check_hardware WHERE name = 'WC LIFT DOWN');
INSERT INTO mam.check_hardware(name, eventcode, description, critical, updated) SELECT 'WC LIFT UP',50, 'Please lift up the wheel chair', 'false', now() WHERE NOT EXISTS (SELECT name FROM mam.check_hardware WHERE name = 'WC LIFT UP');
INSERT INTO mam.check_hardware(name, eventcode, description, critical, updated) SELECT 'SWIPE CARD',21, 'Please swipe card', 'true', now() WHERE NOT EXISTS (SELECT name FROM mam.check_hardware WHERE name = 'SWIPE CARD');
-- 09/13/2019 add MAM_settings timeout for check hardware
INSERT INTO mam.MAM_Settings ( app_id, setting_name, setting_value, setting_description, setting_type, setting_updated,condition_id, default_value ) SELECT 1, 'CHECK_HARDWARE_TIMEOUT', '15', 'CHECK_HARDWARE_TIMEOUT', 'All', now(), 2 , '15' WHERE NOT EXISTS (SELECT setting_name FROM mam.MAM_Settings WHERE setting_name = 'CHECK_HARDWARE_TIMEOUT');
-- 09/20/2019 add MAM_settings use function Cellular for Virginia Beach
INSERT INTO mam.MAM_Settings ( app_id, setting_name, setting_value, setting_description, setting_type, setting_updated,condition_id, default_value ) SELECT 1, 'USE_CELLULAR', 'OFF', 'USE_CELLULAR', 'All', now(), 1 , 'OFF' WHERE NOT EXISTS (SELECT setting_name FROM mam.MAM_Settings WHERE setting_name = 'USE_CELLULAR');
-- 09/26/2019 add mam.check_hardware Check_Cellular 
INSERT INTO mam.check_hardware(name, eventcode, description, critical, updated) SELECT 'CHECK CELLULAR', -128, 'Please check cellular', 'true', now() WHERE NOT EXISTS (SELECT name FROM mam.check_hardware WHERE name = 'CHECK CELLULAR');
-- 02/05/2020 add mam.MAM_Modules IVIN module
INSERT INTO mam.MAM_Modules ( module_name, module_description, app_id,dev_module_enable) SELECT 'IVIN', 'Intelligent Vehicle Inspection', 1,'1' WHERE NOT EXISTS (SELECT module_name FROM mam.MAM_Modules WHERE module_name = 'IVIN');
insert into mam.MAM_Dev_Module(dev_id,module_id,dev_module_enable,dev_module_updated,dev_module_default)
select d.dev_id,m.module_id, true as dev_module_enable, now() as dev_module_updated, false as dev_module_default
from mam.MDM_Devices d, mam.MAM_Modules m where m.module_name<>'Settings'
except
select dev_id,module_id, true as dev_module_enable, now() as dev_module_updated, false as dev_module_default
from mam.MAM_Dev_Module  ;
-- 02/11/2020 
INSERT INTO mam.MAM_Modules ( module_name, module_description, app_id,dev_module_enable) SELECT 'Timesheet Review', 'Timesheet Review', 1,'1' WHERE NOT EXISTS (SELECT module_name FROM mam.MAM_Modules WHERE module_name = 'Timesheet Review');
insert into mam.MAM_Dev_Module(dev_id,module_id,dev_module_enable,dev_module_updated,dev_module_default)
select d.dev_id,m.module_id, true as dev_module_enable, now() as dev_module_updated, false as dev_module_default
from mam.MDM_Devices d, mam.MAM_Modules m where m.module_name<>'Settings'
except
select dev_id,module_id, true as dev_module_enable, now() as dev_module_updated, false as dev_module_default
from mam.MAM_Dev_Module  ;
INSERT INTO mam.MAM_Settings ( app_id, setting_name, setting_value, setting_description, setting_type, setting_updated,condition_id, default_value ) SELECT 1, 'TIMESHEET_MODULE', 'OFF', 'TIMESHEET_MODULE', 'All', now(), 1 , 'OFF' WHERE NOT EXISTS (SELECT setting_name FROM mam.MAM_Settings WHERE setting_name = 'TIMESHEET_MODULE');
-- 02/11/2020 add mam.MAM_Settings
INSERT INTO mam.MAM_Settings ( app_id, setting_name, setting_value, setting_description, setting_type, setting_updated,condition_id, default_value ) SELECT 1, 'SKIP_DOWNLOAD_CERTIFICATE', 'OFF', 'SKIP_DOWNLOAD_CERTIFICATE', 'All', now(), 1 , 'OFF' WHERE NOT EXISTS (SELECT setting_name FROM mam.MAM_Settings WHERE setting_name = 'SKIP_DOWNLOAD_CERTIFICATE');
-- 02/21/2020 add value into mam.dp_color_theme
insert into mam.dp_theme (id, name, is_default) SELECT 1 , 'Default' , true WHERE NOT EXISTS (SELECT id, name FROM mam.dp_theme WHERE id = 1 and name = 'Default');
insert into mam.dp_theme (id, name, is_default) SELECT 2 , 'Monocolor' , false WHERE NOT EXISTS (SELECT id, name FROM mam.dp_theme WHERE id = 2 and name = 'Monocolor');
insert into mam.dp_theme (id, name, is_default) SELECT 3 , 'Colorful' , false WHERE NOT EXISTS (SELECT id, name FROM mam.dp_theme WHERE id = 3 and name = 'Colorful');
INSERT INTO mam.dp_color_theme (theme_id, color_type, color_value) SELECT 1, 'default_direction_fill_color', '#007173'  WHERE NOT EXISTS ( SELECT theme_id, color_type FROM mam.dp_color_theme WHERE theme_id = 1 and color_type = 'default_direction_fill_color');
INSERT INTO mam.dp_color_theme (theme_id, color_type, color_value) SELECT 1, 'default_direction_background_color', '#FFFFFF'  WHERE NOT EXISTS ( SELECT theme_id, color_type FROM mam.dp_color_theme WHERE theme_id = 1 and color_type = 'default_direction_background_color');
INSERT INTO mam.dp_color_theme (theme_id, color_type, color_value) SELECT 1, 'default_direction_style', 'REGULAR'  WHERE NOT EXISTS ( SELECT theme_id, color_type FROM mam.dp_color_theme WHERE theme_id = 1 and color_type = 'default_direction_style');
INSERT INTO mam.dp_color_theme (theme_id, color_type, color_value) SELECT 1, 'miss_direction_fill_color', '#929292'  WHERE NOT EXISTS ( SELECT theme_id, color_type FROM mam.dp_color_theme WHERE theme_id = 1 and color_type = 'miss_direction_fill_color');
INSERT INTO mam.dp_color_theme (theme_id, color_type, color_value) SELECT 1, 'miss_direction_background_color', '#FFFFFF'  WHERE NOT EXISTS ( SELECT theme_id, color_type FROM mam.dp_color_theme WHERE theme_id = 1 and color_type = 'miss_direction_background_color');
INSERT INTO mam.dp_color_theme (theme_id, color_type, color_value) SELECT 1, 'miss_direction_style', 'REGULAR'  WHERE NOT EXISTS ( SELECT theme_id, color_type FROM mam.dp_color_theme WHERE theme_id = 1 and color_type = 'miss_direction_style');
INSERT INTO mam.dp_color_theme (theme_id, color_type, color_value) SELECT 1, 'pass_direction_fill_color', '#FCD6C3'  WHERE NOT EXISTS ( SELECT theme_id, color_type FROM mam.dp_color_theme WHERE theme_id = 1 and color_type = 'pass_direction_fill_color');
INSERT INTO mam.dp_color_theme (theme_id, color_type, color_value) SELECT 1, 'pass_direction_background_color', '#FFFFFF'  WHERE NOT EXISTS ( SELECT theme_id, color_type FROM mam.dp_color_theme WHERE theme_id = 1 and color_type = 'pass_direction_background_color');
INSERT INTO mam.dp_color_theme (theme_id, color_type, color_value) SELECT 1, 'pass_direction_style', 'REGULAR'  WHERE NOT EXISTS ( SELECT theme_id, color_type FROM mam.dp_color_theme WHERE theme_id = 1 and color_type = 'pass_direction_style');
INSERT INTO mam.dp_color_theme (theme_id, color_type, color_value) SELECT 1, 'next_direction_fill_color', '#F47839'  WHERE NOT EXISTS ( SELECT theme_id, color_type FROM mam.dp_color_theme WHERE theme_id = 1 and color_type = 'next_direction_fill_color');
INSERT INTO mam.dp_color_theme (theme_id, color_type, color_value) SELECT 1, 'next_direction_background_color', '#FFFFFF'  WHERE NOT EXISTS ( SELECT theme_id, color_type FROM mam.dp_color_theme WHERE theme_id = 1 and color_type = 'next_direction_background_color');
INSERT INTO mam.dp_color_theme (theme_id, color_type, color_value) SELECT 1, 'next_direction_style', 'REGULAR'  WHERE NOT EXISTS ( SELECT theme_id, color_type FROM mam.dp_color_theme WHERE theme_id = 1 and color_type = 'next_direction_style');
INSERT INTO mam.dp_color_theme (theme_id, color_type, color_value) SELECT 1, 'default_stop_fill_color', '#007173'  WHERE NOT EXISTS ( SELECT theme_id, color_type FROM mam.dp_color_theme WHERE theme_id = 1 and color_type = 'default_stop_fill_color');
INSERT INTO mam.dp_color_theme (theme_id, color_type, color_value) SELECT 1, 'default_stop_background_color', '#FFFFFF'  WHERE NOT EXISTS ( SELECT theme_id, color_type FROM mam.dp_color_theme WHERE theme_id = 1 and color_type = 'default_stop_background_color');
INSERT INTO mam.dp_color_theme (theme_id, color_type, color_value) SELECT 1, 'default_stop_style', 'REGULAR'  WHERE NOT EXISTS ( SELECT theme_id, color_type FROM mam.dp_color_theme WHERE theme_id = 1 and color_type = 'default_stop_style');
INSERT INTO mam.dp_color_theme (theme_id, color_type, color_value) SELECT 1, 'miss_stop_fill_color', '#929292'  WHERE NOT EXISTS ( SELECT theme_id, color_type FROM mam.dp_color_theme WHERE theme_id = 1 and color_type = 'miss_stop_fill_color');
INSERT INTO mam.dp_color_theme (theme_id, color_type, color_value) SELECT 1, 'miss_stop_background_color', '#FFFFFF'  WHERE NOT EXISTS ( SELECT theme_id, color_type FROM mam.dp_color_theme WHERE theme_id = 1 and color_type = 'miss_stop_background_color');
INSERT INTO mam.dp_color_theme (theme_id, color_type, color_value) SELECT 1, 'miss_stop_style', 'REGULAR'  WHERE NOT EXISTS ( SELECT theme_id, color_type FROM mam.dp_color_theme WHERE theme_id = 1 and color_type = 'miss_stop_style');
INSERT INTO mam.dp_color_theme (theme_id, color_type, color_value) SELECT 1, 'pass_stop_fill_color', '#FCD6C3'  WHERE NOT EXISTS ( SELECT theme_id, color_type FROM mam.dp_color_theme WHERE theme_id = 1 and color_type = 'pass_stop_fill_color');
INSERT INTO mam.dp_color_theme (theme_id, color_type, color_value) SELECT 1, 'pass_stop_background_color', '#FFFFFF'  WHERE NOT EXISTS ( SELECT theme_id, color_type FROM mam.dp_color_theme WHERE theme_id = 1 and color_type = 'pass_stop_background_color');
INSERT INTO mam.dp_color_theme (theme_id, color_type, color_value) SELECT 1, 'pass_stop_style', 'REGULAR'  WHERE NOT EXISTS ( SELECT theme_id, color_type FROM mam.dp_color_theme WHERE theme_id = 1 and color_type = 'pass_stop_style');
INSERT INTO mam.dp_color_theme (theme_id, color_type, color_value) SELECT 1, 'next_stop_fill_color', '#F47839'  WHERE NOT EXISTS ( SELECT theme_id, color_type FROM mam.dp_color_theme WHERE theme_id = 1 and color_type = 'next_stop_fill_color');
INSERT INTO mam.dp_color_theme (theme_id, color_type, color_value) SELECT 1, 'next_stop_background_color', '#FFFFFF'  WHERE NOT EXISTS ( SELECT theme_id, color_type FROM mam.dp_color_theme WHERE theme_id = 1 and color_type = 'next_stop_background_color');
INSERT INTO mam.dp_color_theme (theme_id, color_type, color_value) SELECT 1, 'next_stop_style', 'REGULAR'  WHERE NOT EXISTS ( SELECT theme_id, color_type FROM mam.dp_color_theme WHERE theme_id = 1 and color_type = 'next_stop_style');
INSERT INTO mam.dp_color_theme (theme_id, color_type, color_value) SELECT 2, 'default_direction_fill_color', '#007173'  WHERE NOT EXISTS ( SELECT theme_id, color_type FROM mam.dp_color_theme WHERE theme_id = 2 and color_type = 'default_direction_fill_color');
INSERT INTO mam.dp_color_theme (theme_id, color_type, color_value) SELECT 2, 'default_direction_background_color', '#FFFFFF'  WHERE NOT EXISTS ( SELECT theme_id, color_type FROM mam.dp_color_theme WHERE theme_id = 2 and color_type = 'default_direction_background_color');
INSERT INTO mam.dp_color_theme (theme_id, color_type, color_value) SELECT 2, 'default_direction_style', 'REGULAR'  WHERE NOT EXISTS ( SELECT theme_id, color_type FROM mam.dp_color_theme WHERE theme_id = 2 and color_type = 'default_direction_style');
INSERT INTO mam.dp_color_theme (theme_id, color_type, color_value) SELECT 2, 'miss_direction_fill_color', '#007173'  WHERE NOT EXISTS ( SELECT theme_id, color_type FROM mam.dp_color_theme WHERE theme_id = 2 and color_type = 'miss_direction_fill_color');
INSERT INTO mam.dp_color_theme (theme_id, color_type, color_value) SELECT 2, 'miss_direction_background_color', '#FFFFFF'  WHERE NOT EXISTS ( SELECT theme_id, color_type FROM mam.dp_color_theme WHERE theme_id = 2 and color_type = 'miss_direction_background_color');
INSERT INTO mam.dp_color_theme (theme_id, color_type, color_value) SELECT 2, 'miss_direction_style', 'STRIKETHROUGH'  WHERE NOT EXISTS ( SELECT theme_id, color_type FROM mam.dp_color_theme WHERE theme_id = 2 and color_type = 'miss_direction_style');
INSERT INTO mam.dp_color_theme (theme_id, color_type, color_value) SELECT 2, 'pass_direction_fill_color', '#B2D4D5'  WHERE NOT EXISTS ( SELECT theme_id, color_type FROM mam.dp_color_theme WHERE theme_id = 2 and color_type = 'pass_direction_fill_color');
INSERT INTO mam.dp_color_theme (theme_id, color_type, color_value) SELECT 2, 'pass_direction_background_color', '#FFFFFF'  WHERE NOT EXISTS ( SELECT theme_id, color_type FROM mam.dp_color_theme WHERE theme_id = 2 and color_type = 'pass_direction_background_color');
INSERT INTO mam.dp_color_theme (theme_id, color_type, color_value) SELECT 2, 'pass_direction_style', 'REGULAR'  WHERE NOT EXISTS ( SELECT theme_id, color_type FROM mam.dp_color_theme WHERE theme_id = 2 and color_type = 'pass_direction_style');
INSERT INTO mam.dp_color_theme (theme_id, color_type, color_value) SELECT 2, 'next_direction_fill_color', '#FFFFFF'  WHERE NOT EXISTS ( SELECT theme_id, color_type FROM mam.dp_color_theme WHERE theme_id = 2 and color_type = 'next_direction_fill_color');
INSERT INTO mam.dp_color_theme (theme_id, color_type, color_value) SELECT 2, 'next_direction_background_color', '#007173'  WHERE NOT EXISTS ( SELECT theme_id, color_type FROM mam.dp_color_theme WHERE theme_id = 2 and color_type = 'next_direction_background_color');
INSERT INTO mam.dp_color_theme (theme_id, color_type, color_value) SELECT 2, 'next_direction_style', 'REGULAR'  WHERE NOT EXISTS ( SELECT theme_id, color_type FROM mam.dp_color_theme WHERE theme_id = 2 and color_type = 'next_direction_style');
INSERT INTO mam.dp_color_theme (theme_id, color_type, color_value) SELECT 2, 'default_stop_fill_color', '#007173'  WHERE NOT EXISTS ( SELECT theme_id, color_type FROM mam.dp_color_theme WHERE theme_id = 2 and color_type = 'default_stop_fill_color');
INSERT INTO mam.dp_color_theme (theme_id, color_type, color_value) SELECT 2, 'default_stop_background_color', '#FFFFFF'  WHERE NOT EXISTS ( SELECT theme_id, color_type FROM mam.dp_color_theme WHERE theme_id = 2 and color_type = 'default_stop_background_color');
INSERT INTO mam.dp_color_theme (theme_id, color_type, color_value) SELECT 2, 'default_stop_style', 'REGULAR'  WHERE NOT EXISTS ( SELECT theme_id, color_type FROM mam.dp_color_theme WHERE theme_id = 2 and color_type = 'default_stop_style');
INSERT INTO mam.dp_color_theme (theme_id, color_type, color_value) SELECT 2, 'miss_stop_fill_color', '#007173'  WHERE NOT EXISTS ( SELECT theme_id, color_type FROM mam.dp_color_theme WHERE theme_id = 2 and color_type = 'miss_stop_fill_color');
INSERT INTO mam.dp_color_theme (theme_id, color_type, color_value) SELECT 2, 'miss_stop_background_color', '#FFFFFF'  WHERE NOT EXISTS ( SELECT theme_id, color_type FROM mam.dp_color_theme WHERE theme_id = 2 and color_type = 'miss_stop_background_color');
INSERT INTO mam.dp_color_theme (theme_id, color_type, color_value) SELECT 2, 'miss_stop_style', 'STRIKETHROUGH'  WHERE NOT EXISTS ( SELECT theme_id, color_type FROM mam.dp_color_theme WHERE theme_id = 2 and color_type = 'miss_stop_style');
INSERT INTO mam.dp_color_theme (theme_id, color_type, color_value) SELECT 2, 'pass_stop_fill_color', '#B2D4D5'  WHERE NOT EXISTS ( SELECT theme_id, color_type FROM mam.dp_color_theme WHERE theme_id = 2 and color_type = 'pass_stop_fill_color');
INSERT INTO mam.dp_color_theme (theme_id, color_type, color_value) SELECT 2, 'pass_stop_background_color', '#FFFFFF'  WHERE NOT EXISTS ( SELECT theme_id, color_type FROM mam.dp_color_theme WHERE theme_id = 2 and color_type = 'pass_stop_background_color');
INSERT INTO mam.dp_color_theme (theme_id, color_type, color_value) SELECT 2, 'pass_stop_style', 'REGULAR'  WHERE NOT EXISTS ( SELECT theme_id, color_type FROM mam.dp_color_theme WHERE theme_id = 2 and color_type = 'pass_stop_style');
INSERT INTO mam.dp_color_theme (theme_id, color_type, color_value) SELECT 2, 'next_stop_fill_color', '#FFFFFF'  WHERE NOT EXISTS ( SELECT theme_id, color_type FROM mam.dp_color_theme WHERE theme_id = 2 and color_type = 'next_stop_fill_color');
INSERT INTO mam.dp_color_theme (theme_id, color_type, color_value) SELECT 2, 'next_stop_background_color', '#007173'  WHERE NOT EXISTS ( SELECT theme_id, color_type FROM mam.dp_color_theme WHERE theme_id = 2 and color_type = 'next_stop_background_color');
INSERT INTO mam.dp_color_theme (theme_id, color_type, color_value) SELECT 2, 'next_stop_style', 'REGULAR'  WHERE NOT EXISTS ( SELECT theme_id, color_type FROM mam.dp_color_theme WHERE theme_id = 2 and color_type = 'next_stop_style');
INSERT INTO mam.dp_color_theme (theme_id, color_type, color_value) SELECT 3, 'default_direction_fill_color', '#007173'  WHERE NOT EXISTS ( SELECT theme_id, color_type FROM mam.dp_color_theme WHERE theme_id = 3 and color_type = 'default_direction_fill_color');
INSERT INTO mam.dp_color_theme (theme_id, color_type, color_value) SELECT 3, 'default_direction_background_color', '#FFFFFF'  WHERE NOT EXISTS ( SELECT theme_id, color_type FROM mam.dp_color_theme WHERE theme_id = 3 and color_type = 'default_direction_background_color');
INSERT INTO mam.dp_color_theme (theme_id, color_type, color_value) SELECT 3, 'default_direction_style', 'REGULAR'  WHERE NOT EXISTS ( SELECT theme_id, color_type FROM mam.dp_color_theme WHERE theme_id = 3 and color_type = 'default_direction_style');
INSERT INTO mam.dp_color_theme (theme_id, color_type, color_value) SELECT 3, 'miss_direction_fill_color', '#EC1126'  WHERE NOT EXISTS ( SELECT theme_id, color_type FROM mam.dp_color_theme WHERE theme_id = 3 and color_type = 'miss_direction_fill_color');
INSERT INTO mam.dp_color_theme (theme_id, color_type, color_value) SELECT 3, 'miss_direction_background_color', '#FFFFFF'  WHERE NOT EXISTS ( SELECT theme_id, color_type FROM mam.dp_color_theme WHERE theme_id = 3 and color_type = 'miss_direction_background_color');
INSERT INTO mam.dp_color_theme (theme_id, color_type, color_value) SELECT 3, 'miss_direction_style', 'REGULAR'  WHERE NOT EXISTS ( SELECT theme_id, color_type FROM mam.dp_color_theme WHERE theme_id = 3 and color_type = 'miss_direction_style');
INSERT INTO mam.dp_color_theme (theme_id, color_type, color_value) SELECT 3, 'pass_direction_fill_color', '#26B134'  WHERE NOT EXISTS ( SELECT theme_id, color_type FROM mam.dp_color_theme WHERE theme_id = 3 and color_type = 'pass_direction_fill_color');
INSERT INTO mam.dp_color_theme (theme_id, color_type, color_value) SELECT 3, 'pass_direction_background_color', '#FFFFFF'  WHERE NOT EXISTS ( SELECT theme_id, color_type FROM mam.dp_color_theme WHERE theme_id = 3 and color_type = 'pass_direction_background_color');
INSERT INTO mam.dp_color_theme (theme_id, color_type, color_value) SELECT 3, 'pass_direction_style', 'REGULAR'  WHERE NOT EXISTS ( SELECT theme_id, color_type FROM mam.dp_color_theme WHERE theme_id = 3 and color_type = 'pass_direction_style');
INSERT INTO mam.dp_color_theme (theme_id, color_type, color_value) SELECT 3, 'next_direction_fill_color', '#F37739'  WHERE NOT EXISTS ( SELECT theme_id, color_type FROM mam.dp_color_theme WHERE theme_id = 3 and color_type = 'next_direction_fill_color');
INSERT INTO mam.dp_color_theme (theme_id, color_type, color_value) SELECT 3, 'next_direction_background_color', '#FFFFFF'  WHERE NOT EXISTS ( SELECT theme_id, color_type FROM mam.dp_color_theme WHERE theme_id = 3 and color_type = 'next_direction_background_color');
INSERT INTO mam.dp_color_theme (theme_id, color_type, color_value) SELECT 3, 'next_direction_style', 'REGULAR'  WHERE NOT EXISTS ( SELECT theme_id, color_type FROM mam.dp_color_theme WHERE theme_id = 3 and color_type = 'next_direction_style');
INSERT INTO mam.dp_color_theme (theme_id, color_type, color_value) SELECT 3, 'default_stop_fill_color', '#007173'  WHERE NOT EXISTS ( SELECT theme_id, color_type FROM mam.dp_color_theme WHERE theme_id = 3 and color_type = 'default_stop_fill_color');
INSERT INTO mam.dp_color_theme (theme_id, color_type, color_value) SELECT 3, 'default_stop_background_color', '#FFFFFF'  WHERE NOT EXISTS ( SELECT theme_id, color_type FROM mam.dp_color_theme WHERE theme_id = 3 and color_type = 'default_stop_background_color');
INSERT INTO mam.dp_color_theme (theme_id, color_type, color_value) SELECT 3, 'default_stop_style', 'REGULAR'  WHERE NOT EXISTS ( SELECT theme_id, color_type FROM mam.dp_color_theme WHERE theme_id = 3 and color_type = 'default_stop_style');
INSERT INTO mam.dp_color_theme (theme_id, color_type, color_value) SELECT 3, 'miss_stop_fill_color', '#EC1126'  WHERE NOT EXISTS ( SELECT theme_id, color_type FROM mam.dp_color_theme WHERE theme_id = 3 and color_type = 'miss_stop_fill_color');
INSERT INTO mam.dp_color_theme (theme_id, color_type, color_value) SELECT 3, 'miss_stop_background_color', '#FFFFFF'  WHERE NOT EXISTS ( SELECT theme_id, color_type FROM mam.dp_color_theme WHERE theme_id = 3 and color_type = 'miss_stop_background_color');
INSERT INTO mam.dp_color_theme (theme_id, color_type, color_value) SELECT 3, 'miss_stop_style', 'REGULAR'  WHERE NOT EXISTS ( SELECT theme_id, color_type FROM mam.dp_color_theme WHERE theme_id = 3 and color_type = 'miss_stop_style');
INSERT INTO mam.dp_color_theme (theme_id, color_type, color_value) SELECT 3, 'pass_stop_fill_color', '#26B134'  WHERE NOT EXISTS ( SELECT theme_id, color_type FROM mam.dp_color_theme WHERE theme_id = 3 and color_type = 'pass_stop_fill_color');
INSERT INTO mam.dp_color_theme (theme_id, color_type, color_value) SELECT 3, 'pass_stop_background_color', '#FFFFFF'  WHERE NOT EXISTS ( SELECT theme_id, color_type FROM mam.dp_color_theme WHERE theme_id = 3 and color_type = 'pass_stop_background_color');
INSERT INTO mam.dp_color_theme (theme_id, color_type, color_value) SELECT 3, 'pass_stop_style', 'REGULAR'  WHERE NOT EXISTS ( SELECT theme_id, color_type FROM mam.dp_color_theme WHERE theme_id = 3 and color_type = 'pass_stop_style');
INSERT INTO mam.dp_color_theme (theme_id, color_type, color_value) SELECT 3, 'next_stop_fill_color', '#F37739'  WHERE NOT EXISTS ( SELECT theme_id, color_type FROM mam.dp_color_theme WHERE theme_id = 3 and color_type = 'next_stop_fill_color');
INSERT INTO mam.dp_color_theme (theme_id, color_type, color_value) SELECT 3, 'next_stop_background_color', '#FFFFFF'  WHERE NOT EXISTS ( SELECT theme_id, color_type FROM mam.dp_color_theme WHERE theme_id = 3 and color_type = 'next_stop_background_color');
INSERT INTO mam.dp_color_theme (theme_id, color_type, color_value) SELECT 3, 'next_stop_style', 'REGULAR'  WHERE NOT EXISTS ( SELECT theme_id, color_type FROM mam.dp_color_theme WHERE theme_id = 3 and color_type = 'next_stop_style');
-- chuong, 02/25/2020 add check_hardware event IGNITION
INSERT INTO mam.check_hardware(name, eventcode, description, critical, updated) SELECT 'IGNITION ON',4, 'Please turn ignition on', 'true', now() WHERE NOT EXISTS (SELECT name FROM mam.check_hardware WHERE name = 'IGNITION ON');
INSERT INTO mam.check_hardware(name, eventcode, description, critical, updated) SELECT 'IGNITION OFF',5, 'Please turn ignition off', 'true', now() WHERE NOT EXISTS (SELECT name FROM mam.check_hardware WHERE name = 'IGNITION OFF');
--chuong , 03/24/2020 add setting for interval athena resend
INSERT INTO mam.MAM_Settings ( app_id, setting_name, setting_value, setting_description, setting_type, setting_updated,condition_id, default_value ) SELECT 1, 'INTERVAL_ATHENA_RETRY', '60', 'INTERVAL_ATHENA_RETRY', 'All', now(), 2 , '60' WHERE NOT EXISTS (SELECT setting_name FROM mam.MAM_Settings WHERE setting_name = 'INTERVAL_ATHENA_RETRY');
-- chuong, 03/19/2020 add setting DATE_FORMAT into mam_settings
INSERT INTO mam.condition (condition_id, condition_operator, condition_type, condition_value, condition_updated, condition_description) SELECT 9, 'IN', 'TEXT', 'MM/DD/YYYY;DD/MM/YYYY', now(), 'use for DATE_FORMAT' WHERE NOT EXISTS (SELECT condition_id FROM mam.condition where condition_id = 9)  ;
ALTER SEQUENCE mam.condition_condition_id_seq RESTART with 10;
INSERT INTO mam.MAM_Settings ( app_id, setting_name, setting_value, setting_description, setting_type, setting_updated,condition_id, default_value ) SELECT 1, 'DATE_FORMAT', 'MM/DD/YYYY', 'DATE_FORMAT', 'All', now(), 9 , 'MM/DD/YYYY' WHERE NOT EXISTS (SELECT setting_name FROM mam.MAM_Settings WHERE setting_name = 'DATE_FORMAT');
-- chuong , 03/24/2020 add setting Interval retry athena 
INSERT INTO mam.MAM_Settings ( app_id, setting_name, setting_value, setting_description, setting_type, setting_updated,condition_id, default_value ) SELECT 1, 'INTERVAL_ATHENA_RETRY', '60', 'INTERVAL_ATHENA_RETRY', 'All', now(), 2 , '60' WHERE NOT EXISTS (SELECT setting_name FROM mam.MAM_Settings WHERE setting_name = 'INTERVAL_ATHENA_RETRY');
--MinhVo, 04/15/2020 add setting DEFAULT_VOICE_TTS 
INSERT INTO mam.MAM_Settings ( app_id, setting_name, setting_value, setting_description, setting_type, setting_updated,condition_id, default_value ) SELECT 1, 'DEFAULT_VOICE_TTS', 'ON', 'DEFAULT_VOICE_TTS', 'All', now(), 1 , 'ON' WHERE NOT EXISTS (SELECT setting_name FROM mam.MAM_Settings WHERE setting_name = 'DEFAULT_VOICE_TTS');
-- chuong, 05/13/2020 add setting STOPVIEW_CONFIRM_TIME_THRESHOLD into mam_setting
INSERT INTO mam.mam_settings ( app_id, setting_name, setting_value, setting_description, setting_type, setting_updated,condition_id, default_value ) SELECT 1, 'STOPVIEW_CONFIRM_TIME_THRESHOLD', '5', 'STOPVIEW_CONFIRM_TIME_THRESHOLD', 'All', now(), 2 ,'5' WHERE NOT EXISTS (SELECT setting_name FROM mam.mam_settings WHERE setting_name = 'STOPVIEW_CONFIRM_TIME_THRESHOLD');
