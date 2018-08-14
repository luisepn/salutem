--
-- PostgreSQL database dump
--

-- Dumped from database version 10.4
-- Dumped by pg_dump version 10.4

-- Started on 2018-08-14 17:28:05 -05

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
-- TOC entry 3188 (class 1262 OID 66018)
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
-- TOC entry 3191 (class 0 OID 0)
-- Dependencies: 1
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 196 (class 1259 OID 66019)
-- Name: archivos; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.archivos (
    id integer NOT NULL,
    fecha timestamp without time zone,
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
-- TOC entry 197 (class 1259 OID 66025)
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
-- TOC entry 3192 (class 0 OID 0)
-- Dependencies: 197
-- Name: archivos_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.archivos_id_seq OWNED BY public.archivos.id;


--
-- TOC entry 198 (class 1259 OID 66027)
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
-- TOC entry 199 (class 1259 OID 66033)
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
-- TOC entry 3193 (class 0 OID 0)
-- Dependencies: 199
-- Name: citas_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.citas_id_seq OWNED BY public.citas.id;


--
-- TOC entry 200 (class 1259 OID 66035)
-- Name: consultas; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.consultas (
    id integer NOT NULL,
    fecha timestamp without time zone,
    paciente integer,
    especialidad integer,
    motivo text,
    observaciones text,
    indicaciones text,
    usuario text,
    activo boolean
);


ALTER TABLE public.consultas OWNER TO postgres;

--
-- TOC entry 201 (class 1259 OID 66041)
-- Name: consultas_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.consultas_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.consultas_id_seq OWNER TO postgres;

--
-- TOC entry 3194 (class 0 OID 0)
-- Dependencies: 201
-- Name: consultas_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.consultas_id_seq OWNED BY public.consultas.id;


--
-- TOC entry 202 (class 1259 OID 66043)
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
-- TOC entry 203 (class 1259 OID 66049)
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
-- TOC entry 3195 (class 0 OID 0)
-- Dependencies: 203
-- Name: direcciones_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.direcciones_id_seq OWNED BY public.direcciones.id;


--
-- TOC entry 204 (class 1259 OID 66051)
-- Name: formulas; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.formulas (
    id integer NOT NULL,
    consulta integer,
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
    activo boolean
);


ALTER TABLE public.formulas OWNER TO postgres;

--
-- TOC entry 205 (class 1259 OID 66057)
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
-- TOC entry 3196 (class 0 OID 0)
-- Dependencies: 205
-- Name: formulas_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.formulas_id_seq OWNED BY public.formulas.id;


--
-- TOC entry 206 (class 1259 OID 66059)
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
-- TOC entry 207 (class 1259 OID 66065)
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
-- TOC entry 3197 (class 0 OID 0)
-- Dependencies: 207
-- Name: horarios_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.horarios_id_seq OWNED BY public.horarios.id;


--
-- TOC entry 208 (class 1259 OID 66067)
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
-- TOC entry 209 (class 1259 OID 66073)
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
-- TOC entry 3198 (class 0 OID 0)
-- Dependencies: 209
-- Name: horas_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.horas_id_seq OWNED BY public.horas.id;


--
-- TOC entry 210 (class 1259 OID 66075)
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
-- TOC entry 211 (class 1259 OID 66081)
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
-- TOC entry 3199 (class 0 OID 0)
-- Dependencies: 211
-- Name: instituciones_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.instituciones_id_seq OWNED BY public.instituciones.id;


--
-- TOC entry 212 (class 1259 OID 66083)
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
-- TOC entry 213 (class 1259 OID 66089)
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
-- TOC entry 3200 (class 0 OID 0)
-- Dependencies: 213
-- Name: maestros_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.maestros_id_seq OWNED BY public.maestros.id;


--
-- TOC entry 214 (class 1259 OID 66091)
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
-- TOC entry 215 (class 1259 OID 66097)
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
-- TOC entry 3201 (class 0 OID 0)
-- Dependencies: 215
-- Name: materiales_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.materiales_id_seq OWNED BY public.materiales.id;


--
-- TOC entry 216 (class 1259 OID 66099)
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
-- TOC entry 217 (class 1259 OID 66105)
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
-- TOC entry 3202 (class 0 OID 0)
-- Dependencies: 217
-- Name: menus_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.menus_id_seq OWNED BY public.menus.id;


--
-- TOC entry 218 (class 1259 OID 66107)
-- Name: ordenes; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.ordenes (
    id integer NOT NULL,
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
    activo boolean
);


ALTER TABLE public.ordenes OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 66113)
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
-- TOC entry 3203 (class 0 OID 0)
-- Dependencies: 219
-- Name: ordenes_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.ordenes_id_seq OWNED BY public.ordenes.id;


--
-- TOC entry 220 (class 1259 OID 66115)
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
    actualizadopor text
);


ALTER TABLE public.pacientes OWNER TO postgres;

--
-- TOC entry 221 (class 1259 OID 66121)
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
-- TOC entry 3204 (class 0 OID 0)
-- Dependencies: 221
-- Name: pacientes_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.pacientes_id_seq OWNED BY public.pacientes.id;


--
-- TOC entry 222 (class 1259 OID 66123)
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
-- TOC entry 223 (class 1259 OID 66129)
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
-- TOC entry 3205 (class 0 OID 0)
-- Dependencies: 223
-- Name: parametros_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.parametros_id_seq OWNED BY public.parametros.id;


--
-- TOC entry 224 (class 1259 OID 66131)
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
-- TOC entry 225 (class 1259 OID 66137)
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
-- TOC entry 3206 (class 0 OID 0)
-- Dependencies: 225
-- Name: perfiles_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.perfiles_id_seq OWNED BY public.perfiles.id;


--
-- TOC entry 226 (class 1259 OID 66139)
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
    fotografia integer,
    genero integer,
    descripcion text,
    creado timestamp without time zone,
    creadopor text,
    actualizado timestamp without time zone,
    actualizadopor text
);


ALTER TABLE public.personas OWNER TO postgres;

--
-- TOC entry 227 (class 1259 OID 66146)
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
-- TOC entry 3207 (class 0 OID 0)
-- Dependencies: 227
-- Name: personas_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.personas_id_seq OWNED BY public.personas.id;


--
-- TOC entry 228 (class 1259 OID 66148)
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
    actualizadopor text
);


ALTER TABLE public.profesionales OWNER TO postgres;

--
-- TOC entry 229 (class 1259 OID 66154)
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
-- TOC entry 3208 (class 0 OID 0)
-- Dependencies: 229
-- Name: profesionales_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.profesionales_id_seq OWNED BY public.profesionales.id;


--
-- TOC entry 230 (class 1259 OID 66156)
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
-- TOC entry 231 (class 1259 OID 66162)
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
-- TOC entry 3209 (class 0 OID 0)
-- Dependencies: 231
-- Name: usuarios_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.usuarios_id_seq OWNED BY public.usuarios.id;


--
-- TOC entry 2919 (class 2604 OID 66164)
-- Name: archivos id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.archivos ALTER COLUMN id SET DEFAULT nextval('public.archivos_id_seq'::regclass);


--
-- TOC entry 2920 (class 2604 OID 66165)
-- Name: citas id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.citas ALTER COLUMN id SET DEFAULT nextval('public.citas_id_seq'::regclass);


--
-- TOC entry 2921 (class 2604 OID 66166)
-- Name: consultas id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.consultas ALTER COLUMN id SET DEFAULT nextval('public.consultas_id_seq'::regclass);


--
-- TOC entry 2922 (class 2604 OID 66167)
-- Name: direcciones id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.direcciones ALTER COLUMN id SET DEFAULT nextval('public.direcciones_id_seq'::regclass);


--
-- TOC entry 2923 (class 2604 OID 66168)
-- Name: formulas id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.formulas ALTER COLUMN id SET DEFAULT nextval('public.formulas_id_seq'::regclass);


--
-- TOC entry 2924 (class 2604 OID 66169)
-- Name: horarios id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.horarios ALTER COLUMN id SET DEFAULT nextval('public.horarios_id_seq'::regclass);


--
-- TOC entry 2925 (class 2604 OID 66170)
-- Name: horas id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.horas ALTER COLUMN id SET DEFAULT nextval('public.horas_id_seq'::regclass);


--
-- TOC entry 2926 (class 2604 OID 66171)
-- Name: instituciones id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.instituciones ALTER COLUMN id SET DEFAULT nextval('public.instituciones_id_seq'::regclass);


--
-- TOC entry 2927 (class 2604 OID 66172)
-- Name: maestros id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.maestros ALTER COLUMN id SET DEFAULT nextval('public.maestros_id_seq'::regclass);


--
-- TOC entry 2928 (class 2604 OID 66173)
-- Name: materiales id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.materiales ALTER COLUMN id SET DEFAULT nextval('public.materiales_id_seq'::regclass);


--
-- TOC entry 2929 (class 2604 OID 66174)
-- Name: menus id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.menus ALTER COLUMN id SET DEFAULT nextval('public.menus_id_seq'::regclass);


--
-- TOC entry 2930 (class 2604 OID 66175)
-- Name: ordenes id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ordenes ALTER COLUMN id SET DEFAULT nextval('public.ordenes_id_seq'::regclass);


--
-- TOC entry 2931 (class 2604 OID 66176)
-- Name: pacientes id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pacientes ALTER COLUMN id SET DEFAULT nextval('public.pacientes_id_seq'::regclass);


--
-- TOC entry 2932 (class 2604 OID 66177)
-- Name: parametros id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.parametros ALTER COLUMN id SET DEFAULT nextval('public.parametros_id_seq'::regclass);


--
-- TOC entry 2933 (class 2604 OID 66178)
-- Name: perfiles id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.perfiles ALTER COLUMN id SET DEFAULT nextval('public.perfiles_id_seq'::regclass);


--
-- TOC entry 2935 (class 2604 OID 66179)
-- Name: personas id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.personas ALTER COLUMN id SET DEFAULT nextval('public.personas_id_seq'::regclass);


--
-- TOC entry 2936 (class 2604 OID 66180)
-- Name: profesionales id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.profesionales ALTER COLUMN id SET DEFAULT nextval('public.profesionales_id_seq'::regclass);


--
-- TOC entry 2937 (class 2604 OID 66181)
-- Name: usuarios id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuarios ALTER COLUMN id SET DEFAULT nextval('public.usuarios_id_seq'::regclass);


--
-- TOC entry 3147 (class 0 OID 66019)
-- Dependencies: 196
-- Data for Name: archivos; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.archivos (id, fecha, clasificador, identificador, nombre, tipo, ruta, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (2, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.archivos (id, fecha, clasificador, identificador, nombre, tipo, ruta, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (3, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.archivos (id, fecha, clasificador, identificador, nombre, tipo, ruta, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (4, '2018-01-04 20:33:49.324', NULL, NULL, 'Captura de pantalla de 2017-12-28 22-37-57.png', 'image/png', NULL, NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.archivos (id, fecha, clasificador, identificador, nombre, tipo, ruta, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (5, '2018-01-04 20:37:35.354', NULL, NULL, 'Captura de pantalla de 2017-12-18 22-41-39.png', 'image/png', NULL, NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.archivos (id, fecha, clasificador, identificador, nombre, tipo, ruta, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (8, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.archivos (id, fecha, clasificador, identificador, nombre, tipo, ruta, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (9, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.archivos (id, fecha, clasificador, identificador, nombre, tipo, ruta, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (1, '2018-01-12 12:48:39.315', 'Fotografias', NULL, 'Captura de pantalla de 2017-12-18 22-41-39.png', 'image/png', '/home/fernando/Imágenes/salutem/Fotografias/1', NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.archivos (id, fecha, clasificador, identificador, nombre, tipo, ruta, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (12, '2018-01-12 13:31:39.462', 'Fotografias', NULL, 'WallE.jpg', 'image/jpeg', '/home/fernando/Imágenes/salutem/Fotografias/12', NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.archivos (id, fecha, clasificador, identificador, nombre, tipo, ruta, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (13, '2018-01-12 13:49:01.188', 'Fotografias', NULL, 'png.png', 'image/png', '/home/fernando/Imágenes/salutem/Fotografias/13', NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.archivos (id, fecha, clasificador, identificador, nombre, tipo, ruta, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (14, NULL, 'Fotografias', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.archivos (id, fecha, clasificador, identificador, nombre, tipo, ruta, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (11, NULL, 'Fotografias', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.archivos (id, fecha, clasificador, identificador, nombre, tipo, ruta, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (17, NULL, 'Fotografias', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.archivos (id, fecha, clasificador, identificador, nombre, tipo, ruta, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (18, NULL, 'Fotografias', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.archivos (id, fecha, clasificador, identificador, nombre, tipo, ruta, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (19, NULL, 'Fotografias', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.archivos (id, fecha, clasificador, identificador, nombre, tipo, ruta, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (20, NULL, 'Fotografias', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.archivos (id, fecha, clasificador, identificador, nombre, tipo, ruta, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (10, '2018-06-27 14:55:05.332', 'Logotipos', NULL, 'Ubuntu-Wallpapers-5.jpg', 'image/jpeg', '/home/usuario/Imágenes/salutem/Logotipos/10', NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.archivos (id, fecha, clasificador, identificador, nombre, tipo, ruta, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (6, '2018-06-27 14:55:15.674', 'Logotipos', NULL, 'Ubuntu-Wallpapers-5.jpg', 'image/jpeg', '/home/usuario/Imágenes/salutem/Logotipos/6', NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.archivos (id, fecha, clasificador, identificador, nombre, tipo, ruta, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (22, '2018-07-18 11:41:38.816', 'Logotipos', NULL, 'Foto universo.jpg', 'image/jpeg', '/home/usuario/Imágenes/salutem/Logotipos/22', NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.archivos (id, fecha, clasificador, identificador, nombre, tipo, ruta, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (7, '2018-07-18 11:41:48.565', 'Logotipos', NULL, 'nocrackle-2560-1600.png', 'image/png', '/home/usuario/Imágenes/salutem/Logotipos/7', NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.archivos (id, fecha, clasificador, identificador, nombre, tipo, ruta, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (21, '2018-08-12 06:49:35.762', 'Fotografias', NULL, 'png.png', 'image/png', '/home/usuario/Imágenes/salutem/Fotografias/21', NULL, NULL, NULL, '2018-08-12 07:07:27.684', 'imordonez', true);
INSERT INTO public.archivos (id, fecha, clasificador, identificador, nombre, tipo, ruta, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (15, '2018-08-12 06:49:25.625', 'Fotografias', NULL, '4k-wallpapers-Is-4K-Wallpaper.jpg', 'image/jpeg', '/home/usuario/Imágenes/salutem/Fotografias/15', NULL, NULL, NULL, '2018-08-14 00:21:43.584', 'imordonez', true);
INSERT INTO public.archivos (id, fecha, clasificador, identificador, nombre, tipo, ruta, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (16, '2018-08-12 06:49:16.932', 'Fotografias', NULL, 'Pg_logo.png', 'image/png', '/home/usuario/Imágenes/salutem/Fotografias/16', NULL, NULL, NULL, '2018-08-14 00:22:26.57', 'imordonez', true);


--
-- TOC entry 3149 (class 0 OID 66027)
-- Dependencies: 198
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


--
-- TOC entry 3151 (class 0 OID 66035)
-- Dependencies: 200
-- Data for Name: consultas; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.consultas (id, fecha, paciente, especialidad, motivo, observaciones, indicaciones, usuario, activo) VALUES (1, '2018-06-25 09:50:57.145', 10, NULL, NULL, '', '', NULL, NULL);


--
-- TOC entry 3153 (class 0 OID 66043)
-- Dependencies: 202
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
INSERT INTO public.direcciones (id, primaria, numero, secundaria, piso, referencia, fijo, movil, ciudad, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (17, '', '', '', '', '', '', '', '', NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.direcciones (id, primaria, numero, secundaria, piso, referencia, fijo, movil, ciudad, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (18, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.direcciones (id, primaria, numero, secundaria, piso, referencia, fijo, movil, ciudad, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (19, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.direcciones (id, primaria, numero, secundaria, piso, referencia, fijo, movil, ciudad, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (20, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.direcciones (id, primaria, numero, secundaria, piso, referencia, fijo, movil, ciudad, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (21, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.direcciones (id, primaria, numero, secundaria, piso, referencia, fijo, movil, ciudad, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (22, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.direcciones (id, primaria, numero, secundaria, piso, referencia, fijo, movil, ciudad, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (23, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.direcciones (id, primaria, numero, secundaria, piso, referencia, fijo, movil, ciudad, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (24, 'Calle 1', 'N56', 'Calle 2', '', '', '3030160', '', 'Quito', NULL, NULL, NULL, NULL, NULL, true);


--
-- TOC entry 3155 (class 0 OID 66051)
-- Dependencies: 204
-- Data for Name: formulas; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.formulas (id, consulta, lod, loi, avscod, avscoi, avccod, avccoi, esferaod, esferaoi, cilindrood, cilindrooi, ejeod, ejeoi, addod, addoi, dpod, dpoi, avod, avoi, material, altura, tratamiento, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (1, 1, '', NULL, '', '', '', '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, NULL);


--
-- TOC entry 3157 (class 0 OID 66059)
-- Dependencies: 206
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
INSERT INTO public.horarios (id, profesional, hora, dia, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (1, 1, 1, 35, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-14 00:36:49.504', 'imordonez', true);


--
-- TOC entry 3159 (class 0 OID 66067)
-- Dependencies: 208
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
-- TOC entry 3161 (class 0 OID 66075)
-- Dependencies: 210
-- Data for Name: instituciones; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.instituciones (id, nombre, fecha, direccion, logotipo, email, web, laboratorio, activo, descripcion, creado, creadopor, actualizado, actualizadopor) VALUES (1, 'Salud Integral para Todos', '2006-05-10', 2, 7, 'fvptfloresta@hotmail.com', 'https://www.facebook.com/saludintegralfloresta', false, true, '', NULL, NULL, NULL, NULL);
INSERT INTO public.instituciones (id, nombre, fecha, direccion, logotipo, email, web, laboratorio, activo, descripcion, creado, creadopor, actualizado, actualizadopor) VALUES (4, 'Iess', '2018-07-13', 24, 22, 'iess@iess.com.ec', 'www.iess.com', false, true, '', NULL, NULL, NULL, NULL);


--
-- TOC entry 3163 (class 0 OID 66083)
-- Dependencies: 212
-- Data for Name: maestros; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.maestros (id, codigo, nombre, activo, descripcion, creado, creadopor, actualizado, actualizadopor) VALUES (8, 'C001', 'Maestro001', true, '', '2017-07-11 08:07:24.181', NULL, '2018-07-11 08:07:24.181', 'root');
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


--
-- TOC entry 3165 (class 0 OID 66091)
-- Dependencies: 214
-- Data for Name: materiales; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.materiales (id, nombre, tipo, activo, foco, descripcion, creado, creadopor, actualizado, actualizadopor, codigo) VALUES (3, 'F/TPHOTOGRAY', 29, true, 27, '', NULL, NULL, '2018-07-13 13:38:08.17', 'root', 'M004');
INSERT INTO public.materiales (id, nombre, tipo, activo, foco, descripcion, creado, creadopor, actualizado, actualizadopor, codigo) VALUES (4, 'Material 003', 30, true, 28, '', '2018-07-13 13:37:50.767', 'root', '2018-07-13 13:40:29.612', 'root', 'M003');
INSERT INTO public.materiales (id, nombre, tipo, activo, foco, descripcion, creado, creadopor, actualizado, actualizadopor, codigo) VALUES (1, 'Airwear blanco', 29, true, 27, '', NULL, NULL, '2018-07-13 13:40:59.024', 'root', 'M001');


--
-- TOC entry 3167 (class 0 OID 66099)
-- Dependencies: 216
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
INSERT INTO public.menus (id, nombre, formulario, menupadre, modulo, descripcion, creado, creadopor, actualizado, actualizadopor, activo, codigo, icono) VALUES (19, 'Consultas', 'Consultas', 16, NULL, '', NULL, NULL, '2018-08-14 00:20:11.827', 'root', true, '03', 'ui-icon ui-icon-bookmark');


--
-- TOC entry 3169 (class 0 OID 66107)
-- Dependencies: 218
-- Data for Name: ordenes; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.ordenes (id, formula, factura, usuario, laboratorio, registro, envio, entrega, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (1, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);


--
-- TOC entry 3171 (class 0 OID 66115)
-- Dependencies: 220
-- Data for Name: pacientes; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.pacientes (id, persona, institucion, activo, descripcion, creado, creadopor, actualizado, actualizadopor) VALUES (10, 3, 1, true, NULL, NULL, NULL, NULL, NULL);
INSERT INTO public.pacientes (id, persona, institucion, activo, descripcion, creado, creadopor, actualizado, actualizadopor) VALUES (11, 1, 1, true, NULL, NULL, NULL, NULL, NULL);


--
-- TOC entry 3173 (class 0 OID 66123)
-- Dependencies: 222
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
INSERT INTO public.parametros (id, maestro, nombre, codigo, descripcion, parametros, activo, creado, creadopor, actualizado, actualizadopor) VALUES (42, 11, 'Cédula', 'BCD', 'Búsqueda por cédula de identidad', 'o.persona.cedula', true, '2018-07-27 15:02:28.073', 'root', '2018-07-27 15:03:29.833', 'root');
INSERT INTO public.parametros (id, maestro, nombre, codigo, descripcion, parametros, activo, creado, creadopor, actualizado, actualizadopor) VALUES (43, 11, 'Nombres', 'BNM', 'Búsqueda por nombres de paciente', 'o.persona.nombres', true, '2018-07-27 15:04:53.749', 'root', '2018-07-27 15:04:53.749', 'root');
INSERT INTO public.parametros (id, maestro, nombre, codigo, descripcion, parametros, activo, creado, creadopor, actualizado, actualizadopor) VALUES (44, 11, 'Apellidos', 'BAP', 'Búsqueda por Apellidos', 'o.persona.apellidos', true, '2018-07-27 15:05:35.017', 'root', '2018-07-27 15:05:35.017', 'root');
INSERT INTO public.parametros (id, maestro, nombre, codigo, descripcion, parametros, activo, creado, creadopor, actualizado, actualizadopor) VALUES (35, 10, 'Lunes', 'L', 'Lunes', '1', true, '2018-07-24 15:56:57.423', 'root', '2018-08-02 08:51:14.309', 'root');
INSERT INTO public.parametros (id, maestro, nombre, codigo, descripcion, parametros, activo, creado, creadopor, actualizado, actualizadopor) VALUES (36, 10, 'Martes', 'M', 'Martes', '2', true, '2018-07-24 15:57:21.57', 'root', '2018-08-02 08:51:20.039', 'root');
INSERT INTO public.parametros (id, maestro, nombre, codigo, descripcion, parametros, activo, creado, creadopor, actualizado, actualizadopor) VALUES (37, 10, 'Miércoles', 'X', 'Miércoles', '3', true, '2018-07-24 15:57:40.284', 'root', '2018-08-02 08:51:34.458', 'root');
INSERT INTO public.parametros (id, maestro, nombre, codigo, descripcion, parametros, activo, creado, creadopor, actualizado, actualizadopor) VALUES (38, 10, 'Jueves', 'J', 'Jueves', '4', true, '2018-07-24 15:57:54.795', 'root', '2018-08-02 08:51:39.381', 'root');
INSERT INTO public.parametros (id, maestro, nombre, codigo, descripcion, parametros, activo, creado, creadopor, actualizado, actualizadopor) VALUES (39, 10, 'Viernes', 'V', 'Viernes', '5', true, '2018-07-24 15:58:12.296', 'root', '2018-08-02 08:51:44.639', 'root');
INSERT INTO public.parametros (id, maestro, nombre, codigo, descripcion, parametros, activo, creado, creadopor, actualizado, actualizadopor) VALUES (40, 10, 'Sábado', 'S', 'Sábado', '6', true, '2018-07-24 15:59:09.493', 'root', '2018-08-02 08:51:50.346', 'root');
INSERT INTO public.parametros (id, maestro, nombre, codigo, descripcion, parametros, activo, creado, creadopor, actualizado, actualizadopor) VALUES (45, 8, 'P001', 'P001', 'P001', '', true, '2018-08-12 07:09:37.429', 'root', '2018-08-12 07:09:37.429', 'root');


--
-- TOC entry 3175 (class 0 OID 66131)
-- Dependencies: 224
-- Data for Name: perfiles; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.perfiles (id, menu, consulta, modificacion, borrado, nuevo, grupo, activo, descripcion, creado, creadopor, actualizado, actualizadopor, auditoria) VALUES (16, 12, true, true, true, true, 8, true, NULL, '2018-07-13 10:03:31.181', 'root', '2018-07-13 10:03:31.181', 'root', NULL);
INSERT INTO public.perfiles (id, menu, consulta, modificacion, borrado, nuevo, grupo, activo, descripcion, creado, creadopor, actualizado, actualizadopor, auditoria) VALUES (17, 21, true, true, true, true, 4, true, NULL, '2018-07-18 11:47:10.089', 'root', '2018-07-18 11:47:10.089', 'root', NULL);
INSERT INTO public.perfiles (id, menu, consulta, modificacion, borrado, nuevo, grupo, activo, descripcion, creado, creadopor, actualizado, actualizadopor, auditoria) VALUES (18, 21, true, true, true, true, 7, true, NULL, '2018-07-24 13:56:52.784', 'root', '2018-07-24 13:56:52.784', 'root', NULL);
INSERT INTO public.perfiles (id, menu, consulta, modificacion, borrado, nuevo, grupo, activo, descripcion, creado, creadopor, actualizado, actualizadopor, auditoria) VALUES (19, 18, true, true, true, true, 7, true, NULL, '2018-07-24 13:57:10.646', 'root', '2018-07-24 13:57:10.646', 'root', NULL);
INSERT INTO public.perfiles (id, menu, consulta, modificacion, borrado, nuevo, grupo, activo, descripcion, creado, creadopor, actualizado, actualizadopor, auditoria) VALUES (20, 19, true, true, true, true, 7, true, NULL, '2018-07-24 13:57:26.184', 'root', '2018-07-24 13:57:26.184', 'root', NULL);
INSERT INTO public.perfiles (id, menu, consulta, modificacion, borrado, nuevo, grupo, activo, descripcion, creado, creadopor, actualizado, actualizadopor, auditoria) VALUES (21, 20, true, true, true, true, 7, true, NULL, '2018-07-24 13:57:36.48', 'root', '2018-07-24 13:57:36.48', 'root', NULL);
INSERT INTO public.perfiles (id, menu, consulta, modificacion, borrado, nuevo, grupo, activo, descripcion, creado, creadopor, actualizado, actualizadopor, auditoria) VALUES (22, 22, true, true, true, true, 7, true, NULL, '2018-07-24 14:03:09.686', 'root', '2018-07-24 14:03:09.686', 'root', NULL);
INSERT INTO public.perfiles (id, menu, consulta, modificacion, borrado, nuevo, grupo, activo, descripcion, creado, creadopor, actualizado, actualizadopor, auditoria) VALUES (23, 23, true, true, true, true, 7, true, NULL, '2018-07-24 14:03:51.495', 'root', '2018-07-24 14:03:51.495', 'root', NULL);
INSERT INTO public.perfiles (id, menu, consulta, modificacion, borrado, nuevo, grupo, activo, descripcion, creado, creadopor, actualizado, actualizadopor, auditoria) VALUES (24, 24, true, true, true, true, 7, true, NULL, '2018-08-02 08:14:02.77', 'root', '2018-08-02 08:14:02.77', 'root', NULL);
INSERT INTO public.perfiles (id, menu, consulta, modificacion, borrado, nuevo, grupo, activo, descripcion, creado, creadopor, actualizado, actualizadopor, auditoria) VALUES (5, 5, true, true, true, true, 4, true, NULL, '2018-07-13 09:58:26.937227', 'root', '2018-07-13 09:59:16.44678', 'root', NULL);
INSERT INTO public.perfiles (id, menu, consulta, modificacion, borrado, nuevo, grupo, activo, descripcion, creado, creadopor, actualizado, actualizadopor, auditoria) VALUES (6, 6, true, true, true, true, 4, true, NULL, '2018-07-13 09:58:26.937227', 'root', '2018-07-13 09:59:16.44678', 'root', NULL);
INSERT INTO public.perfiles (id, menu, consulta, modificacion, borrado, nuevo, grupo, activo, descripcion, creado, creadopor, actualizado, actualizadopor, auditoria) VALUES (7, 7, true, true, true, true, 4, true, NULL, '2018-07-13 09:58:26.937227', 'root', '2018-07-13 09:59:16.44678', 'root', NULL);
INSERT INTO public.perfiles (id, menu, consulta, modificacion, borrado, nuevo, grupo, activo, descripcion, creado, creadopor, actualizado, actualizadopor, auditoria) VALUES (8, 8, true, true, true, true, 4, true, NULL, '2018-07-13 09:58:26.937227', 'root', '2018-07-13 09:59:16.44678', 'root', NULL);
INSERT INTO public.perfiles (id, menu, consulta, modificacion, borrado, nuevo, grupo, activo, descripcion, creado, creadopor, actualizado, actualizadopor, auditoria) VALUES (9, 9, true, true, true, true, 4, true, NULL, '2018-07-13 09:58:26.937227', 'root', '2018-07-13 09:59:16.44678', 'root', NULL);
INSERT INTO public.perfiles (id, menu, consulta, modificacion, borrado, nuevo, grupo, activo, descripcion, creado, creadopor, actualizado, actualizadopor, auditoria) VALUES (10, 10, true, true, true, true, 4, true, NULL, '2018-07-13 09:58:26.937227', 'root', '2018-07-13 09:59:16.44678', 'root', NULL);
INSERT INTO public.perfiles (id, menu, consulta, modificacion, borrado, nuevo, grupo, activo, descripcion, creado, creadopor, actualizado, actualizadopor, auditoria) VALUES (11, 11, true, true, true, true, 4, true, NULL, '2018-07-13 09:58:26.937227', 'root', '2018-07-13 09:59:16.44678', 'root', NULL);
INSERT INTO public.perfiles (id, menu, consulta, modificacion, borrado, nuevo, grupo, activo, descripcion, creado, creadopor, actualizado, actualizadopor, auditoria) VALUES (12, 12, true, true, true, true, 4, true, NULL, '2018-07-13 09:58:26.937227', 'root', '2018-07-13 09:59:16.44678', 'root', NULL);
INSERT INTO public.perfiles (id, menu, consulta, modificacion, borrado, nuevo, grupo, activo, descripcion, creado, creadopor, actualizado, actualizadopor, auditoria) VALUES (13, 18, true, true, true, true, 4, true, NULL, '2018-07-13 09:58:26.937227', 'root', '2018-07-13 09:59:16.44678', 'root', NULL);
INSERT INTO public.perfiles (id, menu, consulta, modificacion, borrado, nuevo, grupo, activo, descripcion, creado, creadopor, actualizado, actualizadopor, auditoria) VALUES (14, 19, true, true, true, true, 4, true, NULL, '2018-07-13 09:58:26.937227', 'root', '2018-07-13 09:59:16.44678', 'root', NULL);
INSERT INTO public.perfiles (id, menu, consulta, modificacion, borrado, nuevo, grupo, activo, descripcion, creado, creadopor, actualizado, actualizadopor, auditoria) VALUES (15, 20, true, true, true, true, 4, true, NULL, '2018-07-13 09:58:26.937227', 'root', '2018-07-13 09:59:16.44678', 'root', NULL);
INSERT INTO public.perfiles (id, menu, consulta, modificacion, borrado, nuevo, grupo, activo, descripcion, creado, creadopor, actualizado, actualizadopor, auditoria) VALUES (4, 4, true, true, true, true, 4, true, NULL, '2018-07-13 09:58:26.937227', 'root', '2018-07-13 09:59:16.44678', 'root', NULL);


--
-- TOC entry 3177 (class 0 OID 66139)
-- Dependencies: 226
-- Data for Name: personas; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.personas (id, nombres, apellidos, email, userid, clave, cedula, fecha, direccion, rol, activo, ocupacion, fotografia, genero, descripcion, creado, creadopor, actualizado, actualizadopor) VALUES (2, 'Julio Cesar', 'Villalba Guachamin', 'juliocvillalbag@hotmail.com', 'jcvillalba', '502ff82f7f1f8218dd41201fe4353687', '1703325934', '1951-04-12', NULL, NULL, true, 'Ingeniero Electrónico', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO public.personas (id, nombres, apellidos, email, userid, clave, cedula, fecha, direccion, rol, activo, ocupacion, fotografia, genero, descripcion, creado, creadopor, actualizado, actualizadopor) VALUES (5, 'Andrea Paola', 'Villalba Casagallo', 'pao.landyllita@hotmail.com', 'pavillalba', '502ff82f7f1f8218dd41201fe4353687', '1720731320', '1991-05-02', NULL, NULL, true, 'Administradora de Empresas', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO public.personas (id, nombres, apellidos, email, userid, clave, cedula, fecha, direccion, rol, activo, ocupacion, fotografia, genero, descripcion, creado, creadopor, actualizado, actualizadopor) VALUES (14, 'José', 'Rodríguez', 'cruzjonathan705@gmail.com', '1722227004', '6e17bb67cc18cf43f3f1b713ed4e2d82', '1722227004', '1993-04-21', 23, NULL, true, '', 21, 10, '', NULL, NULL, '2018-08-12 07:07:27.696', 'imordonez');
INSERT INTO public.personas (id, nombres, apellidos, email, userid, clave, cedula, fecha, direccion, rol, activo, ocupacion, fotografia, genero, descripcion, creado, creadopor, actualizado, actualizadopor) VALUES (3, 'Isabel Macrina', 'Ordóñez Armijos', 'macrina.ordonez@gmail.com', 'imordonez', '502ff82f7f1f8218dd41201fe4353687', '1725351777', '1992-08-16', 17, NULL, true, 'DIseñadora Gráfica', 15, 9, '', NULL, NULL, '2018-08-14 00:21:43.592', 'imordonez');
INSERT INTO public.personas (id, nombres, apellidos, email, userid, clave, cedula, fecha, direccion, rol, activo, ocupacion, fotografia, genero, descripcion, creado, creadopor, actualizado, actualizadopor) VALUES (1, 'Luis Fernando', 'Ordóñez Armijos', 'louis.fercho@gmail.com', 'root', '502ff82f7f1f8218dd41201fe4353687', '1725351736', '1991-05-15', 18, NULL, true, 'Ingeniero Informático', 16, 10, '', NULL, NULL, '2018-08-14 00:22:26.585', 'imordonez');


--
-- TOC entry 3179 (class 0 OID 66148)
-- Dependencies: 228
-- Data for Name: profesionales; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.profesionales (id, persona, especialidad, institucion, activo, descripcion, creado, creadopor, actualizado, actualizadopor) VALUES (1, 1, 31, 1, true, NULL, '2018-07-24 14:00:37.478', 'imordonez', '2018-08-12 07:06:54.665', 'imordonez');
INSERT INTO public.profesionales (id, persona, especialidad, institucion, activo, descripcion, creado, creadopor, actualizado, actualizadopor) VALUES (3, 14, 32, 1, true, NULL, '2018-07-24 18:09:08.391', 'imordonez', '2018-08-12 07:07:27.716', 'imordonez');
INSERT INTO public.profesionales (id, persona, especialidad, institucion, activo, descripcion, creado, creadopor, actualizado, actualizadopor) VALUES (2, 3, 31, 1, true, NULL, '2018-07-24 18:05:53.302', 'imordonez', '2018-08-12 07:59:28.309', 'imordonez');


--
-- TOC entry 3181 (class 0 OID 66156)
-- Dependencies: 230
-- Data for Name: usuarios; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.usuarios (id, modulo, grupo, institucion, persona, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (1, 1, 4, NULL, 1, NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.usuarios (id, modulo, grupo, institucion, persona, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (7, 3, 7, 1, 14, NULL, '2018-07-12 16:46:51.825', 'root', '2018-07-12 16:56:15.187', 'root', true);
INSERT INTO public.usuarios (id, modulo, grupo, institucion, persona, descripcion, creado, creadopor, actualizado, actualizadopor, activo) VALUES (8, 2, 7, 1, 3, NULL, '2018-07-24 13:54:50.415', 'root', '2018-07-24 13:54:50.415', 'root', true);


--
-- TOC entry 3210 (class 0 OID 0)
-- Dependencies: 197
-- Name: archivos_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.archivos_id_seq', 22, true);


--
-- TOC entry 3211 (class 0 OID 0)
-- Dependencies: 199
-- Name: citas_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.citas_id_seq', 28, true);


--
-- TOC entry 3212 (class 0 OID 0)
-- Dependencies: 201
-- Name: consultas_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.consultas_id_seq', 1, true);


--
-- TOC entry 3213 (class 0 OID 0)
-- Dependencies: 203
-- Name: direcciones_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.direcciones_id_seq', 24, true);


--
-- TOC entry 3214 (class 0 OID 0)
-- Dependencies: 205
-- Name: formulas_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.formulas_id_seq', 1, true);


--
-- TOC entry 3215 (class 0 OID 0)
-- Dependencies: 207
-- Name: horarios_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.horarios_id_seq', 168, true);


--
-- TOC entry 3216 (class 0 OID 0)
-- Dependencies: 209
-- Name: horas_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.horas_id_seq', 24, true);


--
-- TOC entry 3217 (class 0 OID 0)
-- Dependencies: 211
-- Name: instituciones_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.instituciones_id_seq', 4, true);


--
-- TOC entry 3218 (class 0 OID 0)
-- Dependencies: 213
-- Name: maestros_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.maestros_id_seq', 11, true);


--
-- TOC entry 3219 (class 0 OID 0)
-- Dependencies: 215
-- Name: materiales_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.materiales_id_seq', 4, true);


--
-- TOC entry 3220 (class 0 OID 0)
-- Dependencies: 217
-- Name: menus_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.menus_id_seq', 24, true);


--
-- TOC entry 3221 (class 0 OID 0)
-- Dependencies: 219
-- Name: ordenes_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.ordenes_id_seq', 1, true);


--
-- TOC entry 3222 (class 0 OID 0)
-- Dependencies: 221
-- Name: pacientes_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.pacientes_id_seq', 11, true);


--
-- TOC entry 3223 (class 0 OID 0)
-- Dependencies: 223
-- Name: parametros_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.parametros_id_seq', 45, true);


--
-- TOC entry 3224 (class 0 OID 0)
-- Dependencies: 225
-- Name: perfiles_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.perfiles_id_seq', 24, true);


--
-- TOC entry 3225 (class 0 OID 0)
-- Dependencies: 227
-- Name: personas_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.personas_id_seq', 14, true);


--
-- TOC entry 3226 (class 0 OID 0)
-- Dependencies: 229
-- Name: profesionales_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.profesionales_id_seq', 3, true);


--
-- TOC entry 3227 (class 0 OID 0)
-- Dependencies: 231
-- Name: usuarios_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.usuarios_id_seq', 8, true);


--
-- TOC entry 2939 (class 2606 OID 66183)
-- Name: archivos archivos_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.archivos
    ADD CONSTRAINT archivos_pkey PRIMARY KEY (id);


--
-- TOC entry 2941 (class 2606 OID 66185)
-- Name: citas citas_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.citas
    ADD CONSTRAINT citas_pkey PRIMARY KEY (id);


--
-- TOC entry 2943 (class 2606 OID 66187)
-- Name: consultas consultas_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.consultas
    ADD CONSTRAINT consultas_pkey PRIMARY KEY (id);


--
-- TOC entry 2945 (class 2606 OID 66189)
-- Name: direcciones direcciones_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.direcciones
    ADD CONSTRAINT direcciones_pkey PRIMARY KEY (id);


--
-- TOC entry 2947 (class 2606 OID 66191)
-- Name: formulas formulas_consulta_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.formulas
    ADD CONSTRAINT formulas_consulta_key UNIQUE (consulta);


--
-- TOC entry 2949 (class 2606 OID 66193)
-- Name: formulas formulas_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.formulas
    ADD CONSTRAINT formulas_pkey PRIMARY KEY (id);


--
-- TOC entry 2951 (class 2606 OID 66195)
-- Name: horarios horarios_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.horarios
    ADD CONSTRAINT horarios_pkey PRIMARY KEY (id);


--
-- TOC entry 2953 (class 2606 OID 66197)
-- Name: horas horas_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.horas
    ADD CONSTRAINT horas_pkey PRIMARY KEY (id);


--
-- TOC entry 2955 (class 2606 OID 66199)
-- Name: instituciones instituciones_direccion_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.instituciones
    ADD CONSTRAINT instituciones_direccion_key UNIQUE (direccion);


--
-- TOC entry 2957 (class 2606 OID 66201)
-- Name: instituciones instituciones_logotipo_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.instituciones
    ADD CONSTRAINT instituciones_logotipo_key UNIQUE (logotipo);


--
-- TOC entry 2959 (class 2606 OID 66203)
-- Name: instituciones instituciones_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.instituciones
    ADD CONSTRAINT instituciones_pkey PRIMARY KEY (id);


--
-- TOC entry 2961 (class 2606 OID 66205)
-- Name: maestros maestro_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.maestros
    ADD CONSTRAINT maestro_pkey PRIMARY KEY (id);


--
-- TOC entry 2963 (class 2606 OID 66207)
-- Name: maestros maestros_codigo_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.maestros
    ADD CONSTRAINT maestros_codigo_key UNIQUE (codigo);


--
-- TOC entry 2965 (class 2606 OID 66209)
-- Name: materiales materiales_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.materiales
    ADD CONSTRAINT materiales_pkey PRIMARY KEY (id);


--
-- TOC entry 2967 (class 2606 OID 66211)
-- Name: menus menus_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.menus
    ADD CONSTRAINT menus_pkey PRIMARY KEY (id);


--
-- TOC entry 2969 (class 2606 OID 66213)
-- Name: ordenes ordenes_formula_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ordenes
    ADD CONSTRAINT ordenes_formula_key UNIQUE (formula);


--
-- TOC entry 2971 (class 2606 OID 66215)
-- Name: ordenes ordenes_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ordenes
    ADD CONSTRAINT ordenes_pkey PRIMARY KEY (id);


--
-- TOC entry 2973 (class 2606 OID 66217)
-- Name: pacientes pacientes_persona_institucion_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pacientes
    ADD CONSTRAINT pacientes_persona_institucion_key UNIQUE (persona, institucion);


--
-- TOC entry 2975 (class 2606 OID 66219)
-- Name: pacientes pacientes_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pacientes
    ADD CONSTRAINT pacientes_pkey PRIMARY KEY (id);


--
-- TOC entry 2977 (class 2606 OID 66221)
-- Name: parametros parametros_maestro_codigo_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.parametros
    ADD CONSTRAINT parametros_maestro_codigo_key UNIQUE (maestro, codigo);


--
-- TOC entry 2979 (class 2606 OID 66223)
-- Name: parametros parametros_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.parametros
    ADD CONSTRAINT parametros_pkey PRIMARY KEY (id);


--
-- TOC entry 2981 (class 2606 OID 66225)
-- Name: perfiles perfiles_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.perfiles
    ADD CONSTRAINT perfiles_pkey PRIMARY KEY (id);


--
-- TOC entry 2983 (class 2606 OID 66227)
-- Name: personas personas_direccion_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.personas
    ADD CONSTRAINT personas_direccion_key UNIQUE (direccion);


--
-- TOC entry 2985 (class 2606 OID 66229)
-- Name: personas personas_fotografia_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.personas
    ADD CONSTRAINT personas_fotografia_key UNIQUE (fotografia);


--
-- TOC entry 2987 (class 2606 OID 66231)
-- Name: personas personas_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.personas
    ADD CONSTRAINT personas_pkey PRIMARY KEY (id);


--
-- TOC entry 2989 (class 2606 OID 66233)
-- Name: profesionales profesionales_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.profesionales
    ADD CONSTRAINT profesionales_pkey PRIMARY KEY (id);


--
-- TOC entry 2991 (class 2606 OID 66235)
-- Name: usuarios usuarios_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuarios
    ADD CONSTRAINT usuarios_pkey PRIMARY KEY (id);


--
-- TOC entry 2992 (class 2606 OID 66236)
-- Name: citas citas_paciente_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.citas
    ADD CONSTRAINT citas_paciente_fkey FOREIGN KEY (paciente) REFERENCES public.pacientes(id);


--
-- TOC entry 2993 (class 2606 OID 66241)
-- Name: citas citas_profesional_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.citas
    ADD CONSTRAINT citas_profesional_fkey FOREIGN KEY (profesional) REFERENCES public.profesionales(id);


--
-- TOC entry 2994 (class 2606 OID 66246)
-- Name: consultas consultas_especialidad_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.consultas
    ADD CONSTRAINT consultas_especialidad_fkey FOREIGN KEY (especialidad) REFERENCES public.parametros(id);


--
-- TOC entry 2995 (class 2606 OID 66251)
-- Name: consultas consultas_paciente_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.consultas
    ADD CONSTRAINT consultas_paciente_fkey FOREIGN KEY (paciente) REFERENCES public.pacientes(id);


--
-- TOC entry 2996 (class 2606 OID 66256)
-- Name: formulas formulas_consulta_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.formulas
    ADD CONSTRAINT formulas_consulta_fkey FOREIGN KEY (consulta) REFERENCES public.consultas(id);


--
-- TOC entry 2997 (class 2606 OID 66261)
-- Name: formulas formulas_material_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.formulas
    ADD CONSTRAINT formulas_material_fkey FOREIGN KEY (material) REFERENCES public.materiales(id);


--
-- TOC entry 2998 (class 2606 OID 66266)
-- Name: formulas formulas_tratamiento_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.formulas
    ADD CONSTRAINT formulas_tratamiento_fkey FOREIGN KEY (tratamiento) REFERENCES public.parametros(id);


--
-- TOC entry 2999 (class 2606 OID 66271)
-- Name: horarios horarios_dia_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.horarios
    ADD CONSTRAINT horarios_dia_fkey FOREIGN KEY (dia) REFERENCES public.parametros(id);


--
-- TOC entry 3000 (class 2606 OID 66276)
-- Name: horarios horarios_hora_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.horarios
    ADD CONSTRAINT horarios_hora_fkey FOREIGN KEY (hora) REFERENCES public.horas(id);


--
-- TOC entry 3001 (class 2606 OID 66281)
-- Name: horarios horarios_profesional_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.horarios
    ADD CONSTRAINT horarios_profesional_fkey FOREIGN KEY (profesional) REFERENCES public.profesionales(id);


--
-- TOC entry 3002 (class 2606 OID 66286)
-- Name: horas horas_institucion_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.horas
    ADD CONSTRAINT horas_institucion_fkey FOREIGN KEY (institucion) REFERENCES public.instituciones(id);


--
-- TOC entry 3003 (class 2606 OID 66291)
-- Name: instituciones instituciones_direccion_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.instituciones
    ADD CONSTRAINT instituciones_direccion_fkey FOREIGN KEY (direccion) REFERENCES public.direcciones(id);


--
-- TOC entry 3004 (class 2606 OID 66296)
-- Name: instituciones instituciones_logotipo_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.instituciones
    ADD CONSTRAINT instituciones_logotipo_fkey FOREIGN KEY (logotipo) REFERENCES public.archivos(id);


--
-- TOC entry 3005 (class 2606 OID 66301)
-- Name: materiales materiales_foco_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.materiales
    ADD CONSTRAINT materiales_foco_fkey FOREIGN KEY (foco) REFERENCES public.parametros(id);


--
-- TOC entry 3006 (class 2606 OID 66306)
-- Name: materiales materiales_tipo_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.materiales
    ADD CONSTRAINT materiales_tipo_fkey FOREIGN KEY (tipo) REFERENCES public.parametros(id);


--
-- TOC entry 3007 (class 2606 OID 66311)
-- Name: menus menus_menupadre_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.menus
    ADD CONSTRAINT menus_menupadre_fkey FOREIGN KEY (menupadre) REFERENCES public.menus(id);


--
-- TOC entry 3008 (class 2606 OID 66316)
-- Name: menus menus_modulo_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.menus
    ADD CONSTRAINT menus_modulo_fkey FOREIGN KEY (modulo) REFERENCES public.parametros(id);


--
-- TOC entry 3009 (class 2606 OID 66321)
-- Name: ordenes ordenes_formula_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ordenes
    ADD CONSTRAINT ordenes_formula_fkey FOREIGN KEY (formula) REFERENCES public.formulas(id);


--
-- TOC entry 3010 (class 2606 OID 66326)
-- Name: ordenes ordenes_laboratorio_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ordenes
    ADD CONSTRAINT ordenes_laboratorio_fkey FOREIGN KEY (laboratorio) REFERENCES public.instituciones(id);


--
-- TOC entry 3011 (class 2606 OID 66331)
-- Name: pacientes pacientes_institucion_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pacientes
    ADD CONSTRAINT pacientes_institucion_fkey FOREIGN KEY (institucion) REFERENCES public.instituciones(id);


--
-- TOC entry 3012 (class 2606 OID 66336)
-- Name: pacientes pacientes_persona_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pacientes
    ADD CONSTRAINT pacientes_persona_fkey FOREIGN KEY (persona) REFERENCES public.personas(id);


--
-- TOC entry 3013 (class 2606 OID 66341)
-- Name: parametros parametros_maestro_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.parametros
    ADD CONSTRAINT parametros_maestro_fkey FOREIGN KEY (maestro) REFERENCES public.maestros(id);


--
-- TOC entry 3014 (class 2606 OID 66346)
-- Name: perfiles perfiles_grupo_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.perfiles
    ADD CONSTRAINT perfiles_grupo_fkey FOREIGN KEY (grupo) REFERENCES public.parametros(id);


--
-- TOC entry 3015 (class 2606 OID 66351)
-- Name: perfiles perfiles_menu_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.perfiles
    ADD CONSTRAINT perfiles_menu_fkey FOREIGN KEY (menu) REFERENCES public.menus(id);


--
-- TOC entry 3016 (class 2606 OID 66356)
-- Name: personas personas_direccion_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.personas
    ADD CONSTRAINT personas_direccion_fkey FOREIGN KEY (direccion) REFERENCES public.direcciones(id);


--
-- TOC entry 3017 (class 2606 OID 66361)
-- Name: personas personas_fotografia_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.personas
    ADD CONSTRAINT personas_fotografia_fkey FOREIGN KEY (fotografia) REFERENCES public.archivos(id);


--
-- TOC entry 3018 (class 2606 OID 66366)
-- Name: personas personas_genero_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.personas
    ADD CONSTRAINT personas_genero_fkey FOREIGN KEY (genero) REFERENCES public.parametros(id);


--
-- TOC entry 3019 (class 2606 OID 66371)
-- Name: profesionales profesionales_especialidad_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.profesionales
    ADD CONSTRAINT profesionales_especialidad_fkey FOREIGN KEY (especialidad) REFERENCES public.parametros(id);


--
-- TOC entry 3020 (class 2606 OID 66376)
-- Name: profesionales profesionales_institucion_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.profesionales
    ADD CONSTRAINT profesionales_institucion_fkey FOREIGN KEY (institucion) REFERENCES public.instituciones(id);


--
-- TOC entry 3021 (class 2606 OID 66381)
-- Name: profesionales profesionales_persona_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.profesionales
    ADD CONSTRAINT profesionales_persona_fkey FOREIGN KEY (persona) REFERENCES public.personas(id);


--
-- TOC entry 3022 (class 2606 OID 66386)
-- Name: usuarios usuarios_grupo_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuarios
    ADD CONSTRAINT usuarios_grupo_fkey FOREIGN KEY (grupo) REFERENCES public.parametros(id);


--
-- TOC entry 3023 (class 2606 OID 66391)
-- Name: usuarios usuarios_institucion_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuarios
    ADD CONSTRAINT usuarios_institucion_fkey FOREIGN KEY (institucion) REFERENCES public.instituciones(id);


--
-- TOC entry 3024 (class 2606 OID 66396)
-- Name: usuarios usuarios_modulo_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuarios
    ADD CONSTRAINT usuarios_modulo_fkey FOREIGN KEY (modulo) REFERENCES public.parametros(id);


--
-- TOC entry 3025 (class 2606 OID 66401)
-- Name: usuarios usuarios_persona_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuarios
    ADD CONSTRAINT usuarios_persona_fkey FOREIGN KEY (persona) REFERENCES public.personas(id);


--
-- TOC entry 3190 (class 0 OID 0)
-- Dependencies: 5
-- Name: SCHEMA public; Type: ACL; Schema: -; Owner: postgres
--

GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2018-08-14 17:28:05 -05

--
-- PostgreSQL database dump complete
--

