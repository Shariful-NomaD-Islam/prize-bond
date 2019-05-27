INSERT INTO fruit(pk_id, name) VALUES (nextval('fruit_pk_id_seq'), 'Cherry') ON CONFLICT DO NOTHING
INSERT INTO fruit(pk_id, name) VALUES (nextval('fruit_pk_id_seq'), 'Apple') ON CONFLICT DO NOTHING
INSERT INTO fruit(pk_id, name) VALUES (nextval('fruit_pk_id_seq'), 'Banana') ON CONFLICT DO NOTHING

INSERT INTO app_user(Pk_id, user_name, password, create_date_time, status) VALUES (nextval('app_user_pk_id_seq'), 'nomad', 'nomad', '2019-01-01 01:01:01', 'logged_out') ON CONFLICT DO NOTHING

INSERT INTO bond(pk_id, prefix, number, status, create_date_time, owner, remarks, fk_app_user_id) VALUES (nextval('bond_pk_id_seq'), 'KA', '0002965', 'active', '2019-01-01 01:01:01', 'nomad', null, 1)
INSERT INTO bondv2(pk_id, prefix, number, status, create_date_time, owner, remarks, bondv2ref) VALUES (nextval('bond_pk_id_seq'), 'GA', '0002966', 'active', '2019-01-01 01:01:01', 'nomad', null, 1)

INSERT INTO winner(pk_id, suffix, draw_number, prize_ammount, place, status, create_date_time) VALUES (nextval('winner_pk_id_seq'), '0002965', 100, 10000, 5, 'active','2019-01-01 01:01:01')

INSERT INTO rel_bond_winner(pk_id, status, create_date_time, remarks, fk_winner_id, fk_bond_id) VALUES (nextval('rel_bond_winner_pk_id_seq'), 'active', '2019-01-02 01:01:01', 'winner-takes-all', 1, 1)