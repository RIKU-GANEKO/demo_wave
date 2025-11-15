-- Name: update_updated_at_column(); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION public.update_updated_at_column() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$;


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: category; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.category (
    id integer NOT NULL,
    name character varying(255) NOT NULL,
--
CREATE TRIGGER update_category_updated_at BEFORE UPDATE ON public.category FOR EACH ROW EXECUTE FUNCTION public.update_updated_at_column();


--
-- Name: comment update_comment_updated_at; Type: TRIGGER; Schema: public; Owner: -
--

CREATE TRIGGER update_comment_updated_at BEFORE UPDATE ON public.comment FOR EACH ROW EXECUTE FUNCTION public.update_updated_at_column();


--
-- Name: demo update_demo_updated_at; Type: TRIGGER; Schema: public; Owner: -
--

CREATE TRIGGER update_demo_updated_at BEFORE UPDATE ON public.demo FOR EACH ROW EXECUTE FUNCTION public.update_updated_at_column();


--
-- Name: favorite_demo update_favorite_demo_updated_at; Type: TRIGGER; Schema: public; Owner: -
--

CREATE TRIGGER update_favorite_demo_updated_at BEFORE UPDATE ON public.favorite_demo FOR EACH ROW EXECUTE FUNCTION public.update_updated_at_column();


--
-- Name: participant update_participant_updated_at; Type: TRIGGER; Schema: public; Owner: -
--

CREATE TRIGGER update_participant_updated_at BEFORE UPDATE ON public.participant FOR EACH ROW EXECUTE FUNCTION public.update_updated_at_column();


--
-- Name: payment update_payment_updated_at; Type: TRIGGER; Schema: public; Owner: -
--

CREATE TRIGGER update_payment_updated_at BEFORE UPDATE ON public.payment FOR EACH ROW EXECUTE FUNCTION public.update_updated_at_column();


--
-- Name: prefecture update_prefecture_updated_at; Type: TRIGGER; Schema: public; Owner: -
--

CREATE TRIGGER update_prefecture_updated_at BEFORE UPDATE ON public.prefecture FOR EACH ROW EXECUTE FUNCTION public.update_updated_at_column();


--
-- Name: users update_users_updated_at; Type: TRIGGER; Schema: public; Owner: -
--

CREATE TRIGGER update_users_updated_at BEFORE UPDATE ON public.users FOR EACH ROW EXECUTE FUNCTION public.update_updated_at_column();


--
-- Name: comment comment_demo_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.comment
    ADD CONSTRAINT comment_demo_id_fkey FOREIGN KEY (demo_id) REFERENCES public.demo(id) ON DELETE CASCADE;


--
-- Name: comment comment_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.comment
    ADD CONSTRAINT comment_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(id) ON DELETE CASCADE;


--
-- Name: demo demo_category_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
