create database tp_jee_contact;
use tp_jee_contact;

create table contact (
	id int auto_increment primary key,
	nom varchar(50) not null,
	prenom varchar(50) not null,
	tel varchar(10),
	email varchar(50) not null
)

insert into contact (nom, presom,tel, email) values (
		"amine", "bouizmoune", "0620256513", "amine@gmail.com"
	)