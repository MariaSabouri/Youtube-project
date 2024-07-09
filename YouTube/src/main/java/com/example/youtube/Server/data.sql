--
-- PostgreSQL database dump
--

-- Dumped from database version 16.3
-- Dumped by pg_dump version 16.3

-- Started on 2024-07-09 23:14:06

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 2 (class 3079 OID 16384)
-- Name: adminpack; Type: EXTENSION; Schema: -; Owner: -
--

CREATE EXTENSION IF NOT EXISTS adminpack WITH SCHEMA pg_catalog;


--
-- TOC entry 4862 (class 0 OID 0)
-- Dependencies: 2
-- Name: EXTENSION adminpack; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION adminpack IS 'administrative functions for PostgreSQL';


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 216 (class 1259 OID 40960)
-- Name: channelplayliststable; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.channelplayliststable (
    pcid integer NOT NULL,
    pcname character varying(25) NOT NULL,
    usertable_uname character varying(25) NOT NULL,
    "PCID" integer NOT NULL
);


ALTER TABLE public.channelplayliststable OWNER TO postgres;

--
-- TOC entry 224 (class 1259 OID 57344)
-- Name: channelplayliststable_ID2_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public."channelplayliststable_ID2_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public."channelplayliststable_ID2_seq" OWNER TO postgres;

--
-- TOC entry 4863 (class 0 OID 0)
-- Dependencies: 224
-- Name: channelplayliststable_ID2_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public."channelplayliststable_ID2_seq" OWNED BY public.channelplayliststable."PCID";


--
-- TOC entry 217 (class 1259 OID 40965)
-- Name: commentstable; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.commentstable (
    messageid integer NOT NULL,
    playlistchannelvideotable_vpcid integer NOT NULL,
    message character varying(1000) NOT NULL,
    usertable_uname character varying(25) NOT NULL,
    commentstable_messageid integer
);


ALTER TABLE public.commentstable OWNER TO postgres;

--
-- TOC entry 218 (class 1259 OID 40972)
-- Name: playlistchannelvideotable; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.playlistchannelvideotable (
    vpcid integer DEFAULT nextval('public."channelplayliststable_ID2_seq"'::regclass) NOT NULL,
    channelplayliststable_pcid integer NOT NULL,
    videotable_vid integer NOT NULL,
    "NumberOfLike" integer DEFAULT 0,
    "NumberOfDislike" integer DEFAULT 0
);


ALTER TABLE public.playlistchannelvideotable OWNER TO postgres;

--
-- TOC entry 225 (class 1259 OID 57359)
-- Name: subscriptionsTable; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."subscriptionsTable" (
    username character varying(25),
    channelname character varying(25)
);


ALTER TABLE public."subscriptionsTable" OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 40977)
-- Name: userlikeanddislikeaction; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.userlikeanddislikeaction (
    id integer DEFAULT nextval('public."channelplayliststable_ID2_seq"'::regclass) NOT NULL,
    playlistchannelvideotable_vpcid integer NOT NULL,
    usertable_uname character varying(25) NOT NULL,
    "Like" boolean DEFAULT false NOT NULL,
    dislike boolean DEFAULT false NOT NULL
);


ALTER TABLE public.userlikeanddislikeaction OWNER TO postgres;

--
-- TOC entry 220 (class 1259 OID 40984)
-- Name: userplaylisttable; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.userplaylisttable (
    puid integer NOT NULL,
    puname character varying(25) NOT NULL,
    usertable_uname character varying(25) NOT NULL
);


ALTER TABLE public.userplaylisttable OWNER TO postgres;

--
-- TOC entry 221 (class 1259 OID 40989)
-- Name: usertable; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.usertable (
    username character varying(25) NOT NULL,
    password integer NOT NULL,
    name character varying(25),
    channel character varying(25),
    "numberOfSubscriptions" integer DEFAULT 0
);


ALTER TABLE public.usertable OWNER TO postgres;

--
-- TOC entry 222 (class 1259 OID 40995)
-- Name: uservideoplaylist; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.uservideoplaylist (
    vpuid integer NOT NULL,
    userplaylisttable_puid integer NOT NULL,
    playlistchannelvideotable_vpcid integer NOT NULL
);


ALTER TABLE public.uservideoplaylist OWNER TO postgres;

--
-- TOC entry 223 (class 1259 OID 41000)
-- Name: videotable; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.videotable (
    vid integer NOT NULL,
    usertable_uname character varying(25) NOT NULL,
    vname character varying(25) NOT NULL,
    "numberOfview" integer DEFAULT 0,
    "numberOfLikes" integer DEFAULT 0,
    "dateOfIllustration" character varying(25),
    idd integer NOT NULL,
    "numberOfDislike" integer DEFAULT 0
);


ALTER TABLE public.videotable OWNER TO postgres;

--
-- TOC entry 226 (class 1259 OID 57362)
-- Name: videotable_idd_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.videotable_idd_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.videotable_idd_seq OWNER TO postgres;

--
-- TOC entry 4864 (class 0 OID 0)
-- Dependencies: 226
-- Name: videotable_idd_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.videotable_idd_seq OWNED BY public.videotable.idd;


--
-- TOC entry 4668 (class 2604 OID 57352)
-- Name: channelplayliststable pcid; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.channelplayliststable ALTER COLUMN pcid SET DEFAULT nextval('public."channelplayliststable_ID2_seq"'::regclass);


--
-- TOC entry 4669 (class 2604 OID 57345)
-- Name: channelplayliststable PCID; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.channelplayliststable ALTER COLUMN "PCID" SET DEFAULT nextval('public."channelplayliststable_ID2_seq"'::regclass);


--
-- TOC entry 4677 (class 2604 OID 57368)
-- Name: videotable vid; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.videotable ALTER COLUMN vid SET DEFAULT nextval('public.videotable_idd_seq'::regclass);


--
-- TOC entry 4680 (class 2604 OID 57363)
-- Name: videotable idd; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.videotable ALTER COLUMN idd SET DEFAULT nextval('public.videotable_idd_seq'::regclass);


--
-- TOC entry 4846 (class 0 OID 40960)
-- Dependencies: 216
-- Data for Name: channelplayliststable; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.channelplayliststable (pcid, pcname, usertable_uname, "PCID") FROM stdin;
375656863	ss	l	1
3468	kl	l	4
5	playlist name	l	6
11	name	l;k	12
\.


--
-- TOC entry 4847 (class 0 OID 40965)
-- Dependencies: 217
-- Data for Name: commentstable; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.commentstable (messageid, playlistchannelvideotable_vpcid, message, usertable_uname, commentstable_messageid) FROM stdin;
\.


--
-- TOC entry 4848 (class 0 OID 40972)
-- Dependencies: 218
-- Data for Name: playlistchannelvideotable; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.playlistchannelvideotable (vpcid, channelplayliststable_pcid, videotable_vid, "NumberOfLike", "NumberOfDislike") FROM stdin;
\.


--
-- TOC entry 4855 (class 0 OID 57359)
-- Dependencies: 225
-- Data for Name: subscriptionsTable; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."subscriptionsTable" (username, channelname) FROM stdin;
\.


--
-- TOC entry 4849 (class 0 OID 40977)
-- Dependencies: 219
-- Data for Name: userlikeanddislikeaction; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.userlikeanddislikeaction (id, playlistchannelvideotable_vpcid, usertable_uname, "Like", dislike) FROM stdin;
\.


--
-- TOC entry 4850 (class 0 OID 40984)
-- Dependencies: 220
-- Data for Name: userplaylisttable; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.userplaylisttable (puid, puname, usertable_uname) FROM stdin;
192577626	hh	l
\.


--
-- TOC entry 4851 (class 0 OID 40989)
-- Dependencies: 221
-- Data for Name: usertable; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.usertable (username, password, name, channel, "numberOfSubscriptions") FROM stdin;
p	99	\N	\N	0
qq	33	\N	\N	0
ww	88	\N	\N	0
ww	88	wwkjkjkj	\N	0
m	123	n	\N	0
pp	123	lc	\N	0
l	99	\N	sha la la	0
mmmmm	123	m	shala	0
String Username	123	Name	shala	0
\.


--
-- TOC entry 4852 (class 0 OID 40995)
-- Dependencies: 222
-- Data for Name: uservideoplaylist; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.uservideoplaylist (vpuid, userplaylisttable_puid, playlistchannelvideotable_vpcid) FROM stdin;
\.


--
-- TOC entry 4853 (class 0 OID 41000)
-- Dependencies: 223
-- Data for Name: videotable; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.videotable (vid, usertable_uname, vname, "numberOfview", "numberOfLikes", "dateOfIllustration", idd, "numberOfDislike") FROM stdin;
-2145389220	l	String VideoName	\N	0	\N	1	0
-1149184690	p	amir	\N	0	\N	2	0
1621124883	l	lhkjjklh	\N	0	\N	3	0
6	w	qw	1	0	\N	7	0
8	q	aa	0	0	\N	9	0
\.


--
-- TOC entry 4865 (class 0 OID 0)
-- Dependencies: 224
-- Name: channelplayliststable_ID2_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public."channelplayliststable_ID2_seq"', 13, true);


--
-- TOC entry 4866 (class 0 OID 0)
-- Dependencies: 226
-- Name: videotable_idd_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.videotable_idd_seq', 9, true);


--
-- TOC entry 4683 (class 2606 OID 40964)
-- Name: channelplayliststable channelplayliststable_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.channelplayliststable
    ADD CONSTRAINT channelplayliststable_pk PRIMARY KEY (pcid);


--
-- TOC entry 4685 (class 2606 OID 40971)
-- Name: commentstable commentstable_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.commentstable
    ADD CONSTRAINT commentstable_pk PRIMARY KEY (messageid);


--
-- TOC entry 4687 (class 2606 OID 40976)
-- Name: playlistchannelvideotable playlistchannelvideotable_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.playlistchannelvideotable
    ADD CONSTRAINT playlistchannelvideotable_pk PRIMARY KEY (vpcid);


--
-- TOC entry 4689 (class 2606 OID 40983)
-- Name: userlikeanddislikeaction userlikeanddislikeaction_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.userlikeanddislikeaction
    ADD CONSTRAINT userlikeanddislikeaction_pk PRIMARY KEY (id);


--
-- TOC entry 4691 (class 2606 OID 40988)
-- Name: userplaylisttable userplaylisttable_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.userplaylisttable
    ADD CONSTRAINT userplaylisttable_pk PRIMARY KEY (puid);


--
-- TOC entry 4693 (class 2606 OID 40999)
-- Name: uservideoplaylist uservideoplaylist_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.uservideoplaylist
    ADD CONSTRAINT uservideoplaylist_pk PRIMARY KEY (vpuid);


--
-- TOC entry 4695 (class 2606 OID 41004)
-- Name: videotable videotable_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.videotable
    ADD CONSTRAINT videotable_pk PRIMARY KEY (vid);


--
-- TOC entry 4696 (class 2606 OID 41010)
-- Name: commentstable commentstable_commentstable; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.commentstable
    ADD CONSTRAINT commentstable_commentstable FOREIGN KEY (commentstable_messageid) REFERENCES public.commentstable(messageid);


--
-- TOC entry 4697 (class 2606 OID 41015)
-- Name: commentstable commentstable_playlistchannelvideotable; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.commentstable
    ADD CONSTRAINT commentstable_playlistchannelvideotable FOREIGN KEY (playlistchannelvideotable_vpcid) REFERENCES public.playlistchannelvideotable(vpcid);


--
-- TOC entry 4698 (class 2606 OID 41025)
-- Name: playlistchannelvideotable playlistchannelvideotable_channelplayliststable; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.playlistchannelvideotable
    ADD CONSTRAINT playlistchannelvideotable_channelplayliststable FOREIGN KEY (channelplayliststable_pcid) REFERENCES public.channelplayliststable(pcid);


--
-- TOC entry 4699 (class 2606 OID 41030)
-- Name: playlistchannelvideotable playlistchannelvideotable_videotable; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.playlistchannelvideotable
    ADD CONSTRAINT playlistchannelvideotable_videotable FOREIGN KEY (videotable_vid) REFERENCES public.videotable(vid);


--
-- TOC entry 4700 (class 2606 OID 41035)
-- Name: userlikeanddislikeaction userlikeanddislikeaction_playlistchannelvideotable; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.userlikeanddislikeaction
    ADD CONSTRAINT userlikeanddislikeaction_playlistchannelvideotable FOREIGN KEY (playlistchannelvideotable_vpcid) REFERENCES public.playlistchannelvideotable(vpcid);


--
-- TOC entry 4701 (class 2606 OID 41050)
-- Name: uservideoplaylist uservideoplaylist_playlistchannelvideotable; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.uservideoplaylist
    ADD CONSTRAINT uservideoplaylist_playlistchannelvideotable FOREIGN KEY (playlistchannelvideotable_vpcid) REFERENCES public.playlistchannelvideotable(vpcid);


--
-- TOC entry 4702 (class 2606 OID 41055)
-- Name: uservideoplaylist uservideoplaylist_userplaylisttable; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.uservideoplaylist
    ADD CONSTRAINT uservideoplaylist_userplaylisttable FOREIGN KEY (userplaylisttable_puid) REFERENCES public.userplaylisttable(puid);


-- Completed on 2024-07-09 23:14:07

--
-- PostgreSQL database dump complete
--

