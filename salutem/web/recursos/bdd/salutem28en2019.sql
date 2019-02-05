--
-- PostgreSQL database dump
--

-- Dumped from database version 11.1 (Ubuntu 11.1-1.pgdg18.04+1)
-- Dumped by pg_dump version 11.1 (Ubuntu 11.1-1.pgdg18.04+1)

-- Started on 2019-01-28 00:43:05 -05

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 196 (class 1259 OID 16395)
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
-- TOC entry 197 (class 1259 OID 16401)
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
-- TOC entry 3266 (class 0 OID 0)
-- Dependencies: 197
-- Name: archivos_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.archivos_id_seq OWNED BY public.archivos.id;


--
-- TOC entry 198 (class 1259 OID 16403)
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
-- TOC entry 199 (class 1259 OID 16409)
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
-- TOC entry 3267 (class 0 OID 0)
-- Dependencies: 199
-- Name: atenciones_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.atenciones_id_seq OWNED BY public.atenciones.id;


--
-- TOC entry 200 (class 1259 OID 16411)
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
-- TOC entry 201 (class 1259 OID 16417)
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
-- TOC entry 3268 (class 0 OID 0)
-- Dependencies: 201
-- Name: campos_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.campos_id_seq OWNED BY public.campos.id;


--
-- TOC entry 202 (class 1259 OID 16419)
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
-- TOC entry 203 (class 1259 OID 16425)
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
-- TOC entry 3269 (class 0 OID 0)
-- Dependencies: 203
-- Name: citas_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.citas_id_seq OWNED BY public.citas.id;


--
-- TOC entry 204 (class 1259 OID 16427)
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
-- TOC entry 205 (class 1259 OID 16433)
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
-- TOC entry 3270 (class 0 OID 0)
-- Dependencies: 205
-- Name: datos_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.datos_id_seq OWNED BY public.datos.id;


--
-- TOC entry 206 (class 1259 OID 16435)
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
-- TOC entry 207 (class 1259 OID 16441)
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
-- TOC entry 3271 (class 0 OID 0)
-- Dependencies: 207
-- Name: direcciones_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.direcciones_id_seq OWNED BY public.direcciones.id;


--
-- TOC entry 208 (class 1259 OID 16443)
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
    indicaciones text,
    creado timestamp without time zone,
    creadopor text,
    actualizado timestamp without time zone,
    actualizadopor text,
    activo boolean
);


ALTER TABLE public.formulas OWNER TO postgres;

--
-- TOC entry 209 (class 1259 OID 16449)
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
-- TOC entry 3272 (class 0 OID 0)
-- Dependencies: 209
-- Name: formulas_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.formulas_id_seq OWNED BY public.formulas.id;


--
-- TOC entry 210 (class 1259 OID 16451)
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
-- TOC entry 211 (class 1259 OID 16457)
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
-- TOC entry 3273 (class 0 OID 0)
-- Dependencies: 211
-- Name: horarios_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.horarios_id_seq OWNED BY public.horarios.id;


--
-- TOC entry 212 (class 1259 OID 16459)
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
-- TOC entry 213 (class 1259 OID 16465)
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
-- TOC entry 3274 (class 0 OID 0)
-- Dependencies: 213
-- Name: horas_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.horas_id_seq OWNED BY public.horas.id;


--
-- TOC entry 214 (class 1259 OID 16467)
-- Name: instituciones; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.instituciones (
    id integer NOT NULL,
    nombre text,
    fecha date,
    email text,
    web text,
    laboratorio boolean,
    activo boolean,
    descripcion text,
    creado timestamp without time zone,
    creadopor text,
    actualizado timestamp without time zone,
    actualizadopor text,
    logotipo bytea,
    primaria text,
    numero text,
    secundaria text,
    referencia text,
    fijo text,
    movil text,
    ciudad text,
    ruc text
);


ALTER TABLE public.instituciones OWNER TO postgres;

--
-- TOC entry 215 (class 1259 OID 16473)
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
-- TOC entry 3275 (class 0 OID 0)
-- Dependencies: 215
-- Name: instituciones_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.instituciones_id_seq OWNED BY public.instituciones.id;


--
-- TOC entry 216 (class 1259 OID 16475)
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
-- TOC entry 217 (class 1259 OID 16481)
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
-- TOC entry 3276 (class 0 OID 0)
-- Dependencies: 217
-- Name: maestros_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.maestros_id_seq OWNED BY public.maestros.id;


--
-- TOC entry 218 (class 1259 OID 16483)
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
-- TOC entry 219 (class 1259 OID 16489)
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
-- TOC entry 3277 (class 0 OID 0)
-- Dependencies: 219
-- Name: materiales_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.materiales_id_seq OWNED BY public.materiales.id;


--
-- TOC entry 220 (class 1259 OID 16491)
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
-- TOC entry 221 (class 1259 OID 16497)
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
-- TOC entry 3278 (class 0 OID 0)
-- Dependencies: 221
-- Name: menus_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.menus_id_seq OWNED BY public.menus.id;


--
-- TOC entry 222 (class 1259 OID 16499)
-- Name: ordenes; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.ordenes (
    id integer NOT NULL,
    formula integer,
    factura text,
    laboratorio integer,
    registro timestamp without time zone,
    envio timestamp without time zone,
    recepcion timestamp without time zone,
    entrega timestamp without time zone,
    descripcion text,
    seleccionado boolean,
    creado timestamp without time zone,
    creadopor text,
    actualizado timestamp without time zone,
    actualizadopor text,
    activo boolean
);


ALTER TABLE public.ordenes OWNER TO postgres;

--
-- TOC entry 223 (class 1259 OID 16505)
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
-- TOC entry 3279 (class 0 OID 0)
-- Dependencies: 223
-- Name: ordenes_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.ordenes_id_seq OWNED BY public.ordenes.id;


--
-- TOC entry 224 (class 1259 OID 16507)
-- Name: pacientes; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.pacientes (
    id integer NOT NULL,
    persona integer,
    institucion integer,
    activo boolean,
    creado timestamp without time zone,
    creadopor text,
    actualizado timestamp without time zone,
    actualizadopor text
);


ALTER TABLE public.pacientes OWNER TO postgres;

--
-- TOC entry 225 (class 1259 OID 16513)
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
-- TOC entry 3280 (class 0 OID 0)
-- Dependencies: 225
-- Name: pacientes_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.pacientes_id_seq OWNED BY public.pacientes.id;


--
-- TOC entry 226 (class 1259 OID 16515)
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
-- TOC entry 227 (class 1259 OID 16521)
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
-- TOC entry 3281 (class 0 OID 0)
-- Dependencies: 227
-- Name: parametros_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.parametros_id_seq OWNED BY public.parametros.id;


--
-- TOC entry 228 (class 1259 OID 16523)
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
-- TOC entry 229 (class 1259 OID 16529)
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
-- TOC entry 3282 (class 0 OID 0)
-- Dependencies: 229
-- Name: perfiles_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.perfiles_id_seq OWNED BY public.perfiles.id;


--
-- TOC entry 230 (class 1259 OID 16531)
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
    rol text,
    activo boolean DEFAULT false,
    ocupacion text,
    genero integer,
    descripcion text,
    creado timestamp without time zone,
    creadopor text,
    actualizado timestamp without time zone,
    actualizadopor text,
    primaria text,
    numero text,
    secundaria text,
    referencia text,
    fijo text,
    movil text,
    ciudad text,
    fotografia bytea
);


ALTER TABLE public.personas OWNER TO postgres;

--
-- TOC entry 231 (class 1259 OID 16538)
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
-- TOC entry 3283 (class 0 OID 0)
-- Dependencies: 231
-- Name: personas_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.personas_id_seq OWNED BY public.personas.id;


--
-- TOC entry 232 (class 1259 OID 16540)
-- Name: prescripciones; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.prescripciones (
    id integer NOT NULL,
    atencion integer,
    medicamento text,
    dosis text,
    frecuencia text,
    duracion text,
    administracion text,
    activo boolean,
    creado timestamp without time zone,
    creadopor text,
    actualizado timestamp without time zone,
    actualizadopor text
);


ALTER TABLE public.prescripciones OWNER TO postgres;

--
-- TOC entry 233 (class 1259 OID 16546)
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
-- TOC entry 3284 (class 0 OID 0)
-- Dependencies: 233
-- Name: prescripciones_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.prescripciones_id_seq OWNED BY public.prescripciones.id;


--
-- TOC entry 234 (class 1259 OID 16548)
-- Name: profesionales; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.profesionales (
    id integer NOT NULL,
    persona integer,
    especialidad integer,
    institucion integer,
    activo boolean,
    creado timestamp without time zone,
    creadopor text,
    actualizado timestamp without time zone,
    actualizadopor text
);


ALTER TABLE public.profesionales OWNER TO postgres;

--
-- TOC entry 235 (class 1259 OID 16554)
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
-- TOC entry 3285 (class 0 OID 0)
-- Dependencies: 235
-- Name: profesionales_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.profesionales_id_seq OWNED BY public.profesionales.id;


--
-- TOC entry 236 (class 1259 OID 16556)
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
-- TOC entry 237 (class 1259 OID 16562)
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
-- TOC entry 3286 (class 0 OID 0)
-- Dependencies: 237
-- Name: usuarios_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.usuarios_id_seq OWNED BY public.usuarios.id;


--
-- TOC entry 2982 (class 2604 OID 16564)
-- Name: archivos id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.archivos ALTER COLUMN id SET DEFAULT nextval('public.archivos_id_seq'::regclass);


--
-- TOC entry 2983 (class 2604 OID 16565)
-- Name: atenciones id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.atenciones ALTER COLUMN id SET DEFAULT nextval('public.atenciones_id_seq'::regclass);


--
-- TOC entry 2984 (class 2604 OID 16566)
-- Name: campos id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.campos ALTER COLUMN id SET DEFAULT nextval('public.campos_id_seq'::regclass);


--
-- TOC entry 2985 (class 2604 OID 16567)
-- Name: citas id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.citas ALTER COLUMN id SET DEFAULT nextval('public.citas_id_seq'::regclass);


--
-- TOC entry 2986 (class 2604 OID 16568)
-- Name: datos id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.datos ALTER COLUMN id SET DEFAULT nextval('public.datos_id_seq'::regclass);


--
-- TOC entry 2987 (class 2604 OID 16569)
-- Name: direcciones id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.direcciones ALTER COLUMN id SET DEFAULT nextval('public.direcciones_id_seq'::regclass);


--
-- TOC entry 2988 (class 2604 OID 16570)
-- Name: formulas id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.formulas ALTER COLUMN id SET DEFAULT nextval('public.formulas_id_seq'::regclass);


--
-- TOC entry 2989 (class 2604 OID 16571)
-- Name: horarios id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.horarios ALTER COLUMN id SET DEFAULT nextval('public.horarios_id_seq'::regclass);


--
-- TOC entry 2990 (class 2604 OID 16572)
-- Name: horas id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.horas ALTER COLUMN id SET DEFAULT nextval('public.horas_id_seq'::regclass);


--
-- TOC entry 2991 (class 2604 OID 16573)
-- Name: instituciones id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.instituciones ALTER COLUMN id SET DEFAULT nextval('public.instituciones_id_seq'::regclass);


--
-- TOC entry 2992 (class 2604 OID 16574)
-- Name: maestros id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.maestros ALTER COLUMN id SET DEFAULT nextval('public.maestros_id_seq'::regclass);


--
-- TOC entry 2993 (class 2604 OID 16575)
-- Name: materiales id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.materiales ALTER COLUMN id SET DEFAULT nextval('public.materiales_id_seq'::regclass);


--
-- TOC entry 2994 (class 2604 OID 16576)
-- Name: menus id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.menus ALTER COLUMN id SET DEFAULT nextval('public.menus_id_seq'::regclass);


--
-- TOC entry 2995 (class 2604 OID 16577)
-- Name: ordenes id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ordenes ALTER COLUMN id SET DEFAULT nextval('public.ordenes_id_seq'::regclass);


--
-- TOC entry 2996 (class 2604 OID 16578)
-- Name: pacientes id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pacientes ALTER COLUMN id SET DEFAULT nextval('public.pacientes_id_seq'::regclass);


--
-- TOC entry 2997 (class 2604 OID 16579)
-- Name: parametros id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.parametros ALTER COLUMN id SET DEFAULT nextval('public.parametros_id_seq'::regclass);


--
-- TOC entry 2998 (class 2604 OID 16580)
-- Name: perfiles id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.perfiles ALTER COLUMN id SET DEFAULT nextval('public.perfiles_id_seq'::regclass);


--
-- TOC entry 3000 (class 2604 OID 16581)
-- Name: personas id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.personas ALTER COLUMN id SET DEFAULT nextval('public.personas_id_seq'::regclass);


--
-- TOC entry 3001 (class 2604 OID 16582)
-- Name: prescripciones id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.prescripciones ALTER COLUMN id SET DEFAULT nextval('public.prescripciones_id_seq'::regclass);


--
-- TOC entry 3002 (class 2604 OID 16583)
-- Name: profesionales id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.profesionales ALTER COLUMN id SET DEFAULT nextval('public.profesionales_id_seq'::regclass);


--
-- TOC entry 3003 (class 2604 OID 16584)
-- Name: usuarios id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuarios ALTER COLUMN id SET DEFAULT nextval('public.usuarios_id_seq'::regclass);


--
-- TOC entry 3219 (class 0 OID 16395)
-- Dependencies: 196
-- Data for Name: archivos; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.archivos VALUES (2, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.archivos VALUES (3, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.archivos VALUES (4, NULL, NULL, 'Captura de pantalla de 2017-12-28 22-37-57.png', 'image/png', NULL, NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.archivos VALUES (5, NULL, NULL, 'Captura de pantalla de 2017-12-18 22-41-39.png', 'image/png', NULL, NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.archivos VALUES (8, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.archivos VALUES (9, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.archivos VALUES (1, 'Fotografias', NULL, 'Captura de pantalla de 2017-12-18 22-41-39.png', 'image/png', '/home/fernando/Imágenes/salutem/Fotografias/1', NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.archivos VALUES (12, 'Fotografias', NULL, 'WallE.jpg', 'image/jpeg', '/home/fernando/Imágenes/salutem/Fotografias/12', NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.archivos VALUES (13, 'Fotografias', NULL, 'png.png', 'image/png', '/home/fernando/Imágenes/salutem/Fotografias/13', NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.archivos VALUES (14, 'Fotografias', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.archivos VALUES (11, 'Fotografias', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.archivos VALUES (17, 'Fotografias', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.archivos VALUES (18, 'Fotografias', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.archivos VALUES (19, 'Fotografias', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.archivos VALUES (20, 'Fotografias', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.archivos VALUES (10, 'Logotipos', NULL, 'Ubuntu-Wallpapers-5.jpg', 'image/jpeg', '/home/usuario/Imágenes/salutem/Logotipos/10', NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.archivos VALUES (6, 'Logotipos', NULL, 'Ubuntu-Wallpapers-5.jpg', 'image/jpeg', '/home/usuario/Imágenes/salutem/Logotipos/6', NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.archivos VALUES (34, 'Personas', 20, NULL, NULL, '/home/usuario/Imágenes/salutem/Personas/*', NULL, '2018-09-12 08:50:08.06', 'root', '2018-09-12 08:50:08.06', 'root', true);
INSERT INTO public.archivos VALUES (50, 'Datos', 141, 'PANAMERICANA Daniela M.ods', 'application/vnd.oasis.opendocument.spreadsheet', '/home/usuario/Imágenes/salutem/Datos/50', NULL, '2018-09-14 12:03:25.598', 'apvillalba', '2018-09-14 12:03:25.598', 'apvillalba', true);
INSERT INTO public.archivos VALUES (24, 'Fotografias', NULL, NULL, NULL, '/home/usuario/Imágenes/salutem/Pacientes/38', NULL, '2018-08-16 08:23:20.299', 'root', '2018-08-16 08:25:08.099', 'root', true);
INSERT INTO public.archivos VALUES (26, 'Datos', NULL, 'HOJAS DE DIETA  PARDO.pdf', 'application/pdf', NULL, NULL, '2018-09-07 12:09:02.123', 'root', '2018-09-07 12:09:02.123', NULL, true);
INSERT INTO public.archivos VALUES (27, 'Datos', NULL, 'pdfs.csv', 'text/csv', '/home/usuario/Imágenes/salutem/Datos/*', NULL, '2018-09-07 12:22:04.646', 'root', '2018-09-07 12:22:04.646', 'root', true);
INSERT INTO public.archivos VALUES (28, 'Datos', NULL, 'HOJAS DE DIETA  PARDO.pdf', 'application/pdf', '/home/usuario/Imágenes/salutem/Datos/*', NULL, '2018-09-07 12:22:35.492', 'root', '2018-09-07 12:22:35.492', 'root', true);
INSERT INTO public.archivos VALUES (40, 'Pacientes', 26, 'Ubuntu-Wallpapers-5.jpg', 'image/jpeg', '/home/usuario/Imágenes/salutem/Pacientes/*', NULL, '2018-09-12 09:54:50.009', 'root', '2018-09-12 09:54:50.009', 'root', true);
INSERT INTO public.archivos VALUES (46, 'Datos', 108, 'Ubuntu-Wallpapers-5.jpg', 'image/jpeg', '/home/usuario/Imágenes/salutem/Datos/46', NULL, '2018-09-13 11:03:32.049', 'root', '2018-09-13 11:03:32.049', 'root', true);
INSERT INTO public.archivos VALUES (29, 'Datos', 88, 'Citas.xls', 'application/vnd.ms-excel', '/home/usuario/Imágenes/salutem/Datos/29', NULL, '2018-09-07 16:14:31.857', 'root', '2018-09-07 16:14:31.857', 'root', true);
INSERT INTO public.archivos VALUES (30, 'Personas', 16, NULL, NULL, NULL, NULL, '2018-09-12 08:17:00.147', 'root', '2018-09-12 08:17:00.147', 'root', true);
INSERT INTO public.archivos VALUES (21, 'Fotografias', NULL, 'png.png', 'image/png', '/home/usuario/Imágenes/salutem/Fotografias/21', NULL, NULL, NULL, '2018-08-16 08:24:57.702', 'root', true);
INSERT INTO public.archivos VALUES (32, 'Personas', 18, NULL, NULL, '/home/usuario/Imágenes/salutem/Personas/*', NULL, '2018-09-12 08:31:02.32', 'root', '2018-09-12 08:31:02.32', 'root', true);
INSERT INTO public.archivos VALUES (49, 'Datos', 138, 'EXCEL GILBERT .xlsx', 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet', '/home/usuario/Imágenes/salutem/Datos/49', NULL, '2018-09-14 10:27:38.41', 'root', '2018-09-14 10:27:38.41', 'root', true);
INSERT INTO public.archivos VALUES (47, 'Datos', 118, 'KENNEDY Daniela.ods', 'application/vnd.oasis.opendocument.spreadsheet', '/home/usuario/Imágenes/salutem/Datos/47', NULL, '2018-09-13 11:11:42.577', 'root', '2018-09-13 11:11:42.577', 'root', true);
INSERT INTO public.archivos VALUES (48, 'Datos', 128, 'RESOLUCION 319.pdf', 'application/pdf', '/home/usuario/Imágenes/salutem/Datos/48', NULL, '2018-09-13 12:12:09.358', 'root', '2018-09-13 12:12:09.358', 'root', true);
INSERT INTO public.archivos VALUES (45, 'Profesionales', 31, 'Logo-PostgreSQL-468x405.png', 'image/png', '/home/usuario/Imágenes/salutem/Profesionales/45', NULL, '2018-09-12 10:26:40.976', 'root', '2018-09-12 10:26:40.976', 'apvillalba', true);
INSERT INTO public.archivos VALUES (51, 'Datos', 145, '04ORI_AGOSTO2018.pdf', 'application/pdf', '/home/usuario/Imágenes/salutem/Datos/51.04ORI_AGOSTO2018.pdf', NULL, '2018-09-19 05:48:59.03', 'apvillalba', '2018-09-19 05:48:59.03', 'apvillalba', true);
INSERT INTO public.archivos VALUES (16, 'Fotografias', 1, 'Pg_logo.png', 'image/png', '/home/usuario/Imágenes/salutem/Fotografias/16', NULL, NULL, NULL, '2018-08-16 09:13:43.633', 'apvillalba', true);
INSERT INTO public.archivos VALUES (25, 'Fotografias', 5, 'Logo-PostgreSQL-468x405.png', 'image/png', '/home/usuario/Imágenes/salutem/Profesionales/25', NULL, '2018-08-16 08:31:34.002', 'root', '2018-08-16 08:31:34.002', 'apvillalba', true);
INSERT INTO public.archivos VALUES (52, 'Datos', 160, 'comprobantepago_agosto2018.pdf', 'application/pdf', '/home/usuario/Imágenes/salutem/Datos/52', NULL, '2018-11-02 01:03:09.187', 'root', '2018-11-02 01:03:09.187', 'root', true);
INSERT INTO public.archivos VALUES (31, 'Personas', 17, 'Captura de pantalla de 2018-06-04 22-27-55.png', 'image/png', '/home/usuario/Imágenes/salutem/Pacientes/31', NULL, '2018-09-12 08:29:12.999', 'root', '2018-09-12 08:29:12.999', 'root', true);
INSERT INTO public.archivos VALUES (33, 'Personas', 19, 'sipt.png', 'image/png', '/home/usuario/Imágenes/salutem/Pacientes/33', NULL, '2018-09-12 08:38:29.173', 'root', '2018-09-12 08:38:29.173', 'root', true);
INSERT INTO public.archivos VALUES (53, 'Datos', 170, 'comprobantepago_agosto2018.xls', 'application/vnd.ms-excel', '/home/usuario/Imágenes/salutem/Datos/53', NULL, '2018-11-02 01:14:31.94', 'root', '2018-11-02 01:14:31.94', 'root', true);
INSERT INTO public.archivos VALUES (15, 'Fotografias', 3, '14_20180811_225034.jpg', 'image/jpeg', '/home/usuario/Imágenes/salutem/Profesionales/15', NULL, NULL, NULL, NULL, 'root', true);
INSERT INTO public.archivos VALUES (37, 'Pacientes', 23, '14_20180811_225034.jpg', 'image/jpeg', '/home/usuario/Imágenes/salutem/Pacientes/37', NULL, '2018-09-12 09:35:39.314', 'root', '2018-09-12 09:35:39.314', 'root', true);
INSERT INTO public.archivos VALUES (36, 'Pacientes', 22, 'WallE.jpg', 'image/jpeg', '/home/usuario/Imágenes/salutem/Pacientes/36', NULL, '2018-09-12 09:29:49.546', 'root', '2018-09-12 09:29:49.546', 'root', true);
INSERT INTO public.archivos VALUES (38, 'Pacientes', 24, 'Captura de pantalla de 2018-06-04 22-47-01.png', 'image/png', '/home/usuario/Imágenes/salutem/Pacientes/38', NULL, '2018-09-12 09:42:38.635', 'root', '2018-09-12 09:42:38.635', 'root', true);
INSERT INTO public.archivos VALUES (39, 'Pacientes', 25, 'Pg_logo.png', 'image/png', '/home/usuario/Imágenes/salutem/Pacientes/39', NULL, '2018-09-12 09:48:48.675', 'root', '2018-09-12 09:48:48.675', 'root', true);
INSERT INTO public.archivos VALUES (35, 'Pacientes', 21, 'IMG-20180129-WA0001.jpg', 'image/jpeg', '/home/usuario/Imágenes/salutem/Pacientes/35', NULL, '2018-09-12 08:51:30.381', 'root', '2018-09-12 08:51:30.381', 'root', true);
INSERT INTO public.archivos VALUES (54, 'Pacientes', 26, 'Selección_002.png', 'image/png', '/home/usuario/Imágenes/salutem/Pacientes/54', NULL, '2018-11-03 00:21:06.902', 'root', '2018-11-03 00:21:06.902', 'root', true);
INSERT INTO public.archivos VALUES (41, 'Pacientes', 27, 'Logo-PostgreSQL-468x405.png', 'image/png', '/home/usuario/Imágenes/salutem/Pacientes/41', NULL, '2018-09-12 10:03:24.758', 'root', '2018-09-12 10:03:24.758', 'root', true);
INSERT INTO public.archivos VALUES (42, 'Pacientes', 28, 'WallE.jpg', 'image/jpeg', '/home/usuario/Imágenes/salutem/Pacientes/42', NULL, '2018-09-12 10:06:56.559', 'root', '2018-09-12 10:06:56.559', 'root', true);
INSERT INTO public.archivos VALUES (43, 'Pacientes', 29, 'Selección_002.png', 'image/png', '/home/usuario/Imágenes/salutem/Pacientes/43', NULL, '2018-09-12 10:17:54.021', 'root', '2018-09-12 10:17:54.021', 'root', true);
INSERT INTO public.archivos VALUES (44, 'Pacientes', 30, 'EPSON018.jpg', 'image/jpeg', '/home/usuario/Imágenes/salutem/Pacientes/44', NULL, '2018-09-12 10:19:28.225', 'root', '2018-09-12 10:19:28.225', 'root', true);
INSERT INTO public.archivos VALUES (55, 'Datos', 180, '14_20180811_225034.jpg', 'image/jpeg', '/home/usuario/Imágenes/salutem/Datos/55', NULL, '2018-11-03 00:30:29.813', 'root', '2018-11-03 00:30:29.813', 'root', true);
INSERT INTO public.archivos VALUES (58, 'Profesionales', 31, '14_20180811_225034.jpg', 'image/jpeg', '/home/usuario/Imágenes/salutem/Profesionales/58', NULL, '2018-11-03 21:07:06.885', 'root', '2018-11-03 21:07:06.885', 'root', true);
INSERT INTO public.archivos VALUES (57, 'Profesionales', 1, 'EPSON018.jpg', 'image/jpeg', '/home/usuario/Imágenes/salutem/Profesionales/57', NULL, '2018-11-03 21:05:16.032', 'root', '2018-11-03 21:05:16.032', 'root', true);
INSERT INTO public.archivos VALUES (7, 'Logotipos', 1, 'netbeans-logo.png', 'image/png', '/home/luis/Imágenes/salutem/Instituciones/7', NULL, NULL, NULL, NULL, 'root', true);
INSERT INTO public.archivos VALUES (75, 'Datos', 344, 'Captura de pantalla de 2017-12-18 22-53-29.png', 'image/png', '/home/usuario/Imágenes/salutem/Datos/75', NULL, '2018-11-11 19:27:05.696', 'root', '2018-11-11 19:27:05.696', 'root', true);
INSERT INTO public.archivos VALUES (60, 'Profesionales', 14, 'IMG-20180129-WA0002.jpg', 'image/jpeg', '/home/usuario/Imágenes/salutem/Profesionales/60', NULL, '2018-11-03 21:20:32.035', 'root', '2018-11-03 21:20:32.035', 'root', true);
INSERT INTO public.archivos VALUES (76, 'Datos', 354, '1.Rock and Roll Revolution.mp3', 'audio/mp3', '/home/usuario/Imágenes/salutem/Datos/76', NULL, '2018-11-11 19:33:37.438', 'root', '2018-11-11 19:33:37.438', 'root', true);
INSERT INTO public.archivos VALUES (61, 'Profesionales', 5, 'Captura realizada el 2018-08-05 23.18.46.png', 'image/png', '/home/usuario/Imágenes/salutem/Profesionales/61', NULL, '2018-11-03 21:21:02.567', 'root', '2018-11-03 21:21:02.567', 'root', true);
INSERT INTO public.archivos VALUES (62, 'Pacientes', 32, '4k-wallpapers-Is-4K-Wallpaper.jpg', 'image/jpeg', '/home/usuario/Imágenes/salutem/Pacientes/62', NULL, '2018-11-03 23:15:28.254', 'root', '2018-11-03 23:15:28.254', 'root', true);
INSERT INTO public.archivos VALUES (63, 'Datos', 190, 'descarga.png', 'image/png', '/home/usuario/Imágenes/salutem/Datos/63', NULL, '2018-11-08 21:42:25.434', 'root', '2018-11-08 21:42:25.434', 'root', true);
INSERT INTO public.archivos VALUES (64, 'Datos', 224, 'descarga.png', 'image/png', '/home/usuario/Imágenes/salutem/Datos/64', NULL, '2018-11-08 22:38:37.542', 'root', '2018-11-08 22:38:37.542', 'root', true);
INSERT INTO public.archivos VALUES (65, 'Datos', 234, 'Atenciones.xls', 'application/vnd.ms-excel', '/home/usuario/Imágenes/salutem/Datos/65', NULL, '2018-11-10 13:27:14.16', 'root', '2018-11-10 13:27:14.16', 'root', true);
INSERT INTO public.archivos VALUES (66, 'Datos', 254, 'data-1541303863716.csv', 'text/csv', '/home/usuario/Imágenes/salutem/Datos/66', NULL, '2018-11-10 23:25:45.088', 'root', '2018-11-10 23:25:45.088', 'root', true);
INSERT INTO public.archivos VALUES (77, 'Datos', 357, 'Captura de pantalla de 2018-06-04 22-27-55.png', 'image/png', '/home/usuario/Imágenes/salutem/Datos/77', NULL, '2018-11-11 22:00:57.506', 'apvillalba', '2018-11-11 22:00:57.506', 'apvillalba', true);
INSERT INTO public.archivos VALUES (67, 'Datos', 264, 'EPSON018.jpg', 'image/jpeg', '/home/usuario/Imágenes/salutem/Datos/67', NULL, '2018-11-11 09:23:43.801', 'root', '2018-11-11 09:23:43.801', 'root', true);
INSERT INTO public.archivos VALUES (68, 'Datos', 274, 'DIMMDatosRucs.xml', 'text/xml', '/home/usuario/Imágenes/salutem/Datos/68', NULL, '2018-11-11 18:35:41.213', 'root', '2018-11-11 18:35:41.213', 'root', true);
INSERT INTO public.archivos VALUES (69, 'Datos', 284, '1 - Alfoncina y el mar.mp3', 'audio/mp3', '/home/usuario/Imágenes/salutem/Datos/69', NULL, '2018-11-11 18:36:39.112', 'root', '2018-11-11 18:36:39.112', 'root', true);
INSERT INTO public.archivos VALUES (70, 'Datos', 294, 'Captura de pantalla de 2018-06-04 22-28-03.png', 'image/png', '/home/usuario/Imágenes/salutem/Datos/70', NULL, '2018-11-11 18:52:15.142', 'root', '2018-11-11 18:52:15.142', 'root', true);
INSERT INTO public.archivos VALUES (71, 'Datos', 304, 'Captura de pantalla de 2017-12-18 22-53-25.png', 'image/png', '/home/usuario/Imágenes/salutem/Datos/71', NULL, '2018-11-11 19:06:06.324', 'root', '2018-11-11 19:06:06.324', 'root', true);
INSERT INTO public.archivos VALUES (72, 'Datos', 314, 'Captura de pantalla de 2017-12-18 22-58-29.png', 'image/png', '/home/usuario/Imágenes/salutem/Datos/72', NULL, '2018-11-11 19:07:29.949', 'root', '2018-11-11 19:07:29.949', 'root', true);
INSERT INTO public.archivos VALUES (73, 'Datos', 324, 'Captura de pantalla de 2017-12-18 22-53-37.png', 'image/png', '/home/usuario/Imágenes/salutem/Datos/73', NULL, '2018-11-11 19:08:29.056', 'root', '2018-11-11 19:08:29.056', 'root', true);
INSERT INTO public.archivos VALUES (74, 'Datos', 334, 'Captura de pantalla de 2017-12-18 22-59-43.png', 'image/png', '/home/usuario/Imágenes/salutem/Datos/74', NULL, '2018-11-11 19:12:16.247', 'root', '2018-11-11 19:12:16.247', 'root', true);
INSERT INTO public.archivos VALUES (78, 'Datos', 413, 'Captura de pantalla de 2018-06-04 22-47-01.png', 'image/png', '/home/usuario/Imágenes/salutem/Datos/78', NULL, '2018-11-18 18:56:18.145', 'root', '2018-11-18 18:56:18.145', 'root', true);
INSERT INTO public.archivos VALUES (79, 'Datos', 421, 'Windows 10.vmxf', 'application/x-vmware-vmfoundry', '/home/luis/Imágenes/salutem/Datos/79', NULL, '2019-01-26 22:59:53.078', 'root', '2019-01-26 22:59:53.078', 'root', true);
INSERT INTO public.archivos VALUES (59, 'Pacientes', 3, '14_20180811_225034.jpg', 'image/jpeg', '/home/luis/Imágenes/salutem/Pacientes/59', NULL, '2018-11-03 21:07:18.64', 'root', '2018-11-03 21:07:18.64', 'root', true);
INSERT INTO public.archivos VALUES (22, 'Logotipos', 4, 'netbeans-logo.png', 'image/png', '/home/luis/Imágenes/salutem/Instituciones/22', NULL, NULL, NULL, NULL, 'root', true);
INSERT INTO public.archivos VALUES (56, 'Profesionales', 3, 'luis.jpg', 'image/jpeg', '/home/luis/Imágenes/salutem/Profesionales/56', NULL, '2018-11-03 20:54:48.453', 'root', '2018-11-03 20:54:48.453', 'root', true);
INSERT INTO public.archivos VALUES (80, 'Datos', 429, '08. Fuego de juventud.mp3', 'audio/mp3', '/home/luis/Imágenes/salutem/Datos/80', NULL, '2019-01-27 00:46:35.197', 'root', '2019-01-27 00:46:35.197', 'root', true);


--
-- TOC entry 3221 (class 0 OID 16403)
-- Dependencies: 198
-- Data for Name: atenciones; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.atenciones VALUES (27, '2018-09-14 09:41:09.095', NULL, 10, 1, 31, 'Ninguno.', 'Pulmonía.', 'Cuidados del clima.', true, '2018-09-14 09:41:09.1', 'root', '2018-09-14 12:34:26.449', 'root');
INSERT INTO public.atenciones VALUES (1, '2018-08-30 11:07:56.702', NULL, 10, 5, NULL, NULL, NULL, NULL, true, '2018-08-30 11:07:56.702', 'apvillalba', '2018-08-30 11:07:56.702', 'apvillalba');
INSERT INTO public.atenciones VALUES (10, '2018-08-30 16:54:25.34', 32, 10, 1, 31, NULL, NULL, NULL, true, '2018-08-30 16:54:25.362', 'root', '2018-08-30 16:54:25.362', 'root');
INSERT INTO public.atenciones VALUES (11, '2018-09-03 14:43:52.486', NULL, 10, 1, 31, NULL, NULL, NULL, true, '2018-09-03 14:43:52.492', 'root', '2018-09-03 14:43:52.492', 'root');
INSERT INTO public.atenciones VALUES (13, '2018-09-04 08:33:56.215', NULL, 10, 1, 31, 'Por una simple prueba.', 'Todo esto es una farsa.', 'Por favor hacer caso a las prescripciones médicas.', true, '2018-09-04 08:33:56.221', 'root', '2018-09-04 17:43:15.782', 'root');
INSERT INTO public.atenciones VALUES (14, '2018-09-05 09:48:46.198', NULL, 10, 1, 31, 'Chequeo de rutina.', 'Pulmonía.', 'Seguir estrictamente las recomendaciones.', true, '2018-09-05 09:48:46.204', 'root', '2018-09-05 16:20:02.766', 'root');
INSERT INTO public.atenciones VALUES (15, '2018-09-06 12:13:55.248', NULL, 10, 1, 31, NULL, NULL, NULL, true, '2018-09-06 12:13:55.253', 'root', '2018-09-06 12:13:55.253', 'root');
INSERT INTO public.atenciones VALUES (16, '2018-09-07 12:08:48.229', NULL, 12, 1, 31, NULL, NULL, NULL, true, '2018-09-07 12:08:48.234', 'root', '2018-09-07 12:08:48.234', 'root');
INSERT INTO public.atenciones VALUES (18, '2018-09-10 12:21:21.13', NULL, 10, 5, 33, '', '', '', true, '2018-09-10 12:21:21.134', 'apvillalba', '2018-09-10 16:58:40.339', 'apvillalba');
INSERT INTO public.atenciones VALUES (20, '2018-09-11 17:57:09.426', NULL, 10, 5, 33, '', '', '', true, '2018-09-11 17:57:09.431', 'apvillalba', '2018-09-11 18:16:34.982', 'apvillalba');
INSERT INTO public.atenciones VALUES (23, '2018-09-13 12:11:46.109', NULL, 12, 1, 31, NULL, NULL, NULL, true, '2018-09-13 12:11:46.113', 'root', '2018-09-13 12:11:46.113', 'root');
INSERT INTO public.atenciones VALUES (24, '2018-09-13 16:42:25.631', NULL, 10, 5, 33, NULL, NULL, NULL, true, '2018-09-13 16:42:25.637', 'apvillalba', '2018-09-13 16:42:25.637', 'apvillalba');
INSERT INTO public.atenciones VALUES (28, '2018-09-14 09:44:17.174', NULL, 10, 5, 33, 'Obtener una prueba exitosa.', 'No tiene nada, solo pereza.', 'Siga con pereza, por favor.', true, '2018-09-14 09:44:17.179', 'apvillalba', '2018-09-14 15:21:59.125', 'apvillalba');
INSERT INTO public.atenciones VALUES (29, '2018-09-19 05:48:31.235', NULL, 10, 5, 33, NULL, NULL, NULL, true, '2018-09-19 05:48:31.24', 'apvillalba', '2018-09-19 05:48:31.24', 'apvillalba');
INSERT INTO public.atenciones VALUES (30, '2018-11-02 00:53:30.213', NULL, 10, 5, 33, '', '', '', true, '2018-11-02 00:53:30.215', 'apvillalba', '2018-11-02 00:53:43.003', 'apvillalba');
INSERT INTO public.atenciones VALUES (32, '2018-11-02 01:14:12.668', NULL, 10, 1, 31, 'Motivo', 'Diagnostico', 'Observaciones', true, '2018-11-02 01:14:12.67', 'root', '2018-11-02 01:18:15.27', 'root');
INSERT INTO public.atenciones VALUES (33, '2018-11-03 00:27:51.647', NULL, 10, 1, 31, NULL, NULL, NULL, true, '2018-11-03 00:27:51.649', 'root', '2018-11-03 00:27:51.649', 'root');
INSERT INTO public.atenciones VALUES (36, '2018-11-08 22:34:12.795', NULL, 25, 5, 33, NULL, NULL, NULL, true, '2018-11-08 22:34:12.796', 'apvillalba', '2018-11-08 22:34:12.796', 'apvillalba');
INSERT INTO public.atenciones VALUES (37, '2018-11-08 22:35:06.183', NULL, 25, 5, 31, NULL, NULL, NULL, true, '2018-11-08 22:35:06.184', 'apvillalba', '2018-11-08 22:35:06.184', 'apvillalba');
INSERT INTO public.atenciones VALUES (38, '2018-11-08 22:37:19.316', NULL, 25, 1, 31, '', '', '', true, '2018-11-08 22:37:19.317', 'root', '2018-11-08 22:39:07.88', 'root');
INSERT INTO public.atenciones VALUES (41, '2018-11-10 23:13:07.883', NULL, 10, 1, 31, NULL, NULL, NULL, true, '2018-11-10 23:13:07.884', 'root', '2018-11-10 23:13:07.884', 'root');
INSERT INTO public.atenciones VALUES (51, '2018-11-11 19:32:10.005', NULL, 10, 1, 31, 'Motivo', 'Diagnóstico', 'Observaciones', true, '2018-11-11 19:32:10.009', 'root', '2018-11-11 21:40:46.726', 'root');
INSERT INTO public.atenciones VALUES (57, '2018-11-11 22:49:07.907', NULL, 10, 5, 33, NULL, NULL, NULL, true, '2018-11-11 22:49:07.907', 'apvillalba', '2018-11-11 22:49:07.907', 'apvillalba');
INSERT INTO public.atenciones VALUES (61, '2018-11-13 19:16:16.181', NULL, 10, 1, 33, NULL, NULL, NULL, true, '2018-11-13 19:16:16.188', 'root', '2018-11-13 19:16:16.188', 'root');
INSERT INTO public.atenciones VALUES (62, '2018-11-13 19:18:54.235', NULL, 25, 1, 33, NULL, NULL, NULL, true, '2018-11-13 19:18:54.242', 'root', '2018-11-13 19:18:54.242', 'root');
INSERT INTO public.atenciones VALUES (63, '2018-11-15 22:22:31.965', NULL, 10, 5, 33, NULL, NULL, NULL, true, '2018-11-15 22:22:31.969', 'apvillalba', '2018-11-15 22:22:31.969', 'apvillalba');
INSERT INTO public.atenciones VALUES (64, '2018-11-18 18:54:42.399', 33, 25, 1, 33, 'Una prueba de rutina', NULL, NULL, true, '2018-11-18 18:54:42.401', 'root', '2018-11-18 18:56:41.294', 'root');
INSERT INTO public.atenciones VALUES (65, '2019-01-08 17:29:11.514', NULL, 25, 1, 33, NULL, NULL, NULL, true, '2019-01-08 17:29:11.516', 'root', '2019-01-08 17:29:11.516', 'root');
INSERT INTO public.atenciones VALUES (66, '2019-01-26 22:59:42.463', NULL, 10, 1, 33, '', NULL, NULL, true, '2019-01-26 22:59:42.466', 'root', '2019-01-26 23:14:03.203', 'root');
INSERT INTO public.atenciones VALUES (68, '2019-01-27 00:46:25.969', NULL, 25, 1, 33, '', NULL, NULL, true, '2019-01-27 00:46:25.971', 'root', '2019-01-27 11:52:37.967', 'root');
INSERT INTO public.atenciones VALUES (69, '2019-01-27 21:28:24.28', 34, 25, 1, 33, '', NULL, NULL, true, '2019-01-27 21:28:24.281', 'root', '2019-01-27 21:28:28.809', 'root');


--
-- TOC entry 3223 (class 0 OID 16411)
-- Dependencies: 200
-- Data for Name: campos; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.campos VALUES (6, 1, 'Atenciones', 31, 2, 'Altura (m)', '', NULL, 49, '2018-08-30 16:37:53.725', 'root', '2018-08-30 16:37:53.725', 'root', true, true);
INSERT INTO public.campos VALUES (5, 1, 'Atenciones', 31, 1, 'Peso (kg)', '', NULL, 49, '2018-08-30 16:32:54.599', 'root', '2018-08-30 16:38:03.147', 'root', true, true);
INSERT INTO public.campos VALUES (7, 1, 'Atenciones', 31, 3, 'Presión arterial', '', NULL, 49, '2018-08-30 16:39:50.34', 'root', '2018-08-30 16:39:50.34', 'root', true, true);
INSERT INTO public.campos VALUES (8, 1, 'Atenciones', 31, 4, 'Temperatura (ºC)', '', NULL, 49, '2018-08-30 16:40:20.047', 'root', '2018-08-30 16:54:42.537', 'root', true, true);
INSERT INTO public.campos VALUES (9, 1, 'Atenciones', 31, 6, 'Fecha Atención', '', NULL, 50, '2018-09-04 11:15:20.66', 'root', '2018-09-04 11:15:20.66', 'root', true, true);
INSERT INTO public.campos VALUES (10, 1, 'Atenciones', 31, 7, 'Alergias', '', NULL, 47, '2018-09-04 11:16:08.528', 'root', '2018-09-04 11:16:08.528', 'root', true, true);
INSERT INTO public.campos VALUES (11, 1, 'Atenciones', 31, 8, 'Selección múltiple', '', '{"0": "Opción 01", "1": "Opción 02", "2": "Opción 03"}', 54, '2018-09-04 11:17:05.302', 'root', '2018-09-04 11:17:05.302', 'root', true, true);
INSERT INTO public.campos VALUES (13, 1, 'Atenciones', 31, 10, 'Archivo', 'Archivo', NULL, 55, '2018-09-07 12:08:30.886', 'root', '2018-09-07 12:08:30.886', 'root', true, true);
INSERT INTO public.campos VALUES (12, 1, 'Atenciones', 31, 9, 'Seleccione Una', '', '{"0": "Opción Uno", "1": "Opción Dos", "2": "Opción Tres", "3": "Opción Cuatro"}', 57, '2018-09-05 09:06:51.433', 'root', '2018-09-13 11:02:13.336', 'root', true, true);
INSERT INTO public.campos VALUES (14, 1, 'Atenciones', 33, 1, 'Verdadero o Falso', 'Verdadero o Falso', NULL, 47, '2018-09-14 12:00:19.936', 'apvillalba', '2018-09-14 12:00:19.936', 'apvillalba', true, false);
INSERT INTO public.campos VALUES (15, 1, 'Atenciones', 33, 2, 'Fecha y hora', 'Fecha y hora', NULL, 52, '2018-09-14 12:01:20.381', 'apvillalba', '2018-09-14 12:01:20.381', 'apvillalba', true, false);
INSERT INTO public.campos VALUES (16, 1, 'Atenciones', 33, 3, 'Suba un archivo', 'Suba un archivo', NULL, 55, '2018-09-14 12:01:58.435', 'apvillalba', '2018-09-14 12:01:58.435', 'apvillalba', true, false);
INSERT INTO public.campos VALUES (17, 1, 'Atenciones', 33, 4, 'Selección', 'Selección', '{"0": "Opción 1", "1": "Opción 2"}', 54, '2018-09-14 12:02:52.764', 'apvillalba', '2018-09-14 12:02:52.764', 'apvillalba', true, false);
INSERT INTO public.campos VALUES (18, 1, 'Archivos', 56, 3, '1', '1', NULL, 47, '2018-11-10 10:58:32.44', 'root', '2018-11-10 10:58:32.44', 'root', true, true);
INSERT INTO public.campos VALUES (2, 1, 'Archivos', 56, 2, '¿Es paciente regular?', '', NULL, 47, '2018-08-24 11:34:50.382', 'apvillalba', '2018-11-10 12:42:32.672', 'root', true, false);
INSERT INTO public.campos VALUES (19, 1, 'Usuarios', 56, 5, '01', '01', NULL, 47, '2018-11-10 12:45:06.182', 'root', '2018-11-10 12:46:15.773', 'root', true, true);
INSERT INTO public.campos VALUES (4, 1, 'Archivos', 56, 4, 'Seleccione Varios', '', '{"0": "Opción 001", "1": "Opción 002", "2": "Opción 003"}', 54, '2018-08-24 11:40:12.289', 'apvillalba', '2018-11-10 12:48:03.408', 'root', true, true);
INSERT INTO public.campos VALUES (20, 1, 'Archivos', 56, 5, '01', '01', '{"0": "001", "1": "002", "2": "03"}', 53, '2018-11-10 13:21:02.516', 'root', '2018-11-11 19:38:53.124', 'root', true, true);
INSERT INTO public.campos VALUES (1, 1, 'Archivos', 56, 1, 'C001', 'C001', '{"0": "Valor001", "1": "Valor002", "2": "Valor003", "3": "Valor004"}', 52, '2018-08-23 11:36:24.873', 'apvillalba', '2018-11-11 19:48:28.587', 'root', true, true);
INSERT INTO public.campos VALUES (3, 1, 'Atenciones', 31, 5, 'Seleccione una opción', '\n', '{"0": "Opción 01", "1": "Opción 02", "2": "Opción 03", "3": "EEEE, dd ''de'' MMMMM ''de'' yyyy HH:mm:ss"}', 53, '2018-08-24 11:35:45.008', 'apvillalba', '2018-11-18 20:59:08.742', 'root', true, true);


--
-- TOC entry 3225 (class 0 OID 16419)
-- Dependencies: 202
-- Data for Name: citas; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.citas VALUES (2, 10, 3, '2018-08-02 08:40:00', true, '[Cita agendada por: imordonez - 02/08/2018 12:41]', '2018-08-02 12:41:22.206', 'imordonez', '2018-08-02 12:41:22.206', 'imordonez');
INSERT INTO public.citas VALUES (3, 10, 3, '2018-08-02 09:20:00', true, '[Cita agendada por: imordonez - 02/08/2018 13:58]', '2018-08-02 13:58:12.75', 'imordonez', '2018-08-02 13:58:12.75', 'imordonez');
INSERT INTO public.citas VALUES (1, 10, 3, '2018-08-02 16:56:50.937', false, '[Cita agendada por: imordonez - 02/08/2018 10:25] [Cita cancelada por: imordonez - 02/08/2018 16:27] [Cita reactivada por: imordonez - 02/08/2018 16:27] [Cita cancelada por: imordonez - 02/08/2018 16:56]', '2018-08-02 10:25:49.885', 'imordonez', '2018-08-02 16:56:50.937', 'imordonez');
INSERT INTO public.citas VALUES (4, 10, 1, '2018-08-03 08:00:00', true, '[Cita agendada por: imordonez - 03/08/2018 23:05] [Cita cancelada por: imordonez - 03/08/2018 23:05] [Cita reactivada por: imordonez - 03/08/2018 23:05]', '2018-08-03 23:05:37.56', 'imordonez', '2018-08-03 23:05:59.316', 'imordonez');
INSERT INTO public.citas VALUES (6, 10, 3, '2018-08-04 08:40:00', true, '[Cita agendada por: imordonez - 04/08/2018 19:06]', '2018-08-04 19:06:29.699', 'imordonez', '2018-08-04 19:06:29.699', 'imordonez');
INSERT INTO public.citas VALUES (7, 10, 1, '2018-08-05 00:00:00', true, '[Cita agendada por: imordonez - 05/08/2018 22:55]', '2018-08-05 22:55:14.505', 'imordonez', '2018-08-05 22:55:14.505', 'imordonez');
INSERT INTO public.citas VALUES (8, 10, 1, '2018-08-06 00:00:00', true, '[Cita agendada por: imordonez - 05/08/2018 23:21]', '2018-08-05 23:21:12.572', 'imordonez', '2018-08-05 23:21:12.572', 'imordonez');
INSERT INTO public.citas VALUES (9, 10, 1, '2018-08-06 01:00:00', true, '[Cita agendada por: imordonez - 05/08/2018 23:21]', '2018-08-05 23:21:19.149', 'imordonez', '2018-08-05 23:21:19.149', 'imordonez');
INSERT INTO public.citas VALUES (10, 10, 1, '2018-08-05 23:00:00', true, '[Cita agendada por: imordonez - 05/08/2018 23:21]', '2018-08-05 23:21:34.54', 'imordonez', '2018-08-05 23:21:34.54', 'imordonez');
INSERT INTO public.citas VALUES (11, 10, 1, '2018-08-07 07:00:00', true, '[Cita agendada por: imordonez - 07/08/2018 07:28]', '2018-08-07 07:28:09.791', 'imordonez', '2018-08-07 07:28:09.791', 'imordonez');
INSERT INTO public.citas VALUES (12, 10, 1, '2018-08-07 08:00:00', true, '[Cita agendada por: imordonez - 07/08/2018 07:36]', '2018-08-07 07:36:01.267', 'imordonez', '2018-08-07 07:36:01.267', 'imordonez');
INSERT INTO public.citas VALUES (14, 10, 1, '2018-08-10 20:00:00', true, '[Cita agendada por: imordonez - 10/08/2018 20:11]', '2018-08-10 20:11:26.982', 'imordonez', '2018-08-10 20:11:26.982', 'imordonez');
INSERT INTO public.citas VALUES (15, 11, 1, '2018-08-10 21:00:00', true, '[Cita agendada por: imordonez - 10/08/2018 20:11]', '2018-08-10 20:11:48.078', 'imordonez', '2018-08-10 20:11:48.078', 'imordonez');
INSERT INTO public.citas VALUES (22, 10, 1, '2018-08-10 23:00:00', true, '[Cita agendada por: imordonez - 10/08/2018 23:24]', '2018-08-10 23:24:39.964', 'imordonez', '2018-08-10 23:24:39.964', 'imordonez');
INSERT INTO public.citas VALUES (24, 10, 1, '2018-08-11 01:00:00', true, '[Cita agendada por: imordonez - 11/08/2018 00:00]', '2018-08-11 00:00:58.499', 'imordonez', '2018-08-11 00:00:58.499', 'imordonez');
INSERT INTO public.citas VALUES (25, 10, 1, '2018-08-11 02:00:00', true, '[Cita agendada por: imordonez - 11/08/2018 00:39]', '2018-08-11 00:39:24.772', 'imordonez', '2018-08-11 00:39:24.772', 'imordonez');
INSERT INTO public.citas VALUES (26, 11, 1, '2018-08-11 03:00:00', true, '[Cita agendada por: imordonez - 11/08/2018 01:10]', '2018-08-11 01:10:41.17', 'imordonez', '2018-08-11 01:10:41.17', 'imordonez');
INSERT INTO public.citas VALUES (23, 10, 1, '2018-08-11 00:00:00', false, 'Cita cancelada.Por razones médicas', '2018-08-11 00:00:54.507', 'imordonez', '2018-08-11 02:36:09.165', 'imordonez');
INSERT INTO public.citas VALUES (27, 10, 1, '2018-08-11 04:00:00', true, 'Cita agendada. Vendrá temprano.', '2018-08-11 02:38:43.87', 'imordonez', '2018-08-11 02:38:43.87', 'imordonez');
INSERT INTO public.citas VALUES (28, 10, 1, '2018-08-12 07:00:00', true, 'Cita agendada. ', '2018-08-12 07:49:35.908', 'imordonez', '2018-08-12 07:49:35.908', 'imordonez');
INSERT INTO public.citas VALUES (29, 11, 1, '2018-08-14 18:00:00', true, 'Cita agendada. Prueba', '2018-08-14 18:05:22.608', 'imordonez', '2018-08-14 18:05:22.608', 'imordonez');
INSERT INTO public.citas VALUES (30, 12, 1, '2018-08-16 09:00:00', true, 'Cita reagendada. Primera Cita, se solicita puntualidad.', '2018-08-16 09:05:38.01', 'apvillalba', '2018-08-16 10:14:23.813', 'apvillalba');
INSERT INTO public.citas VALUES (31, 10, 1, '2018-08-27 08:00:00', true, 'Cita agendada. Cita programada', '2018-08-27 08:25:57.594', 'apvillalba', '2018-08-27 08:25:57.594', 'apvillalba');
INSERT INTO public.citas VALUES (32, 10, 1, '2018-08-30 11:00:00', true, 'Cita agendada. Ninguna', '2018-08-30 11:12:20.1', 'apvillalba', '2018-08-30 11:12:20.1', 'apvillalba');
INSERT INTO public.citas VALUES (33, 25, 1, '2018-11-18 18:00:00', true, 'Cita agendada. Cita agendada por el administrador', '2018-11-18 18:54:20.124', 'root', '2018-11-18 18:54:20.124', 'root');
INSERT INTO public.citas VALUES (34, 25, 1, '2019-01-27 21:00:00', true, 'Cita agendada. Prueba', '2019-01-27 21:27:00.639', 'root', '2019-01-27 21:27:00.639', 'root');
INSERT INTO public.citas VALUES (35, 10, 1, '2019-01-27 22:00:00', true, 'Cita agendada. Prueba 001', '2019-01-27 21:27:39.145', 'root', '2019-01-27 21:27:39.145', 'root');
INSERT INTO public.citas VALUES (36, 25, 1, '2019-01-31 00:00:00', true, 'Cita agendada. Prueba 002', '2019-01-27 21:29:42.118', 'root', '2019-01-27 21:29:42.118', 'root');
INSERT INTO public.citas VALUES (37, 25, 5, '2019-01-28 00:00:00', true, 'Cita agendada. ', '2019-01-28 00:27:37.854', 'root', '2019-01-28 00:27:37.854', 'root');


--
-- TOC entry 3227 (class 0 OID 16427)
-- Dependencies: 204
-- Data for Name: datos; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.datos VALUES (78, 'Atenciones', 15, 31, 'Medicina General', 9, 'Seleccione Una', '', '{"0": "Opción Uno", "1": "Opción Dos", "2": "Opción Tres", "3": "Opción Cuatro"}', 57, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-09-06 12:13:55.57', 'root', '2018-09-06 15:00:20.821', 'root', true, false);
INSERT INTO public.datos VALUES (70, 'Atenciones', 15, 31, 'Medicina General', 1, 'Peso (kg)', '', NULL, 49, NULL, NULL, NULL, 67, NULL, NULL, NULL, NULL, NULL, '2018-09-06 12:13:55.382', 'root', '2018-09-06 15:00:20.615', 'root', true, true);
INSERT INTO public.datos VALUES (71, 'Atenciones', 15, 31, 'Medicina General', 2, 'Altura (m)', '', NULL, 49, NULL, NULL, NULL, 67, NULL, NULL, NULL, NULL, NULL, '2018-09-06 12:13:55.412', 'root', '2018-09-06 15:00:20.663', 'root', true, true);
INSERT INTO public.datos VALUES (1, 'Atenciones', 9, 0, 'Medicina General', NULL, NULL, '', NULL, 49, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-08-30 16:40:53.85', 'root', '2018-08-30 16:40:53.85', 'root', true, true);
INSERT INTO public.datos VALUES (2, 'Atenciones', 9, 1, 'Medicina General', NULL, NULL, '', NULL, 49, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-08-30 16:40:53.918', 'root', '2018-08-30 16:40:53.918', 'root', true, true);
INSERT INTO public.datos VALUES (3, 'Atenciones', 9, 2, 'Medicina General', NULL, NULL, '', NULL, 49, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-08-30 16:40:53.935', 'root', '2018-08-30 16:40:53.935', 'root', true, true);
INSERT INTO public.datos VALUES (4, 'Atenciones', 9, 3, 'Medicina General', NULL, NULL, '', NULL, 49, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-08-30 16:40:53.959', 'root', '2018-08-30 16:40:53.959', 'root', true, true);
INSERT INTO public.datos VALUES (5, 'Atenciones', 10, 31, 'Medicina General', 2, 'Altura (m)', '', NULL, 49, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-08-30 16:54:25.397', 'root', '2018-08-30 16:54:25.397', 'root', true, true);
INSERT INTO public.datos VALUES (6, 'Atenciones', 10, 31, 'Medicina General', 1, 'Peso (kg)', '', NULL, 49, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-08-30 16:54:25.416', 'root', '2018-08-30 16:54:25.416', 'root', true, true);
INSERT INTO public.datos VALUES (7, 'Atenciones', 10, 31, 'Medicina General', 3, 'Presión arterial', '', NULL, 49, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-08-30 16:54:25.433', 'root', '2018-08-30 16:54:25.433', 'root', true, true);
INSERT INTO public.datos VALUES (8, 'Atenciones', 10, 31, 'Medicina General', 4, 'Temperatura (ºC)', '', NULL, 49, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-08-30 16:54:25.441', 'root', '2018-08-30 16:54:25.441', 'root', true, true);
INSERT INTO public.datos VALUES (9, 'Atenciones', 11, 31, 'Medicina General', 2, 'Altura (m)', '', NULL, 49, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-09-03 14:43:52.786', 'root', '2018-09-03 14:43:52.786', 'root', true, true);
INSERT INTO public.datos VALUES (10, 'Atenciones', 11, 31, 'Medicina General', 1, 'Peso (kg)', '', NULL, 49, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-09-03 14:43:52.841', 'root', '2018-09-03 14:43:52.841', 'root', true, true);
INSERT INTO public.datos VALUES (11, 'Atenciones', 11, 31, 'Medicina General', 3, 'Presión arterial', '', NULL, 49, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-09-03 14:43:52.858', 'root', '2018-09-03 14:43:52.858', 'root', true, true);
INSERT INTO public.datos VALUES (72, 'Atenciones', 15, 31, 'Medicina General', 3, 'Presión arterial', '', NULL, 49, NULL, NULL, NULL, 67, NULL, NULL, NULL, NULL, NULL, '2018-09-06 12:13:55.429', 'root', '2018-09-06 15:00:20.715', 'root', true, true);
INSERT INTO public.datos VALUES (12, 'Atenciones', 11, 31, 'Medicina General', 4, 'Temperatura (ºC)', '', NULL, 49, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-09-03 14:43:52.875', 'root', '2018-09-03 14:43:52.875', 'root', true, true);
INSERT INTO public.datos VALUES (13, 'Atenciones', 11, 31, 'Medicina General', 5, 'Seleccione una opción', '', NULL, 53, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-09-03 14:43:52.883', 'root', '2018-09-03 14:43:52.883', 'root', true, true);
INSERT INTO public.datos VALUES (14, 'Atenciones', 12, 31, 'Medicina General', 2, 'Altura (m)', '', NULL, 49, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-09-04 08:13:42.324', 'root', '2018-09-04 08:13:42.324', 'root', true, true);
INSERT INTO public.datos VALUES (73, 'Atenciones', 15, 31, 'Medicina General', 4, 'Temperatura (ºC)', '', NULL, 49, NULL, NULL, NULL, 67, NULL, NULL, NULL, NULL, NULL, '2018-09-06 12:13:55.445', 'root', '2018-09-06 15:00:20.732', 'root', true, true);
INSERT INTO public.datos VALUES (75, 'Atenciones', 15, 31, 'Medicina General', 6, 'Fecha Atención', '', NULL, 50, NULL, NULL, NULL, NULL, '2018-09-06', NULL, NULL, NULL, NULL, '2018-09-06 12:13:55.504', 'root', '2018-09-06 15:00:20.772', 'root', true, true);
INSERT INTO public.datos VALUES (76, 'Atenciones', 15, 31, 'Medicina General', 7, 'Alergias', '', NULL, 47, NULL, true, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-09-06 12:13:55.52', 'root', '2018-09-06 15:00:20.78', 'root', true, true);
INSERT INTO public.datos VALUES (74, 'Atenciones', 15, 31, 'Medicina General', 5, 'Seleccione una opción', '', '{"0": "Opción 01", "1": "Opción 02", "2": "Opción 03"}', 53, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{"0": "Opción 01"}', NULL, '2018-09-06 12:13:55.453', 'root', '2018-09-06 15:00:20.747', 'root', true, true);
INSERT INTO public.datos VALUES (77, 'Atenciones', 15, 31, 'Medicina General', 8, 'Selección múltiple', '', '{"0": "Opción 01", "1": "Opción 02", "2": "Opción 03"}', 54, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{"0": "Opción 01"}', NULL, '2018-09-06 12:13:55.537', 'root', '2018-09-06 15:00:20.797', 'root', true, true);
INSERT INTO public.datos VALUES (15, 'Atenciones', 12, 31, 'Medicina General', 1, 'Peso (kg)', '', NULL, 49, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-09-04 08:13:42.389', 'root', '2018-09-04 08:13:42.389', 'root', true, true);
INSERT INTO public.datos VALUES (16, 'Atenciones', 12, 31, 'Medicina General', 3, 'Presión arterial', '', NULL, 49, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-09-04 08:13:42.431', 'root', '2018-09-04 08:13:42.431', 'root', true, true);
INSERT INTO public.datos VALUES (17, 'Atenciones', 12, 31, 'Medicina General', 4, 'Temperatura (ºC)', '', NULL, 49, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-09-04 08:13:42.464', 'root', '2018-09-04 08:13:42.464', 'root', true, true);
INSERT INTO public.datos VALUES (18, 'Atenciones', 12, 31, 'Medicina General', 5, 'Seleccione una opción', '', NULL, 53, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-09-04 08:13:42.514', 'root', '2018-09-04 08:13:42.514', 'root', true, true);
INSERT INTO public.datos VALUES (55, 'Atenciones', 13, 31, 'Medicina General', 3, 'Presión arterial', '', NULL, 49, NULL, NULL, NULL, 2, NULL, NULL, NULL, NULL, NULL, '2018-09-04 17:31:27.449', 'root', '2018-09-04 17:31:27.449', 'root', true, true);
INSERT INTO public.datos VALUES (58, 'Atenciones', 13, 31, 'Medicina General', 6, 'Fecha Atención', '', NULL, 50, NULL, NULL, NULL, NULL, '2018-09-04', NULL, NULL, NULL, NULL, '2018-09-04 17:31:27.508', 'root', '2018-09-04 17:31:27.508', 'root', true, true);
INSERT INTO public.datos VALUES (60, 'Atenciones', 13, 31, 'Medicina General', 8, 'Selección múltiple', '', '{"0": "Opción 01", "1": "Opción 02", "2": "Opción 03"}', 54, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{"0": "Opción 01", "1": "Opción 02", "2": "Opción 03"}', NULL, '2018-09-04 17:31:27.533', 'root', '2018-09-04 17:31:27.533', 'root', true, true);
INSERT INTO public.datos VALUES (59, 'Atenciones', 13, 31, 'Medicina General', 7, 'Alergias', '', NULL, 47, NULL, true, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-09-04 17:31:27.516', 'root', '2018-09-04 17:31:27.516', 'root', true, true);
INSERT INTO public.datos VALUES (56, 'Atenciones', 13, 31, 'Medicina General', 4, 'Temperatura (ºC)', '', NULL, 49, NULL, NULL, NULL, 3, NULL, NULL, NULL, NULL, NULL, '2018-09-04 17:31:27.458', 'root', '2018-09-04 17:31:27.458', 'root', true, true);
INSERT INTO public.datos VALUES (53, 'Atenciones', 13, 31, 'Medicina General', 1, 'Peso (kg)', '', NULL, 49, NULL, NULL, NULL, 58, NULL, NULL, NULL, NULL, NULL, '2018-09-04 17:31:27.356', 'root', '2018-09-04 17:31:27.356', 'root', true, true);
INSERT INTO public.datos VALUES (54, 'Atenciones', 13, 31, 'Medicina General', 2, 'Altura (m)', '', NULL, 49, NULL, NULL, NULL, 52, NULL, NULL, NULL, NULL, NULL, '2018-09-04 17:31:27.433', 'root', '2018-09-04 17:31:27.433', 'root', true, true);
INSERT INTO public.datos VALUES (57, 'Atenciones', 13, 31, 'Medicina General', 5, 'Seleccione una opción', '', '{"0": "Opción 01", "1": "Opción 02", "2": "Opción 03"}', 53, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{"2": "Opción 03"}', NULL, '2018-09-04 17:31:27.474', 'root', '2018-09-04 17:31:27.474', 'root', true, true);
INSERT INTO public.datos VALUES (61, 'Atenciones', 14, 31, 'Medicina General', 1, 'Peso (kg)', '', NULL, 49, NULL, NULL, NULL, 120, NULL, NULL, NULL, NULL, NULL, '2018-09-05 09:48:46.306', 'root', '2018-09-05 11:46:11.598', 'root', true, true);
INSERT INTO public.datos VALUES (62, 'Atenciones', 14, 31, 'Medicina General', 2, 'Altura (m)', '', NULL, 49, NULL, NULL, NULL, 1.72999999999999998, NULL, NULL, NULL, NULL, NULL, '2018-09-05 09:48:46.323', 'root', '2018-09-05 11:46:11.608', 'root', true, true);
INSERT INTO public.datos VALUES (64, 'Atenciones', 14, 31, 'Medicina General', 4, 'Temperatura (ºC)', '', NULL, 49, NULL, NULL, NULL, 35, NULL, NULL, NULL, NULL, NULL, '2018-09-05 09:48:46.373', 'root', '2018-09-05 11:46:11.643', 'root', true, true);
INSERT INTO public.datos VALUES (66, 'Atenciones', 14, 31, 'Medicina General', 6, 'Fecha Atención', '', NULL, 50, NULL, NULL, NULL, NULL, '2018-09-05', NULL, NULL, NULL, NULL, '2018-09-05 09:48:46.407', 'root', '2018-09-05 11:46:11.692', 'root', true, true);
INSERT INTO public.datos VALUES (65, 'Atenciones', 14, 31, 'Medicina General', 5, 'Seleccione una opción', '', '{"0": "Opción 01", "1": "Opción 02", "2": "Opción 03"}', 53, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{"1": "Opción 02"}', NULL, '2018-09-05 09:48:46.382', 'root', '2018-09-05 11:46:11.66', 'root', true, true);
INSERT INTO public.datos VALUES (68, 'Atenciones', 14, 31, 'Medicina General', 8, 'Selección múltiple', '', '{"0": "Opción 01", "1": "Opción 02", "2": "Opción 03"}', 54, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{"0": "Opción 01", "1": "Opción 02"}', NULL, '2018-09-05 09:48:46.432', 'root', '2018-09-05 11:46:11.725', 'root', true, true);
INSERT INTO public.datos VALUES (69, 'Atenciones', 14, 31, 'Medicina General', 9, 'Seleccione Una', '', '{"0": "Opción Uno", "1": "Opción Dos", "2": "Opción Tres", "3": "Opción Cuatro"}', 57, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{"3": "Opción Cuatro"}', NULL, '2018-09-05 09:48:46.457', 'root', '2018-09-05 11:46:11.75', 'root', true, true);
INSERT INTO public.datos VALUES (63, 'Atenciones', 14, 31, 'Medicina General', 3, 'Presión arterial', '', NULL, 49, NULL, NULL, NULL, 12, NULL, NULL, NULL, NULL, NULL, '2018-09-05 09:48:46.357', 'root', '2018-09-05 11:46:11.626', 'root', true, true);
INSERT INTO public.datos VALUES (67, 'Atenciones', 14, 31, 'Medicina General', 7, 'Alergias', '', NULL, 47, NULL, true, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-09-05 09:48:46.415', 'root', '2018-09-05 11:46:11.708', 'root', true, true);
INSERT INTO public.datos VALUES (79, 'Atenciones', 16, 31, 'Medicina General', 1, 'Peso (kg)', '', NULL, 49, NULL, NULL, NULL, 12, NULL, NULL, NULL, NULL, NULL, '2018-09-07 12:08:48.278', 'root', '2018-09-07 12:08:48.278', 'root', true, true);
INSERT INTO public.datos VALUES (80, 'Atenciones', 16, 31, 'Medicina General', 2, 'Altura (m)', '', NULL, 49, NULL, NULL, NULL, 12, NULL, NULL, NULL, NULL, NULL, '2018-09-07 12:08:48.291', 'root', '2018-09-07 12:08:48.291', 'root', true, true);
INSERT INTO public.datos VALUES (81, 'Atenciones', 16, 31, 'Medicina General', 3, 'Presión arterial', '', NULL, 49, NULL, NULL, NULL, 12, NULL, NULL, NULL, NULL, NULL, '2018-09-07 12:08:48.308', 'root', '2018-09-07 12:08:48.308', 'root', true, true);
INSERT INTO public.datos VALUES (82, 'Atenciones', 16, 31, 'Medicina General', 4, 'Temperatura (ºC)', '', NULL, 49, NULL, NULL, NULL, 12, NULL, NULL, NULL, NULL, NULL, '2018-09-07 12:08:48.316', 'root', '2018-09-07 12:08:48.316', 'root', true, true);
INSERT INTO public.datos VALUES (83, 'Atenciones', 16, 31, 'Medicina General', 5, 'Seleccione una opción', '', '{"0": "Opción 01", "1": "Opción 02", "2": "Opción 03"}', 53, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{"0": "Opción 01"}', NULL, '2018-09-07 12:08:48.333', 'root', '2018-09-07 12:08:48.333', 'root', true, true);
INSERT INTO public.datos VALUES (84, 'Atenciones', 16, 31, 'Medicina General', 6, 'Fecha Atención', '', NULL, 50, NULL, NULL, NULL, NULL, '2018-09-07', NULL, NULL, NULL, NULL, '2018-09-07 12:08:48.358', 'root', '2018-09-07 12:08:48.358', 'root', true, true);
INSERT INTO public.datos VALUES (85, 'Atenciones', 16, 31, 'Medicina General', 7, 'Alergias', '', NULL, 47, NULL, true, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-09-07 12:08:48.366', 'root', '2018-09-07 12:08:48.366', 'root', true, true);
INSERT INTO public.datos VALUES (86, 'Atenciones', 16, 31, 'Medicina General', 8, 'Selección múltiple', '', '{"0": "Opción 01", "1": "Opción 02", "2": "Opción 03"}', 54, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{"0": "Opción 01"}', NULL, '2018-09-07 12:08:48.383', 'root', '2018-09-07 12:08:48.383', 'root', true, true);
INSERT INTO public.datos VALUES (87, 'Atenciones', 16, 31, 'Medicina General', 9, 'Seleccione Una', '', '{"0": "Opción Uno", "1": "Opción Dos", "2": "Opción Tres", "3": "Opción Cuatro"}', 57, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{"0": "Opción Uno"}', NULL, '2018-09-07 12:08:48.408', 'root', '2018-09-07 12:08:48.408', 'root', true, false);
INSERT INTO public.datos VALUES (88, 'Atenciones', 16, 31, 'Medicina General', 10, 'Archivo', 'Archivo', NULL, 55, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 29, '2018-09-07 12:08:48.433', 'root', '2018-09-07 12:08:48.433', 'root', true, true);
INSERT INTO public.datos VALUES (119, 'Atenciones', 23, 31, 'Medicina General', 1, 'Peso (kg)', '', NULL, 49, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-09-13 12:11:46.131', 'root', '2018-09-13 12:11:46.131', 'root', true, true);
INSERT INTO public.datos VALUES (120, 'Atenciones', 23, 31, 'Medicina General', 2, 'Altura (m)', '', NULL, 49, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-09-13 12:11:46.14', 'root', '2018-09-13 12:11:46.14', 'root', true, true);
INSERT INTO public.datos VALUES (121, 'Atenciones', 23, 31, 'Medicina General', 3, 'Presión arterial', '', NULL, 49, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-09-13 12:11:46.157', 'root', '2018-09-13 12:11:46.157', 'root', true, true);
INSERT INTO public.datos VALUES (122, 'Atenciones', 23, 31, 'Medicina General', 4, 'Temperatura (ºC)', '', NULL, 49, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-09-13 12:11:46.166', 'root', '2018-09-13 12:11:46.166', 'root', true, true);
INSERT INTO public.datos VALUES (123, 'Atenciones', 23, 31, 'Medicina General', 5, 'Seleccione una opción', '', '{"0": "Opción 01", "1": "Opción 02", "2": "Opción 03"}', 53, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-09-13 12:11:46.182', 'root', '2018-09-13 12:11:46.182', 'root', true, true);
INSERT INTO public.datos VALUES (124, 'Atenciones', 23, 31, 'Medicina General', 6, 'Fecha Atención', '', NULL, 50, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-09-13 12:11:46.207', 'root', '2018-09-13 12:11:46.207', 'root', true, true);
INSERT INTO public.datos VALUES (125, 'Atenciones', 23, 31, 'Medicina General', 7, 'Alergias', '', NULL, 47, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-09-13 12:11:46.215', 'root', '2018-09-13 12:11:46.215', 'root', true, true);
INSERT INTO public.datos VALUES (134, 'Atenciones', 27, 31, 'Medicina General', 6, 'Fecha Atención', '', NULL, 50, NULL, NULL, NULL, NULL, '2018-09-14', NULL, NULL, NULL, NULL, '2018-09-14 09:41:09.269', 'root', '2018-09-14 09:41:09.269', 'root', true, true);
INSERT INTO public.datos VALUES (142, 'Atenciones', 28, 33, 'Optometría', 4, 'Selección', 'Selección', '{"0": "Opción 1", "1": "Opción 2"}', 54, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{"0": "Opción 1", "1": "Opción 2"}', NULL, '2018-09-14 12:03:02.168', 'apvillalba', '2018-09-14 12:03:02.168', 'apvillalba', true, false);
INSERT INTO public.datos VALUES (133, 'Atenciones', 27, 31, 'Medicina General', 5, 'Seleccione una opción', '', '{"0": "Opción 01", "1": "Opción 02", "2": "Opción 03"}', 53, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{"0": "Opción 01"}', NULL, '2018-09-14 09:41:09.244', 'root', '2018-09-14 09:41:09.244', 'root', true, true);
INSERT INTO public.datos VALUES (126, 'Atenciones', 23, 31, 'Medicina General', 8, 'Selección múltiple', '', '{"0": "Opción 01", "1": "Opción 02", "2": "Opción 03"}', 54, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-09-13 12:11:46.232', 'root', '2018-09-13 12:11:46.232', 'root', true, true);
INSERT INTO public.datos VALUES (127, 'Atenciones', 23, 31, 'Medicina General', 9, 'Seleccione Una', '', '{"0": "Opción Uno", "1": "Opción Dos", "2": "Opción Tres", "3": "Opción Cuatro"}', 57, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-09-13 12:11:46.257', 'root', '2018-09-13 12:11:46.257', 'root', true, true);
INSERT INTO public.datos VALUES (128, 'Atenciones', 23, 31, 'Medicina General', 10, 'Archivo', 'Archivo', NULL, 55, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 48, '2018-09-13 12:11:46.282', 'root', '2018-09-13 12:11:46.282', 'root', true, true);
INSERT INTO public.datos VALUES (138, 'Atenciones', 27, 31, 'Medicina General', 10, 'Archivo', 'Archivo', NULL, 55, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 49, '2018-09-14 09:41:09.344', 'root', '2018-09-14 09:41:09.344', 'root', true, true);
INSERT INTO public.datos VALUES (129, 'Atenciones', 27, 31, 'Medicina General', 1, 'Peso (kg)', '', NULL, 49, NULL, NULL, NULL, 150, NULL, NULL, NULL, NULL, NULL, '2018-09-14 09:41:09.138', 'root', '2018-09-14 09:41:09.138', 'root', true, true);
INSERT INTO public.datos VALUES (141, 'Atenciones', 28, 33, 'Optometría', 3, 'Suba un archivo', 'Suba un archivo', NULL, 55, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 50, '2018-09-14 12:03:02.16', 'apvillalba', '2018-09-14 12:03:02.16', 'apvillalba', true, false);
INSERT INTO public.datos VALUES (139, 'Atenciones', 28, 33, 'Optometría', 1, 'Verdadero o Falso', 'Verdadero o Falso', NULL, 47, NULL, true, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-09-14 12:03:02.115', 'apvillalba', '2018-09-14 12:03:02.115', 'apvillalba', true, false);
INSERT INTO public.datos VALUES (130, 'Atenciones', 27, 31, 'Medicina General', 2, 'Altura (m)', '', NULL, 49, NULL, NULL, NULL, 1.67999999999999994, NULL, NULL, NULL, NULL, NULL, '2018-09-14 09:41:09.203', 'root', '2018-09-14 09:41:09.203', 'root', true, true);
INSERT INTO public.datos VALUES (140, 'Atenciones', 28, 33, 'Optometría', 2, 'Fecha y hora', 'Fecha y hora', NULL, 52, NULL, NULL, NULL, NULL, NULL, NULL, '2018-09-14 12:03:46', NULL, NULL, '2018-09-14 12:03:02.143', 'apvillalba', '2018-09-14 12:03:02.143', 'apvillalba', true, false);
INSERT INTO public.datos VALUES (131, 'Atenciones', 27, 31, 'Medicina General', 3, 'Presión arterial', '', NULL, 49, NULL, NULL, NULL, 12, NULL, NULL, NULL, NULL, NULL, '2018-09-14 09:41:09.219', 'root', '2018-09-14 09:41:09.219', 'root', true, true);
INSERT INTO public.datos VALUES (132, 'Atenciones', 27, 31, 'Medicina General', 4, 'Temperatura (ºC)', '', NULL, 49, NULL, NULL, NULL, 36, NULL, NULL, NULL, NULL, NULL, '2018-09-14 09:41:09.228', 'root', '2018-09-14 09:41:09.228', 'root', true, true);
INSERT INTO public.datos VALUES (136, 'Atenciones', 27, 31, 'Medicina General', 8, 'Selección múltiple', '', '{"0": "Opción 01", "1": "Opción 02", "2": "Opción 03"}', 54, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{"0": "Opción 01"}', NULL, '2018-09-14 09:41:09.295', 'root', '2018-09-14 09:41:09.295', 'root', true, true);
INSERT INTO public.datos VALUES (135, 'Atenciones', 27, 31, 'Medicina General', 7, 'Alergias', '', NULL, 47, NULL, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-09-14 09:41:09.278', 'root', '2018-09-14 09:41:09.278', 'root', true, true);
INSERT INTO public.datos VALUES (137, 'Atenciones', 27, 31, 'Medicina General', 9, 'Seleccione Una', '', '{"0": "Opción Uno", "1": "Opción Dos", "2": "Opción Tres", "3": "Opción Cuatro"}', 57, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{"0": "Opción Uno"}', NULL, '2018-09-14 09:41:09.319', 'root', '2018-09-14 09:41:09.319', 'root', true, true);
INSERT INTO public.datos VALUES (145, 'Atenciones', 29, 33, 'Optometría', 3, 'Suba un archivo', 'Suba un archivo', NULL, 55, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 51, '2018-09-19 05:48:31.347', 'apvillalba', '2018-09-19 05:48:31.347', 'apvillalba', true, false);
INSERT INTO public.datos VALUES (143, 'Atenciones', 29, 33, 'Optometría', 1, 'Verdadero o Falso', 'Verdadero o Falso', NULL, 47, NULL, true, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-09-19 05:48:31.328', 'apvillalba', '2018-09-19 05:48:31.328', 'apvillalba', true, false);
INSERT INTO public.datos VALUES (144, 'Atenciones', 29, 33, 'Optometría', 2, 'Fecha y hora', 'Fecha y hora', NULL, 52, NULL, NULL, NULL, NULL, NULL, NULL, '2018-09-19 05:50:46', NULL, NULL, '2018-09-19 05:48:31.34', 'apvillalba', '2018-09-19 05:48:31.34', 'apvillalba', true, false);
INSERT INTO public.datos VALUES (146, 'Atenciones', 29, 33, 'Optometría', 4, 'Selección', 'Selección', '{"0": "Opción 1", "1": "Opción 2"}', 54, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{"0": "Opción 1"}', NULL, '2018-09-19 05:48:31.352', 'apvillalba', '2018-09-19 05:48:31.352', 'apvillalba', true, false);
INSERT INTO public.datos VALUES (148, 'Atenciones', 30, 33, 'Optometría', 2, 'Fecha y hora', 'Fecha y hora', NULL, 52, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-11-02 00:53:30.273', 'apvillalba', '2018-11-02 00:53:30.273', 'apvillalba', true, false);
INSERT INTO public.datos VALUES (149, 'Atenciones', 30, 33, 'Optometría', 3, 'Suba un archivo', 'Suba un archivo', NULL, 55, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-11-02 00:53:30.278', 'apvillalba', '2018-11-02 00:53:30.278', 'apvillalba', true, false);
INSERT INTO public.datos VALUES (147, 'Atenciones', 30, 33, 'Optometría', 1, 'Verdadero o Falso', 'Verdadero o Falso', NULL, 47, NULL, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-11-02 00:53:30.265', 'apvillalba', '2018-11-02 00:53:30.265', 'apvillalba', true, false);
INSERT INTO public.datos VALUES (150, 'Atenciones', 30, 33, 'Optometría', 4, 'Selección', 'Selección', '{"0": "Opción 1", "1": "Opción 2"}', 54, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-11-02 00:53:30.283', 'apvillalba', '2018-11-02 00:53:30.283', 'apvillalba', true, false);
INSERT INTO public.datos VALUES (166, 'Atenciones', 32, 31, 'Medicina General', 6, 'Fecha Atención', '', NULL, 50, NULL, NULL, NULL, NULL, '2018-11-15', NULL, NULL, NULL, NULL, '2018-11-02 01:14:12.704', 'root', '2018-11-02 01:14:12.704', 'root', true, true);
INSERT INTO public.datos VALUES (168, 'Atenciones', 32, 31, 'Medicina General', 8, 'Selección múltiple', '', '{"0": "Opción 01", "1": "Opción 02", "2": "Opción 03"}', 54, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{"1": "Opción 02"}', NULL, '2018-11-02 01:14:12.71', 'root', '2018-11-02 01:14:12.71', 'root', true, true);
INSERT INTO public.datos VALUES (169, 'Atenciones', 32, 31, 'Medicina General', 9, 'Seleccione Una', '', '{"0": "Opción Uno", "1": "Opción Dos", "2": "Opción Tres", "3": "Opción Cuatro"}', 57, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{"1": "Opción Dos"}', NULL, '2018-11-02 01:14:12.714', 'root', '2018-11-02 01:14:12.714', 'root', true, true);
INSERT INTO public.datos VALUES (170, 'Atenciones', 32, 31, 'Medicina General', 10, 'Archivo', 'Archivo', NULL, 55, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 53, '2018-11-02 01:14:12.718', 'root', '2018-11-02 01:14:12.718', 'root', true, true);
INSERT INTO public.datos VALUES (161, 'Atenciones', 32, 31, 'Medicina General', 1, 'Peso (kg)', '', NULL, 49, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, '2018-11-02 01:14:12.689', 'root', '2018-11-02 01:14:12.689', 'root', true, true);
INSERT INTO public.datos VALUES (162, 'Atenciones', 32, 31, 'Medicina General', 2, 'Altura (m)', '', NULL, 49, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, '2018-11-02 01:14:12.692', 'root', '2018-11-02 01:14:12.692', 'root', true, true);
INSERT INTO public.datos VALUES (163, 'Atenciones', 32, 31, 'Medicina General', 3, 'Presión arterial', '', NULL, 49, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, '2018-11-02 01:14:12.694', 'root', '2018-11-02 01:14:12.694', 'root', true, true);
INSERT INTO public.datos VALUES (164, 'Atenciones', 32, 31, 'Medicina General', 4, 'Temperatura (ºC)', '', NULL, 49, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, '2018-11-02 01:14:12.697', 'root', '2018-11-02 01:14:12.697', 'root', true, true);
INSERT INTO public.datos VALUES (167, 'Atenciones', 32, 31, 'Medicina General', 7, 'Alergias', '', NULL, 47, NULL, true, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-11-02 01:14:12.706', 'root', '2018-11-02 01:14:12.706', 'root', true, true);
INSERT INTO public.datos VALUES (165, 'Atenciones', 32, 31, 'Medicina General', 5, 'Seleccione una opción', '', '{"0": "Opción 01", "1": "Opción 02", "2": "Opción 03"}', 53, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{"0": "Opción 01"}', NULL, '2018-11-02 01:14:12.699', 'root', '2018-11-02 01:14:12.699', 'root', true, true);
INSERT INTO public.datos VALUES (171, 'Atenciones', 33, 31, 'Medicina General', 1, 'Peso (kg)', '', NULL, 49, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-11-03 00:27:51.683', 'root', '2018-11-03 00:27:51.683', 'root', true, true);
INSERT INTO public.datos VALUES (172, 'Atenciones', 33, 31, 'Medicina General', 2, 'Altura (m)', '', NULL, 49, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-11-03 00:27:51.695', 'root', '2018-11-03 00:27:51.695', 'root', true, true);
INSERT INTO public.datos VALUES (173, 'Atenciones', 33, 31, 'Medicina General', 3, 'Presión arterial', '', NULL, 49, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-11-03 00:27:51.702', 'root', '2018-11-03 00:27:51.702', 'root', true, true);
INSERT INTO public.datos VALUES (174, 'Atenciones', 33, 31, 'Medicina General', 4, 'Temperatura (ºC)', '', NULL, 49, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-11-03 00:27:51.709', 'root', '2018-11-03 00:27:51.709', 'root', true, true);
INSERT INTO public.datos VALUES (175, 'Atenciones', 33, 31, 'Medicina General', 5, 'Seleccione una opción', '', '{"0": "Opción 01", "1": "Opción 02", "2": "Opción 03"}', 53, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-11-03 00:27:51.715', 'root', '2018-11-03 00:27:51.715', 'root', true, true);
INSERT INTO public.datos VALUES (176, 'Atenciones', 33, 31, 'Medicina General', 6, 'Fecha Atención', '', NULL, 50, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-11-03 00:27:51.724', 'root', '2018-11-03 00:27:51.724', 'root', true, true);
INSERT INTO public.datos VALUES (177, 'Atenciones', 33, 31, 'Medicina General', 7, 'Alergias', '', NULL, 47, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-11-03 00:27:51.729', 'root', '2018-11-03 00:27:51.729', 'root', true, true);
INSERT INTO public.datos VALUES (178, 'Atenciones', 33, 31, 'Medicina General', 8, 'Selección múltiple', '', '{"0": "Opción 01", "1": "Opción 02", "2": "Opción 03"}', 54, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-11-03 00:27:51.734', 'root', '2018-11-03 00:27:51.734', 'root', true, true);
INSERT INTO public.datos VALUES (179, 'Atenciones', 33, 31, 'Medicina General', 9, 'Seleccione Una', '', '{"0": "Opción Uno", "1": "Opción Dos", "2": "Opción Tres", "3": "Opción Cuatro"}', 57, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-11-03 00:27:51.744', 'root', '2018-11-03 00:27:51.744', 'root', true, true);
INSERT INTO public.datos VALUES (180, 'Atenciones', 33, 31, 'Medicina General', 10, 'Archivo', 'Archivo', NULL, 55, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 55, '2018-11-03 00:27:51.753', 'root', '2018-11-03 00:27:51.753', 'root', true, true);
INSERT INTO public.datos VALUES (201, 'Atenciones', 36, 33, 'Optometría', 1, 'Verdadero o Falso', 'Verdadero o Falso', NULL, 47, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-11-08 22:34:12.836', 'apvillalba', '2018-11-08 22:34:12.836', 'apvillalba', true, false);
INSERT INTO public.datos VALUES (202, 'Atenciones', 36, 33, 'Optometría', 2, 'Fecha y hora', 'Fecha y hora', NULL, 52, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-11-08 22:34:12.844', 'apvillalba', '2018-11-08 22:34:12.844', 'apvillalba', true, false);
INSERT INTO public.datos VALUES (203, 'Atenciones', 36, 33, 'Optometría', 3, 'Suba un archivo', 'Suba un archivo', NULL, 55, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-11-08 22:34:12.848', 'apvillalba', '2018-11-08 22:34:12.848', 'apvillalba', true, false);
INSERT INTO public.datos VALUES (204, 'Atenciones', 36, 33, 'Optometría', 4, 'Selección', 'Selección', NULL, 54, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-11-08 22:34:12.851', 'apvillalba', '2018-11-08 22:34:12.851', 'apvillalba', true, false);
INSERT INTO public.datos VALUES (205, 'Atenciones', 37, 31, 'Medicina General', 1, 'Peso (kg)', '', NULL, 49, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-11-08 22:35:06.208', 'apvillalba', '2018-11-08 22:35:06.208', 'apvillalba', true, true);
INSERT INTO public.datos VALUES (206, 'Atenciones', 37, 31, 'Medicina General', 2, 'Altura (m)', '', NULL, 49, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-11-08 22:35:06.212', 'apvillalba', '2018-11-08 22:35:06.212', 'apvillalba', true, true);
INSERT INTO public.datos VALUES (207, 'Atenciones', 37, 31, 'Medicina General', 3, 'Presión arterial', '', NULL, 49, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-11-08 22:35:06.216', 'apvillalba', '2018-11-08 22:35:06.216', 'apvillalba', true, true);
INSERT INTO public.datos VALUES (208, 'Atenciones', 37, 31, 'Medicina General', 4, 'Temperatura (ºC)', '', NULL, 49, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-11-08 22:35:06.22', 'apvillalba', '2018-11-08 22:35:06.22', 'apvillalba', true, true);
INSERT INTO public.datos VALUES (209, 'Atenciones', 37, 31, 'Medicina General', 5, 'Seleccione una opción', '', NULL, 53, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-11-08 22:35:06.223', 'apvillalba', '2018-11-08 22:35:06.223', 'apvillalba', true, true);
INSERT INTO public.datos VALUES (210, 'Atenciones', 37, 31, 'Medicina General', 6, 'Fecha Atención', '', NULL, 50, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-11-08 22:35:06.229', 'apvillalba', '2018-11-08 22:35:06.229', 'apvillalba', true, true);
INSERT INTO public.datos VALUES (211, 'Atenciones', 37, 31, 'Medicina General', 7, 'Alergias', '', NULL, 47, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-11-08 22:35:06.233', 'apvillalba', '2018-11-08 22:35:06.233', 'apvillalba', true, true);
INSERT INTO public.datos VALUES (212, 'Atenciones', 37, 31, 'Medicina General', 8, 'Selección múltiple', '', NULL, 54, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-11-08 22:35:06.236', 'apvillalba', '2018-11-08 22:35:06.236', 'apvillalba', true, true);
INSERT INTO public.datos VALUES (213, 'Atenciones', 37, 31, 'Medicina General', 9, 'Seleccione Una', '', NULL, 57, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-11-08 22:35:06.241', 'apvillalba', '2018-11-08 22:35:06.241', 'apvillalba', true, true);
INSERT INTO public.datos VALUES (214, 'Atenciones', 37, 31, 'Medicina General', 10, 'Archivo', 'Archivo', NULL, 55, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-11-08 22:35:06.246', 'apvillalba', '2018-11-08 22:35:06.246', 'apvillalba', true, true);
INSERT INTO public.datos VALUES (223, 'Atenciones', 38, 31, 'Medicina General', 9, 'Seleccione Una', '', '{"0": "Opción Uno", "1": "Opción Dos", "2": "Opción Tres", "3": "Opción Cuatro"}', 57, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{"0": "Opción Uno"}', NULL, '2018-11-08 22:37:19.373', 'root', '2018-11-08 22:37:19.373', 'root', true, true);
INSERT INTO public.datos VALUES (249, 'Atenciones', 41, 31, 'Medicina General', 5, 'Seleccione una opción', '', '{"0": "Opción 01", "1": "Opción 02", "2": "Opción 03"}', 53, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-11-10 23:13:07.908', 'root', '2018-11-10 23:13:07.908', 'root', true, true);
INSERT INTO public.datos VALUES (224, 'Atenciones', 38, 31, 'Medicina General', 10, 'Archivo', 'Archivo', NULL, 55, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 64, '2018-11-08 22:37:19.378', 'root', '2018-11-08 22:37:19.378', 'root', true, true);
INSERT INTO public.datos VALUES (215, 'Atenciones', 38, 31, 'Medicina General', 1, 'Peso (kg)', '', NULL, 49, NULL, NULL, NULL, 12, NULL, NULL, NULL, NULL, NULL, '2018-11-08 22:37:19.338', 'root', '2018-11-08 22:37:19.338', 'root', true, true);
INSERT INTO public.datos VALUES (216, 'Atenciones', 38, 31, 'Medicina General', 2, 'Altura (m)', '', NULL, 49, NULL, NULL, NULL, 123, NULL, NULL, NULL, NULL, NULL, '2018-11-08 22:37:19.342', 'root', '2018-11-08 22:37:19.342', 'root', true, true);
INSERT INTO public.datos VALUES (217, 'Atenciones', 38, 31, 'Medicina General', 3, 'Presión arterial', '', NULL, 49, NULL, NULL, NULL, 134, NULL, NULL, NULL, NULL, NULL, '2018-11-08 22:37:19.346', 'root', '2018-11-08 22:37:19.346', 'root', true, true);
INSERT INTO public.datos VALUES (218, 'Atenciones', 38, 31, 'Medicina General', 4, 'Temperatura (ºC)', '', NULL, 49, NULL, NULL, NULL, 4532, NULL, NULL, NULL, NULL, NULL, '2018-11-08 22:37:19.349', 'root', '2018-11-08 22:37:19.349', 'root', true, true);
INSERT INTO public.datos VALUES (219, 'Atenciones', 38, 31, 'Medicina General', 5, 'Seleccione una opción', '', '{"0": "Opción 01", "1": "Opción 02", "2": "Opción 03"}', 53, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{"2": "Opción 03"}', NULL, '2018-11-08 22:37:19.352', 'root', '2018-11-08 22:37:19.352', 'root', true, true);
INSERT INTO public.datos VALUES (220, 'Atenciones', 38, 31, 'Medicina General', 6, 'Fecha Atención', '', NULL, 50, NULL, NULL, NULL, NULL, '2018-11-08', NULL, NULL, NULL, NULL, '2018-11-08 22:37:19.359', 'root', '2018-11-08 22:37:19.359', 'root', true, true);
INSERT INTO public.datos VALUES (221, 'Atenciones', 38, 31, 'Medicina General', 7, 'Alergias', '', NULL, 47, NULL, true, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-11-08 22:37:19.363', 'root', '2018-11-08 22:37:19.363', 'root', true, true);
INSERT INTO public.datos VALUES (222, 'Atenciones', 38, 31, 'Medicina General', 8, 'Selección múltiple', '', '{"0": "Opción 01", "1": "Opción 02", "2": "Opción 03"}', 54, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{"0": "Opción 01"}', NULL, '2018-11-08 22:37:19.367', 'root', '2018-11-08 22:37:19.367', 'root', true, true);
INSERT INTO public.datos VALUES (250, 'Atenciones', 41, 31, 'Medicina General', 6, 'Fecha Atención', '', NULL, 50, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-11-10 23:13:07.912', 'root', '2018-11-10 23:13:07.912', 'root', true, true);
INSERT INTO public.datos VALUES (251, 'Atenciones', 41, 31, 'Medicina General', 7, 'Alergias', '', NULL, 47, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-11-10 23:13:07.915', 'root', '2018-11-10 23:13:07.915', 'root', true, true);
INSERT INTO public.datos VALUES (245, 'Atenciones', 41, 31, 'Medicina General', 1, 'Peso (kg)', '', NULL, 49, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-11-10 23:13:07.898', 'root', '2018-11-10 23:13:07.898', 'root', true, true);
INSERT INTO public.datos VALUES (246, 'Atenciones', 41, 31, 'Medicina General', 2, 'Altura (m)', '', NULL, 49, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-11-10 23:13:07.901', 'root', '2018-11-10 23:13:07.901', 'root', true, true);
INSERT INTO public.datos VALUES (247, 'Atenciones', 41, 31, 'Medicina General', 3, 'Presión arterial', '', NULL, 49, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-11-10 23:13:07.903', 'root', '2018-11-10 23:13:07.903', 'root', true, true);
INSERT INTO public.datos VALUES (248, 'Atenciones', 41, 31, 'Medicina General', 4, 'Temperatura (ºC)', '', NULL, 49, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-11-10 23:13:07.905', 'root', '2018-11-10 23:13:07.905', 'root', true, true);
INSERT INTO public.datos VALUES (252, 'Atenciones', 41, 31, 'Medicina General', 8, 'Selección múltiple', '', '{"0": "Opción 01", "1": "Opción 02", "2": "Opción 03"}', 54, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-11-10 23:13:07.918', 'root', '2018-11-10 23:13:07.918', 'root', true, true);
INSERT INTO public.datos VALUES (253, 'Atenciones', 41, 31, 'Medicina General', 9, 'Seleccione Una', '', '{"0": "Opción Uno", "1": "Opción Dos", "2": "Opción Tres", "3": "Opción Cuatro"}', 57, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-11-10 23:13:07.922', 'root', '2018-11-10 23:13:07.922', 'root', true, true);
INSERT INTO public.datos VALUES (254, 'Atenciones', 41, 31, 'Medicina General', 10, 'Archivo', 'Archivo', NULL, 55, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 66, '2018-11-10 23:13:07.926', 'root', '2018-11-10 23:13:07.926', 'root', true, true);
INSERT INTO public.datos VALUES (349, 'Atenciones', 51, 31, 'Medicina General', 5, 'Seleccione una opción', '', '{"0": "Opción 01", "1": "Opción 02", "2": "Opción 03"}', 53, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{"0": "Opción 01"}', NULL, '2018-11-11 19:33:19.68', 'root', '2018-11-11 19:33:19.68', 'root', true, true);
INSERT INTO public.datos VALUES (352, 'Atenciones', 51, 31, 'Medicina General', 8, 'Selección múltiple', '', '{"0": "Opción 01", "1": "Opción 02", "2": "Opción 03"}', 54, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{"0": "Opción 01"}', NULL, '2018-11-11 19:33:19.706', 'root', '2018-11-11 19:33:19.706', 'root', true, true);
INSERT INTO public.datos VALUES (353, 'Atenciones', 51, 31, 'Medicina General', 9, 'Seleccione Una', '', '{"0": "Opción Uno", "1": "Opción Dos", "2": "Opción Tres", "3": "Opción Cuatro"}', 57, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{"0": "Opción Uno"}', NULL, '2018-11-11 19:33:19.715', 'root', '2018-11-11 19:33:19.715', 'root', true, true);
INSERT INTO public.datos VALUES (375, 'Atenciones', 57, 33, 'Optometría', 1, 'Verdadero o Falso', 'Verdadero o Falso', NULL, 47, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-11-11 22:49:07.918', 'apvillalba', '2018-11-11 22:49:07.918', 'apvillalba', true, false);
INSERT INTO public.datos VALUES (354, 'Atenciones', 51, 31, 'Medicina General', 10, 'Archivo', 'Archivo', NULL, 55, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 76, '2018-11-11 19:33:19.723', 'root', '2018-11-11 19:33:19.723', 'root', true, true);
INSERT INTO public.datos VALUES (345, 'Atenciones', 51, 31, 'Medicina General', 1, 'Peso (kg)', '', NULL, 49, NULL, NULL, NULL, 12, NULL, NULL, NULL, NULL, NULL, '2018-11-11 19:33:19.648', 'root', '2018-11-11 19:33:19.648', 'root', true, true);
INSERT INTO public.datos VALUES (346, 'Atenciones', 51, 31, 'Medicina General', 2, 'Altura (m)', '', NULL, 49, NULL, NULL, NULL, 12, NULL, NULL, NULL, NULL, NULL, '2018-11-11 19:33:19.659', 'root', '2018-11-11 19:33:19.659', 'root', true, true);
INSERT INTO public.datos VALUES (347, 'Atenciones', 51, 31, 'Medicina General', 3, 'Presión arterial', '', NULL, 49, NULL, NULL, NULL, 34, NULL, NULL, NULL, NULL, NULL, '2018-11-11 19:33:19.666', 'root', '2018-11-11 19:33:19.666', 'root', true, true);
INSERT INTO public.datos VALUES (348, 'Atenciones', 51, 31, 'Medicina General', 4, 'Temperatura (ºC)', '', NULL, 49, NULL, NULL, NULL, 4, NULL, NULL, NULL, NULL, NULL, '2018-11-11 19:33:19.673', 'root', '2018-11-11 19:33:19.673', 'root', true, true);
INSERT INTO public.datos VALUES (376, 'Atenciones', 57, 33, 'Optometría', 2, 'Fecha y hora', 'Fecha y hora', NULL, 52, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-11-11 22:49:07.922', 'apvillalba', '2018-11-11 22:49:07.922', 'apvillalba', true, false);
INSERT INTO public.datos VALUES (350, 'Atenciones', 51, 31, 'Medicina General', 6, 'Fecha Atención', '', NULL, 50, NULL, NULL, NULL, NULL, '2018-11-11', NULL, NULL, NULL, NULL, '2018-11-11 19:33:19.695', 'root', '2018-11-11 19:33:19.695', 'root', true, true);
INSERT INTO public.datos VALUES (351, 'Atenciones', 51, 31, 'Medicina General', 7, 'Alergias', '', NULL, 47, NULL, true, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-11-11 19:33:19.701', 'root', '2018-11-11 19:33:19.701', 'root', true, true);
INSERT INTO public.datos VALUES (377, 'Atenciones', 57, 33, 'Optometría', 3, 'Suba un archivo', 'Suba un archivo', NULL, 55, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-11-11 22:49:07.925', 'apvillalba', '2018-11-11 22:49:07.925', 'apvillalba', true, false);
INSERT INTO public.datos VALUES (378, 'Atenciones', 57, 33, 'Optometría', 4, 'Selección', 'Selección', '{"0": "Opción 1", "1": "Opción 2"}', 54, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-11-11 22:49:07.928', 'apvillalba', '2018-11-11 22:49:07.928', 'apvillalba', true, false);
INSERT INTO public.datos VALUES (367, 'Atenciones', 55, 33, 'Optometría', 1, 'Verdadero o Falso', 'Verdadero o Falso', NULL, 47, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-11-11 22:39:25.958', 'apvillalba', '2018-11-11 22:39:25.958', 'apvillalba', true, false);
INSERT INTO public.datos VALUES (368, 'Atenciones', 55, 33, 'Optometría', 2, 'Fecha y hora', 'Fecha y hora', NULL, 52, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-11-11 22:39:25.986', 'apvillalba', '2018-11-11 22:39:25.986', 'apvillalba', true, false);
INSERT INTO public.datos VALUES (369, 'Atenciones', 55, 33, 'Optometría', 3, 'Suba un archivo', 'Suba un archivo', NULL, 55, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-11-11 22:39:25.989', 'apvillalba', '2018-11-11 22:39:25.989', 'apvillalba', true, false);
INSERT INTO public.datos VALUES (370, 'Atenciones', 55, 33, 'Optometría', 4, 'Selección', 'Selección', '{"0": "Opción 1", "1": "Opción 2"}', 54, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-11-11 22:39:25.993', 'apvillalba', '2018-11-11 22:39:25.993', 'apvillalba', true, false);
INSERT INTO public.datos VALUES (383, 'Atenciones', 51, 33, 'Optometría', 1, 'Verdadero o Falso', 'Verdadero o Falso', NULL, 47, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-11-13 16:41:46.234', 'root', '2018-11-13 16:41:46.234', 'root', true, false);
INSERT INTO public.datos VALUES (384, 'Atenciones', 51, 33, 'Optometría', 2, 'Fecha y hora', 'Fecha y hora', NULL, 52, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-11-13 16:41:46.247', 'root', '2018-11-13 16:41:46.247', 'root', true, false);
INSERT INTO public.datos VALUES (385, 'Atenciones', 51, 33, 'Optometría', 3, 'Suba un archivo', 'Suba un archivo', NULL, 55, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-11-13 16:41:46.264', 'root', '2018-11-13 16:41:46.264', 'root', true, false);
INSERT INTO public.datos VALUES (386, 'Atenciones', 51, 33, 'Optometría', 4, 'Selección', 'Selección', '{"0": "Opción 1", "1": "Opción 2"}', 54, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-11-13 16:41:46.272', 'root', '2018-11-13 16:41:46.272', 'root', true, false);
INSERT INTO public.datos VALUES (387, 'Atenciones', 10, 33, 'Optometría', 1, 'Verdadero o Falso', 'Verdadero o Falso', NULL, 47, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-11-13 16:42:01.758', 'root', '2018-11-13 16:42:01.758', 'root', true, false);
INSERT INTO public.datos VALUES (388, 'Atenciones', 10, 33, 'Optometría', 2, 'Fecha y hora', 'Fecha y hora', NULL, 52, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-11-13 16:42:01.789', 'root', '2018-11-13 16:42:01.789', 'root', true, false);
INSERT INTO public.datos VALUES (389, 'Atenciones', 10, 33, 'Optometría', 3, 'Suba un archivo', 'Suba un archivo', NULL, 55, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-11-13 16:42:01.805', 'root', '2018-11-13 16:42:01.805', 'root', true, false);
INSERT INTO public.datos VALUES (390, 'Atenciones', 10, 33, 'Optometría', 4, 'Selección', 'Selección', '{"0": "Opción 1", "1": "Opción 2"}', 54, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-11-13 16:42:01.813', 'root', '2018-11-13 16:42:01.813', 'root', true, false);
INSERT INTO public.datos VALUES (399, 'Atenciones', 61, 33, 'Optometría', 1, 'Verdadero o Falso', 'Verdadero o Falso', NULL, 47, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-11-13 19:16:16.327', 'root', '2018-11-13 19:16:16.327', 'root', true, false);
INSERT INTO public.datos VALUES (400, 'Atenciones', 61, 33, 'Optometría', 2, 'Fecha y hora', 'Fecha y hora', NULL, 52, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-11-13 19:16:16.349', 'root', '2018-11-13 19:16:16.349', 'root', true, false);
INSERT INTO public.datos VALUES (401, 'Atenciones', 61, 33, 'Optometría', 3, 'Suba un archivo', 'Suba un archivo', NULL, 55, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-11-13 19:16:16.375', 'root', '2018-11-13 19:16:16.375', 'root', true, false);
INSERT INTO public.datos VALUES (402, 'Atenciones', 61, 33, 'Optometría', 4, 'Selección', 'Selección', '{"0": "Opción 1", "1": "Opción 2"}', 54, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-11-13 19:16:16.399', 'root', '2018-11-13 19:16:16.399', 'root', true, false);
INSERT INTO public.datos VALUES (403, 'Atenciones', 62, 33, 'Optometría', 1, 'Verdadero o Falso', 'Verdadero o Falso', NULL, 47, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-11-13 19:18:54.435', 'root', '2018-11-13 19:18:54.435', 'root', true, false);
INSERT INTO public.datos VALUES (404, 'Atenciones', 62, 33, 'Optometría', 2, 'Fecha y hora', 'Fecha y hora', NULL, 52, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-11-13 19:18:54.459', 'root', '2018-11-13 19:18:54.459', 'root', true, false);
INSERT INTO public.datos VALUES (405, 'Atenciones', 62, 33, 'Optometría', 3, 'Suba un archivo', 'Suba un archivo', NULL, 55, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-11-13 19:18:54.486', 'root', '2018-11-13 19:18:54.486', 'root', true, false);
INSERT INTO public.datos VALUES (406, 'Atenciones', 62, 33, 'Optometría', 4, 'Selección', 'Selección', '{"0": "Opción 1", "1": "Opción 2"}', 54, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-11-13 19:18:54.51', 'root', '2018-11-13 19:18:54.51', 'root', true, false);
INSERT INTO public.datos VALUES (407, 'Atenciones', 63, 33, 'Optometría', 1, 'Verdadero o Falso', 'Verdadero o Falso', NULL, 47, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-11-15 22:22:40.484', 'apvillalba', '2018-11-15 22:22:40.484', 'apvillalba', true, false);
INSERT INTO public.datos VALUES (408, 'Atenciones', 63, 33, 'Optometría', 2, 'Fecha y hora', 'Fecha y hora', NULL, 52, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-11-15 22:22:40.496', 'apvillalba', '2018-11-15 22:22:40.496', 'apvillalba', true, false);
INSERT INTO public.datos VALUES (409, 'Atenciones', 63, 33, 'Optometría', 3, 'Suba un archivo', 'Suba un archivo', NULL, 55, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-11-15 22:22:40.502', 'apvillalba', '2018-11-15 22:22:40.502', 'apvillalba', true, false);
INSERT INTO public.datos VALUES (410, 'Atenciones', 63, 33, 'Optometría', 4, 'Selección', 'Selección', '{"0": "Opción 1", "1": "Opción 2"}', 54, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-11-15 22:22:40.508', 'apvillalba', '2018-11-15 22:22:40.508', 'apvillalba', true, false);
INSERT INTO public.datos VALUES (413, 'Atenciones', 64, 33, 'Optometría', 3, 'Suba un archivo', 'Suba un archivo', NULL, 55, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 78, '2018-11-18 18:54:42.46', 'root', '2018-11-18 18:54:42.46', 'root', true, false);
INSERT INTO public.datos VALUES (411, 'Atenciones', 64, 33, 'Optometría', 1, 'Verdadero o Falso', 'Verdadero o Falso', NULL, 47, NULL, true, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-11-18 18:54:42.444', 'root', '2018-11-18 18:54:42.444', 'root', true, false);
INSERT INTO public.datos VALUES (412, 'Atenciones', 64, 33, 'Optometría', 2, 'Fecha y hora', 'Fecha y hora', NULL, 52, NULL, NULL, NULL, NULL, NULL, NULL, '2018-11-18 18:56:10', NULL, NULL, '2018-11-18 18:54:42.454', 'root', '2018-11-18 18:54:42.454', 'root', true, false);
INSERT INTO public.datos VALUES (414, 'Atenciones', 64, 33, 'Optometría', 4, 'Selección', 'Selección', '{"0": "Opción 1", "1": "Opción 2"}', 54, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{"0": "Opción 1"}', NULL, '2018-11-18 18:54:42.465', 'root', '2018-11-18 18:54:42.465', 'root', true, false);
INSERT INTO public.datos VALUES (415, 'Atenciones', 65, 33, 'Optometría', 1, 'Verdadero o Falso', 'Verdadero o Falso', NULL, 47, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2019-01-08 17:29:11.564', 'root', '2019-01-08 17:29:11.564', 'root', true, false);
INSERT INTO public.datos VALUES (416, 'Atenciones', 65, 33, 'Optometría', 2, 'Fecha y hora', 'Fecha y hora', NULL, 52, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2019-01-08 17:29:11.574', 'root', '2019-01-08 17:29:11.574', 'root', true, false);
INSERT INTO public.datos VALUES (417, 'Atenciones', 65, 33, 'Optometría', 3, 'Suba un archivo', 'Suba un archivo', NULL, 55, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2019-01-08 17:29:11.579', 'root', '2019-01-08 17:29:11.579', 'root', true, false);
INSERT INTO public.datos VALUES (418, 'Atenciones', 65, 33, 'Optometría', 4, 'Selección', 'Selección', '{"0": "Opción 1", "1": "Opción 2"}', 54, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2019-01-08 17:29:11.585', 'root', '2019-01-08 17:29:11.585', 'root', true, false);
INSERT INTO public.datos VALUES (420, 'Atenciones', 66, 33, 'Optometría', 2, 'Fecha y hora', 'Fecha y hora', NULL, 52, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2019-01-26 22:59:42.557', 'root', '2019-01-26 22:59:42.557', 'root', true, false);
INSERT INTO public.datos VALUES (421, 'Atenciones', 66, 33, 'Optometría', 3, 'Suba un archivo', 'Suba un archivo', NULL, 55, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 79, '2019-01-26 22:59:42.562', 'root', '2019-01-26 22:59:42.562', 'root', true, false);
INSERT INTO public.datos VALUES (419, 'Atenciones', 66, 33, 'Optometría', 1, 'Verdadero o Falso', 'Verdadero o Falso', NULL, 47, NULL, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2019-01-26 22:59:42.547', 'root', '2019-01-26 22:59:42.547', 'root', true, false);
INSERT INTO public.datos VALUES (422, 'Atenciones', 66, 33, 'Optometría', 4, 'Selección', 'Selección', '{"0": "Opción 1", "1": "Opción 2"}', 54, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2019-01-26 22:59:42.568', 'root', '2019-01-26 22:59:42.568', 'root', true, false);
INSERT INTO public.datos VALUES (427, 'Atenciones', 68, 33, 'Optometría', 1, 'Verdadero o Falso', 'Verdadero o Falso', NULL, 47, NULL, true, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2019-01-27 00:46:25.998', 'root', '2019-01-27 00:46:25.998', 'root', true, false);
INSERT INTO public.datos VALUES (429, 'Atenciones', 68, 33, 'Optometría', 3, 'Suba un archivo', 'Suba un archivo', NULL, 55, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 80, '2019-01-27 00:46:26.006', 'root', '2019-01-27 00:46:26.006', 'root', true, false);
INSERT INTO public.datos VALUES (428, 'Atenciones', 68, 33, 'Optometría', 2, 'Fecha y hora', 'Fecha y hora', NULL, 52, NULL, NULL, NULL, NULL, NULL, NULL, '2019-01-18 00:00:00', NULL, NULL, '2019-01-27 00:46:26.002', 'root', '2019-01-27 00:46:26.002', 'root', true, false);
INSERT INTO public.datos VALUES (430, 'Atenciones', 68, 33, 'Optometría', 4, 'Selección', 'Selección', '{"0": "Opción 1", "1": "Opción 2"}', 54, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{"0": "Opción 1"}', NULL, '2019-01-27 00:46:26.01', 'root', '2019-01-27 00:46:26.01', 'root', true, false);
INSERT INTO public.datos VALUES (432, 'Atenciones', 69, 33, 'Optometría', 2, 'Fecha y hora', 'Fecha y hora', NULL, 52, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2019-01-27 21:28:24.314', 'root', '2019-01-27 21:28:24.314', 'root', true, false);
INSERT INTO public.datos VALUES (433, 'Atenciones', 69, 33, 'Optometría', 3, 'Suba un archivo', 'Suba un archivo', NULL, 55, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2019-01-27 21:28:24.316', 'root', '2019-01-27 21:28:24.316', 'root', true, false);
INSERT INTO public.datos VALUES (431, 'Atenciones', 69, 33, 'Optometría', 1, 'Verdadero o Falso', 'Verdadero o Falso', NULL, 47, NULL, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2019-01-27 21:28:24.307', 'root', '2019-01-27 21:28:24.307', 'root', true, false);
INSERT INTO public.datos VALUES (434, 'Atenciones', 69, 33, 'Optometría', 4, 'Selección', 'Selección', '{"0": "Opción 1", "1": "Opción 2"}', 54, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2019-01-27 21:28:24.319', 'root', '2019-01-27 21:28:24.319', 'root', true, false);


--
-- TOC entry 3229 (class 0 OID 16435)
-- Dependencies: 206
-- Data for Name: direcciones; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.direcciones VALUES (3, 'Virgilio Castillo', 'E3-48', 'Domingo Dobbie', 'PB', 'UPC El Camal', '2654855', '0998319195', NULL, NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.direcciones VALUES (4, 'Vizcaya', 'N-24G', 'Pontevedra', 'PB', 'La Floresta', '0992529704', '', NULL, NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.direcciones VALUES (5, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.direcciones VALUES (6, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.direcciones VALUES (7, 'Av. 10 de agosto', 'N-15-21', 'Riofrío', NULL, '2do piso Oficina 208', '2506859', '2526077', NULL, NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.direcciones VALUES (1, 'OE13G1', 'S/N', 'S38B', 'PB', 'El Girón del Sur', '3030160', '0987328457', NULL, NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.direcciones VALUES (10, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.direcciones VALUES (11, '1', '1', '1', NULL, '', '1', '', NULL, NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.direcciones VALUES (12, '1', '1', '1', NULL, '', '1', '', NULL, NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.direcciones VALUES (14, 'Colón', '10 de Agosto', 'N23', '15', 'Tecnomega Edificio', '3030160', '0987328457', NULL, NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.direcciones VALUES (8, 'OE13G', 'S/N', 'S38B', '1', 'Lote 102', '0987328457', '3030160', 'Quito', NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.direcciones VALUES (15, 'América', 'Colón', 'N75', '7', 'Nada', '3030160', '0987328427', '', NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.direcciones VALUES (16, '', '', '', '', '', '', '', '', NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.direcciones VALUES (2, 'Vizcaya', 'N-24G', 'Pontevedra', 'Condominios del Sur 205', 'La Floresta', '322-7638', '0992529703', 'Quito', NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.direcciones VALUES (9, 'machala', 'n-123', ' vaca de castro', '2', 'frente banco pichicha', '0995719800', '', '', NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.direcciones VALUES (13, '1', '1', '1', '', '', '1', '', '', NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.direcciones VALUES (19, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.direcciones VALUES (20, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.direcciones VALUES (21, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.direcciones VALUES (22, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.direcciones VALUES (23, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.direcciones VALUES (24, 'Calle 1', 'N56', 'Calle 2', '', '', '3030160', '', 'Quito', NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.direcciones VALUES (25, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.direcciones VALUES (26, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.direcciones VALUES (28, 'Av. Teniente Hugo Ortiz', 'OE5-11', 'Fray Francisco de San Miguel', '2º piso', 'Tribuna del Sur', '2662005', '098732885', 'Quito', NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.direcciones VALUES (18, 'OE13G', 'S/N', 'S38B', '1º piso', 'Barrio Colinas del Sur', '3030160', '0987328457', 'Quito', NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.direcciones VALUES (29, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.direcciones VALUES (30, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.direcciones VALUES (31, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.direcciones VALUES (32, 'P000003', 'P000003', 'P000003', 'P000003', 'P000003', 'P000003', 'P000003', 'Quito', NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.direcciones VALUES (33, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.direcciones VALUES (34, '', '', '', '', '', '', '', 'Quito', NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.direcciones VALUES (35, '', '', '', '', '', '', '', 'Quito', NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.direcciones VALUES (36, '', '', '', '', '', '', '', 'Quito', NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.direcciones VALUES (37, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'Quito', NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.direcciones VALUES (38, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'Quito', NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.direcciones VALUES (39, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'Quito', NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.direcciones VALUES (40, '', '', '', '', '', '', '', 'Quito', NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.direcciones VALUES (41, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'Quito', NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.direcciones VALUES (42, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'Quito', NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.direcciones VALUES (43, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'Quito', NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.direcciones VALUES (44, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'Quito', NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.direcciones VALUES (17, 'OE13G', 'S/N', 'S38B', '1º piso', 'Barrio Colinas del Sur', '3030160', '0987328457', 'QuitoG', NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.direcciones VALUES (45, 'OE13G', 'S/N', 'S38B', 'Ordóñez', 'El Girón PB', '3030160', '0987328457', 'Quito', NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.direcciones VALUES (46, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, true);


--
-- TOC entry 3231 (class 0 OID 16443)
-- Dependencies: 208
-- Data for Name: formulas; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.formulas VALUES (5, 20, '{"d": "", "i": ""}', '{"d": "", "i": ""}', '{"d": "", "i": ""}', '{"d": "", "i": "d"}', '{"d": "w", "i": "w"}', '{"d": "w", "i": "w"}', '{"d": "", "i": ""}', '{"d": "", "i": ""}', '{"d": "", "i": ""}', NULL, NULL, NULL, NULL, '2018-09-11 17:57:09.431', 'apvillalba', '2018-09-11 18:16:35.015', 'apvillalba', true);
INSERT INTO public.formulas VALUES (6, 24, '{"d": "", "i": ""}', '{"d": "", "i": ""}', '{"d": "", "i": ""}', '{"d": "", "i": ""}', '{"d": "", "i": ""}', '{"d": "", "i": ""}', '{"d": "", "i": ""}', '{"d": "", "i": ""}', '{"d": "", "i": ""}', NULL, NULL, NULL, NULL, '2018-09-13 16:42:25.637', 'apvillalba', '2018-09-13 16:42:25.637', 'apvillalba', true);
INSERT INTO public.formulas VALUES (9, 28, '{"d": " 1", "i": "4"}', '{"d": "2", "i": "5 "}', '{"d": "3", "i": "6"}', '{"d": "12", "i": "6"}', '{"d": "13", "i": "7"}', '{"d": "14", "i": "8"}', '{"d": "15", "i": "9"}', '{"d": "16", "i": "10"}', '{"d": "17", "i": "11"}', NULL, NULL, NULL, NULL, '2018-09-14 09:44:17.186', 'apvillalba', '2018-09-14 15:21:59.383', 'apvillalba', true);
INSERT INTO public.formulas VALUES (10, 29, '{"d": "", "i": ""}', '{"d": "", "i": ""}', '{"d": "", "i": ""}', '{"d": "", "i": ""}', '{"d": "", "i": ""}', '{"d": "", "i": ""}', '{"d": "", "i": ""}', '{"d": "", "i": ""}', '{"d": "", "i": ""}', NULL, NULL, NULL, NULL, '2018-09-19 05:48:31.297', 'apvillalba', '2018-09-19 05:48:31.24', 'apvillalba', true);
INSERT INTO public.formulas VALUES (11, 30, '{"d": "", "i": ""}', '{"d": "", "i": ""}', '{"d": "", "i": ""}', '{"d": "", "i": ""}', '{"d": "", "i": ""}', '{"d": "", "i": ""}', '{"d": "", "i": ""}', '{"d": "", "i": ""}', '{"d": "", "i": ""}', NULL, NULL, NULL, NULL, '2018-11-02 00:53:30.237', 'apvillalba', '2018-11-02 00:53:43.046', 'apvillalba', true);
INSERT INTO public.formulas VALUES (12, 36, '{"d": "", "i": ""}', '{"d": "", "i": ""}', '{"d": "", "i": ""}', '{"d": "", "i": ""}', '{"d": "", "i": ""}', '{"d": "", "i": ""}', '{"d": "", "i": ""}', '{"d": "", "i": ""}', '{"d": "", "i": ""}', NULL, NULL, NULL, NULL, '2018-11-08 22:34:12.812', 'apvillalba', '2018-11-08 22:34:12.796', 'apvillalba', true);
INSERT INTO public.formulas VALUES (18, 57, '{"d": null, "i": null}', '{"d": null, "i": null}', '{"d": null, "i": null}', '{"d": null, "i": null}', '{"d": null, "i": null}', '{"d": null, "i": null}', '{"d": null, "i": null}', '{"d": null, "i": null}', '{"d": null, "i": null}', NULL, NULL, NULL, NULL, '2018-11-11 22:49:07.912', 'apvillalba', '2018-11-11 22:49:07.907', 'apvillalba', true);
INSERT INTO public.formulas VALUES (22, 61, '{"d": null, "i": null}', '{"d": null, "i": null}', '{"d": null, "i": null}', '{"d": null, "i": null}', '{"d": null, "i": null}', '{"d": null, "i": null}', '{"d": null, "i": null}', '{"d": null, "i": null}', '{"d": null, "i": null}', NULL, NULL, NULL, NULL, '2018-11-13 19:16:16.227', 'root', '2018-11-13 19:16:16.188', 'root', true);
INSERT INTO public.formulas VALUES (23, 62, '{"d": null, "i": null}', '{"d": null, "i": null}', '{"d": null, "i": null}', '{"d": null, "i": null}', '{"d": null, "i": null}', '{"d": null, "i": null}', '{"d": null, "i": null}', '{"d": null, "i": null}', '{"d": null, "i": null}', NULL, NULL, NULL, NULL, '2018-11-13 19:18:54.263', 'root', '2018-11-13 19:18:54.242', 'root', true);
INSERT INTO public.formulas VALUES (24, 63, '{"d": null, "i": null}', '{"d": null, "i": null}', '{"d": null, "i": null}', '{"d": null, "i": null}', '{"d": null, "i": null}', '{"d": null, "i": null}', '{"d": null, "i": null}', '{"d": null, "i": null}', '{"d": null, "i": null}', NULL, NULL, NULL, NULL, '2018-11-15 22:22:40.446', 'apvillalba', '2018-11-15 22:22:31.969', 'apvillalba', true);
INSERT INTO public.formulas VALUES (25, 64, '{"d": "43", "i": "+69"}', '{"d": "+663", "i": "-98"}', '{"d": "+98", "i": "-87"}', '{"d": "10.0", "i": "-0.2"}', '{"d": "11.8", "i": "+0.1"}', '{"d": "12.8", "i": "1.7"}', '{"d": "5.9", "i": "3.2"}', '{"d": "60.0", "i": "5.1"}', '{"d": "+18", "i": "7.6"}', 4, '12', NULL, 'Nunca haga cosas malas.', '2018-11-18 18:54:42.409', 'root', '2018-11-18 18:56:41.359', 'root', true);
INSERT INTO public.formulas VALUES (26, 65, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2019-01-08 17:29:11.536', 'root', '2019-01-08 17:29:11.516', 'root', true);
INSERT INTO public.formulas VALUES (27, 66, '{"d": "", "i": ""}', '{"d": "", "i": ""}', '{"d": "", "i": ""}', '{"d": "", "i": ""}', '{"d": "", "i": ""}', '{"d": "", "i": ""}', '{"d": "", "i": ""}', '{"d": "", "i": ""}', '{"d": "", "i": ""}', NULL, '', NULL, '', '2019-01-26 22:59:42.504', 'root', '2019-01-26 23:14:03.264', 'root', true);
INSERT INTO public.formulas VALUES (29, 68, '{"d": "", "i": ""}', '{"d": "", "i": ""}', '{"d": "", "i": ""}', '{"d": "", "i": ""}', '{"d": "", "i": ""}', '{"d": "", "i": ""}', '{"d": "", "i": ""}', '{"d": "", "i": ""}', '{"d": "", "i": ""}', NULL, '', NULL, '', '2019-01-27 00:46:25.978', 'root', '2019-01-27 11:52:37.998', 'root', true);
INSERT INTO public.formulas VALUES (30, 69, '{"d": "", "i": ""}', '{"d": "", "i": ""}', '{"d": "", "i": ""}', '{"d": "", "i": ""}', '{"d": "", "i": ""}', '{"d": "", "i": ""}', '{"d": "", "i": ""}', '{"d": "", "i": ""}', '{"d": "", "i": ""}', NULL, '', NULL, '', '2019-01-27 21:28:24.289', 'root', '2019-01-27 21:28:28.841', 'root', true);


--
-- TOC entry 3233 (class 0 OID 16451)
-- Dependencies: 210
-- Data for Name: horarios; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.horarios VALUES (2, 1, 2, 35, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (3, 1, 3, 35, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (4, 1, 4, 35, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (5, 1, 5, 35, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (6, 1, 6, 35, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (7, 1, 7, 35, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (8, 1, 8, 35, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (9, 1, 9, 35, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (10, 1, 10, 35, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (11, 1, 11, 35, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (12, 1, 12, 35, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (13, 1, 13, 35, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (14, 1, 14, 35, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (15, 1, 15, 35, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (16, 1, 16, 35, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (17, 1, 17, 35, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (18, 1, 18, 35, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (19, 1, 19, 35, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (20, 1, 20, 35, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (21, 1, 21, 35, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (22, 1, 22, 35, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (23, 1, 23, 35, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (24, 1, 24, 35, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (25, 1, 1, 36, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (26, 1, 2, 36, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (27, 1, 3, 36, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (28, 1, 4, 36, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (29, 1, 5, 36, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (30, 1, 6, 36, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (31, 1, 7, 36, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (32, 1, 8, 36, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (33, 1, 9, 36, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (34, 1, 10, 36, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (35, 1, 11, 36, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (36, 1, 12, 36, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (37, 1, 13, 36, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (38, 1, 14, 36, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (39, 1, 15, 36, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (40, 1, 16, 36, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (41, 1, 17, 36, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (42, 1, 18, 36, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (43, 1, 19, 36, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (44, 1, 20, 36, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (45, 1, 21, 36, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (46, 1, 22, 36, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (47, 1, 23, 36, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (48, 1, 24, 36, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (49, 1, 1, 37, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (50, 1, 2, 37, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (51, 1, 3, 37, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (52, 1, 4, 37, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (53, 1, 5, 37, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (54, 1, 6, 37, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (55, 1, 7, 37, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (56, 1, 8, 37, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (57, 1, 9, 37, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (58, 1, 10, 37, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (59, 1, 11, 37, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (60, 1, 12, 37, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (61, 1, 13, 37, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (62, 1, 14, 37, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (63, 1, 15, 37, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (64, 1, 16, 37, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (65, 1, 17, 37, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (66, 1, 18, 37, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (67, 1, 19, 37, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (68, 1, 20, 37, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (69, 1, 21, 37, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (70, 1, 22, 37, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (71, 1, 23, 37, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (72, 1, 24, 37, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (73, 1, 1, 38, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (74, 1, 2, 38, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (75, 1, 3, 38, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (76, 1, 4, 38, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (77, 1, 5, 38, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (78, 1, 6, 38, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (79, 1, 7, 38, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (80, 1, 8, 38, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (81, 1, 9, 38, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (82, 1, 10, 38, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (83, 1, 11, 38, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (84, 1, 12, 38, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (85, 1, 13, 38, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (86, 1, 14, 38, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (87, 1, 15, 38, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (88, 1, 16, 38, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (89, 1, 17, 38, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (90, 1, 18, 38, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (91, 1, 19, 38, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (92, 1, 20, 38, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (93, 1, 21, 38, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (94, 1, 22, 38, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (95, 1, 23, 38, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (96, 1, 24, 38, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (97, 1, 1, 39, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (98, 1, 2, 39, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (99, 1, 3, 39, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (100, 1, 4, 39, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (101, 1, 5, 39, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (102, 1, 6, 39, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (103, 1, 7, 39, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (104, 1, 8, 39, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (105, 1, 9, 39, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (106, 1, 10, 39, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (107, 1, 11, 39, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (108, 1, 12, 39, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (109, 1, 13, 39, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (110, 1, 14, 39, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (111, 1, 15, 39, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (112, 1, 16, 39, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (113, 1, 17, 39, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (114, 1, 18, 39, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (115, 1, 19, 39, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (116, 1, 20, 39, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (117, 1, 21, 39, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (118, 1, 22, 39, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (119, 1, 23, 39, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (120, 1, 24, 39, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (121, 1, 1, 40, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (122, 1, 2, 40, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (123, 1, 3, 40, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (124, 1, 4, 40, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (125, 1, 5, 40, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (126, 1, 6, 40, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (127, 1, 7, 40, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (128, 1, 8, 40, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (129, 1, 9, 40, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (130, 1, 10, 40, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (131, 1, 11, 40, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (132, 1, 12, 40, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (133, 1, 13, 40, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (134, 1, 14, 40, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (135, 1, 15, 40, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (136, 1, 16, 40, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (137, 1, 17, 40, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (138, 1, 18, 40, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (139, 1, 19, 40, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (140, 1, 20, 40, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (141, 1, 21, 40, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (142, 1, 22, 40, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (143, 1, 23, 40, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (144, 1, 24, 40, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (145, 1, 1, 41, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (146, 1, 2, 41, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (147, 1, 3, 41, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (148, 1, 4, 41, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (149, 1, 5, 41, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (150, 1, 6, 41, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (151, 1, 7, 41, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (152, 1, 8, 41, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (153, 1, 9, 41, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (154, 1, 10, 41, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (155, 1, 11, 41, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (156, 1, 12, 41, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (157, 1, 13, 41, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (158, 1, 14, 41, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (159, 1, 15, 41, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (160, 1, 16, 41, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (161, 1, 17, 41, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (162, 1, 18, 41, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (163, 1, 19, 41, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (164, 1, 20, 41, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (165, 1, 21, 41, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (166, 1, 22, 41, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (167, 1, 23, 41, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (168, 1, 24, 41, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-05 22:27:38.801359', 'root', true);
INSERT INTO public.horarios VALUES (1, 1, 1, 35, NULL, '2018-08-05 22:27:38.801359', 'root', '2018-08-15 11:38:00.693', 'imordonez', true);
INSERT INTO public.horarios VALUES (171, 5, 3, 35, NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.horarios VALUES (172, 5, 4, 35, NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.horarios VALUES (173, 5, 5, 35, NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.horarios VALUES (174, 5, 6, 35, NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.horarios VALUES (175, 5, 7, 35, NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.horarios VALUES (176, 5, 8, 35, NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.horarios VALUES (177, 5, 9, 35, NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.horarios VALUES (178, 5, 10, 35, NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.horarios VALUES (179, 5, 11, 35, NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.horarios VALUES (180, 5, 12, 35, NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.horarios VALUES (181, 5, 13, 35, NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.horarios VALUES (182, 5, 14, 35, NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.horarios VALUES (183, 5, 15, 35, NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.horarios VALUES (184, 5, 16, 35, NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.horarios VALUES (185, 5, 17, 35, NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.horarios VALUES (186, 5, 18, 35, NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.horarios VALUES (187, 5, 19, 35, NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.horarios VALUES (188, 5, 20, 35, NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.horarios VALUES (189, 5, 21, 35, NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.horarios VALUES (190, 5, 22, 35, NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.horarios VALUES (191, 5, 23, 35, NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.horarios VALUES (192, 5, 24, 35, NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.horarios VALUES (193, 5, 1, 35, NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.horarios VALUES (194, 5, 2, 35, NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.horarios VALUES (195, 5, 1, 36, NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.horarios VALUES (196, 5, 2, 36, NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.horarios VALUES (197, 5, 3, 36, NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.horarios VALUES (198, 5, 4, 36, NULL, NULL, NULL, NULL, NULL, true);


--
-- TOC entry 3235 (class 0 OID 16459)
-- Dependencies: 212
-- Data for Name: horas; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.horas VALUES (2, '02º Hora', '01:00:00', '02:00:00', 1, true, '02º Hora de Atención', '2018-08-05 21:45:06.06436', 'root', '2018-08-05 21:45:06.06436', 'root', 'H02');
INSERT INTO public.horas VALUES (3, '03º Hora', '02:00:00', '03:00:00', 1, true, '03º Hora de Atención', '2018-08-05 21:45:06.06436', 'root', '2018-08-05 21:45:06.06436', 'root', 'H03');
INSERT INTO public.horas VALUES (7, '07º Hora', '06:00:00', '07:00:00', 1, true, '07º Hora de Atención', '2018-08-05 21:45:06.06436', 'root', '2018-08-05 21:45:06.06436', 'root', 'H07');
INSERT INTO public.horas VALUES (8, '08º Hora', '07:00:00', '08:00:00', 1, true, '08º Hora de Atención', '2018-08-05 21:45:06.06436', 'root', '2018-08-05 21:45:06.06436', 'root', 'H08');
INSERT INTO public.horas VALUES (9, '09º Hora', '08:00:00', '09:00:00', 1, true, '09º Hora de Atención', '2018-08-05 21:45:06.06436', 'root', '2018-08-05 21:45:06.06436', 'root', 'H09');
INSERT INTO public.horas VALUES (19, '19º Hora', '18:00:00', '19:00:00', 1, true, '19º Hora de Atención', '2018-08-05 21:45:06.06436', 'root', '2018-08-05 21:45:06.06436', 'root', 'H19');
INSERT INTO public.horas VALUES (10, '10º Hora', '09:00:00', '10:00:00', 1, true, '10º Hora de Atención', '2018-08-05 21:45:06.06436', 'root', '2018-08-05 21:45:06.06436', 'root', 'H10');
INSERT INTO public.horas VALUES (11, '11º Hora', '10:00:00', '11:00:00', 1, true, '11º Hora de Atención', '2018-08-05 21:45:06.06436', 'root', '2018-08-05 21:45:06.06436', 'root', 'H11');
INSERT INTO public.horas VALUES (4, '04º Hora', '03:00:00', '04:00:00', 1, true, '04º Hora de Atención', '2018-08-05 21:45:06.06436', 'root', '2018-08-05 21:45:06.06436', 'root', 'H04');
INSERT INTO public.horas VALUES (5, '05º Hora', '04:00:00', '05:00:00', 1, true, '05º Hora de Atención', '2018-08-05 21:45:06.06436', 'root', '2018-08-05 21:45:06.06436', 'root', 'H05');
INSERT INTO public.horas VALUES (6, '06º Hora', '05:00:00', '06:00:00', 1, true, '06º Hora de Atención', '2018-08-05 21:45:06.06436', 'root', '2018-08-05 21:45:06.06436', 'root', 'H06');
INSERT INTO public.horas VALUES (12, '12º Hora', '11:00:00', '12:00:00', 1, true, '12º Hora de Atención', '2018-08-05 21:45:06.06436', 'root', '2018-08-05 21:45:06.06436', 'root', 'H12');
INSERT INTO public.horas VALUES (13, '13º Hora', '12:00:00', '13:00:00', 1, true, '13º Hora de Atención', '2018-08-05 21:45:06.06436', 'root', '2018-08-05 21:45:06.06436', 'root', 'H13');
INSERT INTO public.horas VALUES (14, '14º Hora', '13:00:00', '14:00:00', 1, true, '14º Hora de Atención', '2018-08-05 21:45:06.06436', 'root', '2018-08-05 21:45:06.06436', 'root', 'H14');
INSERT INTO public.horas VALUES (15, '15º Hora', '14:00:00', '15:00:00', 1, true, '15º Hora de Atención', '2018-08-05 21:45:06.06436', 'root', '2018-08-05 21:45:06.06436', 'root', 'H15');
INSERT INTO public.horas VALUES (16, '16º Hora', '15:00:00', '16:00:00', 1, true, '16º Hora de Atención', '2018-08-05 21:45:06.06436', 'root', '2018-08-05 21:45:06.06436', 'root', 'H16');
INSERT INTO public.horas VALUES (17, '17º Hora', '16:00:00', '17:00:00', 1, true, '17º Hora de Atención', '2018-08-05 21:45:06.06436', 'root', '2018-08-05 21:45:06.06436', 'root', 'H17');
INSERT INTO public.horas VALUES (18, '18º Hora', '17:00:00', '18:00:00', 1, true, '18º Hora de Atención', '2018-08-05 21:45:06.06436', 'root', '2018-08-05 21:45:06.06436', 'root', 'H18');
INSERT INTO public.horas VALUES (20, '20º Hora', '19:00:00', '20:00:00', 1, true, '20º Hora de Atención', '2018-08-05 21:45:06.06436', 'root', '2018-08-05 21:45:06.06436', 'root', 'H20');
INSERT INTO public.horas VALUES (21, '21º Hora', '20:00:00', '21:00:00', 1, true, '21º Hora de Atención', '2018-08-05 21:45:06.06436', 'root', '2018-08-05 21:45:06.06436', 'root', 'H21');
INSERT INTO public.horas VALUES (22, '22º Hora', '21:00:00', '22:00:00', 1, true, '22º Hora de Atención', '2018-08-05 21:45:06.06436', 'root', '2018-08-05 21:45:06.06436', 'root', 'H22');
INSERT INTO public.horas VALUES (23, '23º Hora', '22:00:00', '23:00:00', 1, true, '23º Hora de Atención', '2018-08-05 21:45:06.06436', 'root', '2018-08-05 21:45:06.06436', 'root', 'H23');
INSERT INTO public.horas VALUES (24, '24º Hora', '23:00:00', '00:00:00', 1, true, '24º Hora de Atención', '2018-08-05 21:45:06.06436', 'root', '2018-08-05 21:45:06.06436', 'root', 'H24');
INSERT INTO public.horas VALUES (1, '01º Hora', '00:00:00', '01:00:00', 1, true, '01º Hora de Atención', '2018-08-05 21:45:06.06436', 'root', '2018-08-12 06:49:51.265', 'imordonez', 'H01');


--
-- TOC entry 3237 (class 0 OID 16467)
-- Dependencies: 214
-- Data for Name: instituciones; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.instituciones VALUES (4, 'Iessas', '2018-07-13', 'iess@iess.com.ec', 'www.iess.com', true, true, '', NULL, NULL, NULL, NULL, '\x89504e470d0a1a0a0000000d49484452000000e0000000e008060000001a2d6ae10000000473424954080808087c0864880000005f7a5458745261772070726f66696c652074797065204150503100000899e34a4fcd4b2dca4c562828ca4fcbcc49e552000363132e134b134ba3440303030b0308303430303604924640b6395428d1000598989ba501a1b959b2992988cf05004fba15681b2dd88c0000200049444154789cecbdd9b224c7752db8f6768f2933cf50a72614c622088a12d492d9ed6ed37db82fb4fe07fd827e03a64fd35bbf75abdb8896ae48a208102c145013ce9431b8efdd0f3e84479e3c3560e0586e752a2333232323237cc55a7b0ce0cd7833de8c37e3cd7833de8c37e3cd7833de8c37e3cdf86b19f4c7de8137e395c6773d4ffa83eec59bf1838f3700fcd31af97c7cf2c92779f9d34f3fcdcb1f7ffcf10bcfd9a79f7eaac5ba79f95ffff55f4b30be01e69fc87803c03ffe2060065c02dbc71f7f4c0f1f3e2400b877ef1e3d7dfa9400e0f4f4f485e7ecf0f05001e0e4e44401e0e1c3877aefde3d8ddbd604ca02906fc0f8471c6f00f8c7195740970097c0767a7a4a27272774717141878787747979499bcd860060bbddee3d6f5dd729009c9f9feb6ab5d2d3d3535dafd7faf4e9533d3c3cd49393134d804c4cf9f1c71feb1b30fef186f963efc05fd1a0f4f7c9279fd09d3b77f8f6eddbfcf0e143fef0c30ff9e9d3a77c7979c9dbedd6acd76b73707060c77134de7be39cb36ddbdabeefad31c67aef2d802b7fcc6cb6dbad699ac66cb75bd3b6adb9bcbc34b76edde26118f8f4f494d6eb353f7cf8907ff6b39f91738e0e0e0ee8ce9d3bf4cffffccff8c52f7e41fff66fff96f6f5cdf8038c3700fcf14706dd2f7ef18b0c3ce71c4fd36412e888c89e9c9c98beefad73ce3ae72c1155755d57226255b5aaebba9aa6a9aaaaaa32c65855ad88a822a2aaaa2a3b4d934d8f755d5b00c618c3cc6c8c316cad65ef3d5b6b09005b6be9e2e282de7fff7d7af8f0e1028c0510df80f1471c6f0eee8f335e28311f3c78c027272714c1c1cf9f3f67630caf562b737171c1cccc4dd31822e2611898889888d85a4bde7bf2de535a0600638c0280734e8d31aaaa921ee3b24fcb2222d65a2f7178efc53927ebf55a9d73126d47792351ff30e30d007fd89181b7cfaeb3d6f2c5c505596bd93997d889bdf7a66d5b4344ecbd372262aaaa32e3387255558688789a2606c0c6182222060011210060660500efbdc665498063669f1ea769f24dd3c8388ede18e3af03e3300c727878a8c33048b217df386f7e9cf10680df7f5ccb764dd3f0e9e929354dc3d65a4e8073ce9904bca6698cf7de30b315910cbeb4acaac604d43100f6de33339388103313008848028310510660021f1179efbdb7d6ba699a3c337b66f6c6189fc068adf5979797de7b2fc7c7c719904f9f3ed5fbf7ef4be9bc7903c61f6ebc01e0771b8b78dd756c574accf57a6d12f84ac0958f446455d510d16259550d008e0cc90038812f0d115166565515440012912722afaa2e82d1898863665f3ea665638ceffb3eb3a3f75eacb57eb55a6962c7e4497df6ec99006f24eaf71d6f00f87a63213181395eb78fed4aa66bdbf60af0e272e5bdb70974446401544464bdf796998d8858664e1235d9848b73a7aa4a44198044e445c433b35355a7aace189397cbe709845555b9699a7c5dd76e18862b32f58d44fde1c71b00be7cbc964365188605d3354d63c671b4555599699a6c025ef4605a55ad0054e979b91c9f5b55b5446410bcd60c80559554359fbf243de39fdf052000076022a2293ecfcb443489485a6fda65c8245145442e2e2e7c29514b562cc1f80688af36de0070ff786d8999bc980978755d9b711c33e0625821b39baa56cc5c270012515e4604213357aa6a55b51ac7b153a20d0847a7e7a7b7abaada585b75cc6455649c26bf55f1cfd6ebcd5315ffdcb23db7d60e0960002644d0019844243f67e6d17b3f19639c736e32c638669e76656a2951dfb0e20f33de0070395e4b62964c972466292f4b8969adad54b52a01971e45a44ecf45a466e60a40354ed366dbf7b7d607abbb47374fde596fba1bcd6abd69bb665d37956d6c4dca2012e8344d32f4fdd0f7c3b6bfbc3c3d7b7ef6e8e9e3a75fb9d17dd5b5ed53634c0f604400dd282213338f0026551d5535bd978159b26392a8098849a25a6bfd776045e00d1801bc0120f00348cc5ddbae9497a5c44c8c17d92e81ae490024a2da7b5f8fcedd72e2debf73efeecf3ef8c9fd5baba3f56ab5ded4755db1ad2a185b8108200e717282421550f5f0ce43dc846118dce5c5e570fafcdb8b5ffedf9f3ed89e5ffeb6b1d56f9ba63953d531026c24a231019099f702d17b3f2100d501984a208a882b1d37d65aff86155f7dfcb502f00791980978c9b62b6db817315d029eaad6009a699ad620bac1957df7e8e6c9c777eedd7deb9d0fdee9d6071b02086086b116440c6603e200bbec872100aa5055000a150f15818a07a080089e3e7e2a5f7cf6c5e9a3df3ffcd5e559ff2b3f8e5f5755f58c8812330e098c25104b408ac85882701cc7699fe326c9d3a669b24c7d13ced83ffeda00f8c240f9ab7831772566025f029c31a652d52425eb12780970006aef7deb448e2637be7df79d7b1f1dddbcf9ceedb76e9d9cdcbc55d56d0332066c2c980dd81830338809c40c44f051fe450054e38342550051a87a8808d43b400328cf4fcff4f1d78fb78fbf79facdc32f7effabd327cfbf58ad565f55c65c14527448204ca04c0064e631b162b219adb5d9935ad7b5db175bb4d6fadd8c9b378e9bbf0e00be52a0fcf0f09092bc7c99c474ce596b6de5bdb7097028ecb704b41dfbae498f67676737d498bf7ff7febbf7dffdc97b378f4f6eaedbae65632b98aa0ac04ba0331c998f018ac0cbe02b4f9f867f8905550115a84800a178a8f710ef20e2e0a709a7cfbe1d1f3f7a7cf6c5832fbffae6abaf7f591bf379d334970086127ce9790225338f223212d1444493736e42214dcbd8a2f7debd09675c3ffe5201f89d733177bd9865f8609fc434c65422926dbb5d794944f5e8dcda3977dcaebaf7ea55f7b3f7eebffffe7bf7df5b355d0b3083d982ad813116642c38321d53603d108129305f30feb064bfa041e7a9aa0a0d680c8c2812585105221eea05220ee21d4815e21cbe79f458be78f0c5d3875f3efcd57439fc97aa3eae8c398b12754094a888604c40c48e4c4d802c59f155c2197fad12f52f0d807bbd9800f8557231f705ca5f2431116dbb5da70a801a40b3dd6e3764ed07eb83cdfb376edf78e7edf7dfbb7b7cf3465dd50d8c8d2c97d9ce800d839803c331834000d36cef11019a9c2fe58fa600380048c003822c55405566468c12554520de4325b0a28a83771edb8b4b7cf9f9ef9e3d79f4f8d1a3df7ffddbf172fb60bd5e3f26a23eb1210a464ccb2880688cc9b6620af497ec785dc6cd5f6352f85f0200bf772ee6be4079e9c54ccc574acc7df232b1de388e47fd347d74f79dbb1fbdffb39fbc7de3c6cdb65baf6c55d7016c25f838812e018fe232007024bd0042a59d134600294149018d2c988726b31050c9f25491ecc304441f65aa0b80f41eea1dfabed7f3d3b3f18b075f3efddd679f7f7ef6f4f97f1e1e1efe1e8109af306292a7bb76220a667cd58c9ba669fce9e9a90ec3207fe9acf8e70ac01f251733860aeccb2466292f55b516916e9ca693aa6bee82f9c3fb1f7df437efdd7f77d36d3a185381d804c6b3166c184c066482c444065cf84973688132f8ca9f7d45751240c5e3528da6ff35acb36b274a046496a9b33c4df6a27a8f7198f0e49b27fe97fffecbcfcfbf3dfd359cff02c45f57c65ca060445ce3bcc19e70c68b24eabea4f0bfd4d8e29f1300af800e584acc57cdc5dc015fcec5c40ee092173302f28ac41cc7713538776f737cf8c1adbbb73e3cb975f3e69d7b6f75ed6a456c186c2c8cadc0c68092c48c60db75aa04cf26c26bd8a1baa5c1b77859f73c2e872e5e54d518b54852b5b01555b23c559100427110e7a3e3c6e1d99367eeebafbe7efee8cb879f3ffbe6c90378fd5dd334cf921cc58e03675f7c31d98965c6cd2e10ff5a628b7f0e007c6d89799d17f365b998d863d395b1bbc47c7ddf1f3c3f3b7bef839f7ef8bfbcfb93f7ef1e9d1cad0f8f0eabaa6ea2c4aca2c48cb61d07a6cb4e15cc00046696db65bbf2ec9420bbc27cc57baa71b3a52acd6fa070d420830f4074d204661489aca8121971b615453cc4399c7d7beacf9e9ff78fbe7af8ecd3ffebd34f2be6cf369bcd1322ea63a6cd820d010c29fb267950f19a1937af1a5bfc7362c53f5500beb61773576296b998a5c444ccc54c01f21278fbc0165f6fc6693a626b6eb7ebf5dfddb875f2fefd9f7f74e3e8e8908929305d55c344d0119bd9b62b2526ed4acb1964f9f5ebc0b5233317afbd681decccc0f2c508ca921583f346323baacc12557280dfc13b170129e82fb6f8ed67bfbdf8facbafbf7cfee4e9a72afe21133db56cb7a59d88a5f32665e24c6548434432184b5bf155638b888e9b3f1756fc5303e0152fe68d1b37f8552bcaaff36222820e3b9929d8015e192827a2da39b73a3d3fbf75e3cead0f6fdc3c79ffe8e6c99d7b6fbfb5d91c1e1031838c2dbc992666a91088d2e37e89197e69b2e776680e28a86ef996ead5d5af95a0bb6fec8e2ba88c4b9a5831ca53994119ecc4c08cc173ea03234e0e50417fd9e3d1c347dbc78f1e3f7df6f4e9e78f7ef7fb5f6fbaf5236bed19823c7d61c64d4a0ac79eb4b77db1c5b29affcfd571f3a700c01fdd8b8997484c44a64b36de344dddb3d3d3fb4727271ffdf46f7ffaceadb7ee1cad379baaed5ab0a9b25d17fe62a0dc98c8720c4eb1babdac07e8c299424b79f822547d9f75f61d75ddfd7a9d419e583107f5675b112a90d256140fc9f6a2c3348cb838bbf04fbe797af69bfffacd575ffdee77bfdeb4ab5fb56d7b86228e883d4e9b04c8e4494df2f465b1c53f57c7cd1f13802f65bbd7f16222b2dcae17f365b998118ced304d076ccc1ddb343f393c39fef0673fff9b5b376edf30e0002e53b05d9298c1b6db89db959ecb127c2522caa3feaae0baee4cbd2ad3bdf2f693d38696ded3f47a92a6655c516630e6708684a47011c1f9e905fef397fff1f4e9d74f1eb861f8d5b81d1e364d739a628bfb02fcbb8c98242a02385fe8b8f973cab8f94303f03bb1ddab7831536a5869dbbd2817131180dbedf61eb7cddbb7df7aebfd1bb74fdebd7df7cefaf0f890abaa8af998a543858bb81d65c60b44c733d08a24e9cc7585ad96c78b8095d70946dd3e229bb19302f1c55a79dbd751605a67cffbe57ecd9418c31788582c9d36891d0590c48ec15993ec463f8e383f3bd727df7cb3fdeacb475f3efefaf1e7fde9c5efbab67dc8cc3d627c711f20513062b2177f68c70df0c701e31f0280f93b5e2766f75dbc98bca7dc675f2ea6f7befdf6fcfcedaa6d3efae8e39fffe4cedd3b079ba383a6eb3a3255956d3b9393a0cd1587ca0cc0326c105ed3a5c8fc81196acfe7ff50dbcf714514ded3c2665405a4ccb649417e896c1840d96fb77a7e7a3e7ef3f53717fff1effff1e0f2f4f4578707075f586b2ff780efdac4f0eb1c3709882f6b38f5a720517f4c00be16dbedc6ec98d9ee02aff4629680c3cbbd98f5308e474474c29579efe4eeddbf7bf7271fdcba7df7b6ad9b06c404b6154c91041da4668cdb7101b69c0c9d3253a22b65511a84cc7e0b662a5c9621ab6c0fb35d89aca7cdcec0d67d2ecff4de9575b038cbf33acbef2d336aaedf37ec0037c511e3db3b604c31c5e045f539295cfc14638b82691cf1f8abc7fef3079f3f79f4e5ef3f55a75fb0ea6363cc29112db26d88684c2c9900b82b534b3072d15ae34f55a2fe18005cd8762f62bb97f54f899da077c1f7ca5e4cef7d3b8ce30998efddb87beba31bb76ede7debde5bc74727c7c65655cc4c098f26818e3826439b68cf15008c3f6f013624d9b97314ae65991d99581eb5bc2ecde0591cd557d9fe9ef5ae6cff153fbbef7b76f7bb6045528524bb715195318351258251635cd1059bd14f0ecf9f7d2b5f3ffceaf993af1f3f7af4bbaf7e05912fbbb67dba1b5bc49e603f0a997a5da95402627c2edfb19aff0705e20fd99a9e80e57d0f9c737c7070c04f9e3c31a9fd3a003b0c4365adadb6dbad0550d7759d98ab61e6d639d75655d579ef5b6b6d07a053d515801511ad8868adaa6b225a8bc81ac006c086883644b451d583b3b3f3b71de39fdeffe9fd7ffae8effff6e39ffcfca3b7eebdfbf67a7378c855dba2aa1bd8ba0e7f314dcc985095c06c82fd972b12767335974c388386e6c7459caf5c07916592c306f3c4ceeb166c993713991674757bf1f99575ca3383623bc5637ebfd8d6623b7bf67f96dfc8bf75d701351fa3986e07ce173744491f92144cce8fed561d9ddc3ee96ebd75fbd68dbbb7deabdbf6fef3e7a727a7a7a763dd343073a957ba18e71e3a08f7c748e1260bc0c6f6fd96880c1159113126b40c37d65aa3aaecbd67e71c8fe3c800c87bcfa7a7a7340c031d1f1f63f7fe193ff4bd337e880d5dcb780f1e3ce024339d7366d7b6dbc77600aad43f4544ea32350c3be182c2ce6bbcf7eb49e44655d73f6937ebfbefffecc377de79f7ed3a2540e75ccc4581eb5ce89a4b7ed224245a4cea14b7bb4264b142613e923b94735db266fa40b2adf276763f83f9f57de31ac93abf1ff765b1dd9ddd5838637684e76b326c96c0d9831a57d2d836038ae43d05961ed44538639ae0bd43df8f78f4e5c3f1d7fff9ebdf5d9e9eff469d7f60989f596bcf230be6203feda4bf612707955e21293cb1620af25f274f7f2846fcbe00a4d2c64b6184eb8017efec634a474a59dcba2b2ff7d974d8f1625ef6fda1a9aa0f0e6f1cbf77707cf4f6db1fbc737272f3a6b5d682ac85a9aa184230458d1dc76542e8ef408bab7a00582139778f56668b17e9337ac9f20bd65f4cf6eb3463711250da9afbf6eb9acf67dc979661b9dd1d4001c53a7ae5975d193a2fcc3e9cb975c69c839a326e5228a3487d7321eb669a1c9e7cf3787af8c5c3c7cf9e3cf9fdb3af1fffb636d5e755559d253b117b4219b4d3808ae8fa1e37c979e3bd77a53c7dfefcb9df0df023b47ffcde36e27705e015d6434c881e86c1ec031e33db941a56c6e9f6b1dd6e80bc7c2cf2316f0f223f79fb83f73e7ceffebbb7364747ede6e0806daa28b701749ce54eaa2a8f3228c9a6a2de2e134109baf4734b26ba2e1113c0776340fc896c7f0f5077edd22befc7c7bd0905bb8cbb0f88e9758d1ed468276ae141f531edcd7b8cdb1e17e717fedb67a7fde79ffdf6d1e7bffeecbf1a637fbbd96c9e604fb6cd2e2bee4b0ac78e03a7ae6b27b1d954d7756e9711efdfbf2f2852debe0f08bf0b0017e02b59eff6eddb661806e39c3336a4b09bb66dab711cf3edb544e48ab464e6bde182d2a9e29c5b39915bb669eed9bafaf0bd0fefbfffeefdf7bba64dc5ad317450c84ce290f49c733153a94faab7db9198e9c769b2678a8933f34139c7ca09364b485dccc75df947719d989a96b6bd679d79bbfbd659ec4801b4f4c5b4d8ef6b59781f30d33a3bdf4b71bf5fb46f44baf3b50530d3cf2d8f664264d9544a91d95055e7961a31f5cdbb097e9a4201f1e516bff9afcfcebff9ead167fdf9c5af75745f31f353634c1f9971afd3a64c0a4f9e54661e99794acda652c03f31a273ce79efa5699a0cc63db2f4b540f8ba00cce003c029a460ade504bcaaaaacb536f7c454d5ca185327e01151cdcc8b767c89ed50802f2d5f5e5e1e4d226fddbc73fbfdc39bc7f76fddb973e3e69d5b55b75a1125dbaeac2a2ffba8c40c95203131b31e2803301d8259f1ed1c9205fbe0d5dffb21c6deedef7b719f147ed5ed5ff7997d92f69576f0e5db2f983257f2672026606a66c66c236a612b8a872fbca867a767faf8eb27fdd3478f9f3cf9ea9bdf9c3e7bf679db745f5755a859a46b92c24b3016ef4fd6da711cc7cc94de7bd7759d73ceb9a6697c294bf13dd8f075a6cf5ef00dc3608e8f8fcd300c46442a638c8d8f75b4f16a225a000b3b404b7faa5aab6a83908f796370f2c1cdb7eefcfcbd9fdebf75747cb8da1c6c8c6d1a58938a5b63816b597d90c30640cecb0466f603cd6455d6ed14922c97f4cc33261faa05bbbdf63a57d757d59d32a47ddb2ce4e28bd679e995e2ba755f04b6976d7f0fab1676e32beddbac4be7f8a520da9d0a49a552825cb32885d32665dd38e730f603befdf6f9f4fcc9b7179ffdcf5f3ffcfacb87ff71b8d9fcae699a731412157b40095c2d2e66e6c97b9f9872da6eb7530944efbd1c1e1efa74c39ad705e16b01b004df300ca6691a3e3e3e366767675655abaeeb2a11c9a003d0586b33c044a445005f8b00b836bde79cebbcf747a6aeefd4ebd5dfdf7aebcebbefddff60737c724c602e72316da8268f95074c57335432e38167906166c1ef3467ff42462944affb91afb3ce3e61beff7b77dd397bb6bf68abb1d3d726d98c59a6a698a200ea215e626cd1c34f23c439a82a9e7cfdcc3ff8cd83674f1e3efaad9fa6ff70937bda5455ce43c50cc69e880611c94da8e2eb83f77eb4d68edefbb1aaaab1effb0cc2699a5c29497740f88301904a9b2f315fd334d65a6bb7dbadadaaaa36c624db2d038e995b556d23e83a552d9f3722b272dedfa2aafae99db7dffee0c6dddb77df7afbadae5d7539f93967a89899e5988b360e54f44e29e26f65acabccc9dc3df5d73d7fe9f8a348d01f7b7c57cdfd2a3bfbaaebcc8b65a60d30db89736546aae49fa5aaa432a958b3388d13cecfcef5abdffdfeecd9e3a75f3dfaf2ab07328c0fbaae7b8a1df01151cfccbd88e4471480043024207aef1d334f4dd3f8699adc7701e1ab9cdebde03b3e3e36d33455097c884c668c6923d37544d489484b441d80fca7aa2d33b7171717b7c4da8f3ffcf9cf7ffacefdf78ed7070775d3b6309589653f73e3a2853dc7344b4b2400c6fccb227c10debbe61cff05b1da77194b967bfd75f6b9765e655bafbecd9219c3fb51b44747ce2e18518031349e4a8c28cec1fb5026d5f7bd9e7d7b3e7efeabcf9e7cfe9bcf7e6315ffd1c66c1b55ede3e396423bc6adaaf6aaba2d41890288d193ea0e0e0edc7701a17d85630720f75ea1d3d353eeba2ec9ce0c3e6b6debbd6f45a453d515117544b462e615e64c964e041b2ffa961afe9b9fffd37fff870f7efa93aee9ba1c2c37317c4064c02606c78b56ec294c005c05d7e20446b32e9fb9b882c6c7bc6e6102ee63c7ecc0db6742ed31d1ae607b8f3a5bac93bd88fb3e5c6ee7550ccb57df217dad75766dd059465eddce7e399a7ea6bee080eade7d4bca45e3a653420481696ec64809a8459f1bd6d0784a2a0f2b025b3bd4dd8ad60787cdedb7eebefd77ffed1fdffef2b79fff6fbff9fffeeb97eadcff549147953167082a6e8b106fae63e8cc1291119154044e00c85a4b007076760622d2a669707272a2f7eedda34f3ffd74e7a45c1d2fe38105fb0dc360d6ebb5adaaca8a4865adad9d736d029faaae98792522eb08bc35805504e46a9afcb136f53fbcf7e1477f7bef83fb370f6e1cb1b1156c5da1aaaa2c3393bce4c47414dcdb4b49594c96122dd8597ee56bf49bf1e73016d8cd2f20cf012d628d398c11ed4589bd50bd38b8312484bb69c4f9b767f2f0775f3e7ff8d96f7f73f6e4d9ffbb5e775f4716bc54d54b0097447429229700d2eb5b634cef9cebabaa1a9d7323334fc9263c3c3cf488f76b7c110bbe2c1794eedcb9c3497a1e1e1edaed766bbbaeab9c73b58834d6daa660bd75cac714910d111d88c801331f9c9d5dbcbdbe73ef7f7cfcbffff77fb8fdde078776b521b20dc83680a901b65036089929060a46ca52093577f33d11920c2594b999a5cd47b87295cd27687e7b9701cb8b7cbe7eefe2f805fe839731e0ebac7365bcdccff1faebbf749deffbc3cad55f93c17736144e7b3231281742a7e71abddc0a8280a2efa67c4ef09a9e33141c5e2786a91b3abc71a3bb71e7ce1da772eff1575f8935664be14ec446c3adc21900453f033133bcf76aad85734e01685dd7daf7bd765da7bffffdefb1dd6ef58b2fbe4091437a65bc0880f4c9279fd0eddbb7f9e0e080a76932d65a638cb17ddf57081507adaa76c6982e254803380070c0cc07aaba0170703efa9fdffec9dfff1f1ffcfdfffa3e9a43d38bc5e80d26b598d4601482f3c024042704a780174094201a1f311f504571e0cb134349aa26fcc5850cc622813afe9f3e5390ebfcdefc31ecacb07cbe5cdc398adf6d9dab67e3ba0f7f8ff55fbacef7fd61e5eab47c7cc90e51b1edd2b186e2dc870b6a58534071be2844c27c11059ca43fc5e415930726070c1e183d303a609814a32328577c74fbcec1e6e4f6fda78f9f1c4cc3f65b6b8c3233036022a29db0910250665611d1699ad41803115100da340dde7fff7d05a0df098077eedc61e71c3f79f2c46c361b338ea3edfbbeeaba2e793b3b6b6da7aa6b555d33f3018083549100e0e0dbf3ed0737eeffe32f0edffee8c6d948f8b6179cf782cb49b11d15c324e847890746e1bcc20be0253c6a0660f8d1aa344b0c0210af6ac90193430d5806df895e74e52d4ffe9bf1c71ae9b2982e9e49d5cc95274111696238a5181ea498531ae78a48009d0ba0735e3039c1380986c9a31f3d2e078f6def71d94f38df86bf8bf81ca636abe39bb74f9f3d5f4d97e767315734cca600c2606d064fac10911291d675ade3388aaaaa3146cfcfcf35b1606ab1bf3bae05e0279f7cc2df7cf30d7df8e187bcdd6e0d005b55556ad5dec4f042875022b456d58daa1e44091ac17771af3af9e9ff3037eedf7a7ceef0fcc2e172506c27453f0ab6a3603b08fa51318ce1e08c4ee13d2210155e083eb652cfd222cb0b6467c0acb013eba593169675a74ca63ce557650fe17a405e77b9dff7d917adfb47a1b4d7fcde1ff7a2542a8e52892c2ea4c5b9d2d9219ae78140a34a5238419e3bce2bc608ba7e94083a878b7ec2d9e5848b8b21806eebb01d02287be733218c8ea85a1d9c9c9f9eb63a5c3eab2aeb294e9c40885000097c628c115595aaaab4ae6be9fb5eadb599057ff18b5fec95a1d7794109084d921e3c78c0ebf53a55ace7fe9adefb1a40066274baac221037fd389d0cf6f67febcd9dbba7cf46d45d075bd7205b872c962ad4de29112601a6ad800681311e4d35a1a90d9adaa26b0ceacaa0b68cba625496602da33204c30033c1308109f12f5e3949c1890591444df4b36519317bd1c2494da162e4ff974e9e79d6cc8b699de8dd5385ee962e2d0eabe609144c22dd21e6e29365ca1695ef945b9fd75dda8ff377cd3ff0ca0e5dfdde45a5c7ee45bbdc03bdf2ee95fd2fd6581e0f5a360d4ebfa9f0726b711680d9a7e6a3a75394826345354a4d8517817860f23ea829e7c3457d128ca3433f3a4c9340e2c12718988a61aa2a872e10dbf3fb69c20098a3777efe37df7cf6ff4cb5c8ff198127aa2a003c11396676defb54f0eba669f2aaea8d319e88f8f4f4548661a067cf9eed39212f0843a42a87d3d35372ce715dd74644ac732ee5743608fd553a662e9d302b00eb6f2ffdbb97f5c9bb9536dc352dea2614c1d66d0b5bc77e2b863358d2095700c324189dc365ef71591bb4b545db30da3a80b1b28ada122ac33046610d0710326058c1d16e2d7af442000020004944415450025813f3a583be33742eb3991ddc69ca14b3273feaeebbf1bd787c89f2b4b99e3f66d0cd993af37bf9b1f8de799bd7787417e0bb7e9dc5f677f629acb3efb3691fe7cf5eef57debffdb9c9e19e7dce89f025e096e053447c44c049f46a2653c579817302e70583134c93609c3cb6c3846912781128086c2d6cf4ae27471ea7df26f32ddcfc6443552f310ffd873f3ffdfcd32f6e1cadbf00e0e39f03e04424756a4b817967ad75cc6ccececea4691ab977ef9e3c7bf66cefd1da2b413ff9e413be7dfb363f7dfa94abaa32aa6a633275c5cc8d31a625a204ba8daaa66af4e478d97cf95cffb1baf1d35b75db51bb5aa3ed3668bb0d569b4374dd064ddba1ae6ad8aa823115d8304c6e7e143c5caa84c90986315cc56659aa4817acd87224fe693ed97303af251c349ef05cfb5754412ce44f9e244906d18b99a8f0f2a55e317373a6ab141a56dfd3c0a91c0bc6bd8655f77cf4da755e53995e5d7d5ee97a9fcae2aab1f3b8acb99c9d2b146c799a8fa52238e014e11cfb083c2f0ae701ef15930f1233c84c8f61f4e807878bcb0917db0997bdc3e804a090ca58598baa0a7f7555a1b2164d5da16d1b347585ae69d036359aaa826546cab6699a9579fad59764591e1b6689769f5755cfcc5e55bd861bce7822f2d334051f90aa1c1c1cc8f9f9b9163274710ddac780040031df93eedebdcbce394344c65a6b4524df0d16319f93995bcc992eabb3f3f313b43fbd67ab86acad50d72bac57c7581f1e63bd3944dd56309601287cbc51e4e446783fc2fb09dec70e5ab926cc471d3fc25e129aca60d5593495415333da8a612dc31a426d19c604596a4d08d61a0694018a2540e19b23b6d24201554d6535e149716466813a5f9f23a092eca4ebe7fb0286c5557fb976b15699edb3f77bb327aadcf2f5eb5cb985d98bd7b9ca72f3f7a82e1f97eb60de1685e35e8231cbd0f81b13c3e5c496b82cc9b1220a1fc1e7e345d839c1e80493f398bc60e83d7ae7308e1ece0513838d415d338c611863e223c3b0455d5b345585bab2686a8bca1a58264004e330e2f2e212cf9800ef20d3885beffdeced679ffdfbbdea683300c8b7eb16912166cdd4defb8188aaaaaaa6d4648c99738b8b7bf7ee5d39a27b2568d15a829f3f7fce4dd3705555661806cbc5fdf198b9f1de3728d2cc88a87d76dadfaeefdc6ca00a43164dd5a16b0fb05e1d61b55aa3e918d6021cbf5d244886699a308e23c6690be7c650f7e54788985c82e244e07a8761f2a82b46531bacda00c6da32a64a61a33cad2c60986118d94e8c2d4a021023d082daa32c072921094556bf023958584edcb84c8ba524d7aeda41cbb5af3223166b9740dafddeddcf5e67afedfb9e6b0073655f76807c655cf7bd71898a055abe17b63e2b95202fa35773476afa28359d574c51664e2e3aedc660e70da30ffd498960abb9a5a4b5a1d99635064d5da1a96bd47585cdaa43d755583506ebd6c212c120d898dbadc3e9b735741c305e5e62b86034ab5563bbcd6d11f9da1833c6105c8f659b94ca39670098baaecd344d193f08b2f5cad80bc08f3ffe989e3e7d4a878787747171c1b18d446e254144552a1d8aecd7c404eb4655db9156376b53057dcd06d654a8ab16b56dc14a5017188915301540cce8560cef2d9c6bb0ddae300c03a6b1c7386ee1fc08ef1dc83b506af42a82cbdea31f82ec480e9b6427d695a0f20c6b80ca0096196c00a3811599084204935410c2353947a0a874c814d32ccfd1128c0b6ec857f8dd09bf5c671754cbedec07657afdeab7be182457767ecfe3ee77ec02fe65db8fc7ab60b8f06f276c94597366ba398480d9bed3188ef28a49043eb2dd18edbb61f2d8f60e93178806cfa4ad421655c976417256689b1aebd50aebaec16655e1c6ca625d111a2654c5cff700faaa86d5352ebfed7056d7b8308cbaaa8d693727ae7fba36c66c53250fcded522a11c94da38761606b2d775d475555d17abd4e1de01707f20a00538f97d3d353ba7bf72e3333d7756d9c73292320b594a811dbffa1a8e9ebfb7eadf5cda3e45124102c573064a05e308d2971960001c403c6863f6b80aa63741dc3b90ac3b0c6763b621c078c118cde4ff1ae3c2ed7856d47413f4c38bf9cd036c169d3d4065d6350590e0e1babb00630866199604c70d6c815460c8788e3219abda825eee2842c7b72eabcde55602de5e615bb3095482decc16bd6c9eba6b10f4811058bf6867b40b79b06b4d867bc7c1d2abf7f16c933f05253fbf08ec6fd888db4a37da7596e7a498ca73194e0433861f473486170189c87f70a6282e1c06e596632a1b255b0f3ea0aeb558bf5aac3c1aac18d7585c386b0e6d0468d1173c58a3f00a80db059d55835359acac032c3da0a55db6d864b3d6c80b334f72311553157d4aaaa898dc63866d2f0e5e5250dc3b0ef6aba9f011f3e7c4827272794e8938838b582f7dea7b66fa931eea271d238b91555074db2431831482a0aef7c9c67165083d8c93cff1903b009d2d45ac05a42d33498c61ac3d061db7718c701d3d463723dc47b702a3d110f2f828bc88a75c5e81b8bb6b1682a4653332ac3b0464328237950a3f794690e65049f0045464c934a1102c1126c9a38f77431517727f0756cb3f378955eaf5fa7b4495ff499bc8f2f60c0bdebecc8ce2bb6e1ee3ce2595d16405542e1c95cda7933dbc5308224e78a64fb2e49cde06071c111e7c2fb4c0c5b45d031830dc39a60c7555585ae6db0ea5aacd70d8ed72d8e5a83838ab03261e2a65f929335e3f30c440dde49268221ce73c2d8a6d9f6d3c1ba6dca7688b91522857b93181131097ce338d266b3a1478f1ea5de498b710580656bc1aeeb08000fc3c05555996858e6be8bf1cb3305ab6a3d7aeac85a1b0f7bac5c0eb55ae21c0a910f88810a413ca01e101398907d04a2012a0b5496d0b415ba9545df771806876d7f0137f5d1561c432bbb785f022f1edb41308c23eaad43d3443bb19ee389d628ac2154962308673012012655606096a661fa71b61b935326316362042d276b9e98e544dfc32419172f5b678701f367f631e0bc4fb8769f76d7c19eed4456d3b21b78dad6bcacc5fe67c0a54defda761a122ebc0a7ccc56714e30c5c7610adeef61741827095e630e9232038f0dac0d7f4d53a36b1a745d8ba3830e87ab0ac72de3d0121a9addfd25d84ad02966afba7840a650710f0d3958448abaaa8d28360970b1b198a570bf492322396f9488d839476ddbd276bbddcb7ec05500120014f61f2506242236c618296e7619b36216570381edc086677650889b308d03aca90055a80f761cc442bc81b10c3194d98f6d6043939e730063d712dacec23b8b7e68d0f70ee33060182e314d4300a39f203edcef5cc4a39fc2dfc5760eeeaf5270bf62d45642603f7a4d8d49f1c40846280c1348914f0425e74d2938b38a2ca4e9227523fd1726ec22ac70cd3a25dbe42a9edd2a8f3d5ed025bb010b405d61b5dd7570753b8bc5d43d8e32b016fb192f40f9311e021f992e494c2f0af18ad10b5cf4648e6388dd25a78a97e8cdb4a126d41a86b126c84ecba8eb1a6d53a36b3b1c1e743858d5386a0d4e1a424b33dbedb25cba3024af884f92580009b73ac4344db9df8c4a00a1a92a2653af922906c0a8aa41c0774ad8e6f468ada5711c09004e4e4e5e5d8202c0e5e525c5b6f19424a8aa1a664e28379899305d11ac282a0251bea34fec62956ece41009419700e4e01168188898179023982b180d8c088e4222b46463426b0d37a4d68bb0a6eb4d86e1b0cc38461ec31f6db0c442fe12692aa3ec89bad473f08fa2104f59b3a396d18b5655415c1fa10c6b01c2f00146c16132ff629bb86344ec5328e08cc9ebff8ea22f45c8065976bf293bdb658f96c5752bee871dfd865e33defe7d5680667b8fa000817a09284b3b444d8ae20c84fd114af9d63785e8227d37bc1e414a30bd929830bf69d73022f01e0d69a282fc3a33106756533f00ed62bac572d0e57154e3ac6c6105a5e06b73df6305db9cf1a182f99432a0af1826118310c03bc0fbe06a8828c2153d90a33e04cac966004939201b088908890aad2344d242249145e19575efdf8e38fe9c1830774f7ee5df2ded3388e19d100b80461897e84ab819d4667a909f5eac94922e2e1dc007113a8629008bca6a6ac26d46d79c92d275418e4091241276e664549ac1881681a425d5770be42df37d85eae300c3da629386dc44f1071f014638a1abda7a34765095db413dbdaa0768cda46696a8207d59a509728d9562c9c36990135e79d6a0130a53459b598db894ee6c99ba1f002062c57a197acb3dcf29e7516df8379df765e52cc4e2968ea763d17cc96c04b9b942c31936d2f397e37798dc08bc9d11174c3e43039816a38d6b64a0e9560dfcd01f4183e5875385837b8b9ae705433d6066868de752dfef6494dd59cf412f6df27300ac44b68ee340e98c611de8537158021266b6b0bb88485ac0ee37302900047d33451d334b4dd6e0104dfcaee59ba9601a36ea5aaaa68180662664a6519defbb24e2ab1a151555690e1589b9578ddbb11d338c037238cb1d0d89753e25952c31016b0278019e2c34d30bd2118c3200b9809f0b660410b909999b1ae80a63658af0dc6a1453f38f4db01e3d0639c7ab8699803fcb1c764b03326d8ad435331ba2604659b185fac0c604dcc3fcdd234c41553282314ec2f830a4cc55c5ed84c717a44993afb3cf7493eda8bad2043afb3e34a2c5dcfa4e912b180e8de0c8259a2a63b27497cbd049e487a0c1e4d5fc84cef25c7efca8a84610a41f324338d99d3130d33ac35a86cc85869bb06ab558783758b1beb06470de3b0023a9a932a12c880425a16ef95610e24f009824d9aee6f280a118fa1efb1bdd8621c47f8788f4300219e585586c893aaa6ca0822224ecfd3f0dee76af9aeebe8e2e2e2d525e8c9c9095d5c5c1033a7ed254f6810223b5a3751b0aab290469516265ceedfe81da66984ad5a10479b86145e18a40a660d2c230c1501b38f017806f960231a1fec443141365069279a602b1a03741dd0b416abcea21f5a0cfd846dbf851b7b4c6e8073436c6917d9d9fb90b83b0aeaca854c9bd6e678626d193682b032089936146d535db2629afcb948aa6c748b1994e9021594c21eb0ed3e2fd1823d8fc53aba4056b14ef19e2e3eb2049feec8d3242d4bd095e97f5e83749bcbc866a69b83e6d1be2b9c2ae014340fa033368414acb568db1a5dd362b56a7174d0e2a8ab70dc300e0d5017d79dd2a6db2b354bfb2e820e8a05e892c40414ce390cfd807e18304d139c7721793b26cd9331ac7e065f62bdf2518b03384d13555585ebc64b7bc278efa9aa2a72ce91318612d822eab3348da51aacc2456a63f8b5aa02ef26381758c852354f510d49b202050941584122500e52557dbc8f833190d8ae22c9528a7662f29a1a3383920cd03440dd18f8b5c1765ba3efd718861ee3b0c5143da839a62802af3297a68c2ec713dbc6441b91519b101e310c54519ea6aa0c8e6cc7b9870db2f3e66a5f9592c44a4bb164425daebcf8d0d575b283f485eba45ea4c587305f344af06569899941f2b2cc52d38b4004214bc905c6732e254507a74a3f38382f00084445d03c81ce98901e56d7e8ba1687eb0d369b1ac72de366c3e828d8e189f14ae0c99ee70bfb2ebe28f142918187b42cf17709fa6d8f6dbfc5380e70cec17b0f95394d114c20c9cc9780183fafa962094982ee81d4625c0160d4a92904919555041fc56f289739aec3aa73040d1c4eaaaac2fb09ce4f70d38469ec83e4600bc02355b7ab8ff68648b877838413256c827dc53e37e1a598e5c08ee01957bca73e396c2c401c02fc87878ccda6c638d6d86ed718fa10e01ffa8bd969e3a76c276e471f724f0d65ef69db18b415a3b606d622d88996609960130313655604c5f2a8f240a6099eab3376cb97e67b5484d3ba4365d983992032bfad7b4097735a0b2a4c9324870d6258652e7a9e375bc6ef52968a6af06a264fe6e403d339afa10ac105b60be53f1e0177046babd99b999c2a7585baaed135350e0e563858b5385cd5386908073674454a7bae58caca6b8127bb7ff1ce4cea23d1153d4655a114003984ae69d8f603867ec038ce39c985bcc923012e0151420f1ae26083e5913ca140487429efc0fb4a5dd1bcf7146f6e426565b088248d1a35b05228fbd162c2cd7748f57e0a313b63c155eaf3120f04713cf1a1a540c89cd75cbb47c25065280b4808eae7fb3f1843204f6017bca7890d4522387d04284556ac0ca65587716ab0bd6c83dc888e1bef42dadb22b8bf75e8078f6d1fd8b06b6cf09c568ada7394a6613f8c01acce817d252cedc4746c921363c78ccb71b6525a96677ebee717aee8cdcc6af38733f8caeda68f1581789957ceb694a4c98c18422883e6857d37ba90003dbac07401841ea29453c46c911e66adcddeccf56a85f5bac5c1bac1c9cae2a822ac4d005e1a2f92999af6b3909ad9a922f3dd9712e834677ccf378751514cd384a1efd1f73dc661c4144310e2f365291c56efa5d8b505eb7d97f142004ed344cc9c2568faa6a4778180c4f291412ae185e868d0e80d75101feef9e6fc08320686aab91e4e256db2f0b611c0892104a41ca42911d4840e57c4066a924c8d4c1a59d026e0f12c53297a4f9b1aa86b46d7b61886067dbfc6763bcbd369eaa334f5f07e96a7c3e471d93bb4b5894e9b14c6a0ec3df5966058e71a4584091e6cc60444c480fecc88419223784f1368d2739d9d26199ff1858cc9f4bcd0b979f348cc362740a78f94849a273466c065e07929d2c4421861723eda7641ba6799c9210dd01a13647a74aa54d662d5b5e8ba0e9b4d87e3831ac70de3c812564508611770c01e4f26965213a9858994808b04a0f3ca9ab70ca82aa67142df0f18c611c334629a464c9383571f4229f1084a6c004cc1c99f8197806c8c593c7f95f1ca7d41f3092b8c8444b5251503408ec2c7b35a762bf67e829bc2ada009a16464511d90aece4098943e4e4422400524d183ead37dfe7c5e666678175b1b1a9aa56819ba883146e6f0672d50ad09ebb5c5306c300c6bf4fd88bebfc438f670e308e787787bacd0fe7c1883f73404f7e7bcd3a632a86ce1b449c17dd630094d002351788e5c1e159013f4402121638d5c4e7d5bd88ac8c8d39c99b327813ccb492ab6b00c216031990bd02962f9cfecc9f4d1beeb23f0423582404441ccb0952d12a2836d5755166dd3a0eb1aacd71d8e0f3a1c7516473570c4cb80f9424e16cb65b6ca82ed0a6f66606b99edbcb4629c8f393335fe782fb270baf4c390d9cf7b07e7fccc9ae1a2a7ce7b01bf2e6aae1f0b007ef2c927f4f0e1430041b796d45ac8cf04bc920de741aaa4a4397e847050909930de01d51b084f8109d8ce977145c18ae1910abb4729d88da15e5283134762fc908207557cbc618b25b0e190702d0170e200ae668f697a3d3b6d6ac26ad5a0ef2bf4fd1a7d3f144e9b501ea5de4354e0c4c36f43ab837e9803fb29e52d25821b0e49e09520d8ac1c4a5f420238902af525fcf858d79b2438e52a82340b1294008e6c394bd704512d52d61268d3957c01bcc420281c2b2231313ab09cf7128b5f03e3a54c9510bbd3c07675f4649a90d954198bbaa9d0340dbab6c5d16187c3558da3d6e2b80256bc9c7cfb52c4ae0031c5edae006f2933677939ffd0f010012831d17b7298dc847e18314e53f67a7ae7430a65623c2405a180f857a7b75718afcd80e528c11797cbb0f3bcd3ba033eb1f0e2403e5c4ad42a982d72285bd3153c5cd1854a100676089ddf082404620d654a642012ec0ee1c096cc6162900fde536f0013e568b99cffa2d366b361745d8d71aab0bdecd0f72386618b71b80c85c36e5ae49fa6e0fed6520ceccf05c3950dd517cec42470de49028ff9a71cd991a27c6789b997d1ce4bc7a438ba987da8bb9da51360c3514c9f5a783213e3e99c22367b3303f0c614389f04dbc1619c669999627629686eadc955084dd3e060b3c6c1a6c5c1aac2ad96711003e6fb64e62ed3e5d734002f59291257d2e8bdd4cc6852385864fe91c5510af9a781f5a669827381e582e40ccfbd0b6129ef13fbc97c5183c27b2ff80129f07b017077b031b075ad5371cdcd364bbe22cdf77713efe00950a780095752a639bb9e22d8d263162a5a4e3e82faf83d1ced444f2023217ec8e18f733883219632d87cb5acc2481936e9b16b095d6be19c0d15199707c1501f7b8ce365f49e46833d57ee4fb0c6cdd234d628363109bc3214431904cb1a5af0738c2d22785189c28527c9d485a3261ff1f9f824764bafcf66080766d5127433e305d069ae46c841731f4308a3e44c152f0a66035355d1935994ffc410c2661d9c2a879b16272b83030b6c685985f03299b9909ab2b4ef10bb5d6b64b252522686cb17fef02e544bc60ba1851462989c0fe09b26b808bee0f98c366431bf090a15efff640048b9456258265554b6d609c8de825096920ce178c30c0d6544ca0cf185cc55855288f951a96c93574fe7e7817023189354f6b1f48942709f9843609f0912d950842162723c918582448d551839ddcdcc4e1be6b22aa3c63456d8f61db697ab58347c35d3269446390ca30be08b609c3bbc0536b4866025805d8a78e2a2c35baa53c46e701f80ceec961517e27c2c6c9e00be7d6c97bc99d1a9e22557998f539499008819756d8af29f1030afeb0aab2e04cc0fd62d8e37358e1b838d055605db01fbe5e5b5de4c3f2f878ca9621ec57496e4354f8f2840978cdcc466cecde0f322f0cec5c7988811d70ba993b18a2791469acf5e14ea25d41dfc306301c07ffdd77fd57ff9977f0100d475add334cd074f4413e04444638bb6050801c4fbb5e5673b773c967c5493240d1e1502c885b4748e773a2213ee0ba19c6d9cd9a889e04bc05399fb7ec600ab128155e129842d98a3075518ea25b0a261b037f04c017c2ec610633d62025f99695319a05a11dab6c23a96476db713fafe12d3b8857303dc3466203a15b818dcdf0e1c3abcc5e07eeaee1680c8b051feb289f1449ee389261e444a36f115cfb72e019899616e68545623a4a0b98f3d3407e74352f4182e1a2eaa386683ca706e9a1564a641533768db069b7587c3c3158e5706c735e38043a60acf7b951ff7da75e9b9141233d9798a45a68a464a2c8197bac3a6df9e8492170f1f19cf790f2f21db4912006388c1c7fb4524d065db0f7398623ec40a91a46f7f98f19d195083eb73f91a9186c4a4791fb37ed6e48851a87a883248393a517cf8042948195e05cc12d830dd2f029483d38b4c0ec4f0856876e5a76ff63ea60f114125ddda2cddfc25dcd833ddda9a5c4877a3d86fd4dbb90aa3acc6a0c2566c5b42db1a6c0e0cfabec1d01fa2ef7bf4fd25dc38e4cc1f1fdb6884ce5d23ce99d04669da54098cb1a9544c023731be69a27de869ceb04172dec4df19fe9f5de2617e526eed303b5562d16b0c9e4f53b0f1c6c963181c461faefec69890105d7833ab5873d7340d565d83e3c3150e37212ff3d8022d966c779d7d578614b4b0efe27d36c372ba48471b2f19aea55733833009f3f87a60721718cefbe054f132339f84cc165f80cefb982ea989fdcaec98f93749d2c03fe07829008d311a6f3e0160c9788915e36b292134e41d248305c8aedcf4270a70f82f802f7a29852948c72427d800aa3308d30d5972a8a2383a8bdb62295048d8204b23105592d60b017d1128fbc88ce196d7ca0416827731b3a65a8633163235b2d67a1d6cc5695a63bb6dd16f470c438f61b88cc1fdd4ed4db23ced078faa627463c8b4a96d4a020fe10b5ba4baa5dc53a239ef7439e64929d149116af06253a3184e982609f748708195b353452916bc06674ace54a92ad4b1755f70aa34385a553869181b13fa90944e15d959de1b5ac8696125d3c58b7452305282ad046079de35025632905c61dfa5e789d932e369ba91a764275a547611d2612eedf05fb86bf61f9201abaacafbc0cc4a442a2298535ee2e18847283cc6384c1ecb9f91ed926c9bc45002389a74020e5b018942d8845c3c623042dfd0ec7cc8ce86f9c48443178158c41429c9d4a878a19af3225582c75459622546b01d83379572103f9542a56c9bb25838544a00a625d48dc57a1d12c12f2f56d17bbac5340da1cb5bbc459617851f428540657db0136b833a86315277b7dcd58d43f5852900984312f198a57858f2f865b9e98367731c4347b9c9857e2b44a17156ca52613273efccbac67ad562b399cb7f0eabd053a5c65266ee82ec3a9999d92effc579109335172962595aee32606c1b99d95db20733247af8282d65663a9dd94fe23997e2229502eef96ebbbbf35701f11e4b4efcfe632f00d7ebb54ac1b4c6986cfb25e045099a961333aaaa08f1ee3ecea0038a03aca1062ce0d7234027b41c570addcb3c148c18e753812a83d8c43436cc6c983017c1b5e32e8c8ed37865531fa55c64402210138419443e80dd87ef61c3609702fb73d5bed991a60b309a9069d3348ccdbac530b4e887436c2fb621197cece15c9f4b5d4482b7711c275c980995e5204deb606f55368432721bfe1cb6408ac6664fa0c409e5bc42a2976f8cf65d021d0070643bc30cb626e6b506d025a7cae1a6c5c96183c39a70c0a1ef6402dd75ecb6eb6049a7bb9498cbf89d4490c547956ccbcde194b0a19438a42a5156cef2729ad2b1d42c3725c66b93ac4ccca7056b96e96ae97e82296d28ed0bd27116aff157fc60e30a00efddbba7a7a7a7d86eb7da751d9c735a5515bcf7cacc89edb2ec2c97892814ade4d48c74e8e22f086b63bea6c5f7a34da7259210aee82c127c348250b21433d399822c8d1938f9202d3691bda7051a172f25a91172494934c414297a5759b2f794fd9c61c3b158d81785c2c983baeb3d350cac5640db1a74dd0643df61bb1dd1f75b4cd17beadc989b10873a45c5e41cb6a38fe0e36c23a644e6e4a0490c287142fb5c542ad1de13b838d14084aa8a1e60a660ebd964df857e2aeb558be38316c72b8bc3e45401167577bb7fbb09d2618297d232bc2131c7ad9cf0f9e2515c98d39c98e7cf0c42f11a1c2c2ed9773e7b3393a4f4a5bc94242f93f933032eb16bde9fb41be9ca51666885f9a8bcbf77ff771e2f94a0c330a83106ce394d1234ee8d464fa8a611e5a9ce1d8bca5d47212b92172be96d01c1842b614c372b4fb7e4ba7385c434345245b85395c65b5873764ee45bab2cca7fcae07e62c9a45a136d22872f40a12d3e62b1a83243d807568c49c594c2188e8224f531b05f06f553691487bfae059ac660bde97079d960e8630ee270398731620b04d150452063684ec4149c32d92e34734ee92cd7829da79149e201813136143573ea9b1998af696a34758db6ab7174b0c2d1a6c1516b706c095d912216ced67c3e17b2b278dc05de2268aeb35db79bad327b35b5b8764640c4df17983d7832afd8773ee4eba63be1fa05f064065d5c0edf8d22d657826cc906c5000020004944415450f3becc9237ee9f78a5d2c3f8038c6b01d8348d4ed304634c96a3e9c683a5e3655792d28e9728d4c36926bf5c03ae94cf66eab349190c9a9192240ab8bc1a71b8b252a81b6431c1c319f34b3507f1e327a25c4b205cb023d23e043b5162cc4d34d02e215660500ae8fb3984c1c169e327ca72d4ef48536fafda8ad60247470c3968304d0db6db0dfa7ec4380c319491babcc5662534bbe12709f1d2c9eb5c591143154ce1c26028485550b01d89c3cd6b0c13aa2a82aead62964a8dc355851b35614d4bb643f178ad43055741976cbd653582ccaca3e9c4234f786066f385f52089c90bc6f3b3d325db75d9a132c7f07467394ed479cee91caa99bf3fed538abdce7b170ac5e9c707e0d3a74ff5f0f0105555a9b556bdf70bc9b9f327c5eb81b44a2f4ce908c91fd2c51225eea7a20d3c006898f8c13e6084d3ce804a28dc8d400c71430e49cd949c34b438b0f96a9ab69f1446510f977e40aeab03412964d7507c540d55fb21e52d64db309b285529d88a6523a9185f2c336d4a30b66da8cad81cb418c70643df61e81da669c0340d1099a0b10971680295a4670ab1ccb1c2bc8c0036a2c07ae166242168be59b7d8ac6a1cb606478dc9fd5452dd1df69de07d8ff178a5fc4c64f0c563993c8a8b6c951423ce6e950cc2cc7c790f0089ce9514245fd876b1d74cb0e324db7a4166ceb69de40b00b20333ec579a97b3df73263c9ad7997707eaa1443f32037efae9a7ba5aadd0759d7aef5555b3042dcf0bc7bbc42030623e3786777b66152736ffc2129f251ad2627cae01cf441c255502617465527829c5c082024688ef91596c323160da212dbf2b831119a4c13ce4b8e2ec2048ded2f01824aab044af69bce59a217847c13e74452f9bd24e8cac483129dc5440d310369b1ace55a1af4d2f1887d849c00d507810245657c46c99e89049e96ae1f7110cc580795361b56ab16e2d0e5bc649c35833a1e190cf5146337601072cbb8a25b60bc71a73f03caea02a33e024b15ce1ed2ee466fac29c4c8e39c1407c701825869b83e681fd5493ccf433c892932539774a1989308ff21c2c7eed3c13b57879f7ca1d5fb3a475db2c27f7f71cbb005400383c3ccc5f12ef7b7d850177e567fe45a482c20d3a1bb7e50f8f8ff100d162d28777675b80020811991014590e8121454315446441628022388962f03dedccae3d9858b274da9455e5f1fb721c5167db9228954611883c54189e19e442fc8c98619c99fb9d46399a00688a0a7eb5210e9a3cab7547d86c08aae17e19e3d461ec056e72803aa84c5009b7a9cb1dda9850d9d4d6c1a26b2dbadae0a00a81f2152f83e5a5842c9f0be6a6460bd9a973ec6e663be47338db75512ea7735e3a377654cf6e4b532f81edc4fbec404a3d7b96325372b0bc74aa241b6ebfa3a5e0dd3417f33562be48cc6c38dba4e902c1a2ca647e5400e6717e7e9eef7b0d40a769526b6d10122212c197ee9596014831949ac6ecdd9c7ff37201a51f24bca13cfb4f8ad767fbb1dc9e46d7785c510070e8e5491c4218892228ca8b0cf29278d3f75c61c4e26abdb3c3a9709638d8aa44a9ada28418a2f721d3c64479ea2883507c90a76200230178e90bd249b715d0b4c0860155869b6a88afe146c1340640aa381843681a8ba3b5c551c3d858c2da86ae610d96998b096469393d2fffae305e01b82437172c53f4544917da99e9f4ca499f6dbd20f57c6237171ec587bb60a5b082f804ba5daf6672aa04f5b364bdc47cbaf8fabc079ae138efdf2cd516c72b7d463425f5fd8192b1633ea8129146674c005960c3243f13101300cb3cb182d5d20fd5c595269d0ccd09c733f300c8804adb0aabcc0735f5a9ccdb81ccf25463405f83f72fd9853be58648dfaa6952d0e24564d0e6135bf4c824827a84e260049a60936a1409220213ab32d818b0e75c0695e4a98d31b2e4bcc8d7029eedc5aa06b80bd70316c63831b68381fa1a75051c378cb76ae0806669b93b4db4f82b0197ee9150bea7ba1b3047660944e7463a1e596e22bd8ffc3c9ff76c9217675d43f82049cb144af0ba4c8a96283b73ec6e97b54ae62a417f655f5000af18e922ac055cd36f45f19e88aac8958f7f9fb1af31af02d08b8b0bf57e8e397aefd55abb083ba8ea0cbccc82a43160997f5b76bea4b6eef3cfc28c80f4c383a40cf33d4ac978d00abf4a2c648d20ca572e8957d5602baac430050559129c299443168b91be00009416d89bf771ee2896efff90e8db874261e290e79abdb2ec21c93b694c0eee4bcc3d650ba803a40a80d4789f0cadc375c7233ca656fd96809a81430bd88ec020ac011ce1d572f44bc039ecb09ecec0db9599527832a3e1514cd0145ac2cc8040f46acfc730cf0399ed39f12e2ec7fc4c2da46672a4888f5e5629b27d0ac6433c51f92a51dc99293bd86670e510c7ff4fddbb6dcb71245782db3c2233cf050049548114abd4ea927a8d66865af33acff513fa1efd45fd84bea21e66d68c5ad317ad6ea8aeaa124112004182b89c73f212116ef3e06e66db3df300a08a944a411e6466848787bbb96dbbb9b907280aaac1af06445f185e4b67cd2a8ccaefe078a306dced76bad96c1480d6fc3a05e0f37f086d68a044125534fbbbb4160813c20f239a34e30588d5d3a1a56aafc08606583488667ea36af9a6524d521810e1ab288c4160f5a0b13ea38cfba821d94b7baa0ecd2813f9a80b856d657b359d6cbfd39407e852974b2d65f2265ba648255af877409e809c00adb1a501c00700eed4efbd3ce90fd36c4bf79711be5da3f51485c9698d50ac12b041b8cdbf67f7210069d3021e54b1a8663781ae1cd1ccb6f85a0bf872f04f442dcdf4b451ccd114fe24b560e5e9529cab423ab02646c07fbd79c0fea8a0434ac927e14b9b49f69502555cd241311a35f4909580d432b456ad922c9e2aa6fdd476f30bc2169b97401b17ac696535748220db4446b947ec4d47fc661f5e4f51eaf3b8aefd636539013c5bc34c296ad164920a1051e639350924d594a83a87a963d98058975a65d54052bb931458a4ae191440ebe645eb771c4045006ea6ef8b693b85bf1fc1b48b33b5274503bcd25c3d9285f88eaa5d386055cb3bd0965c36bbad1a6ea97e9ebd74b599bb332de7000b303bc8829dcad8699dc3750673ae03dfddec64e57c22d16f161c768f66d3ffdfd97172fc1e3f7eac007418061c0e074d29e96ab5d29cb3f98138363d0b00eb8a416d4c373a626385daf1ea5bd9de98a1dae87ec7a610e062b2436c60923d43a1e5a5f0408d9a8a45c452ae011aabbf98bb91ce263e26f606a476f95da8c7764025348168ed5a89e062291a527286d4f764eb9091259585c979401eca9ba2548b4f993bc78c77de9e36e5fbbb826f46073ca064d918e82ca8c29aae4a470b6898f073796be356bfd36894ef75a2db4cca79ae9b5a99b6d3589d60e0b208271411e144ebeb99d42efbc98676e6777078e287b7a350a271e32b75d874b66745c84243de4291453589dff19d1cef1284f1df063ed27e0c44f8b579516659d40e99b46c4d50d322f6abd3404d3d1a4ace6c1cf3c1a82adbd0c8429bc59eaff565d4b54f52548be692caa6887a5035987d3739d036a53b4833726b6dfc6be33497b66558d65e64b9141f760472d923d5723d85be27948d9db06a9a7bf230cdc7e09babd65be61678a1f52263c52491b53f58cf3bd58e721d9cc21cd913a58bd9d9e667c65442ccdff972200aaab8867559ec8821ff0f0e9e7e68baae9c1cba0693123c2a90a67f49bfe34c6cbca309ca6b022de7b32c6aedb49ffdd6ec0defe1d3ef5e42171959012c3fa5c7777171927072c244545386745f9881b9988616a469cc5aad981237931bac1e0d2a1550841fcb402c29ebd55aa7feaa099f11406a4097a4beb555cb2b94e7b1c890db0ef3f71aeda7c052b56b5e2c5666dacee6efac8f660658e7488c54d92688f340152c666e5aaea666685d8bb8d8c4794d0e2f5b64e412f051f2e35cd12913d67f2b3fb6110c341efdbd1d7d1874c693218850c788033c0d09beb3e3684283b7cd06da35817ce49cd9ff8b0a93b07fdb4828265e6f5218213948e392b7630afe2fee07571e75f833d0de6b6572acba2ed23f3b98cb118b800dc1c693116840848a44c334b2089c76edd7fa02d13ae99c67fb3e97ad0f0f072c8719f30198f7c0b407a61d70d895cffd0db03f14601d8d0b0ad8a6fa67009cabd65b2660994ac85f33fd6998820a02636ee95f649b0952163b659bc6c334613a1c62f3a369c1b4cce1f72d64823a10db6d201cfc3e9ea1f9c2b8ec0116e79bb2c61fc65b2464ec5cb631aa03a9b53f5e7715148bceaa0af351d5b4b91d962ef76d8e6fb525850562f8e8b46191ef92dd246d8474e7dbb9127126ae7ac724ecd127396386003163c1182426f19b2b02afdb9e298a6a866a487457601256ae35df346b0a9fc34168f7d90dd23dc77777b3f693f95453b86aec141902599692ce2643c9e91c0a1813ca0afc692ee6241358714b9473e1bff22e46d87b128c7189d99be192102c46cb98c6a94293a7156c75826a9dbfcbb1e0b56a5b8b70baa969b4680057ebb77f7b6da67c5e5de6117b55fa5399eededbea2d9648ebf517e4694ec91722e0bb386e0560bf28d70e8b827693945a57976baa7688f1064ba4d040884fa53d3f6b20a3b51e0d81708eb0a85b218ce7c7c0564f886d58eb6b0f391dad3cd733feb301aa95e70a0075ad61331de1819d888e0ab5d9cb384079695486ed62162eac56a6ac3b63d76c5f91029825950da596aaf19200f300ec7740be4003c01945ebf95c9f567faf313b9758781a6aa0ed372cc9401bafc00bd68efad4419dbff35dc52ad838c062530aa64da020101ef346608bb55734e3c8a2f2de747de23242f5738fb4bb476c1eb19696226a543379f86017ac39c79b96bded7863a9ed76ab0030cfb30ec370f420d37add791458849ef3895bc027e279db083e8e5c5fce4a29d59012a9fde780894969039f496a68018201c5fc2d5b96028d8100e066983db0fe85f4afe66875d28a996a7b784a53b6fd64ada9d10f63463307f352766f9be762322ec5849ca76292eef7c0b6b341cde4e429866506f25cc157c39e6aa14f67e2e877b1a0db3e82db0f40b3960d6debe647b10f4bf6e4694b9af68c16327183377cd02bfdc9dcf07160c0109a2034b65c5719a8ca72043e78fdce5f041f7561ad6e8e36e54abf4faabd6a8edeaa126b3a27806317ef3bdd9817b008296fdfdd12a8f3e08a96f0800b0392d2bd60be07c5048c3cac1ca154a61a74865f3713ebb360379b3643d548e5bc695117fa6492b2d9e96da5767b485ee81176b5fe70f65205eced10758b0ef8561d196a1b472de535d93915501df6c06186bf464841592d8a329561ab15687e2f13d8a3c5560388fe2654e277d61abd5c6e5b756e1b1ca1a6aa91962393dbf237235860d4084dc4e75d03c68fa372ca655a55499c66a759711127b6a79b36978b398b48b680a31dee6e914232a575ca8ae4e3a406fcf8e38f6f4573ff205e9c0b140026e7506f7dfba912e2c93fd82732891864d3ae1a9ffb7331a69e04e32f6fb2c19120b28bc6ae523eed10f27f98611b0e2d8f61805571e05abeb9d6de1a17dbf6d8948469aa12b0c99e2f9aab369c28e123a34639b56abe0abceccc6a33ee0da5838634e226f9791bc8f28296a5ee1e3d37592c8b99a21a7bd2a82a322c6fb3f695036ffeb0da061a9eaaa6fcd93ca1cff8e3b1a3dba24fb57e27318134204975dabd1cfde4ba558eb6e22c740a6b30a564492a006e0f62daf1560d68db5200b07d61b4eec372140535e75433a1abef48ed0db13d8ac6091bf3e4c4772d196f4090b84f117e23d5dc98b2c5fdaae6223fa73086f96a661986c66d35b39fe7895f054afa92c2e7201b28728b6ce825327ab45452e492f9495a760a572dab0d4c832d550b4ec0cd0e58ee94415c50229d96d9d2589975becdf7d92433d0339092788bbda5e4cbd96647b6d8d5b68808ff0e6823a921488bd91963ae46ff861b02645ac7d34e4bb3503bdaa824357b910207beb5c106af91abf078370907e71ab3164a652a4016917cdb6e10d48c773ede795d05ef0dca47d798da6eb3878f1cbc86f0cd2593fe765753c89826aebb607770844c8b0ac9b7b33296f1c155d39d6d2d1d33f960d4328d6aab6014fba7b1a6dd84b5ebe2d705f68e61330585eeb5b6647b666dda32d7e04a2de71be6697c1a108d0ede433136a6f611b58b10aa192cbe1a3da6112cf0c2c11574e073d5a7f1bc60fe166ca5ad34b151a5a0d1d84d55e1fb6b6f9c5031d631bc54bf49568d29944620d4b1510993193496a55cec0cd16c44663300d5175c964579ee7cbbddeae5e5e549fcbc13002d00631b3391eddb6841f5a888f9802ce35a4915accecc7ea26c2329e10ce660001c50ee3770579d292b409c19895bf916929ace112c3d3bf0fae11a92a62fc4ac54ad205443a0efe3d25b34b10ea3328b4bf8d8afd234dbb200133793783e9a17881490392621408490585cf15cf7d78c3d586c229d832931bd5029da83c0078ebc30e5318797b57b05a689684c8d2fac2cd3dec684facc8f8e3a6cd43b314e85638f527121143c9631cfe1d019af1be80c07064603df7ebff7a69c72ed6e05e0f3e7cf75b3d9e8291bd61e72cb3ea159eb6b7c8f0d4fa758433825aa06718d09899a2e15e92401c3064f1193ba2ce15aa71a31983cf0c4fcc1365ab93edae15ac3dd38cba021891a04ab917bd382ea5a100ed2fa0f01232e1878d4542114c0e1107e604d6a69b45f336654bfb046f60d65cab59c739d385f624bf7ba7a2156a5d3db837200c3e91a548bf650bb7d2c6c3caccf4a3e2b8480c31de131898ef27c5f3f7e613295eb6e1a7b4da4f1ea590b78c61494a8eaec0118d27c997fd34205f43307a78e77d280e338ea300c6a7380009ae04b3f2521ba74eb0a40c469a819d2a7deee6941f51f0f35779aa7591d4f8ebc1b7724119923d55bd572aad2381d2b39639e8683da294a018affd7197304a6d07aa17a84cf23b451b3c70bf89eda3d5b3244ed3ca2ee9124386e16a0cdb34a9274a48ef1a4b9453843682ad5427645a7a65450162e33fd88c04a65a59a0e2d78d8b4b5a2d28e158f8f9f13fe4a32bbf007712c02f0f5b16c9fa3a6ca653741335b818603035f4a498761d0711c75b55ae966b3d1e7cf9f9f04e3b7f2012d08632a971fde9aa5e69a988c090203315885896d44eb352264982c715f7f7fcf798d64541c9509d3c26f686421fb8efc0ca5b25e5d11d40d336b8c193c0287131870f3538e2e0addefc065a9406d9f35164d743cd39ab80e70d2bcd10828106bf5f2d2a489d99f998aa61ddcec341a577f53fc9ad1bf15803124ad00d486ee367cfd58d9f94c83d2f2567c8ba910e723362b9b9157ba3f04be9d17cdaae51d42c6f7b990ccb1e8bf976571ff8fe7004f1d2701f8f0e1430580f3f373b56d29ec9aa19c51df276503e5b54426cd9c038ebe75abab8ca12b619b3b2596c378e4b39e6f7c0b4286135502e82131eb205bbea6ddd68c32eaaa883ac8de58f5362aa28b52db6eeff3b3d74b97082bdc3fec74a4034508146e269a7ae2320024d569079a8a005de70745bdf43c6f9395a32469ced1041a139319dce964a6b71462b83557cf396db51416a7bf4df768a5a9348cef5c454bbf22d21d1946662541d932aa0363d6518c1c09f778364b698d9189b15245d6b9ac5f26659352cae8cc50d380869bf3f373bd77ef9e1aaef8b8751ac27646dbed765a76531e7ad0f1664cfebd6eceeb64f405120412d6894e6889ef364c26d5d506475a26e8f32e99d0c217943eeaa089dbfbe283e7d30ee589b069051b097501a14d6ea978bd7066900a6efb04ff66424b308db5a55588b5af04406b8b056380460990102bed3213cfac3061e6b2ee5b5085f3343998d20838975f517f0350f2ffc0e7417e3682ee5d19e20cfa19c06c0044751cd5236d99006337086a34e03687d96be201c4e7a52bcad312d996e9d5d7392800dded76aaaa3e9587f6c92735a017b8baba52207c4033418761b087b94468beabb62b65943042035aa9d43d364c2226b47a3d4af5f5e6692c77f21b88215a09dab7a36f050d344de4fb7e228a90d6644a722a176b40fb6d96019b82ec1fb2fd2820adc96b15692fd02cd5f2eb34a46b403ed1389bd1670125483710628a108dd8e46be8c963a52e549dc1edb14a23d24dc497efd640d2a0f65ce285f860a0294852d7d302967c3d6f76ac57fb67161ca2e1591b5e27f72b5b0026e7ec5310d334e9db4cd05b35e0fdfbf7f5d9b367b7ee8c56daa9a609dbcd996c9f3a9698279f62435d88262ea184129c2d29b8ec520d5fe95e082b7d8dae7540696c3698a1619ce6568656b4c77ea196538ad014de96f6d3a5ab05936e2b0bb429754c1889bfd60f94f63af1fb54e7021b3a58fdcc70757eabb5f9a33a052d0baa9a2f189e348181d48497b7c57c3b125cae594868f978971debd0dd731c6c6941d7b49e71ef7ca66d59af5fbcfea89e7c4055df7e43b8a63a16ba94d403de1fd7fcc03e1839cf739331737171a19797977afffefd23189cf401ebce68b8b8b850e05803b2bddb3fbc34ae9aa04deef8ed121540237d4e311308486d9d21c2b48a2affd533db512043490273fb582c7603592a8acf7ad12438a7927b760d3b5b1a06a029a88600fc8dfcc1a8511a33346b4cc6371a907e47ad2d903d08448ce9fdedfacedccf7de4e822fbc8118154577b6dbdda158fab0d00a8459c58dfd42840f8893d1df921479d6a7a6bfeb7091aae4bf302d3772005649fa601c94ad4711c75b7dbddc2f0e5786b14942b4829e9b22ca605dde4ec27e701334183f84c30e5ef2eba5a691a44a361a0f3b79941b67110d3d88328086907ed2425b5d0253995751f8fb502838f6a5000b6a42576d30e7a348c79c4bcf51461a5fd82104a6169b5cccd26a6f3918636552e18e5d8df83fb7fe10bba07af9114c07ebd6b4da779adca170a846fe982d288973b9e20b6a041699e6965638ad6ea2e7ff6d24d1ba306b8ea5506098af7e444b67202c10255e53847f7c75310409935381c0e6f041ff006003e7efc585fbd7aa50070381cd4b2bbed21f47a6a036149c457d5245276652ab93d30ae8bbccb20805359bd183c29c91c001b002f6e3238ccaae0ef6a662a0388eb1627ae5446131f34aba30e8083031180a903188953d6567554343a4eb4fdace73de18c345163692ac0018e08c0b4634a4d0455d95c28d7c49f5cce91d9aca82f2e611fb0d66f78558a4a928dab7caefeeb3b19862910011da38f01ca849099f8062e4ac0761f3b7ad0a2470c84113853e737aa1f06c23013c48529d1a5fe6eecb7ac1025a87707cf91f304fc7abdd657af5e69dde8ece878ab063ce544f2ce68a1f5100b1435176141d288bbcf9126ffb7fa2825c811e2dba70fa2e38067ba503506b80a06f5f388f3300917d294192c341aa59498efd46b4d6d1eeb208da9dfe8dff16f0230d1a2d1a49521d9dc6c0ed27e140c3dbade1f16866fb81295757dc848a854eda5e2546fb58ed8988676f39437df3c97faafad668a3231c6f535afe59c503d507fc39109d110b0564798158dc5e596169bdcc62fb53e55171eadc521c0b228ad863fa901edf3d4fad9db8e779a885fad561ed9e19dd16e9b88170b8cf22cb093856c2366b8e82d7cf0b980039a245ba54de3586bd4e7c06e4cbdb83764bdba7f51fe27dbbf6fa39b3fd6fe0a524665d39753bfc3468c285f57b61bbeb8f344e0a9bb5d701a7fa6fbd9f7b33e6a476f17304e170a48d5a73a6049c82a023cd108ad7b7c5a3b5a01d8d469b0391246c763cface47e63fbd810ddc13e27a4151c88469b5e13e76ae5b1c2b149785e8cfba6a548fd625ce016005ac1cbcb4bb555f1fc0043ba9deb53d114925bce25896a0cef94a06b1a84e0710d2915f519087247714fb555c4736c0c5ca297fb9b241c7a9667cb737b7b8140a82ea31152fa4878dc7a081aaa9dc06b5f8bf5dc406664f6b8d05b9f2c0d4d42880410bd57e47739d59aeec5589a8fc516806b45c0b3795469947ce84313f9b899d66f846bd4e726ada0ae6088deb1bfc7b48c7c5f6e5fcf5a9537c8c428427ed124d298a040f0be4dc003ed2e826f5a0901bca306dceff78d5a65109a04e0604c79933c3db39372cda7f2d7b69d2ac745ad54e3e7d8d9feb797d1e6bcdd4fbc1fe0010d6ed33e2546e1b2f51f3209dbbe9ed245d469395dc21a789b0eed8a355495ae70c3605ea8940c9392cc40124c0d501a28320683d60c12ef6233348a005507086e2b012fb45f4f83a858e927392c15f454973f9789a2def6a0a9825385f2bce8b05add9eff4c51503bf7b69510c01b00f8b655f1f6506e445c3001d17240215275a6f9ae467c0773c7a0c790f703c452ad9873318c61e7c77743aef3cc915dc28347c064cb990206d63633c584db7fab3e6a8dc496bd88242735a23496fd91a57ef289e10284f5795cb290a6f6f868588dbb592a76406c1a4142aa01710b2ab4d5399e19320c2eab8fbf1f4b987a8530c491d5d30da6762bef17542a9897298f439936e798079ba4ff92e35bbfe88c11eecdefb6a5185ce6b5d2cdd999cd08635e92a48d9651749f61ea35924c89f93b029bfb182696a21f46071b838e19478fdb506ee7a4e0b8379e23dd67d461f748d75eff7502030832f5a78f0fe7f180b8d392105e820f398411d10ab69f0cf5cd130d7ddc788c88c64d37fbffe81e074747db93f573d9682ba8cebe8c77ab498b84f729f8f218a3a609f3341707a612894d50fbcd6b01bfd3200cd0dab59480ed0db14344cc0685a560f501022aec5f7d421c35228790dbe25a53c864e36004ddabe45fd4b30acb493529cf5a38ca3672cc33609a934d38a94fecb4afd2d5df6c9f612d3f4a0bb36bf59f6602de2af64b5ef614468f4d50d68046c3d687f5cd32c8642b6438eea06b14b0b04243774bf6865fad772a919419be9b88e75ae3f1ed73bc4c9766162f49efad8a301d7878bdbf6cb0a9f5a2d07bc973166ddf8bd283116883300050df2e76ebf15600bea982db546f42dd1ba67624c2f81d115984792c5d9bc10d29c4928be428050aca676eefe9a464a927471dbd74e532f43c1b60dfeea6b957a98f75332274f5377021edab74ba391a289d441b75e94dc59a330a4b08e7faa9fd4795d7d6f6f432a23bc978aceabca2f340ab411b0169657250a5a731aba69e37a229daf20595692c1b6aa33fd1a2593e8eb58ccd2f0bb0cc8baa9ef6fb782a028879408b82deb61610f88edeb5db03318966d5ac21a9590d9a0385fa475a8024bb9761ffd0cedb4f0585b1d56d7633afc287662e17342f09ed7238a954009b4c1737e3a8748c08e0fb9a7a459c4227543b75b6b3121a1af0e59365b825ddf98e7482d04cb1ea437d8ac078ddcf33b38a691023ba38939a6666c6e72822bb8d9c08c1eeb72d65323c44a3b59906e0d25a0000200049444154aaf08978370794a804f491bb76a57c67f9d4fb5a0146f697cb4881e6298b84d9799bff671ab0becefdadc777be2f68696002250d84c40450d6ea0e7ede6c6ca150b2d95865981268675044667b35a55429f11ac5d42cb3b7705343c5e70bed259d360691af1943e4735fb0c192e69a6990662bd3fabd5caccb15d0266ad3138e89a68ac624af14b06707cf3452c7af9d0423c93abe1aee9f371a113409abc12a6bc158c7d12387ddf8ba860a30371a302ec4b3ac4c3cd0811fe050f745cd04669af4e598106dd0ac5690d108186b96c2569347fd02202faa18ddec5400cd762caadaa4a1d1f2a3371edfdddbe6b9d2d450a439423bf46c41d7996b68b022073a021aad56389676f15be95ebee388433d13234a54c66c9201a8698dc2338085a917d5932e3ae5e01d1db51eb0929423b4bdad26e97f752ab29f36b06b114882b7c3f16342aa5138a439285053ce06dddae61c53a7698bb4346cc5577bde8488b435f9a79f6519d64b2fea1adfa4ba64d4744b54f0f1f49b81cf8294b614e9fcfc5c8158e4de1fdf0b008761c8b635614bacdc4619db305a07960046d8e5f5fa092fbef52be2c92457cb212149fbb251d371beaa0b6a2ba1daf27165baf0edc8f3d313f24881d3a0abcd14ffa7bd85dbdaf2faf1c1643dfd94ae83a1259a3a1bfac3af33e95883685b61bd57da53f45cd6760cd8c65f6cec557abedd6bcfd5306f1b5f15316e8d6f68e78817c9e50c1ecd9a8794726f7ef23a404bc8ee23a0b6b8fdd4f1bd98a0abf33bc8cb214ef45aca4e9a1650842f567d341b2f5e176ff7a9d6ac110de3d44cbe52da92adebf9a066b92e6d7d00bc4edb58d756c4978b6e77ba1ddaaefb8b16ba6cb0f634abefad3d9d783d7184a9166327f4ef9190a79f4237784e33514098ded534cfce840c246df069811a8d42cec06dc3cb386a750fe080239313ad60b27654a83ab80552d60dd6df4e119ae96af0e9f554f39d4d66a2a336848b7efb3079e152cf92e70c8c6c821e6dd00b40976579e7d5f0c0f7a4014b622f6fe3d7494f3fa9d16b92c0a1cde8466daa0be916165f7b3fb88a28e08c8460722fcfcc7a52ca5243b8d91ca573591c5a5eb905de0f876cfc6c6868ffb6263303cf70addde57e94a9e747cf727af5f69fa2fdf33e599e645beed852080175ac414f1c4af59fe096e84753bcbbc0c2a0151ecde76d2683cbea169e50852eb9d9f705044200997743038a09faa6eedaf1fd0050b3cae2ee725ce806b5195f3023f4860cb13401b607582b8883f98349e226e5113426216eb23ac3edab13fd0628516a151c6cd2556700b5a54f3d3bb0c9da12119d33d20158bcb56f3441ad2af622030c64def2961446231224ceccfd6a121a29a3a9bd00c6b4a502b12f8e3f83ea8fe24075539a9dc04d5376a6a2b78deb61c63a75bff5575157c003b1ec2d6a63ba97b5f0f33b6d47d1ef88767171717225bc1ddf0b0021d0ac9431637eff69610f00e4142b1a66e9017cf4559b0a9bba4f98be66d27aeddd72a760da6a9a42c93ca620d2114903509108de098be6680ccaa3a04c808a56d8b72ee1ad06ac745f9856360ecd34816a476e039c9c3867e7b9532dfd7c4541db11123676421cec11ec68479f1ded768681dd17964b1d1d4fd4d31f0cbbe35d17fc390cba2320f6c9d8fd6a78db65a23fbe171fb00cceb109aa6ac2a69562a54065c86ac3bb1fd6f87980ed17133c504926b4eb97f96cf5e59b001adf0e9a9de163e3a49e114b1911f117bbd854896385dbc6a6628df33b1093d56ffe67258ab597296742cab51fb387dfe65f95680ba35133d4da7cdaf3acddad96e9b51d6931a53ac8ff636d430646776f0bc222a40411f047d3e82689833b4c4b9a78baa3a149e3523417bcbd4a546f4c5c6f772bb95415c88bef7b544ea97fd224bca6949a657b6f3bbe9f69087b89384b6bad2ccad254019ed32a5748e2abfd0357a1a2a687840a9134f654b40894682d17c256e25600d698782e9b6bc5dc8c095f6b0e4f8584d91682b67a33474e596dbf0980135aadd76cb1febf2dd058156665900cb11b58d7b6abf283e99ab933d2282e395084584305bbd769d3ee3ac72be26dcc452568675342045cbf8187d47e48f4cde0632578cac75301bb523e05c5fd20e123760f07dc8c46a2bc296fb3357dbf23b6f5e26dabe181ef5803465640069685486882d2751469f6c8f58c7c40d2123e890ed72c3e3a22412b03819856b37a8d233b8957b5649bb1115a4190ea63e2f90e28eb100d709b2c50345d56d2dcc6d87dd241d7e510b17cba95d9cd443c29a14a92461b86c457afc9059b497c1a2485faee60a8df8f40d76812f88ed8de52eadb71c08bcef9386aec09c3acda583a7af2d35bd1343104affbb32c18a88ed6eb0b2dc982075020a3d911cdfeba3434df96f03b49c6b61c365ed96b76ae76a1a4e6b72097c5fd8d718546acf40785f5435372f190ae917bab8e03fb945a897d3a0959cbd9602885a99d21b9c2da208b1048d7fa883e87fad1004aef76b03c765354d0d46b0a2e56adbb5c8e12d27e4d54b22199ff8eab1244aaeda88ca71dd0104203902e7e44029452c52cfc6f20d376948246a0647b97a021fc7a33b2b5084960da496a76937404250baa3cb118334ca1a04dcbd0e5b408301d0e38bfbc28bbce9cd88ac2f241fb446c5e8c7b6a353cf02f3441fb25180c3ed5ba9774f36a5ea56f2665f43852c9d7e3844ba276402bb3d73ac212ed25a4d5217edecb485b9697ab380b348c57cbda3b068909d82f323f4af91ef3b1b4e719b6f7ba6be693099f6ffba51a79ecda5d6612b6d2df59bd6ec8cbf5697c5a3f1a9f4ffdc1ac0b9abec230152b5398264e8f7a2d3b5dfa71ee09c2a3539f471af2d47fd16e9ada6f26476940ba76a2f2de3c4f1857eb662952ff4ab27e353c2fc67dd3f1ce00b4bd41df50248098926a6a36b3f2a3f171d8d56a38a7a9b6b9b7ffaecda77667edbb86245496bc0a52a734100de2480213e87c80597330ba02cc7e6ffb906242779d76ef566b2f85bdb6eea8e6269baba49cfc8bf4ff463228f9392cfa6a9b7d5f08164e0806f51ed56bd56c5374011eef8fd1c7c6c2a2a05503d5324dbaa2d579245d683ce4a8415e3f51a6fe2f2e607c98faf16b682c587256cdf3d106bcfd82f4536b6581372f6effd61ad090deef0615ed151555c5b2b0d94dfd22891ba7e84b6736ba04d3760c9d89d10089331f7c8058bad97fd62eafb46d542b3dbd0202149af3aca58381417570474df228547b58a963af7c1c7144fcdb5d32586953b2ad97c7a1a84f4593344175350fc84ca26058233d88143c0e408ca3b522daa1d1471a0fe556d23319844dd70984da5cd1b65dfe6f4305e227a1ef70224b5e54bba8e6a97434e0db2dc605de0180f686243ed76fc8c40d0180944413385460525d01a5f8a10d0c459dca4f92a2ee6bd44f589e6665b5ac61864821618968915d6f93e65a7c2fabc3c1c58c53cbfa5ea1213cc168f6e04afd2cdd69eb2d419be85b3c24d73ac49c5e2393d3aa7c25dfcdcd096d9f4b2363a0f40c3f677aab97f75493d064e2ad26cd10c2cba667c4af87390d6513d97625087a583b439956adef6a9b9f5b41a0654da50936868b45624d891bb88d141223407c63a32744773bd3098aa08eb7775916407539e572d5fb2adfb73ba2bde9bd8076dc0ac03e7b7b9a26fd16ef08cc192c3148a208cb9a7acd19d4022c65c49c11ec93226aacc5fc091e35e055f121052bb162e992152739181ff17c5e0511e692ba9fc46668a9d7da006a73ab799b4080769f7edb51070364765e0270d4bce67b98eb440857c20126255a6b575ea19e93196dafc2cf324a6001d1b623bd2974624803415578fa948144cb831036beeafdb067701f9a4f1f5812bcfe48f5aaa8992ee832b4587508b0f56034e5f36db41ff0160df8a62cee37bd23b05022ebd1cd2651bd9fca37747c98db800055d648ad1a771716832e8983f94d0b36834f6ce6cf755f481d8fd1f63a1ccc104a7d215066e49619ba1bb4ed504b243b772cc20a04054d9bf3b105d9d4d78a0e8a225a00a6033e8f9b254e1c99f13e70a10159305880cd2a6dcd57ca80410827176e76458fe9dc0885321c315efeecf029a99990be3df6579197594081be2e8b42b090abd5989dfda2dc775d8c0bbcc33ce0d5d5958a88aed76b5d96a599f30060d11fd786f55c4eed38360707509ce41ae2da325d9a80019ba775feaf14a80b6fed1acc0c213b8c565d48d25094761fca2096950dd550537b367cf0c5b7da475df88b087a64a9cd8cc95e0bf197f226b0052a1927422a1d75baaf8d4f48666a0798163df0b21d6b8509ed8289347afdddf8b8ae1aac231a6025ff97dbe349084d2025ea2a1e89d229327fad1673316ac582983a72cdc7e79583382d4d68d7d8aefd2db95b5314585435f5bbbfdb75daa4771886de3a7deb719b063c598bbd21892340a7cc50009a4f896536f77ad156bf2b89d1e346c460b40387465ab269e8656c8003f5747f80c998af91f25ed63d25f295d0de1b32d7f8d46ee51eb6cf3f85a0bef32d9fc6adbdc03e01daf68995f9abff14afe48a32dad5ef24229084d63a6e13ff38b224bccee30e694770ef0fa9650fb27582214069e2d41b40f5b174f01eb6ed521c09c7bc6488c87234dd4616a09de7446c5b8cfba6e38d26a86571db3b02fb17b4b019aa352bdc8331b298c86a7a67d2a989eeb1634334612bec369fceafc58f23006b5d841b83d90d7efd341325047705927fb6f506bb518308ec7e4fcbc1c41712be18d04cbec777a691500021b498d5d9b00d29010ebcd833d9df89266a0b8213e3e0828c2d0cbfd60245893e6dda5f771ca58ee929caf25303dc6e6584d9e9fd67c9003d513fe2bef8da5c5500c88b72024a1f7404e2cd61c0f152a4db56c3036f00e0a977040265aec366ff55356b7d397d1f882961ace83f4731994795c4ac99246e12f9c069105c6358dc37e432148e66d3b2122f92c1dd19e7dbe37c446a4165f4e85e16084aff9d2adb0cab9fefcca5a8211e8ef67a3f8f6cf46d04448323f513f64c363b6322dce8440052f8f222fecf131ffc539de195fac656ac521dde2ea39b7f563851ddfdd8336f34cfefe88f53cf212a59ffbd28a5d431d99779f1b77f01b76f47011c0761de144701de711ed09655b0f63b31fd90edaf9c2ba9685df23f80e3401db73072afa5217c7b5f7c6b77333b01d46e0f986ac9c7fdc4ea388a4c1ec9c2684b0f287e0a3333106073e6a03ef234044cc709451af9626d6b28327fb22952e53be80bff570a065da4b6d1851a1dadf6b24ff1fe84066afb617e161a3a8168dc82830b5a6275aba6891ed2d3df2dcfce2f6e62ede8abb44999637a35bd405ee62cc3f04edb51004559bdedc59c76bc310853b3b8f5fcfc5c0f878381cf66fc35a5643e1f6743990e34deeb32fef5e8bfe34d6201ad548ec46a78f0c5b783b07fed21f55ae264ec66a4a416cf5ede7745430c1a0f3a077600d4b2bccb35202ac828d2ace03bd7a040ad2d515047d5570f9880f0086eaddffee04fa80033538d87d63504d3aeed86805e2d064a52aff7db60b19fca324539591a51969b181852af932fb6f75ac5ea636665b4a9909bd902d649c07580eaa0b634606fea608d1cda97cb02c0b2645d57be06d0ec8676cb7614004a00f34ddb5100df2213c65e5176cb4b3afbd41c159b1c327fc5694e0e1a49db9050020b9ff14ed84a0ccacc886a56da200895e13e7380c5e49ed486b54164daa2d0eab67b4d10a05beb2831012f603f17b6e8e258645bf68950dfea89982e0fc9d534b1538eda94ec0579409335a00121d7567b24d3189a5893698b0a3ca13a98f98de7c3dbaa34460c77a3036decac8df56404db5ac030211c2c452adb8f561251fd261401694c4fa66d6848b292f29c4fbd8ecfcc4f7e332e103ba2e11d8eb702b07fb5922d3634957beaa8d469a6552213e2c42834407481d248cdf005496eb1e3d249cdf66e7a6e23e35ac6b13adcbf003cd9dbfd046618f6ef949ecbbece91af6985cbf7e354b3a0025f3b611d05b63a2662ba979f72cb050355ab99b42b13a4a7d1b199a8a25ebbb856dc63b43b32e6b5ad93418f86e6043e5099fa1ca38ef2133ccf37da226c365bbbfbf636d409ee5974e9819751d701f21fbfbe0128f193376d4701bc0180bc7c62bbddaabda2ccfbd86dcbcd51a11205b5494aa76e1c0d1f687bb26b2ec3c52453933f6dc4f6138478bed73e051430e898ae19058daf5c13d5dfb44d4eb43f52f5098c700d54eeed3d5be51ffef5b6513c2223dd6b5ab4d1462cd9112b113ac45641d2d19f0b9006746636c0d41f0d3337964f5b9f6b6e0671d3ad101462ab5a8a84f236447a5b8eb10996e9840aba43faeefb79552069bc98f3b6846c8b827e9bed2880b768c05359dc7d20e6d677042a6f20007362a28be6f4db352e0704531cb5c0b4432dde0d5ac0a69592ec76b4ce3d5d24397a0c3f6227e5fafbf671bb09dcf42ca5a8e26dc9d63d43885dea55a1953ba1225b9ab4b35b4aedf04969650153fe31c6079bfc7e0e4d590666430600a6262d38dec83b17824c2ba5bfd828b9b15b2ae02d66a0dee2b6e7270789dbcc7fcaf68242f382d5669551df8c64517ffb44d58496a239cfb31e0e07058057af5edd0a3c3bdec907b417b4588895975df492c04cd071b3ce9a97b6c36add823b48c54ad018108a18c6bd3d2307c158ca86331df7599d9e71df9980565b6b86443d1eaab676daf07b3deacce8a6a7f5939834ccd2b8c5ae1dfb6cedc993e6a795946379727c1fd3aac25142df6ab77d4998d1f04fd0392725a2dfdc4f3769998e3028b1b9a8dd5084e052de3e82c7e9a8a5f0765a45adc9cfc36de75b0e63fe634a1994f334db7614b9e7f7376d47017820f3d6e3ad00ec57c5f7af283b951d2e22ba1a57aa79a992fbd8882accd358ee010825a7bdf127023fc1ffc4e81db1fd3ed07918e3188371a118483fdf0954efaaa019ac18b4960102a847bd07a81c512f7e5b20e2d4adf633d7b76e3766eda9a2146b564073a6ad2782462c18e2079ba3d13f7e4a089eee3cd787104447946301d868614429edc488823474d4a174bd6b0e41ab96570022d06316f523cff3d2cf73db61c1c89cb327aaf4ef867fd3f19d6dcac4b631006099979cb3da22cd5326a80fa41e0d47f3af3381f959fe0974d19ce69a056ed87c4157bfd06f157a2d070d6a836cba2e1df0150adb90ffe859065e67b4ec965dbb3ed63607a2ee37846e7f3aa3bd71c8c57bef5342c2d15063fab6cde55a9f381d6555e3659d0e0025303280f20960b16a44dcefd7700b187b4ba416686582fa7fcc5fd6ee5248bc0ed6f4cda1194b3ecc40587b6c86f27614bc1afe5db6a300be25006d557c978276cbb614cb026326c7c829ee393e3818c1befbf184f009ce6ba6275a00175cda14414f6ea5a90d84765602908bd1ce840199b87560439c0463b72621fd3a217de3e7ed69db5dc15ba621a290f8c388e9b82f26482a303cf26c3d577343c327135a4b782c35820ab7f9ae5ea2b94ea323013203b6d1ba6107bfff6d94e31d077afed1e6a7025854a15967e37578a2491b8039f120006f7fd5fb77a2012919d58138489e15b336b3f0dd8aa5e6cf89dcafe58bebad56218036be490b9a6ea411a030460b1356f97988f23c502c71a34dd6fcda7adb3d9adaa0aa3119aea58c3d5fbaa7f951e9a5a722acdda1460bab8319b20972a9b7276eb6be700b6a7b798850d3d6a86f412e32532b883508d3d0dc479269cde799a647783671d722f0942fa9fd27b5a5d94787db40a6bc00d09c21c0d44740edd3fc40db11db4cd077598c0bbc2300fb55f1fd243c701c8c194739202fd9267e6ddfcaa3616ee86809d15d24d027e4833ab1c7a3d2a46db3f161f334dfd3446d7a88574603dddc060d769c6aa4ab0a815f5d3dab4ab45335ea3553f344408101d34c1d68219c274c9dd022fe1837b97b4524613978474a9df64216667cc79be196b27618d3cab4a50695a4196de85f0010a90a918963dad8c82ddea94648d23442a16d644f696da355647473d051fa5ed1dcb9d6a3f1601618209610c17cd8e74174220dd880d0be9b09fa6dd602026f01e06dabe2ed773ff5c0c79830613e64071f69c266b82d3dcb4602c4103e1e36287694721eae6049e84f08ee7425da3cbb966a2466ad469a3cf27a32ca4420a1690e7ad94a558788aedf8d7d9c0ddb873515b7066f574a4fdfc5679bdfd5b42eaf903eee9fa3cf9e4c65f2d1c34874fa5004d88efae434eaefeb40d7dddb90ae19c1b62d546bd316ab24576a5b178307fa8755d00bb0cc731ed27000999ea8fe9ff98316099de759fbf702beed78ab06b46c6e4ec8b6390f4ecfe13434003a4017e46581830fb094a4c6173ae1fc9befc1799fda0095ef2837b8095889c9f7b2e8b65790f9c368f00d14a2ad19eca68b742befdd11aa43a9ad7fe44c750ab8647aaa6f6bed1d6afeba94e1e620ecdfea03365b56709237cfff517dd4f8e66458c2f1b0c682517bba81b02b6075f073c84f374114e9721ad2896849a7a8ae881cb4cbafe2e19c4dd507801c841256874030cdfb05c87329723c076838b0497820b072efde3d7dd35224e01d4dd0abab2b05624912875eabffd72c4b0290d3a0534a870334973e25f1f7f295e13e7e59a7fb47f4bb598e443e913612db494cf7a21f21074da3811b90b4923846073e60ee2f4a770f81b591d21dc8edd300090da1114fd4e6afadab3bb889f1a85345ca776bb7e6ba25854d8cabb7a7d1b9d65e65aa31fddb4fd73ef61ce9eb68c7b7b9a7d2e4686c5b32927fd93db7e127a27bb582bc0de68f6bb104162ddeb9e54e7ae05014f37eb7cfcb7c00adf649293910f98f4d50c3ccdb8e770ec2d8a25cd380c33064fbb386d5862c00f228328d79da69ce5ac017a6681b0db4c1b084c2603a266c8c66f91a525ddbba1089bc6d642c06b95736479ac38086da24e1bbbd40eb378ab6655ca246a51277377de5db42db897181fb856d30a5ed7d30cd6dbad2aeb1271e1abb698d31b695f46e6bed2a69160e82393d3a21d52a22aa4ffcd369e2fd8ae73b5d695c852b72ffd752d594fa136d720150054dae81307701acba5468afaa58e6f990a093882c22b20058400a8727e1d904edf2406f05e39b00a84059157f7171a1bbdd4ec7717435cc0b72ab3dcc0d5c56abf1302cd7d7c09c218a94ac63085ba6117126bb324cc31b287b8956c68a811a60d1ba9d5d0fe05e4345011a6af2419d04ea377792b6add3bb511bc27536121ea1d55dda03278405b5f1b6d109d2f5cdbcf59e30e38a56c847115b82e79176b1538a58d41b956b539651cd14d5a69e933e38baceb4cd0134fcd130a10d747e05a6d533e20dc05a132433df435611f3e832cf79d95d6fc7719c50f95acbf6848b69c165591c90a7f240df767cab6988c3e1a0f33ceb6ab5ca156c39e7ecc053d5858138e49bd7986ea69406a494909240929bd91e1050c975b544f6c12a9249c3f7b3a4eb4a36617d42914c93f02e214d02570d251e558c016c07dfdd74fb41fbbd548d81c83cf4c324b00d64c38070a96bcca940b879f6217c32342020a4ddc42ff1280b153fd680f110db342afb4b574895561a8b921f4b3eb853b53301dbd0bd6d9025413b1338a5a2d6e2b07be987d40eaa8d2d75c8ed01deb690c6ef689e13417b36419b3f7b4e75932425484a98f6fb797bf5ead5300c9301aff2f70c60360c18f84c19d95b9180372762036f01e0279f7ca28f1f3fd657af5ee9b22c79b55af94350fcbda56e5633abea04600230d9f7bb179bebc3eb27d7c338161f70900a42a9605188999ebad441ca80d9e44438e4d67c88ef062262786612f0352d756b652663ba466ab3f9d29e833db3ab320484096e33eb94b4bd37263eb5afa7bbdeea84f8562f49579ae5461c548aee0bae3eb60acc1762adca8db4a9003daabeb3226cacc8346dca789da74082666c5b0b86e9c7b457f7eb1c5c39d773e53d18451bd275a2ad981d2fc0300e78fdcd57fb75922b559d01cca8fc5dbf2f001611599665590c179c05f3b63c50e01d35e0e5e5a56e361bcd39e7cd66e326677df02c2253950a0e3e1199c671dc8ddb2fbf9ea7bda60abef209a40a42fbb3008b18086db08c19441d98652c42323b331023351e97204c3ddf00884c2f80a271e51edf19db19b067eb0048041659539752b6c133e7bd06dfd133246ae676f5fe69ef41373895b62c958a0b52c59c3361f4cf4c42b20f9cb1bdcf9d606321d5b4c9fa48e02c4341c2af6d1d410141c3aa61adacf96b0a34090a51dcc69ffe53851aefe4304b1b89545da434142b0d009e7df1e8e5d966732322ae5c5078dc00399b129aa66919c7512f2f2fb32d5e7897e3ad00fcf8e38ff5f9f3e77a7e7eaed5f1cb29a5659ee7651886d91aa7aa070007113988c841550f22727870ffee57578ffff11524631804c398308ca90071b0e08c85f76dcb7685ea02e85208677f282084e6baf16d2609cb03955b6631aef201d766c08cf16ce06cb2daa52e94ae99e43426aa40ae9bdcba6984ae6d2ed16b1f4c13a806f83d1a706b1805ce302782300dfffa37822cf19cfd7606a59b58fbf834909a586c831cb601bad1b94cc4535d0eea6afa2aaf87d0a0036cc57ad0c24d4325cb478b80f6df39ce65b79c72d176d9345f8e4f64f7ffca120740aa4230eb4c52c2d3cf1f1dceb07c2122fbcadb7b00ced7a84054d539a5b4a49496c3e1b0ec763b3d3f3fd7e7cf9febdbd2d080b700d09248efddbba75757570ec0699a96d56a35574930e59c276ae45e5577f67d1cc7dd1d79fd78fbead9410614e08df56f08896340846b42e6981c4ceb80b0c1e3400d6919bdadefda7c6b19d7e119d2d8394df9d64e5a4745c64c7e81344ca3f14e35498160d5b71fbd926e7c25fade5c2740e9893e71d0a8bdd97eb01e47a7b05dbf451d62188a716d6af0be77f7de42020374015a05910131b366cfb0801ef72b9eacd597961a9f287e1f92607b739dbf7ef4e9d3cbcbcb2b543ea6bf83aa1e2abf4f06402d13f27ed8fcf99b12b18177d0800f1f3ed4fbf7efeb3ccf79bd5eeb6eb75b524a4bced94ccf29a5d4804f447600b622b253d5dd9d75fa6a79f6bba787ddf59206c1309ed08409158855134aaecc62c08be8282c4aaad590d0522ea2a63e5404ce00254bfc168ef469c2d780d56950d31e110d642dc6cc75e27cf3388dcb5e86023176d5832b812ce3fd46c3f5b1a91a988a32b1a1949fd28610f5763dd1d6e893d3d8e910f728d5e174aacf86d30cad7c6a7f445daaedb8d935d3864a3e1efb7ab5ac473f590056d29ad64b43820c022420eb822f1ffdf3ab71de3f06b017919dfd2178fc60bc3f0cc39c739e2b26f2b22cf9d5ab577afffefdb74ec203eff88aeac78f1febe5e5a5cef39c2f2f2f73ce7999e77956d5691cc769599683355655b700d600d6aaba129171358ec3fdf3f9d1b3dffebd8c7ff9311476e400002000494441547ffe6873e76e4a1060902ab94ad6b9997155acd12029805400e68e4c2a4ca9b635bdc536eb8e64be831a62deaa72b94291e89aeda0c63ba905f375e6a028ec35d3a8f718e3b3d6b5ddd67c8329f0ee6ee49b20346e44678d515aada9f5f9863266e2aa3c632a4e4c431eef97a28a96810167641768547963a6ba608b3d3f016a48239cda36464fd19441f73b2c1cf83314d9a71054edd53f14a8b3000c98b6d100ad115913744952f1fb7c61b242b2e0f35ffdd3ebeb275ffcee62b379a9aa5b11d902d856abce94ca1ec03ee77c50d5494466559d876158eeddbb97a769d2c78f1febdb22a0c03b00b056a2cf9f3fcfcbb2e49b9b1b0fc5ae56ab39e77c00b04f29ed5475adaa6b1400ae008caa3a0248ab714c3fbcd4cf9efff33f0c777efc970feedc7f304a1143c5475814651f0b852e95c97d2b3c29c06af679c9153425642da8f5507093d7da958f5a0f926106060e6302c3004ff4ab943bac8ea660e7afc5f3685b4433cd544ed81cb4cb9b029c06e548aa1f45199fb02d5b256b6e5368708dd67af8c8033086d4ee101cbdb73d8299b42d6313740a9875d8a21fd5076461e18d0e6de96d045c7b59f68a6b420da0b1968b809b11a55026097c9ecfe7fb50b4e4322ff8e2b7bfbff9e69f7ef3bbcb8bf3e7aa7a232237006e72ce3722725395cb36a5b4cb391f446452d529e73c8fe3b81c0e879c73cef33ce777f1ff80b7035001c8c3870fb54e496400f9eeddbbf3e170184564aa0dd9e79c5700d622b2aaf50ef62735137bbd5ae107327dfae2d3ff7998f7ffe947f7fff43fae501953442ae8142af5d5c9c6045a5f66620e3904a2293497337a82429060cb5a5320b1e1fc1c08abf74a65fedeff89cc11712d27354bc23f8d50743f6bdf230ddb33124a9f39552fda11ccd46b40c09893b4bddde1b80a860ed56375daf222bace263b7d7a408a34a21c57071b4f7fbf5fbda0552a7a6bea7d5a254503a64ccfea406649e46676f6d7eb9d75682b6daaaf2735d269bf2da5f17098f0f9af7ffdfad5a347ff7ce7f2e22b55bd01700de0da8058ffb639e79d88b8065cad56330033419757af5ee9c71f7fac0f1f3ed4bffddbbf6d6c8453c7f0a68b00f0f39fff1c7ff5577f25f33ccb7ebf97b3b33399a6495435e59c45449294230dc3900088882455959452326ea9aa5e9248be58a7edf4cde3ebafbffe7a335ede598deb554a43f5073d1a959c5866754ad413cebd332e61cabe93c921f43a6811f3a9f8d3f609b5cf44754a57363e6d80db4f89e7a0bde66d118148aafd1d90d25002025202024912d290fcf738260c43fd3e00c3008c03b01a80cb0bc1d9a6d580001ad0b0bd9873c634cf98e719392fc859b12c3137c61a3112da43ebd70568adf6f24f0ba698c0a271d07a57a7f9d4df80c46d0eb3d740d76ac0d68c67ed69ed15f2f1d228901a739054c0372d0b5e3c7f31ffcfffebff7e3a3ffffa7767ebd5d7006e44e44a44ae55f54a55af44e40ac01580ab6118ae73ce37c330ec72cefb6559f62272d8ed76b3aa2edbed76d96eb779b7db7d773e2099a1badfeff366b359d6ebf5bcdfefd3388e83880c3967333707551d0c7ca62534c471d1a297e7cbfa707db8faed7f7b7073f7c3fbeffde99f5f6ece2e2083949da43390b2202745aa21658b74997914992fd50fac9a4e446bb8db46d84cbf301b0b78b3ab8d620e5988bc328ac0fdbd22c085b44c5139622b31ccde6b5491564093df08d6b2a6515aa3b65e392a176aaa1acfa641da08d189834c320a6a345a88feb4791e2b4e75edead30fdab511b79f2f8f77db36341c34a61328e0621acede869719740442af9d0481a4122348f5d3923f004186e2f5cbd7fae477bfbf7efde4e9b30be4c7abb333d776a86033e089c8b5885c2fcbb2cd39ef524a3b11d98fe37898e7791ac771c9392ffbfd3effe4273fc900de690a0278070d08142df8e1871fca6ab5c27ebf97711c4544304d93acd76b0180799e25a5a20c73cea5e3712800ad1ac1972d8d439a37926fd2feeae6f9974fd241b139bbbc9b6c7e30a514e6036b3e39deb8a8f172c8ee37ed19f77ab1d08ecd67f5c05c4b02f6ce4003ea292d67eb1d852a3e4a1fabd7450409c9e79c920cb5af52c3e2e573304d9804e3983c5c3e540d3824603528ee5c08369bb0009ce88d4969882ada6e9a66cccb8c9c33349739b4cce0840901fb462389c03c84afb26aebe481d3bc080cf5fa3b539341e92672240de4ba7a23c01742c156dda4a1d0368df5d3572b0b969cf1e4d3cfe6dffdd7fffe34bf7cf968057d360cc315aae603e05a4f555fdb6f00d7552bde88c82ee7bc1f86e1b0dfefa794d234cfb36bbf478f1ee9cf7ef6b3b79a9fc03b6a4020b42080fcead5ab65b55a494a695e9645b4bcc1372dcb9244448661c0b22c18868117ed662db9749641300398c6719c8741a78de6eb9bc7bfbcfbe8d1af1f7cf09ffe8f0f3677ef8debcd99a46128ae5c16a461405eaaffb198d494ea33546d565e288ca2cf1222c432b8d6326da52248ec4b8af996cc4f06aaca381ec984fd0363ad92d5e3bf8ad603da7baa5da6a63943bdd44756c67221631ac146823460fbf4aaa55dd17143ac338d96b1e7b5a17a04106a7d595bcd1d1a9234e9ad9fa5e399c0c95acc41cf3e1d6bc49c91e5187c4607079fe771c677337e72ced8ef0ffaec8b2787cf7ff1cb1769b77d7a717efe0ac3e05366aa7a43be9f999cd7668e02b851d56d4a690760bf2ccb619ee7e9fcfc7c5ead564b4a6979f0e0c1b7d27ec03b6a4000625ab06a3a5c5f5fa3bea845c671949c33c6712c1255150642dbbedeb49ed0ae5252b7f806b0a494f26a1ca6cb55bada7ef578bf7dfd0a87651e57e717c3b01a4bb682d44c05486845937c8da6d37a9ee9101c19eb83a5bb6a9aca6047f5931a15523522a1f53c11b9d1a0540f282020c927818b2f38944fb149e14e030ea601a564140dc02055035e0a36ebce1503e32e10a10a2ccbe23ee0b264e49cb1e4dc81d3b6e7d7301ba8aeb0416eff6c0401bddc25fb7482a24c7f20c056ebf70975d4cd7c4938b8a04bd54a1ac8d7abd92c9acaf5dd7e8fa78f3e3f7cf1ebdfbe7cf6ab5f7d769ed293cd66f3ca229ca89a0d45d3bdae807b4dfedf752d7393733613743f0cc3a126a0ccd7d7d74b4a29bf78f142bf8df603de5d032a00f9e4934ff4e1c387f9830f3ec07ebf977bf7ee2daf5ebd92b3b333a9c471edb72c0ba46c5893534a594b36b9693f4eda3ef05f4a7276ef7cb59f9757afa667d7779f7ef5f8c3f30fffecee077ff2a3c1cc3324415a809c6bd4b4fa88453f574dc5230fd36e454ba9d6d8906bb10208689435fde2d301c576ac4cc0e69e871ca05e30ec3485d6fd5a242e99d91c8aaf9c16ab4f5138a8fcee573e3403a3f0f7bc344f7779a3ddcd1500a451b563970642b5528b7e561b03e6f86a735fa98b956e69236936d77e55fb5a927da63247114e031e3ce826025abd80327ef5bad5ffecf1d3e5d3fff1f01bd9efbf1aa12fef5d5eded434c91d809d886c5575ab65beef06c50cbd06709d52ba51d56b9b82c8396f8761d8cdf3bc5f96e5b0d96ca69b9b9bb9622003c8df7cf34d7e97b93f3ede55030228bee05ffff55fe3eeddbbf2fefbefe38b2fbec0fdfbf7f1faf56b9c9d9d41556dcf187b5fb69badf657c1e81a506245852d715a446419529a5749f6e7985e1ebe7972f5fc8bcf209bb3950c431a86512c9a6512cffc26110d8d08751fd002254e1df71d997dfaa335f7ecb76b5061df4f611154a510ac4558439b4694d523a143a44299f994528d880ec54f1c53a701139044b14ac5073cdb4874cb5a5f55500152986fcbbc60ee3460c99da41ccb06b727d0495f0324958e55a3995909a0e4ee66f6f7b26b3cf5952ee18b0271afd238a654693294a8b00c557826204bc67ebbd7675f3c997ef1777ffffcf9af7ff3e9e5387cbe1ad2cb21a5eb3a9767be9c69bd2b11795dffcc0734ad779d52bac939ef54759b73deaf56ab83aa5a42f69252ca1f7df451be7bf7ae3e78f0406bead9776e82dae1a6e883070ff0fefbef63b7db89aa62b7dbe9d9d9194404396748bb8d9b6d60b318e8ea775fc07bea9c9ddfacc6c3c50aafaf9f7d7ed8dd5c6377d80feb8b3bc3302677bc4d2a8659ca41103e629d8481b401149516bb57cc9094a84f0888febb82802a0cf3141e840199a0118419308899a3a99e97005d0fc0a198e4ab21e3ce65c2d9868de4dad30680a5af39674ccb82792e564a5eea3444ce8d668a7ac2f36bcd59f1df219cc237e44865690b69b70670065e5e2214a6a602be5221562ba41a6881fb79fbc3419f7cfad9f4d9af7efde2eb5fffe6b38de6c7e767672fa464b2dca0f87047e04b2959c0c5feae8761b8b67b9665d902e034b429a5344dd3346f369be5debd7b0b80fcf0e143333dbfd5f1ce41181b5384298a4f3ef924dfbb770fbffffdeff5c18307faead52bd435833a8e639ea6295bf025a5b488c82c22f3b22c93948c72cf21ad9ddb03380370262267aa7a066003609380cd7be7ebdd7c78f9cdeee98b7b4f5f7cf5e1f9831fdf7dffc14783a4d1b31c94cdd245e05b966692ecd5242c5315c1b60a508e4c027b832e8291c8c64a314561a7dccc1494977b92795ad1687b87aaa600086abb6cd912f96276ed7830e25eb66e4f738152034b63cbfa38869809efaab55c0d12304f7d6a808f7d3af3e3a0e4d33500d4c6d70bf0abc7ca0c786e727a064bc98c5954f1f59367f977fff00fcf8769fe6a547d79797e7683921a69e6e6be065bf86fdbfded10592edb9cf3ce78721cc7c3b22c072979cf0ebe799e332af8c8f4fcfe4c503bcc147df8f0214c139a399a73d6f57aadd59c512dab85dde4d4b2678c25749b96b3058e33cacafa66bd15481b0e49e6cd9876177a78b9ffea8beb175f3d03d2b092f52a9529495e5a82462bba35a800a4db33d8101445e33c10f7f7e7c1a6289b9ce697c4a4b407615042e3a6ed869420698c808c540d98c4afdb24fc901286b14c412400ab9471e7e25d35600148998258c8fc2c7f2ea0d449411da65952452c8245988bacf5b4065db42e82d5dc4e253818cd2734cba4fada65017724eba7b1264c4b01e77ebfd7278f3e3ffcea3fff976fbefecdaf7f7f77bdfe7c14799952ba42d55ea0000bcde9799025a574655a11d5efcb39dfa0e67cae56ab3d4a9ae561b55a4daa3a33f8f6fb7d7ef4e8917ef2c927dfdaf4b4e35f0440e0761002d0cbcb4bdd6eb7b665b7e69c751cc7bc2c0b6fe0e440eca7272ae8e61e885606619aee2f64797df5d5d3c3feea35f6d334ae2fef0eb1ced09c7432512101beeab7358729085bb663ff2a5dac7ac6ebab5ad5e3356e77aa83ce7d47379193b7cd4cd094862a34c807ac7ea1812f256048c50415015643c6ddce040ddfacfc524255ce19f31c51d0997c400e96783dd5242c5aac3329c9ac6db55e6b5296f57945671dad465775da59f68a485dae564168992b0a60bf3be0c9a79f1d1efde2972fbefea7df7d76067d7c7176f6524b9ee68dd44973d0340208783967cb68315fef2655ffb09a9b5bcb7251d5434ae930cff3b4dd6ee78b8b8b39a594efddbbb7bc78f1422de8f22f051ff007001028207cf8f0a1fef4a73f05505ee4b9d96c0094f7654fd3a42925039f6bc31a19357fd0b51ff981965f37a305a2970369c5f3d570332cbbebf9e557375f3f7d32625caf3717772455d32555ad18a64c1b3c11028b9960e6ff0008901ea565d9b56ec90fc89fa4cf02c2fae7df93832f7cbf98a228c093a20187131a7058ded107ac9a2a570d38170db8649b8630139d2ca9b0409189c51a4da761aada5687bce51f03bbc964a96d72535350825135b06293e965ca41b0a8e2cbc74f975ffedd7ffefafaf1d3cf647bf3f8e26cf3cd90d235087855a339e86a90e52ae7ecda4e556d42fd1a25d97a9b73de8ee3b81b86615fc13789c8619aa6691886f9f2f27279f6ecd9f2d1471f65007fb0e6b3e30f02603de4e73fff397efad39fe2d1a34758ad56f8e10f7fa8cf9e3d5333491988160135bf7059966518069ea23053d426eb79cb8b59ead20f90c90a6019539a36e3b0bb90f9c5f5934fafbe7efa4592b3f395ac86340c83142d12011b0bd4146c68d55e35e4efe6680f466d3619f24f50a0c7b809f089776dcc5003643b0f380c43354363be3336b2b20c984e034231a405772f069c9ddda20155c15b3e2e8be5812e98e6c9cd50cd1947a95e30ff2dcc4d8b583613e89449933d92991ba03693fd165c2964707353ea9c9e6b3c55ecf7077dfce8b3c32ffeeeef9f3fffcd3ffdfece387c3e2679318ea3074a087447e6a6962c169bcfb33237289a6fabaabb711ccd2fdc4b49b49e72cebed66f9a26df8ef0eeddbb6a3edf1f0a3ee0bb01208030491f3d7a84070f1e60bbdd2a00ddeff71886c1f64bccfbfddea722c671f4ed2d6c597fd5820d10d182913f1d84a615534acbf9d97abfd1f9f5f5d34787edeb2b4c79596d2e2f873494eea6643e580dab24c0b41800886bb8fa291d8d1b4dd81dac1c3d7c534168fe8db43e604a0386a1fca51a090d00160d98868441aa361ccb348440310e19772f5b00361ad04dd0722e2f650a62aa73b5e103b65aad6057da737aecc7d9560f0af53a8ecc50d77a0092561f0f359a293e99ce51cded6e87278f3e3f7cfe8b5f7ff3ec37bf7974067c71be59bf04f977e84ccc9c3383ee1ad5d4cc395fe31878db6118b6f33cef6c7ae170381cd6ebf5b4dbede66118e6f57a3da794724a29eff7fbfccd37dfe4070f1ee8cf7ef633fdf9cf7f6ea3fd071ddf190081d624356df8f1c71febb367cfb46e58633e611ec7d18232791cc7655916dfe2908188135a51ca3aacb9d38a8d793a0cc37cb61a6f86797b35bffee6faeb2f9f8c586d56671797093538136669f9539f5ae08000aaab18a6199ba747d14969bf844274dd571810a9c98649c38021714e680461520dbb0f43c23008c6bab11554b11a9623009ef601cb3ce0bc54f3735e30d5a908d37e360fd868c1135a0c5c9623997d808516f99a2909013058df4cf3c12d90acc093cf1e2fbff9fffedb57578f1f3fc276fbc5c566f3c24c4d747e1e6b39fb9d52ba4a295de79cafb5a49735c003b0ab192d87d56ab51791c33ccf87f57a3d5f5d5d39f8a6695ade7ffffd7c7e7ede9b9c4ce63fe8f84e01580f39a50dcd371cc751cd2c358d883241bf58949481c881189006c469ad68c19b05c587cc434ad36690dd852c2fae3fffa7eb6fbe7c227276b192614cc3982436e2b1c00de0b310a8e6a9055d383c21356e574dbbd07aa61803a0e10302313728eeebc93054df6e8c73b694c6e7feba64ec04087289825e0e383f23c0d74705006d6d9e9634b4692e6668d5804bce58f2d2693a10e8aa795a35dc52cfd91e2cb615c4a24b6c0b612b1e38b85253c7d25880871ad5ccc8d86e77faf4f32ff6fff8fffcbf5f3fffed6f7f7f390e9f8f29bde87d3c54a0996f27b154e8ba4640af50d6f159f9238d378ee37eb55aed55f530cff361188669bbddceaa3ae79c97ed76bb7cf4d147965af69d6b3d3ebe0f000238d6867ff6677f66ef4bd3711c75b3d9e4c3e1a039e77c767696f7fb7d169123209a69daf97d3d187d5b0006a3455a61c19ab3cdfe0cf3eb574f3edfef6eae649ae7717da7464d018f407ac434192269f29ee8df0450abe6d43ac5d19c2e97604e6009c004d0a46abe61185d03a60e801e80e97cc0553541cf4903fa234f444197aa01e7b900d10098974c4bbe626544ec28cde702744a26a895f7e7db3e3f3e895e4c4ed82a51116c777b7dfccf9f1e1efffab7df3cfbe52f1f9da7f4f8fcecec05cadcdc35075740c033b049244b37fe9d454501ecd8d4dc6c36873701ef3ffc87ff602b1af25ffcc55fe4070f1ee0bbd67a7cf463f67d1c02007ff3377f230f1f3e944f3ef94400a4e7cf9fcb388e691886b4dfef87799e878b8b8b61bbdd8ec3308c29a531a534aaea6a9ee73580554a690d6023226bd4097aad93f536717fea93cbd5ef9bc3b25cec16bdb71d2e3f7affcffef2eebd1ffc7028db2594ad2d34c319b27cd6fc52dfb2b09445cd3195eabb01f1bd0479525d0ec3737c8294462419300c2386718571b5c67a7d86b1823016e10e188701abd580711cb01a13562b603502d01917e3848f3fdce083f793c90058d033d7dd04725eea3c5cc66eb7c3cd768bdd6e8ffd61c2613a14404e738d882e8d2fd746314debe9b1a989f2f0aceac126b0401bca4955db1c17f8f2f32f96470ffff16bd9edbe1a8017ab71bc414dca90b21da00747b46ef62575a32ffb446c9cb4b7ed516cfa20a5341d0e874944a6f57a3de79ce76559e6711c97ebebeb651c479fcffbc94f7e921f3f7eac9ccff97d02af01c7bfd2f1ad81b8d96c869cf3ca80a8aaab6559d629a535831004c66f034411592fcb72f6fce5ebf7f2e50f1ebcf7e7ffeb7b9b3bef8f9bf5ba88690262ce802eeae1f86279fa0456015cdd64b200938087f0ebc427d9070c6944aa005c8d1b6c366718c7554cba0f03c6a17c36005c9795f0c80bce577bfce8c30d3e787f40750b8f00a8d9822d0b6eb65b6c777becb63becf6fb3a2551f3424d1be6e556a039206dfe0f68229b25275a227dacfa7d8a124ddd6db7fafce9b3fd67bff8c5cbf9d5ab2fdfbb7bf71ba52d2d19783dd87ad021a296b641d2bf1be035a0f8573ebe1510e7791e524ae3e17018d7ebf5ea141073ce6b009b945203328974b673fabee9c12822eb699ace5eeee61fc8bd1ffee0fc077f72f7de477fba4a755b0acd85831c887527370361b13a6d85450db09876440daf83c0270992866a728e18c715c60ac0d57a5da3a20963fd1c86847135623d0e1847c17a25184740f38cf3718f1f7f74860fde1f6230f3090da819cb32e3fae606dbed0ebbdd1ebbdd1ed33cc7e25c5f9ab434114e363dfb200d5082276652f2be2b906a352460bbddeb978f3e3b7cf3f8e9cbeb675f3e3d1fc79735fc6fa988f6fd56e0096d0d68c043d5782853070729fb14cd22322dcb320fc3b0bc0d784059effaaf093c3bfe2d00d83cfb6d404c29a5711cc71e88d3348dc330ac557595525aabeac680880e8c6fd38af69773de4cd37c71b3e0bddd78e74feeffe47fb97be7fe0f93a562430579a969a559a10b22c4afb6115489e078fab60c3eef57022c43354107a4346218561580c504dd9c9d97e98814da6f18125663d58243c27a5df683d165c2d9ea801f7d78861f7c30f8ae1b75f3f0dacea2015533a679c2ebab2bec767b6cb77becf6fb32255101b85050a637398f0048c908c5f266bfb9ce19a208ad2f1f3f597effdfffe1ab34cf5f8dc08bf538de68d95bd3b7f8c33b00cfb45faafbd0aaaae768da9f69bb611896799e678bb0ffb168bcfef8b704a01d47407cfcf8b16c369bd403719ee7611cc7d18068a629aa7f98525a2fcb72649aa6946e05e0292002d82ccbb279f1eae6fde9f2fd0f3ff88fffdb7b67f7de1f56ebb538d814058c5981ac95d96b77d834c500a0443c7df25d6cd29d7cc00ac0f3f34b8ce3bacef795e868f101478c63f10bd723308e409e0f381bf7f8d19f9ce3871f8c0500116fa9e67386ea829c171cf607bc7cfdba0070b7c37e7fc0344f98a705d332232f0b96acc85a3560ee8057810528e5d7d6359a21a3ca04fa76a75f3d79ba7ff48fbf7aa157af9fdebd73f912c789f7acd5b6a7cc4ca55dd651c1f726e09d9f9fcf373737cb29e0a14b9cfeb7049e1d7f0c00b4e3ad404c29b9793a160a0f55037e27400499a728da733d4dd3f9ebc3f243bdfbc3fb173ffcf8ee7b0f7ebc4a43aa93d40072ab116b4657ec1ca629325f7cee2fb9064cc3807158631857d8accf71767e89f5faac989dc3e826e86a356265266805e07cd8e16c75c08f3fbac00fef9715213ee16e2f9dca8a9c172ccb849b9b1b5c5d5d635b01681a709e0d80d9b35a9a08a84dbf7802424436cbeff2e00cc56e574ccdaf3fffe2e5feabaf9e6c56ab97abd58a97f334e07a9bb6c3bf107896305d5f92f947073c3bbeb769887fe9f1a6e90bd014469db8cfcbb22c355bc1ded5e6d31796e296529a4564aeab2f4e4de63713fd368f089fd01f6ed2e1fad5f4f2f9cd575f7eb94aebb3f5e6e24212c4a72e22e207d4997bd8f44559fea48db88b55169c135af6bd298198a15d0dd1ac07042429f23c63c4843b972b5c5e0c48b6520a3e2b52fd34c53ccf2502badbe3304d384c13a669c2bc2c98b3ad8c585abf0f013e9b1f95c11207520458a4cc0d3ef9fcf1f2cbbffbfb2f6f9e7ef928ed765f9c6d36df0cc37053a7107c912bba7431feb472888c17df0a621886ed300c3b00fb6118f687c3613a3b3b3b4cd334a9ea7c7e7eee89183681fef0e1c3fce0c103b5e984ef632eef0f39fe9834607f7c2b8db82c8b9ba716a801b01ac77165dad0a63150fcbd33bc452ba28b9aa29aa62faf761f4c17ef7d78ffcffff7f73677de1b56ebb5f806c140d938aa066c727d7992dad6173e4d3140c4ccd0e207aec63536679738bfb883b3cd39c671f4486889800e588d82710524641c76d7381b0ff8f19fdcc1470f3618ab3835f3b3604f31cf13ae6f6ef0faea0aafafaeb1dbedb03f1c70d84f9896920f3a2f0b4dc62b3cc720e95154d3822e5915fbdd4e9f7df164ffe817bf7ca137374fee9e9f37a6264e68bb379d4304556c5a611ac7d1832b2925df89da34de66b37173b3d7787f4cdaeed4f147a701fbe34d1af1f9f3e7f9eeddbbbad96c326a8adb3ccf9935a2882c35f56dae1936963bea89dddaa5b5e196f436d45cd38bf3cd76b5ec5fbf7af2f97e7b7d25f3b2ac361797298da9ba45b170d4b26b5cd2b119479f365f36d494b4611cb15a8d94fd525f6693e093f0f374c09826dcb9b3c69d8b15c61475823e0fd384ed6e5ba29ffb1d0e87098769f664ec252f584e0558869aaee72f5685bf63e1e666ab8ffff9d3fde7bff8d5f32f7ffd9b4f2f52fafc6c1db99ad2a58bf59f1a5bfd5d51d91b2d5b3f6c534adb611876c330eca669da0fc37050d5c3300c53dd04d7351ea78c99c6fbbe3257beebe38f1e80769c02a2257c3f7ffe3cabaabd12aa01a288e46a8a360b8109880b0351cb9603b781d2cae66118e68bcd7893f6d7afa757cf6f9e7ff5e55ad667abcde58588e8d1f6780e42294ba0e2c5155a3717424cdca784711cb15e6fdc141dc701a96e45318e0090311f764832e1e27c85f7eeaeb04a948e564dc69c81edb64c3d6c7737d8ed8b095a52d166cc758e5075713f2fd2c6783d5e697256c5e79f7e36ffe6bffcd767d78f9f7c6ab99a8952c670622d5effc9a0c309e0a594f6063c11390cc330e59ca7799e1be06db7dbe5c30f3ffc77073c3bfedd00d08edb80b8d96c701b105157e3dbe25f9c00a27689df6fd28a06441199c77198cec6b43dcbfbe72f3efbedf58bafbf4ae3e67c2de390d2107e5bf2b9315b15919d430a6c42330ee9ff6fefdc96dbba8e34fcf73a6d6c82a22c66e20992aadce4410a35faf500000be649444154fd407e8fa92433732b576cc5924c533475202910fbb80e9d8bbd7a6303042d5396ad13fe2a1450a4c05209fad8bd5677ffbd3e0f0eb79f66dd886d8608187c40df55194087c3b9c3ccac01940279dbb5b85eadb0aa5668ba0e4ddba10f1e3ef408290dfd9b48e32d26c4ec4ac053c32d6853377cf6f4a4fde6efff7c79f5ef278f0fadf9d11089d9d1ce68c76b5bf7f17b4454a5942aca2e647701cf397703bc939313fe18c1137d74008a6e0371b158f0e9e9290b884dd38cbda6b80544ac7b47c73e53adf59dd253a5549897b3c6c5f67af9fcc7bea91af810edecf09e92c2f4e8dc261736c016941823e7e0053aa4a226a7a3021f81e1fb1ec137502aa02c2c0e0e0a1c38958b1eb914e003aabac1f2fa1a4ddba2e93af83e971e327c32032983b01be73c4568aa86cf9e9e74a7df3d7af5e2bb4727074a3d2bcbd9157e660876c7b3ec59a898b9524a3594b70de53360638cd900cf7bdfabc171fa93044ff4d10228da0651a62f044419839a82688c899c576df3e0572ab7a563d3b7002620226f43a58965c6245a8ef3885a6b3fb3a656edeababb7ad95ebe7a69b52bad2b4b62e2e1a672ac9de59b52494bc75bfde16b72e6535ac1b9a1458d1423a580103a04df80c8c33983b22c5014063ab77db521a26a5a5c5743f4abdb067dd7a1f739ede475d49341d875019d1119387d7a12befdbfff7fb1caa9e67c36bb5493b1a0378137854edea3b5de180902d0e6b3dd0678655906194bfb14c1137df4008aee02a28c411963a2d65a2e6bb67d6ac6f2043347adf5180105c4ede8384d4f8dd1beb4ba2e627b71f9c3a36af9fa42ab62669535a4cd30a1afc5d979689e818c39c943b6062bb51e1a6624a4e4916283101b100538a7e19c85b116018cda0f2d67ab6a85e56a85555d0d972f61283b48ca49f982856818184ecc68ea9a7f3a39edbef99f7f3cbf7cfcf8fb7bd69e5aadaf647f02ee001e26a504ad7503a08931cab2cb566e3b8d317e1bbc10c2270d9ee8930150f44b4064e614634cceb9f1b246408c3146d976a3268651929ee6dfca1b338ab7810820ea9c9a9ad0be5e9e9ff66d55516436c57cae87742fafd1a2751d91f3eca1ac6b1bd2576070f28f60ee916207660f5209d62a18330ce9fae0d1761d5619bcbaadd1340d7c6eb64eb94639d6f0380d13e855c3a74f9e743f7efbddab57df7effb4547476309b8db79abc36abddaedb5dcbd7053c95cf85cc7c033c22ea94521d33f7c6189f52f29f2378a24f0e40d1cf812813facc9c64305840b4d626ef7d64e61144ac6d2fee0c627e1d8dd6a1b4baa6b6ba6e2f5eb497af5e5853cead9dcd86e39f5af751aa5cdc568a41f95969060d6b3400eec1f0000214318c1ecc7a1347f4be43d7b5a89a1a7553a3699ba1dcc029d7efd6a96642424809cf7e380dfffac73f9f5767e727d4b6cf0e66c5986a4ec0dbb86401207b142a5e0fc756f992a511f84208ed3678defbd163f373054ff4c902287a1b108d311b205a6ba767bd1bdea52abbb7613788e3795211456bb49f19aa8ad45e5e9e3caa5f5f5e28539496ac21ad3529c5b9ee060069887af9313a73500451845209da483374444c1e5ddfa26e6a544d85ae6fd0877e9cd71b3ae78676b3aa6ef8d9e3a7ed377fffdfe7cbc78fff3db7f6d419fd5a52cd5de051f6d39c463b010f93ae15adb56c9295e6e90df09c7341c07bfefc7930c6a4cf0d3cd187dc09f3aeb5d15903000f1e3c508bc5829e3c79a2a4c32684a0e7f3b90e2168190ceefbde30b375ced994924d29ed9c47a4dc4533eda8d9eeb4d97e4fd775e575a03faaa33ffce1e0cb3fdfbbffe5c21aadc7f6b46114c9625614289c83731685934909c0668326d915c12920440fdf770831e459c0a133273263b5bce6f393d3f6f2d94f57edab57e7f3a186b7d177493fd3a7298ed1dbef4929f57963d0d8ab99520a4325288c63414551c494527af1e2453c3a3ae2aeeb92ac74fe183a57deb53e270045238800f0f0e1437a13888373a21e5bdc7e0d88d3ef039b63501dd4fd5a977fbaffd7bfdd7ff0c7ffd666a8b8432b8562e6e0ac813316d60e83b9c6106c8e968a184479f2210e10a698c696b26a55e3ecf10ffdcba73ffc447dff4ac5b474ced67211429326e8770d9ef73e7cf1c517e9eaea2a765d97f6e0adf5390228fa45206aadd574fa4240544a19adb57b1388d89a49e4759fe9b4dfb42022c7cc8e998bebbabd5793fbf2de5ffe7a3c7ff05f4ecd66da192dceec3034ecb050601845309660ad8232c89b838642baef3d57d7b57ff6e8fbeaeaf4ece5a131cf9d730d809e86dd1ce384c26d004a2a49930158fc02f0cab20c5555c52978328bb7076fadcf1940d16f02a20c096f47bd1d206e43eb98d901b0cb5573d46b7b5fdfbbff852a0f0ecdfc5e5138ab35080801a9ef409c501c3814a5859b5924620e5d1f9aabd755f3fa7a1556d59549e9b270aec650cbecb761ba2ddaedc1fbedb50770ad1b20ca94fe2f05313f3b0151a62fb641c45654c46e082d009b989d0fc185440781e8a00df1908db91799add25a6b4d4484e48c8a08a15629568536abd4fbca10b55aeb4e2925e0f5bce5bf92235c378d76d8317d7e1b784a29dfb66ddc83f776baeb7ab24f5932ac0900f8eaabaf0040fee32464108f8f8f292f9989ccac01c4ec416272eb9ac790e2b918a323a25e29256643058602f40c8307ca8c886e8c3c0170001c11590598997316c06b66d6f79d5644a4989988f2f42133534ccc20266d8786827c730bc0e7c77413f10d1f1666eeb4d66d8c7163081603a83ec3eb651c88887cdff7b12ccb90528ad6da04209e9f9f8fe001e0cbcb4b5e2c16fcf5d75fefc1dba13d8037359d9aa65d205e5c5c24638cba0dc41c714610f3e54daf94eab26f4d9b23618b21e26d444089a0120599d91091c15036d20088060f4464109959e6f3a558380e196fac02df4e29e597434aa94d2975f9cf754aa931da01f04aa911be3d78ef4efb14f4cdfad9d4f4f8f898baaed3929a6e7b9bcacd29335badf5868b1bb63c4ee5eb44246749cbcc36c367006866d69938358d8090325f0690d676fe1ef9ec870cd7d605ccc6eb2978f210f0a6a9e62e87b17daa7977ed01bc9b681788e2e476171073742b04c8a9c5a2c0975f1b0026a7b83a473e79dc8880cc9c68e84795253792827a661ebd33e519efc053730fdedb6b0fe0dbe99d80886c22c56b6b453b8d7c006e443f00bbcf80449c5262356c9e1a53509ad8f7e78b98d13f5329d5c718fd1ebcf7a73d806faf5f9d9aee02526b6d99d9a6948c52ca6238f38dd16f9282128b090d001ad637c9eec58d28388d84f27a7ab6db83f7feb407f0d7ebadcb176a73ff855cb4582232f288311aadb51eccdf9406a0043ea5d4f8f94da21f67f0d2240d8d945de00440a9ddedc17bbfdadf82fe7add5abe78f0e0012f168b340131755d97b4d60a40f4de870c6220222d407aef8d52caa494b41e767a6ba5946266ad94a294929ac2277f0f3907a694362e63f21662d90214acb521c6188ba20829a550d7752ccb32745d17cbb24c00625555fb5bcddf41fb08f8eef556bda64551e818a316f0524ada5aabe5351129225229a5717c576b3d7e7e3146564a3186994636c6a4e9e661efbdf8e004f14c9d46bc7d01fdfd681f01dfbd7645c498419c46c4d4755dcc53174a29a5534a1ac3394dcb4329a542084a0034c6508c91524ae31930c6c8c01002b5d64c79f3704e491333c7a22862dff732e39888284ac43b3a3a4ae7e7e7319b5925ec23deefa67d04fcedb5111101a8b3b333dad56b2aca8b6846e88848755da78c3144442ac6480060cc608316426000d05a730881b5d69cc14bcc9c445aeb610f788c697ac6db4f27bc3fed23e06fafed88982e2f2f69b15850511463443c3e3e268130841090cb0d4a29d5f73d196394f79e9c73d3db4f0111de7bb16264ad35f77dcff3f93cb56dcb02e1d1d1514a29a5e572c95555897d633a3e3ee6870f1fee23de7bd03e02fefeda796b2ab6fbcbe59204c6baaee9f0f0909aa621a58619f9beef69361b16c2f77d3f7e7ece390680b66dd939275ea95c96250b7412ed8e8f8f797fc6fb30b407f0fd69fcb79f4ee90b8c8bc5822e2e2e4880acaa8a00e0e8e88800a0aeeb1b9fddc1c10103c072b9e4f97c2e16feb80d3ae0c3da14f4396a0fe087a10d18812132020390007076764600b0582cdef899e5d201168b05e79fc5f9676d4307ecc17bafda03f8e1eac66733b9c879a3b62013ed61fbc0f41f8edd849b4be9561c0000000049454e44ae426082', 'OE13G', 'S/N', 'S38B', 'Edificio azul', '3030160', '0987328457', 'Quito', NULL);
INSERT INTO public.instituciones VALUES (1, 'Salud Integral para Todos', '2006-05-10', 'info@saludintegral.com.ec', 'https://www.facebook.com/saludintegralfloresta', false, true, 'Oftalmólogo · Dentista y consultorio dental', NULL, NULL, NULL, NULL, '\x89504e470d0a1a0a0000000d4948445200000380000001480806000000011090a7000064304944415478daed9d897b54c799eef90b6e666eb689013b9edc19034eeccccdb521ce3233b101c7d91c038e3349ec043b7612c7011c4fe238361867c13688c59855625f24b123041202b16b414802218110ab00b37a258941129ea7eef9dab4225add7dea9c535f559d73dedff3bccf8c43eb54d5575fd5f9deaed3dd3d7a0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000001007aa5edf25dc84280100000000000080c51c58b5bfbcda316f2b1e5e9ad094beaf28d5dcbb6724aebbfdcf9bc5de057b601201000000000000401749b3a7dae87915f5a11aa78600000000000000a00e3ae12b7e729578cd315db66ae69d93c5a6dfad871904002887de68f22a440d00000000a1830c95cda62f93e6dd3d038f8a868c20734d79dab2b5e5638822b02d3f492b1f5e1a8bfcfce0d40cd151f72dd156fe515fba7afc15ecd98aa198fa9d0f9a4b9a53441100007af4f0bb97b66dbf597434fc507c7076a9ddfb299df651c11246e397ee5410efc447bfc0ee6a061149606b7ed2be1a4993d1f29c68dbfcd1c0ba7a0c0650f9dc38310d3c37db6e86110400c0002ab8cfb5effabcf89f0bc563ad1bdcd2fbe745c2f8c108c6b3c04e0a730d6ccd4fda8ba21417ba99a9b829c2005a6c00af89dec14644010030800aee77079eb4633fb5fdf37daa440617291c7d0348c2e74181adf91915134827434a6f883080561bc0c4bbd7d55fc51c01006000c36e02e97329b39c6264aa5394c449bb7142641d1cf34c8f3323b2c0c6fccc0ff99b51aacd1f0c60380ca055ef5c030040880d60623f6d794eff7ebaf977ebc5eb4e211257ad8ae8e771c20ac71ccf8ed8e376205af919d637a2125ff4c2712384010c85012459f91916000008990124691d44c1fdf3626dfe922ac023a1912eb0499bf12828c01b14caf8e0cc52d1b6e9a32c82016432800c73d5bef3f3982b0040bc0c20d3bdafa3f65b7af653983f98c03819409c02029bf3735fc87eae863e030603080348c22920000006508d60fe600251603308d105b6e667d81e456f2bfb98e0d2d5a358abca0da01353b6f93af41ce60b00101f03c878fffbe04c3edf7ebada2934a6390507945eabf19940a370ce6dd84e59407cf233374427d47483820184014caa63cfb7315f000018409bef7fbbfebc594c778a0d28bb284e48713370ce6b0dbef515589c9fa132131b3fc62618c070cd59fb8e7fc37c0100e2630039ef7fcd0c4f54d04f3dc0dcc98be2853487010400061006100630bb106100000c607075d4303c5191d77f8a98deef554852142fa4b981029b714e610081cdf90933010308030800003080ca28ffdd06983a1f2af9e52adcd860000180018401840184010400807019c0194e7101f913525d2f9c73b9070610589c9f6189c107475f15ed1b3fce26ba3e322d5c7386080300e202e75eda51f31d75fbe9da47f261e40268f13d3371738301040006f01a578fbc2ada4a3fc126ba3e322d5c7386080300e202e75eda5173bfbafdd40603458f5252f1ed5680275f43af5ff6ddf9d698c0e6558deb91f2308000c000c200c200c20002006000ad36805b7eb741cc740a0b9d9adb7f8aa076cf28fc16cd8a71e562896324758f25a9253805d406e73cc200029bf3335466a2e4136c82010cdf9c21c20080d81840c6bdb463b7220348664ca7f1d351609b30b533f15940184000600061006100610001003080361bc096558deb673945850e153d92affd06b0f197ab84aef19136e21b41b5c03987b53080c0e2fc84998001840104000018c05018a4adbfdb606cf3a7c74ce7f59fa2cd0422ed610001f213061006100610061000000368a501d4618c4c9abfaed067f47418c0167c190c0c20407ec200c200c200c200020060006d3480b39d828253b63d1239df31bc711b7314e19c3f184060737e86c64c1c1e2fda367c924d747d645ab8e60c110600c4c60032eea51dd5010d60e3825a91eb14145c5ae0982d1b2785fac539ee5c3c06ca0ee7dcd5c100028bf313660206100610000060007d5335ae5cc4b190e636beb61669341fa4e247f23b95cd0ce7df33f3bad796fd7295485e030610d80ccde1b6df6de8ccdd74f39c9ad72a7f920606301e06f07fce973c4bfdb8daf054e2864c6a2bfb97ebfad9bef50b9dffd651f748a2df574fe75f82010c079d737cf0f9ce796cdff9b5ac855952f437f4b7740decca1ff2c189598998d05ae88ca7b346d2c6d3594bd7c593e6016ffc44fe4da8447e7499f78cf99126473af7580bd69dd50630536114874265e577e747d6fc52219b347adc463769149345340c2030f5a68eaa354df9bc2de06f94c2005e3313eb3fc926dd8560c2081c704c40d5fd4ac7d1bee50b090379f5947953c83d67a13028c7677d38c71bff852506641e298fe2600a13b1ac7d2491e35c39457305931d5eb3c7b9d6ae93d386ee5ce11c0f8d2550e70a06ce1479b7becaa262033ff9e015aeb193363da5f7738054ac529b0bfb4f119ce3f232ff4d0b6b4518e7ae6edaf50690c6c2d99eadeb83becc68fb731f9ea2a9882347ffb8e786f6483fe38863be44d10052019b38ede12e4e520da16310a23a679ea5c11853bc394d4ad642ce31483698ff28c4325918d3ba85bd4a991727c712ebe9da9b5849659b2b9637d11a9ed263f864df8c61bc8f586d00e738c50497eaa7d97f8ab2de291eb9c6bffa81f95ac64f715ee4983eceb954110bd5df8caa337739f3648e85053d19771539c5b507502e153ac64c771e7b194f9cf22563c1d1e29889e24fb289aecf552825de9566ecbbacdacbbfa0b598e59e333f0a5ce884609e3b737adf53a1342e36c632993b5132d7d226abe5dac95ae9bf048a9fd237d29cbdccb6fce0de6badde17398b14959fa7e184d33cc5ddf8a58afaabca14c000daffa6088701a437134ce630194f18c0681a407a673c68c11476731007034886c0f66294cbf472183fdb639990b3aec36aaea54f5d777c4d79dc94ec2716efa9dc7baddd06b09f535030292c0b874e13c21403ea6fc2f831ce1db7124630e0e7ec38fb97dab784298ac15a59fdddf9ac710c42f5b872ab72d86d6cd85b9366e29fd8a4ca005eddf72bd67eaa5647d5774558e7ccf478e95a98eb78c6b273df70d67b244c1fed5ba5ffca1aab20fd6b2fff7fa1cc0f95f718abf786b94e31c1a5302d2432556188c1e65fae129c73a65b6bbeebff3159ce7ea516f71b1c0318f5b5c23146550690f2c4c6fcdd90e573ced85bed3780570fbc10eee284a1908daa01b4715c26deec886a8ec4c5087e78e2aacf58c5715f4d15c51b06d0a3547fe64b07cbee99696da1a6ba6fb66871ff29be720506501d4d0b6ab5c4d10f941f617c130306f05aa1b8ee9fd8e4b728fee0f86cd156f2afac7dd326671c2acd01f79cf9514765b042a77dfbdd91986b1a87f1533f672e22b16ebaee234d2f8850eca506f6acb8ae3555f71b9bf7c51ef36e1d2fb8b4f3b90da13c663fec9891f2a7562b89818afed0672997f47f4d70ce950dda3dae5cd892bb7ba7555cd79792470a58c76e3ae7b9f22b358e51cdfbb569bef029caf912660318c50256a539b0d1005eddebffa4263246bfabe13f69e64b4da25cdc9b8cabcd6f56c57aad29d88b626b00a978eb01029bd1a81b3fbf6f1ac000da9f637e0d6018f33ed504c200da6500a9b88b7c81a2a088b5d200fa30fa89f98ef05c9331d0b996e350dc2b29aa2366b8911fc14ca0d5b93adf292638e5f55407e89d1f1b256b1a74f6810c20677b26738c4ebb4dcf652a4bfbbf16cadca53cd1919fa1318087c68b2b45ffc426babed4a95fd30bacfdb04d414c20f79cf9d1ff9c2f1de1d5fcc5619e3bf6eaf90cdb15a7b88fd3faa1f14679dff422e447b0b5c7d987f68a80067081534c70eb48083f0b6803f94e11ac637e6c94cc4f8870b6bf2fc5b8943a853d677b26f36cc5c059dae21887bcafb9f6a65754f3256c06906ed4712b4e829840eb0ca0c7623c2ee64f97096cdb76772cd74fd03752a2126f98bf2cf971ccfd14de6a03a8abd88209b4a7280f83f2251e1f86015483ce38ba51f4c0fcc8bc81010348666282b8b2f6536ca2eb676ddfb94173b66fbbbc9e9ce99833afeaa81fe129dfaf6cb82576f32c5388fa7a0471d703b15e3f89d86a348136e62ef2c37f7c1273cad836c53f50c27117b6414f03e2089d20c4d9fc25b5cbe5f3803080d132805b9e5a1da937306000cd1bc0b81727545086da007aec7f1ccd5f10b31fa63702a214db30e52ef2239809b3da00563845f642a7a0d0a58d5d3e2303d2a3733e6c97a938a51a17ca5b53e30c73bec91ac0830b6b91ef21c997b018c0389b812045824d859d97c2bb6debdd30fb2a0d09d60e5b6cc3146be447b05362ab0d608353a02d720a0addaa08e94f447053fcc07c23f361abb63db53a639e70b6db90625cca1c03c8d99ec99cd319c74c14f47f0df91e927c098301a4c7065198f83352b618402f8fdee13442514108339d511493389dfc65338078f4332537caee10a13480dc05208ca03cf4394914b7f2c52e0c60340c20776c6100e367005194c81728d69929a720f6fac81de658dde701e3feb9d9ac9f476d54fb83f161784a01ebcdde93d2d01bc0a4560d9c259a17d6c6da0ce2f42fbd32e5050c60f80d207d590a723cc206b0d931136b6e60135dbffbbbd34358db0cab3e682db860c39c65d4fa5b44c7fed19e73bb6deb3d98df9438063225cedf238e99a56a6f0c4bde767bbaa26e04f2208d282e69d713639b74af0b9c88f498dd62a7a8b045ab1d33581bc3df0f341d773239a9b2211fca327c6e94b3cd740690b3bda8e69d9b01a4b56ed3de1316c1006636802846d2abadec4e61c39c51d1d25564fafee7acbf2fd920538bb9952f46ad35ff21928aa29b723eac86d7f83ee618e7d43dc4e6fdd57a03786c55e3fa254e5161ab3639c5f7fe887f8328195e9d31ddee987eaf27aef4faaae73688354ed1ae3b07d2f587b3bdd47ca31cd43d3e5de88c6398f61d9b151e039823da56f764135dffbaf6ea46b2b6974ded5b0626fa93c9cc9059a1feb56fec6fac8f32468b7bce54e6575b711f63b1ec9c77673e3b760ee914e581e93ef98db30dfd0e8382e42cadc1b08ef583a3b946f654af6f0a5d750cb68975a87b4dd17ea3a608fcac53588440cb06bce6cbbcd8ce9a41b3b4c44ea591a639d0d16f92ee9ced66007f5ca07d7cda0ca0c638eacef96ca2f6695ea98fa9a23d66c390f9d6ee833080e90da07643e0b4e7b540316956a9cda81840ee7e662cbaaa870b2a84658bfdc43c1b30aa32736d433c93eb88e2eab696e8dfe975a64d5190c2dbe41b4041d72b8ddbe63d359321d4d26f47e91eb30f8501acfafd06b1d4292cc2a6e58ea9d97cad883bb67aff8ab01a40ee38edc8f26d9a4139e418419a07cefea79b5bcef6528d0be518677b51cdbd4c0690e6d3c47eb1d6317d5edf0439b3e5e8976cdb1f6100bb1b40baf96a2db0151529ba8a135903161603a8bb90f66aa8d215a2bafb6cfb696a90989a8867d03c366ab2158c53c7891fe7fd883bc7d3dd134261007598105d22b3132633c85d0c53f11a45230103185e0348a76bbadf283aa4e0a9015b8c200c60f71baeae93012a3a55c729615e3515e06e8f8186c100ea34fbaa8b529d2640d660e97ef384d6aab2b5438f2586c4bc86fd71d7309b3f1d26305d4e84c600563fb741e4df3a3e522a7fa44049e1c749ddb872b6f1973c305fdbd81b9d629f6b1c8d698c0467dea4b64779c4d99ec9fcd319471d6da6db0354c78cd695c97d0d06b0bb01d4510486fd1d6a99c2350c0650d7e77b829efa193f7173dab0299e991e930be389b06c6c4d3ef2ad72bdb2ee0b1e6369eb9b47e90c59680ca0eec24cb78a06ce126472e9abe76d2a9238cd4554cc445d9a6f8585010ca701d4f9465335e3ef8c9a7cc32c3406f0a05334aceac526ba7ee78d96b11d527be9003def50afeb6b741cdc73a62446cc739dc8adda5122ec739d18476b61ab0df15435f7594da093dbbac642fae06cc91336e52ce79cd17a3099a36158771d3b860a9df39eaebd6066e4c705a2e0b3e363a1d221f345fdcbe5f8217a85ac1b348b65ae282f53dbe2cc8d6e0690795d989c339d7124560e784dcbfaaed6f0e833b56162ef8201bcde007e702437f4c56be73bd486c762bb01bcda302612e62f3116a7f0e51e4b4755f6c72d75c4536791afc3547bcd134ef314957d4f27649c60003350e8141871d44618c2c06c718c12c7dc6c4963003973a129c5b8708d2b299373a6338ebaf6975d8c5f7a94ca6ec704eadeabc26400afacecc5a6a4016caf1cceda4e872643d07992b17d28eb784cce59e002bf64006bffda360fd23bd7ccb9eb1673ee7826d68f6332b5bd8172b6e409eef174e64a89dc5301578afa0a5d7dca94d3b487757d6222ce70edaf74dd6e73cf38afe9da0b0c156dcbe8670362acad3f2ec042f1419963a275cd07e7fca71a176a9fb33d9373a6338e358e59e25ebbc5836689a8e4bd8df962a301e42e6275c78dbb88cd5608da6e004d9a252eb8c774f544e6d3377603e2981fed05be06532d9b2f578fe41a317d640eb2cd3b0c200c609682668153684c88bd8a07cd168723f69b83bc6682671ed21b40be794f6f00f9da8be29ca58b23ad27ee351bc538da942f361ac0289d08e93835c876a269b301e42ea6759ff426a176590bc5caf48f81ea3027a64e9d749dbab97d0e90f60fddc60f95a899dc888c014c9ac0e54eb1014d106b064c1535bf2fc1c2ca02c5872bfedbd21840cef93e90625ca87dcef64cce9bce3872af53936b9433ff6dca174f05fb01c74cace8c526ba7ee226cbd846c7be31664cc19e516c63ca563870cf59e0931d4bfb16b828651cd795b5e94fe1b8e399a9dd28ec3da97b9091794d8935197a54a2e6f222ad01649c7376b30f13286746e2cef1d54d2b74c71c06305c06903b476c30463080d133805ebee94f69ece874c640616eb301a47e73f5ab6dd320a3ebaa6dc300ed71e78ca7c9374f748dcff5cd14ce356c89d10e95f93b51d8aa3b17426d00894d431788159f9b00a5a8f2576bb0e81cf6bdbc853dd6db7fd2dd0072b67760faf50690dae76ccfe4fce98ae39edf97b0c6708f0527f4b427e8d87bc26300273a37aade6ca2eb5f3d92c7da86d95321fde3e29e335be341796472aec92ce91e1f677ba6d78e8e5c269171cf7862bd7d187bfb57d6f6431d2af5865a1efb5cd07ceb5c63e9da0b6d911f46adfde2d4d82ebed6d54d2b360c9ead25ce3080e13780dc6f24d9b026ce6c39fa251840cd06d062c30203a82e9e1fbe7b1f5db392f8e21fc6f1d123c53ae3d9b669b015fb14bb01cb923b3adac617bdb8a3c58847d90026d9ec14712b9d0204ba5e6486e2b2981a5ede224a1ce3a733bee90c20677b07d31840cef64ccea7ae3872b643fb922deba3e88b53d9d743a80ce0f2de6cea34808c6d182d5e0d8ccbd678264ec8223acf3ae6bb7ddbf5c522773caf1eceb322a6ed158fb28e335bee70b79b3aa7a0cb3ee6e45fdbfa01ec73ef361f919b7f2aead63945ce2aa71081fe2e3246515c48271d735bfbfb1251ea983e53b1dd91c60072b6976a00a97dcef64cceafae3872b67364913ddfd44b6b857b3dc000fedd00d24d100630fa0690739edbca2c39ad5ad34f5beca3bc6eaecbe7e385ad260c20f73ab229c6b6cc7347cd28eda62f76063049b353dc153b4670b55390401faa2ec4df144af3b9df31b13b1db3b365e802abe2ba338d01e46caf39c50052fb9ced999c775d718c6afc52a1c740b9d7030c200c60dc0c20676167cb498ace5ce68c275ddba67dc88411e33e798cebe95ff2a90f1a3fbd7163caecc5de002639bab8566cbc77b6587dfb04c811c5c3dad3bcb58d8b9a673846ef55c7e80d2f10c55f9a1a8a98525fbb1917c6f62846d71940a77dcef68c1a400d71a4ffcbd506e5b06deb8c7b3d84e666dde4988965bdd944d76fdf3a8cb50da385ab817171cf998db1a031dbb05ee8044357ec39dba171d8b40f71ef1174fad4ed0d0b32279adb8c0a1f9c2e7982d664e7febeba1f6b2c5589faaa73df4ad79e51ea9e2f11ebef9a2ad6dc3621d6b2e544af7ac49a48ccc7ae340690b3bdd413406a3faaf9a2238eb42fe8cc0dd3940d9e1df9fd05061006302a06d016c3c26d00bb7e2e4f573b71d98774e6abe97d49e9dc38b9d2beeb51d1563c2014260f065092c657b6082a84d63a054bdcb4e12efda712f459bddd8ee18b623cd315f99ced1d4a630039db33b94e75c491337ea9736503dceb305406b0b0379b120670cb30d6368c1a4003e3e29e331b63111775352a515d33994e9474c555477c69cf0bade13b5ed84a86efcaaa7e915b5fe9e60579e0706c51ada0a2888c515c4ca08e9389289b3e18401840199db4f01b7869dc308030803080308036a9a3fec31f66e7364436ee45513280c9790cd3239d09d317f1f50503e8c110d638c6a5c43184454e4113551d63fa76c2a657b6443e765d5591c60072b6976a00a97dcef64cae451d718c6aecb2ed6f711b330c200c20573cafb6e4c1c0292c1859e77855bfd81b40f613c7907cfe8fe2d2b66e40ecd6170ca0d793acb58d8bf63e5f2236df9b2bd6dd9e1319957ce975a51344318a527c645539bcb05b1c39db6b9951795d7bd43e677b26d79e8e38463576a6e20a030803182b03c8dc2718c0e817a56d1b076b3380b69ea26bddf723f888270ca0268e2faa4b14fdeb6ecb09bd682c41e3d132bd3212b1f0abb40690b13d8a773703c8d89e51a3a2218e518d9da9b8c200c200c200423080f270ee133080f1367e3080cc5021b967c41a51ec143f6153f9e0dc4093447f1fc671ab54551a03c8d95eaa01a4f639db33b9b674c431aab13315d7d018c046a72028e8cd26ba7e7bf930d6368c1a4003e3e29e331bf3282ea2b5c21d4fbab6950690719f481db38d6b88fd84b57430d657791a03a8b9bd5870d8292c770e5b28d63bc55018e4678c075ed91a9af1712b9d01e46cef701a03685b7ea842471ca31a3b537185018401840184fcc6bf7de7a3b133803a4d2f677c6d3380589b30804639bdb671512dfddedded39d6aae1f9124f139530b7168f47b7d21a40c6f60ecf48630019db336a5434c431aab13315d75019c0fcde6cea34808c6d18358006c6c53d6736e6519cd4791ac6b826e3b61775338011de93748e336c4a6b0035b7176bde70cc601dfddc845324d9a4320f5f0643afb5adff5ec6c971ddea340690731ca90690dae76ccfe49ad111c7a8c6ce545c6100610061002118401840638f7c960c0eef3a58d10f0630ea1c718acf6df7e68992db275a21993eef1ab6c89afe7ad1fee74b4532e61cd7df3d7c59b7f8718ee7488a01a4f64de706173ae218d5d8998a2b0c200c200c200403080308f3e7cd30d14f7470edad3080969e0ad638057ca953389954aaa948c5863e7a518563565b175fff0da734468eb66ad218409d73c53d3726d7878e38463576a6e21a96bdb7c3298c2e3b372a2ed1f5db9c9b20671b26e367625cdc7366631ec54989029e71cd74586a0039732875cc51de933aeac6842adfc9ac5e3d942774ecad6d690c19ebd86000bd4185fc46a78032a174262609192953fdf2a2bd23d676337d5d39ea14fcba62c739cea3690c20677b26d7848e38463576a6e21aae13c01bd9f4e109e083ac6d983d01d43f2eee39b3318fe2a444115ffd34eb9a8cdb5ed4fd04107b924951fcd3993eee7150bb3ae395ae3d20712258fee56962e3e7276a15b599a94f26fa23a3ca071789e657b78a37b71cfd824c6c130690a11f358fa631808ce3ee66009df639db336a5434c431aab13315d7d018c0fd4ed1b5f44636d1f5db373fc8da86d162cbc0b8b8e7ccc63c8a93b8e349d7b6712fe2dc2752c71cd53da96ded17adcce9b60d834547dd8b9d8f779ada5b69de75eee1e9da039234bd502aca9c624aa7329926ddfd48a72d8e09dd3b726d37f3e305aeb1ec4963003963911a036a5f775ee842471ca31a3b5371850184018401843c6bf9ad30803080fef6f26385adb6e430c537689ec10082c469e0a6db270a5d4ad787ad5f9a2674f621a9ea618b448b87d33d198e39053f475f6bd33c02ca199b63290690dad79d17bad011c7a8c6ced4be0203080318270348efecc3c0a92b1839e7984e626cdc8be8940806307ca77fd42ee5141950dbf75618c090a2cb849936a03befcd1327b37c860f061006306c0650e51b18b6af03184018c0381a40ee58d87a6ac5b62e0fe5c5ae28d5993f51dc934c98beb08d070630c46cfefc44c1add4361b46ae153adaad7b74999644a1c25757ff39e3956a00a97d9d791195bc4fc671d7d7f3b4cd950d1c78a134b2f9e2d9002eb9914d0903b8e941d6368c16ad06c6c53d6736c6226e0690339eb41ee334de74f913b53da9a3ea69d6f124d5b6e68bca4ffa74e643badce78c97efb576766de3a2e34ee1c4a5306e88d4ef72fab21646a5c686bbbded5f9ea6f584842b86e90ca0ce79a2f639db3399f73ae2c8193f7a13c5b6bd24caf90203080318350368ab6909656e2dbb150630627b12193356f3e7e4ccd5e63c11f67c088d014481929e8aafe745c600ee7e7091f67980018401d46d0069cddab68f70bfb1030308030803080318b6dc8af23e146503c87aeab761b0f6f1243e531c670358ef14645be89b1f9914d64df1d4e23ad6b8743516f4ff73b5b323cb4f4e701b40eeb825d1354f515f2f3ac6c599eb36ee379c63ad31f0c68eefc2ab6192b8b2f82636d1f5dbcb1e646dc368916e605cdc73e6fb1462fdbd7cfd2afc6cfc0c6044d74c3adab73dc6be0f5dd75e84f6a4ab479735472d57b8f6389a779deb2c5d7b5234bf502ab63ac504974e84f4315082332ef4e86db29dc6916bb5b4a393bd8e51d2954f3af39773aeb61a36303ac6757a711d6bfc6cfa22185a7b9c63ddabe9f3bc3080308036c593bb888f9b01e45c37579be75815cfb6d577c1005ab88777543e0d0368c2009e985929b6fedb4436358e5a1b5e03c81897ebccd263cb58dad8f99569c662bfe77b8b58c644f9aa739e52dbe35e2f71c877ce769a47975ab3dfd0dec73956da3760006100e36600b9fbf5c1c9d287e36400a9f88e5a616f621d45dd00728e256a6f7e84c600d2bbd4db9c62825361dd1875c584ab8d23e3b78aa8c52e9d01e49ca7740630aa6b25ecf94eda65f04d8f54a82f9c6335b9be610061004dc5930c1a67bf3a6ac7c6eba7209ae7b0c5924edce2304e18c000e665db63c67284eb4438dd1b1f561a4062bb534c70ea9ca1c71083c219131ded987c1c8e6b4c6f2ce9fedb859cf3d49a6200e9bf75e54554f3bdc231469c6dd9f01828f5817b5f6d9d5909030803183b03c81d0ffa8c618f981187476a593f3b1a0303489f8f8dda63c2baf2c06a03b8c3292638d510a2479574c545473ba6e2766cfc56a1734c9cf394aec8d6951751cd77da0f38db3a60c163e7d407ee7d3554a70c642616ddc4a64e03c8d886d102ddc0b8b8e72c5031bfeaaec8ce75d4f2ab638f1d27aa9c63ecba0f75338011c9d3a8adb5c489b0a63ce0cebf400670f737e6881dff7712abc2b621beb1a45e5b3ca21673ce7c4a6b5c18e7a9756695d0d91ee55dd4f2bdf2abd3534e51ab22bfdf708f2f6c7b2a0c200ca0ca7876543c2de2605a74c1b976c8ac9b1e1f77bec000866f5f6d2bbe976d5ed27d8ed85a0378f0e922b1cb292838b5f7a1c5a1da503963d2f8d3e5d7c582ab1d13713b5fd494cb9947e9dae46cef641a03c8d91ee55dd4f39d3b8626e3481c1ebd917d3fdd153203d8b16f92b8bcf02636d1f5db363ec8da86c9f8991817f79c057a43e1c8b266cebe5dc9b7e3e720dac9b8acbc4be9d8ae1eecfec81dfd6f9cf1a4f932fa068a339f9ce3ebba0f5d673222b427456d5fd53d2f9ced519ef90ec49925f55a0a9633864e37fcc019072a10a36a00f77c630e5bdcaa524e92a26800338d911b6a5757beeb3080260d928eb1a533d530803080713180dc314957cc6b5d2f3563b5c79dd550af34770ad8bef5312de60f06303cfb2a774e84ca0012154e51a1436128568e38052b670ce8944c47ec75c7edac63f039e396a9e8e56cf3541a0348fdd0991f3a4e6da3164352ed37f47fb85cc7b848c7c76f830184018cb5016c5b772f7b416f62ae39c795cd88a93e69943979e4861ec5d365fe6000c3b3c64cbcd961b501acfbd61c51f9ff26b16bf77f4cb7be70e18e81aef6dede71a49fceb8d1dc72c6edd4ac2aa17bbed2b57962c236d671d25ad4396fdc6b3fed9b054bebb5ec37877eadef5150ca151d63ca1453eb0de0829bd8943080a50fb2b661d4001a1817f79c058d49e2b145c6fe91aeacd07b72d556742fef3aa9196b6cbe4d3c569b78f493394752f7a1ebe633427b12d71874bf31406b9a734edab7a4ff590bce3629cf0205858ada2aa7b0d0a11a8b4d20f58d73ecf5698a7baeb68ebeb8515b9c1bbebf983d6f32b5cdd9e6e93406f062d181d7b8c77a3a83d9558d8e756f62de74af031d39211353184018c0b81840eeb8741657457a7e164287a1351d4f5db1d461a6e36600b9cc7460f3e2013267dc3990c9d05a6d007516644951f11927f397a9180dbbd1d661fe6c3280bad64a144c4b7d96d34c5d79c36d02759b3f9da79a3080308036c7937bce3bdfd5dff534ebfc27bed4c602f3a5239e3a4ca009f3177503c839162de6cf59c33a72c0c41eaec400363a05d96e7a4c53a3f67d6b8ef162860ab85ac72ce9186fbaf60f3ebe9cadbd93cc265b57ce1cce52f472b6fb4606034879cb3de65a46034f39af63deb2e59fae3e2445b9aa3a8ec71c63a97bcfa4b8c100c20086dd00a6fbaa74af243ee3a5a9b8e7322e3a4e2512271392dfc419e658523ee87eec130690efb1c9b099bf6c8f8c5b6f001305d91d9385091d7cc2ccb7da51bbbac6b8efdbe9cdeee1678a58db7d6f7bebcd1cb1abfdcf19da6297ade8e56cf78dd9e90de0c99c6ddac69ea90f7ee1ceb7aeb2298748d49e8a78523ed27a36b157f608211d7b1d3331ff2636d1f5db4a1e646dc3a80134302eee39a3f952f2681a7da687b19f5d7565e96713b9a66a4dd0f574f5dbb678aa8c65a2c82f7f4c5b1e64db87ae334d11da93b8f783ab87797e2a24711aac6bfe7767fe8c6d18f6d21e75ff395dd4dc31c998a8a8a2e29afbc4afe9bf166b1f5ba6c293fe77ce76694e559a403210ba6397f5d15d0373c6dd6eba7511f4e487c6a2737d535b6e7d3afee24623fb0cf5edb88fc742cf2fad4fcc85a9fdf1b8c6cff5c200c20072174ced3b833f5a4985a3ee423f615e76fbfbb178fa3b5dc62f933131996f2a6349277e9443a68d5f1c0ca08e7576f580ba27024de485a935a5cc00729b11af455af313cb03bf634f8533994a13a6cf061393141937bf31240369c2f8c914bda60ca00923406d9209f1327794fb26ded891352b36ec35b4cf507f69be5345ff66d2f4c9ee21d61bc0799f66d38706f07bac6d18358006c6c53d67495d59fe25df06a0f3d4cab9868ebe6612e55efbce5f27f23055f4bf73e766d6f82ef99ce7d89aec6fdbdaaf8bf6f29f5e174332069dffede48ac9feb9ed433ae318a57da8eb5a0af20830ad37537b80a9d805895937f63ac562ad536cd8acfd4e41d6e21466e96463ff4fb814c33afb72c031c2679cc236dba9e05b8e69a6d7503c4dc7cef53142c6b6cf643180f46fa6d7c051c794533f5265c33a90dd6f686dd8bedfd8a0962796c300c20046ce00aa287412a713161a021be4f75405b18301ecf646cb92cfe97be3e2da1b43d93e2b4cff96dcfb75f6cdcf5c84c60052f15f7be76401a9935bcc0ffc6031e2944627c6ba9f2271b67fc6e5f41973945efbbfedade840cc82ef2130803080713580a64fad6c159da6f95eb34ef18d18c2005eff58e5af31cf3e0d58680c20d1f2b3e5a2ce293aa0e03a2bf108eb85fc7ac42a45fbbe36432aa94dce5dab63503157ddf596c7cf2b9ea6c75411b78c3a9db30d0610061006d0e063566154d0bc23038938c200e2a43df83c84ca00120d4e015eef141f907f3579380941bcae179962d3719331ef5827fe731e71e48b270c200c609071d1a383613380369a565bcc88cd9ffb82010ccf9e64fa514bebe65cf2f3cba133806f171d78adfe0ea708817ccb4bbc8f3d5384985dd3610f9f77e2ec878c01a4d760cefce5bcceb98c633cad3180f593c5e5b937b389aedfb6e121d6368c1a4043e3e26cd78f688e5d4fad98f3200c6a5b739fb27cbdda3427f6f1f4b20fe9cc45237b79f54b986b1febccf4bee88b5363378a7df4381ee459e77c7c7b29e23659344a3efaa92366b273d8f283c5c879facc66806f9a4d7c218cf3f788e3df7531bf1e0610061006d063a17365f16db12d4a69ecca3ffbb5e31914fc3080d6ee0b615867a1348028caf416c288f564ab4cb317134fc635cef37650d1a38a718f6352a7c66e8c84f9830184013451e8c4d1047298bf4e33b3e63e983c18402dfb791874b5c5db0fd787d600264f381a9ca2047217c52a48ac9b9c0238aeb17bdbc78f9d73f6e7bcc753dcb8ce5b93c7535bc431bba264fe6000c36b006d7b9cd24ba1435f110ff30713981a23ced3ccb818c0c41b2ccbbe0ca31f1703481cfeafc562ff1d93a12ca218a9f8ec651c63e7c7fc119c7df26a00e33a771c9f3f8eeb1e7236e4dff8090318ae622b4c9ffff25ae8d0bbf4307f8a7331c427ab89df8d63dc8be26400e3fa2828bd81605bacb41840e2f44b65a2b1ff14288d8efc7089409cfde99de283aff87e6490b15f1772ab7dcde9c1bb67c462de689c5c7b0de5449cd600c532c83ab0de00ceb9994d0903b8fe21d6364cc6af7dd3e3c6c6c51953afa239f61abb8409b4680caa7565d16dda73b36df57da18b137d7909f75ed4cd0046784fea7c8328c26b2b557ecd1ff73eea675ff4cd85d9d5a2e9ce2950179df8d90ae513f0c6d8b258c4ee5d9f277f9d8fcc32f68d72dd6fbf2827a23c6f2ddf9ecbbee9506e60ff80018cbb01e43431aee6931e990bb101ec3cb9728c52d40a523262a672324c26b06be10e03a8f8cd298bf6074e91d90d745a1a150398a4f96b33c401a77889bb2e0630096e9c714c6054e376589181b0796edfcedf2ba2b84ecee56c17d86bec5903d61bc03aa7e8cabb994d74fdb6e28758db301dc32b0b6f33362eaeb6bd8ae638906959759f15e35092f3552f19cfc9c4c9b4e5716adf7efda90de75e44d7be2edf22be27e9dadf4d8af63e258fcb5abc2ffaa6f5672bc441a79089a38e682adec844442d76e7151a08ce7eaa32f764e4a3306fad064faaa2b4d71c720c2de71b473080d12bb612a78086c6c5d5b68942870c41a80bd2822f8b38ad6d95e60f06900ffa7ca52d6f1429cb9f4d8f2b8b71240d20f1567efda543f7cc1007074c898568ac1773f5176fc77fb424f4b16bfdb97a03c1d95fd5f37ce6a532cc5b00de2b3ef84a98f71a537b070c60348a2d0ef31226e3a4aad04914ab8e910adda95fdd646bf70edb4e5733c58a739f88b301d435e6b0bec912590398e44da7b069768a9c28ebac53c09b8c3199ed16a7880c5bdc4e321a08ce7ebfc954ac531e85611e4ffedccecfa6d1bc84691d1cbd7faea0b5db23a6240c60eecd6cea34808c6d585360adbccfd8b8ae36ce618db19b54173a94375716dc66744c326adff68c08cb3a371dcf2bf9d98b77ce7d22ad018cc19e94ee89019a07dbd755b7fdc5d95bbdfebe9fb4010cd1be18cca438c5d911a7386b710a9f2888c67271d276ab165b58627c4e8361e6ecff5bcca735640a8e3be6c0b67c3f67f88d0e2febc0b6f875d599dfac8bdd691f0ca0867856bd64745cdcb1d65de8d86a04c362fc6c8827b5277342cad92f18c090bec152f6387b2c636300935c2a3e30f6e4c34bc4e1bba68452a79f5c21680c5617c0794e01fcc05cabe276e6b7ebb4c68d732c145f5de3b83879bbd1b9d43d6faa39ff87327174d08cd8e53f0c60fc0c60122a5c4c8e8b8ca8ea134993850ebdfbaf733c194f211ae744e28d231a07f74950c2f879f8421cee7d0806d0ce75657a9dc5ce0076e5edfcfa4b6f3886ea885320d9acb34ef1f676481fd57ad33110271c03a13b6634afd4b6b1dc724c1a974cae17cac5638ea1e19ab7530f2f313a6fdc39a16bbf89721c559b402e258b0cee366c25f103d7968cab5b5fc8243a054a36757d7c2fdbeb74ce0315872a0cb68c81a176a262fab2bd59a0d20c2662e6e3713d9d0630ce7b92ae3cf062f8a86dda9fa276ffe37a6c95b5403bf3e44a71f4aed78ce9f8a099893e982cf4394f5fa928a5f1d13883c68874e10f9b8c1ba3b842f3995c33a71e5eea6b0e93f317c7f8bde318eae47a687d609eafb5407f477f4fd77927c69fe703208e5c6d9c2b3a2a1d33bbeefbe2cafcdbc5e5d9ffec596d2bbf91f8fb8e5a2ada96ef8d6d2c9db177c672e957dce3e6bcae7de3e389b8059d433ff326aba0fd8b751ef85c5324fa5bba46d73cc15c840c2a4e93459ad722379b920686ae0df30200000000102fc81c701ac00f5acbee439401000000000000c002829c32c9081106000000000000004bb83ccb316a8c428401000000000000c002dab73e2360000100000000000020065c9e77bb78df31699c429401000000000000c030f48d90dce6ef4ad1f7610001000000000000c03497e7de2ede9ff9cfac6adff20c0c20000000004014f9c6ce0be21f569ff2a4cf6d3c2be8ef9eddffae283ef3fe2b8822b009322fdc06a9a3d9ccef1eb657bcc43e36d2d5fd7361000100000000a2c837779c17ffb8ea6420ddb4eeb478f9e07b28188115d0efd7711b243a858be2b892421601000000004494fb1c03f8bf1c13a742b7969e41e108ace0fd198e9161d69565dfd09aef97e7dc2e748c8b840c020000000088aa01dc7e5e7c6465ab32f52e3a85c7428171da4a1ed76294747d564ea7f9bbb2165f0003000000001059beb1fd9cf8871527940b910526a1cfe869334ccc27813acd1fe96a033eff07000000001059eedb7656fcafe5c795abd79a561491c0283a4dd3e5c55f519eeff4993fdde60f8f7f0200000000c4c0007ec4316c1c1a77e01d1493c018896f03d56c9e543d126aa2ef263ed708000000000034f38dad67c43f2c3bc6a21b57e351506096cbd33f234ca8bdfcbf7de57e47cd147125eff3c254bfaf1e34f3f3160000000008094b4efc458c6b7a27abe8358894bddce718c08f38668d4bb68fbff8f4df5e71cbe1e987f113175ec9164f8ab9ae7e5c59f45563668a44ed77ecfa83a0c73933f5913e73d75ef284d17e26faea184f642e0000001073a8507bb9e96df1cd2d67c457369e12ff58785489e87abfd87d414c6f41616d1a9a0b55f39a4e368c9172f807bbce099563a5f540d7a46bc7f18d9fe4be70dbba56a5fb4232a6aa4c229d685d9ee6181cc85574fa881d11000040aca0a2e37f3b45881f7d7ad5f144e13223e486a6f8f45fc7feaefe4d71bb53d4f98d459ce31746bee514dd9c736b6a2d7fd531683a739844b18ca2215cea183e5a9fb44e75c794f6226a7b69802709ae2cfc2a0c9e8baee4e2f40f0000401c0d60e35be21ff30f2bd12faacf87ea66fabbba8be2a61547958d3fa8be5272522c397e09058906be597e9a752e758d63faa177c56d45c7adc961ea0bf529ac7941eb8f3b37fcee0d5ee38a53409cfe010000006979c531801f750a0c55bad931544b2d3731df720a3c9563562d8a21cd0bb233bc39c0ddff27abcf5b9dc3a42743f48610f595d69ded3125fd70c719e9b8b6adfe3e8c5ea6d3bf855fc51e0b0000209ebcbcff2df1bf97b628d7d263f699c06f6d3ec532562e7d7af91141f3832c0d5f2e70f5fb1755e74295c324eab3ad7910c6782675fbdae352fb2ccc5e7a6117040000105b5eddffa6f8f892432cca3f66c767db7ee914795c63d4a1cf2c3b2c4a4effe55964ab3abeb3e914eb9c71ac53ca8330e7308d210efb9e6e512e671b6bc7ee29e2fdd73f037511c504bb20000080d8f24ac39be2638b9b59f4cf852d466fb2c5272f8da53e708d4fb77eb4ed348a16457cbbec24eb5ca9ecebbfaf3f1e991ca6b1989efb7f5b7d3432f1ecaae76a333f727ba5e09b307ed7d4b6fe09eca3000000e2cdab8e01fcb8533c706966b3996f07fcbd530c718ecb943e63d8544785ef380690739e54f4914ed069bea398c726f6056a338ab1ec2a32b799c67f79d6e7c5fb533f136b5dc9ff26f64f000000e0d5868be2138b0eb2e9feb256ed375c6a93734c366856f3db28642cce91a0fd7baae24ce47398c6a86bbee310cfa4fe4fc1219840983f000000208b01dc77517c7cc101367da7f484d69bee7f141d631d8f4dfae5ae3328687c4279c9393741fa46f31a971ca6f5ca3dd7718a67529fc94f7f0add7160f9de389abff64dff8dbd12000000f8bb01bc203eb1a0894df76b3480ff597494752c36eaa95d6fa0b0f101e525e7bc2087e54563e69a675a1f718b6752ff072630a1abfbe6628f04000095cc9b374f8c1933468c183142fcfbbfff7b42f4df24eeb657ad5ab53ed956b2edef7ce73b9ded53dfc214cb49932625fafdc8238f748e87e2ca1dcf57f75e101f9fdfc8a6ef94e8f9d287ff283ac23a0e9bf5cb9df87218af505e72ce899f3efda8fc646c7398d6afea39ce3ff24e6ce399d467961e8cede3a078e41300001442a6e4a69b6ea28d554a64ca541acedb6fbf5dba6dd2473ef29184a9e2326d5dcd6fb24d59c346fda2fe79190fc55ea5211cbff7bcf8e4bcfd6cbabf84ff11afafad3dcc3a863068d68137ad2f764a4ebef72ce5db23e5ad89bcf8c2f266d771d1dcd26b9fdf7d56d0dfaaea0b5d93733ebcf6e729c7c4c73d879f52fc4646dce3d9750d658ad195a5df14efbff69948e9f2cccf8bab7b71ea0700004ab866a27ceb9a41f28d57e3c7d10782cc979b01763368d7fe3d90c838aa3082e3eb1d0338b7814df76f38ca7a23a6eb73f63f4cb2d1f03db5e394f8c2b266e563fdda9a16f17cb5ffcf4072e78d4d6b304ca258a8c83d8e9c0bb3b2ad15fa7c5c548c5f47357edf0f000094a0c2ac7495d74733e9514fafa7646ec6c96f1c64fb91cd980535d2a90a7aba39a1fe9cf8d49c7d6c7a603d9f01e4ee7bd874cba2462b8a9f118ee9a3bee81af71d850705e582973e525e72f649b61fa5272f8d40eefa8b5d265ea87e0371f418d70f5acbeebb3cefab307e0000007af4e8fa58a32913a8d2fcf935815e4f1f331940d5e64f85099c50e798a8bcbd6c7aa0f808db8d99b3df611567bc5d4d95d3b6e9f1bf5025f7a538dc7d958dd92d0bf7236f534431c1be60666f202345862a14c6cf31ac307e0000a0986b9fdde3942b5e3e6bc8659afc18d02c278046e399de009e159fcaad67d303c587596ed077e437b1f63bcc9add78416b5144736cd3f86f59d0e01a03ee3ecbc4edc765c790af1934629bbfdf0fa5bf43fc322bffd05b9764e268b3116c2b7e42d03799a24a030000c55c3ba163d5b55335a37de032a0e90c20a799ed11e0cb76c800de905bc7a621c52dcacd08779fc3ae5b16ecd3620047579db63a0ed9728ffe8db36d99f8215783c7d0a698de99df98c8b94ca27f371d53ea83977892d122c36583e9c317bb000000331c8f5df6f0f828a8ec6397f43a3acdebfa130ab2edd3b778729c8066380194fea29ae44f3f501f3c1a47cfe4d49e113d67d5b26948917a03c8d9dfa86874c529d662a9ff92fda188439f797b45e98977477433804e5e72b61b95f899d4c01507844d7b59aa866f3c2a72f7fb3b6da7bfa3f19988abdf354f6690be3046c76705e9db49dbb7ff41d06713519101008065a77f6456524d1c992a5903e9f2ad9c810ce435739755f4130e41da973580325fa4e376222a69487d19c05eb3f6b06968d121a54664d4d6e3acfd951d13c52d7371775e503ffbceab37da4f8efd81c6663afe7e943a5f3487a6625f78e862abe9780c72ccc7988a53a2e4c47b4f64ea23fdfb80250d46fb49fd90cd4d5d7d1da3f8cd15dd7b1ab5a7a2df64cee851513a9dbbb2f2fbbe1e1925a3477f4bd7c1091f00001844f6e4cbc53c79f9e2145f064ce68b64aef5d1b3010dfae537a906d02da66ee62f8984b1f6ccc43d6f88de3376b369d8da834a6fea9c7d751b4761b37c31da15fa5b137d1eb545ed6f30d2f54cc55f753cb8e724ab5159bccfc8f8a9ddbc8673224c734f7db6696ff0bb07c8307859a3b6b8a2d2010000e0d564487f818acce38bd91ec30c8adb69669613484f3f32aff287d9b321614cbd1bc01ac7004edfcda67e79b562d89a83bed4ad082d3fc6dad774a23655cddf8045fbb4f75f55df693e74f79d733eb9c793298e85072fb6ea1e73bab514a63ca098b9f58b8c2d773ffc9a674f26b0b0514b4c4b4ebcfd04aa1d000000d2e6c7cb4f28ecd8b1a35f0f0d3fce9e09b793b7746dcb3c3ada43e2c7de4dcc8d3f03785af49e566da552fbda2f778fb6b60717ee6799df519b8f6a8d21b517bc28dd6f6d8ef8d1989dad62d8ea835a73b7d34031b7db55b45e380afdbc7de7b4ced78085ee3fabf1e88616e37d506702f76b59032875000000489b0cafbf3927f92526dacd5f260328f3f8a76ef3472799929fabf44c8e63007b4dabb2525dfb5978f042abae7687ae3ec03abf3a63de37b726d0582816b6e647d0b8e8caddaee81adfa0c206d61cd6b91eb3c533098d97b37d5ab33af77ceef170e7070000801021f30530ab56ad5aefe59a6e9fc3536500a9efd496971f6dcf70fa98f56fae195a7692df6aeaf11b59bd9b91dda744afd72badd4754664559396360715e8298cc6ec3ca12d8eb9fbcefafbdcd7e623d6e686ed3239e77d67ebf98c57c2046a8a27e562b6be98984f6e681ea336260000001622f36d955eaf79cd302abd26f593cc5bd09fabf06300ddbefcc68f71f5faf3154a0d60f529d1f3b50a2bd5b59f3adaeb33b35a6b513472d3112d711cb8d4fbef02e6ee3d6b6d5e8441e962da7f5eadb1b6b9283870fe82c998eada1f4cdc8f4b8fbe3d226a630200001013032863aa64fba6fac7d4fd184055b1bef628add61fb64fc784ea93e286293bad54d72253477b5470e95e73f72ca9d71a4b596ccd89b0c8544c693debcee111652d5a624afb80a97c3511d724b7cca8b2624f00000010530318e0f147dfdf044a27885c3f4c6fc20032193fff2780552745cfc93badd3c0c5f59de3195e7490bd3d6ac3d4bad311cfdcfa33d2e3a3d8db981361526a4c29fedc6df69f53632c87a96d936b94bbed3e33aa8c1aa6911b5b8cee09000000626c00037c63a7af2f5561364bda0da0ea134c157d9b50d52a6e98bcc33a1534fdfdddfe5b6654b2b76772dde998837b16d78930e743d8941ad721cb1bb4ae19139888abceb6691f327d8fa6391ee19841ca275ad341c6436b1d550f000000ab0ca0e4e3a859fbeaf387e0590ca0972fa7e991e1f70625aee1dd7c54b68a4f4ddc6e951e5876fd67d6b8db1b517ac8782174cbb40af6b8caf4c396f97f61eb5141b999df78ee52b26ff4ffd3ff46a2d7dc91b7dbbadccd146beef62816a67398e6847b9c991ed3d639b7947fa816000000c4c6005e3320ca0de0b56f1ff56ac4ba9924fae987ae8f93bafd0ea12e03e8c7cc52acc9c076fdd6558e1f82cfa93c217ae66cb5460317eeb96e1cb975a7d9dbb461ede99887d2c317b37ec6716449b3b179a7b6ddfa9709ca11ca1b9bf238b58fdced15349ebb603a8769feb8c7397a6bfa6f03d53dbf7d5edf2968cda26a000000107903d843d397c0c8fe183b99a46c9f1fb4c500ca7c863179ca97ed6736380ce0848ae3e286095bacd0e82d87bb8de1276bf6b3b679cf821a6b8a3813f1d5d9be9f3e79654841bd15b9dcb54fb36b4f696dcf2477ceae34b25e4dcef5889283bedfbc00000000226900dd4c58ba6bba191d324bd7ae1ba86d8d8f8066bde6b51fad7785cd008e2f37aa111b327fb9c39db32a58dba6e2dc96f577cffcddac631d5290f9738034073ae79cc6ca6a4498f3c64dd7bd89b1ba81b52dbabe2d393cbafcb0d6d8ea5a3b32bae5b5ed89b928d87ff602aa090000009132805e7f08dea7a9f4fdada15d71fb0d421d06f0dae3ad4aaec9f208e8ae63a2e7ab9bd9d467ca363124bfae9b4697b748154a9c7d23d9b4fe721d336a6abcdced76d5c80d07b4c47df8aa06a1735c99e23c707e356b5bb6190e13394c39656aae33a9ffcc5d895c8721040000107a0378ed9b39a571fbe6cb6b8f47b29830b7f164f85177e53f5adf43d117eb483c4aeadd00ee3c2a7abe5cc6a6214bf6042af639fbd667d216eb3ec3c3395e92891ce8aa91c54d5a634eede91a5ba6389b98d328e770ee9eeebfc957d070e6828979f6badf0c5fb9376dff01000000ab0da017a321f3f86786c71f95fc16a19bf9ccf0f3135a0d60a69fc048c5ed34d3af019ce014ff378c2b6353100338bbe6246bdf7eb262af758518e77849a587ce77fbacd29dd37708ee764923349bbf24d4ae8ef17595ae39bd65a27d6f62d09ae71c33ed5926d68e6ad1baa33d88c6434ab736010000006b0ca0eca995ccefde65789c33b0019479f4d2e7b78f1a3180128f7ffa2a0427ee38227affb9944dc316fbff9215eebed1f56d5b83142fdd63eee5fcefdc1a94576134d6d4be8e7126d5b56d5bd71717a6f6944797d7b3b6ab4b839d5c1db3a959941c3eff04aa12000000d618c01e128f827af8ddbb1e1c264ce69b37b9daf6125319332d332f7e7fa331c729d67afda9844d4317f9ffb20ffa5bcebed958608d2adacf3ae63165d77fe10efd37677b49d9105b1de34c1d2ff7fa4a9d4f1b28dc77bad5d49ea2738e7589c69bbb1b3f39010000c00203d8e3da695cea291afdb7ccc99f8b69f1fd7b84d4be8cf9cb7292a8fd4b60329c4426b866b4fd9ea4ba1bc0ed8745af3f6e60d3d085d5fe0da0f3b79c7db3710dea9e8f41b93b59db23d198ec30d70dec634dcd2deef9b425b6dd8c98a6f8eade334c6bc0d4ad569a7e0000003132800a941699c71dc9e4d197b8509f49f4ffcb1acf1ed91fbd34f13b8809539b1c0be9da672303c7d2d5706c6b11bd5e2a66d3d00555befb36604a396bdf6c5c83857b4fb5ea9c0fceb6487d5f29b52acedce34dcdad516bf7b1b653d27cd6cac70475c5d7e41c9b16ed8fb9d5c7610601000084c700667b6451f687e0fd2ac3378fb219402fc6d48f327c9ba914131d03d87bec3a360d9b5fe9bb6f9cfd22d9ba0e39c73c78e6f6ce719379e08e31e5974db11db566afe01e73d7dca2fc470eeb1d37f79e66a31e2dd863ed9b010000006000650c98ac11f32d97df32546e0039e3eae55b51d3164b5b0f89de2f16b169d83cff5ffec1d92f92b5c5b3a67173cfbd8d314e985ee631771d37e53f7258ffb8b9e36eab064cda240aeb4fb6a29a010000a0cca8d0638a2a4eb3b27de68ddb34499c96b13c6ac9710a2869a4b392e398805e638ad834348001e4ec17c9d675a86bdc34379ced0c9ab1cdca18f71d5722a212e3b8e7703628ffb8fb61ab6004010000283380c9c73625bf61d3b7f94b22fb05283dd43e2ac9620083c68dc3fc1113b7348bdea3d7b069d8dc5dfe4f0019fb45b2751dea1af7808965aced506ed918df4797560b5d31a6fc8f630ef7fbf37a2bc63d78fa56f6b9b659b4c651d9000000506200fd98197abd17f327dba71e928f49ba3cf6a9c500121e7e1ec3f74f70783280e50745efe757b16958de0eff0690b15f246b0da0a671c735be79154784aeb153fec731c6368d7bd4ca5af6f9b65d63d637c008020000086e0009d9133a1586c5cf69207d93a60fd3c96a00092f3f95d1d5c406f9b2974ce43806b09753207069680003c8d9af5e1617cfbac61dd7f8ea1c3be57f1c636cdbb8b9f7b93068d0ebe53081000000821bc02474ba96fce9027a0d89fedbcf899f1be9daa2ff3ff9f3091c6d7242df784ac62e391652d79fb7e06e3f67b353183db7924d4373b7fb37808cfd22596b4e348d9bb38d01af96d86d0035c598f21f396ccfb8074ddd2cb8fb66b3fabe540413080000408d0104e12567d301d1f3d9e56c1a32cbff178170f68b64eb9ce81ab7adf31ea518531c90c3768dbb60cff10bfd5f5e2fb8fb68ab064ec1e702010000c000c69a899b9a44efdf2e63d3b0595bfd7f0690b15f245be744c7b86d9e771d0c78b9584b6e511c38db29dc73bc35ae391c148a1d771ed82adbd7270000001840c06900cb1a45efdf14b069d84cff9f3be1ec17c95a03c838e601e33efc116dee79a7ebdb9cf794973a728bbb1d5be31ca6b55b5873bc957b9e6c54dece16dccf010000c000c6911ca780ecf5df056c1a3ac3bf011c34b194b56f36ce47ee8e162df3c13def39961b408a838edc1a55501dcb38738e9973ed523c07fc799de0eebf0deafbc24adccf010000c000c6d2006edc2f7a3eb3944d43a66ff69d33f4b79c7da3b1c7753eb8dbb131b63a734bd77c0e9fb7c3ca38738eb96b7c3919bdb64ef4ffd35ac13d1693b27d9d0200008001041c86a3d429509f5ec2a621d336f9ce99e173b7b3f68dc66edb7c708f99aeaf63de6d8ced7506d0c94bcef187617d7191bbbd9975ccfdffb04698181377ce98509fe796e19e0e000030803080f133800da2d7a8c56c1afabaff6f9cb3b96f5c509f38c74c31d511db514b2bed7e049439ce5ddbe26c27b52dec29fc94ec3ff9c498d5b562d0f8f5827b6e75a870f7b156dc09010000061006304e06b0a441f41cb9884d43a6fa2fd672b735b3f6adff58fb7e489b73bc249a6fdbe75d0734f79ce3d739a7b6c596e69e73bcc3f3ecfa8991d286932346afda2306be5a2cb8e73a0ef10400006099017ce4914770a3889a01dcb04ff4fcd542360d796d63a09ce1ec1bc93a03a869bcb6cf7b54e21cc71ceef3db7cd6f152ee86615f1d9ebb55f47f71a5e09eff28e6100000008b0c2022143d263885ca0d4f2d60d39029a581f286b36f249be6a2a0eae8055de3a5530bce766ef94dbed5fb85cebcba73cc0ad6b626586688b8634beb248c7b2df57bf4ca1a71cfcb45823b4661de070100005862003ff2918f8879f3e6e126114172d6ef153d7f399f4d43269704ca9b3effbd94b57f347e5be662e4a29dac6325756d4f675bb6a173ecb4066c5e636a4dce910b71ce2bafb1a235cfbdc7c988fa82bb210000c4dc0476158c5fd40d60bde8f5e45c360d9dbc2150fed0dfdbdc3f95f47d66b1d6b172b645b235e70b2b5b5a758e9d7b8dd914ebe1b3368bb8e655104af69d7c4247ec328972147743000000202e06b0d8294e7f31974d4327053358639657b3f68f64cb5c708f9362a9b3bddc2d4d561695ba738a8a7beef6a88d38e470d0fd240c8c5ab8833d3f5345f701dc0d010000809830b1b84ef4fe791e9b864d2c0e54585061cbd93f129d08192ffa166c17dce34c3509fd9e5ec8dadea333365959540efed32af658a7b6c9dd1ee58fe9b8d23ae21ee7986555d78d93f6172ea5b6a59b01bf2f60cfd3a4e83e80bb21000000101703b8ce31803fcb63d3b09ce2c0850567ff54f53128fd462d14dce34c6d93c6cdd91e8dc9c69ce78e73ba580f78ae20f2b11efcc755ec714d7d1383b32d9ab338c4d4963d1000000000ba0c6051adb8f1f1d96c7a70c2bac085c5e09756b2f69114e53920510c53db7db1b092bddd927d279eb029df0b2b0eb5728f395d3e3d3d6f1b7b9b343693b13511d728ef0b496e1d319f3daeb417e06e08000000c4c900fe74169b1e1c5f14b8b078b1a082b58fa4a7e76e355600ddfaab79ece3cbdbb45fa43543ccedaa987f950c1ebb823dd65f7c76899158a76b5717d436f7f81e9bd6fdb725b9db8cc31e4da236703704000000e26200d7ee11373e36934d0fbeba367061515277e209ce3e266522fe141f93638b6a5c4d8e3753ceeb683b9dd10ffb1e9254614573abee98be985f21e290b73487b81b020000003161d29a1a71d3f0e96c7af095354a0a8bcf3e99c7da4fd2bd630a4594622f33073ae2fad8543b7ea78efaa123de34af69cdbe330fdc6dd37cea8eab8e98661a5714e369629c73ca1a600001000080b830d929566ffec934363df4f26a2585c533799b59fb99d44b4b776a298496ed3cd8ac633c246acb745c6dc8755df12ead3ffe70baf6e794edd3d2fe7da3f5bd9171db2f72b58c89f2d4d49c4ece60e87541f91487f509000000005d0670b563007f3c8d4d0f8d536300134510633fbb6ad98ecc864989f973aeaf6b2cb7fd3c57d81057b77e70f3e55f2f14ba629ed5846aeac333b99bd9e34d736a3aa6b4bf443d77692e4de62c00000000226700778b9b1f99caa687c6ad52565cdcf6b359ac7ded2a8a0b8ff93bd0ac6b0ca49796ec10b6c4f5bee7f38d149acfe46ed2166fb7313e3ea5585b5fa82d36f3a7712d66db4374cdada9dc4dbc69c03c369a4bdc090100008018316955b5f8f4c3afb1e97b7f5e29c2d2d7547dfdf9a54a0ba3b18bb76bed3f49a65fbf9ebd495b7f7e3a59efef8de91c1b694ee9deec27ae7b8e3facb33f5f7a7a9e58b6fd80b2136d1a9fee1cced67f9dfd51bd1fc840f317b5350900000000d3067065b5b8e98753d8f4e01f57282d2e38fbca35062a60ef1a39577bbf9f9e55266c8cebbdcfe9f9b9021abfee98cbf4cb442e509b418d20ad0313fdb629773ffbf80c417ba68efca5b6748c49e51b04000000000801135756891b7f30994d0ffe71b9d262e9e9991b59fb9b4d5f1c3147bcb8709bf478f24aea137f63aabfde8afbe55afb76eb4f6788923d47d97e24de44dc6573bd70db815653394171a735241b7beaabeedce82a6adfc6b9a636697db3bc71a1798fc35d10000000889b015c51257affd724360df8d51ce50546bfc7a6b3f65956c3feb03c218a2129f9df36f46d8c07a39a2cf44dc550656e8c9ab1d158cc65cc4a125a1736e4c9e0671727e680f28572f8d19ca2c47fdbb0c6647383fa6eb29f14b3a06690fe9ee6c244df7117040000006246cef24ad1eba189ac0a639fc3acbec3fd7dabdfa0df2e32d6e701bfcc1363166cf5d5efdc0d75c264df93fdf7da6fe4aaba7dc3a67e0f1dbb2c21daa73269f884b589d78429c600000000889001ecf9bd1c560dfccd42e58546ff2773d9fb1d5695d61c1ee137aeb68c817266c8d8c2b4c5f3c8e925897febf393d7431df3e1e3d7205f3368f47c6f6f062096de457b28ee80000000401c0de0b20ad1f3c1f1eceaf3e3d79ca2ae5c69c1a1a3df61d3f05783fdee22fd3de2e84d435e2cf01d735a1788e1f5eaffa4bf9f2540ecbc09773f00000020a64c58b64bdc30ec552b15e6be9bd09dbf98a9a4a8bbe5912988a7a4285641e38d387a5bf79918f1fa06c44f523f7975150c200000001057720a77899e435fb15232fd1ffeca2a6bfbaf537d1e56f76d7ea5d5874720a672a258058d776ef11ec4f29a0ab6ecbf102496b40e10477d7b050000000042c884829de253df1d67a564c770f7a839d68e41974aab0f8d5099172fccd914fb98ba69f6ba1a6585f488d78a631f4fda8b82bf797168047253ef5e01000000809031bba846dc70ff382be5651cf78c9c63ed38b855b0b9e102476e0cf9fd92d8c6d44d23a6142b3f45a16b229ec1199db709399a4113f277e2f40f000000003d7a7cea3b7f1636caeb38ee1e91276c1d0b97f237efbbc4991b718ca99be84d13ae788f98b22e76f1a431238ee18c330000000042cabf7e6fbcf8a76ffdd13af919cbaf2615593916d5a239cbdfc46bfe3a4de0af726311539998eb787c6ef6daddb189e9f8a5dbd94c495cf60219512c70a7030000004027239de2a0e737ff689dfc8e2767c9762bc7a34a7dbea7ff2bdc073e951be998ba69c8b30bb5c69c8c26cd739473b86013cfa3cb61d8db742a77cd6e983f0000000074e786fb5e12b629e898eefcf11461e3b88268c86f17182be646cf2a8d5c3cdd44395450b6ef82a998d37c4731a63a63387b4d75ecf29674cbb05760fc0000000090999cc5db44afaf8fb54a2ac6356666a975e3f2a3be435f16b9abab8d17742595cd4f505fa210d36c1af0c86451b8716fab0d6b93e63d2a31a7f5682a8e34a751cfdba4868f2d80f903000000803ba326ac16bd07bf688d548e6dd833f3ac1a9b173dfa62be75c5dc981925a18d67360dfef90cc7f8d5b7627daa8fab0d319cb8686b24f336a9013f9a04e307000000001f85fda0315688637c8f8ec9b7667c6e22d36afd9b06e35787269e99d4efbbe3048d23346fd48428e6037e38491496da67a8a390b75d35f86733ac8c330000000042c4b05fcf13bd078e312a56a33bbd44f4bb7f9cf131a6d3a8575787ee5dfc890bb7268a7d1be39949f46640988be6bc9555d6c69cf68f30c4d6e67d20aa7b05000000006c2f905edf20067c3f47f4feda0bdaa5637c253b0f3c31eae595a2dfb7fe64648c490d1b3547e4ada814c8193ed11cd35c179644efa4c486980ffee9343171fe9650e6b02dfb804c0e3ffac29248e630000000002c65d986bae649f3cb45524f3b45d38323f3d2eaae8726889bfef37969ddfbd8ebd7fdbd89f1d198a86d2ffdf6236a63ecebeb45e9ce830f473d5f2847bce6820a519b8f3dbf44cc595111ab53121a2f8dfbb3dffc236b7c69bdd2dcd21c472d86baf601d91ca6fee0ee0300000000a0a3985e5e21c64e2d160f8e700cedf7c68b9bfee3f79ef4d96ffc21f1b74f8f5b21e85a88e8f531bdf7d1a99e639a2aba0e69d2bc72c438ad09af6da6d8048937fded63bf5f9c88711c63486fd4d0d8691dfbdd0bdcf608e4300000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000070306c6ca1802008822037e18e090000004480dedf9f2420088220c84db8630200000011e0c61f4c1610044110e426dc31010000800870d38f5e1310044110e426dc31010000800870f323530504411004b909774c000000200a06f0c7d3844eddf742a1d858775ca463f2ea1aa1bb3f71d343e356678c3f2732735bd5fc46b7bfbbf47e9b58beb359dcf6f35ccc1fc4aa741c38f9a6787cca06c4e79a70c70400000022c0a7874f17baf4d3a925594dc2a4353542677fe2a65fe7950b53c8cc6dd5a13319ff9e8ce0e79eccc33c426cc2dee42edc31010000800870d36333840e7df6a93989223e6b91b5768fd0d59fb84926feac0650626eb319406279c521cc25c42637eefaede2d8c708774c0000002002dcf8d35942871e9bb6d1b5c09a58542b74f5276e92893f273273eb66004fbff517cc25c426375e2ca8887d8c70c7040000002240efc7670b1d220320631274f5276e92893fb70174eb6365cb59d7eb602e212e617f7217ee980000004004e8f5b33ca14339ebea5c0b2c7a8daefec44d32f1e744666e650c20e612e212f62777e18e0900000044c100fe62aed0a19ce27af702cb798daefec44d32f16f3afdb6a83c7c8e45a316ee70ed23bdced500622e2126617f7217ee980000004004e8f9e43ca14339ebf7ba1758ce6b74f5276e9289ff90c92546fb28630031971097b03fb90b774c000000200a06f0a90542877236ec732fb09cd7e8ea8f1ff51fb3420c99529a56035f2eb2baef32f1a77198eca3940154d85e9fdfe477cedff0d95bc4c845bb3aff9bfecde6f95429cadd74394df96eaa4f5de74657bf4cee4f61d95b70c704000000a260007fb550e890b401d4d41f19f5f96dbe18bdb246541e392ffd59b7536fff5514561f1523175724fede96b14819c0d7361aeda34c9c83b6313c776b627e689e64e792fec654fed19c500ed2fc2545710a72ddfe2fae4c5cb3e98db75d6370e972bb28d97f8a3d9fe9dad406b5456dca4071a071d07854f441a6bdaef340a23ec7696fc11d130000008882011cb948e8504e4983bb01745ea3ab3fd9d4e7d90291bbad39f0179f50215bb8fb68e27aa6c72413ff2153cb8cf651ca00fabcf6f0bc6d52a62f5bf13d7249057b0ca89f94336e7df573edfe635725ae1d249f695da8cc67ba16e5a6ace9cb66ce06be5a1ca82f7edb35315ec2c4de823b2600000010017a8d5a2c7428a754c2003aafd1d59f4c1a347ebd38fdf6df947e0326157b435f2f333a2e99f89beea38c01f47acdbebf2b14a5fb4f299b4bea235d53f5d8472dadf494777eaeafc27424f379f89c6d56aeb5dcedcdbefbe3371f4cef2d2ae64256b863020000005138017c66a9d0a19c8dfbdd0da0f31a5dfd49a7811337282b92d39eb04ddf6c6c6c32f137d93f52e5d10bee27801eaed7e7f9e5a2e9cc3bcae7f1d43b7f4be48aaa9cf3d3472f6d14ee39c692cf230baaac5c6b345e3f7df2c3e8b57556ec2d41e6c28b70c704000000a260007f53207428a7acd1dd003aafd1d59f54f519bdd229d03a587f0b8fae4fed98189f4cfc87cc2c37167f52e5310903e8613e9bcebccb369774eda07339b2b0da77cea99cf740c6c319839fb546269af577277dec257e72c096f1521ef51fb78e7d8de28e0900000044c100fe7699d0a19c4d4dee459bf31a5dfd495561ed093d3f886e688c32f11f326babb1f8932a8f5d74373e92d72a697a837d2ea90dbf631db9ac2650db326dd07cea60e0e4324f63cfdd79584bbffabf5ceca95f5e4e80693df519b3daaaf1d21ec6bd4671c704000000a260009f5d2e742867d30109737440e8ea4f57f579718ddc3bfe67df15c317563885e5faebfe76c8ac6d62f4ba7d897f97291e4d8c5126fe340e137d4b4aca004a5c87c6217d7272a5235138537c929299c724940f5ec739704a59a2dd400650a21dd953277a5deeaec3d7c580fe5bf6ef295eaad71aada7aeeb2c29fadfa87f32f14b18220ff3c2b13fd17865faeab6b7c8ac0d6a877b8de28e0900000044805ecfad143a94b3f9a07b81e5bc46577fba6ad48a5ad7be2dab6b95ba566ec511f72ff030304699f82b39e10c308795c72f2a899dcc75a858a679cf748da1b9dba50af7d3efbccf32cea07190c969624c7143d6ebd0bfcb902d961c6b6dd0d4cdaef343ffee655e38727bf8a24aa9f1f67da948c91a1ef06a09eb3e823b26000000108513c0e757091dca29973080ce6b74f5a7abfa4f281543f2766495976bb93e32f77ab9f631cac45f89010c308795c7df743ff972b906c556c6fcd13cc95c4bc6040e5f52253dc6912b6b3dc593daa7b8a4caad9da6b3ef29ebb74c9f65fa249b873273233b3f5ed62e476eabdc5bfafc719dfb29be87ebf911ee9800000040144e005f582d7428678bfbefead16b74f5874b037236baffdcc29c9ddafb25137f250630c01cca1840b76be4561e95303ed5d27da2b9723dc1a93f297dbda673ef49c591ae3968da165f71a4bf53d967d9b9a1dc57719dbe7f2af634d6d28367339f4caeac93be96edfb13c5c5f4de823b2600000010017abf5824dc346c5e45b71b7f61fdc9d60193360999bf274ddc7ac8b578a1d7c85ecf562ddbebfe9b73144fddfd9289bf0ac69434f9ee63e589b75cafef768da67397b23fb2f9eefbcafb45a75032d7193c43eeb389a356ef0d34d73407ae66cdc3da253d9a5fa364ee4b9bcfb97f1ece9943136bc4f6fd89636fa15c4bdddb73b63637f71b5792f6f5b8630200000011e0c6b1eb44363dbd666fd69bfee099db85db354813b7b5b81758ce6b64ae659bbe387973224e642e6478707ea5f63ecac43ff057d13b46e8d6974b7cf7b14ac200ba5dc38d174b9b3cf78be656c59c52dbae2773fb4e059e6b379345f3e4f59a34af2afaee250f0f3846906226bbc704958dfb13e7dee2766f48b79671c7040000002240af978a45260d98522e825e23a91c89c28f5e23732d531abaa02a21ea676ef57151d9fa9653985df66c94e81abafb9ec36c00c9540c2fdc13a88f144fd747405de68723f67d5f71ff5ce798d203aed7293de47efa456b2ee85cbb9d82529cfd5cd7f5738012d71d346b87effca2eb539c690e55c4c9ebf838f727dd7b0bc5d1cfde8e3b26000000100503f8c70d22930af79d6e95b9c6f0657522db754839dbdd7f0b8b5ee3761d9d1a94bb53e4ee3e219ace5f526a96862eacd63e1699f8d3382b5bdff6ac650d6f880153b706ee235dcbd50066f97bcac3207f9f4d2a72d72d8f4ebf7759c95ccb9929eff32cf5eda48ae659068a17ad4f99fd47d71c87656f91bd3ff41d5fe6ebef0000000060313dff542a3249f61a393b8e886cd721d16b5c0b2c89ebe8d0c8a2fde2d47b97d94ecb862caad13e2699f89be85757c91883a063e4ea5b49cb05d76bb89fa0bdad248e2691e9dfc0dc0ac7845e557c027dd53154ada2ffebdbd9e2a6627fb2656f91dddb53af873b26000000100503386ea3c8a4d243e747c85c63f4a66691ed3aa49c9deedfce48af71bb0ea7862ca9612dce3a8b34a71ddd639389bf897e7555e549090318708c5c7da37f77bb86ab0194b8868c8c1a40c93e0e9c53a9dc042629dc7f46f49fbe4379dc82ec4f34dea6f37fb1666f91bd3fa4c611774c000000200a06f0e5329149238b9ba46ef8892221cb7548d206d0e53a5ca2b106818c63ee9e9389c2d3bd48dba37d7c7206708fb1f8932a4fbee36e30028e91ab6ff4ef6ed7703780ef2889a35103e8a19fb4564a0e5fe0f94c6adbd5c49a561937bffbd3f0957b95ec2d03e756295bc37eef0fb8630200000011e086f1e5229bdcfe7e42c571e1760d12bdce0dd96ba9d69db32a7c1465574461e35931bafcb0b867feeeebaee75aa415d4691fa34cfc4df4abab2a4fb91bc0a063e4ea1bfdbbdb355c0da0c4356464123ffda5f5436b894c9b6a52d76690b8f9d99f686ff13a2eda5b4a8e5c64dd5ba85f6e7b7bba6be18e0900000044c1004ed822b2e996a93b44e9e18b23329a3f97bf4f4ada004a5e4fa50a9bce499d28d0eb7eb2667f2226d9aee75ea4d56b1fa39c01ac3712ffa42a4fbdeb6e30b2fc3dcd8d1b77ceaef4d53715b9eb6e00df551247d72ffbb9f0d7c45c732848bf695dd11cd23a3b75e98a1203e825a61cfb93ccde92786cd5c0de72cf821a91d9fca5bf0eee980000004004e899b355c868e0c23d22a7f24442234b9a459fd7770ad9bf25d1dfb93e62e5bcc6cb355588c6e15a305ffcabe89f5b257d4dd722ad70aff671cac4df44bfba4ac60066fb7bea3fc718657244267765cc8a8a38ba992755ed708bf69cd15b8f24cc11ad41bfd07554ac5b3ffb5318f696e16b1ac584cae3ad24faffb3bd16774c000000200a0670d276a1433955adee0596f31a5dfd496ac8f27dee05e4e25a4fd774a3cff40aede394893fc54277bfbaaaf2b48401ccf2f714578e1c1b5ee4fef9507a4dd0bca0f1db10479b45394a73e8e5845076ce55e78eccde2293375efac8bd8671c704000000a2600027ef143a94537552a2c03a2974f547b65f54687ab9de90e5ee8f21ea1ea36cfca9ef26fa9654e5e9f702c7cecd18941c7dcb73bf72ebcfb89bfa1955aed7713780ef699beb818bebb5cf6fff3935891ccb262fd71bb9b145eaf375b2fb8aeafd89fae7f658b997ebd19ca9c8c320c21d130000008800374cd929746842b57b514aafd1d51fd97ecdde7bc6d3f5e8f56e459fee31cac67fc88afd46fa96948c0174bb46e1c1f3ee9f039c5b23dda75b9c82dacd64345dfc9bd4b5640ca08a38d23cba7ee6cc8993d7eb52dc466f3f96c8a57472cb1f991cf43237a411652dcaf615d5fb93db78bdceb74cfcb8d728ee9800000040144e005fab103a94537dcafd1d76e735bafa23dbaf92636f4b5fabffbc5a57b3e0e57abae33f6465a391be2555f986c409a0cb358617374b989f0bd27da2d7ba317afb71a96bb91a4067fcaa6279ea529bfb29e0d27dd2d71bb9e948e0ebc9cc0db5e3659c94b3aaf615d5fb93db9af332df7d6656bbee2d2af3279370c7040000002240afd7ab840ee5ec3eed5a602d6bbe2886ae3aa05cd9fa356af351d713bbbeb36b5cc747af697af36fae631cb3f384d01573aff1778b15b72adfb8e4da4799ebc83c1648f3ee761dca47996f8795c90f92bb01bca475bea9ef830a1a5cafe5b64664fb4e6db971fa2f6dd2f194eddbf0f58794cc0fc5d4cb1cd07a72236c7b0bee980000004004e83dad5ae8d0c41af782948b6cfd1a5cb85fca98f6cbdd93f11a54845e6afb40aa2fd9ae633afec3561f34d2b7a4640ca0ca5ca3f61eddd09236274a8fbf23758dbc7de7a4c727d31f55b1a43c93cd491a43eadcd3df536c64e6c44beec8f4894ca0ccf5a87f32d7935d7332fd1ab070aff41cc8ec2d4d6fbeefbab750bb3278e99b5fe18e0900000044c100ced82d7468e29e37cc194097bec91491f49a6587de4c8c23293209b24536417fa32bde7ee23f6ced4163fd23559eb914782e49fde6d44a17cd9de6cb699be4653ee9b5d496ecf864faa0329e63769dd4b2be681dc8f629afe19cf475690e936b8ec6f268c9e1c4ff4fd7909d5ffa7b55f3d3b55fc97ca1be64bba64c3f298f288641f616b77ea812ee980000004004e835b346e8508e41036843df9ade7a5ff49d5b2774c5dbcf1887ae6d36d63f52e599bf049ecba4682cdc788d97bb01fc8bf298969e788735068947363de435bdf652fb075ad63db53360c93e65f3e367cea2b6b7e08e0900000044809eb36a850ee5d49e316600ddfad667de5e71ea2fedac8568ff25fb85ae58fb8dff90a216a37d9431805eae3772eb09b639a56b7b1d9f8c99501d53ca6d32085c793d70c501cf7d1abef1a896754fed78e9979f38b9cd998ebdc5cf1cf815ee980000004004b821b74ee8d084bab3c60ca04cffee597990e564a2e9adcbe2cefc46a12bce41e23fa4b8c5681f2bcffe45c95c76158d49e5bcd2b5466c6bf5353e5733e18c9f23aeb72cd8274a5adf559ad7d457baaedf3e510cb94e02fdce919f3d4a66ce38f716bab6ce358a3b2600000010013e95572f74c8a40194ede31d054da2e2ec5f9515a134665df15511ff078a0f1beda34cecfd5cf796850da2e0f0db81e794fa4739e2777c32d7e78cef0b55a7031b11fa7bba8e8afedcbdaa59d97a53354764aabcb6676a6fa1bcd6bd4671c704000000a26000e7ec133a34a1fe9c3903e8b1af0fac3feabb586b7afbb278a1fa0d71cba246a12bb6aae24fe336d947290318e0fa77141e14b39b2e8a537f6df7546c937954111b2933c11c63ca4bca052f31e0ce6b8a6d49eb7bbed7378d85e6e8eed52dcad60a8d57da00c6686fc11d1300000088009f9cdb2074e80bcb9ac5fd1b8e1a51903e3fb5e394185f7f5e549cfb6b37cd6a7a33f16f246ae75f1637095df1e488bfe9fe7f6d4d0bdb5c7a995b9ad7e7abcf24faa3727c6e6353dd9e4cbc33c560c3c94b89ff9dfe9d62a5ab4f14078a7da635d775dd3db2f98496be71cc99dbde5270f81d2bf716dc310100008008f089f98d0282200882dc843b2600000010013eb6a05140100441909b70c70400000022c0c7171c1010044110e426dc310100008008f08945070504411004b909774c00000020027c6c71b3802008822037e18e0900000044808f2e392420088220c84db863020000005138015cda2220088220c84db86302601fff1f6b2dd413eb9d2a990000000049454e44ae426082', 'Vizcaya', 'N45', 'Pontevedra', 'La Floresta.', '(02) 322 7638', '(099) 252 9703', 'Quito', '1725351736001');


--
-- TOC entry 3239 (class 0 OID 16475)
-- Dependencies: 216
-- Data for Name: maestros; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.maestros VALUES (4, 'PG', 'Parámetros Generales', true, '', '2017-07-11 08:07:24.181', NULL, '2018-07-11 10:27:43.816', 'root');
INSERT INTO public.maestros VALUES (2, 'GRPUSR', 'Grupos de Usuarios', true, NULL, '2017-07-11 08:07:24.181', NULL, '2018-05-11 08:07:24.181', NULL);
INSERT INTO public.maestros VALUES (7, 'TTR', 'Tipo de Tratamiento', true, NULL, '2017-07-11 08:07:24.181', NULL, '2018-05-11 08:07:24.181', NULL);
INSERT INTO public.maestros VALUES (6, 'TMT', 'Tipo de Material', true, NULL, '2017-07-11 08:07:24.181', NULL, '2018-05-11 08:07:24.181', NULL);
INSERT INTO public.maestros VALUES (5, 'TF', 'Tipo de Foco', true, NULL, '2017-07-11 08:07:24.181', NULL, '2018-05-11 08:07:24.181', NULL);
INSERT INTO public.maestros VALUES (3, 'GHU', 'Género Humano', true, '', '2017-07-11 08:07:24.181', NULL, '2018-05-11 08:07:24.181', NULL);
INSERT INTO public.maestros VALUES (1, 'MDS', 'Módulos del Sistema', true, '', '2017-07-11 08:07:24.181', NULL, '2018-05-11 08:07:24.181', NULL);
INSERT INTO public.maestros VALUES (9, 'ESP', 'Especialidades', true, '', '2018-07-24 15:53:57.918', 'root', '2018-07-24 15:53:57.918', 'root');
INSERT INTO public.maestros VALUES (12, 'TD', 'Tipos de Datos', true, 'Tipos de Datos', '2018-08-23 10:57:05.669', 'root', '2018-08-23 10:57:05.669', 'root');
INSERT INTO public.maestros VALUES (13, 'GD', 'Grupos de Datos', true, 'Grupos de Datos', '2018-08-23 10:57:24.379', 'root', '2018-08-23 10:57:24.379', 'root');
INSERT INTO public.maestros VALUES (8, 'M001', 'Maestro ', true, 'Maestro de pruebas', '2017-07-11 08:07:24.181', NULL, '2018-11-02 17:49:22.253', 'root');
INSERT INTO public.maestros VALUES (11, 'CBP01', 'Claves de búsqueda de pacientes', false, 'Claves de búsqueda de pacientes', '2018-07-27 15:00:50.944', 'root', '2019-01-09 17:27:42.781', 'root');
INSERT INTO public.maestros VALUES (10, 'DS', 'Días de la Semana', true, 'Días de la Semana', '2018-07-24 15:56:37.326', 'root', '2019-01-27 01:18:20.69', 'root');


--
-- TOC entry 3241 (class 0 OID 16483)
-- Dependencies: 218
-- Data for Name: materiales; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.materiales VALUES (3, 'F/TPHOTOGRAY', 30, true, 27, '', NULL, NULL, '2018-11-13 15:06:36.954', 'root', 'M004');
INSERT INTO public.materiales VALUES (4, 'Material 003', 29, true, 28, '', '2018-07-13 13:37:50.767', 'root', '2018-11-13 15:06:46.315', 'root', 'M003');
INSERT INTO public.materiales VALUES (1, 'Airwear blanco', 29, true, 27, '', NULL, NULL, '2019-01-12 16:53:44.433', 'root', 'M001');


--
-- TOC entry 3243 (class 0 OID 16491)
-- Dependencies: 220
-- Data for Name: menus; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.menus VALUES (12, '09. Materiales', 'Materiales', 1, NULL, NULL, NULL, NULL, NULL, NULL, true, NULL, NULL);
INSERT INTO public.menus VALUES (11, '08. Instituciones', 'Instituciones', 1, NULL, NULL, NULL, NULL, NULL, NULL, true, NULL, NULL);
INSERT INTO public.menus VALUES (13, '01. Consultas', 'Consultas', 2, NULL, NULL, NULL, NULL, NULL, NULL, true, NULL, NULL);
INSERT INTO public.menus VALUES (4, '01. Maestros', 'Maestros', 1, NULL, NULL, NULL, NULL, NULL, NULL, true, NULL, NULL);
INSERT INTO public.menus VALUES (6, '03. Menús', 'Menus', 1, NULL, NULL, NULL, NULL, NULL, NULL, true, NULL, NULL);
INSERT INTO public.menus VALUES (8, '05. Personas', 'Personas', 1, NULL, NULL, NULL, NULL, NULL, NULL, true, NULL, NULL);
INSERT INTO public.menus VALUES (10, '07. Perfiles', 'Perfiles', 1, NULL, NULL, NULL, NULL, NULL, NULL, true, NULL, NULL);
INSERT INTO public.menus VALUES (9, '06. Usuarios', 'Usuarios', 1, NULL, NULL, NULL, NULL, NULL, NULL, true, NULL, NULL);
INSERT INTO public.menus VALUES (14, '02. Ordenes de Laboratorio', 'Ordenes', 2, NULL, NULL, NULL, NULL, NULL, NULL, true, NULL, NULL);
INSERT INTO public.menus VALUES (5, '02.Parámetros', 'Parametros', 1, NULL, NULL, NULL, NULL, NULL, NULL, true, NULL, NULL);
INSERT INTO public.menus VALUES (7, '04. Submenús', 'Submenus', 1, NULL, NULL, NULL, NULL, NULL, NULL, true, NULL, NULL);
INSERT INTO public.menus VALUES (1, 'Mantenimiento', NULL, NULL, 1, '', NULL, NULL, '2018-08-14 00:14:24.211', 'root', true, '01', 'ui-icon ui-icon-contact');
INSERT INTO public.menus VALUES (2, 'Transacciones', NULL, NULL, 1, '', NULL, NULL, '2018-08-14 00:14:30.457', 'root', true, '02', 'ui-icon ui-icon-mail-closed');
INSERT INTO public.menus VALUES (3, 'Reportes', NULL, NULL, 1, '', NULL, NULL, '2018-08-14 00:14:37.724', 'root', true, '03', 'ui-icon ui-icon-script');
INSERT INTO public.menus VALUES (15, 'Mantenimiento', NULL, NULL, 2, '', NULL, NULL, '2018-08-14 00:14:46.717', 'root', true, '01', 'ui-icon ui-icon-folder-collapsed');
INSERT INTO public.menus VALUES (16, 'Transacciones', NULL, NULL, 2, '', NULL, NULL, '2018-08-14 00:14:54.74', 'root', true, '02', 'ui-icon ui-icon-note');
INSERT INTO public.menus VALUES (17, 'Reportes', NULL, NULL, 2, '', NULL, NULL, '2018-08-14 00:15:02.418', 'root', true, '03', 'ui-icon ui-icon-calendar');
INSERT INTO public.menus VALUES (21, 'Profesionales', 'Profesionales', 15, NULL, '', '2018-07-18 11:31:02.177', 'root', '2018-08-14 00:17:42.999', 'root', true, '01', 'ui-icon ui-icon-person');
INSERT INTO public.menus VALUES (18, 'Pacientes', 'Pacientes', 15, NULL, '', NULL, NULL, '2018-08-14 00:18:13.001', 'root', true, '02', 'ui-icon ui-icon-person');
INSERT INTO public.menus VALUES (22, 'Horas', 'Horas', 15, NULL, 'Horas de Atención', '2018-07-24 14:01:30.82', 'root', '2018-08-14 00:18:40.794', 'root', true, '03', 'ui-icon ui-icon-calendar');
INSERT INTO public.menus VALUES (23, 'Horarios', 'Horarios', 15, NULL, 'Horario de Atención Médica', '2018-07-24 14:02:23.049', 'root', '2018-08-14 00:18:51.46', 'root', true, '04', 'ui-icon ui-icon-calendar');
INSERT INTO public.menus VALUES (20, 'Órdenes de Laboratorio', 'Ordenes', 16, NULL, '', NULL, NULL, '2018-08-14 00:19:30.166', 'root', true, '02', 'ui-icon ui-icon-suitcase');
INSERT INTO public.menus VALUES (24, 'Citas', 'Citas', 16, NULL, 'Citas Médicas', '2018-08-02 08:13:39.97', 'root', '2018-08-14 00:19:52.22', 'root', true, '01', 'ui-icon ui-icon-contact');
INSERT INTO public.menus VALUES (25, 'Historial', 'Historial', 3, NULL, 'Historial de cambios', '2018-08-16 10:16:49.267', 'root', '2018-08-16 10:16:49.267', 'root', true, '01', 'ui-icon ui-icon-script');
INSERT INTO public.menus VALUES (26, 'Campos', 'Campos', 15, NULL, 'Campos de Datos', '2018-08-23 10:26:00.164', 'root', '2018-08-30 10:05:22.499', 'root', true, '04', 'ui-icon ui-icon-contact');
INSERT INTO public.menus VALUES (19, 'Atenciones', 'Atenciones', 16, NULL, 'Atención a Pacientes', NULL, NULL, '2018-08-30 10:06:04.442', 'root', true, '03', 'ui-icon ui-icon-bookmark');


--
-- TOC entry 3245 (class 0 OID 16499)
-- Dependencies: 222
-- Data for Name: ordenes; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.ordenes VALUES (4, 22, NULL, NULL, '2018-11-13 19:16:16.253', '2018-11-15 23:09:54.092', '2018-11-15 23:10:28.581', NULL, 'Por entregar', false, '2018-11-13 19:16:16.253', 'root', '2018-11-15 23:10:28.581', 'apvillalba', true);
INSERT INTO public.ordenes VALUES (6, 24, NULL, NULL, '2018-11-15 22:22:40.455', '2018-11-15 23:09:54.11', '2018-11-15 23:10:28.599', NULL, 'Por entregar', false, '2018-11-15 22:22:40.455', 'apvillalba', '2018-11-15 23:10:28.599', 'apvillalba', true);
INSERT INTO public.ordenes VALUES (5, 23, NULL, NULL, '2018-11-13 19:18:54.329', '2018-11-15 23:09:54.102', '2018-11-15 23:10:28.59', NULL, 'Por entregar', false, '2018-11-13 19:18:54.329', 'root', '2018-11-15 23:10:28.59', 'apvillalba', true);
INSERT INTO public.ordenes VALUES (7, 25, '1123', 4, '2018-11-18 18:54:42.42', NULL, NULL, NULL, NULL, false, '2018-11-18 18:54:42.42', 'root', '2018-11-18 18:54:42.401', 'root', true);
INSERT INTO public.ordenes VALUES (8, 26, NULL, NULL, '2019-01-08 17:29:11.541', NULL, NULL, NULL, NULL, false, '2019-01-08 17:29:11.541', 'root', '2019-01-08 17:29:11.516', 'root', true);
INSERT INTO public.ordenes VALUES (9, 27, NULL, NULL, '2019-01-26 22:59:42.516', NULL, NULL, NULL, NULL, false, '2019-01-26 22:59:42.516', 'root', '2019-01-26 22:59:42.466', 'root', true);
INSERT INTO public.ordenes VALUES (11, 29, NULL, NULL, '2019-01-27 00:46:25.985', NULL, NULL, NULL, NULL, false, '2019-01-27 00:46:25.985', 'root', '2019-01-27 00:46:25.971', 'root', true);
INSERT INTO public.ordenes VALUES (12, 30, NULL, NULL, '2019-01-27 21:28:24.295', NULL, NULL, NULL, NULL, false, '2019-01-27 21:28:24.295', 'root', '2019-01-27 21:28:24.281', 'root', true);


--
-- TOC entry 3247 (class 0 OID 16507)
-- Dependencies: 224
-- Data for Name: pacientes; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.pacientes VALUES (12, 5, 1, true, NULL, NULL, NULL, NULL);
INSERT INTO public.pacientes VALUES (13, 19, 1, true, NULL, NULL, NULL, NULL);
INSERT INTO public.pacientes VALUES (14, 17, 1, true, NULL, NULL, NULL, NULL);
INSERT INTO public.pacientes VALUES (15, 21, 1, true, NULL, NULL, NULL, NULL);
INSERT INTO public.pacientes VALUES (16, 22, 1, true, NULL, NULL, NULL, NULL);
INSERT INTO public.pacientes VALUES (17, 23, 1, true, NULL, NULL, NULL, NULL);
INSERT INTO public.pacientes VALUES (18, 24, 1, true, NULL, NULL, NULL, NULL);
INSERT INTO public.pacientes VALUES (19, 25, 1, true, NULL, NULL, NULL, NULL);
INSERT INTO public.pacientes VALUES (20, 26, 1, true, NULL, NULL, NULL, NULL);
INSERT INTO public.pacientes VALUES (21, 27, 1, true, NULL, NULL, NULL, NULL);
INSERT INTO public.pacientes VALUES (22, 28, 1, true, NULL, NULL, NULL, NULL);
INSERT INTO public.pacientes VALUES (23, 29, 1, true, NULL, NULL, NULL, NULL);
INSERT INTO public.pacientes VALUES (24, 30, 1, true, NULL, NULL, NULL, NULL);
INSERT INTO public.pacientes VALUES (25, 32, 1, true, NULL, NULL, NULL, NULL);
INSERT INTO public.pacientes VALUES (11, 1, 1, true, NULL, NULL, NULL, NULL);
INSERT INTO public.pacientes VALUES (30, 34, 1, true, NULL, NULL, NULL, NULL);
INSERT INTO public.pacientes VALUES (10, 3, 1, true, NULL, NULL, '2019-01-27 15:28:39.773', 'root');


--
-- TOC entry 3249 (class 0 OID 16515)
-- Dependencies: 226
-- Data for Name: parametros; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.parametros VALUES (11, 4, 'Institución Predeterminada', 'INSP', 'De acuerdo a éste parámetro (id de la Institución) el nombre y el logotipo del Sistema se ajustarán en los espacios Predeterminados', '1', true, NULL, NULL, NULL, NULL);
INSERT INTO public.parametros VALUES (4, 2, 'Super administradores', 'GSA', 'Grupo de Super Administradores', NULL, true, NULL, NULL, NULL, NULL);
INSERT INTO public.parametros VALUES (3, 1, 'Externo', 'MTE', 'Módulo de Trasacciones Externas', '/externo/Transacciones', true, NULL, NULL, NULL, NULL);
INSERT INTO public.parametros VALUES (6, 2, 'Recepcionistas', 'GR', 'Grupo de Recepcionistas', NULL, true, NULL, NULL, NULL, NULL);
INSERT INTO public.parametros VALUES (10, 3, 'Masculino', 'M', 'Género Maculino', NULL, true, NULL, NULL, NULL, NULL);
INSERT INTO public.parametros VALUES (9, 3, 'Femenino', 'F', 'Género Femenino', NULL, true, NULL, NULL, NULL, NULL);
INSERT INTO public.parametros VALUES (2, 1, 'Transaccional', 'MTI', 'Módulo de Transacciones Internas', '/Interno/Transacciones', true, NULL, NULL, NULL, NULL);
INSERT INTO public.parametros VALUES (1, 1, 'Seguridad', 'MSP', 'Módulo de Seguridad', '/Seguridad/Seguridad', true, NULL, NULL, NULL, NULL);
INSERT INTO public.parametros VALUES (8, 2, 'Pacientes', 'GP', 'Grupo de Pacientes', '', true, NULL, NULL, '2018-07-10 18:47:44.487', 'root');
INSERT INTO public.parametros VALUES (5, 2, 'Administradores', 'GA', 'Grupo de Administradores', '', true, NULL, NULL, '2018-07-10 18:51:18.699', 'root');
INSERT INTO public.parametros VALUES (7, 2, 'Médicos', 'GM', 'Grupo de Médicos', '', true, NULL, NULL, '2018-07-10 18:51:23.275', 'root');
INSERT INTO public.parametros VALUES (27, 5, 'Foco 001', 'F001', 'Foco 001', '', true, '2018-07-13 12:00:37.895', 'root', '2018-07-13 12:00:37.895', 'root');
INSERT INTO public.parametros VALUES (28, 5, 'Foco 2', 'F002', 'Foco 2', '', true, '2018-07-13 12:00:54.337', 'root', '2018-07-13 12:00:54.337', 'root');
INSERT INTO public.parametros VALUES (29, 6, 'Material 1', 'M001', 'Material 1', '', true, '2018-07-13 12:01:09.779', 'root', '2018-07-13 12:01:09.779', 'root');
INSERT INTO public.parametros VALUES (30, 6, 'Material 2', 'M002', 'Material 2', '', true, '2018-07-13 12:01:30.583', 'root', '2018-07-13 12:01:30.583', 'root');
INSERT INTO public.parametros VALUES (31, 9, 'Medicina General', 'MG', 'Medicina General', '', true, '2018-07-24 15:54:28.921', 'root', '2018-07-24 15:54:28.921', 'root');
INSERT INTO public.parametros VALUES (32, 9, 'Odontología', 'ODT', 'Odontología', '', true, '2018-07-24 15:54:46.406', 'root', '2018-07-24 15:54:46.406', 'root');
INSERT INTO public.parametros VALUES (33, 9, 'Optometría', 'OPT', 'Optometría', '', true, '2018-07-24 15:55:07.079', 'root', '2018-07-24 15:55:07.079', 'root');
INSERT INTO public.parametros VALUES (34, 9, 'Ginecología', 'GCL', 'Ginecología', '', true, '2018-07-24 15:55:38.724', 'root', '2018-07-24 15:55:38.724', 'root');
INSERT INTO public.parametros VALUES (35, 10, 'Lunes', 'L', 'Lunes', '1', true, '2018-07-24 15:56:57.423', 'root', '2018-08-02 08:51:14.309', 'root');
INSERT INTO public.parametros VALUES (36, 10, 'Martes', 'M', 'Martes', '2', true, '2018-07-24 15:57:21.57', 'root', '2018-08-02 08:51:20.039', 'root');
INSERT INTO public.parametros VALUES (37, 10, 'Miércoles', 'X', 'Miércoles', '3', true, '2018-07-24 15:57:40.284', 'root', '2018-08-02 08:51:34.458', 'root');
INSERT INTO public.parametros VALUES (38, 10, 'Jueves', 'J', 'Jueves', '4', true, '2018-07-24 15:57:54.795', 'root', '2018-08-02 08:51:39.381', 'root');
INSERT INTO public.parametros VALUES (39, 10, 'Viernes', 'V', 'Viernes', '5', true, '2018-07-24 15:58:12.296', 'root', '2018-08-02 08:51:44.639', 'root');
INSERT INTO public.parametros VALUES (40, 10, 'Sábado', 'S', 'Sábado', '6', true, '2018-07-24 15:59:09.493', 'root', '2018-08-02 08:51:50.346', 'root');
INSERT INTO public.parametros VALUES (45, 8, 'P001', 'P001', 'P001', '', true, '2018-08-12 07:09:37.429', 'root', '2018-08-12 07:09:37.429', 'root');
INSERT INTO public.parametros VALUES (46, 12, 'Texto', 'TEXT', 'Texto', '', true, '2018-08-23 10:57:54.97', 'root', '2018-08-23 10:57:54.97', 'root');
INSERT INTO public.parametros VALUES (47, 12, 'Si/No', 'BOOLEAN', 'Si/No', '', true, '2018-08-23 10:58:18.783', 'root', '2018-08-23 10:58:18.783', 'root');
INSERT INTO public.parametros VALUES (48, 12, 'Número Entero', 'INTEGER', 'Número Entero', '', true, '2018-08-23 10:58:38.816', 'root', '2018-08-23 10:58:38.816', 'root');
INSERT INTO public.parametros VALUES (49, 12, 'Número Decimal', 'DOUBLE', 'Número Decimal', '', true, '2018-08-23 10:58:58.207', 'root', '2018-08-23 10:58:58.207', 'root');
INSERT INTO public.parametros VALUES (50, 12, 'Fecha', 'DATE', 'Fecha', '', true, '2018-08-23 10:59:13.954', 'root', '2018-08-23 10:59:13.954', 'root');
INSERT INTO public.parametros VALUES (51, 12, 'Hora', 'TIME', 'Hora', '', true, '2018-08-23 11:00:07.864', 'root', '2018-08-23 11:00:07.864', 'root');
INSERT INTO public.parametros VALUES (52, 12, 'Fecha y Hora', 'DATETIME', 'Fecha y Hora', '', true, '2018-08-23 11:00:23.223', 'root', '2018-08-23 11:00:23.223', 'root');
INSERT INTO public.parametros VALUES (53, 12, 'Selección Simple', 'ONE', 'Selección Simple', '', true, '2018-08-23 11:00:57.81', 'root', '2018-08-23 11:00:57.81', 'root');
INSERT INTO public.parametros VALUES (54, 12, 'Selección Múltiple', 'MANY', 'Selección Múltiple', '', true, '2018-08-23 11:01:13.958', 'root', '2018-08-23 11:01:13.958', 'root');
INSERT INTO public.parametros VALUES (55, 12, 'Archivo', 'FILE', 'Archivo', '', true, '2018-08-23 11:01:28.78', 'root', '2018-08-23 11:01:28.78', 'root');
INSERT INTO public.parametros VALUES (56, 13, 'Todo', 'A', 'Todo', '', true, '2018-08-23 11:02:02.385', 'root', '2018-08-23 11:02:02.385', 'root');
INSERT INTO public.parametros VALUES (57, 12, 'Lista de opciones', 'LIST', 'Lista de opciones', '', true, '2018-09-05 09:01:28.201', 'root', '2018-09-05 09:01:28.201', 'root');
INSERT INTO public.parametros VALUES (26, 4, 'Formato de fecha', 'FTF', 'Formato de fecha del sistema
EEEE, dd ''de'' MMMMM ''de'' yyyy




', 'dd/MM/yyyy', true, '2018-07-11 08:14:10.521', 'root', '2018-11-18 20:34:28.455', 'root');
INSERT INTO public.parametros VALUES (25, 4, 'Formato de fecha y hora', 'FFH', 'Formato de fecha y hora del Sistema
EEEE, dd ''de'' MMMMM ''de'' yyyy HH:mm:ss











.', 'EEEE, dd ''de'' MMMMM ''de'' yyyy HH:mm:ss', true, '2018-07-11 08:12:46.371', 'root', '2018-11-18 20:54:26.751', 'root');
INSERT INTO public.parametros VALUES (12, 4, 'Directorio de Archivos', 'DARCH', 'Directorio de Archivos', '/home/luis/Imágenes/salutem', true, NULL, NULL, '2019-01-08 17:30:01.316', 'root');
INSERT INTO public.parametros VALUES (41, 10, 'Domingo', 'D', 'Domingo', '7', true, '2018-07-24 15:59:20.627', 'root', '2019-01-26 23:52:11.439', 'root');
INSERT INTO public.parametros VALUES (58, 4, 'Configuración de Tema', 'STYLE', 'Configuración de Tema: Puedes utilizar:
 - base.css
 - hotSneaks.css
 - pepperGrinder.css
', 'pepperGrinder.css', true, '2018-11-11 19:43:10.984', 'root', '2019-01-27 15:45:50.963', 'root');


--
-- TOC entry 3251 (class 0 OID 16523)
-- Dependencies: 228
-- Data for Name: perfiles; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.perfiles VALUES (4, 4, true, true, true, true, 4, true, NULL, '2018-07-13 09:58:26.937227', 'root', '2018-08-16 10:01:00.561', 'root', true);
INSERT INTO public.perfiles VALUES (5, 5, true, true, true, true, 4, true, NULL, '2018-07-13 09:58:26.937227', 'root', '2018-08-16 10:01:05.458', 'root', true);
INSERT INTO public.perfiles VALUES (6, 6, true, true, true, true, 4, true, NULL, '2018-07-13 09:58:26.937227', 'root', '2018-08-16 10:01:10.325', 'root', true);
INSERT INTO public.perfiles VALUES (7, 7, true, true, true, true, 4, true, NULL, '2018-07-13 09:58:26.937227', 'root', '2018-08-16 10:01:14.582', 'root', true);
INSERT INTO public.perfiles VALUES (8, 8, true, true, true, true, 4, true, NULL, '2018-07-13 09:58:26.937227', 'root', '2018-08-16 10:01:24.598', 'root', true);
INSERT INTO public.perfiles VALUES (16, 12, true, true, true, true, 8, true, NULL, '2018-07-13 10:03:31.181', 'root', '2018-08-16 10:01:34.714', 'root', true);
INSERT INTO public.perfiles VALUES (17, 21, true, true, true, true, 4, true, NULL, '2018-07-18 11:47:10.089', 'root', '2018-08-16 10:01:38.563', 'root', true);
INSERT INTO public.perfiles VALUES (18, 21, true, true, true, true, 7, true, NULL, '2018-07-24 13:56:52.784', 'root', '2018-08-16 10:01:42.269', 'root', true);
INSERT INTO public.perfiles VALUES (19, 18, true, true, true, true, 7, true, NULL, '2018-07-24 13:57:10.646', 'root', '2018-08-16 10:01:46.998', 'root', true);
INSERT INTO public.perfiles VALUES (20, 19, true, true, true, true, 7, true, NULL, '2018-07-24 13:57:26.184', 'root', '2018-08-16 10:01:50.776', 'root', true);
INSERT INTO public.perfiles VALUES (21, 20, true, true, true, true, 7, true, NULL, '2018-07-24 13:57:36.48', 'root', '2018-08-16 10:01:55.157', 'root', true);
INSERT INTO public.perfiles VALUES (22, 22, true, true, true, true, 7, true, NULL, '2018-07-24 14:03:09.686', 'root', '2018-08-16 10:01:59.339', 'root', true);
INSERT INTO public.perfiles VALUES (23, 23, true, true, true, true, 7, true, NULL, '2018-07-24 14:03:51.495', 'root', '2018-08-16 10:02:03.497', 'root', true);
INSERT INTO public.perfiles VALUES (24, 24, true, true, true, true, 7, true, NULL, '2018-08-02 08:14:02.77', 'root', '2018-08-16 10:02:07.448', 'root', true);
INSERT INTO public.perfiles VALUES (9, 9, true, true, true, true, 4, true, NULL, '2018-07-13 09:58:26.937227', 'root', '2018-08-16 10:02:11.233', 'root', true);
INSERT INTO public.perfiles VALUES (10, 10, true, true, true, true, 4, true, NULL, '2018-07-13 09:58:26.937227', 'root', '2018-08-16 10:02:15.244', 'root', true);
INSERT INTO public.perfiles VALUES (11, 11, true, true, true, true, 4, true, NULL, '2018-07-13 09:58:26.937227', 'root', '2018-08-16 10:02:19.754', 'root', true);
INSERT INTO public.perfiles VALUES (12, 12, true, true, true, true, 4, true, NULL, '2018-07-13 09:58:26.937227', 'root', '2018-08-16 10:02:23.525', 'root', true);
INSERT INTO public.perfiles VALUES (13, 18, true, true, true, true, 4, true, NULL, '2018-07-13 09:58:26.937227', 'root', '2018-08-16 10:02:27.969', 'root', true);
INSERT INTO public.perfiles VALUES (14, 19, true, true, true, true, 4, true, NULL, '2018-07-13 09:58:26.937227', 'root', '2018-08-16 10:02:31.748', 'root', true);
INSERT INTO public.perfiles VALUES (15, 20, true, true, true, true, 4, true, NULL, '2018-07-13 09:58:26.937227', 'root', '2018-08-16 10:02:36.507', 'root', true);
INSERT INTO public.perfiles VALUES (25, 25, true, true, true, true, 4, true, NULL, '2018-08-16 10:17:14.756', 'root', '2018-08-16 10:17:14.756', 'root', true);
INSERT INTO public.perfiles VALUES (26, 26, true, true, true, true, 7, true, NULL, '2018-08-23 10:26:42.944', 'root', '2018-08-23 10:26:42.944', 'root', true);


--
-- TOC entry 3253 (class 0 OID 16531)
-- Dependencies: 230
-- Data for Name: personas; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.personas VALUES (2, 'Julio Cesar', 'Villalba Guachamin', 'juliocvillalbag@hotmail.com', 'jcvillalba', '502ff82f7f1f8218dd41201fe4353687', '1703325934', '1951-04-12', NULL, true, 'Ingeniero Electrónico', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO public.personas VALUES (15, 'Alex David', 'Ordóñez Armijos', 'alex.ceuz@outlook.com', 'adordóñez', '502ff82f7f1f8218dd41201fe4353687', '1751222512', '1999-03-01', NULL, true, 'Técnico en Telecomunicaciones', 10, 'Técnico en Telecomunicaciones', '2018-08-16 08:23:20.448', 'root', '2018-08-16 08:25:08.161', 'root', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO public.personas VALUES (16, 'Erika Priscila', 'Pila Valdiviezo', 'erikaprispi@gmail.com', '1724523640', '502ff82f7f1f8218dd41201fe4353687', '1724523640', '1991-07-22', NULL, true, 'Tecnologa', 9, 'Lindura ñaña', '2018-09-12 08:17:00.229', 'root', '2018-09-12 08:20:09.394', 'root', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO public.personas VALUES (18, 'P000002', 'P000002', 'aaa@ewee.com', 'P000002', '502ff82f7f1f8218dd41201fe4353687', 'P000002', '2018-09-12', NULL, true, 'P000002', 10, 'P000002', '2018-09-12 08:37:28.596', 'root', '2018-09-12 08:37:28.596', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO public.personas VALUES (20, 'P000004', 'P000004', 'luis@2018.com', 'P000004', '502ff82f7f1f8218dd41201fe4353687', 'P000004', '2018-09-12', NULL, true, 'P000004', 9, 'P000004', '2018-09-12 08:50:08.077', 'root', '2018-09-12 08:50:08.077', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO public.personas VALUES (24, 'P000008', 'P000008', 'asdsa@asd.omc', 'P000008', '502ff82f7f1f8218dd41201fe4353687', 'P000008', '2018-09-12', NULL, true, 'P000008', 9, NULL, '2018-09-12 09:41:32.399', 'root', '2018-11-03 00:20:48.195', 'root', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO public.personas VALUES (25, 'P000009', 'P000009', 'aqdsa@ad.as', 'P000009', '502ff82f7f1f8218dd41201fe4353687', 'P000009', '2018-09-12', NULL, true, 'P000009', 9, NULL, '2018-09-12 09:48:32.18', 'root', '2018-11-03 00:20:59.34', 'root', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO public.personas VALUES (26, 'P000010', 'P000010', '', 'P000010', '502ff82f7f1f8218dd41201fe4353687', 'P000010', '2018-09-12', NULL, true, 'P000010', 9, NULL, '2018-09-12 09:54:08.236', 'root', '2018-11-03 00:21:06.916', 'root', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO public.personas VALUES (27, 'P000011', 'P000011', '', 'P000011', '502ff82f7f1f8218dd41201fe4353687', 'P000011', '2018-09-12', NULL, true, 'P000011', 9, NULL, '2018-09-12 10:03:04.475', 'root', '2018-11-03 00:21:15.085', 'root', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO public.personas VALUES (31, 'P000015', 'P000015', '', 'P000015', '502ff82f7f1f8218dd41201fe4353687', 'P000015', '2018-09-12', NULL, true, 'P000015', 10, NULL, '2018-09-12 10:26:24.331', 'root', '2018-11-03 21:07:06.864', 'root', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO public.personas VALUES (28, 'P000012', 'P000012', '', 'P000012', '502ff82f7f1f8218dd41201fe4353687', 'P000012', '2018-09-12', NULL, true, 'P000012', 9, NULL, '2018-09-12 10:06:45.241', 'root', '2018-11-03 00:21:25.28', 'root', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO public.personas VALUES (29, 'P000013', 'P000013', '', 'P000013', '502ff82f7f1f8218dd41201fe4353687', 'P000013', '2018-09-12', NULL, true, 'P000013', 10, NULL, '2018-09-12 10:17:41.838', 'root', '2018-11-03 00:21:33.659', 'root', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO public.personas VALUES (30, 'P000014', 'P000014', '', 'P000014', '502ff82f7f1f8218dd41201fe4353687', 'P000014', '2018-09-12', NULL, true, 'P000014', 9, NULL, '2018-09-12 10:19:28.249', 'root', '2018-11-03 00:21:39.66', 'root', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO public.personas VALUES (17, 'Pedro', 'Páramo', 'nadie@nada.com', 'P000001', '502ff82f7f1f8218dd41201fe4353687', 'P000001', '2018-09-12', NULL, true, 'Ninguna', 10, 'Nadie', '2018-09-12 08:30:29.387', 'root', '2018-11-03 00:21:45.275', 'root', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO public.personas VALUES (21, 'P000005', 'P000005', 'asdas@asd.com', 'P000005', '502ff82f7f1f8218dd41201fe4353687', 'P000005', '2018-09-12', NULL, true, 'P000005', 9, NULL, '2018-09-12 08:51:30.405', 'root', '2018-11-03 00:22:18.92', 'root', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO public.personas VALUES (14, 'José', 'Rodríguez', 'cruzjonathan705@gmail.com', 'jrodríguez', '502ff82f7f1f8218dd41201fe4353687', '1722227004', '1993-04-21', NULL, true, 'Informático', 10, 'Informático', NULL, NULL, '2018-11-03 21:20:32.021', 'root', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO public.personas VALUES (32, 'Limón', 'Ordóñez', 'limon.ordonez@gmail.com', 'PG001', '502ff82f7f1f8218dd41201fe4353687', 'PG001', '2014-11-18', NULL, true, 'Dormir', NULL, NULL, '2018-11-03 23:15:28.245', 'root', '2018-11-03 23:15:28.245', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO public.personas VALUES (19, 'P000003', 'P000003', 'ewrwe@sefds.com', 'P000003', '502ff82f7f1f8218dd41201fe4353687', 'P000003', '2018-09-12', NULL, true, 'P000003', 10, 'P000003', '2018-09-12 08:38:29.191', 'root', '2018-11-03 00:20:14.527', 'root', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO public.personas VALUES (22, 'P000006', 'P000006', 'asas@asda.com', 'P000006', '502ff82f7f1f8218dd41201fe4353687', 'P000006', '2018-09-12', NULL, true, 'P000006', 10, NULL, '2018-09-12 09:27:21.872', 'root', '2018-11-03 00:20:27.926', 'root', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO public.personas VALUES (23, 'P000007', 'P000007', 'dasd@adsa.vxc', 'P000007', '502ff82f7f1f8218dd41201fe4353687', 'P000007', '2018-09-12', NULL, true, 'P000007', 9, NULL, '2018-09-12 09:35:21.34', 'root', '2018-11-03 00:20:40.769', 'root', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO public.personas VALUES (33, 'Victor', 'Villalba', 'lordonez.ar@gmail.com', 'P0010', '502ff82f7f1f8218dd41201fe4353687', 'P1725351736', '1991-05-31', NULL, true, 'Médico', 10, 'Médico', '2018-12-26 11:45:32.689', 'root', '2019-01-09 11:31:26.013', 'root', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO public.personas VALUES (3, 'Isabel Macrina', 'Ordóñez', 'macrina.ordonez@gmail.com', 'imordoñez', '502ff82f7f1f8218dd41201fe4353687', '1725351777', '1992-08-16', NULL, true, 'Viajera', 9, 'Diseñadora Gráfica.', NULL, NULL, '2019-01-27 15:41:24.951', 'root', '', '', '', '', '', '', 'Quito', '\x89504e470d0a1a0a0000000d4948445200000104000000ea080300000073efcb7e0000000467414d410000afc837058ae90000001974455874536f6674776172650041646f626520496d616765526561647971c9653c00000300504c54457575758d8d8d959595d8e2ea7195b2f4f6f9b9cad97d9eb84d7a9ef2f2f2f0f0f07d7d7d6189a9a1b9cc6d6d6df9fafcd5e0e8ecececf0f4f7eaeaeae0e8ee45759a595959baccdaf2f5f8c6d5e06e92b03232320a0a0a5e5e5ea6bccf545454797979dadada95b0c6427299fbfcfd616161ddddddecf0f4e8eef34949495580a3aaaaaa86a4bdcecece696969eeeeee3a6c94e4e4e4414141505050d4d4d4c4c4c44d4d4d989898dee6ede4ebf0e8e7e7bccddb444444ccccccdce5ec3366902121217699b5e0e0e06a90ae040404cedbe4f8fafbd2d2d2c0c0c0d6d6d6507ca01d1d1d80a0ba688ead3d3d3de2e2e2789ab6151515d0d0d0668cacc0d0dd87a5bebcbcbc5882a47070708888882d2d2d8fabc2b5c8d7363636b0b0b0b8b8b8c8d6e1e6e6e6a9bed0393939c2c2c2929292dedede292929d8d8d8808080252525eef3f66666667b9cb79ab4c89e9e9ec8c8c8b6b6b69eb6ca4a789d48769c386a93a4a4a4366992bebebeabc0d1acacacb4b4b4b2b2b23e6f96191919646464a7a7a78eabc2868686b0c4d4a2a2a28484849c9c9c1111118b8b8b92adc4a0a0a0cacaca5c86a7c4d3df98b2c78f8f8facc1d2aec3d3becfdcd5dfe8eaf0f4727272aeaeaebababa8282823468915a83a5d4dee7c3d2dec6c6c65e87a8e6ecf183a2bc9a9a9aa6a6a6dbe4ebccd8e3cbd8e28ca9c1658cabb7c9d88ba8c0a4bbcd376a93d1dce644739982a2bb99b3c8537ea2eff3f6396b94b2c6d5adc2d284a3bcd2dde6527da14e7b9fdde5ec3c6e95fefefefdfdfd346791fcfcfcf7f7f7fafbfc3c6d95fcfdfdfafafaf6f6f6f9f9f9fdfdfe3d6e96fcfcfdf6f8faf5f5f5648babfdfefef7f9faf8f8f8f4f4f4e3eaf0eaeff3407097356891d3dee7547fa294afc5e7edf2b3c6d6b4c7d6e2e9efcfdbe5f7f9fbcad7e2eef2f5cddae43f7097f8f9fbe8edf2c2d1deb2c5d5a0b8cbe5ebf16088a8c5d4dff5f8fae3eaef7396b38daac19cb5c981a1bb9db6caedf1f57f9fbaa7bdcfeaeff4f5f7f9ccd9e3dfe7ed8eaac1c9d7e27497b49db5ca8aa7bf000000ffffff32669000000026bf02550000010074524e53ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff0053f707250000265d4944415478da62f83f0afe030410c36810fcff0f1040a38100040001341a0840001040a38100040001341a0840001040a38100040001342402e1885aca224606553323e92c0710c8923632535548adb215de4315f3010268900702a7a7b6827c7abbcb1fec40a6ce26a2cce414a5b60004107502e1c061b2a2e428a79a695c7e6a240898e76b8775a7719dda0793dc235cad601488cbfb48c0a5ceb8dfe42825ce0708204a0241b0a08171aaaca691b474567a9474b28d7cbd427f83a9d33e62f4ee33e9e2b37190c1f05060944d7d5395c9790f856422fc8f000e7a6187c9f60840009119084e710c3675b8d268961943972d171edd8a4cc6ed7fa80e02f5e6ee23cf37000144462070564444c9104ca33251aa4cba9cd842a049dae50f8d803493203981001040a406c261d39c3a122247b375ee7964ede7e26c6448f79b4b7b94a65e2693767c81ae2208e89a16963132882507629a15c8e0447a200004106981c0c914457a2a354b55836a3fcf9445aaee763f3e730fff037fb1827d861ea9b2e9e8b991cf90d440000820520241983b90cc641ac530f7e8fffde6de18c587b7b4a658fd540686a939b2c67e517528f9c4254b76a1c981dd7f09803d825539a8612bc34062a6000820e20381933110dd0746f54df9f1dd8a56c220a066625a93dfca671685b55a7371d0434d44ed460c65ee82fb91fdb8ef94a0495ca46c14a8d06c3763523cfa9758703428330ad9d63af373a404024000111d08f128c94ec68fbbd0098723cf3b85a5ea25e32bfe5da2143cb870fa68b7604a6499e05f12c1015b3ee4484ad6252110000288c840e0ca418e44d569fa041d7524a82d271d6b35e0ade0befb2f2d00671b526293c9243e31000410718160ea8088451bed53c4ba6a8f70155f145a40f8691f25c7837b368a6c0b79f2e9a91210f4f14e604b587c114b69b9afda086153ac27b181001040c404c21e2678552423e6be87c464aa668ed432f28b3f40aaf75f8a58e7b1cf133d78f91f0ab0dfa43c2be06daf860a9a7571d288145b4664631e2080880884a311f05420e64e563ae5aa5105e757076d8c203873e0ce4bf5478f2ebe3ccbaf8235937ce934f88707946ff7b9a1710c59c3b9367843c625623f51810010408403e19418bca2f34077e6810b5fd9e4fa9685becb0d0d8dce4b62bb6bc18aab8828140b64e044d5ccfce43bcbac928310df6c5aebf5dc31ba72a93a4650bc5a59fe0f2fd83befa908b206c17a7816b421aab2040820828170c4189613148e20db7447e0137bb8c15e74f75c5efb9cfdfd2411372c85fe5124ef1db358e57cdc1e9b8f6696bc7ebaed24aace0bec0482e1dfdee71385903484c1cbb0743522020120800805c2617958991e86e40721f180b57bf1b8e9f2dae5d1d6f35570650f75b959f87da5cc3251033945ecbe72fa1f21a0fce60252629085d745b684030120800804c23e3e5851eb8488c5c93ecaff880097bd3ede987f083345882c384e84ee4dbecd4871fb57a5792d412d063e96f090db630e2bcc03e7120c048000221008fd509334e1d9797742b0fd3fa2c15ead95926751c2e0fabbd5c46adeb54e002939a8e7121172cb36c2d517c01ae9ed298402012080f00742183438e5cfc18c5eecbcf71f89a0d63104ee17fe55c749d1ba77f9e313888a449c8804a83c119e090d6361698150eb112080f00682153430cd6045e29dbe4dff4807abe7c00261310fa97aed67cd41142d96c144e808b680b72035615d0902cd268000c21b08d042511a96172ef0901104ff2eaf80e5a4985a32b4db2fbf024f486e9d44a4c3da2d6760f532ac544f4fc41b080001842f10b4a1e1082b13bfae25270cece560655bf43ff2c0de771fe02592f8412234e4c25a2bfb616d1c3fbc23d20001842710042199c1251e6a2251f6638250687bee6cc03fb2c1dacdf05a46a09808f5e1df60a1004b0bb2f802012080f0040203443b0334318a6f22cbfda7a195c317a97f14007b167558283c9b45847ad1fbb01c61030d05263c81001040b803410d5233a4430b84fbe4a503e5f9d036f2eb7f9481925e58c9b0f13411ca0d60359220b44325e38e3b1000020877204047102aa0d5fb5af2b2b338b4edb2ee1fa5e0b212ac9a38e94b84f25236a86a35687fca0ff7bc044000e10c04454842908704a8db69f25c9e0bd1befbe93f2a00295813f28b3331153334fcff7a401b3bda3803012080700602a4ff2c6302f1c45bf29c2d0acdc92f4aa91108ffb4609d45566242615308b446e980d6f43893024000e10a044ec8408818c41c3bf20a847f1321da2f96fca30e28990c0b05625a2c07a1aa0f408b85786ced4110011040b8028109523d420651ce9059b2cf83e4e2332cffa8051e24c0fab1578909334b88e242c8f88218ae940010403802e100a4ddad09c9d2927bc9abd7a0f5d48d7fd40306b050b8404ceaea81b49af6419282cc791c810010403802c104127635904c4566a9280571ee07512a06c2bfe392d050b84d4c160d85a84d85e4871a1c81001040380221133220011957de46669d06c992bb43ff51151c5f020d85cd4474e94b214d47274805118123100002087b20408b9208882f02284a08cca5d40d847f9798a1a5fe3222147f87345320fe91c631750f1040d8034111921b4c21c999bcaa61ef57885359fe511b94c05aa1445411c590520d5ae1e35834011040d803c11c3218b10f6cc004f29cfa1c321c32b99cea81f06f9604741c9a8851965b60952d904201c7e80a400031e01948908544a52f792ebd0971e8eb7f34008ed01634db5e229da10b098406ec810010405803611fa4135d0669e91890d7fbfd02d6fd6d2f2d02e15f1eb458f848506534a41b05c9df91d803012080187017092e8a902e3479ce5c4693aa013ef104ad2837121c5d780d56770a3263cd873d1000028801f790923764d651873c67428a70a14bb409847fa2d0c1a61082ed2570c97800b2b0401e7b20000410d6405000eb3086c4e555b21c79f52f25852a1180053ad6e44840dd0ec8f427a4056c833d100002086b2040466967402ac84d1464da63cf691608ff2640024183405a137d08560619608ac51e080001843510203379d320a3ab64b9702624375894d32e102e412abfbfbc04c69e210534245ea5b107024000610b842390a2d413ac3b892c176e878caee6fda3219082d871127f7e3d080904797c81001040d8022103327b055936144a41ddf077162d03013674674d7120000410b6408887ac1686b4179dc972e00bb05e91725a06c2bf1288ff4ee0ede4ae858c769be10b048000c21608f990f55f9070de408ef36a8528c84ac4032548c76029de9072432a187104024000610b8426489d0a0904b2da8bc110bd2c340e8483909ed4eee578d478a920559146d80301208018708eb1ea41964d91e5ba3e483a3d4ee340f8f7ee2fc191af59e0d4b20fb2b84f137b20000410b64080acf2e0069b3f9f2cc741c6c0aed03a0cfead5e0c698ef4e056e20c998ef3c637ca081040d80201b24aa90332f54796dbb289a9c1a90172214961052115d0c1731c7d078000c216089042c49c70a183737e00d2a4fd49fb402885cc44bc2c26d08bb48274a567600f048000c216089042a40592ddc88f9f031cb40f04d848aa124e0572c8e3096dd803012080b0050264404e9bfc8e342f6420622f1d02c1005219dfba8c4bc163b07c1c2410aab107024000d12410204da5de7ff400ab204523ce06d362e4317713ec810010403409040db0d6a77409042d15bcedb2bd6791065a5d70ac6f0508205a04c241483efd485bdfefadadad0575f3af407ad4b8469bff227522bd712c750608205a04c22c88c5f3302436196ca282ef2f5ff5f9f4c24242e5c489132a07d4175f84d8168ea3ab09998783ec0f4ac631f9021040b80381fcdae127644903d264e1a6abecab42164f111212d2d8f624743b0525e6de59ab5e9dc0b65438145f0d292c8377e1124000610b043fa476c25d321c0a99f55187c5fa5a76b6eb6790dd7b68f63b7253c4f35e5c0ba62bb16bb8815c43e2186cfe0f1040b81b4be4b7182155f36248f9c02ec98fc5c92264f5ad8e4f3884734f05f67cbb971979ee05d7842c400031e09c7a51802c2225c3add6609d8f41c3ad9f84706c773a53497a62d8618167efd427ec7dfa934895c39f341c810010400c38976cd5431623931108b7c13a27daf74ce2c7b31d660ea9539cdbe7e3db5d83bd429e079184ec8c6ac7b5951a2080b0050237a4db09d14ffaaab5bd3b219d1a6b15fc9b82c42f9364acb205a250b9f8987765ae949494a34f34ef0a563c81f01bb2230832f5e2876ba50a400031e09c8e4dc755d111ecd46860f3b2c60bb9e877ec5b95563c832fd1256d996f0c4cdfad6b5751aa17013c8100d1045d72826b79c27f8000c2160885903969c82ace5c9203e120c63ea87b6c3e1cf068bf7c1ab62cd36d3b09a6fa42cbc48d3ae8fb2520eb30d6636d524086e5bb20454219ae400008206c81e00969633ae1361cff00236a367869cd8296fbf7fa7c818e1393b07d04bafe49c40b346e98cbcbf6f8c94a4843643524e1615db1c101997e8214722e3817fc030410b640d80f09380fc8ec37e9b322c87b7dd49590bafae56b450dc029420a32faa9a245b4a1e190d4f301d83df7127f084d14e0bebab204ee141b005108195b0bc4b98e112080f0cc40415a4bcc2437ef381061303f14a9223c2d094c00879e89076c820d42e2a8d7b081a7903065071a0f5ff70f19c8e580e49360dc5375fa9022c10ce78a568000c21a0836482b96580f921d08129dc87ab5607ba1765b06ff3b781db2629ad8c6c25e48e9b7b31c3ea2062a6ac003b9cb219c1dd8b4418a8b1afcedc5ffff0102086b20f021d791dbc90c84ddbd1cd88603c16b93bdfe5d83a821b6eea9bd032fa0d6c22a9f0390e1bb75905ab31c779130151208b8f78301041003eee5acdebbc91b2984f65e7fa1350336206d117ef78f036238b1b524b4630a9e609805693449e4226714ac2ddb8fc84b39db711f750310405803c103b2d6cb90bceae1c15fecf37772880dc2c002d1828879440460818c1f41166ad55ebbf268e72a58810b59c6fd029b2ec8e62b2b177c6b1340002080b00682a10c52f53089d440d87512fbea89526bd8eed24e7899359fc84a1212a742b0e930fb7244710d196f96c35690dc034b354072432bee40000820ac81b0073255910a69a0cd2431103641ecc6dce6519e076a46edbec80ee22cc01874c007b682557fc6528e42679d4371771c54218150803b10000208fb123ec868540ed88c87a42e4d2eb7c499dd4bb6ca3d0d80ec0bec3943cafa40dc81b0e1044e73d6433a0e900a3f10cf7e388000c21e0890454bd27f71d7c0843b5004ca12680c36126726a4d5f3f220ae6936951d38ab55e8808a3c9e8d600001c4806747a4cc51924a70a27a34080019ee78429c9110afeec692287520ad192c698403d27c6fc53bef02060001843d103c2105aa2e790de71f442d5c8374f02613672474311c96c5ccbf205d0a9cdde8ddc99040c07702154000610f044e4807bc0d52329237f742288e3b2151486463e900b47d810136e35c5ef6037995bf34becda100018463bf831fd2120537527730891335580f1993fe4b5cb17b5904d714cb1c5cd3d22590663a6470e40f37be40000820067c7be162c16b5a773bd32410aeeec695c2b18149b832cf1a5c3db1adc8d32e2e05f802012080700402a48121c34960ca175f6e7f4c68b8ec0b29833690bec697b538e61ab16c594c405ed9ed80f7c44a8000c211088ad00606644894c44080340613083527201da14ee2ccc4b90aea020e534a205d2ec822345cab33a000208070040274794b3f64f0822681f06f36292bdc369dc531c5822b10a07503f41c090fbc81001040b8f6456a22ef0df5a249204c22a90b05a972e65f263610b6413a4f90bac1fb3cde400008205c8100d90e9705a9997c480b841bc42d4f8084952b9186ae84f4bd30ca511ccd53afddc875430efee344000208572014220fb66ea145ed005d08bf98c8e1bbed07b0f7162133f3abb0f71b761b11931bfe030410ae40e082e8ae823874262d02013261fc8cd8a5bf905d961f2e63cd2637d14b5dc8583b74ebbfc311fc810010403877cd43b68a4c850c8290d69124aec508adc8d5891d668484d95f29acf531fa988a2f243740d6e6fe994ae088218000c21908f590e61239eb73bf12b7e92580b481dc1d6e58c77856214d81a3070d74d3cf1f42278f010410ce408054b03282d8b31c157a91ff1c21ed1fa247b321ad1f37b4258b6f20b5386ac9b2163209660a6929a5133a710b2080700602b4231946fa025d683bff1ab503013a5c9d872d3d9d55c6d248f85b4f68ac1d0a00020867201cf6465ae1fc8594b9e94deac4ed162135100e42ccbd8eaa81079cfbf7a0cc654197669c87148b32044f230408200602c76df9612f8ef081e39059b2006a0702748de85f766c730b289dbcd37b909bcc9a044fe1030820dc810059ff287384e4358925448eca2d2075b7dd8e635826060d20d97f2be650fbbe644207cac0004000e10e84a03f488502330981d003090482fb2921fba4e6af26dee449d80e37b980d18d1485240e5be8a9949c04030120807007c229e442e10e092d05c8c6dc3d0477b4bf859c5943c2846f3064eef53e8ae052c889124822d0d9de1ce21a0940001040788e1d836c7bf0db43ea641ca4957f8fe0bea1662c3e22308e7d05cbf12637d1bb6b0690c95e61c816071713c281001040780201322329a38f6f992036b08ac83e4102a1fd1a3887e450b6fb40e622772204d8218a18a1e7f5127176334000e1090413882985a48eb6861035b0f46fef2dd2978143b7fb9c598e91f0b211cd144827e328f4a4ff2a224eeb0508203c81b0df01f96015e2777030e39c1c441d5e8314eb0b486a8c421b4c097bd15b4bbbe122bec7907738781373230e4000e13b9412b2212c6a37692708ec9d4fdc940d644fff5fd28e2f5a6d07598de08b3e2ff317be6fbc171228367837fca0028000c2170890455f324e248d00fddb7411b3e2c606a01b771e90365601cdf0b7f7a2d7c8b0943a4b0579f24d86a85b3f0002085f2018420cca87643a625b76d0ddb184ce358096eaa4ceecec85acc0398338aa251c12085a280da5ddd0734955890983ff000184f7a0da28e4814662d36ded46a25acd9721b92686d4e92de8e18676f0c1150e94bde93b549047535c6c890a048000c21b08902998c0732415e3446687f013c4e51a8cc063fe8b3a23871a08d0a3252288af1f81002080f0060264fb3cf4cc29620703a10523a1f904c86c8a0ae9fbd1a5207b27e6c3f6206b2167871d90d906c376dc870f62010001843710a0fb4a212de703c40ebc13379f00895011d237c1ecbd0bf1751f943f0fb960dc02e170133a861215000410fe63cc3591176bfc26d295d6c4ec1bdab09bec9dc4d0a3bc84a0f3c45248b58c1764b994702081c5cc68002080f0070213746e9fb8d9142828828c7de0ef1d421abb67c839121c768206742200b273fe007289001d5f7520f6ea408000c21f08d083e8200b7c4f2a13e74868130e6fee31804c447e23eba80de8a941fc900d70ef2123f7e0840099ab83aeaef8634e6418fc070820fc81003b7600e22d22279039886862429bbfebff9105205d34e81cd7244432bd899210881848800280002270bf8302f2193344361aa1abd75ee04bd16b28da52bef61ee2683c6865042a87a19b656125423fb161f01f208008048229f244d4172247c220030527f12c70e139445c4f131768842436d7cbf0e3427cfefdb387a489bf99d084c04574200004108140809d4041d2b1829035b8f85a0ad67f2938b90754a4400facf785cd631d02361396431a1089eda42684ff000144e8e29b7ae4ad4044ae635b0de93dccc739b6b49d1f7d20845400ddcdf11836e7f7e8f2bfcbb7511a8bc49708ffff030410a14028441e591122f2a4982402c710420ab6dd0bc80f8472485beb4078890a6ce233009245a0e7f0e33dc51f1d000410a140d8df8eb44992d8fca005a9c3d47184d941c8869d8b07c90f0458979aad0f7610ec41e8516cd0bb8f706d90c70a000288e06558b2d095c124eda1879ea29e846fc201deee252f2940ba6927bec0c213d217f9ab0bbd0c2c9f8430f80f10400403a1ea0fd231d61244b697e64192820ad6926f2f24f37ea1ec80f768e41d77ab6047901d800e28459174bf324000110c04ae76a4436bd1a6c07003e84eceebd84ebd81ac4b277d24016dd802690bea81edf6500ba1238b2e71a484c17f8000227c411e648c46663fc116103228865ec5b0064bbe87aea409a62c10fe85ee46da31bf1c12b0e7a10b128c0e9014080001443810a0810b3958839fd8369e0ed47dbd180dacd5b7a873425f29ec9a83bf27ae6e828eb4406f72c0bf7e1513000410e140e0845c1762b69b94b597fff63e866d8e47bffb69f96eca8b4548971ab6b36cc2bfcedd286329b2a485c17f800022e2d2cc7ae46d6116c4eeec5d0bbbb36f32da7e0cc8b8c7312a9c44f5146a43f40e0994ea5186d4fbb50102888840a8f883b4baf52fd123003d12b03d9c28f39806f7485abe887734139a09f8a11923057adb5126a295a35b36834f354721df1def2df400014444209c834c68491f20f1c60a67f8d597e24889416a3705a79ea2d7334228f7a642ef1276003598cfa585a5e64823ae6177e8c0d39f020820622ed2858cd8b9cc85ac36237ee58e14fc92c49349db5173c359ea5c7ce188bc413f1276a5748e9851960cc66dce0eb8ab4d8000222610a04bde219be3fe2e23de8dce889314f85ff89480a70a1e917fb81f169087a82715db09dcf5eed284cb83000144d4bdd27ec8432b76e564640870fb71a7f8fb95904d3f7fdf5029102ecf815f15aa49f8ca7b5c9d2a8000222a10a027f440861a7713bd886bde495cc7a91c9ae7e5554b9550f0ba0335b28b7018fc91c171bf0340001115085c81c8f3d3c41ec2a5f501f775f22755544e0a74527a3dd4de60b62f30236db0e6006f1bbe48f38e7ad8e5c246d82b09800022ee9a75e875ba2990641d4edc78eb2ba8874db97085c517398a0efde7988374f6d47e6db340a4c250a65d5aac354e0dd2d8ff7b9ed105dfdc244000111708d00e2af4a2b8cdc4b8507931d4753532759986b882e15b38f961701a3da5ed37a96e8b0482d4966a5d43d41bed77f7e3db14071040c405c21ec8be01e8b5172a44dc5b69b006f97a3a17b33243eca76509917dd2f93c56526e7a875e5e82fde0668000222e1060a30a117f89dcb7b41a764c802eace29291ce618a37713a8ceebaf964b6181edc22edbe7b4867d801abef000288c84080ae5f6ab72274382eb4c4aa8455dede682d1641cc83b7c83b98f02d2ca1b3be78c3bc9b7020400e1068c7ea3b8000223210fe433ba9adc4250525e8d18386e968755401ac57c5e2fb099a9c0f9135f07e1caafb990fb06cdd746d23c140809c299285d5730001446c204007980221ab1a4fe29fa67f072db5b962d1aaac3268944d028d329440e6f0c93af2117ab4c6dffbd0d6c6411f498c22e290c6d7a40545d033f04cf02df606082006a2bb9b7f904e69c43f36e60cede8ef476fc445ec817a1b3223210a19213b40cec5019099e92f483db35a9ed04fd6f79999ed982727b07dea0c98b776d3bf7225a853f619a3773091014000111d08d0dde7d0699803782e739905eddb1d56450b0323e865ed22b0e15ae85401196de8f20bc42c87e59084a6bcdd6dd0c613f626234000111d08fff5fe205d87f3770d4e9b613755ee56400b036961e81954f00d1ae5cf485b1d88009754086fd8bc1c0aef698741fbd47ed8c71e010288f840804eedb4431a3ebbdfe11a0516805adc8fda9975c981decdacf2137d9449848c910488593df832a500bcce0883d6d32e61d8bd061040c407022c29e8418f82c3def0bffc186a75154a18d4e5b843cb83ddc87b98207dca8b64b4942086e1acaa2f2f4f8037a97797c1c656c4702c660308201202210dbaa5c8164ffdbe17ba7ee26f01625047469a218c135e664f2ac758d97381f440f0c5b71cd65e34f4ca31c48093022c3a1c8471f80c20804808045805610429718f7dc43319a008bdd1baceac290ca57d3419797eb27c31b981c0837327fbc1e54af7910e38fbab66048f0c9c2b3b0102889440d0872e0169831670981b7ca4a0b5b23068b8cfc528d5f3005a5bee2bca3cdefadd24ef2d82759ea03d8f8901572f41c7bf0f96cc7add796331aa8d4753e1e34d32d5383d061040a40402746f18ac6cfc6b873e2ea2b51171cbbdcc54c53de8cd179524940929d8f56e649c0dfd13d12462fd20b278dbe26f1f241e62349ef798262332650d6e7f0104104981b01f7a4bb5d901acd34b6ba11780ef013610fc32301aaec76e2fc73e264f4620fc26a2cbb45b511e513807a6e0f1174000911408ff3da0a63241edb1460e8507d0def3eec83f7f6ccea33b49e2c672d4799b59f0e180fba407c27bc261a02b8b543f49e3dd200a1040a40502f45c6b784768772f228f8bc24610b45dfe045aa1b8e7c0e22429f4b53ba7efc1a513480f841b0442c02adfcf05a58d82d7570001446220084247eb1c12614343b0f6f36958ffbea01d76e20078cdeac6afbc3f4b300e01d9bbf5ec5f4a02e12e9e00e0d465b4914169a44c23b0d81d2080480c04d81aa63f7eb0f47ee0e6bcf2bdab7b9ec0aa254307f814f6df43573a7bb0aec92911473ee69f8c4080ac5ef32814441ea4d973944bb78cc126106db055d58a90a7000288d44080ee81f8f3c7783fdcf28d3b11499b4b1ab1eef1ef1aec2dbae39d28d3676404c2414879c2f7c7c5c146762a77537f2b7784aca6749d0be68073b229613f010410c981700456ebc87362498a82c9489b03fefe3dfbc417bd2cd81bce8b3e021242722088ba215dfd870f4435ec23c24f0001447220fc873506ff1859618481532c6c4e145e4f7d98d3c7e2756913a88d5d5ebb3de0d34ea4e9c3f8e9645ea9128e3c5a841b489711b7860d2080480f84ff1eb052c7c114b579b23b0c3ea2c887da50faf2e89b9dddcef91228ea0f30ca907baf0c74cd6c20dee926b1e9c4aede0208203202e17f032cebb9f0212706613ea42cc94d70ecd3df8cfccb7520db098fe00e8140f906e29736ff0708207202e17f1bdcb7ed11b690f2795fd054d459617943bc417004dca8273710206b5432b04f3e3bc87714ec27c93f0001445620fc5f288364a7714426837c1646c11cd82a8833084e95a55374cd1264f3631824d94377ffc9b4d7499b45a456181e25d93b0001445e20fc8f932162123890affb309610d8a396099b202537106e231d17a2eb64e20e046a865c7bfe9309000288cc40f81f948eab3c32d743e2798ba59aa22408e130ee5844a221371034101b1b64b8fe530c000288dc40f8cf95e382b5560afa7f940f2d58bc8dc42232818041d5c6015513998170fc0b6252c9fb1ce5810010400ce46bf548c6cc008ca053cef6a5129359280804e86e17f09851fa3eca03012080280884ff07aa6d5022d6a103760b5f901f2981604ddeb8d2e17482c7f012090002888132ed86e6f259e0680ff45330458a937d0d51c4048239797d878f48f7c2265321100002888162130e731a9aa8096254cc87abcdf0640a0706330a0201b2d3c390d08d054403800062f84f3b60982fe6ed82ad35a3177f0a323c4366203c811c2f07e9cd52c1a1000144cb4000151b8adadcf251ed2ed02543c09e2f5f8be701f8f83d998100395aa90cdf55d124018000a2712040c17927135d5d5d45ab7d689318901b246e931a08964827d5e750c1790001449f40c002c02d9d48b2469b67aa201d34480d0f0004d080054224f981508cbc7231920a6e0108a0010b04f0815899903d14240602e434ef3d59a46d8dc703000268c002017ca232035973913e90d1cc76a24fd42204000268c002211f11081aa42e6d07eb4a7321e64866a20040000d58206823760f089118089063123cf05e1a4e120008a0010b846af0595090b5702406c21ae4c34705a9e01680001ab040000f0bc943ca79d2c260f57ca413745ca8e11680001ab040001f1e6a060904d2768e43ef7fc1b74e9744001040031608e07b5d6d2081a04c5220404f974ac67f392e29002080062c109c5c10273891b647127206ecee40b2f6816205000134608100be7e221de5dc34224134f2f515dcd4700b40000d58209c0279c2fb0019075356221fb8d8450db70004d08005c2ff40c4d914a45d1a0039e0561b7a381e359c02104003170859885d86eca484c1de5748274a1177f22621001040031708a0d2dd053257b792944050461a6fff13b8871a4e0108a0810b04f020a327e9a78f69ed46aa218da8e21480001ab840c8419c7759494a20fc449e968fa08a53000268e00201dcec8d237de201728c8e20a45c6ca18a53000268e002c11c31d24ad220e30aa4a166aaf421ffff0708a0810b8442c4d092c865120201b2281cb295a5ee3c559c0210400317089e48030a06c4870174323682c84b3c8802000134708100be7a4013e7ddb0b8c0f3dd48577d4552c729000134708170c001d179784e6acfe114a472e8a68e53000268e002017c1470fb7e526fe07b8cb4cdb1ee08755c021040031808a039281735124f2e5efd05e9f63f312ab9042080063010c057b4c693b84916ba82519ec87b7d8803000134808190869883bab78bb45602e41430194e2ab90420800630100e3bc0875a89beb058f40bd2e23d6ae586ff0001348081f01fd4110c3c4cd2b22525e473000aa9e51080001ac840d0469cd3f2b098a83058fb016906cee11cb51c0210400319089ca0113659524eb6879e4dc144cd9612100004d0400602f8bc4b19c846a22fc4341aa167121cce829cea4135770004d0800602383f34fd25764c8103ba7fae8d6aab33a0002080063410c079db9bf0890cd0b519d063bb05c1bb2ada13a9e70e80001ad040809cffda8f63bf2dfa46f9f9d0f5e1906d89ad54740640000d6c2080a7a6eba02bc0d9f09d6c571e0d3bc80d722681c3792a3a0320800636104e81d7fcf341770f25e13c6c68af33fc545a937632ce242600000268600301b286cdc514eac115d8cf3d2f755c03df4c64e840c22557c40280001ae0403004cf2866c1b64458386324864da793a620f6536540b659a40b52d515000134c081003ddfcf0cb6d3f444020b525f6ad386752b3e23ed28dbed01d98ed8ae4b5d470004d04007027472390771f8c5bd39791fa5a49c73bf272dbd88baa5ee402b74bb530d951d011040031d08ffa1177cd61f207816802974e7a94b3eb5dd001040031e08e7a05e331624701c02ec440819aa87c17f80001af040f8ef0edd4f995eb1076708ec8f3783ed19688fa3be13000268e003e17f19d47f2eb269d8b6d5eede57c0e000df2c91a54b03170004d0200884ff91f0ed2062f1fbd14240ad4cd51bf92c042e5a38002080064320ece1463a0046bedf234df0d4a9539c569e851d62a85b08d3abf7d0c4010001341802e1ff1e2694ed522eed81758181183ba8023b3869643f40000d8a40f8ffdfc381d0c639877e7d9ad90e1040832410fe73f2b9e0098176b1c27d34b41c2080064b20fcff6f22867d0ba18bb7ac36176dad0608a0c11308ffffab6546b9a0ef1e144b75df4f738b010268300502b084542c8bd04c071d47deeee0273b234e6d0f5dac050830002ed01914afb112470000000049454e44ae426082');
INSERT INTO public.personas VALUES (34, 'Magali', 'Soler', 'msoler@gmail.com', 'msoler', '1184b6a69bb0cc03e4b7b4757e6f08af', '1716983075', '1991-05-15', NULL, true, 'Ninguna', 9, 'Otras cosas', '2019-01-27 12:41:07.862', 'root', '2019-01-27 14:16:04.772', 'root', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO public.personas VALUES (1, 'Luis Fernando', 'Ordóñez Armijos', 'louis.fercho@gmail.com', 'root', '502ff82f7f1f8218dd41201fe4353687', '1725351736', '1991-05-15', NULL, true, 'Analista de Sistemas Informáticos', 10, 'No siempre soy el mismo, a veces soy yo...', NULL, NULL, '2019-01-27 13:49:03.352', 'root', 'OE13G', 'S/N', 'S38B', 'Colinas del Sur', '3030160', '0987328457', 'Quito - Ecuador', '\xffd8ffe000104a46494600010200000100010000fffe00042a00ffe2021c4943435f50524f46494c450001010000020c6c636d73021000006d6e74725247422058595a2007dc00010019000300290039616373704150504c0000000000000000000000000000000000000000000000000000f6d6000100000000d32d6c636d7300000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000a64657363000000fc0000005e637072740000015c0000000b777470740000016800000014626b70740000017c000000147258595a00000190000000146758595a000001a4000000146258595a000001b80000001472545243000001cc0000004067545243000001cc0000004062545243000001cc0000004064657363000000000000000363320000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000074657874000000004642000058595a20000000000000f6d6000100000000d32d58595a20000000000000031600000333000002a458595a200000000000006fa2000038f50000039058595a2000000000000062990000b785000018da58595a2000000000000024a000000f840000b6cf63757276000000000000001a000000cb01c903630592086b0bf6103f15511b3421f1299032183b92460551775ded6b707a0589b19a7cac69bf7dd3c3e930ffffffdb00430007050606060507060606080807090b120c0b0a0a0b1710110d121b171c1c1a171a191d212a241d1f2820191a253225282c2d2f302f1d233438342e372a2e2f2effdb0043010808080b0a0b160c0c162e1e1a1e2e2e2e2e2e2e2e2e2e2e2e2e2e2e2e2e2e2e2e2e2e2e2e2e2e2e2e2e2e2e2e2e2e2e2e2e2e2e2e2e2e2e2e2e2e2e2e2e2e2effc20011080197015d03002200011101021101ffc4001a000002030101000000000000000000000000010204050306ffc40014010100000000000000000000000000000000ffc40014010100000000000000000000000000000000ffda000c03000001110211000001f4a0030132234c1c406a51249c09c460881d0cdaa6d2f3a1e8d6274364c399b0f8581a001a0005240268908004352010818c4343401124270e4767cf38d0a753a0b9f78919ce2763a32a4bb54343ae3c0b7d7857378c1be5e9f3980d0a49121030431031322c008c84d3138f23bd7cfaa5bb7c799d1f2a45b8f5471b95744c6b70d432b86f661c2e7245c1b2bf49733946e2394b85c2d19fa0008180d00340d00800682509521579e512daa768236334cddba3c8deceb99e56d7ccd938439d736b1b4734dce71ec52efdb892e5da4579f2e842be8571baddcb4d03000006807164400173394659470bf574ce8abf73871722df93f5de7ce5cfb74386960b3d257cbd22756d7632fb59b852bfd789d2c50b059e1ce05b9a0ceb6aa9aae2c1a000112008b108156b59c77ceab748769f71d49712b6963da36954bc7927e9391e5677fa9956afdb327bde467f0d4a84ede65a2ec68f13d1f2c7d03b5bab60e752e659b408908189898098ce5222197a78273d1ab689d5b540efcbbb27ca1b0568e844a5cb442ad99073e5611c67d595fa482156f864ad7e067d0d8e617b22c1a3837605fed56d013889a63001c410c3863e857175eb58abd696a9ce3d74887424093052402609808600c1340c067299c323772853381a36f2f504344a2d80021845a919935d8a59b711627d6e11e890c4c173ae5d75267755e05c2848b8665737579f46fcbce40f4fcf0ba1b9cfcec0ddceea10b1d59c34b3af9d45208889b8b0524269153bf0c63ad4e5c0dae98574bf3b564c9a3a760c69ef502a5a95c1f2e760f3b7b3b6ce54342d14aed8c4353963681d2e4eb98be870b6ca5da905fa95f70c8d5e3c0d1698c4c001898a27130acd70ed423dc954d9e25cbd8fae53bd4ed98509d727b3e7b78ec4aa19f79572e5fabd07e4bd979b1f28691ad56dd729d4ef5cd0e1b3cce5856244acf781a0e20d490d29116d908c91e7345d339e868e192bd89e84c6ddc9d62bf49711e6ed067df2b0e85aca37f2762a96ab2e65c8e773357a62742ff5cf44674b64b2a554e99fd42cd7a9ac76942409b0182148e728b0cbd5679c5af50a7ad8d68bfcb4199b1d38997cf643cf2f4333cedad4cd347a466030843a0467161248af65a0ab6734c9d2cdf423b0313013684304d003403472cadaae603e16cdf752e11926128a1b4ce59ba59c6a34c0007163421a8c893411cad3c62b6ee26e161f3ea4580a4989a621a1021a5323c2c65981a79db472dbf3fb2776804c191915ebdba268987d8bdd7958018383e04e55ec136995f3ace19635e8ccd2ed19800000d38924044001843077328c7e5d227a02d54351f3e82621a600818980c12e344b39f6aa06ff001ea253aa54e5db10b5d74ac91ec3006449c41802010d04660bcd7a5e4549dc657874651d3ceb2586804d027223467826cf4a150d1b3e56b1ea74a8ea0d0c86574ec59e36e053bc00d826818008449304a4109a644001815ec733953d1aa4fbe6f72f10900072f3de96b189b3c424ad5823210569e316b579f41b40c4c43412400308cd1190869c40189a626808b08b7133d68d00b7c5972352c93431b8849441d7cde655d8f3be8cbd240c00698860c40c4c010290418c02235388098d0c400a1d22558dd815a1659c2ce5e79e9a1e6b81bd8d6764f2fea7cffa83cfd7d2f3c7b4acf3cd5efe5af9b466db3b8ab169c64339c8626124804c08c8130401251609a1c2680111876ac78cf45e67749c0e66f4e74cc7dcceac67d9efbc6176db898f3b74ca17b52817fcf4b40eba75ec18c6879a3d6ceaf72498008250090813103010d030224911e2bcf9439e9eb1e4eef6ea6ae6685e30ec6d48e7d1c46388c5322c818d7ac23b802ab6d15ec3642431210c01a004c043004153be29a5df0e246fd7dc14e320001a634034d0dc589c9093621821a06801a094410000020069889028744796dfccdb1c46309111b22d80260340c40c00402686c01304c434031c448013018862630004195a58fae4f1b6f00d1bd4ed83189a625208b68188001898d2620006089449210c108000430813421b4c408913e64b3f42247a244d4a049340e32004225124009b8138ca04da99cc013006e209b3ffc4002c100002020201020505000203010000000001020003041112132110202230401423313233052441425034ffda0008010000010502f8bf99b1030ffc6660b1b240872098f672889cc7d2ee7d219d2c8ae0c86488eade1af803dcdf812046cb5116dbec869302d2236d82356b0ae3b4e88115dd4ad80c20307c7d1aeeefe23e0efce4f29e90a721998ab6f82563a8c67a16177835057511d3ae746bdd8a80eed81ae812e8c96b325ceb05fa8ac1be3d8e1435a9badd7597c8a271e972d9f420af8b4e614751d727bb46afb7250517a8463a89ab04255832b2c57dcb71f9c5dace98316d2bf10b851764f11ae4c0d1196a9d6e10ed99047ab9ae3f13382cccda956e53b6e822d8f8da64e30234fb93b19ae52b24cb17b2120fa6c5ac9acfc0319d98b71ac2f2b6f142cfa7aa355501c7a86b411b412addb561ab182cd4c8d11867ed98a7865c65569c5922372857718133f60bdc3241e86b006153725f63b79cbb585db8c66fbb53155eac661086b20561553905acb3bd78cda6fe39a7b8b978a02e993d612ed446daf815101d8308e0cbf932c5d8a9fba766f7772c63b66e8d65d8c445adeaec8cdce77117d4f6e88d95b8decb3a8e0e51b651929c725d3a76bab2fd53683b9752c075ad95e4d820c8105b598089f9893d504b8717520bfbac752aee7258bdf4af083edd688cc1dd6a5b1cef13d53f2f9f4710af4f49e85642e782aeddb75d5570709d3a6e561caea19a53d228caa4b55148e5d059c6c58967ae112c1c928ee3cbaf2efc6eee1dba6947f3a7413b925822aa1de49d263eba6a238e4171762da1ea6b3b21e519c3a85af8575d4cab8e141aef0dd5b108cc6d7d5085d6e019eb8a761b8b153c4cfc1a7b59eededdef2cccc9a2c393f627f73d73b55b2f724d031ed0fe0d5af36f54c8a91674eed25141271d3629871773e8e598ad34887a15386aee03a8eb29bc2467562df88ffaf2d5bee1961f5d2376d3bd5223da2717b8e49952746aa94deed8eb3efa4ea5b385cf1295ae08514ce92c159138cd77e30a831b1909355cb3a844de3996aaa953e853b10fe6b3c93db3f83b7603b59fa643f4ebc54e42d6e328afa634f7ba80a3dcede1f91d1ae1c6a8c38e167aab9d4082bb5fa9d8598ffcf7e047b367e946e540f0fcd991eb37d81569a7843bbdab508bf0ed5056c1f694eed6fef8ff00af8f6f3ea649d2560ad3577a98aa25cebd4a2a0a3bde55428f78f9770cb441fd5096beb6e0fedd849bf7a95b6aa726e64c7a9069ee8a001ef6e6e6e19dbc091aebd3bc83a85b4942cb7422fe3da5ff00e8ff00b11c885e9b0acb4edaf1ed0b813af5efad5ceaaceaac36cea3cdb986d227d418725e75ee86cba7de8945d2eaac5a999cd55e33d8061d4894036c5ad5657dce58ec9fafb49aea9975e95b9cc1ce9cb3683659cb95e66ae8fcd57d661a5a0c6267435176906b577a9393eaba3686b459ded2b8ed15350aa9868136f4b31e4a045d099967a685e2963854ac5b61bf9ac420af9bbf92c6091f26c7031eede4295b48e04dbc9b1c96aa37dcb12b134b1f2965790ac753f9b37eacba98ff00cefdca102d72ec9f557d5b25791c588062fdb7b87dc53e8ac8bb2b26fd363d66c80010a076c63e3af1fc7909d072d73e157e9bb3101b1d6d5084464e94c33f60fe2bfd3fe33ed3bc5a1585f474da86e7558bf6b7baec5ff5f14fdbb81dcb3612cee69b3a72d60eea7b583eede3efd9771a712be14fd37dd764ad1b36db1a81981b5fec79f7e177f343e82c570718f047a5cd940ed9d5fa300f6317f9ccb40b7b19ea98ca56b7fd4b7faca9cb170bf168252b6e425a9c1d54ef1f1b5373f76c8fed8b52db6c1fb5fcd6c556594b8b153bddec98074adc5d3d2b5db8ecefb4f575081626082b6c3d98197d42d5fa67dd78eabe177a8649d2a8d2a0e964cd713cb7197702010e84df2874a3a95f5708a969ff006745b1061573d15ad4fb6efed6555b184fa73a12ebf9c0c56e967a32f7db60c1e89a9bf076d12f556a8e1ad53b5b518b7de11ac33a8675ad81f2271ca69d1bc8fa510f054a2ae9ac63c4577825dde0af711542fb465f8fb8532ec86bfa7367e319f9d77d22d0b8ecb3a2d385c27fb4b0be4c2b9060c7b8cb3165abc284fd7c7538c026bc0c5a555bc2c6e28c36b5565d52b0b35eeb328399c4a2774c33a79bf0d4d09a9a1e0d3288e29d93db332bf43c8cabb2fbc5762f4358adfee06d6478f7f2b47f55fed6bc3fe325bb1fe40fabc7bfb9701d31daca903538efc93cdda37698cbc9fd8dea0f132fef1bf80ef529dafbd6902b663c501095384bfcee3631bf9783d816292478ef5e0ac0f8da7491f7d3c73f653b780f772cf6bbf38c436395dad6792f89f16dc616216ea7344b1cad4a3cb63aa2d6dd48a00f1c96005a8d4d817a98d88ddc8f7f2583db72eaddb20b1253b561ec6bcd65c16152f3fc7a9dcef0c3deeeb076adba56271ea046f1dfb760daf4569468118ce2193f528792fb3dbc2c75ac752cb60a55200725d11557c2e7e0a8bbaf4f4be834d7c0cbb3ab68c6acab50a5ff0057babe6297d4ff008f39caab7f535ecdeb381b597a686e7eab5481166a12147f5bd65b5ab2e2ab257f03a4848f075da8ee2d1a28dbf277f1c83f6a972963df42839a63df90d38bf2c3ab827e3c721e529c46e1f54035e7edef7eaccbdff46e7dfcae030b938cc6c4535a568a1dd04aaae4de36be85039b7c861b80f216a76462623f7f2db52da0752b1f75e254abe1af073a8dbb1d1788f6b5e6dfb27b4ec63d711b9ce45056eacbecd8fa96dcec71abe03e5eb508dc6af454ce3059af36e6fb1bf959b7b5ed460312dead3f3488520deb924fc45bbb06533b467512ccba525b9365f2b0e6daeb081d747fc759c189d4191598b62b7c03ee901a6e71133ac64ad6bc857b6bc90a31aeb063e24e2a8140fa8995d3dd609b486201eec42bd7989a392bba9cbacbae4a5477134de1df7f07f1e2edc16d6ea385c9d645d68af0ed6e29ce3feb40ddd75cb4a655cf74c7c53d3ff6aa9f553fd6b27d354e1d6ca6c196411757ac82ccc72901df6c9b6f4b2bcc5e4a430f7cf96e1badf4263e7271c970e30cf1b2653854c25d2db5a5af5d09695a904e2214531b1d08af1b8b1a51a6462f525b51a52ba2c65aa8aeb12ead1d6cac54d47029f119977915eb24d6cb16c151a8f265bc703bbd9dcb1af18961a03c7f3388f01de678d4c5b458be36d2b68a6a5a57e1dd6044e7c87d3397ba81607c47e4988e0fd30b192a0938cd79cc1a59654960aaa4abe29965f554df514c162b0cab3a8f8b4711a1bf635e7dfc66f484ad726d381ebc9ad2b1898c401f8f3ebc0f9b5ed76f773f20b3d15f0aa6bbff00e46720e4be1beff07bfc7ccd6c7e2649e1763bb587df3f077e7ca4b5ee41a59fe47f4c6ed47c0edf03b79f5e39140ba22851f2ff00ffc40014110100000000000000000000000000000090ffda0008010211013f010f3fffc40014110100000000000000000000000000000090ffda0008010111013f010f3fffc40034100001030203060503040203010000000001000211213103124110223032516113204071814291b1335052a1236272a2c1f0ffda0008010000063f02f495db433c0b7aeac2a05cd1ecb9ccafd5caa46395fa93f0b75d28788355ba7657d7568a816e859b1b13e02e427dd6ee1c0d542830b7312bd0a87428342ab559f08d7a2c8ebf7f590d5eca30da56f3b32ccf5bb0c6f55abcaa3085fe427d908561b28e2888242e6c87a2a62e9d56f1a8ecb2c82b79a429067d3d51cee91d0201b0b76d3543c3a4eaa30c7ca92731599ca802abe8e50493f0b75d056fccabc33dd50ad0adf6c7752c74aad566170a1e330599872959714477f49256ecadff00bab85ba402a1e6689dbb1d82ad7b0b2b0951f5745ca98f1a141c15827b1e2482a70d657cb4a96e25158382dda3ba28e57aca51216aa0ac86a34f445ac16d51ae67f4463e4f4559255042929e5b40aa11e888cd17089fa9b450f6e529bd2564376d367676cde0b76485dd74eeab7eaa7ead91a6c0ee9e861b41aaf0d837965075aadcc03750f639beea88b9df64ecc085bff4d91ad0843b883eebb3f64daaa081bca0b4b5388229050d8614eaabb330b7932a70e3e50ba950de67286d5ff513a2a99595bf7511bae5e18e50506a06c0d110c32135f23aa6bcf5443d1a8d14b5864754d88fca766c3699444111dd183fdadf12172395f6428e9b23541c355f1c6aacc50c36a7baf96ddd4b8c937401b7f15f80984d027e245caff8a2f6dbf0ad5fca2ecd90681452f745b2a376140127b9595fee162786dccb3d27a05a21110a70ce52b2e2b60a9145bae95be20f901238f29d8d224d94bbe94315d736081332519ab9c64f64046a9a1bb329b14e14194c2a8908ce1dd07861fba3b81bf2a4e2106d455c4872ddc73281690f086610a68aad51450e3baaf2b2ea17652a344e69b8f35f8144199a653305a9b862cda945da7e10c47728b27786ccddd5c5148fb22336ccc5d1ff00ab2b7eeb2b65ce29b9c1cbfeaa499729631aa3c3c355ca3d82a6210a7303d8acb89851dd6e3a140dff00744382d60e89af638517bed6bb8c5e745e23bdd3b14dd175779469358d50f10c347d21783851f0aab3bada054dd3d9521e3bafd1fed6f10d1d977d956aa485fa8e5cc55cecb0f9523756ebb30eeb2e2e0ba3b2a1cbd8a8c3766553b5fd90e24ac835ba23f93a3d93582ea9ccbc57dbe90a1bcceb2f1310cb90366a005b8f0acb914b1df745af09dfd2caf8829ed3af14d56262c764cd6b24a1d8273ba980bc16732f13179bf0b28e4f65945bd2b66e134a8478902e689c1059ba94032b0bc6c4abbbe8a07202a05bd30a7c291a291fda8d0f11ad1a270d81acb5a5079b8409a355b8f7f3492a338520d26ea0733aea62ab0cd90e1bcf452b290535ac11eea710cf650153c9cca3385cdb6c7ecb90ab2b6c80ad0afa2ab8a938bfda27c4b5555ffd2e6b15505c538fd3d14814d8d9429c37cdf638ccb90764ac6aa40a8d1730055fe56ab7a9f3b2a572afd3855ab3f0a61112b9eabf51cb527badd1bbd96817328a2a50aaef3145c1594d9517862ee400a5157eca41cadd102e39c0aa0470f3acac0b33e3e54e503f09b8f84289ef1d109bececd56d94aa0d3425428fa5db27a84159346b17d840d3ba962f0b1f9baec2ceb508fdd4a73872b565c32839f6fca847a22ce8786ee811c4359590373a1fc85d073379874d511fc86ba21b27aecc9d11cc5d2a87d903aa775527a2610a534f7d84ab5d0741c8745e20a2a058740987aaf0c5d7fb396f111d149b0e88b3070fda50cef11d0856e13bd962f55bbadd07001cf77f4a5ed0deeb21d2cb32cbb2886a9ae7595e15abb0a25427b7c8e0e14ea88692b33fedb257b2739da69b2a9dbb2bc57656cfd2b34422e3c3737f92cbde166187213713299d42201b55109ec371b2742ab651f642a28a4993b3204cc31a943d911b29e5803e54507ca2b12236e570908d5d5d10168b2cad1df8799a9cc3afe5551c367dd07493456586ff00e54d90bab74db0b283552e784ec7361640a0e6dd7d2b7b0cfc2b1f7546cfc2e55d1478b44733c950de626140d8495069d25650e12b36249eca94e2676187acaee543a2cdd28aba202617eabd497a90f257d2572a375be7fb4d0d6eb524a0108d96e0668aed2561c9dfeab366a71f7a8baa3869cd3e4aedb6d636b250f3c79b2acf26f17503cd6e0c1440ab518d459070eb5e106f48e31ea99ee87b7a072a593c59d333c2389c5baf843dd0ea871dd98e89a62107caff0057f01dd546dea557cb27cae76964ce9250597a71eff2bfd8528a3a2ca6e0a07cfba8967cacc3305bf3e6a94553c85d52d77f49c02ca7d016fe14b5d2b2826e9af51d7d07898b6fca73b4f2126c1652d969e88b70f799fc7553040775d155f2d51c5216728b9493dd41ba0c3f081e2495ba285677d54d98101d36f52abade503f4ca6b9b0abe83c26ca0d2135f5a2af29422842caee0c2baa4accee5519851784cd5446daa9d02845a5a1653616f439b289db4d81e35ba8f392aadcc0ae4aaff1b04aa92a0835409bf9328f6436547a6f755b2009ddd16f79a084e1bb459dd72b942a5d788ff81e52ff00497f3c7d9398ef8591de6add65c81c144405dfcb95a547a8ebb27450bffaaa0aad4291c2a2a5949bfae943a296d143a9c0395dba1656f2ea8b858141f1ebe47d96e9f82b7847ba963be16f8852365e15f37b2cac6bb2a186e300dd002cb11a6c53b0bbaffd46aa867d6d543b60cb15a5964915b1527129d907e6a7552f10a1a2136baec05c60adceb7542a315a14e1516fd23554aacd96366f3aa76f37a724acf29afcf9bb10a1d87fdacacc295bd03b0d80354b96f51bd1076783d153782ff2e115330b71d557403c29cc1788794d945d59506ef550fa7754f4aed818fa42dde5d0a83aed388ed54875ae4a195b182dff00b2e5d9512aca5ae840b9b2b7689a04972ff2bbd82a0d8730418fab5461dbd286cd4a701f0807028c1a774729b5429343d150eeaf0dac393f2b36211ff154e035ca3a2b6d8705959e90b8acd52fb26be62aba1eaac0ad020e7483d9437830a8b7823905fd306b9d0e2b9c2cc0d16ef20bacc7dd4fecd25d659c8dc0ae72cd420d64e6d166c5fd9adb0e0b6da9406c9f596e2b08415544fed386d9fab6b7113ddf4e9b6ffb28ca2880d8d4cee3f689dad41bd38d4e37ffc400271000020202020300020301010101000000000111213141105161718191a120b1f0c1d1e130ffda0008010000013f2111be3e99e10fd71a2fa20d8f8971971cc8dc2b2135b26bd94e528f2336945b4d13378e24bec87b27c12fa7c3e7f043762653d9eb9fa474448f1835c26e4d9b2bd1be67c95d70d1f442e70f67f78c9d588c8983db5d8c9ae368ac31c4dafb0db17c32edac2d0de871fb2f1892943668566cfa8fd9e91fa2bb93e1058bd8ed1506d23e85ef951e38724bdaa3d71d095b89b3c6c7f661458dc08a6358a3fbc0b1f423a2543e58fd8868d2f2e644314f3591fede0910cd6ca75bbb1f9453ce854114b63ac04ee9f45e8ea592f48d9f925ce2b8f45ef89e3e0b8c69b15e88f1c3f014f6cbe8642112a2da2f3edfe8894e3c8fb6c45e399e93982610a7a4c54a14a7fbec0f524dd13c2ead288589a7fa3df0fc5dca6487d68f6528d54a4234f0830dc8fc82c373c0d4bfa1b26416ac89f6f28530fe8b660fd70a6483f3c6f8b4294cc8be70f193475c50a06bc0bedb3486d901491434c61e188a259040e6864d44938f99ec73ebbbc4998cb7a8251acbcb827152525891c4d030177fe9c570f5b16cbf46c44cd3e9334a3221a961252089ab16c442864d21a81e4d09618f933a4055bcda37259f46c53c6fa3e17e48ac967c23c917cbc17144c58487be33d6079874f2c64c16fbc325a72eec933534b56332a138ecff007fc12a3f05fb12b284a4c8ea4e0ad05d0634ba810ac4a89367e41c2afd10e6451d17bfb721496983cbc6e4d6fd8c3f18d2469163db1d593256f04ba94dafc9926bb0dca5660b158c4df0b9be245c2899329c0b44de5d0764ff00ea179de3fa42ad59e5b6399da754342142d9a4e708696e7bec5937116d901b2e1dfb644156a567fd6664edd3264a1c3fb25917ca083d37e894ab2274d989bce86f907068328423a4bf6169db42117a570454c29c6ccddaa813ff004f24a479dbf02519b264577c4f8e342e1fe095183d3f82f449f609a9055322172aa4b927f27a10e4a9396e05694f6825a3a9a428978ac05aa09943f51430ea609bcae05507b85a12d8a289f13fe92392b242aba2b7782541194f098a4bd78199c2992b04d63284e1ff00e956452354a11092d1846ba22efdd0cb1c352854d0c7cf46693f02e53b1491c78fe13c593c64ed83ce79f42a098547d1beee1ca1064c89602dbec312ed329e591086ad82f43a879b4e44ecd5428a33554c55de8f30246c4de1a663369523e89d0fbd99e36cd6459596c84c80b2a5fd18b01aca813469b286235562c3c03a0798943fabe262072d34e7a634964b225761ed23ec1f16b26213597a0b1826096f65f12f8f078208f03e23c846c81ce09d2716221f5cf620f71294eca358284a1412bfc8c7a835319ff60b990c0871e20d53957693150e95613f463c82f5b24ae59229549cbbdd106ea48729a1ae3d5ed2e156a5f2869ce6ef05d1fed8545b52dae884f65d0f4df82e8ac1da964954afc932c7e40c8263ebd9336b0552a9912e31a1e4884da17de67883d915c495d96a1fb249416e4916510b246548b9f42d2ad53dc1120c90ca3a5b2368cd07a8b64d2f0f037535f81497d27fa418c21549e72217ba424553574e74222664a12c27e8fe56222564060c430cccf313927ab366f24714136fa91619a15a99f149792378976879e861604d0dfd0917ec1aa5909b81b4ae3489492a858b9e3cc0fd1aa27c398b246e8d70c8a652b05e999111da7168a37548bc16049a63d0b674b0891d03bd25e245a7919ca288626d9260b74753ec849dda45dbb44c05a98e9a69608608910f247a681b820a6222062541fe48e88d3350d6344b8446bc84878547914e3127f04ee49fa18013a731fb3a15db0ca0e9b8642d959092c2aa11ee9c1346d2d32551f49d10b53f7f85cf102cdf0d084ba1ec4ed318bfd1d97a444989b69f02bd13fe40d510a5515e48124a5e1bf62e7836adcf6421496988929a346c5a9447a1e0cea437a1a341f2a98e5d9e146386fb22b667b1169f304fff00909d24b048b528db11a11864f3024f98992e224c33a3b7ac8106788d0c8a539a11e1552547b16b9aa214c2e88a2fa3e22cd1a14be628b2aed8225994fa8d694b829a0bd95c410c965b0b62e0d6ecfec5ced7d3744c4c25b6b5e071742fe2c91d70d13512323f8d7627c459bbe0a070c759a1c1ff00b63f96adf6f4484371a522d4b769a2050b13e6477416b289845da14d95a4e76295e35027ac9d945762c8d922c97c26b03b54b1b119c6e86f09340f112c2fb25334884415cf6584466ac27231c284ca6441d84121f847b3e90f9fb04f8e18bf84c7933c46f640a27c7910e425b3fdb1b093a875242124f3ec5a1a7922cc096db1f043e2ca186e5df04cbcbf0650937a18ddb95674a446be934a8af8d409b344498c8b288450a4ddb2bb3751f8198d122c95c7e44e8cf9204976427982e6b8226bff000437dd2bf4688b88a16fc7fa0f199d86a33cc6bc144eb284fc7f0823cf0f060791bf82491b4250bcbb2bbb37d0a75ac9a1b0a295e4508841e64ae17b36e88da5c2366cf5c2778fd8dd6608761267242af247646a526e342cc941723b44fb892787e0750ca9a27a1d32450b82c136b8dd7fe8f3b24707d3522ce09e2c97db3641f290294f2d47b2612ab50be96b2b763391d30249412e86c4b64a9883a4da113a5793393bd4944c63c334ce703833ee109a499e504e98f7b2e3ac79c8d1a8caed8e4c43a54608db7e704d1f2336b93e8738c8c391f698812cd410863953f0244ab5f41a22577a25588d408f8883bd95863313387e844a1d0563c13a9a1253917a2f8fbc7b124642313a1508dd1a746ec35a6b2063509cdc10a24fc2bfe1665ebb1a433442f694c7bc8ddb5f82d3fba3a3a9a4f42642ee80906078e8569a0e250a35a8d60826e67b32f2835120471533a87da963f397a448b265117b1d6dce2c091393e9b5a25154181f6d52da5162261122090f7468a1997ffa449927a964a863656c5ee3488186535c493352633045c91d1427c3e32399a9584472db21377d109eee37309d919035b4ff00621461d50f176fb495b624fbd7d8db4f054914694762f3a96bb3a6153b209b84a62dd7abc311c9f488892228fc8d91292b8556e08fab679130bc781d4b1d3fa08a53395de07211fa04f8f22e5228112d04b5775166108ea9bf036238a7392753b44341168b43c1c38410a7cb9a18ff00229206bb2f6468899f64781d970ba22d7ba18b562685e11a2b650dfc613502d55763b21ac8c62ff1d143b91e1d99f70b0d08ac9c796460c443508455bdc3426ffb22e27b60953c86baeabf24ca9a7654109a3624323693815c8d9cb08cba4c4345d8876f22a0e04d742a691a95305a5605439398f526514265fa16c49684a95d190fe49b76c86cdc02ab0ca32291af08629ed0bd95a7660916590bb1145943ad0adb3350d64eafcda0c720c76189d6e612eca384317383a5224a89a1146ca1fe04d406c83649dd34d54a3a61cd8929e4b7ace9714637949a19bb35686af0d0af48d4af0258aded2324849e141b9fb0d16198634869d9a4c23006d52d8924895ec3b46a8e1a9c089cb27d19284f08bdbd25c26d19339c0d115c7e4424a76ac91142484fc8b3494a664d7ec6797bd21d1e23b0ce8daabe5a3065b510c6a315c7686b22e2e864509ad86894a13c87093a484d8fe47e93f4359c3d9bad10a5b09408876ff4465e4942d9b488243a7061b81a21d12daa1b4da8e982599d1913ceea75738143e6507814ad31d8e321ca2461044a5414203c83e1a1745cf0b9438d8a8e97327b7ab1170ad92d3986c60e04c8a359660c4615c6c8be8dfab13096ded812f86bb443e7a22fb19c1da3039cbeda72fd09425b0b0452a27454526513a997ec7d47b9a0e3e8e9a2a8f01c8661a472f055b4bd16c45d10d160fc89149889c88ac4db9692174959fbc5038c5fb04f49642db4930b91012aa6362983e489dda15cf18e27c1ec422818ea11b81538149618ebaa4a1cec44d6e964eec8a7070864d43a729a232c6bc8a65f9a27365512e0505257866faad4092971eb9120c4dcb9189838499970c0bd0fe86abfa439a747b8d9cd0c250228ac9045c5edf0862a7c31f2e8b74cb87ffc15a5b43abfec652a5b1209291b8e3f44f0a16c9e6472d432b3caa056f08d53270ca563c0ca13cfd17913599db23dbf278c6bc46dc5e066ee5bbd8d41c8c0902563ef131e4f22199d93421a457d22770422aa476105b456272fd094186e3046f65c0ef28488e4b23f505742e3dd0fa111699710aaa266250aa63e8d09389e14c938354577ca251e0a5ce6171825088632085a218a4781d40a886dbc99b10a4ec89914d2c51c4b67b17b35fc5fa346a0bc4491d97aebf03dcdb2148967904128729413c7e052b04b9b135728c287ca70a44e5db3c1065e468d0b193f636fa1fd89349981c0f5a6c74cb32a5302372e34facea1a21d4388e702fe4eb32c99c49688805694c598b9e312a05311ca9206dc411d0fae8783e0e7c93ac13f45922cb462b49839bf0385d4a2614b65228099a11c68d9913834a9ac189a3d3272ff00636dc3864fd8f3a23fec1edeede049b79e8fb18c499d1aae371c4a249f02cf178225b521054c95ff003c0a783a2f0331151f83784281fb3d8a382f64c7d1672d05ad278686782c798724e65fb81c5dbf244388ae29ec7587f81c1ba7d8b46acf7a104241b9252620a7767d76db0859b4e534c52675b88285dab176d0fbe77c429a57c4f386592df6487494372d2b04e30d171b2252b7268e8262e0f06b219b56b9437ca2043cab14fde258dee204b56ffa12b912e13085e9e3933d8d2f49753d9329fbad11d25b9537a19e1ea486c484e929409f6304209e23d13c41f92c5e4c90fca70ed12d3b6bcc0f305a4e16e4b4f3faf5fb11a926e2e72fd9083ad9e2883e9979e1e7895c7a59f927a4b2928f66852ed2b7d15c7b42f8b082609b16c84db8524a248836d6158a9565f66e54a78ca1ca12cc62747d3e922cdc0c8b2399f2410be90f4299cc1ec8744254b5a25769245a05f438cc93f65a19098fd0b83dac4fbe1a13e7eb422cedcaaa434a24c2d5610b444a54ca5a5e4e449fc9e47817eda1f7f743c35340892ec4488d647c7a93df44b327cfe13a2e097ce3426b316398347b1b4f625141639dd2d0ed12f191789b47e0292598fc9104a1ce91e841818bc21c3c01e86a944ad3868991db3632525623026b427909824945f47e866c42a1ba5ba7d264ff000884cc0ab604e1460c98c12f8f8407a14cf67ec9f047920533d8e7f8cc2d21435298c09aa73d0ab75b362648b6d9b8fa2c61fe48f1fb1be1feb350c9ec70ba9c9275e7490b108f27639689d47b1110c9a81a88690beb12ff00f3d90b2971a21907d33b171ae5a93ef107b1a5d9ae88f636411c3514650844faf448ece13c968b16e3f23248684d352a1a35847b35c4053084c87547529c1084cf3588b09f632241f51241776b4865ae6fc094ab544c15a2cbe2248e6048475c7d3e93c57b1f38ec9718243ce18adeb6c946d3b2ea96d61847e1a126690e5a1099f3056c659087f9256b2611637040876dacc090a9fdbfc8ed46cafa43304e60b8c7e19704be89fe13e4983cb421c904a98217086575c3928a5d0ace881981901b53708c26ad53a646720ed3c4f62730f28f8b8c13f9311c1bbefa2fa6abcb9326a4f5d0a1daae87b0da1f8e217912f3c3d112440f88e3eae1ccc14410b11642d709be874c92787c273750678931935d0d76c9e71bac6e52f6c380e1f27c84c279d782c04d7b1f9577d0a65a279705cc3c0c0485cb8cb3101b0b23e9a216f70b6d1245a72920397483e4ac5435123bae028247ec9704aed320545eacaeec9fa5bb2cfa67cb153be0940bd0fd09b2cde0d540b1c7ec7288503cb435b25294d90620aa46dd904952e0e18e6cc3284ec36c4b3f0b2f13887b17e14059be4504d428192306d896e0982e34d450a4cd337035b64f4f0cf2121e52272ae62879669f63c8a1e806835246a4b16bd413a1d10e49b27c13e0a8f248f43457b28882b88259629e876e12b3a0de0f46b7a5231aeeee7d1182aa5343f050dced0b69bd34b24bff004c3c3c51d6c73298e0c4fa474895258cdc2a65a1aeabd095d231d21b824f746176d7439466c53474ad7a1ca29d370cb0d265f046597a98b825b54da23d1ea22200406e9aca63fd1827cf1836267ce2b983026e4667d259fa3d10a6eda64a7e5b8f246aa94378648125bc92356b51a14b1a19e6d3cc91a94e3e06073e30b49657909ea1f58e04b42aa3d864c7c2a1a8760f0a8b002b991524d3f1b1ed36f3a282c2868b202aeec7556c415aff0044c8f621d1d0c6d7147d7c28ef88ae3d1ed106cfa222cf9c6cde4d391163a13914e59b6919b655494ccce2029e8a7ec19beacb6cbf9132fb161da28492a61414258458120d05831e388e82c8615e08ace898dbd5082a9ca6c69ec3c63882b7e46443bcb6685c7d1f15d713c492c67a3dd992066a4670921bb115bf4f43babce1e8965f485de11c934c50d21f780ac189e01274ff0026d9238fc0a5bd0a853ed1169e53942a2123e912f264c20794f1f10d133546097cbc1a157122f43f347c6572914ed9d0281e89fe85c4fdd898a4f297aff7e44273e164941e342a732c538d1fd9f24d60f6f8c882fb23b65f6a066c86ac9f823d18343c71828509f665f13e0c89783d22fb3f1c7bfd8fc11645594a18f4c4fb626163258b04a1de541cd09e996e61091856b8449eb8fd113be5e0298e2a099aafe0be891f82fa2fa2fa23bfe2ad9433f3fc10f62188cddfd8662e36163024afcf1d8bd112c8cf19b8a3d10cf6cf46ec7e1f1f9363ee38a257124f08fa4867f5ccf6670971f4438323a221a52e1d1823ebfe0d5fe83d027d3791432a3854648f260b7c23ef1eb949b1f9c9380bf8ec6859e2f8bc3fe0d283e98c16321e8bd9246c3b8c9d0d1a3926937d8ca38d5cb4bffc1464c685e8920d283e58abc9f1f2bd0b38e1f13fc2064304470a0f82c8fcc8fea42ba8cae17709e7c83743a35962263446e489634a678548fd0f1df305fb236a2fe138c9fd9f4b83e93fc6f84ee20cb1c7663cb1be25a5cb6c9c8b14634431b88621930908592258dc3866742f251e79722f27a3da2dbe82771b1d1221fae6f45aca177a28659fffda000c03000001110211000010420138634010620910b3473c90cf0cd3031881c22850ca1cf3c20ce20d3430c300f28134b3c920e34b2cb18d0483cf34d2cc3842cd3022c90c638d3050063051c300d2cc0c70c228e0433003c52820c138808934430f24624d38738124f04f1cb2ce18114c04624f0cc1c508834f34e30918e38338e00e18614e1023c118710310530b20f20a0493473cf2c600e3c710c18a0c21030c12433cd1cb1441811833031cc1c410610a30e38b2cf04b3830463c13421463071423ca24c3c638b10a34c34f3c334e38b1441c33c61851823c72cd34930b10a28d1021c620b2c20ca1c51403cd28828a14e08f34628700438b3c214e3c628e1c214d14b1c130714338128b28c08108604b0041052cf28b20e3cf00804b34530f2ce20238604d08710134320514b1ca24814424238a30f18b14518220a1070c13831892cf3ce2cb0cd3cc14b2c21c53893c300d10818734214508f3cd18820e2c720a24b3c130404914930800120f04208910f08638314d1c600b08b14938d14b38a3042cc2c200c20f30e3c010418d3400c60801470cf3c80811ce1cc20b10c24f08b24e34d14d14400428518914638330220408a24814ffc40014110100000000000000000000000000000090ffda0008010211013f100f3fffc40014110100000000000000000000000000000090ffda0008010111013f100f3fffc4002610010002020202020202030100000000000100112131415161718191a1b1c1d110e1f0f1ffda0008010000013f104e6e27e25ed717b0a9542f4c041035b3cc6fa96bc0416d940e62b1b62d94508aa218d8f529fe15a3e25c16ab8875af139e2c26489f1349615408951de2a71077719aa7f134241c2971535486d8270209cca6f23c10c82c4b71666785ab98bed10cdfe2277bf703101728c205ff00738101d4aa3a436b5a97ce88dd19c45d5afb97d38963f826c56fccd1e621e70eea0545f4af32d295ed034b8e085ddde222b49b59f2977c928378f128afd1891406ccbea14e60f0c6ad1b0e5f1fee1dea10a13e14bcabc4405dc8960a77fa9754fd0819fcc3e90c14fc6e06c0f35803acc30a425fb2165d4d839c2558b8b42528f9bc3302ad4bcbd4460acc94ba22f10c99b0752ddd31172c61c17a61ec798db97e650d3122f26378255106326e862a5ca9c4daaf5e62e6a9f70bb1f3cce4bdba625b89d9142aaed0c3d25b75178216f33d40eb114786419881a8cda61f51a18c01d5c756569ffa9583dd5b612ba8674836cfe6a53d100696f8e90e6e4da5c99cdccb84b447e92e0a518c1f58fe6242dd88bbf033c80ee5ea0c196a838424057a52fd4bce9697978804cc2ab062e547dcce40fc3332bf245bd3e66337f4a9600f7314a969b4b5a303fc2ee66eb3f336d57b885b6b321a019ca456b26dce12eb0ea50154f3122bf24b10bc5fa8da348df72971b1433b5a80ef4294c9f283150196cb146eeba2a16f981de39f88392ad1a9d7e6160a0658fc770eb4b17c5b8f5368a43f8a8e9166cb2066fc408bd1418a44d2a1007ee1935a583f5873ea0ea7ce692c7fa11aea8b5a5e18c8e16ca9b13ba8642a90e4afa822c2841f622a0ca29686ce68a27f4423cba9b3f50bf349d45a53a994c61c7994f3bf32cd3118106abd9ff166d14f894083cdc563f7115728368be258f1311565e25e431469d32895e5eee65c5fccc98ca138b781dffd98a91161afb65c81d55f531585934076c74a02d2026f3cfe2086d2aa01e570365ef754785e6565e8b06844d2239a8799c4a4b183ac4b661a6547ce66628e28b3e5e65db0156958e667766879bf503a70db553f10c260d094fd46cd3761c598298539d05d54146d22fc314022828bf9182379d2e6fac0c26e48dd870d29dcc8504165de2605cb88bcdf750c1797888f11229312eb881793f08eeb3f2b110e6cfa97c9f84f0897fda0f3589c4296655caa75b83b6b64bb325e0234e70bd90e6c5066c3d172a002df51eaddfcca8b1c8c7671464bb988084aa6667d00f92d5457d782941bc3d5d5453312a58015c9dcbf983b09592a5b89942774792d3ee0cecf72fd1108f09fbb9628b39a6229609e97108615a4ecf50bc4bc37fb9c9a4483a8a6eb196ffa306f4d4d0df8865c73f41a863d0de980886c6128770068ca00e6eb26588b1cb9f12a20bde08a8f32f62a0e5b829c5ce83eea393336de26079948583eac80b88e22a9a7702cc0cabeca63a470e6a53680b6b2e7e25ccb2cb61e906a0a5ab3c5c5c5bc4344eb8d71d415002d6c42443cb3f11c8d903c75033c5080687e7ff66218215c159a257da0067460ea165e45b7f588b768342fc0c6289b9e0b512909680e1cde6260ee6198ed416a12877e6a58c754631034864980aca8730a8e68228ce1d066e18fa09c93c0b29962f82fbaf6e12b3aac818366e40e2323f8e1a9684c6ed4715372948592193388a58a4b2a60555798fab88ac90353f2941b05fa9657333ccf3bf12de3ed89ac97c62095780cea644a51c32f5ed58ac3e6e656a6882edd09a882224edee186e9ad73ac8c04ad1e908078cc71b3f71900018707c461de1150428b5bab66babb262aa0d94506056dfc4a8bb5061b1c7fccc7aa6822bfb9b66310946cb23a30406c78802d0b6b60f4c265b1c2314fea336d40b1c3ea07f4234d2194226365bb3170a9cbb17f999a34adc32e68a2e3b6195695671088b8bbaf306162c64e13c400141421abfb959a17bb02acdcb5d71cd429b2be582d58c773f2415e108df89a382036a7105c604bb22bcabdc12181309dca442f1a71c984d8ca2da54549b6038e8bf9804a02e3d80e86b52dc40028b1cdfd3a8a7da6b0f52fa97856d9372e209e93d4e16e48cc8248c170ae655f7c0ff24cb895655378e31137c0e53cbdc2296005ff0069471c80408bbdf11dd1871fc66be298e4c09b74aa38fcccce8427318233dad534be29f98529390c69bbc402dcd1842bf1160805ad87d404efd92a788a83323923dc5c194d2d320f8942cf81834d84198a9178a2e53be43e92d4a40c02ff0052f6457412d6131e628798d1aa216c73436e2155db9953bfb805731badc14c4b3312cb0f043dd1d0e6ba6575a6959b7cf715a6d177380c1ed585b27519b9826a352e60976d40e15562d2132800c9b1dffcdcad74b503068652f083efc8c2bc2a0b6bbfcc654b76c856c2bf712bfd7bdd7896478040bf8cca63901d9b3eee063972d19f9c67f0c700e2a61f1a988111262ca6b39ef8d426ad2908c14dfcca8a637783d21cb3c19944ae95407148cf3609fbc783540c47109c960ce7a898950c3520795335e8ccb428cddc4b9d390e596cab77978a94c012dd7e250003e4c2f588d178a651a18d164deb5c1187198500f328b7cca5b805914f7057686c64f32a4700621d00a05372a26c219a35306c59ded31bec12b40ec96c960037558fa88ed22a20baf98c9341d0d3e78836eca37ce100b02d7db032605dbbbc4488429797fb7e48b386b11c5f32b8247667b424669d8d57b7c7b8278ad170bd52a779a8e70556a53eec86c3206c5c5f8e65842b66a786e5b9c41b6c08b6894a6aa6fde61571bad665563810cbf3b22466b83f1515f735bc2d269dac3adc6468b7cce3274b77ea3617537dba8bbc2f8b350734e7ced668240a64f11ae1b5e2d8398e112a4aaf3323289afe480376750cae489654c395ca1c1f307de08af131872fa82b63b6391f2436840e5ef51e903c980bb5961292765d298d7220c0a1b43690d5ab6e054b34a1fb646510caaff144e857386bb7fd6e024d553b67898d8d8da8bc4a54044d7c297d51070816a37e3315215a53fd4a00982088564a9b902e92aeaaa39b086acd6a53bc7b6f2e61629bb468502d6c06850e2ec5bac19c7506d02db549ee3224a8a9c3b8987725069d3c973ab6733eb2fc4b6b2dadba3b2be388b085686b43243869a8bd798c2ca5331c9579986241b45276d4e16d06d7730260dc365a7753952f697f102b83dd4ae4216e91a372fbe1967aa599ae65866b16d751a5a1a299231daaa86122e776f6812d695d49c08e60a8076abb07bd425cad63d9c14d121a09a588957708bf039ee0c61e5181df572e6c17aaf962872176ad516b1ee2d3f5a8df91d5544829e5b9cf301496b6ea54a10aa14fdc5fe94771936daeacfe214597fa7f52890d5db289e06df1531e93202edea284f909f516877d261fed14d405669ee06b5e97c8fee1946817937956b352b30a5cee114290568e63a54bb3c9c9ee71cc0cc366e34b6f997ddde497c2c9bf49751723872415318236b1b942fa21b8182ca2c54ab74891dac700bafd462ecf084f2c34fc2b72aab7d6e2a62dd537bfeaa5345500bb0bc17a961e20560af2dcbb85b555de1e56a1b23023f703a15ff7528b56a38ab8931555e6645ceca6e512aae6529b0f1103cbc4cd4ebd4b38d40b6cdc05a2a2572ccd9454beaa0a7b98b7e3fee8689725cfb471c745dbea27079c823e73610a900d1840e0acabec8589121c856e058382a514118b96793f70b0327102db7a4029b9286db950542136acfb8b69c6e88b736dc5d406c8278f10801a30c6712ca28c096d5030b75e45af2dc142e736edcbe2aff102ee001392eaf37a8c3cd35cc0e3edea541a6ef64cea02ae680e6ef3f99cd12193ec8160e31ae20448e1a95e257f44c1cfca755b6170f4cef2ad752cd37116ea589b5ea0d3a7ccd1439e730c69f9462acbb3c1a8e360da701aa3da12968f1bc886904ab0618d4a2e0b20d7f51b2573545c6f541ee05d365862bea500562a1e6e56f772cbb373259940680cf1529595419ce234008daf065460835dee5403c7a2bf31d62fab515f7e7f12e0d02ce9be655cdd071679dcc089a3b8d3a0eaa556a95d54ba1f89c42e77301da65ac45a52c72cd8f530ea59c8a036b44712fb7ca5bba3b960b497d328d9a7010bb2af132b91af2c466daf71ad3383335558cca3b8f225440b1b316d8afb8503395b18a5fedf33276d058c5e3443b8e43e0b37f7382ffb132c711dd0f062535f8416f4a7564b1e2a1a617214e0fc31ab891539325c27158d1f0fcc6f0e86a977ea5e2a0ba9a258b2a54326db87149b2ec64c5cb3e4016ffee214642f2a4a4b4aab17148b76f8838ba9b2fd73393308790dd0ea2379fcc48b75e25b6afcc2b08a94e45cbb4c22bcc0c8c3d2096c9c4c1947dcb39619d4e665c2a3146026e79cd17b8de32c66de66582d190bf3b866aaaa6eaf83cfc45b764c50c180f8b8cc564a395d7cca7a09415794bbf8814b456ac0b1035940c5f128a2085d5bab954972461605fa996e55988d27fea4ae10b0dcb3b3597aeb1006db06bd258adb9fba7f11ea728aea5fdc31453958a952301f1282b22a95206a721c54130556aef507223b1711d2a36b84e878ad140c56d1662db4b801783925046e0e328cbc4531864c1142e06b9266faa260d44a2b89ad9757b8b41a3c3ed2ddeebb08e6bba8f474e8947ee5906320c2276347179983d6dc34d9523b117bd4c56c12d797f5f987d8c5b8a154ab7c0384d460002825a7980d0d8782d7f99615ac36bb6298a145177325fee036d44a61f0a607b3dc56d105166e25712536c10a108ecee096bcbf04bee0074cc63c805146dabd3b8ab2ac4c0a1a959beb7ae5df5315528ac1166846738f54dbe65e4aae8d6e6a14b3b3d6a52e5250ab7838f0325e3a86d8a9c5cd798581414b6f924c28030f25e63001ad66d18a2aa5783c40b2e0196a32852f2d788719c655bb838bc086cc3cf50369a0b6c3df52993a143b609c291769e5b01a8e2e64557e496215e1715283c6a0140ebfdc1f9c169e8fea509c4ab8a38556c01cdb9fdbf11abc8d3fb7899e58143c8d74402e9e12cc5035b97a53d270f52ea0b1174dae370383e930d951b36b32c33920ab21f53de25550dc6f6828be623b0b00da752b2210d7e4419f6c9778e7dc5b79e8baba7ac4250829aac80fee50dc22f2c1af5129c1cb2aed8a95a84060225e617d5ec1aa9936e015adb51477ae7d196e1c36b919722d75d7e28471566ebadc610ac2a30c136c0a584362146e5d544516403be4fa94551a6d55885caeff008731986654c17f73754d28b783329c17583fc1b99051de2a837b975c11a97a9cef0c3a33f89636d1f218da5d5751494b5a3c59cf13198b0c0f2cc204034206f8a0675096a582b573068fa403393c592caef86691b562032859d4baece0664ab5e91c0babf32e31b808c08b2f7185434262f83dca2ea95346e305d68b631d1cd732b803dc12f128926a30596170f9b706fcb35e6097e8cf89a6a8161c9b5aefe65c61b05d900164086d4b85b0620aa789764ac759514e0aa26ec895308851d4cdc431b718253da297709b4cc644a542ef3195ca8ae2f302ccf164ceca8a77539d64c39816afba175e13cc2f96644f8573a7ec8c38303c28e63c0cc4e40cf064ce8aeff1002e2b89e96fd9ec99f3a46b3430bd7c4caca14beebacd479746391d54a5033229dce5bb6a15ec370b3a1200c5bcc58085b8d40364f752c4a69c528ae8f716d5e14ad9330c983ee610591ba0af53341457c89966dd312a6c882ede859c7f511e430362ab0a254dea628a6b72cc256518ad5fea2160e6df701b730c22e4363c076cc4b04bbb99ac7675ba2062a162720c7b957be4516de9961f76e518b815deb5292a2081ef885645fb51285d1943cdcc859507837303722980a3b948c7506a252e5381d706527006c01bff00aa02d01ba096f2b6d3b868d691bbca7ec80addd258b5fd5cd4f183ea03acc9acd2c3f680d39fdcd5dd455ba37f30a3b25e4b7c4b05da52389c17f114bfdcc3b7511a0af811ca095a26e59cee19bc53e22b330f08a40c9cd3a989671c618b16df7a85a1ba5f078ea28ea5a052f388372bcf418be398c82d5f90a890712bb202042eb0ca9e208d1e605051506203415a5986a278800c22619c983ee00f5e8ca75a3543888070aaee88f301162e5b06f015e0dfe7f100801634cb75de0cd9ccba571be6591d09c91282ab684d7b195ebe218816688a411b2002cb5d68801403fa472c0fa85ea2d872659a9c1a4179830225175a6e3c36071f300e682d1bcf72b0baa94194e8db028f12e86f71448b40671d4d318f72e43ec4516ab0363036ad1d185fab88fa32b554622046840e6ae345184beaa2ab58e23d371d2d5583cea069b5ae52c633822ea059b2a4dbcb1028a50b0a21bd1e70965e15ce1c453cf33aa3188c6f25e9c4ce9922b6fdcb38a7699f8cf3ddde7f3025780b8ddcd073fb4b42adaeee91b3415920983814194cd35dd8b7ee246a8767e2098c30531507868d33c56b728361101c73dcb380f11d437850d5035e22fc97577e21a82550d20dd433bc9552ea13968ab81c3352eab3f12d42bd8c43f22317844b352cda1bb1bf13c9cbd72600d118ce1d812df1703259bbe51931cca8d2c74cb960db25f80e6580406a9fe9ea4518aefcbc6008b4b4dc47e23110965895c8f1399cd566fcc7d05ae857dc7e41b684c90282978cee2323a70fe66ed4946adee1b7151e65cbb56b6a33cacaac840068a7010015b44c91b81703a600d543b8195df0c3d39dd47bda9c44a3716b57ccbcb266e825d688239174052f533bf2c658ef472811ccbd5a17fc2112d815801b32ed7702bea18a324742b77c13155b4b145ddcbb82d76ea579252d788595fc468dd7d930608e0a2125708edbbc4542e5ab085dfcea59cd4360d99a6c0a5eb91fcc1b8b05d3ac6a2529a3a148ba229786d1428b9e5b96451ea85818564648fa57e107c5725048d7240cbcecac17a1a868fc25068dd6264e6fee6d40fb65229c266016ead8810bdff282fe446eaacf64c9058171b7de3281a7895cfc9aa7ad0dbf72d16194062f78f6cc4ad76a83453b9601f340351a53b7c455c61e61781d13651157250165b4c08f30b4af922c6d47247e02ef539878a2ce6a5fe5254631c461a2b1dac73f89982d55fc7129abb812a9c8be440e1f08031488a6fe311a18c3a89a7ad65ec0ccb5db5aad4c5e7995926a3461cc179bb9614d5406a3631504a1366ea646d2bc40193f337895e63410b6f304031c692f77562c5da7f10125516aabf12e5abf72ea8a71a9a1c42982846db7c933cae6eb3a96e7a94f1299a6c4ab1e5a8dd9085620d0c28f3e95512f95a05e15b9646779d070e21dacac74f5ba9a786c5d0565ff00b8940c6a1bf110ebe045c026d644c5604acca0e8eb12a500583d4435810a7cc33a37ec87698a169d3007a3cc72a8bc70c342f2f39991d4012a97313b6c270411745fa8c025f9c4615d41c187992d0ddba80b95889e3231041014fa9ca5433f303863d253cca5f10ed98b2f9ff17517046ddcde27250f71c0d502e60f640e41530bc85784c9b957c3498bc5e598cc090e4dff006c4e688d2fd4201b99432f770d6abec43402b9789610421aa2f5c04a16d88fa951b23173479815c605ea18801772f87632c2156f3115610c4b2cde6b1336453354c1bad9973c427b7a6580281c90ba3132b26c267947800605bd0c150654b735ff00399a9015efff002080c17662159d4e0a7db1a317923d4a45b3340385f104686d961b9ff3513ee17c2aa2f8f98f393d4ba5b041151608d8cb505a48529756d6e11e08d1b0731b91795693c4ad1b597d4d4328a55644a435e820333631c4b32bc2c9b7ad66736fd5ab77303afac7f10f2be736292ae0ec1b83d00c45e2e6a0cef310ab3c543904394aaaaa81911472af640a16cf3330b045cac9578897516cb57aa56d32b0f6e3e20e15105e6b32dc2df4cf1051acc485293a09b2ea9f303533ff00a8dbc3ed494dbd8ff1e84acca2010a02ad098962c1062c86db5ce1f2ea12500d165ba7efee34a2218857ed9f15cc5a5ad02ea00a0f312f8b895c532e993e670fea5f5171acc162261ee730a7a8da85a34b645a6ed7a9d010036bb712ceb8708fb4ba46cfcad98ea2ce8f6130665b58af8252b4698230b4e88b57b10b18afd269588e4d19c171ddec5953de18af3052529696c6077ee01815941fa9a34f0531b6f4452034d4aba473cc3276af13a2fd4dea5a51ac9de250f5e5cc0166d72949c4e38892b352ee8cd0d97f10d6335e59dad8b0005b59e4e015e507dc096211c629adc1e2b5d74ea06115fc8e2195627767046b7fcca04f2d4a723a940d6c88a752ef51d0b4f81806135ddc4015939b869c039adc208514076405441a6e27937f3128c8c11ba02e8b5f1538600a9518fcc57d624195082e0b2d166d5825e940035318ce31d472195c025993fef3049565d17f0ea25d3bf9e218cdd7dcb2d9bf3165c9d101d40fca5077b8d725798b9dff0085b3e75896e0615703db024251c7711a0176e559576352c51dc4fc22c2b1772a92241867fa8848023585620e46fc39759c2b273c4bc3659961c198b288ce8a22156952c60d3afa8ad2a4e965aacd95ab8f30bab253585c254ac1620538e49cd2a4baaf9fe235c1409601709e04b5005d372822ae59d2e042a253e41ea331ff004f0f7f10ee7c2d97494c0cc5d0baaee94abf31ff00b2195a4c79f9977a20cab71079a17588b5c343a864d44cb820e596205413b312b3a9bb05bc46eb0f8635ee1a152d50c556a1850d5f3323f8018b257658a019898568974e570b2730d607888d1c56a16622194636abfc659c91a0d83503562071d30f0957716a9703ce219d63063ce2e30e057998f105c55d2617ea5bab0b9309bf621058c0b3598bf3926061cf4b5f9e26500642adc37cfe210c0c52bb800191e261a8716cda0f10f5fd4bb6ee00731392bdc342dff8b71584d8f33129bf32c5856d0e3ccbad114f0f51bf4f72fa6abb65145a704c55a3a2557fb82e6b988f72d293664944ca6c2597dba771c39923baac4064e0ab63ea6a11140724295b63e9000a90e568e1a62a8d73dcb8e7c62516f377b00ebd42ce9dd637b7c4ba50c2c331adab5dae7d9187ccb21c38653c130033bc44f9b571b845e44b50150bb20195dc6f039c42aa58d772eb4235b5a0fa986ba8264dddea08f353ae6a3555a4cdc0a794cc69a9438d216abe904b67033701760f421716dd90eda89575f12ec09d87712b4455f2ea0de8ac557ea1578ad17ed1f54ac028af6cc240d2d469000fa990e9e262170e2eb551e28b906aff00701204160138ce65dd10b5179c2520bc1c54036c14991d1047668cdab3998f81bcb4ee622a0e0e621534b06968f44cd734114f3896565cfbff001358563a3706dc38ff00046e358badee29e32ce8c644589e999374be88a9853c6621cc4d8b3d32c14ae5acacb91e226d897cc576a3404c798dda545f97897d333ab61e26cea056dcf75dc33168b761e48edadb2d1c2d97896d1be26064fc9330f2bdc0720bb65a55bda6306e68a8819a839afc532f8916e99f9fa88ba9857ebf85fcc120bb723355a80f94829e13b6516bdf896e0f996b27f04ba2bf312e5f10b6c850349f328e007fc5b50be170506889c443ac7c4a5e1f88ae5fc9075b240326e34ee546519825728b69e6014183c445b945af4a8cb96e420bb387922010ea19b3920520e2132f4f995ecb82bb8e4ad5c9ca5a22ee78956dfcd501e7e217ed11c15153867040299195e082b4f09e0d3a978d942065a65f230a7eac6fa3f610981550e441b1b0bdcf027ab8159a788b9c5662e956e6a6096e172853e72429c930772cbcb0dec23a8285ee7846fa8d001f0950233cc532c66adf12d62beaa648b010e3153497435f920a6d41d73173b8d184f8959550107a95b4855655dbd425350796f67719f82c7ee5480461e29f50469ba3cfcc1ca028a1320d70b784616e610f943eccf6d79954eb0ddf90c112501216cd63dcc5e02d72fb1605800d7441454c0e0b4d67cc050583e11f0dbb2c790cc989520c4cb03cca5793b89e07a23a8d3db28ac8f4ca2dddf8188da8f6c2f900e25a69af4a8ab40b1cb112dbaa816818d4805687c32d8b528cb1b6bdc422638c33d92d635ea2aac189cad5110b22dc103872eca88567f110e3e11682a5964cb983de0cf888211c0a589dc40532e52e00bdd565b9cea08a570eb05bea5d9773e3649365d5d456b2503a3ea38a04aa1b8f50d0715978cfa963a0e8ccdfa90784eb117345cbf9f301f000c32f2cd2d02b0f1158b2e8a4ea1405600c125851a95dd5a8b7f52e872811aee200b9045a8788a608a92f43416599aa996054f1124085d464ea3a2cab52a4a165929c13dc571696e542816fd225884f2ea607c4b42340f72b81f1102dfa2025c609403d3c4456efc4745e38278999db9c1a87660a3057c4672069d4ac9ba76c61d6ea07444d605158b3c0c259eda071aa4b9516369b996215640382cc5692da36f95fe26421534bc413d8bf475c5798ead3425af50e2aad8b879628f26613afb840bd9c30939696d2cb75cc7132841cfd4f6102610a31b8a78811a52ffe20e1ad77e6d557f1342315f000ce62b11489e0dbf3084a235b1c6bc410aa01439480c55e2a6fddc52a0aa835c0bfb8a10941c5732d59a63190ccf8fd337dfcca298bee2ec141c44ceb3c30c2a9079220fa881319f71c5a397827abb85dbba60646fa84c25a330dd997533a70412d6d533e4f64c0e15f88910ab18611d5dc6bda6e73b35168d166d3b5ccc0380fc410b58556388eb405154d328361772c129feb8d1278319f9866f2146f92fa204cc0a0d5f99b83a6598835da01e6f9957eb5111f880e1150155f2f32a09206f667b8de490283e90111dc8067f70c060a0d077086c9b032c54b1e083d5cf76fca5b4244a7e09a2c8c9b4f3129a6da861639f12fff00498e6016ed1ca469fb16473875d40c5083bc59a18134e618c96359be1a8a9d5c1363f339c0537039aa79940939c4cbf451041b5d620ba0daac898a4ad467c61fe2160b0202ff00b9b9141afbaac4bc90ae6ae7f3aa80ea30a5938c42d53bb4f8f8850b0e4e83e21020bc37c20a241a0289a716c747b5e48d99691e62020152aa12c559846209c19ccc2e919aaff535783707767dc4b430e04e215c6a7903dd91c8a39aaa89ef1f2f897bd3b4e7f110da87c4b8f67896752ee9c0bd31678af10cbe255f4bf5142b055e60d5ad788ad0706619637e61775e8945387b9a79799777b3899c5129c04fcb98ae9b318c731b68c7a84846bde7fa9876c9071ad52e53808464e54f98231354573152d02511b66ec9b0ec953ee847e06074018e0a86de4dc02d58b2aae26b1b861a48856a9e626094e23b66597c9c4000c15e08619e369a56bcadb8b2d012b63e6eba941afa25e612fa83d2b1500e14f61316ff0012b9b2e1e6658240b4606a0ae5bf505b50fb8aad1f332c966516161a6f2c1d84bea1973a856896398eea01bc4a8b1b28b506c3b4177cbf5035558239a850d10458a6dfa823325c8a0653a036251cc2c3e06a54059e732810a26e863562e7b4146c0bd4e580fa9c18183485dde2504d7e6537980b5f088181fd24f08202f5cce803a8a069abc13dae5dd9b10d9bccdda8beea5fca1a0bcb88e55706999a46932bd444d27cc50119f089a7e50b1a825b23d477bf8107c3f13d2bca89a6df2454e5db1c3d4a1e03769e0ab854d600b1c5df505b770914ce8a8cd728b847ba835d02503895acfc4b56bf136cd54735008e4b8255f688ec968595734bb5c5c8dccd5a0c1c955f117b29e4a9ac6e659ba9930d7b085bf9451a893232c71397f88173965e044aac5de27908f9898b659456e0c324cb529787106f16bda4783350aac466fbbf1332d9263c45cfe2a17090d19b63f10756abc86eb73168a7775129cc4a8b2a54018cb72e8a750074e22c5671415dc10e1321639f112c3472897d09ca3832c12f67b979216d7094a16c7132995f887b5ed960318bcc408953e608f1532ac6b799b6f9d4a3bb8239651ee35c2fab80bbcbee5536d3a09e61f333057dc2ccbb788e75128538800422ee14343d431bfa95c83d418cca32198e0752a5cd7925dc8fa1712eb8afcc024d46e068e86ccc1e22e4c310768738ab8d5367f894ce47cc32ee1905c44a2ae5334b98f48af50a3178ee15e529338ccbabdbea3183ee62861a4a11643b9477b8ed87bc4bde21ea1b4e896a75717a08e71c92f7311c6a5addf313977283f3500abd234ad5be6169e50d888d3281f716105467b21bc69d4a4b2fab95539f4d57302a0a0a6e383a94b9bc410217e1961accabcd5f8956e311387f134665c734c50e4cf988787a894e5f31fa468e6040665808b29d90215329f13e0c4c5f50e7989e544f243cc17a8637fe06dea5f09f331dc6ddd789815fb8ad00b36c2cb2d771178a806ee55e9240e00dc75631f04bebe415fcc16e287b399dd278c46b0a22a7ae65515a8a8ec3fb88d2203971555292cd5d5ee01d608055f69bdb0868578b9dd28e7b8a515b65acab7a95652f682b0c3c4321567b82ea99cad81c3080e48bd450d99f297b61961a7100c8996a63a2f51543a8a555e65c0b983728adfe25aeaa196a0705eee5019c0c1976f52ce4a6ae2b058d05c43791f706f15af33657f0886943e22b0297ab7ce1dc58a953d057e88c690b1cf9625029d5cf822ca684ab58e3a850c559ac4be25c5045ed8817a32ef1f44053a0e616dbf28f91f52ab4905e2afa85a0b95ca932ee6723a83fec97903d90ae40c3d1af1504ae21f33fffd9');


--
-- TOC entry 3255 (class 0 OID 16540)
-- Dependencies: 232
-- Data for Name: prescripciones; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.prescripciones VALUES (6, 11, NULL, NULL, NULL, NULL, NULL, true, '2018-09-03 15:50:00.256', 'root', '2018-09-03 15:50:00.256', 'root');
INSERT INTO public.prescripciones VALUES (7, 11, NULL, NULL, NULL, NULL, NULL, true, '2018-09-03 16:32:04.759', 'root', '2018-09-03 16:32:04.759', 'root');
INSERT INTO public.prescripciones VALUES (8, 13, NULL, NULL, NULL, NULL, NULL, true, '2018-09-04 08:34:15.461', 'root', '2018-09-04 08:34:15.461', 'root');
INSERT INTO public.prescripciones VALUES (9, 14, 'Medicamento 01', '500 mg', 'Cada 8 horas', '1 semana', 'Ninguna', true, '2018-09-05 11:47:01.919', 'root', '2018-09-05 16:20:02.86', 'root');
INSERT INTO public.prescripciones VALUES (10, 14, 'Medicamento 02', '1 comprimido', 'Cada 10 horas', '2 días', 'Ninguna', true, '2018-09-05 14:17:55.063', 'root', '2018-09-05 16:20:02.875', 'root');
INSERT INTO public.prescripciones VALUES (12, 27, '', '', '', '', '', true, '2018-09-14 10:25:50.051', 'root', '2018-09-14 12:34:26.514', 'root');
INSERT INTO public.prescripciones VALUES (13, 27, '', '', '', '', '', true, '2018-09-14 10:27:27.936', 'root', '2018-09-14 12:34:26.528', 'root');
INSERT INTO public.prescripciones VALUES (17, 41, 'Prescripciones', 'Prescripciones', '', '', '', true, '2018-11-10 23:13:10.685', 'root', '2018-11-10 23:29:17.663', 'root');
INSERT INTO public.prescripciones VALUES (18, 41, '', '', 'a', 'a', 'a', true, '2018-11-10 23:13:14.494', 'root', '2018-11-10 23:29:26.152', 'root');
INSERT INTO public.prescripciones VALUES (19, 41, '', '', '', '', '', true, '2018-11-10 23:13:20.074', 'root', '2018-11-10 23:29:26.211', 'root');
INSERT INTO public.prescripciones VALUES (20, 41, NULL, NULL, NULL, NULL, NULL, true, '2018-11-10 23:29:26.298', 'root', '2018-11-10 23:29:26.298', 'root');
INSERT INTO public.prescripciones VALUES (28, 51, '01', '01', '01', '01', '01', true, '2018-11-11 20:16:27.601', 'root', '2018-11-11 21:40:46.796', 'root');
INSERT INTO public.prescripciones VALUES (29, 51, '02', '02', '02', '02', '02', true, '2018-11-11 20:26:54.267', 'root', '2018-11-11 21:40:46.825', 'root');


--
-- TOC entry 3257 (class 0 OID 16548)
-- Dependencies: 234
-- Data for Name: profesionales; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.profesionales VALUES (6, 31, 34, 1, true, '2018-09-12 10:26:24.352', 'root', '2018-11-03 21:07:06.899', 'root');
INSERT INTO public.profesionales VALUES (3, 14, 32, 1, true, '2018-07-24 18:09:08.391', 'imordonez', '2018-11-03 21:20:32.045', 'root');
INSERT INTO public.profesionales VALUES (5, 5, 33, 1, true, '2018-08-30 10:03:26.735', 'apvillalba', '2018-11-11 21:59:49.373', 'root');
INSERT INTO public.profesionales VALUES (1, 1, 33, 1, true, NULL, NULL, '2018-11-13 15:00:40.368', 'root');
INSERT INTO public.profesionales VALUES (2, 3, 34, 1, true, '2018-07-24 18:05:53.302', 'imordonez', '2019-01-27 15:26:06.932', 'root');


--
-- TOC entry 3259 (class 0 OID 16556)
-- Dependencies: 236
-- Data for Name: usuarios; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.usuarios VALUES (1, 1, 4, NULL, 1, NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO public.usuarios VALUES (7, 3, 7, 1, 14, NULL, '2018-07-12 16:46:51.825', 'root', '2018-07-12 16:56:15.187', 'root', true);
INSERT INTO public.usuarios VALUES (8, 2, 7, 1, 3, NULL, '2018-07-24 13:54:50.415', 'root', '2018-08-16 08:37:54.755', 'root', true);
INSERT INTO public.usuarios VALUES (13, 2, 7, 1, 5, NULL, '2018-08-16 08:41:41.634', 'root', '2018-08-16 08:41:41.634', 'root', true);
INSERT INTO public.usuarios VALUES (14, 2, 7, 1, 1, NULL, '2018-08-30 11:13:00.727', 'root', '2018-08-30 11:13:00.727', 'root', true);
INSERT INTO public.usuarios VALUES (15, 2, 5, 1, 1, NULL, '2019-01-27 20:07:24.801', 'root', '2019-01-27 20:07:24.801', 'root', true);


--
-- TOC entry 3287 (class 0 OID 0)
-- Dependencies: 197
-- Name: archivos_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.archivos_id_seq', 80, true);


--
-- TOC entry 3288 (class 0 OID 0)
-- Dependencies: 199
-- Name: atenciones_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.atenciones_id_seq', 69, true);


--
-- TOC entry 3289 (class 0 OID 0)
-- Dependencies: 201
-- Name: campos_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.campos_id_seq', 20, true);


--
-- TOC entry 3290 (class 0 OID 0)
-- Dependencies: 203
-- Name: citas_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.citas_id_seq', 37, true);


--
-- TOC entry 3291 (class 0 OID 0)
-- Dependencies: 205
-- Name: datos_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.datos_id_seq', 434, true);


--
-- TOC entry 3292 (class 0 OID 0)
-- Dependencies: 207
-- Name: direcciones_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.direcciones_id_seq', 46, true);


--
-- TOC entry 3293 (class 0 OID 0)
-- Dependencies: 209
-- Name: formulas_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.formulas_id_seq', 30, true);


--
-- TOC entry 3294 (class 0 OID 0)
-- Dependencies: 211
-- Name: horarios_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.horarios_id_seq', 198, true);


--
-- TOC entry 3295 (class 0 OID 0)
-- Dependencies: 213
-- Name: horas_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.horas_id_seq', 24, true);


--
-- TOC entry 3296 (class 0 OID 0)
-- Dependencies: 215
-- Name: instituciones_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.instituciones_id_seq', 4, true);


--
-- TOC entry 3297 (class 0 OID 0)
-- Dependencies: 217
-- Name: maestros_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.maestros_id_seq', 22, true);


--
-- TOC entry 3298 (class 0 OID 0)
-- Dependencies: 219
-- Name: materiales_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.materiales_id_seq', 4, true);


--
-- TOC entry 3299 (class 0 OID 0)
-- Dependencies: 221
-- Name: menus_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.menus_id_seq', 26, true);


--
-- TOC entry 3300 (class 0 OID 0)
-- Dependencies: 223
-- Name: ordenes_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.ordenes_id_seq', 12, true);


--
-- TOC entry 3301 (class 0 OID 0)
-- Dependencies: 225
-- Name: pacientes_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.pacientes_id_seq', 30, true);


--
