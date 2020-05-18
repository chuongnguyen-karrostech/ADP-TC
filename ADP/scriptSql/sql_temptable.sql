USE [ETEXT]
GO
IF Not EXISTS(SELECT * FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'ActivityCode')
Begin
CREATE TABLE [dbo].[ActivityCode](
	[StartID] [int] NOT NULL,
	[Description] [varchar](50) NULL,
 CONSTRAINT [PK_ActivityCode] PRIMARY KEY CLUSTERED 
(
	[StartID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
End
GO
/****** Object:  Table [dbo].[BillingType]    Script Date: 9/26/2017 8:59:29 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF Not EXISTS(SELECT * FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'BillingType')
Begin
CREATE TABLE [dbo].[BillingType](
	[BillingID] [varchar](4) NOT NULL,
	[BillingDescription] [varchar](50) NULL,
 CONSTRAINT [PK_BillingType] PRIMARY KEY CLUSTERED 
(
	[BillingID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
End
GO
/****** Object:  Table [dbo].[LocationAccessList]    Script Date: 9/26/2017 8:59:29 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF Not EXISTS(SELECT * FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'LocationAccessList')
Begin
CREATE TABLE [dbo].[LocationAccessList](
	[id] [bigint] NOT NULL,
	[Code] [varchar](8) NULL,
	[Name] [varchar](40) NULL,
	[X] [float] NULL,
	[Y] [float] NULL,
	[lat] [float] NULL,
	[lon] [float] NULL,
	[radius] [float] NULL,
	[LocationType] [varchar](10) NULL,
 CONSTRAINT [PK_LocationAccessList] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
End
GO

/****** Object:  Table [dbo].[StudentTagIDMappings]    Script Date: 9/26/2017 8:59:29 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF Not EXISTS(SELECT * FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'StudentTagIDMappings')
Begin
CREATE TABLE [dbo].[StudentTagIDMappings](
	[EdulogStudentID] [varchar](50) NOT NULL,
	[TagID] [varchar](50) NULL,
 CONSTRAINT [PK_StudentTagIDMappings] PRIMARY KEY CLUSTERED 
(
	[EdulogStudentID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
End
GO
/****** Object:  Table [dbo].[BillingType_ActivityCode] Script Date: 9/27/2017 10:00:00 AM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO
--Thanh update EventCodes to 88
Update [dbo].[EventCodes] set [EventCodes_StartID] = 88 where ([EventCodes_StartID] = 80 and [EventCodes_Description] like 'Standard')
--Update StopLoad and Unknown Student in ETGPS
IF EXISTS(SELECT * FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'StopLoad')
	BEGIN
	    IF NOT EXISTS(SELECT * FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'StopLoad' and COLUMN_NAME = 'Longitude')
			BEGIN
				ALTER TABLE StopLoad
				ADD Longitude float
			END
        IF NOT EXISTS(SELECT * FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'StopLoad' and COLUMN_NAME = 'Latitude')
			BEGIN
				ALTER TABLE StopLoad
				ADD Latitude float 
			END
	END
ELSE
	BEGIN
		CREATE TABLE [dbo].[StopLoad ](
			[AutoID] [bigint] IDENTITY(1,1) NOT NULL,
			[VehicleID] [varchar](25) NULL,
			[EmployeeID] [varchar](25) NULL,
			[Date] [datetime] NULL,
			[Route] [varchar](10) NULL,
			[Run] [varchar](11) NULL,
			[Stop] [varchar](11) NULL,
			[StudCount] [smallint] NULL,
			[Longitude] [float] NULL,
			[Latitude] [float] NULL,
		 CONSTRAINT [PK_StopLoad] PRIMARY KEY CLUSTERED 
		(
			[AutoID] ASC
		)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
		) ON [PRIMARY]
	END
GO
--minh vo new VehicleUnitMapping 
IF Not EXISTS(SELECT * FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'VehicleUnitMapping')
Begin
CREATE TABLE [VehicleUnitMapping](
	[UnitID] [varchar](50) NULL,
	[BusNumber] [varchar](50) NULL
) ON [PRIMARY]
End
GO

USE [#ETGPSDATABASE]
IF NOT EXISTS(SELECT * FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'UnknownStudent')
	BEGIN
		CREATE TABLE [dbo].[UnknownStudent](
			[UnknownID] [int] IDENTITY(1,1) NOT NULL,
			[UnitID] [varchar](50) NULL,
			[StudentName] [varchar](50) NULL,
			[Longitude] [float] NULL,
			[Latitude] [float] NULL,
			[PositionTimeStamp] [datetime] NULL,
			[Source] [varchar](50) NULL,
		 CONSTRAINT [PK_UnknownStudent] PRIMARY KEY CLUSTERED 
		(
			[UnknownID] ASC
		)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
		) ON [PRIMARY]
	END
GO