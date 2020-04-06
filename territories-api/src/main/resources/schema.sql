CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

drop table if exists continent cascade;
create table continent (id int8 generated by default as identity, iso_code2 varchar(255), primary key (id));
drop table if exists country cascade;
create table country (id uuid default uuid_generate_v1() not null, latitude float8 check (latitude>=-90 AND latitude<=90), longitude float8 check (longitude<=180 AND longitude>=-180), iso_code2 varchar(2) not null, iso_code3 varchar(3), iso_numeric int4, internet_cctld varchar(5), phone_prefix int2, primary key (id));
drop table if exists country_continents cascade;
create table country_continents (countries_id uuid default uuid_generate_v1() not null, continents_id int8 not null);
drop table if exists country_political_division_list cascade;
create table country_political_division_list (country_list_id uuid default uuid_generate_v1() not null, political_division_list_id int8 not null);
drop table if exists landmark cascade;
create table landmark (id int8 generated by default as identity, latitude float8 check (latitude>=-90 AND latitude<=90), longitude float8 check (longitude<=180 AND longitude>=-180), name varchar(255), street_address varchar(255), locality_id uuid default uuid_generate_v1(), primary key (id));
drop table if exists locality cascade;
create table locality (id uuid default uuid_generate_v1() not null, latitude float8 check (latitude>=-90 AND latitude<=90), longitude float8 check (longitude<=180 AND longitude>=-180), iso_code2 varchar(2) not null, iso_code3 varchar(3), iso_numeric int4, division_level int2, local_code varchar(255), name varchar(255), country_id uuid default uuid_generate_v1() not null, parent_locality_id uuid default uuid_generate_v1() not null, primary key (id));
drop table if exists political_division cascade;
create table political_division (id int8 generated by default as identity, division_level int2, name varchar(255), primary key (id));
alter table if exists country add constraint UK_55hucmu5uv84s4mwx4780d6bq unique (iso_code2);
alter table if exists country add constraint UK_36dgtb7tjioi4cj35g20b36x9 unique (iso_code3);
alter table if exists country add constraint UK_bdebt358g213lt2in79fypt9w unique (iso_numeric);
alter table if exists locality add constraint UK_94jc2hn57694mp4tq32hnm7kc unique (iso_code2);
alter table if exists locality add constraint UK_81jixb3wni5br8daap8a05xxn unique (iso_code3);
alter table if exists locality add constraint UK_sfndt886iow0q36em0tdonlud unique (iso_numeric);
alter table if exists country_continents add constraint FKtbdnofkbcys88vfor4exeqqj3 foreign key (continents_id) references continent;
alter table if exists country_continents add constraint FKhtguws6jwvh4ojf0ga0v78gbh foreign key (countries_id) references country;
alter table if exists country_political_division_list add constraint FKpnafyqk11m8yg1avob6yow1ih foreign key (political_division_list_id) references political_division;
alter table if exists country_political_division_list add constraint FKrgqoi035y764u40hd7ptfvjk3 foreign key (country_list_id) references country;
alter table if exists landmark add constraint FKfm7166jbj9p32rj864xe4rm1u foreign key (locality_id) references locality;
alter table if exists locality add constraint FK6b1x8a26j9w11je3qb0qy0apo foreign key (country_id) references country;
alter table if exists locality add constraint FKlxir51tpt6a1gstyvybskdhx4 foreign key (parent_locality_id) references locality;
