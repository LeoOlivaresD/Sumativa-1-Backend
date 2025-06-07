create database letrasypapeles;

create user 'myuser'@'%' identified by 'password';

grant all on letrasypapeles.* to 'myuser'@'%';