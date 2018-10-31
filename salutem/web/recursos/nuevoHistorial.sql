
create table historial1(
	id integer,
    fecha timestamp without time zone,
    usuario text,
    ip text,
    operacion char,
    tabla text,
    registro integer,
    anterior jsonb,
    nuevo jsonb
)

insert into historial1
  select id,fecha, userid, ip,operacion,tabla, (objeto->>'id')::integer, objeto, objeto
  from historial order by id;
  
  --delete from historial1;
  select * from historial1;
  
  
  create table historial(
	id serial primary key,
    fecha timestamp without time zone,
    usuario text,
    ip text,
    operacion char,
    tabla text,
    registro integer,
    anterior jsonb,
    nuevo jsonb
)

insert into historial
 select *  from historial1 order by id;
 
 select *, nuevo->>'id' from historial order by id desc;
 
 update historial set registro=0 where registro is null;
 
Select o.id, o.fecha, o.usuario, o.ip, o.operacion, o.tabla, o.registro, jsonb_pretty(o.anterior), jsonb_pretty(o.nuevo), o.nuevo->>id from Historial o

