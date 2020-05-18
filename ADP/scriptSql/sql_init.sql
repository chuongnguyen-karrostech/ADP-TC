USE [@mammdm@]
GO

INSERT [dbo].[MDM_Modes] ( [mode_name], [mode_description]) VALUES ( N'Floating', N'Floating mode')
GO
INSERT [dbo].[MDM_Modes] ( [mode_name], [mode_description]) VALUES ( N'Permanently Mounted', N'Permanently mounted mode')
GO
INSERT [dbo].[MAM_Application] ( [app_name], [app_version]) VALUES ( N'Driver portal', N'2.0.7')
GO
INSERT [dbo].[MAM_Modules] ( [module_name], [module_description], [app_id]) VALUES ( N'Route Builder', N'Route build', 1)
GO
INSERT [dbo].[MAM_Modules] ( [module_name], [module_description], [app_id]) VALUES ( N'eDTA', N'Driver Time Attendance', 1)
GO
INSERT [dbo].[MAM_Modules] ( [module_name], [module_description], [app_id]) VALUES ( N'Select Route', N'Select Route', 1)
INSERT [dbo].[MAM_Modules] ( [module_name], [module_description], [app_id]) VALUES ( N'Driving Mode', N'Driving Mode', 1)
INSERT [dbo].[MAM_Modules] ( [module_name], [module_description], [app_id]) VALUES ( N'Assigned Route', N'Assigned Route', 1)
INSERT [dbo].[MAM_Modules] ( [module_name], [module_description], [app_id]) VALUES ( N'Additional Employee', N'Additional Employee', 1)
INSERT [dbo].[MAM_Modules] ( [module_name], [module_description], [app_id]) VALUES ( N'Switch Session Owner', N'Switch Session Owner', 1)
INSERT [dbo].[MAM_Modules] ( [module_name], [module_description], [app_id]) VALUES ( N'Settings', N'Settings', 1)
GO
INSERT [dbo].[MAM_Settings] ( [app_id], [setting_name], [setting_value], [setting_description], [setting_type], [setting_updated]) VALUES ( 1, N'TRANSMISSION_FREQUENCY', N'10', N'TRANSMISSION_FREQUENCY', N'All', getdate())
GO
INSERT [dbo].[MAM_Settings] ( [app_id], [setting_name], [setting_value], [setting_description], [setting_type], [setting_updated]) VALUES ( 1, N'STOP_RADIUS', N'10', N'STOP_RADIUS', N'All', getdate())
GO
INSERT [dbo].[MAM_Settings] ( [app_id], [setting_name], [setting_value], [setting_description], [setting_type], [setting_updated]) VALUES ( 1, N'NO_MOTION_SPEED_THRESHOLD', N'5', N'NO_MOTION_SPEED_THRESHOLD', N'All', getdate())
GO
INSERT [dbo].[MAM_Settings] ( [app_id], [setting_name], [setting_value], [setting_description], [setting_type], [setting_updated]) VALUES ( 1, N'NO_MOTION_TIME_THRESHOLD', N'30', N'NO_MOTION_TIME_THRESHOLD', N'All', getdate())
GO
INSERT [dbo].[MAM_Settings] ( [app_id], [setting_name], [setting_value], [setting_description], [setting_type], [setting_updated]) VALUES ( 1, N'AUDIO_IN_MOTION', N'ON', N'AUDIO_IN_MOTION', N'All', getdate())
GO
INSERT [dbo].[MAM_Settings] ( [app_id], [setting_name], [setting_value], [setting_description], [setting_type], [setting_updated]) VALUES ( 1, N'VIDEO_IN_MOTION', N'OFF', N'VIDEO_IN_MOTION', N'All', getdate())
GO
INSERT INTO [dbo].[MAM_Settings] (app_id,setting_name,setting_type,setting_updated,setting_value,setting_description) values (1,'LIMIT_NUMBER_OF_DEVICES','All',getdate(),300,'Limit number of devices')
GO
INSERT [dbo].[MAM_Settings] ( [app_id], [setting_name], [setting_value], [setting_description], [setting_type], [setting_updated]) VALUES ( 1, N'IDLE_TIME_THRESHOLD', N'300', N'IDLE_TIME_THRESHOLD', N'All', getdate())
GO
INSERT [dbo].[MAM_Settings] ( [app_id], [setting_name], [setting_value], [setting_description], [setting_type], [setting_updated]) VALUES ( 1, N'SCHOOL_RADIUS', N'30', N'SCHOOL_RADIUS', N'All', getdate())
GO
INSERT [dbo].[MAM_Settings] ( [app_id], [setting_name], [setting_value], [setting_description], [setting_type], [setting_updated]) VALUES ( 1, N'DISPLAY_UNITS', N'IMPERIAL', N'DISPLAY_UNITS', N'All', getdate())
GO
INSERT [dbo].[MAM_Settings] ( [app_id], [setting_name], [setting_value], [setting_description], [setting_type], [setting_updated]) VALUES ( 1, N'KIOSK_MODE', N'ON', N'KIOSK_MODE', N'All', getdate())
GO
INSERT [dbo].[MAM_Settings] ( [app_id], [setting_name], [setting_value], [setting_description], [setting_type], [setting_updated]) VALUES ( 1, N'MATCHTIMEWINDOWINSECONDS', N'1800', N'MatchTimeWindowInSeconds', N'All', getdate())
GO
INSERT [dbo].[MAM_Settings] ( [app_id], [setting_name], [setting_value], [setting_description], [setting_type], [setting_updated]) VALUES ( 1, N'AUTOSWIPECARDSATSCHOOL', N'ON', N'Auto swipe cards at school', N'All', getdate())
GO
--Thanh 12/13/2017 Add enable job
INSERT [dbo].[MAM_Settings] ( [app_id], [setting_name], [setting_value], [setting_description], [setting_type], [setting_updated]) VALUES ( 1, N'ENABLED_JOBCODE_PERFORMINGTASK', N'ON', N'Enabled Jobcode Performingtask', N'All', getdate())
Insert into [MAM_Default_Module] values (1, 2, getdate())
GO

insert into MAM_Dev_Module
select d.dev_id,m.module_id, 1 as dev_module_enable, getdate() as dev_module_update, 0 as dev_module_default
from MDM_Devices d, MAM_Modules m where m.module_name<>'Settings'
except
select dev_id,module_id, 1 as dev_module_enable, getdate() as dev_module_update, 0 as dev_module_default
from MAM_Dev_Module  
GO
insert into MAM_Dev_Module
select d.dev_id,m.module_id, 0 as dev_module_enable, getdate() as dev_module_update, 0 as dev_module_default
from MDM_Devices d, MAM_Modules m where m.module_name ='Settings'
except
select dev_id,module_id, 1 as dev_module_enable, getdate() as dev_module_update, 0 as dev_module_default
from MAM_Dev_Module 
GO

insert into [MAM_Device_Settings] ( [app_id], [dev_id], [setting_name], [setting_value], [setting_description], [setting_type], [setting_updated]) 
select [app_id], [dev_id], [setting_name], [setting_value], [setting_description], [setting_type], [setting_updated]
from MDM_Devices, MAM_Settings where setting_name in ('NO_MOTION_SPEED_THRESHOLD', 'NO_MOTION_TIME_THRESHOLD', 'KIOSK_MODE')
order by [dev_id], [setting_name]
GO

SET IDENTITY_INSERT [MAM_Events] ON
GO
INSERT INTO [MAM_Events](event_id,[event_name],[event_description],[event_updated]) VALUES (101,'RAW_EVENT','Live Cookie',getdate())
GO
SET IDENTITY_INSERT [MAM_Events] OFF
GO
INSERT INTO [MAM_Events]([event_name],[event_description],[event_updated]) VALUES ('START_RUN','Start Run',getdate())
GO
INSERT INTO [MAM_Events]([event_name],[event_description],[event_updated]) VALUES ('END_RUN','End Run',getdate())
GO
INSERT INTO [MAM_Events]([event_name],[event_description],[event_updated]) VALUES ('NO_MOTION_BEGIN','No Motion Begin',getdate())
GO
INSERT INTO [MAM_Events]([event_name],[event_description],[event_updated]) VALUES ('NO_MOTION_END','No Motion End',getdate())
GO
INSERT INTO [MAM_Events]([event_name],[event_description],[event_updated]) VALUES ('STOP_BEGIN','Stop Begin',getdate())
GO
INSERT INTO [MAM_Events]([event_name],[event_description],[event_updated]) VALUES ('STOP_END','Stop End',getdate())
GO
INSERT INTO [MAM_Events]([event_name],[event_description],[event_updated]) VALUES ('EXCESSIVE_IDLE_BEGIN','Excessive Idle Begin',getdate())
GO
INSERT INTO [MAM_Events]([event_name],[event_description],[event_updated]) VALUES ('EXCESSIVE_IDLE_END','Excessive Idle End',getdate())
GO
INSERT INTO [MAM_Events]([event_name],[event_description],[event_updated]) VALUES ('STUDENT_BOARD','Student Board',getdate())
GO
INSERT INTO [MAM_Events]([event_name],[event_description],[event_updated]) VALUES ('STUDENT_DISEMBARK','Student Disembark',getdate())
GO
INSERT INTO [MAM_Events]([event_name],[event_description],[event_updated]) VALUES ('LOG_IN','Log In',getdate())
GO
INSERT INTO [MAM_Events]([event_name],[event_description],[event_updated]) VALUES ('LOG_OUT','Log Out',getdate())
GO
INSERT INTO [MAM_Events]([event_name],[event_description],[event_updated]) VALUES ('STOP_STATUS','Stop Status',getdate())
GO
INSERT INTO [MAM_Events]([event_name],[event_description],[event_updated]) VALUES ('STOP_MISSED','Stop Missed',getdate())
GO

/* minh vo 11/17/2017*/
INSERT [dbo].[MAM_Settings] ( [app_id], [setting_name], [setting_value], [setting_description], [setting_type], [setting_updated]) VALUES ( 1, N'AUTO_LOG_OUT_THRESHOLD', N'600', N'Auto Log Out Threshold', N'All', getdate())
GO

update mam_modules set dev_module_enable = 1 where  module_name in ('eDTA','Additional Employee','Switch Session Owner');

update mam.mam_modules set dev_module_enable = 0 where  module_name not in ('eDTA','Additional Employee','Switch Session Owner');


delete from dbo.mam_dev_route_historical USING (select route_id,max(dev_route_created) as maxtime FROM dbo.mam_dev_route_historical group by route_id) a where mam_dev_route_historical.route_id = a.route_id and mam_dev_route_historical.dev_route_created<a.maxtime ;
    
/*Thanh script set file to download dp*/
--INSERT INTO mam_files(file_type, file_version, file_url, file_updated)
--	VALUES ('maps', '1.1.0', 'http://113.20.115.84:9995/live/dphomer/map.mbtiles', getdate());
    
--INSERT INTO mam_files(file_type, file_version, file_url, file_updated)
--	VALUES ('database', null, 'http://113.20.115.84:9995/live/dphomer/encypteddb.sqlite', getdate());