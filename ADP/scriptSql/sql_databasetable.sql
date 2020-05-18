/*    ==Scripting Parameters==

    Source Server Version : SQL Server 2014 (12.0.4522)
    Source Database Engine Edition : Microsoft SQL Server Enterprise Edition
    Source Database Engine Type : Standalone SQL Server

    Target Server Version : SQL Server 2008 R2
    Target Database Engine Edition : Microsoft SQL Server Standard Edition
    Target Database Engine Type : Standalone SQL Server
*/
USE [master]
GO
/****** Object:  Database [MAM_MDM]    Script Date: 9/26/2017 8:59:29 AM ******/
CREATE DATABASE [MAM_MDM] 
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [MAM_MDM].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [MAM_MDM] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [MAM_MDM] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [MAM_MDM] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [MAM_MDM] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [MAM_MDM] SET ARITHABORT OFF 
GO
ALTER DATABASE [MAM_MDM] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [MAM_MDM] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [MAM_MDM] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [MAM_MDM] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [MAM_MDM] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [MAM_MDM] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [MAM_MDM] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [MAM_MDM] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [MAM_MDM] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [MAM_MDM] SET  DISABLE_BROKER 
GO
ALTER DATABASE [MAM_MDM] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [MAM_MDM] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [MAM_MDM] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [MAM_MDM] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [MAM_MDM] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [MAM_MDM] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [MAM_MDM] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [MAM_MDM] SET RECOVERY FULL 
GO
ALTER DATABASE [MAM_MDM] SET  MULTI_USER 
GO
ALTER DATABASE [MAM_MDM] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [MAM_MDM] SET DB_CHAINING OFF 
GO
EXEC sys.sp_db_vardecimal_storage_format N'MAM_MDM', N'ON'
GO
USE [MAM_MDM]
GO
/****** Object:  Schema [mam]    Script Date: 9/26/2017 8:59:29 AM ******/
CREATE SCHEMA [mam]
GO
/****** Object:  Schema [mdm]    Script Date: 9/26/2017 8:59:29 AM ******/
CREATE SCHEMA [mdm]
GO
/****** Object:  Table [dbo].[ActivityCode]    Script Date: 9/26/2017 8:59:29 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

/****** Object:  Table [dbo].[MAM_Application]    Script Date: 9/26/2017 8:59:29 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[MAM_Application](
	[app_id] [int] IDENTITY(1,1) NOT NULL,
	[app_name] [nvarchar](50) NULL,
	[app_version] [nvarchar](20) NULL,
 CONSTRAINT [Key11] PRIMARY KEY CLUSTERED 
(
	[app_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[MAM_Dev_Module]    Script Date: 9/26/2017 8:59:29 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[MAM_Dev_Module](
	[dev_module_id] [int] IDENTITY(1,1) NOT NULL,
	[dev_id] [int] NOT NULL,
	[module_id] [int] NOT NULL,
	[dev_module_enable] [bit] NULL,
	[dev_module_updated] [datetime] NULL,
 CONSTRAINT [Key8] PRIMARY KEY CLUSTERED 
(
	[dev_module_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO


/****** Object:  Table [dbo].[MAM_Dev_Route_Historical]    Script Date: 9/26/2017 8:59:29 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[MAM_Dev_Route_Historical](
	[dev_route_id] [int] IDENTITY(1,1) NOT NULL,
	[dev_id] [int] NOT NULL,
	[route_id] [nvarchar](20) NULL,
	[route_description] [nvarchar](100) NULL,
	[route_beginplan] [nvarchar](20) NULL,
	[route_endplan] [nvarchar](20) NULL,
	[dev_route_created] [datetime] NULL,
 CONSTRAINT [Key10] PRIMARY KEY CLUSTERED 
(
	[dev_route_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[MAM_Module_Setting]    Script Date: 9/26/2017 8:59:29 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[MAM_Module_Setting](
	[module_setting_id] [int] IDENTITY(1,1) NOT NULL,
	[module_id] [int] NOT NULL,
	[setting_id] [int] NOT NULL,
 CONSTRAINT [Key9] PRIMARY KEY CLUSTERED 
(
	[module_setting_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[MAM_Modules]    Script Date: 9/26/2017 8:59:29 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[MAM_Modules](
	[module_id] [int] IDENTITY(1,1) NOT NULL,
	[module_name] [nvarchar](50) NULL,
	[module_description] [nvarchar](250) NULL,
	[app_id] [int] NULL,
	
 CONSTRAINT [Key4] PRIMARY KEY CLUSTERED 
(
	[module_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
ALTER TABLE MAM_Modules ADD [dev_module_enable] [bit] NULL
GO
/****** Object:  Table [dbo].[MAM_Settings]    Script Date: 9/26/2017 8:59:29 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[MAM_Settings](
	[setting_id] [int] IDENTITY(1,1) NOT NULL,
	[app_id] [int] NULL,
	[setting_name] [nvarchar](50) NULL,
	[setting_value] [nvarchar](250) NULL,
	[setting_description] [nvarchar](250) NULL,
	[setting_type] [nvarchar](50) NULL,
	[setting_updated] [datetime] NULL,
 CONSTRAINT [Key5] PRIMARY KEY CLUSTERED 
(
	[setting_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[MAM_Sync_Info]    Script Date: 9/26/2017 8:59:29 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[MAM_Sync_Info](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[name] [nvarchar](100) NULL,
	[type] [nvarchar](50) NULL,
	[date_create] [date] NULL,
	[url] [nvarchar](200) NULL,
	[file_type] [nvarchar](20) NULL,
 CONSTRAINT [PK_MAM_Sync_Info] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[MDM_Device_Mode]    Script Date: 9/26/2017 8:59:29 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[MDM_Device_Mode](
	[dev_mode_id] [int] IDENTITY(1,1) NOT NULL,
	[dev_id] [int] NOT NULL,
	[mode_id] [int] NOT NULL,
	[dev_bus_id] [nvarchar](50) NULL,
	[dev_mode_updated] [datetime] NULL,
 CONSTRAINT [Key6] PRIMARY KEY CLUSTERED 
(
	[dev_mode_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[MDM_Devices]    Script Date: 9/26/2017 8:59:29 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[MDM_Devices](
	[dev_id] [int] IDENTITY(1,1) NOT NULL,
	[dev_model] [nvarchar](50) NULL,
	[dev_serialnumber] [nvarchar](50) NULL,
	[dev_name] [nvarchar](50) NULL,
	[dev_macaddress] [nvarchar](50) NULL,
	[dev_os] [nvarchar](50) NULL,
	[dev_updated] [datetime] NULL,
 CONSTRAINT [Key1] PRIMARY KEY CLUSTERED 
(
	[dev_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[MDM_Modes]    Script Date: 9/26/2017 8:59:29 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[MDM_Modes](
	[mode_id] [int] IDENTITY(1,1) NOT NULL,
	[mode_name] [nvarchar](50) NULL,
	[mode_description] [nvarchar](250) NULL,
 CONSTRAINT [Key2] PRIMARY KEY CLUSTERED 
(
	[mode_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

/****** Object:  Table [dbo].[MAM_Default_Module]   ******/
/*Note: app_id to easy code api, don't use join two tables - Thanh - Minh */
IF NOT EXISTS(SELECT * FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'MAM_Default_Module')
 BEGIN
  CREATE TABLE [dbo].[MAM_Default_Module](
   [default_module_id] [int] IDENTITY(1,1) NOT NULL,
   [app_id] [int] NOT NULL,
   [module_id] [int] NOT NULL,      
   [default_module_created] [datetime] NULL 
   CONSTRAINT [KeyDefaultModule] PRIMARY KEY CLUSTERED 
  (
   [default_module_id] ASC
  )WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
  ) ON [PRIMARY]
 END
ELSE
 BEGIN
  print('Update MAM_Default_Module Table');
 END
GO


/****** Object:  Table [dbo].[StudentTagIDMappings]    Script Date: 9/26/2017 8:59:29 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[StudentTagIDMappings](
	[EdulogStudentID] [varchar](50) NOT NULL,
	[TagID] [varchar](50) NULL,
 CONSTRAINT [PK_StudentTagIDMappings] PRIMARY KEY CLUSTERED 
(
	[EdulogStudentID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[BillingType_ActivityCode] Script Date: 9/27/2017 10:00:00 AM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[BillingType_ActivityCode](
	[BillingID] [varchar](4) NULL,
	[StartID] [int] NULL
) ON [PRIMARY]

GO
/****** Object:  Index [IX_device_module]    Script Date: 9/26/2017 8:59:29 AM ******/
CREATE NONCLUSTERED INDEX [IX_device_module] ON [dbo].[MAM_Dev_Module]
(
	[dev_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
/****** Object:  Index [IX_module_device]    Script Date: 9/26/2017 8:59:29 AM ******/
CREATE NONCLUSTERED INDEX [IX_module_device] ON [dbo].[MAM_Dev_Module]
(
	[module_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
/****** Object:  Index [IX_dev_route]    Script Date: 9/26/2017 8:59:29 AM ******/
CREATE NONCLUSTERED INDEX [IX_dev_route] ON [dbo].[MAM_Dev_Route_Historical]
(
	[dev_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
/****** Object:  Index [IX_module_setting]    Script Date: 9/26/2017 8:59:29 AM ******/
CREATE NONCLUSTERED INDEX [IX_module_setting] ON [dbo].[MAM_Module_Setting]
(
	[module_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
/****** Object:  Index [IX_setting_module]    Script Date: 9/26/2017 8:59:29 AM ******/
CREATE NONCLUSTERED INDEX [IX_setting_module] ON [dbo].[MAM_Module_Setting]
(
	[setting_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
/****** Object:  Index [IX_Relationship11]    Script Date: 9/26/2017 8:59:29 AM ******/
CREATE NONCLUSTERED INDEX [IX_Relationship11] ON [dbo].[MAM_Modules]
(
	[app_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
/****** Object:  Index [IX_device_mode]    Script Date: 9/26/2017 8:59:29 AM ******/
CREATE NONCLUSTERED INDEX [IX_device_mode] ON [dbo].[MDM_Device_Mode]
(
	[dev_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
/****** Object:  Index [IX_mode_device]    Script Date: 9/26/2017 8:59:29 AM ******/
CREATE NONCLUSTERED INDEX [IX_mode_device] ON [dbo].[MDM_Device_Mode]
(
	[mode_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]

/****** Object:  Index [IX_default_module]    Script Date: 9/26/2017 8:59:29 AM ******/
CREATE NONCLUSTERED INDEX [IX_default_module] ON [dbo].[MAM_Default_Module]
(
	[default_module_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]

GO
ALTER TABLE [dbo].[MAM_Dev_Module]  WITH CHECK ADD  CONSTRAINT [device_module] FOREIGN KEY([dev_id])
REFERENCES [dbo].[MDM_Devices] ([dev_id])
GO
ALTER TABLE [dbo].[MAM_Dev_Module] CHECK CONSTRAINT [device_module]
GO
ALTER TABLE [dbo].[MAM_Dev_Module]  WITH CHECK ADD  CONSTRAINT [module_device] FOREIGN KEY([module_id])
REFERENCES [dbo].[MAM_Modules] ([module_id])
GO
ALTER TABLE [dbo].[MAM_Dev_Module] CHECK CONSTRAINT [module_device]
GO
ALTER TABLE [dbo].[MAM_Dev_Route_Historical]  WITH CHECK ADD  CONSTRAINT [dev_route] FOREIGN KEY([dev_id])
REFERENCES [dbo].[MDM_Devices] ([dev_id])
GO
ALTER TABLE [dbo].[MAM_Dev_Route_Historical] CHECK CONSTRAINT [dev_route]
GO
ALTER TABLE [dbo].[MAM_Module_Setting]  WITH CHECK ADD  CONSTRAINT [module_setting] FOREIGN KEY([module_id])
REFERENCES [dbo].[MAM_Modules] ([module_id])
GO
ALTER TABLE [dbo].[MAM_Module_Setting] CHECK CONSTRAINT [module_setting]
GO
ALTER TABLE [dbo].[MAM_Module_Setting]  WITH CHECK ADD  CONSTRAINT [setting_module] FOREIGN KEY([setting_id])
REFERENCES [dbo].[MAM_Settings] ([setting_id])
GO
ALTER TABLE [dbo].[MAM_Module_Setting] CHECK CONSTRAINT [setting_module]
GO
ALTER TABLE [dbo].[MAM_Modules]  WITH CHECK ADD  CONSTRAINT [application_module] FOREIGN KEY([app_id])
REFERENCES [dbo].[MAM_Application] ([app_id])
GO
ALTER TABLE [dbo].[MAM_Modules] CHECK CONSTRAINT [application_module]
GO
ALTER TABLE [dbo].[MDM_Device_Mode]  WITH CHECK ADD  CONSTRAINT [device_mode] FOREIGN KEY([dev_id])
REFERENCES [dbo].[MDM_Devices] ([dev_id])
GO
ALTER TABLE [dbo].[MDM_Device_Mode] CHECK CONSTRAINT [device_mode]
GO
ALTER TABLE [dbo].[MDM_Device_Mode]  WITH CHECK ADD  CONSTRAINT [mode_device] FOREIGN KEY([mode_id])
REFERENCES [dbo].[MDM_Modes] ([mode_id])
GO
ALTER TABLE [dbo].[MDM_Device_Mode] CHECK CONSTRAINT [mode_device]
GO

IF NOT EXISTS(SELECT * FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'MAM_DEV_SyncInfo')
 BEGIN
CREATE TABLE [dbo].[MAM_DEV_SyncInfo](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[app_id] [int] NULL,
	[dev_id] [int] NULL,	
	[sync_date] [datetime] NULL,
 CONSTRAINT [Key_MAM_DEV_SyncInfo] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
END
ELSE
 BEGIN
  print('Update MAM_DEV_SyncInfo Table');
 END
GO

IF NOT EXISTS(SELECT * FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'MAM_Device_Settings')
 BEGIN
CREATE TABLE [dbo].[MAM_Device_Settings](
	[dev_setting_id] [int] IDENTITY(1,1) NOT NULL,
	[app_id] [int] NULL,
	[dev_id] [int] NULL,
	[setting_name] [nvarchar](50) NULL,
	[setting_value] [nvarchar](250) NULL,
	[setting_description] [nvarchar](250) NULL,
	[setting_type] [nvarchar](50) NULL,
	[setting_updated] [datetime] NULL,
 CONSTRAINT [Key_MAM_Device_Settings] PRIMARY KEY CLUSTERED 
(
	[dev_setting_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
END
ELSE
 BEGIN
  print('Update MAM_Device_Settings Table');
 END
GO
if NOT EXISTS (SELECT * FROM syscolumns where name='dev_module_default' and id = (select id from dbo.sysobjects 
	WHERE name = 'MAM_Dev_Module' and OBJECTPROPERTY(id, N'IsUserTable') = 1))
ALTER TABLE MAM_Dev_Module ADD dev_module_default bit null
GO
update MAM_Dev_Module set dev_module_default = 1 where module_id in 
(select top 1 module_id from MAM_Default_Module order by default_module_created )
GO
update MAM_Dev_Module set dev_module_default = 0 where module_id not in
(select top 1 module_id from MAM_Default_Module order by default_module_created )
GO

IF NOT EXISTS(SELECT * FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'MAM_Events')
 BEGIN
CREATE TABLE [dbo].MAM_Events(
	[event_id] [int] IDENTITY(1,1) NOT NULL,
	[event_name] [nvarchar](50) NULL,	
	[event_description] [nvarchar](250) NULL,	
	[event_updated] [datetime] NULL,
 CONSTRAINT [Key_MAM_Events] PRIMARY KEY CLUSTERED 
(
	[event_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
END
ELSE
 BEGIN
  print('Update MAM_event Table');
 END
GO

IF NOT EXISTS(SELECT * FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'MAM_Device_Event')
 BEGIN
CREATE TABLE [dbo].MAM_Device_Event(
	[device_event_id] [int] IDENTITY(1,1) NOT NULL,
	[dev_id] [int],
	[bus_id] nvarchar(50),		
	[latitude] float,
	[longitude] float,
	[event_time] datetime,
	[event_send] datetime,
	[event_write_tstamp] datetime,
	[event_id] [int],
	[event_name] [nvarchar](50) NULL,
	[status] nvarchar(50)	
 CONSTRAINT [Key_MAM_Device_Event] PRIMARY KEY CLUSTERED 
(
	[device_event_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
END
ELSE
 BEGIN
  print('Update MAM_Device_Event Table');
 END
GO

IF NOT EXISTS(SELECT * FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'MAM_Device_Run_Event')
 BEGIN
CREATE TABLE [dbo].MAM_Device_Run_Event(
	[dev_run_event_id] [int] IDENTITY(1,1) NOT NULL,
	[dev_id] [int],
	[bus_id] nvarchar(50),		
	[latitude] float,
	[longitude] float,
	[event_time] datetime,
	[event_send] datetime,
	[event_write_tstamp] datetime,
	[event_id] [int],
	[event_name] [nvarchar](50) NULL,	
	[status] nvarchar(50),
	[run_id] nvarchar(50) NULL,
	[runroute] nvarchar(50),
	[beginactual] datetime NULL,
	[endactual] datetime NULL,
	[earlylate] float NULL
	
 CONSTRAINT [Key_MAM_Device_Run_Event] PRIMARY KEY CLUSTERED 
(
	[dev_run_event_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
END
ELSE
 BEGIN
  print('Update MAM_Device_Run_Event Table');
 END
GO

IF NOT EXISTS(SELECT * FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'MAM_Device_Stop_Event')
 BEGIN
CREATE TABLE [dbo].MAM_Device_Stop_Event(
	[dev_stop_event_id] [int] IDENTITY(1,1) NOT NULL,
	[dev_id] [int],
	[bus_id] nvarchar(50),		
	[latitude] float,
	[longitude] float,
	[event_time] datetime,
	[event_send] datetime,
	[event_write_tstamp] datetime,
	[event_id] [int],
	[event_name] [nvarchar](50) NULL,	
	[status] nvarchar(50) NULL,	
	[condition] nvarchar(50) NULL,
	[duration] float NULL,
	[actualarrival] datetime NULL,
	[actualdeparture] datetime NULL,
	[stoprunid] nvarchar(50),
	[run_id] nvarchar(50) NULL,
	[runroute] nvarchar(50) NULL,
	[loadCount] int NULL,
	[onBoardStudent] int NULL,
	[disembarkStudent] int NULL
 CONSTRAINT [Key_MAM_Device_Stop_Event] PRIMARY KEY CLUSTERED 
(
	[dev_stop_event_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
END
ELSE
 BEGIN
  print('Update MAM_Device_Stop_Event Table');
 END
GO

IF NOT EXISTS(SELECT * FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'MAM_Device_Student_Event')
 BEGIN
CREATE TABLE [dbo].MAM_Device_Student_Event(
	[dev_stu_event_id] [int] IDENTITY(1,1) NOT NULL,
	[dev_id] [int],
	[bus_id] nvarchar(50),		
	[latitude] float,
	[longitude] float,
	[event_time] datetime,
	[event_send] datetime,
	[event_write_tstamp] datetime,
	[event_id] [int],
	[event_name] [nvarchar](50) NULL,
	[student_id] nvarchar(50),
	[student_name] nvarchar(50),
	[status] nvarchar(50),	
	[stoprunid] nvarchar(50),
	[run_id] nvarchar(50),
	[runroute] nvarchar(50)
 CONSTRAINT [Key_MAM_Device_Student_Event] PRIMARY KEY CLUSTERED 
(
	[dev_stu_event_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
END
ELSE
 BEGIN
  print('Update MAM_Device_Student_Event Table');
 END
GO

IF NOT EXISTS(SELECT * FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'MAM_Device_RawEvent')
 BEGIN
CREATE TABLE [dbo].MAM_Device_RawEvent(
	[device_rawevent_id] [int] IDENTITY(1,1) NOT NULL,
	[dev_id] [int],
	[bus_id] nvarchar(50),		
	[latitude] float,
	[longitude] float,
	[event_time] datetime,
	[event_send] datetime,
	[event_write_tstamp] datetime,
	[event_id] [int],
	[event_name] [nvarchar](50) NULL
 CONSTRAINT [Key_MAM_Device_RawEvent] PRIMARY KEY CLUSTERED 
(
	[device_rawevent_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
END
ELSE
 BEGIN
  print('Update MAM_Device_RawEvent Table');
 END
GO

IF NOT EXISTS(SELECT * FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'MAM_Files')
 BEGIN
CREATE TABLE [dbo].MAM_Files(
	[file_id] [int] IDENTITY(1,1) NOT NULL,
	[file_type] [nvarchar](50) NULL,
	[file_version] nvarchar(50) NULL,		
	[file_url] [nvarchar](50) NULL,	
	[file_updated] datetime NULL
 CONSTRAINT [Key_MAM_Files] PRIMARY KEY CLUSTERED 
(
	[file_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
END
ELSE
 BEGIN
  print('Update MAM_Files Table');
 END
GO

/*11/20/2017 minhvo, new DP_routes, DP_runs, DP_stops table*/
IF NOT EXISTS(SELECT * FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'DP_Routes')
 BEGIN
CREATE TABLE [dbo].DP_Routes(
	[routeid] [int] IDENTITY(1,1) NOT NULL,
	[routeNumber] [nvarchar](50) NULL,
	[dev_id] nvarchar(50) NULL,		
	[routeDesc] nvarchar(max) NULL,	
	[routeDate] datetime NULL,
	[lastUpdated] datetime NULL
 CONSTRAINT [Key_DP_Routes] PRIMARY KEY CLUSTERED 
(
	[routeid] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
END
ELSE
 BEGIN
  print('Update DP_Routes Table');
 END
GO

IF NOT EXISTS(SELECT * FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'DP_Runs')
 BEGIN
CREATE TABLE [dbo].DP_Runs(
	[runroute] [int] IDENTITY(1,1) NOT NULL,
	[runid] [nvarchar](50) NULL,
	[routeid] int NULL,
	[toFrom] [nvarchar](50) NULL,
	[beginPlannedTime] datetime NULL,
	[endPlannedTime] datetime NULL,
	[actualRunStart] datetime NULL,
	[actualRunEnd] datetime NULL,
	[runDate] datetime NULL,
	[lastUpdated] datetime NULL
 CONSTRAINT [Key_DP_Runs] PRIMARY KEY CLUSTERED 
(
	[runroute] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
END
ELSE
 BEGIN
  print('Update DP_Runs Table');
 END
GO

IF NOT EXISTS(SELECT * FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'DP_Stops')
 BEGIN
CREATE TABLE [dbo].DP_Stops(
	[stoprunid] [int] IDENTITY(1,1) NOT NULL,
	[stopid] [nvarchar](50) NULL,
	[runroute] int NULL,		
	[location] nvarchar(max) NULL,
	[latitude] float NULL,
	[longitude] float NULL,
	[numberOfStudents] int  NULL,
	[stopDesc] nvarchar(max) NULL,
	[stopTime] datetime NULL,
	[lastUpdated] datetime NULL
 CONSTRAINT [Key_DP_Stops] PRIMARY KEY CLUSTERED 
(
	[stoprunid] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
END
ELSE
 BEGIN
  print('Update DP_Stops Table');
 END
GO

ALTER TABLE [dbo].DP_Stops WITH CHECK ADD CONSTRAINT [run_stop] FOREIGN KEY([runroute])
REFERENCES [dbo].DP_Runs ([runroute])
GO

ALTER TABLE [dbo].DP_Runs WITH CHECK ADD CONSTRAINT [route_run] FOREIGN KEY([routeid])
REFERENCES [dbo].DP_Routes ([routeid])
GO
/*12/12/2017*/
ALTER TABLE MAM_Modules ADD [dev_module_enable] [bit] NULL
GO

update MAM_Modules set [dev_module_enable] = 1 where  module_name in ('eDTA','Additional Employee','Switch Session Owner');
update MAM_Modules set [dev_module_enable] = 0 where  module_name not in ('eDTA','Additional Employee','Switch Session Owner');
GO

USE [master]
GO
ALTER DATABASE [MAM_MDM] SET  READ_WRITE 
GO
