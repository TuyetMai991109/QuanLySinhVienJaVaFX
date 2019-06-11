/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2008                    */
/* Created on:     10/04/2019 8:55:30 CH                        */
/*==============================================================*/

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('DIEM') and o.name = 'FK_DIEM_DIEM_MONHOC')
alter table DIEM
   drop constraint FK_DIEM_DIEM_MONHOC
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('DIEM') and o.name = 'FK_DIEM_DIEM2_SINHVIEN')
alter table DIEM
   drop constraint FK_DIEM_DIEM2_SINHVIEN
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('LOP') and o.name = 'FK_LOP_CO_HEDT')
alter table LOP
   drop constraint FK_LOP_CO_HEDT
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('LOP') and o.name = 'FK_LOP_QL_KHOA')
alter table LOP
   drop constraint FK_LOP_QL_KHOA
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('LOP') and o.name = 'FK_LOP_THUOC_KHOAHOC')
alter table LOP
   drop constraint FK_LOP_THUOC_KHOAHOC
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('SINHVIEN') and o.name = 'FK_SINHVIEN_HOC_LOP')
alter table SINHVIEN
   drop constraint FK_SINHVIEN_HOC_LOP
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('DIEM')
            and   name  = 'DIEM_FK'
            and   indid > 0
            and   indid < 255)
   drop index DIEM.DIEM_FK
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('DIEM')
            and   name  = 'DIEM2_FK'
            and   indid > 0
            and   indid < 255)
   drop index DIEM.DIEM2_FK
go

if exists (select 1
            from  sysobjects
           where  id = object_id('DIEM')
            and   type = 'U')
   drop table DIEM
go

if exists (select 1
            from  sysobjects
           where  id = object_id('HEDT')
            and   type = 'U')
   drop table HEDT
go

if exists (select 1
            from  sysobjects
           where  id = object_id('KHOA')
            and   type = 'U')
   drop table KHOA
go

if exists (select 1
            from  sysobjects
           where  id = object_id('KHOAHOC')
            and   type = 'U')
   drop table KHOAHOC
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('LOP')
            and   name  = 'QL_FK'
            and   indid > 0
            and   indid < 255)
   drop index LOP.QL_FK
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('LOP')
            and   name  = 'CO_FK'
            and   indid > 0
            and   indid < 255)
   drop index LOP.CO_FK
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('LOP')
            and   name  = 'THUOC_FK'
            and   indid > 0
            and   indid < 255)
   drop index LOP.THUOC_FK
go

if exists (select 1
            from  sysobjects
           where  id = object_id('LOP')
            and   type = 'U')
   drop table LOP
go

if exists (select 1
            from  sysobjects
           where  id = object_id('MONHOC')
            and   type = 'U')
   drop table MONHOC
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('SINHVIEN')
            and   name  = 'HOC_FK'
            and   indid > 0
            and   indid < 255)
   drop index SINHVIEN.HOC_FK
go

if exists (select 1
            from  sysobjects
           where  id = object_id('SINHVIEN')
            and   type = 'U')
   drop table SINHVIEN
go

if exists (select 1
            from  sysobjects
           where  id = object_id('TAIKHOAN')
            and   type = 'U')
   drop table TAIKHOAN
go

/*==============================================================*/
/* Table: DIEM                                                  */
/*==============================================================*/
create table DIEM (
   MASV                 INT          not null,
   MAMH                 INT          not null,
   STT                  INT          not null		IDENTITY(1,1),
   HOCKY                float            null,
   DIEMLAN1             float             null,
   DIEMLAN2             float             null,
   constraint PK_DIEM primary key nonclustered (MASV, MAMH)
)
go

/*==============================================================*/
/* Index: DIEM2_FK                                              */
/*==============================================================*/
create index DIEM2_FK on DIEM (
MASV ASC
)
go

/*==============================================================*/
/* Index: DIEM_FK                                               */
/*==============================================================*/
create index DIEM_FK on DIEM (
MAMH ASC
)
go

/*==============================================================*/
/* Table: HEDT                                                  */
/*==============================================================*/
create table HEDT (
   MAHEDT               INT          not null 	IDENTITY(1,1),
   TENHEDT              nvarchar(50)          null,
   constraint PK_HEDT primary key nonclustered (MAHEDT)
)
go

/*==============================================================*/
/* Table: KHOA                                                  */
/*==============================================================*/
create table KHOA (
   MAKHOA               INT          not null		IDENTITY(1,1),
   TENKHOA              nvarchar(50)          null,
   DIACHI               nvarchar(50)          null,
   DIENTHOAI            char(10)             null,
   constraint PK_KHOA primary key nonclustered (MAKHOA)
)
go

/*==============================================================*/
/* Table: KHOAHOC                                               */
/*==============================================================*/
create table KHOAHOC (
   MAKHOAHOC            INT          not null IDENTITY(1,1),
   TENKHOAHOC           nvarchar(50)          null,
   constraint PK_KHOAHOC primary key nonclustered (MAKHOAHOC)
)
go

/*==============================================================*/
/* Table: LOP                                                   */
/*==============================================================*/
create table LOP (
   MALOP                INT          not null 		IDENTITY(1,1),
   MAKHOAHOC            INT          not null,
   MAHEDT               INT          not null,
   MAKHOA               INT          not null,
   TENLOP               nvarchar(50)          null,
   constraint PK_LOP primary key nonclustered (MALOP)
)
go

/*==============================================================*/
/* Index: THUOC_FK                                              */
/*==============================================================*/
create index THUOC_FK on LOP (
MAKHOAHOC ASC
)
go

/*==============================================================*/
/* Index: CO_FK                                                 */
/*==============================================================*/
create index CO_FK on LOP (
MAHEDT ASC
)
go

/*==============================================================*/
/* Index: QL_FK                                                 */
/*==============================================================*/
create index QL_FK on LOP (
MAKHOA ASC
)
go

/*==============================================================*/
/* Table: MONHOC                                                */
/*==============================================================*/
create table MONHOC (
   MAMH                 INT          not null			IDENTITY(1,1),
   TENMH                nvarchar(50)          null,
   SOTINCHI             int                  null,
   constraint PK_MONHOC primary key nonclustered (MAMH)
)
go

/*==============================================================*/
/* Table: SINHVIEN                                              */
/*==============================================================*/
create table SINHVIEN (
   MASV                 INT          not null		IDENTITY(1,1),
   MALOP                INT          not null,
   TENSV                nvarchar(50)          null,
   GIOITINH             bit                  null,
   NGAYSINH             datetime             null,
   QUEQUAN              nvarchar(50)          null,
   constraint PK_SINHVIEN primary key nonclustered (MASV)
)
go

/*==============================================================*/
/* Index: HOC_FK                                                */
/*==============================================================*/
create index HOC_FK on SINHVIEN (
MALOP ASC
)
go

/*==============================================================*/
/* Table: TAIKHOAN                                              */
/*==============================================================*/
create table TAIKHOAN (
   ID                   int                  not null		IDENTITY(1,1),
   TENDANGNHAP          varchar(50)             not null,
   MATKHAU              varchar(50)             null,
   QUYEN                int                  null,
   constraint PK_TAIKHOAN primary key nonclustered (ID)
)
go

insert SINHVIEN(TENSV,GIOITINH,NGAYSINH,QUEQUAN,MALOP)
values
(N'Nguyễn văn a',0,'20180801','Ho Bom',1),
(N'Nguyễn văn b',0,'20180801','Ho Bom 2',2),
(N'Nguyễn văn c',0,'20180801','Ho Bom 3',1)
go

insert LOP(TENLOP,MAKHOA,MAHEDT,MAKHOAHOC)
values
('17C1',1,1,1),
('17C2',1,1,1),
('17C3',1,1,1)
go

insert KHOA(TENKHOA,DIACHI,DIENTHOAI)
values
('CNTT','390','0123456789'),
('CNM','390','0123456799')
go

insert HEDT(TENHEDT)
values
('CD')
go

insert KHOAHOC(TENKHOAHOC)
values 
('2017-2019')
go

insert MONHOC(TENMH,SOTINCHI)
values
(N'Pháp luật',6)
go
insert TAIKHOAN(TENDANGNHAP,MATKHAU,QUYEN)
values('aaa','aaa',0)
go
alter table DIEM
   add constraint FK_DIEM_DIEM_MONHOC foreign key (MAMH)
      references MONHOC (MAMH)
go

alter table DIEM
   add constraint FK_DIEM_DIEM2_SINHVIEN foreign key (MASV)
      references SINHVIEN (MASV)
go

alter table LOP
   add constraint FK_LOP_CO_HEDT foreign key (MAHEDT)
      references HEDT (MAHEDT)
go

alter table LOP
   add constraint FK_LOP_QL_KHOA foreign key (MAKHOA)
      references KHOA (MAKHOA)
go

alter table LOP
   add constraint FK_LOP_THUOC_KHOAHOC foreign key (MAKHOAHOC)
      references KHOAHOC (MAKHOAHOC)
go

alter table SINHVIEN
   add constraint FK_SINHVIEN_HOC_LOP foreign key (MALOP)
      references LOP (MALOP)
go

select * from SINHVIEN