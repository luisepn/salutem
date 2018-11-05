--
-- PostgreSQL database dump
--

-- Dumped from database version 10.5
-- Dumped by pg_dump version 10.5

-- Started on 2018-11-05 06:03:52 -05

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

DROP DATABASE salutem;
--
-- TOC entry 3237 (class 1262 OID 68306)
-- Name: salutem; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE salutem WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'es_EC.utf8' LC_CTYPE = 'es_EC.utf8';


ALTER DATABASE salutem OWNER TO postgres;

\connect salutem

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 1 (class 3079 OID 13054)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 3240 (class 0 OID 0)
-- Dependencies: 1
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 196 (class 1259 OID 68307)
-- Name: archivos; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.archivos (
    id integer NOT NULL,
    clasificador text,
    identificador integer,
    nombre text,
    tipo text,
    ruta text,
    descripcion text,
    creado timestamp without time zone,
    creadopor text,
    actualizado timestamp without time zone,
    actualizadopor text,
    activo boolean
);


ALTER TABLE public.archivos OWNER TO postgres;

--
-- TOC entry 197 (class 1259 OID 68313)
-- Name: archivos_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.archivos_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.archivos_id_seq OWNER TO postgres;

--
-- TOC entry 3241 (class 0 OID 0)
-- Dependencies: 197
-- Name: archivos_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.archivos_id_seq OWNED BY public.archivos.id;


--
-- TOC entry 198 (class 1259 OID 68315)
-- Name: atenciones; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.atenciones (
    id integer NOT NULL,
    fecha timestamp without time zone,
    cita integer,
    paciente integer,
    profesional integer,
    especialidad integer,
    motivo text,
    diagnostico text,
    observaciones text,
    activo boolean,
    creado timestamp without time zone,
    creadopor text,
    actualizado timestamp without time zone,
    actualizadopor text
);


ALTER TABLE public.atenciones OWNER TO postgres;

--
-- TOC entry 199 (class 1259 OID 68321)
-- Name: atenciones_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.atenciones_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.atenciones_id_seq OWNER TO postgres;

--
-- TOC entry 3242 (class 0 OID 0)
-- Dependencies: 199
-- Name: atenciones_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.atenciones_id_seq OWNED BY public.atenciones.id;


--
-- TOC entry 200 (class 1259 OID 68323)
-- Name: campos; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.campos (
    id integer NOT NULL,
    institucion integer,
    clasificador text,
    grupo integer,
    codigo integer,
    nombre text,
    descripcion text,
    opciones jsonb,
    tipo integer,
    creado timestamp without time zone,
    creadopor text,
    actualizado timestamp without time zone,
    actualizadopor text,
    activo boolean,
    requerido boolean
);


ALTER TABLE public.campos OWNER TO postgres;

--
-- TOC entry 201 (class 1259 OID 68329)
-- Name: campos_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.campos_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.campos_id_seq OWNER TO postgres;

--
-- TOC entry 3243 (class 0 OID 0)
-- Dependencies: 201
-- Name: campos_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.campos_id_seq OWNED BY public.campos.id;


--
-- TOC entry 202 (class 1259 OID 68331)
-- Name: citas; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.citas (
    id integer NOT NULL,
    paciente integer,
    profesional integer,
    fecha timestamp without time zone,
    activo boolean,
    descripcion text,
    creado timestamp without time zone,
    creadopor text,
    actualizado timestamp without time zone,
    actualizadopor text
);


ALTER TABLE public.citas OWNER TO postgres;

--
-- TOC entry 203 (class 1259 OID 68337)
-- Name: citas_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.citas_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.citas_id_seq OWNER TO postgres;

--
-- TOC entry 3244 (class 0 OID 0)
-- Dependencies: 203
-- Name: citas_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.citas_id_seq OWNED BY public.citas.id;


--
-- TOC entry 204 (class 1259 OID 68339)
-- Name: datos; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.datos (
    id integer NOT NULL,
    clasificador text,
    identificador integer,
    ordengrupo integer,
    grupo text,
    codigo integer,
    nombre text,
    descripcion text,
    opciones jsonb,
    tipo integer,
    texto text,
    booleano boolean,
    entero integer,
    "decimal" double precision,
    fecha date,
    hora time without time zone,
    fechahora timestamp without time zone,
    seleccion jsonb,
    archivo integer,
    creado timestamp without time zone,
    creadopor text,
    actualizado timestamp without time zone,
    actualizadopor text,
    activo boolean,
    requerido boolean
);


ALTER TABLE public.datos OWNER TO postgres;

--
-- TOC entry 205 (class 1259 OID 68345)
-- Name: datos_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.datos_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.datos_id_seq OWNER TO postgres;

--
-- TOC entry 3245 (class 0 OID 0)
-- Dependencies: 205
-- Name: datos_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.datos_id_seq OWNED BY public.datos.id;


--
-- TOC entry 206 (class 1259 OID 68347)
-- Name: direcciones; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.direcciones (
    id integer NOT NULL,
    primaria text,
    numero text,
    secundaria text,
    piso text,
    referencia text,
    fijo text,
    movil text,
    ciudad text,
    descripcion text,
    creado timestamp without time zone,
    creadopor text,
    actualizado timestamp without time zone,
    actualizadopor text,
    activo boolean
);


ALTER TABLE public.direcciones OWNER TO postgres;

--
-- TOC entry 207 (class 1259 OID 68353)
-- Name: direcciones_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.direcciones_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.direcciones_id_seq OWNER TO postgres;

--
-- TOC entry 3246 (class 0 OID 0)
-- Dependencies: 207
-- Name: direcciones_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.direcciones_id_seq OWNED BY public.direcciones.id;


--
-- TOC entry 208 (class 1259 OID 68355)
-- Name: formulas; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.formulas (
    id integer NOT NULL,
    atencion integer,
    lensometria jsonb,
    agudezavisualsincristal jsonb,
    agudezavisualconcristal jsonb,
    esfera jsonb,
    cilindro jsonb,
    eje jsonb,
    adicion jsonb,
    distanciapupilar jsonb,
    agudezavisual jsonb,
    material integer,
    altura text,
    tratamiento integer,
    descripcion text,
    creado timestamp without time zone,
    creadopor text,
    actualizado timestamp without time zone,
    actualizadopor text,
    activo boolean
);


ALTER TABLE public.formulas OWNER TO postgres;

--
-- TOC entry 209 (class 1259 OID 68361)
-- Name: formulas_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.formulas_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.formulas_id_seq OWNER TO postgres;

--
-- TOC entry 3247 (class 0 OID 0)
-- Dependencies: 209
-- Name: formulas_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.formulas_id_seq OWNED BY public.formulas.id;


--
-- TOC entry 210 (class 1259 OID 68363)
-- Name: horarios; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.horarios (
    id integer NOT NULL,
    profesional integer,
    hora integer,
    dia integer,
    descripcion text,
    creado timestamp without time zone,
    creadopor text,
    actualizado timestamp without time zone,
    actualizadopor text,
    activo boolean
);


ALTER TABLE public.horarios OWNER TO postgres;

--
-- TOC entry 211 (class 1259 OID 68369)
-- Name: horarios_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.horarios_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.horarios_id_seq OWNER TO postgres;

--
-- TOC entry 3248 (class 0 OID 0)
-- Dependencies: 211
-- Name: horarios_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.horarios_id_seq OWNED BY public.horarios.id;


--
-- TOC entry 212 (class 1259 OID 68371)
-- Name: horas; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.horas (
    id integer NOT NULL,
    nombre text,
    horainicio time without time zone,
    horafin time without time zone,
    institucion integer,
    activo boolean,
    descripcion text,
    creado timestamp without time zone,
    creadopor text,
    actualizado timestamp without time zone,
    actualizadopor text,
    codigo text
);


ALTER TABLE public.horas OWNER TO postgres;

--
-- TOC entry 213 (class 1259 OID 68377)
-- Name: horas_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.horas_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.horas_id_seq OWNER TO postgres;

--
-- TOC entry 3249 (class 0 OID 0)
-- Dependencies: 213
-- Name: horas_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.horas_id_seq OWNED BY public.horas.id;


--
-- TOC entry 214 (class 1259 OID 68379)
-- Name: instituciones; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.instituciones (
    id integer NOT NULL,
    nombre text,
    fecha date,
    direccion integer,
    logotipo integer,
    email text,
    web text,
    laboratorio boolean,
    activo boolean,
    descripcion text,
    creado timestamp without time zone,
    creadopor text,
    actualizado timestamp without time zone,
    actualizadopor text
);


ALTER TABLE public.instituciones OWNER TO postgres;

--
-- TOC entry 215 (class 1259 OID 68385)
-- Name: instituciones_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.instituciones_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.instituciones_id_seq OWNER TO postgres;

--
-- TOC entry 3250 (class 0 OID 0)
-- Dependencies: 215
-- Name: instituciones_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.instituciones_id_seq OWNED BY public.instituciones.id;


--
-- TOC entry 216 (class 1259 OID 68387)
-- Name: maestros; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.maestros (
    id integer NOT NULL,
    codigo text,
    nombre text,
    activo boolean,
    descripcion text,
    creado timestamp without time zone,
    creadopor text,
    actualizado timestamp without time zone,
    actualizadopor text
);


ALTER TABLE public.maestros OWNER TO postgres;

--
-- TOC entry 217 (class 1259 OID 68393)
-- Name: maestros_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.maestros_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.maestros_id_seq OWNER TO postgres;

--
-- TOC entry 3251 (class 0 OID 0)
-- Dependencies: 217
-- Name: maestros_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.maestros_id_seq OWNED BY public.maestros.id;


--
-- TOC entry 218 (class 1259 OID 68395)
-- Name: materiales; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.materiales (
    id integer NOT NULL,
    nombre text,
    tipo integer,
    activo boolean,
    foco integer,
    descripcion text,
    creado timestamp without time zone,
    creadopor text,
    actualizado timestamp without time zone,
    actualizadopor text,
    codigo text
);


ALTER TABLE public.materiales OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 68401)
-- Name: materiales_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.materiales_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.materiales_id_seq OWNER TO postgres;

--
-- TOC entry 3252 (class 0 OID 0)
-- Dependencies: 219
-- Name: materiales_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.materiales_id_seq OWNED BY public.materiales.id;


--
-- TOC entry 220 (class 1259 OID 68403)
-- Name: menus; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.menus (
    id integer NOT NULL,
    nombre text,
    formulario text,
    menupadre integer,
    modulo integer,
    descripcion text,
    creado timestamp without time zone,
    creadopor text,
    actualizado timestamp without time zone,
    actualizadopor text,
    activo boolean,
    codigo text,
    icono text
);


ALTER TABLE public.menus OWNER TO postgres;

--
-- TOC entry 221 (class 1259 OID 68409)
-- Name: menus_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.menus_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.menus_id_seq OWNER TO postgres;

--
-- TOC entry 3253 (class 0 OID 0)
-- Dependencies: 221
-- Name: menus_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.menus_id_seq OWNED BY public.menus.id;


--
-- TOC entry 222 (class 1259 OID 68411)
-- Name: ordenes; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.ordenes (
    id integer NOT NULL,
    formula integer,
    factura text,
    laboratorio integer,
    registro timestamp without time zone,
    envio timestamp without time zone,
    entrega timestamp without time zone,
    descripcion text,
    creado timestamp without time zone,
    creadopor text,
    actualizado timestamp without time zone,
    actualizadopor text,
    activo boolean
);


ALTER TABLE public.ordenes OWNER TO postgres;

--
-- TOC entry 223 (class 1259 OID 68417)
-- Name: ordenes_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.ordenes_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.ordenes_id_seq OWNER TO postgres;

--
-- TOC entry 3254 (class 0 OID 0)
-- Dependencies: 223
-- Name: ordenes_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.ordenes_id_seq OWNED BY public.ordenes.id;


--
-- TOC entry 224 (class 1259 OID 68419)
-- Name: pacientes; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.pacientes (
    id integer NOT NULL,
    persona integer,
    institucion integer,
    activo boolean,
    descripcion text,
    creado timestamp without time zone,
    creadopor text,
    actualizado timestamp without time zone,
    actualizadopor text,
    fotografia integer
);


ALTER TABLE public.pacientes OWNER TO postgres;

--
-- TOC entry 225 (class 1259 OID 68425)
-- Name: pacientes_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.pacientes_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.pacientes_id_seq OWNER TO postgres;

--
-- TOC entry 3255 (class 0 OID 0)
-- Dependencies: 225
-- Name: pacientes_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.pacientes_id_seq OWNED BY public.pacientes.id;


--
-- TOC entry 226 (class 1259 OID 68427)
-- Name: parametros; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.parametros (
    id integer NOT NULL,
    maestro integer,
    nombre text,
    codigo text,
    descripcion text,
    parametros text,
    activo boolean,
    creado timestamp without time zone,
    creadopor text,
    actualizado timestamp without time zone,
    actualizadopor text
);


ALTER TABLE public.parametros OWNER TO postgres;

--
-- TOC entry 227 (class 1259 OID 68433)
-- Name: parametros_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.parametros_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.parametros_id_seq OWNER TO postgres;

--
-- TOC entry 3256 (class 0 OID 0)
-- Dependencies: 227
-- Name: parametros_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.parametros_id_seq OWNED BY public.parametros.id;


--
-- TOC entry 228 (class 1259 OID 68435)
-- Name: perfiles; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.perfiles (
    id integer NOT NULL,
    menu integer NOT NULL,
    consulta boolean,
    modificacion boolean,
    borrado boolean,
    nuevo boolean,
    grupo integer,
    activo boolean,
    descripcion text,
    creado timestamp without time zone,
    creadopor text,
    actualizado timestamp without time zone,
    actualizadopor text,
    auditoria boolean
);


ALTER TABLE public.perfiles OWNER TO postgres;

--
-- TOC entry 229 (class 1259 OID 68441)
-- Name: perfiles_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.perfiles_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.perfiles_id_seq OWNER TO postgres;

--
-- TOC entry 3257 (class 0 OID 0)
-- Dependencies: 229
-- Name: perfiles_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.perfiles_id_seq OWNED BY public.perfiles.id;


--
-- TOC entry 230 (class 1259 OID 68443)
-- Name: personas; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.personas (
    id integer NOT NULL,
    nombres text,
    apellidos text,
    email text,
    userid text,
    clave text,
    cedula text NOT NULL,
    fecha date,
    direccion integer,
    rol text,
    activo boolean DEFAULT false,
    ocupacion text,
    genero integer,
    descripcion text,
    creado timestamp without time zone,
    creadopor text,
    actualizado timestamp without time zone,
    actualizadopor text
);


ALTER TABLE public.personas OWNER TO postgres;

--
-- TOC entry 231 (class 1259 OID 68450)
-- Name: personas_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.personas_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.personas_id_seq OWNER TO postgres;

--
-- TOC entry 3258 (class 0 OID 0)
-- Dependencies: 231
-- Name: personas_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.personas_id_seq OWNED BY public.personas.id;


--
-- TOC entry 232 (class 1259 OID 68452)
-- Name: prescripciones; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.prescripciones (
    id integer NOT NULL,
    atencion integer,
    medicamento text,
    dosis text,
    frecuencia text,
    duracion text,
    advertencias text,
    activo boolean,
    creado timestamp without time zone,
    creadopor text,
    actualizado timestamp without time zone,
    actualizadopor text
);


ALTER TABLE public.prescripciones OWNER TO postgres;

--
-- TOC entry 233 (class 1259 OID 68458)
-- Name: prescripciones_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.prescripciones_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.prescripciones_id_seq OWNER TO postgres;

--
-- TOC entry 3259 (class 0 OID 0)
-- Dependencies: 233
-- Name: prescripciones_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.prescripciones_id_seq OWNED BY public.prescripciones.id;


--
-- TOC entry 234 (class 1259 OID 68460)
-- Name: profesionales; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.profesionales (
    id integer NOT NULL,
    persona integer,
    especialidad integer,
    institucion integer,
    activo boolean,
    descripcion text,
    creado timestamp without time zone,
    creadopor text,
    actualizado timestamp without time zone,
    actualizadopor text,
    fotografia integer
);


ALTER TABLE public.profesionales OWNER TO postgres;

--
-- TOC entry 235 (class 1259 OID 68466)
-- Name: profesionales_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.profesionales_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.profesionales_id_seq OWNER TO postgres;

--
-- TOC entry 3260 (class 0 OID 0)
-- Dependencies: 235
-- Name: profesionales_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.profesionales_id_seq OWNED BY public.profesionales.id;


--
-- TOC entry 236 (class 1259 OID 68468)
-- Name: usuarios; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.usuarios (
    id integer NOT NULL,
    modulo integer,
    grupo integer,
    institucion integer,
    persona integer,
    descripcion text,
    creado timestamp without time zone,
    creadopor text,
    actualizado timestamp without time zone,
    actualizadopor text,
    activo boolean
);


ALTER TABLE public.usuarios OWNER TO postgres;

--
-- TOC entry 237 (class 1259 OID 68474)
-- Name: usuarios_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.usuarios_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.usuarios_id_seq OWNER TO postgres;

--
-- TOC entry 3261 (class 0 OID 0)
-- Dependencies: 237
-- Name: usuarios_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.usuarios_id_seq OWNED BY public.usuarios.id;


--
-- TOC entry 2940 (class 2604 OID 68476)
-- Name: archivos id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.archivos ALTER COLUMN id SET DEFAULT nextval('public.archivos_id_seq'::regclass);


--
-- TOC entry 2941 (class 2604 OID 68477)
-- Name: atenciones id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.atenciones ALTER COLUMN id SET DEFAULT nextval('public.atenciones_id_seq'::regclass);


--
-- TOC entry 2942 (class 2604 OID 68478)
-- Name: campos id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.campos ALTER COLUMN id SET DEFAULT nextval('public.campos_id_seq'::regclass);


--
-- TOC entry 2943 (class 2604 OID 68479)
-- Name: citas id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.citas ALTER COLUMN id SET DEFAULT nextval('public.citas_id_seq'::regclass);


--
-- TOC entry 2944 (class 2604 OID 68480)
-- Name: datos id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.datos ALTER COLUMN id SET DEFAULT nextval('public.datos_id_seq'::regclass);


--
-- TOC entry 2945 (class 2604 OID 68481)
-- Name: direcciones id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.direcciones ALTER COLUMN id SET DEFAULT nextval('public.direcciones_id_seq'::regclass);


--
-- TOC entry 2946 (class 2604 OID 68482)
-- Name: formulas id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.formulas ALTER COLUMN id SET DEFAULT nextval('public.formulas_id_seq'::regclass);


--
-- TOC entry 2947 (class 2604 OID 68483)
-- Name: horarios id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.horarios ALTER COLUMN id SET DEFAULT nextval('public.horarios_id_seq'::regclass);


--
-- TOC entry 2948 (class 2604 OID 68484)
-- Name: horas id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.horas ALTER COLUMN id SET DEFAULT nextval('public.horas_id_seq'::regclass);


--
-- TOC entry 2949 (class 2604 OID 68485)
-- Name: instituciones id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.instituciones ALTER COLUMN id SET DEFAULT nextval('public.instituciones_id_seq'::regclass);


--
-- TOC entry 2950 (class 2604 OID 68486)
-- Name: maestros id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.maestros ALTER COLUMN id SET DEFAULT nextval('public.maestros_id_seq'::regclass);


--
-- TOC entry 2951 (class 2604 OID 68487)
-- Name: materiales id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.materiales ALTER COLUMN id SET DEFAULT nextval('public.materiales_id_seq'::regclass);


--
-- TOC entry 2952 (class 2604 OID 68488)
-- Name: menus id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.menus ALTER COLUMN id SET DEFAULT nextval('public.menus_id_seq'::regclass);


--
-- TOC entry 2953 (class 2604 OID 68489)
-- Name: ordenes id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ordenes ALTER COLUMN id SET DEFAULT nextval('public.ordenes_id_seq'::regclass);


--
-- TOC entry 2954 (class 2604 OID 68490)
-- Name: pacientes id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pacientes ALTER COLUMN id SET DEFAULT nextval('public.pacientes_id_seq'::regclass);


--
-- TOC entry 2955 (class 2604 OID 68491)
-- Name: parametros id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.parametros ALTER COLUMN id SET DEFAULT nextval('public.parametros_id_seq'::regclass);


--
-- TOC entry 2956 (class 2604 OID 68492)
-- Name: perfiles id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.perfiles ALTER COLUMN id SET DEFAULT nextval('public.perfiles_id_seq'::regclass);


--
-- TOC entry 2958 (class 2604 OID 68493)
-- Name: personas id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.personas ALTER COLUMN id SET DEFAULT nextval('public.personas_id_seq'::regclass);


--
-- TOC entry 2959 (class 2604 OID 68494)
-- Name: prescripciones id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.prescripciones ALTER COLUMN id SET DEFAULT nextval('public.prescripciones_id_seq'::regclass);


--
-- TOC entry 2960 (class 2604 OID 68495)
-- Name: profesionales id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.profesionales ALTER COLUMN id SET DEFAULT nextval('public.profesionales_id_seq'::regclass);


--
-- TOC entry 2961 (class 2604 OID 68496)
-- Name: usuarios id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuarios ALTER COLUMN id SET DEFAULT nextval('public.usuarios_id_seq'::regclass);


--
-- TOC entry 3190 (class 0 OID 68307)
-- Dependencies: 196
-- Data for Name: archivos; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.archivos (id, clasificador, identificador, nombre, tipo, ruta, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (2, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.archivos (id, clasificador, identificador, nombre, tipo, ruta, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (3, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.archivos (id, clasificador, identificador, nombre, tipo, ruta, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (4, NULL, NULL, 'Captura de pantalla de 2017-12-28 22-37-57.png', 'image/png', NULL, NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.archivos (id, clasificador, identificador, nombre, tipo, ruta, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (5, NULL, NULL, 'Captura de pantalla de 2017-12-18 22-41-39.png', 'image/png', NULL, NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.archivos (id, clasificador, identificador, nombre, tipo, ruta, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (8, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.archivos (id, clasificador, identificador, nombre, tipo, ruta, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (9, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.archivos (id, clasificador, identificador, nombre, tipo, ruta, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (1, 'Fotografias', NULL, 'Captura de pantalla de 2017-12-18 22-41-39.png', 'image/png', '/home/fernando/Imágenes/salutem/Fotografias/1', NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.archivos (id, clasificador, identificador, nombre, tipo, ruta, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (12, 'Fotografias', NULL, 'WallE.jpg', 'image/jpeg', '/home/fernando/Imágenes/salutem/Fotografias/12', NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.archivos (id, clasificador, identificador, nombre, tipo, ruta, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (13, 'Fotografias', NULL, 'png.png', 'image/png', '/home/fernando/Imágenes/salutem/Fotografias/13', NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.archivos (id, clasificador, identificador, nombre, tipo, ruta, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (14, 'Fotografias', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.archivos (id, clasificador, identificador, nombre, tipo, ruta, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (11, 'Fotografias', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.archivos (id, clasificador, identificador, nombre, tipo, ruta, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (17, 'Fotografias', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.archivos (id, clasificador, identificador, nombre, tipo, ruta, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (18, 'Fotografias', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.archivos (id, clasificador, identificador, nombre, tipo, ruta, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (19, 'Fotografias', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.archivos (id, clasificador, identificador, nombre, tipo, ruta, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (20, 'Fotografias', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.archivos (id, clasificador, identificador, nombre, tipo, ruta, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (10, 'Logotipos', NULL, 'Ubuntu-Wallpapers-5.jpg', 'image/jpeg', '/home/usuario/Imágenes/salutem/Logotipos/10', NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.archivos (id, clasificador, identificador, nombre, tipo, ruta, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (6, 'Logotipos', NULL, 'Ubuntu-Wallpapers-5.jpg', 'image/jpeg', '/home/usuario/Imágenes/salutem/Logotipos/6', NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.archivos (id, clasificador, identificador, nombre, tipo, ruta, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (7, 'Logotipos', NULL, 'nocrackle-2560-1600.png', 'image/png', '/home/usuario/Imágenes/salutem/Logotipos/7', NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.archivos (id, clasificador, identificador, nombre, tipo, ruta, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (34, 'Personas', 20, NULL, NULL, '/home/usuario/Imágenes/salutem/Personas/*', NULL, '2018-09-12 08:50:08.06', 'root', '2018-09-12 08:50:08.06', 'root', true);
INSERT INTO public.archivos (id, clasificador, identificador, nombre, tipo, ruta, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (50, 'Datos', 141, 'PANAMERICANA Daniela M.ods', 'application/vnd.oasis.opendocument.spreadsheet', '/home/usuario/Imágenes/salutem/Datos/50', NULL, '2018-09-14 12:03:25.598', 'apvillalba', '2018-09-14 12:03:25.598', 'apvillalba', true);
INSERT INTO public.archivos (id, clasificador, identificador, nombre, tipo, ruta, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (24, 'Fotografias', NULL, NULL, NULL, '/home/usuario/Imágenes/salutem/Pacientes/38', NULL, '2018-08-16 08:23:20.299', 'root', '2018-08-16 08:25:08.099', 'root', true);
INSERT INTO public.archivos (id, clasificador, identificador, nombre, tipo, ruta, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (26, 'Datos', NULL, 'HOJAS DE DIETA  PARDO.pdf', 'application/pdf', NULL, NULL, '2018-09-07 12:09:02.123', 'root', '2018-09-07 12:09:02.123', NULL, true);
INSERT INTO public.archivos (id, clasificador, identificador, nombre, tipo, ruta, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (27, 'Datos', NULL, 'pdfs.csv', 'text/csv', '/home/usuario/Imágenes/salutem/Datos/*', NULL, '2018-09-07 12:22:04.646', 'root', '2018-09-07 12:22:04.646', 'root', true);
INSERT INTO public.archivos (id, clasificador, identificador, nombre, tipo, ruta, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (28, 'Datos', NULL, 'HOJAS DE DIETA  PARDO.pdf', 'application/pdf', '/home/usuario/Imágenes/salutem/Datos/*', NULL, '2018-09-07 12:22:35.492', 'root', '2018-09-07 12:22:35.492', 'root', true);
INSERT INTO public.archivos (id, clasificador, identificador, nombre, tipo, ruta, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (40, 'Pacientes', 26, 'Ubuntu-Wallpapers-5.jpg', 'image/jpeg', '/home/usuario/Imágenes/salutem/Pacientes/*', NULL, '2018-09-12 09:54:50.009', 'root', '2018-09-12 09:54:50.009', 'root', true);
INSERT INTO public.archivos (id, clasificador, identificador, nombre, tipo, ruta, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (46, 'Datos', 108, 'Ubuntu-Wallpapers-5.jpg', 'image/jpeg', '/home/usuario/Imágenes/salutem/Datos/46', NULL, '2018-09-13 11:03:32.049', 'root', '2018-09-13 11:03:32.049', 'root', true);
INSERT INTO public.archivos (id, clasificador, identificador, nombre, tipo, ruta, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (29, 'Datos', 88, 'Citas.xls', 'application/vnd.ms-excel', '/home/usuario/Imágenes/salutem/Datos/29', NULL, '2018-09-07 16:14:31.857', 'root', '2018-09-07 16:14:31.857', 'root', true);
INSERT INTO public.archivos (id, clasificador, identificador, nombre, tipo, ruta, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (30, 'Personas', 16, NULL, NULL, NULL, NULL, '2018-09-12 08:17:00.147', 'root', '2018-09-12 08:17:00.147', 'root', true);
INSERT INTO public.archivos (id, clasificador, identificador, nombre, tipo, ruta, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (21, 'Fotografias', NULL, 'png.png', 'image/png', '/home/usuario/Imágenes/salutem/Fotografias/21', NULL, NULL, NULL, '2018-08-16 08:24:57.702', 'root', true);
INSERT INTO public.archivos (id, clasificador, identificador, nombre, tipo, ruta, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (32, 'Personas', 18, NULL, NULL, '/home/usuario/Imágenes/salutem/Personas/*', NULL, '2018-09-12 08:31:02.32', 'root', '2018-09-12 08:31:02.32', 'root', true);
INSERT INTO public.archivos (id, clasificador, identificador, nombre, tipo, ruta, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (49, 'Datos', 138, 'EXCEL GILBERT .xlsx', 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet', '/home/usuario/Imágenes/salutem/Datos/49', NULL, '2018-09-14 10:27:38.41', 'root', '2018-09-14 10:27:38.41', 'root', true);
INSERT INTO public.archivos (id, clasificador, identificador, nombre, tipo, ruta, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (47, 'Datos', 118, 'KENNEDY Daniela.ods', 'application/vnd.oasis.opendocument.spreadsheet', '/home/usuario/Imágenes/salutem/Datos/47', NULL, '2018-09-13 11:11:42.577', 'root', '2018-09-13 11:11:42.577', 'root', true);
INSERT INTO public.archivos (id, clasificador, identificador, nombre, tipo, ruta, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (48, 'Datos', 128, 'RESOLUCION 319.pdf', 'application/pdf', '/home/usuario/Imágenes/salutem/Datos/48', NULL, '2018-09-13 12:12:09.358', 'root', '2018-09-13 12:12:09.358', 'root', true);
INSERT INTO public.archivos (id, clasificador, identificador, nombre, tipo, ruta, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (45, 'Profesionales', 31, 'Logo-PostgreSQL-468x405.png', 'image/png', '/home/usuario/Imágenes/salutem/Profesionales/45', NULL, '2018-09-12 10:26:40.976', 'root', '2018-09-12 10:26:40.976', 'apvillalba', true);
INSERT INTO public.archivos (id, clasificador, identificador, nombre, tipo, ruta, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (51, 'Datos', 145, '04ORI_AGOSTO2018.pdf', 'application/pdf', '/home/usuario/Imágenes/salutem/Datos/51.04ORI_AGOSTO2018.pdf', NULL, '2018-09-19 05:48:59.03', 'apvillalba', '2018-09-19 05:48:59.03', 'apvillalba', true);
INSERT INTO public.archivos (id, clasificador, identificador, nombre, tipo, ruta, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (16, 'Fotografias', 1, 'Pg_logo.png', 'image/png', '/home/usuario/Imágenes/salutem/Fotografias/16', NULL, NULL, NULL, '2018-08-16 09:13:43.633', 'apvillalba', true);
INSERT INTO public.archivos (id, clasificador, identificador, nombre, tipo, ruta, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (25, 'Fotografias', 5, 'Logo-PostgreSQL-468x405.png', 'image/png', '/home/usuario/Imágenes/salutem/Profesionales/25', NULL, '2018-08-16 08:31:34.002', 'root', '2018-08-16 08:31:34.002', 'apvillalba', true);
INSERT INTO public.archivos (id, clasificador, identificador, nombre, tipo, ruta, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (52, 'Datos', 160, 'comprobantepago_agosto2018.pdf', 'application/pdf', '/home/usuario/Imágenes/salutem/Datos/52', NULL, '2018-11-02 01:03:09.187', 'root', '2018-11-02 01:03:09.187', 'root', true);
INSERT INTO public.archivos (id, clasificador, identificador, nombre, tipo, ruta, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (31, 'Personas', 17, 'Captura de pantalla de 2018-06-04 22-27-55.png', 'image/png', '/home/usuario/Imágenes/salutem/Pacientes/31', NULL, '2018-09-12 08:29:12.999', 'root', '2018-09-12 08:29:12.999', 'root', true);
INSERT INTO public.archivos (id, clasificador, identificador, nombre, tipo, ruta, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (33, 'Personas', 19, 'sipt.png', 'image/png', '/home/usuario/Imágenes/salutem/Pacientes/33', NULL, '2018-09-12 08:38:29.173', 'root', '2018-09-12 08:38:29.173', 'root', true);
INSERT INTO public.archivos (id, clasificador, identificador, nombre, tipo, ruta, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (53, 'Datos', 170, 'comprobantepago_agosto2018.xls', 'application/vnd.ms-excel', '/home/usuario/Imágenes/salutem/Datos/53', NULL, '2018-11-02 01:14:31.94', 'root', '2018-11-02 01:14:31.94', 'root', true);
INSERT INTO public.archivos (id, clasificador, identificador, nombre, tipo, ruta, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (15, 'Fotografias', 3, '14_20180811_225034.jpg', 'image/jpeg', '/home/usuario/Imágenes/salutem/Profesionales/15', NULL, NULL, NULL, NULL, 'root', true);
INSERT INTO public.archivos (id, clasificador, identificador, nombre, tipo, ruta, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (37, 'Pacientes', 23, '14_20180811_225034.jpg', 'image/jpeg', '/home/usuario/Imágenes/salutem/Pacientes/37', NULL, '2018-09-12 09:35:39.314', 'root', '2018-09-12 09:35:39.314', 'root', true);
INSERT INTO public.archivos (id, clasificador, identificador, nombre, tipo, ruta, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (36, 'Pacientes', 22, 'WallE.jpg', 'image/jpeg', '/home/usuario/Imágenes/salutem/Pacientes/36', NULL, '2018-09-12 09:29:49.546', 'root', '2018-09-12 09:29:49.546', 'root', true);
INSERT INTO public.archivos (id, clasificador, identificador, nombre, tipo, ruta, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (38, 'Pacientes', 24, 'Captura de pantalla de 2018-06-04 22-47-01.png', 'image/png', '/home/usuario/Imágenes/salutem/Pacientes/38', NULL, '2018-09-12 09:42:38.635', 'root', '2018-09-12 09:42:38.635', 'root', true);
INSERT INTO public.archivos (id, clasificador, identificador, nombre, tipo, ruta, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (39, 'Pacientes', 25, 'Pg_logo.png', 'image/png', '/home/usuario/Imágenes/salutem/Pacientes/39', NULL, '2018-09-12 09:48:48.675', 'root', '2018-09-12 09:48:48.675', 'root', true);
INSERT INTO public.archivos (id, clasificador, identificador, nombre, tipo, ruta, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (35, 'Pacientes', 21, 'IMG-20180129-WA0001.jpg', 'image/jpeg', '/home/usuario/Imágenes/salutem/Pacientes/35', NULL, '2018-09-12 08:51:30.381', 'root', '2018-09-12 08:51:30.381', 'root', true);
INSERT INTO public.archivos (id, clasificador, identificador, nombre, tipo, ruta, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (54, 'Pacientes', 26, 'Selección_002.png', 'image/png', '/home/usuario/Imágenes/salutem/Pacientes/54', NULL, '2018-11-03 00:21:06.902', 'root', '2018-11-03 00:21:06.902', 'root', true);
INSERT INTO public.archivos (id, clasificador, identificador, nombre, tipo, ruta, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (41, 'Pacientes', 27, 'Logo-PostgreSQL-468x405.png', 'image/png', '/home/usuario/Imágenes/salutem/Pacientes/41', NULL, '2018-09-12 10:03:24.758', 'root', '2018-09-12 10:03:24.758', 'root', true);
INSERT INTO public.archivos (id, clasificador, identificador, nombre, tipo, ruta, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (42, 'Pacientes', 28, 'WallE.jpg', 'image/jpeg', '/home/usuario/Imágenes/salutem/Pacientes/42', NULL, '2018-09-12 10:06:56.559', 'root', '2018-09-12 10:06:56.559', 'root', true);
INSERT INTO public.archivos (id, clasificador, identificador, nombre, tipo, ruta, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (43, 'Pacientes', 29, 'Selección_002.png', 'image/png', '/home/usuario/Imágenes/salutem/Pacientes/43', NULL, '2018-09-12 10:17:54.021', 'root', '2018-09-12 10:17:54.021', 'root', true);
INSERT INTO public.archivos (id, clasificador, identificador, nombre, tipo, ruta, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (44, 'Pacientes', 30, 'EPSON018.jpg', 'image/jpeg', '/home/usuario/Imágenes/salutem/Pacientes/44', NULL, '2018-09-12 10:19:28.225', 'root', '2018-09-12 10:19:28.225', 'root', true);
INSERT INTO public.archivos (id, clasificador, identificador, nombre, tipo, ruta, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (55, 'Datos', 180, '14_20180811_225034.jpg', 'image/jpeg', '/home/usuario/Imágenes/salutem/Datos/55', NULL, '2018-11-03 00:30:29.813', 'root', '2018-11-03 00:30:29.813', 'root', true);
INSERT INTO public.archivos (id, clasificador, identificador, nombre, tipo, ruta, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (22, 'Logotipos', 4, '4k-wallpapers-Is-4K-Wallpaper.jpg', 'image/jpeg', '/home/usuario/Imágenes/salutem/Instituciones/22', NULL, NULL, NULL, NULL, 'root', true);
INSERT INTO public.archivos (id, clasificador, identificador, nombre, tipo, ruta, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (56, 'Profesionales', 3, '4k-wallpapers-Is-4K-Wallpaper.jpg', 'image/jpeg', '/home/usuario/Imágenes/salutem/Profesionales/56', NULL, '2018-11-03 20:54:48.453', 'root', '2018-11-03 20:54:48.453', 'root', true);
INSERT INTO public.archivos (id, clasificador, identificador, nombre, tipo, ruta, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (57, 'Profesionales', 1, 'Captura de pantalla de 2018-06-04 22-47-01.png', 'image/png', '/home/usuario/Imágenes/salutem/Profesionales/57', NULL, '2018-11-03 21:05:16.032', 'root', '2018-11-03 21:05:16.032', 'root', true);
INSERT INTO public.archivos (id, clasificador, identificador, nombre, tipo, ruta, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (58, 'Profesionales', 31, '14_20180811_225034.jpg', 'image/jpeg', '/home/usuario/Imágenes/salutem/Profesionales/58', NULL, '2018-11-03 21:07:06.885', 'root', '2018-11-03 21:07:06.885', 'root', true);
INSERT INTO public.archivos (id, clasificador, identificador, nombre, tipo, ruta, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (59, 'Pacientes', 3, '14_20180811_225034.jpg', 'image/jpeg', '/home/usuario/Imágenes/salutem/Pacientes/59', NULL, '2018-11-03 21:07:18.64', 'root', '2018-11-03 21:07:18.64', 'root', true);
INSERT INTO public.archivos (id, clasificador, identificador, nombre, tipo, ruta, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (60, 'Profesionales', 14, 'IMG-20180129-WA0002.jpg', 'image/jpeg', '/home/usuario/Imágenes/salutem/Profesionales/60', NULL, '2018-11-03 21:20:32.035', 'root', '2018-11-03 21:20:32.035', 'root', true);
INSERT INTO public.archivos (id, clasificador, identificador, nombre, tipo, ruta, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (61, 'Profesionales', 5, 'Captura realizada el 2018-08-05 23.18.46.png', 'image/png', '/home/usuario/Imágenes/salutem/Profesionales/61', NULL, '2018-11-03 21:21:02.567', 'root', '2018-11-03 21:21:02.567', 'root', true);
INSERT INTO public.archivos (id, clasificador, identificador, nombre, tipo, ruta, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (62, 'Pacientes', 32, '4k-wallpapers-Is-4K-Wallpaper.jpg', 'image/jpeg', '/home/usuario/Imágenes/salutem/Pacientes/62', NULL, '2018-11-03 23:15:28.254', 'root', '2018-11-03 23:15:28.254', 'root', true);


--
-- TOC entry 3192 (class 0 OID 68315)
-- Dependencies: 198
-- Data for Name: atenciones; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.atenciones (id, fecha, cita, paciente, profesional, especialidad, motivo, diagnostico, observaciones, activo, creado, creadopor, actualizado, actualizadopor) VALUES (27, '2018-09-14 09:41:09.095', NULL, 10, 1, 31, 'Ninguno.', 'Pulmonía.', 'Cuidados del clima.', true, '2018-09-14 09:41:09.1', 'root', '2018-09-14 12:34:26.449', 'root');
INSERT INTO public.atenciones (id, fecha, cita, paciente, profesional, especialidad, motivo, diagnostico, observaciones, activo, creado, creadopor, actualizado, actualizadopor) VALUES (1, '2018-08-30 11:07:56.702', NULL, 10, 5, NULL, NULL, NULL, NULL, true, '2018-08-30 11:07:56.702', 'apvillalba', '2018-08-30 11:07:56.702', 'apvillalba');
INSERT INTO public.atenciones (id, fecha, cita, paciente, profesional, especialidad, motivo, diagnostico, observaciones, activo, creado, creadopor, actualizado, actualizadopor) VALUES (10, '2018-08-30 16:54:25.34', 32, 10, 1, 31, NULL, NULL, NULL, true, '2018-08-30 16:54:25.362', 'root', '2018-08-30 16:54:25.362', 'root');
INSERT INTO public.atenciones (id, fecha, cita, paciente, profesional, especialidad, motivo, diagnostico, observaciones, activo, creado, creadopor, actualizado, actualizadopor) VALUES (11, '2018-09-03 14:43:52.486', NULL, 10, 1, 31, NULL, NULL, NULL, true, '2018-09-03 14:43:52.492', 'root', '2018-09-03 14:43:52.492', 'root');
INSERT INTO public.atenciones (id, fecha, cita, paciente, profesional, especialidad, motivo, diagnostico, observaciones, activo, creado, creadopor, actualizado, actualizadopor) VALUES (13, '2018-09-04 08:33:56.215', NULL, 10, 1, 31, 'Por una simple prueba.', 'Todo esto es una farsa.', 'Por favor hacer caso a las prescripciones médicas.', true, '2018-09-04 08:33:56.221', 'root', '2018-09-04 17:43:15.782', 'root');
INSERT INTO public.atenciones (id, fecha, cita, paciente, profesional, especialidad, motivo, diagnostico, observaciones, activo, creado, creadopor, actualizado, actualizadopor) VALUES (14, '2018-09-05 09:48:46.198', NULL, 10, 1, 31, 'Chequeo de rutina.', 'Pulmonía.', 'Seguir estrictamente las recomendaciones.', true, '2018-09-05 09:48:46.204', 'root', '2018-09-05 16:20:02.766', 'root');
INSERT INTO public.atenciones (id, fecha, cita, paciente, profesional, especialidad, motivo, diagnostico, observaciones, activo, creado, creadopor, actualizado, actualizadopor) VALUES (15, '2018-09-06 12:13:55.248', NULL, 10, 1, 31, NULL, NULL, NULL, true, '2018-09-06 12:13:55.253', 'root', '2018-09-06 12:13:55.253', 'root');
INSERT INTO public.atenciones (id, fecha, cita, paciente, profesional, especialidad, motivo, diagnostico, observaciones, activo, creado, creadopor, actualizado, actualizadopor) VALUES (16, '2018-09-07 12:08:48.229', NULL, 12, 1, 31, NULL, NULL, NULL, true, '2018-09-07 12:08:48.234', 'root', '2018-09-07 12:08:48.234', 'root');
INSERT INTO public.atenciones (id, fecha, cita, paciente, profesional, especialidad, motivo, diagnostico, observaciones, activo, creado, creadopor, actualizado, actualizadopor) VALUES (18, '2018-09-10 12:21:21.13', NULL, 10, 5, 33, '', '', '', true, '2018-09-10 12:21:21.134', 'apvillalba', '2018-09-10 16:58:40.339', 'apvillalba');
INSERT INTO public.atenciones (id, fecha, cita, paciente, profesional, especialidad, motivo, diagnostico, observaciones, activo, creado, creadopor, actualizado, actualizadopor) VALUES (20, '2018-09-11 17:57:09.426', NULL, 10, 5, 33, '', '', '', true, '2018-09-11 17:57:09.431', 'apvillalba', '2018-09-11 18:16:34.982', 'apvillalba');
INSERT INTO public.atenciones (id, fecha, cita, paciente, profesional, especialidad, motivo, diagnostico, observaciones, activo, creado, creadopor, actualizado, actualizadopor) VALUES (23, '2018-09-13 12:11:46.109', NULL, 12, 1, 31, NULL, NULL, NULL, true, '2018-09-13 12:11:46.113', 'root', '2018-09-13 12:11:46.113', 'root');
INSERT INTO public.atenciones (id, fecha, cita, paciente, profesional, especialidad, motivo, diagnostico, observaciones, activo, creado, creadopor, actualizado, actualizadopor) VALUES (24, '2018-09-13 16:42:25.631', NULL, 10, 5, 33, NULL, NULL, NULL, true, '2018-09-13 16:42:25.637', 'apvillalba', '2018-09-13 16:42:25.637', 'apvillalba');
INSERT INTO public.atenciones (id, fecha, cita, paciente, profesional, especialidad, motivo, diagnostico, observaciones, activo, creado, creadopor, actualizado, actualizadopor) VALUES (28, '2018-09-14 09:44:17.174', NULL, 10, 5, 33, 'Obtener una prueba exitosa.', 'No tiene nada, solo pereza.', 'Siga con pereza, por favor.', true, '2018-09-14 09:44:17.179', 'apvillalba', '2018-09-14 15:21:59.125', 'apvillalba');
INSERT INTO public.atenciones (id, fecha, cita, paciente, profesional, especialidad, motivo, diagnostico, observaciones, activo, creado, creadopor, actualizado, actualizadopor) VALUES (29, '2018-09-19 05:48:31.235', NULL, 10, 5, 33, NULL, NULL, NULL, true, '2018-09-19 05:48:31.24', 'apvillalba', '2018-09-19 05:48:31.24', 'apvillalba');
INSERT INTO public.atenciones (id, fecha, cita, paciente, profesional, especialidad, motivo, diagnostico, observaciones, activo, creado, creadopor, actualizado, actualizadopor) VALUES (30, '2018-11-02 00:53:30.213', NULL, 10, 5, 33, '', '', '', true, '2018-11-02 00:53:30.215', 'apvillalba', '2018-11-02 00:53:43.003', 'apvillalba');
INSERT INTO public.atenciones (id, fecha, cita, paciente, profesional, especialidad, motivo, diagnostico, observaciones, activo, creado, creadopor, actualizado, actualizadopor) VALUES (32, '2018-11-02 01:14:12.668', NULL, 10, 1, 31, 'Motivo', 'Diagnostico', 'Observaciones', true, '2018-11-02 01:14:12.67', 'root', '2018-11-02 01:18:15.27', 'root');
INSERT INTO public.atenciones (id, fecha, cita, paciente, profesional, especialidad, motivo, diagnostico, observaciones, activo, creado, creadopor, actualizado, actualizadopor) VALUES (33, '2018-11-03 00:27:51.647', NULL, 10, 1, 31, NULL, NULL, NULL, true, '2018-11-03 00:27:51.649', 'root', '2018-11-03 00:27:51.649', 'root');


--
-- TOC entry 3194 (class 0 OID 68323)
-- Dependencies: 200
-- Data for Name: campos; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.campos (id, institucion, clasificador, grupo, codigo, nombre, descripcion, opciones, tipo, creado, creadopor, actualizado, actualizadopor, activo, requerido) VALUES (6, 1, 'Atenciones', 31, 2, 'Altura (m)', '', NULL, 49, '2018-08-30 16:37:53.725', 'root', '2018-08-30 16:37:53.725', 'root', true, true);
INSERT INTO public.campos (id, institucion, clasificador, grupo, codigo, nombre, descripcion, opciones, tipo, creado, creadopor, actualizado, actualizadopor, activo, requerido) VALUES (5, 1, 'Atenciones', 31, 1, 'Peso (kg)', '', NULL, 49, '2018-08-30 16:32:54.599', 'root', '2018-08-30 16:38:03.147', 'root', true, true);
INSERT INTO public.campos (id, institucion, clasificador, grupo, codigo, nombre, descripcion, opciones, tipo, creado, creadopor, actualizado, actualizadopor, activo, requerido) VALUES (7, 1, 'Atenciones', 31, 3, 'Presión arterial', '', NULL, 49, '2018-08-30 16:39:50.34', 'root', '2018-08-30 16:39:50.34', 'root', true, true);
INSERT INTO public.campos (id, institucion, clasificador, grupo, codigo, nombre, descripcion, opciones, tipo, creado, creadopor, actualizado, actualizadopor, activo, requerido) VALUES (8, 1, 'Atenciones', 31, 4, 'Temperatura (ºC)', '', NULL, 49, '2018-08-30 16:40:20.047', 'root', '2018-08-30 16:54:42.537', 'root', true, true);
INSERT INTO public.campos (id, institucion, clasificador, grupo, codigo, nombre, descripcion, opciones, tipo, creado, creadopor, actualizado, actualizadopor, activo, requerido) VALUES (3, 1, 'Atenciones', 31, 5, 'Seleccione una opción', '', '{"0": "Opción 01", "1": "Opción 02", "2": "Opción 03"}', 53, '2018-08-24 11:35:45.008', 'apvillalba', '2018-09-04 08:37:11.506', 'root', true, true);
INSERT INTO public.campos (id, institucion, clasificador, grupo, codigo, nombre, descripcion, opciones, tipo, creado, creadopor, actualizado, actualizadopor, activo, requerido) VALUES (1, 1, 'Archivos', 56, 1, 'C001', '', '{"0": "Valor001", "1": "Valor002", "2": "Valor003", "3": "Valor004"}', 52, '2018-08-23 11:36:24.873', 'apvillalba', '2018-09-04 08:38:54.254', 'root', true, true);
INSERT INTO public.campos (id, institucion, clasificador, grupo, codigo, nombre, descripcion, opciones, tipo, creado, creadopor, actualizado, actualizadopor, activo, requerido) VALUES (2, 1, 'Archivos', 56, 2, '¿Es paciente regular?', '', NULL, 47, '2018-08-24 11:34:50.382', 'apvillalba', '2018-09-04 08:39:00.233', 'root', true, true);
INSERT INTO public.campos (id, institucion, clasificador, grupo, codigo, nombre, descripcion, opciones, tipo, creado, creadopor, actualizado, actualizadopor, activo, requerido) VALUES (9, 1, 'Atenciones', 31, 6, 'Fecha Atención', '', NULL, 50, '2018-09-04 11:15:20.66', 'root', '2018-09-04 11:15:20.66', 'root', true, true);
INSERT INTO public.campos (id, institucion, clasificador, grupo, codigo, nombre, descripcion, opciones, tipo, creado, creadopor, actualizado, actualizadopor, activo, requerido) VALUES (4, 1, 'Archivos', 56, 4, 'Seleccione Varios', '', '{"0": "Opción 01", "1": "Opción 02", "2": "Opción 03"}', 54, '2018-08-24 11:40:12.289', 'apvillalba', '2018-09-04 08:39:44.432', 'root', true, true);
INSERT INTO public.campos (id, institucion, clasificador, grupo, codigo, nombre, descripcion, opciones, tipo, creado, creadopor, actualizado, actualizadopor, activo, requerido) VALUES (10, 1, 'Atenciones', 31, 7, 'Alergias', '', NULL, 47, '2018-09-04 11:16:08.528', 'root', '2018-09-04 11:16:08.528', 'root', true, true);
INSERT INTO public.campos (id, institucion, clasificador, grupo, codigo, nombre, descripcion, opciones, tipo, creado, creadopor, actualizado, actualizadopor, activo, requerido) VALUES (11, 1, 'Atenciones', 31, 8, 'Selección múltiple', '', '{"0": "Opción 01", "1": "Opción 02", "2": "Opción 03"}', 54, '2018-09-04 11:17:05.302', 'root', '2018-09-04 11:17:05.302', 'root', true, true);
INSERT INTO public.campos (id, institucion, clasificador, grupo, codigo, nombre, descripcion, opciones, tipo, creado, creadopor, actualizado, actualizadopor, activo, requerido) VALUES (13, 1, 'Atenciones', 31, 10, 'Archivo', 'Archivo', NULL, 55, '2018-09-07 12:08:30.886', 'root', '2018-09-07 12:08:30.886', 'root', true, true);
INSERT INTO public.campos (id, institucion, clasificador, grupo, codigo, nombre, descripcion, opciones, tipo, creado, creadopor, actualizado, actualizadopor, activo, requerido) VALUES (12, 1, 'Atenciones', 31, 9, 'Seleccione Una', '', '{"0": "Opción Uno", "1": "Opción Dos", "2": "Opción Tres", "3": "Opción Cuatro"}', 57, '2018-09-05 09:06:51.433', 'root', '2018-09-13 11:02:13.336', 'root', true, true);
INSERT INTO public.campos (id, institucion, clasificador, grupo, codigo, nombre, descripcion, opciones, tipo, creado, creadopor, actualizado, actualizadopor, activo, requerido) VALUES (14, 1, 'Atenciones', 33, 1, 'Verdadero o Falso', 'Verdadero o Falso', NULL, 47, '2018-09-14 12:00:19.936', 'apvillalba', '2018-09-14 12:00:19.936', 'apvillalba', true, false);
INSERT INTO public.campos (id, institucion, clasificador, grupo, codigo, nombre, descripcion, opciones, tipo, creado, creadopor, actualizado, actualizadopor, activo, requerido) VALUES (15, 1, 'Atenciones', 33, 2, 'Fecha y hora', 'Fecha y hora', NULL, 52, '2018-09-14 12:01:20.381', 'apvillalba', '2018-09-14 12:01:20.381', 'apvillalba', true, false);
INSERT INTO public.campos (id, institucion, clasificador, grupo, codigo, nombre, descripcion, opciones, tipo, creado, creadopor, actualizado, actualizadopor, activo, requerido) VALUES (16, 1, 'Atenciones', 33, 3, 'Suba un archivo', 'Suba un archivo', NULL, 55, '2018-09-14 12:01:58.435', 'apvillalba', '2018-09-14 12:01:58.435', 'apvillalba', true, false);
INSERT INTO public.campos (id, institucion, clasificador, grupo, codigo, nombre, descripcion, opciones, tipo, creado, creadopor, actualizado, actualizadopor, activo, requerido) VALUES (17, 1, 'Atenciones', 33, 4, 'Selección', 'Selección', '{"0": "Opción 1", "1": "Opción 2"}', 54, '2018-09-14 12:02:52.764', 'apvillalba', '2018-09-14 12:02:52.764', 'apvillalba', true, false);


--
-- TOC entry 3196 (class 0 OID 68331)
-- Dependencies: 202
-- Data for Name: citas; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.citas (id, paciente, profesional, fecha, activo, descripcion, creado, creadopor, actualizado, actualizadopor) VALUES (2, 10, 3, '2018-08-02 08:40:00', true, '[Cita agendada por: imordonez - 02/08/2018 12:41]', '2018-08-02 12:41:22.206', 'imordonez', '2018-08-02 12:41:22.206', 'imordonez');
INSERT INTO public.citas (id, paciente, profesional, fecha, activo, descripcion, creado, creadopor, actualizado, actualizadopor) VALUES (3, 10, 3, '2018-08-02 09:20:00', true, '[Cita agendada por: imordonez - 02/08/2018 13:58]', '2018-08-02 13:58:12.75', 'imordonez', '2018-08-02 13:58:12.75', 'imordonez');
INSERT INTO public.citas (id, paciente, profesional, fecha, activo, descripcion, creado, creadopor, actualizado, actualizadopor) VALUES (1, 10, 3, '2018-08-02 16:56:50.937', false, '[Cita agendada por: imordonez - 02/08/2018 10:25] [Cita cancelada por: imordonez - 02/08/2018 16:27] [Cita reactivada por: imordonez - 02/08/2018 16:27] [Cita cancelada por: imordonez - 02/08/2018 16:56]', '2018-08-02 10:25:49.885', 'imordonez', '2018-08-02 16:56:50.937', 'imordonez');
INSERT INTO public.citas (id, paciente, profesional, fecha, activo, descripcion, creado, creadopor, actualizado, actualizadopor) VALUES (4, 10, 1, '2018-08-03 08:00:00', true, '[Cita agendada por: imordonez - 03/08/2018 23:05] [Cita cancelada por: imordonez - 03/08/2018 23:05] [Cita reactivada por: imordonez - 03/08/2018 23:05]', '2018-08-03 23:05:37.56', 'imordonez', '2018-08-03 23:05:59.316', 'imordonez');
INSERT INTO public.citas (id, paciente, profesional, fecha, activo, descripcion, creado, creadopor, actualizado, actualizadopor) VALUES (6, 10, 3, '2018-08-04 08:40:00', true, '[Cita agendada por: imordonez - 04/08/2018 19:06]', '2018-08-04 19:06:29.699', 'imordonez', '2018-08-04 19:06:29.699', 'imordonez');
INSERT INTO public.citas (id, paciente, profesional, fecha, activo, descripcion, creado, creadopor, actualizado, actualizadopor) VALUES (7, 10, 1, '2018-08-05 00:00:00', true, '[Cita agendada por: imordonez - 05/08/2018 22:55]', '2018-08-05 22:55:14.505', 'imordonez', '2018-08-05 22:55:14.505', 'imordonez');
INSERT INTO public.citas (id, paciente, profesional, fecha, activo, descripcion, creado, creadopor, actualizado, actualizadopor) VALUES (8, 10, 1, '2018-08-06 00:00:00', true, '[Cita agendada por: imordonez - 05/08/2018 23:21]', '2018-08-05 23:21:12.572', 'imordonez', '2018-08-05 23:21:12.572', 'imordonez');
INSERT INTO public.citas (id, paciente, profesional, fecha, activo, descripcion, creado, creadopor, actualizado, actualizadopor) VALUES (9, 10, 1, '2018-08-06 01:00:00', true, '[Cita agendada por: imordonez - 05/08/2018 23:21]', '2018-08-05 23:21:19.149', 'imordonez', '2018-08-05 23:21:19.149', 'imordonez');
INSERT INTO public.citas (id, paciente, profesional, fecha, activo, descripcion, creado, creadopor, actualizado, actualizadopor) VALUES (10, 10, 1, '2018-08-05 23:00:00', true, '[Cita agendada por: imordonez - 05/08/2018 23:21]', '2018-08-05 23:21:34.54', 'imordonez', '2018-08-05 23:21:34.54', 'imordonez');
INSERT INTO public.citas (id, paciente, profesional, fecha, activo, descripcion, creado, creadopor, actualizado, actualizadopor) VALUES (11, 10, 1, '2018-08-07 07:00:00', true, '[Cita agendada por: imordonez - 07/08/2018 07:28]', '2018-08-07 07:28:09.791', 'imordonez', '2018-08-07 07:28:09.791', 'imordonez');
INSERT INTO public.citas (id, paciente, profesional, fecha, activo, descripcion, creado, creadopor, actualizado, actualizadopor) VALUES (12, 10, 1, '2018-08-07 08:00:00', true, '[Cita agendada por: imordonez - 07/08/2018 07:36]', '2018-08-07 07:36:01.267', 'imordonez', '2018-08-07 07:36:01.267', 'imordonez');
INSERT INTO public.citas (id, paciente, profesional, fecha, activo, descripcion, creado, creadopor, actualizado, actualizadopor) VALUES (14, 10, 1, '2018-08-10 20:00:00', true, '[Cita agendada por: imordonez - 10/08/2018 20:11]', '2018-08-10 20:11:26.982', 'imordonez', '2018-08-10 20:11:26.982', 'imordonez');
INSERT INTO public.citas (id, paciente, profesional, fecha, activo, descripcion, creado, creadopor, actualizado, actualizadopor) VALUES (15, 11, 1, '2018-08-10 21:00:00', true, '[Cita agendada por: imordonez - 10/08/2018 20:11]', '2018-08-10 20:11:48.078', 'imordonez', '2018-08-10 20:11:48.078', 'imordonez');
INSERT INTO public.citas (id, paciente, profesional, fecha, activo, descripcion, creado, creadopor, actualizado, actualizadopor) VALUES (22, 10, 1, '2018-08-10 23:00:00', true, '[Cita agendada por: imordonez - 10/08/2018 23:24]', '2018-08-10 23:24:39.964', 'imordonez', '2018-08-10 23:24:39.964', 'imordonez');
INSERT INTO public.citas (id, paciente, profesional, fecha, activo, descripcion, creado, creadopor, actualizado, actualizadopor) VALUES (24, 10, 1, '2018-08-11 01:00:00', true, '[Cita agendada por: imordonez - 11/08/2018 00:00]', '2018-08-11 00:00:58.499', 'imordonez', '2018-08-11 00:00:58.499', 'imordonez');
INSERT INTO public.citas (id, paciente, profesional, fecha, activo, descripcion, creado, creadopor, actualizado, actualizadopor) VALUES (25, 10, 1, '2018-08-11 02:00:00', true, '[Cita agendada por: imordonez - 11/08/2018 00:39]', '2018-08-11 00:39:24.772', 'imordonez', '2018-08-11 00:39:24.772', 'imordonez');
INSERT INTO public.citas (id, paciente, profesional, fecha, activo, descripcion, creado, creadopor, actualizado, actualizadopor) VALUES (26, 11, 1, '2018-08-11 03:00:00', true, '[Cita agendada por: imordonez - 11/08/2018 01:10]', '2018-08-11 01:10:41.17', 'imordonez', '2018-08-11 01:10:41.17', 'imordonez');
INSERT INTO public.citas (id, paciente, profesional, fecha, activo, descripcion, creado, creadopor, actualizado, actualizadopor) VALUES (23, 10, 1, '2018-08-11 00:00:00', false, 'Cita cancelada.Por razones médicas', '2018-08-11 00:00:54.507', 'imordonez', '2018-08-11 02:36:09.165', 'imordonez');
INSERT INTO public.citas (id, paciente, profesional, fecha, activo, descripcion, creado, creadopor, actualizado, actualizadopor) VALUES (27, 10, 1, '2018-08-11 04:00:00', true, 'Cita agendada. Vendrá temprano.', '2018-08-11 02:38:43.87', 'imordonez', '2018-08-11 02:38:43.87', 'imordonez');
INSERT INTO public.citas (id, paciente, profesional, fecha, activo, descripcion, creado, creadopor, actualizado, actualizadopor) VALUES (28, 10, 1, '2018-08-12 07:00:00', true, 'Cita agendada. ', '2018-08-12 07:49:35.908', 'imordonez', '2018-08-12 07:49:35.908', 'imordonez');
INSERT INTO public.citas (id, paciente, profesional, fecha, activo, descripcion, creado, creadopor, actualizado, actualizadopor) VALUES (29, 11, 1, '2018-08-14 18:00:00', true, 'Cita agendada. Prueba', '2018-08-14 18:05:22.608', 'imordonez', '2018-08-14 18:05:22.608', 'imordonez');
INSERT INTO public.citas (id, paciente, profesional, fecha, activo, descripcion, creado, creadopor, actualizado, actualizadopor) VALUES (30, 12, 1, '2018-08-16 09:00:00', true, 'Cita reagendada. Primera Cita, se solicita puntualidad.', '2018-08-16 09:05:38.01', 'apvillalba', '2018-08-16 10:14:23.813', 'apvillalba');
INSERT INTO public.citas (id, paciente, profesional, fecha, activo, descripcion, creado, creadopor, actualizado, actualizadopor) VALUES (31, 10, 1, '2018-08-27 08:00:00', true, 'Cita agendada. Cita programada', '2018-08-27 08:25:57.594', 'apvillalba', '2018-08-27 08:25:57.594', 'apvillalba');
INSERT INTO public.citas (id, paciente, profesional, fecha, activo, descripcion, creado, creadopor, actualizado, actualizadopor) VALUES (32, 10, 1, '2018-08-30 11:00:00', true, 'Cita agendada. Ninguna', '2018-08-30 11:12:20.1', 'apvillalba', '2018-08-30 11:12:20.1', 'apvillalba');


--
-- TOC entry 3198 (class 0 OID 68339)
-- Dependencies: 204
-- Data for Name: datos; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.datos (id, clasificador, identificador, ordengrupo, grupo, codigo, nombre, descripcion, opciones, tipo, texto, booleano, entero, "decimal", fecha, hora, fechahora, seleccion, archivo, creado, creadopor, actualizado, actualizadopor, activo, requerido) VALUES (78, 'Atenciones', 15, 31, 'Medicina General', 9, 'Seleccione Una', '', '{"0": "Opción Uno", "1": "Opción Dos", "2": "Opción Tres", "3": "Opción Cuatro"}', 57, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-09-06 12:13:55.57', 'root', '2018-09-06 15:00:20.821', 'root', true, false);
INSERT INTO public.datos (id, clasificador, identificador, ordengrupo, grupo, codigo, nombre, descripcion, opciones, tipo, texto, booleano, entero, "decimal", fecha, hora, fechahora, seleccion, archivo, creado, creadopor, actualizado, actualizadopor, activo, requerido) VALUES (70, 'Atenciones', 15, 31, 'Medicina General', 1, 'Peso (kg)', '', NULL, 49, NULL, NULL, NULL, 67, NULL, NULL, NULL, NULL, NULL, '2018-09-06 12:13:55.382', 'root', '2018-09-06 15:00:20.615', 'root', true, true);
INSERT INTO public.datos (id, clasificador, identificador, ordengrupo, grupo, codigo, nombre, descripcion, opciones, tipo, texto, booleano, entero, "decimal", fecha, hora, fechahora, seleccion, archivo, creado, creadopor, actualizado, actualizadopor, activo, requerido) VALUES (71, 'Atenciones', 15, 31, 'Medicina General', 2, 'Altura (m)', '', NULL, 49, NULL, NULL, NULL, 67, NULL, NULL, NULL, NULL, NULL, '2018-09-06 12:13:55.412', 'root', '2018-09-06 15:00:20.663', 'root', true, true);
INSERT INTO public.datos (id, clasificador, identificador, ordengrupo, grupo, codigo, nombre, descripcion, opciones, tipo, texto, booleano, entero, "decimal", fecha, hora, fechahora, seleccion, archivo, creado, creadopor, actualizado, actualizadopor, activo, requerido) VALUES (1, 'Atenciones', 9, 0, 'Medicina General', NULL, NULL, '', NULL, 49, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-08-30 16:40:53.85', 'root', '2018-08-30 16:40:53.85', 'root', true, true);
INSERT INTO public.datos (id, clasificador, identificador, ordengrupo, grupo, codigo, nombre, descripcion, opciones, tipo, texto, booleano, entero, "decimal", fecha, hora, fechahora, seleccion, archivo, creado, creadopor, actualizado, actualizadopor, activo, requerido) VALUES (2, 'Atenciones', 9, 1, 'Medicina General', NULL, NULL, '', NULL, 49, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-08-30 16:40:53.918', 'root', '2018-08-30 16:40:53.918', 'root', true, true);
INSERT INTO public.datos (id, clasificador, identificador, ordengrupo, grupo, codigo, nombre, descripcion, opciones, tipo, texto, booleano, entero, "decimal", fecha, hora, fechahora, seleccion, archivo, creado, creadopor, actualizado, actualizadopor, activo, requerido) VALUES (3, 'Atenciones', 9, 2, 'Medicina General', NULL, NULL, '', NULL, 49, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-08-30 16:40:53.935', 'root', '2018-08-30 16:40:53.935', 'root', true, true);
INSERT INTO public.datos (id, clasificador, identificador, ordengrupo, grupo, codigo, nombre, descripcion, opciones, tipo, texto, booleano, entero, "decimal", fecha, hora, fechahora, seleccion, archivo, creado, creadopor, actualizado, actualizadopor, activo, requerido) VALUES (4, 'Atenciones', 9, 3, 'Medicina General', NULL, NULL, '', NULL, 49, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-08-30 16:40:53.959', 'root', '2018-08-30 16:40:53.959', 'root', true, true);
INSERT INTO public.datos (id, clasificador, identificador, ordengrupo, grupo, codigo, nombre, descripcion, opciones, tipo, texto, booleano, entero, "decimal", fecha, hora, fechahora, seleccion, archivo, creado, creadopor, actualizado, actualizadopor, activo, requerido) VALUES (5, 'Atenciones', 10, 31, 'Medicina General', 2, 'Altura (m)', '', NULL, 49, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-08-30 16:54:25.397', 'root', '2018-08-30 16:54:25.397', 'root', true, true);
INSERT INTO public.datos (id, clasificador, identificador, ordengrupo, grupo, codigo, nombre, descripcion, opciones, tipo, texto, booleano, entero, "decimal", fecha, hora, fechahora, seleccion, archivo, creado, creadopor, actualizado, actualizadopor, activo, requerido) VALUES (6, 'Atenciones', 10, 31, 'Medicina General', 1, 'Peso (kg)', '', NULL, 49, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-08-30 16:54:25.416', 'root', '2018-08-30 16:54:25.416', 'root', true, true);
INSERT INTO public.datos (id, clasificador, identificador, ordengrupo, grupo, codigo, nombre, descripcion, opciones, tipo, texto, booleano, entero, "decimal", fecha, hora, fechahora, seleccion, archivo, creado, creadopor, actualizado, actualizadopor, activo, requerido) VALUES (7, 'Atenciones', 10, 31, 'Medicina General', 3, 'Presión arterial', '', NULL, 49, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-08-30 16:54:25.433', 'root', '2018-08-30 16:54:25.433', 'root', true, true);
INSERT INTO public.datos (id, clasificador, identificador, ordengrupo, grupo, codigo, nombre, descripcion, opciones, tipo, texto, booleano, entero, "decimal", fecha, hora, fechahora, seleccion, archivo, creado, creadopor, actualizado, actualizadopor, activo, requerido) VALUES (8, 'Atenciones', 10, 31, 'Medicina General', 4, 'Temperatura (ºC)', '', NULL, 49, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-08-30 16:54:25.441', 'root', '2018-08-30 16:54:25.441', 'root', true, true);
INSERT INTO public.datos (id, clasificador, identificador, ordengrupo, grupo, codigo, nombre, descripcion, opciones, tipo, texto, booleano, entero, "decimal", fecha, hora, fechahora, seleccion, archivo, creado, creadopor, actualizado, actualizadopor, activo, requerido) VALUES (9, 'Atenciones', 11, 31, 'Medicina General', 2, 'Altura (m)', '', NULL, 49, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-09-03 14:43:52.786', 'root', '2018-09-03 14:43:52.786', 'root', true, true);
INSERT INTO public.datos (id, clasificador, identificador, ordengrupo, grupo, codigo, nombre, descripcion, opciones, tipo, texto, booleano, entero, "decimal", fecha, hora, fechahora, seleccion, archivo, creado, creadopor, actualizado, actualizadopor, activo, requerido) VALUES (10, 'Atenciones', 11, 31, 'Medicina General', 1, 'Peso (kg)', '', NULL, 49, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-09-03 14:43:52.841', 'root', '2018-09-03 14:43:52.841', 'root', true, true);
INSERT INTO public.datos (id, clasificador, identificador, ordengrupo, grupo, codigo, nombre, descripcion, opciones, tipo, texto, booleano, entero, "decimal", fecha, hora, fechahora, seleccion, archivo, creado, creadopor, actualizado, actualizadopor, activo, requerido) VALUES (11, 'Atenciones', 11, 31, 'Medicina General', 3, 'Presión arterial', '', NULL, 49, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-09-03 14:43:52.858', 'root', '2018-09-03 14:43:52.858', 'root', true, true);
INSERT INTO public.datos (id, clasificador, identificador, ordengrupo, grupo, codigo, nombre, descripcion, opciones, tipo, texto, booleano, entero, "decimal", fecha, hora, fechahora, seleccion, archivo, creado, creadopor, actualizado, actualizadopor, activo, requerido) VALUES (72, 'Atenciones', 15, 31, 'Medicina General', 3, 'Presión arterial', '', NULL, 49, NULL, NULL, NULL, 67, NULL, NULL, NULL, NULL, NULL, '2018-09-06 12:13:55.429', 'root', '2018-09-06 15:00:20.715', 'root', true, true);
INSERT INTO public.datos (id, clasificador, identificador, ordengrupo, grupo, codigo, nombre, descripcion, opciones, tipo, texto, booleano, entero, "decimal", fecha, hora, fechahora, seleccion, archivo, creado, creadopor, actualizado, actualizadopor, activo, requerido) VALUES (12, 'Atenciones', 11, 31, 'Medicina General', 4, 'Temperatura (ºC)', '', NULL, 49, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-09-03 14:43:52.875', 'root', '2018-09-03 14:43:52.875', 'root', true, true);
INSERT INTO public.datos (id, clasificador, identificador, ordengrupo, grupo, codigo, nombre, descripcion, opciones, tipo, texto, booleano, entero, "decimal", fecha, hora, fechahora, seleccion, archivo, creado, creadopor, actualizado, actualizadopor, activo, requerido) VALUES (13, 'Atenciones', 11, 31, 'Medicina General', 5, 'Seleccione una opción', '', NULL, 53, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-09-03 14:43:52.883', 'root', '2018-09-03 14:43:52.883', 'root', true, true);
INSERT INTO public.datos (id, clasificador, identificador, ordengrupo, grupo, codigo, nombre, descripcion, opciones, tipo, texto, booleano, entero, "decimal", fecha, hora, fechahora, seleccion, archivo, creado, creadopor, actualizado, actualizadopor, activo, requerido) VALUES (14, 'Atenciones', 12, 31, 'Medicina General', 2, 'Altura (m)', '', NULL, 49, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-09-04 08:13:42.324', 'root', '2018-09-04 08:13:42.324', 'root', true, true);
INSERT INTO public.datos (id, clasificador, identificador, ordengrupo, grupo, codigo, nombre, descripcion, opciones, tipo, texto, booleano, entero, "decimal", fecha, hora, fechahora, seleccion, archivo, creado, creadopor, actualizado, actualizadopor, activo, requerido) VALUES (73, 'Atenciones', 15, 31, 'Medicina General', 4, 'Temperatura (ºC)', '', NULL, 49, NULL, NULL, NULL, 67, NULL, NULL, NULL, NULL, NULL, '2018-09-06 12:13:55.445', 'root', '2018-09-06 15:00:20.732', 'root', true, true);
INSERT INTO public.datos (id, clasificador, identificador, ordengrupo, grupo, codigo, nombre, descripcion, opciones, tipo, texto, booleano, entero, "decimal", fecha, hora, fechahora, seleccion, archivo, creado, creadopor, actualizado, actualizadopor, activo, requerido) VALUES (75, 'Atenciones', 15, 31, 'Medicina General', 6, 'Fecha Atención', '', NULL, 50, NULL, NULL, NULL, NULL, '2018-09-06', NULL, NULL, NULL, NULL, '2018-09-06 12:13:55.504', 'root', '2018-09-06 15:00:20.772', 'root', true, true);
INSERT INTO public.datos (id, clasificador, identificador, ordengrupo, grupo, codigo, nombre, descripcion, opciones, tipo, texto, booleano, entero, "decimal", fecha, hora, fechahora, seleccion, archivo, creado, creadopor, actualizado, actualizadopor, activo, requerido) VALUES (76, 'Atenciones', 15, 31, 'Medicina General', 7, 'Alergias', '', NULL, 47, NULL, true, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-09-06 12:13:55.52', 'root', '2018-09-06 15:00:20.78', 'root', true, true);
INSERT INTO public.datos (id, clasificador, identificador, ordengrupo, grupo, codigo, nombre, descripcion, opciones, tipo, texto, booleano, entero, "decimal", fecha, hora, fechahora, seleccion, archivo, creado, creadopor, actualizado, actualizadopor, activo, requerido) VALUES (74, 'Atenciones', 15, 31, 'Medicina General', 5, 'Seleccione una opción', '', '{"0": "Opción 01", "1": "Opción 02", "2": "Opción 03"}', 53, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{"0": "Opción 01"}', NULL, '2018-09-06 12:13:55.453', 'root', '2018-09-06 15:00:20.747', 'root', true, true);
INSERT INTO public.datos (id, clasificador, identificador, ordengrupo, grupo, codigo, nombre, descripcion, opciones, tipo, texto, booleano, entero, "decimal", fecha, hora, fechahora, seleccion, archivo, creado, creadopor, actualizado, actualizadopor, activo, requerido) VALUES (77, 'Atenciones', 15, 31, 'Medicina General', 8, 'Selección múltiple', '', '{"0": "Opción 01", "1": "Opción 02", "2": "Opción 03"}', 54, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{"0": "Opción 01"}', NULL, '2018-09-06 12:13:55.537', 'root', '2018-09-06 15:00:20.797', 'root', true, true);
INSERT INTO public.datos (id, clasificador, identificador, ordengrupo, grupo, codigo, nombre, descripcion, opciones, tipo, texto, booleano, entero, "decimal", fecha, hora, fechahora, seleccion, archivo, creado, creadopor, actualizado, actualizadopor, activo, requerido) VALUES (15, 'Atenciones', 12, 31, 'Medicina General', 1, 'Peso (kg)', '', NULL, 49, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-09-04 08:13:42.389', 'root', '2018-09-04 08:13:42.389', 'root', true, true);
INSERT INTO public.datos (id, clasificador, identificador, ordengrupo, grupo, codigo, nombre, descripcion, opciones, tipo, texto, booleano, entero, "decimal", fecha, hora, fechahora, seleccion, archivo, creado, creadopor, actualizado, actualizadopor, activo, requerido) VALUES (16, 'Atenciones', 12, 31, 'Medicina General', 3, 'Presión arterial', '', NULL, 49, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-09-04 08:13:42.431', 'root', '2018-09-04 08:13:42.431', 'root', true, true);
INSERT INTO public.datos (id, clasificador, identificador, ordengrupo, grupo, codigo, nombre, descripcion, opciones, tipo, texto, booleano, entero, "decimal", fecha, hora, fechahora, seleccion, archivo, creado, creadopor, actualizado, actualizadopor, activo, requerido) VALUES (17, 'Atenciones', 12, 31, 'Medicina General', 4, 'Temperatura (ºC)', '', NULL, 49, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-09-04 08:13:42.464', 'root', '2018-09-04 08:13:42.464', 'root', true, true);
INSERT INTO public.datos (id, clasificador, identificador, ordengrupo, grupo, codigo, nombre, descripcion, opciones, tipo, texto, booleano, entero, "decimal", fecha, hora, fechahora, seleccion, archivo, creado, creadopor, actualizado, actualizadopor, activo, requerido) VALUES (18, 'Atenciones', 12, 31, 'Medicina General', 5, 'Seleccione una opción', '', NULL, 53, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-09-04 08:13:42.514', 'root', '2018-09-04 08:13:42.514', 'root', true, true);
INSERT INTO public.datos (id, clasificador, identificador, ordengrupo, grupo, codigo, nombre, descripcion, opciones, tipo, texto, booleano, entero, "decimal", fecha, hora, fechahora, seleccion, archivo, creado, creadopor, actualizado, actualizadopor, activo, requerido) VALUES (55, 'Atenciones', 13, 31, 'Medicina General', 3, 'Presión arterial', '', NULL, 49, NULL, NULL, NULL, 2, NULL, NULL, NULL, NULL, NULL, '2018-09-04 17:31:27.449', 'root', '2018-09-04 17:31:27.449', 'root', true, true);
INSERT INTO public.datos (id, clasificador, identificador, ordengrupo, grupo, codigo, nombre, descripcion, opciones, tipo, texto, booleano, entero, "decimal", fecha, hora, fechahora, seleccion, archivo, creado, creadopor, actualizado, actualizadopor, activo, requerido) VALUES (58, 'Atenciones', 13, 31, 'Medicina General', 6, 'Fecha Atención', '', NULL, 50, NULL, NULL, NULL, NULL, '2018-09-04', NULL, NULL, NULL, NULL, '2018-09-04 17:31:27.508', 'root', '2018-09-04 17:31:27.508', 'root', true, true);
INSERT INTO public.datos (id, clasificador, identificador, ordengrupo, grupo, codigo, nombre, descripcion, opciones, tipo, texto, booleano, entero, "decimal", fecha, hora, fechahora, seleccion, archivo, creado, creadopor, actualizado, actualizadopor, activo, requerido) VALUES (60, 'Atenciones', 13, 31, 'Medicina General', 8, 'Selección múltiple', '', '{"0": "Opción 01", "1": "Opción 02", "2": "Opción 03"}', 54, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{"0": "Opción 01", "1": "Opción 02", "2": "Opción 03"}', NULL, '2018-09-04 17:31:27.533', 'root', '2018-09-04 17:31:27.533', 'root', true, true);
INSERT INTO public.datos (id, clasificador, identificador, ordengrupo, grupo, codigo, nombre, descripcion, opciones, tipo, texto, booleano, entero, "decimal", fecha, hora, fechahora, seleccion, archivo, creado, creadopor, actualizado, actualizadopor, activo, requerido) VALUES (59, 'Atenciones', 13, 31, 'Medicina General', 7, 'Alergias', '', NULL, 47, NULL, true, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-09-04 17:31:27.516', 'root', '2018-09-04 17:31:27.516', 'root', true, true);
INSERT INTO public.datos (id, clasificador, identificador, ordengrupo, grupo, codigo, nombre, descripcion, opciones, tipo, texto, booleano, entero, "decimal", fecha, hora, fechahora, seleccion, archivo, creado, creadopor, actualizado, actualizadopor, activo, requerido) VALUES (56, 'Atenciones', 13, 31, 'Medicina General', 4, 'Temperatura (ºC)', '', NULL, 49, NULL, NULL, NULL, 3, NULL, NULL, NULL, NULL, NULL, '2018-09-04 17:31:27.458', 'root', '2018-09-04 17:31:27.458', 'root', true, true);
INSERT INTO public.datos (id, clasificador, identificador, ordengrupo, grupo, codigo, nombre, descripcion, opciones, tipo, texto, booleano, entero, "decimal", fecha, hora, fechahora, seleccion, archivo, creado, creadopor, actualizado, actualizadopor, activo, requerido) VALUES (53, 'Atenciones', 13, 31, 'Medicina General', 1, 'Peso (kg)', '', NULL, 49, NULL, NULL, NULL, 58, NULL, NULL, NULL, NULL, NULL, '2018-09-04 17:31:27.356', 'root', '2018-09-04 17:31:27.356', 'root', true, true);
INSERT INTO public.datos (id, clasificador, identificador, ordengrupo, grupo, codigo, nombre, descripcion, opciones, tipo, texto, booleano, entero, "decimal", fecha, hora, fechahora, seleccion, archivo, creado, creadopor, actualizado, actualizadopor, activo, requerido) VALUES (54, 'Atenciones', 13, 31, 'Medicina General', 2, 'Altura (m)', '', NULL, 49, NULL, NULL, NULL, 52, NULL, NULL, NULL, NULL, NULL, '2018-09-04 17:31:27.433', 'root', '2018-09-04 17:31:27.433', 'root', true, true);
INSERT INTO public.datos (id, clasificador, identificador, ordengrupo, grupo, codigo, nombre, descripcion, opciones, tipo, texto, booleano, entero, "decimal", fecha, hora, fechahora, seleccion, archivo, creado, creadopor, actualizado, actualizadopor, activo, requerido) VALUES (57, 'Atenciones', 13, 31, 'Medicina General', 5, 'Seleccione una opción', '', '{"0": "Opción 01", "1": "Opción 02", "2": "Opción 03"}', 53, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{"2": "Opción 03"}', NULL, '2018-09-04 17:31:27.474', 'root', '2018-09-04 17:31:27.474', 'root', true, true);
INSERT INTO public.datos (id, clasificador, identificador, ordengrupo, grupo, codigo, nombre, descripcion, opciones, tipo, texto, booleano, entero, "decimal", fecha, hora, fechahora, seleccion, archivo, creado, creadopor, actualizado, actualizadopor, activo, requerido) VALUES (61, 'Atenciones', 14, 31, 'Medicina General', 1, 'Peso (kg)', '', NULL, 49, NULL, NULL, NULL, 120, NULL, NULL, NULL, NULL, NULL, '2018-09-05 09:48:46.306', 'root', '2018-09-05 11:46:11.598', 'root', true, true);
INSERT INTO public.datos (id, clasificador, identificador, ordengrupo, grupo, codigo, nombre, descripcion, opciones, tipo, texto, booleano, entero, "decimal", fecha, hora, fechahora, seleccion, archivo, creado, creadopor, actualizado, actualizadopor, activo, requerido) VALUES (62, 'Atenciones', 14, 31, 'Medicina General', 2, 'Altura (m)', '', NULL, 49, NULL, NULL, NULL, 1.72999999999999998, NULL, NULL, NULL, NULL, NULL, '2018-09-05 09:48:46.323', 'root', '2018-09-05 11:46:11.608', 'root', true, true);
INSERT INTO public.datos (id, clasificador, identificador, ordengrupo, grupo, codigo, nombre, descripcion, opciones, tipo, texto, booleano, entero, "decimal", fecha, hora, fechahora, seleccion, archivo, creado, creadopor, actualizado, actualizadopor, activo, requerido) VALUES (64, 'Atenciones', 14, 31, 'Medicina General', 4, 'Temperatura (ºC)', '', NULL, 49, NULL, NULL, NULL, 35, NULL, NULL, NULL, NULL, NULL, '2018-09-05 09:48:46.373', 'root', '2018-09-05 11:46:11.643', 'root', true, true);
INSERT INTO public.datos (id, clasificador, identificador, ordengrupo, grupo, codigo, nombre, descripcion, opciones, tipo, texto, booleano, entero, "decimal", fecha, hora, fechahora, seleccion, archivo, creado, creadopor, actualizado, actualizadopor, activo, requerido) VALUES (66, 'Atenciones', 14, 31, 'Medicina General', 6, 'Fecha Atención', '', NULL, 50, NULL, NULL, NULL, NULL, '2018-09-05', NULL, NULL, NULL, NULL, '2018-09-05 09:48:46.407', 'root', '2018-09-05 11:46:11.692', 'root', true, true);
INSERT INTO public.datos (id, clasificador, identificador, ordengrupo, grupo, codigo, nombre, descripcion, opciones, tipo, texto, booleano, entero, "decimal", fecha, hora, fechahora, seleccion, archivo, creado, creadopor, actualizado, actualizadopor, activo, requerido) VALUES (65, 'Atenciones', 14, 31, 'Medicina General', 5, 'Seleccione una opción', '', '{"0": "Opción 01", "1": "Opción 02", "2": "Opción 03"}', 53, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{"1": "Opción 02"}', NULL, '2018-09-05 09:48:46.382', 'root', '2018-09-05 11:46:11.66', 'root', true, true);
INSERT INTO public.datos (id, clasificador, identificador, ordengrupo, grupo, codigo, nombre, descripcion, opciones, tipo, texto, booleano, entero, "decimal", fecha, hora, fechahora, seleccion, archivo, creado, creadopor, actualizado, actualizadopor, activo, requerido) VALUES (68, 'Atenciones', 14, 31, 'Medicina General', 8, 'Selección múltiple', '', '{"0": "Opción 01", "1": "Opción 02", "2": "Opción 03"}', 54, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{"0": "Opción 01", "1": "Opción 02"}', NULL, '2018-09-05 09:48:46.432', 'root', '2018-09-05 11:46:11.725', 'root', true, true);
INSERT INTO public.datos (id, clasificador, identificador, ordengrupo, grupo, codigo, nombre, descripcion, opciones, tipo, texto, booleano, entero, "decimal", fecha, hora, fechahora, seleccion, archivo, creado, creadopor, actualizado, actualizadopor, activo, requerido) VALUES (69, 'Atenciones', 14, 31, 'Medicina General', 9, 'Seleccione Una', '', '{"0": "Opción Uno", "1": "Opción Dos", "2": "Opción Tres", "3": "Opción Cuatro"}', 57, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{"3": "Opción Cuatro"}', NULL, '2018-09-05 09:48:46.457', 'root', '2018-09-05 11:46:11.75', 'root', true, true);
INSERT INTO public.datos (id, clasificador, identificador, ordengrupo, grupo, codigo, nombre, descripcion, opciones, tipo, texto, booleano, entero, "decimal", fecha, hora, fechahora, seleccion, archivo, creado, creadopor, actualizado, actualizadopor, activo, requerido) VALUES (63, 'Atenciones', 14, 31, 'Medicina General', 3, 'Presión arterial', '', NULL, 49, NULL, NULL, NULL, 12, NULL, NULL, NULL, NULL, NULL, '2018-09-05 09:48:46.357', 'root', '2018-09-05 11:46:11.626', 'root', true, true);
INSERT INTO public.datos (id, clasificador, identificador, ordengrupo, grupo, codigo, nombre, descripcion, opciones, tipo, texto, booleano, entero, "decimal", fecha, hora, fechahora, seleccion, archivo, creado, creadopor, actualizado, actualizadopor, activo, requerido) VALUES (67, 'Atenciones', 14, 31, 'Medicina General', 7, 'Alergias', '', NULL, 47, NULL, true, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-09-05 09:48:46.415', 'root', '2018-09-05 11:46:11.708', 'root', true, true);
INSERT INTO public.datos (id, clasificador, identificador, ordengrupo, grupo, codigo, nombre, descripcion, opciones, tipo, texto, booleano, entero, "decimal", fecha, hora, fechahora, seleccion, archivo, creado, creadopor, actualizado, actualizadopor, activo, requerido) VALUES (79, 'Atenciones', 16, 31, 'Medicina General', 1, 'Peso (kg)', '', NULL, 49, NULL, NULL, NULL, 12, NULL, NULL, NULL, NULL, NULL, '2018-09-07 12:08:48.278', 'root', '2018-09-07 12:08:48.278', 'root', true, true);
INSERT INTO public.datos (id, clasificador, identificador, ordengrupo, grupo, codigo, nombre, descripcion, opciones, tipo, texto, booleano, entero, "decimal", fecha, hora, fechahora, seleccion, archivo, creado, creadopor, actualizado, actualizadopor, activo, requerido) VALUES (80, 'Atenciones', 16, 31, 'Medicina General', 2, 'Altura (m)', '', NULL, 49, NULL, NULL, NULL, 12, NULL, NULL, NULL, NULL, NULL, '2018-09-07 12:08:48.291', 'root', '2018-09-07 12:08:48.291', 'root', true, true);
INSERT INTO public.datos (id, clasificador, identificador, ordengrupo, grupo, codigo, nombre, descripcion, opciones, tipo, texto, booleano, entero, "decimal", fecha, hora, fechahora, seleccion, archivo, creado, creadopor, actualizado, actualizadopor, activo, requerido) VALUES (81, 'Atenciones', 16, 31, 'Medicina General', 3, 'Presión arterial', '', NULL, 49, NULL, NULL, NULL, 12, NULL, NULL, NULL, NULL, NULL, '2018-09-07 12:08:48.308', 'root', '2018-09-07 12:08:48.308', 'root', true, true);
INSERT INTO public.datos (id, clasificador, identificador, ordengrupo, grupo, codigo, nombre, descripcion, opciones, tipo, texto, booleano, entero, "decimal", fecha, hora, fechahora, seleccion, archivo, creado, creadopor, actualizado, actualizadopor, activo, requerido) VALUES (82, 'Atenciones', 16, 31, 'Medicina General', 4, 'Temperatura (ºC)', '', NULL, 49, NULL, NULL, NULL, 12, NULL, NULL, NULL, NULL, NULL, '2018-09-07 12:08:48.316', 'root', '2018-09-07 12:08:48.316', 'root', true, true);
INSERT INTO public.datos (id, clasificador, identificador, ordengrupo, grupo, codigo, nombre, descripcion, opciones, tipo, texto, booleano, entero, "decimal", fecha, hora, fechahora, seleccion, archivo, creado, creadopor, actualizado, actualizadopor, activo, requerido) VALUES (83, 'Atenciones', 16, 31, 'Medicina General', 5, 'Seleccione una opción', '', '{"0": "Opción 01", "1": "Opción 02", "2": "Opción 03"}', 53, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{"0": "Opción 01"}', NULL, '2018-09-07 12:08:48.333', 'root', '2018-09-07 12:08:48.333', 'root', true, true);
INSERT INTO public.datos (id, clasificador, identificador, ordengrupo, grupo, codigo, nombre, descripcion, opciones, tipo, texto, booleano, entero, "decimal", fecha, hora, fechahora, seleccion, archivo, creado, creadopor, actualizado, actualizadopor, activo, requerido) VALUES (84, 'Atenciones', 16, 31, 'Medicina General', 6, 'Fecha Atención', '', NULL, 50, NULL, NULL, NULL, NULL, '2018-09-07', NULL, NULL, NULL, NULL, '2018-09-07 12:08:48.358', 'root', '2018-09-07 12:08:48.358', 'root', true, true);
INSERT INTO public.datos (id, clasificador, identificador, ordengrupo, grupo, codigo, nombre, descripcion, opciones, tipo, texto, booleano, entero, "decimal", fecha, hora, fechahora, seleccion, archivo, creado, creadopor, actualizado, actualizadopor, activo, requerido) VALUES (85, 'Atenciones', 16, 31, 'Medicina General', 7, 'Alergias', '', NULL, 47, NULL, true, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-09-07 12:08:48.366', 'root', '2018-09-07 12:08:48.366', 'root', true, true);
INSERT INTO public.datos (id, clasificador, identificador, ordengrupo, grupo, codigo, nombre, descripcion, opciones, tipo, texto, booleano, entero, "decimal", fecha, hora, fechahora, seleccion, archivo, creado, creadopor, actualizado, actualizadopor, activo, requerido) VALUES (86, 'Atenciones', 16, 31, 'Medicina General', 8, 'Selección múltiple', '', '{"0": "Opción 01", "1": "Opción 02", "2": "Opción 03"}', 54, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{"0": "Opción 01"}', NULL, '2018-09-07 12:08:48.383', 'root', '2018-09-07 12:08:48.383', 'root', true, true);
INSERT INTO public.datos (id, clasificador, identificador, ordengrupo, grupo, codigo, nombre, descripcion, opciones, tipo, texto, booleano, entero, "decimal", fecha, hora, fechahora, seleccion, archivo, creado, creadopor, actualizado, actualizadopor, activo, requerido) VALUES (87, 'Atenciones', 16, 31, 'Medicina General', 9, 'Seleccione Una', '', '{"0": "Opción Uno", "1": "Opción Dos", "2": "Opción Tres", "3": "Opción Cuatro"}', 57, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{"0": "Opción Uno"}', NULL, '2018-09-07 12:08:48.408', 'root', '2018-09-07 12:08:48.408', 'root', true, false);
INSERT INTO public.datos (id, clasificador, identificador, ordengrupo, grupo, codigo, nombre, descripcion, opciones, tipo, texto, booleano, entero, "decimal", fecha, hora, fechahora, seleccion, archivo, creado, creadopor, actualizado, actualizadopor, activo, requerido) VALUES (88, 'Atenciones', 16, 31, 'Medicina General', 10, 'Archivo', 'Archivo', NULL, 55, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 29, '2018-09-07 12:08:48.433', 'root', '2018-09-07 12:08:48.433', 'root', true, true);
INSERT INTO public.datos (id, clasificador, identificador, ordengrupo, grupo, codigo, nombre, descripcion, opciones, tipo, texto, booleano, entero, "decimal", fecha, hora, fechahora, seleccion, archivo, creado, creadopor, actualizado, actualizadopor, activo, requerido) VALUES (119, 'Atenciones', 23, 31, 'Medicina General', 1, 'Peso (kg)', '', NULL, 49, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-09-13 12:11:46.131', 'root', '2018-09-13 12:11:46.131', 'root', true, true);
INSERT INTO public.datos (id, clasificador, identificador, ordengrupo, grupo, codigo, nombre, descripcion, opciones, tipo, texto, booleano, entero, "decimal", fecha, hora, fechahora, seleccion, archivo, creado, creadopor, actualizado, actualizadopor, activo, requerido) VALUES (120, 'Atenciones', 23, 31, 'Medicina General', 2, 'Altura (m)', '', NULL, 49, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-09-13 12:11:46.14', 'root', '2018-09-13 12:11:46.14', 'root', true, true);
INSERT INTO public.datos (id, clasificador, identificador, ordengrupo, grupo, codigo, nombre, descripcion, opciones, tipo, texto, booleano, entero, "decimal", fecha, hora, fechahora, seleccion, archivo, creado, creadopor, actualizado, actualizadopor, activo, requerido) VALUES (121, 'Atenciones', 23, 31, 'Medicina General', 3, 'Presión arterial', '', NULL, 49, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-09-13 12:11:46.157', 'root', '2018-09-13 12:11:46.157', 'root', true, true);
INSERT INTO public.datos (id, clasificador, identificador, ordengrupo, grupo, codigo, nombre, descripcion, opciones, tipo, texto, booleano, entero, "decimal", fecha, hora, fechahora, seleccion, archivo, creado, creadopor, actualizado, actualizadopor, activo, requerido) VALUES (122, 'Atenciones', 23, 31, 'Medicina General', 4, 'Temperatura (ºC)', '', NULL, 49, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-09-13 12:11:46.166', 'root', '2018-09-13 12:11:46.166', 'root', true, true);
INSERT INTO public.datos (id, clasificador, identificador, ordengrupo, grupo, codigo, nombre, descripcion, opciones, tipo, texto, booleano, entero, "decimal", fecha, hora, fechahora, seleccion, archivo, creado, creadopor, actualizado, actualizadopor, activo, requerido) VALUES (123, 'Atenciones', 23, 31, 'Medicina General', 5, 'Seleccione una opción', '', '{"0": "Opción 01", "1": "Opción 02", "2": "Opción 03"}', 53, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-09-13 12:11:46.182', 'root', '2018-09-13 12:11:46.182', 'root', true, true);
INSERT INTO public.datos (id, clasificador, identificador, ordengrupo, grupo, codigo, nombre, descripcion, opciones, tipo, texto, booleano, entero, "decimal", fecha, hora, fechahora, seleccion, archivo, creado, creadopor, actualizado, actualizadopor, activo, requerido) VALUES (124, 'Atenciones', 23, 31, 'Medicina General', 6, 'Fecha Atención', '', NULL, 50, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-09-13 12:11:46.207', 'root', '2018-09-13 12:11:46.207', 'root', true, true);
INSERT INTO public.datos (id, clasificador, identificador, ordengrupo, grupo, codigo, nombre, descripcion, opciones, tipo, texto, booleano, entero, "decimal", fecha, hora, fechahora, seleccion, archivo, creado, creadopor, actualizado, actualizadopor, activo, requerido) VALUES (125, 'Atenciones', 23, 31, 'Medicina General', 7, 'Alergias', '', NULL, 47, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-09-13 12:11:46.215', 'root', '2018-09-13 12:11:46.215', 'root', true, true);
INSERT INTO public.datos (id, clasificador, identificador, ordengrupo, grupo, codigo, nombre, descripcion, opciones, tipo, texto, booleano, entero, "decimal", fecha, hora, fechahora, seleccion, archivo, creado, creadopor, actualizado, actualizadopor, activo, requerido) VALUES (134, 'Atenciones', 27, 31, 'Medicina General', 6, 'Fecha Atención', '', NULL, 50, NULL, NULL, NULL, NULL, '2018-09-14', NULL, NULL, NULL, NULL, '2018-09-14 09:41:09.269', 'root', '2018-09-14 09:41:09.269', 'root', true, true);
INSERT INTO public.datos (id, clasificador, identificador, ordengrupo, grupo, codigo, nombre, descripcion, opciones, tipo, texto, booleano, entero, "decimal", fecha, hora, fechahora, seleccion, archivo, creado, creadopor, actualizado, actualizadopor, activo, requerido) VALUES (142, 'Atenciones', 28, 33, 'Optometría', 4, 'Selección', 'Selección', '{"0": "Opción 1", "1": "Opción 2"}', 54, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{"0": "Opción 1", "1": "Opción 2"}', NULL, '2018-09-14 12:03:02.168', 'apvillalba', '2018-09-14 12:03:02.168', 'apvillalba', true, false);
INSERT INTO public.datos (id, clasificador, identificador, ordengrupo, grupo, codigo, nombre, descripcion, opciones, tipo, texto, booleano, entero, "decimal", fecha, hora, fechahora, seleccion, archivo, creado, creadopor, actualizado, actualizadopor, activo, requerido) VALUES (133, 'Atenciones', 27, 31, 'Medicina General', 5, 'Seleccione una opción', '', '{"0": "Opción 01", "1": "Opción 02", "2": "Opción 03"}', 53, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{"0": "Opción 01"}', NULL, '2018-09-14 09:41:09.244', 'root', '2018-09-14 09:41:09.244', 'root', true, true);
INSERT INTO public.datos (id, clasificador, identificador, ordengrupo, grupo, codigo, nombre, descripcion, opciones, tipo, texto, booleano, entero, "decimal", fecha, hora, fechahora, seleccion, archivo, creado, creadopor, actualizado, actualizadopor, activo, requerido) VALUES (126, 'Atenciones', 23, 31, 'Medicina General', 8, 'Selección múltiple', '', '{"0": "Opción 01", "1": "Opción 02", "2": "Opción 03"}', 54, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-09-13 12:11:46.232', 'root', '2018-09-13 12:11:46.232', 'root', true, true);
INSERT INTO public.datos (id, clasificador, identificador, ordengrupo, grupo, codigo, nombre, descripcion, opciones, tipo, texto, booleano, entero, "decimal", fecha, hora, fechahora, seleccion, archivo, creado, creadopor, actualizado, actualizadopor, activo, requerido) VALUES (127, 'Atenciones', 23, 31, 'Medicina General', 9, 'Seleccione Una', '', '{"0": "Opción Uno", "1": "Opción Dos", "2": "Opción Tres", "3": "Opción Cuatro"}', 57, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-09-13 12:11:46.257', 'root', '2018-09-13 12:11:46.257', 'root', true, true);
INSERT INTO public.datos (id, clasificador, identificador, ordengrupo, grupo, codigo, nombre, descripcion, opciones, tipo, texto, booleano, entero, "decimal", fecha, hora, fechahora, seleccion, archivo, creado, creadopor, actualizado, actualizadopor, activo, requerido) VALUES (128, 'Atenciones', 23, 31, 'Medicina General', 10, 'Archivo', 'Archivo', NULL, 55, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 48, '2018-09-13 12:11:46.282', 'root', '2018-09-13 12:11:46.282', 'root', true, true);
INSERT INTO public.datos (id, clasificador, identificador, ordengrupo, grupo, codigo, nombre, descripcion, opciones, tipo, texto, booleano, entero, "decimal", fecha, hora, fechahora, seleccion, archivo, creado, creadopor, actualizado, actualizadopor, activo, requerido) VALUES (138, 'Atenciones', 27, 31, 'Medicina General', 10, 'Archivo', 'Archivo', NULL, 55, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 49, '2018-09-14 09:41:09.344', 'root', '2018-09-14 09:41:09.344', 'root', true, true);
INSERT INTO public.datos (id, clasificador, identificador, ordengrupo, grupo, codigo, nombre, descripcion, opciones, tipo, texto, booleano, entero, "decimal", fecha, hora, fechahora, seleccion, archivo, creado, creadopor, actualizado, actualizadopor, activo, requerido) VALUES (129, 'Atenciones', 27, 31, 'Medicina General', 1, 'Peso (kg)', '', NULL, 49, NULL, NULL, NULL, 150, NULL, NULL, NULL, NULL, NULL, '2018-09-14 09:41:09.138', 'root', '2018-09-14 09:41:09.138', 'root', true, true);
INSERT INTO public.datos (id, clasificador, identificador, ordengrupo, grupo, codigo, nombre, descripcion, opciones, tipo, texto, booleano, entero, "decimal", fecha, hora, fechahora, seleccion, archivo, creado, creadopor, actualizado, actualizadopor, activo, requerido) VALUES (141, 'Atenciones', 28, 33, 'Optometría', 3, 'Suba un archivo', 'Suba un archivo', NULL, 55, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 50, '2018-09-14 12:03:02.16', 'apvillalba', '2018-09-14 12:03:02.16', 'apvillalba', true, false);
INSERT INTO public.datos (id, clasificador, identificador, ordengrupo, grupo, codigo, nombre, descripcion, opciones, tipo, texto, booleano, entero, "decimal", fecha, hora, fechahora, seleccion, archivo, creado, creadopor, actualizado, actualizadopor, activo, requerido) VALUES (139, 'Atenciones', 28, 33, 'Optometría', 1, 'Verdadero o Falso', 'Verdadero o Falso', NULL, 47, NULL, true, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-09-14 12:03:02.115', 'apvillalba', '2018-09-14 12:03:02.115', 'apvillalba', true, false);
INSERT INTO public.datos (id, clasificador, identificador, ordengrupo, grupo, codigo, nombre, descripcion, opciones, tipo, texto, booleano, entero, "decimal", fecha, hora, fechahora, seleccion, archivo, creado, creadopor, actualizado, actualizadopor, activo, requerido) VALUES (130, 'Atenciones', 27, 31, 'Medicina General', 2, 'Altura (m)', '', NULL, 49, NULL, NULL, NULL, 1.67999999999999994, NULL, NULL, NULL, NULL, NULL, '2018-09-14 09:41:09.203', 'root', '2018-09-14 09:41:09.203', 'root', true, true);
INSERT INTO public.datos (id, clasificador, identificador, ordengrupo, grupo, codigo, nombre, descripcion, opciones, tipo, texto, booleano, entero, "decimal", fecha, hora, fechahora, seleccion, archivo, creado, creadopor, actualizado, actualizadopor, activo, requerido) VALUES (140, 'Atenciones', 28, 33, 'Optometría', 2, 'Fecha y hora', 'Fecha y hora', NULL, 52, NULL, NULL, NULL, NULL, NULL, NULL, '2018-09-14 12:03:46', NULL, NULL, '2018-09-14 12:03:02.143', 'apvillalba', '2018-09-14 12:03:02.143', 'apvillalba', true, false);
INSERT INTO public.datos (id, clasificador, identificador, ordengrupo, grupo, codigo, nombre, descripcion, opciones, tipo, texto, booleano, entero, "decimal", fecha, hora, fechahora, seleccion, archivo, creado, creadopor, actualizado, actualizadopor, activo, requerido) VALUES (131, 'Atenciones', 27, 31, 'Medicina General', 3, 'Presión arterial', '', NULL, 49, NULL, NULL, NULL, 12, NULL, NULL, NULL, NULL, NULL, '2018-09-14 09:41:09.219', 'root', '2018-09-14 09:41:09.219', 'root', true, true);
INSERT INTO public.datos (id, clasificador, identificador, ordengrupo, grupo, codigo, nombre, descripcion, opciones, tipo, texto, booleano, entero, "decimal", fecha, hora, fechahora, seleccion, archivo, creado, creadopor, actualizado, actualizadopor, activo, requerido) VALUES (132, 'Atenciones', 27, 31, 'Medicina General', 4, 'Temperatura (ºC)', '', NULL, 49, NULL, NULL, NULL, 36, NULL, NULL, NULL, NULL, NULL, '2018-09-14 09:41:09.228', 'root', '2018-09-14 09:41:09.228', 'root', true, true);
INSERT INTO public.datos (id, clasificador, identificador, ordengrupo, grupo, codigo, nombre, descripcion, opciones, tipo, texto, booleano, entero, "decimal", fecha, hora, fechahora, seleccion, archivo, creado, creadopor, actualizado, actualizadopor, activo, requerido) VALUES (136, 'Atenciones', 27, 31, 'Medicina General', 8, 'Selección múltiple', '', '{"0": "Opción 01", "1": "Opción 02", "2": "Opción 03"}', 54, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{"0": "Opción 01"}', NULL, '2018-09-14 09:41:09.295', 'root', '2018-09-14 09:41:09.295', 'root', true, true);
INSERT INTO public.datos (id, clasificador, identificador, ordengrupo, grupo, codigo, nombre, descripcion, opciones, tipo, texto, booleano, entero, "decimal", fecha, hora, fechahora, seleccion, archivo, creado, creadopor, actualizado, actualizadopor, activo, requerido) VALUES (135, 'Atenciones', 27, 31, 'Medicina General', 7, 'Alergias', '', NULL, 47, NULL, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-09-14 09:41:09.278', 'root', '2018-09-14 09:41:09.278', 'root', true, true);
INSERT INTO public.datos (id, clasificador, identificador, ordengrupo, grupo, codigo, nombre, descripcion, opciones, tipo, texto, booleano, entero, "decimal", fecha, hora, fechahora, seleccion, archivo, creado, creadopor, actualizado, actualizadopor, activo, requerido) VALUES (137, 'Atenciones', 27, 31, 'Medicina General', 9, 'Seleccione Una', '', '{"0": "Opción Uno", "1": "Opción Dos", "2": "Opción Tres", "3": "Opción Cuatro"}', 57, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{"0": "Opción Uno"}', NULL, '2018-09-14 09:41:09.319', 'root', '2018-09-14 09:41:09.319', 'root', true, true);
INSERT INTO public.datos (id, clasificador, identificador, ordengrupo, grupo, codigo, nombre, descripcion, opciones, tipo, texto, booleano, entero, "decimal", fecha, hora, fechahora, seleccion, archivo, creado, creadopor, actualizado, actualizadopor, activo, requerido) VALUES (145, 'Atenciones', 29, 33, 'Optometría', 3, 'Suba un archivo', 'Suba un archivo', NULL, 55, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 51, '2018-09-19 05:48:31.347', 'apvillalba', '2018-09-19 05:48:31.347', 'apvillalba', true, false);
INSERT INTO public.datos (id, clasificador, identificador, ordengrupo, grupo, codigo, nombre, descripcion, opciones, tipo, texto, booleano, entero, "decimal", fecha, hora, fechahora, seleccion, archivo, creado, creadopor, actualizado, actualizadopor, activo, requerido) VALUES (143, 'Atenciones', 29, 33, 'Optometría', 1, 'Verdadero o Falso', 'Verdadero o Falso', NULL, 47, NULL, true, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-09-19 05:48:31.328', 'apvillalba', '2018-09-19 05:48:31.328', 'apvillalba', true, false);
INSERT INTO public.datos (id, clasificador, identificador, ordengrupo, grupo, codigo, nombre, descripcion, opciones, tipo, texto, booleano, entero, "decimal", fecha, hora, fechahora, seleccion, archivo, creado, creadopor, actualizado, actualizadopor, activo, requerido) VALUES (144, 'Atenciones', 29, 33, 'Optometría', 2, 'Fecha y hora', 'Fecha y hora', NULL, 52, NULL, NULL, NULL, NULL, NULL, NULL, '2018-09-19 05:50:46', NULL, NULL, '2018-09-19 05:48:31.34', 'apvillalba', '2018-09-19 05:48:31.34', 'apvillalba', true, false);
INSERT INTO public.datos (id, clasificador, identificador, ordengrupo, grupo, codigo, nombre, descripcion, opciones, tipo, texto, booleano, entero, "decimal", fecha, hora, fechahora, seleccion, archivo, creado, creadopor, actualizado, actualizadopor, activo, requerido) VALUES (146, 'Atenciones', 29, 33, 'Optometría', 4, 'Selección', 'Selección', '{"0": "Opción 1", "1": "Opción 2"}', 54, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{"0": "Opción 1"}', NULL, '2018-09-19 05:48:31.352', 'apvillalba', '2018-09-19 05:48:31.352', 'apvillalba', true, false);
INSERT INTO public.datos (id, clasificador, identificador, ordengrupo, grupo, codigo, nombre, descripcion, opciones, tipo, texto, booleano, entero, "decimal", fecha, hora, fechahora, seleccion, archivo, creado, creadopor, actualizado, actualizadopor, activo, requerido) VALUES (148, 'Atenciones', 30, 33, 'Optometría', 2, 'Fecha y hora', 'Fecha y hora', NULL, 52, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-11-02 00:53:30.273', 'apvillalba', '2018-11-02 00:53:30.273', 'apvillalba', true, false);
INSERT INTO public.datos (id, clasificador, identificador, ordengrupo, grupo, codigo, nombre, descripcion, opciones, tipo, texto, booleano, entero, "decimal", fecha, hora, fechahora, seleccion, archivo, creado, creadopor, actualizado, actualizadopor, activo, requerido) VALUES (149, 'Atenciones', 30, 33, 'Optometría', 3, 'Suba un archivo', 'Suba un archivo', NULL, 55, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-11-02 00:53:30.278', 'apvillalba', '2018-11-02 00:53:30.278', 'apvillalba', true, false);
INSERT INTO public.datos (id, clasificador, identificador, ordengrupo, grupo, codigo, nombre, descripcion, opciones, tipo, texto, booleano, entero, "decimal", fecha, hora, fechahora, seleccion, archivo, creado, creadopor, actualizado, actualizadopor, activo, requerido) VALUES (147, 'Atenciones', 30, 33, 'Optometría', 1, 'Verdadero o Falso', 'Verdadero o Falso', NULL, 47, NULL, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-11-02 00:53:30.265', 'apvillalba', '2018-11-02 00:53:30.265', 'apvillalba', true, false);
INSERT INTO public.datos (id, clasificador, identificador, ordengrupo, grupo, codigo, nombre, descripcion, opciones, tipo, texto, booleano, entero, "decimal", fecha, hora, fechahora, seleccion, archivo, creado, creadopor, actualizado, actualizadopor, activo, requerido) VALUES (150, 'Atenciones', 30, 33, 'Optometría', 4, 'Selección', 'Selección', '{"0": "Opción 1", "1": "Opción 2"}', 54, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-11-02 00:53:30.283', 'apvillalba', '2018-11-02 00:53:30.283', 'apvillalba', true, false);
INSERT INTO public.datos (id, clasificador, identificador, ordengrupo, grupo, codigo, nombre, descripcion, opciones, tipo, texto, booleano, entero, "decimal", fecha, hora, fechahora, seleccion, archivo, creado, creadopor, actualizado, actualizadopor, activo, requerido) VALUES (166, 'Atenciones', 32, 31, 'Medicina General', 6, 'Fecha Atención', '', NULL, 50, NULL, NULL, NULL, NULL, '2018-11-15', NULL, NULL, NULL, NULL, '2018-11-02 01:14:12.704', 'root', '2018-11-02 01:14:12.704', 'root', true, true);
INSERT INTO public.datos (id, clasificador, identificador, ordengrupo, grupo, codigo, nombre, descripcion, opciones, tipo, texto, booleano, entero, "decimal", fecha, hora, fechahora, seleccion, archivo, creado, creadopor, actualizado, actualizadopor, activo, requerido) VALUES (168, 'Atenciones', 32, 31, 'Medicina General', 8, 'Selección múltiple', '', '{"0": "Opción 01", "1": "Opción 02", "2": "Opción 03"}', 54, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{"1": "Opción 02"}', NULL, '2018-11-02 01:14:12.71', 'root', '2018-11-02 01:14:12.71', 'root', true, true);
INSERT INTO public.datos (id, clasificador, identificador, ordengrupo, grupo, codigo, nombre, descripcion, opciones, tipo, texto, booleano, entero, "decimal", fecha, hora, fechahora, seleccion, archivo, creado, creadopor, actualizado, actualizadopor, activo, requerido) VALUES (169, 'Atenciones', 32, 31, 'Medicina General', 9, 'Seleccione Una', '', '{"0": "Opción Uno", "1": "Opción Dos", "2": "Opción Tres", "3": "Opción Cuatro"}', 57, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{"1": "Opción Dos"}', NULL, '2018-11-02 01:14:12.714', 'root', '2018-11-02 01:14:12.714', 'root', true, true);
INSERT INTO public.datos (id, clasificador, identificador, ordengrupo, grupo, codigo, nombre, descripcion, opciones, tipo, texto, booleano, entero, "decimal", fecha, hora, fechahora, seleccion, archivo, creado, creadopor, actualizado, actualizadopor, activo, requerido) VALUES (170, 'Atenciones', 32, 31, 'Medicina General', 10, 'Archivo', 'Archivo', NULL, 55, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 53, '2018-11-02 01:14:12.718', 'root', '2018-11-02 01:14:12.718', 'root', true, true);
INSERT INTO public.datos (id, clasificador, identificador, ordengrupo, grupo, codigo, nombre, descripcion, opciones, tipo, texto, booleano, entero, "decimal", fecha, hora, fechahora, seleccion, archivo, creado, creadopor, actualizado, actualizadopor, activo, requerido) VALUES (161, 'Atenciones', 32, 31, 'Medicina General', 1, 'Peso (kg)', '', NULL, 49, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, '2018-11-02 01:14:12.689', 'root', '2018-11-02 01:14:12.689', 'root', true, true);
INSERT INTO public.datos (id, clasificador, identificador, ordengrupo, grupo, codigo, nombre, descripcion, opciones, tipo, texto, booleano, entero, "decimal", fecha, hora, fechahora, seleccion, archivo, creado, creadopor, actualizado, actualizadopor, activo, requerido) VALUES (162, 'Atenciones', 32, 31, 'Medicina General', 2, 'Altura (m)', '', NULL, 49, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, '2018-11-02 01:14:12.692', 'root', '2018-11-02 01:14:12.692', 'root', true, true);
INSERT INTO public.datos (id, clasificador, identificador, ordengrupo, grupo, codigo, nombre, descripcion, opciones, tipo, texto, booleano, entero, "decimal", fecha, hora, fechahora, seleccion, archivo, creado, creadopor, actualizado, actualizadopor, activo, requerido) VALUES (163, 'Atenciones', 32, 31, 'Medicina General', 3, 'Presión arterial', '', NULL, 49, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, '2018-11-02 01:14:12.694', 'root', '2018-11-02 01:14:12.694', 'root', true, true);
INSERT INTO public.datos (id, clasificador, identificador, ordengrupo, grupo, codigo, nombre, descripcion, opciones, tipo, texto, booleano, entero, "decimal", fecha, hora, fechahora, seleccion, archivo, creado, creadopor, actualizado, actualizadopor, activo, requerido) VALUES (164, 'Atenciones', 32, 31, 'Medicina General', 4, 'Temperatura (ºC)', '', NULL, 49, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, '2018-11-02 01:14:12.697', 'root', '2018-11-02 01:14:12.697', 'root', true, true);
INSERT INTO public.datos (id, clasificador, identificador, ordengrupo, grupo, codigo, nombre, descripcion, opciones, tipo, texto, booleano, entero, "decimal", fecha, hora, fechahora, seleccion, archivo, creado, creadopor, actualizado, actualizadopor, activo, requerido) VALUES (167, 'Atenciones', 32, 31, 'Medicina General', 7, 'Alergias', '', NULL, 47, NULL, true, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-11-02 01:14:12.706', 'root', '2018-11-02 01:14:12.706', 'root', true, true);
INSERT INTO public.datos (id, clasificador, identificador, ordengrupo, grupo, codigo, nombre, descripcion, opciones, tipo, texto, booleano, entero, "decimal", fecha, hora, fechahora, seleccion, archivo, creado, creadopor, actualizado, actualizadopor, activo, requerido) VALUES (165, 'Atenciones', 32, 31, 'Medicina General', 5, 'Seleccione una opción', '', '{"0": "Opción 01", "1": "Opción 02", "2": "Opción 03"}', 53, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{"0": "Opción 01"}', NULL, '2018-11-02 01:14:12.699', 'root', '2018-11-02 01:14:12.699', 'root', true, true);
INSERT INTO public.datos (id, clasificador, identificador, ordengrupo, grupo, codigo, nombre, descripcion, opciones, tipo, texto, booleano, entero, "decimal", fecha, hora, fechahora, seleccion, archivo, creado, creadopor, actualizado, actualizadopor, activo, requerido) VALUES (171, 'Atenciones', 33, 31, 'Medicina General', 1, 'Peso (kg)', '', NULL, 49, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-11-03 00:27:51.683', 'root', '2018-11-03 00:27:51.683', 'root', true, true);
INSERT INTO public.datos (id, clasificador, identificador, ordengrupo, grupo, codigo, nombre, descripcion, opciones, tipo, texto, booleano, entero, "decimal", fecha, hora, fechahora, seleccion, archivo, creado, creadopor, actualizado, actualizadopor, activo, requerido) VALUES (172, 'Atenciones', 33, 31, 'Medicina General', 2, 'Altura (m)', '', NULL, 49, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-11-03 00:27:51.695', 'root', '2018-11-03 00:27:51.695', 'root', true, true);
INSERT INTO public.datos (id, clasificador, identificador, ordengrupo, grupo, codigo, nombre, descripcion, opciones, tipo, texto, booleano, entero, "decimal", fecha, hora, fechahora, seleccion, archivo, creado, creadopor, actualizado, actualizadopor, activo, requerido) VALUES (173, 'Atenciones', 33, 31, 'Medicina General', 3, 'Presión arterial', '', NULL, 49, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-11-03 00:27:51.702', 'root', '2018-11-03 00:27:51.702', 'root', true, true);
INSERT INTO public.datos (id, clasificador, identificador, ordengrupo, grupo, codigo, nombre, descripcion, opciones, tipo, texto, booleano, entero, "decimal", fecha, hora, fechahora, seleccion, archivo, creado, creadopor, actualizado, actualizadopor, activo, requerido) VALUES (174, 'Atenciones', 33, 31, 'Medicina General', 4, 'Temperatura (ºC)', '', NULL, 49, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-11-03 00:27:51.709', 'root', '2018-11-03 00:27:51.709', 'root', true, true);
INSERT INTO public.datos (id, clasificador, identificador, ordengrupo, grupo, codigo, nombre, descripcion, opciones, tipo, texto, booleano, entero, "decimal", fecha, hora, fechahora, seleccion, archivo, creado, creadopor, actualizado, actualizadopor, activo, requerido) VALUES (175, 'Atenciones', 33, 31, 'Medicina General', 5, 'Seleccione una opción', '', '{"0": "Opción 01", "1": "Opción 02", "2": "Opción 03"}', 53, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-11-03 00:27:51.715', 'root', '2018-11-03 00:27:51.715', 'root', true, true);
INSERT INTO public.datos (id, clasificador, identificador, ordengrupo, grupo, codigo, nombre, descripcion, opciones, tipo, texto, booleano, entero, "decimal", fecha, hora, fechahora, seleccion, archivo, creado, creadopor, actualizado, actualizadopor, activo, requerido) VALUES (176, 'Atenciones', 33, 31, 'Medicina General', 6, 'Fecha Atención', '', NULL, 50, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-11-03 00:27:51.724', 'root', '2018-11-03 00:27:51.724', 'root', true, true);
INSERT INTO public.datos (id, clasificador, identificador, ordengrupo, grupo, codigo, nombre, descripcion, opciones, tipo, texto, booleano, entero, "decimal", fecha, hora, fechahora, seleccion, archivo, creado, creadopor, actualizado, actualizadopor, activo, requerido) VALUES (177, 'Atenciones', 33, 31, 'Medicina General', 7, 'Alergias', '', NULL, 47, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-11-03 00:27:51.729', 'root', '2018-11-03 00:27:51.729', 'root', true, true);
INSERT INTO public.datos (id, clasificador, identificador, ordengrupo, grupo, codigo, nombre, descripcion, opciones, tipo, texto, booleano, entero, "decimal", fecha, hora, fechahora, seleccion, archivo, creado, creadopor, actualizado, actualizadopor, activo, requerido) VALUES (178, 'Atenciones', 33, 31, 'Medicina General', 8, 'Selección múltiple', '', '{"0": "Opción 01", "1": "Opción 02", "2": "Opción 03"}', 54, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-11-03 00:27:51.734', 'root', '2018-11-03 00:27:51.734', 'root', true, true);
INSERT INTO public.datos (id, clasificador, identificador, ordengrupo, grupo, codigo, nombre, descripcion, opciones, tipo, texto, booleano, entero, "decimal", fecha, hora, fechahora, seleccion, archivo, creado, creadopor, actualizado, actualizadopor, activo, requerido) VALUES (179, 'Atenciones', 33, 31, 'Medicina General', 9, 'Seleccione Una', '', '{"0": "Opción Uno", "1": "Opción Dos", "2": "Opción Tres", "3": "Opción Cuatro"}', 57, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-11-03 00:27:51.744', 'root', '2018-11-03 00:27:51.744', 'root', true, true);
INSERT INTO public.datos (id, clasificador, identificador, ordengrupo, grupo, codigo, nombre, descripcion, opciones, tipo, texto, booleano, entero, "decimal", fecha, hora, fechahora, seleccion, archivo, creado, creadopor, actualizado, actualizadopor, activo, requerido) VALUES (180, 'Atenciones', 33, 31, 'Medicina General', 10, 'Archivo', 'Archivo', NULL, 55, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 55, '2018-11-03 00:27:51.753', 'root', '2018-11-03 00:27:51.753', 'root', true, true);


--
-- TOC entry 3200 (class 0 OID 68347)
-- Dependencies: 206
-- Data for Name: direcciones; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.direcciones (id, primaria, numero, secundaria, piso, referencia, fijo, movil, ciudad, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (3, 'Virgilio Castillo', 'E3-48', 'Domingo Dobbie', 'PB', 'UPC El Camal', '2654855', '0998319195', NULL, NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.direcciones (id, primaria, numero, secundaria, piso, referencia, fijo, movil, ciudad, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (4, 'Vizcaya', 'N-24G', 'Pontevedra', 'PB', 'La Floresta', '0992529704', '', NULL, NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.direcciones (id, primaria, numero, secundaria, piso, referencia, fijo, movil, ciudad, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (5, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.direcciones (id, primaria, numero, secundaria, piso, referencia, fijo, movil, ciudad, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (6, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.direcciones (id, primaria, numero, secundaria, piso, referencia, fijo, movil, ciudad, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (7, 'Av. 10 de agosto', 'N-15-21', 'Riofrío', NULL, '2do piso Oficina 208', '2506859', '2526077', NULL, NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.direcciones (id, primaria, numero, secundaria, piso, referencia, fijo, movil, ciudad, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (1, 'OE13G1', 'S/N', 'S38B', 'PB', 'El Girón del Sur', '3030160', '0987328457', NULL, NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.direcciones (id, primaria, numero, secundaria, piso, referencia, fijo, movil, ciudad, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (10, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.direcciones (id, primaria, numero, secundaria, piso, referencia, fijo, movil, ciudad, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (11, '1', '1', '1', NULL, '', '1', '', NULL, NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.direcciones (id, primaria, numero, secundaria, piso, referencia, fijo, movil, ciudad, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (12, '1', '1', '1', NULL, '', '1', '', NULL, NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.direcciones (id, primaria, numero, secundaria, piso, referencia, fijo, movil, ciudad, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (14, 'Colón', '10 de Agosto', 'N23', '15', 'Tecnomega Edificio', '3030160', '0987328457', NULL, NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.direcciones (id, primaria, numero, secundaria, piso, referencia, fijo, movil, ciudad, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (8, 'OE13G', 'S/N', 'S38B', '1', 'Lote 102', '0987328457', '3030160', 'Quito', NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.direcciones (id, primaria, numero, secundaria, piso, referencia, fijo, movil, ciudad, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (15, 'América', 'Colón', 'N75', '7', 'Nada', '3030160', '0987328427', '', NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.direcciones (id, primaria, numero, secundaria, piso, referencia, fijo, movil, ciudad, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (16, '', '', '', '', '', '', '', '', NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.direcciones (id, primaria, numero, secundaria, piso, referencia, fijo, movil, ciudad, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (2, 'Vizcaya', 'N-24G', 'Pontevedra', 'Condominios del Sur 205', 'La Floresta', '322-7638', '0992529703', 'Quito', NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.direcciones (id, primaria, numero, secundaria, piso, referencia, fijo, movil, ciudad, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (9, 'machala', 'n-123', ' vaca de castro', '2', 'frente banco pichicha', '0995719800', '', '', NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.direcciones (id, primaria, numero, secundaria, piso, referencia, fijo, movil, ciudad, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (13, '1', '1', '1', '', '', '1', '', '', NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.direcciones (id, primaria, numero, secundaria, piso, referencia, fijo, movil, ciudad, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (19, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.direcciones (id, primaria, numero, secundaria, piso, referencia, fijo, movil, ciudad, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (20, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.direcciones (id, primaria, numero, secundaria, piso, referencia, fijo, movil, ciudad, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (21, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.direcciones (id, primaria, numero, secundaria, piso, referencia, fijo, movil, ciudad, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (22, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.direcciones (id, primaria, numero, secundaria, piso, referencia, fijo, movil, ciudad, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (23, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.direcciones (id, primaria, numero, secundaria, piso, referencia, fijo, movil, ciudad, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (24, 'Calle 1', 'N56', 'Calle 2', '', '', '3030160', '', 'Quito', NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.direcciones (id, primaria, numero, secundaria, piso, referencia, fijo, movil, ciudad, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (25, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.direcciones (id, primaria, numero, secundaria, piso, referencia, fijo, movil, ciudad, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (26, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.direcciones (id, primaria, numero, secundaria, piso, referencia, fijo, movil, ciudad, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (28, 'Av. Teniente Hugo Ortiz', 'OE5-11', 'Fray Francisco de San Miguel', '2º piso', 'Tribuna del Sur', '2662005', '098732885', 'Quito', NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.direcciones (id, primaria, numero, secundaria, piso, referencia, fijo, movil, ciudad, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (18, 'OE13G', 'S/N', 'S38B', '1º piso', 'Barrio Colinas del Sur', '3030160', '0987328457', 'Quito', NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.direcciones (id, primaria, numero, secundaria, piso, referencia, fijo, movil, ciudad, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (29, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.direcciones (id, primaria, numero, secundaria, piso, referencia, fijo, movil, ciudad, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (30, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.direcciones (id, primaria, numero, secundaria, piso, referencia, fijo, movil, ciudad, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (31, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.direcciones (id, primaria, numero, secundaria, piso, referencia, fijo, movil, ciudad, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (32, 'P000003', 'P000003', 'P000003', 'P000003', 'P000003', 'P000003', 'P000003', 'Quito', NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.direcciones (id, primaria, numero, secundaria, piso, referencia, fijo, movil, ciudad, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (33, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.direcciones (id, primaria, numero, secundaria, piso, referencia, fijo, movil, ciudad, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (34, '', '', '', '', '', '', '', 'Quito', NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.direcciones (id, primaria, numero, secundaria, piso, referencia, fijo, movil, ciudad, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (35, '', '', '', '', '', '', '', 'Quito', NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.direcciones (id, primaria, numero, secundaria, piso, referencia, fijo, movil, ciudad, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (36, '', '', '', '', '', '', '', 'Quito', NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.direcciones (id, primaria, numero, secundaria, piso, referencia, fijo, movil, ciudad, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (37, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'Quito', NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.direcciones (id, primaria, numero, secundaria, piso, referencia, fijo, movil, ciudad, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (38, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'Quito', NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.direcciones (id, primaria, numero, secundaria, piso, referencia, fijo, movil, ciudad, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (39, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'Quito', NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.direcciones (id, primaria, numero, secundaria, piso, referencia, fijo, movil, ciudad, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (40, '', '', '', '', '', '', '', 'Quito', NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.direcciones (id, primaria, numero, secundaria, piso, referencia, fijo, movil, ciudad, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (41, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'Quito', NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.direcciones (id, primaria, numero, secundaria, piso, referencia, fijo, movil, ciudad, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (42, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'Quito', NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.direcciones (id, primaria, numero, secundaria, piso, referencia, fijo, movil, ciudad, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (43, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'Quito', NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.direcciones (id, primaria, numero, secundaria, piso, referencia, fijo, movil, ciudad, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (44, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'Quito', NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.direcciones (id, primaria, numero, secundaria, piso, referencia, fijo, movil, ciudad, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (17, 'OE13G', 'S/N', 'S38B', '1º piso', 'Barrio Colinas del Sur', '3030160', '0987328457', 'QuitoG', NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.direcciones (id, primaria, numero, secundaria, piso, referencia, fijo, movil, ciudad, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (45, 'OE13G', 'S/N', 'S38B', 'Ordóñez', 'El Girón PB', '3030160', '0987328457', 'Quito', NULL, NULL, NULL, NULL, NULL, true);


--
-- TOC entry 3202 (class 0 OID 68355)
-- Dependencies: 208
-- Data for Name: formulas; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.formulas (id, atencion, lensometria, agudezavisualsincristal, agudezavisualconcristal, esfera, cilindro, eje, adicion, distanciapupilar, agudezavisual, material, altura, tratamiento, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (5, 20, '{"d": "", "i": ""}', '{"d": "", "i": ""}', '{"d": "", "i": ""}', '{"d": "", "i": "d"}', '{"d": "w", "i": "w"}', '{"d": "w", "i": "w"}', '{"d": "", "i": ""}', '{"d": "", "i": ""}', '{"d": "", "i": ""}', NULL, NULL, NULL, NULL, '2018-09-11 17:57:09.431', 'apvillalba', '2018-09-11 18:16:35.015', 'apvillalba', true);
INSERT INTO public.formulas (id, atencion, lensometria, agudezavisualsincristal, agudezavisualconcristal, esfera, cilindro, eje, adicion, distanciapupilar, agudezavisual, material, altura, tratamiento, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (6, 24, '{"d": "", "i": ""}', '{"d": "", "i": ""}', '{"d": "", "i": ""}', '{"d": "", "i": ""}', '{"d": "", "i": ""}', '{"d": "", "i": ""}', '{"d": "", "i": ""}', '{"d": "", "i": ""}', '{"d": "", "i": ""}', NULL, NULL, NULL, NULL, '2018-09-13 16:42:25.637', 'apvillalba', '2018-09-13 16:42:25.637', 'apvillalba', true);
INSERT INTO public.formulas (id, atencion, lensometria, agudezavisualsincristal, agudezavisualconcristal, esfera, cilindro, eje, adicion, distanciapupilar, agudezavisual, material, altura, tratamiento, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (9, 28, '{"d": " 1", "i": "4"}', '{"d": "2", "i": "5 "}', '{"d": "3", "i": "6"}', '{"d": "12", "i": "6"}', '{"d": "13", "i": "7"}', '{"d": "14", "i": "8"}', '{"d": "15", "i": "9"}', '{"d": "16", "i": "10"}', '{"d": "17", "i": "11"}', NULL, NULL, NULL, NULL, '2018-09-14 09:44:17.186', 'apvillalba', '2018-09-14 15:21:59.383', 'apvillalba', true);
INSERT INTO public.formulas (id, atencion, lensometria, agudezavisualsincristal, agudezavisualconcristal, esfera, cilindro, eje, adicion, distanciapupilar, agudezavisual, material, altura, tratamiento, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (10, 29, '{"d": "", "i": ""}', '{"d": "", "i": ""}', '{"d": "", "i": ""}', '{"d": "", "i": ""}', '{"d": "", "i": ""}', '{"d": "", "i": ""}', '{"d": "", "i": ""}', '{"d": "", "i": ""}', '{"d": "", "i": ""}', NULL, NULL, NULL, NULL, '2018-09-19 05:48:31.297', 'apvillalba', '2018-09-19 05:48:31.24', 'apvillalba', true);
INSERT INTO public.formulas (id, atencion, lensometria, agudezavisualsincristal, agudezavisualconcristal, esfera, cilindro, eje, adicion, distanciapupilar, agudezavisual, material, altura, tratamiento, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (11, 30, '{"d": "", "i": ""}', '{"d": "", "i": ""}', '{"d": "", "i": ""}', '{"d": "", "i": ""}', '{"d": "", "i": ""}', '{"d": "", "i": ""}', '{"d": "", "i": ""}', '{"d": "", "i": ""}', '{"d": "", "i": ""}', NULL, NULL, NULL, NULL, '2018-11-02 00:53:30.237', 'apvillalba', '2018-11-02 00:53:43.046', 'apvillalba', true);


--
-- TOC entry 3204 (class 0 OID 68363)
-- Dependencies: 210
-- Data for Name: horarios; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (2, 1, 2, 35, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (3, 1, 3, 35, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (4, 1, 4, 35, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (5, 1, 5, 35, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (6, 1, 6, 35, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (7, 1, 7, 35, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (8, 1, 8, 35, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (9, 1, 9, 35, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (10, 1, 10, 35, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (11, 1, 11, 35, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (12, 1, 12, 35, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (13, 1, 13, 35, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (14, 1, 14, 35, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (15, 1, 15, 35, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (16, 1, 16, 35, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (17, 1, 17, 35, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (18, 1, 18, 35, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (19, 1, 19, 35, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (20, 1, 20, 35, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (21, 1, 21, 35, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (22, 1, 22, 35, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (23, 1, 23, 35, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (24, 1, 24, 35, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (25, 1, 1, 36, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (26, 1, 2, 36, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (27, 1, 3, 36, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (28, 1, 4, 36, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (29, 1, 5, 36, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (30, 1, 6, 36, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (31, 1, 7, 36, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (32, 1, 8, 36, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (33, 1, 9, 36, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (34, 1, 10, 36, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (35, 1, 11, 36, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (36, 1, 12, 36, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (37, 1, 13, 36, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (38, 1, 14, 36, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (39, 1, 15, 36, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (40, 1, 16, 36, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (41, 1, 17, 36, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (42, 1, 18, 36, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (43, 1, 19, 36, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (44, 1, 20, 36, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (45, 1, 21, 36, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (46, 1, 22, 36, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (47, 1, 23, 36, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (48, 1, 24, 36, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (49, 1, 1, 37, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (50, 1, 2, 37, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (51, 1, 3, 37, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (52, 1, 4, 37, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (53, 1, 5, 37, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (54, 1, 6, 37, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (55, 1, 7, 37, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (56, 1, 8, 37, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (57, 1, 9, 37, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (58, 1, 10, 37, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (59, 1, 11, 37, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (60, 1, 12, 37, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (61, 1, 13, 37, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (62, 1, 14, 37, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (63, 1, 15, 37, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (64, 1, 16, 37, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (65, 1, 17, 37, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (66, 1, 18, 37, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (67, 1, 19, 37, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (68, 1, 20, 37, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (69, 1, 21, 37, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (70, 1, 22, 37, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (71, 1, 23, 37, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (72, 1, 24, 37, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (73, 1, 1, 38, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (74, 1, 2, 38, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (75, 1, 3, 38, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (76, 1, 4, 38, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (77, 1, 5, 38, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (78, 1, 6, 38, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (79, 1, 7, 38, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (80, 1, 8, 38, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (81, 1, 9, 38, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (82, 1, 10, 38, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (83, 1, 11, 38, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (84, 1, 12, 38, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (85, 1, 13, 38, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (86, 1, 14, 38, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (87, 1, 15, 38, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (88, 1, 16, 38, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (89, 1, 17, 38, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (90, 1, 18, 38, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (91, 1, 19, 38, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (92, 1, 20, 38, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (93, 1, 21, 38, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (94, 1, 22, 38, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (95, 1, 23, 38, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (96, 1, 24, 38, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (97, 1, 1, 39, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (98, 1, 2, 39, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (99, 1, 3, 39, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (100, 1, 4, 39, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (101, 1, 5, 39, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (102, 1, 6, 39, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (103, 1, 7, 39, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (104, 1, 8, 39, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (105, 1, 9, 39, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (106, 1, 10, 39, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (107, 1, 11, 39, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (108, 1, 12, 39, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (109, 1, 13, 39, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (110, 1, 14, 39, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (111, 1, 15, 39, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (112, 1, 16, 39, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (113, 1, 17, 39, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (114, 1, 18, 39, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (115, 1, 19, 39, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (116, 1, 20, 39, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (117, 1, 21, 39, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (118, 1, 22, 39, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (119, 1, 23, 39, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (120, 1, 24, 39, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (121, 1, 1, 40, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (122, 1, 2, 40, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (123, 1, 3, 40, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (124, 1, 4, 40, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (125, 1, 5, 40, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (126, 1, 6, 40, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (127, 1, 7, 40, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (128, 1, 8, 40, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (129, 1, 9, 40, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (130, 1, 10, 40, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (131, 1, 11, 40, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (132, 1, 12, 40, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (133, 1, 13, 40, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (134, 1, 14, 40, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (135, 1, 15, 40, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (136, 1, 16, 40, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (137, 1, 17, 40, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (138, 1, 18, 40, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (139, 1, 19, 40, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (140, 1, 20, 40, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (141, 1, 21, 40, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (142, 1, 22, 40, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (143, 1, 23, 40, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (144, 1, 24, 40, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (145, 1, 1, 41, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (146, 1, 2, 41, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (147, 1, 3, 41, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (148, 1, 4, 41, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (149, 1, 5, 41, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (150, 1, 6, 41, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (151, 1, 7, 41, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (152, 1, 8, 41, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (153, 1, 9, 41, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (154, 1, 10, 41, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (155, 1, 11, 41, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (156, 1, 12, 41, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (157, 1, 13, 41, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (158, 1, 14, 41, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (159, 1, 15, 41, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (160, 1, 16, 41, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (161, 1, 17, 41, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (162, 1, 18, 41, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (163, 1, 19, 41, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (164, 1, 20, 41, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (165, 1, 21, 41, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (166, 1, 22, 41, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (167, 1, 23, 41, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (168, 1, 24, 41, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (1, 1, 1, 35, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-15 11:38:00.693', 'imordonez', true);


--
-- TOC entry 3206 (class 0 OID 68371)
-- Dependencies: 212
-- Data for Name: horas; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.horas (id, nombre, horainicio, horafin, institucion, activo, descripcion, creado, creadopor, actualizado, actualizadopor, codigo) VALUES (2, '02º Hora', '01:00:00', '02:00:00', 1, true, '02º Hora de Atención', '2018-08-05 21:45:06.06436', 'root', '2018-08-05 21:45:06.06436', 'root', 'H02');
INSERT INTO public.horas (id, nombre, horainicio, horafin, institucion, activo, descripcion, creado, creadopor, actualizado, actualizadopor, codigo) VALUES (3, '03º Hora', '02:00:00', '03:00:00', 1, true, '03º Hora de Atención', '2018-08-05 21:45:06.06436', 'root', '2018-08-05 21:45:06.06436', 'root', 'H03');
INSERT INTO public.horas (id, nombre, horainicio, horafin, institucion, activo, descripcion, creado, creadopor, actualizado, actualizadopor, codigo) VALUES (7, '07º Hora', '06:00:00', '07:00:00', 1, true, '07º Hora de Atención', '2018-08-05 21:45:06.06436', 'root', '2018-08-05 21:45:06.06436', 'root', 'H07');
INSERT INTO public.horas (id, nombre, horainicio, horafin, institucion, activo, descripcion, creado, creadopor, actualizado, actualizadopor, codigo) VALUES (8, '08º Hora', '07:00:00', '08:00:00', 1, true, '08º Hora de Atención', '2018-08-05 21:45:06.06436', 'root', '2018-08-05 21:45:06.06436', 'root', 'H08');
INSERT INTO public.horas (id, nombre, horainicio, horafin, institucion, activo, descripcion, creado, creadopor, actualizado, actualizadopor, codigo) VALUES (9, '09º Hora', '08:00:00', '09:00:00', 1, true, '09º Hora de Atención', '2018-08-05 21:45:06.06436', 'root', '2018-08-05 21:45:06.06436', 'root', 'H09');
INSERT INTO public.horas (id, nombre, horainicio, horafin, institucion, activo, descripcion, creado, creadopor, actualizado, actualizadopor, codigo) VALUES (19, '19º Hora', '18:00:00', '19:00:00', 1, true, '19º Hora de Atención', '2018-08-05 21:45:06.06436', 'root', '2018-08-05 21:45:06.06436', 'root', 'H19');
INSERT INTO public.horas (id, nombre, horainicio, horafin, institucion, activo, descripcion, creado, creadopor, actualizado, actualizadopor, codigo) VALUES (10, '10º Hora', '09:00:00', '10:00:00', 1, true, '10º Hora de Atención', '2018-08-05 21:45:06.06436', 'root', '2018-08-05 21:45:06.06436', 'root', 'H10');
INSERT INTO public.horas (id, nombre, horainicio, horafin, institucion, activo, descripcion, creado, creadopor, actualizado, actualizadopor, codigo) VALUES (11, '11º Hora', '10:00:00', '11:00:00', 1, true, '11º Hora de Atención', '2018-08-05 21:45:06.06436', 'root', '2018-08-05 21:45:06.06436', 'root', 'H11');
INSERT INTO public.horas (id, nombre, horainicio, horafin, institucion, activo, descripcion, creado, creadopor, actualizado, actualizadopor, codigo) VALUES (4, '04º Hora', '03:00:00', '04:00:00', 1, true, '04º Hora de Atención', '2018-08-05 21:45:06.06436', 'root', '2018-08-05 21:45:06.06436', 'root', 'H04');
INSERT INTO public.horas (id, nombre, horainicio, horafin, institucion, activo, descripcion, creado, creadopor, actualizado, actualizadopor, codigo) VALUES (5, '05º Hora', '04:00:00', '05:00:00', 1, true, '05º Hora de Atención', '2018-08-05 21:45:06.06436', 'root', '2018-08-05 21:45:06.06436', 'root', 'H05');
INSERT INTO public.horas (id, nombre, horainicio, horafin, institucion, activo, descripcion, creado, creadopor, actualizado, actualizadopor, codigo) VALUES (6, '06º Hora', '05:00:00', '06:00:00', 1, true, '06º Hora de Atención', '2018-08-05 21:45:06.06436', 'root', '2018-08-05 21:45:06.06436', 'root', 'H06');
INSERT INTO public.horas (id, nombre, horainicio, horafin, institucion, activo, descripcion, creado, creadopor, actualizado, actualizadopor, codigo) VALUES (12, '12º Hora', '11:00:00', '12:00:00', 1, true, '12º Hora de Atención', '2018-08-05 21:45:06.06436', 'root', '2018-08-05 21:45:06.06436', 'root', 'H12');
INSERT INTO public.horas (id, nombre, horainicio, horafin, institucion, activo, descripcion, creado, creadopor, actualizado, actualizadopor, codigo) VALUES (13, '13º Hora', '12:00:00', '13:00:00', 1, true, '13º Hora de Atención', '2018-08-05 21:45:06.06436', 'root', '2018-08-05 21:45:06.06436', 'root', 'H13');
INSERT INTO public.horas (id, nombre, horainicio, horafin, institucion, activo, descripcion, creado, creadopor, actualizado, actualizadopor, codigo) VALUES (14, '14º Hora', '13:00:00', '14:00:00', 1, true, '14º Hora de Atención', '2018-08-05 21:45:06.06436', 'root', '2018-08-05 21:45:06.06436', 'root', 'H14');
INSERT INTO public.horas (id, nombre, horainicio, horafin, institucion, activo, descripcion, creado, creadopor, actualizado, actualizadopor, codigo) VALUES (15, '15º Hora', '14:00:00', '15:00:00', 1, true, '15º Hora de Atención', '2018-08-05 21:45:06.06436', 'root', '2018-08-05 21:45:06.06436', 'root', 'H15');
INSERT INTO public.horas (id, nombre, horainicio, horafin, institucion, activo, descripcion, creado, creadopor, actualizado, actualizadopor, codigo) VALUES (16, '16º Hora', '15:00:00', '16:00:00', 1, true, '16º Hora de Atención', '2018-08-05 21:45:06.06436', 'root', '2018-08-05 21:45:06.06436', 'root', 'H16');
INSERT INTO public.horas (id, nombre, horainicio, horafin, institucion, activo, descripcion, creado, creadopor, actualizado, actualizadopor, codigo) VALUES (17, '17º Hora', '16:00:00', '17:00:00', 1, true, '17º Hora de Atención', '2018-08-05 21:45:06.06436', 'root', '2018-08-05 21:45:06.06436', 'root', 'H17');
INSERT INTO public.horas (id, nombre, horainicio, horafin, institucion, activo, descripcion, creado, creadopor, actualizado, actualizadopor, codigo) VALUES (18, '18º Hora', '17:00:00', '18:00:00', 1, true, '18º Hora de Atención', '2018-08-05 21:45:06.06436', 'root', '2018-08-05 21:45:06.06436', 'root', 'H18');
INSERT INTO public.horas (id, nombre, horainicio, horafin, institucion, activo, descripcion, creado, creadopor, actualizado, actualizadopor, codigo) VALUES (20, '20º Hora', '19:00:00', '20:00:00', 1, true, '20º Hora de Atención', '2018-08-05 21:45:06.06436', 'root', '2018-08-05 21:45:06.06436', 'root', 'H20');
INSERT INTO public.horas (id, nombre, horainicio, horafin, institucion, activo, descripcion, creado, creadopor, actualizado, actualizadopor, codigo) VALUES (21, '21º Hora', '20:00:00', '21:00:00', 1, true, '21º Hora de Atención', '2018-08-05 21:45:06.06436', 'root', '2018-08-05 21:45:06.06436', 'root', 'H21');
INSERT INTO public.horas (id, nombre, horainicio, horafin, institucion, activo, descripcion, creado, creadopor, actualizado, actualizadopor, codigo) VALUES (22, '22º Hora', '21:00:00', '22:00:00', 1, true, '22º Hora de Atención', '2018-08-05 21:45:06.06436', 'root', '2018-08-05 21:45:06.06436', 'root', 'H22');
INSERT INTO public.horas (id, nombre, horainicio, horafin, institucion, activo, descripcion, creado, creadopor, actualizado, actualizadopor, codigo) VALUES (23, '23º Hora', '22:00:00', '23:00:00', 1, true, '23º Hora de Atención', '2018-08-05 21:45:06.06436', 'root', '2018-08-05 21:45:06.06436', 'root', 'H23');
INSERT INTO public.horas (id, nombre, horainicio, horafin, institucion, activo, descripcion, creado, creadopor, actualizado, actualizadopor, codigo) VALUES (24, '24º Hora', '23:00:00', '00:00:00', 1, true, '24º Hora de Atención', '2018-08-05 21:45:06.06436', 'root', '2018-08-05 21:45:06.06436', 'root', 'H24');
INSERT INTO public.horas (id, nombre, horainicio, horafin, institucion, activo, descripcion, creado, creadopor, actualizado, actualizadopor, codigo) VALUES (1, '01º Hora', '00:00:00', '01:00:00', 1, true, '01º Hora de Atención', '2018-08-05 21:45:06.06436', 'root', '2018-08-12 06:49:51.265', 'imordonez', 'H01');


--
-- TOC entry 3208 (class 0 OID 68379)
-- Dependencies: 214
-- Data for Name: instituciones; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.instituciones (id, nombre, fecha, direccion, logotipo, email, web, laboratorio, activo, descripcion, creado, creadopor, actualizado, actualizadopor) VALUES (1, 'Salud Integral para Todos', '2006-05-10', 2, 7, 'fvptfloresta@hotmail.com', 'https://www.facebook.com/saludintegralfloresta', false, true, '', NULL, NULL, NULL, NULL);
INSERT INTO public.instituciones (id, nombre, fecha, direccion, logotipo, email, web, laboratorio, activo, descripcion, creado, creadopor, actualizado, actualizadopor) VALUES (4, 'Iess', '2018-07-13', 24, 22, 'iess@iess.com.ec', 'www.iess.com', true, true, '', NULL, NULL, NULL, NULL);


--
-- TOC entry 3210 (class 0 OID 68387)
-- Dependencies: 216
-- Data for Name: maestros; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.maestros (id, codigo, nombre, activo, descripcion, creado, creadopor, actualizado, actualizadopor) VALUES (4, 'PG', 'Parámetros Generales', true, '', '2017-07-11 08:07:24.181', NULL, '2018-07-11 10:27:43.816', 'root');
INSERT INTO public.maestros (id, codigo, nombre, activo, descripcion, creado, creadopor, actualizado, actualizadopor) VALUES (2, 'GRPUSR', 'Grupos de Usuarios', true, NULL, '2017-07-11 08:07:24.181', NULL, '2018-05-11 08:07:24.181', NULL);
INSERT INTO public.maestros (id, codigo, nombre, activo, descripcion, creado, creadopor, actualizado, actualizadopor) VALUES (7, 'TTR', 'Tipo de Tratamiento', true, NULL, '2017-07-11 08:07:24.181', NULL, '2018-05-11 08:07:24.181', NULL);
INSERT INTO public.maestros (id, codigo, nombre, activo, descripcion, creado, creadopor, actualizado, actualizadopor) VALUES (6, 'TMT', 'Tipo de Material', true, NULL, '2017-07-11 08:07:24.181', NULL, '2018-05-11 08:07:24.181', NULL);
INSERT INTO public.maestros (id, codigo, nombre, activo, descripcion, creado, creadopor, actualizado, actualizadopor) VALUES (5, 'TF', 'Tipo de Foco', true, NULL, '2017-07-11 08:07:24.181', NULL, '2018-05-11 08:07:24.181', NULL);
INSERT INTO public.maestros (id, codigo, nombre, activo, descripcion, creado, creadopor, actualizado, actualizadopor) VALUES (3, 'GHU', 'Género Humano', true, '', '2017-07-11 08:07:24.181', NULL, '2018-05-11 08:07:24.181', NULL);
INSERT INTO public.maestros (id, codigo, nombre, activo, descripcion, creado, creadopor, actualizado, actualizadopor) VALUES (1, 'MDS', 'Módulos del Sistema', true, '', '2017-07-11 08:07:24.181', NULL, '2018-05-11 08:07:24.181', NULL);
INSERT INTO public.maestros (id, codigo, nombre, activo, descripcion, creado, creadopor, actualizado, actualizadopor) VALUES (9, 'ESP', 'Especialidades', true, '', '2018-07-24 15:53:57.918', 'root', '2018-07-24 15:53:57.918', 'root');
INSERT INTO public.maestros (id, codigo, nombre, activo, descripcion, creado, creadopor, actualizado, actualizadopor) VALUES (10, 'DS', 'Días de la Semana', true, '', '2018-07-24 15:56:37.326', 'root', '2018-07-24 15:56:37.326', 'root');
INSERT INTO public.maestros (id, codigo, nombre, activo, descripcion, creado, creadopor, actualizado, actualizadopor) VALUES (11, 'CBP', 'Claves de búsqueda de pacientes', true, 'Claves de búsqueda de pacientes', '2018-07-27 15:00:50.944', 'root', '2018-07-27 15:00:50.944', 'root');
INSERT INTO public.maestros (id, codigo, nombre, activo, descripcion, creado, creadopor, actualizado, actualizadopor) VALUES (12, 'TD', 'Tipos de Datos', true, 'Tipos de Datos', '2018-08-23 10:57:05.669', 'root', '2018-08-23 10:57:05.669', 'root');
INSERT INTO public.maestros (id, codigo, nombre, activo, descripcion, creado, creadopor, actualizado, actualizadopor) VALUES (13, 'GD', 'Grupos de Datos', true, 'Grupos de Datos', '2018-08-23 10:57:24.379', 'root', '2018-08-23 10:57:24.379', 'root');
INSERT INTO public.maestros (id, codigo, nombre, activo, descripcion, creado, creadopor, actualizado, actualizadopor) VALUES (8, 'M001', 'Maestro ', true, 'Maestro de pruebas', '2017-07-11 08:07:24.181', NULL, '2018-11-02 17:49:22.253', 'root');


--
-- TOC entry 3212 (class 0 OID 68395)
-- Dependencies: 218
-- Data for Name: materiales; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.materiales (id, nombre, tipo, activo, foco, descripcion, creado, creadopor, actualizado, actualizadopor, codigo) VALUES (3, 'F/TPHOTOGRAY', 29, true, 27, '', NULL, NULL, '2018-07-13 13:38:08.17', 'root', 'M004');
INSERT INTO public.materiales (id, nombre, tipo, activo, foco, descripcion, creado, creadopor, actualizado, actualizadopor, codigo) VALUES (4, 'Material 003', 30, true, 28, '', '2018-07-13 13:37:50.767', 'root', '2018-07-13 13:40:29.612', 'root', 'M003');
INSERT INTO public.materiales (id, nombre, tipo, activo, foco, descripcion, creado, creadopor, actualizado, actualizadopor, codigo) VALUES (1, 'Airwear blanco', 29, true, 27, '', NULL, NULL, '2018-07-13 13:40:59.024', 'root', 'M001');


--
-- TOC entry 3214 (class 0 OID 68403)
-- Dependencies: 220
-- Data for Name: menus; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.menus (id, nombre, formulario, menupadre, modulo, descripcion, creado, creadopor, actualizado, actualizadopor, activo, codigo, icono) VALUES (12, '09. Materiales', 'Materiales', 1, NULL, NULL, NULL, NULL, NULL, NULL, true, NULL, NULL);
INSERT INTO public.menus (id, nombre, formulario, menupadre, modulo, descripcion, creado, creadopor, actualizado, actualizadopor, activo, codigo, icono) VALUES (11, '08. Instituciones', 'Instituciones', 1, NULL, NULL, NULL, NULL, NULL, NULL, true, NULL, NULL);
INSERT INTO public.menus (id, nombre, formulario, menupadre, modulo, descripcion, creado, creadopor, actualizado, actualizadopor, activo, codigo, icono) VALUES (13, '01. Consultas', 'Consultas', 2, NULL, NULL, NULL, NULL, NULL, NULL, true, NULL, NULL);
INSERT INTO public.menus (id, nombre, formulario, menupadre, modulo, descripcion, creado, creadopor, actualizado, actualizadopor, activo, codigo, icono) VALUES (4, '01. Maestros', 'Maestros', 1, NULL, NULL, NULL, NULL, NULL, NULL, true, NULL, NULL);
INSERT INTO public.menus (id, nombre, formulario, menupadre, modulo, descripcion, creado, creadopor, actualizado, actualizadopor, activo, codigo, icono) VALUES (6, '03. Menús', 'Menus', 1, NULL, NULL, NULL, NULL, NULL, NULL, true, NULL, NULL);
INSERT INTO public.menus (id, nombre, formulario, menupadre, modulo, descripcion, creado, creadopor, actualizado, actualizadopor, activo, codigo, icono) VALUES (8, '05. Personas', 'Personas', 1, NULL, NULL, NULL, NULL, NULL, NULL, true, NULL, NULL);
INSERT INTO public.menus (id, nombre, formulario, menupadre, modulo, descripcion, creado, creadopor, actualizado, actualizadopor, activo, codigo, icono) VALUES (10, '07. Perfiles', 'Perfiles', 1, NULL, NULL, NULL, NULL, NULL, NULL, true, NULL, NULL);
INSERT INTO public.menus (id, nombre, formulario, menupadre, modulo, descripcion, creado, creadopor, actualizado, actualizadopor, activo, codigo, icono) VALUES (9, '06. Usuarios', 'Usuarios', 1, NULL, NULL, NULL, NULL, NULL, NULL, true, NULL, NULL);
INSERT INTO public.menus (id, nombre, formulario, menupadre, modulo, descripcion, creado, creadopor, actualizado, actualizadopor, activo, codigo, icono) VALUES (14, '02. Ordenes de Laboratorio', 'Ordenes', 2, NULL, NULL, NULL, NULL, NULL, NULL, true, NULL, NULL);
INSERT INTO public.menus (id, nombre, formulario, menupadre, modulo, descripcion, creado, creadopor, actualizado, actualizadopor, activo, codigo, icono) VALUES (5, '02.Parámetros', 'Parametros', 1, NULL, NULL, NULL, NULL, NULL, NULL, true, NULL, NULL);
INSERT INTO public.menus (id, nombre, formulario, menupadre, modulo, descripcion, creado, creadopor, actualizado, actualizadopor, activo, codigo, icono) VALUES (7, '04. Submenús', 'Submenus', 1, NULL, NULL, NULL, NULL, NULL, NULL, true, NULL, NULL);
INSERT INTO public.menus (id, nombre, formulario, menupadre, modulo, descripcion, creado, creadopor, actualizado, actualizadopor, activo, codigo, icono) VALUES (1, 'Mantenimiento', NULL, NULL, 1, '', NULL, NULL, '2018-08-14 00:14:24.211', 'root', true, '01', 'ui-icon ui-icon-contact');
INSERT INTO public.menus (id, nombre, formulario, menupadre, modulo, descripcion, creado, creadopor, actualizado, actualizadopor, activo, codigo, icono) VALUES (2, 'Transacciones', NULL, NULL, 1, '', NULL, NULL, '2018-08-14 00:14:30.457', 'root', true, '02', 'ui-icon ui-icon-mail-closed');
INSERT INTO public.menus (id, nombre, formulario, menupadre, modulo, descripcion, creado, creadopor, actualizado, actualizadopor, activo, codigo, icono) VALUES (3, 'Reportes', NULL, NULL, 1, '', NULL, NULL, '2018-08-14 00:14:37.724', 'root', true, '03', 'ui-icon ui-icon-script');
INSERT INTO public.menus (id, nombre, formulario, menupadre, modulo, descripcion, creado, creadopor, actualizado, actualizadopor, activo, codigo, icono) VALUES (15, 'Mantenimiento', NULL, NULL, 2, '', NULL, NULL, '2018-08-14 00:14:46.717', 'root', true, '01', 'ui-icon ui-icon-folder-collapsed');
INSERT INTO public.menus (id, nombre, formulario, menupadre, modulo, descripcion, creado, creadopor, actualizado, actualizadopor, activo, codigo, icono) VALUES (16, 'Transacciones', NULL, NULL, 2, '', NULL, NULL, '2018-08-14 00:14:54.74', 'root', true, '02', 'ui-icon ui-icon-note');
INSERT INTO public.menus (id, nombre, formulario, menupadre, modulo, descripcion, creado, creadopor, actualizado, actualizadopor, activo, codigo, icono) VALUES (17, 'Reportes', NULL, NULL, 2, '', NULL, NULL, '2018-08-14 00:15:02.418', 'root', true, '03', 'ui-icon ui-icon-calendar');
INSERT INTO public.menus (id, nombre, formulario, menupadre, modulo, descripcion, creado, creadopor, actualizado, actualizadopor, activo, codigo, icono) VALUES (21, 'Profesionales', 'Profesionales', 15, NULL, '', '2018-07-18 11:31:02.177', 'root', '2018-08-14 00:17:42.999', 'root', true, '01', 'ui-icon ui-icon-person');
INSERT INTO public.menus (id, nombre, formulario, menupadre, modulo, descripcion, creado, creadopor, actualizado, actualizadopor, activo, codigo, icono) VALUES (18, 'Pacientes', 'Pacientes', 15, NULL, '', NULL, NULL, '2018-08-14 00:18:13.001', 'root', true, '02', 'ui-icon ui-icon-person');
INSERT INTO public.menus (id, nombre, formulario, menupadre, modulo, descripcion, creado, creadopor, actualizado, actualizadopor, activo, codigo, icono) VALUES (22, 'Horas', 'Horas', 15, NULL, 'Horas de Atención', '2018-07-24 14:01:30.82', 'root', '2018-08-14 00:18:40.794', 'root', true, '03', 'ui-icon ui-icon-calendar');
INSERT INTO public.menus (id, nombre, formulario, menupadre, modulo, descripcion, creado, creadopor, actualizado, actualizadopor, activo, codigo, icono) VALUES (23, 'Horarios', 'Horarios', 15, NULL, 'Horario de Atención Médica', '2018-07-24 14:02:23.049', 'root', '2018-08-14 00:18:51.46', 'root', true, '04', 'ui-icon ui-icon-calendar');
INSERT INTO public.menus (id, nombre, formulario, menupadre, modulo, descripcion, creado, creadopor, actualizado, actualizadopor, activo, codigo, icono) VALUES (20, 'Órdenes de Laboratorio', 'Ordenes', 16, NULL, '', NULL, NULL, '2018-08-14 00:19:30.166', 'root', true, '02', 'ui-icon ui-icon-suitcase');
INSERT INTO public.menus (id, nombre, formulario, menupadre, modulo, descripcion, creado, creadopor, actualizado, actualizadopor, activo, codigo, icono) VALUES (24, 'Citas', 'Citas', 16, NULL, 'Citas Médicas', '2018-08-02 08:13:39.97', 'root', '2018-08-14 00:19:52.22', 'root', true, '01', 'ui-icon ui-icon-contact');
INSERT INTO public.menus (id, nombre, formulario, menupadre, modulo, descripcion, creado, creadopor, actualizado, actualizadopor, activo, codigo, icono) VALUES (25, 'Historial', 'Historial', 3, NULL, 'Historial de cambios', '2018-08-16 10:16:49.267', 'root', '2018-08-16 10:16:49.267', 'root', true, '01', 'ui-icon ui-icon-script');
INSERT INTO public.menus (id, nombre, formulario, menupadre, modulo, descripcion, creado, creadopor, actualizado, actualizadopor, activo, codigo, icono) VALUES (26, 'Campos', 'Campos', 15, NULL, 'Campos de Datos', '2018-08-23 10:26:00.164', 'root', '2018-08-30 10:05:22.499', 'root', true, '04', 'ui-icon ui-icon-contact');
INSERT INTO public.menus (id, nombre, formulario, menupadre, modulo, descripcion, creado, creadopor, actualizado, actualizadopor, activo, codigo, icono) VALUES (19, 'Atenciones', 'Atenciones', 16, NULL, 'Atención a Pacientes', NULL, NULL, '2018-08-30 10:06:04.442', 'root', true, '03', 'ui-icon ui-icon-bookmark');


--
-- TOC entry 3216 (class 0 OID 68411)
-- Dependencies: 222
-- Data for Name: ordenes; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.ordenes (id, formula, factura, laboratorio, registro, envio, entrega, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (1, 6, NULL, 4, '2018-09-13 16:53:46.589', NULL, NULL, NULL, '2018-09-13 16:53:46.589', 'apvillalba', '2018-09-13 16:42:25.637', 'apvillalba', true);
INSERT INTO public.ordenes (id, formula, factura, laboratorio, registro, envio, entrega, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (4, 9, NULL, 4, '2018-09-14 09:44:33.778', NULL, NULL, NULL, '2018-09-14 09:44:33.778', 'apvillalba', '2018-09-14 09:44:17.179', 'apvillalba', true);


--
-- TOC entry 3218 (class 0 OID 68419)
-- Dependencies: 224
-- Data for Name: pacientes; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.pacientes (id, persona, institucion, activo, descripcion, creado, creadopor, actualizado, actualizadopor, fotografia) VALUES (12, 5, 1, true, 'Administradora de Empresas', NULL, NULL, NULL, NULL, NULL);
INSERT INTO public.pacientes (id, persona, institucion, activo, descripcion, creado, creadopor, actualizado, actualizadopor, fotografia) VALUES (11, 1, 1, false, '', NULL, NULL, NULL, NULL, NULL);
INSERT INTO public.pacientes (id, persona, institucion, activo, descripcion, creado, creadopor, actualizado, actualizadopor, fotografia) VALUES (13, 19, 1, true, '', NULL, NULL, NULL, NULL, NULL);
INSERT INTO public.pacientes (id, persona, institucion, activo, descripcion, creado, creadopor, actualizado, actualizadopor, fotografia) VALUES (14, 17, 1, true, '', NULL, NULL, NULL, NULL, NULL);
INSERT INTO public.pacientes (id, persona, institucion, activo, descripcion, creado, creadopor, actualizado, actualizadopor, fotografia) VALUES (15, 21, 1, true, 'P000005', NULL, NULL, NULL, NULL, NULL);
INSERT INTO public.pacientes (id, persona, institucion, activo, descripcion, creado, creadopor, actualizado, actualizadopor, fotografia) VALUES (16, 22, 1, true, 'P000006', NULL, NULL, NULL, NULL, NULL);
INSERT INTO public.pacientes (id, persona, institucion, activo, descripcion, creado, creadopor, actualizado, actualizadopor, fotografia) VALUES (17, 23, 1, true, 'P000007', NULL, NULL, NULL, NULL, NULL);
INSERT INTO public.pacientes (id, persona, institucion, activo, descripcion, creado, creadopor, actualizado, actualizadopor, fotografia) VALUES (18, 24, 1, true, 'P000008', NULL, NULL, NULL, NULL, NULL);
INSERT INTO public.pacientes (id, persona, institucion, activo, descripcion, creado, creadopor, actualizado, actualizadopor, fotografia) VALUES (19, 25, 1, true, 'P000009', NULL, NULL, NULL, NULL, NULL);
INSERT INTO public.pacientes (id, persona, institucion, activo, descripcion, creado, creadopor, actualizado, actualizadopor, fotografia) VALUES (20, 26, 1, true, 'P000010', NULL, NULL, NULL, NULL, NULL);
INSERT INTO public.pacientes (id, persona, institucion, activo, descripcion, creado, creadopor, actualizado, actualizadopor, fotografia) VALUES (21, 27, 1, true, 'P000011', NULL, NULL, NULL, NULL, NULL);
INSERT INTO public.pacientes (id, persona, institucion, activo, descripcion, creado, creadopor, actualizado, actualizadopor, fotografia) VALUES (22, 28, 1, true, 'P000012', NULL, NULL, NULL, NULL, NULL);
INSERT INTO public.pacientes (id, persona, institucion, activo, descripcion, creado, creadopor, actualizado, actualizadopor, fotografia) VALUES (23, 29, 1, true, 'P000013', NULL, NULL, NULL, NULL, NULL);
INSERT INTO public.pacientes (id, persona, institucion, activo, descripcion, creado, creadopor, actualizado, actualizadopor, fotografia) VALUES (24, 30, 1, true, 'P000014', NULL, NULL, NULL, NULL, NULL);
INSERT INTO public.pacientes (id, persona, institucion, activo, descripcion, creado, creadopor, actualizado, actualizadopor, fotografia) VALUES (10, 3, 1, true, '', NULL, NULL, NULL, NULL, 59);
INSERT INTO public.pacientes (id, persona, institucion, activo, descripcion, creado, creadopor, actualizado, actualizadopor, fotografia) VALUES (25, 32, 1, true, 'Es un gato dormilón', NULL, NULL, NULL, NULL, 62);


--
-- TOC entry 3220 (class 0 OID 68427)
-- Dependencies: 226
-- Data for Name: parametros; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.parametros (id, maestro, nombre, codigo, descripcion, parametros, activo, creado, creadopor, actualizado, actualizadopor) VALUES (11, 4, 'Institución Predeterminada', 'INSP', 'De acuerdo a éste parámetro (id de la Institución) el nombre y el logotipo del Sistema se ajustarán en los espacios Predeterminados', '1', true, NULL, NULL, NULL, NULL);
INSERT INTO public.parametros (id, maestro, nombre, codigo, descripcion, parametros, activo, creado, creadopor, actualizado, actualizadopor) VALUES (4, 2, 'Super administradores', 'GSA', 'Grupo de Super Administradores', NULL, true, NULL, NULL, NULL, NULL);
INSERT INTO public.parametros (id, maestro, nombre, codigo, descripcion, parametros, activo, creado, creadopor, actualizado, actualizadopor) VALUES (3, 1, 'Externo', 'MTE', 'Módulo de Trasacciones Externas', '/externo/Transacciones', true, NULL, NULL, NULL, NULL);
INSERT INTO public.parametros (id, maestro, nombre, codigo, descripcion, parametros, activo, creado, creadopor, actualizado, actualizadopor) VALUES (6, 2, 'Recepcionistas', 'GR', 'Grupo de Recepcionistas', NULL, true, NULL, NULL, NULL, NULL);
INSERT INTO public.parametros (id, maestro, nombre, codigo, descripcion, parametros, activo, creado, creadopor, actualizado, actualizadopor) VALUES (10, 3, 'Masculino', 'M', 'Género Maculino', NULL, true, NULL, NULL, NULL, NULL);
INSERT INTO public.parametros (id, maestro, nombre, codigo, descripcion, parametros, activo, creado, creadopor, actualizado, actualizadopor) VALUES (9, 3, 'Femenino', 'F', 'Género Femenino', NULL, true, NULL, NULL, NULL, NULL);
INSERT INTO public.parametros (id, maestro, nombre, codigo, descripcion, parametros, activo, creado, creadopor, actualizado, actualizadopor) VALUES (2, 1, 'Transaccional', 'MTI', 'Módulo de Transacciones Internas', '/Interno/Transacciones', true, NULL, NULL, NULL, NULL);
INSERT INTO public.parametros (id, maestro, nombre, codigo, descripcion, parametros, activo, creado, creadopor, actualizado, actualizadopor) VALUES (1, 1, 'Seguridad', 'MSP', 'Módulo de Seguridad', '/Seguridad/Seguridad', true, NULL, NULL, NULL, NULL);
INSERT INTO public.parametros (id, maestro, nombre, codigo, descripcion, parametros, activo, creado, creadopor, actualizado, actualizadopor) VALUES (12, 4, 'Directorio de Archivos', 'DARCH', 'Directorio de Archivos', '/home/usuario/Imágenes/salutem', true, NULL, NULL, NULL, NULL);
INSERT INTO public.parametros (id, maestro, nombre, codigo, descripcion, parametros, activo, creado, creadopor, actualizado, actualizadopor) VALUES (8, 2, 'Pacientes', 'GP', 'Grupo de Pacientes', '', true, NULL, NULL, '2018-07-10 18:47:44.487', 'root');
INSERT INTO public.parametros (id, maestro, nombre, codigo, descripcion, parametros, activo, creado, creadopor, actualizado, actualizadopor) VALUES (5, 2, 'Administradores', 'GA', 'Grupo de Administradores', '', true, NULL, NULL, '2018-07-10 18:51:18.699', 'root');
INSERT INTO public.parametros (id, maestro, nombre, codigo, descripcion, parametros, activo, creado, creadopor, actualizado, actualizadopor) VALUES (7, 2, 'Médicos', 'GM', 'Grupo de Médicos', '', true, NULL, NULL, '2018-07-10 18:51:23.275', 'root');
INSERT INTO public.parametros (id, maestro, nombre, codigo, descripcion, parametros, activo, creado, creadopor, actualizado, actualizadopor) VALUES (41, 10, 'Domingo', 'D', 'Domingo', '7', true, '2018-07-24 15:59:20.627', 'root', '2018-08-02 08:51:55.341', 'root');
INSERT INTO public.parametros (id, maestro, nombre, codigo, descripcion, parametros, activo, creado, creadopor, actualizado, actualizadopor) VALUES (27, 5, 'Foco 001', 'F001', 'Foco 001', '', true, '2018-07-13 12:00:37.895', 'root', '2018-07-13 12:00:37.895', 'root');
INSERT INTO public.parametros (id, maestro, nombre, codigo, descripcion, parametros, activo, creado, creadopor, actualizado, actualizadopor) VALUES (28, 5, 'Foco 2', 'F002', 'Foco 2', '', true, '2018-07-13 12:00:54.337', 'root', '2018-07-13 12:00:54.337', 'root');
INSERT INTO public.parametros (id, maestro, nombre, codigo, descripcion, parametros, activo, creado, creadopor, actualizado, actualizadopor) VALUES (29, 6, 'Material 1', 'M001', 'Material 1', '', true, '2018-07-13 12:01:09.779', 'root', '2018-07-13 12:01:09.779', 'root');
INSERT INTO public.parametros (id, maestro, nombre, codigo, descripcion, parametros, activo, creado, creadopor, actualizado, actualizadopor) VALUES (30, 6, 'Material 2', 'M002', 'Material 2', '', true, '2018-07-13 12:01:30.583', 'root', '2018-07-13 12:01:30.583', 'root');
INSERT INTO public.parametros (id, maestro, nombre, codigo, descripcion, parametros, activo, creado, creadopor, actualizado, actualizadopor) VALUES (31, 9, 'Medicina General', 'MG', 'Medicina General', '', true, '2018-07-24 15:54:28.921', 'root', '2018-07-24 15:54:28.921', 'root');
INSERT INTO public.parametros (id, maestro, nombre, codigo, descripcion, parametros, activo, creado, creadopor, actualizado, actualizadopor) VALUES (32, 9, 'Odontología', 'ODT', 'Odontología', '', true, '2018-07-24 15:54:46.406', 'root', '2018-07-24 15:54:46.406', 'root');
INSERT INTO public.parametros (id, maestro, nombre, codigo, descripcion, parametros, activo, creado, creadopor, actualizado, actualizadopor) VALUES (33, 9, 'Optometría', 'OPT', 'Optometría', '', true, '2018-07-24 15:55:07.079', 'root', '2018-07-24 15:55:07.079', 'root');
INSERT INTO public.parametros (id, maestro, nombre, codigo, descripcion, parametros, activo, creado, creadopor, actualizado, actualizadopor) VALUES (34, 9, 'Ginecología', 'GCL', 'Ginecología', '', true, '2018-07-24 15:55:38.724', 'root', '2018-07-24 15:55:38.724', 'root');
INSERT INTO public.parametros (id, maestro, nombre, codigo, descripcion, parametros, activo, creado, creadopor, actualizado, actualizadopor) VALUES (26, 4, 'Formato de fecha', 'FTF', 'Formato de fecha del sistema', 'EEEE, dd ''de'' MMMMM ''de'' yyyy', true, '2018-07-11 08:14:10.521', 'root', '2018-07-24 16:18:42.886', 'root');
INSERT INTO public.parametros (id, maestro, nombre, codigo, descripcion, parametros, activo, creado, creadopor, actualizado, actualizadopor) VALUES (25, 4, 'Formato de fecha y hora', 'FFH', 'Formato de fecha y hora del Sistema', 'EEEE, dd ''de'' MMMMM ''de'' yyyy hh:mm:ss', true, '2018-07-11 08:12:46.371', 'root', '2018-07-24 16:19:02.293', 'root');
INSERT INTO public.parametros (id, maestro, nombre, codigo, descripcion, parametros, activo, creado, creadopor, actualizado, actualizadopor) VALUES (35, 10, 'Lunes', 'L', 'Lunes', '1', true, '2018-07-24 15:56:57.423', 'root', '2018-08-02 08:51:14.309', 'root');
INSERT INTO public.parametros (id, maestro, nombre, codigo, descripcion, parametros, activo, creado, creadopor, actualizado, actualizadopor) VALUES (36, 10, 'Martes', 'M', 'Martes', '2', true, '2018-07-24 15:57:21.57', 'root', '2018-08-02 08:51:20.039', 'root');
INSERT INTO public.parametros (id, maestro, nombre, codigo, descripcion, parametros, activo, creado, creadopor, actualizado, actualizadopor) VALUES (37, 10, 'Miércoles', 'X', 'Miércoles', '3', true, '2018-07-24 15:57:40.284', 'root', '2018-08-02 08:51:34.458', 'root');
INSERT INTO public.parametros (id, maestro, nombre, codigo, descripcion, parametros, activo, creado, creadopor, actualizado, actualizadopor) VALUES (38, 10, 'Jueves', 'J', 'Jueves', '4', true, '2018-07-24 15:57:54.795', 'root', '2018-08-02 08:51:39.381', 'root');
INSERT INTO public.parametros (id, maestro, nombre, codigo, descripcion, parametros, activo, creado, creadopor, actualizado, actualizadopor) VALUES (39, 10, 'Viernes', 'V', 'Viernes', '5', true, '2018-07-24 15:58:12.296', 'root', '2018-08-02 08:51:44.639', 'root');
INSERT INTO public.parametros (id, maestro, nombre, codigo, descripcion, parametros, activo, creado, creadopor, actualizado, actualizadopor) VALUES (40, 10, 'Sábado', 'S', 'Sábado', '6', true, '2018-07-24 15:59:09.493', 'root', '2018-08-02 08:51:50.346', 'root');
INSERT INTO public.parametros (id, maestro, nombre, codigo, descripcion, parametros, activo, creado, creadopor, actualizado, actualizadopor) VALUES (45, 8, 'P001', 'P001', 'P001', '', true, '2018-08-12 07:09:37.429', 'root', '2018-08-12 07:09:37.429', 'root');
INSERT INTO public.parametros (id, maestro, nombre, codigo, descripcion, parametros, activo, creado, creadopor, actualizado, actualizadopor) VALUES (46, 12, 'Texto', 'TEXT', 'Texto', '', true, '2018-08-23 10:57:54.97', 'root', '2018-08-23 10:57:54.97', 'root');
INSERT INTO public.parametros (id, maestro, nombre, codigo, descripcion, parametros, activo, creado, creadopor, actualizado, actualizadopor) VALUES (47, 12, 'Si/No', 'BOOLEAN', 'Si/No', '', true, '2018-08-23 10:58:18.783', 'root', '2018-08-23 10:58:18.783', 'root');
INSERT INTO public.parametros (id, maestro, nombre, codigo, descripcion, parametros, activo, creado, creadopor, actualizado, actualizadopor) VALUES (48, 12, 'Número Entero', 'INTEGER', 'Número Entero', '', true, '2018-08-23 10:58:38.816', 'root', '2018-08-23 10:58:38.816', 'root');
INSERT INTO public.parametros (id, maestro, nombre, codigo, descripcion, parametros, activo, creado, creadopor, actualizado, actualizadopor) VALUES (49, 12, 'Número Decimal', 'DOUBLE', 'Número Decimal', '', true, '2018-08-23 10:58:58.207', 'root', '2018-08-23 10:58:58.207', 'root');
INSERT INTO public.parametros (id, maestro, nombre, codigo, descripcion, parametros, activo, creado, creadopor, actualizado, actualizadopor) VALUES (50, 12, 'Fecha', 'DATE', 'Fecha', '', true, '2018-08-23 10:59:13.954', 'root', '2018-08-23 10:59:13.954', 'root');
INSERT INTO public.parametros (id, maestro, nombre, codigo, descripcion, parametros, activo, creado, creadopor, actualizado, actualizadopor) VALUES (51, 12, 'Hora', 'TIME', 'Hora', '', true, '2018-08-23 11:00:07.864', 'root', '2018-08-23 11:00:07.864', 'root');
INSERT INTO public.parametros (id, maestro, nombre, codigo, descripcion, parametros, activo, creado, creadopor, actualizado, actualizadopor) VALUES (52, 12, 'Fecha y Hora', 'DATETIME', 'Fecha y Hora', '', true, '2018-08-23 11:00:23.223', 'root', '2018-08-23 11:00:23.223', 'root');
INSERT INTO public.parametros (id, maestro, nombre, codigo, descripcion, parametros, activo, creado, creadopor, actualizado, actualizadopor) VALUES (53, 12, 'Selección Simple', 'ONE', 'Selección Simple', '', true, '2018-08-23 11:00:57.81', 'root', '2018-08-23 11:00:57.81', 'root');
INSERT INTO public.parametros (id, maestro, nombre, codigo, descripcion, parametros, activo, creado, creadopor, actualizado, actualizadopor) VALUES (54, 12, 'Selección Múltiple', 'MANY', 'Selección Múltiple', '', true, '2018-08-23 11:01:13.958', 'root', '2018-08-23 11:01:13.958', 'root');
INSERT INTO public.parametros (id, maestro, nombre, codigo, descripcion, parametros, activo, creado, creadopor, actualizado, actualizadopor) VALUES (55, 12, 'Archivo', 'FILE', 'Archivo', '', true, '2018-08-23 11:01:28.78', 'root', '2018-08-23 11:01:28.78', 'root');
INSERT INTO public.parametros (id, maestro, nombre, codigo, descripcion, parametros, activo, creado, creadopor, actualizado, actualizadopor) VALUES (56, 13, 'Todo', 'A', 'Todo', '', true, '2018-08-23 11:02:02.385', 'root', '2018-08-23 11:02:02.385', 'root');
INSERT INTO public.parametros (id, maestro, nombre, codigo, descripcion, parametros, activo, creado, creadopor, actualizado, actualizadopor) VALUES (57, 12, 'Lista de opciones', 'LIST', 'Lista de opciones', '', true, '2018-09-05 09:01:28.201', 'root', '2018-09-05 09:01:28.201', 'root');


--
-- TOC entry 3222 (class 0 OID 68435)
-- Dependencies: 228
-- Data for Name: perfiles; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.perfiles (id, menu, consulta, modificacion, borrado, nuevo, grupo, activo, descripcion, creado, creadopor, actualizado, actualizadopor, auditoria) VALUES (4, 4, true, true, true, true, 4, true, NULL, '2018-07-13 09:58:26.937227', 'root', '2018-08-16 10:01:00.561', 'root', true);
INSERT INTO public.perfiles (id, menu, consulta, modificacion, borrado, nuevo, grupo, activo, descripcion, creado, creadopor, actualizado, actualizadopor, auditoria) VALUES (5, 5, true, true, true, true, 4, true, NULL, '2018-07-13 09:58:26.937227', 'root', '2018-08-16 10:01:05.458', 'root', true);
INSERT INTO public.perfiles (id, menu, consulta, modificacion, borrado, nuevo, grupo, activo, descripcion, creado, creadopor, actualizado, actualizadopor, auditoria) VALUES (6, 6, true, true, true, true, 4, true, NULL, '2018-07-13 09:58:26.937227', 'root', '2018-08-16 10:01:10.325', 'root', true);
INSERT INTO public.perfiles (id, menu, consulta, modificacion, borrado, nuevo, grupo, activo, descripcion, creado, creadopor, actualizado, actualizadopor, auditoria) VALUES (7, 7, true, true, true, true, 4, true, NULL, '2018-07-13 09:58:26.937227', 'root', '2018-08-16 10:01:14.582', 'root', true);
INSERT INTO public.perfiles (id, menu, consulta, modificacion, borrado, nuevo, grupo, activo, descripcion, creado, creadopor, actualizado, actualizadopor, auditoria) VALUES (8, 8, true, true, true, true, 4, true, NULL, '2018-07-13 09:58:26.937227', 'root', '2018-08-16 10:01:24.598', 'root', true);
INSERT INTO public.perfiles (id, menu, consulta, modificacion, borrado, nuevo, grupo, activo, descripcion, creado, creadopor, actualizado, actualizadopor, auditoria) VALUES (16, 12, true, true, true, true, 8, true, NULL, '2018-07-13 10:03:31.181', 'root', '2018-08-16 10:01:34.714', 'root', true);
INSERT INTO public.perfiles (id, menu, consulta, modificacion, borrado, nuevo, grupo, activo, descripcion, creado, creadopor, actualizado, actualizadopor, auditoria) VALUES (17, 21, true, true, true, true, 4, true, NULL, '2018-07-18 11:47:10.089', 'root', '2018-08-16 10:01:38.563', 'root', true);
INSERT INTO public.perfiles (id, menu, consulta, modificacion, borrado, nuevo, grupo, activo, descripcion, creado, creadopor, actualizado, actualizadopor, auditoria) VALUES (18, 21, true, true, true, true, 7, true, NULL, '2018-07-24 13:56:52.784', 'root', '2018-08-16 10:01:42.269', 'root', true);
INSERT INTO public.perfiles (id, menu, consulta, modificacion, borrado, nuevo, grupo, activo, descripcion, creado, creadopor, actualizado, actualizadopor, auditoria) VALUES (19, 18, true, true, true, true, 7, true, NULL, '2018-07-24 13:57:10.646', 'root', '2018-08-16 10:01:46.998', 'root', true);
INSERT INTO public.perfiles (id, menu, consulta, modificacion, borrado, nuevo, grupo, activo, descripcion, creado, creadopor, actualizado, actualizadopor, auditoria) VALUES (20, 19, true, true, true, true, 7, true, NULL, '2018-07-24 13:57:26.184', 'root', '2018-08-16 10:01:50.776', 'root', true);
INSERT INTO public.perfiles (id, menu, consulta, modificacion, borrado, nuevo, grupo, activo, descripcion, creado, creadopor, actualizado, actualizadopor, auditoria) VALUES (21, 20, true, true, true, true, 7, true, NULL, '2018-07-24 13:57:36.48', 'root', '2018-08-16 10:01:55.157', 'root', true);
INSERT INTO public.perfiles (id, menu, consulta, modificacion, borrado, nuevo, grupo, activo, descripcion, creado, creadopor, actualizado, actualizadopor, auditoria) VALUES (22, 22, true, true, true, true, 7, true, NULL, '2018-07-24 14:03:09.686', 'root', '2018-08-16 10:01:59.339', 'root', true);
INSERT INTO public.perfiles (id, menu, consulta, modificacion, borrado, nuevo, grupo, activo, descripcion, creado, creadopor, actualizado, actualizadopor, auditoria) VALUES (23, 23, true, true, true, true, 7, true, NULL, '2018-07-24 14:03:51.495', 'root', '2018-08-16 10:02:03.497', 'root', true);
INSERT INTO public.perfiles (id, menu, consulta, modificacion, borrado, nuevo, grupo, activo, descripcion, creado, creadopor, actualizado, actualizadopor, auditoria) VALUES (24, 24, true, true, true, true, 7, true, NULL, '2018-08-02 08:14:02.77', 'root', '2018-08-16 10:02:07.448', 'root', true);
INSERT INTO public.perfiles (id, menu, consulta, modificacion, borrado, nuevo, grupo, activo, descripcion, creado, creadopor, actualizado, actualizadopor, auditoria) VALUES (9, 9, true, true, true, true, 4, true, NULL, '2018-07-13 09:58:26.937227', 'root', '2018-08-16 10:02:11.233', 'root', true);
INSERT INTO public.perfiles (id, menu, consulta, modificacion, borrado, nuevo, grupo, activo, descripcion, creado, creadopor, actualizado, actualizadopor, auditoria) VALUES (10, 10, true, true, true, true, 4, true, NULL, '2018-07-13 09:58:26.937227', 'root', '2018-08-16 10:02:15.244', 'root', true);
INSERT INTO public.perfiles (id, menu, consulta, modificacion, borrado, nuevo, grupo, activo, descripcion, creado, creadopor, actualizado, actualizadopor, auditoria) VALUES (11, 11, true, true, true, true, 4, true, NULL, '2018-07-13 09:58:26.937227', 'root', '2018-08-16 10:02:19.754', 'root', true);
INSERT INTO public.perfiles (id, menu, consulta, modificacion, borrado, nuevo, grupo, activo, descripcion, creado, creadopor, actualizado, actualizadopor, auditoria) VALUES (12, 12, true, true, true, true, 4, true, NULL, '2018-07-13 09:58:26.937227', 'root', '2018-08-16 10:02:23.525', 'root', true);
INSERT INTO public.perfiles (id, menu, consulta, modificacion, borrado, nuevo, grupo, activo, descripcion, creado, creadopor, actualizado, actualizadopor, auditoria) VALUES (13, 18, true, true, true, true, 4, true, NULL, '2018-07-13 09:58:26.937227', 'root', '2018-08-16 10:02:27.969', 'root', true);
INSERT INTO public.perfiles (id, menu, consulta, modificacion, borrado, nuevo, grupo, activo, descripcion, creado, creadopor, actualizado, actualizadopor, auditoria) VALUES (14, 19, true, true, true, true, 4, true, NULL, '2018-07-13 09:58:26.937227', 'root', '2018-08-16 10:02:31.748', 'root', true);
INSERT INTO public.perfiles (id, menu, consulta, modificacion, borrado, nuevo, grupo, activo, descripcion, creado, creadopor, actualizado, actualizadopor, auditoria) VALUES (15, 20, true, true, true, true, 4, true, NULL, '2018-07-13 09:58:26.937227', 'root', '2018-08-16 10:02:36.507', 'root', true);
INSERT INTO public.perfiles (id, menu, consulta, modificacion, borrado, nuevo, grupo, activo, descripcion, creado, creadopor, actualizado, actualizadopor, auditoria) VALUES (25, 25, true, true, true, true, 4, true, NULL, '2018-08-16 10:17:14.756', 'root', '2018-08-16 10:17:14.756', 'root', true);
INSERT INTO public.perfiles (id, menu, consulta, modificacion, borrado, nuevo, grupo, activo, descripcion, creado, creadopor, actualizado, actualizadopor, auditoria) VALUES (26, 26, true, true, true, true, 7, true, NULL, '2018-08-23 10:26:42.944', 'root', '2018-08-23 10:26:42.944', 'root', true);


--
-- TOC entry 3224 (class 0 OID 68443)
-- Dependencies: 230
-- Data for Name: personas; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.personas (id, nombres, apellidos, email, userid, clave, cedula, fecha, direccion, rol, activo, ocupacion, genero, descripcion, creado, creadopor, actualizado, actualizadopor) VALUES (2, 'Julio Cesar', 'Villalba Guachamin', 'juliocvillalbag@hotmail.com', 'jcvillalba', '502ff82f7f1f8218dd41201fe4353687', '1703325934', '1951-04-12', NULL, NULL, true, 'Ingeniero Electrónico', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO public.personas (id, nombres, apellidos, email, userid, clave, cedula, fecha, direccion, rol, activo, ocupacion, genero, descripcion, creado, creadopor, actualizado, actualizadopor) VALUES (15, 'Alex David', 'Ordóñez Armijos', 'alex.ceuz@outlook.com', 'adordóñez', 'c7bbdcc977c6fe757e15fb85fb2929cc', '1751222512', '1999-03-01', 26, NULL, true, 'Técnico en Telecomunicaciones', 10, 'Técnico en Telecomunicaciones', '2018-08-16 08:23:20.448', 'root', '2018-08-16 08:25:08.161', 'root');
INSERT INTO public.personas (id, nombres, apellidos, email, userid, clave, cedula, fecha, direccion, rol, activo, ocupacion, genero, descripcion, creado, creadopor, actualizado, actualizadopor) VALUES (16, 'Erika Priscila', 'Pila Valdiviezo', 'erikaprispi@gmail.com', '1724523640', '3e49697d4dd98b02434b39569460b62e', '1724523640', '1991-07-22', 29, NULL, true, 'Tecnologa', 9, 'Lindura ñaña', '2018-09-12 08:17:00.229', 'root', '2018-09-12 08:20:09.394', 'root');
INSERT INTO public.personas (id, nombres, apellidos, email, userid, clave, cedula, fecha, direccion, rol, activo, ocupacion, genero, descripcion, creado, creadopor, actualizado, actualizadopor) VALUES (18, 'P000002', 'P000002', 'aaa@ewee.com', 'P000002', 'eed9d765e3111b86fff00f1dd08ef9ac', 'P000002', '2018-09-12', 31, NULL, true, 'P000002', 10, 'P000002', '2018-09-12 08:37:28.596', 'root', '2018-09-12 08:37:28.596', NULL);
INSERT INTO public.personas (id, nombres, apellidos, email, userid, clave, cedula, fecha, direccion, rol, activo, ocupacion, genero, descripcion, creado, creadopor, actualizado, actualizadopor) VALUES (20, 'P000004', 'P000004', 'luis@2018.com', 'P000004', 'd922fe3253aec44d0460dbf78bbc04e1', 'P000004', '2018-09-12', 33, NULL, true, 'P000004', 9, 'P000004', '2018-09-12 08:50:08.077', 'root', '2018-09-12 08:50:08.077', NULL);
INSERT INTO public.personas (id, nombres, apellidos, email, userid, clave, cedula, fecha, direccion, rol, activo, ocupacion, genero, descripcion, creado, creadopor, actualizado, actualizadopor) VALUES (24, 'P000008', 'P000008', 'asdsa@asd.omc', 'P000008', 'cf4828856d49527ba7b607a046c45915', 'P000008', '2018-09-12', 37, NULL, true, 'P000008', 9, NULL, '2018-09-12 09:41:32.399', 'root', '2018-11-03 00:20:48.195', 'root');
INSERT INTO public.personas (id, nombres, apellidos, email, userid, clave, cedula, fecha, direccion, rol, activo, ocupacion, genero, descripcion, creado, creadopor, actualizado, actualizadopor) VALUES (25, 'P000009', 'P000009', 'aqdsa@ad.as', 'P000009', 'e0494a9fb66cbbc8f593b5449f01d668', 'P000009', '2018-09-12', 38, NULL, true, 'P000009', 9, NULL, '2018-09-12 09:48:32.18', 'root', '2018-11-03 00:20:59.34', 'root');
INSERT INTO public.personas (id, nombres, apellidos, email, userid, clave, cedula, fecha, direccion, rol, activo, ocupacion, genero, descripcion, creado, creadopor, actualizado, actualizadopor) VALUES (26, 'P000010', 'P000010', '', 'P000010', '8db1e8cd181b3a4414ec7c25a5cd249d', 'P000010', '2018-09-12', 39, NULL, true, 'P000010', 9, NULL, '2018-09-12 09:54:08.236', 'root', '2018-11-03 00:21:06.916', 'root');
INSERT INTO public.personas (id, nombres, apellidos, email, userid, clave, cedula, fecha, direccion, rol, activo, ocupacion, genero, descripcion, creado, creadopor, actualizado, actualizadopor) VALUES (27, 'P000011', 'P000011', '', 'P000011', '027910220caa0e7e590220f113e1d646', 'P000011', '2018-09-12', 40, NULL, true, 'P000011', 9, NULL, '2018-09-12 10:03:04.475', 'root', '2018-11-03 00:21:15.085', 'root');
INSERT INTO public.personas (id, nombres, apellidos, email, userid, clave, cedula, fecha, direccion, rol, activo, ocupacion, genero, descripcion, creado, creadopor, actualizado, actualizadopor) VALUES (31, 'P000015', 'P000015', '', 'P000015', '56ede6e6dd447f88496bffef51d9116a', 'P000015', '2018-09-12', 44, NULL, true, 'P000015', 10, NULL, '2018-09-12 10:26:24.331', 'root', '2018-11-03 21:07:06.864', 'root');
INSERT INTO public.personas (id, nombres, apellidos, email, userid, clave, cedula, fecha, direccion, rol, activo, ocupacion, genero, descripcion, creado, creadopor, actualizado, actualizadopor) VALUES (28, 'P000012', 'P000012', '', 'P000012', 'a421413f4e47ca6fbfa53ebf8a55784e', 'P000012', '2018-09-12', 41, NULL, true, 'P000012', 9, NULL, '2018-09-12 10:06:45.241', 'root', '2018-11-03 00:21:25.28', 'root');
INSERT INTO public.personas (id, nombres, apellidos, email, userid, clave, cedula, fecha, direccion, rol, activo, ocupacion, genero, descripcion, creado, creadopor, actualizado, actualizadopor) VALUES (29, 'P000013', 'P000013', '', 'P000013', '3f89936015a23bc7af6641520626c7f3', 'P000013', '2018-09-12', 42, NULL, true, 'P000013', 10, NULL, '2018-09-12 10:17:41.838', 'root', '2018-11-03 00:21:33.659', 'root');
INSERT INTO public.personas (id, nombres, apellidos, email, userid, clave, cedula, fecha, direccion, rol, activo, ocupacion, genero, descripcion, creado, creadopor, actualizado, actualizadopor) VALUES (30, 'P000014', 'P000014', '', 'P000014', 'ae50080ab2566d20d0ed1c8ca99e40e5', 'P000014', '2018-09-12', 43, NULL, true, 'P000014', 9, NULL, '2018-09-12 10:19:28.249', 'root', '2018-11-03 00:21:39.66', 'root');
INSERT INTO public.personas (id, nombres, apellidos, email, userid, clave, cedula, fecha, direccion, rol, activo, ocupacion, genero, descripcion, creado, creadopor, actualizado, actualizadopor) VALUES (17, 'Pedro', 'Páramo', 'nadie@nada.com', 'P000001', 'c37b43e992ea3dd6ef8f1bba52d1416e', 'P000001', '2018-09-12', 30, NULL, true, 'Ninguna', 10, 'Nadie', '2018-09-12 08:30:29.387', 'root', '2018-11-03 00:21:45.275', 'root');
INSERT INTO public.personas (id, nombres, apellidos, email, userid, clave, cedula, fecha, direccion, rol, activo, ocupacion, genero, descripcion, creado, creadopor, actualizado, actualizadopor) VALUES (3, 'Isabel Macrina', 'Ordóñez', 'macrina.ordonez@gmail.com', 'imordoñez', '502ff82f7f1f8218dd41201fe4353687', '1725351777', '1992-08-16', 17, NULL, true, 'Viajera Intelectual', 9, 'Diseñadora Gráfica', NULL, NULL, '2018-11-03 21:07:18.626', 'root');
INSERT INTO public.personas (id, nombres, apellidos, email, userid, clave, cedula, fecha, direccion, rol, activo, ocupacion, genero, descripcion, creado, creadopor, actualizado, actualizadopor) VALUES (21, 'P000005', 'P000005', 'asdas@asd.com', 'P000005', '822305cdc64327a39e28a9c8f565749c', 'P000005', '2018-09-12', 34, NULL, true, 'P000005', 9, NULL, '2018-09-12 08:51:30.405', 'root', '2018-11-03 00:22:18.92', 'root');
INSERT INTO public.personas (id, nombres, apellidos, email, userid, clave, cedula, fecha, direccion, rol, activo, ocupacion, genero, descripcion, creado, creadopor, actualizado, actualizadopor) VALUES (14, 'José', 'Rodríguez', 'cruzjonathan705@gmail.com', 'jrodríguez', '6e17bb67cc18cf43f3f1b713ed4e2d82', '1722227004', '1993-04-21', 23, NULL, true, 'Informático', 10, 'Informático', NULL, NULL, '2018-11-03 21:20:32.021', 'root');
INSERT INTO public.personas (id, nombres, apellidos, email, userid, clave, cedula, fecha, direccion, rol, activo, ocupacion, genero, descripcion, creado, creadopor, actualizado, actualizadopor) VALUES (5, 'Andrea Paola', 'Villalba Casagallo', 'pao.landyllita@hotmail.com', 'apvillalba', '502ff82f7f1f8218dd41201fe4353687', '1720731320', '1991-05-02', 28, NULL, true, 'Administradora de Empresas', 9, 'Administradora de Empresas', NULL, NULL, '2018-11-03 21:21:02.558', 'root');
INSERT INTO public.personas (id, nombres, apellidos, email, userid, clave, cedula, fecha, direccion, rol, activo, ocupacion, genero, descripcion, creado, creadopor, actualizado, actualizadopor) VALUES (32, 'Limón', 'Ordóñez', 'limon.ordonez@gmail.com', 'PG001', '0328ed3c962cca75d7cce5b5e2f0fc74', 'PG001', '2014-11-18', 45, NULL, true, 'Dormir', NULL, NULL, '2018-11-03 23:15:28.245', 'root', '2018-11-03 23:15:28.245', NULL);
INSERT INTO public.personas (id, nombres, apellidos, email, userid, clave, cedula, fecha, direccion, rol, activo, ocupacion, genero, descripcion, creado, creadopor, actualizado, actualizadopor) VALUES (19, 'P000003', 'P000003', 'ewrwe@sefds.com', 'P000003', '5a81d15faca140aee869c8db4b42aa9a', 'P000003', '2018-09-12', 32, NULL, true, 'P000003', 10, 'P000003', '2018-09-12 08:38:29.191', 'root', '2018-11-03 00:20:14.527', 'root');
INSERT INTO public.personas (id, nombres, apellidos, email, userid, clave, cedula, fecha, direccion, rol, activo, ocupacion, genero, descripcion, creado, creadopor, actualizado, actualizadopor) VALUES (22, 'P000006', 'P000006', 'asas@asda.com', 'P000006', 'ac6470860013cc7672fd421b2ad4833d', 'P000006', '2018-09-12', 35, NULL, true, 'P000006', 10, NULL, '2018-09-12 09:27:21.872', 'root', '2018-11-03 00:20:27.926', 'root');
INSERT INTO public.personas (id, nombres, apellidos, email, userid, clave, cedula, fecha, direccion, rol, activo, ocupacion, genero, descripcion, creado, creadopor, actualizado, actualizadopor) VALUES (23, 'P000007', 'P000007', 'dasd@adsa.vxc', 'P000007', 'b9058b4224cf223b6a41db87df90e8fa', 'P000007', '2018-09-12', 36, NULL, true, 'P000007', 9, NULL, '2018-09-12 09:35:21.34', 'root', '2018-11-03 00:20:40.769', 'root');
INSERT INTO public.personas (id, nombres, apellidos, email, userid, clave, cedula, fecha, direccion, rol, activo, ocupacion, genero, descripcion, creado, creadopor, actualizado, actualizadopor) VALUES (1, 'Luis Fernando', 'Ordóñez Armijos', 'louis.fercho@gmail.com', 'root', '502ff82f7f1f8218dd41201fe4353687', '1725351736', '1991-05-15', 18, NULL, true, 'Analista de Sistemas Informáticos', 10, '', NULL, NULL, '2018-11-03 21:05:16.01', 'root');


--
-- TOC entry 3226 (class 0 OID 68452)
-- Dependencies: 232
-- Data for Name: prescripciones; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.prescripciones (id, atencion, medicamento, dosis, frecuencia, duracion, advertencias, activo, creado, creadopor, actualizado, actualizadopor) VALUES (6, 11, NULL, NULL, NULL, NULL, NULL, true, '2018-09-03 15:50:00.256', 'root', '2018-09-03 15:50:00.256', 'root');
INSERT INTO public.prescripciones (id, atencion, medicamento, dosis, frecuencia, duracion, advertencias, activo, creado, creadopor, actualizado, actualizadopor) VALUES (7, 11, NULL, NULL, NULL, NULL, NULL, true, '2018-09-03 16:32:04.759', 'root', '2018-09-03 16:32:04.759', 'root');
INSERT INTO public.prescripciones (id, atencion, medicamento, dosis, frecuencia, duracion, advertencias, activo, creado, creadopor, actualizado, actualizadopor) VALUES (8, 13, NULL, NULL, NULL, NULL, NULL, true, '2018-09-04 08:34:15.461', 'root', '2018-09-04 08:34:15.461', 'root');
INSERT INTO public.prescripciones (id, atencion, medicamento, dosis, frecuencia, duracion, advertencias, activo, creado, creadopor, actualizado, actualizadopor) VALUES (9, 14, 'Medicamento 01', '500 mg', 'Cada 8 horas', '1 semana', 'Ninguna', true, '2018-09-05 11:47:01.919', 'root', '2018-09-05 16:20:02.86', 'root');
INSERT INTO public.prescripciones (id, atencion, medicamento, dosis, frecuencia, duracion, advertencias, activo, creado, creadopor, actualizado, actualizadopor) VALUES (10, 14, 'Medicamento 02', '1 comprimido', 'Cada 10 horas', '2 días', 'Ninguna', true, '2018-09-05 14:17:55.063', 'root', '2018-09-05 16:20:02.875', 'root');
INSERT INTO public.prescripciones (id, atencion, medicamento, dosis, frecuencia, duracion, advertencias, activo, creado, creadopor, actualizado, actualizadopor) VALUES (12, 27, '', '', '', '', '', true, '2018-09-14 10:25:50.051', 'root', '2018-09-14 12:34:26.514', 'root');
INSERT INTO public.prescripciones (id, atencion, medicamento, dosis, frecuencia, duracion, advertencias, activo, creado, creadopor, actualizado, actualizadopor) VALUES (13, 27, '', '', '', '', '', true, '2018-09-14 10:27:27.936', 'root', '2018-09-14 12:34:26.528', 'root');


--
-- TOC entry 3228 (class 0 OID 68460)
-- Dependencies: 234
-- Data for Name: profesionales; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.profesionales (id, persona, especialidad, institucion, activo, descripcion, creado, creadopor, actualizado, actualizadopor, fotografia) VALUES (2, 3, 31, 1, true, 'Diseñadora Gráfica', '2018-07-24 18:05:53.302', 'imordonez', '2018-11-03 20:54:48.475', 'root', 56);
INSERT INTO public.profesionales (id, persona, especialidad, institucion, activo, descripcion, creado, creadopor, actualizado, actualizadopor, fotografia) VALUES (1, 1, 31, 1, true, 'Ingeniero Informático', NULL, NULL, '2018-11-03 21:05:16.041', 'root', 57);
INSERT INTO public.profesionales (id, persona, especialidad, institucion, activo, descripcion, creado, creadopor, actualizado, actualizadopor, fotografia) VALUES (6, 31, 34, 1, true, 'P000015', '2018-09-12 10:26:24.352', 'root', '2018-11-03 21:07:06.899', 'root', 58);
INSERT INTO public.profesionales (id, persona, especialidad, institucion, activo, descripcion, creado, creadopor, actualizado, actualizadopor, fotografia) VALUES (3, 14, 32, 1, true, NULL, '2018-07-24 18:09:08.391', 'imordonez', '2018-11-03 21:20:32.045', 'root', 60);
INSERT INTO public.profesionales (id, persona, especialidad, institucion, activo, descripcion, creado, creadopor, actualizado, actualizadopor, fotografia) VALUES (5, 5, 33, 1, true, '', '2018-08-30 10:03:26.735', 'apvillalba', '2018-11-03 21:21:02.573', 'root', 61);


--
-- TOC entry 3230 (class 0 OID 68468)
-- Dependencies: 236
-- Data for Name: usuarios; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.usuarios (id, modulo, grupo, institucion, persona, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (1, 1, 4, NULL, 1, NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.usuarios (id, modulo, grupo, institucion, persona, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (7, 3, 7, 1, 14, NULL, '2018-07-12 16:46:51.825', 'root', '2018-07-12 16:56:15.187', 'root', true);
INSERT INTO public.usuarios (id, modulo, grupo, institucion, persona, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (8, 2, 7, 1, 3, NULL, '2018-07-24 13:54:50.415', 'root', '2018-08-16 08:37:54.755', 'root', true);
INSERT INTO public.usuarios (id, modulo, grupo, institucion, persona, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (13, 2, 7, 1, 5, NULL, '2018-08-16 08:41:41.634', 'root', '2018-08-16 08:41:41.634', 'root', true);
INSERT INTO public.usuarios (id, modulo, grupo, institucion, persona, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (14, 2, 7, 1, 1, NULL, '2018-08-30 11:13:00.727', 'root', '2018-08-30 11:13:00.727', 'root', true);


--
-- TOC entry 3262 (class 0 OID 0)
-- Dependencies: 197
-- Name: archivos_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.archivos_id_seq', 62, true);


--
-- TOC entry 3263 (class 0 OID 0)
-- Dependencies: 199
-- Name: atenciones_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.atenciones_id_seq', 33, true);


--
-- TOC entry 3264 (class 0 OID 0)
-- Dependencies: 201
-- Name: campos_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.campos_id_seq', 17, true);


--
-- TOC entry 3265 (class 0 OID 0)
-- Dependencies: 203
-- Name: citas_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.citas_id_seq', 32, true);


--
-- TOC entry 3266 (class 0 OID 0)
-- Dependencies: 205
-- Name: datos_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.datos_id_seq', 180, true);


--
-- TOC entry 3267 (class 0 OID 0)
-- Dependencies: 207
-- Name: direcciones_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.direcciones_id_seq', 45, true);


--
-- TOC entry 3268 (class 0 OID 0)
-- Dependencies: 209
-- Name: formulas_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.formulas_id_seq', 11, true);


--
-- TOC entry 3269 (class 0 OID 0)
-- Dependencies: 211
-- Name: horarios_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.horarios_id_seq', 168, true);


--
-- TOC entry 3270 (class 0 OID 0)
-- Dependencies: 213
-- Name: horas_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.horas_id_seq', 24, true);


--
-- TOC entry 3271 (class 0 OID 0)
-- Dependencies: 215
-- Name: instituciones_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.instituciones_id_seq', 4, true);


--
-- TOC entry 3272 (class 0 OID 0)
-- Dependencies: 217
-- Name: maestros_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.maestros_id_seq', 22, true);


--
-- TOC entry 3273 (class 0 OID 0)
-- Dependencies: 219
-- Name: materiales_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.materiales_id_seq', 4, true);


--
-- TOC entry 3274 (class 0 OID 0)
-- Dependencies: 221
-- Name: menus_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.menus_id_seq', 26, true);


--
-- TOC entry 3275 (class 0 OID 0)
-- Dependencies: 223
-- Name: ordenes_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.ordenes_id_seq', 4, true);


--
-- TOC entry 3276 (class 0 OID 0)
-- Dependencies: 225
-- Name: pacientes_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.pacientes_id_seq', 25, true);


--
-- TOC entry 3277 (class 0 OID 0)
-- Dependencies: 227
-- Name: parametros_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.parametros_id_seq', 57, true);


--
-- TOC entry 3278 (class 0 OID 0)
-- Dependencies: 229
-- Name: perfiles_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.perfiles_id_seq', 26, true);


--
-- TOC entry 3279 (class 0 OID 0)
-- Dependencies: 231
-- Name: personas_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.personas_id_seq', 32, true);


--
-- TOC entry 3280 (class 0 OID 0)
-- Dependencies: 233
-- Name: prescripciones_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.prescripciones_id_seq', 13, true);


--
-- TOC entry 3281 (class 0 OID 0)
-- Dependencies: 235
-- Name: profesionales_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.profesionales_id_seq', 6, true);


--
-- TOC entry 3282 (class 0 OID 0)
-- Dependencies: 237
-- Name: usuarios_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.usuarios_id_seq', 14, true);


--
-- TOC entry 2963 (class 2606 OID 68498)
-- Name: archivos archivos_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.archivos
    ADD CONSTRAINT archivos_pkey PRIMARY KEY (id);


--
-- TOC entry 2965 (class 2606 OID 68500)
-- Name: atenciones atenciones_cita_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.atenciones
    ADD CONSTRAINT atenciones_cita_key UNIQUE (cita);


--
-- TOC entry 2967 (class 2606 OID 68502)
-- Name: atenciones atenciones_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.atenciones
    ADD CONSTRAINT atenciones_pkey PRIMARY KEY (id);


--
-- TOC entry 2969 (class 2606 OID 68504)
-- Name: campos campos_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.campos
    ADD CONSTRAINT campos_pkey PRIMARY KEY (id);


--
-- TOC entry 2971 (class 2606 OID 68506)
-- Name: citas citas_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.citas
    ADD CONSTRAINT citas_pkey PRIMARY KEY (id);


--
-- TOC entry 2973 (class 2606 OID 68508)
-- Name: datos datos_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.datos
    ADD CONSTRAINT datos_pkey PRIMARY KEY (id);


--
-- TOC entry 2975 (class 2606 OID 68510)
-- Name: direcciones direcciones_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.direcciones
    ADD CONSTRAINT direcciones_pkey PRIMARY KEY (id);


--
-- TOC entry 2977 (class 2606 OID 68512)
-- Name: formulas formulas_atencion_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.formulas
    ADD CONSTRAINT formulas_atencion_key UNIQUE (atencion);


--
-- TOC entry 2979 (class 2606 OID 68514)
-- Name: formulas formulas_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.formulas
    ADD CONSTRAINT formulas_pkey PRIMARY KEY (id);


--
-- TOC entry 2981 (class 2606 OID 68516)
-- Name: horarios horarios_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.horarios
    ADD CONSTRAINT horarios_pkey PRIMARY KEY (id);


--
-- TOC entry 2983 (class 2606 OID 68518)
-- Name: horas horas_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.horas
    ADD CONSTRAINT horas_pkey PRIMARY KEY (id);


--
-- TOC entry 2985 (class 2606 OID 68520)
-- Name: instituciones instituciones_direccion_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.instituciones
    ADD CONSTRAINT instituciones_direccion_key UNIQUE (direccion);


--
-- TOC entry 2987 (class 2606 OID 68522)
-- Name: instituciones instituciones_logotipo_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.instituciones
    ADD CONSTRAINT instituciones_logotipo_key UNIQUE (logotipo);


--
-- TOC entry 2989 (class 2606 OID 68524)
-- Name: instituciones instituciones_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.instituciones
    ADD CONSTRAINT instituciones_pkey PRIMARY KEY (id);


--
-- TOC entry 2991 (class 2606 OID 68526)
-- Name: maestros maestro_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.maestros
    ADD CONSTRAINT maestro_pkey PRIMARY KEY (id);


--
-- TOC entry 2993 (class 2606 OID 68528)
-- Name: maestros maestros_codigo_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.maestros
    ADD CONSTRAINT maestros_codigo_key UNIQUE (codigo);


--
-- TOC entry 2995 (class 2606 OID 68530)
-- Name: materiales materiales_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.materiales
    ADD CONSTRAINT materiales_pkey PRIMARY KEY (id);


--
-- TOC entry 2997 (class 2606 OID 68532)
-- Name: menus menus_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.menus
    ADD CONSTRAINT menus_pkey PRIMARY KEY (id);


--
-- TOC entry 2999 (class 2606 OID 68534)
-- Name: ordenes ordenes_formula_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ordenes
    ADD CONSTRAINT ordenes_formula_key UNIQUE (formula);


--
-- TOC entry 3001 (class 2606 OID 68536)
-- Name: ordenes ordenes_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ordenes
    ADD CONSTRAINT ordenes_pkey PRIMARY KEY (id);


--
-- TOC entry 3003 (class 2606 OID 68798)
-- Name: pacientes pacientes_fotografia_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pacientes
    ADD CONSTRAINT pacientes_fotografia_key UNIQUE (fotografia);


--
-- TOC entry 3005 (class 2606 OID 68538)
-- Name: pacientes pacientes_persona_institucion_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pacientes
    ADD CONSTRAINT pacientes_persona_institucion_key UNIQUE (persona, institucion);


--
-- TOC entry 3007 (class 2606 OID 68540)
-- Name: pacientes pacientes_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pacientes
    ADD CONSTRAINT pacientes_pkey PRIMARY KEY (id);


--
-- TOC entry 3009 (class 2606 OID 68542)
-- Name: parametros parametros_maestro_codigo_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.parametros
    ADD CONSTRAINT parametros_maestro_codigo_key UNIQUE (maestro, codigo);


--
-- TOC entry 3011 (class 2606 OID 68544)
-- Name: parametros parametros_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.parametros
    ADD CONSTRAINT parametros_pkey PRIMARY KEY (id);


--
-- TOC entry 3013 (class 2606 OID 68546)
-- Name: perfiles perfiles_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.perfiles
    ADD CONSTRAINT perfiles_pkey PRIMARY KEY (id);


--
-- TOC entry 3015 (class 2606 OID 68548)
-- Name: personas personas_direccion_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.personas
    ADD CONSTRAINT personas_direccion_key UNIQUE (direccion);


--
-- TOC entry 3017 (class 2606 OID 68552)
-- Name: personas personas_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.personas
    ADD CONSTRAINT personas_pkey PRIMARY KEY (id);


--
-- TOC entry 3019 (class 2606 OID 68554)
-- Name: prescripciones prescripciones_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.prescripciones
    ADD CONSTRAINT prescripciones_pkey PRIMARY KEY (id);


--
-- TOC entry 3021 (class 2606 OID 68805)
-- Name: profesionales profesionales_fotografia_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.profesionales
    ADD CONSTRAINT profesionales_fotografia_key UNIQUE (fotografia);


--
-- TOC entry 3023 (class 2606 OID 68556)
-- Name: profesionales profesionales_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.profesionales
    ADD CONSTRAINT profesionales_pkey PRIMARY KEY (id);


--
-- TOC entry 3025 (class 2606 OID 68558)
-- Name: usuarios usuarios_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuarios
    ADD CONSTRAINT usuarios_pkey PRIMARY KEY (id);


--
-- TOC entry 3026 (class 2606 OID 68559)
-- Name: atenciones atenciones_cita_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.atenciones
    ADD CONSTRAINT atenciones_cita_fkey FOREIGN KEY (cita) REFERENCES public.citas(id);


--
-- TOC entry 3027 (class 2606 OID 68564)
-- Name: atenciones atenciones_especialidad_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.atenciones
    ADD CONSTRAINT atenciones_especialidad_fkey FOREIGN KEY (especialidad) REFERENCES public.parametros(id);


--
-- TOC entry 3028 (class 2606 OID 68569)
-- Name: atenciones atenciones_paciente_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.atenciones
    ADD CONSTRAINT atenciones_paciente_fkey FOREIGN KEY (paciente) REFERENCES public.pacientes(id);


--
-- TOC entry 3029 (class 2606 OID 68574)
-- Name: atenciones atenciones_profesional_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.atenciones
    ADD CONSTRAINT atenciones_profesional_fkey FOREIGN KEY (profesional) REFERENCES public.profesionales(id);


--
-- TOC entry 3030 (class 2606 OID 68579)
-- Name: campos campos_grupo_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.campos
    ADD CONSTRAINT campos_grupo_fkey FOREIGN KEY (grupo) REFERENCES public.parametros(id);


--
-- TOC entry 3031 (class 2606 OID 68584)
-- Name: campos campos_institucion_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.campos
    ADD CONSTRAINT campos_institucion_fkey FOREIGN KEY (institucion) REFERENCES public.instituciones(id);


--
-- TOC entry 3032 (class 2606 OID 68589)
-- Name: campos campos_tipo_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.campos
    ADD CONSTRAINT campos_tipo_fkey FOREIGN KEY (tipo) REFERENCES public.parametros(id);


--
-- TOC entry 3033 (class 2606 OID 68594)
-- Name: citas citas_paciente_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.citas
    ADD CONSTRAINT citas_paciente_fkey FOREIGN KEY (paciente) REFERENCES public.pacientes(id);


--
-- TOC entry 3034 (class 2606 OID 68599)
-- Name: citas citas_profesional_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.citas
    ADD CONSTRAINT citas_profesional_fkey FOREIGN KEY (profesional) REFERENCES public.profesionales(id);


--
-- TOC entry 3035 (class 2606 OID 68604)
-- Name: datos datos_archivo_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.datos
    ADD CONSTRAINT datos_archivo_fkey FOREIGN KEY (archivo) REFERENCES public.archivos(id);


--
-- TOC entry 3036 (class 2606 OID 68609)
-- Name: datos datos_tipo_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.datos
    ADD CONSTRAINT datos_tipo_fkey FOREIGN KEY (tipo) REFERENCES public.parametros(id);


--
-- TOC entry 3037 (class 2606 OID 68614)
-- Name: formulas formulas_atencion_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.formulas
    ADD CONSTRAINT formulas_atencion_fkey FOREIGN KEY (atencion) REFERENCES public.atenciones(id);


--
-- TOC entry 3038 (class 2606 OID 68619)
-- Name: formulas formulas_material_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.formulas
    ADD CONSTRAINT formulas_material_fkey FOREIGN KEY (material) REFERENCES public.materiales(id);


--
-- TOC entry 3039 (class 2606 OID 68624)
-- Name: formulas formulas_tratamiento_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.formulas
    ADD CONSTRAINT formulas_tratamiento_fkey FOREIGN KEY (tratamiento) REFERENCES public.parametros(id);


--
-- TOC entry 3040 (class 2606 OID 68629)
-- Name: horarios horarios_dia_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.horarios
    ADD CONSTRAINT horarios_dia_fkey FOREIGN KEY (dia) REFERENCES public.parametros(id);


--
-- TOC entry 3041 (class 2606 OID 68634)
-- Name: horarios horarios_hora_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.horarios
    ADD CONSTRAINT horarios_hora_fkey FOREIGN KEY (hora) REFERENCES public.horas(id);


--
-- TOC entry 3042 (class 2606 OID 68639)
-- Name: horarios horarios_profesional_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.horarios
    ADD CONSTRAINT horarios_profesional_fkey FOREIGN KEY (profesional) REFERENCES public.profesionales(id);


--
-- TOC entry 3043 (class 2606 OID 68644)
-- Name: horas horas_institucion_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.horas
    ADD CONSTRAINT horas_institucion_fkey FOREIGN KEY (institucion) REFERENCES public.instituciones(id);


--
-- TOC entry 3044 (class 2606 OID 68649)
-- Name: instituciones instituciones_direccion_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.instituciones
    ADD CONSTRAINT instituciones_direccion_fkey FOREIGN KEY (direccion) REFERENCES public.direcciones(id);


--
-- TOC entry 3045 (class 2606 OID 68654)
-- Name: instituciones instituciones_logotipo_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.instituciones
    ADD CONSTRAINT instituciones_logotipo_fkey FOREIGN KEY (logotipo) REFERENCES public.archivos(id);


--
-- TOC entry 3046 (class 2606 OID 68659)
-- Name: materiales materiales_foco_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.materiales
    ADD CONSTRAINT materiales_foco_fkey FOREIGN KEY (foco) REFERENCES public.parametros(id);


--
-- TOC entry 3047 (class 2606 OID 68664)
-- Name: materiales materiales_tipo_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.materiales
    ADD CONSTRAINT materiales_tipo_fkey FOREIGN KEY (tipo) REFERENCES public.parametros(id);


--
-- TOC entry 3048 (class 2606 OID 68669)
-- Name: menus menus_menupadre_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.menus
    ADD CONSTRAINT menus_menupadre_fkey FOREIGN KEY (menupadre) REFERENCES public.menus(id);


--
-- TOC entry 3049 (class 2606 OID 68674)
-- Name: menus menus_modulo_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.menus
    ADD CONSTRAINT menus_modulo_fkey FOREIGN KEY (modulo) REFERENCES public.parametros(id);


--
-- TOC entry 3050 (class 2606 OID 68679)
-- Name: ordenes ordenes_formula_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ordenes
    ADD CONSTRAINT ordenes_formula_fkey FOREIGN KEY (formula) REFERENCES public.formulas(id);


--
-- TOC entry 3051 (class 2606 OID 68684)
-- Name: ordenes ordenes_laboratorio_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ordenes
    ADD CONSTRAINT ordenes_laboratorio_fkey FOREIGN KEY (laboratorio) REFERENCES public.instituciones(id);


--
-- TOC entry 3054 (class 2606 OID 68799)
-- Name: pacientes pacientes_fotografia_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pacientes
    ADD CONSTRAINT pacientes_fotografia_fkey FOREIGN KEY (fotografia) REFERENCES public.archivos(id);


--
-- TOC entry 3052 (class 2606 OID 68689)
-- Name: pacientes pacientes_institucion_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pacientes
    ADD CONSTRAINT pacientes_institucion_fkey FOREIGN KEY (institucion) REFERENCES public.instituciones(id);


--
-- TOC entry 3053 (class 2606 OID 68694)
-- Name: pacientes pacientes_persona_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pacientes
    ADD CONSTRAINT pacientes_persona_fkey FOREIGN KEY (persona) REFERENCES public.personas(id);


--
-- TOC entry 3055 (class 2606 OID 68699)
-- Name: parametros parametros_maestro_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.parametros
    ADD CONSTRAINT parametros_maestro_fkey FOREIGN KEY (maestro) REFERENCES public.maestros(id);


--
-- TOC entry 3056 (class 2606 OID 68704)
-- Name: perfiles perfiles_grupo_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.perfiles
    ADD CONSTRAINT perfiles_grupo_fkey FOREIGN KEY (grupo) REFERENCES public.parametros(id);


--
-- TOC entry 3057 (class 2606 OID 68709)
-- Name: perfiles perfiles_menu_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.perfiles
    ADD CONSTRAINT perfiles_menu_fkey FOREIGN KEY (menu) REFERENCES public.menus(id);


--
-- TOC entry 3058 (class 2606 OID 68714)
-- Name: personas personas_direccion_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.personas
    ADD CONSTRAINT personas_direccion_fkey FOREIGN KEY (direccion) REFERENCES public.direcciones(id);


--
-- TOC entry 3059 (class 2606 OID 68724)
-- Name: personas personas_genero_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.personas
    ADD CONSTRAINT personas_genero_fkey FOREIGN KEY (genero) REFERENCES public.parametros(id);


--
-- TOC entry 3060 (class 2606 OID 68729)
-- Name: prescripciones prescripciones_atencion_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.prescripciones
    ADD CONSTRAINT prescripciones_atencion_fkey FOREIGN KEY (atencion) REFERENCES public.atenciones(id);


--
-- TOC entry 3061 (class 2606 OID 68734)
-- Name: profesionales profesionales_especialidad_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.profesionales
    ADD CONSTRAINT profesionales_especialidad_fkey FOREIGN KEY (especialidad) REFERENCES public.parametros(id);


--
-- TOC entry 3064 (class 2606 OID 68806)
-- Name: profesionales profesionales_fotografia_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.profesionales
    ADD CONSTRAINT profesionales_fotografia_fkey FOREIGN KEY (fotografia) REFERENCES public.archivos(id);


--
-- TOC entry 3062 (class 2606 OID 68739)
-- Name: profesionales profesionales_institucion_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.profesionales
    ADD CONSTRAINT profesionales_institucion_fkey FOREIGN KEY (institucion) REFERENCES public.instituciones(id);


--
-- TOC entry 3063 (class 2606 OID 68744)
-- Name: profesionales profesionales_persona_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.profesionales
    ADD CONSTRAINT profesionales_persona_fkey FOREIGN KEY (persona) REFERENCES public.personas(id);


--
-- TOC entry 3065 (class 2606 OID 68749)
-- Name: usuarios usuarios_grupo_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuarios
    ADD CONSTRAINT usuarios_grupo_fkey FOREIGN KEY (grupo) REFERENCES public.parametros(id);


--
-- TOC entry 3066 (class 2606 OID 68754)
-- Name: usuarios usuarios_institucion_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuarios
    ADD CONSTRAINT usuarios_institucion_fkey FOREIGN KEY (institucion) REFERENCES public.instituciones(id);


--
-- TOC entry 3067 (class 2606 OID 68759)
-- Name: usuarios usuarios_modulo_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuarios
    ADD CONSTRAINT usuarios_modulo_fkey FOREIGN KEY (modulo) REFERENCES public.parametros(id);


--
-- TOC entry 3068 (class 2606 OID 68764)
-- Name: usuarios usuarios_persona_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuarios
    ADD CONSTRAINT usuarios_persona_fkey FOREIGN KEY (persona) REFERENCES public.personas(id);


--
-- TOC entry 3239 (class 0 OID 0)
-- Dependencies: 5
-- Name: SCHEMA public; Type: ACL; Schema: -; Owner: postgres
--

GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2018-11-05 06:03:52 -05

--
-- PostgreSQL database dump complete
--

