USE [master]
GO
/****** Object:  Database [TaskManagement]    Script Date: 6/13/2023 1:11:23 AM ******/
CREATE DATABASE [TaskManagement]
GO

USE [TaskManagement]
GO
/****** Object:  User [userTask]    Script Date: 6/13/2023 1:11:23 AM ******/
CREATE USER [userTask] FOR LOGIN [taskUser] WITH DEFAULT_SCHEMA=[db_owner]
GO
/****** Object:  Table [dbo].[Projects]    Script Date: 6/13/2023 1:11:23 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Projects](
                                 [Id] [varchar](36) NOT NULL,
                                 [Name] [nvarchar](100) NOT NULL,
                                 [Description] [nvarchar](500) NOT NULL,
                                 [IsDeleted] [bit] NOT NULL,
                                 PRIMARY KEY CLUSTERED
                                     (
                                      [Id] ASC
                                         )WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Sessions]    Script Date: 6/13/2023 1:11:23 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Sessions](
                                 [Id] [varchar](36) NOT NULL,
                                 [UserId] [varchar](36) NOT NULL,
                                 [TimeCreated] [datetime2](7) NOT NULL,
                                 PRIMARY KEY CLUSTERED
                                     (
                                      [Id] ASC
                                         )WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Tasks]    Script Date: 6/13/2023 1:11:23 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Tasks](
                              [Id] [varchar](36) NOT NULL,
                              [Name] [nvarchar](100) NOT NULL,
                              [Description] [nvarchar](500) NOT NULL,
                              [Status] [varchar](50) NOT NULL,
                              [ProjectId] [varchar](36) NOT NULL,
                              [IsDeleted] [bit] NOT NULL,
                              PRIMARY KEY CLUSTERED
                                  (
                                   [Id] ASC
                                      )WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Users]    Script Date: 6/13/2023 1:11:23 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Users](
                              [Id] [varchar](36) NOT NULL,
                              [Username] [varchar](100) NOT NULL,
                              [Password] [varchar](500) NOT NULL,
                              [Salt] [varbinary](16) NOT NULL,
                              [FirstName] [nvarchar](100) NOT NULL,
                              [LastName] [nvarchar](100) NOT NULL,
                              [IsDeleted] [bit] NOT NULL,
                              PRIMARY KEY CLUSTERED
                                  (
                                   [Id] ASC
                                      )WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
                              UNIQUE NONCLUSTERED
                                  (
                                   [Username] ASC
                                      )WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[UsersProjects]    Script Date: 6/13/2023 1:11:23 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[UsersProjects](
                                      [UserId] [varchar](36) NOT NULL,
                                      [ProjectId] [varchar](36) NOT NULL,
                                      [Role] [varchar](20) NOT NULL,
                                      [IsDeleted] [bit] NOT NULL,
                                      PRIMARY KEY CLUSTERED
                                          (
                                           [UserId] ASC,
                                           [ProjectId] ASC
                                              )WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[Projects] ADD  DEFAULT ((0)) FOR [IsDeleted]
GO
ALTER TABLE [dbo].[Tasks] ADD  DEFAULT ((0)) FOR [IsDeleted]
GO
ALTER TABLE [dbo].[Users] ADD  DEFAULT ((0)) FOR [IsDeleted]
GO
ALTER TABLE [dbo].[UsersProjects] ADD  DEFAULT ((0)) FOR [IsDeleted]
GO
ALTER TABLE [dbo].[Sessions]  WITH CHECK ADD FOREIGN KEY([UserId])
    REFERENCES [dbo].[Users] ([Id])
GO
ALTER TABLE [dbo].[Tasks]  WITH CHECK ADD FOREIGN KEY([ProjectId])
    REFERENCES [dbo].[Projects] ([Id])
GO
USE [master]
GO
ALTER DATABASE [TaskManagement] SET  READ_WRITE
GO
