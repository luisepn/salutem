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

CREATE TABLE public.campos
(
    id serial PRIMARY KEY,
    clasificador text,
    identificador integer,
	ordengrupo integer,
	grupo text,
	orden integer,
	nombre text,
    texto text,
	tipo text,
    booleano boolean,
	entero integer,
	flotante float,
	fecha  timestamp without time zone,
	registro jsonb,
	archivo integer,
	imprimible boolean,
    creado timestamp without time zone,
    creadopor text,
    actualizado timestamp without time zone,
    actualizadopor text,
    activo boolean
);

CREATE TABLE public.datos
(
    id serial PRIMARY KEY,
    clasificador text,
    identificador integer,
	ordengrupo integer,
	grupo text,
	orden integer,
	nombre text,
    texto text,
	tipo text,
    booleano boolean,
	entero integer,
	flotante float,
	fecha  timestamp without time zone,
	registro jsonb,
	archivo integer,
	imprimible boolean,
    creado timestamp without time zone,
    creadopor text,
    actualizado timestamp without time zone,
    actualizadopor text,
    activo boolean
);

