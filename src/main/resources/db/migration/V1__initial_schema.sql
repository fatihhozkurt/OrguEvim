--
-- PostgreSQL database dump
--

-- Dumped from database version 17.2
-- Dumped by pg_dump version 17.2

-- Started on 2024-12-31 21:16:18

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 218 (class 1259 OID 37300)
-- Name: categories; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.categories (
    record_status boolean NOT NULL,
    create_time timestamp(6) without time zone NOT NULL,
    post_count bigint NOT NULL,
    update_time timestamp(6) without time zone NOT NULL,
    id uuid NOT NULL,
    category_name character varying(20) NOT NULL
);


ALTER TABLE public.categories OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 37307)
-- Name: comments; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.comments (
    record_status boolean NOT NULL,
    create_time timestamp(6) without time zone NOT NULL,
    reply_count bigint NOT NULL,
    update_time timestamp(6) without time zone NOT NULL,
    id uuid NOT NULL,
    post_id uuid,
    supcomment_id uuid,
    user_id uuid NOT NULL,
    content text NOT NULL
);


ALTER TABLE public.comments OWNER TO postgres;

--
-- TOC entry 217 (class 1259 OID 37291)
-- Name: flyway_schema_history; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.flyway_schema_history (
    installed_rank integer NOT NULL,
    version character varying(50),
    description character varying(200) NOT NULL,
    type character varying(20) NOT NULL,
    script character varying(1000) NOT NULL,
    checksum integer,
    installed_by character varying(100) NOT NULL,
    installed_on timestamp without time zone DEFAULT now() NOT NULL,
    execution_time integer NOT NULL,
    success boolean NOT NULL
);


ALTER TABLE public.flyway_schema_history OWNER TO postgres;

--
-- TOC entry 220 (class 1259 OID 37314)
-- Name: images; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.images (
    record_status boolean NOT NULL,
    create_time timestamp(6) without time zone NOT NULL,
    update_time timestamp(6) without time zone NOT NULL,
    id uuid NOT NULL,
    post_id uuid,
    image_path character varying(500) NOT NULL
);


ALTER TABLE public.images OWNER TO postgres;

--
-- TOC entry 221 (class 1259 OID 37321)
-- Name: likes; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.likes (
    record_status boolean NOT NULL,
    create_time timestamp(6) without time zone NOT NULL,
    update_time timestamp(6) without time zone NOT NULL,
    comment_id uuid,
    id uuid NOT NULL,
    post_id uuid,
    user_id uuid NOT NULL
);


ALTER TABLE public.likes OWNER TO postgres;

--
-- TOC entry 222 (class 1259 OID 37326)
-- Name: posts; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.posts (
    record_status boolean NOT NULL,
    comment_count bigint NOT NULL,
    create_time timestamp(6) without time zone NOT NULL,
    image_count bigint NOT NULL,
    like_count bigint NOT NULL,
    update_time timestamp(6) without time zone NOT NULL,
    category_id uuid NOT NULL,
    id uuid NOT NULL,
    user_id uuid NOT NULL,
    title character varying(100) NOT NULL,
    youtube_link character varying(100),
    ingredients text NOT NULL,
    content text NOT NULL
);


ALTER TABLE public.posts OWNER TO postgres;

--
-- TOC entry 223 (class 1259 OID 37333)
-- Name: user_follow; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.user_follow (
    follower_id uuid NOT NULL,
    user_id uuid NOT NULL
);


ALTER TABLE public.user_follow OWNER TO postgres;

--
-- TOC entry 224 (class 1259 OID 37338)
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
    record_status boolean NOT NULL,
    create_time timestamp(6) without time zone NOT NULL,
    followers_count bigint NOT NULL,
    following_count bigint NOT NULL,
    post_count bigint NOT NULL,
    update_time timestamp(6) without time zone NOT NULL,
    avatar_id uuid,
    id uuid NOT NULL,
    password character varying(16) NOT NULL,
    username character varying(33) NOT NULL,
    name character varying(50) NOT NULL,
    surname character varying(50) NOT NULL,
    email character varying(345) NOT NULL,
    biography text
);


ALTER TABLE public.users OWNER TO postgres;

--
-- TOC entry 4774 (class 2606 OID 37306)
-- Name: categories categories_category_name_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.categories
    ADD CONSTRAINT categories_category_name_key UNIQUE (category_name);


--
-- TOC entry 4776 (class 2606 OID 37304)
-- Name: categories categories_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.categories
    ADD CONSTRAINT categories_pkey PRIMARY KEY (id);


--
-- TOC entry 4778 (class 2606 OID 37313)
-- Name: comments comments_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.comments
    ADD CONSTRAINT comments_pkey PRIMARY KEY (id);


--
-- TOC entry 4771 (class 2606 OID 37298)
-- Name: flyway_schema_history flyway_schema_history_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.flyway_schema_history
    ADD CONSTRAINT flyway_schema_history_pk PRIMARY KEY (installed_rank);


--
-- TOC entry 4780 (class 2606 OID 37320)
-- Name: images images_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.images
    ADD CONSTRAINT images_pkey PRIMARY KEY (id);


--
-- TOC entry 4782 (class 2606 OID 37325)
-- Name: likes likes_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.likes
    ADD CONSTRAINT likes_pkey PRIMARY KEY (id);


--
-- TOC entry 4784 (class 2606 OID 37332)
-- Name: posts posts_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.posts
    ADD CONSTRAINT posts_pkey PRIMARY KEY (id);


--
-- TOC entry 4786 (class 2606 OID 37337)
-- Name: user_follow user_follow_user_id_follower_id_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_follow
    ADD CONSTRAINT user_follow_user_id_follower_id_key UNIQUE (user_id, follower_id);


--
-- TOC entry 4788 (class 2606 OID 37346)
-- Name: users users_avatar_id_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_avatar_id_key UNIQUE (avatar_id);


--
-- TOC entry 4790 (class 2606 OID 37344)
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- TOC entry 4772 (class 1259 OID 37299)
-- Name: flyway_schema_history_s_idx; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX flyway_schema_history_s_idx ON public.flyway_schema_history USING btree (success);


--
-- TOC entry 4802 (class 2606 OID 37402)
-- Name: users fk2lttmx8vn9eecykig5sch3v0h; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT fk2lttmx8vn9eecykig5sch3v0h FOREIGN KEY (avatar_id) REFERENCES public.images(id);


--
-- TOC entry 4798 (class 2606 OID 37387)
-- Name: posts fk5lidm6cqbc7u4xhqpxm898qme; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.posts
    ADD CONSTRAINT fk5lidm6cqbc7u4xhqpxm898qme FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- TOC entry 4791 (class 2606 OID 37357)
-- Name: comments fk8omq0tc18jd43bu5tjh6jvraq; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.comments
    ADD CONSTRAINT fk8omq0tc18jd43bu5tjh6jvraq FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- TOC entry 4794 (class 2606 OID 37362)
-- Name: images fkcp0pycisii8ub3q4b7x5mfpn1; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.images
    ADD CONSTRAINT fkcp0pycisii8ub3q4b7x5mfpn1 FOREIGN KEY (post_id) REFERENCES public.posts(id);


--
-- TOC entry 4795 (class 2606 OID 37367)
-- Name: likes fke4guax66lb963pf27kvm7ikik; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.likes
    ADD CONSTRAINT fke4guax66lb963pf27kvm7ikik FOREIGN KEY (comment_id) REFERENCES public.comments(id);


--
-- TOC entry 4792 (class 2606 OID 37347)
-- Name: comments fkh4c7lvsc298whoyd4w9ta25cr; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.comments
    ADD CONSTRAINT fkh4c7lvsc298whoyd4w9ta25cr FOREIGN KEY (post_id) REFERENCES public.posts(id);


--
-- TOC entry 4800 (class 2606 OID 37392)
-- Name: user_follow fki9gqdjcgtclgypxp2krye61n7; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_follow
    ADD CONSTRAINT fki9gqdjcgtclgypxp2krye61n7 FOREIGN KEY (follower_id) REFERENCES public.users(id);


--
-- TOC entry 4799 (class 2606 OID 37382)
-- Name: posts fkijnwr3brs8vaosl80jg9rp7uc; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.posts
    ADD CONSTRAINT fkijnwr3brs8vaosl80jg9rp7uc FOREIGN KEY (category_id) REFERENCES public.categories(id);


--
-- TOC entry 4796 (class 2606 OID 37377)
-- Name: likes fknvx9seeqqyy71bij291pwiwrg; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.likes
    ADD CONSTRAINT fknvx9seeqqyy71bij291pwiwrg FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- TOC entry 4793 (class 2606 OID 37352)
-- Name: comments fkol8l8lh9s6o5rx1s3ksp30qnj; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.comments
    ADD CONSTRAINT fkol8l8lh9s6o5rx1s3ksp30qnj FOREIGN KEY (supcomment_id) REFERENCES public.comments(id);


--
-- TOC entry 4801 (class 2606 OID 37397)
-- Name: user_follow fkpt785jr8exenf2qqlsx709gcg; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_follow
    ADD CONSTRAINT fkpt785jr8exenf2qqlsx709gcg FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- TOC entry 4797 (class 2606 OID 37372)
-- Name: likes fkry8tnr4x2vwemv2bb0h5hyl0x; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.likes
    ADD CONSTRAINT fkry8tnr4x2vwemv2bb0h5hyl0x FOREIGN KEY (post_id) REFERENCES public.posts(id);


-- Completed on 2024-12-31 21:16:19

--
-- PostgreSQL database dump complete
--

