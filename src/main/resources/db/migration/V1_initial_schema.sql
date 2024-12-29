--
-- PostgreSQL database dump
--

-- Dumped from database version 17.2
-- Dumped by pg_dump version 17.2

-- Started on 2024-12-29 20:58:30

-- TOC entry 217 (class 1259 OID 33386)
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
-- TOC entry 218 (class 1259 OID 33393)
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
-- TOC entry 219 (class 1259 OID 33400)
-- Name: images; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.images (
    image_count integer NOT NULL,
    record_status boolean NOT NULL,
    create_time timestamp(6) without time zone NOT NULL,
    update_time timestamp(6) without time zone NOT NULL,
    id uuid NOT NULL,
    post_id uuid NOT NULL,
    image_path character varying(500) NOT NULL
);


ALTER TABLE public.images OWNER TO postgres;

--
-- TOC entry 220 (class 1259 OID 33407)
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
-- TOC entry 221 (class 1259 OID 33412)
-- Name: posts; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.posts (
    record_status boolean NOT NULL,
    comment_count bigint NOT NULL,
    create_time timestamp(6) without time zone NOT NULL,
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
-- TOC entry 222 (class 1259 OID 33419)
-- Name: user_follow; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.user_follow (
    follower_id uuid NOT NULL,
    user_id uuid NOT NULL
);


ALTER TABLE public.user_follow OWNER TO postgres;

--
-- TOC entry 223 (class 1259 OID 33424)
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
    username character varying(33) NOT NULL,
    name character varying(43) NOT NULL,
    surname character varying(50) NOT NULL,
    email character varying(345) NOT NULL,
    biography text,
    password character varying(255) NOT NULL
);


ALTER TABLE public.users OWNER TO postgres;

--
-- TOC entry 4766 (class 2606 OID 33392)
-- Name: categories categories_category_name_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.categories
    ADD CONSTRAINT categories_category_name_key UNIQUE (category_name);


--
-- TOC entry 4768 (class 2606 OID 33390)
-- Name: categories categories_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.categories
    ADD CONSTRAINT categories_pkey PRIMARY KEY (id);


--
-- TOC entry 4770 (class 2606 OID 33399)
-- Name: comments comments_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.comments
    ADD CONSTRAINT comments_pkey PRIMARY KEY (id);


--
-- TOC entry 4772 (class 2606 OID 33406)
-- Name: images images_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.images
    ADD CONSTRAINT images_pkey PRIMARY KEY (id);


--
-- TOC entry 4774 (class 2606 OID 33411)
-- Name: likes likes_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.likes
    ADD CONSTRAINT likes_pkey PRIMARY KEY (id);


--
-- TOC entry 4776 (class 2606 OID 33418)
-- Name: posts posts_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.posts
    ADD CONSTRAINT posts_pkey PRIMARY KEY (id);


--
-- TOC entry 4778 (class 2606 OID 33423)
-- Name: user_follow user_follow_user_id_follower_id_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_follow
    ADD CONSTRAINT user_follow_user_id_follower_id_key UNIQUE (user_id, follower_id);


--
-- TOC entry 4780 (class 2606 OID 33432)
-- Name: users users_avatar_id_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_avatar_id_key UNIQUE (avatar_id);


--
-- TOC entry 4782 (class 2606 OID 33436)
-- Name: users users_email_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_email_key UNIQUE (email);


--
-- TOC entry 4784 (class 2606 OID 33430)
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- TOC entry 4786 (class 2606 OID 33434)
-- Name: users users_username_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_username_key UNIQUE (username);


--
-- TOC entry 4798 (class 2606 OID 33492)
-- Name: users fk2lttmx8vn9eecykig5sch3v0h; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT fk2lttmx8vn9eecykig5sch3v0h FOREIGN KEY (avatar_id) REFERENCES public.images(id);


--
-- TOC entry 4794 (class 2606 OID 33477)
-- Name: posts fk5lidm6cqbc7u4xhqpxm898qme; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.posts
    ADD CONSTRAINT fk5lidm6cqbc7u4xhqpxm898qme FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- TOC entry 4787 (class 2606 OID 33447)
-- Name: comments fk8omq0tc18jd43bu5tjh6jvraq; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.comments
    ADD CONSTRAINT fk8omq0tc18jd43bu5tjh6jvraq FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- TOC entry 4790 (class 2606 OID 33452)
-- Name: images fkcp0pycisii8ub3q4b7x5mfpn1; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.images
    ADD CONSTRAINT fkcp0pycisii8ub3q4b7x5mfpn1 FOREIGN KEY (post_id) REFERENCES public.posts(id);


--
-- TOC entry 4791 (class 2606 OID 33457)
-- Name: likes fke4guax66lb963pf27kvm7ikik; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.likes
    ADD CONSTRAINT fke4guax66lb963pf27kvm7ikik FOREIGN KEY (comment_id) REFERENCES public.comments(id);


--
-- TOC entry 4788 (class 2606 OID 33437)
-- Name: comments fkh4c7lvsc298whoyd4w9ta25cr; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.comments
    ADD CONSTRAINT fkh4c7lvsc298whoyd4w9ta25cr FOREIGN KEY (post_id) REFERENCES public.posts(id);


--
-- TOC entry 4796 (class 2606 OID 33482)
-- Name: user_follow fki9gqdjcgtclgypxp2krye61n7; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_follow
    ADD CONSTRAINT fki9gqdjcgtclgypxp2krye61n7 FOREIGN KEY (follower_id) REFERENCES public.users(id);


--
-- TOC entry 4795 (class 2606 OID 33472)
-- Name: posts fkijnwr3brs8vaosl80jg9rp7uc; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.posts
    ADD CONSTRAINT fkijnwr3brs8vaosl80jg9rp7uc FOREIGN KEY (category_id) REFERENCES public.categories(id);


--
-- TOC entry 4792 (class 2606 OID 33467)
-- Name: likes fknvx9seeqqyy71bij291pwiwrg; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.likes
    ADD CONSTRAINT fknvx9seeqqyy71bij291pwiwrg FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- TOC entry 4789 (class 2606 OID 33442)
-- Name: comments fkol8l8lh9s6o5rx1s3ksp30qnj; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.comments
    ADD CONSTRAINT fkol8l8lh9s6o5rx1s3ksp30qnj FOREIGN KEY (supcomment_id) REFERENCES public.comments(id);


--
-- TOC entry 4797 (class 2606 OID 33487)
-- Name: user_follow fkpt785jr8exenf2qqlsx709gcg; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_follow
    ADD CONSTRAINT fkpt785jr8exenf2qqlsx709gcg FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- TOC entry 4793 (class 2606 OID 33462)
-- Name: likes fkry8tnr4x2vwemv2bb0h5hyl0x; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.likes
    ADD CONSTRAINT fkry8tnr4x2vwemv2bb0h5hyl0x FOREIGN KEY (post_id) REFERENCES public.posts(id);


-- Completed on 2024-12-29 20:58:30

--
-- PostgreSQL database dump complete
--

