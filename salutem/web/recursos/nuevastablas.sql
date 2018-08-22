--drop TABLE ordenes
--drop table formulas
--drop table consultas

CREATE TABLE public.atenciones
(
    id serial,
	fecha timestamp without time zone,
    cita integer,
	paciente integer,
	profesional integer,
    especialidad integer,
    motivo text,
    observaciones text,
    indicaciones text,
    activo boolean,
	creado timestamp without time zone,
    creadopor text,
    actualizado timestamp without time zone,
    actualizadopor text,
    CONSTRAINT atenciones_pkey PRIMARY KEY (id),
	CONSTRAINT atenciones_cita_key UNIQUE (cita),
	CONSTRAINT atenciones_cita_fkey FOREIGN KEY (cita)
        REFERENCES public.citas (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT atenciones_especialidad_fkey FOREIGN KEY (especialidad)
        REFERENCES public.parametros (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT atenciones_paciente_fkey FOREIGN KEY (paciente)
        REFERENCES public.pacientes (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE TABLE public.formulas
(
    id serial,
    atencion integer,
    lod text,
    loi text,
    avscod text,
    avscoi text,
    avccod text,
    avccoi text,
    esferaod text,
    esferaoi text,
    cilindrood text,
    cilindrooi text,
    ejeod text,
    ejeoi text,
    addod text,
    addoi text,
    dpod text,
    dpoi text,
    avod text,
    avoi text,
    material integer,
    altura text,
    tratamiento integer,
    descripcion text,
    creado timestamp without time zone,
    creadopor text,
    actualizado timestamp without time zone,
    actualizadopor text,
    activo boolean,
    CONSTRAINT formulas_pkey PRIMARY KEY (id),
    CONSTRAINT formulas_atencion_key UNIQUE (atencion),
    CONSTRAINT formulas_atencion_fkey FOREIGN KEY (atencion)
        REFERENCES public.atenciones (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT formulas_material_fkey FOREIGN KEY (material)
        REFERENCES public.materiales (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT formulas_tratamiento_fkey FOREIGN KEY (tratamiento)
        REFERENCES public.parametros (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE TABLE public.ordenes
(
    id serial,
    formula integer,
    factura text,
    usuario text,
    laboratorio integer,
    registro timestamp without time zone,
    envio timestamp without time zone,
    entrega timestamp without time zone,
    descripcion text,
    creado timestamp without time zone,
    creadopor text,
    actualizado timestamp without time zone,
    actualizadopor text,
    activo boolean,
    CONSTRAINT ordenes_pkey PRIMARY KEY (id),
    CONSTRAINT ordenes_formula_key UNIQUE (formula),
    CONSTRAINT ordenes_formula_fkey FOREIGN KEY (formula)
        REFERENCES public.formulas (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT ordenes_laboratorio_fkey FOREIGN KEY (laboratorio)
        REFERENCES public.instituciones (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE TABLE public.prescripciones
(
    id serial primary key,
	atencion integer,
	medicamento text,
	dosis text,
	frecuencia text,
	duracion text,
	advertencias text,
    CONSTRAINT prescripciones_atencion_fkey FOREIGN KEY (atencion)
        REFERENCES public.atenciones (id) MATCH SIMPLE
);
drop table campos;
CREATE TABLE public.campos
(
    id serial PRIMARY KEY,
	institucion integer,
    clasificador text,
	grupo integer,
	orden integer,
	nombre text,
    opciones jsonb,
	tipo integer,
    creado timestamp without time zone,
    creadopor text,
    actualizado timestamp without time zone,
    actualizadopor text,
    activo boolean, 
	CONSTRAINT campos_grupo_fkey FOREIGN KEY (grupo)
        REFERENCES public.parametros (id) MATCH SIMPLE,
	CONSTRAINT campos_tipo_fkey FOREIGN KEY (tipo)
        REFERENCES public.parametros (id) MATCH SIMPLE,
	CONSTRAINT campos_institucion_fkey FOREIGN KEY (institucion)
        REFERENCES public.instituciones (id) MATCH SIMPLE
);drop table datos;
CREATE TABLE public.datos
(
    id serial PRIMARY KEY,
    clasificador text,
    identificador integer,
	ordengrupo integer,
	grupo text,
	orden integer,
	nombre text, 
	opciones jsonb,
	tipo integer,
	texto text,
    booleano boolean,
	entero integer,
	decimal double precision,
	fecha date,
	hora time,
	fechahora  timestamp without time zone,
	seleccion jsonb,
	archivo integer,
    creado timestamp without time zone,
    creadopor text,
    actualizado timestamp without time zone,
    actualizadopor text,
    activo boolean,
	CONSTRAINT datos_tipo_fkey FOREIGN KEY (tipo)
        REFERENCES public.parametros (id) MATCH SIMPLE,
    CONSTRAINT datos_archivo_fkey FOREIGN KEY (archivo)
        REFERENCES public.archivos (id) MATCH SIMPLE
);

